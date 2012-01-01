package feather.rs.auth.resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import feather.rs.View;
import feather.rs.auth.AuthenticationException;
import feather.rs.auth.LoginService;
import feather.rs.html.Html;

/**
 * An abstract class which represents a Login page.
 * 
 * @author sheenobu
 *
 */
@Named
@Path("/login")
public class Login {
	
	@Inject LoginService loginService;
		
	
	/**
	 * Gets the view.
	 * @return
	 */
	@GET
	public View loginView() {
		return getView(null);
	}
	
	/**
	 * Accepts the form input and tries to authenticate, returning a response which represents the
	 * success or failure of the authentication.
	 * 
	 * @param username attempted username.
	 * @param password attempted password. 
	 * @return
	 */
	@POST
	public Response attemptLogin(
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("rememberMe") @DefaultValue("false") boolean rememberMe)
	{		
		try {
			loginService.login(username, password, rememberMe);
		}catch(AuthenticationException exn)
		{
			return onFailure(exn);
		}
		
		return onSuccess();	
	}

	/**
	 * Gets the view, given an optional {@link AuthenticationException}
	 * 
	 * @param exn The optional {@link AuthenticationException}
	 * @return The populated View.
	 */
	public View getView(AuthenticationException exn)
	{
		//build path using UriBuilder.fromResource because the actual URI might be customized
		//	by some end-user magic.
		String path = UriBuilder.fromResource(this.getClass()).build().toString();
		if(exn == null)
			return new LoginView(path);
		else
			return new LoginView(path,convertErrorMessage(exn));
	}

	/**
	 * Callback method to invoke when we successfully authenticate.
	 * @return The JAX-RS {@link Response} 
	 */
	public Response onSuccess() { return Response.ok(getView(null)).build();	}
	
	/**
	 * Callback method to invoke when we fail to authenticate.
	 * 
	 * @param exn The {@link AuthenticationException} representing the failure.
	 * @return The JAX-RS {@link Response}
	 */
	public Response onFailure(AuthenticationException exn) { return Response.ok(getView(exn)).build();	}
	
	
	/**
	 * Reads in the {@link AuthenticationException} and returns a user-friendly message.
	 * @param exn The {@link AuthenticationException} object.
	 * @return A string of the user friendly message.
	 */
	public String convertErrorMessage(AuthenticationException exn){
		return exn.getLocalizedMessage();
	}
	
}

/**
 * Sample login view, using the local login.html embedded in the JAR file.
 * 
 * @author sheenobu
 *
 */
class LoginView implements View
{

	String msg;
	String actionUrl;
	
	public LoginView(String actionUrl,String msg) {
		this.msg = msg;
		this.actionUrl = actionUrl;
	}
	
	public LoginView(String actionUrl) {
		this.msg = null;
		this.actionUrl = actionUrl;
	}
	
	@Override
	public void render(Html html) throws Exception {
		html.load(Login.class.getResourceAsStream("login.html"));
		html.bindAttr("#loginForm", "action",actionUrl);
		
		
		if(msg != null)
		{
			html.bindValue("#error",msg);
		}
	}
	
}
