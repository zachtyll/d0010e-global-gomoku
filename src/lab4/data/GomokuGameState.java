/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private int currentState;
	private String message;

    // Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHERS_TURN = 2;
	private final int FINISHED = 3;

	private GameGrid gameGrid;
	private GomokuClient client;

	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){ return message; }
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		if (currentState == MY_TURN) {
			if (gameGrid.move(x,y,gameGrid.ME)) {
				gameGrid.move(x,y,gameGrid.ME);
				client.sendMoveMessage(x, y);
				if (gameGrid.isWinner(gameGrid.ME)) {
					currentState = FINISHED;
					message = "You are the winner!";
				} else {
					currentState = OTHERS_TURN;
					message = "Other players turn.";
				}
				setChanged();
				notifyObservers();
			} else {
				message = "This square is already occupied!";
			}
		} else {
			message = "It is not your turn!";
		}
	}
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){
		if (client.getConnectionStatus() == GomokuClient.UNCONNECTED) {
			message = "You are not connected!";
		} else {
			gameGrid.clearGrid();
			currentState = OTHERS_TURN;
			message = "Other players turn.";
			client.sendNewGameMessage();
		}
			setChanged();
			notifyObservers();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "It is your turn.";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "Other player has disconnected from the game.";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		if (client.getConnectionStatus() == GomokuClient.UNCONNECTED) {
			message = "You are not connected!";
		} else {
			gameGrid.clearGrid();
			currentState = NOT_STARTED;
			client.disconnect();
			message = "You have disconnected from the game.";
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		gameGrid.move(x, y, gameGrid.OTHER);
		if (gameGrid.isWinner(gameGrid.OTHER)) {
			message = "You have lost!";
			currentState = FINISHED;
		} else {
			currentState = MY_TURN;
			message = "It is your turn.";
		}
		setChanged();
		notifyObservers();
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHERS_TURN;
			break;
		}
		setChanged();
		notifyObservers();
		
		
	}
	
}
