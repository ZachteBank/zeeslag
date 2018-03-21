package ships;

import org.junit.Test;
import seabattlegame.game.ships.Cruiser;
import seabattlegame.game.ships.Ship;

import static org.junit.Assert.assertEquals;

public class TestCruiser {

    @Test
    public void testConstructor() {
        Ship cruiser = new Cruiser();
        assertEquals(5, cruiser.getLength());
        assertEquals(0, cruiser.getHits());
        assertEquals(false, cruiser.isSunk());
    }
}
