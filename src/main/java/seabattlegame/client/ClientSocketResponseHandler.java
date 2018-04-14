package seabattlegame.client;

import seabattlegame.SeaBattleGame;

public class ClientSocketResponseHandler extends java.util.Observable implements IMessageHandler {
    private SeaBattleGame game;

    public ClientSocketResponseHandler(SeaBattleGame game) {
        this.game = game;
        addObserver(game);
    }

    private void registerResponse() {

    }

    private void startGame() {
        game.foundOpponent();
    }

    @Override
    public void handleMessage(String message) {
        switch (message) {
            case "Registered as player 1":
                registerResponse();
                break;
            case "registered as player 2":
                registerResponse();
                break;
            case "Game started, place your ships!":
                startGame();
                break;
            case "Ship placed":
                notifyObservers();
                break;
        }
    }
}