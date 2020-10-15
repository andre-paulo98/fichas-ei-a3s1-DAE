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
	private int courseCode;
	private String courseName;

	public StudentDTO() {
	}

	public StudentDTO(String id, String password, String name, String email, int courseCode, String courseName) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.courseCode = courseCode;
		this.courseName = courseName;
	}

	public static StudentDTO toDTO(Student student) {
		return new StudentDTO(
				student.getId(),
				student.getPassword(),
				student.getName(),
				student.getEmail(),
				student.getCourse().getId(),
				student.getCourse().getName()
		);
	}

	public static List<StudentDTO> toDTOs(List<Student> students) {
		return students.stream().map(StudentDTO::toDTO).collect(Collectors.toList());
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

	public int getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(int courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
