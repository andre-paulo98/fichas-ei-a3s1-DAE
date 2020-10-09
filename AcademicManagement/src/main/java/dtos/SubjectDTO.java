package dtos;

import entities.Course;

import java.io.Serializable;

public class SubjectDTO implements Serializable {


    private int code;
    private String name;
    private String courseName;
    private int courseCode;
    private int courseYear;
    private int scholarYear;

	public SubjectDTO() {
	}

	public SubjectDTO(int code, String name, Course course, int courseYear, int scholarYear) {
		this.code = code;
		this.name = name;
		this.courseCode = course.getId();
		this.courseName = course.getName();
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

	public int getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(int courseCode) {
		this.courseCode = courseCode;
	}

	public int getCourseYear() {
		return courseYear;
	}

	public void setCourseYear(int courseYear) {
		this.courseYear = courseYear;
	}

	public int getScholarYear() {
		return scholarYear;
	}

	public void setScholarYear(int scholarYear) {
		this.scholarYear = scholarYear;
	}
}
