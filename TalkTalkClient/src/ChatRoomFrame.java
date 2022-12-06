import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChatRoomFrame extends JFrame {
	
private static final long serialVersionUID = 1L;
	private TalkTalkClientView clientView;
	private JPanel contentPane;
	private JTextField txtInput;
	private String UserName;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private JLabel lblUserName;
	// private JTextArea textArea;
	private JTextPane textPane;
	private JTextPane textArea1;
	
	private Frame frame;
	private FileDialog fd;
	private JButton imgBtn;
	private JButton listBtn;
	public int roomId;
	public String userlist;
	
	ImageIcon Emogi = new ImageIcon("src/emogi.PNG");
	ImageIcon File = new ImageIcon("src/file.png");
	ImageIcon List = new ImageIcon("src/emogi.PNG");
	
	Color skyblue = new Color(186, 206, 224);
	Color yellow = new Color(254,240,27);

	public ChatRoomFrame(int roomId, TalkTalkClientView clientView, String userlist) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.clientView = clientView;
		this.roomId = roomId;
		this.userlist =userlist;
		UserName = clientView.obcm.getUsername();
		setBounds(100, 100, 400, 640);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setBounds(0, 0, 380, 640);

		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		
		JLabel topLabel = new JLabel();
		topLabel.setBounds(0, 0, 366, 60);
		topLabel.setOpaque(true);
		topLabel.setBackground(skyblue);
		
		JLabel friendName = new JLabel();
		friendName.setBounds(65,8,311,25);
		friendName.setText(userlist);
		topLabel.add(friendName);
		contentPane.add(topLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 60, 367, 420);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
	
		textPane = new JTextPane();
		textPane.setEditable(true);
		textPane.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		textPane.setBounds(0, 60, 367, 420);
		textPane.setBackground(skyblue);
		
		scrollPane.setViewportView(textPane);

		txtInput = new JTextField() { //텍스트필드 테두리 삭제
            @Override
            public void setBorder(Border border) {
               
            }
        };
        
		txtInput.setBounds(23, 492, 343, 65);
		contentPane.add(txtInput);
		txtInput.setColumns(0);

		btnSend = new JButton("전송");
		btnSend.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btnSend.setBounds(297, 563, 59, 30);
		btnSend.setBorderPainted(false);
		btnSend.setBackground(yellow);
		contentPane.add(btnSend);

		lblUserName = new JLabel("Name");
		lblUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblUserName.setBackground(Color.WHITE);
		lblUserName.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(12, 539, 62, 40);
		setVisible(true);

		lblUserName.setText(UserName);

		imgBtn = new JButton(Emogi);
		imgBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		imgBtn.setBounds(0, 565, 40, 30);
		imgBtn.setBorderPainted(false);
		imgBtn.setFocusPainted(false);
		imgBtn.setContentAreaFilled(false);
		
		contentPane.add(imgBtn);
		
		
		listBtn = new JButton(List);
		listBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		listBtn.setBounds(310, 10, 40, 30);
		listBtn.setBorderPainted(false);
		listBtn.setFocusPainted(false);
		listBtn.setContentAreaFilled(false);
		
		topLabel.add(listBtn);
		
		TextSendAction action = new TextSendAction();
		btnSend.addActionListener(action);
		txtInput.addActionListener(action);
		txtInput.requestFocus();
	}
	
	
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = "";
				msg = txtInput.getText();				
				
				ChatMsg obcm = new ChatMsg(roomId, UserName, "200", msg);
				obcm.setUserlist(userlist);
				System.out.println("입력:" + msg + UserName);
				clientView.SendObject(obcm);
				
				txtInput.setText(""); 
				txtInput.requestFocus();
				if (msg.contains("/exit"))
					System.exit(0);
			}
		}
	}
	
	public void AppendText(ChatMsg cm) {
		System.out.println(cm.getMsg());
		String msg = cm.getMsg();
		msg = msg.trim() + "\n";

		FriendLabel friend = new FriendLabel();
		friend.setBackground(yellow);
		textPane.setCaretPosition(textPane.getDocument().getLength());
		textPane.insertComponent(friend);
		
		JLabel msgLabel = new JLabel();
		msgLabel.setBounds(10,10,100,100);
		msgLabel.setOpaque(true);
		msgLabel.setBackground(Color.red);
		textPane.insertComponent(msgLabel);

		int len = textPane.getDocument().getLength();

		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, Color.BLACK);
		
		if(msg!=null &&!msg.equals("")&&!msg.equals("\n"))
			StyleConstants.setBackground(left, Color.WHITE);
		
	    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
	    try {
			doc.insertString(doc.getLength(), msg + "\n", left );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    textPane.setCaretPosition(len);
	}
	
	public void AppendTextUser(ChatMsg cm) {
		String msg = cm.getMsg().trim() + "\n";
		msg = msg.trim() + "\n";

		FriendLabel friend = new FriendLabel();
		friend.setBounds(10,10,100,100);
		friend.setOpaque(true);
		friend.setBackground(Color.black);
		textPane.setCaretPosition(textPane.getDocument().getLength());
		textPane.insertComponent(friend);
		
		/*JLabel msgLabel = new JLabel();
		msgLabel.setBounds(100,100,100,100);
		msgLabel.setOpaque(true);
		msgLabel.setBackground(yellow);
		textPane.insertComponent(msgLabel);*/

		int len = textPane.getDocument().getLength();
		
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.BLACK);	
		
		if(msg!=null &&!msg.equals("")&&!msg.equals("\n"))
			StyleConstants.setBackground(right, yellow);
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
	    
		try {
			doc.insertString(doc.getLength(),msg+"\n", right );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		textPane.setCaretPosition(len);
	}

}

