/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattlegame;

import seabattlegame.game.Game;
import seabattlegame.game.Player;
import seabattlegame.game.shipfactory.ShipFactory;
import seabattlegame.game.ships.Ship;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.ShipType;
import seabattlegui.ShotType;

import java.util.ArrayList;
import java.util.List;

/**
 * The Sea Battle game. To be implemented.
 * @author Nico Kuijpers
 */
public class SeaBattleGame implements ISeaBattleGame {

    private Game game;
    private Player[] players;

    public SeaBattleGame() {
        players = new Player[2];
    }

    @Override
    public boolean startNewGame(int playerNr) {
        return game.startGame();
    }

    @Override
    public int registerPlayer(String name, ISeaBattleGUI application, boolean singlePlayerMode) {
        if (players[0] == null) {
            players[0] = new Player(0, name);
            application.setPlayerName(0, name);
            return 0;
        }

        if (players[1] == null && !singlePlayerMode) {
            players[1] = new Player(1, name);
            application.setOpponentName(1, name);
        }
        return -1;
    }

    @Override
    public boolean placeShipsAutomatically(int playerNr) {
        throw new UnsupportedOperationException("Method placeShipsAutomatically not implemented.");
    }

    @Override
    public boolean placeShip(int playerNr, ShipType shipType, int bowX, int bowY, boolean horizontal) {
        Ship ship = ShipFactory.createShip(shipType);

        if (horizontal) {
            if (bowX - ship.getLength() < 0 || bowX + ship.getLength() > 100) {
                return false;
            }
        }
        else {
            if (bowY - ship.getLength() < 0 || bowY + ship.getLength() > 100) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeShip(int playerNr, int posX, int posY) {
        throw new UnsupportedOperationException("Method removeShip() not implemented.");
    }

    @Override
    public boolean removeAllShips(int playerNr) {
        players[playerNr].getGrid().removeAllShips();
    //    if (players[playerNr].getGrid().)
        return false;
    }

    @Override
    public boolean notifyWhenReady(int playerNr) {
        throw new UnsupportedOperationException("Method notifyWhenReady() not implemented.");
    }

    @Override
    public ShotType fireShotPlayer(int playerNr, int posX, int posY) {
        throw new UnsupportedOperationException("Method fireShotPlayer() not implemented.");
    }

    @Override
    public ShotType fireShotOpponent(int playerNr) {
        throw new UnsupportedOperationException("Method fireShotOpponent() not implemented.");
    }
}
