package game;

/**
 * Board class containing a "map" of all the pieces involved in a game of Connect Four
 * 
 * @author Daniel McCann
 *
 */
public class Board {
	
	private final char RED_PIECE = 'X';
	private final char BLUE_PIECE = 'O';
	private final char EMPTY_PIECE = '*';

	private char map[][];
	
	/**
	 * Constructor for the game board
	 * 
	 * @param boardWidth takes the intended width of the game board
	 * @param boardHeight takes the intended height of the game board
	 */
	public Board(int boardWidth, int boardHeight) {
		map = new char[boardHeight][boardWidth];
		
		
		for(int i = 0; i < map.length; i++)
			for(int j = 0; j < map[i].length; j++)
				map[i][j] = EMPTY_PIECE;
	}
	
	/**
	 * Public accessor for the map
	 * @return Returns a character array representing the game board
	 */
	public char[][] getMap() {
		return map;
	}
	
	@Override
	public String toString() {
		String out = "";
		
		//Begin add map contents to the output string
		for(int i = 0; i < map.length; i++) {
			if (i != 0) out += "\n";
			for(int j = 0; j < map[i].length; j++)	
				out += (j == map[i].length-1 ? map[i][j]:map[i][j]+" ");
		}
		//end
		
		
		return out;
	}
	
	/**
	 * Method for initiating the red players turn.
	 * @param 	col col recieves an index for the column number.<br>
	 * 			The piece will seek the bottom most row of the column, where no pieces already are
	 * 
	 * @return 	Returns -1 if the insert turn was unsuccessful<br>
	 * 			Returns 0 if the column is full of other pieces<br>
	 * 			Returns the row that the piece was placed in if column was valid
	 */
	public int redTurn(int col) {
		if (map[0][col] != EMPTY_PIECE) return 0;//This return is just for consistency with checkWin 
	
		//Loops through and finds where the piece should be placed
		for (int i = 0; i <= map.length - 1; i++) {
			if (i == map.length-1 || map[i+1][col] != EMPTY_PIECE) {
				map[i][col] = RED_PIECE;
				return i;//NOTE: This returns the row that the spot was found in
			}			 //		 when using checkWin, the column must be used with this return value
		}
		
		return -1;
	}
	
	/**
	 * Method for initiating the blue players turn.
	 * @param 	col col recieves an index for the column number.<br>
	 * 			The piece will seek the bottom most row of the column, where no pieces already are
	 * 
	 * @return 	Returns -1 if the insert turn was unsuccessful<br>
	 * 			Returns -10 if the column is full of other pieces<br>
	 * 			Returns the row that the piece was placed in if column was valid
	 */
	public int blueTurn(int col) {
		if (map[0][col] != EMPTY_PIECE) return -10;//This return is just for consistency with checkWin 
	
		//Loops through and finds where the piece should be placed, then places it
		for (int i = 0; i <= map.length - 1; i++) {
			if (i == map.length-1 || map[i+1][col] != EMPTY_PIECE) {
				map[i][col] = BLUE_PIECE;
				return i;//NOTE: This returns the row that the spot was found in
			}			 //		 when using checkWin, the column must be used with this return value
		}
		return -1;
	}
	
	
	/**
	 * Method that takes the coordinates of the grid to check win conditions on
	 * @param row the row of the piece in question
	 * @param col the column of the piece in question
	 * @return returns true if the specified coordinate satisfies at least one win condition
	 */
	public boolean checkWin(int row, int col) {
		if(row < 0) return false;
		if(map[row][col] == EMPTY_PIECE) return false;
		
		int counter = 0;
		char player = map[row][col];
		
		
		//check vertical
		for (int i = row; i < map.length; i++) {
			if (map[i][col] == player) counter++;
			else counter = 0;//Counter resets if the streak is broken
				
			if (counter >= 4) return true;
		}
		counter = 0;
		
		//TODO: Check horizontal
		for (int i = 0; i < map.length; i++) {
			
		}
		
		//TODO: Check Diagonal
		
		return false;
	}

	//main method for testing only
	public static void main(String[] args) {
		
		Board b = new Board(7,6);
		System.out.println(b.toString() + "\n");
		
		System.out.println(b.checkWin(b.redTurn(0),0));
		System.out.println(b.toString() + "\n");
		
		System.out.println(b.checkWin(b.blueTurn(0),0));
		System.out.println(b.toString() + "\n");
		
		System.out.println(b.checkWin(b.blueTurn(0),0));
		System.out.println(b.toString() + "\n");
		
		System.out.println(b.checkWin(b.blueTurn(0),0));
		System.out.println(b.toString() + "\n");
		
		System.out.println(b.checkWin(b.blueTurn(0),0));
		System.out.println(b.toString() + "\n");
		
		System.out.println(b.checkWin(b.redTurn(0),0));
		System.out.println(b.toString() + "\n");
		
		
		
		/*
		 * The statement to execute a turn and check if that was a winning turn is:
		 * b.checkWin(b.redTurn(column),column);
		 * or 
		 * b.checkWin(b.blueTurn(column),column)
		 */
	}
}
