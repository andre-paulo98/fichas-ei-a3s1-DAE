package ejbs;

import entities.Administrator;
import entities.Teacher;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class AdministratorBean {

	@PersistenceContext
	EntityManager entityManager;

	public void create(String id, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException {
		if (getAdmin(id) != null)
			throw new MyEntityExistsException("ADMIN ALREADY EXISTS WITH THAT ID");

		try {
			Administrator administrator = new Administrator(id, password, name, email);
			entityManager.persist(administrator);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}


	public List<Administrator> getAllAdmins() {
		return (List<Administrator>) entityManager.createNamedQuery("getAllAdmins").getResultList();
	}

	public Administrator getAdmin(String id) {
		return entityManager.find(Administrator.class, id);
	}

	public void update(String id, String password, String name, String email) throws MyEntityNotFoundException, MyConstraintViolationException {
		Administrator admin = entityManager.find(Administrator.class, id);
		if (admin == null)
			throw new MyEntityNotFoundException("ADMIN DOES NOT EXIST");

		try {
			admin.setPassword(password);
			admin.setName(name);
			admin.setEmail(email);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public void delete(String id) throws MyEntityNotFoundException {
		Administrator administrator = getAdmin(id);
		if (administrator == null)
			throw new MyEntityNotFoundException("ADMIN DOES NOT EXIST");

		entityManager.remove(administrator);
	}
}
