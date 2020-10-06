package ejbs;

import entities.Course;
import entities.Student;

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
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return (List<Student>) entityManager.createNamedQuery("getAllStudents").getResultList();
    }

    public Student findStudent(String id) {
        return entityManager.find(Student.class, id);
    }

}