import java.io.Serializable;
import javax.swing.ImageIcon;

public class ChatMsg implements Serializable{
   private static final long serialVersionUID = 1L;
   private String username;
   private String code;
   private String userlist;
   private String roomId;
   public ImageIcon profileImg;
   private int friendNumber = 0;
   private String searchFriend;
   private String friendlist;
   private String roomlist;
   
   private String msg;
   public ImageIcon img;
   public ImageIcon imoji;
   

   public ChatMsg(String username, String code) {
      this.username = username;
      this.code = code;      
   }
   
   //userName
   public String getUsername() {
      return username;
   }
   public void setUsername(String username) {
      this.username = username;
   }
   
   // code
   public String getCode() {
      return code;
   }
   public void setCode(String code) {
      this.code = code;
   }
   
   // userlist
   public String getUserlist() {
	   return userlist;
   }
   public void setUserlist(String userlist) {
	   this.userlist = userlist;
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
   public ImageIcon getProfileImg() {
	   return profileImg;
   }

   
   // msg
   public String getMsg() {
      return msg;
   }
   public void setMsg(String msg) {
	  this.msg = msg;
   }

   
   // img
   public void setImg(ImageIcon img) {
      this.img = img;
   }
   public ImageIcon getImg() {
	      return img;
   }
   
   
   // imoji
   public void setImoji(ImageIcon imoji) {
      this.imoji = imoji;
   }
   public ImageIcon getImoji() {
	      return imoji;
   }
}