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
		int boardWidth = 7;//These determine the board dimensions
		int boardHeight = 6;	//TODO:	Currently these numbers have to match the board
								//		on GameThread object, which needs to be changed
		
		
		//Connect to server
		while (s == null) {
			try
			{
				s = new Socket(adder, 2282);
			} catch (IOException e) {
				adder = JOptionPane.showInputDialog("Oops, enter the IP again",null);
			}
		}
		
		try
		{
			//Get player colour and send response to confirm
			dis = new DataInputStream(s.getInputStream());
			player = dis.readChar();//Take player assignment
			
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeChar(player);//send confirmation
			
		}catch (IOException e) {e.printStackTrace();}
		
		
		
		//Open the window
		pf = new PlayerFrame(adder + " " + player, player, boardWidth, boardHeight);
		pf.setVisible(true);
		pf.enableButtons();
	}
}
