package game;

import api.BaseGeoLocation;
import api.BaseNodeData;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AgentTest {
    Agent a1 = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));

    @Test
    void get_last_node_in_q() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        a.add_next_node(4);
        a.add_next_node(5);
        assertEquals(5, a.get_last_node_in_q());
    }

    @Test
    void getId() {
        assertEquals(0, a1.getId());
    }

    @Test
    void getLocation() {
        assertEquals(new BaseGeoLocation("1.1,2.3,0.0"), a1.getLocation());
    }

    @Test
    void setPos() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        assertEquals(new BaseGeoLocation("1.1,2.3,0.0"), a.getLocation());
        a.setPos(new BaseGeoLocation("5.1,5.3,5.0"));
        assertEquals(new BaseGeoLocation("5.1,5.3,5.0"), a.getLocation());
    }

    @Test
    void getSrc() {
        assertEquals(0, a1.getSrc());
    }

    @Test
    void setSrc() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        assertEquals(0, a.getSrc());
        a.setSrc(3);
        assertEquals(3, a.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(-1, a1.getDest());
    }

    @Test
    void setDest() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        assertEquals(-1, a.getDest());
        a.setDest(8);
        assertEquals(8, a.getDest());
    }

    @Test
    void getValue() {
        assertEquals(1, a1.getValue());
    }

    @Test
    void setValue() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        assertEquals(1, a.getValue());
        a.setValue(6);
        assertEquals(6, a.getValue());
    }

    @Test
    void getSpeed() {
        assertEquals(1.0, a1.getSpeed());
    }

    @Test
    void setSpeed() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        assertEquals(1.0, a.getSpeed());
        a.setSpeed(2.0);
        assertEquals(2.0, a.getSpeed());
    }

    @Test
    void has_next_node() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        assertFalse(a.has_next_node());
        a.add_next_node(4);
        assertTrue(a.has_next_node());
    }

    @Test
    void get_next_node() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        a.add_next_node(4);
        a.add_next_node(5);
        assertEquals(4, a.get_next_node());
    }

    @Test
    void get_next_nodes_num() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        a.add_next_node(4);
        a.add_next_node(5);
        assertEquals(2, a.get_next_nodes_num());
    }

    @Test
    void set_next_nodes() {
        ArrayList<NodeData>  l = new ArrayList<>();
        l.add(new BaseNodeData(0,0,"",0,null));
        l.add(new BaseNodeData(1,0,"",0,null));
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        a.set_next_nodes(l);
        assertEquals(2, a.get_next_nodes_num());
    }

    @Test
    void add_next_node() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        a.add_next_node(4);
        assertEquals(1, a.get_next_nodes_num());
    }

    @Test
    void on_the_way_to() {
        Agent a = new Agent(0, 1,0,-1, 1.0, new BaseGeoLocation("1.1,2.3,0.0"));
        a.add_next_node(4);
        a.add_next_node(5);
        a.add_next_node(6);
        a.add_next_node(7);

        assertTrue(a.on_the_way_to(5,6));
        assertFalse(a.on_the_way_to(6,5));
    }

    @Test
    void get_agents() {
        HashMap<Integer, Agent> l = new HashMap<>();
        l = Agent.get_agents("{\"Agents\":[{\"Agent\":{\"id\":0,\"value\":0.0,\"src\":3,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.197528356739305,32.1053088,0.0\"}}]}");
        assertEquals(1, l.size());
        assertEquals(0, l.get(0).getId());
        assertEquals(3, l.get(0).getSrc());
        assertEquals(-1, l.get(0).getDest());
        assertEquals(1.0, l.get(0).getSpeed());
        assertEquals(new BaseGeoLocation("35.197528356739305,32.1053088,0.0"), l.get(0).getLocation());
    }
}