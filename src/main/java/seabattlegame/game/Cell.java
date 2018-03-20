package seabattlegame.game;
public class Cell {

	private SquareState state;

	public Cell() {
		state = SquareState.WATER;
	}

	public SquareState getState() {
		return this.state;
	}

	public void hit() {
		// TODO - implement Cell.hit
		throw new UnsupportedOperationException();
	}

}