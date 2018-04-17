package seabattlegame.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import seabattlegame.SeaBattleGame;
import seabattlegui.ISeaBattleGUI;
import server.json.Message;
import server.json.actions.Register;
import server.json.actions.Result;
import server.json.actions.Shot;
import server.json.actions.client.Grid;

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

        switch(message.getAction()) {
            case "registerResult":
                message.parseData(Result.class);
                Result data = (Result) message.getData();

                if (data.getResult()) {
                    application.showResult("Registered successfully!");
                }
                break;

            case "registerOpponent":
                message.parseData(Register.class);
                Register opponentData = (Register) message.getData();
                game.registerPlayer(opponentData.getName(), application, false);
                if (game.getGame().getPlayer2() != null) {
                    application.showResult("Found an opponent!");
                }
                break;

            case "shipPlaced":
                message.parseData(Result.class);
                Result placeShipResult = (Result) message.getData();

                if(!placeShipResult.getResult()) {
                    application.showResult("Could not place ship.");
                }
                break;

            case "opponentShot":
                message.parseData(Shot.class);
                Shot shotResult = (Shot) message.getData();

                game.fireShotPlayer(game.getGame().getPlayer2().getId(), shotResult.getX(), shotResult.getY());
                break;

            case "placeShipsAutomatically":
                message.parseData(Result.class);
                Result automaticShipsResult = (Result) message.getData();

                if(!automaticShipsResult.getResult()) {
                    application.showResult("Placing ships was unsuccessful.");
                }
                break;

        }
    }
}