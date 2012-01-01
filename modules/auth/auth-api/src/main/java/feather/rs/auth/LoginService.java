package feather.rs.auth;

public interface LoginService {

	public void login(String username,String password,boolean rememberMe) throws AuthenticationException;
	
}
