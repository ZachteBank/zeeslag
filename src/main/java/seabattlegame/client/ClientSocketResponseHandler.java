package seabattlegame.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import seabattlegame.SeaBattleGame;
import seabattlegame.game.Player;
import server.json.Message;
import server.json.actions.Register;

public class ClientSocketResponseHandler implements IMessageHandler {
    private SeaBattleGame game;

    public ClientSocketResponseHandler(SeaBattleGame game) {
        this.game = game;
    }

    @Override
    public void handleMessage(String json) {

        Gson gson = new Gson();
        Message message = null;

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        message = gson.fromJson(jsonObject, Message.class);

        switch(message.getAction()) {
            case "register":
                break;


        }
    }
}