package game;

/**
 * Board class containing a "map" of all the pieces involved in a game of Connect Four
 * 
 * @author Daniel McCann
 *
 */
public class Board {
	
	public static final char 	MY_PIECE = 'X',
								THEIR_PIECE = 'O',
								EMPTY_PIECE = '*';
	
	private int boardWidth,
				boardHeight;

	private char map[][];
	
	/**
	 * Constructor for the game board
	 * 
	 * @param boardWidth takes the intended width of the game board
	 * @param boardHeight takes the intended height of the game board
	 */
	public Board(int boardWidth, int boardHeight) {
		map = new char[boardHeight][boardWidth];
		
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		
		for(int i = 0; i < map.length; i++)
			for(int j = 0; j < map[i].length; j++)
				map[i][j] = EMPTY_PIECE;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	/**
	 * Public getter for the map
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
	 * Method for recording the enemy players turn.
	 * @param 	col col recieves an index for the column number.<br>
	 * 			The piece will seek the bottom most row of the column, where no pieces already are
	 * 
	 * @return 	Returns -1 if the insert turn was unsuccessful (This should never happen)<br>
	 * 			Returns -10 if the column is full of other pieces<br>
	 * 			Returns the row that the piece was placed in if column was valid
	 */
	public int theirTurn(int col) {
		if (map[0][col] != EMPTY_PIECE) return -10;//This return is just in case there needs to be a different result from playing into a filled column
	
		//Loops through and finds where the piece should be placed
		for (int i = 0; i <= map.length - 1; i++) {
			if (i == map.length-1 || map[i+1][col] != EMPTY_PIECE) {
				map[i][col] = THEIR_PIECE;
				return i;//NOTE: This returns the row that the spot was found in
			}			 //		 when using checkWin, the column must be used with this return value
		}
		
		return -1;//Insert failed. There is really no reason that this should happen.
	}
	
	/**
	 * Method for recording the players turn.
	 * @param 	col col recieves an index for the column number.<br>
	 * 			The piece will seek the bottom most row of the column, where no pieces already are
	 * 
	 * @return 	Returns -1 if the insert turn was unsuccessful (This should never happen).<br>
	 * 			Returns -10 if the column is full of other pieces.<br>
	 * 			If the insert was successful, then the row where the piece landed in is returned.
	 */
	public int myTurn(int col) {
		if (map[0][col] != EMPTY_PIECE) return -10;//This return is just in case there needs to be a different result from playing into a filled column
	
		//Loops through and finds where the piece should be placed, then places it
		for (int i = 0; i <= map.length - 1; i++) {
			if (i == map.length-1 || map[i+1][col] != EMPTY_PIECE) {
				map[i][col] = MY_PIECE;
				return i;//NOTE: This returns the row that the spot was found in
			}			 //		 when using checkWin, the column must be used with this return value
		}
		return -1;//Insert failed. There is really no reason that this should happen.
	}
	
	
	/**
	 * Method that takes the coordinates of the grid to check win conditions on
	 * @param row the row of the piece in question (Can be taken from the myTurn or theirTurn methods)
	 * @param col the column of the piece in question
	 * @return returns true if the specified coordinate satisfies at least one win condition
	 */
	public boolean checkWin(int row, int col) {
		if(row < 0) return false;
		if(map[row][col] == EMPTY_PIECE) return false;
		
		int counter = 0;
		char player = map[row][col];
		
		
		//check vertical
		for (int i = row; i < map.length; i++) {//Starts from the piece just placed, and works downward
			if (map[i][col] == player) counter++;
			else counter = 0;//Counter resets if the streak is broken
				
			if (counter >= 4) return true;
		}
		counter = 0;
		
		//Check horizontal
		for (int i = 0; i < map[row].length; i++) {
			if (map[row][i] == player) counter++;
			else counter = 0;//Counter resets if the streak is broken
				
			if (counter >= 4) return true;
		}
		counter = 0;
		
		//Check Diagonal
		//top left to bottom right
		int h,//this is the row of one of the uppermost diagonals
			v;//this is the col of one of the uppermost diagonals
		
		//This gets the index of the top-leftmost diagonal
		for (int i = 0;; i++)
			if (row - i == 0 || col - i == 0) {
			    //top left reached
				h = row - i;
				v = col - i;
				break;
			}
		//System.out.println("map["+h+"]["+v+"]"); //CONFIRMED WORKS
		
		//This entire thing goes down the diagonal line, from the top-left, to the bottom-right, until it hits a wall.
		for (int i = 0; ;i++) {
			if (h+i > map.length-1 || v+i > map[h+i].length-1)	break;
			
			if (map[h+i][v+i] == player)	counter ++;
			else	counter = 0;
			
			if (counter >= 4)	return true;
		}
		counter = 0;
		
		//Bottom left to top right
		
		//Find the top right
		for (int i = 0;; i++)
			if (row - i == 0 || col + i == map[row - i].length-1) {
			    //top left reached
				h = row - i;
				v = col + i;
				break;
			}
		
		for (int i = 0;;i++) {
			if (h+i > map.length-1 || v-i < 0)	break;
			
			if (map[h+i][v-i] == player)	counter ++;//Similar to above steps to check line diagonally, but along a different axis.
			else	counter = 0;
			
			if (counter == 4)	return true;
		}
		
		return false;//Finally confirmed to not be a winning move
	}
}
