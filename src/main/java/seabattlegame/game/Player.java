package seabattlegame.game;
public class Player {

	private String UUID;
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
        this.UUID = "0";
    }

    public Player(String UUID, String name) {
        this.UUID = UUID;
        this.name = name;
    }

    public Player(Integer UUID, String name) {
        this.UUID = UUID.toString();
        this.name = name;
    }


    public void createGrid(int size){
    	this.grid = new Grid(size);
	}

	public String getUUID() {
		return UUID;
	}

	public int getId(){
    	return Integer.parseInt(this.UUID);
	}
}