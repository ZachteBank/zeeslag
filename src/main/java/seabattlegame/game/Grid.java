package seabattlegame.game;
import seabattlegame.game.ships.*;

public class Grid {

	private Cell[][] cells;

	public Cell[][] getCells() {
		return this.cells;
	}

	public Grid(int size) {
	    cells = new Cell[size][size];
		// TODO - implement Grid.Grid
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ship
	 */
	public void addShip(Ship ship, int x, int y) {
		// TODO - implement Grid.addShip
		throw new UnsupportedOperationException();
	}

	public boolean removeAllShips(){
        return true;
    }

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public ShotType shoot(Integer x, Integer y) {
	    SquareState state = cells[x][y].hit();
	    switch (state){
            case WATER:
                return ShotType.MISSED;
            case SHOTMISSED:
                return ShotType.MISSED;
            case SHOTHIT:
                return ShotType.HIT;
            case SHIPSUNK:
                return ShotType.SUNK;
            default:
                return ShotType.MISSED;
        }
	}

	public boolean placeShipsAutomatically(){
        return true;
	}

}