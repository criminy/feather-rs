package feather.rs.example.templating.web;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import feather.rs.View;
import feather.rs.example.templating.web.views.HomePageView;
import feather.rs.example.templating.web.views.PrimaryView;
import feather.rs.html.Html;

@Named
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

}
