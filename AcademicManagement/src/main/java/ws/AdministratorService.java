package ws;

import dtos.AdministratorDTO;
import dtos.SubjectDTO;
import dtos.TeacherDTO;
import ejbs.AdministratorBean;
import ejbs.TeacherBean;
import entities.Administrator;
import entities.Teacher;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/administrators") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministratorService {

	@EJB
	private AdministratorBean administratorBean;

	@GET
	@Path("/")
	public List<AdministratorDTO> getAllAdmins() {
		return AdministratorDTO.toDTOs(administratorBean.getAllAdmins());
	}

	@GET
	@Path("/{id}")
	public Response getAdmin(@PathParam("id") String id) {
		Administrator admin = administratorBean.getAdmin(id);
		if (admin != null) {
			return Response.status(Response.Status.OK)
					.entity(AdministratorDTO.toDTO(admin))
					.build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("ERROR_FINDING_TEACHER")
				.build();
	}

	@POST
	@Path("/")
	public Response createNewAdmin(AdministratorDTO administratorDTO) {
		administratorBean.create(
				administratorDTO.getId(),
				administratorDTO.getPassword(),
				administratorDTO.getName(),
				administratorDTO.getEmail()
		);

		Administrator newAdmin = administratorBean.getAdmin(administratorDTO.getId());
		if (newAdmin == null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		return Response.status(Response.Status.CREATED)
				.entity(AdministratorDTO.toDTO(newAdmin))
				.build();
	}

	@PUT
	@Path("{id}")
	public Response updateAdmin(@PathParam("id") String id, AdministratorDTO administratorDTO) {
		administratorBean.update(id, administratorDTO.getPassword(), administratorDTO.getName(), administratorDTO.getEmail());

		Administrator newAdmin = administratorBean.getAdmin(id);

		if (newAdmin == null || // if something doesn't match
				!newAdmin.getPassword().equals(administratorDTO.getPassword()) ||
				!newAdmin.getName().equals(administratorDTO.getName()) ||
				!newAdmin.getEmail().equals(administratorDTO.getEmail()))
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		return Response.status(Response.Status.OK)
				.entity(AdministratorDTO.toDTO(newAdmin))
				.build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteAdmin(@PathParam("id") String id) {

		administratorBean.delete(id);

		if (administratorBean.getAdmin(id) != null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR_WHILE_DELETING")
					.build();

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();

	}

}
