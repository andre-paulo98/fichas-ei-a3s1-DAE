package dtos;

import entities.Student;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDTO implements Serializable {

	private String id;
	private String password;
	private String name;
	private String email;
	private long courseCode;
	private String courseName;
	private List<SubjectDTO> subjectDTOS;
	private List<DocumentDTO> documentDTOS;

	public StudentDTO() {
	}

	public StudentDTO(String id, String password, String name, String email, long courseCode, String courseName) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.courseCode = courseCode;
		this.courseName = courseName;
	}

	public StudentDTO(String id, String password, String name, String email, long courseCode, String courseName, List<SubjectDTO> subjectDTOS) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.subjectDTOS = subjectDTOS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(long courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public List<SubjectDTO> getSubjectDTOS() {
		return subjectDTOS;
	}

	public void setSubjectDTOS(List<SubjectDTO> subjectDTOS) {
		this.subjectDTOS = subjectDTOS;
	}
}
