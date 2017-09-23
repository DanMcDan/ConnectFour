package application;

import java.net.ServerSocket;
import java.net.Socket;

public class ConnectFourDriverServer {
	public static void main(String[] args) {
		
		try
		{
			Socket[] clients;
			GameThread gt;
			ServerSocket ss = new ServerSocket(2282);
			int threads = 0;
			
			//Waits for incoming players
			while (true) {
				clients = new Socket[2];
				for(int i = 0;i < clients.length;i++) {
					System.out.println("Looking for connection...");
					clients[i] = (ss.accept());//wait for connection
					System.out.println("Connected!");
					
					if (i == 1) {
						System.out.println("Match starting...");
						gt = new GameThread(clients[0], clients[1]);
						Thread th = new Thread(gt);//Players split off into their own thread, where their game continues forever
						th.start();
						System.out.println("Done!");
					}
					
				}
				if (threads == 10) break;
				else threads++;
				
			}
			ss.close();
			
		}catch (Exception e) {e.printStackTrace();}
	}
}
