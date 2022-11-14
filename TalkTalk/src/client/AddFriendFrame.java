package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AddFriendFrame extends JFrame{
	private Socket socket;
	private UserInfo userInfo;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private MyPanel contentPane;
	
	private JLabel addFriend;
	private JLabel addFriendByName;
	
	
	public AddFriendFrame(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, UserInfo userInfo) {
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
		this.userInfo = userInfo;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 170, 270, 400);
		contentPane = new MyPanel();
		contentPane.setBackground(new Color(255,255,255));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		uiInit();
		
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
	}
	
	
}

class MyPanel extends JPanel {
	public MyPanel() {
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(200, 200, 200));
		g.drawLine(10, 100, 240, 100);
		System.out.println("drawLine");
	}
}