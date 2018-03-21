package cells;

import org.junit.Before;
import org.junit.Test;
import seabattlegame.game.cells.Cell;
import seabattlegame.game.cells.ShipCell;
import seabattlegame.game.SquareState;
import seabattlegame.game.ships.*;

import static org.junit.Assert.assertEquals;

public class ShipCellTest {

    private Cell cell;

    @Before
    public void testInitialize() {
        Ship ship = new AircraftCarrier();
        cell = new ShipCell(ship);
    }

    @Test
    public void testConstructor() {
        assertEquals(SquareState.SHIP, cell.getState());
    }
}
