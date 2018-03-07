package seabattlegame.game;
public class Game {

	private Player player1;
	private Player player2;
	private Integer turn;

	public Player getPlayer1() {
		return this.player1;
	}

	public Player getPlayer2() {
		return this.player2;
	}

	/**
	 *
	 * @param player1 The first player
	 * @param player2 The second player
	 */
	public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
	}

	public void startGame() {
		// TODO - implement Game.startGame
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param x coord
	 * @param y coord
	 */
	public ShotType attack(Integer x, Integer y) {
		// TODO - implement Game.attack
		throw new UnsupportedOperationException();
	}


}