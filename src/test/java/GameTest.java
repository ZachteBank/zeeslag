import org.junit.After;
import org.junit.Before;
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
    public void testAttackPlayer() {
        initializeTwoPlayerGame();
        ShotType shotType = game.attack(game.getPlayer1(), 0, 0);
        assertEquals(ShotType.MISSED, shotType);
    }

    private void initializeTwoPlayerGame(){
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
