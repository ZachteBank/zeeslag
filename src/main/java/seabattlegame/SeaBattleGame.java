/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattlegame;

import seabattlegui.ISeaBattleGUI;
import seabattlegui.ShipType;
import seabattlegui.ShotType;

/**
 * The Sea Battle game. To be implemented.
 * @author Nico Kuijpers
 */
public class SeaBattleGame implements ISeaBattleGame {

    @Override
    public int registerPlayer(String name, ISeaBattleGUI application, boolean singlePlayerMode) {
        throw new UnsupportedOperationException("Method registerPlayer() not implemented.");
    }

    @Override
    public boolean placeShipsAutomatically(int playerNr) {
        throw new UnsupportedOperationException("Method placeShipsAutomatically not implemented.");
    }

    @Override
    public boolean placeShip(int playerNr, ShipType shipType, int bowX, int bowY, boolean horizontal) {
        throw new UnsupportedOperationException("Method placeShip() not implemented.");
    }

    @Override
    public boolean removeShip(int playerNr, int posX, int posY) {
        throw new UnsupportedOperationException("Method removeShip() not implemented.");
    }

    @Override
    public boolean removeAllShips(int playerNr) {
        throw new UnsupportedOperationException("Method removeAllShips() not implemented.");
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

    @Override
    public boolean startNewGame(int playerNr) {
        throw new UnsupportedOperationException("Method startNewGame() not implemented.");
    }
}
