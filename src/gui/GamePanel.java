package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import game.Board;

class GamePanel extends JPanel{
	private static final long serialVersionUID = -9100426103735670345L;
	
	//These change the size of the board
	private int boardWidth;
	private int boardHeight;
	
//	private Board board = new Board(7,6);

	private JPanel gameBoard;
	private JPanel[][] panels;
	private JButton[] turnButtons;
	
	
	/**
	 * Constructor for the GamePanel<br>
	 * Contains the UI for the actual game within it.
	 * 
	 * @param BOARDWIDTH Integer representing the width of the board
	 * @param BOARDHEIGHT Integer representing the height of the board
	 */
	public GamePanel(int boardWidth, int boardHeight) {
		this.boardHeight = boardHeight;
		this.boardWidth = boardWidth;
		
		gameBoard = new JPanel(new GridLayout(boardHeight+1,boardWidth));
		panels = new JPanel[boardHeight][boardWidth];
		turnButtons = new JButton[boardWidth];
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		constructGameBoard();
		add(gameBoard, BorderLayout.CENTER);
	}
	
	
	/**
	 * Method that creates the graphical game board<br>
	 * instantiates the buttons and panels, and places them in a grid, such that the first row is all buttons
	 */
	private void constructGameBoard() {
		for (int i = 0; i < boardHeight+1; i++) {
			for (int j = 0; j < boardWidth; j++) {
				
				if(i != 0) {
					panels[i-1][j] = new JPanel(new BorderLayout());
					panels[i-1][j].setBackground(Color.GRAY);
					panels[i-1][j].setBorder(BorderFactory.createEtchedBorder());
					gameBoard.add(panels[i-1][j]);
				} else {
					turnButtons[j] = new JButton(Integer.toString(j));
					gameBoard.add(turnButtons[j]);
				}
				
			}
		}
	}
	
	public void updatePanels(Board board) {
		for (int i = 0; i < panels.length; i++) {
			for (int j = 0; j < panels[i].length; j++) {
				if (board.getMap()[i][j] == Board.THEIR_PIECE)
					panels[i][j].setBackground(Color.RED);
				
				else if (board.getMap()[i][j] == Board.MY_PIECE)
					panels[i][j].setBackground(Color.BLUE);
			}
		}
	}
	
	public JButton[] getButtons() {
		return turnButtons;
	}
	public JPanel[][] getPanels() {
		return panels;
	}
	
	
}
