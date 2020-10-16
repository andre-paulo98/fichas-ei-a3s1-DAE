package dtos;

import entities.Course;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CourseDTO implements Serializable {

    private long id;
    private String name;

    public CourseDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CourseDTO() {
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
}
