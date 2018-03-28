package seabattlegame.game;
import seabattlegame.game.cells.Cell;
import seabattlegame.game.cells.ShipCell;
import seabattlegame.game.ships.*;

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

    public boolean allShipsDead(){
	    boolean allDead = false;
        for (Ship ship : ships) {
            if(ship.isSunk()){
                allDead = true;
            }
        }
        return allDead;
    }

    /**
	 * 
	 * @param ship
	 */
	public void placeShip(Ship ship, int x, int y, boolean horizontal) throws IllegalArgumentException {
	    if(ship == null) {
	        throw new IllegalArgumentException("Passed ship cannot be null.");
        }
        for (Ship ship1 : ships) {
            if(ship.getType() == ship1.getType()){
                throw new IllegalArgumentException("Tried placing a ship of a type that is already placed.");
            }
        }
	    if(x < 0 || y < 0 || x > getCells().length || y > getCells().length){
	        throw new IllegalArgumentException("One or more of the given coordinates is out of bounds.");
        }

        if (horizontal && x + ship.getLength() > getCells().length) {
            throw new IllegalArgumentException("Attempted to extend the ship past the grid horizontally.");
        }else if (y + ship.getLength() > getCells().length && !horizontal){
            throw new IllegalArgumentException("Attempted to extend the ship past the grid vertically.");
        }

        for (int i = 0; i < ship.getLength(); i++){
            if(cells[!horizontal ? y + i : y][horizontal ? x + i : x].getState() != SquareState.WATER|| cells[!horizontal ? y + i : y][horizontal ? x + i : x] instanceof ShipCell){
                throw new IllegalArgumentException("Tried placing a ship on a cell that is already occupied by another ship.");
            }

        }
        for (int i = 0; i < ship.getLength(); i++){
            cells[!horizontal ? y + i : y][horizontal ? x + i : x] = new ShipCell(ship);
        }
	    ships.add(ship);
	}

	public boolean removeShip(int x, int y){
        if(x < 0 || y < 0 || x > getCells().length || y > getCells().length){
            throw new IllegalArgumentException("One or more of the given coordinates is out of bounds.");
        }
        return removeShip(((ShipCell)cells[y][x]).getShip());
    }

	public boolean removeShip(Ship ship){
	    if(ship == null){
	        throw new IllegalArgumentException("Ship can't be null");
        }

        if(!this.ships.contains(ship)){
	        return false;
        }

        for (int i = 0; i < this.cells.length; i++){
            for (int j = 0; j < this.cells.length; j++){
                if(cells[i][j] instanceof ShipCell && ((ShipCell)cells[i][j]).getShip().getType() == ship.getType()){
                    cells[i][j] = new Cell();
                }
            }
        }
        return true;
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
	public ShotType shoot(int x, int y) {
	    if(x < 0 || x > this.cells.length || y < 0 || y > this.cells.length){
	        throw new IllegalArgumentException("One or more of the given coordinates is out of bounds");
        }
	    SquareState state = cells[y][x].hit();
	    if(state == SquareState.SHIPSUNK){
	        setAllCellsShipSunk(x, y);

        }
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

	private void setAllCellsShipSunk(int x, int y){
        ShipCell cell = (ShipCell) cells[y][x];
        Ship ship = cell.getShip();

        for (int i = 0; i < cells.length; i++){
            for (int j = 0; j < cells.length; j++){
                if((cells[i][j] instanceof ShipCell) &&
                        ship.getType() == ((ShipCell)cells[i][j]).getShip().getType()){
                    ((ShipCell)cells[i][j]).checkShipSunk();
                }
            }
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