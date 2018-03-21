package seabattlegame.game.cells;

import seabattlegame.game.SquareState;

public class Cell {

	protected SquareState state;

	public Cell() {
		state = SquareState.WATER;
	}

	public SquareState getState() {
		return this.state;
	}

	public SquareState hit() {
		this.state = SquareState.SHOTMISSED;
		return this.state;
	}

}