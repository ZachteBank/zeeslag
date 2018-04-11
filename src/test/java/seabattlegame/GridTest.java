package seabattlegame;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seabattlegame.game.Grid;
import seabattlegame.game.ShotType;
import seabattlegame.game.SquareState;
import seabattlegame.game.ships.AircraftCarrier;
import seabattlegame.game.ships.Minesweeper;

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
        exception.expectMessage("Passed ship cannot be null.");
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
        exception.expectMessage("Tried placing a ship of a type that is already placed.");
        AircraftCarrier carrier1 = new AircraftCarrier();
        grid.placeShip(carrier1, 0, 0, true);
        AircraftCarrier carrier2 = new AircraftCarrier();
        grid.placeShip(carrier2, 4, 4, true);
    }

    @Test
    public void testPlaceShipXCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("One or more of the given coordinates is out of bounds.");
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, -1, 0, true);
    }

    @Test
    public void testPlaceShipYCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("One or more of the given coordinates is out of bounds.");
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, -1, true);
    }

    @Test
    public void testPlaceShipXCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("One or more of the given coordinates is out of bounds.");
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, grid.getCells()[0].length + 1, 0, true);
    }

    @Test
    public void testPlaceShipYCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("One or more of the given coordinates is out of bounds.");
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, grid.getCells().length + 1, true);
    }

    @Test
    public void testPlaceShipShipDoesntFitHorizontally() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Attempted to extend the ship past the grid horizontally.");
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 6, 0, true);
    }

    @Test
    public void testPlaceShipShipDoesntFitVertically() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Attempted to extend the ship past the grid vertically.");
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, 6, false);
    }

    @Test
    public void testPlaceShipCellAlreadyOccupied() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Tried placing a ship on a cell that is already occupied by another ship.");
        AircraftCarrier carrier = new AircraftCarrier();
        Minesweeper minesweeper = new Minesweeper();
        grid.placeShip(carrier, 0, 0, true);
        grid.placeShip(minesweeper, 0, 0, false);
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

    @Test
    public void testShootShipSunk() {
        AircraftCarrier carrier = new AircraftCarrier();
        grid.placeShip(carrier, 0, 0, true);
        for(int i = 0; i < carrier.getLength() - 1; i++) {
            grid.shoot(i, 0);
        }

        assertEquals(SquareState.SHIP, grid.getCells()[0][4].getState());
        assertEquals(ShotType.SUNK, grid.shoot(4, 0));
        assertEquals(SquareState.SHIPSUNK, grid.getCells()[0][4].getState());
    }

    @Test
    public void testShootShotMissed() {
        assertEquals(SquareState.WATER, grid.getCells()[0][0].getState());
        assertEquals(ShotType.MISSED, grid.shoot(0, 0));
    }

    @Test
    public void testShootShootAtSameSpot() {
        assertEquals(SquareState.WATER, grid.getCells()[0][0].getState());
        assertEquals(ShotType.MISSED, grid.shoot(0, 0));
        assertEquals(ShotType.MISSED, grid.shoot(0, 0));
    }

    @Test
    public void testShootXCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("One or more of the given coordinates is out of bounds");
        grid.shoot(-1, 0);
    }

    @Test
    public void testShootXCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("One or more of the given coordinates is out of bounds");
        grid.shoot(grid.getCells()[0].length + 1, 0);
    }

    @Test
    public void testShootYCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("One or more of the given coordinates is out of bounds");
        grid.shoot(0, -1);
    }

    @Test
    public void testShootYCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("One or more of the given coordinates is out of bounds");
        grid.shoot(0, grid.getCells().length + 1);
    }

    @Test
    public void testPlaceShipsAutomatically() {
        grid.placeShipsAutomatically();
        assertEquals(5, grid.getShips().size());
    }
}
