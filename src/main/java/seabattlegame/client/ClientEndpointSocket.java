package seabattlegame.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import seabattlegame.SeaBattleGame;
import server.json.Message;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class ClientEndpointSocket {

    private IMessageHandler messageHandler;
    private Session session;

    public ClientEndpointSocket() {
    }

    public void connect() {
        URI uri = URI.create("ws://localhost:9090/seabattleserver/");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            // Attempt Connect
            session = container.connectToServer(this, uri);

        } catch (DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onText(String message) {

        if(messageHandler == null){
            throw new NullPointerException("No messageHandler found");
        }

        if(!message.isEmpty()) {
            messageHandler.handleMessage(message);
        }
    }


    public void addMessageHandler(IMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void sendMessage(Message message) {
        try {
            Gson json = new GsonBuilder().create();
            String jsontosend = json.toJson(message);
            session.getBasicRemote().sendText(jsontosend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

