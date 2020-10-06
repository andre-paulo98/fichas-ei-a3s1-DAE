package ejbs;

import entities.Course;

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

}
