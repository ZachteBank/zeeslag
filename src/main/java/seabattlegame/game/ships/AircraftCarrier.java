package seabattlegame.game.ships;

import seabattlegui.ShipType;

public class AircraftCarrier extends Ship {

	public AircraftCarrier() {
		super(5);
	}

	@Override
	public ShipType getType() {
		return ShipType.AIRCRAFTCARRIER;
	}
}