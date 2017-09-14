package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = -9100426103735670345L;
	
	//These change the size of the board
	private final int BOARDWIDTH = 7;
	private final int BOARDHEIGHT = 6;

	private JPanel gameBoard = new JPanel(new GridLayout(BOARDHEIGHT+1,BOARDWIDTH));
	private JPanel[][] panels = new JPanel[BOARDHEIGHT][BOARDWIDTH];
	private JButton[] turnButtons = new JButton[BOARDWIDTH];
	
	
	/**
	 * 
	 */
	public GamePanel() {
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
		for (int i = 0; i < BOARDHEIGHT+1; i++) {
			for (int j = 0; j < BOARDWIDTH; j++) {
				
				if(i != 0) {
					panels[i-1][j] = new JPanel(new BorderLayout());
					panels[i-1][j].setBackground(Color.GRAY);
					panels[i-1][j].setBorder(BorderFactory.createEtchedBorder());
					gameBoard.add(panels[i-1][j]);
				} else {
					turnButtons[j] = new JButton(":^)");
					gameBoard.add(turnButtons[j]);
				}
				
			}
		}
	}
}
