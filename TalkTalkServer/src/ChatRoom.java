import java.util.ArrayList;

// 채팅방id와 user 정보가 담긴 클래스
public class ChatRoom {
	private int roomId;
	private String userlist;
	public ChatRoom(int roomId, String userlist) {
		this.roomId = roomId;
		this.userlist = userlist;
	}
	public void addUser(String username) {
		this.userlist += " " + username;
	}
	public String[] getUserlist(){
		return userlist.split(" ");
	}
}
