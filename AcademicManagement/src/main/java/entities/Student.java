package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllStudents",
                query = "SELECT s FROM Student s ORDER BY s.name" // JPQL
        )
})
@Table(name="STUDENTS")
public class Student extends User {

    @ManyToOne
    @JoinColumn(name="COURSE_CODE")
    @NotNull
    private Course course;

    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;

    public Student() {
        subjects = new LinkedList<>();
    }

    public Student(String id, String password, String name, String email, Course course) {
        super(id, password, name, email);
        this.course = course;
        subjects = new LinkedList<>();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }


}
