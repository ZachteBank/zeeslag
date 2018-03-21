import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;
import seabattlegame.game.Game;
import seabattlegame.game.Player;
import seabattlegame.game.ShotType;

import static org.junit.Assert.*;

public class GameTest {

    private Player player1;
    private Player player2;
    private Game game;

    @Before
    public void testInitialize(){
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

    @Test
    public void testAttackPlayerId() {
        ShotType shotType = game.attack(game.getPlayer1().getId(), 0, 0);
        assertEquals(ShotType.MISSED, shotType);
    }

    @Test
    public void testAttackPlayer() {
        ShotType shotType = game.attack(game.getPlayer1(), 0, 0);
        assertEquals(ShotType.MISSED, shotType);
    }
}
