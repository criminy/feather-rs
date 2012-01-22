package feather.rs.example.secured;

import java.security.Principal;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import feather.rs.View;
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
	public View getPublicPage(@Context SecurityContext context) {
		return new PublicPageView(context.getUserPrincipal());
	}
	
	/**
	 * The private secured view
	 * @param context The SecurityContext
	 * @return The view object
	 */
	//secured elsewhere
	@GET
	@Path("/secured")
	public View getSecuredPage(@Context SecurityContext context) {
		return new SecuredPageView(context.getUserPrincipal());
	}
	
}

/**
 * The public page HTML view
 * @author sheenobu
 *
 */
class PublicPageView implements View
{

	Footer footer;
	public PublicPageView(Principal principal) {
		this.footer = new Footer(principal);
	}
	
	
	@Override
	public void render(Html html) throws Exception {
		html.load(Application.class.getResourceAsStream("public.html"));
		html.renderTo("#footer",footer);
	}
}

/**
 * The private page HTML view.
 * @author sheenobu
 *
 */
class SecuredPageView implements View
{
	Footer footer;
	public SecuredPageView(Principal principal) {
		this.footer = new Footer(principal);
	}
	
	
	@Override
	public void render(Html html) throws Exception {
		html.load(Application.class.getResourceAsStream("secured.html"));
		html.renderTo("#footer",footer);
	}
}


/**
 * The common footer between both pages.
 * @author sheenobu
 *
 */
class Footer implements View
{
	Principal principal;
	
	public Footer(Principal principal) {
		this.principal = principal;
	}
	
	@Override
	public void render(Html html) throws Exception {		
		// perform different behaviors depending on whether we are logged in our not.		
		if(principal == null)
		{
			html.load(Application.class.getResourceAsStream("publicFooter.html"));
		}else{
			html.load(Application.class.getResourceAsStream("securedFooter.html"));
		}
	}
	
}
