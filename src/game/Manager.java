package game;

import api.*;
import gui.GameDraw;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Manager {

    private JFrame frame;
    private GameDraw g_draw;

    private Client client;
    private BaseDirectedWeightedGraph graph;
    private BaseDirectedWeightedGraphAlgo algo;

    private String server_ip;
    private int server_port;

    private HashMap<Integer, Agent> agents;
    private ArrayList<Pokemon> pokemons;
    private int time_last_loop;
    private Info info;

    int last_time_moved;
    final int MOVE_DELTA = 220  ; // milliseconds

    Queue<Integer> move_times = new PriorityQueue<>(Collections.reverseOrder());

    public Manager(){
        this("127.0.0.1", 6666);
    }
    public Manager(String ip, int port){
        this.server_ip = ip;
        this.server_port = port;

        this.pokemons = new ArrayList<>();
    }

    public void show(){

        this.frame = new JFrame();
        frame.getContentPane();
        this.frame.setTitle("Pokemon - Level: " + this.info.getLevel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        if(this.algo.getGraph() == null || this.algo.getGraph().nodeSize()==0){
            JOptionPane.showMessageDialog(this.frame, "Invalid Graph file!","Error", JOptionPane.WARNING_MESSAGE);
            System.exit(1);
        }

        this.g_draw = new GameDraw(this.graph);
        this.g_draw.setVisible(true);
        this.g_draw.set_agents(this.agents);
        this.g_draw.set_pokemons(this.pokemons);
        this.g_draw.set_info(this.info);


        frame.setSize(1000,600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.add(this.g_draw);
        this.g_draw.set_update();
        this.g_draw.repaint();

        this.set_menu();
    }

    private void stop_game(){
        this.client.stop();
        this.info.setIs_running(false);
        this.g_draw.set_update();
        this.g_draw.repaint();
    }

    private void set_menu(){
        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Stop");
        menu.addItemListener((event) -> this.stop_game());
        menuBar.add(menu);

        this.frame.setJMenuBar(menuBar);
    }

    public void init(){
        this.client = new Client();
        try {
            client.startConnection(this.server_ip, this.server_port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this.frame, "Server is not running!","Pokemon: Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        String graphStr = client.getGraph();
        this.algo = new BaseDirectedWeightedGraphAlgo();
        this.algo.load_from_buf(graphStr);
        this.graph = this.algo.getGraph();

        String info = this.client.getInfo();
        this.info = new Info(info, this.client.timeToEnd(), this.is_running());
        System.out.println(info);
        this.last_time_moved = this.info.getTime();

        String pokemonsStr = this.client.getPokemons();
        Pokemon.update_pokemons(pokemonsStr, this.pokemons);
        this.update_pok_edges();

        create_agents();
    }

    public void create_agents(){
        int agents_num = this.info.getAgents();
        int pokemon_num = this.info.getPokemons();

        if(agents_num == pokemon_num){ // Set an agent for each pokemon.
            for(Pokemon p: this.pokemons){
                this.addAgent(p.edge.getSrc());
            }
        }
        else if(agents_num < pokemon_num){ // Set the agents next to the highest value pokemons
            int c=0;
            this.pokemons.sort((Pokemon o1, Pokemon o2) -> (int) (o2.getValue() - o1.getValue()));
            for(Pokemon p: this.pokemons){
                this.addAgent(p.edge.getSrc());
                if(++c == agents_num) break;
            }
        }
        else{ // agents_num > pokemon_num -- Set the agents next to pokemons and set the rest of the agents randomly.
            int c=0;
            for(Pokemon p: this.pokemons){
                this.addAgent(p.edge.getSrc());
                c++;
            }
            for(int i=c; i<agents_num; i++){
                this.addAgent(new Random().nextInt(this.graph.nodeSize()));
            }
        }

        String agentsStr = this.client.getAgents();
        this.agents = Agent.get_agents(agentsStr);
    }

    public void update(){

        boolean should_update_gui = false;

        String info = this.client.getInfo();
        String agentsStr = this.client.getAgents();
        String pokemonsStr = this.client.getPokemons();

        if(info == null || agentsStr == null || pokemonsStr == null){
            this.info.setIs_running(false);
            return;
        }

        this.info.update(info, client.timeToEnd(), this.is_running());

        if (Agent.update_agents(this.agents, agentsStr)) {
            should_update_gui = true;
        }
        if (Pokemon.update_pokemons(pokemonsStr, this.pokemons)) {
            should_update_gui = true;
            this.update_pok_edges();
        }

        if(g_draw != null && should_update_gui){
            this.g_draw.set_update();
            this.g_draw.repaint();
        }
    }

    public void update_pok_edges(){
        for(Pokemon pok: this.pokemons){
            if(pok.edge == null)
                pok.setEdge(getPokemonEdge(pok));
        }
    }

    public BaseEdgeData getPokemonEdge(Pokemon pok){
        Iterator<EdgeData> it = this.graph.edgeIter();
        while(it.hasNext()){
            BaseEdgeData e = (BaseEdgeData) it.next();
            // Check that the pokemon type and the edge in the same direction
            if(pok.getType() > 0 && e.getSrc() < e.getDest() ||
               pok.getType() < 0 && e.getSrc() > e.getDest()) {
                if(e.edge_contains(pok.getLocation())){
                    return e;
                }
            }
        }
        return null;
    }

    public boolean is_running(){
        String isRunningStr = this.client.isRunning();
        if(isRunningStr == null) return false;
        return isRunningStr.equals("true");
    }

    public void start_game(){
        this.client.start();
        this.last_time_moved = Integer.parseInt(client.timeToEnd());
    }
    public void move(){
        this.client.move();
        this.last_time_moved = Integer.parseInt(this.client.timeToEnd());
    }
    public void choose_next(Agent agent, int node_id){
        int time_to_end = Integer.parseInt(this.client.timeToEnd());
        int agent_id = agent.getId();
        this.client.chooseNextEdge("{\"agent_id\":" + agent_id + ", \"next_node_id\": " + node_id + "}");
        agent.setDest(node_id);
        System.out.println("agent: "+agent_id+" to node: "+node_id);

        EdgeData e = this.graph.getEdge(agent.getSrc(), node_id);

        int time = (int) Math.round((e.getWeight() / agent.getSpeed())*1000);

        this.move_times.add(time_to_end - time-10);
        this.move_times.add(time_to_end - time+10);
    }

    public void collect_pokemons(){
        for(Agent a: this.agents.values()){
            for(Pokemon pok: this.pokemons) {
                if(pok.set_move_time < this.info.getTime()) continue;
                if (a.getSrc() == pok.getEdge().getSrc() && a.getDest() == pok.getEdge().getDest()) { // the agent is now on the edge of the pokemon
                    double edge_len = pok.edge.getSrcObj().getLocation().distance(pok.edge.getDestObj().getLocation());
                    double w_on_edge = a.getLocation().distance(pok.pos) * (pok.edge.getWeight() / edge_len); // weight from src to pok
                    int time = (int) Math.round((w_on_edge / a.getSpeed()) * 1000);

                    this.move_times.add(this.info.getTime() - time);
                    pok.set_move_time = this.info.getTime() - time;
                }
            }
        }
    }


    public void main_loop(){
        this.start_game();

        while(this.is_running()){

            this.update();

            this.collect_pokemons();

            if(this.agents.size() > 1)
                this.choose_next_algo__multi_agents();
            else
                this.choose_next_algo__single_agent();

            for(Agent a: this.agents.values()){ // For each agent, set the next destination
                if(a.has_next_node() && a.getDest() == -1) {
                    choose_next(a, a.get_next_node());
                }
            }

            if(!this.move_times.isEmpty() && this.move_times.peek() >= this.info.getTime()){
                if (this.last_time_moved - this.move_times.peek() >= 20) { // very close to the last move
                    this.move();
                }
                this.move_times.poll();
            }

            else if(this.last_time_moved - this.info.getTime() > this.MOVE_DELTA) {
                System.out.println("Auto move");
                this.move();
            }


//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        this.g_draw.set_update();
        this.g_draw.repaint();
    }

    public void choose_next_algo__multi_agents(){

        for(Pokemon pok: this.pokemons){
            if(pok.isAssigned()) continue;
            Agent closest_a = null;
            double min_dist = Double.MAX_VALUE;
            List<NodeData> min_path = null;
            int dest = pok.getEdge().getSrc();

            for(Agent a: this.agents.values()){
                // Check if the agent go through the pokemon - Assign it.
                if(a.has_next_node() && a.on_the_way_to(dest, pok.getEdge().getDest())){
                    pok.setAssigned();
                    break;
                }

                int agent_pos = a.getSrc();

                if(a.has_next_node()){
                    agent_pos = a.get_last_node_in_q();
                    if(a.get_next_nodes_num() > 5)
                        continue;
                }
                else if  (a.getDest() != -1){
                    agent_pos = a.getDest();
                }

                List<NodeData> path = this.algo.shortestPath(agent_pos, dest);
                if(agent_pos == dest){ // The agent is already on the wanted node
                    a.add_next_node(pok.getEdge().getDest());
                    pok.setAssigned();
                }
                else { // Calculate the distance and check if its lower the others
                    double dist = (this.algo.pathWeight(path) + pok.getEdge().getWeight()) / a.getSpeed();
                    if(dist < min_dist){
                        min_dist = dist;
                        closest_a = a;
                        min_path = path;
                    }
                }
            }
            if(min_path != null && !pok.isAssigned()){
                min_path.remove(0); // Remove the first node (the agent is already there)
                closest_a.set_next_nodes(min_path); // Add all path to the start of the edge
                closest_a.add_next_node(pok.getEdge().getDest()); // Add the end of the edge
                pok.setAssigned();
            }
        }
    }

    public void choose_next_algo__single_agent(){

        Agent a = this.agents.get(0);

        double min_dist = Double.MAX_VALUE;
        List<NodeData> min_path = null;
        Pokemon closest_p = null;

        for(Pokemon pok: this.pokemons) {
            if (pok.isAssigned()) continue;

            int dest = pok.getEdge().getSrc();

            // Check if the agent go through the pokemon - Assign it.
            if (a.has_next_node() && a.on_the_way_to(dest, pok.getEdge().getDest())) {
                pok.setAssigned();
                break;
            }
            // Check if agent is busy
            if (a.has_next_node() || a.getDest() != -1) break;

            if (a.getSrc() == dest) { // The agent is already on the wanted node
                a.add_next_node(pok.getEdge().getDest());
                pok.setAssigned();
                break;
            }
            else { // Calculate the distance and check if its lower the others
                List<NodeData> path = this.algo.shortestPath(a.getSrc(), dest);
                double dist = (this.algo.pathWeight(path) + pok.getEdge().getWeight()) / a.getSpeed();
                if (dist < min_dist) {
                    min_dist = dist;
                    closest_p = pok;
                    min_path = path;
                }
            }
        }

        if(min_path != null && !a.has_next_node()){
            min_path.remove(0); // Remove the first node (the agent is already there)
            a.set_next_nodes(min_path); // Add all path to the start of the edge
            a.add_next_node(closest_p.getEdge().getDest()); // Add the end of the edge
            closest_p.setAssigned();
        }
    }

    public void choose_next_algo__random(){
        for(int i=0; i<this.agents.size();i++){
            Agent agent = this.agents.get(i);
            if(agent.getDest() == -1) {
                ArrayList<Integer> nei = this.graph.get_node_neighbors(agent.getSrc());
                int rand_index = new Random().nextInt(nei.size());
                int next_node = nei.get(rand_index);
                choose_next(agent, next_node);
//                agent.setDest(next_node);
                System.out.println("agent: "+i+" to node: "+next_node);
            }
        }
    }

    private void addAgent(int id){
        this.client.addAgent("{\"id\":" + id + "}");
    }

    public static void main(String[] args){

        Manager m = new Manager();
        m.init();
        m.update();

        m.show();
        m.main_loop();
        System.out.println("Game Over");
    }

}
