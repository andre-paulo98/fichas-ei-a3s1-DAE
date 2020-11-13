package entities;

import javax.persistence.*;

@Entity
@NamedQuery(name = "getStudentDocuments", query = "SELECT doc FROM Document doc WHERE doc.student.id = :id")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String filepath;
	private String filename;

	@ManyToOne
	private Student student;

	public Document(String filepath, String filename, Student student) {
		this.filepath = filepath;
		this.filename = filename;
		this.student = student;
	}

	public Document() {
	}

	public long getId() {
		return id;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
