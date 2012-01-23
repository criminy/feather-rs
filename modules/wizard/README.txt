The wizard module is a module which provides a form wizard API and facilities. Wizard
information can be stored in the session and/or a database, and aggregated for
the end of the wizard actions.

The Wizard class defines methods for the wizard lifecycle, 'onStart' for the creation of a 
wizard, 'onNext' for the next button of a wizard, 'onCancel' when the wizard is cancelled,
and 'onFinish' when the wizard completes.

@Named
@Path("/wizard");
public class MyWizard extends Wizard
{
	public Response onStart() {
		return toStep(new Step1());
	}

	public Response onNext(Step step) {
		if(step instanceof Step1) {
			return toStep(new Step2() );

		}
		if(step instanceof Step2) {
			return toLastStep(new Step3());
		}
		if(step instanceOf Step3) {
			return toFinish();
		}
		return toError();
	}

	public Response onCancel(List<Step> steps) {
		
	}
	public Response onFinish(List<Step> steps) {

	}

}


