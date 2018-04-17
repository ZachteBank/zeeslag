package server.messageHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import seabattlegame.game.Game;
import seabattlegame.game.Player;
import seabattlegame.game.SquareState;
import seabattlegame.game.shipfactory.ShipFactory;
import seabattlegame.game.ships.Ship;
import seabattlegui.ShipType;
import server.json.Message;
import server.json.actions.*;
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
            sendMessage(new Message("error", e.getMessage()), session);
            return;
        }

        switch (message.getAction()){
            case "register":
                message.parseData(Register.class);
                try {
                    if(!registerPlayer(session, message.getData())){
                        sendMessage(new Message("error", "Couldn't register player"), session);
                    }
                } catch (Exception e) {
                    sendMessage(new Message("error", e.getMessage()), session);
                }
                break;
            case "placeShip":
                message.parseData(PlaceShip.class);
                if(!placeShip(session, message.getData())){
                    sendMessage(new Message("error", "Couldn't place ship"), session);
                }else{
                    sendMessage(new Message("shipPlaced", new Result(true)), session);
                }
                break;
            case "shot":
                message.parseData(Shot.class);
                try {
                    if(!shot(session, message.getData())){
                        sendMessage(null, session);
                    }else{
                        sendMessage(null, session);
                    }
                } catch (Exception e) {
                    sendMessage(new Message("error", e.getMessage()), session);
                }
                break;
        }
    }

    private void startGame(){
        game = new Game(player1.getPlayer(), player2.getPlayer(), size);
        broadcast(new Message("startGame", "Game started, place your ships!"));
        Player turn = game.getTurn();
        broadcast(new Message("firstTurn", turn.getName()+" has the first turn"));
        PlayerSession playerSession = getPlayerSessionWithUUID(turn.getUUID());
        if(playerSession == null){
            broadcast(new Message("error", "Something went very wrong"));
        }else{
            sendMessage(new Message("turn", "It's your turn!"), playerSession.getSession());
        }
    }

    private boolean registerPlayer(Session session, IAction args) {
        if(!(args instanceof Register)){
            return false;
        }
        Register data = (Register) args;

        if (player1 == null) {
            player1 = new PlayerSession(session, new Player(session.getId(), data.getName()));
            sendMessage(new Message("registerResult", new Result(true)), session);
            return true;
        } else if (player2 == null) {
            player2 = new PlayerSession(session, new Player(session.getId(), data.getName()));
            sendMessage(new Message("registerOpponent", data), player1.getSession());
            sendMessage(new Message("registerResult", new Result(true)), session);
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
        }
        catch (Exception e){
            sendMessage(new Message("error", e.getMessage()), session);
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
            sendMessage(new Message("error", e.getMessage()), session);
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

    private void sendMessage(Message message, Session session){

        Gson json = new GsonBuilder().create();

        if(message == null || session == null){
            throw new IllegalArgumentException("Message or session can't be null");
        }
        try {
            String jsonToSend = json.toJson(message);
            session.getBasicRemote().sendText(jsonToSend);
            System.out.println("Send message to "+session.getId()+" with: \""+jsonToSend+"\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(Message message){
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
