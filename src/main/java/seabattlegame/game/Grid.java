package seabattlegame.game;
import seabattlegame.game.ships.*;

import java.util.List;

public class Grid {

	private List<Cell> cells;

	public List<Cell> getCells() {
		return this.cells;
	}

	public Grid() {
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
		// TODO - implement Grid.shoot
		throw new UnsupportedOperationException();
	}

	public boolean placeShipsAutomatically(){
        return true;
	}

}