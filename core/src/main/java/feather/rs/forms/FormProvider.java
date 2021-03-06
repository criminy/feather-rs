package feather.rs.forms;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;

import feather.rs.conversion.ConverterFactory;
import feather.rs.log.Log;
import feather.rs.log.LogFactory;

@Named
@Provider
public class FormProvider<T> implements MessageBodyReader<Form<T>>{

	Log log = LogFactory.getLog(FormProvider.class);
	
	@Context HttpServletRequest request;
	@Context ContextResolver<ValidatorFactory> validatorContextResolver;
	
	@Inject ConverterFactory converterFactory;
	
	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		if(Form.class.isAssignableFrom(type))
		{
			return true;
		}else{
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Form<T> readFrom(Class<Form<T>> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String,String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		
		Class<T> wrappedJavaBeanType = null;
		
		if(genericType instanceof ParameterizedType)
		{
			Type t = ((ParameterizedType)genericType).getActualTypeArguments()[0];
			wrappedJavaBeanType = (Class<T>) t;
		}
		if(wrappedJavaBeanType == null)
			throw new WebApplicationException(new Exception("No valid parameterized type for Form<?>"));
		
		try {
			T o = wrappedJavaBeanType.newInstance();
		
			if(request.getMethod().equalsIgnoreCase("GET"))
			{
				return new Form<T>(o,false);
			}
					        
		    Validator validator;
			validator = this.validatorContextResolver.getContext(ValidatorFactory.class).getValidator();			
					
			String x = IOUtils.toString(entityStream);
			Map<String, String> fields = formPostToMap(x);	
			
			for(Entry<String, String> e : fields.entrySet())
			{
				Method getter = o.getClass().getMethod("get" + e.getKey() );
				Object val = null;
				
				//TODO: convert string type to valid type
				//TODO: use some converter database
				
				if(getter.getReturnType().equals(Boolean.class) ||  getter.getReturnType().toString().equals("boolean"))
				{				
					//always use true for 'checkboxes' since the existence of it 
					// in the form POST is what 'checked' means.
					val = true;
				}else if(getter.getReturnType().equals(String.class))
				{
					val = e.getValue();
				}else{
					//use converter.
					val = converterFactory.convert(e.getValue(), getter.getReturnType());
				}
				
				if(getter != null) {				
					o.getClass().getMethod("set" + e.getKey(),getter.getReturnType()).invoke(o,val);
				}
			}
			
			Form<T> f = new Form<T>(o,true);
			
			Set<ConstraintViolation<T>> v = validator.validate(o,javax.validation.groups.Default.class);
			
			for(ConstraintViolation<T> vo : v)
			{
				f.setValid(false);
				f.addError(vo.getPropertyPath().toString(), vo.getPropertyPath() + " " + vo.getMessage());
			}
			
			return f;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	
	protected Map<String, String> formPostToMap(String line) throws UnsupportedEncodingException {
	    String[] pairs = line.split("\\&");
	    Map<String, String> map = new HashMap<String, String>();
	    for (int i = 0; i < pairs.length; i++) {
	      String[] fields = pairs[i].split("=");
	      String name = URLDecoder.decode(fields[0], "UTF-8");
	      name = name.substring(0,1).toUpperCase() + name.substring(1);
	      if(fields.length > 1) {	    	  
	    	  String value = URLDecoder.decode(fields[1], "UTF-8");
	    	  map.put(name,value );	    	  
	      }else{
	    	  map.put(name,null);
	      }	     
	    }
	    return map;
	}

}
