import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Viewer {

	public static void main(String[] args) {
		
		HomeFrame homeFrame = new HomeFrame("ScheduleSetup", 820, 805);
		ScheduledPanel panel = new ScheduledPanel();
		homeFrame.add(panel);
		
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setVisible(true);


	}

}
