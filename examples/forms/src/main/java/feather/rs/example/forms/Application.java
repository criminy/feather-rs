package feather.rs.example.forms;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import feather.rs.View;
import feather.rs.forms.Form;
import feather.rs.html.Html;

/**
 * The primary application, with two views.
 * 
 * @author sheenobu
 *
 */

@Named
@Path("/")
public class Application {

	/**
	 * The public page view.
	 * @param context The SecurityContext
	 * @return The view object
	 */
	@GET
	public View getPublicPage() {
		return getView(new Form<RegistrationForm>(new RegistrationForm()));
	}
	
	@GET
	@Path("/success")
	public View formSuccess() {
		return new View() {			
			@Override
			public void render(Html html) throws Exception {
				html.loadFromString("<h1>Registration successful!</h1>");
			}
		};
	}

	protected View getView(Form<RegistrationForm> form)
	{
		return new PublicPageView(form);
	}

	@POST
	public Object formSubmit(Form<RegistrationForm> form) 
	{			
		RegistrationForm registrationForm = form.getObject();
		
		if(	form.isValidList("password","password2") && 
			!registrationForm.getPassword().equals(registrationForm.getPassword2()))
		{
			form.setValid(false);
			form.addError("password","Passwords do not match");
		}				
		
		if(form.isValid())
			return Response.seeOther(UriBuilder.fromResource(this.getClass()).segment("success").build()).build();
		else
			return getView(form);		
	}
	
}

/**
 * The public page HTML view
 * @author sheenobu
 *
 */
class PublicPageView implements View
{
	Form<RegistrationForm> formObject;
	
	public PublicPageView(Form<RegistrationForm> formObject) {
		this.formObject = formObject;
	}

	@Override
	public void render(Html html) throws Exception {
		html.load(Application.class.getResourceAsStream("public.html"));
		html.form("#registrationForm p",formObject);			
	}
}
