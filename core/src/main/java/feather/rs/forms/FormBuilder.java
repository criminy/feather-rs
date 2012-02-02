package feather.rs.forms;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import feather.rs.html.Html;

class FieldNameTypePair {
	private String fieldName;
	private Class<?> fieldType;
	
	public String getFieldName() {
		return fieldName;
	}
	
	public Class<?> getFieldType() {
		return fieldType;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	public FieldNameTypePair(String fieldName, Class<?> fieldType) {
		super();
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}
	
	
}

@Named
public class FormBuilder {

	public void renderFormUsingP(Html html,String cssSelector,Form<?> formObject)
	{
		List<FieldNameTypePair> fields = new ArrayList<FieldNameTypePair>();
		
		Object o = formObject.getObject();
		
		Method[] mList = o.getClass().getMethods();
		for(Method m : mList)
		{
			if(m.getName().startsWith("set"))
			{				
				fields.add(new FieldNameTypePair(m.getName().substring(3),m.getParameterTypes()[0]));
				//fieldNames.add(m.getName().substring(3));
			}
		}
		
		StringBuffer htmlForm = new StringBuffer();
		
		for(FieldNameTypePair field : fields)
		{
			String s = field.getFieldName();
			String val;
			try {
				//TODO: use /is/ for bool values and /get/ for all others.
				Object origVal = o.getClass().getMethod("get" + s).invoke(o);
				if(origVal == null) 
					val  = "";
				else
					val = origVal.toString(); //TODO: use system converters					
			} catch (Exception e) {
				throw new RuntimeException(e);//todo: use a custom  exception type
			}
					
			String err = "";
			if(formObject != null) {				
				err = formObject.getError( s.substring(0,1).toLowerCase() + s.substring(1));
				err = (err == null ? "" : err);
			}
			
			if(field.getFieldType().toString().equals("boolean") || field.getFieldType().equals(Boolean.class))
			{
				if(val.equalsIgnoreCase("true"))
					val = "checked='true';";
				else
					val = "";							
				htmlForm.append(
						String.format("<p><label>%s:</label><input name='%s' id='%s' type='checkbox' %s /><br/><span class='error'>%s</span></p>\n",
								s,
								s,
								s,
								val == null ? "" : val,
								err));
			}else{
			
				htmlForm.append(
						String.format("<p><label>%s:</label><input name='%s' id='%s' type='text' value='%s'/><br/><span class='error'>%s</span></p>\n",
								s,
								s,
								s,
								val == null ? "" : val,
									err
				));
			}
		}
		
		html.getDocument().select(cssSelector).html(htmlForm.toString());		
	}
	
}
