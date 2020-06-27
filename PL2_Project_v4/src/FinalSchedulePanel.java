import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import java.util.Random;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultListCellRenderer;
import java.sql.ResultSetMetaData;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.imageio.ImageIO;
import java.sql.SQLException;
public class FinalSchedulePanel extends JPanel{
	private JPanel calenderPanel, weekOfDayPanel, timeOfDayPanel, topPanel, westPanel, mainPanel, eastPanel, monPanel,tuePanel,wedPanel,thuPanel,friPanel, rightDownPanel, sendEmailPanel;
	private JPanel subPanel;
	private JTextArea subTextArea;
	private int ranNum;
	private String courseName;
	private String day;
	private JButton genBtn, exitBtn, sendBtn;
	private String time;
	private double credits;
	private String courseID;
	private ArrayList<String> weekList;
	private ArrayList<String> timeList;
	private String classroom;
	private Register register;
	private SelectCoursePanel selectCoursePanel;
	private ArrayList<ArrayList<Lecture>> filterLectureList;
	private static final int AREA_HEIGHT =1;
	private static final int AREA_WIDTH = 5;
	private int count;
	private int dayCount;
	private Random ran;
	private JLabel creditsLabel, emailLabel;
	private JTextArea creditTextArea;
	private ArrayList<String> lectureTimeFilteredArrayList;
	private double totalCredits;
	private int lectureListCount;
	private JTextArea courseInfoArea;
	private JTextField emailField;
	private static final Color otherCourse = Color.decode("#cbbebf");  // #d1b2c3  #e4d2dc
	private static final Color scheduledCourse = Color.decode("#dcbcea");  // #efdcf7
	private static final Color selectCourse = Color.decode("#c8fbc6");  // #dafcea
	private static final Color mainColor = Color.decode("#a99395"); // #e4d2dc  可以用#d1b2c3
	private static final Color otherPlan = Color.decode("#f0c3e5"); // #f5d6ed
	private static final Color textAreaColor = Color.decode("#c2d0da"); // #fceada  #f9c86f  #e4d2dc
	private static final Color BACKGROUND = Color.decode("#E4DAD2");  // #E4DAD2
	private static final Color calenderBack = Color.decode("#cbbebf"); // #d1b2c3  #e4d2dc
	private static final Color functionColor = Color.decode("#88e1d8"); // #8cece2
	

	public FinalSchedulePanel() throws SQLException {
		register= new Register();
		lectureTimeFilteredArrayList=new ArrayList<String>();
		filterLectureList = SelectCoursePanel.filterLectureList;
		if (filterLectureList.size()==0)
		{
			JOptionPane.showMessageDialog(null, "No more course suggestions for you.");
			setVisible(false);
			HomeFrame homeFrame = getFrame();
			homeFrame.add(new SelectCoursePanel());
			homeFrame.setSize(760, 720);
			homeFrame.setTitle("Course Registration Guide - Select Course");
			homeFrame.setLocationRelativeTo(null);
		}
//		register.addCourse("789", "econ", 3, "一", "D5", "wayetr");
		selectCoursePanel = new SelectCoursePanel();
		ran=new Random();
		courseInfoArea=new JTextArea();
		weekList= new ArrayList<String>(Arrays.asList("   ","一", "二","三","四","五"));
		timeList= new ArrayList<String>(Arrays.asList("   ","A 6-7", "B 7-8","1 8-9","2 9-10","3 10-11","4 11-12","C 12-13","D 13-14","5 14-15","6 15-16","7 16-17","8 17-18","E 18-19","F 19-20","G 20-21","H 21-22"));
//		filterLectureList=new ArrayList<ArrayList<Lecture>>();
		topPanel = new JPanel();
		westPanel = new JPanel();
		mainPanel = new JPanel();
		eastPanel = new JPanel();
		monPanel = new JPanel();
		tuePanel= new JPanel();
		wedPanel= new JPanel();
		thuPanel= new JPanel();
		friPanel= new JPanel();
		rightDownPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,6));
		eastPanel.setLayout(new BorderLayout());
		rightDownPanel.setLayout(new BorderLayout());
		day="";
		time="";
		courseName="";
		credits=0;
		totalCredits=0;
		lectureListCount=0;
		courseID="";
		count=0;
		dayCount=0;
	
		createCourseFromList();
		getPlanToTakeToRegister();
		getOtherPlanToRegister();
		
		westPanel.add(createTimeOfWeekPanel());
		mainPanel.add(westPanel);
		
		monPanel=indDayOfWeekPanel("一");
		tuePanel=indDayOfWeekPanel("二");
		wedPanel=indDayOfWeekPanel("三");
		thuPanel=indDayOfWeekPanel("四");
		friPanel=indDayOfWeekPanel("五");
		monPanel.setBackground(calenderBack);
		tuePanel.setBackground(calenderBack);
		wedPanel.setBackground(calenderBack);
		thuPanel.setBackground(calenderBack);
		friPanel.setBackground(calenderBack);
		
		mainPanel.add(monPanel);
		mainPanel.add(tuePanel);
		mainPanel.add(wedPanel);
		mainPanel.add(thuPanel);
		mainPanel.add(friPanel);
//		setColor(mainPanel,"brown");
//		setColor(westPanel, "blue");
//		setColor(eastPanel, "green");
		mainPanel.setBackground(functionColor);
		westPanel.setBackground(calenderBack);
//		mainPanel.add(indDayOfWeekPanel("一"));
//		mainPanel.add(indDayOfWeekPanel("二"));
//		mainPanel.add(indDayOfWeekPanel("三"));
//		mainPanel.add(indDayOfWeekPanel("四"));
//		mainPanel.add(indDayOfWeekPanel("五"));
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BorderLayout());
		sendEmailPanel= new JPanel();
		eastPanel.add(createCourseDetailTextArea(),BorderLayout.NORTH);
		eastPanel.add(createCreditTextArea(),BorderLayout.CENTER);
		
		createGenerateBtn();
		createExitBtn();
		createSendEmailComp();
		btnPanel.add(genBtn,BorderLayout.NORTH);
		sendEmailPanel.add(emailLabel);
		sendEmailPanel.add(emailField);
		sendEmailPanel.add(sendBtn);
		btnPanel.add(exitBtn,BorderLayout.SOUTH);
		rightDownPanel.add(sendEmailPanel,BorderLayout.NORTH);
		rightDownPanel.add(btnPanel,BorderLayout.CENTER);
		btnPanel.setBackground(functionColor);   //
		sendEmailPanel.setBackground(functionColor);
		rightDownPanel.setBackground(functionColor);
		eastPanel.add(rightDownPanel, BorderLayout.SOUTH);
		add(mainPanel,BorderLayout.WEST);

		add(eastPanel, BorderLayout.EAST);

		setBackground(BACKGROUND);
	
		//add(calenderPanel);		
	}
	public void getPlanToTakeToRegister()  throws SQLException
	{
		String type="Scheduled";
		System.out.println("jij");
		String server = "jdbc:mysql://140.119.19.73:9306/";
		String database = "TG06";
		String config= "?useUnicode=true&characterEncoding=utf8";
		String url = server + database + config;
		String username = "TG06";
		String password = "i8p3q6";
		Connection conn = null;
		
		try {			
			conn = DriverManager.getConnection(url, username, password);
			Statement stat = conn.createStatement();
			String query = "SELECT * FROM PlanToTake";
			ResultSet result = stat.executeQuery(query);
			while(result.next()) {			
				String  lectureId = result.getString("subNum");
				System.out.println(lectureId);
				String lectureName = result.getString("subNam");
				System.out.println(lectureName);
				String  lectureCredits = result.getString("subPoint").substring(0,1);
				System.out.println(lectureCredits);
				lectureTimeFilteredArrayList=checkTime(result.getString("subTime"));
//				String  lectureDay = result.getString("subTime").substring(0,1);
//				System.out.println(lectureDay);
//				String  lectureTime = result.getString("subTime").substring(1);
//				System.out.println(lectureTime);
				String  lectureClassroom = result.getString("subClassroom");
				System.out.println(lectureClassroom);
				
				register.addCourse(lectureId,lectureName,Double.parseDouble(lectureCredits),lectureTimeFilteredArrayList,lectureClassroom,type);
				System.out.println("jj");				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		} finally {
			
			conn.close();
		}
	}
	public void getOtherPlanToRegister() throws SQLException
	{
		String type="PlanToTake";
		String server = "jdbc:mysql://140.119.19.73:9306/";
		String database = "TG06";
		String config= "?useUnicode=true&characterEncoding=utf8";
		String url = server + database + config;
		String username = "TG06";
		String password = "i8p3q6";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			Statement stat = conn.createStatement();
			String query = "SELECT * FROM OtherPlan";
			ResultSet result = stat.executeQuery(query);
//			System.out.println("bk,");
			while(result.next()) {
				String  lectureId = "NA";
				String  lectureName = result.getString("subNam");
				double  lectureCredits = 0;
				lectureTimeFilteredArrayList=checkTime(result.getString("subTime"));
//				String  lectureDay = result.getString("subTime").substring(0,1);
//				String  lectureTime = result.getString("subTime").substring(1);
				String  lectureClassroom = "NA";
				
				register.addCourse(lectureId,lectureName,lectureCredits,lectureTimeFilteredArrayList,lectureClassroom,type);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		} finally {
			
			conn.close();
		}
	}
	public void setColor(JPanel panel, String color)
	{
		if (color.equals("brown"))
		panel.setBackground(Color.decode("#594F4F"));
		else if (color.equals("darkBlue"))
		panel.setBackground(Color.decode("#547980"));
		else if (color.equals("green"))
			panel.setBackground(Color.decode("#45ADA8"));
		else if (color.equals("lightGreen"))
			panel.setBackground(Color.decode("#9DE0AD"));
		else if (color.equals("yellow"))
			panel.setBackground(Color.decode("#E5FCC2"));		
	}
	public void createCourseFromList()
	{
		String type="Normal";
		System.out.println("tes");
		System.out.println(filterLectureList);
		System.out.println("count:"+lectureListCount);
//		System.out.println("Size: "+selectCoursePanel.getFilterLectureList().get(lectureListCount).size());
		System.out.println(filterLectureList.get(lectureListCount).size());

		for (int k = 0; k<filterLectureList.get(lectureListCount).size();k++) {
			Lecture lec = filterLectureList.get(lectureListCount).get(k);
			lectureTimeFilteredArrayList=checkTime(lec.getLectureTime());
			//Course course = new Course(lec.getLectureID(),lec.getLectureName(),Integer.parseInt(lec.getLecturePoint()),lec.getLectureTime().substring(0,3),lec.getLectureTime().substring(3),lec.getLectureClassRoom());
			register.addCourse(lec.getLectureID(),lec.getLectureName(),Double.parseDouble(lec.getLecturePoint()),lectureTimeFilteredArrayList,lec.getLectureClassRoom(),type);
		}		
	}

	public JPanel indDayOfWeekPanel(String day)
	{
		count=0;
//		weekList.remove(0);
		
		weekOfDayPanel = new JPanel();
		weekOfDayPanel.setLayout(new GridLayout(17,1));
		String dayOfWeek= day;
			
			for (String timeOfDay : timeList)
			{
				if (count==0)
				{					
					weekOfDayPanel.add(createDatePanel("          "+dayOfWeek));
					count++;
				}
				else {
					String abbTimeOfDay="";
					abbTimeOfDay=timeOfDay.substring(0,1);
					
					boolean flag = false;
					
					for (Course course: register.getCourseList()) {

					for (String time:course.getCourseTimeComplete()) 
						if (time.substring(0,1).contains(dayOfWeek) && time.substring(1).contains(abbTimeOfDay))
								{
									flag = true;
//									System.out.print(course);
									if (course.getType().equals("Scheduled"))
									{
									weekOfDayPanel.add(createScheduledPanel(course.getCourseName(),course.getCredits()));
									}
									else if (course.getType().equals("PlanToTake"))
									{
										weekOfDayPanel.add(createPlanToTakePanel(course.getCourseName(),course.getCredits()));	
									}
									else if (course.getType().equals("Normal"))
									{
										weekOfDayPanel.add(createNormalPanel(course.getCourseName(),course.getCredits()));
									}
								}						
					}				
					if(!flag) 
					{
//							System.out.println(timeOfDay);		
							weekOfDayPanel.add(createNullPanel());
					}
				}
			}
			return weekOfDayPanel;	
			}
		
	
	public JPanel createTimeOfWeekPanel()
	{
		timeOfDayPanel = new JPanel();
		timeOfDayPanel.setLayout(new GridLayout(17,1));
		for (String timeOfDay : timeList)
		{
			timeOfDayPanel.add(createTimePanel(timeOfDay));
		}
		return timeOfDayPanel;		
	}

	public JPanel createScheduledPanel(String courseName,double credits)
	{
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subTextArea = new JTextArea(AREA_HEIGHT,AREA_WIDTH);
		subTextArea.setText(courseName+" "+credits+"學分");
		subTextArea.setEditable(true);
		subTextArea.setLineWrap(true);
		arrange(subTextArea, "Scheduled");
		JScrollPane pane =new JScrollPane(subTextArea);
		subPanel.add(pane,BorderLayout.CENTER);
		subPanel.setBorder(new EtchedBorder());
		return subPanel;
	}
	
	public JPanel createPlanToTakePanel(String courseName,double credits)
	{
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subTextArea = new JTextArea(AREA_HEIGHT,AREA_WIDTH);
		subTextArea.setText(courseName+" "+credits+"學分");
		subTextArea.setEditable(true);
		subTextArea.setLineWrap(true);
		arrange(subTextArea, "PlanToTake");
		JScrollPane pane =new JScrollPane(subTextArea);
		subPanel.add(pane,BorderLayout.CENTER);
		subPanel.setBorder(new EtchedBorder());
		return subPanel;
	}
	
	public JPanel createNormalPanel(String courseName,double credits)
	{
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subTextArea = new JTextArea(AREA_HEIGHT,AREA_WIDTH);
		subTextArea.setText(courseName+" "+credits+"學分");
		subTextArea.setEditable(true);
		subTextArea.setLineWrap(true);
		arrange(subTextArea, "Selected");
		JScrollPane pane =new JScrollPane(subTextArea);
		subPanel.add(pane,BorderLayout.CENTER);
		subPanel.setBorder(new EtchedBorder());
		return subPanel;
	}
	public JPanel createNullPanel()
	{
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subTextArea = new JTextArea();
		subTextArea.setText("");
		subTextArea.setEditable(true);
		subTextArea.setLineWrap(true);
		arrange(subTextArea, "Other");
		subPanel.add(subTextArea,BorderLayout.CENTER);
		subPanel.setBorder(new EtchedBorder());
		return subPanel;
	}
	public JPanel createDatePanel(String day) 
	{
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subTextArea = new JTextArea(AREA_HEIGHT,AREA_WIDTH);
		subTextArea.setText(day);
		subPanel.add(subTextArea);
		arrange(subTextArea, "Main");
		subPanel.setBorder(new EtchedBorder());
		return subPanel;
	}
	public JPanel createTimePanel(String time)
	{
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subTextArea = new JTextArea(2,AREA_WIDTH);
		subTextArea.setText(time);
		subPanel.add(subTextArea);
		arrange(subTextArea, "Main");
		subPanel.setBorder(new EtchedBorder());
		return subPanel;
	}
	public JButton createGenerateBtn() throws SQLException
	{
		genBtn =new JButton("Generate A New Schedule");
		genBtn.setForeground(Color.decode("#454545"));
		class GenerateBtnListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent event)
			{
				//add 上界線
				//ranNum=ran.nextInt();
//				weekList.add(0,"dt");
				lectureListCount++;
//				
				westPanel.removeAll();
				westPanel.repaint();
				mainPanel.removeAll();
				mainPanel.repaint();
				sendEmailPanel.removeAll();
				sendEmailPanel.repaint();
				eastPanel.removeAll();
				eastPanel.repaint();
				
				updateUI();

				register.getCourseList().clear();
				
				System.out.println(register.getCourseList());
				System.out.println("------------");
				if (lectureListCount>=filterLectureList.size())
				{					
						JOptionPane.showMessageDialog(null, "No more course suggestions for you.\nClick [Generate A New Schedule] to start once again from the first suggestion.");
						lectureListCount=0;
				}
			//要加createCourseFromList，記得
				register.getCourseList().clear();
				createCourseFromList();
				try {
					getPlanToTakeToRegister();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				to be added
				try {
					getOtherPlanToRegister();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
				westPanel.add(createTimeOfWeekPanel());
				mainPanel.add(westPanel);

//				mainPanel.add(indDayOfWeekPanel("一"));
//				mainPanel.add(indDayOfWeekPanel("二"));
//				mainPanel.add(indDayOfWeekPanel("三"));
//				mainPanel.add(indDayOfWeekPanel("四"));
//				mainPanel.add(indDayOfWeekPanel("五"));
				
				monPanel=indDayOfWeekPanel("一");
				tuePanel=indDayOfWeekPanel("二");
				wedPanel=indDayOfWeekPanel("三");
				thuPanel=indDayOfWeekPanel("四");
				friPanel=indDayOfWeekPanel("五");
				monPanel.setBackground(calenderBack);
				tuePanel.setBackground(calenderBack);
				wedPanel.setBackground(calenderBack);
				thuPanel.setBackground(calenderBack);
				friPanel.setBackground(calenderBack);
				
				mainPanel.add(monPanel);
				mainPanel.add(tuePanel);
				mainPanel.add(wedPanel);
				mainPanel.add(thuPanel);
				mainPanel.add(friPanel);
				revalidate();
				add(mainPanel,BorderLayout.WEST);
//				add(westPanel,BorderLayout.WEST);
				System.out.print(register.getCourseList());
				
				JPanel btnPanel = new JPanel();
				btnPanel.setLayout(new BorderLayout());
				eastPanel.add(createCourseDetailTextArea(),BorderLayout.NORTH);
				totalCredits=0.0;
				eastPanel.add(createCreditTextArea(),BorderLayout.CENTER);
				try {
					createGenerateBtn();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				createExitBtn();
				createSendEmailComp();
				btnPanel.add(genBtn,BorderLayout.NORTH);
				sendEmailPanel.add(emailLabel);
				sendEmailPanel.add(emailField);
				sendEmailPanel.add(sendBtn);
				btnPanel.add(exitBtn,BorderLayout.SOUTH);
				rightDownPanel.add(sendEmailPanel,BorderLayout.NORTH);
				rightDownPanel.add(btnPanel,BorderLayout.CENTER);
				btnPanel.setBackground(calenderBack);
				sendEmailPanel.setBackground(functionColor);
				rightDownPanel.setBackground(functionColor);
				eastPanel.add(rightDownPanel, BorderLayout.SOUTH);
				add(mainPanel,BorderLayout.WEST);

				add(eastPanel, BorderLayout.EAST);

				setBackground(BACKGROUND);//grey #778899				
			}
		}
		 ActionListener listener = new GenerateBtnListener();
		 genBtn.addActionListener(listener);			
		return genBtn;		
	}
	public JPanel createCreditTextArea()
	{
		subPanel=new JPanel();
		creditsLabel = new JLabel("Credits:");
		creditsLabel.setForeground(Color.white);
		creditTextArea = new JTextArea(1,5);
		creditTextArea.setText(""+calculateTotalCredits());
		creditTextArea.setForeground(Color.decode("#454545"));
		subPanel.add(creditsLabel);
		subPanel.add(creditTextArea);
		subPanel.setBackground(textAreaColor);
		return subPanel;		
	}
	public double calculateTotalCredits()
	{
		System.out.println(register.getCourseList());
		for (Course course:register.getCourseList())
		{
			System.out.println("------"+course.getCredits());
			totalCredits+=course.getCredits();
		}
		return totalCredits;
	}
	public JPanel createCourseDetailTextArea()
	{		
		subPanel = new JPanel();
		courseInfoArea = new JTextArea(30,59);
		courseInfoArea.setText("");
		courseInfoArea.append(courseInfoToString());
		courseInfoArea.setEditable(true);
		
		courseInfoArea.setLineWrap(true);
		
		courseInfoArea.setBackground(textAreaColor);
		courseInfoArea.setForeground(Color.decode("#454545"));
		JScrollPane pane =new JScrollPane(courseInfoArea);
		subPanel.add(pane);
		subPanel.setBackground(textAreaColor);
		subPanel.setBorder(new EtchedBorder());
		return subPanel;		
	}
	public String courseInfoToString()
	{
		String ans="";
		for (Course cou:register.getCourseList())
		{
			ans+="CourseID: "+cou.getCourseID()+", CourseName: "+cou.getCourseName()+", Credits: "+cou.getCredits()+", Time:"+cou.getCourseTimeComplete()+", Classroom: "+cou.getClassroom()+"\n";
		}
		return ans;
	}
	public JButton createExitBtn()
	{
		exitBtn = new JButton("Done~~");
		exitBtn.setForeground(Color.decode("#454545"));
		class exitBtnListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		}
		 ActionListener listener = new exitBtnListener();
		 exitBtn.addActionListener(listener);			
		return exitBtn;		
	}
	public void arrange(JTextArea textArea, String type) {
		if(type.equals("Other")) {
			textArea.setBorder(BorderFactory.createLineBorder(otherCourse, 1, true));
			textArea.setBackground(otherCourse);
			textArea.setForeground(Color.white);
		}
		else if(type.equals("Main")) {
			textArea.setBorder(BorderFactory.createLineBorder(mainColor, 1, true));
			textArea.setBackground(mainColor);
			textArea.setFont(new Font("Serif", Font.BOLD, 16));
			textArea.setForeground(Color.white);
		}
		else if(type.equals("Scheduled")) {
			textArea.setBorder(BorderFactory.createLineBorder(scheduledCourse, 1, true));
			textArea.setBackground(scheduledCourse);
			textArea.setForeground(Color.white);
		}
		else if(type.equals("Selected")) {
			textArea.setBorder(BorderFactory.createLineBorder(selectCourse, 1, true));
			textArea.setBackground(selectCourse);
			textArea.setForeground(Color.decode("#454545"));
		}
		else if (type.equals("PlanToTake"))
		{
			textArea.setBorder(BorderFactory.createLineBorder(otherPlan, 1, true));
			textArea.setBackground(otherPlan);
			textArea.setForeground(Color.white);
		}
	}
	public void createSendEmailComp()
	{
		emailLabel=new JLabel("Your email:");
		emailLabel.setForeground(Color.white);
		emailField = new JTextField(20);
		emailField.setText("@gmail.com");
		emailField.setForeground(Color.decode("#454545"));
		sendBtn = new JButton("Send");
		sendBtn.setForeground(Color.decode("#454545"));

		class sendBtnListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent event)
			{
				try {
					screenshot();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String emailString = emailField.getText();
				sendEmail(emailString);
						
			}
		}
		sendBtn.addActionListener(new sendBtnListener());
	}
	public void screenshot() throws AWTException, IOException
	{
		Robot r = new Robot();
    	String pathString = "src/img/finalScheduleScreenshot.jpg";
    	BufferedImage screenShot = r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    	ImageIO.write(screenShot, "JPG", new File(pathString));
	}
	public void sendEmail(String email)
	{
		final String username = "bensonsu02@gmail.com";
		final String password = "tklcolbmaooljiix";
		String fromEmail = "bensonsu02@gmail.com";
		String toEmail = email;
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
		//Start our mail message
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//			set subject line
			msg.setSubject("Final Schedule");
			
			Multipart emailContent = new MimeMultipart();
			
			//Text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("Here is your final schedule ==>\n"+courseInfoArea.getText());
			
			//Attachment body part.
			MimeBodyPart pdfAttachment = new MimeBodyPart();
			pdfAttachment.attachFile("src/img/finalScheduleScreenshot.jpg");

			//Attach body parts
			emailContent.addBodyPart(textBodyPart);
			emailContent.addBodyPart(pdfAttachment);
			
			//Attach multipart to messages
			msg.setContent(emailContent);
			
			Transport.send(msg);
			System.out.println("Sent message");
			sendBtn.setText("Email Sent");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private ResultSet doSql(String sql, String method) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://140.119.19.73:9306/TG06?characterEncoding=UTF-8", "TG06", "i8p3q6");
			Statement statement = connection.createStatement();
			if(method.equals("execute")) {
				statement.execute(sql);
			}else if(method.equals("executeQuery")) {
				ResultSet resultSet = statement.executeQuery(sql);
				return resultSet;
			}
			return null;
		}catch(Exception e) {
			System.out.println("Error occur!");
			e.printStackTrace();
			return null;
		}
	}
	
	private ArrayList<String> checkTime(String courseTime) {
		ArrayList<String> courseTimeList = new ArrayList<>();
		ArrayList<Integer> indexList = new ArrayList<>();
		String[] weekdayArray= {"一","二","三","四","五"};
		for(String weekday:weekdayArray) {
			if(courseTime.contains(weekday)) {
				int index = courseTime.indexOf(weekday);
				indexList.add(index);
			}
		}
		String time = "";
		for(int i=0; i<indexList.size(); i++) {
			if(i != indexList.size() - 1) {
				time = courseTime.substring(indexList.get(i), indexList.get(i+1));
			}else {
				time = courseTime.substring(indexList.get(i));
			}
			courseTimeList.add(time);
		}
		return courseTimeList;
	}
	
	private HomeFrame getFrame() {
		for(Frame frame:JFrame.getFrames()) {
			if(frame.getTitle().equals("Course Registration Guide - Final Schedule")) {
				HomeFrame homeFrame = (HomeFrame) frame;
				return homeFrame;
			}
		}
		return null;
	}

}
