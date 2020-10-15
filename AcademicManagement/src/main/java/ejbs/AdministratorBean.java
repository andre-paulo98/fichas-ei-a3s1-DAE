package ejbs;

import entities.Administrator;
import entities.Teacher;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class AdministratorBean {

	@PersistenceContext
	EntityManager entityManager;

	public void create(String id, String password, String name, String email){
		Administrator administrator = new Administrator(id, password, name, email);
		entityManager.persist(administrator);
	}



	public List<Administrator> getAllAdmins() {
		return (List<Administrator>) entityManager.createNamedQuery("getAllAdmins").getResultList();
	}

	public Administrator getAdmin(String id) {
		return entityManager.find(Administrator.class, id);
	}

	public void update(String id, String password, String name, String email) {
		Administrator admin = entityManager.find(Administrator.class, id);
		if(admin != null) {
			admin.setPassword(password);
			admin.setName(name);
			admin.setEmail(email);
			entityManager.persist(admin);
		} else {
			throw new NotFoundException("ADMIN NOT FOUND");
		}
	}

	public void delete(String id) {
		Administrator administrator = getAdmin(id);
		entityManager.remove(administrator);
	}
}
