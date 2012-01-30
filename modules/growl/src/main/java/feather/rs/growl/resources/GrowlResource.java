package feather.rs.growl.resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import feather.rs.growl.service.GrowlService;

@Named
@Path("/growls")
public class GrowlResource {

	@Inject
	GrowlService growlService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Growls getGrowls(@Context HttpServletRequest req) 
	{
		Growls g = new Growls();
		growlService.register(req.getSession(true));	
		g.setGrowls(growlService.popAll(req.getSession(true).getId()));		
		return g;
	}

}
