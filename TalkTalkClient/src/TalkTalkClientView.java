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



   public TalkTalkClientView(String username, String ip_addr, String port_no) {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100,390,600);
      contentPane = new JPanel();
      setContentPane(contentPane);
      contentPane.setLayout(new BorderLayout());
      
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
      splitPane();
      setResizable(false);
      setVisible(true);
   }
   
   public void splitPane() {
      hPane = new JSplitPane();   // JSplitPane 생성
      
      hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
      hPane.setDividerLocation(70);
      hPane.setDividerSize(0);
      hPane.setEnabled(false);
      menuPanel = new MenuPanel(this);
      friendListPanel = new FriendListPanel(this, obcm);
      chatListPanel = new ChatListPanel(this, obcm);
      
      hPane.setLeftComponent(menuPanel);
      hPane.setRightComponent(friendListPanel);
      
   
      getContentPane().add(hPane, BorderLayout.CENTER);
      
   }
   
   public void SendObject(Object ob) {
      try {
         oos.writeObject(ob);
         oos.flush();
      } catch(IOException e) {
         System.out.println("SendObject Error");
      }
   }
   
   public void replaceSplitPaneChild(String paneName) {
      oldChild = (JPanel) hPane.getRightComponent();
      switch (paneName) {
      case "flp":
         newChild = new FriendListPanel(this, obcm); break;
      case "clp":
         newChild = new ChatListPanel(this, obcm); break;
      }
      JSplitPane parent = (JSplitPane) oldChild.getParent();
      int dividerLocation = parent.getDividerLocation();
      parent.remove(oldChild);
      parent.add(newChild);
      parent.setDividerLocation(dividerLocation);
      newChild.revalidate();
      newChild.repaint();
   
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
   						System.out.println(cm.getProfileImg().toString()+" clientViewAccept");
   						break;
   						
   					case "303":  // 친구 추가
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

