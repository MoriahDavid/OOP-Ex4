package game;

import api.BaseGeoLocation;
import api.NodeData;
import com.google.gson.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Agent{
    private int id;
    private double value;
    private int src;
    private int dest;
    private double speed;
    private BaseGeoLocation pos;
    private LinkedList<Integer> next_nodes;
    private int last_dest;


    public int getLast_dest() {
        return last_dest;
    }

    public void setLast_dest(int last_dest) {
        this.last_dest = last_dest;
    }

    public Agent(int id, double value, int src, int dest, Double speed, BaseGeoLocation pos){
        this.id = id;
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.speed = speed;
        this.pos = pos;
        this.next_nodes = new LinkedList<>();
    }

    public int getId() {
        return id;
    }
    public BaseGeoLocation getLocation() {
        return pos;
    }
    public void setPos(BaseGeoLocation l){
        this.pos = l;
    }
    public int getSrc() {
        return src;
    }
    public void setSrc(int src){
        this.src = src;
    }
    public int getDest() {
        return dest;
    }
    public void setDest(int dest){
        this.dest = dest;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean has_next_node(){
        return !this.next_nodes.isEmpty();
    }

    public int get_next_node(){
//        System.out.println(this.id + ": " + this.next_nodes);
        return this.next_nodes.pollFirst();
    }

    public void set_next_nodes(List<NodeData> l){
        for(NodeData n: l){
            this.next_nodes.add(n.getKey());
        }
    }
    public void add_next_node(int node_id){
        this.next_nodes.add(node_id);
    }

    public boolean on_the_way_to(int src, int dst){
        int i_s = this.next_nodes.indexOf(src);
        int i_d = this.next_nodes.indexOf(dst);
        if (i_s > -1 && i_d > -1 && i_s < i_d){
            return true;
        }
        return false;
    }

    public static HashMap<Integer, Agent> get_agents(String json_string){
        HashMap<Integer, Agent> l = new HashMap<>();
        JsonArray s = new Gson().fromJson(json_string, JsonObject.class).get("Agents").getAsJsonArray();
        for (JsonElement a : s) {
            JsonObject j = a.getAsJsonObject().get("Agent").getAsJsonObject();
            int id = j.get("id").getAsInt();
            double value = j.get("value").getAsDouble();
            int src = j.get("src").getAsInt();
            int dest = j.get("dest").getAsInt();
            double speed = j.get("speed").getAsDouble();
            String pos = j.get("pos").getAsString();

            Agent agent = new Agent (id, value, src, dest, speed, new BaseGeoLocation(pos));
            l.put(id, agent);
        }

        return l;
    }
    public static boolean update_agents(HashMap<Integer, Agent> l, String json_string){
        boolean pos_changed = false;
        JsonArray s = new Gson().fromJson(json_string, JsonObject.class).get("Agents").getAsJsonArray();
        for (JsonElement a : s) {
            JsonObject j = a.getAsJsonObject().get("Agent").getAsJsonObject();
            int id = j.get("id").getAsInt();
            Agent agent = l.get(id);
            agent.setValue(j.get("value").getAsDouble());
            agent.setSrc(j.get("src").getAsInt());
            agent.setDest(j.get("dest").getAsInt());
            agent.setSpeed(j.get("speed").getAsDouble());
            BaseGeoLocation new_pos = new BaseGeoLocation(j.get("pos").getAsString());
            if(!new_pos.equals(agent.getLocation())){
                agent.setPos(new_pos);
                pos_changed = true;
            }
        }
        return pos_changed;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", value=" + value +
                ", src=" + src +
                ", dest=" + dest +
                ", speed=" + speed +
                ", pos=" + pos +
                '}';
    }
}
