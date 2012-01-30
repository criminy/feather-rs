package feather.rs.example.growl.web;

import javax.inject.Named;

import feather.rs.media.AbsoluteClasspathMediaFactory;

@Named
public class TemplatingMediaFactory extends AbsoluteClasspathMediaFactory {

	@Override
	public String getModuleName() {
		// makes the directory go on /media/growl
		return "growl";
	}
	
	@Override
	public String getPathToFolder() {
		//loads from src/main/resources/feather.rs.examples.growl.theme
		return "/feather.rs.examples.growl.theme";
	}
	
}
