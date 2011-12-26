package feather.rs;

import feather.rs.html.Html;

/**
 * The primary interface for implementing feather views.
 * 
 * @author sheenobu
 *
 */
public interface View {

	public void render(Html html) throws Exception;
	
}
