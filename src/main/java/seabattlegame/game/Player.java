package seabattlegame.game;
public class Player {

	private String name;
	private Grid grid;

	public String getName() {
		return this.name;
	}

	public Grid getGrid() {
		return this.grid;
	}

	/**
	 * 
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
	}

}