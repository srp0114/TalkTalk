import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Friend extends JComponent{
	private TalkTalkClientView clientView = null;
	private String username;
	private ImageIcon profileImage;
	private	JLabel imageLabel;
	private JLabel userName;
	public Friend(TalkTalkClientView clientView,ImageIcon profileImage, String username) {
		setBackground(new Color(255, 255, 255));
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lblNewLabel.setBounds(87, 37, 94, 15);
		add(lblNewLabel);
		
		this.clientView = clientView;
		this.profileImage = profileImage;
		this.username = username;
		this.userName.setText(username);;
		imageLabel = new JLabel(profileImage);
		imageLabel.setBounds(20, 20, 50, 50);
		add(imageLabel);
	}
	
	public String getUsername() {
		return username;
	}
	
	
}
