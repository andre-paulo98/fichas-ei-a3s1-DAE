package dtos;

import entities.Course;
import entities.Subject;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SubjectDTO implements Serializable {


    private int code;
    private String name;
    private String courseName;
    private long courseCode;
    private String courseYear;
    private int scholarYear;

	public SubjectDTO() {
	}

	public SubjectDTO(int code, String name, long courseCode, String courseName, String courseYear, int scholarYear) {
		this.code = code;
		this.name = name;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseYear = courseYear;
		this.scholarYear = scholarYear;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(long courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseYear() {
		return courseYear;
	}

	public void setCourseYear(String courseYear) {
		this.courseYear = courseYear;
	}

	public int getScholarYear() {
		return scholarYear;
	}

	public void setScholarYear(int scholarYear) {
		this.scholarYear = scholarYear;
	}
}
