package application;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import game.Player;
import gui.PlayerFrame;

public class ConnectFourDriverServer {
	public static void main(String[] args) {
		
		try
		{
			Socket[] clients;
			//Player[] players;
			GameThread gt;
			ServerSocket ss = new ServerSocket(25565);
			
			
			
			while (true) {
				clients = new Socket[2];
//				players = new Player[2];
				for(int i = 0;i < clients.length;i++) {
					clients[i] = (ss.accept());//wait for connection
//					players[i] = new Player(clients[i].getInetAddress().toString(), 25565);
					gt = new GameThread(clients);//TODO TODO TODO TODO TODO TODO TODO TODO TODO
					Thread th = new Thread(gt);
					th.start();
				}
			}
			
			
		}catch (Exception e) {e.printStackTrace();}
		
		
//		PlayerFrame pf = new PlayerFrame("Connect Four");
//		pf.setVisible(true);
	}
}
