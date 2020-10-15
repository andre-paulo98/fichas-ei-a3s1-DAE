package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
		@NamedQuery(
				name = "getAllAdmins",
				query = "SELECT s FROM Administrator s ORDER BY s.name" // JPQL
		)
})
public class Administrator extends User {

	public Administrator() {
		super();
	}

	public Administrator(String id, @NotNull String password, @NotNull String name, @NotNull @Email String email) {
		super(id, password, name, email);
	}
}
