package seabattlegame.game.shipfactory;

import seabattlegame.game.ships.*;
import seabattlegui.ShipType;

public class ShipFactory {

    public static Ship createShip(ShipType shipType) {

        Ship ship = null;

        if (shipType == null) {
            throw new IllegalArgumentException("ShipType cannot be null.");
        }


        switch (shipType) {
            case CRUISER:
                ship = new Cruiser();
                break;
            case SUBMARINE:
                ship = new Submarine();
                break;
            case AIRCRAFTCARRIER:
                ship = new AircraftCarrier();
                break;
            case MINESWEEPER:
                ship = new Minesweeper();
                break;
            case BATTLESHIP:
                ship = new Battleship();
                break;
            default:
                return ship;
        }
        return ship;
    }
}
