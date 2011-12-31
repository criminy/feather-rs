package feather.rs.example.templating.web.views;

import java.text.DateFormat;
import java.util.Date;

import feather.rs.View;
import feather.rs.html.Html;

public class PrimaryView implements View{

	View content;
	View sidebar;
	
	public PrimaryView(View content) {
		this.content = content;
		this.sidebar = new SidebarView();
	}
	
	@Override
	public void render(Html html) throws Exception {
		html.load(PrimaryView.class.getClassLoader().getResourceAsStream("/feather.rs.examples.templating.theme/index.html"));
		html.renderTo("#content",content);
		html.renderTo("#sidebar",sidebar);
	}
		
}

class SidebarView implements View
{
	@Override
	public void render(Html html) throws Exception {
		
		html.load(PrimaryView.class.getResourceAsStream("sidebar.html"));
		
		DateFormat df = DateFormat.getDateTimeInstance();
		html.bindValue("#time", df.format(new Date()));		
	}
}