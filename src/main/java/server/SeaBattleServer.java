package server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import server.REST.RestService;
import server.messageHandler.SeaBattleGameMessageHandler;

import javax.websocket.server.ServerContainer;


public class SeaBattleServer {
    public static void main(String[] args) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9090);
        server.addConnector(connector);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        SeaBattleGameMessageHandler messageHandler = new SeaBattleGameMessageHandler();
        try {
            ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);
            EventServerSocket.setMessageHandler(messageHandler);
            container.addEndpoint(EventServerSocket.class);
            server.start();
            server.join();


        }catch (Throwable t){
            t.printStackTrace(System.err);
        }
    }

}
