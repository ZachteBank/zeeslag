package server.messageHandler;

import seabattlegame.game.Game;
import seabattlegame.game.Player;
import seabattlegame.game.shipfactory.ShipFactory;
import seabattlegame.game.ships.Ship;
import seabattlegui.ShipType;
import server.messageHandler.game.PlayerSession;

import javax.websocket.Session;
import java.io.IOException;

public class SeaBattleGameMessageHandler implements IMessageHandler {

    private Game game;
    private PlayerSession player1;
    private PlayerSession player2;
    private static int size = 10;

    @Override
    public void handleMessage(String message, Session session) {
        if(!message.contains("|")){
            sendMessage("Message wasn't in correct format", session);
            return;
        }

        String extractedMessage[] = message.split("[|]");
        String action = extractedMessage[0];

        switch (action){
            case "register":
                try {
                    if(!registerPlayer(session, extractedMessage)){
                        sendMessage("Couldn't register player", session);
                    }
                } catch (Exception e) {
                    sendMessage(e.getMessage(), session);
                }
                break;
            case "placeShip":
                if(!placeShip(session, extractedMessage)){
                    sendMessage("Couldn't place ship", session);
                }else{
                    sendMessage("Ship placed", session);
                }
                break;
            case "fire":
                break;
        }
    }

    private void startGame(){
        game = new Game(player1.getPlayer(), player2.getPlayer(), size);
        broadcast("Game started, place your ships!");
        Player turn = game.getTurn();
        broadcast(turn.getName()+" has the first turn");
        PlayerSession playerSession = getPlayerSessionWithUUID(turn.getUUID());
        if(playerSession == null){
            broadcast("Something went very wrong");
        }else{
            sendMessage("It's your turn!", playerSession.getSession());
        }
    }

    private boolean registerPlayer(Session session, String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Args isn't in correct format");
        }

        String name = args[1];

        if (player1 == null) {
            player1 = new PlayerSession(session, new Player(session.getId(), name));
            sendMessage("Registered as player 1", session);
            return true;
        } else if (player2 == null) {
            player1 = new PlayerSession(session, new Player(session.getId(), name));
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

    private boolean placeShip(Session session, String[] args){
        Player player = game.getPlayer(session.getId());
        if(player == null){
            return false;
        }

        if(args.length != 5){
            return false;
        }

        if(!tryParseInt(args[1]) || !tryParseInt(args[2])){
            return false;
        }

        boolean horzontal = args[4].equals("true");

        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);

        ShipType shipType = ShipType.valueOf(args[3].toUpperCase());
        Ship ship = ShipFactory.createShip(shipType);

        try {
            player.getGrid().placeShip(ship, x, y, horzontal);
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
