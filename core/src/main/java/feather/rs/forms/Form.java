package feather.rs.forms;

import java.util.HashMap;
import java.util.Map;

import feather.rs.log.Log;
import feather.rs.log.LogFactory;

public class Form<T> {

	T t;
	
	
	public Form(T t) {
		this.valid = false;
		this.t = t;
	}
	
	
	public Form(T t,boolean valid) {
		this.valid = valid;
		this.t = t;
	}
	
	public T getObject() {
		return t;
	}
	
	private boolean valid;	
	
	public boolean isValid() {
		return valid;
	}
	
	public boolean isValidList(String... fields)
	{
		for(String s : fields)
		{
			if(this.errors.containsKey(s)) return false;			
		}
		return true;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	Map<String,String> errors = new HashMap<String, String>();
	
	Log log = LogFactory.getLog(Form.class);
	
	public void addError(String fieldName, String err)
	{
		log.info("putting " + fieldName + " " + err);
		errors.put(fieldName,err);
	}
	
	public String getError(String fieldName)
	{
		log.info("getting " + fieldName);
		return errors.get(fieldName);
	}
	
}
