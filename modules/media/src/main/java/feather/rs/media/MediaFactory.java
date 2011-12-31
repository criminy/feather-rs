package feather.rs.media;

import java.io.InputStream;

/**
 * Factory interface used by module and application implementors
 * who which to expose static media resources through feather-rs.
 * 
 * @see AbsoluteClasspathMediaFactory
 * @author sheenobu
 *
 */
public interface MediaFactory {

	/**
	 * Get the streaming type (text/css, media/jpg, etc) of the path.
	 * 
	 * @param path The file path.
	 * @return The streaming type.
	 */
	public String getType(String path);
	
	/**
	 * Get the inputstream of the given path.
	 * 
	 * @param path The file path.
	 * @return The input stream.
	 */
	public InputStream getFile(String path);
	
	/**
	 * Get the name of the module this media factory implements, used as a subdirectory of the /media root.
	 * 
	 * @return the module name.
	 */
	public String getModuleName();
	
}
