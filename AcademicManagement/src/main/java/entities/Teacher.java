package entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Teacher extends User {

	private String office;

	@ManyToMany
	@JoinTable(name = "SUBJECTS_TEACHERS",
			joinColumns = @JoinColumn(name = "SUBJECT_CODE", referencedColumnName = "CODE"),
			inverseJoinColumns = @JoinColumn(name = "TEACHER_ID", referencedColumnName = "ID"))
	private Set<Subject> subjects;

	// TODO 27. Ficha 3
}
