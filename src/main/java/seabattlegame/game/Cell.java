package seabattlegame.game;
public class Cell {

	protected SquareState state;

	public Cell() {
		// TODO - implement Cell.Cell
		throw new UnsupportedOperationException();
	}

	public SquareState getState() {
		return this.state;
	}

	public SquareState hit() {
		this.state = SquareState.SHOTMISSED;
		return this.state;
	}

}