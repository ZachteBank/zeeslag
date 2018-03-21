import org.junit.Before;
import org.junit.Test;
import seabattlegame.game.Grid;

import static org.junit.Assert.assertEquals;

public class GridTest {

    private Grid grid;

    @Before
    public void testInitialize() {
        grid = new Grid(10);
    }

    @Test
    public void testConstructor() {

        assertEquals(10, grid.getCells().length);
        for (int i = 0; i < grid.getCells().length; i++) {
            assertEquals(10, grid.getCells()[i].length);
        }
        assertEquals(0, grid.getShips().size());
    }

    
}
