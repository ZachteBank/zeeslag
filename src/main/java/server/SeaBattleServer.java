package server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.jersey.servlet.ServletContainer;
import server.REST.OpponentNameServer;
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

            startRest();

        }catch (Throwable t){
            t.printStackTrace(System.err);
        }
    }

    private static void startRest(){
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Server jettyServer = new Server(8090);
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                OpponentNameServer.class.getCanonicalName());
        try {
            jettyServer.start();
            jettyServer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //jettyServer.destroy();
        }
    }

}
