import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class MakeRoomFrame extends JFrame {
	private TalkTalkClientView clientView = null;
	private ChatMsg chatMsg;
	private ChatMsg friendUserInfo;
	private JScrollPane scrollPane;
	private JPanel contentPane;
	private JTextPane textPane;
	private JButton okBtn;
	
	public MakeRoomFrame(TalkTalkClientView clientView, ChatMsg chatMsg) {
		this.clientView = clientView;
		this.chatMsg = chatMsg;
		this.setSize(300,390);
		this.setTitle("대화상대 선택");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(251, 252, 255));
        
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
        //JCheckBox cb = new JCheckBox("버튼 비활성화");
        //JCheckBox cb1 = new JCheckBox("버튼 감추기");
        //JButton btn = new JButton("확인");
            
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0,0,300,325);
		contentPane.add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setBounds(0,0,300,325);
		textPane.setEditable(true);
		scrollPane.setViewportView(textPane);

		okBtn = new JButton("확인");
		okBtn.setBackground(new Color(252, 255, 255));
		
		okBtn.setBounds(215,328,80,30);
		contentPane.add(okBtn);
		
        for(int i = 0; i < clientView.FriendVector.size();i++) {
            Friend f1 = clientView.FriendVector.elementAt(i);
            System.out.println("??이름: " + f1.getUsername());
            
            //Friend f2 = new Friend(f1.getUsername());
            //f2.setBounds(10,10,100,100);
            //textPane.insertComponent(f2);
         }  
        
        setVisible(true);
	}
	

	
}

