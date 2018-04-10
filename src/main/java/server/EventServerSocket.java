package server;

import seabattlegame.game.Game;
import seabattlegame.game.Player;
import sun.rmi.runtime.Log;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

// https://github.com/jetty-project/embedded-jetty-websocket-examples/tree/master/javax.websocket-example/src/main/java/org/eclipse/jetty/demo

/**
 * https://github.com/jetty-project/embedded-jetty-websocket-examples/blob/
 * master/javax.websocket-example/src/main/java/org/eclipse/jetty/
 * demo/EventServerSocket.java
 *
 * @author Nico Kuijpers, copied from github, adapted by Marcel Koonen
 */

@ServerEndpoint(value = "/seabattleserver/")
public class EventServerSocket {
    private static Map<String, Session> map = new HashMap<>();
    private static Map<String, Session> sessions = Collections.synchronizedMap(map);

    private Game game;
    private Player player1;
    private Player player2;
    private static int size = 10;

    @OnOpen
    public void onConnect(Session session) {
        System.out.println("[Connected] SessionID:" + session.getId());
        String message = String.format("[New client with client side session ID]: %s", session.getId());
        broadcast(message);
        sessions.put(session.getId(), session);
        System.out.println("[#sessions]: " + sessions.size());

        if (sessions.size() == 0) {
            game = new Game();
        }
    }

    @OnMessage
    public void onText(String message,Session session) {
        System.out.println("[Session ID] : " + session.getId() + " [Received] : " + message);

        if(message.isEmpty()){
            sendMessage("No message found", session);
            return;
        }
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
                }
                break;
            case "fire":
                break;
        }
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("[Session ID] : " + session.getId() + "[Socket Closed: " + reason);
        sessions.remove(session.getId());
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        System.out.println("[Session ID] : " + session.getId() + "[ERROR]: ");
        cause.printStackTrace(System.err);
    }

    private void startGame(){
        game = new Game(player1, player2, size);
        broadcast("Game started, place your ships!");
        Player turn = game.getTurn();
        broadcast(turn.getName()+" has the first turn");
        Session session = findSessionById(turn.getUUID());
        if(session == null){
            broadcast("Something went very wrong");
        }else{
            sendMessage("It's your turn!", session);
        }
    }

    private boolean placeShip(Session session, String[] args){
        return false;
    }

    private boolean registerPlayer(Session session, String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Args isn't in correct format");
        }
        if (this.game == null) {
            return false;
        }

        String name = args[1];

        if (player1 == null) {
            player1 = new Player(session.getId(), name);
            sendMessage("Registerd as player 1", session);
            return true;
        } else if (player2 == null) {
            player2 = new Player(session.getId(), name);
            sendMessage("Registerd as player 2", session);
            startGame();
            return true;
        }
        return false;
    }

    public void broadcast(String s) {
        System.out.println("[Broadcast] { " + s + " } to:");
        for(Map.Entry<String, Session> entry : sessions.entrySet()) {
            try {
                entry.getValue().getBasicRemote().sendText(s);
                System.out.println("\t\t >> Client associated with server side session ID: " + entry.getValue().getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[End of Broadcast]");
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

    private Session findSessionById(String id){
        if(sessions.containsKey(id)){
            return sessions.get(id);
        }
        return null;
    }
}