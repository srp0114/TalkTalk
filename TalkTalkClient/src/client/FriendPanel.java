package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class FriendPanel extends JPanel{
	JScrollPane scrollPane;
	JTextPane textArea;
	
	private JLabel lblFriend;  // 친구 레이블
	 
	private UserInfo userInfo;
	private String userName;  // 로그인한 client 이름
	private JLabel lblUserName;  // username 레이블
	
	private Image searchIconImg = Toolkit.getDefaultToolkit().getImage("src/searchIcon.png");
	private ImageIcon searchIcon;
	private JButton btnSearchIcon;
	
	private Image addFriendIconImg = Toolkit.getDefaultToolkit().getImage("src/addFriendIcon.png");
	private ImageIcon addFriendIcon;
	private JButton btnAddFriendIcon;
	
	private Image profileImg = Toolkit.getDefaultToolkit().getImage("src/noProfileImg.jpg");
	private ImageIcon profileIcon;
	private JButton btnProfileImg;
	
	public FriendPanel(UserInfo userInfo) {  // 매개변수로 username 받는 생성자
		this.userInfo = userInfo;
		
		this.setBackground(new Color(255,255,255));  // 배경색: 흰색
		setLayout(null);
		UIInit();  // ui 세팅
	}
	
	public void UIInit() {
		
		lblFriend = new JLabel("친구");
		lblFriend.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblFriend.setBounds(23, 20, 50, 50);
		this.add(lblFriend);
		
		
		searchIconImg = searchIconImg.getScaledInstance(25, 25,  Image.SCALE_DEFAULT);
		searchIcon = new ImageIcon(searchIconImg);
		btnSearchIcon = new JButton(searchIcon);
		btnSearchIcon.setBorderPainted(false);
		btnSearchIcon.setFocusPainted(false);
		btnSearchIcon.setContentAreaFilled(false);
		btnSearchIcon.setOpaque(false);
		btnSearchIcon.setBounds(230, 32, 25, 25);
		this.add(btnSearchIcon);
		
		addFriendIconImg = addFriendIconImg.getScaledInstance(25, 25,  Image.SCALE_DEFAULT);
		addFriendIcon = new ImageIcon(addFriendIconImg);
		btnAddFriendIcon = new JButton(addFriendIcon);
		btnAddFriendIcon.setBorderPainted(false);
		btnAddFriendIcon.setFocusPainted(false);
		btnAddFriendIcon.setContentAreaFilled(false);
		btnAddFriendIcon.setOpaque(false);
		btnAddFriendIcon.setBounds(267, 32, 25, 25);
		this.add(btnAddFriendIcon);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 330, 500);
		scrollPane.setBackground(new Color(255,255,255));
		scrollPane.setBorder(null);
		this.add(scrollPane);
		
		textArea = new JTextPane();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		
		profileImg = profileImg.getScaledInstance(50, 50,  Image.SCALE_DEFAULT);
		profileIcon = new ImageIcon(profileImg);
		btnProfileImg = new JButton(profileIcon);
		btnProfileImg.setBorderPainted(false);
		btnProfileImg.setFocusPainted(false);
		btnProfileImg.setContentAreaFilled(false);
		btnProfileImg.setOpaque(false);
		btnProfileImg.setBounds(23, 80, 50, 50);
		textArea.add(btnProfileImg);
		
		
		lblUserName = new JLabel(userInfo.getUsername());
		lblUserName.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lblUserName.setBounds(90, 76, 50, 50);
		textArea.add(lblUserName);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(234, 234, 234));
		g.drawLine(10, 150, 300, 150);
	}
}
