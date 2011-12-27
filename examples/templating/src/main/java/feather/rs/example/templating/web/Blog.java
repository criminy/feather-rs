package feather.rs.example.templating.web;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import feather.rs.View;
import feather.rs.example.templating.web.views.HomePageView;
import feather.rs.example.templating.web.views.PrimaryView;
import feather.rs.html.Html;

@Path("/")
public class Blog {

	@GET
	public View frontPage() {
		return new PrimaryView(new HomePageView());
	}
	
	@GET
	@Path("page1")
	public View page1() {
		return new PrimaryView(new View() {
			
			@Override
			public void render(Html html) throws Exception {
				html.loadFromString("<p>This is an inline view built from html.loadFromString. <a href='/page2'>Here</a> is the next page." +
						", which doesn't really exist.</p>");				
			}
		});
	}
	
	//TODO: move this into static media subsystem
	@Path("/style.css")
	@Produces("text/css")
	@GET
	public InputStream styleCss() {
		return PrimaryView.class.getClassLoader().getResourceAsStream("/theme/style.css");
	}

	//TODO: move this into static media subsystem
	@Path("/images/{imageName}") 
	@Produces("media/jpeg")
	@GET
	public InputStream img(@PathParam("imageName") String imgName) {
		return PrimaryView.class.getClassLoader().getResourceAsStream("/theme/images/" + imgName);
	}	
}
