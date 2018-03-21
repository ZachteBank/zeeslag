import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;
import seabattlegame.game.Game;
import seabattlegame.game.Player;

import static org.junit.Assert.*;

public class GameTest {

    private Player player1;
    private Player player2;
    private Game game;

    //TODO: Give valid arguments to game
    @Before
    public void testInitalize(){
        player1 = new Player("John doe");
        player2 = new Player("Jane doe");
        try {
            game = new Game(player1, player2, 10);
        }
        catch (InvalidArgumentException e) {

        }
    }

    @Test
    public void testStartNewGame() {
        assertEquals("John doe", game.getPlayer1().getName());
        assertEquals("Jane doe", game.getPlayer2().getName());
        assertTrue(game.startNewGame());
        assertEquals(null, game.getPlayer1());
        assertEquals(null, game.getPlayer2());
    }

    @Test
    public void testStartGame() {
        assertTrue(game.startGame());
    }
}
