package seabattlegame.game;
import seabattlegame.game.ships.*;

public class ShipCell extends Cell {

	private Ship ship;

	public ShipCell(Ship ship) {
	    this.state = SquareState.SHIP;
		this.ship = ship;
	}

    public Ship getShip() {
        return ship;
    }

    public void checkShipSunk(){
	    if(ship.isSunk()){
	        this.state = SquareState.SHIPSUNK;
        }
    }

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