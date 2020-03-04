package lab4.data;

import java.util.Arrays;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{
	private int[][] grid;
	private int INROW = 5;
	public final int OTHER = -1;
	public final int EMPTY = 0;
	public final int ME = 1;


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
			this.setChanged();
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
		int consecutive_row = 0;
		int consecutive_col = 0;
		int consecutive_dgnl_left_x = 0;
		int consecutive_dgnl_left_y = 0;
		int consecutive_dgnl_right_x = 0;
		int consecutive_dgnl_right_y = 0;
		int boardArrayLength = getSize() - 1;

		for (int i = 0; i <= boardArrayLength; i++) {
			for (int j = 0; j <= boardArrayLength; j++) {
				// Check Rows.
				if (consecutive_row == INROW) {
					return true;
				}
				else if (getLocation(j, i) == player) {
					consecutive_row++;
				} else {
					consecutive_row = 0;
				}
				// Check Columns.
				if (consecutive_col == INROW) {
					return true;
				}
				if (getLocation(i, j) == player) {
					consecutive_col++;
				} else {
					consecutive_col = 0;
				}
				// Check Diagonals going right.
				if (consecutive_dgnl_right_x == INROW || consecutive_dgnl_right_y == INROW) {
					return true;
				} else if (i + j <= boardArrayLength)  {
					if (getLocation(i + j, j) == player) {
						consecutive_dgnl_right_x++;
					} else if (getLocation(j, i + j) == player) {
						consecutive_dgnl_right_y++;
					}
				} else {
					consecutive_dgnl_right_x = 0;
					consecutive_dgnl_right_y = 0;
				}
				// Check Diagonals going left.
				if (consecutive_dgnl_left_x == INROW || consecutive_dgnl_left_y == INROW) {
					return true;
				} else if (j - i >= 0) {
					if (getLocation(boardArrayLength - (i + (j - i)), j - i) == player) {
						consecutive_dgnl_left_x++;
					} else if (getLocation(j - i, boardArrayLength - (i + (j - i))) == player) {
						consecutive_dgnl_left_y++;
					}
				} else {
					consecutive_dgnl_left_x = 0;
					consecutive_dgnl_left_y = 0;
				}
			}
		}
		return false;
	}
}
