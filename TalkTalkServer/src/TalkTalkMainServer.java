import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;
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
	private int roomId = 1000;
	
	//private Vector<Friend> FriendVector = new Vector();
	private Vector<ChatRoom> RoomVector = new Vector();

	
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
		public String username="";   // 유저 이름
		public ImageIcon profileImg = new ImageIcon("src/no_profile.jpg");  // 프로필 사진
		public String userStatus;
		public String searchFriendName="";
		int i = 0;
		public Vector<UserService> FriendList = new Vector<UserService>();
		public Vector<String> friendNames = new Vector<String>();
		public ChatMsg userInfo;
		
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
					String msg = null;
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
						AppendObject(cm);

					} else
						continue;
					System.out.println("실제 받아온 프로토콜: " + cm.getCode());
					
					if(cm.getCode().matches("100")) { // 로그인
						username = cm.getUsername();
						profileImg = cm.profileImg;
						userInfo = new ChatMsg(username, "100");
						userInfo.setProfileImg(profileImg);
						//userInfos.add(cm);
						Login();
					}
					else if (cm.getCode().matches("200")) {  // 메시지 보내기
						System.out.println("cm.getCode() matches 200");
                        msg = String.format("[%s] %s", cm.getUsername(), cm.getCode());
                        AppendText(msg); // server 화면에 출력
                        System.out.println(cm.getMsg());
      
                        for(int i = 0; i < RoomVector.size(); i++) {
                        	ChatRoom cr = RoomVector.get(i);
                        	int crRoomId = cr.roomId;
                        	System.out.println(cr.roomId);
                        	if(crRoomId == cm.getRoomId()) {
                        		System.out.println("roomId 일치함!");
                        		String[] users = cr.getUserlist();
                        		for(i = 0; i < user_vc.size(); i++) {
                        			UserService u = (UserService)user_vc.get(i);
                        			for(int j = 0; j < users.length; j++) {
                        				if(u.username.equals(users[j])) {
                        					System.out.println(u.username  + " = " + users[j]);
//                        					ChatMsg cm200 = new ChatMsg(cm.getUsername(), "200", cm.getMsg());
//                        					cm200.setRoomId(cm.getRoomId());
                        					u.WriteObject(cm);
                       					System.out.println(users[i]+"에게 msg:" + cm.getMsg() + "보냄!");
                      				}
                        			}
                        		}
                        	}
                        }
					}

					else if(cm.getCode().matches("301")) {  // 프로필 변경
						System.out.println("cm.getCode() matches 301");
						profileImg = cm.profileImg;
						for(i = 0; i < FriendList.size(); i++) {
							UserService u = (UserService)FriendList.get(i);
							ChatMsg newProfileCm = new ChatMsg(username, "301");
							newProfileCm.setProfileImg(profileImg);
							u.WriteObject(newProfileCm);
							WriteObject(newProfileCm);
						}
					}

					else if(cm.getCode().matches("302")) { // 친구 검색
						System.out.println("cm.getCode() matches 302");
						searchFriendName = cm.getSearchFriend();
						System.out.println(searchFriendName);
						SearchFriend();
					}
					else if(cm.getCode().matches("303")) {  // 친구 추가
						System.out.println("cm.getCode() matches 303");
						String userName = cm.getUsername();
						String friendName = cm.getSearchFriend();
						
						for(int i = 0; i < FriendList.size(); i++) {
							if(friendNames.contains(((UserService)FriendList.get(i)).username))
								continue;
							friendNames.add(((UserService)FriendList.get(i)).username);
						}
						
						
						
						if(friendNames.contains(friendName)) {
							System.out.println("Server(303): " + friendName+ "은 이미 친구!");
						}
						else {
							for(int i = 0; i < user_vc.size(); i++) {
								UserService u = (UserService)user_vc.get(i);
								if(u.username.equals(friendName)) {
									FriendList.add(u);
									friendNames.add(friendName);
									
									u.FriendList.add(this);
									u.friendNames.add(this.username);
									
									ChatMsg friendCm = new ChatMsg(friendName, "303");
									friendCm.setProfileImg(u.profileImg);
									WriteObject(friendCm);
									
									ChatMsg selfCm = new ChatMsg(username, "303");
									selfCm.setProfileImg(profileImg);
									u.WriteObject(selfCm);
								}
							}
						}
						
						System.out.println("유저 이름: "+ username);
						System.out.print("친구 이름들 ");
						for(int i = 0; i < friendNames.size(); i++) {
							System.out.print(friendNames.get(i));
						}		
						
					}
					else if(cm.getCode().matches("400")) { // 채팅방 만들기
						System.out.println("cm.getCode() matches 400");
						if(RoomVector.size() == 0) {
							ChatRoom chatRoom = new ChatRoom(roomId, cm.getUserlist());
							RoomVector.add(chatRoom);
							String[] userlist = chatRoom.getUserlist();
							for(i = 0; i < user_vc.size(); i++) {
								UserService u = (UserService)user_vc.get(i);
								for(int j = 0; j < userlist.length; j++) {
									if(u.username.equals(userlist[j])){
										ChatMsg rcm = new ChatMsg(u.username, "401");
										rcm.setRoomId(roomId);
										rcm.setUserlist(cm.getUserlist());
										u.WriteObject(rcm);
									}
								}
							}
						}
						else {
							roomId++;
							ChatRoom chatRoom = new ChatRoom(roomId, cm.getUserlist());
							RoomVector.add(chatRoom);
							String[] userlist = chatRoom.getUserlist();
							for(i = 0; i < user_vc.size(); i++) {
								UserService u = (UserService)user_vc.get(i);
								for(int j = 0; j < userlist.length; j++) {
									if(u.username.equals(userlist[j])){
										ChatMsg rcm = new ChatMsg(u.username, "401");
										rcm.setRoomId(roomId);
										rcm.setUserlist(cm.getUserlist());
										u.WriteObject(rcm);
									}
								}
							}
						}
						System.out.println(roomId);
						System.out.println("server userList: " + cm.getUserlist());
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

			for(int i = 0; i < user_vc.size(); i++) {
				UserService u = (UserService) user_vc.get(i);
				if(u.username.equals(searchFriendName)) {
					AppendText("user들 중 " + u.username + "검색됨.");
					ChatMsg searched = new ChatMsg(u.username, "302");
					searched.setProfileImg(u.profileImg);
					WriteObject(searched);
					System.out.println(searched.profileImg.toString());
				}
			}
		}
		
		// 모든 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
	      public void WriteAll(String str) {
	          for (int i = 0; i < user_vc.size(); i++) {
	              UserService user = (UserService) user_vc.elementAt(i);
	              if (user.userStatus == "O")
	                  user.WriteOne(str);
	          }
	      }

		
		  // 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
	      public void WriteAllObject(Object ob) {
	          for (int i = 0; i < user_vc.size(); i++) {
	              UserService user = (UserService) user_vc.elementAt(i);
	              if (user.userStatus == "O")
	                  user.WriteOneObject(ob);
	          }
	      }

		
		 public void WriteOthers(String str) {
	          for (int i = 0; i < user_vc.size(); i++) {
	              UserService user = (UserService) user_vc.elementAt(i);
	              if (user != this && user.userStatus == "O")
	                  user.WriteOne(str);
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
		public void WriteOne(String msg) {
	          try {
	              ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
	              oos.writeObject(obcm);
	          } catch (IOException e) {
	              AppendText("dos.writeObject() error");
	              try {
	                  ois.close();
	                  oos.close();
	                  client_socket.close();
	                  client_socket = null;
	                  ois = null;
	                  oos = null;
	              } catch (IOException e1) {
	                  // TODO Auto-generated catch block
	                  e1.printStackTrace();
	              }
	              Logout(); // 에러가난 현재 객체를 벡터에서 지운다
	          }
	      }

		
		
		public void WriteOneObject(Object ob) {
	          try {
	              oos.writeObject(ob);
	          }
	          catch (IOException e) {
	              AppendText("oos.writeObject(ob) error");
	              try {
	                  ois.close();
	                  oos.close();
	                  client_socket.close();
	                  client_socket = null;
	                  ois = null;
	                  oos = null;
	              } catch (IOException e1) {
	                  // TODO Auto-generated catch block
	                  e1.printStackTrace();
	              }
	              Logout();
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