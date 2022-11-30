
public class Friend {
	String username;
	String friendlist = "";
	public Friend(String username, String friendlist) {
		this.username = username;
		this.friendlist = friendlist;
	}
	public void addFriend(String friendName) {
		this.friendlist += " " + friendName;
	}
	
	
}
