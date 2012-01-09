package feather.rs.validation;

import javax.inject.Named;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Named
@Provider
public class ValidatorProvider implements ContextResolver<ValidatorFactory>{
	@Override
	public ValidatorFactory getContext(Class<?> arg0) {
		return Validation.buildDefaultValidatorFactory();
	}
}
