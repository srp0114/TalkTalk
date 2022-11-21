import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatListPanel extends JPanel{
	private TalkTalkClientView clientView;
	private ChatMsg chatMsg;
	
	private JLabel lblChating;
	
	
	
	
	public ChatListPanel(TalkTalkClientView clientView, ChatMsg chatMsg) {
		this.clientView = clientView;
		this.chatMsg = chatMsg;
		
		this.setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		UIinit();
	}
	
	
	public void UIinit() {
		lblChating = new JLabel("채팅");
		lblChating.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblChating.setBounds(23, 20, 50, 50);
		this.add(lblChating);
	}
}
