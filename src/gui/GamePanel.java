package gui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = -9100426103735670345L;

	public GamePanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
}
