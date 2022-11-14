package client;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class UserInfo implements Serializable{
	private String username;
	private String code;
	private ImageIcon profileImg;
	private int friendNumber = 0;
	private ArrayList<UserInfo> friendsUserInfo;
	private String searchFriend;
	
	public UserInfo(String username, String code) {
		this.username = username;
		this.code = code;		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setImg(ImageIcon profileImg) {
		this.profileImg = profileImg;
	}
	
	public void setFriendNumber(int n) {
		this.friendNumber = n;
	}
	public int getFriendNumber() {
		return friendNumber;
	}
	public void setFriendsUserInfo(ArrayList<UserInfo> friendsUserInfo) {
		this.friendsUserInfo = friendsUserInfo;
	}
	public ArrayList<UserInfo> getFriendsUserInfo() {
		return friendsUserInfo;
	}
	
	public void setSearchFriend(String searchFriend) {
		this.searchFriend = searchFriend;
	}
}
