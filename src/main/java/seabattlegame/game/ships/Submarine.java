package seabattlegame.game.ships;

import seabattlegui.ShipType;

public class Submarine extends Ship {

	public Submarine() {
		super(3);
	}

	@Override
	public ShipType getType() {
		return ShipType.SUBMARINE;
	}
}