import org.junit.Test;
import seabattlegame.game.shipfactory.ShipFactory;
import seabattlegame.game.ships.AircraftCarrier;
import seabattlegui.ShipType;

import static org.junit.Assert.*;

public class ShipFactoryTest {

    @Test
    public void testCreateShipAircraftCarrier() {
        AircraftCarrier carrier = new AircraftCarrier();
        assertEquals(carrier.getType(), ShipFactory.createShip(ShipType.AIRCRAFTCARRIER).getType());
    }

}
