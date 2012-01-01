package feather.rs.forms;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import feather.rs.html.Html;

@Named
public class FormBuilder {

	public void renderFormUsingP(Html html,String cssSelector,Form formObject)
	{
		List<String> fieldNames = new ArrayList<String>();
		
		Object o = formObject.getObject();
		
		Method[] mList = o.getClass().getMethods();
		for(Method m : mList)
		{
			if(m.getName().startsWith("set"))
			{				
				fieldNames.add(m.getName().substring(3));
			}
		}
		
		StringBuffer htmlForm = new StringBuffer();
		
		for(String s : fieldNames)
		{
			String val;
			try {
				val = (String) o.getClass().getMethod("get" + s).invoke(o);
			} catch (Exception e) {
				throw new RuntimeException(e);//todo: use a custom  exception type
			}
			
			
			String err = "";
			if(formObject != null) {
				err = formObject.getError( s.substring(0,1).toLowerCase() + s.substring(1));
				err = (err == null ? "" : err);
			}
			
			htmlForm.append(
				String.format("<p><label>%s:</label><input name='%s' id='%s' type='text' value='%s'/><br/><span class='error'>%s</span></p>\n",
					s,
					s,
					s,
					val == null ? "" : val,
					err
				));
		}
		
		html.html(cssSelector,htmlForm.toString());
	}
	
}
