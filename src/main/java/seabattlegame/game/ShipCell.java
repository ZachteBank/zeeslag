package seabattlegame.game;
import seabattlegame.game.ships.*;

public class ShipCell extends Cell {

	private Ship ship;

	public ShipCell() {
		// TODO - implement ShipCell.ShipCell
		throw new UnsupportedOperationException();
	}

	@Override
	public SquareState hit() {
	    if(this.state == SquareState.SHIP) {
            ship.hit();
            if (ship.isSunk()) {
                this.state = SquareState.SHIPSUNK;
            }else{
                this.state = SquareState.SHOTHIT;
            }
        }
        return this.state;
	}

}