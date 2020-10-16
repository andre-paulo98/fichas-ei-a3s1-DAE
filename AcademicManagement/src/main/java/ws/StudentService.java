package ws;

import dtos.StudentDTO;
import dtos.SubjectDTO;
import dtos.TeacherDTO;
import ejbs.StudentBean;
import entities.Student;
import entities.Subject;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/students") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class StudentService {

    @EJB
    private StudentBean studentBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<StudentDTO> getAllStudentsWS() {
        return StudentService.toDTOsNoSubjects(studentBean.getAllStudents());
    }

    @POST
    @Path("/")
    public Response createNewStudent (StudentDTO studentDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        studentBean.create(studentDTO.getId(),
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getCourseCode());

        Student newStudent = studentBean.getStudent(studentDTO.getId());
        if(newStudent == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        return Response.status(Response.Status.CREATED)
                .entity(StudentService.toDTO(newStudent))
                .build();
    }

    @GET
    @Path("{id}")
    public Response getStudentDetails(@PathParam("id") String id) {
        Student student = studentBean.getStudent(id);
        if (student != null) {
            return Response.status(Response.Status.OK)
                    .entity(StudentService.toDTO(student))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateStudent(@PathParam("id") String id, StudentDTO studentDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
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
                .entity(StudentService.toDTO(newStudent))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteStudent(@PathParam("id") String id) throws MyEntityNotFoundException {

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
            GenericEntity<List<SubjectDTO>> entity = new GenericEntity<>(SubjectService.toDTOs(student.getSubjects())){};

            return Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    public static StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getPassword(),
                student.getName(),
                student.getEmail(),
                student.getCourse().getId(),
                student.getCourse().getName(),
                SubjectService.toDTOs(new ArrayList<>(student.getSubjects()))
        );
    }

    public static List<StudentDTO> toDTOs(List<Student> students) {
        return students.stream().map(StudentService::toDTO).collect(Collectors.toList());
    }

    public static StudentDTO toDTONoSubjects(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getPassword(),
                student.getName(),
                student.getEmail(),
                student.getCourse().getId(),
                student.getCourse().getName()
        );
    }

    public static List<StudentDTO> toDTOsNoSubjects(List<Student> students) {
        return students.stream().map(StudentService::toDTONoSubjects).collect(Collectors.toList());
    }


}