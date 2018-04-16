package server.messageHandler.game;

import seabattlegame.game.Player;

import javax.websocket.Session;

public class PlayerSession {
    private Session session;
    private Player player;
    private boolean ready;

    public PlayerSession(Session session, Player player) {
        this.session = session;
        this.player = player;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady() {
        this.ready = true;
    }

    public Session getSession() {
        return session;
    }

    public Player getPlayer() {
        return player;
    }
}
