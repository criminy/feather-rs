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

/**
 * Default media resource JAX-RS endpoint
 */
@Named
@Path("/media")
public class MediaResource {

	Log log = LogFactory.getLog(MediaResource.class);
	Map<String,MediaFactory> factories;
	
	@Inject 
	public void setMediaFactory(java.util.List<MediaFactory> mediaFactories)
	{
		//convert for easy indexing of our media factories based on moduleName.
		factories = new HashMap<String, MediaFactory>();
		for(MediaFactory fact : mediaFactories)
		{
			factories.put(fact.getModuleName(), fact);
		}		
	}
	
	/**
	 * read the given filename, using the moduleName as the search base.
	 * @param moduleName The name of the base module.
	 * @param path The extended path of the filename
	 * @return When found, a Response object of 200 OK with the content-type and file contents.
	 * @throws Exception when the file is not found.
	 */
	@GET
	@Path("/{module}/{path:.*}")
	public Response open(@PathParam("module") String moduleName,@PathParam("path") String path)
	{				
		String type = factories.get(moduleName).getType(path);
		InputStream file = factories.get(moduleName).getFile(path);		
		return Response.ok(file,type).build();
	}
	
	
}
