package seabattlegame.game;
import com.sun.javafx.UnmodifiableArrayList;
import seabattlegame.game.ships.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Grid {

    private List<Ship> ships = new ArrayList<>();
	private Cell[][] cells;

	public Cell[][] getCells() {
		return this.cells;
	}

	public Grid(int size) {
	    initalisizeCells(size);
	}

    public List<Ship> getShips() {
        return Collections.unmodifiableList(ships);
    }

    /**
	 * 
	 * @param ship
	 */
	public void placeShip(Ship ship, int x, int y, boolean horizontal) throws IllegalArgumentException {
	    boolean error;

	    error = ship == null || ships.contains(ship);

	    if(x < 0 || y < 0 || x > getCells().length || y > getCells().length){
	        error = true;
        }

        if (horizontal && x + ship.getLength() > getCells().length) {
            error = true;
        }else if (y + ship.getLength() > getCells().length){
            error = true;
        }

        if(error){
	        throw new IllegalArgumentException();
        }

        for (int i = 0; i < ship.getLength(); i++){
            if(cells[!horizontal ? y + i : y][horizontal ? x + i : x].state != SquareState.WATER|| cells[!horizontal ? y + i : y][horizontal ? x + i : x] instanceof ShipCell){
                throw new IllegalArgumentException("cell already occuped");
            }

        }
        for (int i = 0; i < ship.getLength(); i++){
            cells[!horizontal ? y + i : y][horizontal ? x + i : x] = new ShipCell(ship);
        }
	    ships.add(ship);

	}

	public boolean removeAllShips(){
	    initalisizeCells();
        ships = new ArrayList<>();
        return true;
    }

    private void initalisizeCells(){
	    if(cells != null){
	        initalisizeCells(cells.length);
        }
    }
    private void initalisizeCells(int size){
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                cells[i][j] = new Cell();
            }
        }
    }

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public ShotType shoot(Integer x, Integer y) {
	    SquareState state = cells[y][x].hit();
	    switch (state){
            case WATER:
                return ShotType.MISSED;
            case SHOTMISSED:
                return ShotType.MISSED;
            case SHOTHIT:
                return ShotType.HIT;
            case SHIPSUNK:
                return ShotType.SUNK;
            default:
                return ShotType.MISSED;
        }
	}

	public boolean placeShipsAutomatically() {
        Random rand = new Random();
        removeAllShips();

        try {
            placeShip(new Minesweeper(), rand.nextInt(10 - 2), rand.nextInt(10 - 2) ,rand.nextBoolean());
            placeShip(new Battleship(), rand.nextInt(10 - 4), rand.nextInt(10 - 4), rand.nextBoolean());
            placeShip(new Cruiser(), rand.nextInt(10 - 3), rand.nextInt(10 - 3), rand.nextBoolean());
            placeShip(new AircraftCarrier(), rand.nextInt(10 - 5), rand.nextInt(10 - 5), rand.nextBoolean());
            placeShip(new Submarine(), rand.nextInt(10 - 3), rand.nextInt(10 - 3), rand.nextBoolean());
        }

        catch (Exception e) {
            removeAllShips();
            placeShipsAutomatically();
        }
        return true;
	}
}