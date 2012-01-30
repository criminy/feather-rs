package feather.rs.example.growl.web;

import javax.inject.Named;

import feather.rs.media.AbsoluteClasspathMediaFactory;

@Named
public class ScriptsMediaFactory extends AbsoluteClasspathMediaFactory {

	@Override
	public String getModuleName() {
		// makes the directory go on /media/scripts
		return "scripts";
	}
	
	@Override
	public String getPathToFolder() {
		//loads from src/main/resources/feather.rs.examples.growl.scripts
		return "/feather.rs.examples.growl.scripts";
	}
	
}
