package client;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

public class TalkTalkClientView extends JFrame{
	private JPanel contentPane;
	private String username;
	
	private MenuPanel menuPanel;  // 메뉴 패널
	private FriendPanel friendPanel;  // 친구창 패널

	public TalkTalkClientView(String username, String ip_addr, String port_no) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,390,600);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		this.username = username;
		
		setResizable(false);
		setVisible(true);
		
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
		friendPanel = new FriendPanel(username);
		hPane.setRightComponent(friendPanel);
		getContentPane().add(hPane, BorderLayout.CENTER);
	}
}
