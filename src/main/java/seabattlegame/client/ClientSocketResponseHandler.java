package seabattlegame.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import seabattlegame.SeaBattleGame;
import seabattlegame.game.SquareState;
import seabattlegui.ISeaBattleGUI;
import server.json.Message;
import server.json.actions.Register;
import server.json.actions.RemoveShip;
import server.json.actions.Result;
import server.json.actions.Shot;
import server.json.actions.client.Grid;
import server.json.actions.client.Hit;

public class ClientSocketResponseHandler implements IMessageHandler {
    private SeaBattleGame game;
    private ISeaBattleGUI application;

    public ClientSocketResponseHandler(SeaBattleGame game, ISeaBattleGUI application) {
        this.game = game;
        this.application = application;
    }

    @Override
    public void handleMessage(String json) {

        Gson gson = new Gson();
        Message message;

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        message = gson.fromJson(jsonObject, Message.class);

        switch (message.getAction()) {
            case "registerResult":
                message.parseData(Result.class);
                Result data = (Result) message.getData();

                if (data.getResult()) {
                    application.showResult("Registered successfully!");
                }
                break;
            case "playerNumber":
                game.setPlayerNumber(Integer.parseInt(message.getContent()));
                break;

            case "shipPlaced":
                message.parseData(Result.class);
                Result placeShipResult = (Result) message.getData();

                if (!placeShipResult.getResult()) {
                    application.showResult("Could not place ship.");
                }
                break;

            case "placeShipsAutomatically":
                message.parseData(Result.class);
                Result automaticShipsResult = (Result) message.getData();

                if (!automaticShipsResult.getResult()) {
                    application.showResult("Placing ships was unsuccessful.");
                }
                break;

            case "hit":
                message.parseData(Hit.class);
                Hit hit = (Hit) message.getData();

                game.fireShotPlayer(1, hit.getX(), hit.getY());
                break;

            case "yourGrid":
                message.parseData(Grid.class);
                Grid yourGrid = (Grid) message.getData();
                for (int i = 0; i < yourGrid.getCells().length; i++) {
                    for (int j = 0; j < yourGrid.getCells().length; j++) {
                        application.showSquarePlayer(0, j, i, yourGrid.getCells()[i][j].getState());
                    }
                }
                break;

            case "opponentGrid":
                message.parseData(Grid.class);
                Grid opponentGrid = (Grid) message.getData();
                for (int i = 0; i < opponentGrid.getCells().length; i++) {
                    for (int j = 0; j < opponentGrid.getCells().length; j++) {
                        SquareState state = opponentGrid.getCells()[i][j].getState();
                        if (!state.equals(SquareState.SHIP)) {
                            application.showSquareOpponent(0, j, i, state);
                        }
                    }
                }
                break;

            case "shipRemoved":
                message.parseData(RemoveShip.class);
                Result removeShipResult = (Result) message.getData();

                if (!removeShipResult.getResult()) {
                    application.showResult("Placing ships was unsuccessful.");
                }
                break;

            case "startGame":
                application.playerFound();
                break;

            case "error":
                application.showResult(message.getContent());
                break;
        }
    }
}