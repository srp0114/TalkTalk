import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import util.ColorDefinition;

public class AddFriendFrame extends JFrame{
	private TalkTalkClientView clientView = null;
	private ChatMsg chatMsg;
	private ChatMsg friendUserInfo;
	
	private MyPanel contentPane;
	
	private JLabel addFriend;
	private JLabel addFriendByName;
	private JTextField tfUserName;
	private JButton btnAddFriend;
	
	private Image profileImg = Toolkit.getDefaultToolkit().getImage("src/noProfileImg.jpg");
	private ImageIcon searchedIcon = null;
	private JLabel searchedImg = null;
	private JLabel searchedName = null;
	
	public AddFriendFrame(TalkTalkClientView clientView, ChatMsg chatMsg) {
		this.clientView = clientView;
		this.chatMsg = chatMsg;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 170, 290, 450);
		contentPane = new MyPanel();
		contentPane.setBackground(new Color(255,255,255));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		uiInit();
		tfUserName.addActionListener(new Myaction());
		
		setResizable(false);
		setVisible(true);
	}
	
	class Myaction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("tfUserName actionPerformed");
			String friendName = tfUserName.getText().trim();
			System.out.println("tfUserName: " + friendName);
			ChatMsg chatmsg = new ChatMsg(chatMsg.getUsername(), "302");
			chatmsg.setSearchFriend(friendName);
			System.out.println(chatmsg.getCode());
			System.out.println(chatmsg.getSearchFriend());
			clientView.SendObject(chatmsg);
			
		}
	}
	
	public void uiInit() {
		addFriend = new JLabel("친구 추가");
		addFriend.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		addFriend.setBounds(30, 15, 80, 55);
		contentPane.add(addFriend);
		
		addFriendByName = new JLabel("이름으로 친구 추가");
		addFriendByName.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		addFriendByName.setBounds(30, 55, 130, 55);
		contentPane.add(addFriendByName);

		
		tfUserName = new JTextField();
		tfUserName.setHorizontalAlignment(SwingConstants.CENTER);
		tfUserName.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		tfUserName.setBounds(30, 125, 220, 38);
		tfUserName.setColumns(10);
		contentPane.add(tfUserName);
		
		btnAddFriend = new JButton("친구 추가");
		btnAddFriend.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		btnAddFriend.setForeground(new Color(0, 0, 0));
		btnAddFriend.setBounds(160, 360, 100, 38);
		btnAddFriend.setBackground(ColorDefinition.kakaoYello);
		btnAddFriend.setBorderPainted(false);
		btnAddFriend.setFocusPainted(false);
		btnAddFriend.setVisible(false);
		contentPane.add(btnAddFriend);
		
		profileImg = profileImg.getScaledInstance(70, 70,  Image.SCALE_SMOOTH);
		searchedIcon = new ImageIcon(profileImg);
		searchedImg = new JLabel(searchedIcon);
		searchedImg.setBounds(98, 201, 70, 70);
		searchedImg.setVisible(false);
		contentPane.add(searchedImg);
	
		
		searchedName = new JLabel("유진");
		searchedName.setHorizontalAlignment(SwingConstants.CENTER);
		searchedName.setBounds(83, 270, 100, 50);
		searchedName.setVisible(false);
		contentPane.add(searchedName);
	}
	public void updateSearchResult(ChatMsg searchResult) {
		System.out.println("updateSearchResult(): " + searchResult.getUsername());
		ImageIcon resultIcon = searchResult.getProfileImg();
		JLabel image = new JLabel(resultIcon);
		image.setBounds(70, 230, 50, 50);
		//JLabel searchedName = new JLabel(searchResult.getUsername());
		//searchedName.setBounds(70, 300, 100, 50);
		//contentPane.add(image);
		//contentPane.add(searchedName);
		
	
		searchedImg.setIcon(resultIcon);
		searchedName.setText(searchResult.getUsername());
		searchedName.setVisible(true);
		btnAddFriend.setVisible(true);
	}
}

class MyPanel extends JPanel {
	public MyPanel() {
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(200, 200, 200));
		g.drawLine(10, 100, 260, 100);
		System.out.println("drawLine");
	}
}