package feather.rs.wizard;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WizardData {

	@NotNull
	@Size(min=2,max=50)
	private String wizardId;
	
	@NotNull
	@Size(min=2,max=50)
	private String userId;
		
	@NotNull		
	private SortedSet<Step> stepData = new TreeSet<Step>();
		
	public String getWizardId() {
		return wizardId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public SortedSet<Step> getStepData() {
		return stepData;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setWizardId(String wizardId) {
		this.wizardId = wizardId;
	}
	
}
