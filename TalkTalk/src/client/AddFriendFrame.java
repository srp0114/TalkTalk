package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import util.ColorDefinition;

public class AddFriendFrame extends JFrame{
	private Socket socket;
	private UserInfo userInfo;
	private UserInfo friendUserInfo;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private MyPanel contentPane;
	
	private JLabel addFriend;
	private JLabel addFriendByName;
	private JTextField tfUserName;
	private JButton btnAddFriend;
	
	
	public AddFriendFrame(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, UserInfo userInfo) {
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
		this.userInfo = userInfo;
		
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
		contentPane.add(btnAddFriend);
	}
	class Myaction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String friendName = tfUserName.getText().trim();
			try {
				oos.flush();
				userInfo.setCode("302");
				userInfo.setSearchFriend(friendName);
				System.out.println(userInfo.getCode());
				SendObject(userInfo);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void SendObject(Object ob) {
		try {
			oos.writeObject(ob);
		} catch(IOException e) {
			System.out.println("SendObject Error");
		}
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