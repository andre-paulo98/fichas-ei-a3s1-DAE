package ws;

import dtos.CourseDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import ejbs.CourseBean;
import entities.Course;
import entities.Student;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/courses") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class CourseService {

    @EJB
    private CourseBean courseBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<CourseDTO> getAllCoursesWS() {
        return CourseService.toDTOs(courseBean.getAllCourses());
    }

    @GET
    @Path("/{id}")
    public Response getStudentDetails(@PathParam("id") int id) {
        Course course = courseBean.getCourse(id);
        if (course != null) {
            return Response.status(Response.Status.OK)
                    .entity(CourseService.toDTO(course))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_COURSE")
                .build();
    }

    @POST
    @Path("/")
    public Response createNewCourse(CourseDTO courseDTO) throws MyEntityExistsException, MyConstraintViolationException {
        courseBean.create(
                courseDTO.getName()
        );

        Course newCourse = courseBean.getCourse(courseDTO.getId());
        if (newCourse == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.CREATED)
                .entity(CourseService.toDTO(newCourse))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateCourse(@PathParam("id") int id, CourseDTO courseDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        courseBean.update(id,
                courseDTO.getName());

        Course newCourse = courseBean.getCourse(id);

        if (newCourse == null || // if something doesn't match
                !newCourse.getName().equals(courseDTO.getName()))
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        return Response.status(Response.Status.OK)
                .entity(CourseService.toDTO(newCourse))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteStudent(@PathParam("id") int id) throws MyEntityNotFoundException {

        courseBean.delete(id);

        if (courseBean.getCourse(id) != null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERROR_WHILE_DELETING")
                    .build();

        return Response.status(Response.Status.OK)
                .entity("SUCCESS")
                .build();

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
        return courses.stream().map(CourseService::toDTO).collect(Collectors.toList());
    }

}
