
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
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
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TalkTalkClientView extends JFrame{
	private JPanel contentPane;
	private String username;  // username
	private String ip_addr;
	private String port_no;
	private UserInfo obui;		// obui
	
	private JSplitPane hPane;

	private JButton btnprofileIcon;
	private JButton btnchatIcon;
	
	private static final int BUF_LEN = 128;
	private Socket socket;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	
	private MenuPanel menuPanel;  // 메뉴 패널
	private FriendListPanel friendPanel;  // 친구창 패널

	public TalkTalkClientView(String username, String ip_addr, String port_no) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,390,600);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		this.username = username;
		this.ip_addr = ip_addr;
		this.port_no = port_no;
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			
			obui = new UserInfo(username, "100");			
			SendObject(obui);
		}catch(NumberFormatException | IOException e) {
			e.printStackTrace();
			System.out.println("connect error");
		}
		
		splitPane();
		setResizable(false);
		setVisible(true);
	}
	
	public void splitPane() {
		hPane = new JSplitPane();   // JSplitPane 생성
		
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(70);
		hPane.setDividerSize(0);
		hPane.setEnabled(false);
		
		menuPanel = new MenuPanel();
		hPane.setLeftComponent(menuPanel);
		friendPanel = new FriendListPanel(socket, ois, oos, obui);
		hPane.setRightComponent(friendPanel);
		getContentPane().add(hPane, BorderLayout.CENTER);
	}
	
	public void SendObject(Object ob) {
		try {
			oos.writeObject(ob);
			oos.flush();
		} catch(IOException e) {
			System.out.println("SendObject Error");
		}
	}
	
	
	
	class MenuPanel extends JPanel{
		private Image profileIconImg = Toolkit.getDefaultToolkit().getImage("src/profileIcon.png");
		private ImageIcon profileIcon;
		
		private Image chatIconImg = Toolkit.getDefaultToolkit().getImage("src/chatIcon.png");
		private ImageIcon chatIcon;

		
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
		class MyChangeaction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
			}
		}
	}
}