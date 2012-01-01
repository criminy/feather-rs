package feather.rs.example.secured;

import javax.inject.Named;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Named
@Path("/MYlogin") //customized the URL using @Path
public class Login extends  feather.rs.auth.resources.Login {
 
	//customized the behavior of onSuccess 
	@Override
	public Response onSuccess() {
		//redirect to the front of the application after login 
		// TODO: the default behavior should use the HTTP referrer. 
		return Response.seeOther(UriBuilder.fromPath("/").build()).build();
	}

}
