package ships;

import org.junit.Test;
import seabattlegame.game.ships.Minesweeper;
import seabattlegame.game.ships.Ship;

import static org.junit.Assert.assertEquals;

public class MinesweeperTest {

    @Test
    public void testConstructor() {
        Ship minesweeper = new Minesweeper();
        assertEquals(2, minesweeper.getLength());
        assertEquals(0, minesweeper.getHits());
        assertEquals(false, minesweeper.isSunk());
    }
}
