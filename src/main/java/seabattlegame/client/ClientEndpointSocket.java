package seabattlegame.client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class ClientEndpointSocket {

    private IMessageHandler messageHandler;

    private Session session;

    public ClientEndpointSocket() {
        URI uri = URI.create("ws://localhost:9090/seabattleserver/");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            // Attempt Connect
            session = container.connectToServer(this, uri);

        } catch (DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onWebSocketConnect() {
        System.out.println("[Connected]");
    }

    @OnMessage
    public void onWebSocketText(String message) {
        System.out.println("[Received]: " + message);
        messageHandler.handleMessage(message);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        System.out.println("[Closed]: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        System.out.println("[ERROR]: " + cause.getMessage());
    }

    public void addMessageHandler(IMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

