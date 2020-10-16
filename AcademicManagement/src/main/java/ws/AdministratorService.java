package ws;

import dtos.AdministratorDTO;
import dtos.SubjectDTO;
import dtos.TeacherDTO;
import ejbs.AdministratorBean;
import ejbs.TeacherBean;
import entities.Administrator;
import entities.Teacher;
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

@Path("/administrators") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministratorService {

	@EJB
	private AdministratorBean administratorBean;

	@GET
	@Path("/")
	public List<AdministratorDTO> getAllAdmins() {
		return AdministratorService.toDTOs(administratorBean.getAllAdmins());
	}

	@GET
	@Path("/{id}")
	public Response getAdmin(@PathParam("id") String id) {
		Administrator admin = administratorBean.getAdmin(id);
		if (admin != null) {
			return Response.status(Response.Status.OK)
					.entity(AdministratorService.toDTO(admin))
					.build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("ERROR_FINDING_TEACHER")
				.build();
	}

	@POST
	@Path("/")
	public Response createNewAdmin(AdministratorDTO administratorDTO) throws MyEntityExistsException, MyConstraintViolationException {
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
				.entity(AdministratorService.toDTO(newAdmin))
				.build();
	}

	@PUT
	@Path("{id}")
	public Response updateAdmin(@PathParam("id") String id, AdministratorDTO administratorDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
		administratorBean.update(id, administratorDTO.getPassword(), administratorDTO.getName(), administratorDTO.getEmail());

		Administrator newAdmin = administratorBean.getAdmin(id);

		if (newAdmin == null || // if something doesn't match
				!newAdmin.getPassword().equals(administratorDTO.getPassword()) ||
				!newAdmin.getName().equals(administratorDTO.getName()) ||
				!newAdmin.getEmail().equals(administratorDTO.getEmail()))
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		return Response.status(Response.Status.OK)
				.entity(AdministratorService.toDTO(newAdmin))
				.build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteAdmin(@PathParam("id") String id) throws MyEntityNotFoundException {

		administratorBean.delete(id);

		if (administratorBean.getAdmin(id) != null)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("ERROR_WHILE_DELETING")
					.build();

		return Response.status(Response.Status.OK)
				.entity("SUCCESS")
				.build();

	}

	public static AdministratorDTO toDTO(Administrator administrator) {
		return new AdministratorDTO(
				administrator.getId(),
				administrator.getPassword(),
				administrator.getName(),
				administrator.getEmail()
		);
	}

	public static List<AdministratorDTO> toDTOs(List<Administrator> administrators) {
		return administrators.stream().map(AdministratorService::toDTO).collect(Collectors.toList());
	}

}
