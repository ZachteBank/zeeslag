import org.junit.Before;
import org.junit.Test;
import seabattlegame.game.Game;
import seabattlegame.game.Player;

import static org.junit.Assert.*;

public class GameTest {

    private Player player1;
    private Player player2;
    private Game game;

    @Before
    public void testInitalize(){
        player1 = new Player("John doe");
        player2 = new Player("Jane doe");
        game = new Game(player1, player2);
    }

    //TODO: pass players as reference to game
    @Test
    public void testStartNewGame() {
        assertEquals("John doe", player1.getName());
        assertEquals("Jane doe", player2.getName());
        assertTrue(game.startNewGame());
        player1 = null;
        player2 = null;
        assertEquals(null, player1);
        assertEquals(null, player2);

    }
}
