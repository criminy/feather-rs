package feather.rs.validation;

import javax.inject.Named;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Standard provider for a javax.validation.ValidatorFactory which uses Validation.buildDefaultValidatorFactory.
 *
 * If you want to override the ValidatorFactory, just implement a new ContextResolver<ValidatorFactory> and
 * place it on the classpath and dependency injection context.
 */
@Named
@Provider
public class ValidatorProvider implements ContextResolver<ValidatorFactory>{
	@Override
	public ValidatorFactory getContext(Class<?> arg0) {
		return Validation.buildDefaultValidatorFactory();
	}
}
