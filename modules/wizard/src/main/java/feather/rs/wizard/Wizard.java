package feather.rs.wizard;

import java.util.UUID;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import feather.rs.View;

/**
 * Abstract class representing a series of JAX-RS-based workflow 
 * steps in a web application.
 * 
 * 
 * @author artripa
 *
 */
public abstract class Wizard {

	
	public abstract Step onStart();	
	public abstract Response onNext(Step currentStep, WizardData data);
	public abstract Response onCancel(WizardData data);
	public abstract Response onFinish(WizardData data);
	
	
	@GET//TODO: not indomopent, possibly bad design.
	@Path("/new")
	public Response wizardStartGET()
	{
		//Just return a view with the wizard 'begin' button
		//	and possibly information on the wizard 
		//	and the first form.
		
		//WizardData data = new WizardData();
		//data.setWizardId(UUID.randomUUID().toString());		
		//return onStart(data);
		Step s = onStart();
		return null;
	}
	
	
	@POST
	@Path("/new")
	public Response wizardStartPOST(Step step)
	{
		return null;
	}
	
	
	@GET
	@Path("/w/{id}/{step}")
	public View wizardStep(
				@PathParam("step") String stepId, 
				@PathParam("id") String wizardId)
	{
		return null;
	}
	
	@POST
	@Path("/w/{id}/{step}")
	public Response onAction(
			@PathParam("step") String stepId,
			@PathParam("id") String wizardId,
			@FormParam("action") String actionName
		)
	{
		return null;
	}
	
	public Response toNext(Step step)
	{
		return null;
	}
	
	public Response toLast(Step step) {
		return null;
	}

	public Response toError() {
		return null;
	}
	
}
