package lab4.data;

import java.util.Arrays;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{
	private int[][] grid;
	private int INROW = 5;
	public static final int OTHER = -1;
	public static final int EMPTY = 0;
	public static final int ME = 1;


	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		this.grid = new int[size][size];
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return this.grid[x][y];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){ return this.grid.length; }
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player the player that tries to occupy the square
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		if (getLocation(x, y) == EMPTY) {
			this.grid[x][y] = player;
			this.hasChanged();
			this.notifyObservers();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		for (int i = 0; i < getSize(); i ++) {
			Arrays.fill(this.grid[i], EMPTY);
		}
		this.hasChanged();
		this.notifyObservers();
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		int consecutive = 0;
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				if (consecutive == INROW) {
					return true;
				}
				if (this.grid[i][j] == player) {
					consecutive++;
				} else {
					consecutive = 0;
				}
			}
		}
		return false;
	}
}
