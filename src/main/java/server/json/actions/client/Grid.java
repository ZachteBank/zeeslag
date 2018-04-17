package server.json.actions.client;

import seabattlegame.game.cells.Cell;
import server.json.actions.IAction;

public class Grid implements IAction {
    private Cell[][] cells;

    public Grid(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
