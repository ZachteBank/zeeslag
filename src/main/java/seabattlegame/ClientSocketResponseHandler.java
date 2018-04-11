package seabattlegame;

import seabattlegame.client.IMessageHandler;

import java.util.Observable;

public class ClientSocketResponseHandler extends Observable implements IMessageHandler {
    public ClientSocketResponseHandler() {
    }

    private void registerResponse() {

    }

    private void startGame() {
        notifyObservers();
    }

    @Override
    public void handleMessage(String message) {
        switch (message) {
            case "registered as player 1":
                registerResponse();
                break;
            case "registered as player 2":
                registerResponse();
                break;
            case "Game started, place your ships!":
                startGame();
                break;
        }
    }
}