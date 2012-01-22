package feather.rs.auth.resources;

import javax.validation.constraints.*;

/**
 * Form object for logging in to the system.
 */
public class LoginForm {

	@NotNull
	private String username;

	@NotNull
	private String password;

	private boolean rememberMe;


	public String getUsername() { return this.username; }
	public String getPassword() { return this.password; }
	public boolean isRememberMe() { return this.rememberMe; }
	public boolean getRememberMe() { return this.rememberMe; } //TODO: fix is lookup in formbuilder

	public void setUsername(String arg) { this.username = arg; }
	public void setPassword(String arg) { this.password = arg; }
	public void setRememberMe(boolean arg) { this.rememberMe = arg; }

}
