package ships;

import org.junit.Test;
import seabattlegame.game.ships.Ship;

import static org.junit.Assert.assertEquals;

public class TestBattleship {

    @Test
    public void testConstructor() {
        Ship battleship = new seabattlegame.game.ships.Battleship();
        assertEquals(4, battleship.getLength());
        assertEquals(0, battleship.getHits());
        assertEquals(false, battleship.isSunk());
    }
}
