package seabattlegame.game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGame {
    private Game game;

    @Before
    public void TestInitalize(){
        Player player1 = new Player("John doe");
        Player player2 = new Player("Jane doe");
        game = new Game(player1, player2);
    }

    @Test
    public void TestConstructor(){
        Player player1 = new Player("John doe");
        Player player2 = new Player("Jane doe");
        assertEquals(player1.getName(), game.getPlayer1().getName());
        assertEquals(player2.getName(), game.getPlayer2().getName());
    }
}
