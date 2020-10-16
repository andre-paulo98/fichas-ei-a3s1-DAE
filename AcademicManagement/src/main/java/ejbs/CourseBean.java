package ejbs;

import entities.Course;
import entities.Student;
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
public class CourseBean {
	@PersistenceContext
	EntityManager entityManager;

	public void create(String name) throws MyEntityExistsException, MyConstraintViolationException {
		/*if (getCourse(id) != null)
			throw new MyEntityExistsException("COURSE ALREADY EXISTS WITH THAT ID");*/

		try {
			Course course = new Course(name);
			entityManager.persist(course);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public List<Course> getAllCourses() {
		return (List<Course>) entityManager.createNamedQuery("getAllCourses").getResultList();
	}

	public Course getCourse(long id) {
		return entityManager.find(Course.class, id);
	}


	public void update(long id, String name) throws MyEntityNotFoundException, MyConstraintViolationException {
		Course course = getCourse(id);
		if (course == null)
			throw new MyEntityNotFoundException("COURSE DOES NOT EXIST");

		try {
			course.setName(name);
			entityManager.persist(course);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}

	}

	public void delete(int id) throws MyEntityNotFoundException {
		Course course = getCourse(id);
		if (course == null)
			throw new MyEntityNotFoundException("COURSE DOES NOT EXIST");

		entityManager.remove(course);
	}
}
