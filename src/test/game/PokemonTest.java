package game;

import api.BaseGeoLocation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    Pokemon p1 = new Pokemon(1.0, 1, new BaseGeoLocation("35.197656770719604,32.10191878639921,0.0"));

    String j = "{\"Pokemons\":[{\"Pokemon\":{\"value\":5.0,\"type\":-1,\"pos\":\"35.197656770719604,32.10191878639921,0.0\"}},{\"Pokemon\":{\"value\":8.0,\"type\":-1,\"pos\":\"35.199963710098416,32.105723673136964,0.0\"}}]}";
    @Test
    void isAssigned() {
        Pokemon p = new Pokemon(1.0, 1, new BaseGeoLocation("35.197656770719604,32.10191878639921,0.0"));
        assertFalse(p.isAssigned());
        p.setAssigned();
        assertTrue(p.isAssigned());
    }

    @Test
    void setAssigned() {
        Pokemon p = new Pokemon(1.0, 1, new BaseGeoLocation("35.197656770719604,32.10191878639921,0.0"));
        assertFalse(p.isAssigned());
        p.setAssigned();
        assertTrue(p.isAssigned());
    }

    @Test
    void getType() {
        assertEquals(1, p1.getType());
    }

    @Test
    void getValue() {
        assertEquals(1.0, p1.getValue());
    }

    @Test
    void getLocation() {
        assertEquals(new BaseGeoLocation("35.197656770719604,32.10191878639921,0.0"), p1.getLocation());
    }

    @Test
    void update_pokemons() {
        ArrayList<Pokemon> l = new ArrayList<>();
        Pokemon.update_pokemons(j, l);
        assertEquals(2, l.size());
        Pokemon p = l.get(0);
        assertEquals(5, p.getValue());
        assertEquals(-1, p.getType());
        assertEquals(new BaseGeoLocation("35.197656770719604,32.10191878639921,0.0"), p.getLocation());
    }
}