package game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Info {
    int time;
    int level;
    int pokemons;
    int agents;
    String graph;
    int moves;
    int grade;
    boolean is_running;

    public Info(String st, String time, boolean is_running) {
        this.update(st, time, is_running);
    }

    public int getTime() {
        return time;
    }

    public int getLevel() {
        return level;
    }

    public boolean getIsRunning(){
        return this.is_running;
    }

    public int getPokemons() {
        return pokemons;
    }

    public int getAgents() {
        return agents;
    }

    public int getMoves() {
        return moves;
    }

    public int getGrade() {
        return grade;
    }

    public void setIs_running(boolean running){
        this.is_running = running;
    }

    public void update(String json_str, String time, boolean is_running){
        JsonObject j = new Gson().fromJson(json_str, JsonObject.class).get("GameServer").getAsJsonObject();

        this.pokemons = j.get("pokemons").getAsInt();
        this.moves = j.get("moves").getAsInt();
        this.grade = j.get("grade").getAsInt();
        this.level = j.get("game_level").getAsInt();
        this.graph = j.get("graph").getAsString();
        this.agents = j.get("agents").getAsInt();
        this.time = Integer.parseInt(time);
        this.is_running = is_running;
    }
}
