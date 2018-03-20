package seabattlegame.game;
import seabattlegame.game.ships.*;

public class ShipCell extends Cell {

	private Ship ship;

	public ShipCell(Ship ship) {
		this.ship = ship;
	}

	public Void hit() {
		// TODO - implement ShipCell.hit
		throw new UnsupportedOperationException();
	}

}