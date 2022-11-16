import java.awt.Color;

import javax.swing.JPanel;

import util.ColorDefinition;


public class ChatListPanel extends JPanel{
	private TalkTalkClientView clientView;
	private UserInfo userInfo;
	public ChatListPanel(TalkTalkClientView clientView, UserInfo userInfo) {
		this.clientView = clientView;
		this.userInfo = userInfo;
		
		this.setBackground(new Color(255, 255, 255));
		setLayout(null);
	}
}

