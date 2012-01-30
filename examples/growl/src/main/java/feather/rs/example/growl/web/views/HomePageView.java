package feather.rs.example.growl.web.views;

import feather.rs.View;
import feather.rs.html.Html;

public class HomePageView implements View {

	@Override
	public void render(Html html) throws Exception {
		html.load(HomePageView.class.getResourceAsStream("home.html"));
	}
	
}
