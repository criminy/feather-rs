package feather.rs.media;

import java.io.InputStream;

/**
 * Abstract {@link MediaFactory} implementation which uses
 * the classloader (AbsoluteClasspathMediaFactory.class.getClassLoader().getResourceAsStream)
 * to load the file inputstream.
 *   
 * @author sheenobu
 *
 */
public abstract class AbsoluteClasspathMediaFactory implements MediaFactory {
	
	public abstract String getPathToFolder();
	
	@Override
	public String getType(String path) {
		{
			String pathLower = path.toLowerCase();
			if(pathLower.endsWith("jpg") || pathLower.endsWith("jpeg"))
			{
				return "media/jpeg";
			}
			if(pathLower.endsWith("png"))
			{
				return "media/png";
			}
			if(pathLower.endsWith("gif"))
			{
				return "media/gif";
			}
			if(pathLower.endsWith("ico"))
			{
				return "media/ico";
			}
			if(pathLower.endsWith("css"))
			{
				return "text/css";
			}
			if(pathLower.endsWith("js"))
			{
				return "text/javascript";
			}
			if(pathLower.endsWith("html"))
			{
				return "text/html";
			}
			if(pathLower.endsWith("xml"))
			{
				return "text/xml";
			}
			if(pathLower.endsWith("json"))
			{
				return "text/xml";
			}
			return "text/plain";
		}					
	}
	
	@Override
	public InputStream getFile(String path) {
		return AbsoluteClasspathMediaFactory.class.getClassLoader().getResourceAsStream(
			this.getPathToFolder() + "/" + path);
	}
	
}
