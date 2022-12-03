import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.SwingConstants;

public class Friend extends JPanel{
   private TalkTalkClientView clientView = null;
   
   public String username ="";
   public ImageIcon profileImage;
   public String selectUser = "";
   //private JButton imageLabel;
   private JButton imageButton;
   private JLabel userName;
   private JCheckBox checkBox;
   public Boolean isChecked = false; 
   
   public Friend (ImageIcon profileImage, String username) {
      setBackground(new Color(255, 255, 255));
      //setSize(290, 60);
      setPreferredSize(new Dimension(300, 70));
      setLayout(new FlowLayout(FlowLayout.LEFT, 15,10));
      
      //this.clientView = clientView;
      this.username = username;
      this.profileImage = profileImage;
      

      userName = new JLabel(username);
      userName.setHorizontalAlignment(SwingConstants.LEFT);
      userName.setVerticalAlignment(SwingConstants.CENTER);
      //userName.setBounds(55,12,100,50);
      userName.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
      userName.setVisible(true);

      MyItemListener listener = new MyItemListener();

      checkBox = new JCheckBox();
      checkBox.setHorizontalAlignment(SwingConstants.RIGHT);
      checkBox.addItemListener(listener); // 체크박스에 Item 리스너 등록
      SetSelect(false);
      
      imageButton = new JButton(profileImage);
      imageButton.setHorizontalAlignment(SwingConstants.LEFT);
      imageButton.setVerticalAlignment(SwingConstants.CENTER);
      imageButton.setBorderPainted(false);
      imageButton.setFocusPainted(false);
      imageButton.setContentAreaFilled(false);
      imageButton.setOpaque(false);
      imageButton.setBorder(null);
      //imageButton.setBounds(15,10,50,50);
      imageButton.setVisible(true);

      this.add(imageButton);
      this.add(userName);
      this.add(checkBox);
      this.setVisible(true);
   }
   
   public String getUsername() {
      return username;
   }


	public void SetSelect(boolean select) {
		if(select == true) {
			checkBox.setVisible(true);
			
		}
		else {
			checkBox.setVisible(false);
		}
	}
	
	
	class MyItemListener implements ItemListener {       
	       // 체크박스의 선택 상태가 변하면 itemStateChanged()가 호출됨
	       public void itemStateChanged(ItemEvent e) {
	           if(e.getStateChange() == ItemEvent.SELECTED) { // 선택 경우
		             isChecked = true;
	           }
	           else { // 해제 경우
		             isChecked = false;
	           }
	       }
	 }
	
	

}
