package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import application.InputListener;
import game.Message;
import game.Board;


/**
 * Frame object intended to be part of the client side of the Connect Four game.<br>
 * The user would play the actual game in a PlayerFrame and be sending turns to the server.
 * @author Daniel McCann
 *
 */
public class PlayerFrame extends JFrame implements Observer {
	private static final long serialVersionUID = -5683163455384492681L;
	
	private boolean gameWon = false;
	
	public static final String DISCONNECT_MSG = "Other player disconnected\nLooking for next player...\n\n";
	
	private JPanel wrapper 		= new JPanel(new GridBagLayout());
	private MenuPanel menuPanel = new MenuPanel();
	private GamePanel gamePanel;
	
	private Thread th;
	private Socket socket;
	private String addr;
	private ObjectOutputStream oos;
	
	//private Socket socket;
	private Board board;
	
	
	/**
	 * Constructor for the window to be used as the game area for Connect Four.<br>
	 * Sets bounds for window and constructs the <i>MenuPanel</i> and <i>GamePanel</i>
	 * @param title title is the String value to set the title of the window.
	 * @param socket socket represents the socket connected to the server
	 * @param boardWidth boardWidth is an integer value representing the width of the game board
	 * @param boardHeight boardHeight is an integer value representing the height of the game board
	 */
	public PlayerFrame(String title, Socket socket, int boardWidth, int boardHeight) {
		super(title);
		
		this.addr = title;
		this.socket = socket;
		
		board = new Board(boardWidth,boardHeight);
		gamePanel = new GamePanel(boardWidth,boardHeight);//Set the width/height to 7x6
		createTurnListeners();
		createChatListeners();
		
		
		try
		{
			oos = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException e1) {e1.printStackTrace();}
		
		
		setLayout(new BorderLayout());
		add(wrapper, BorderLayout.CENTER);
		
		
		
		
		//Doing things like setting the window size and making its size static
		setBounds(200, 200, 700, 400);
		setResizable(false);

		//Listener defines what happens when the application is closed
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try
				{
					oos.writeObject(DISCONNECT_MSG);
				} catch (IOException e1) {e1.printStackTrace();}
				disconnect();
				System.exit(0);//Close game
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
		
		
		th = new Thread(new InputListener(socket, this));
		th.start();//Start listening
	}
	
	/**
	 * Method for disconnecting the client from the server. Used to either reset the connection, or close the game
	 */
	private void disconnect() {
		try
		{
			oos.close();
			oos = null;
			socket.close();	
			socket = null;
			th.interrupt();
			th = null;
			
		} catch (IOException e) {e.printStackTrace();}
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
	
	private void createChatListeners() {
		//create Listener for the Send button
		menuPanel.getChatButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();
			}
		});
		
		//listener for chat box
		menuPanel.getChatField().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
			
		});
	}
	
	/**
	 * Sends a Message object to the other player. Prevents users from just sending each other plain strings, which could cause a player to disconnect.
	 */
	private void sendMessage() {
		if(!menuPanel.getChatField().getText().equals("")) {
			Message m = new Message(menuPanel.getChatField().getText(), menuPanel.getUserName());
			
			menuPanel.getChatArea().append(m.toString());
			menuPanel.getChatField().setText("");
			
			try
			{
				oos.writeObject(m);
			}
			catch (IOException e) {e.printStackTrace();}
		}
		
	}
	
	/**
	 * Sends a turn to the other player, in the form of an Integer object, and updates the GUI
	 * @param column column represents the column that the piece is being dropped into.
	 */
	private void makeTurn(int column) {
		int row = board.myTurn(column);
		
		if (row == -10) {
			menuPanel.getChatArea().append("That column is invalid!\n");
			return;
		}
		
		gameWon = board.checkWin(row, column);
		
		updateBoard();
		disableButtons();
		try
		{
			oos.writeObject(new Integer(column));
		
		
			if (gameWon) {
				oos.writeObject(board.toString());
				if (JOptionPane.showConfirmDialog(this, "You win!\nPlay again?") == JOptionPane.YES_OPTION) {
					requeue();
				} else {
					disconnect();
					System.exit(0);
				}
			}
		} catch (IOException e) {e.printStackTrace();}
	}
	
	
	/**
	 * Method that takes the logical board and updates the graphical board with it
	 */
	private void updateBoard() {
		gamePanel.updatePanels(board);
	}
	
	
	/**
	 * Disables all buttons except the chat send button. Prevents users from taking their turn twice.
	 */
	private void disableButtons() {
		for (int i = 0; i < gamePanel.getButtons().length; i++) {
			gamePanel.getButtons()[i].setEnabled(false);
		}
	}
	
	/**
	 * Undoes disableButtons. Happens after the other player has sent their turn.
	 */
	public void enableButtons() {
		for (int i = 0; i < gamePanel.getButtons().length; i++) {
			gamePanel.getButtons()[i].setEnabled(true);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Message) {//Message received
			Message m = (Message) arg;
			menuPanel.getChatArea().append(m.toString());
		}
		//Enemy turn sent.
		//checks if its a winning turn. if it is, then ask to replay
		else if (arg instanceof Integer) {
			if (board.checkWin(board.theirTurn((int)arg), (int)arg)) {
				gamePanel.updatePanels(board);
				
				if (JOptionPane.showConfirmDialog(this, "You lost.\nTry again?") == JOptionPane.YES_OPTION) {
					requeue();
				}
				else {
					disconnect();
					System.exit(0);
				}
				
			} else {
				gamePanel.updatePanels(board);
				enableButtons();
			}
		} else if (arg instanceof String) {
			//Message from listener
			//Makes sure the user knows what the listener is telling them
			menuPanel.getChatArea().append((String)arg);
			
			if (((String)arg).equals(DISCONNECT_MSG)) {//other player has disconnected
				requeue();
			}
			
		}
		
	}
	
	/**
	 * Method for playing another game after either the game ended, or the other player left.
	 */
	private void requeue() {
		try
		{
			disconnect();//First we disconnect from the server
			board.resetBoard();
			updateBoard();
			socket = new Socket(addr, 2282);//Then we enter the server again
			th = new Thread(new InputListener(socket, this));
			th.start();
			oos = new ObjectOutputStream(socket.getOutputStream());
			enableButtons();
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	
	/**
	 * Private class used only for the turnButtons, as there can be a variable number of them, a listener for them had to take their index in the array as an argument to tell them apart.
	 * 
	 * @author Daniel McCann
	 *
	 */
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
