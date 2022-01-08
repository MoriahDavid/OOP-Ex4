package gui;
import api.*;
import game.Agent;
import game.Info;
import game.Pokemon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class GameDraw extends JPanel {

    private static final int MINIMUM_NODE_SIZE = 32;
    private static final int NODE_PADDING = 8;

    private final int EDGE_ARR_SIZE = 7;
    private final int EDGE_ARROW_Y_POS = -2;
    private final int EDGE_TEXT_SIZE = 12;
    private BufferedImage img_agent = null;

    private Color edgeColorMarked = new Color(0x130ED3);
    private Color backGroundColor = new Color(0xF5E7BB);
    private Color nodeColor = new Color(0x7C3751);
    private Color edgeColor = new Color(0x44A98E);
    private Color edgePositiveColor = new Color(0x1FD266);
    private Color edgeNegativeColor = new Color(0xDA560E);
    private Color pokemonColor = new Color(0xFFEE6E);
    private Color agentColor = new Color(0x7C7B37);

    private HashMap<Integer, Agent> agents;
    private ArrayList<Pokemon> pokemons;
    private Info game_info;

    private DirectedWeightedGraph graph;
    private double max_x=Double.MIN_VALUE, max_y=Double.MIN_VALUE;
    private double min_x=Double.MAX_VALUE, min_y=Double.MAX_VALUE;

    private FontMetrics metrics;
    private int width, height;
    private boolean should_update = true;

    BufferedImage img;

    private int last_mc;

    private boolean show_edges_weight = true;

    private java.util.List<EdgeData> marked_edges;
    private int agent_stroke_width = 3;
    private int agent_size = 10;
    private int node_stroke_width = 3;
    private Color nodeStrokeColor =  Color.BLACK;
    private Color agentStrokeColor = Color.BLACK;
    private Color pokemonStrokeColor = Color.BLACK;
    private int pokemon_stroke_width = 2;
    private int edge_stroke_width = 2;

    public void set_show_edges_weight(boolean f){this.show_edges_weight = f;}
    public boolean is_show_edges_weight(){return this.show_edges_weight;}

    public GameDraw(DirectedWeightedGraph graph){
        this.graph = graph;
        this.last_mc = graph.getMC();

        this.width = 0;
        this.height = 0;

        this.setLayout(null);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.set_scale();
        this.marked_edges = new LinkedList<>();

        // Load images
        try {
            File file = new File("res/pokeball.png");
            this.img_agent = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set_agents(HashMap<Integer, Agent> l){
        this.agents = l;
    }
    public void set_pokemons(ArrayList<Pokemon> l){
        this.pokemons = l;
    }
    public void set_info(Info i){
        this.game_info = i;
    }

    public void set_marked_edges(List<EdgeData> e){
        this.marked_edges = e;
        this.set_update();
        this.repaint();
    }
    public void clear_marked_edges(){
        this.marked_edges.clear();
        this.set_update();
        this.repaint();
    }

    public void update(DirectedWeightedGraph g){
        this.graph = g;
        this.last_mc = g.getMC();
        this.should_update = true;
        this.repaint();
    }

    public void set_scale(){
        max_y=max_x=Double.MIN_VALUE;
        min_y=min_x=Double.MAX_VALUE;

        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            NodeData n = it.next();
            if(n.getLocation().x() > max_x) max_x = n.getLocation().x();
            if(n.getLocation().x() < min_x) min_x = n.getLocation().x();
            if(n.getLocation().y() > max_y) max_y = n.getLocation().y();
            if(n.getLocation().y() < min_y) min_y = n.getLocation().y();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        metrics = g2d.getFontMetrics();

        if(this.img==null || this.width != this.getWidth() || this.height != this.getHeight()) {
            this.img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D drawer = img.createGraphics() ;
            drawer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            drawer.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            set_scale();
            this.width = this.getWidth();
            this.height = this.getHeight();
            this.last_mc = this.graph.getMC();

            paintBackground(drawer);
            drawEdges(drawer);
            drawNodes(drawer);

            should_update = true;
        }

        if(should_update) {

            g2d.drawImage(this.img,0,0,this.width, this.height, null); // Draw the graph cached img
            drawInfo(g2d);
            drawAgents(g2d);
            drawPokemons(g2d);

            this.should_update = false;
        }
    }

    private void drawNodes(Graphics2D g2d) {
        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            NodeData node = it.next();
            drawNode(g2d, node);
        }
    }

    private void drawAgents(Graphics2D g2d) {
        if(this.agents == null) return;
        for(Map.Entry<Integer, Agent> agent : this.agents.entrySet()) {
            drawAgent(g2d, agent.getValue());
        }
    }

    private void drawPokemons(Graphics2D g2d) {
        if(this.pokemons == null) return;
        ArrayList<Pokemon> poks = (ArrayList<Pokemon>) this.pokemons.clone();
        for(Pokemon pok : poks) {
            drawPokemon(g2d, pok);
        }
    }

    private int convertLocationX(double x){
        return (int) map(x, min_x, max_x, 20, this.getWidth()-50);
    }

    private int convertLocationY(double y){
        return (int) map(y, min_y, max_y, 20, this.getHeight()-50);
    }

    private double backConvertLocationX(double x){
        return map(x, 20, this.getWidth()-50, min_x, max_x);
    }

    private double backConvertLocationY(double y){
        return map(y, 20, this.getHeight()-50, min_y, max_y);
    }

    private GeoLocation convertLocation(GeoLocation l){
        return new BaseGeoLocation(convertLocationX(l.x()), convertLocationY(l.y()));
    }

    private double map(double v, double in_min, double in_max, double out_min, double out_max) {
        return (v - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);

        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g.setFont(font);
        g.drawString(text, x, y);
    }

    private void drawInfo(Graphics2D g){
        if(this.game_info == null) return;
        g.setColor(Color.BLACK);

        String text = "LEFT TIME: " + this.game_info.getTime()/1000; // Time in sec
        Font t_font = new Font(null, Font.BOLD, 20);
        int pos_x = 100;
        int pos_y = 20;

        g.setFont(t_font);
        g.drawString(text, pos_x, pos_y);

        text = "TOTAL GRADE: "+this.game_info.getGrade();
        pos_y += 20;
        g.drawString(text, pos_x, pos_y);

        text = "TOTAL MOVES: "+this.game_info.getMoves();
        pos_y += 20;
        g.drawString(text, pos_x, pos_y);

        if(!this.game_info.getIsRunning()) {
            g.setFont(new Font(null, Font.BOLD, 100));
            g.drawString("GAME OVER", this.getWidth() / 2 - 300, this.getHeight() / 2);
        }
    }

    private void drawNode(Graphics2D g2d, NodeData node){

        String text = ""+node.getKey();
        int size = get_node_size(text);
        int x_pos = convertLocationX(node.getLocation().x()) - size/2;
        int y_pos = convertLocationY(node.getLocation().y()) - size/2;

        g2d.setColor(this.nodeColor);
        g2d.fillOval(x_pos, y_pos, size, size);

        g2d.setColor(this.nodeStrokeColor);
        g2d.setStroke(new BasicStroke(this.node_stroke_width));
        g2d.drawOval(x_pos, y_pos, size, size);

        Shape s = new Ellipse2D.Float(x_pos, y_pos, size, size);
        this.drawCenteredString(g2d, text, s.getBounds(), new Font(null, Font.BOLD, 12));
    }

    private void drawAgent(Graphics2D g2d, Agent a){
        String text = ""+a.getId();
        int size = get_node_size(text);
        int x = convertLocationX(a.getLocation().x());
        int y = convertLocationY(a.getLocation().y());
        int x_pos = x - size/2;
        int y_pos = y - size/2;

        if(this.img_agent == null){
            g2d.setColor(this.agentColor);
            g2d.fillOval(x_pos, y_pos, size, size);

            g2d.setColor(this.agentStrokeColor);
            g2d.setStroke(new BasicStroke(this.agent_stroke_width));
            g2d.drawOval(x_pos, y_pos, size, size);
            Shape s = new Ellipse2D.Float(x_pos, y_pos, size, size);
            this.drawCenteredString(g2d, text, s.getBounds(), new Font(null, Font.BOLD, 12));
        }
        else {
            g2d.drawImage(this.img_agent, x_pos, y_pos, size, size, null);
            g2d.setFont(new Font(null, Font.BOLD, 20));
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, x-5, y);
        }

    }

    private void drawPokemon(Graphics2D g2d, Pokemon a){
        String text = ""+a.getValue();
        int size = get_node_size(text);
        int x_pos = convertLocationX(a.getLocation().x()) - size/2;
        int y_pos = convertLocationY(a.getLocation().y()) - size/2;

        g2d.setColor(this.pokemonColor);

        g2d.fillOval(x_pos, y_pos, size, size);

        if(a.getType() > 0)
            g2d.setColor(this.edgePositiveColor);
        else
            g2d.setColor(this.edgeNegativeColor);

        g2d.setStroke(new BasicStroke(this.pokemon_stroke_width));
        g2d.drawOval(x_pos, y_pos, size, size);

        Shape s = new Ellipse2D.Float(x_pos, y_pos, size, size);

        if (a.isAssigned())
            g2d.setColor(Color.BLACK);

        this.drawCenteredString(g2d, text, s.getBounds(), new Font(null, Font.BOLD, 12));
    }

    private void drawEdge(Graphics2D g, EdgeData edge){

        NodeData n1 = this.graph.getNode(edge.getSrc());
        NodeData n2 = this.graph.getNode(edge.getDest());
        if (n1 == null || n2 == null) return;

        Graphics2D g2d = (Graphics2D) g.create();
        GeoLocation start = convertLocation(n1.getLocation()), end = convertLocation(n2.getLocation());

        double dx = end.x() - start.x();
        double dy = end.y() - start.y();
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        int arrowDis = 40/2 + 2;

        g2d.translate(start.x(), start.y());
        g2d.rotate(angle);

        int l = len - arrowDis;
        if(this.marked_edges.contains(edge)){
            g2d.setColor(this.edgeColorMarked);
        }
        else{
            if(edge.getSrc() < edge.getDest())
                g2d.setColor(this.edgePositiveColor);
            else
                g2d.setColor(this.edgeNegativeColor);
        }

        Shape arc = new Arc2D.Double(0,-10, l-5, 20, 0, 180, Arc2D.OPEN);
        Polygon poly = new Polygon(new int[] {l, l-EDGE_ARR_SIZE, l-EDGE_ARR_SIZE, l},
                new int[] {EDGE_ARROW_Y_POS, -EDGE_ARR_SIZE+EDGE_ARROW_Y_POS, EDGE_ARR_SIZE+EDGE_ARROW_Y_POS, EDGE_ARROW_Y_POS}, 4);

        g2d.setStroke(new BasicStroke(this.edge_stroke_width));
        g2d.draw(arc);
        g2d.fillPolygon(poly);

        // text
        if(this.show_edges_weight){
            String t = String.format("%.5f", edge.getWeight());

            int txt_len = g2d.getFontMetrics().stringWidth(t);

            Font font = new Font(null, Font.PLAIN, 12);
            AffineTransform affineTransform = new AffineTransform();

            int y_pos = -15;
            int x_pos = (len/2)-(txt_len/2);

            if(Math.abs(angle) > 2){
                affineTransform.rotate(Math.toRadians(180), 0, 0);
                y_pos = -25;
                x_pos = x_pos + 40;
            }
            Font rotatedFont = font.deriveFont(affineTransform);
            g2d.setFont(rotatedFont);

            g2d.setColor(Color.BLACK);
            g2d.drawString(t, x_pos, y_pos);
        }
    }

    public void set_update(){
        this.should_update = true;
    }

    private int get_node_size(String n) {
        int width = metrics.stringWidth(n);
        if (width < MINIMUM_NODE_SIZE - 5) {
            return MINIMUM_NODE_SIZE;
        }
        return width + 2 * NODE_PADDING;
    }

    private void drawEdges(Graphics2D g2d) {
        for (Iterator<EdgeData> it = this.graph.edgeIter(); it.hasNext(); ) {
            EdgeData edge = it.next();
            drawEdge(g2d, edge);
        }
    }

    private void paintBackground(Graphics2D g2d) {
        g2d.setColor(this.backGroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}

