package application;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

public class InputListener extends Observable implements Runnable {
	private char player;
	private Socket s;
	private ObjectInputStream ois;
	
	public InputListener(Socket s, Observer observer) {
		player = 0;
		this.s = s;
		this.addObserver(observer);
	}
	
	
	public InputListener(Socket s, char player, Observer observer) {
		this.player = player;
		this.s = s;
		this.addObserver(observer);
	}
	
	public char getPlayer() {
		return player;
	}
	public void setPlayer(char player) {
		this.player = player;
	}
	@Override
	public void run() {
		try
		{
			ois = new ObjectInputStream(s.getInputStream());
			while (true) {
				
				Object o = ois.readObject();
				setChanged();
				notifyObservers(o);
				System.out.println("InputListener: Input Detected");
			}
		}
		catch(SocketException e) {e.printStackTrace();}
		catch(EOFException e) {e.printStackTrace();}
		catch(IOException e) {e.printStackTrace();}
		catch(ClassNotFoundException e) {e.printStackTrace();}
	}
}
