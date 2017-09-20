package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import game.Board;
import gui.PlayerFrame;

public class GameThread implements Runnable{
	
	private Socket clients[];
	private Board board = new Board(7,6);//this is the 7x6 board
	
	public GameThread(Socket[] clients) {
		this.clients = clients;	//Takes the array of client sockets
								//NOTE: SHOULD NEVER BE MORE THAN TWO
	}
	
	
	/**
	 * Method that assigns both players a colour and begins trading turns
	 */
	private void playGame() {
		boolean gameOver = false;
		String[] turn;
		DataOutputStream dos;//Stream for Sending
		DataInputStream dis;//stream for Receiving
		try
		{
			//for loop to assign both players their colour
			//Player 1 is blue and the second is red
			for (int i = 0; i < clients.length; i++) {
				dos = new DataOutputStream(clients[i].getOutputStream());
				dis = new DataInputStream(clients[i].getInputStream());
				dos.writeChar(i==0 ? PlayerFrame.BLUE:PlayerFrame.RED);
				//Print out the character that has hopefully gotten there
				System.out.println("host: "+clients[i].getInetAddress().toString()+" as player: "+dis.readChar());
				
				
			}
			
			//Both players now have a colour, now turns should be made
			for (int i = 0; !gameOver; i++) {
				synchronized(this) {
					//Sends a string to wake up client
					System.out.println("Player "+(i+1)+"start");
					dos = new DataOutputStream(clients[i].getOutputStream());
					dos.writeUTF("Start Turn");
					
					//waits for button to be pressed
					dis = new DataInputStream(clients[i].getInputStream());
					turn = dis.readUTF().split(";");
					
					System.out.println(turn);
					
					String player = turn[0];
					int column = Integer.valueOf(turn[1]);
					
					if (player.equals("B"))
						gameOver = board.checkWin(board.redTurn(column),column);
					else
						gameOver = board.checkWin(board.blueTurn(column),column);
					
					i%=2;
					dis.close();
				}
			}
			
			
			
		}catch(IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void run() {
		playGame();
	}
}
