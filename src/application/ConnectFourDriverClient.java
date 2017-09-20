package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import gui.PlayerFrame;

public class ConnectFourDriverClient {
	
	public static void main(String[] args) {
		DataOutputStream dos;//Stream for Sending
		DataInputStream dis;//stream for Receiving
		Socket s = null;
		String adder = JOptionPane.showInputDialog("Enter server IP",null);
		
		char player = 0;
		PlayerFrame pf;
		int boardWidth = 7;
		int boardHeight = 6;
		
		
		//Connect to server
		while (s == null) {
			try
			{
				s = new Socket(adder, 25565);
			} catch (IOException e) {
				adder = JOptionPane.showInputDialog("Oops, enter the IP again",null);
			}
		}
		
		try
		{
			//Get player colour and send response
			dis = new DataInputStream(s.getInputStream());
			player = dis.readChar();
			
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeChar(player);
			
		}catch (IOException e) {e.printStackTrace();}
		
		//Open the window
		pf = new PlayerFrame(adder + " " + player, player, boardWidth, boardHeight);
		pf.setVisible(true);
		pf.enableButtons();
	}
}
