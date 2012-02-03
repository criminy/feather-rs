package feather.rs.forms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import feather.rs.forms.Form;
import feather.rs.html.components.SelectItem;


public abstract class FormRenderer<T> {

	public void setFields(Map<String, FieldNameTypePair> fields) {
		this.fields = fields;
	}
	
	public void setFormObject(Form<T> formObject) {
		this.formObject = formObject;
	}
	
	public void setHtmlForm(StringBuffer htmlForm) {
		this.htmlForm = htmlForm;
	}
	
	public void setO(Object o) {
		this.o = o;
	}
	
	public T getObject() {
		return formObject.getObject();
	}
	
	
	Map<String,FieldNameTypePair> fields;
	Form<T> formObject;
	StringBuffer htmlForm = new StringBuffer();
	Object o;
	
	public void hidden(String fieldName) {
		FieldNameTypePair field = fields.get(fieldName);
		String val;
		try {
			// TODO: use /is/ for bool values and /get/ for all others.
			Object origVal = o
					.getClass()
					.getMethod(
							"get" + fieldName.substring(0, 1).toUpperCase()
									+ fieldName.substring(1)).invoke(o);
			if (origVal == null)
				val = "";
			else
				val = origVal.toString(); // TODO: use system converters
		} catch (Exception e) {
			throw new RuntimeException(e);// todo: use a custom exception type
		}

		String err = "";
		if (formObject != null) {
			err = formObject.getError(fieldName.substring(0, 1).toLowerCase()
					+ fieldName.substring(1));
			err = (err == null ? "" : err);
		}

		htmlForm.append(String.format(
				"<input name='%s' id='%s' type='%s' value='%s' />", fieldName,
				fieldName, "hidden",  val == null ? "" : val, err));

	}
	
	protected static Method getGetter(Class<?> clazz,String property) {
		for(Method m : clazz.getMethods())
		{
			if(m.getName().equals("get" + property.substring(0,1).toUpperCase() + property.substring(1)))
			{
				return m;
			}
		}
		return null;
	}
	
	protected static Object getValue(Object o,String property) {
		for(Method m : o.getClass().getMethods())
		{
			if(m.getName().equals("get" + property.substring(0,1).toUpperCase() + property.substring(1)))
			{
				try {
					return m.invoke(o);
				} catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}
	
	
	public void group(String title){
		
	}
	
	
	public <T> void selectOne(String fieldName,String longName,Collection<T> coll,SelectItem.Processor<T> processor)
	{
		FieldNameTypePair field = fields.get(fieldName);
		String val;
		try {
			// TODO: use /is/ for bool values and /get/ for all others.
			Object origVal = o
					.getClass()
					.getMethod(
							"get" + fieldName.substring(0, 1).toUpperCase()
									+ fieldName.substring(1)).invoke(o);
			if (origVal == null)
				val = "";
			else
				val = origVal.toString(); // TODO: use system converters
		} catch (Exception e) {
			throw new RuntimeException(e);// todo: use a custom exception type
		}

		String err = "";
		if (formObject != null) {
			err = formObject.getError(fieldName.substring(0, 1).toLowerCase()
					+ fieldName.substring(1));
			err = (err == null ? "" : err);
		}

		
		//"hidden", val == null ? "" : val, err));
		htmlForm.append(String.format(
				"<p><label>%s:</label><select name='%s' id='%s'>", 
				longName,fieldName,fieldName));
					
		for(T o : coll) {
			SelectItem item = new SelectItem();
			processor.process(o,item);
			htmlForm.append(String.format("<option value='%s' %s>%s</option>",item.getValue(),item.isSelected() ? "selected='selected'" : "",item.getLabel()));
		}
		
		htmlForm.append("</select></p>");				
	}
	
	public void text(String fieldName,String longName){
		
		FieldNameTypePair field = fields.get(fieldName);
		
		if(field == null) throw new NullPointerException("FormRenderer: Can't find field " + fieldName);
		
          String val;
          try {
                  //TODO: use /is/ for bool values and /get/ for all others.
                  Object origVal = o.getClass().getMethod("get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1)).invoke(o);
                  if(origVal == null)
                          val  = "";
                  else
                          val = origVal.toString(); //TODO: use system converters                                 
          } catch (Exception e) {
                  throw new RuntimeException(e);//todo: use a custom  exception type
          }

          String err = "";
          if(formObject != null) {
                  err = formObject.getError( fieldName.substring(0,1).toLowerCase() + fieldName.substring(1));
                  err = (err == null ? "" : err);
          }


          htmlForm.append(
                          String.format("<p><label>%s:</label><input name='%s' id='%s' type='text' value='%s'/><br/><span class='error'>%s</span></p>\n",
                        		  longName,
                                  fieldName,
                                  fieldName,
                                          val == null ? "" : val,
                                                  err
          ));

	}

	
	public void password(String fieldName,String longName){
		
		FieldNameTypePair field = fields.get(fieldName);
		
		if(field == null) throw new NullPointerException("FormRenderer: Can't find field " + fieldName);
		
          String val;
          try {
                  //TODO: use /is/ for bool values and /get/ for all others.
                  Object origVal = o.getClass().getMethod("get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1)).invoke(o);
                  if(origVal == null)
                          val  = "";
                  else
                          val = origVal.toString(); //TODO: use system converters                                 
          } catch (Exception e) {
                  throw new RuntimeException(e);//todo: use a custom  exception type
          }

          String err = "";
          if(formObject != null) {
                  err = formObject.getError( fieldName.substring(0,1).toLowerCase() + fieldName.substring(1));
                  err = (err == null ? "" : err);
          }


          htmlForm.append(
                          String.format("<p><label>%s:</label><input name='%s' id='%s' type='password' '%s'/><br/><span class='error'>%s</span></p>\n",
                        		  longName,
                                  fieldName,
                                  fieldName,
                                  (val == null || val.equalsIgnoreCase("false")) ? "" : "value='1'",                                          
                                   err
          ));

	}
	
	public void checkbox(String fieldName,String longName){
		
		FieldNameTypePair field = fields.get(fieldName);
		
		if(field == null) throw new NullPointerException("FormRenderer: Can't find field " + fieldName);
		
          String val;
          try {
                  //TODO: use /is/ for bool values and /get/ for all others.
                  Object origVal = o.getClass().getMethod("get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1)).invoke(o);
                  if(origVal == null)
                          val  = "";
                  else
                          val = origVal.toString(); //TODO: use system converters                                 
          } catch (Exception e) {
                  throw new RuntimeException(e);//todo: use a custom  exception type
          }

          String err = "";
          if(formObject != null) {
                  err = formObject.getError( fieldName.substring(0,1).toLowerCase() + fieldName.substring(1));
                  err = (err == null ? "" : err);
          }


          htmlForm.append(
                          String.format("<p><label>%s:</label><input name='%s' id='%s' type='checkbox' value='%s'/><br/><span class='error'>%s</span></p>\n",
                        		  longName,
                                  fieldName,
                                  fieldName,
                                          val == null ? "" : val,
                                                  err
          ));

	}
	
	
	public abstract void run();
	
	
}
