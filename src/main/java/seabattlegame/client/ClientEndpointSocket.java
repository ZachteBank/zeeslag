package seabattlegame.client;

import seabattlegame.SeaBattleGame;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

// https://github.com/jetty-project/embedded-jetty-websocket-examples/tree/master/javax.websocket-example/src/main/java/org/eclipse/jetty/demo

@ClientEndpoint
public class ClientEndpointSocket {
    @OnOpen
    public void onWebSocketConnect() {
        System.out.println("[Connected]");
    }

    @OnMessage
    public void onWebSocketText(String message) {

        System.out.println("[Received]: " + message);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        System.out.println("[Closed]: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        System.out.println("[ERROR]: " + cause.getMessage());
    }
}