package feather.rs.auth.shiro11;

import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import feather.rs.auth.AuthenticationException;
import feather.rs.auth.LoginService;

@Named
public class ShiroLoginService implements LoginService
{
	@Override
	public void login(String username, String password, boolean rememberMe)
			throws AuthenticationException {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe);
		try {
			SecurityUtils.getSubject().login(token);
		}catch(org.apache.shiro.authc.AuthenticationException exn)
		{
			throw new AuthenticationException(exn);
		}
	}
}
