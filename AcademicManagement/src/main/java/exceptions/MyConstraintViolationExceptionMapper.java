package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyConstraintViolationExceptionMapper implements ExceptionMapper<MyConstraintViolationException> {
	private static final Logger logger = Logger.getLogger("exceptions.MyConstraintViolationException");

	@Override
	public Response toResponse(MyConstraintViolationException e) {
		String errorMsg = e.getMessage();
		logger.warning("ERROR: " + errorMsg);
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMsg).build();
	}
}