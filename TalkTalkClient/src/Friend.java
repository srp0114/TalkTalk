import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Friend extends JButton{
	private TalkTalkClientView clientView = null;
	private String username ="";
	private ImageIcon profileImage;
	private	JLabel imageLabel;
	private JLabel userName;
	
	public Friend(TalkTalkClientView clientView,ImageIcon profileImage, String username) {
		setBackground(new Color(255, 255, 255));
		setSize(300, 80);
		
		this.clientView = clientView;
		this.profileImage = profileImage;
		this.username = username;
		this.userName = new JLabel(username);
		userName.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		userName.setBounds(87, 37, 94, 15);
		userName.setVisible(true);
		add(userName);
		
		imageLabel = new JLabel(profileImage);
		imageLabel.setBounds(20, 20, 50, 50);
		imageLabel.setVisible(true);
		add(imageLabel);
	}
	
	public String getUsername() {
		return username;
	}
	
	
}
