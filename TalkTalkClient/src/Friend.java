import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

public class Friend extends JPanel{
   private TalkTalkClientView clientView = null;
   
   private String username ="";
   private ImageIcon profileImage;
   
   //private JButton imageLabel;
   private JLabel imageLabel;
   private JLabel userName;
   
   public Friend(ImageIcon profileImage, String username) {
      setBackground(new Color(0, 255, 255));
      setSize(280, 100);
      setLayout(null);
      
      //this.clientView = clientView;
      this.username = username;
      this.profileImage = profileImage;
      
      userName = new JLabel(username);
      userName.setBounds(100,25,100,50);
      userName.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      userName.setVisible(true);

      imageLabel = new JLabel(profileImage);
      imageLabel.setBounds(20,20,50,50);
      imageLabel.setVisible(true);

      this.add(userName);
      this.add(imageLabel);
      this.setVisible(true);
      /*
      this.clientView = clientView;
      this.profileImage = profileImage;
      this.username = username;
      this.userName = new JLabel(username);
      
      userName.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      userName.setBounds(87, 57, 94, 15);
      userName.setVisible(true);
      add(userName);
      
      imageLabel = new JLabel(profileImage);
      imageLabel.setBounds(20, 20, 50, 50);
      imageLabel.setVisible(true);
      add(imageLabel);      
      */
   }
   
   public String getUsername() {
      return username;
   }
}
