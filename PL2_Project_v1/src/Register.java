import java.util.ArrayList;
public class Register {
	
	private ArrayList<Course> courseList;
	public Register()
	{		
		courseList = new ArrayList<Course>();		
	}


	public ArrayList<Course> getCourseList() {
		return courseList;
	}
	public void addCourse(String id, String name, double credits, String date, String time,String classroom, String type)
	{
		Course course1= new Course(id,name, credits,date,time,classroom,type);
		courseList.add(course1);
	}

	public Course findCourse(String courseID)
	{
		for (Course element:courseList)
		{
			if (element.getCourseID().equals(courseID))
			{
				return element;
			}
		}
		return null;
	}
	
	public void removeCourse(String courseID)
	{
		for (Course element : courseList)
		{
			if (element.getCourseID() == courseID)
			{
				courseList.remove(element);
				
			}
		}
	
	}
}