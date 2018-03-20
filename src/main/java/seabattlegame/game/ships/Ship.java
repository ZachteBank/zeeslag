package seabattlegame.game.ships;

public abstract class Ship {

	private int hits;
	private int length;

	public int getHits() {
		return this.hits;
	}

	public int getLength() {
		return this.length;
	}

	public boolean isSunk() {
		// TODO - implement Ship.IsSunk
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param length the length of the ship
	 */
	public Ship(int length) {
		// TODO - implement Ship.Ship
		throw new UnsupportedOperationException();
	}

	public void hit() {
		// TODO - implement Ship.hit
		throw new UnsupportedOperationException();
	}

}