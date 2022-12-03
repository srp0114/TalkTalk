import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;


public class ChatListPanel extends JPanel{
	private TalkTalkClientView clientView;
	private ChatMsg chatMsg;
	
	private JLabel lblChating;
	
	private Image addChatroomIconImg = Toolkit.getDefaultToolkit().getImage("src/new-message.png");
	private ImageIcon addChatroomIcon;
	private JButton btnAddChatroom;
	public MakeRoomFrame makeRoomFrame;
	
	public ChatListHeaderPanel chatListHeaderPanel;
	public ChatListScrollPane chatListScrollPane;

	public ChatListPanel(TalkTalkClientView clientView, ChatMsg chatMsg) {
		this.clientView = clientView;
		this.chatMsg = chatMsg;
		
		this.setBackground(new Color(255, 255, 255));
		setSize(310, 562);
		setLayout(null);
		
		chatListHeaderPanel = new ChatListHeaderPanel();
		chatListHeaderPanel.setBounds(0, 0, 310,80);
		chatListHeaderPanel.setVisible(true);
		this.add(chatListHeaderPanel);
		
		chatListScrollPane = new ChatListScrollPane();
		//friendListScrollPane.setLocation(0, 152);
		chatListScrollPane.setBounds(0,81, 305, 410);
		chatListScrollPane.setVisible(true);
		this.add(chatListScrollPane);
		
		setVisible(true);
	}
	
	

	class ChatListHeaderPanel extends JPanel{
		public ChatListHeaderPanel() {
			setSize(310, 100);
			setLayout(null);
			this.setBackground(new Color(255,255,255));
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
			
			btnAddChatroom.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent arg0) {
	    			makeRoomFrame = new MakeRoomFrame(clientView, chatMsg);
	            }
	        });
		}
	
	}
	
	class ChatListScrollPane extends JScrollPane{
		private JTextPane textPaneChatList;
		
		public ChatListScrollPane() {
			this.setBackground(new Color(255,255,255));  // 배경색: 흰색
			this.setLayout(null);
			this.setSize(305,410);
			
			setBorder(null);
			
			setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.textPaneChatList = new JTextPane();
			
			this.textPaneChatList.setBounds(3, 5, 300, 400);
			this.textPaneChatList.setBackground(new Color(255, 255, 255));
			this.textPaneChatList.setEditable(false);
			
			this.setViewportView(textPaneChatList);
			this.add(textPaneChatList);
			this.setVisible(true);
		}
		
		public void updateChatRoomList(ChatRoom chatRoom) {
			System.out.println("updateChatRoomList 함수 코드 시작");
			try {
				textPaneChatList.getDocument().insertString(textPaneChatList.getDocument().getLength(), " ", null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textPaneChatList.insertComponent(chatRoom);
			textPaneChatList.setCaretPosition(textPaneChatList.getDocument().getLength());
			textPaneChatList.setCaretPosition(0);
			OpenChatRoomAction openChatRoomAction = new OpenChatRoomAction(chatRoom.roomId);
			chatRoom.addMouseListener(openChatRoomAction);
			repaint();			
		}
		
		class OpenChatRoomAction implements	MouseListener {
			int roomId;
			public OpenChatRoomAction(int roomId) {
				this.roomId = roomId;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				for(int i = 0; i < clientView.RoomFrameVector.size(); i++) {
					ChatRoomFrame crf = clientView.RoomFrameVector.get(i);
					if(crf.roomId == this.roomId) {
						crf.setVisible(true);
					}
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i = 0; i < clientView.RoomFrameVector.size(); i++) {
					ChatRoomFrame crf = clientView.RoomFrameVector.get(i);
					if(crf.roomId == this.roomId) {
						crf.setVisible(true);
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		}
	}
}
