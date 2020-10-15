package ejbs;

import entities.Course;
import entities.Student;
import entities.Subject;
import entities.Teacher;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class SubjectBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(int code, String name, int courseID, String courseName, String courseYear, int scholarYear, Set<Student> students) {
        Course course = entityManager.find(Course.class, courseID);
        if(course != null) {
            Subject subject = new Subject(code, name, course, courseYear, scholarYear, students);
            entityManager.persist(subject);
        } else {
            throw new NotFoundException("COURSE NOT FOUND");
        }
    }

    public void create(int code, String name, int courseID, String courseName, String courseYear, int scholarYear) {
        Course course = entityManager.find(Course.class, courseID);
        if(course != null) {
            Subject subject = new Subject(code, name, course, courseYear, scholarYear, new LinkedHashSet<>());
            entityManager.persist(subject);
        } else {
            throw new NotFoundException("COURSE NOT FOUND");
        }
    }

    public void create(int code, String name, Course course, String courseYear, int scholarYear) {
        Subject subject = new Subject(code, name, course, courseYear, scholarYear, new LinkedHashSet<>());
        entityManager.persist(subject);
    }

    public void update(int code, String name, int courseID, String courseYear, int scholarYear) {
        Subject subject = entityManager.find(Subject.class, code);
        if(subject != null) {
            Course course = entityManager.find(Course.class, courseID);
            if(course != null) {
                subject.setName(name);
                subject.setCourse(course);
                subject.setCourseYear(courseYear);
                subject.setScholarYear(scholarYear);
                entityManager.persist(subject);
            } else {
                throw new NotFoundException("COURSE NOT FOUND");
            }
        } else {
            throw new NotFoundException("SUBJECT NOT FOUND");
        }
    }

    public void delete(int code) {
        Subject subject = getSubject(code);
        entityManager.remove(subject);
    }

    public List<Subject> getAllSubjects() {
        return (List<Subject>) entityManager.createNamedQuery("getAllSubjects").getResultList();
    }

    public Subject getSubject(int code) {
        return entityManager.find(Subject.class, code);
    }

    public String associateTeacherInSubject(int subjectCode, String id) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        if(teacher != null) {
            Subject subject = entityManager.find(Subject.class, subjectCode);
            if(subject != null) {
                if(!subject.getTeachers().contains(teacher)) {
                    teacher.addSubject(subject);
                    subject.addTeacher(teacher);
                    return "OK";
                }
                return "TEACHER IN SUBJECT ALREADY";
            }
            return "SUBJECT DOES NOT EXIST";
        }
        return "TEACHER DOES NOT EXIST";
    }

    public String dissociateTeacherInSubject(int subjectCode, String id) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        if(teacher != null) {
            Subject subject = entityManager.find(Subject.class, subjectCode);
            if(subject != null) {
                teacher.removeSubject(subject);
                subject.removeTeacher(teacher);
                return "OK";
            }
            return "SUBJECT DOES NOT EXIST";
        }
        return "TEACHER DOES NOT EXIST";
    }

    public String enrollStudentInSubject(int subjectCode, String id) {
        Student student = entityManager.find(Student.class, id);
        if(student != null) {
            Subject subject = entityManager.find(Subject.class, subjectCode);
            if(subject != null) {
                if(subject.getCourse().equals(student.getCourse()) && !subject.getStudents().contains(student)) {
                    student.addSubject(subject);
                    subject.addStudent(student);
                    return "OK";
                }
                return "COURSE IS NOT THE SAME OR STUDENT ALREADY IN SUBJECT";
            }
            return "SUBJECT DOES NOT EXIST";
        }
        return "STUDENT DOES NOT EXIST";
    }

    public String unrollStudentInSubject(int subjectCode, String id) {
        Student student = entityManager.find(Student.class, id);
        if(student != null) {
            Subject subject = entityManager.find(Subject.class, subjectCode);
            if(subject != null) {
                subject.removeStudent(student);
                student.removeSubject(subject);
                return "OK";
            }
            return "SUBJECT DOES NOT EXIST";
        }
        return "STUDENT DOES NOT EXIST";
    }
}
