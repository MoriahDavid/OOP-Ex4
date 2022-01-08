package game;

import api.BaseEdgeData;
import api.BaseGeoLocation;
import api.EdgeData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Pokemon {

    int type;
    double value;
    BaseGeoLocation pos;

    BaseEdgeData edge;
    double w_on_edge;

    boolean assigned = false;
    public int set_move_time = Integer.MAX_VALUE;

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned() {
        this.assigned = true;
    }

    public void setEdge(BaseEdgeData edge) {
        this.edge = edge;

        double edge_len = edge.getSrcObj().getLocation().distance(edge.getDestObj().getLocation());
        double dist_to_pok = edge.getSrcObj().getLocation().distance(this.pos);

        double w_in_dist = edge.getWeight() / edge_len;

        this.w_on_edge = dist_to_pok * w_in_dist; // weight from src to pok
    }

    public BaseEdgeData getEdge() {
        return edge;
    }

    public Pokemon(double value, int type, BaseGeoLocation pos) {
        this.value = value;
        this.type = type;
        this.pos = pos;
    }

    public int getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public BaseGeoLocation getLocation() {
        return pos;
    }

    public static boolean update_pokemons(String json_string, ArrayList<Pokemon> l){
        JsonArray s = new Gson().fromJson(json_string, JsonObject.class).get("Pokemons").getAsJsonArray();

        boolean has_changed = false;

        Iterator<Pokemon> it = l.iterator();
        while(it.hasNext()) { // Remove the unrelated pokemon
            Pokemon p = it.next();
            boolean exists = false;
            for (JsonElement a : s){
                JsonObject j = a.getAsJsonObject().get("Pokemon").getAsJsonObject();
                String pos = j.get("pos").getAsString();
                BaseGeoLocation loc = new BaseGeoLocation(pos);
                if(p.pos.equals(loc)) exists = true;
            }
            if(!exists){
                it.remove();
                has_changed = true;
            }
        }

        for (JsonElement a : s) { // Add the new Pokemons
            JsonObject j = a.getAsJsonObject().get("Pokemon").getAsJsonObject();
            String pos = j.get("pos").getAsString();
            BaseGeoLocation loc = new BaseGeoLocation(pos);
            boolean exists = false;
            for(Pokemon p: l) if(p.pos.equals(loc)) exists = true;
            if (exists) continue;

            double value = j.get("value").getAsDouble();
            int type = j.get("type").getAsInt();

            Pokemon pok = new Pokemon(value, type, loc);
            l.add(pok);
            has_changed = true;
        }

        return has_changed;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "type=" + type +
                ", is_handle=" + assigned +
                ", value=" + value +
                ", pos=" + pos +
                ", edge=" + edge +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(pos, pokemon.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }
}
