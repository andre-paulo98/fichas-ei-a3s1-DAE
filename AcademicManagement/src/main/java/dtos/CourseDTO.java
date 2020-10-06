package dtos;

import java.io.Serializable;

public class CourseDTO implements Serializable {

    private int id;
    private String name;

    public CourseDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CourseDTO() {
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
