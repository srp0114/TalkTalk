import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ChatRoom extends JPanel{
	public int roomId = 0;
	public ImageIcon roomIcon;
	public String userlist = "";
	public int userCount = 0;
	public String[] users;
	private String roomName;
	public JLabel roomNameLabel;
	
	TalkTalkClientView clientView;

	public ChatRoom(TalkTalkClientView clientView, int roomId, String userlist) {
		setBackground(new Color(255, 255, 255));
	      //setSize(290, 60);
	    setPreferredSize(new Dimension(300, 70));
	    setLayout(new FlowLayout(FlowLayout.LEFT, 15,10));
	      
	    this.clientView = clientView;
	
		this.roomId = roomId;
		this.userlist = userlist;
		setUsers();
		
		roomNameLabel = new JLabel(roomName);
		roomNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		roomNameLabel.setVerticalAlignment(SwingConstants.CENTER);
		roomNameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		roomNameLabel.setVisible(true);
		
		this.add(roomNameLabel);
		this.setVisible(true);
	}
	
	
	
	public String getUserlist() {
		return userlist;
	}
	public void setUsers() {
		users = userlist.split(" ");
		setUserCount();
		
		roomName = users[0];
		
		for(int i = 1; i < users.length; i++) {
			roomName += ", " + users[i] ;
		}
	}
	public void setUserCount() {
		for(int i = 0; i < users.length; i++) {
			userCount += 1;
		}
		System.out.println(userCount);
	}
	
}
