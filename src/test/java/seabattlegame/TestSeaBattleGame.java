package seabattlegame;


import org.junit.Before;
import org.junit.Test;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.SeaBattleApplication;

public class TestSeaBattleGame {
    private ISeaBattleGame seaBattleGame;
    private ISeaBattleGUI seaBattleGui;

    @Before
    public void testInitalize(){
        seaBattleGame = new SeaBattleGame();
        seaBattleGui = new SeaBattleApplication();
    }

    @Test
    public void testRegisterPlayerWrong(){
        seaBattleGame.registerPlayer("Bram", seaBattleGui, true);
    }
}
