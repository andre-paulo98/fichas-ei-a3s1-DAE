package ejbs;

import entities.Course;
import entities.Student;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class StudentBean {

	@PersistenceContext
	EntityManager entityManager;

	public void create(String id, String password, String name, String email, long courseId) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {

		Course course = entityManager.find(Course.class, courseId);
		if (course == null)
			throw new MyEntityNotFoundException("COURSE DOES NOT EXIST");

		if (entityManager.find(Student.class, id) != null)
			throw new MyEntityExistsException("STUDENT ALREADY EXISTS WITH THAT ID");

		try {
			Student student = new Student(id, password, name, email, course);
			course.addStudent(student);
			entityManager.persist(student);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public List<Student> getAllStudents() {
		return (List<Student>) entityManager.createNamedQuery("getAllStudents").getResultList();
	}

	public Student getStudent(String id) {
		return entityManager.find(Student.class, id);
	}

	public void update(String id, String password, String name, String email, long courseCode) throws MyEntityNotFoundException, MyConstraintViolationException {
		Student student = entityManager.find(Student.class, id);
		if (student == null)
			throw new MyEntityNotFoundException("STUDENT DOES NOT EXIST");

		Course course = entityManager.find(Course.class, courseCode);
		if (course == null)
			throw new MyEntityNotFoundException("COURSE DOES NOT EXIST");

		try {
			entityManager.lock(student, LockModeType.OPTIMISTIC);
			student.setPassword(password);
			student.setName(name);
			student.setEmail(email);

			student.getCourse().removeStudent(student);
			student.setCourse(course);
			course.addStudent(student);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public void delete(String id) throws MyEntityNotFoundException {
		Student student = getStudent(id);
		if (student == null)
			throw new MyEntityNotFoundException("STUDENT DOES NOT EXIST");

		entityManager.remove(student);
	}
}
