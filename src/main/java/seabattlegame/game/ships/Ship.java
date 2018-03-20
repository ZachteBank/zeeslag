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
		return hits >= length;
	}

	/**
	 *
	 * @param length the length of the ship
	 */
	public Ship(int length) {
		this.length = length;
	}

	public void hit() {
		hits++;
	}

}