package application;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import game.Player;

public class GameThread implements Runnable{
	
	private Player p;
	private Socket clients[];
	
	public GameThread(Socket[] clients) {
		this.clients = clients;
	}

	private void playGame() {
		// TODO Auto-generated method stub
		try
		{
			for (int i = 0; i < clients.length; i++) {
				DataOutputStream dos;
				dos = new DataOutputStream(clients[i].getOutputStream());
				dos.writeChar(i==0 ? 'B':'R');
			}
		}catch(IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void run() {
		playGame();
	}

	
}
