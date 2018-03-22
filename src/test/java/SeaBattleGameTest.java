import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import seabattlegame.ISeaBattleGame;
import seabattlegame.SeaBattleGame;
import seabattlegame.game.ships.Ship;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.SeaBattleApplication;
import seabattlegui.ShipType;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class SeaBattleGameTest {

    private ISeaBattleGame seaBattleGame;
    private ISeaBattleGUI seaBattleGUI;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void testInitialize() {
        seaBattleGame = new SeaBattleGame();
        seaBattleGUI = new SeaBattleApplication();
    }

    @Test
    public void testStartNewGame() {
        int playerId = seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.startNewGame(playerId));
    }

    @Test
    public void testStartNewGameInvalidPlayerId() {
        exception.expect(IllegalArgumentException.class);
        seaBattleGame.startNewGame(2);
    }

    @Test
    public void testRegisterPlayerOnePlayerMode() {
        assertNull(seaBattleGame.getGame().getPlayer1());
        assertEquals(0, seaBattleGame.registerPlayer("John doe", seaBattleGUI, true));
        assertNotNull(seaBattleGame.getGame().getPlayer1());
    }

    @Test
    public void testRegisterPlayerTwoPlayerMode() {
        assertNull(seaBattleGame.getGame().getPlayer1());
        assertNull(seaBattleGame.getGame().getPlayer2());
        assertEquals(0, seaBattleGame.registerPlayer("John doe", seaBattleGUI, false));
        assertEquals(1, seaBattleGame.registerPlayer("Jane doe", seaBattleGUI, false));
        assertNotNull(seaBattleGame.getGame().getPlayer1());
        assertNotNull(seaBattleGame.getGame().getPlayer2());
    }

    @Test
    public void testRegisterPlayerNameAlreadyInUse() {
        assertEquals(0, seaBattleGame.registerPlayer("John doe", seaBattleGUI, false));
        assertEquals(-1, seaBattleGame.registerPlayer("John doe", seaBattleGUI, false));
    }

    @Test
    public void testRegisterPlayerRegisterSecondPlayerInSinglePlayerMode() {
        assertEquals(0, seaBattleGame.registerPlayer("John doe", seaBattleGUI, true));
        assertEquals(-1, seaBattleGame.registerPlayer("Jane doe", seaBattleGUI, true));
    }

    @Test
    public void testRegisterPlayerNameIsNull() {
        assertEquals(-1, seaBattleGame.registerPlayer(null, seaBattleGUI, true));
    }

    @Test
    public void testRegisterPlayerRegisterThirdPlayer() {
        assertEquals(0, seaBattleGame.registerPlayer("John doe", seaBattleGUI, false));
        assertEquals(1, seaBattleGame.registerPlayer("Jane doe", seaBattleGUI, false));
        assertEquals(-1, seaBattleGame.registerPlayer("Jake doe", seaBattleGUI, false));
    }

    @Test
    public void testPlaceShipsAutomatically() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.getGame().placeShipsAutomatically(0));
        assertEquals(5, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testPlaceShipsAutomaticallyInvalidPlayer() {
        assertFalse(seaBattleGame.getGame().placeShipsAutomatically(0));
    }

    @Test
    public void testPlaceShip() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true));
    }

    @Test
    public void testPlaceShipInvalidPlayer() {
        assertFalse(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true));
    }

    @Test
    public void testPlaceShipShipTypeIsNull() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(0, null, 0, 0, true));
    }

    @Test
    public void testPlaceShipXCoordTooLow() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, -1, 0, true));
    }

    @Test
    public void testPlaceShipXCoordTooHigh() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER,
                seaBattleGame.getGame().getSize(), 0, true));
    }

    @Test
    public void testPlaceShipYCoordTooLow() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, -1, true));
    }

    @Test
    public void testPlaceShipYCoordTooHigh() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, 0,
                seaBattleGame.getGame().getSize(), true));
    }

    @Test
    public void testRemoveShipRemoveShipAtFirstCoordinate() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true));
        assertEquals(1, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeShip(0, 0, 0));
        assertEquals(0, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testRemoveShipRemoveShipAtMiddleCoordinate() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true));
        assertEquals(1, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeShip(0, 3, 0));
        assertEquals(0, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testRemoveShipRemoveShipAtLastCoordinate() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true));
        assertEquals(1, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeShip(0, 5, 0));
        assertEquals(0, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testRemoveShipMultipleShipsOnGrid() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true));
        assertTrue(seaBattleGame.placeShip(0, ShipType.MINESWEEPER, 0, 5, true));
        assertEquals(2, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeShip(0, 0, 0));
        for (Ship ship : seaBattleGame.getGame().getPlayer1().getGrid().getShips()) {
            if (ship.getType().equals(ShipType.AIRCRAFTCARRIER)) {
                assertTrue(false);
            }
        }
    }

    @Test
    public void testRemoveShipInvalidPlayerId() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true));
        assertFalse(seaBattleGame.removeShip(2, 0, 0));
    }

    @Test
    public void testRemoveShipNoShipAtGivenCoordinate() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.removeShip(0, 0, 0));
    }

    @Test
    public void testRemoveShipXCoordTooLow() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.removeShip(0, -1, 0));
    }

    @Test
    public void testRemoveShipXCoordTooHigh() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.removeShip(0, seaBattleGame.getGame().getSize(), 0));
    }

    @Test
    public void testRemoveShipYCoordTooLow() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.removeShip(0, 0, -1));
    }

    @Test
    public void testRemoveShipYCoordTooHigh() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.removeShip(0, -1, seaBattleGame.getGame().getSize()));
    }
}
