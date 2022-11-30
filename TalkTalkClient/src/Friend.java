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
   private JButton imageLabel;
   private JLabel userName;
   
   public Friend(TalkTalkClientView clientView, ImageIcon profileImage, String username) {
      setBackground(new Color(200, 200, 200));
      setSize(280, 100);
      setBounds(0,0,250,100);
      setLayout(null);
      
      this.username = username;
      userName = new JLabel(username);
      userName.setBounds(100,25,100,50);
      userName.setVisible(true);

      imageLabel = new JButton(profileImage);
      imageLabel.setBounds(20,20,60,60);
      imageLabel.setVisible(true);

      add(userName);
      add(imageLabel);
      
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
