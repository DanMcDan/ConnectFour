package application;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

public class InputListener extends Observable implements Runnable {
	private Socket s;
	private ObjectInputStream ois;
	
	/**
	 * Constructor takes socket and an object to observe this one
	 * @param s s is the socket for listening, once something has been heard, the observer is given the object heard
	 * @param observer observer is the object that will be notified of the heard object
	 */
	public InputListener(Socket s, Observer observer) {
		this.s = s;
		this.addObserver(observer);
	}
	
	@Override
	public void run() {
		try
		{
			//An ObjectInputStream is created, and used to listen to what that socket might be saying FOREVER
			ois = new ObjectInputStream(s.getInputStream());
			while (true) {
				Object o = ois.readObject();//NEVER NOT READING OBJECT
				setChanged();
				notifyObservers(o);
			}
		}
		catch(SocketException			e)	{JOptionPane.showMessageDialog(null, "Socket Disconnect");}
		catch(EOFException				e)	{e.printStackTrace();}
		catch(IOException				e)	{e.printStackTrace();}
		catch(ClassNotFoundException	e)	{e.printStackTrace();}
	}
}
