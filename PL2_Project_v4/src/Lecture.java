import java.util.PrimitiveIterator.OfDouble;

public class Lecture {
	private String lectureID;
	private String lecturePoint;
	private String lectureName;
	private String lectureClassRoom;
	private String lectureTime;
	
	public Lecture(String id,String lecturePoint,String name,String lectureClassRoom,String time) {
		setLectureID(id);
		setLecturePoint(lecturePoint);
		setLectureName(name);
		setLectureClassRoom(lectureClassRoom);
		setLectureTime(time);
	}

	public String getLectureID() {
		return lectureID;
	}

	public void setLectureID(String lectureID) {
		this.lectureID = lectureID;
	}

	public String getLectureName() {
		return lectureName;
	}

	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}

	public String getLectureTime() {
		return lectureTime;
	}

	public void setLectureTime(String lectureTime) {
		this.lectureTime = lectureTime;
	}

	public String getLecturePoint() {
		return lecturePoint;
	}

	public void setLecturePoint(String lecturePoint) {
		this.lecturePoint = lecturePoint;
	}

	public String getLectureClassRoom() {
		if (lectureClassRoom.equals(""))
		{
			return "NA";
		}
		return lectureClassRoom;
	}

	public void setLectureClassRoom(String lectureClassRoom) {
		this.lectureClassRoom = lectureClassRoom;
	}
}