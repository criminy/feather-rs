package feather.rs.example.growl.web.views;

import javax.inject.Named;
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
@Named
@Path("/404")
public class NotFoundView implements View{

	@GET
	public View get() {
		return new PrimaryView(new NotFoundView());
	}
	
	@Override
	public void render(Html html) throws Exception {
		html.loadFromString("<p>Powderj example: 404 file not found</p>");		
	}

}
