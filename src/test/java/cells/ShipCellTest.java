package cells;

import org.junit.Before;
import org.junit.Test;
import seabattlegame.game.cells.Cell;
import seabattlegame.game.cells.ShipCell;
import seabattlegame.game.SquareState;
import seabattlegame.game.ships.*;

import static org.junit.Assert.assertEquals;

public class ShipCellTest {

    private ShipCell shipCell;

    @Before
    public void testInitialize() {
        Ship ship = new AircraftCarrier();
        shipCell = new ShipCell(ship);
    }

    @Test
    public void testConstructor() {
        assertEquals(SquareState.SHIP, shipCell.getState());
    }

    @Test
    public void testHitHit() {
        SquareState state;
        state = shipCell.hit();
        assertEquals(SquareState.SHOTHIT, state);
    }

    @Test
    public void testHitSunk() {
        SquareState state;
        for (int i = 0; i < 4; i++) {
            shipCell.getShip().hit();
        }
        state = shipCell.hit();
        assertEquals(SquareState.SHIPSUNK, state);
    }
}
