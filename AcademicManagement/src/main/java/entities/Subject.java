package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="SUBJECTS", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "COURSE_YEAR", "SCHOLAR_YEAR"}))
@NamedQueries({
        @NamedQuery(
                name = "getAllSubjects",
                query = "SELECT s FROM Subject  s ORDER BY s.course.name, s.scholarYear DESC, s.courseYear, s.name"
        )
})
public class Subject {

    @Id
    private int code;

    @NotNull
    private String name;

    @ManyToOne
    private Course course;

    @Column(name = "COURSE_YEAR")
    private int courseYear;

    @Column(name = "SCHOLAR_YEAR")
    private int scholarYear;

    @ManyToMany
    @JoinTable(name = "SUBJECTS_STUDENTS",
            joinColumns = @JoinColumn(name = "SUBJECT_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID"))
    private Set<Student> students;

    @ManyToMany(mappedBy = "subjects")
    private Set<Teacher> teachers;

    public Subject() {
        students = new LinkedHashSet<>();
    }

    public Subject(int code, String name, Course course, int courseYear, int scholarYear, Set<Student> students) {
        this.code = code;
        this.name = name;
        this.course = course;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
        this.students = students;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public int getScholarYear() {
        return scholarYear;
    }

    public void setScholarYear(int scholarYear) {
        this.scholarYear = scholarYear;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }



}
