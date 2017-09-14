package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	
	private JTextArea chatArea = new JTextArea();
	private JScrollPane chatBox = new JScrollPane();
	private JTextField chatField = new JTextField();
	private JButton quitButton = new JButton("QUIT");

	public MenuPanel() {
		setBorder(BorderFactory.createTitledBorder("Chat"));
		setLayout(new BorderLayout());
		
		constructInterface();
		createListeners();
	}
	
	private void constructInterface() {
		JPanel lowerPanel = new JPanel(new BorderLayout());
		
		lowerPanel.add(quitButton, BorderLayout.WEST);
		lowerPanel.add(chatField, BorderLayout.CENTER);
		
		add(lowerPanel, BorderLayout.SOUTH);
		add(chatBox, BorderLayout.CENTER);
		chatArea.setEditable(false);
		chatBox.getViewport().add(chatArea);
	}

	private void createListeners() {
		//create Listener for the quit button
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Have this method initiate the quit process
				System.out.println("Quit pushed");
			}
		});
		
		//listener for chat box
		chatField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!chatField.getText().equals("")) {
					chatArea.append(chatField.getText() + "\n");
					chatField.setText("");
				}
			}
			
		});
		
		//add listeners for any future buttons here
	}
}
