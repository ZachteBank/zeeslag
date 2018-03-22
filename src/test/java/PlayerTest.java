import org.junit.Test;
import seabattlegame.game.Player;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PlayerTest {

    @Test
    public void testConstructorNoId() {
        Player player = new Player("Ferd");
        assertEquals("Ferd", player.getName());
        assertEquals(0, player.getId());
        assertNull(player.getGrid());
    }

    @Test
    public void testConstructorId() {
        Player player = new Player(1, "Ferd");
        assertEquals("Ferd", player.getName());
        assertEquals(1, player.getId());
        assertNull(player.getGrid());
    }

    @Test
    public void testCreateGrid() {
        Player player = new Player("Ferd");
        assertNull(player.getGrid());
        player.createGrid(10);
        assertNotNull(player.getGrid());
    }
}
