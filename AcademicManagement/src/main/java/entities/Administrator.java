package entities;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
public class Administrator extends User {

	public Administrator() {
		super();
	}

	public Administrator(String id, @NotNull String password, @NotNull String name, @NotNull @Email String email) {
		super(id, password, name, email);
	}
}
