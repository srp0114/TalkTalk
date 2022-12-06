import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
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
import java.util.Vector;

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

import util.ColorDefinition;

public class ChatRoomFrame extends JFrame {
   
private static final long serialVersionUID = 1L;
   private TalkTalkClientView clientView;
   private JPanel contentPane;
   private JTextField txtInput;
   private String UserName;
   private String roomName = "";
   private JButton sendBtn;
   
   private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
   private JTextPane textPane;
   private Frame frame;
   private FileDialog fd;
   private JButton imgBtn;
   private JButton listBtn;
   public int roomId;
   public String userlist;
   public String[] users;
   public Vector<FriendLabel> FriendLabelVector = new Vector();
   public ChatRoom chatRoom;
   
   ImageIcon File = new ImageIcon("src/file.png");
   
   Color skyblue = new Color(186, 206, 224);
   Color yellow = new Color(254,240,27);

   public ChatRoomFrame(int roomId, TalkTalkClientView clientView, String userlist, ChatRoom chatRoom) {
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      this.clientView = clientView;
      this.roomId = roomId;
      this.userlist =userlist;
      this.chatRoom = chatRoom;
      setUsers();
      setRoomName();
      UserName = clientView.obcm.getUsername();
      setBounds(500, 100, 390, 600);
      contentPane = new JPanel();
      contentPane.setBackground(Color.WHITE);
      setContentPane(contentPane);      
      contentPane.setLayout(null);
      
      JLabel topLabel = new JLabel();
      topLabel.setBounds(0, 0, 386, 45);
      topLabel.setOpaque(true);
      topLabel.setBackground(skyblue);
      contentPane.add(topLabel);

      JLabel friendName = new JLabel();
      friendName.setBounds(12,8,311,25);
      friendName.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      friendName.setText(roomName);
      topLabel.add(friendName);
      
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(0, 45, 377, 410);
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      contentPane.add(scrollPane);
   
      textPane = new JTextPane();
      textPane.setEditable(false);
      textPane.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      textPane.setBounds(0, 45, 377, 410);
      textPane.setBackground(skyblue);      
      scrollPane.setViewportView(textPane);

      txtInput = new JTextField() { //텍스트필드 border 삭제
            @Override
            public void setBorder(Border border) {
               
            }
        };
        
      txtInput.setBounds(23, 460, 333, 50);
      txtInput.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      contentPane.add(txtInput);
      txtInput.setColumns(0);

      sendBtn = new JButton("전송");
      sendBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      sendBtn.setBounds(305, 523, 59, 30);
      sendBtn.setForeground(new Color(0, 0, 0));
      sendBtn.setBackground(ColorDefinition.kakaoYello);
      sendBtn.setBorderPainted(false);
      sendBtn.setFocusPainted(false);
      contentPane.add(sendBtn);

      imgBtn = new JButton(File);
      imgBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
      imgBtn.setBounds(12, 534, 16, 16);
      imgBtn.setBorderPainted(false);
      imgBtn.setFocusPainted(false);
      imgBtn.setContentAreaFilled(false);
      
      contentPane.add(imgBtn);

      TextSendAction action = new TextSendAction();
      sendBtn.addActionListener(action);
      txtInput.addActionListener(action);
      txtInput.requestFocus();
      
      ImageSendAction action2 = new ImageSendAction();
      imgBtn.addActionListener(action2);
   } 
   
   public void setUsers() {
     this.users = userlist.split(" ");
   }
      
   public void setRoomName() {
      UserName = clientView.username;
      
      for(int i = 0; i < users.length; i++) {
         if(users[i].equals(UserName))
            continue;
         else{
            roomName += users[i];
            if(i < users.length - 1) {
               roomName +=  ", ";
            }   
         }
         
      }   
      if (roomName.endsWith(", ")) {
           roomName = roomName.substring(0, roomName.length() - 2);
       }
   }
      
   class TextSendAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == sendBtn || e.getSource() == txtInput) {
            String msg = "";
            msg = txtInput.getText();            
            
            ChatMsg obcm = new ChatMsg(roomId, UserName, "200", msg);
            obcm.setProfileImg(clientView.profile);
            obcm.setUserlist(userlist);
            System.out.println("입력:" + msg + UserName);
            clientView.SendObject(obcm);
            
            txtInput.setText(""); 
            txtInput.requestFocus();
         }
      }
   }

   class ImageSendAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == imgBtn) {
            frame = new Frame("이미지첨부");
            fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
            fd.setVisible(true);
            
            ChatMsg obcm = new ChatMsg(roomId, UserName, "201", "IMG");
            ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
            obcm.setImg(img);
            clientView.SendObject(obcm);
         }
      }
   }
   
   public void AppendText(ChatMsg cm) {
      System.out.println(cm.getMsg());
      String msg = cm.getMsg();
      msg = msg.trim() + "\n";

      FriendLabel friend = new FriendLabel(cm.profileImg, cm.getUsername(), msg);
      FriendLabelVector.add(friend);
      friend.setProfileImage(cm.profileImg);
      
      textPane.setCaretPosition(textPane.getDocument().getLength());
      textPane.insertComponent(friend);
      textPane.replaceSelection("\n");
      repaint();
      chatRoom.setLastMsg(msg);
   }
   
   public void AppendTextUser(ChatMsg cm) {
      String msg = cm.getMsg().trim() + "\n";
      msg = msg.trim() + "\n";

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
         e.printStackTrace();
      }
      textPane.setCaretPosition(len);
      textPane.replaceSelection("\n");
      repaint();
      chatRoom.setLastMsg(msg);
   }
   
   public void AppendImage(ChatMsg cm) {
      ImageIcon ori_icon = cm.img;
      int len = textPane.getDocument().getLength();

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
         
      textPane.setCaretPosition(len); // place caret at the end (with no selection)
      Image ori_img = ori_icon.getImage();
      int width, height;
      double ratio;
      width = ori_icon.getIconWidth();
      height = ori_icon.getIconHeight();

      if (width > 200 || height > 200) {
         if (width > height) { 
            ratio = (double) height / width;
            width = 200;
            height = (int) (width * ratio);
         } else { 
            ratio = (double) width / height;
            height = 200;
            width = (int) (height * ratio);
         }
         Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
         ImageIcon new_icon = new ImageIcon(new_img);
         textPane.insertIcon(new_icon);
      } else
         textPane.insertIcon(ori_icon);
      len = textPane.getDocument().getLength();
       doc.setParagraphAttributes(doc.getLength(), 1, left, false);
      textPane.setCaretPosition(len);
      textPane.setCaretPosition(textPane.getDocument().getLength());
      //textPane.replaceSelection("\n");
      textPane.setCaretPosition(0);
      repaint();
   }
   
   public void AppendImageUser(ChatMsg cm) {
      ImageIcon ori_icon = cm.img;
      int len = textPane.getDocument().getLength();

      StyledDocument doc = textPane.getStyledDocument();
       SimpleAttributeSet right = new SimpleAttributeSet();
       StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
         
      textPane.setCaretPosition(len); // place caret at the end (with no selection)
      Image ori_img = ori_icon.getImage();
      int width, height;
      double ratio;
      width = ori_icon.getIconWidth();
      height = ori_icon.getIconHeight();

      if (width > 200 || height > 200) {
         if (width > height) { 
            ratio = (double) height / width;
            width = 200;
            height = (int) (width * ratio);
         } else { 
            ratio = (double) width / height;
            height = 200;
            width = (int) (height * ratio);
         }
         Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
         ImageIcon new_icon = new ImageIcon(new_img);
         textPane.insertIcon(new_icon);
      } else
         textPane.insertIcon(ori_icon);
      len = textPane.getDocument().getLength();
       doc.setParagraphAttributes(doc.getLength(), 1, right, false);
      textPane.setCaretPosition(len);
      textPane.setCaretPosition(textPane.getDocument().getLength());
      //textPane.replaceSelection("\n");
      textPane.setCaretPosition(0);
      repaint();
   }

}
