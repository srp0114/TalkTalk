import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	private Image profileIconImg = Toolkit.getDefaultToolkit().getImage("src/profileIcon.png");
	private ImageIcon profileIcon;
	private JButton btnprofileIcon;
	
	private Image chatIconImg = Toolkit.getDefaultToolkit().getImage("src/chatIcon.png");
	private ImageIcon chatIcon;
	private JButton btnchatIcon;
	
	public MenuPanel() {
		this.setBackground(new Color(236, 236, 237));
		setLayout(null);
		
		uiInit();
		
	}
	
	public void uiInit() {
		profileIconImg = profileIconImg.getScaledInstance(28, 28,  Image.SCALE_SMOOTH);
		profileIcon = new ImageIcon(profileIconImg);
		btnprofileIcon = new JButton(profileIcon);
		btnprofileIcon.setBorderPainted(false);
		btnprofileIcon.setFocusPainted(false);
		btnprofileIcon.setContentAreaFilled(false);
		btnprofileIcon.setOpaque(false);
		btnprofileIcon.setBounds(20, 30, 28, 28);
		this.add(btnprofileIcon);
		
		chatIconImg = chatIconImg.getScaledInstance(28, 28,  Image.SCALE_SMOOTH);
		chatIcon = new ImageIcon(chatIconImg);
		btnchatIcon = new JButton(chatIcon);
		btnchatIcon.setBorderPainted(false);
		btnchatIcon.setFocusPainted(false);
		btnchatIcon.setContentAreaFilled(false);
		btnchatIcon.setOpaque(false);
		btnchatIcon.setBounds(20, 90, 28, 28);
		this.add(btnchatIcon);
	}
}