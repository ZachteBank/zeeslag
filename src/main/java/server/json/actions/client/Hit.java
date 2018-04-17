package server.json.actions.client;

import seabattlegame.game.SquareState;
import server.json.actions.IAction;

public class Hit implements IAction {
    private int x;
    private int y;
    private String squareState;

    public Hit(int x, int y, String squareState) {
        this.x = x;
        this.y = y;
        this.squareState = squareState;
    }

    public Hit(int x, int y, SquareState squareState) {
        this.x = x;
        this.y = y;
        this.squareState = squareState.toString();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public SquareState getSquareState() {
        return SquareState.valueOf(squareState);
    }
}
