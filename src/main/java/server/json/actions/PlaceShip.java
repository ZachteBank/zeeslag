package server.json.actions;

public class PlaceShip implements IAction {
    private int x;
    private int y;
    private boolean horizontal;
    private String shipType;

    public PlaceShip(int x, int y, boolean horizontal, String shipType) {
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
        this.shipType = shipType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public String getShipType() {
        return shipType;
    }
}
