package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TalkTalkClientMain extends JFrame {
	private Image logoImg = Toolkit.getDefaultToolkit().getImage("src/talkLogo.png");
	private ImageIcon talkLogo;
	private JLabel lblTalkLogo;
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
		contentPane.setBackground(new Color(254,229,0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 이미지 크기 조정
		logoImg = logoImg.getScaledInstance(100,  100,  Image.SCALE_DEFAULT);
		talkLogo = new ImageIcon(logoImg);
		lblTalkLogo = new JLabel(talkLogo);
		lblTalkLogo.setBounds(106, 70, 150, 130);
		contentPane.add(lblTalkLogo);
		
		
		JLabel lblUserName = new JLabel("이름");
		lblUserName.setBounds(80, 220, 100, 38);
		contentPane.add(lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserName.setBounds(80, 250, 205, 38);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JButton btnLogin = new JButton("로그인");
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBounds(80, 300, 205, 38);
		btnLogin.setBackground(new Color(66, 54, 48));
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
