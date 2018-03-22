import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seabattlegame.game.Game;
import seabattlegame.game.Player;
import seabattlegame.game.ShotType;

import static org.junit.Assert.*;

public class GameTest {

    private Player player1;
    private Player player2;
    private Game game;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testOnePlayerConstructor() {
        player1 = new Player(0, "John doe");
        game = new Game(player1, 10);
        assertEquals("John doe", game.getPlayer1().getName());
        assertNull(game.getPlayer2());
        assertEquals(10, game.getSize());
    }

    @Test
    public void testOnePlayerConstructorSizeTooHigh() {
        exception.expect(IllegalArgumentException.class);
        player1 = new Player("John doe");
        game = new Game(player1, 101);
    }

    @Test
    public void testOnePlayerConstructorSizeTooLow() {
        exception.expect(IllegalArgumentException.class);
        player1 = new Player("John Doe");
        game = new Game(player1, -1);
    }

    @Test
    public void testOnePlayerConstructorPlayerIsNull() {
        exception.expect(IllegalArgumentException.class);
        game = new Game(player1, 10);
    }

    @Test
    public void testTwoPlayersConstructor() {
        initializeTwoPlayerGame();
        assertEquals("John doe", game.getPlayer(0).getName());
        assertEquals("Jane doe", game.getPlayer(1).getName());
        assertEquals(10, game.getSize());
    }

    @Test
    public void testTwoPlayersConstructorSizeTooLow() {
        exception.expect(IllegalArgumentException.class);
        player1 = new Player(0, "John doe");
        player2 = new Player(1, "Jane doe");
        game = new Game(player1, player2, -1);
    }

    @Test
    public void testTwoPlayersConstructorSizeTooHigh() {
        exception.expect(IllegalArgumentException.class);
        player1 = new Player(0, "John doe");
        player2 = new Player(1, "Jane doe");
        game = new Game(player1, player2, 101);
    }

    @Test
    public void testTwoPlayersConstructorPlayer1IsNull() {
        exception.expect(IllegalArgumentException.class);
        player2 = new Player(1, "Jane doe");
        game = new Game(player1, player2, 10);
    }

    @Test
    public void testTwoPlayersConstructorPlayer2IsNull() {
        exception.expect(IllegalArgumentException.class);
        player1 = new Player(0, "John doe");
        game = new Game(player1, player2, 10);
    }

    @Test
    public void testStartNewGame() {
        initializeTwoPlayerGame();
        assertEquals("John doe", game.getPlayer1().getName());
        assertEquals("Jane doe", game.getPlayer2().getName());
        assertTrue(game.startNewGame());
        assertEquals(null, game.getPlayer1());
        assertEquals(null, game.getPlayer2());
    }

    @Test
    public void testStartGame() {
        initializeTwoPlayerGame();
        assertTrue(game.startGame());
    }

    @Test
    public void testStartGamePlayersNotInitialized() {
        initializeTwoPlayerGame();
        assertTrue(game.startNewGame());
        assertFalse(game.startGame());
    }

    @Test
    public void testAttackPlayerId() {
        initializeTwoPlayerGame();
        ShotType shotType = game.attack(game.getPlayer1().getId(), 0, 0);
        assertEquals(ShotType.MISSED, shotType);
    }

    @Test
    public void testAttackPlayerIdInvalidPlayerId() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(2, 0, 0);
    }

    @Test
    public void testAttackPlayerIdXCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(game.getPlayer1().getId(), -1, 0);
    }

    @Test
    public void testAttackPlayerIdXCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(game.getPlayer1().getId(), game.getPlayer1().getGrid().getCells()[0].length, 0);
    }

    @Test
    public void testAttackPlayerIdYCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(game.getPlayer1().getId(), 0, -1);
    }

    @Test
    public void testAttackPlayerIdYCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(game.getPlayer1().getId(), 0, game.getPlayer1().getGrid().getCells().length);
    }

    @Test
    public void testAttackPlayer() {
        initializeTwoPlayerGame();
        ShotType shotType = game.attack(game.getPlayer1(), 0, 0);
        assertEquals(ShotType.MISSED, shotType);
    }

    @Test
    public void testAttackPlayerPlayerIsNull() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(null, 0, 0);
    }

    @Test
    public void testAttackPlayerXCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(game.getPlayer1(), -1, 0);
    }

    @Test
    public void testAttackPlayerXCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(game.getPlayer1(), game.getPlayer1().getGrid().getCells()[0].length, 0);
    }

    @Test
    public void testAttackPlayerYCoordTooLow() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(game.getPlayer1(), 0, -1);
    }

    @Test
    public void testAttackPlayerYCoordTooHigh() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.attack(game.getPlayer1(), 0, game.getPlayer1().getGrid().getCells().length);
    }

    @Test
    public void testPlaceShipsAutomaticallyPlayerId() {
        initializeTwoPlayerGame();
        assertTrue(game.placeShipsAutomatically(game.getPlayer1().getId()));
        assertEquals(5, game.getPlayer1().getGrid().getShips().size());
        assertEquals(0, game.getPlayer2().getGrid().getShips().size());
    }

    @Test
    public void testPlaceShipsAutomaticallyPlayerIdInvalidPlayerId() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.placeShipsAutomatically(2);
    }

    @Test
    public void testPlaceShipsAutomaticallyPlayer() {
        initializeTwoPlayerGame();
        assertTrue(game.placeShipsAutomatically(game.getPlayer1()));
        assertEquals(5, game.getPlayer1().getGrid().getShips().size());
        assertEquals(0, game.getPlayer2().getGrid().getShips().size());
    }

    @Test
    public void testPlaceShipsAutomaticallyPlayerPlayerIsNull() {
        exception.expect(IllegalArgumentException.class);
        initializeTwoPlayerGame();
        game.placeShipsAutomatically(null);
    }

    private void initializeTwoPlayerGame() {
        player1 = new Player(0, "John doe");
        player2 = new Player(1, "Jane doe");
        game = new Game(player1, player2, 10);
    }

    @After
    public void resetGame() {
        player1 = null;
        player2 = null;
        game = null;
    }
}
