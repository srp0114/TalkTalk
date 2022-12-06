import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import util.ColorDefinition;

public class MakeRoomFrame extends JFrame {
   private TalkTalkClientView clientView = null;
   private ChatMsg chatMsg;
   private ChatMsg friendUserInfo;
   private JScrollPane scrollPane;
   private JPanel contentPane;
   private JTextPane textPane;
   private JButton okBtn;
   private StyledDocument document;
   private Friend f1;
   public String userList ="";
   public Vector<Friend> SelectVector = new Vector<Friend>();

   
   public MakeRoomFrame(TalkTalkClientView clientView, ChatMsg chatMsg) {
      this.clientView = clientView;
      this.chatMsg = chatMsg;
      this.setSize(300,390);
      this.setBounds(147, 205, 300, 390);

      this.setTitle("대화상대 선택");

      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(251, 252, 255));
        
      setContentPane(contentPane);
      contentPane.setLayout(null);
            
      scrollPane = new JScrollPane();
      scrollPane.setBounds(0,0,290,300);
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      contentPane.add(scrollPane);
      
      textPane = new JTextPane();
      textPane.setBounds(0,0,290,300);
      textPane.setEditable(false);
      scrollPane.setViewportView(textPane);

      okBtn = new JButton("확인");
      okBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
      okBtn.setBounds(205,310,70,30);      
      okBtn.setForeground(new Color(0, 0, 0));
      okBtn.setBackground(ColorDefinition.kakaoYello);
      okBtn.setBorderPainted(false);
      okBtn.setFocusPainted(false);
      contentPane.add(okBtn);
      
      userList = chatMsg.getUsername();
      
        for(int i = 0; i < clientView.FriendVector.size();i++) {
         Friend f = clientView.FriendVector.elementAt(i);
         f1 = new Friend(f.profileImage, f.username);
         f1.SetSelect(true);
         
         SelectVector.add(f1);
         System.out.println("===================");
            System.out.println(f1.getUsername());    
            try {
				textPane.getDocument().insertString(textPane.getDocument().getLength(), " ", null);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            //textPane.add(f1);
            textPane.insertComponent(f1);
            textPane.setCaretPosition(textPane.getDocument().getLength());
			//textPaneFriendList.replaceSelection("\n");
			textPane.setCaretPosition(0);
            repaint();
         }  
        setVisible(true);
 
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               f1.SetSelect(false);
               dispose();
            }
            
        }
   );     
        
        okBtn.addActionListener(event -> {
          
            for(int i = 0; i < SelectVector.size();i++) {
                Friend friendList = SelectVector.elementAt(i);
                if(friendList.isChecked == true) {
                   userList += " " + friendList.username;
                }
                
            }
               
           System.out.println("선택된 친구들" + userList);
           ChatMsg cm = new ChatMsg(chatMsg.getUsername(), "400");
           cm.setUserlist(userList);
           clientView.SendObject(cm);
        });   
   }   
}


