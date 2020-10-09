package ejbs;

import entities.Course;
import entities.Student;
import entities.Subject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class SubjectBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(int code, String name, Course course, int courseYear, int scholarYear, Set<Student> students) {
        Subject subject = new Subject(code, name, course, courseYear, scholarYear, students);
        entityManager.persist(subject);
    }

    public void create(int code, String name, Course course, int courseYear, int scholarYear) {
        Subject subject = new Subject(code, name, course, courseYear, scholarYear, new LinkedHashSet<>());
        entityManager.persist(subject);
    }

    public List<Subject> getAllSubjects() {
        return (List<Subject>) entityManager.createNamedQuery("getAllSubjects").getResultList();
    }
}
