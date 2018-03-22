import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seabattlegame.ISeaBattleGame;
import seabattlegame.SeaBattleGame;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.SeaBattleApplication;

import static org.junit.Assert.*;

public class SeaBattleGameTest extends Application {

    private ISeaBattleGame seaBattleGame;
    private ISeaBattleGUI seaBattleGUI;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(SeaBattleGameTest.class);
            }
        };
        t.setDaemon(true);
        t.start();
        Thread.sleep(500);
    }

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
    public void testRegisterPlayerRegisterSecondPlayerInSinglePlayerMode() {
        exception.expect(IllegalArgumentException.class);
        assertEquals(0, seaBattleGame.registerPlayer("John doe", seaBattleGUI, true));
        seaBattleGame.registerPlayer("Jane doe", seaBattleGUI, true);
    }

    @Test
    public void testRegisterPlayerNameIsNull() {
        exception.expect(IllegalArgumentException.class);
        seaBattleGame.registerPlayer(null, seaBattleGUI, true);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
