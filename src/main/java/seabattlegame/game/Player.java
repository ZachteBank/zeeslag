package seabattlegame.game;
public class Player {

	private int id;
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

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void createGrid(int size){
    	this.grid = new Grid(size);
	}

    public int getId() {
        return id;
    }
}