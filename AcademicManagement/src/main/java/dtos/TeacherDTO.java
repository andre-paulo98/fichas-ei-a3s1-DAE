package dtos;

import entities.Teacher;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherDTO {

	private String id;
	private String password;
	private String name;
	private String email;
	private String office;

	public TeacherDTO() {
	}

	public TeacherDTO(String id, String password, String name, String email, String office) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.office = office;
	}

	public static TeacherDTO toDTO(Teacher teacher) {
		return new TeacherDTO(teacher.getId(), teacher.getPassword(), teacher.getName(), teacher.getEmail(), teacher.getOffice());
	}

	public static List<TeacherDTO> toDTOs(List<Teacher> teachers) {
		return teachers.stream().map(TeacherDTO::toDTO).collect(Collectors.toList());
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

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
}
