package ws;


import dtos.StudentDTO;
import dtos.SubjectDTO;
import dtos.TeacherDTO;
import ejbs.TeacherBean;
import entities.Student;
import entities.Subject;
import entities.Teacher;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/teachers") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TeacherService {

	@EJB
	private TeacherBean teacherBean;

	@GET
	@Path("/")
	public List<TeacherDTO> getAllTeachers() {
		return TeacherDTO.toDTOs(teacherBean.getAllTeachers());
	}

	@GET
	@Path("/{id}")
	public Response getTeacher(@PathParam("id") String id) {
		Teacher teacher = teacherBean.getTeacher(id);
		if (teacher != null) {
			return Response.status(Response.Status.OK)
					.entity(TeacherDTO.toDTO(teacher))
					.build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("ERROR_FINDING_TEACHER")
				.build();
	}

	@POST
	@Path("/")
	public Response createNewTeacher(TeacherDTO teacherDTO) {
		teacherBean.create(
				teacherDTO.getId(),
				teacherDTO.getPassword(),
				teacherDTO.getName(),
				teacherDTO.getEmail(),
				teacherDTO.getOffice()
		);

		Teacher newTeacher = teacherBean.getTeacher(teacherDTO.getId());
		if (newTeacher == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		return Response.status(Response.Status.CREATED)
				.entity(TeacherDTO.toDTO(newTeacher))
				.build();
	}

	@PUT
	@Path("{id}")
	public Response updateTeacher(@PathParam("id") String id, TeacherDTO teacherDTO) {
		teacherBean.update(id, teacherDTO.getPassword(), teacherDTO.getName(), teacherDTO.getEmail(), teacherDTO.getOffice());

		Teacher newTeacher = teacherBean.getTeacher(id);

		if (newTeacher == null || // if something doesn't match
				!newTeacher.getPassword().equals(teacherDTO.getPassword()) ||
				!newTeacher.getName().equals(teacherDTO.getName()) ||
				!newTeacher.getEmail().equals(teacherDTO.getEmail()) ||
				!newTeacher.getOffice().equals(teacherDTO.getOffice()))
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		return Response.status(Response.Status.OK)
				.entity(TeacherDTO.toDTO(newTeacher))
				.build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteTeacher(@PathParam("id") String id) {

		teacherBean.delete(id);

		if (teacherBean.getTeacher(id) != null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR_WHILE_DELETING")
					.build();

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();

	}

	@GET
	@Path("{id}/subjects")
	public Response getTeacherSubjects(@PathParam("id") String id) {
		Teacher teacher = teacherBean.getTeacher(id);
		if (teacher != null) {
			GenericEntity<List<SubjectDTO>> entity = new GenericEntity<>(SubjectDTO.toDTOs(new ArrayList<>(teacher.getSubjects()))){};

			return Response.status(Response.Status.OK)
					.entity(entity)
					.build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("ERROR_FINDING_TEACHER")
				.build();
	}
}
