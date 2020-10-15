package ejbs;

import entities.Course;
import entities.Student;
import entities.Subject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class StudentBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String id, String password, String name, String email, int courseId) {

        Course course = entityManager.find(Course.class, courseId);
        if(course != null) {
            Student student = new Student(id, password, name, email, course);
            entityManager.persist(student);

            course.addStudent(student);
        } else {
            throw new NotFoundException("COURSE NOT FOUND");
        }
    }

    public List<Student> getAllStudents() {
        return (List<Student>) entityManager.createNamedQuery("getAllStudents").getResultList();
    }

    public Student getStudent(String id) {
        return entityManager.find(Student.class, id);
    }

    public void update(String id, String password, String name, String email, int courseCode) {
        Student student = entityManager.find(Student.class, id);
        if(student != null) {
            Course course = entityManager.find(Course.class, courseCode);
            if(course != null) {
                student.setPassword(password);
                student.setName(name);
                student.setEmail(email);
                student.setCourse(course);

                entityManager.persist(course);
            } else {
                throw new NotFoundException("COURSE NOT FOUND");
            }
        } else {
            throw new NotFoundException("STUDENT NOT FOUND");
        }
    }

    public void delete(String id) {
        Student student = getStudent(id);
        entityManager.remove(student);
    }
}
