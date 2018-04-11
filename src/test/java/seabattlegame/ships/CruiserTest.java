package seabattlegame.ships;

import org.junit.Test;
import seabattlegame.game.ships.Cruiser;
import seabattlegame.game.ships.Ship;
import seabattlegui.ShipType;

import static org.junit.Assert.assertEquals;

public class CruiserTest {

    @Test
    public void testConstructor() {
        Ship cruiser = new Cruiser();
        assertEquals(3, cruiser.getLength());
        assertEquals(0, cruiser.getHits());
        assertEquals(false, cruiser.isSunk());
        assertEquals(ShipType.CRUISER, cruiser.getType());
    }
}
