package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.*;
import game.Board;


/**
 * Frame object intended to be part of the client side of the Connect Four game.<br>
 * The user would play the actual game in a PlayerFrame and be sending turns to the server.
 * @author Daniel McCann
 *
 */
public class PlayerFrame extends JFrame{
	private static final long serialVersionUID = -5683163455384492681L;
	
	public static final char RED = 'R';
	public static final char BLUE= 'B';
	private char player;
	
	private JPanel wrapper 		= new JPanel(new GridBagLayout());
	private MenuPanel menuPanel = new MenuPanel();
	private GamePanel gamePanel;
	
	//private Socket socket;
	private Board board;
	
	
	/**
	 * Constructor for the window to be used as the game area for Connect Four.<br>
	 * Sets bounds for window and constructs the <i>MenuPanel</i> and <i>GamePanel</i>
	 * @param s String value to set the title of the window.
	 * @param socket 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public PlayerFrame(String s, char player) {
		super(s + " -- " + player);
		
		board = new Board(7,6);
		//this.socket = socket;
		this.player = player;
		gamePanel = new GamePanel(7,6);//Set the width/height to 7x6
		disableButtons();
		createTurnListeners();
		
		setLayout(new BorderLayout());
		add(wrapper, BorderLayout.CENTER);
		
		
		
		
		//Doing things like setting the window size and making its size static
		setBounds(200, 200, 700, 400);
		setResizable(false);

		//Listener defines what happens when the application is closed
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//TODO: add closing operations as needed, in this area
				System.exit(0);
			}
		});
		
		
		//set GridBagConstraints for the wrapper panel
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.8;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		//Begin adding to wrapper
		wrapper.add(gamePanel, c);
		c.gridx++;
		c.weightx = 0.2;
		wrapper.add(menuPanel, c);	//adding the game UI and the menu to the wrapper panel
	}
	
	
	
	/**
	 * Method for creating the listeners for the turn buttons
	 */
	private void createTurnListeners() {
		//Listeners for the array of buttons
		for (int i = 0; i < gamePanel.getButtons().length; i++) {
			gamePanel.getButtons()[i].addActionListener(new TurnListener(i));
		}
	}
	
	
	//This method will make decide if a redTurn or blueTurn should be made
	//then it executes the updateBoard method, so the gui is correct
	private synchronized void makeTurn(int column) {
		
		
		if(player == BLUE) {
			if(board.checkWin(board.blueTurn(column), column)) {
				JOptionPane.showMessageDialog(this, "Blue wins!");
			}
		} else if (player == RED) {
			if(board.checkWin(board.redTurn(column), column)) {
				JOptionPane.showMessageDialog(this, "Red wins!");
			}
		}
		updateBoard();
		
		//This is for preventing the player who isnt taking a turn
		//from pushing buttons
		//disableButtons();
	}
	
	
	/**
	 * Method that takes the logical board and updates the graphical board with it
	 */
	private void updateBoard() {
		
		gamePanel.updatePanels(board);
		System.out.println("Board updated");
	}
	
	
	
	private void disableButtons() {
		for (int i = 0; i < gamePanel.getButtons().length; i++) {
			gamePanel.getButtons()[i].setEnabled(false);
		}
	}
	
	public void enableButtons() {
		for (int i = 0; i < gamePanel.getButtons().length; i++) {
			gamePanel.getButtons()[i].setEnabled(true);
		}
	}
	
	
	
	private class TurnListener implements ActionListener{
		
		private int column;
		
		public TurnListener(int column) {
			this.column = column;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			makeTurn(column);
		}
		
	}
}
