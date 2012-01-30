package feather.rs.wizard;

import feather.rs.forms.Form;

/**
 * An implementation of the {@link Step} interface
 * which wraps a {@link Form} object as a single step.
 * 
 * @author artripa
 *
 */
public class FormStep<T> extends Step {

	
	Form<T> form;
	
	public FormStep(String stepId,Form<T> form) {
		super(stepId);
		this.form = form;
	}
	
}
