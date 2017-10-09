package application;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

class GameThread implements Runnable, Observer{
	
	private Socket pl1;
	private Socket pl2;
	
	private ObjectOutputStream oos1;
	private ObjectOutputStream oos2;
	
	private InputListener il1;
	private InputListener il2;
	
	/**
	 * Constructor for Runnable object used to relay turns and messages. If an entire Board object is sent, it means the player who sent it won the game
	 * @param pl1
	 * @param pl2
	 * @param ss 
	 */
	public GameThread(Socket pl1, Socket pl2) {
		
		this.pl1 = pl1;
		this.pl2 = pl2;
		
		try
		{
			oos1 = new ObjectOutputStream(pl1.getOutputStream());
			oos2 = new ObjectOutputStream(pl2.getOutputStream());
			
		}catch (IOException e) {e.printStackTrace();}
		
		il1 = new InputListener(pl1, this);
		il2 = new InputListener(pl2, this);
		
		Thread th1 = new Thread(il1);
		Thread th2 = new Thread(il2);
		
		th1.start();
		th2.start();//Start listening
	}
	
	@Override
	public void run() {
		try
		{
			oos1.writeObject("Player found at " + pl2.getInetAddress().toString()+"\n\n");
			oos2.writeObject("Player found at " + pl1.getInetAddress().toString()+"\n\n");
		}
		catch (IOException e) {e.printStackTrace();}
	}


	@Override
	public void update(Observable o, Object arg) {
		try 
		{
			//Used to relay turns/messages between players
			if 		(o == il1)	oos2.writeObject(arg);
			else if (o == il2) 	oos1.writeObject(arg);
		}
		catch (SocketException 	e) 	{e.printStackTrace();}
		catch (IOException 		e) 	{e.printStackTrace();}
	}
}
