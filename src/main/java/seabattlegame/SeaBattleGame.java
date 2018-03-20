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
import seabattlegame.game.ShotType;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.ShipType;

/**
 * The Sea Battle game. To be implemented.
 *
 * @author Nico Kuijpers
 */
public class SeaBattleGame implements ISeaBattleGame {

    private Game game;

    public SeaBattleGame() {
        game = new Game();


    }

    @Override
    public boolean startNewGame(int playerNr) {
        return game.startGame();
    }

    @Override
    public int registerPlayer(String name, ISeaBattleGUI application, boolean singlePlayerMode) {
        Player players1 = new Player(0, name);
        application.setPlayerName(0, name);
        if (!singlePlayerMode) {
            Player players2 = new Player(1, name);
            application.setOpponentName(1, name);
            game = new Game(players1, players2);
            return players2.getId();
        }
        game = new Game(players1);
        return players1.getId();
    }

    @Override
    public boolean placeShipsAutomatically(int playerNr) {
        try {
            return game.placeShipsAutomatically(playerNr);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean placeShip(int playerNr, ShipType shipType, int bowX, int bowY, boolean horizontal) {
        Ship ship = ShipFactory.createShip(shipType);
        try {
            game.getPlayer(playerNr).getGrid().placeShip(ship, bowX, bowY, horizontal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean removeShip(int playerNr, int posX, int posY) {
        throw new UnsupportedOperationException("Method removeShip() not implemented.");
    }

    @Override
    public boolean removeAllShips(int playerNr) {
        game.getPlayer(playerNr).getGrid().removeAllShips();
        if (game.getPlayer(playerNr).getGrid().getShips().size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean notifyWhenReady(int playerNr) {
        if (game.getPlayer(playerNr).getGrid().getShips().size() == 5) {
            return true;
        }
        return false;
    }

    @Override
    public ShotType fireShotPlayer(int playerNr, int posX, int posY) {
        if (game.getPlayer(playerNr).getId() == game.getPlayer1().getId()) {
            return game.attack(game.getPlayer2().getId(), posX, posY);
        } else {
            return game.attack(game.getPlayer1(), posX, posY);
        }
    }

    @Override
    public ShotType fireShotOpponent(int playerNr) {
        throw new UnsupportedOperationException("Method fireShotOpponent() not implemented.");
    }
}
