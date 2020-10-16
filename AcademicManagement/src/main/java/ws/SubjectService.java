package ws;

import dtos.StudentDTO;
import dtos.SubjectDTO;
import dtos.TeacherDTO;
import ejbs.SubjectBean;
import entities.Subject;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/subjects") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SubjectService {

	@EJB
	private SubjectBean subjectBean;

	@GET
	@Path("/")
	public List<SubjectDTO> getAllSubjects() {
		return SubjectService.toDTOs(subjectBean.getAllSubjects());
	}

	@GET
	@Path("/{id}")
	public Response getSubject(@PathParam("id") int id) {
		Subject subject = subjectBean.getSubject(id);
		if (subject != null) {
			return Response.status(Response.Status.OK)
					.entity(SubjectService.toDTO(subject))
					.build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("ERROR_FINDING_SUBJECT")
				.build();
	}

	@POST
	@Path("/")
	public Response createNewSubject(SubjectDTO subjectDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
		subjectBean.create(subjectDTO.getCode(), // TODO Perguntar ao professor se dá para fazer ID auto increment
				subjectDTO.getName(),
				subjectDTO.getCourseCode(),
				subjectDTO.getCourseName(),
				subjectDTO.getCourseYear(),
				subjectDTO.getScholarYear());

		Subject newSubject = subjectBean.getSubject(subjectDTO.getCode());
		if (newSubject == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		return Response.status(Response.Status.CREATED)
				.entity(SubjectService.toDTO(newSubject))
				.build();
	}

	@PUT
	@Path("{code}")
	public Response updateSubject(@PathParam("code") int code, SubjectDTO subjectDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
		subjectBean.update(code, subjectDTO.getName(), subjectDTO.getCourseCode(), subjectDTO.getCourseYear(), subjectDTO.getScholarYear());

		Subject newSubject = subjectBean.getSubject(code);

		if (newSubject == null || // if something doesn't match
				!newSubject.getName().equals(subjectDTO.getName()) ||
				newSubject.getCourse().getId() != subjectDTO.getCourseCode() ||
				!newSubject.getCourseYear().equals(subjectDTO.getCourseYear()) ||
				newSubject.getScholarYear() != subjectDTO.getScholarYear())
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		return Response.status(Response.Status.OK)
				.entity(SubjectService.toDTO(newSubject))
				.build();
	}

	@DELETE
	@Path("{code}")
	public Response deleteSubject(@PathParam("code") int code) throws MyEntityNotFoundException {

		subjectBean.delete(code);

		if (subjectBean.getSubject(code) != null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR_WHILE_DELETING")
					.build();

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();

	}

	@GET
	@Path("{subjectID}/students")
	public Response getStudents(@PathParam("subjectID") int subjectID) {
		Subject subject = subjectBean.getSubject(subjectID);
		if (subject == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR_SUBJECT_DOES_NOT_EXIST")
					.build();

		return Response.status(Response.Status.OK)
				.entity(StudentService.toDTOs(new ArrayList<>(subject.getStudents())))
				.build();
	}

	@POST
	@Path("{subjectID}/students/{studentID}")
	public Response enrollStudent(@PathParam("subjectID") int subjectID, @PathParam("studentID") String studentID) throws MyEntityExistsException, MyEntityNotFoundException, MyIllegalArgumentException {
		subjectBean.enrollStudentInSubject(subjectID, studentID);

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();
	}

	@DELETE
	@Path("{subjectID}/students/{studentID}")
	public Response unrollStudent(@PathParam("subjectID") int subjectID, @PathParam("studentID") String studentID) throws MyEntityNotFoundException {
		subjectBean.unrollStudentInSubject(subjectID, studentID);

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();
	}

	@GET
	@Path("{subjectID}/teachers")
	public Response getTeachers(@PathParam("subjectID") int subjectID) {
		Subject subject = subjectBean.getSubject(subjectID);
		if (subject == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR_SUBJECT_DOES_NOT_EXIST")
					.build();

		return Response.status(Response.Status.OK)
				.entity(TeacherService.toDTOs(new ArrayList<>(subject.getTeachers())))
				.build();
	}


	@POST
	@Path("{subjectID}/teachers/{teacherID}")
	public Response associateTeacher(@PathParam("subjectID") int subjectID, @PathParam("teacherID") String teacherID) throws MyEntityExistsException, MyEntityNotFoundException {
		subjectBean.associateTeacherInSubject(subjectID, teacherID);

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();
	}

	@DELETE
	@Path("{subjectID}/teachers/{teacherID}")
	public Response dissociateTeacher(@PathParam("subjectID") int subjectID, @PathParam("teacherID") String teacherID) throws MyEntityNotFoundException {
		subjectBean.dissociateTeacherInSubject(subjectID, teacherID);

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();
	}

	public static SubjectDTO toDTO(Subject subject) {
		return new SubjectDTO(
				subject.getCode(),
				subject.getName(),
				subject.getCourse().getId(),
				subject.getCourse().getName(),
				subject.getCourseYear(),
				subject.getScholarYear()
		);
	}

	public static List<SubjectDTO> toDTOs(List<Subject> subjects) {
		return subjects.stream().map(SubjectService::toDTO).collect(Collectors.toList());
	}

}
