public class Course {
	private String courseID;
	private String courseName;
	private double credits;
	private String day;
	private String time;
	private String classroom;
	private String type;
	public Course () {}
	public Course (String id,String name,double credits, String date, String time, String classroom, String type)
	{
		this.courseID= id;
		this.courseName=name;
		this.credits =credits;
		this.day=date;
		this.time=time;
		this.classroom=classroom;
		this.type=type;
	}

	public String getCourseID() {
		return courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public double getCredits() {
		return credits;
	}
	
	public String getDay() {
		return day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getClassroom() {
		return classroom;
	}
	@Override
	public String toString()
	{
		String ans="CourseID: "+getCourseID()+", CourseName: "+getCourseName()+", Credits: "+getCredits()+", Day: "+getDay()+", Time:"+getTime()+", Classroom: "+getClassroom()+"\n";
		return ans;
	}
	public String getType() {
		return type;
	}
		
}