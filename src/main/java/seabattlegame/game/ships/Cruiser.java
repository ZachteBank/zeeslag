package seabattlegame.game.ships;

import seabattlegui.ShipType;

public class Cruiser extends Ship {

	public Cruiser() {
		super(3);
	}

	@Override
	public ShipType getType() {
		return ShipType.CRUISER;
	}
}