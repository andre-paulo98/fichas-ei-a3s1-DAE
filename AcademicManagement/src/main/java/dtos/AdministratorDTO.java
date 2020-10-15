package dtos;

import entities.Administrator;
import entities.Student;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class AdministratorDTO implements Serializable {
	private String id;
	private String password;
	private String name;
	private String email;

	public AdministratorDTO() {
	}

	public AdministratorDTO(String id, String password, String name, String email) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public static AdministratorDTO toDTO(Administrator administrator) {
		return new AdministratorDTO(
				administrator.getId(),
				administrator.getPassword(),
				administrator.getName(),
				administrator.getEmail()
		);
	}

	public static List<AdministratorDTO> toDTOs(List<Administrator> administrators) {
		return administrators.stream().map(AdministratorDTO::toDTO).collect(Collectors.toList());
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
}
