import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seabattlegame.game.Grid;
import seabattlegame.game.SquareState;
import seabattlegame.game.ships.AircraftCarrier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GridTest {

    private Grid grid;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

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
    public void testPlaceShipHorizontallyInner() {
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, 0, true);

        assertEquals(1, grid.getShips().size());
        for (int i = 0; i < carrier.getLength(); i++) {
            assertEquals(SquareState.SHIP, grid.getCells()[0][i].getState());
        }
        assertEquals(SquareState.WATER, grid.getCells()[0][carrier.getLength() + 1].getState());
    }

    @Test
    public void testPlaceShipHorizontallyOuter() {
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 5, 9,
                true);

        assertEquals(1, grid.getShips().size());
        for (int i = 5; i < grid.getCells().length; i++) {
            assertEquals(SquareState.SHIP, grid.getCells()[9][i].getState());
        }
    }

    @Test
    public void testPlaceShipVerticallyInner() {
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, 0, false);

        assertEquals(1, grid.getShips().size());
        for (int i = 0; i < carrier.getLength(); i++) {
            assertEquals(SquareState.SHIP, grid.getCells()[i][0].getState());
        }
        assertEquals(SquareState.WATER, grid.getCells()[carrier.getLength() + 1][0].getState());
    }

    @Test
    public void testPlaceShipVerticallyOuter() {
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, grid.getCells()[0].length - 1, grid.getCells().length - carrier.getLength(),
                false);

        assertEquals(1, grid.getShips().size());
        for (int i = 5; i < grid.getCells()[0].length; i++) {
            assertEquals(SquareState.SHIP, grid.getCells()[i][9].getState());
        }
    }

    @Test
    public void testPlaceShipShipIsNull() {
        exception.expect(IllegalArgumentException.class);
        AircraftCarrier carrier = null;
        grid.placeShip(carrier, 0, 0, true);
    }

    @Test
    public void testPlaceSameShipAgain() {
        exception.expect(IllegalArgumentException.class);
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, 0, true);
        grid.placeShip(carrier, 4, 4, true);
    }

    @Test
    public void testPlaceShipPlaceShipOfSameType() {
        exception.expect(IllegalArgumentException.class);
        AircraftCarrier carrier1 = new AircraftCarrier();
        grid.placeShip(carrier1, 0, 0, true);
        AircraftCarrier carrier2 = new AircraftCarrier();
        grid.placeShip(carrier2, 4, 4, true);
    }

    @Test
    public void testPlaceShipXCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, -1, 0, true);
    }

    @Test
    public void testPlaceShipYCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, -1, true);
    }

    @Test
    public void testPlaceShipXCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 11, 0, true);
    }

    @Test
    public void testPlaceShipYCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, 11, true);
    }

    @Test
    public void testRemoveAllShips() {
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, 0, true);

        assertEquals(SquareState.SHIP, grid.getCells()[0][0].getState());
        assertEquals(1, grid.getShips().size());

        assertTrue(grid.removeAllShips());

        assertEquals(SquareState.WATER, grid.getCells()[0][0].getState());
        assertEquals(0, grid.getShips().size());
    }
}
