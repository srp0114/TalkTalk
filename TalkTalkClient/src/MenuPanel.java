import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
   private Image profileIconImg = Toolkit.getDefaultToolkit().getImage("src/profileIcon.png");
   private ImageIcon profileIcon;
   private JButton btnprofileIcon;
   
   private Image chatIconImg = Toolkit.getDefaultToolkit().getImage("src/chatIcon.png");
   private ImageIcon chatIcon;
   private JButton btnchatIcon;
   
   TalkTalkClientView clientView;
   public static boolean isFriendListPanel;
   public static boolean isChatListPanel;
   public FriendListPanel flp = TalkTalkClientView.friendListPanel;
   public ChatListPanel clp = TalkTalkClientView.chatListPanel;
   
   
   public MenuPanel(TalkTalkClientView clientView) {
      this.clientView = clientView;
      this.setBackground(new Color(236, 236, 237));
      setLayout(null);
      setSize(70, 562);
      
      isFriendListPanel = true;
      isChatListPanel = true;
      
      uiInit();

      btnprofileIcon.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JButton b = (JButton)e.getSource();
            b.setEnabled(false);
            btnchatIcon.setEnabled(true);
            clientView.friendListPanel.setVisible(true);
            clientView.chatListPanel.setVisible(false);
//            if(isFriendListPanel == true) {
//               isFriendListPanel = false;
//               isChatListPanel = true;
//               clientView.replaceSplitPaneChild("flp");
//            }
         
         }      
      });
      
      btnchatIcon.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JButton b = (JButton)e.getSource();
            b.setEnabled(false);
            btnprofileIcon.setEnabled(true);
            clientView.chatListPanel.setVisible(true);
            clientView.friendListPanel.setVisible(false);
//            if(isChatListPanel == true) {
//               isChatListPanel = false;
//               isFriendListPanel = true;
//               clientView.replaceSplitPaneChild("clp");
//            }
         }
      });
      
   }
   
   public void uiInit() {
      profileIconImg = profileIconImg.getScaledInstance(28, 28,  Image.SCALE_SMOOTH);
      profileIcon = new ImageIcon(profileIconImg);
      btnprofileIcon = new JButton(profileIcon);
      btnprofileIcon.setBorderPainted(false);
      btnprofileIcon.setFocusPainted(false);
      btnprofileIcon.setContentAreaFilled(false);
      btnprofileIcon.setOpaque(false);
      btnprofileIcon.setBounds(20, 30, 28, 28);
      this.add(btnprofileIcon);
      
      chatIconImg = chatIconImg.getScaledInstance(28, 28,  Image.SCALE_SMOOTH);
      chatIcon = new ImageIcon(chatIconImg);
      btnchatIcon = new JButton(chatIcon);
      btnchatIcon.setBorderPainted(false);
      btnchatIcon.setFocusPainted(false);
      btnchatIcon.setContentAreaFilled(false);
      btnchatIcon.setOpaque(false);
      btnchatIcon.setBounds(20, 90, 28, 28);
      this.add(btnchatIcon);
   }
   
}