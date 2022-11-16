import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String code;
	public ImageIcon profileImg;
	private int friendNumber = 0;
	private ArrayList<UserInfo> friendsUserInfo;
	private String searchFriend;
	
	public UserInfo(String username, String code) {
		this.username = username;
		this.code = code;		
	}
	
	
	// username
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
	
	public void setFriendsUserInfo(ArrayList<UserInfo> friendsUserInfo) {
		this.friendsUserInfo = friendsUserInfo;
	}
	public ArrayList<UserInfo> getFriendsUserInfo() {
		return friendsUserInfo;
	}
	
	// searchFrined
	public void setSearchFriend(String searchFriend) {
		this.searchFriend = searchFriend;
	}
	public String getSearchFriend() {
		return searchFriend;
	}
	
	
	public void addFriend(UserInfo friendInfo) {
		friendsUserInfo.add(friendInfo);
	}
	
	
}