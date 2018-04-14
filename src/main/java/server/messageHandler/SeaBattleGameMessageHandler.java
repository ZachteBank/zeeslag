package server.messageHandler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import seabattlegame.game.Game;
import seabattlegame.game.Player;
import seabattlegame.game.shipfactory.ShipFactory;
import seabattlegame.game.ships.Ship;
import seabattlegui.ShipType;
import server.json.Message;
import server.json.actions.IAction;
import server.json.actions.PlaceShip;
import server.json.actions.Register;
import server.json.actions.Shot;
import server.messageHandler.game.PlayerSession;

import javax.websocket.Session;
import java.io.IOException;

public class SeaBattleGameMessageHandler implements IMessageHandler {

    private Game game;
    private PlayerSession player1;
    private PlayerSession player2;
    private static int size = 10;

    @Override
    public void handleMessage(String json, Session session) {

        Gson g = new Gson();
        Message message = null;

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        try {
            message = g.fromJson(jsonObject, Message.class);
        }catch (Exception e){
            System.out.println(json);
            sendMessage(e.getMessage(), session);
            return;
        }

        switch (message.getAction()){
            case "register":
                message.parseData(Register.class);
                try {
                    if(!registerPlayer(session, message.getData())){
                        sendMessage("Couldn't register player", session);
                    }
                } catch (Exception e) {
                    sendMessage(e.getMessage(), session);
                }
                break;
            case "placeShip":
                message.parseData(PlaceShip.class);
                if(!placeShip(session, message.getData())){
                    sendMessage("Couldn't place ship", session);
                }else{
                    sendMessage("Ship placed", session);
                }
                break;
            case "shot":
                message.parseData(Shot.class);
                try {
                    if(!shot(session, message.getData())){
                        sendMessage("Couldn't place ship", session);
                    }else{
                        sendMessage("Ship placed", session);
                    }
                } catch (Exception e) {
                    sendMessage(e.getMessage(), session);
                }
                break;
        }
    }

    private void startGame(){
        game = new Game(player1.getPlayer(), player2.getPlayer(), size);
        broadcast("Game started, place your seabattlegame.ships!");
        Player turn = game.getTurn();
        broadcast(turn.getName()+" has the first turn");
        PlayerSession playerSession = getPlayerSessionWithUUID(turn.getUUID());
        if(playerSession == null){
            broadcast("Something went very wrong");
        }else{
            sendMessage("It's your turn!", playerSession.getSession());
        }
    }

    private boolean registerPlayer(Session session, IAction args) {
        if(!(args instanceof Register)){
            return false;
        }
        Register data = (Register) args;

        if (player1 == null) {
            player1 = new PlayerSession(session, new Player(session.getId(), data.getName()));
            sendMessage("Registered as player 1", session);
            return true;
        } else if (player2 == null) {
            player2 = new PlayerSession(session, new Player(session.getId(), data.getName()));
            sendMessage("Registered as player 2", session);
            startGame();
            return true;
        }
        return false;
    }

    private boolean placeShipsAutomatically(Session session, String[] args){
        if(args.length != 2){
            return false;
        }
        return false;

    }

    private boolean shot(Session session, IAction args) throws Exception {
        if(!(args instanceof Shot)){
            return false;
        }
        Shot data = (Shot) args;
        Player player = game.getOpponent();

        if(player == null){
            return false;
        }

        if(player == game.getPlayer(session.getId())){
            throw new Exception("You can't attack yourself");
        }
        if(game.getTurn() != game.getPlayer(session.getId())){
            throw new Exception("It isn't your turn");
        }

        try {
            game.attack(player.getId(), data.getX(), data.getY());
            return true;
        }catch (Exception e){
            sendMessage(e.getMessage(), session);
            return false;
        }
    }

    private boolean placeShip(Session session, IAction args){
        if(!(args instanceof PlaceShip)){
            return false;
        }
        PlaceShip data = (PlaceShip) args;
        Player player = game.getPlayer(session.getId());
        if(player == null){
            return false;
        }

        ShipType shipType = ShipType.valueOf(data.getShipType().toUpperCase());
        Ship ship = ShipFactory.createShip(shipType);

        try {
            player.getGrid().placeShip(ship, data.getX(), data.getY(), data.isHorizontal());
            return true;
        }catch (Exception e){
            sendMessage(e.getMessage(), session);
            return false;
        }
    }

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void sendMessage(String message, Session session){
        if(message == null || session == null){
            throw new IllegalArgumentException("Message or session can't be null");
        }
        try {
            session.getBasicRemote().sendText(message);
            System.out.println("Send message to "+session.getId()+" with: \""+message+"\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(String message){
        if(player1 != null){
            sendMessage(message, player1.getSession());
        }
        if(player2 != null){
            sendMessage(message, player2.getSession());
        }
    }

    private PlayerSession getPlayerSessionWithUUID(String UUID){
        if(player1 != null && player1.getPlayer().getUUID().equals(UUID)){
            return player1;
        }
        if(player2 != null && player2.getPlayer().getUUID().equals(UUID)){
            return player2;
        }
        return null;
    }
}
