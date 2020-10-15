package entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
		@NamedQuery(
				name = "getAllTeachers",
				query = "SELECT s FROM Teacher s ORDER BY s.name" // JPQL
		)
})
public class Teacher extends User {

	private String office;

	@ManyToMany
	@JoinTable(name = "SUBJECTS_TEACHERS",
			joinColumns = @JoinColumn(name = "TEACHER_ID", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "SUBJECT_CODE", referencedColumnName = "CODE"))
	private Set<Subject> subjects;

	public Teacher() {
		this.subjects = new LinkedHashSet<>();
	}

	public Teacher(String id, @NotNull String password, @NotNull String name, @NotNull @Email String email, String office) {
		super(id, password, name, email);
		this.office = office;
		this.subjects = new LinkedHashSet<>();
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public void addSubject(Subject subject) {
		subjects.add(subject);
	}

	public void removeSubject(Subject subject) {
		subjects.remove(subject);
	}
}
