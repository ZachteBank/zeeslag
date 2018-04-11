package server.messageHandler.game;

import seabattlegame.game.Player;

import javax.websocket.Session;

public class PlayerSession {
    private Session session;
    private Player player;

    public PlayerSession(Session session, Player player) {
        this.session = session;
        this.player = player;
    }

    public Session getSession() {
        return session;
    }

    public Player getPlayer() {
        return player;
    }
}
