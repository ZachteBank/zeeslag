package seabattlegame.game.ships;

public abstract class Ship {

	private Integer hits;
	private Integer length;

	public Integer getHits() {
		return this.hits;
	}

	public Integer getLength() {
		return this.length;
	}

	public Boolean isSunk() {
		// TODO - implement Ship.IsSunk
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param length the length of the ship
	 */
	public Ship(Integer length) {
		// TODO - implement Ship.Ship
		throw new UnsupportedOperationException();
	}

	public Void hit() {
		// TODO - implement Ship.hit
		throw new UnsupportedOperationException();
	}

}