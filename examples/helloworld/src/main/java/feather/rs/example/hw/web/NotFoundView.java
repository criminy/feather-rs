package feather.rs.example.hw.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import feather.rs.View;
import feather.rs.html.Html;

/**
 * Simple 404 not found view.
 * 
 * @author sheenobu
 *
 */
@Path("/404")
public class NotFoundView implements View{

	@GET
	public NotFoundView get() {
		return new NotFoundView();
	}
	
	@Override
	public void render(Html html) throws Exception {
		html.loadFromString("<h1>Powderj example: 404 file not found</h1>");		
	}

}
