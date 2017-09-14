package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class PlayerFrame extends JFrame{
	private static final long serialVersionUID = -5683163455384492681L;
	
	private JPanel wrapper = new JPanel();
	private JPanel menuPanel = new MenuPanel();
	private JPanel gamePanel = new GamePanel();
	
	
	
	public PlayerFrame(String s) {
		super(s);
		setLayout(new BorderLayout());
		add(wrapper, BorderLayout.CENTER);

		//Listener defines what happens when the application is closed
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//add closing operations as needed, in this area
				System.exit(0);
			}
		});
		
		
		
		setBounds(200, 200, 700, 400);
		setVisible(true);
		setResizable(false);
		wrapper.setLayout(new BorderLayout());
		wrapper.add(menuPanel, BorderLayout.EAST);
		wrapper.add(gamePanel, BorderLayout.CENTER);
	}
}
