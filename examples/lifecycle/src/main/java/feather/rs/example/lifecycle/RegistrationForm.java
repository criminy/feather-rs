package feather.rs.example.lifecycle;

import javax.validation.constraints.*;

/**
 * A registration form object.
 */
public class RegistrationForm {
	
	public RegistrationForm() {
		System.out.println("constructing registration form from invoke");
	}

	protected RegistrationForm(boolean a) {

	}


	public static RegistrationForm construct() {
		System.out.println("constructing registration form from code");
		return new RegistrationForm(true);
	}

	@NotNull
	@Size(min=8,max=15)
	private String username;
	
	@NotNull
	@Size(min=8,max=50)
	private String password;

	@NotNull
	@Size(min=8,max=50)	
	private String password2;
	
	@NotNull
	@Size(min=2,max=150)
	private String firstName;
	
	@NotNull
	@Size(min=2,max=150)
	private String lastName;
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPassword2() {
		return password2;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return String.format("Registration form: %s %s %s %s %s",
			getFirstName(),
			getLastName(),
			getPassword(),
			getPassword2(), 
			getUsername());		
	}
	
}
