import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class FriendComponent extends JComponent{
	private ImageIcon profileImage;
	private	JLabel imageLabel;
	private JLabel userName;
	public FriendComponent(ImageIcon profileImage, String username) {
		setBackground(new Color(255, 255, 255));
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lblNewLabel.setBounds(87, 37, 94, 15);
		add(lblNewLabel);
		
		this.profileImage = profileImage;
		this.userName.setText(username);;
		imageLabel = new JLabel(profileImage);
		imageLabel.setBounds(20, 20, 50, 50);
		add(imageLabel);
	}
	
}
