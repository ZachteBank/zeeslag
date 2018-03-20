package seabattlegame.game;
import seabattlegame.game.ships.*;

public class Grid {

	private Cell[][] cells;

	public Cell[][] getCells() {
		return this.cells;
	}

	public Grid(int size) {
	    cells = new Cell[size][size];
	}

	/**
	 * 
	 * @param ship
	 */
	public void placeShip(Ship ship, int x, int y, boolean horizontal) {
	    boolean error = false;
	    error = ship == null;

	    if(x < 0 || y < 0 || x > getCells().length || y > getCells().length){
	        error = true;
        }

        if (horizontal && x + ship.getLength() > getCells().length) {
            error = true;
        }else if (y + ship.getLength() > getCells().length){
            error = true;
        }

        if(error){
	        throw new IllegalArgumentException();
        }

        //for (ship)

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