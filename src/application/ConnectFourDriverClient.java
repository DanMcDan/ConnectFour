package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

import javax.swing.JOptionPane;

import game.Board;
import gui.PlayerFrame;

public class ConnectFourDriverClient {
	
	private static char player;
	
	public static void main(String[] args) {
		Board board = new Board(7,6);//this is the 7x6 board
		
		Socket socket = null;
		String text = JOptionPane.showInputDialog("Enter Server IP",null);
		
		DataInputStream dis;
		DataOutputStream dos;
		
		while(socket == null)
		{
			try
			{
				socket = new Socket(text, 25565);
			} catch (IOException e) {
				e.printStackTrace();
				socket = null;
				text = JOptionPane.showInputDialog("Enter Server IP",null);
			}
		}
		
		try {
			dis = new DataInputStream(socket.getInputStream());
			player = dis.readChar();
			
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeChar(player);
			
			PlayerFrame pf = new PlayerFrame(text, socket, player);
			pf.setVisible(true);
						
			
			while(true) {
				//This players turn has just begun
				dis = new DataInputStream(socket.getInputStream());
				System.out.println(dis.readUTF());
				
				pf.enableButtons();
				
				
			}
			
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}

}
