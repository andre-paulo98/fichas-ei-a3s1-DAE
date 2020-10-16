package ejbs;

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
public class TeacherBean {

	@PersistenceContext
	EntityManager entityManager;

	public void create(String id, String password, String name, String email, String office) throws MyEntityExistsException, MyConstraintViolationException {
		if (getTeacher(id) != null)
			throw new MyEntityExistsException("TEACHER EXISTS ALREADY");

		try {
			Teacher teacher = new Teacher(id, password, name, email, office);
			entityManager.persist(teacher);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public List<Teacher> getAllTeachers() {
		return (List<Teacher>) entityManager.createNamedQuery("getAllTeachers").getResultList();
	}

	public Teacher getTeacher(String id) {
		return entityManager.find(Teacher.class, id);
	}

	public void update(String id, String password, String name, String email, String office) throws MyEntityNotFoundException, MyConstraintViolationException {
		Teacher teacher = entityManager.find(Teacher.class, id);
		if (teacher == null)
			throw new MyEntityNotFoundException("TEACHER NOT FOUND");

		try {
			teacher.setPassword(password);
			teacher.setName(name);
			teacher.setEmail(email);
			teacher.setOffice(office);
			entityManager.persist(teacher);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public void delete(String id) throws MyEntityNotFoundException {
		Teacher teacher = getTeacher(id);
		if (teacher == null)
			throw new MyEntityNotFoundException("TEACHER NOT FOUND");

		entityManager.remove(teacher);
	}
}
