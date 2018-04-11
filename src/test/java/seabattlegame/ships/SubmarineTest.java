package seabattlegame.ships;

import org.junit.Test;
import seabattlegame.game.ships.Ship;
import seabattlegame.game.ships.Submarine;
import seabattlegui.ShipType;

import static org.junit.Assert.assertEquals;

public class SubmarineTest {

    @Test
    public void testConstructor() {
        Ship submarine = new Submarine();
        assertEquals(3, submarine.getLength());
        assertEquals(0, submarine.getHits());
        assertEquals(false, submarine.isSunk());
        assertEquals(ShipType.SUBMARINE, submarine.getType());
    }
}
