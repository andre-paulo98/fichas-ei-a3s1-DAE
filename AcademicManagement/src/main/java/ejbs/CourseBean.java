package ejbs;

import entities.Course;
import entities.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class CourseBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name) {
        Course course = new Course(id, name);
        entityManager.persist(course);
    }

    public List<Course> getAllCourses() {
        return (List<Course>) entityManager.createNamedQuery("getAllCourses").getResultList();
    }

    public Course getCourse(int id) {
        return entityManager.find(Course.class, id);
    }


    public void update(int id, String name) {
        Course course = getCourse(id);
        if(course != null) {
            course.setName(name);

            entityManager.persist(course);
        } else {
            throw new NotFoundException("STUDENT NOT FOUND");
        }
    }

    public void delete(int id) {
        Course course = getCourse(id);
        entityManager.remove(course);
    }
}
