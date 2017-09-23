package application;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import gui.PlayerFrame;

public class ConnectFourDriverClient {
	
	public static void main(String[] args) {
		
		Socket s = null;
		String adder = JOptionPane.showInputDialog("Enter server IP",null);
		
		PlayerFrame pf;
		
		//Board dimensions
		int 	boardWidth = 7,
				boardHeight = 6;
		
		
		//Connect to server
		while (s == null) {
			try
			{
				s = new Socket(adder, 2282);
			} catch (IOException e) {
				adder = JOptionPane.showInputDialog("Oops, enter the IP again",null);
			}
		}
		
		//Open the window
		pf = new PlayerFrame(adder, s, boardWidth, boardHeight);
		pf.setVisible(true);
	}
}
