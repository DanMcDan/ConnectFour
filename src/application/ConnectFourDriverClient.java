package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

import javax.swing.JOptionPane;

import gui.PlayerFrame;

public class ConnectFourDriverClient {
	
	private static char player;
	
	public static void main(String[] args) {
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
			
			PlayerFrame pf = new PlayerFrame(text, socket);
			pf.setVisible(true);
			
			dis.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}

}
