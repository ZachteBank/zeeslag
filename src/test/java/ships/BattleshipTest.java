package ships;

import org.junit.Test;
import seabattlegame.game.ships.Battleship;
import seabattlegame.game.ships.Ship;

import static org.junit.Assert.assertEquals;

public class BattleshipTest {

    @Test
    public void testConstructor() {
        Ship battleship = new Battleship();
        assertEquals(4, battleship.getLength());
        assertEquals(0, battleship.getHits());
        assertEquals(false, battleship.isSunk());
    }
}
