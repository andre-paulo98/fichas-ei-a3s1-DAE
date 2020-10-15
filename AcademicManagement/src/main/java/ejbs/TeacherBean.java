package ejbs;

import entities.Course;
import entities.Subject;
import entities.Teacher;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class TeacherBean {

	@PersistenceContext
	EntityManager entityManager;

	public void create(String id, String password, String name, String email, String office) {
		Teacher teacher = new Teacher(id, password, name, email, office);
		entityManager.persist(teacher);
	}

	public List<Teacher> getAllTeachers() {
		return (List<Teacher>) entityManager.createNamedQuery("getAllTeachers").getResultList();
	}

	public Teacher getTeacher(String id) {
		return entityManager.find(Teacher.class, id);
	}

	public void update(String id, String password, String name, String email, String office) {
		Teacher teacher = entityManager.find(Teacher.class, id);
		if(teacher != null) {
			teacher.setPassword(password);
			teacher.setName(name);
			teacher.setEmail(email);
			teacher.setOffice(office);
			entityManager.persist(teacher);
		} else {
			throw new NotFoundException("TEACHER NOT FOUND");
		}
	}

	public void delete(String id) {
		Teacher teacher = getTeacher(id);
		entityManager.remove(teacher);
	}
}
