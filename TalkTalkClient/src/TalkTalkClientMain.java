
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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

import util.ColorDefinition;

public class TalkTalkClientMain extends JFrame {
	private Image logoImg = Toolkit.getDefaultToolkit().getImage("src/talkLogo.png");
	private ImageIcon talkLogo;
	private JLabel lblTalkLogo;
	private JPanel contentPane;
	private JTextField txtUserName;
	private JButton btnLogin;
	
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
		contentPane.setBackground(ColorDefinition.kakaoYello);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		uiInit();
		
		Myaction action = new Myaction();
		txtUserName.addActionListener(action);
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
	
	public void uiInit() {
		// 이미지 크기 조정
		logoImg = logoImg.getScaledInstance(100,  100,  Image.SCALE_SMOOTH);
		talkLogo = new ImageIcon(logoImg);
		lblTalkLogo = new JLabel(talkLogo);
		lblTalkLogo.setBounds(106, 70, 150, 130);
		contentPane.add(lblTalkLogo);
				
				
		JLabel lblUserName = new JLabel("이름");
		lblUserName.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		lblUserName.setBounds(80, 210, 100, 38);
		contentPane.add(lblUserName);
				
		txtUserName = new JTextField();
		txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserName.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		txtUserName.setBounds(80, 250, 205, 38);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
				
		btnLogin = new JButton("로그인");
		btnLogin.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBounds(80, 300, 205, 38);
		btnLogin.setBackground(new Color(66, 54, 48));
		btnLogin.setBorderPainted(false);
		btnLogin.setFocusPainted(false);
		contentPane.add(btnLogin);
	}
}
