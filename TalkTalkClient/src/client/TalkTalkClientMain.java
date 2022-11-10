package client;

import java.awt.Color;
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
		setBounds(100, 100, 380, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(249,229,93));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(80, 200, 100, 38);
		contentPane.add(lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserName.setBounds(80, 230, 205, 38);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JButton btnLogin = new JButton("로그인");
		btnLogin.setForeground(new Color(245, 245, 245));
		btnLogin.setBounds(80, 300, 205, 38);
		btnLogin.setBackground(new Color(66, 45, 45));
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
