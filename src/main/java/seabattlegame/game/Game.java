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
        if (size < 0 || size > 100) {
            throw new IllegalArgumentException();
        }
        if(player1 == null){
            throw new IllegalArgumentException();
        }
        this.player1 = player1;
        this.player1.createGrid(size);
        this.size = size;
    }

    /**
     * @param player1 The first player
     * @param player2 The second player
     */
    public Game(Player player1, Player player2, int size) throws IllegalArgumentException {
        if (size < 0 || size > 100) {
            throw new IllegalArgumentException("Size is too low or too high");
        }
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("One of both players equals null");
        }
        this.player1 = player1;
        this.player2 = player2;
        this.size = size;

        this.player1.createGrid(size);
        this.player2.createGrid(size);
    }

    public boolean startGame() {
        if (player1 == null || player2 == null) {
            return false;
        }
        return true;
    }

    public boolean startNewGame() {
        this.player1 = null;
        this.player2 = null;
        return true;
    }

    public ShotType attack(String attackedid, Integer x, Integer y){
        return attackPlayer(getPlayer(attackedid), x, y);
    }

    public ShotType attack(int attackedid, Integer x, Integer y) {
        if (attackedid > 1) {
            throw new IllegalArgumentException();
        }
        Integer id = attackedid;
        return attack(id.toString(), x, y);
    }

    public ShotType attackPlayer(Player attacked, Integer x, Integer y) {
        if (attacked != null) {

            return attacked.getGrid().shoot(x, y);
        }
        throw new IllegalArgumentException();
    }

    public boolean placeShipsAutomatically(String playerNr){
        return placeShipsAutomaticallyPlayer(getPlayer(playerNr));
    }

    public boolean placeShipsAutomatically(Integer playerNr){
        return placeShipsAutomatically(playerNr.toString());
    }

    public boolean placeShipsAutomaticallyPlayer(Player player){
        return player != null && player.getGrid().placeShipsAutomatically();
    }

    public Player getPlayer(Integer playerNr){
        return getPlayer(playerNr.toString());
    }

    public Player getPlayer(String playerNr) {
        if (player1 == null || player2 == null) return null;

        if(player1.getUUID().equals(playerNr)){
        	return player1;
		}else if(player2.getUUID().equals(playerNr)){
        	return player2;
		}
		return null;
        //return player1.getId() == playerNr ? player1 : player2;
    }


}