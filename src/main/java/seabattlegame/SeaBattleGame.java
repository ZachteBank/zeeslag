/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattlegame;

import seabattlegame.client.ClientEndpointSocket;
import seabattlegame.client.IMessageHandler;
import seabattlegame.game.Game;
import seabattlegame.game.Player;
import seabattlegame.game.SquareState;
import seabattlegame.game.shipfactory.ShipFactory;
import seabattlegame.game.ships.Ship;
import seabattlegame.game.ShotType;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.ShipType;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * The Sea Battle game. To be implemented.
 *
 * @author Nico Kuijpers
 */
public class SeaBattleGame implements ISeaBattleGame, Observer {

    private Game game;
    private ClientEndpointSocket clientEndpointSocket;
    private ClientSocketResponseHandler clientSocketResponseHandler;

    public Game getGame() {
        return game;
    }

    public SeaBattleGame() {
        game = new Game();

    }

    @Override
    public boolean startNewGame(int playerNr) {
        return game.startGame();
    }

    @Override
    public int registerPlayer(String name, ISeaBattleGUI application, boolean singlePlayerMode) {
        if (name == null) {
            return -1;
        }
        Player players1 = new Player("0", name);
        application.setPlayerName(0, name);
        if (!singlePlayerMode) {
            try {
                clientEndpointSocket = new ClientEndpointSocket();
                IMessageHandler clientSocketResponseHandler = new ClientSocketResponseHandler();

                clientEndpointSocket.addMessageHandler(clientSocketResponseHandler);
                clientEndpointSocket.sendMessage("register|" + name);
                wait();

            } catch (IllegalArgumentException e) {
                return -1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return players1.getId();
        }
        try {
            Player player2 = new Player("1", "AI");
            game = new Game(players1, player2, 10);
            placeShipsAutomatically(1);
        } catch (IllegalArgumentException e) {
            return -1;
        }
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
        if (shipType == null) {
            return false;
        }

        Ship ship = ShipFactory.createShip(shipType);
        try {
            game.getPlayer(playerNr).getGrid().placeShip(ship, bowX, bowY, horizontal);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean removeShip(int playerNr, int posX, int posY, ISeaBattleGUI application) {
        boolean result = game.getPlayer(playerNr).getGrid().removeShip(posX, posY);
        updateGrid(playerNr, 1, application);
        return result;
    }

    @Override
    public boolean removeAllShips(int playerNr, ISeaBattleGUI application) {
        if (playerNr > 1) {
            return false;
        }
        game.getPlayer(playerNr).getGrid().removeAllShips();
        updateGrid(playerNr, 1, application);
        if (game.getPlayer(playerNr).getGrid().getShips().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean notifyWhenReady(int playerNr) {
        if (playerNr < 0 || playerNr > 1) {
            return false;
        }
        if (game.getPlayer(playerNr).getGrid().getShips().size() == 5) {
            return true;
        }
        return false;
    }

    @Override
    public ShotType fireShotPlayer(int playerNr, int posX, int posY) {
        if (playerNr > 1) {
            throw new IllegalArgumentException();
        }
        if (playerNr == game.getPlayer1().getId()) {
            return game.attack(game.getPlayer2().getUUID(), posX, posY);
        } else {
            return game.attackPlayer(game.getPlayer1(), posX, posY);
        }
    }

    @Override
    public ShotType fireShotOpponent(int playerNr) {
        Random random = new Random();
        return game.attack(playerNr, random.nextInt(10), random.nextInt(10));

    }

    @Override
    public void updateGrid(int playerId, int opponentId, ISeaBattleGUI application) {
        Player player = game.getPlayer(playerId);
        Player opponent = game.getPlayer(1);
        for (int i = 0; i < player.getGrid().getCells().length; i++) {
            for (int j = 0; j < player.getGrid().getCells().length; j++) {
                application.showSquarePlayer(playerId, j, i, player.getGrid().getCells()[i][j].getState());
                SquareState state = opponent.getGrid().getCells()[i][j].getState();
                if (!state.equals(SquareState.SHIP)) {
                    application.showSquareOpponent(playerId, j, i, state);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

