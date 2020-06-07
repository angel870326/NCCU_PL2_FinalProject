import javax.swing.JFrame;


public class Viewer {

	public static void main(String[] args) {
		
		HomeFrame homeFrame = new HomeFrame("Login", 295, 90);
		homeFrame.add(new RegisterPanel());
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setVisible(true);


	}

}
