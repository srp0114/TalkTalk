import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class TalkTalkMainServer extends JFrame {

	private JPanel contentPane;
	JTextArea textArea;
	private int portNumber = 30000;
	
	private ServerSocket socket;
	private Socket client_socket;
	private Vector UserVec = new Vector();
	private Vector<ChatMsg> userInfos = new Vector();
	private static final int BUF_LEN = 128;
	
	private Vector<Friend> FriendVector = new Vector();
	private Vector<Room> RoomVector = new Vector();

	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkTalkMainServer frame = new TalkTalkMainServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 생성자
	public TalkTalkMainServer(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 338, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 300, 298);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JButton btnServerStart = new JButton("Server Start");
		btnServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new ServerSocket(portNumber);
				} catch(IOException e1) {
					e1.printStackTrace();
				}
				AppendText("Server Running..");
				btnServerStart.setText("Server Running..");
				btnServerStart.setEnabled(false);
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}			
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);
		
		
	}
	
	// 텍스트 업로드하기
	public void AppendText(String str) {
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}
	
	// 
	public void AppendObject(ChatMsg chatMsg) {
		//textArea.append("사용자로부터 들어온 object: " + str + "\n");
		textArea.append("code = " + chatMsg.getCode() + "\n");
		textArea.append("id = " + chatMsg.getUsername() +"\n");
		textArea.setCaretPosition(textArea.getText().length());
	}
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while(true) {
				try {
					AppendText("Waiting new clients..." );
					client_socket = socket.accept();
					AppendText("새로운 user from " + client_socket);
					
					// User 당 하나씩 Thread 생성
					UserService new_user = new UserService(client_socket);
					UserVec.add(new_user);   // 로그인한 user 추가
					new_user.start();
					AppendText("현재 user 수 " + UserVec.size());
					
				}catch(IOException e) {
					AppendText("accept() error");
				}
			}
		}
	}
	
	// User 당 생성되는 스레드
	class UserService extends Thread {
		
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		
		private Socket client_socket;
		private Vector user_vc;
		public String username="";
		public String searchFriendName="";
		int i = 0;
		
		public UserService(Socket client_socket) {
			this.client_socket = client_socket;
			this.user_vc = UserVec;
			try {
				oos = new ObjectOutputStream(client_socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(client_socket.getInputStream());
			} catch(Exception e) {
				AppendText("UserService error");
			}
		}
		
		public synchronized void run() {
			while(true) {
				try {
					Object obcm = null;
					ChatMsg cm = null;
					if(socket == null) {
						System.out.println("socket is null");
						break;
					}
					
					try {
						obcm = ois.readObject();
						System.out.println("obui success read");
					}catch(ClassNotFoundException e) {
						e.printStackTrace();
						return;
					}
					
					if(obcm == null) {
						System.out.println("obui is null");
						break;
					}
					
					if(obcm instanceof ChatMsg) {
						cm = (ChatMsg)obcm;
						System.out.println(cm.getUsername());
						System.out.println(cm.getCode());
					} else
						continue;
					System.out.println("실제 받아온 프로토콜: " + cm.getCode());
					
					if(cm.getCode().matches("100")) { // 로그인
						username = cm.getUsername();
						userInfos.add(cm);
						Login();
					}
					else if(cm.getCode().matches("302")) { // 친구 검색
						System.out.println("cm.getCode() matches 302");
						searchFriendName = cm.getSearchFriend();
						System.out.println(searchFriendName);
						SearchFriend();
					}
					else if(cm.getCode().matches("303")) {
						System.out.println("cm.getCode() matches 303");
						String username = cm.getUsername();
						String friendName = cm.getSearchFriend();
						boolean isExist = false;
						
						if(FriendVector.size() < 1) {
							System.out.println("FriendVector.size()" + FriendVector.size());
							FriendVector.add(new Friend(username, friendName));
							FriendVector.add(new Friend(friendName, username));
						}
						else {
							for(i = 0; i < FriendVector.size(); i++) {
								Friend user = FriendVector.get(i);
								if(user.username.equals(username)) {
									user.addFriend(cm.getSearchFriend());
								}
								if(user.username.equals(friendName)) {
									isExist = true;
									if(user.friendlist.contains(friendName)) {
										break;
									}
									else {
										user.addFriend(username);
										FriendVector.set(i, user);
									}
								}
								
								if(isExist == false) {
									FriendVector.add(new Friend(friendName, username));
								}
							}
						}
						for(int i = 0; i < FriendVector.size();i++) {
							Friend user = FriendVector.get(i);
							System.out.println("이름: " + user.username);
							System.out.println("친구 목록:" + user.friendlist);
						}
						for(int i = 0; i < userInfos.size(); i++) {
							ChatMsg userInfo = userInfos.get(i);
							if(userInfo.getUsername().equals(username)) {
								ChatMsg cm2 = new ChatMsg(userInfo.getUsername(), "303");
								cm2.setProfileImg(userInfo.profileImg);
								WriteOther(cm2, friendName);
							}
							else if(userInfo.getUsername().equals(friendName)) {
								ChatMsg cm3 = new ChatMsg(userInfo.getUsername(), "303");
								cm3.setProfileImg(userInfo.profileImg);
								WriteObject(cm3);
							}
						}
						
						
					}
					
					
				} catch(IOException e) {
					AppendText("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						client_socket.close();
						Logout();
						break;
					} catch(Exception ee) {
						break;
					}
				}
			}
		}
		public void Login() {  // 로그인 100
			AppendText("새로운 User " + username + " 로그인");
		}
		public void Logout() {  // 로그아웃  101
			UserVec.removeElement(this);
			AppendText("User " + "[" + username + "] 로그아웃. 현재 User 수 " + UserVec.size());
		}
		
		public void SearchFriend() {  // 친구 검색 302
			System.out.println("SearchFriend function");
			AppendText("[" + username + "] searchFriend " + searchFriendName);
			for(int i = 0; i < userInfos.size(); i++) {
				ChatMsg userinfo = userInfos.get(i);
				if(userinfo.getUsername().equals(searchFriendName)) {
					AppendText("user들 중 " + userinfo.getUsername() + "검색됨.");
					ChatMsg searched = new ChatMsg(userinfo.getUsername(), "302");
					searched.setProfileImg(userinfo.profileImg);
					WriteObject(searched);
					System.out.println(searched.profileImg.toString());
				}
			}
		}
		
		// 특정 유저에게 ChatMsg 보내기
		public void WriteOther(Object ob, String name) {
			// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.username.equals(name))
					user.WriteObject(ob);
			}
		}
		
		
		
		public void WriteObject(Object ob) {
			try {
				oos.writeObject(ob);
			}catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}	
	}
}