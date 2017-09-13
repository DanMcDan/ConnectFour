package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class PlayerFrame extends JFrame{
	private static final long serialVersionUID = -5683163455384492681L;
	
	private JPanel menuPanel;
	private JPanel gamePanel;
	
	
	
	public PlayerFrame(String s) {
		super(s);
		setLayout(new BorderLayout());
		add(new JPanel(), BorderLayout.CENTER);
		
		//Listener defines what happens when the application is closed
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//add closing operations as needed, in this area
				
				
				
				System.exit(0);
			}
		});
		
		
		
		setBounds(0, 0, 700, 400);
		setVisible(true);
		setResizable(false);
		
		generateMenuPanel();
	}

	private void generateMenuPanel() {
		
	}
}
