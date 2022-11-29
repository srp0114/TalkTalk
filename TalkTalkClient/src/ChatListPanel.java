import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatListPanel extends JPanel{
	private TalkTalkClientView clientView;
	private ChatMsg chatMsg;
	
	private JLabel lblChating;
	
	private Image addChatroomIconImg = Toolkit.getDefaultToolkit().getImage("src/new-message.png");
	private ImageIcon addChatroomIcon;
	private JButton btnAddChatroom;
	
	
	public ChatListPanel(TalkTalkClientView clientView, ChatMsg chatMsg) {
		this.clientView = clientView;
		this.chatMsg = chatMsg;
		
		this.setBackground(new Color(255, 255, 255));
		setLayout(null);
		setSize(315, 562);
		
		UIinit();
	}
	
	
	public void UIinit() {
		lblChating = new JLabel("채팅");
		lblChating.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblChating.setBounds(23, 20, 50, 50);
		this.add(lblChating);
		
		addChatroomIconImg = addChatroomIconImg.getScaledInstance(25, 25,  Image.SCALE_SMOOTH);
		addChatroomIcon = new ImageIcon(addChatroomIconImg);
		btnAddChatroom = new JButton(addChatroomIcon);
		btnAddChatroom.setBorderPainted(false);
		btnAddChatroom.setFocusPainted(false);
		btnAddChatroom.setContentAreaFilled(false);
		btnAddChatroom.setOpaque(false);
		btnAddChatroom.setBounds(267, 32, 25, 25);
		this.add(btnAddChatroom);
		
	}
}
