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
		int width = grid.getSize() * UNIT_SIZE + grid.getSize() - 1;
		int height = grid.getSize() * UNIT_SIZE + grid.getSize() - 1;

		g.setColor(Color.WHITE);
		g.fillRect(0,0, width, height);

        g.setColor(Color.BLACK);
		g.drawRect(0,0, width, height);
        for (int i = 0; i < width; i = i + UNIT_SIZE) {
            g.drawLine(0, i, width, i);
        }
        for (int i = 0; i < height; i = i + UNIT_SIZE) {
            g.drawLine(i, 0, i, height);
        }
	}
	
}
