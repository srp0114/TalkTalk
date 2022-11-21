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
   public static JPanel contentPane;
   private String username;  // username
   private String ip_addr;
   private String port_no;
   private UserInfo obui;      // obui
   
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
         
         obui = new UserInfo(username, "100");         
         SendObject(obui);
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
      friendListPanel = new FriendListPanel(this, obui);
      chatListPanel = new ChatListPanel(this, obui);
      
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
         newChild = new FriendListPanel(this, obui); break;
      case "clp":
         newChild = new ChatListPanel(this, obui); break;
      }
      JSplitPane parent = (JSplitPane) oldChild.getParent();
      int dividerLocation = parent.getDividerLocation();
      parent.remove(oldChild);
      parent.add(newChild);
      parent.setDividerLocation(dividerLocation);
      newChild.revalidate();
      newChild.repaint();
   
   }

}