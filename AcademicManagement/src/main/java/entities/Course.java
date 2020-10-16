package entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllCourses",
                query = "SELECT s FROM Course s ORDER BY s.name" // JPQL
        )
})
@Table(name="COURSES", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
public class Course {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Student> students;

    @OneToMany(mappedBy = "course")
    private List<Subject> subjects;

    @Version
    private int version;

    public Course(int id, String name) {
        this.id = id;
        this.name = name;
        students = new LinkedList<>();
        subjects = new LinkedList<>();
    }

    public Course(String name) {
        this.name = name;
        students = new LinkedList<>();
        subjects = new LinkedList<>();
    }

    public Course() {
        students = new LinkedList<>();
        subjects = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
