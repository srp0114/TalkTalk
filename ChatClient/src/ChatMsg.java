import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ChatMsg implements Serializable{
   private static final long serialVersionUID = 1L;
   private String username;
   private String code;
   public ImageIcon profileImg;
   private int friendNumber = 0;
   private ArrayList<ChatMsg> friendsUserInfo;
   private String searchFriend;
   
   private String id;
   private String data;
   public ImageIcon img;
   private String roomId;

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
   
   // Img
   public void setImg(ImageIcon profileImg) {
      this.profileImg = profileImg;
   }
   public ImageIcon getImg() {
      return profileImg;
   }
   
   // friendNumber
   public void setFriendNumber(int n) {
      this.friendNumber = n;
   }
   public int getFriendNumber() {
      return friendNumber;
   }
   
   // friendsUserInfo
   
   public void setFriendsUserInfo(ArrayList<ChatMsg> friendsUserInfo) {
      this.friendsUserInfo = friendsUserInfo;
   }
   public ArrayList<ChatMsg> getFriendsUserInfo() {
      return friendsUserInfo;
   }
   
   // searchFrined
   public void setSearchFriend(String searchFriend) {
      this.searchFriend = searchFriend;
   }
   public String getSearchFriend() {
      return searchFriend;
   }
   
   public void addFriend(ChatMsg friendInfo) {
      friendsUserInfo.add(friendInfo);
   }
   
   //Chatmsg
   public ChatMsg(String id, String code, String msg) {
      this.id = id;
      this.code = code;
      this.data = msg;
   }

   public String getData() {
      return data;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setData(String data) {
      this.data = data;
   }

   public void setChatImg(ImageIcon img) {
      this.img = img;
   }
   
}