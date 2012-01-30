package feather.rs.example.growl.web;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import feather.rs.View;
import feather.rs.example.growl.web.views.HomePageView;
import feather.rs.example.growl.web.views.PrimaryView;
import feather.rs.growl.service.GrowlService;

@Named
@Path("/")
public class Blog {

	@Inject GrowlService growlService;
	
	@GET
	public View frontPage(@Context HttpServletRequest req) {		
		return new PrimaryView(new HomePageView());
	}
	
}
