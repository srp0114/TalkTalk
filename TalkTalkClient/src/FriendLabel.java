import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.ColorDefinition;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendLabel extends JPanel {
   Color yellow = new Color(254,240,27);

   public String username;
   public String message;
   public ImageIcon profileImage;
   
   private JLabel userName;
   private JLabel msgLabel;
   private JButton imageButton;

   public FriendLabel(ImageIcon profileImage, String username, String message) {      
      setLayout(null);
      setBackground(ColorDefinition.kakaoSkyblue);
      setBounds(0,0,150,100);
      setVisible(true);
      setPreferredSize(new Dimension(360, 56));
      this.username= username;
      this.message = message;
      this.profileImage = profileImage;
      
      userName = new JLabel(username);
      //userName.setHorizontalAlignment(SwingConstants.LEFT);
       //userName.setVerticalAlignment(SwingConstants.CENTER);
       userName.setBounds(55,1,50,20);
       userName.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
       userName.setVisible(true);
       this.add(userName);
       
       imageButton = new JButton(this.profileImage);
       
       //imageButton.setHorizontalAlignment(SwingConstants.LEFT);
       //imageButton.setVerticalAlignment(SwingConstants.CENTER);
       imageButton.setBounds(0,3,50,50);
       imageButton.setBorderPainted(false);
       imageButton.setFocusPainted(false);
       imageButton.setContentAreaFilled(false);
       imageButton.setOpaque(false);
       imageButton.setBorder(null);
       imageButton.setVisible(true);
       this.add(imageButton);
       
       msgLabel = new JLabel(message);
       //msgLabel.setHorizontalAlignment(SwingConstants.LEFT);
       //msgLabel.setVerticalAlignment(SwingConstants.CENTER);
       msgLabel.setBounds(55,28,100,20);
       msgLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
       msgLabel.setOpaque(true);
       msgLabel.setBackground(Color.WHITE);
       this.add(msgLabel);
       
       this.setVisible(true);
   }
   public void setMessage(String message) {
	   this.message = message;
	   msgLabel.setText(message);
   }
   
   
   public void setProfileImage(ImageIcon Image) {
      profileImage = Image;
      imageButton.setIcon(profileImage);   
   }
   
}
