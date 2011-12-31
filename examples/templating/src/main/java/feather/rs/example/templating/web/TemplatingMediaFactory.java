package feather.rs.example.templating.web;

import javax.inject.Named;

import feather.rs.media.AbsoluteClasspathMediaFactory;

@Named
public class TemplatingMediaFactory extends AbsoluteClasspathMediaFactory {

	@Override
	public String getModuleName() {
		// makes the directory go on /media/templating
		return "templating";
	}
	
	@Override
	public String getPathToFolder() {
		//loads from src/main/resources/feather.rs.examples.templating.theme
		return "/feather.rs.examples.templating.theme";
	}
	
}
