package server.json.actions;

public class Shot implements IAction {
    private int x;
    private int y;

    public Shot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
