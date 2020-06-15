import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Viewer {

	public static void main(String[] args) {
		
		HomeFrame homeFrame = new HomeFrame("Login", 295, 90);
		homeFrame.add(new RegisterPanel());
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setBackground(Color.decode("#E4DAD2"));
		homeFrame.setVisible(true);
		

	}

}
