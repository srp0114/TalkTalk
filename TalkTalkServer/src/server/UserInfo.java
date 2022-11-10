package server;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class UserInfo {
	private String username;
	private ImageIcon profileImg;
	private String code;
	private int friendNumber = 0;
	private ArrayList<UserInfo> friendsUserInfo;
	
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
	
	public int getFriendNumber() {
		return friendNumber;
	}
	
	public ArrayList<UserInfo> getFriendsUserInfo() {
		return friendsUserInfo;
	}
}
