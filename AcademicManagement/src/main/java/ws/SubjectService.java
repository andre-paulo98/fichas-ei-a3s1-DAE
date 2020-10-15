package ws;

import dtos.StudentDTO;
import dtos.SubjectDTO;
import dtos.TeacherDTO;
import ejbs.SubjectBean;
import entities.Subject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/subjects") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SubjectService {

	@EJB
	private SubjectBean subjectBean;

	@GET
	@Path("/")
	public List<SubjectDTO> getAllSubjects() {
		return SubjectDTO.toDTOs(subjectBean.getAllSubjects());
	}

	@GET
	@Path("/{id}")
	public Response getSubject(@PathParam("id") int id) {
		Subject subject = subjectBean.getSubject(id);
		if (subject != null) {
			return Response.status(Response.Status.OK)
					.entity(SubjectDTO.toDTO(subject))
					.build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("ERROR_FINDING_SUBJECT")
				.build();
	}

	@POST
	@Path("/")
	public Response createNewSubject(SubjectDTO subjectDTO) {
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
				.entity(SubjectDTO.toDTO(newSubject))
				.build();
	}

	@PUT
	@Path("{code}")
	public Response updateSubject(@PathParam("code") int code, SubjectDTO subjectDTO) {
		subjectBean.update(code, subjectDTO.getName(), subjectDTO.getCourseCode(), subjectDTO.getCourseYear(), subjectDTO.getScholarYear());

		Subject newSubject = subjectBean.getSubject(code);

		if (newSubject == null || // if something doesn't match
				!newSubject.getName().equals(subjectDTO.getName()) ||
				newSubject.getCourse().getId() != subjectDTO.getCourseCode() ||
				!newSubject.getCourseYear().equals(subjectDTO.getCourseYear()) ||
				newSubject.getScholarYear() != subjectDTO.getScholarYear())
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		return Response.status(Response.Status.OK)
				.entity(SubjectDTO.toDTO(newSubject))
				.build();
	}

	@DELETE
	@Path("{code}")
	public Response deleteSubject(@PathParam("code") int code) {

		try {
			subjectBean.delete(code);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(subjectBean.getSubject(code) != null)
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
		if(subject == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR_SUBJECT_DOES_NOT_EXIST")
					.build();

		return Response.status(Response.Status.OK)
				.entity(StudentDTO.toDTOs(new ArrayList<>(subject.getStudents())))
				.build();
	}

	@POST
	@Path("{subjectID}/students/{studentID}")
	public Response enrollStudent(@PathParam("subjectID") int subjectID, @PathParam("studentID") String studentID) {
		String r = subjectBean.enrollStudentInSubject(subjectID, studentID);
		if(!r.equals("OK")) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR: " + r)
					.build();
		}

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();
	}

	@DELETE
	@Path("{subjectID}/students/{studentID}")
	public Response unrollStudent(@PathParam("subjectID") int subjectID, @PathParam("studentID") String studentID) {
		String r = subjectBean.unrollStudentInSubject(subjectID, studentID);
		if(!r.equals("OK")) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR: " + r)
					.build();
		}

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();
	}

	@GET
	@Path("{subjectID}/teachers")
	public Response getTeachers(@PathParam("subjectID") int subjectID) {
		Subject subject = subjectBean.getSubject(subjectID);
		if(subject == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR_SUBJECT_DOES_NOT_EXIST")
					.build();

		return Response.status(Response.Status.OK)
				.entity(TeacherDTO.toDTOs(new ArrayList<>(subject.getTeachers())))
				.build();
	}


	@POST
	@Path("{subjectID}/teachers/{teacherID}")
	public Response associateTeacher(@PathParam("subjectID") int subjectID, @PathParam("teacherID") String teacherID) {
		String r = subjectBean.associateTeacherInSubject(subjectID, teacherID);
		if(!r.equals("OK")) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR: " + r)
					.build();
		}

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();
	}

	@DELETE
	@Path("{subjectID}/teachers/{teacherID}")
	public Response dissociateTeacher(@PathParam("subjectID") int subjectID, @PathParam("teacherID") String teacherID) {
		String r = subjectBean.dissociateTeacherInSubject(subjectID, teacherID);
		if(!r.equals("OK")) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR: " + r)
					.build();
		}

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();
	}

}
