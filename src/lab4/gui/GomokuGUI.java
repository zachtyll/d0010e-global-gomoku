package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JLabel messageLabel;
	private JPanel buttonPanel;
	private JPanel boardPanel;
	private JPanel controlPanel;

	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);

		// Setup main frame.
		mainFrame = new JFrame("Global Gomoku");
		mainFrame.setSize(1000,1000);
		mainFrame.setLayout(new GridLayout(3, 1));

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.out.println("Exiting Global Gomoku.");
				System.exit(0);
			}
		});

		// Setup labels.
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		statusLabel.setSize(350,100);
		messageLabel = new JLabel("messageLabel", JLabel.CENTER);

		// Setup Board panel.
		boardPanel = new JPanel();
		boardPanel.setLayout(new FlowLayout());

		// Setup buttons panel.
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		// Setup grouping panel
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

		// Add components.
//		mainFrame.add(gameGridPanel);
		mainFrame.add(controlPanel);
		mainFrame.add(headerLabel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	}
	
	public void update(Observable arg0, Object arg1) {
		// Setup Board
		GamePanel gameGridPanel = new GamePanel(gamestate.getGameGrid());
		int width = gamestate.getGameGrid().getSize() * gameGridPanel.UNIT_SIZE;
		int height = gamestate.getGameGrid().getSize() * gameGridPanel.UNIT_SIZE;
		gameGridPanel.setSize(width, height);

		gameGridPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				statusLabel.setText("x: " + e.getX() + "y: " + e.getY());
			}
		});

		JButton connectButton = new JButton("connectButton");
		JButton newGameButton = new JButton("newGameButton");
		JButton disconnectButton = new JButton("disconnectButton");

		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statusLabel.setText("connectButton clicked.");
			}
		});
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageLabel.setText("newGameButton clicked.");
			}
		});
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statusLabel.setText("disconnectButton clicked.");
			}
		});

		boardPanel.add(gameGridPanel);

		buttonPanel.add(connectButton);
		buttonPanel.add(newGameButton);
		buttonPanel.add(disconnectButton);

		controlPanel.add(boardPanel);
		controlPanel.add(buttonPanel);
		controlPanel.add(messageLabel);

		mainFrame.setVisible(true);

		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}
