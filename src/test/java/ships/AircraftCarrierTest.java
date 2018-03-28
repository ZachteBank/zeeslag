package ships;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import seabattlegame.game.ships.*;
import seabattlegui.ShipType;


public class AircraftCarrierTest {

    @Test
    public void testConstructor() {
        Ship carrier = new AircraftCarrier();
        assertEquals(5, carrier.getLength());
        assertEquals(0, carrier.getHits());
        assertEquals(false, carrier.isSunk());
        assertEquals(ShipType.AIRCRAFTCARRIER, carrier.getType());
    }
}
