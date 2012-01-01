package feather.rs.auth;

public class AuthenticationException extends Exception {

	private static final long serialVersionUID = 1L;

	public AuthenticationException(Exception exn) {
		super(exn);
	}

}
