package seabattlegame.game.ships;

import seabattlegui.ShipType;

public abstract class Ship {

	private int hits;
	private int length;
	private ShipType shipType;

	public int getHits() {
		return this.hits;
	}

	public int getLength() {
		return this.length;
	}

	public Boolean isSunk() {
		return getHits() == getLength();
	}

	public Ship(Integer length, ShipType shipType) {
	    this.length = length;
	    this.hits = 0;
	    this.shipType = shipType;
	}

	public void hit() {
	    this.hits++;
	}

	public ShipType getType() {
	    return shipType;
    }

}