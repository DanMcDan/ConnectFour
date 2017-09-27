package application;

import java.net.ServerSocket;
import java.net.Socket;

public class ConnectFourDriverServer {
	public static void main(String[] args) {
		
		try
		{
			Socket[] clients;
			GameThread gt;
			
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(2282);
			
			while (true) {
				clients = new Socket[2];//"clients" refrences a new array
				for(int i = 0;i < clients.length;i++) {
					System.out.println("Looking for connection...");
					clients[i] = (ss.accept());//wait for connection
					System.out.println("Connected!");
					
					if (i == clients.length-1) {//When this is true, we can be sure that there are 2 players connected
						System.out.println("Match starting...");
						Thread th = new Thread(new GameThread(clients[0], clients[1]));//Players split off into their own thread
						th.start();
						System.out.println("Done!");
					}
					
				}
				
			}
			
		}catch (Exception e) {e.printStackTrace();}
	}
}
