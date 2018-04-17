package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import seabattlegame.game.Game;
import seabattlegame.game.Player;
import seabattlegame.game.shipfactory.ShipFactory;
import seabattlegame.game.ships.Ship;
import seabattlegui.ShipType;
import server.messageHandler.IMessageHandler;

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

    private static IMessageHandler messageHandler;

    public static IMessageHandler getMessageHandler() {
        return messageHandler;
    }

    public static void setMessageHandler(IMessageHandler messageHandler) {
        EventServerSocket.messageHandler = messageHandler;
    }

    @OnOpen
    public void onConnect(Session session) {
        System.out.println("[Connected] SessionID:" + session.getId());
        String message = String.format("[New client with client side session ID]: %s", session.getId());
        broadcast(message);
        sessions.put(session.getId(), session);
        System.out.println("[#sessions]: " + sessions.size());
    }

    @OnMessage
    public void onText(String message,Session session) {
        System.out.println("[Received] From : " + session.getId() + " | Content  : " + message);

        if(messageHandler == null){
            throw new NullPointerException("No messageHandler found");
        }

        if(message.isEmpty()){
            sendMessage("No message found", session);
            return;
        }

        messageHandler.handleMessage(message, session);
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
}