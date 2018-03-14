package seabattlegame.game.shipfactory;

import seabattlegame.game.ships.*;
import seabattlegui.ShipType;

public class ShipFactory {

    public Ship createShip(ShipType shipType) {

        Ship ship = null;

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

        }
        return ship;
    }
}
