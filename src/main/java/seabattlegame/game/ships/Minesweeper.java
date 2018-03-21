package seabattlegame.game.ships;

import seabattlegui.ShipType;

public class Minesweeper extends Ship {

	public Minesweeper() {
		super(2);
	}

	@Override
	public ShipType getType() {
		return ShipType.MINESWEEPER;
	}
}