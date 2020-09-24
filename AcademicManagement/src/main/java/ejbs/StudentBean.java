package ejbs;

import entities.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StudentBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String id, String password, String name, String email) {
        Student student = new Student(id, password, name, email);

        entityManager.persist(student);
    }
}
