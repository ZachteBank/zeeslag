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

	public Boolean isSunk() {
		return getHits() == getLength();
	}

	public Ship(Integer length) {
	    this.length = length;
	    this.hits = 0;
	}

	public void hit() {
	    this.hits++;
	}

}