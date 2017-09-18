package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author Daniel McCann
 *
 */
public class MenuPanel extends JPanel{
	private static final long serialVersionUID = -2304450763260224381L;
	
	private JTextArea chatArea 		= new JTextArea();
	private JScrollPane chatBox 	= new JScrollPane();
	private JTextField chatField 	= new JTextField();
	private JButton quitButton 		= new JButton("SEND");
	private String userName;

	public MenuPanel() {
		setBorder(BorderFactory.createTitledBorder("Chat"));
		setLayout(new BorderLayout());
		
		userName = JOptionPane.showInputDialog(null,"Enter stuff");
		
		constructInterface();
		createListeners();
	}
	
	private void constructInterface() {
		JPanel lowerPanel = new JPanel(new BorderLayout());
		
		lowerPanel.add(quitButton, BorderLayout.EAST);
		lowerPanel.add(chatField, BorderLayout.CENTER);
		
		add(lowerPanel, BorderLayout.SOUTH);
		add(chatBox, BorderLayout.CENTER);
		chatArea.setEditable(false);
		chatArea.setWrapStyleWord(true);
		chatArea.setLineWrap(true);
		chatBox.getViewport().add(chatArea);
	}

	private void createListeners() {
		//create Listener for the Send button
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();
			}
		});
		
		//listener for chat box
		chatField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
			
		});
		
		//add listeners for any future buttons here
	}
	
	private void sendMessage() {
		if(!chatField.getText().equals("")) {
			chatArea.append(userName + ": " + chatField.getText() + "\n\n");
			chatField.setText("");
		}
	}
}
