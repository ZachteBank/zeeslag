import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import seabattlegame.ISeaBattleGame;
import seabattlegame.SeaBattleGame;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.SeaBattleApplication;

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
    public void testRegisterPlayerNameIsNull() {
        exception.expect(IllegalArgumentException.class);
        seaBattleGame.registerPlayer(null, seaBattleGUI, true);
    }
}
