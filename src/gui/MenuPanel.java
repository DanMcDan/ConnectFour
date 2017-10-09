package gui;

import java.awt.BorderLayout;
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
class MenuPanel extends JPanel{
	private static final long serialVersionUID = -2304450763260224381L;
	
	private JTextArea chatArea 		= new JTextArea();
	private JScrollPane chatBox 	= new JScrollPane();
	private JTextField chatField 	= new JTextField();
	private JButton chatButton 		= new JButton("Send");

	private String userName;
	
	/**
	 * Constructor for the menu panel. Used to hold the chat, and the text field for chatting.
	 */
	public MenuPanel() {
		setBorder(BorderFactory.createTitledBorder("Chat"));
		setLayout(new BorderLayout());
		
		userName = JOptionPane.showInputDialog(null,"Enter your chat name");
		
		constructInterface();
	}
	
	/**
	 * Private method for adding the panel components
	 */
	private void constructInterface() {
		JPanel lowerPanel = new JPanel(new BorderLayout());
		
		lowerPanel.add(chatButton, BorderLayout.EAST);
		lowerPanel.add(chatField, BorderLayout.CENTER);
		
		add(lowerPanel, BorderLayout.SOUTH);
		add(chatBox, BorderLayout.CENTER);
		chatBox.getViewport().add(chatArea);
		chatArea.setEditable(false);
		chatArea.setWrapStyleWord(true);
		chatArea.setLineWrap(true);
		
	}
	
	public JButton getChatButton() {
		return chatButton;
	}

	public JTextField getChatField() {
		return chatField;
	}
	
	public JTextArea getChatArea() {
		return chatArea;
	}
	
	public String getUserName() {
		return userName;
	}
}
