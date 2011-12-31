package feather.rs.media.resources;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import feather.rs.log.Log;
import feather.rs.log.LogFactory;
import feather.rs.media.MediaFactory;

@Named
@Path("/media")
public class MediaResource {

	Log log = LogFactory.getLog(MediaResource.class);
	Map<String,MediaFactory> factories;
	
	@Inject 
	public void setMediaFactory(java.util.List<MediaFactory> mediaFactories)
	{
		factories = new HashMap<String, MediaFactory>();
		for(MediaFactory fact : mediaFactories)
		{
			factories.put(fact.getModuleName(), fact);
		}		
	}
	
	@GET
	@Path("/{module}/{path:.*}")
	public Response open(@PathParam("module") String moduleName,@PathParam("path") String path)
	{				
		String type = factories.get(moduleName).getType(path);
		InputStream file = factories.get(moduleName).getFile(path);		
		return Response.ok(file,type).build();
	}
	
	
}
