package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import game.Player;
import gui.PlayerFrame;

public class GameThread implements Runnable{
	
	private Socket clients[];
	
	public GameThread(Socket[] clients) {
		this.clients = clients;	//Takes the array of client sockets
								//NOTE: SHOULD NEVER BE MORE THAN TWO
	}
	
	
	//This method is really just a test right now
	private void playGame() {
		DataOutputStream dos;//Stream for testing
		DataInputStream dis;//stream for testing
		try
		{
			//for loop to assign both players their colour
			//Player 1 is blue and the second is red
			for (int i = 0; i < clients.length; i++) {
				dos = new DataOutputStream(clients[i].getOutputStream());
				dis = new DataInputStream(clients[i].getInputStream());
				dos.writeChar(i==0 ? PlayerFrame.BLUE:PlayerFrame.RED);
				//Print out the character that has hopefully gotten there
				System.out.println(clients[i].getInetAddress().toString()+dis.readChar());
			}
			
		}catch(IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void run() {
		playGame();
	}
}
