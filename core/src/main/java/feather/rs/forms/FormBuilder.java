package feather.rs.forms;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import feather.rs.html.Html;



@Named
public class FormBuilder {

	public <T> void  renderFormUsingP(Html html,String cssSelector,Form<T> formObject,FormRenderer<T> renderer)
	{
		Map<String,FieldNameTypePair> fields = new HashMap<String,FieldNameTypePair>();
		
		Object o = formObject.getObject();
		
		Method[] mList = o.getClass().getMethods();
		for(Method m : mList)
		{
			if(m.getName().startsWith("set"))
			{				
				String name = m.getName().substring(3).substring(0,1).toLowerCase() + m.getName().substring(3).substring(1);
				fields.put(name,new FieldNameTypePair(name,m.getParameterTypes()[0]));
			}
		}
		
		
		StringBuffer htmlForm = new StringBuffer();
		
		renderer.setO(formObject.getObject());
		renderer.setFields(fields);
		renderer.setHtmlForm(htmlForm);
		renderer.setFormObject(formObject);
		
		renderer.run();
//		
//		for(FieldNameTypePair field : fields.values())
//		{
//			String s = field.getFieldName();
//			String val;
//			try {
//				//TODO: use /is/ for bool values and /get/ for all others.
//				Object origVal = o.getClass().getMethod("get" + s).invoke(o);
//				if(origVal == null) 
//					val  = "";
//				else
//					val = origVal.toString(); //TODO: use system converters					
//			} catch (Exception e) {
//				throw new RuntimeException(e);//todo: use a custom  exception type
//			}
//					
//			String err = "";
//			if(formObject != null) {				
//				err = formObject.getError( s.substring(0,1).toLowerCase() + s.substring(1));
//				err = (err == null ? "" : err);
//			}
//			
//			if(field.getFieldType().toString().equals("boolean") || field.getFieldType().equals(Boolean.class))
//			{
//				if(val.equalsIgnoreCase("true"))
//					val = "checked='true';";
//				else
//					val = "";							
//				htmlForm.append(
//						String.format("<p><label>%s:</label><input name='%s' id='%s' type='checkbox' %s /><br/><span class='error'>%s</span></p>\n",
//								s,
//								s,
//								s,
//								val == null ? "" : val,
//								err));
//			}else{
//			
//				htmlForm.append(
//						String.format("<p><label>%s:</label><input name='%s' id='%s' type='text' value='%s'/><br/><span class='error'>%s</span></p>\n",
//								s,
//								s,
//								s,
//								val == null ? "" : val,
//									err
//				));
//			}
//		}
//		
		html.getDocument().select(cssSelector).html(htmlForm.toString());		
	}
	
}
