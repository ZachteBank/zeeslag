import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import seabattlegame.ISeaBattleGame;
import seabattlegame.SeaBattleGame;
import seabattlegame.game.ShotType;
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
    public ExpectedException exception = ExpectedException.none();

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
        assertFalse(seaBattleGame.startNewGame(2));
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
        assertTrue(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true));
    }

    @Test
    public void testPlaceShipInvalidPlayer() {
        assertFalse(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true));
    }

    @Test
    public void testPlaceShipShipTypeIsNull() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(),
                null, 0, 0, true));
    }

    @Test
    public void testPlaceShipXCoordTooLow() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                -1, 0, true));
    }

    @Test
    public void testPlaceShipXCoordTooHigh() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                seaBattleGame.getGame().getSize(), 0, true));
    }

    @Test
    public void testPlaceShipYCoordTooLow() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, -1, true));
    }

    @Test
    public void testPlaceShipYCoordTooHigh() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, seaBattleGame.getGame().getSize(), true));
    }

    @Test
    public void testRemoveShipRemoveShipAtFirstCoordinate() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true));
        assertEquals(1, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeShip(seaBattleGame.getGame().getPlayer1().getId(), 0, 0));
        assertEquals(0, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testRemoveShipRemoveShipAtMiddleCoordinate() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true));
        assertEquals(1, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeShip(seaBattleGame.getGame().getPlayer1().getId(), 3, 0));
        assertEquals(0, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testRemoveShipRemoveShipAtLastCoordinate() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true));
        assertEquals(1, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeShip(seaBattleGame.getGame().getPlayer1().getId(), 5, 0));
        assertEquals(0, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testRemoveShipMultipleShipsOnGrid() {
        seaBattleGame.registerPlayer("John Doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true));
        assertTrue(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.MINESWEEPER,
                0, 5, true));
        assertEquals(2, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeShip(seaBattleGame.getGame().getPlayer1().getId(), 0, 0));
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
        assertFalse(seaBattleGame.removeShip(seaBattleGame.getGame().getPlayer1().getId(), 0, 0));
    }

    @Test
    public void testRemoveShipXCoordTooLow() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.removeShip(seaBattleGame.getGame().getPlayer1().getId(), -1, 0));
    }

    @Test
    public void testRemoveShipXCoordTooHigh() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.removeShip(seaBattleGame.getGame().getPlayer1().getId(),
                seaBattleGame.getGame().getSize(), 0));
    }

    @Test
    public void testRemoveShipYCoordTooLow() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.removeShip(seaBattleGame.getGame().getPlayer1().getId(), 0, -1));
    }

    @Test
    public void testRemoveShipYCoordTooHigh() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.removeShip(seaBattleGame.getGame().getPlayer1().getId(), -1,
                seaBattleGame.getGame().getSize()));
    }

    @Test
    public void testRemoveAllShipsNoShips() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.removeAllShips(seaBattleGame.getGame().getPlayer1().getId(), seaBattleGUI));
    }

    @Test
    public void testRemoveAllShipsOneShip() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true));
        assertEquals(1, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeAllShips(seaBattleGame.getGame().getPlayer1().getId(), seaBattleGUI));
        assertEquals(0, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testRemoveAllShipsRemoveFourShips() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        for (int i = 0; i < 4; i++) {
            assertTrue(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.values()[i],
                    0, i, true));
        }
        assertEquals(4, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        seaBattleGame.removeAllShips(seaBattleGame.getGame().getPlayer1().getId(), seaBattleGUI);
        assertEquals(0, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testRemoveAllShipsAllShips() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShipsAutomatically(seaBattleGame.getGame().getPlayer1().getId()));
        assertEquals(5, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
        assertTrue(seaBattleGame.removeAllShips(seaBattleGame.getGame().getPlayer1().getId(), seaBattleGUI));
        assertEquals(0, seaBattleGame.getGame().getPlayer1().getGrid().getShips().size());
    }

    @Test
    public void testRemoveAllShipsInvalidPlayer() {
        assertFalse(seaBattleGame.removeAllShips(2, seaBattleGUI));
    }

    @Test
    public void testNotifyWhenReadyAllShipsPlaced() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertTrue(seaBattleGame.placeShipsAutomatically(seaBattleGame.getGame().getPlayer1().getId()));
        assertTrue(seaBattleGame.notifyWhenReady(seaBattleGame.getGame().getPlayer1().getId()));
    }

    @Test
    public void testNotifyWhenReadyInvalidPlayer() {
        assertFalse(seaBattleGame.notifyWhenReady(2));
    }

    @Test
    public void testNotifyWhenReadyNoShipsPlaced() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertFalse(seaBattleGame.notifyWhenReady(seaBattleGame.getGame().getPlayer1().getId()));
    }

    @Test
    public void testNotifyWhenReadyOneShipPlaced() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true);
        assertFalse(seaBattleGame.notifyWhenReady(seaBattleGame.getGame().getPlayer1().getId()));
    }

    @Test
    public void testNotifyWhenReadyFourShipsPlaced() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        for (int i = 0; i < 4; i++) {
            assertTrue(seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.values()[i],
                    0, i, true));
        }
        assertFalse(seaBattleGame.notifyWhenReady(seaBattleGame.getGame().getPlayer1().getId()));
    }

    @Test
    public void testFireShotPlayerShotMissed() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        assertEquals(ShotType.MISSED, seaBattleGame.fireShotPlayer(1, 0, 0));
    }

    @Test
    public void testFireShotPlayerShotHitFirstCoordinate() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true);
        assertEquals(ShotType.HIT, seaBattleGame.fireShotPlayer(1, 0, 0));
    }

    @Test
    public void testFireShotPlayerShotHitMiddleCoordinate() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true);
        assertEquals(ShotType.HIT, seaBattleGame.fireShotPlayer(1, 3, 0));
    }

    @Test
    public void testFireShotPlayerShotHitLastCoordinate() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true);
        assertEquals(ShotType.HIT, seaBattleGame.fireShotPlayer(1, 4, 0));
    }

    @Test
    public void testFireShotPlayerShipSunk() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true);
        seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.MINESWEEPER,
                0, 1, true);
        for (int i = 0; i < 4; i++) {
            assertEquals(ShotType.HIT, seaBattleGame.fireShotPlayer(1, i, 0));
        }
        assertEquals(ShotType.SUNK, seaBattleGame.fireShotPlayer(1, 4, 0));
    }

    @Test
    public void testFireShotPlayerAllSunk() {
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.placeShip(seaBattleGame.getGame().getPlayer1().getId(), ShipType.AIRCRAFTCARRIER,
                0, 0, true);
        for (int i = 0; i < 4; i++) {
            assertEquals(ShotType.HIT, seaBattleGame.fireShotPlayer(1, i, 0));
        }
        assertEquals(ShotType.ALLSUNK, seaBattleGame.fireShotPlayer(1, 4, 0));
    }

    @Test
    public void testFireShotPlayerXCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.fireShotPlayer(1, -1, 0);
    }

    @Test
    public void testFireShotPlayerXCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.fireShotPlayer(1, seaBattleGame.getGame().getSize(), 0);
    }

    @Test
    public void testFireShotPlayerYCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.fireShotPlayer(1, 0, -1);

    }

    @Test
    public void testFireShotPlayerYCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        seaBattleGame.registerPlayer("John doe", seaBattleGUI, true);
        seaBattleGame.fireShotPlayer(1, 0, seaBattleGame.getGame().getSize());
    }

    @Test
    public void testFireShotPlayerInvalidPlayer() {
        exception.expect(IllegalArgumentException.class);
        seaBattleGame.fireShotPlayer(2, 0, 0);
    }

    @Test
    public void testFireShotOpponentInvalidPlayer() {
        exception.expect(IllegalArgumentException.class);
        seaBattleGame.fireShotOpponent(2);
    }

    @Test
    public void testUpdateGridInvalidPlayerId() {

    }

    @Test
    public void testUpdateGridInvalidOpponentId() {

    }

    //TODO: Few more updateGrid tests
}
