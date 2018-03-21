import org.junit.Before;
import org.junit.Test;
import seabattlegame.game.Cell;
import seabattlegame.game.SquareState;

import static org.junit.Assert.assertEquals;

public class CellTest {

    private Cell cell;

    @Before
    public void testInitialize() {
        cell = new Cell();
    }

    @Test
    public void testConstructor() {
        assertEquals(SquareState.WATER, cell.getState());
    }

    @Test
    public void testHit() {
        assertEquals(SquareState.SHOTMISSED, cell.hit());
    }
}
