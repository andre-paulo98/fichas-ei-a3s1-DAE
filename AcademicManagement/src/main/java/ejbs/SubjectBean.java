package ejbs;

import entities.Course;
import entities.Student;
import entities.Subject;
import entities.Teacher;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotFoundException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class SubjectBean {

	@PersistenceContext
	EntityManager entityManager;

	public void create(int code, String name, int courseID, String courseName, String courseYear, int scholarYear, Set<Student> students, Set<Teacher> teachers) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
		Course course = entityManager.find(Course.class, courseID);
		if (course == null)
			throw new MyEntityNotFoundException("COURSE DOES NOT EXIST");

		if (getSubject(code) != null)
			throw new MyEntityExistsException("SUBJECT ALREADY EXISTS");

		try {
			Subject subject = new Subject(code, name, course, courseYear, scholarYear, students, teachers);
			entityManager.persist(subject);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}

	}

	public void create(int code, String name, long courseID, String courseName, String courseYear, int scholarYear) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
		Course course = entityManager.find(Course.class, courseID);
		if (course == null)
			throw new MyEntityNotFoundException("COURSE DOES NOT EXIST");

		if (getSubject(code) != null)
			throw new MyEntityExistsException("SUBJECT ALREADY EXISTS");

		try {
			Subject subject = new Subject(code, name, course, courseYear, scholarYear, new LinkedHashSet<>(), new LinkedHashSet<>());
			entityManager.persist(subject);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}

	}

	public void create(int code, String name, Course course, String courseYear, int scholarYear) throws MyEntityExistsException, MyConstraintViolationException {

		if (getSubject(code) != null)
			throw new MyEntityExistsException("SUBJECT ALREADY EXISTS");

		try {
			Subject subject = new Subject(code, name, course, courseYear, scholarYear, new LinkedHashSet<>(), new LinkedHashSet<>());
			entityManager.persist(subject);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public void update(int code, String name, long courseID, String courseYear, int scholarYear) throws MyEntityNotFoundException, MyConstraintViolationException {
		Subject subject = entityManager.find(Subject.class, code);
		if (subject == null)
			throw new MyEntityNotFoundException("SUBJECT NOT FOUND");

		Course course = entityManager.find(Course.class, courseID);
		if (course == null)
			throw new MyEntityNotFoundException("COURSE NOT FOUND");

		try {
			subject.setName(name);
			subject.setCourse(course);
			subject.setCourseYear(courseYear);
			subject.setScholarYear(scholarYear);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}

	}

	public void delete(int code) throws MyEntityNotFoundException {
		Subject subject = getSubject(code);
		if (subject == null)
			throw new MyEntityNotFoundException("SUBJECT NOT FOUND");

		entityManager.remove(subject);
	}

	public List<Subject> getAllSubjects() {
		return (List<Subject>) entityManager.createNamedQuery("getAllSubjects").getResultList();
	}

	public Subject getSubject(int code) {
		return entityManager.find(Subject.class, code);
	}

	public void associateTeacherInSubject(int subjectCode, String id) throws MyEntityExistsException, MyEntityNotFoundException {
		Teacher teacher = entityManager.find(Teacher.class, id);
		if (teacher == null)
			throw new MyEntityNotFoundException("TEACHER DOES NOT EXIST");

		Subject subject = entityManager.find(Subject.class, subjectCode);
		if (subject == null)
			throw new MyEntityNotFoundException("SUBJECT DOES NOT EXIST");

		if (subject.getTeachers().contains(teacher))
			throw new MyEntityExistsException("TEACHER IN SUBJECT ALREADY");

		teacher.addSubject(subject);
		subject.addTeacher(teacher);

	}

	public void dissociateTeacherInSubject(int subjectCode, String id) throws MyEntityNotFoundException {
		Teacher teacher = entityManager.find(Teacher.class, id);
		if (teacher == null)
			throw new MyEntityNotFoundException("TEACHER DOES NOT EXIST");

		Subject subject = entityManager.find(Subject.class, subjectCode);
		if (subject == null)
			throw new MyEntityNotFoundException("SUBJECT DOES NOT EXIST");

		teacher.removeSubject(subject);
		subject.removeTeacher(teacher);
	}

	public void enrollStudentInSubject(int subjectCode, String id) throws MyEntityNotFoundException, MyIllegalArgumentException, MyEntityExistsException {
		Student student = entityManager.find(Student.class, id);
		if (student == null)
			throw new MyEntityNotFoundException("STUDENT DOES NOT EXIST");

		Subject subject = entityManager.find(Subject.class, subjectCode);
		if (subject == null)
			throw new MyEntityNotFoundException("SUBJECT DOES NOT EXIST");

		if (!subject.getCourse().equals(student.getCourse()))
			throw new MyIllegalArgumentException("STUDENT COURSE IS NOT THE SAME AS SUBJECT COURSE");

		if (subject.getStudents().contains(student))
			throw new MyEntityExistsException("STUDENT ALREADY IN SUBJECT");

		student.addSubject(subject);
		subject.addStudent(student);
	}

	public void unrollStudentInSubject(int subjectCode, String id) throws MyEntityNotFoundException {
		Student student = entityManager.find(Student.class, id);
		if (student == null)
			throw new MyEntityNotFoundException("STUDENT DOES NOT EXIST");

		Subject subject = entityManager.find(Subject.class, subjectCode);
		if (subject == null)
			throw new MyEntityNotFoundException("SUBJECT DOES NOT EXIST");

		subject.removeStudent(student);
		student.removeSubject(subject);
	}
}
