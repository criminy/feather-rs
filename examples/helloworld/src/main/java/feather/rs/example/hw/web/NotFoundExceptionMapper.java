package feather.rs.example.hw.web;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import feather.rs.example.hw.db.NotFoundException;

/**
 * Provider which converts Database backend exceptions to 
 * proper error codes.
 * 
 * @author sheenobu
 *
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	@Override
	public Response toResponse(NotFoundException exception) {
		return Response.status(404).cacheControl(null).entity(new NotFoundView()).build();		
	}

}
