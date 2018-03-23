import org.junit.Test;
import seabattlegame.game.shipfactory.ShipFactory;
import seabattlegame.game.ships.*;
import seabattlegui.ShipType;

import static org.junit.Assert.*;

public class ShipFactoryTest {

    @Test
    public void testCreateShipAircraftCarrier() {
        AircraftCarrier carrier = new AircraftCarrier();
        assertEquals(carrier.getType(), ShipFactory.createShip(ShipType.AIRCRAFTCARRIER).getType());
    }

    @Test
    public void testCreateShipCruiser() {
        Cruiser cruiser = new Cruiser();
        assertEquals(cruiser.getType(), ShipFactory.createShip(ShipType.CRUISER).getType());
    }

    @Test
    public void testCreateShipSubmarine() {
        Submarine submarine = new Submarine();
        assertEquals(submarine.getType(), ShipFactory.createShip(ShipType.SUBMARINE).getType());
    }

    @Test
    public void testCreateShipMinesweeper() {
        Minesweeper minesweeper = new Minesweeper();
        assertEquals(minesweeper.getType(), ShipFactory.createShip(ShipType.MINESWEEPER).getType());
    }

    @Test
    public void testCreateShipBattleship() {
        Battleship battleship = new Battleship();
        assertEquals(battleship.getType(), ShipFactory.createShip(ShipType.BATTLESHIP).getType());
    }

    @Test
    public void testCreateShipShipIsNull() {
        ShipFactory.createShip(null);
    }
}
