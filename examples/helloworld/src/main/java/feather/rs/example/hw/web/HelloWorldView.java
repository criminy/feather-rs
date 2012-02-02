package feather.rs.example.hw.web;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

import feather.rs.View;
import feather.rs.html.Html;
import feather.rs.html.components.HtmlList;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * The binding view for the hello world page.
 * 
 * @author sheenobu
 * 
 */
public class HelloWorldView implements View {

	private String message;
	private List<String> languages;
	private String currentLang;

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

		HtmlList l = html.list("#languages");
		
		for (String language : languages) {		
			l.put("language", language);			
			if (language.equalsIgnoreCase(currentLang)) {
				l.row(l.template("currentLangTemplate"));
			} else {
				l.row(l.template("langTemplate"));
			}
		}
	}
}
