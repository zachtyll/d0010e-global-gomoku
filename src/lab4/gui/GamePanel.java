package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{
	private final int UNIT_SIZE = 20;
	private final int GRID_WIDTH = 1;
	private GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		int[] gridPosition = {(x/UNIT_SIZE), (y/UNIT_SIZE)};
		return gridPosition;
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int board_width = grid.getSize() * UNIT_SIZE + grid.getSize();
		int board_height = grid.getSize() * UNIT_SIZE + grid.getSize();

		g.setColor(Color.WHITE);
		g.fillRect(0,0, board_width, board_height);

		// Create Grid Horizontal Lines.
        g.setColor(Color.BLACK);
		g.drawRect(0,0, board_width, board_height);
        for (int i = 0; i < board_width; i = i + UNIT_SIZE) {
            g.drawLine(0, i, board_width, i);
        }
		// Create Grid Vertical Lines.
        for (int i = 0; i < board_height; i = i + UNIT_SIZE) {
            g.drawLine(i, 0, i, board_height);
        }

        // Draw Game Pieces.
        for (int i = 0; i < grid.getSize(); i++) {
        	for (int j = 0; j < grid.getSize(); j++) {
        		if (grid.getLocation(i, j) == grid.ME) {
					g.setColor(Color.BLUE);
        			g.fillRect(i * UNIT_SIZE + GRID_WIDTH,j * UNIT_SIZE + GRID_WIDTH, UNIT_SIZE - + GRID_WIDTH, UNIT_SIZE - + GRID_WIDTH);
				}
        		else if (grid.getLocation(i, j) == grid.OTHER) {
					g.setColor(Color.RED);
					g.fillRect(i * UNIT_SIZE + GRID_WIDTH,j * UNIT_SIZE + GRID_WIDTH, UNIT_SIZE - GRID_WIDTH, UNIT_SIZE - GRID_WIDTH);
				}

			}
		}
	}
	
}
