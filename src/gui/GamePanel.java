package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import game.Board;
import game.Player;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = -9100426103735670345L;
	
	//These change the size of the board
	private int boardWidth = 7;
	private int boardHeight = 6;
	
	private Board board = new Board(7,6);

	private JPanel gameBoard = new JPanel(new GridLayout(boardHeight+1,boardWidth));
	private JPanel[][] panels = new JPanel[boardHeight][boardWidth];
	private JButton[] turnButtons = new JButton[boardWidth];
	
	
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
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		constructGameBoard();
		add(gameBoard, BorderLayout.CENTER);
		
		/*
		 * TODO: make the logical board, and figure out how to connect it to the graphical board
		 */
		
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
		
		//Listeners for the array of buttons
		for (int i = 0; i < turnButtons.length; i++) {
					turnButtons[i].addActionListener(new TurnListener(i));
		}
	}
	
	public JButton[] getButtons() {
		return turnButtons;
	}
	
	private class TurnListener implements ActionListener{
		
		private int column;
		
		public TurnListener(int column) {
			this.column = column;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Button: "+column);
			
			
			
			
		}
		
	}
}
