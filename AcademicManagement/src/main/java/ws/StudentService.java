package ws;

import dtos.StudentDTO;
import dtos.SubjectDTO;
import ejbs.StudentBean;
import entities.Student;
import entities.Subject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/students") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class StudentService {

    @EJB
    private StudentBean studentBean;

    // Converts an entity Student to a DTO Student class
    private StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getPassword(),
                student.getName(),
                student.getEmail(),
                student.getCourse().getId(),
                student.getCourse().getName()
        );
    }

    private SubjectDTO toDTO(Subject subject) {
        return new SubjectDTO(
                subject.getCode(),
                subject.getName(),
                subject.getCourse(),
                subject.getCourseYear(),
                subject.getScholarYear()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<StudentDTO> toDTOs(List<Student> students) {
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<SubjectDTO> subjectsToDTOs(List<Subject> subjects) {
        return subjects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<StudentDTO> getAllStudentsWS() {
        return toDTOs(studentBean.getAllStudents());
    }

    @POST
    @Path("/")
    public Response createNewStudent (StudentDTO studentDTO){
        studentBean.create(studentDTO.getId(),
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getCourseCode());
        Student newStudent = studentBean.findStudent(studentDTO.getId());
        if(newStudent == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(newStudent))
                .build();
    }

    @GET
    @Path("{id}")
    public Response getStudentDetails(@PathParam("id") String id) {
        Student student = studentBean.findStudent(id);
        if (student != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTO(student))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @GET
    @Path("{id}/subjects")
    public Response getStudentSubjects(@PathParam("id") String id) {
        Student student = studentBean.findStudent(id);
        if (student != null) {
            GenericEntity<List<SubjectDTO>> entity = new GenericEntity<>(subjectsToDTOs(student.getSubjects())){};
            List<SubjectDTO> a = subjectsToDTOs(student.getSubjects());

            return Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }


}