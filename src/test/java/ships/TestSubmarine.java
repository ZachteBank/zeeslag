package ships;

import org.junit.Test;
import seabattlegame.game.ships.AircraftCarrier;
import seabattlegame.game.ships.Ship;
import seabattlegame.game.ships.Submarine;

import static org.junit.Assert.assertEquals;

public class TestSubmarine {

    @Test
    public void testConstructor() {
        Ship carrier = new Submarine();
        assertEquals(3, carrier.getLength());
        assertEquals(0, carrier.getHits());
        assertEquals(false, carrier.isSunk());
    }
}
