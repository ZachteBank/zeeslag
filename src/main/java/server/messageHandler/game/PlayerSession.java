package server.messageHandler.game;

import seabattlegame.game.Player;

import javax.websocket.Session;

public class PlayerSession {
    private Session session;
    private Player player;
    private boolean ready;

    public PlayerSession(Session session, Player player) {
        if(session == null || player == null){
            throw new IllegalArgumentException("Session or player can't be null");
        }
        this.session = session;
        this.player = player;
    }

    public Session getSession() {
        return session;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady() {
        this.ready = true;
    }

    public Player getPlayer() {
        return player;
    }
}
