import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ChatRoom extends JPanel{
   public int roomId = 0;
   public ImageIcon roomIcon;
   public String userlist = "";
   public int userCount = 0;
   public String[] users;
   public String usersWithoutMe;
   private String username;
   
   public ImageIcon userImages;
   private JLabel userImagesLabel;
   
   private String roomName = "";
   public JLabel roomNameLabel;
   private String lastMsg = "";
   public JLabel lastMsgLabel;
   
   private TalkTalkClientView clientView;

   public ChatRoom(TalkTalkClientView clientView, int roomId, String userlist) {
      setBackground(new Color(255, 255, 255));
       setLayout(null);
       setPreferredSize(new Dimension(300, 70));
       setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
         
       this.clientView = clientView;
   
      this.roomId = roomId;
      this.userlist = userlist;
      setUsers();
      
      userImages = new ImageIcon("src/no_profile.jpg");
      userImagesLabel = new JLabel(userImages);
      setChatRoomImage();
      
      roomNameLabel = new JLabel("<html>" + roomName + " <br>" + lastMsg + "</html>");
      roomNameLabel.setText(roomName);
      roomNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
      //roomNameLabel.setVerticalAlignment(SwingConstants.CENTER);
      roomNameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      roomNameLabel.setPreferredSize(new Dimension(100, 50));
      roomNameLabel.setVisible(true);
      
      lastMsgLabel = new JLabel("");
      lastMsgLabel.setText(lastMsg);
      lastMsgLabel.setHorizontalAlignment(SwingConstants.LEFT);
      //roomNameLabel.setVerticalAlignment(SwingConstants.CENTER);
      lastMsgLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      lastMsgLabel.setPreferredSize(new Dimension(100, 50));
      lastMsgLabel.setVisible(true);
      
      this.add(userImagesLabel);
      this.add(roomNameLabel);
      this.add(lastMsgLabel);
      this.setVisible(true);
   }
   
   
   
   public String getUserlist() {
      return userlist;
   }
   
   public void setUsers() {
      users = userlist.split(" ");
      setUserCount();
      
      username = clientView.username;
      
      
      for(int i = 0; i < users.length; i++) {
         if(users[i].equals(username))
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
   

   
   public void setUserCount() {
      for(int i = 0; i < users.length; i++) {
         userCount += 1;
      }
      System.out.println("채팅방 유저 수: " + userCount);
   }
   
   public void setChatRoomImage() {
      if(userCount == 2) {
         for(int i = 0; i < clientView.FriendVector.size(); i++) {
            Friend f = clientView.FriendVector.get(i);
            if(f.username.equals(users[1])) {
               userImagesLabel.setIcon(f.profileImage);
            }
         }
      }
   }
   
   public void setLastMsg(String msg) {
      this.lastMsg = lastMsg;
      lastMsgLabel.setText(lastMsg);
      repaint();
   }
}
