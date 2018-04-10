package seabattlegame.client;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

public class ClientConnection implements Runnable {

    private Session session;
    public ClientConnection() {
            URI uri = URI.create("ws://localhost:9090/seabattleserver/");
            try {
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                // Attempt Connect
                session = container.connectToServer(ClientEndpointSocket.class, uri);
            } catch (DeploymentException | IOException e) {
                e.printStackTrace();
            }
        }

        public void register(String name) {
            // Send a message
            try {
                session.getBasicRemote().sendText("register|" + name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    @Override
    public void run() {

    }
}
