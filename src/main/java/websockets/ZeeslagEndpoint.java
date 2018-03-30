package websockets;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashSet;

@ServerEndpoint("/hello")
public class ZeeslagEndpoint {

    private static final HashSet<Session> sessions = new HashSet<>();

    @OnOpen
    public void onSession(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {

    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {

    }

    @OnError
    public void onError(Throwable cause, Session session) {

    }
}
