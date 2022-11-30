import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TalkTalkClientView extends JFrame{
	private static final long serialVersionUID = 1L;
	public static JPanel contentPane;
   private String username;  // username
   private String ip_addr;
   private String port_no;
   private ChatMsg obcm;      // obui
   
   public static JSplitPane hPane;

   private JButton btnprofileIcon;
   private JButton btnchatIcon;
   
   private static final int BUF_LEN = 128;
   public Socket socket;
   
   public ObjectInputStream ois;
   public ObjectOutputStream oos;
   public JPanel oldChild;
   public JPanel newChild;
   
   private MenuPanel menuPanel;  // 메뉴 패널
   public static FriendListPanel friendListPanel;  // 친구창 패널
   public static ChatListPanel chatListPanel;
   public Vector<Friend> FriendVector = new Vector<Friend>();


   public TalkTalkClientView(String username, String ip_addr, String port_no) {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100,390,600);
      contentPane = new JPanel();
      setContentPane(contentPane);
      // contentPane.setLayout(new BorderLayout());
      contentPane.setLayout(null);
      
      this.username = username;
      this.ip_addr = ip_addr;
      this.port_no = port_no;
      
      try {
         socket = new Socket(ip_addr, Integer.parseInt(port_no));
         
         oos = new ObjectOutputStream(socket.getOutputStream());
         oos.flush();
         ois = new ObjectInputStream(socket.getInputStream());
         
         obcm = new ChatMsg(username, "100");         
         SendObject(obcm);
         
         ListenNetwork net = new ListenNetwork();
         net.start();
      }catch(NumberFormatException | IOException e) {
         e.printStackTrace();
         System.out.println("connect error");
      }
      setPanel();
      setResizable(false);
      setVisible(true);
   }
   
   public void setPanel() {
      
      menuPanel = new MenuPanel(this);
      menuPanel.setBounds(0, 2, 70, 562);
      friendListPanel = new FriendListPanel(this, obcm);
      friendListPanel.setBounds(70, 0, 310, 561);
      chatListPanel = new ChatListPanel(this, obcm);
      chatListPanel.setBounds(70, 0, 310, 562);
      
      contentPane.add(menuPanel);
      contentPane.add(friendListPanel);
      contentPane.add(chatListPanel);
      friendListPanel.setVisible(true);
      chatListPanel.setVisible(false);
      
   }
   
   public synchronized void SendObject(Object ob) {
      try {
         oos.writeObject(ob);
         oos.flush();
      } catch(IOException e) {
         System.out.println("SendObject Error");
      }
   }
   
   
 //Server Message를 수신해서 화면에 표시
   class ListenNetwork extends Thread {
   		public void run() {
   			while (true) {
   				try {
   					Object obcm = null;
   					String msg = null;
   					ChatMsg cm;
   					try {
   						obcm = ois.readObject();
   					} catch (ClassNotFoundException e) {
   						e.printStackTrace();
   						break;
   					}
   					if (obcm == null)
   						break;
   					if (obcm instanceof ChatMsg) {
   						cm = (ChatMsg) obcm;
   						System.out.println(cm.getCode());
   					} 
   					else
   						continue;
   					
   					switch(cm.getCode()) {
   					case "302":  // 친구 검색
   						friendListPanel.addFriendFrame.updateSearchResult(cm);
   						System.out.println(cm.profileImg.toString()+" clientViewAccept");
   						break;
   						
   					case "303":  // 친구 추가
   						System.out.println("clientView 303 서버로부터 cm 받음 : " + cm.getUsername());
   						friendListPanel.friendListScrollPane.addFriend(cm);
   						System.out.println("303 addFriend함수 호출");
   						friendListPanel.friendListScrollPane.updateFriendList();
   						System.out.println("303 updateFriendList함수 호출");
   						break;
   						
   					case "400":  // 채팅방 생성
   						break;
   					
   						
   					}
   					
   				} catch (IOException e) {
   					System.out.println("ois.readObject() error");
   					try {
   						ois.close();
   						oos.close();
   						socket.close();

   						break;
   					} catch (Exception ee) {
   						break;
   					} // catch문 끝
   				} // 바깥 catch문끝
   			}
   		}
   }

}

