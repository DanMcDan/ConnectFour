package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import application.InputListener;
import chat.Message;
import game.Board;


/**
 * Frame object intended to be part of the client side of the Connect Four game.<br>
 * The user would play the actual game in a PlayerFrame and be sending turns to the server.
 * @author Daniel McCann
 *
 */
public class PlayerFrame extends JFrame implements Observer {
	private static final long serialVersionUID = -5683163455384492681L;
	
	public static final char RED = 'R';
	public static final char BLUE= 'B';
	
	
	private JPanel wrapper 		= new JPanel(new GridBagLayout());
	private MenuPanel menuPanel = new MenuPanel();
	private GamePanel gamePanel;
	
	private Socket socket;
	private InputListener il;
	private ObjectOutputStream oos;
	
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
	public PlayerFrame(String title, Socket socket, char player, int boardWidth, int boardHeight) {
		super(title);
		
		board = new Board(boardWidth,boardHeight);
		this.socket = socket;
		gamePanel = new GamePanel(boardWidth,boardHeight);//Set the width/height to 7x6
		createTurnListeners();
		createChatListeners();
		
		
		this.socket = socket;
		il = new InputListener(this.socket, 'P', this);
		
		try
		{
			oos = new ObjectOutputStream(this.socket.getOutputStream());
		}
		
		catch (IOException e1) {e1.printStackTrace();}
		Thread th = new Thread(il);
		th.start();//Start listening
		
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
	
	
	private void sendMessage() {
		if(!menuPanel.getChatField().getText().equals("")) {
			Message m = new Message(menuPanel.getChatField().getText(), menuPanel.getUserName());
			// TODO: send message to other player
			try
			{
				oos.writeObject(m);
			}
			catch (IOException e) {e.printStackTrace();}
			
			menuPanel.getChatArea().append(m.toString());
			menuPanel.getChatField().setText("");
		}
		
	}
	
	
	//This method will make decide if a redTurn or blueTurn should be made
	//then it executes the updateBoard method, so the gui is correct
	private void makeTurn(int column) {
		
		
		if (board.checkWin(board.myTurn(column), column)) {
			updateBoard();
			JOptionPane.showMessageDialog(this, "You win!");
		}
		
			
		updateBoard();
		disableButtons();
		try
		{
			oos.writeObject(new Integer(column));
		} catch (IOException e) {e.printStackTrace();}
		
		//This is for preventing the player who isn't taking a turn
		//from pushing buttons
		
	}
	
	
	/**
	 * Method that takes the logical board and updates the graphical board with it
	 */
	private void updateBoard() {
		gamePanel.updatePanels(board);
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

	@Override
	public void update(Observable o, Object arg) {
		
		
		
		if (arg instanceof Message) {
			System.out.println("Messaged");
			Message m = (Message) arg;
			menuPanel.getChatArea().append(m.toString());
		}
		else if (arg instanceof Integer) {
			System.out.println("Turn taken");
			board.theirTurn((int)arg);
			gamePanel.updatePanels(board);
			enableButtons();
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
