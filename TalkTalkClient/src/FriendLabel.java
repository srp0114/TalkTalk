// JavaObjClient.java
// ObjecStream 사용하는 채팅 Client

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendLabel extends JLabel {
	Color yellow = new Color(254,240,27);

	public FriendLabel() {
		setPreferredSize(new Dimension(200,40));
		
	}
	
	
	public FriendLabel(ChatMsg msg) {
		
	}
}
