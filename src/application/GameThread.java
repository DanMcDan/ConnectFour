package application;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import gui.PlayerFrame;

public class GameThread implements Runnable, Observer{
	
	private Socket pl1;
	private Socket pl2;
	
	private ObjectOutputStream oos1;
	private ObjectOutputStream oos2;
	
	private InputListener il1;
	private InputListener il2;
	
	public GameThread(Socket pl1, Socket pl2) {
		this.pl1 = pl1;
		this.pl2 = pl2;
		
		try
		{
			oos1 = new ObjectOutputStream(pl1.getOutputStream());
			oos2 = new ObjectOutputStream(pl2.getOutputStream());
		}
		catch (IOException e) {e.printStackTrace();}
		
		
		il1 = new InputListener(this.pl1, this);
		il2 = new InputListener(this.pl2, this);
		Thread th1 = new Thread(il1);
		Thread th2 = new Thread(il2);
		th1.start(); th2.start();//Start listening
	}
	
	@Override
	public void run() {
		
			System.out.println("Client: "+pl1.getInetAddress().toString()+" as player: "+PlayerFrame.BLUE);
			System.out.println("Client: "+pl2.getInetAddress().toString()+" as player: "+PlayerFrame.RED);
			
	}


	@Override
	public void update(Observable o, Object arg) {
		try 
		{
			if (o == il1) {
				oos2.writeObject(arg);
			} else if (o == il2) {
				oos1.writeObject(arg);
			}
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
