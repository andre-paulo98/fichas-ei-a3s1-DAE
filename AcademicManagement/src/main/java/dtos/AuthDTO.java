package dtos;

public class AuthDTO {
	private String id;
	private String password;

	public AuthDTO(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public AuthDTO() {
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
}
