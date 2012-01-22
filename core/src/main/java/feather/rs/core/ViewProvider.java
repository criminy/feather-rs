package feather.rs.core;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import feather.rs.View;
import feather.rs.forms.FormBuilder;
import feather.rs.html.Html;
import feather.rs.log.Log;
import feather.rs.log.LogFactory;

/**
 * The provider which is responsible for converting and writing the {@link View} objects
 * to the Servlet container.
 * 
 * @author sheenobu
 */
@Named
@Provider
@Produces("text/html")
public class ViewProvider implements MessageBodyWriter<View>{
	
	Log log = LogFactory.getLog(ViewProvider.class);
	
	@Context HttpServletRequest request;
	
	@Inject
	FormBuilder formBuilder;
	
	@Override
	public long getSize(View arg0, Class<?> arg1, Type arg2, Annotation[] arg3,
			MediaType arg4) {
		log.warn("Size is always -1, need to implement size calculation");
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2,
			MediaType arg3) {				
		if(View.class.isAssignableFrom(arg0))
			return true;
		else
			return false;
			
	}

	@Override
	public void writeTo(View arg0, Class<?> arg1, Type arg2, Annotation[] arg3,
			MediaType arg4, MultivaluedMap<String, Object> arg5,
			OutputStream arg6) throws IOException, WebApplicationException {
		
		Html html = new Html();
		html.setFormBuilder(formBuilder);
		
		try {
			arg0.render(html);
		} catch (Exception e) {
			throw new IOException(e);
		}
		
		html.updateLinks(request);
		
		
		arg6.write(html.getDocument().html().getBytes());
		arg6.flush();
		arg6.close();		
	}

}
