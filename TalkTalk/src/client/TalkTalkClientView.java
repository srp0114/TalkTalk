package client;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TalkTalkClientView extends JFrame{
	private JPanel contentPane;
	private String username;  // username
	private String ip_addr;
	private String port_no;
	private UserInfo obui;		// obui

	
	private static final int BUF_LEN = 128;
	private Socket socket;
	//private InputStream is;
	//private OutputStream os;
	//private DataInputStream dis;
	//private DataOutputStream dos;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	
	private MenuPanel menuPanel;  // 메뉴 패널
	private FriendPanel friendPanel;  // 친구창 패널

	public TalkTalkClientView(String username, String ip_addr, String port_no) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,390,600);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		this.username = username;
		this.ip_addr = ip_addr;
		this.port_no = port_no;
		
		setResizable(false);
		setVisible(true);
		
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
	}
	
	public void splitPane() {
		JSplitPane hPane = new JSplitPane();   // JSplitPane 생성
		
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(70);
		hPane.setDividerSize(0);
		hPane.setEnabled(false);
		
		menuPanel = new MenuPanel();
		hPane.setLeftComponent(menuPanel);
		friendPanel = new FriendPanel(obui);
		hPane.setRightComponent(friendPanel);
		getContentPane().add(hPane, BorderLayout.CENTER);
	}
	
	public void SendObject(Object ob) {
		try {
			oos.writeObject(ob);
		} catch(IOException e) {
			System.out.println("SendObject Error");
		}
	}
}
