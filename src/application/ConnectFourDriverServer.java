package application;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import gui.PlayerFrame;

public class ConnectFourDriverServer {
	public static void main(String[] args) {
		
		try
		{
			Socket[] clients;
			//Player[] players;
			GameThread gt;
			ServerSocket ss = new ServerSocket(25565);
			int p = 0;
			
			
			while (true) {
				clients = new Socket[2];
				for(int i = 0;i < clients.length;i++) {
					System.out.println("Looking for connection...");
					clients[i] = (ss.accept());//wait for connection
					System.out.println("Connected!");
					
					if (i == 1) {
						System.out.println("Match starting...");
						gt = new GameThread(clients);
						Thread th = new Thread(gt);
						th.start();
						System.out.println("Done!");
					}
					
				}
				if (p == 10) break;
				else p++;
				
			}
			ss.close();
			
		}catch (Exception e) {e.printStackTrace();}
		
		
//		PlayerFrame pf = new PlayerFrame("Connect Four");
//		pf.setVisible(true);
	}
}
