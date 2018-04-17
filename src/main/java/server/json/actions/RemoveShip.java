package server.json.actions;

public class RemoveShip implements IAction {
    private int x;
    private int y;

    public RemoveShip(int x, int y) {
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
