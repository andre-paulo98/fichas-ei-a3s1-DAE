package ejbs;

import entities.Course;
import entities.Document;
import entities.Student;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class DocumentBean {

	@PersistenceContext
	EntityManager entityManager;

	public void create(String id, String filepath, String filename) throws MyEntityNotFoundException, MyConstraintViolationException {
		Student student = entityManager.find(Student.class, id);
		if (student == null)
			throw new MyEntityNotFoundException("STUDENT DOES NOT EXIST");

		try {
			Document document = new Document(filepath, filename, student);
			entityManager.persist(document);
			student.addDocument(document);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}

	}

	public Document findDocument(long id){
		return entityManager.find(Document.class, id);
	}

	public List<Document> getStudentDocuments(String username){
		return (List<Document>) entityManager.createNamedQuery("getStudentDocuments").setParameter("id", username).getResultList();
	}
}
