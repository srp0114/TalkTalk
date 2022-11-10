package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TalkTalkClientMain extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	
	// application 시작
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkTalkClientMain frame = new TalkTalkClientMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Frame 만들기
	public TalkTalkClientMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(12, 39, 82, 33);
		contentPane.add(lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserName.setBounds(101, 39, 116, 33);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(12, 223, 205, 38);
		contentPane.add(btnLogin);
		Myaction action = new Myaction();
		btnLogin.addActionListener(action);
	}

	class Myaction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = txtUserName.getText().trim();
			String ip_addr = "127.0.0.1";
			String port_no = "30000";
			TalkTalkClientView view = new TalkTalkClientView(username, ip_addr, port_no);
			setVisible(false);
		}
	}
}
