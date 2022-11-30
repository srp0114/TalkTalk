import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class ChatMsg implements Serializable{
   private static final long serialVersionUID = 1L;
   private String username;
   private String code;
   private String userlist;
   private String roomId;
   
   private Image profile;
   public ImageIcon profileImg;
   private int friendNumber = 0;
   private String searchFriend;
   private String friendlist;
   private String roomlist;
   
   private String msg;
   public ImageIcon img;
   public ImageIcon emoji;
   
   public ChatMsg(String username, String code) {
      this.username = username;
      this.code = code;      
      Image profile = Toolkit.getDefaultToolkit().getImage("src/noProfileImg.jpg");
      profile = profile.getScaledInstance(50, 50,  Image.SCALE_SMOOTH);
      profileImg = new ImageIcon(profile);
   }

   public ChatMsg(String username, String code, String msg) {
      this.username = username;
      this.code = code;
      this.msg = msg;
      Image profile = Toolkit.getDefaultToolkit().getImage("src/noProfileImg.jpg");
      profile = profile.getScaledInstance(50, 50,  Image.SCALE_SMOOTH);
      profileImg = new ImageIcon(profile);
   }
   
   //userName
   public void setUsername(String username) {
      this.username = username;
   }
   public String getUsername() {
      return username;
   }
   
   // code
   public void setCode(String code) {
      this.code = code;
   }
   public String getCode() {
      return code;
   }

   // userlist
   public void setUserlist(String userlist) {
      this.userlist = userlist;
   }
   public String getUserlist() {
      return userlist;
   }
   
   // friendNumber
   public void setFriendNumber(int n) {
      this.friendNumber = n;
   }
   public int getFriendNumber() {
      return friendNumber;
   }
 
   // searchFrined
   public void setSearchFriend(String searchFriend) {
      this.searchFriend = searchFriend;
   }
   public String getSearchFriend() {
      return searchFriend;
   }
   
   // friendlist
   public void setFriendlist(String friendlist) {
      this.friendlist = friendlist;
   }
   public String getFriendlist() {
      return friendlist;
   }
   
   // roomlist
   public void setRoomlist(String roomlist) {
      this.roomlist = roomlist;
   }
   public String getRoomlist() {
      return roomlist;
   }
   
   // profileImg
   public void setProfileImg(ImageIcon profileImg) {
      this.profileImg = profileImg;
   }

   // msg
   public void setMsg(String msg) {
     this.msg = msg;
   }
   public String getMsg() {
      return msg;
   }
   
   // img
   public void setImg(ImageIcon img) {
      this.img = img;
   }
   
   // imoji
   public void setEmoji(ImageIcon imoji) {
      this.emoji = emoji;
   }
}