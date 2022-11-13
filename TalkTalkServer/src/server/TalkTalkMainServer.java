package server;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
	private static final int BUF_LEN = 128;

	
	public static void main(String[] args) {
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
	public TalkTalkMainServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 440);
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
	public void AppendObject(UserInfo userInfo) {
		//textArea.append("사용자로부터 들어온 object: " + str + "\n");
		textArea.append("code = " + userInfo.getCode() + "\n");
		textArea.append("id = " + userInfo.getUsername() +"\n");
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
					UserVec.add(new_user);
					new_user.start();
					AppendText("현재 user 수 " + UserVec.size());
					
				}catch(IOException e) {
					AppendText("accept() error");
				}
			}
		}
	}
	
	class UserService extends Thread {
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;
		
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		
		private Socket client_socket;
		private Vector user_vc;
		public String username = "";
		
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
		
		public void run() {
			while(true) {
				
			}
		}
	}

}
