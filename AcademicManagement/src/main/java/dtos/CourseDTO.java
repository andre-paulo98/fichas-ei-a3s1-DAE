package dtos;

import entities.Course;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CourseDTO implements Serializable {

    private int id;
    private String name;

    public CourseDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CourseDTO() {
    }

    // Converts an entity Student to a DTO Student class
    public static CourseDTO toDTO(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getName()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<CourseDTO> toDTOs(List<Course> courses) {
        return courses.stream().map(CourseDTO::toDTO).collect(Collectors.toList());
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
}
