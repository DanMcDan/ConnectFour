package game;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Player {
	
	private char pl;
	private Socket addr;
	
	public Player(String ip, int port) throws UnknownHostException, IOException {
		addr  = new Socket(ip,port);
	}
	
	public void setPl(char pl) {
		this.pl = pl;
	}
	
	public char getPl() {
		return pl;
	}
	public Socket getSocket() {
		return addr;
	}
}
