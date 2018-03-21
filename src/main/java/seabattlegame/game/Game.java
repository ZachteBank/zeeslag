package seabattlegame.game;

import com.sun.javaws.exceptions.InvalidArgumentException;

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

	public Game() {
	}

	public Game(Player player1) {
		this.player1 = player1;
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

	public boolean startGame() {
		if(player1 == null || player2 == null){
			return false;
		}
	    return true;
	}

	public boolean startNewGame(){
        player1 = null;
        player2 = null;
        return true;
    }

	public ShotType attack(int attackedid, Integer x, Integer y) {
		return attack(getPlayer(attackedid),x,y);
	}
	public ShotType attack(Player attacked, Integer x, Integer y) {
        return attacked.getGrid().shoot(x, y);
	}

    public boolean placeShipsAutomatically(int playerNr) throws InvalidArgumentException {
	    return placeShipsAutomatically(getPlayer(playerNr));
    }

    public boolean placeShipsAutomatically(Player player) throws InvalidArgumentException {
        if(player == null) {
            throw new InvalidArgumentException(new String[]{"Player can't be null"});
        }
        return player.getGrid().placeShipsAutomatically();
    }

    public Player getPlayer(int playerNr){
        if(player1 == null && player2 == null) return null;

        return player1.getId() == playerNr ? player1 : player2;
    }


}