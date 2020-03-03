package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
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

	private JButton connectButton;
	private JButton newGameButton;
	private JButton disconnectButton;

	private GamePanel gameGridPanel;

	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(final GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);

		// Setup main frame.
		mainFrame = new JFrame("Global Gomoku");
		mainFrame.setSize(450, 450);
		mainFrame.setLayout(new GridLayout(3, 1));


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

		// Setup Buttons.
		connectButton = new JButton("connectButton");
		newGameButton = new JButton("newGameButton");
		disconnectButton = new JButton("disconnectButton");

		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageLabel.setText("connectButton clicked.");
				ConnectionWindow connectionWindow = new ConnectionWindow(client);
			}
		});
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageLabel.setText("newGameButton clicked.");
				gamestate.newGame();
			}
		});
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageLabel.setText("disconnectButton clicked.");
				gamestate.disconnect();
			}
		});

		// Setup label.
		messageLabel = new JLabel("messageLabel", JLabel.CENTER);

		// Setup Board.
		gameGridPanel = new GamePanel(g.getGameGrid());

		// Setup Button Panel.
		buttonPanel = new JPanel();
		buttonPanel.add(connectButton);
		buttonPanel.add(newGameButton);
		buttonPanel.add(disconnectButton);
		buttonPanel.setLayout(new FlowLayout());

		// Setup Board Panel.
		boardPanel = new JPanel();
		boardPanel.add(gameGridPanel);
		boardPanel.setLayout(new FlowLayout());

		gameGridPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				messageLabel.setText("x: " + e.getX() + "y: " + e.getY());
				int x = e.getX();
				int y = e.getY();
				int[] gridPosition = gameGridPanel.getGridPosition(x, y);
				g.move(gridPosition[0], gridPosition[1]);

			}
		});

		// GUI Layout.
		GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();

		hGroup.addGroup(groupLayout.createParallelGroup().
				addComponent(boardPanel).
				addComponent(buttonPanel).
				addComponent(messageLabel));
		groupLayout.setHorizontalGroup(hGroup);

		vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(boardPanel));
		vGroup.addGroup(groupLayout.createParallelGroup().
				addComponent(buttonPanel));
		vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(messageLabel));
		groupLayout.setVerticalGroup(vGroup);

		mainFrame.setVisible(true);
	}
	
	public void update(Observable arg0, Object arg1) {

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
