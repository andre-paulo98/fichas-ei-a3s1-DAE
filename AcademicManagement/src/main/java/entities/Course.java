package entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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
    private int id;

    private String name;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Student> students;

    public Course(int id, String name) {
        this.id = id;
        this.name = name;
        students = new LinkedList<>();
    }

    public Course() {
        students = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
