/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattlegame;

import seabattlegame.client.ClientEndpointSocket;
import seabattlegame.client.ClientSocketResponseHandler;
import seabattlegame.client.IMessageHandler;
import seabattlegame.game.Game;
import seabattlegame.game.Player;
import seabattlegame.game.SquareState;
import seabattlegame.game.shipfactory.ShipFactory;
import seabattlegame.game.ships.Ship;
import seabattlegame.game.ShotType;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.SeaBattleApplication;
import seabattlegui.ShipType;
import server.json.Message;
import server.json.actions.PlaceShip;
import server.json.actions.Register;
import server.json.actions.Shot;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * The Sea Battle game. To be implemented.
 *
 * @author Nico Kuijpers
 */
public class SeaBattleGame implements ISeaBattleGame {

    private Game game;
    private ClientEndpointSocket clientEndpointSocket;
    private ClientSocketResponseHandler clientSocketResponseHandler;
    private boolean singleplayermode;
    private ISeaBattleGUI application;

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

    public int registerPlayer(String name, ISeaBattleGUI application, boolean singlePlayerMode) {
        this.singleplayermode = singlePlayerMode;

        if (name == null) {
            return -1;
        }

        Player player1 = new Player("0", name);
        application.setPlayerName(0, name);

        if (!singlePlayerMode) {
            if (game.getPlayer1() == null) {
                if (clientEndpointSocket == null) {
                    clientEndpointSocket = new ClientEndpointSocket();
                    clientSocketResponseHandler = new ClientSocketResponseHandler(this, application);
                    clientEndpointSocket.setMessageHandler(clientSocketResponseHandler);
                    clientEndpointSocket.connect();
                }

                Register register = new Register(name);

                clientEndpointSocket.sendMessage(new Message("register", register));
                Player player2 = new Player("1", name);
                game = new Game(player1, player2, 10);
                return player1.getId();
            }
        }

        try {
            Player player2 = new Player("1", "AI");
            game = new Game(player1, player2, 10);
            placeShipsAutomatically(1);
            return player1.getId();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    @Override
    public boolean placeShipsAutomatically(int playerNr) {
        if (!singleplayermode) {
            clientEndpointSocket.sendMessage(new Message("placeShipAutomatically"));
            clientEndpointSocket.sendMessage(new Message("grid"));
            return true;
        }
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

        if (!singleplayermode) {
            PlaceShip placeShip = new PlaceShip(bowX, bowY, horizontal, shipType.toString());
            clientEndpointSocket.sendMessage(new Message("placeShip", placeShip));

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

        if (!singleplayermode) {
            clientEndpointSocket.sendMessage(new Message("ready"));
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
            if (!singleplayermode) {
                Shot shot = new Shot(posX, posY);
                clientEndpointSocket.sendMessage(new Message("shot", shot));
            }
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
}

