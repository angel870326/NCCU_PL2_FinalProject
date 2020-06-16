import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SelectCoursePanel extends JPanel{
	private static final int PANEL_WIDTH=760;
	private static final int PANEL_HEIGHT=720;
	private static final int FIELD_WIDTH=10;
	private int currentIndex;
	private ArrayList<JButton> deleteButtonList;
	private ArrayList<JButton> clearButtonList;
	private ArrayList<JButton> saveButtonList;
	private JPanel centerPanel;
	private JPanel nextPagePanel;
	private JScrollPane centerScrollPane;
	private ArrayList<JPanel> centerPanelList;
	private ArrayList<JButton> editButtonList;
	private JPanel mainPanel;
	private JPanel exchangePanel;
	private JButton exchangeButton;
	private JComboBox exchangeComboLeft;
	private JComboBox exchangeComboRight;
	private JButton addButton;
	private JButton runButton;
	private JButton nextPageButton;
	private JPanel resultPanel;
	private ArrayList<JTextField> classNameFieldList;
	private ArrayList<ArrayList<JRadioButton>> classNameRadioButtonList;
	private ArrayList<JTextField> teacherNameFieldList;
	private ArrayList<JComboBox> classTypeComboList;
	private ArrayList<ArrayList<JCheckBox>> dateCheckBoxList;
	private ArrayList<ArrayList<JRadioButton>> languageRadioButtonList;
	private ArrayList<ArrayList<Lecture>> orderLectureList;
	private ArrayList<ArrayList<Lecture>> resultLectureList;
	public static ArrayList<ArrayList<Lecture>> filterLectureList;
	private JLabel resultLabel;
	private static final Color downColor = Color.decode("#E4DAD2");
	private static final Color nextPageColor = Color.decode("#E4DAD2");
	private static final Color centerColor = Color.decode("#d8e8d8");
	private static final Color centerListColor = Color.decode("#d8e8d8");
	private static final Color centerListBorderColor = Color.decode("#c2d0da");
	private static final Color INNER_LABEL = Color.decode("#454545"); 
	private static final Color nextBtnColor = Color.decode("#d5c6b9");
	private static final Color downBtnColor = Color.decode("#d5c6b9");
	
	
	public SelectCoursePanel() {
		setSize(PANEL_WIDTH,PANEL_HEIGHT);
		setLayout(new BorderLayout());
		createMainPanel();
		add(mainPanel,BorderLayout.CENTER);	
		createNextPagePanel();
		arrange(nextPagePanel,"NextPageColor");
		add(nextPagePanel,BorderLayout.SOUTH);
		
	}
	
	public void createNextPagePanel() {
		nextPagePanel=new JPanel();
		nextPageButton=new JButton("Next Page");
		arrange(nextPageButton, "Next");
		nextPageButton.setEnabled(false);
		class nextPageListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				HomeFrame homeFrame = getFrame();
//				System.out.println("this: "+getFilterLectureList().size());
				try {
					homeFrame.add(new FinalSchedulePanel());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				homeFrame.setSize(1500,810);
				homeFrame.setTitle("Course Registration Guide - Final Schedule");
				homeFrame.setLocationRelativeTo(null);
				
			}
			
		}
		ActionListener n=new nextPageListener();
		nextPageButton.addActionListener(n);
		nextPagePanel.add(nextPageButton);
	}
	private HomeFrame getFrame() {
		for(Frame frame:JFrame.getFrames()) {
			if(frame.getTitle().equals("Course Registration Guide - Select Course")) {
				HomeFrame homeFrame = (HomeFrame) frame;
				return homeFrame;
			}
		}
		return null;
	}
	public void arrange(JPanel panel, String type) {
		if(type.equals("Title")) {
			panel.setBackground(downColor);
		}
		else if(type.equals("CenterColor")) {
			panel.setBackground(centerColor);
		}
		else if(type.equals("CenterListColor")) {
			panel.setBackground(centerListColor);
		}
		else if(type.equals("NextPageColor")) {
			panel.setBackground(nextPageColor);
		}
		else if(type.equals("DownColor")) {
			panel.setBackground(downColor);
		}
		else if(type.equals("CenterListBorderColor")) {
			panel.setBackground(centerListBorderColor);
		}
		
	}
	public void arrange(JButton btn, String type) {
		if(type.equals("Next")) {
			btn.setPreferredSize(new Dimension(100, 25));
			btn.setForeground(Color.WHITE);
			btn.setBackground(nextBtnColor);
			btn.setBorder(BorderFactory.createLineBorder(nextBtnColor, 1, true));
			btn.setOpaque(true);
		}
		else if(type.equals("DownBtn")) {
			btn.setPreferredSize(new Dimension(150, 20));
			btn.setForeground(Color.WHITE);
			btn.setBackground(downBtnColor);
			btn.setBorder(BorderFactory.createLineBorder(downBtnColor, 1, true));
			btn.setOpaque(true);
		}
		else if(type.equals("ListBtn")) {
			btn.setPreferredSize(new Dimension(70, 20));
			btn.setForeground(Color.WHITE);
			btn.setBackground(centerListBorderColor);
			btn.setBorder(BorderFactory.createLineBorder(centerListBorderColor, 1, true));
			btn.setOpaque(true);

		}
		
	}
	
	
	public void createMainPanel() {
		mainPanel=new JPanel();
		mainPanel.setLayout(new BorderLayout());
		createresultPanel();
		createCenterPanel();
		createExchangePanel();
		arrange(resultPanel,"DownColor");
		arrange(centerPanel,"CenterColor");
		arrange(exchangePanel,"DownColor");
		mainPanel.add(resultPanel,BorderLayout.SOUTH);
		mainPanel.add(centerScrollPane,BorderLayout.CENTER);
		mainPanel.add(exchangePanel,BorderLayout.NORTH);
	}
	
	public void createExchangePanel() {
		exchangePanel=new JPanel();
		exchangeButton=new JButton("exchange");
		arrange(exchangeButton, "DownBtn");
		exchangeButton.setEnabled(false);
		class exchangeListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				nextPageButton.setEnabled(false);
				int leftIndex=exchangeComboLeft.getSelectedIndex();
				int rightIndex=exchangeComboRight.getSelectedIndex();
				String leftClassName=classNameFieldList.get(leftIndex).getText();
				String rightClassName=classNameFieldList.get(rightIndex).getText();
				classNameFieldList.get(leftIndex).setText(rightClassName);
				classNameFieldList.get(rightIndex).setText(leftClassName);
				int leftSelectedIndex;
				if(classNameRadioButtonList.get(leftIndex).get(0).isSelected()) {
					leftSelectedIndex=0;
				}
				else {
					leftSelectedIndex=1;
				}
				int rightSelectedIndex;
				if(classNameRadioButtonList.get(rightIndex).get(0).isSelected()) {
					rightSelectedIndex=0;
				}
				else {
					rightSelectedIndex=1;
				}
				classNameRadioButtonList.get(leftIndex).get(rightSelectedIndex).setSelected(true);
				classNameRadioButtonList.get(rightIndex).get(leftSelectedIndex).setSelected(true);
				String leftTeacherName=teacherNameFieldList.get(leftIndex).getText();
				String rightTeacherName=teacherNameFieldList.get(rightIndex).getText();
				teacherNameFieldList.get(leftIndex).setText(rightTeacherName);
				teacherNameFieldList.get(rightIndex).setText(leftTeacherName);
				Object leftTypeObject=classTypeComboList.get(leftIndex).getSelectedItem();
				Object rightTypeObject=classTypeComboList.get(rightIndex).getSelectedItem();
				classTypeComboList.get(leftIndex).setSelectedItem(rightTypeObject);
				classTypeComboList.get(rightIndex).setSelectedItem(leftTypeObject);
				ArrayList<Integer> leftDateCheckIndex=new ArrayList<Integer>();
				ArrayList<Integer> rightDateCheckIndex=new ArrayList<Integer>();
				for(int i=0;i<dateCheckBoxList.get(leftIndex).size();i++) {
					if(dateCheckBoxList.get(leftIndex).get(i).isSelected()) {
						leftDateCheckIndex.add(i);
					}
				}
				for(int i=0;i<dateCheckBoxList.get(rightIndex).size();i++) {
					if(dateCheckBoxList.get(rightIndex).get(i).isSelected()) {
						rightDateCheckIndex.add(i);
					}
				}
				for(int i=0;i<dateCheckBoxList.get(leftIndex).size();i++) {
					dateCheckBoxList.get(leftIndex).get(i).setSelected(false);
				}
				for(int i=0;i<dateCheckBoxList.get(rightIndex).size();i++) {
					dateCheckBoxList.get(rightIndex).get(i).setSelected(false);
				}
				for(int right:rightDateCheckIndex) {
					dateCheckBoxList.get(leftIndex).get(right).setSelected(true);
				}
				for(int left:leftDateCheckIndex) {
					dateCheckBoxList.get(rightIndex).get(left).setSelected(true);
				}
				int leftLanguageIndex=0;
				int rightLanguageIndex=0;
				for(int i=0;i<languageRadioButtonList.get(leftIndex).size();i++) {
					if(languageRadioButtonList.get(leftIndex).get(i).isSelected()) {
						leftLanguageIndex=i;
					}
				}
				for(int i=0;i<languageRadioButtonList.get(rightIndex).size();i++) {
					if(languageRadioButtonList.get(rightIndex).get(i).isSelected()) {
						rightLanguageIndex=i;
					}
				}
				languageRadioButtonList.get(leftIndex).get(rightLanguageIndex).setSelected(true);
				languageRadioButtonList.get(rightIndex).get(leftLanguageIndex).setSelected(true);
			}
			
		}
		ActionListener e=new exchangeListener();
		exchangeButton.addActionListener(e);
		exchangeComboLeft=new JComboBox(); 
		exchangeComboRight=new JComboBox(); 
		exchangePanel.add(exchangeButton);
		exchangePanel.add(exchangeComboLeft);
		exchangePanel.add(exchangeComboRight);
	}
	public void createCenterPanel() {
		centerPanel=new JPanel();
		currentIndex=-1;
		centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
		centerScrollPane=new JScrollPane(centerPanel);
		centerPanel.setAutoscrolls(true);
		centerPanelList=new ArrayList<JPanel>();
		deleteButtonList=new ArrayList<JButton>();
		saveButtonList=new ArrayList<JButton>();
		clearButtonList=new ArrayList<JButton>();
		editButtonList=new ArrayList<JButton>();
		classNameFieldList=new ArrayList<JTextField>();
		classNameRadioButtonList=new ArrayList<ArrayList<JRadioButton>>();
		teacherNameFieldList=new ArrayList<JTextField>();
		classTypeComboList=new ArrayList<JComboBox>();
		dateCheckBoxList=new ArrayList<ArrayList<JCheckBox>>();
		languageRadioButtonList=new ArrayList<ArrayList<JRadioButton>>();
	}
	public void createresultPanel() {
		resultPanel=new JPanel();
		resultLabel=new JLabel("Result:  ");
		resultLabel.setForeground(INNER_LABEL);
		resultPanel.add(resultLabel);
		createAddButton();
		resultPanel.add(addButton);
		createRunButton();
		resultPanel.add(runButton);
	}
	
	public void createRunButton() {
		runButton=new JButton("Run");
		arrange(runButton, "DownBtn");
		class runListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				try {
					nextPageButton.setEnabled(true);
					exchangeButton.setEnabled(true);
					orderLectureList=new ArrayList<ArrayList<Lecture>>();
					for (int i=0;i<centerPanelList.size();i++) {
						String sql=String.format("SELECT DISTINCT subNum,subPoint,subNam,subClassroom,subTime FROM Course1082_user WHERE s=2");
						String sql1="";
						String sql2="";
						String sql3="";
						String sql4="";
						String sql5="";
						ArrayList<String> numberList = new ArrayList<>();
						ArrayList<String> pointList = new ArrayList<>();
						ArrayList<String> nameList = new ArrayList<>();
						ArrayList<String> classRoomList = new ArrayList<>();
						ArrayList<String> timeList = new ArrayList<>();
						if(!classNameFieldList.get(i).getText().equals("")) {
							if(classNameRadioButtonList.get(i).get(0).isSelected()) {
								sql1=" AND subNam=N'"+classNameFieldList.get(i).getText()+"'";
							}
							if(classNameRadioButtonList.get(i).get(1).isSelected()) {
								sql1=" AND subNam LIKE N'%"+classNameFieldList.get(i).getText()+"%'";
							}
						}
						if(!teacherNameFieldList.get(i).getText().equals("")) {
								sql2=" AND teaNam LIKE N'%"+teacherNameFieldList.get(i).getText()+"%'";
						}
						if(classTypeComboList.get(i).getSelectedItem().equals("體育")) {
								sql3=sql3+" AND subNam LIKE N'%體育%'";
						}
						if(classTypeComboList.get(i).getSelectedItem().equals("國文通識")) {
							sql3=sql3+" AND subNum LIKE N'03%' AND subNam LIKE N'國文%'";
						}
						if(classTypeComboList.get(i).getSelectedItem().equals("外文通識")) {
								sql3=sql3+" AND subNam LIKE N'大學英文（二）'";
						}
						if(classTypeComboList.get(i).getSelectedItem().equals("服務")) {
								sql3=sql3+" AND subNam LIKE N'服務學習%'";
						}
						if(classTypeComboList.get(i).getSelectedItem().equals("自然核心通識")) {
								sql3=sql3+" AND lmtKind LIKE N'自然通識' AND core=N'是'";
						}
						if(classTypeComboList.get(i).getSelectedItem().equals("人文核心通識")) {
								sql3=sql3+" AND lmtKind LIKE N'人文通識' AND core=N'是'";
						}
						if(classTypeComboList.get(i).getSelectedItem().equals("社會核心通識")) {
								sql3=sql3+" AND lmtKind LIKE N'社會通識' AND core=N'是'";
						}
						if(classTypeComboList.get(i).getSelectedItem().equals("自然通識")) {
							sql3=sql3+" AND lmtKind LIKE N'自然通識'";
					    }
					    if(classTypeComboList.get(i).getSelectedItem().equals("人文通識")) {
							sql3=sql3+" AND lmtKind LIKE N'人文通識'";
					    }
					    if(classTypeComboList.get(i).getSelectedItem().equals("社會通識")) {
							sql3=sql3+" AND lmtKind LIKE N'社會通識'";
					    }
						if(!dateCheckBoxList.get(i).get(0).isSelected()) {
								sql4=sql4+" AND subTime NOT LIKE N'一%'";
						}
						if(!dateCheckBoxList.get(i).get(1).isSelected()) {
								sql4=sql4+" AND subTime NOT LIKE N'二%'";
						}
						
						if(!dateCheckBoxList.get(i).get(2).isSelected()) {
								sql4=sql4+" AND subTime NOT LIKE N'三%'";
						}
						if(!dateCheckBoxList.get(i).get(3).isSelected()) {
								sql4=sql4+" AND subTime NOT LIKE N'四%'";	
						}
						if(!dateCheckBoxList.get(i).get(4).isSelected()) {
								sql4=sql4+" AND subTime NOT LIKE N'五%'";
						}
						if(languageRadioButtonList.get(i).get(0).isSelected()) {
								sql5=sql5+" AND langTpe=N'中文'";
						}
						if(languageRadioButtonList.get(i).get(1).isSelected()) {
								sql5=sql5+" AND langTpe=N'英文'";
						}
						ResultSet resultSet = doSql(sql+sql1+sql2+sql3+sql4+sql5, "executeQuery");
						while(resultSet.next()) {
							numberList.add(resultSet.getString("subNum"));
							pointList.add(resultSet.getString("subPoint"));
							nameList.add(resultSet.getString("subNam"));
							classRoomList.add(resultSet.getString("subClassroom"));
							timeList.add(resultSet.getString("subTime"));
						}
					ArrayList<Lecture> subArray=new ArrayList<Lecture>();	
					for(int r=0;r<numberList.size();r++) {
						Lecture l=new Lecture(numberList.get(r),pointList.get(r),nameList.get(r),classRoomList.get(r),timeList.get(r));
						subArray.add(l);
					}
					orderLectureList.add(subArray);
//					System.out.println(numberList);
//					System.out.println(nameList);
//					System.out.println(timeList);
				}	
					resultLectureList=new ArrayList<ArrayList<Lecture>>();
					for(int i=0;i<orderLectureList.size();i++) {
						ArrayList<ArrayList<Lecture>> tempLectureList=new ArrayList<ArrayList<Lecture>>();
						for(int j=0;j<orderLectureList.get(i).size();j++) {
							if(i==0) {
								ArrayList<Lecture> array=new ArrayList<Lecture>();
								array.add(orderLectureList.get(i).get(j));
								resultLectureList.add(array);
							}
							else {
								for(int k=0;k<resultLectureList.size();k++) {
									if(updateNeed(resultLectureList.get(k),orderLectureList.get(i).get(j))==true) {
										resultLectureList.get(k).add(orderLectureList.get(i).get(j));
										ArrayList<Lecture> copy=new ArrayList(resultLectureList.get(k));
										tempLectureList.add(copy);
										resultLectureList.get(k).remove(orderLectureList.get(i).get(j));
									}
								}
							}
						}
						for(int y=0;y<tempLectureList.size();y++) {
							resultLectureList.add(tempLectureList.get(y));
						}
						
					}
					filterLectureList=new ArrayList<ArrayList<Lecture>>();
					for(int n=0;n<resultLectureList.size();n++) {
						if(resultLectureList.get(n).size()==centerPanelList.size()) {
							getFilterLectureList().add(resultLectureList.get(n));
						}
					}
					resultLabel.setText("Result:"+getFilterLectureList().size());
					for(int u=0;u<getFilterLectureList().size();u++) {
						for(int p=0;p<getFilterLectureList().get(u).size();p++) {
//							System.out.println(filterLectureList.get(u).get(p).getLectureTime());
						}
					}
					
				}
				catch(Exception e1) {
					System.out.println("Error occur!");
					e1.printStackTrace();
				}
				
			}	
		}
		ActionListener run=new runListener();
		runButton.addActionListener(run);
		runButton.setEnabled(false);
	}
	
	public boolean updateNeed(ArrayList<Lecture> list,Lecture lecture){
		boolean conflict=false;
		for(int i=0;i<list.size();i++) {
			if(conflictTest(list.get(i),lecture)==true) {
				conflict=true;
			}
		}
		if(conflict==false) {
			return true;
		}
		return false;
	}
	
	public boolean conflictTest(Lecture lecture1,Lecture lecture2) {
		if(lecture2.getLectureName().equals(lecture1.getLectureName())) {
			return true;
		}
		ArrayList<String> courseTimeList1=checkTime(lecture1.getLectureTime());
		ArrayList<String> courseTimeList2=checkTime(lecture2.getLectureTime());
		for(int i=0;i<courseTimeList1.size();i++) {
			for(int j=0;j<courseTimeList2.size();j++) {
				if(courseTimeList1.get(i).charAt(0)==courseTimeList2.get(j).charAt(0)) {
					for(int ii=1;ii<courseTimeList1.get(i).length();ii++) {
						for(int jj=1;jj<courseTimeList2.get(j).length();jj++) {
							if(courseTimeList1.get(i).charAt(ii)==courseTimeList2.get(j).charAt(jj)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
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
	
	

	public JButton createDeleteButton() {
		JButton button=new JButton("Delete");
		arrange(button, "ListBtn");	
		deleteButtonList.add(button);
		class deleteListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				int deleteIndex=deleteButtonList.indexOf(button);
				deleteButtonList.remove(deleteIndex);
				editButtonList.remove(deleteIndex);
				clearButtonList.remove(deleteIndex);
				saveButtonList.remove(deleteIndex);
				classNameFieldList.remove(deleteIndex);
				classNameRadioButtonList.remove(deleteIndex);
				teacherNameFieldList.remove(deleteIndex);
				classTypeComboList.remove(deleteIndex);
				dateCheckBoxList.remove(deleteIndex);
				languageRadioButtonList.remove(deleteIndex);
				for(int i=0;i<centerPanelList.size();i++) {
					if(i>deleteIndex) {
						centerPanelList.get(i).setBorder(new TitledBorder(new EtchedBorder(),""+i));
					}
				}
				if(currentIndex>=deleteIndex) {currentIndex--;}
				centerPanel.remove(centerPanelList.get(deleteIndex));
				revalidate();
				repaint();
				exchangeComboLeft.removeItem(centerPanelList.size()+"");
				exchangeComboRight.removeItem(centerPanelList.size()+"");
				centerPanelList.remove(deleteIndex);
				addButton.setEnabled(true);
				runButton.setEnabled(true);
				for(JTextField f:classNameFieldList) {f.setEditable(false);}
			}
		}
		ActionListener delete=new deleteListener();
		button.addActionListener(delete);
		return button;
	}
	
	public JButton createEditButton() {
		JButton button=new JButton("Edit");
		arrange(button, "ListBtn");	
		editButtonList.add(button);
		class editListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				nextPageButton.setEnabled(false);
				exchangeButton.setEnabled(false);
				currentIndex=editButtonList.indexOf(button);
				addButton.setEnabled(false);
				runButton.setEnabled(false);
				clearButtonList.get(currentIndex).setEnabled(true);
				deleteButtonList.get(currentIndex).setEnabled(true);
				saveButtonList.get(currentIndex).setEnabled(true);
				classNameFieldList.get(currentIndex).setEditable(true);
				for(JRadioButton n:classNameRadioButtonList.get(currentIndex)) {n.setEnabled(true);}
				teacherNameFieldList.get(currentIndex).setEditable(true);
				classTypeComboList.get(currentIndex).setEnabled(true);
				for(JCheckBox c:dateCheckBoxList.get(currentIndex)){c.setEnabled(true);}
				for(JRadioButton r:languageRadioButtonList.get(currentIndex)) {r.setEnabled(true);}
				for(int i=0;i<centerPanelList.size();i++) {
					if(i!=currentIndex) {
						clearButtonList.get(i).setEnabled(false);
						deleteButtonList.get(i).setEnabled(false);
						saveButtonList.get(i).setEnabled(false);
						classNameFieldList.get(i).setEditable(false);
						for(JRadioButton c:classNameRadioButtonList.get(i)) {c.setEnabled(false);}
						teacherNameFieldList.get(i).setEditable(false);
						classTypeComboList.get(i).setEnabled(false);
						for(JCheckBox c:dateCheckBoxList.get(i)){c.setEnabled(false);}
						for(JRadioButton r:languageRadioButtonList.get(i)) {r.setEnabled(false);}
					}
				}
			}
		}
		ActionListener e=new editListener();
		button.addActionListener(e);
		return button;
	}
	
	public JButton createSaveButton() {
		JButton button=new JButton("Save");
		arrange(button, "ListBtn");	
		saveButtonList.add(button);
		class saveListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(centerPanelList.size()>=2) {
					exchangeButton.setEnabled(true);
				}
				currentIndex=saveButtonList.indexOf(button);
				addButton.setEnabled(true);	
				runButton.setEnabled(true);	
				button.setEnabled(false);
				clearButtonList.get(currentIndex).setEnabled(false);
				deleteButtonList.get(currentIndex).setEnabled(false);
				classNameFieldList.get(currentIndex).setEditable(false);
				for(JRadioButton n:classNameRadioButtonList.get(currentIndex)) {n.setEnabled(false);}
				teacherNameFieldList.get(currentIndex).setEditable(false);
				classTypeComboList.get(currentIndex).setEnabled(false);
				for(JCheckBox c:dateCheckBoxList.get(currentIndex)){c.setEnabled(false);}
				for(JRadioButton r:languageRadioButtonList.get(currentIndex)) {r.setEnabled(false);}
			}
			
		}
		ActionListener save=new saveListener();
		button.addActionListener(save);
		return button;
	}
	
	public JButton createClearButton() {
		JButton button=new JButton("Clear");
		arrange(button, "ListBtn");	
		clearButtonList.add(button);
		class clearListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				currentIndex=clearButtonList.indexOf(button);
				classNameFieldList.get(currentIndex).setText("");
				classNameRadioButtonList.get(currentIndex).get(0).setSelected(true);
				teacherNameFieldList.get(currentIndex).setText("");
				classTypeComboList.get(currentIndex).setSelectedItem("無");
				for(JCheckBox c:dateCheckBoxList.get(currentIndex)){c.setSelected(true);}
				languageRadioButtonList.get(currentIndex).get(2).setSelected(true);
			}
		}
		ActionListener clear=new clearListener();
		button.addActionListener(clear);
		return button;
	}
	
	
	
	public void createAddButton() {
		addButton=new JButton("Add New Course");
		arrange(addButton, "DownBtn");
		class addListener implements ActionListener{
			public JPanel createNewPanel() {
				JPanel panel=new JPanel();
				panel.setPreferredSize(new Dimension(0,300));
				panel.setLayout(new BorderLayout());
				centerPanelList.add(panel);
				exchangeComboLeft.addItem(centerPanelList.size()+"");
				exchangeComboRight.addItem(centerPanelList.size()+"");
				TitledBorder titiledBorder = new TitledBorder(new EtchedBorder(centerListBorderColor, centerListBorderColor),"志願"+(1+centerPanelList.indexOf(panel)));
				titiledBorder.setTitleColor(Color.WHITE);
				panel.setBorder(titiledBorder);
				
				currentIndex=centerPanelList.size()-1;
				arrange(panel,"CenterListBorderColor");
				return panel;
			}
			public void actionPerformed(ActionEvent e) {
				nextPageButton.setEnabled(false);
				runButton.setEnabled(false);	
				JPanel p1=createNewPanel();
				JPanel pSouth=new JPanel();
				arrange(pSouth,"CenterListColor");
				JPanel pCenter=new JPanel();
				pCenter.setLayout(new GridLayout(5,1));
				pCenter.add(createClassNameFieldPanel());
				pCenter.add(createTeacherNameFieldPanel());
				pCenter.add(createClassTypeCombo());
				pCenter.add(createDateCheckBoxPanel());
				pCenter.add(createLanguageRadioPanel());
				pSouth.add(createDeleteButton());
				pSouth.add(createEditButton());
				pSouth.add(createSaveButton());
				pSouth.add(createClearButton());
				p1.add(pSouth,BorderLayout.SOUTH);
				p1.add(pCenter,BorderLayout.CENTER);
				centerPanel.add(p1);
				centerPanel.updateUI();
				addButton.setEnabled(false);
			}
		}
		ActionListener add=new addListener();
		addButton.addActionListener(add);
	}
	
	public JPanel createClassNameFieldPanel() {
		JPanel panel=new JPanel();
		arrange(panel,"CenterListColor");
		JLabel className=new JLabel("課程名稱：");
		className.setForeground(INNER_LABEL);
		JTextField field=new JTextField(FIELD_WIDTH);
		field.setForeground(INNER_LABEL);
		classNameFieldList.add(field);
		panel.add(className);
		panel.add(field);
		JRadioButton button1=new JRadioButton("嚴格篩選");
		button1.setForeground(INNER_LABEL);
		JRadioButton button2=new JRadioButton("寬鬆篩選");
		button2.setForeground(INNER_LABEL);
		ArrayList<JRadioButton> array=new ArrayList<JRadioButton>();
		array.add(button1);
		array.add(button2);
		classNameRadioButtonList.add(array);
		ButtonGroup group=new ButtonGroup();
		button1.setSelected(true);
		group.add(button1);
		group.add(button2);
		panel.add(button1);
		panel.add(button2);
		
		return panel;
	}
	
	public JPanel createClassTypeCombo() {
		JPanel panel=new JPanel();
		arrange(panel,"CenterListColor");
		JComboBox comboBox=new JComboBox();
		comboBox.setForeground(INNER_LABEL);
		JLabel classType=new JLabel("課程類型");
		classType.setForeground(INNER_LABEL);
		classTypeComboList.add(comboBox);
		comboBox.addItem("無");
//		comboBox.addItem("必修");
//		comboBox.addItem("選修");
		comboBox.addItem("國文通識");
		comboBox.addItem("外文通識");
		comboBox.addItem("自然通識");
		comboBox.addItem("社會通識");
		comboBox.addItem("人文通識");
		comboBox.addItem("自然核心通識");
		comboBox.addItem("社會核心通識");
		comboBox.addItem("人文核心通識");
		comboBox.addItem("體育");
		comboBox.addItem("服務");
		panel.add(classType);
		panel.add(comboBox);
		return panel;
	}
	
	public JPanel createDateCheckBoxPanel() {
		JPanel panel=new JPanel();
		arrange(panel,"CenterListColor");
		panel.setLayout(new GridLayout(1,6));
		JCheckBox checkBox1=new JCheckBox("星期ㄧ");
		JCheckBox checkBox2=new JCheckBox("星期二");
		JCheckBox checkBox3=new JCheckBox("星期三");
		JCheckBox checkBox4=new JCheckBox("星期四");
		JCheckBox checkBox5=new JCheckBox("星期五");
		checkBox1.setForeground(INNER_LABEL);
		checkBox2.setForeground(INNER_LABEL);
		checkBox3.setForeground(INNER_LABEL);
		checkBox4.setForeground(INNER_LABEL);
		checkBox5.setForeground(INNER_LABEL);

		ArrayList<JCheckBox> checkBoxArray=new ArrayList<JCheckBox>();
		checkBox1.setSelected(true);
		checkBox2.setSelected(true);
		checkBox3.setSelected(true);
		checkBox4.setSelected(true);
		checkBox5.setSelected(true);
		checkBoxArray.add(checkBox1);
		checkBoxArray.add(checkBox2);
		checkBoxArray.add(checkBox3);
		checkBoxArray.add(checkBox4);
		checkBoxArray.add(checkBox5);
		dateCheckBoxList.add(checkBoxArray);
		panel.add(checkBox1);
		panel.add(checkBox2);
		panel.add(checkBox3);
		panel.add(checkBox4);
		panel.add(checkBox5);
		return panel;
	}
	public JPanel createLanguageRadioPanel() {
		JPanel panel=new JPanel();
		arrange(panel,"CenterListColor");
		JRadioButton button1=new JRadioButton("中文");
		JRadioButton button2=new JRadioButton("英文");
		JRadioButton button3=new JRadioButton("中/英");
		button1.setForeground(INNER_LABEL);
		button2.setForeground(INNER_LABEL);
		button3.setForeground(INNER_LABEL);

		ButtonGroup group=new ButtonGroup();
		group.add(button1);
		group.add(button2);
		group.add(button3);
		button3.setSelected(true);
		ArrayList<JRadioButton> languageArray=new ArrayList<JRadioButton>();
		languageArray.add(button1);
		languageArray.add(button2);
		languageArray.add(button3);
		languageRadioButtonList.add(languageArray);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		return panel;
	}
	public JPanel createTeacherNameFieldPanel() {
		JPanel panel=new JPanel();
		arrange(panel,"CenterListColor");
		JLabel label=new JLabel("教師姓名:");
		label.setForeground(INNER_LABEL);
		JTextField field=new JTextField(FIELD_WIDTH);
		field.setForeground(INNER_LABEL);
		teacherNameFieldList.add(field);
		panel.add(label);
		panel.add(field);
		return panel;
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

	public ArrayList<ArrayList<Lecture>> getFilterLectureList() {
		return filterLectureList;
	}
	public JPanel getSelectCoursePanel()
	{
		return this;
	}
}
