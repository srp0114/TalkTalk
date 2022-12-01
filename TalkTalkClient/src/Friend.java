import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Friend extends JPanel{
   private TalkTalkClientView clientView = null;
   
   private String username ="";
   private ImageIcon profileImage;
   
   //private JButton imageLabel;
   private JButton imageButton;
   private JLabel userName;
   
   public Friend(ImageIcon profileImage, String username) {
      setBackground(new Color(255, 255, 255));
      setSize(290, 60);
      setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
      
      //this.clientView = clientView;
      this.username = username;
      this.profileImage = profileImage;
      
      userName = new JLabel(username);
      userName.setHorizontalAlignment(SwingConstants.LEFT);
      //userName.setHorizontalAlignment(SwingConstants.CENTER);
      //userName.setBounds(55,12,100,50);
      userName.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      userName.setVisible(true);

      imageButton = new JButton(profileImage);
      imageButton.setHorizontalAlignment(SwingConstants.LEFT);
      //imageButton.setHorizontalAlignment(SwingConstants.LEFT);
      imageButton.setBorderPainted(false);
      imageButton.setFocusPainted(false);
      imageButton.setContentAreaFilled(false);
      imageButton.setOpaque(false);
      imageButton.setBorder(null);
      //imageButton.setBounds(15,10,50,50);
      imageButton.setVisible(true);

      this.add(imageButton);
      this.add(userName);
      this.setVisible(true);
   }
   
   public String getUsername() {
      return username;
   }
}
