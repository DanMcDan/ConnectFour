package application;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConnectFourDriverServer {

	public static JFrame serverFrame = new JFrame("Connect Four Server");
	public static JScrollPane jsPane = new JScrollPane();
	public static JTextArea serverLog = new JTextArea();
		
	public static void main(String[] args) {
		
		
		serverFrame.setBounds(200, 200, 500, 300);
		serverFrame.setResizable(false);
		serverFrame.setVisible(true);
		serverFrame.setLayout(new BorderLayout());
		serverFrame.add(jsPane, BorderLayout.CENTER);
		jsPane.getViewport().add(serverLog);
		serverLog.setEditable(false);
		serverLog.setWrapStyleWord(true);
		serverLog.setLineWrap(true);
		serverFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		try
		{
			Socket[] clients;
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(2282);
			
			while (true) {
				clients = new Socket[2];//"clients" references a new array
				for(int i = 0;i < clients.length;i++) {
					serverLog.append("Looking for connection...\n");
//					System.out.println("Looking for connection...");
					clients[i] = (ss.accept());//wait for connection
					serverLog.append("Connected!\n");
//					System.out.println("Connected!");
					
					if (i == clients.length-1) {//When this is true, we can be sure that there are 2 players connected
						serverLog.append("Match starting...\n");
//						System.out.println("Match starting...");
						Thread th = new Thread(new GameThread(clients[0], clients[1]));//Players split off into their own thread
						th.start();
						serverLog.append("Done!\n");
//						System.out.println("Done!");
					}
					
				}
				
			}
			
		}catch (Exception e) {e.printStackTrace();}
	}
}
