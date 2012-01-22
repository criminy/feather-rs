package feather.rs.auth;

/**
 * Interface which provides authentication functionality.
 *
 * @since 0.1
 */
public interface LoginService {

	/**
	 * Attempt a login, given the credentials.
	 * @param Username the username.
	 * @param Password the password.
	 * @param RememberMe true if you want to be remembered, false otherwise.
	 * @throws AuthenticationException on any failure to authenticate.
	 */
	public void login(String username,String password,boolean rememberMe) throws AuthenticationException;
	
}
