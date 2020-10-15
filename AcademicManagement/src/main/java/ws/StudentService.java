package ws;

import dtos.StudentDTO;
import dtos.SubjectDTO;
import dtos.TeacherDTO;
import ejbs.StudentBean;
import entities.Student;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class StudentService {

    @EJB
    private StudentBean studentBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<StudentDTO> getAllStudentsWS() {
        return StudentDTO.toDTOs(studentBean.getAllStudents());
    }

    @POST
    @Path("/")
    public Response createNewStudent (StudentDTO studentDTO){
        studentBean.create(studentDTO.getId(),
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getCourseCode());
        Student newStudent = studentBean.getStudent(studentDTO.getId());
        if(newStudent == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.CREATED)
                .entity(StudentDTO.toDTO(newStudent))
                .build();
    }

    @GET
    @Path("{id}")
    public Response getStudentDetails(@PathParam("id") String id) {
        Student student = studentBean.getStudent(id);
        if (student != null) {
            return Response.status(Response.Status.OK)
                    .entity(StudentDTO.toDTO(student))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateStudent(@PathParam("id") String id, StudentDTO studentDTO) {
        studentBean.update(id,
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getCourseCode());

        Student newStudent = studentBean.getStudent(id);

        if (newStudent == null || // if something doesn't match
                !newStudent.getPassword().equals(studentDTO.getPassword()) ||
                !newStudent.getName().equals(studentDTO.getName()) ||
                !newStudent.getEmail().equals(studentDTO.getEmail()) ||
                newStudent.getCourse().getId() != studentDTO.getCourseCode())
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        return Response.status(Response.Status.OK)
                .entity(StudentDTO.toDTO(newStudent))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteStudent(@PathParam("id") String id) {

        studentBean.delete(id);

        if (studentBean.getStudent(id) != null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERROR_WHILE_DELETING")
                    .build();

        return Response.status(Response.Status.OK)
                .entity("SUCCESS")
                .build();

    }

    @GET
    @Path("{id}/subjects")
    public Response getStudentSubjects(@PathParam("id") String id) {
        Student student = studentBean.getStudent(id);
        if (student != null) {
            GenericEntity<List<SubjectDTO>> entity = new GenericEntity<>(SubjectDTO.toDTOs(student.getSubjects())){};

            return Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }


}