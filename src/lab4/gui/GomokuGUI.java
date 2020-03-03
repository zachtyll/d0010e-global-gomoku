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
	private JLabel messageLabel;
	private JPanel buttonPanel;
	private JPanel boardPanel;

	private final int width;
	private final int height;
	private GamePanel gameGridPanel;

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

		gameGridPanel = new GamePanel(g.getGameGrid());
		width = gamestate.getGameGrid().getSize() * gameGridPanel.UNIT_SIZE;
		height = gamestate.getGameGrid().getSize() * gameGridPanel.UNIT_SIZE;

		// Setup main frame.
		mainFrame = new JFrame("Global Gomoku");
		mainFrame.setSize(450, 450);
		mainFrame.setLayout(new GridLayout(3, 1, 10, 10));


		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.out.println("Exiting Global Gomoku.");
				System.exit(0);
			}
		});

		Container contentPanel = mainFrame.getContentPane();
		GroupLayout groupLayout = new GroupLayout(contentPanel);
		contentPanel.setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);

		// Setup labels.
		messageLabel = new JLabel("messageLabel", JLabel.CENTER);
		messageLabel.setBorder(BorderFactory.createLineBorder(Color.black));

		// Setup Board panel.
		boardPanel = new JPanel();
		boardPanel.setLayout(new FlowLayout());
		boardPanel.setSize(width, height);
		boardPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		// Setup buttons panel.
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		boardPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		// Setup Board
		gameGridPanel.setSize(width, height);

		gameGridPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				messageLabel.setText("x: " + e.getX() + "y: " + e.getY());
			}
		});

		JButton connectButton = new JButton("connectButton");
		JButton newGameButton = new JButton("newGameButton");
		JButton disconnectButton = new JButton("disconnectButton");

		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageLabel.setText("connectButton clicked.");
			}
		});
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageLabel.setText("newGameButton clicked.");
			}
		});
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageLabel.setText("disconnectButton clicked.");
			}
		});

		GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();

		hGroup.addGroup(groupLayout.createParallelGroup().
				addComponent(boardPanel).addComponent(buttonPanel).addComponent(messageLabel));
		groupLayout.setHorizontalGroup(hGroup);

		vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(boardPanel));
		vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(buttonPanel));
		vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(messageLabel));
		groupLayout.setVerticalGroup(vGroup);


		mainFrame.setVisible(true);
	}
	
	public void update(Observable arg0, Object arg1) {
//		// Setup Board
//		gameGridPanel.setSize(width, height);
//
//		gameGridPanel.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				messageLabel.setText("x: " + e.getX() + "y: " + e.getY());
//			}
//		});
//
//		JButton connectButton = new JButton("connectButton");
//		JButton newGameButton = new JButton("newGameButton");
//		JButton disconnectButton = new JButton("disconnectButton");
//
//		connectButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				messageLabel.setText("connectButton clicked.");
//			}
//		});
//		newGameButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				messageLabel.setText("newGameButton clicked.");
//			}
//		});
//		disconnectButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				messageLabel.setText("disconnectButton clicked.");
//			}
//		});

		boardPanel.add(gameGridPanel);

		buttonPanel.add(connectButton);
		buttonPanel.add(newGameButton);
		buttonPanel.add(disconnectButton);
		buttonPanel.add(messageLabel);

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
