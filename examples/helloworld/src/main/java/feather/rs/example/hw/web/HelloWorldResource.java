package feather.rs.example.hw.web;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import feather.rs.View;
import feather.rs.example.hw.db.HelloWorldDatabase;


/**
 * Resource which shows the hello world message.
 *  
 * @author sheenobu
 *
 */
@Path("/")
public class HelloWorldResource {

	//	we don't need to load this from a singleton context (yet) because
	// 	the database is just simple, tiny, and in memory.
	HelloWorldDatabase db = new HelloWorldDatabase();
	
	@GET
	public Response redirect() {
		return Response.seeOther(URI.create("/lang/en")).build();		
	}
	
	/**
	 * Returns the populated html view of the page.
	 * 
	 * @param lang The language to display the page in.
	 * @return The page object.
	 */
	@GET
	@Path("/lang/{lang}")
	@Produces("text/html")
	public View html(@PathParam(value="lang") String lang) {		
		return new HelloWorldView(db.getHelloWorldMessage(lang),db.getSupportedLanguages(),lang);
	}
	
}
