package feather.rs.example.lifecycle;

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

	public Application() {
		System.out.println("Constructing Application object");
	}

	/**
	 * The public page view.
	 * @return The view object.
	 */
	@GET
	public View getPublicPage() {
		System.out.println("PUBLIC GET PAGE");
		return getView(new Form<RegistrationForm>(RegistrationForm.construct()));
	}
	
	/**
	 * The success page view.
	 * @return The view object.
	 */
	@GET
	@Path("/success")
	public View formSuccess() {
		System.out.println("form Success");
		return new View() {			
			@Override
			public void render(Html html) throws Exception {
				html.loadFromString("<h1>Registration successful!</h1>");
			}
		};
	}

	/**
	 * Returns the primary view, given the Form object
	 * @param form The Form binding object.
	 * @return The populated view object.
	 */
	protected View getView(Form<RegistrationForm> form)
	{
		System.out.println("GETTING VIEW");
		View v = new PublicPageView(form);
		System.out.println("-------------");
		return v;
	}

	/**
	 * Action called when the form is submitted.
	 *
	 * @param form The Form binding object.
	 * @return An HTTP redirect to 'success' on success, or the populated view on form validation errors.
	 */
	@POST
	public Object formSubmit(Form<RegistrationForm> form) 
	{			
		System.out.println("form submit!");
		RegistrationForm registrationForm = form.getObject();
		
		//custom validation code, checking if password and password2 are valid fields (NotNull) and
		// then checking if they are the same. 
		// 
		// On error, invalid the form and add the error to the form view.
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
		System.out.println("Constructing view");
		this.formObject = formObject;
	}

	@Override
	public void render(Html html) throws Exception {
		System.out.println("Rendering view");
		html.load(Application.class.getResourceAsStream("public.html"));

		//renders the form
		html.form("#registrationForm p",formObject);			
	}
}
