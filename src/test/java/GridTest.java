import org.junit.Before;
import org.junit.Test;
import seabattlegame.game.Grid;
import seabattlegame.game.SquareState;
import seabattlegame.game.ships.AircraftCarrier;

import static org.junit.Assert.assertEquals;

public class GridTest {

    private Grid grid;

    @Before
    public void testInitialize() {
        grid = new Grid(10);
    }

    @Test
    public void testConstructor() {

        assertEquals(10, grid.getCells().length);
        for (int i = 0; i < grid.getCells().length; i++) {
            assertEquals(10, grid.getCells()[i].length);
        }
        assertEquals(0, grid.getShips().size());
    }

    @Test
    public void testPlaceShip() {
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, 0, true);

        assertEquals(1, grid.getShips().size());
        for (int i = 0; i < carrier.getLength(); i++) {
            assertEquals(SquareState.SHIP, grid.getCells()[0][i].getState());
        }
        assertEquals(SquareState.WATER, grid.getCells()[0][carrier.getLength() + 1].getState());
    }

   
}
