package seabattlegame.game.ships;

import seabattlegui.ShipType;

public class Battleship extends Ship {

	public Battleship() {
		super(4);
	}

	@Override
	public ShipType getType() {
		return ShipType.BATTLESHIP;
	}
}