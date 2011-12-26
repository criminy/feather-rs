package feather.rs.example.hw.web;

import java.util.List;

import feather.rs.View;
import feather.rs.html.ForEach;
import feather.rs.html.Html;
import feather.rs.html.Item;

/**
 * The binding view for the hello world page.
 * 
 * @author sheenobu
 * 
 */
public class HelloWorldView implements View {

	private String message;
	List<String> languages;
	String currentLang;

	/**
	 * Inject in the required data for this view.
	 * 
	 * @param message
	 * @param languages
	 * @param currentLang
	 */
	public HelloWorldView(String message, List<String> languages,
			String currentLang) {
		this.message = message;
		this.languages = languages;
		this.currentLang = currentLang;
	}

	@Override
	public void render(Html html) throws Exception {
		html.load(HelloWorldView.class.getResourceAsStream("helloworld.html"));
		html.bindValue("#message", message);

		html.forEachValue("#languages", languages, new ForEach<String>() {
			@Override
			public void render(Item<String> item) {
				if (item.getObject().equals(currentLang)) {
					item.setContent(item.getObject());
				} else {
					item.setHtml(String.format("<a href='%s'>%s</a>",
							item.getObject(), item.getObject()));
				}
			}
		});
	}
}
