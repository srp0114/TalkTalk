import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;


public class FriendListPanel extends JPanel{
	
	private JLabel lblFriend;  // 친구 레이블
	 
	private TalkTalkClientView clientView;
	private ChatMsg chatMsg;
	
	private String userName;  // 로그인한 client 이름
	private JLabel lblUserName;  // username 레이블
	
	private Image addFriendIconImg = Toolkit.getDefaultToolkit().getImage("src/addFriendIcon.png");
	private ImageIcon addFriendIcon;
	private JButton btnAddFriendIcon;
	
	//private Image profileImg = Toolkit.getDefaultToolkit().getImage("src/noProfileImg.jpg");
	private ImageIcon profileIcon = new ImageIcon("src/no_profile.jpg");
	private JButton btnProfileImg;
	
	public AddFriendFrame addFriendFrame;
	public FriendListHeaderPanel friendListHeaderPanel;
	public FriendListScrollPane friendListScrollPane;
	
	public FriendListPanel(TalkTalkClientView clientView, ChatMsg chatMsg) {  // 매개변수로 username 받는 생성자
		this.clientView = clientView;
		this.chatMsg = chatMsg;
		
		this.setBackground(new Color(255,255,255));
		setSize(310, 562);
		setLayout(null);
	
		friendListHeaderPanel = new FriendListHeaderPanel();
		friendListHeaderPanel.setBounds(0, 0, 310, 151);
		friendListHeaderPanel.setVisible(true);
		this.add(friendListHeaderPanel);
		
		friendListScrollPane = new FriendListScrollPane();
		friendListScrollPane.setLocation(0, 152);
		friendListScrollPane.setVisible(true);
		this.add(friendListScrollPane);
		
		setVisible(true);
	}	
	
	
	
	// 친구리스트패널의 헤더패널 
	class FriendListHeaderPanel extends JPanel{
		
		public FriendListHeaderPanel(){
			setSize(310, 151);
			setLayout(null);
			this.setBackground(new Color(255,255,255));  // 배경색: 흰색
			UIInit();
			
			AddFriendIconAction action = new AddFriendIconAction();
			btnAddFriendIcon.addActionListener(action);
			setVisible(true);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(234, 234, 234));
			g.drawLine(10, 150, 300, 150);
		}
		
		class AddFriendIconAction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				addFriendFrame = new AddFriendFrame(clientView, chatMsg);
			}
		}
		
		public void UIInit() {
			
			lblFriend = new JLabel("친구");
			lblFriend.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			lblFriend.setBounds(20, 20, 50, 50);
			this.add(lblFriend);	

			addFriendIconImg = addFriendIconImg.getScaledInstance(25, 25,  Image.SCALE_SMOOTH);
			addFriendIcon = new ImageIcon(addFriendIconImg);
			btnAddFriendIcon = new JButton(addFriendIcon);
			btnAddFriendIcon.setBorderPainted(false);
			btnAddFriendIcon.setFocusPainted(false);
			btnAddFriendIcon.setContentAreaFilled(false);
			btnAddFriendIcon.setOpaque(false);
			btnAddFriendIcon.setBounds(260, 32, 25, 25);
			this.add(btnAddFriendIcon);
			
			//profileImg = profileImg.getScaledInstance(50, 50,  Image.SCALE_SMOOTH);
			profileIcon = new ImageIcon("src/no_profile.jpg");
			chatMsg.setProfileImg(profileIcon);
			btnProfileImg = new JButton(chatMsg.profileImg);
			btnProfileImg.setBorderPainted(false);
			btnProfileImg.setFocusPainted(false);
			btnProfileImg.setContentAreaFilled(false);
			btnProfileImg.setOpaque(false);
			btnProfileImg.setBounds(20, 80, 50, 50);
			this.add(btnProfileImg);
			
			
			lblUserName = new JLabel(chatMsg.getUsername());
			lblUserName.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			lblUserName.setBounds(90, 75, 100, 50);
			this.add(lblUserName);
			
			
		}
	}
	
	class FriendListScrollPane extends JScrollPane{
		private StyledDocument document;
		private JTextPane textPaneFriendList;
		
		public FriendListScrollPane(){
			this.setBackground(new Color(255,255,255));  // 배경색: 흰색
			this.setLayout(null);
			this.setSize(305,410);
			
			setBorder(null);
			//setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.textPaneFriendList = new JTextPane();
			this.textPaneFriendList.setBounds(3, 20, 300, 400);
			this.textPaneFriendList.setBackground(new Color(200, 200, 200));
			this.textPaneFriendList.setEditable(false);
			//this.textPaneFriendList.setAlignmentY(1.0f);
			this.setViewportView(textPaneFriendList);
			this.add(textPaneFriendList);
			this.setVisible(true);
		}
		
		
		
		public void updateFriendList(Friend friend) {
			document = textPaneFriendList.getStyledDocument();
			SimpleAttributeSet sortMethod = new SimpleAttributeSet();
			//friend.setSize(getPreferredSize());
			System.out.println("updateFriendList 함수 코드 시작");
			textPaneFriendList.setCaretPosition(textPaneFriendList.getDocument().getLength());
			textPaneFriendList.insertComponent(friend);
			textPaneFriendList.setCaretPosition(0);
			repaint();
			
			/*
			for(int i = 0; i < clientView.FriendVector.size(); i++) {
				Friend friend = clientView.FriendVector.elementAt(i);
				textPaneFriendList.setCaretPosition(textPaneFriendList.getDocument().getLength());
				textPaneFriendList.insertComponent(friend);
				textPaneFriendList.setCaretPosition(0);
				System.out.println("friend: " + friend.getUsername());
				repaint();			
			}
			*/
		}
	}
	
}