package seabattlegame.game;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGame {
    private Game game;

    @Before
    public void testInitalize(){
        Player player1 = new Player("John doe");
        Player player2 = new Player("Jane doe");
        game = new Game(player1, player2);
    }

    private void initalizeGame(){

    }

    @Test
    public void testConstructor(){
        assertEquals("John doe", game.getPlayer1().getName());
        assertEquals("Jane doe", game.getPlayer2().getName());
    }
/*
    @Ignore
    @Test
    public void testAttack(){
       initalizeGame();
        ShotType shotType = game.attack(0,0);
        assertEquals(ShotType.MISSED, shotType);

        shotType = game.attack(0,1);
        assertEquals(ShotType.HIT, shotType);
    }*/
}
