
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScheduledPanel extends JPanel{
	
	// Color
	private static final Color TITLE = Color.decode("#c0b2d1");  // #e7e2ee // #d2dce4
	private static final Color BACKGROUND = Color.decode("#E4DAD2");  // #E4DAD2
	private static final Color INNER = Color.decode("#E6EAED"); 
	private static final Color INNER_LABEL = Color.decode("#454545"); 
	private static final Color NEXTCOLOR = Color.decode("#d5c6b9");  // #cbb9a9
	
	// Image
	private ImageIcon icon;

	// Plan to take
	private JPanel mainPanel;
	private JPanel plantoTakePanel;			
	private JPanel plantoTakeTitlePanel;
	private JLabel plantoTakeLabel;	
	private JPanel plantoTakeMainPanel;
	private JPanel addCoursePanel;
	private JLabel addCourseLabel;
	private JTextField addCourseField ;
	private JButton addCourseBtn;
	private JPanel coursePanel;
	private ArrayList<String> planToTakeList;		// courseID
	private ArrayList<String> planToTakeNameList;	// courseName
	private ArrayList<JButton> courseBtnList;

	// Other Plan
	private JLabel otherPlanLabel;	
	private JPanel otherPlanPanel;			
	private JPanel otherPlanTitlePanel;
	private JPanel otherPlanMainPanel;	
	private JPanel addOtherPanel;
	private JLabel addOtherLabel;
	private JTextField addOtherTextField;
	private JButton addOtherBtn;
	private JPanel checkBoxPanel;
	private JButton saveOther;
	private JButton resetOther;
	private int listMaxSize;
	private ArrayList<ScheduledTimeList> otherPlanLists;			// 本學期其他事情的 list
	private ArrayList<ScheduledTimeList> otherPlanListsChanged;		// 本學期其他事情的 list，時間轉代號
	private ArrayList<OtherPlan> otherPlan;							// 本學期其他事情的 list，完整版
	private ArrayList<String> timeID;
	private ArrayList<JCheckBox> checkboxList;
	private ArrayList<String> checkboxNameList;
	private ArrayList<JComboBox> timeComboList;
	
	// Next Page
	private JPanel nextPagePanel;
	private JButton nextPageButton;
	private ArrayList<String> weekdayArray;


	
	/* Constructor */
	
	public ScheduledPanel() {

		this.listMaxSize = 10;
		this.planToTakeList = new ArrayList<String>();
		this.planToTakeNameList = new ArrayList<String>();
		this.courseBtnList = new ArrayList<JButton>();
		this.otherPlanLists = new ArrayList<ScheduledTimeList>();
		this.otherPlanListsChanged = new ArrayList<ScheduledTimeList>();
		this.otherPlan = new ArrayList<OtherPlan>();
		this.timeID = new ArrayList<String>();
		this.checkboxList = new ArrayList<JCheckBox>();
		this.checkboxNameList = new ArrayList<String>();
		this.timeComboList = new ArrayList<JComboBox>();
		this.plantoTakeLabel = new JLabel("已決定要修習的課程");
		this.otherPlanLabel = new JLabel("已有其他安排的時間");
		this.plantoTakeLabel.setForeground(Color.WHITE);;
		this.otherPlanLabel.setForeground(Color.WHITE);;
		this.weekdayArray = new ArrayList<String>();
		this.weekdayArray.add("一");
		this.weekdayArray.add("二");
		this.weekdayArray.add("三");
		this.weekdayArray.add("四");
		this.weekdayArray.add("五");		
		
		setLayout(new BorderLayout());
		add(createTitlePanel(),BorderLayout.NORTH);
		createMainPanel();
		add(mainPanel,BorderLayout.CENTER);
//		add(createNextPagePanel(),BorderLayout.SOUTH);
		
	}
	
	/* Title Panel */
	
	private JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.setPreferredSize(new Dimension(800,55));
		arrange(titlePanel, "Background");
//		icon = new ImageIcon("img/schedule.png", "Schedule");	
		icon = new ImageIcon(getClass().getResource("img/schedule.png"));	
		JLabel label = new JLabel(icon);		
		titlePanel.add(label);
		return titlePanel;
	}
	
	
	/* Main Panel */
	
	private void createMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());		
		arrange(mainPanel, "Background");
		
		mainPanel.add(createPlantoTakePanel());
		mainPanel.add(createOtherPlanPanel());
		mainPanel.add(createNextPagePanel());		
		
		saveOther.setEnabled(false);
		resetOther.setEnabled(false);
		checkBoxPanel.setEnabled(false);
		addOtherPanel.setEnabled(false);
		addOtherTextField.setEnabled(false);
		addOtherBtn.setEnabled(false);
	}
	

	
	
	/* plantoTakePanel
	 * 從 Course1082 課程中選出已決定要修的課（確切哪一門課，用 courseID）*/
	
	private JPanel createPlantoTakePanel() {
		plantoTakePanel = new JPanel();
		plantoTakePanel.setPreferredSize(new Dimension(800, 220));
		plantoTakePanel.setLayout(new BorderLayout());
		plantoTakePanel.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 10, BACKGROUND));

		// title panel
		plantoTakeTitlePanel = new JPanel();
		arrange(plantoTakeTitlePanel, "Title");
		plantoTakeTitlePanel.add(plantoTakeLabel);
		
		// course panel		
		coursePanel = new JPanel();	
		arrange(coursePanel, "Inner");
		coursePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
		
		// main panel
		plantoTakeMainPanel = new JPanel();
		plantoTakeMainPanel.setLayout(new BorderLayout());
		plantoTakeMainPanel.add(AddCoursePanel(), BorderLayout.NORTH);
		plantoTakeMainPanel.add(coursePanel, BorderLayout.CENTER);
		arrange(plantoTakeMainPanel, "Main");
		
		// panel
		plantoTakePanel.add(plantoTakeTitlePanel, BorderLayout.NORTH);
		plantoTakePanel.add(plantoTakeMainPanel, BorderLayout.CENTER);	
		plantoTakePanel.add(courseSaveResetPanel(), BorderLayout.SOUTH);				
		return plantoTakePanel;
	}
	
	// Add Course Panel	
	private JPanel AddCoursePanel() {
		addCoursePanel = new JPanel();
		addCoursePanel.setLayout(new FlowLayout());
		arrange(addCoursePanel, "Inner");
		addCourseLabel = new JLabel("請輸入課程代碼：");
		addCourseLabel.setForeground(INNER_LABEL);
		addCourseField = new JTextField(20);
		addCourseField.setHorizontalAlignment(JTextField.CENTER);
		addCourseField.setForeground(INNER_LABEL);
	
		addCourseBtn = new JButton("Add");
		arrange(addCourseBtn, "InnerBtn");
		class AddListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {			
				String courseID = addCourseField.getText();
				try {
					String sql_match = String.format("SELECT * FROM Course1082_user WHERE subNum = '%s'", courseID);
					System.out.println(sql_match);
					ResultSet match = doSql(sql_match, "executeQuery");
					match.next();
					String courseName = match.getString("subNam");
					System.out.println("Course Name: " + courseName);
					if(planToTakeList.size() >= 8) {
						JOptionPane.showMessageDialog(null, "已輸入超過 8 門課程");	
					}
					else if(courseName.equals("")) {
						JOptionPane.showMessageDialog(null, "無法選擇該門課程");	
					}
					else if(planToTakeList.contains(courseID)) {
						JOptionPane.showMessageDialog(null, "不得加入重複課程");	
					}
					else {
						JButton courseButton = new JButton(courseName);
						planToTakeList.add(match.getString("subNum"));
						planToTakeNameList.add(courseName);
						class courseBtnListener implements ActionListener{
							public void actionPerformed(ActionEvent event) {
								courseButton.setEnabled(false);
								courseButton.setVisible(false);
								planToTakeList.remove(courseID);
								planToTakeNameList.remove(courseName);
								// Console
								System.out.println("Plan To Take List: " + planToTakeList);
								System.out.println("Plan To Take Name List: " + planToTakeNameList);

							}
						}
						courseButton.addActionListener(new courseBtnListener());
						courseButton.setEnabled(true);
						courseButton.setVisible(true);
						coursePanel.add(courseButton);
						courseBtnList.add(courseButton);
						revalidate();
					}									
				}
				catch(SQLException e) {
					JOptionPane.showMessageDialog(null, "無法選擇該門課程");	
					System.out.println("Add Course Error!");
				}
				finally{
					addCourseField.setText(null);
				}				
			}
		}
		addCourseBtn.addActionListener(new AddListener());
		
		addCoursePanel.add(addCourseLabel);
		addCoursePanel.add(addCourseField);
		addCoursePanel.add(addCourseBtn);
		return addCoursePanel;
	}

	// Save and Reset Panel
	private JPanel courseSaveResetPanel() {
		JPanel saveResetPanel = new JPanel();
		arrange(saveResetPanel, "Inner");
		JButton save = new JButton("Save");
		JButton reset = new JButton("Reset");
		arrange(save, "InnerBtn");
		arrange(reset, "InnerBtn");
		reset.setEnabled(false);

		// save
		class SaveListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				save.setEnabled(false);
				reset.setEnabled(true);
				coursePanel.setEnabled(false);
				addCoursePanel.setEnabled(false);
				addCourseField.setEnabled(false);
				addCourseBtn.setEnabled(false);
				for(JButton courseButton: courseBtnList) {
					courseButton.setEnabled(false);
				}
				
				saveOther.setEnabled(true);
				resetOther.setEnabled(false);
				checkBoxPanel.setEnabled(true);
				addOtherPanel.setEnabled(true);
				addOtherTextField.setEnabled(true);
				addOtherBtn.setEnabled(true);

				for(JCheckBox check: checkboxList) {
					check.setEnabled(true);
				}
				for(JComboBox combo: timeComboList) {
					combo.setEnabled(true);
				}
				
				// Console
				System.out.println("Saved Plan To Take List: " + planToTakeList);
				System.out.println("Saved Plan To Take Name List: " + planToTakeNameList);
			}
		}
		save.addActionListener(new SaveListener());
		
		// reset
		class ResetListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {			
				save.setEnabled(true);
				reset.setEnabled(false);
				coursePanel.setEnabled(true);
				addCoursePanel.setEnabled(true);
				addCourseField.setEnabled(true);
				addCourseBtn.setEnabled(true);
				addCourseField.setText(null);;
				planToTakeList.clear();
				planToTakeNameList.clear();
				courseBtnList.clear();
				coursePanel.removeAll();
								
				saveOther.setEnabled(false);
				resetOther.setEnabled(false);
				checkBoxPanel.setEnabled(false);
				addOtherPanel.setEnabled(false);
				addOtherTextField.setEnabled(false);
				addOtherBtn.setEnabled(false);
				nextPageButton.setEnabled(false);

				for(JCheckBox check: checkboxList) {
					check.setEnabled(false);
				}
				for(JComboBox combo: timeComboList) {
					combo.setEnabled(false);
				}
				
				// Console
				System.out.println("Reseted Plan To Take List: " + planToTakeList);
				System.out.println("Reseted Plan To Take Name List: " + planToTakeNameList);
			}
		}
		reset.addActionListener(new ResetListener());
		
		saveResetPanel.add(save);
		saveResetPanel.add(reset);
		return saveResetPanel;
	}
	
		


	/* otherPlanPanel */
		
	private JPanel createOtherPlanPanel() {
		otherPlanPanel = new JPanel();
		otherPlanPanel.setPreferredSize(new Dimension(800,455));
		otherPlanPanel.setLayout(new BorderLayout());
		otherPlanPanel.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, BACKGROUND));
		
		// title panel
		otherPlanTitlePanel = new JPanel();	
		arrange(otherPlanTitlePanel, "Title");
		otherPlanTitlePanel.add(otherPlanLabel);

		// combine panel
		createOtherPlanMainPanel();
		otherPlanPanel.add(otherPlanTitlePanel, BorderLayout.NORTH);
		otherPlanPanel.add(otherPlanMainPanel, BorderLayout.CENTER);
		otherPlanPanel.add(otherSaveResetPanel(), BorderLayout.SOUTH);
		
		return otherPlanPanel;
	}
	
	// Main Panel
	private void createOtherPlanMainPanel() {
		otherPlanMainPanel = new JPanel();
		otherPlanMainPanel.setLayout(new BorderLayout());
		arrange(otherPlanMainPanel, "Main");		

		checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new GridLayout(listMaxSize,1));
		arrange(checkBoxPanel, "Inner");

		// combine panel
		otherPlanMainPanel.add(AddOtherPanel(), BorderLayout.NORTH);
		otherPlanMainPanel.add(checkBoxPanel, BorderLayout.CENTER);
		
	}
	
	// Single Plan Panel
	private JPanel createSingleOtherPanel(String name) {
		JPanel singleScheduledPanel = new JPanel();
		singleScheduledPanel.setLayout(new GridLayout(1,2));
		arrange(singleScheduledPanel, "Inner");
		ScheduledTimeList otherPlanList = new ScheduledTimeList(name);
		
		// check box for 已有安排的時間
		
		JCheckBox checkBox = new JCheckBox(name);
		checkBox.setForeground(INNER_LABEL);
		checkboxList.add(checkBox);
		checkboxNameList.add(checkBox.getText());
		
		class CheckBoxListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				if(checkBox.isSelected()) {
					otherPlanLists.add(otherPlanList);
				}
				else {
					String notSelected = checkBox.getText();
					if(otherPlanLists.size() > 0) {
						for(int i = 0; i < otherPlanLists.size(); i++) {
							if(otherPlanLists.get(i).getCourseName().equals(notSelected)) {
								otherPlanLists.remove(otherPlanLists.get(i));
							}							
						}
					}					
				}
				
				// Console
				otherPlanList.scheduledTimeDetail();
			}
		}
		checkBox.addActionListener(new CheckBoxListener());
		
		// combo box
		
		JPanel timePanel = new JPanel();
		arrange(timePanel, "Inner");
		JLabel dayLabel = new JLabel("星期");
		JLabel timeLabel = new JLabel("時間：");
		JLabel fromLabel = new JLabel("從");
		JLabel endLabel = new JLabel("到");
		dayLabel.setForeground(INNER_LABEL);
		timeLabel.setForeground(INNER_LABEL);
		fromLabel.setForeground(INNER_LABEL);
		endLabel.setForeground(INNER_LABEL);

		
		// 星期
		JComboBox<String> dayComboBox = new JComboBox<String>();
		dayComboBox.setForeground(INNER_LABEL);
		dayComboBox.addItem("一");
		dayComboBox.addItem("二");
		dayComboBox.addItem("三");
		dayComboBox.addItem("四");
		dayComboBox.addItem("五");
		dayComboBox.setSelectedIndex(-1);
		
		class DayComboBoxListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {

				if(checkBox.isSelected()) {
					otherPlanList.setDay((String)dayComboBox.getSelectedItem());
					// Console
					otherPlanList.scheduledTimeDetail();
				}
				else {
					JOptionPane.showMessageDialog(null, "請先勾選事件");
					dayComboBox.setSelectedIndex(-1);
				}				
			}
		}
		dayComboBox.addActionListener(new DayComboBoxListener());
		
		// 開始
		JComboBox<String> fromComboBox = new JComboBox<String>();
		fromComboBox.setForeground(INNER_LABEL);
		for(int i = 6; i < 22; i++) {
			fromComboBox.addItem(Integer.toString(i));
		}
		fromComboBox.setSelectedIndex(-1);
		
		class FromComboBoxListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				if(dayComboBox.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "請先選擇星期");
					fromComboBox.setSelectedIndex(-1);
				}
				else {
					otherPlanList.setFromTime((String)fromComboBox.getSelectedItem());
					// Console
					otherPlanList.scheduledTimeDetail();
				}
			}
		}
		fromComboBox.addActionListener(new FromComboBoxListener());
		
		// 結束
		JComboBox<String> endComboBox = new JComboBox<String>();
		endComboBox.setForeground(INNER_LABEL);
		for(int i = 7; i <= 22; i++) {
			endComboBox.addItem(Integer.toString(i));
		}
		endComboBox.setSelectedIndex(-1);
		
		class EndComboBoxListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				String from = (String)(fromComboBox.getSelectedItem());
				String end = (String)(endComboBox.getSelectedItem());
				if(fromComboBox.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "請先選擇開始時間");
					fromComboBox.setSelectedIndex(-1);
					endComboBox.setSelectedIndex(-1);
				}
				else if(Integer.parseInt(from) >= Integer.parseInt(end)) {	
					JOptionPane.showMessageDialog(null, "結束時間須晚於開始時間");
					fromComboBox.setSelectedIndex(-1);
					endComboBox.setSelectedIndex(-1);
				}
				else {
					otherPlanList.setFromTime((String)fromComboBox.getSelectedItem());
					otherPlanList.setEndTime((String)endComboBox.getSelectedItem());
					// Console
					otherPlanList.scheduledTimeDetail();
				}
			}
		}
		endComboBox.addActionListener(new EndComboBoxListener());
		
		// add combo boxes to timeComboList
		timeComboList.add(dayComboBox);
		timeComboList.add(fromComboBox);
		timeComboList.add(endComboBox);
		
		// combine panels
		timePanel.add(dayLabel);
		timePanel.add(dayComboBox);
		timePanel.add(timeLabel);
		timePanel.add(fromLabel);
		timePanel.add(fromComboBox);
		timePanel.add(endLabel);
		timePanel.add(endComboBox);
				
		singleScheduledPanel.add(checkBox);
		singleScheduledPanel.add(timePanel);
		
		return singleScheduledPanel;
	}
	
	// Add Panel
	private JPanel AddOtherPanel() {
		addOtherPanel = new JPanel();
		arrange(addOtherPanel, "Inner");
		addOtherBtn = new JButton("Add");
		arrange(addOtherBtn, "InnerBtn");
		
		addOtherLabel = new JLabel("事件名稱：");
		addOtherLabel.setForeground(INNER_LABEL);
		addOtherTextField = new JTextField(20);
		addOtherTextField.setHorizontalAlignment(JTextField.CENTER);
		addOtherTextField.setForeground(INNER_LABEL);
		
		class AddListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				String name = addOtherTextField.getText();
				if(checkboxList.size() > 0) {
					if(checkboxList.size() >= listMaxSize) {
						JOptionPane.showMessageDialog(null, "已輸入超過 10 個事件");
					}
					else if(checkboxNameList.contains(name)) {
						JOptionPane.showMessageDialog(null, "請勿輸入重複的事件名稱");	
					}
					else {
						checkBoxPanel.add(createSingleOtherPanel(addOtherTextField.getText()));
						revalidate();
					}									
				}
				else {
					checkBoxPanel.add(createSingleOtherPanel(addOtherTextField.getText()));
					revalidate();
				}
				addOtherTextField.setText(null);				
			}
		}
		addOtherBtn.addActionListener(new AddListener());

		// combine panel
		addOtherPanel.add(addOtherLabel);
		addOtherPanel.add(addOtherTextField);
		addOtherPanel.add(addOtherBtn);
		
		return addOtherPanel;
	}
	
	// Save and Reset Panel
	private JPanel otherSaveResetPanel() {
		JPanel saveResetPanel = new JPanel();
		arrange(saveResetPanel, "Inner");
		saveOther = new JButton("Save");
		resetOther = new JButton("Reset");
		arrange(saveOther, "InnerBtn");
		arrange(resetOther, "InnerBtn");

		resetOther.setEnabled(false);
		
		class SaveListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				// save
				boolean error = false;
				if(otherPlanLists.size() > 0) {
					for(int i = 0; i < otherPlanLists.size(); i++) {
						if(otherPlanLists.get(i).getEndTime() == null) {
							error = true;
						}
					}
				}
				if(!error) {
					saveOther.setEnabled(false);
					resetOther.setEnabled(true);
					checkBoxPanel.setEnabled(false);
					addOtherPanel.setEnabled(false);
					addOtherTextField.setEnabled(false);
					addOtherBtn.setEnabled(false);
					for(JCheckBox check: checkboxList) {
						check.setEnabled(false);
					}
					for(JComboBox combo: timeComboList) {
						combo.setEnabled(false);
					}
					nextPageButton.setEnabled(true);
					
					// Console
					String listsDetail = "";
					for(ScheduledTimeList list: otherPlanLists) {
						listsDetail = listsDetail + list.scheduledTimeDetailStr() + ", ";
					}
					System.out.println("Saved Other Plan Lists: " + listsDetail);
					System.out.println("Saved Checkbox Name List: " + checkboxNameList);
				}
				else {
					JOptionPane.showMessageDialog(null, "勾選的事件中，有時間尚未填寫完整");
				}				
			}
		}
		saveOther.addActionListener(new SaveListener());
		
		class ResetListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				saveOther.setEnabled(true);
				resetOther.setEnabled(false);
				checkBoxPanel.setEnabled(true);
				addOtherPanel.setEnabled(true);
				addOtherTextField.setEnabled(true);
				addOtherBtn.setEnabled(true);
				addOtherTextField.setText(null);;
				otherPlanLists.clear();
				checkboxList.clear();
				checkboxNameList.clear();
				timeComboList.clear();
				checkBoxPanel.removeAll();
				validate();
				repaint();
				nextPageButton.setEnabled(false);
				// Console
				String listsDetail = "";
				for(ScheduledTimeList list: otherPlanLists) {
					listsDetail = listsDetail + list.scheduledTimeDetailStr() + ", ";
				}
				System.out.println("Saved Other Plan Lists: " + listsDetail);
				System.out.println("Reseted Checkbox Name List: " + checkboxNameList);

			}
		}
		resetOther.addActionListener(new ResetListener());
		
		saveResetPanel.add(saveOther);
		saveResetPanel.add(resetOther);
		
		return saveResetPanel;
	}
	
	
	
	
	/* Next Page */
	
	// otherPlanLists 從時間轉成代號
	public void changeTimetoID() {
		if(otherPlanLists.size() > 0) {
			for(int i = 0; i < otherPlanLists.size(); i++) {
				ScheduledTimeList otherPlanChanged = new ScheduledTimeList(otherPlanLists.get(i).getCourseName());
				otherPlanChanged.setDay(otherPlanLists.get(i).getDay());
				switch(otherPlanLists.get(i).getFromTime()) {
				case "6": otherPlanChanged.setFromTime("A");break;
				case "7": otherPlanChanged.setFromTime("B");break;
				case "8": otherPlanChanged.setFromTime("1");break;
				case "9": otherPlanChanged.setFromTime("2");break;
				case "10": otherPlanChanged.setFromTime("3");break;
				case "11": otherPlanChanged.setFromTime("4");break;
				case "12": otherPlanChanged.setFromTime("C");break;
				case "13": otherPlanChanged.setFromTime("D");break;
				case "14": otherPlanChanged.setFromTime("5");break;
				case "15": otherPlanChanged.setFromTime("6");break;
				case "16": otherPlanChanged.setFromTime("7");break;
				case "17": otherPlanChanged.setFromTime("8");break;
				case "18": otherPlanChanged.setFromTime("E");break;
				case "19": otherPlanChanged.setFromTime("F");break;
				case "20": otherPlanChanged.setFromTime("G");break;
				case "21": otherPlanChanged.setFromTime("H");break;				
				}
				switch(otherPlanLists.get(i).getEndTime()) {
				case "7": otherPlanChanged.setEndTime("A");break;
				case "8": otherPlanChanged.setEndTime("B");break;
				case "9": otherPlanChanged.setEndTime("1");break;
				case "10": otherPlanChanged.setEndTime("2");break;
				case "11": otherPlanChanged.setEndTime("3");break;
				case "12": otherPlanChanged.setEndTime("4");break;
				case "13": otherPlanChanged.setEndTime("C");break;
				case "14": otherPlanChanged.setEndTime("D");break;
				case "15": otherPlanChanged.setEndTime("5");break;
				case "16": otherPlanChanged.setEndTime("6");break;
				case "17": otherPlanChanged.setEndTime("7");break;
				case "18": otherPlanChanged.setEndTime("8");break;
				case "19": otherPlanChanged.setEndTime("E");break;
				case "20": otherPlanChanged.setEndTime("F");break;
				case "21": otherPlanChanged.setEndTime("G");break;
				case "22": otherPlanChanged.setEndTime("H");break;				
				}
				otherPlanListsChanged.add(otherPlanChanged);
			}
		}
		// Console
		String listsDetail = "";
		for(ScheduledTimeList list: otherPlanListsChanged) {
			listsDetail = listsDetail + list.scheduledTimeDetailStr() + ", ";
		}
		System.out.println("Other Plan Lists Changed: " + listsDetail);		
	}
	
	// 時間代號 array list
	public void createTimeID() {
		timeID.add("A");
		timeID.add("B");
		for(int i = 1; i <= 4; i++) {
			timeID.add("" + i);
		}
		timeID.add("C");
		timeID.add("D");
		for(int i = 5; i <= 8; i++) {
			timeID.add("" + i);
		}
		timeID.add("E");
		timeID.add("F");
		timeID.add("G");
		timeID.add("H");
	}
	
	// 轉換完成的 list
	public void createOtherPlan() {
		changeTimetoID();
		createTimeID();
		if(otherPlanListsChanged.size() > 0) {
			for(int i = 0; i < otherPlanListsChanged.size(); i++) {
				OtherPlan singlePlan = new OtherPlan(otherPlanListsChanged.get(i).getCourseName(), otherPlanListsChanged.get(i).getDay());
				int indexFromID = timeID.indexOf(otherPlanListsChanged.get(i).getFromTime());
				int indexEndID = timeID.indexOf(otherPlanListsChanged.get(i).getEndTime());
				String time = "";
				for(int j = indexFromID; j <= indexEndID; j++) {
					time = time + timeID.get(j);
				}
				singlePlan.setTime(time);				
				otherPlan.add(singlePlan);
			}
		}
		// Console
		String listsDetail = "";
		for(OtherPlan list: otherPlan) {
			listsDetail = listsDetail + list.otherPlanDetail() + ", ";
		}
		System.out.println("Other Plan: " + listsDetail);		
		
	}
	
	// 分解 subTime to courseTimeList
	private ArrayList<String> findTime(String courseTime) {
		ArrayList<Integer> dayIndexList = new ArrayList<Integer>();
		ArrayList<String> courseTimeList = new ArrayList<String>();
		for(String weekday: weekdayArray) {
			if(courseTime.contains(weekday)) {
				int index = courseTime.indexOf(weekday);
				dayIndexList.add(index);
			}
		}
		String time = "";
		for(int i = 0; i < dayIndexList.size(); i++) {
			if(i != dayIndexList.size() - 1) {
				time = courseTime.substring(dayIndexList.get(i), dayIndexList.get(i+1));
			}else {
				time = courseTime.substring(dayIndexList.get(i));
			}
			courseTimeList.add(time);
		}
		return courseTimeList;		
	}
	
	// 從 Course1082_user 找出特定星期
	private String matchDay(ArrayList<String> courseTimeList, String day) {
		String matchDay = null;
		for(int i = 0; i < courseTimeList.size(); i++) {
			if(courseTimeList.get(i).contains(day)) {
				matchDay = courseTimeList.get(i);
			}
		}
		return matchDay;
	}
	
	
	// Next Page Panel
	private JPanel createNextPagePanel() {
		nextPagePanel = new JPanel();
		arrange(nextPagePanel, "Background");
		nextPageButton = new JButton("Next Page");
		arrange(nextPageButton, "NextBtn");
		nextPageButton.setEnabled(false);
		
		class NextListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
								
				// 1. 已決定要修的課 PlanToTake
				System.out.println("\nNext Page: ");
				doSql("DELETE FROM PlanToTake", "execute");		// clear the table
				if(planToTakeList.size() > 0) {
					String where_subNums = String.format("subNum = '%s'", planToTakeList.get(0));
					for(int i = 1; i < planToTakeList.size(); i++) {
						where_subNums = where_subNums + String.format(" OR subNum = '%s'", planToTakeList.get(i));
					}
					String sql_insert1 = String.format("INSERT INTO PlanToTake SELECT DISTINCT * FROM Course1082 WHERE %s", where_subNums);
					System.out.println("＊已決定要修的課 PlanToTake＊\n" + sql_insert1);
					doSql(sql_insert1, "execute");						
				}
				
				// 2. 已有其他安排的時間 OtherPlan	
				System.out.println("＊已有其他安排的時間 Other Plan＊");
				createOtherPlan();				
				doSql("DELETE FROM OtherPlan", "execute");		// clear the table
				if(otherPlan.size() > 0) {
					for(int i = 0; i < otherPlan.size(); i++) {
						String sql_insert2 = String.format("INSERT INTO OtherPlan (subNam, subTime) VALUES ('%s', '%s')", otherPlan.get(i).getName(), otherPlan.get(i).getDay() + otherPlan.get(i).getTime());
						System.out.println(sql_insert2);
						doSql(sql_insert2, "execute");
					}
				}
				
				// 3. 更新 Course1082_user：排除 subNam 在 planToTakeNameList 裡的				
				if(planToTakeNameList.size() > 0) {
					String where_subNams = String.format("subNam = '%s'", planToTakeNameList.get(0));
					for(int i = 1; i < planToTakeNameList.size(); i++) {
						where_subNams = where_subNams + String.format(" OR subNam = '%s'", planToTakeNameList.get(i));
					}
					String sql_subNam = String.format("DELETE FROM Course1082_user WHERE %s", where_subNams);
					System.out.println("＊更新 Course1082_user＊\n" + sql_subNam);
					doSql(sql_subNam, "execute");						
				}
				
//				String sql_subNam = String.format("DELETE FROM Course1082_user WHERE Course1082_user.subNam = PlanToTake.subNam");
//				doSql(sql_subNam, "execute");						

				
				// 4. 更新 Course1082_user：排除 subTime 在 PlanToTake (database) 裡的
				if(planToTakeList.size() > 0) {
					for(int i = 0; i < planToTakeList.size(); i++) {
						ArrayList<String> courseToDelete = new ArrayList<String>();
						try {
							String sql_course = String.format("SELECT * FROM PlanToTake WHERE subNum = '%s'", planToTakeList.get(i));
							ResultSet course = doSql(sql_course, "executeQuery");
							course.next();
							String courseTime = course.getString("subTime");
							ArrayList<String> planToTakeDayTimeList= findTime(courseTime);
							for(int p = 0; p < planToTakeDayTimeList.size(); p++) {
								String planToTakeDayTime = planToTakeDayTimeList.get(p);
								String planToTakeDay = planToTakeDayTime.substring(0, 1);
								String planToTakeTime = planToTakeDayTime.substring(1);
								String sql_dayContained = String.format("SELECT * FROM Course1082_user WHERE subTime LIKE '%s'",  "%" + planToTakeDay + "%");
								ResultSet dayContained = doSql(sql_dayContained, "executeQuery");
								while(dayContained.next()) {
									String courseID = dayContained.getString("subNum");
									String dayTime = dayContained.getString("subTime");									
									String courseDayMatch = matchDay(findTime(dayTime), planToTakeDay);
									String courseDayMatchTime = courseDayMatch.substring(1);									
									for(int j = 0; j < planToTakeTime.length(); j++) {
										for(int k = 0; k < courseDayMatchTime.length(); k++) {
											if(courseDayMatchTime.charAt(k) == planToTakeTime.charAt(j)) {
												courseToDelete.add(courseID);
//												System.out.println("Delete: " + dayTime);
											}
										}										
									}
								}								
							}
							if(courseToDelete.size() > 0) {
								String sql_subTime = String.format("DELETE FROM Course1082_user WHERE subNum = '%s'", courseToDelete.get(0));
								for(int j = 1; j < courseToDelete.size(); j++) {
									sql_subTime = sql_subTime + String.format(" OR subNum = '%s'", courseToDelete.get(j));
								}
								doSql(sql_subTime, "execute");
								System.out.println("＊更新 Course1082_user from PlanToTake＊");
							}
						}
						catch(Exception e){
							System.out.println("Error: Get subTime from PlanToTake");
							e.printStackTrace();
						}
					}				
				}	
				
				// 5. 更新 Course1082_user：排除 subTime 在 otherPlan 裡的				
				if(otherPlan.size() > 0) {
					for(int i = 0; i < otherPlan.size(); i++) {
						ArrayList<String> courseToDelete = new ArrayList<String>();
						String otherPlanDay = otherPlan.get(i).getDay();
						String otherPlanTime = otherPlan.get(i).getTime();
						try {
							String sql_dayContained = String.format("SELECT * FROM Course1082_user WHERE subTime LIKE '%s'",  "%" + otherPlan.get(i).getDay() + "%");
							ResultSet dayContained = doSql(sql_dayContained, "executeQuery");
							while(dayContained.next()) {
								String courseID = dayContained.getString("subNum");
								String dayTime = dayContained.getString("subTime");
								String courseDayMatch = matchDay(findTime(dayTime), otherPlanDay);
								String courseDayMatchTime = courseDayMatch.substring(1);
								for(int j = 0; j < otherPlanTime.length(); j++) {
									for(int k = 0; k < courseDayMatchTime.length(); k++) {
										if(courseDayMatchTime.charAt(k) == otherPlanTime.charAt(j)) {
											courseToDelete.add(courseID);
//											System.out.println("Delete: " + dayTime);
										}
									}										
								}
							}
							if(courseToDelete.size() > 0) {
								String sql_subTime = String.format("DELETE FROM Course1082_user WHERE subNum = '%s'", courseToDelete.get(0));
								for(int j = 1; j < courseToDelete.size(); j++) {
									sql_subTime = sql_subTime + String.format(" OR subNum = '%s'", courseToDelete.get(j));
								}
								doSql(sql_subTime, "execute");
								System.out.println("＊更新 Course1082_user from OtherPlan＊");
							}
						}
						catch(Exception e) {
							System.out.println("Error: Delete Course1082_user from otherPlan");
							e.printStackTrace();
						}	
					}				
				}
				
				// 6. Next Panel
				setVisible(false);
				HomeFrame homeFrame = getFrame();
				homeFrame.add(new SelectCoursePanel());
				homeFrame.setSize(760, 720);
				homeFrame.setTitle("Course Registration Guide - Select Course");
				homeFrame.setLocationRelativeTo(null);
			
			}
		}
		nextPageButton.addActionListener(new NextListener());
		
		nextPagePanel.add(nextPageButton);
		return nextPagePanel;		
	}
	
	
	/* Arrange Panels */	

	public void arrange(JPanel panel, String type) {
		if(type.equals("Title")) {
			panel.setBackground(TITLE);
		}
		else if(type.equals("Main")) {
			panel.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
		}
		else if(type.equals("Inner")) {
			panel.setBackground(INNER);
		}
		else if(type.equals("Background")) {
			panel.setBackground(BACKGROUND);
		}
	}
	
	/* Arrange Buttons */	
	
	public void arrange(JButton btn, String type) {
		if(type.equals("InnerBtn")) {
			btn.setPreferredSize(new Dimension(50, 20));
			btn.setForeground(Color.WHITE);
			btn.setBackground(TITLE);
			btn.setBorder(BorderFactory.createLineBorder(TITLE, 1, true));
			btn.setOpaque(true);
		}
		else if(type.equals("NextBtn")) {
			btn.setPreferredSize(new Dimension(100, 25));
			btn.setForeground(Color.WHITE);
			btn.setBackground(NEXTCOLOR);
			btn.setBorder(BorderFactory.createLineBorder(NEXTCOLOR, 1, true));
			btn.setOpaque(true);

		}
		
	}
	
	
	
	/* 寫 SQL */
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
	
	
	/* getFrame */
	private HomeFrame getFrame() {
		for(Frame frame:JFrame.getFrames()) {
			if(frame.getTitle().equals("Course Registration Guide - Schedule Setup")) {
				HomeFrame homeFrame = (HomeFrame) frame;
				return homeFrame;
			}
		}
		return null;
	}
	

	
}
