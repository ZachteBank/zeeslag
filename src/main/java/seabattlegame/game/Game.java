package seabattlegame.game;


public class Game {

	private Player player1;
	private Player player2;
	private Integer turn;
	private int size;

	public Player getPlayer1() {
		return this.player1;
	}

	public Player getPlayer2() {
		return this.player2;
	}

    public int getSize() {
        return size;
    }

    public Game() {
	}

	public Game(Player player1, int size) throws IllegalArgumentException {
	    if(size < 0 || size > 100){
	        throw new IllegalArgumentException();
        }
		this.player1 = player1;
        this.player1.createGrid(size);
		this.size = size;
	}

	/**
	 *
	 * @param player1 The first player
	 * @param player2 The second player
	 */
	public Game(Player player1, Player player2, int size) throws IllegalArgumentException {
        if(size < 0 || size > 100){
            throw new IllegalArgumentException();
        }
        this.player1 = player1;
        this.player2 = player2;
        this.size = size;

        this.player1.createGrid(size);
        this.player2.createGrid(size);
	}

	public boolean startGame() {
		if(player1 == null || player2 == null){
			return false;
		}
	    return true;
	}

	public boolean startNewGame(){
        this.player1 = null;
        this.player2 = null;
        return true;
    }

	public ShotType attack(int attackedid, Integer x, Integer y) {
		return attack(getPlayer(attackedid),x,y);
	}
	public ShotType attack(Player attacked, Integer x, Integer y) {
        return attacked.getGrid().shoot(x, y);
	}

    public boolean placeShipsAutomatically(int playerNr) throws IllegalArgumentException {
	    return placeShipsAutomatically(getPlayer(playerNr));
    }

    public boolean placeShipsAutomatically(Player player) throws IllegalArgumentException {
        if(player == null) {
            throw new IllegalArgumentException();
        }
        return player.getGrid().placeShipsAutomatically();
    }

    public Player getPlayer(int playerNr){
        if(player1 == null && player2 == null) return null;

        return player1.getId() == playerNr ? player1 : player2;
    }


}