package feather.rs.html.components;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;

import feather.rs.html.Html;
import freemarker.template.TemplateException;

/**
 * Front end class for working with HTML list elements (ul, li, ol, or any parent element 
 * which contains a repeated child element tag). This class actually does very little, 
 * it is just a wrapper for easy representation of operations.
 * 
 * @author sheenobu
 *
 */
public class HtmlList {

	public HtmlList(Html html, Element wrapped) {
		this.html = html;
		this.wrappedElement = wrapped;
	}
	
	private Map<String,Object> renderContext = new HashMap<String, Object>();
	private Html html;
	private Element wrappedElement;
	
	public void put(String key,Object val) { renderContext.put(key,val); }
	public Object get(String key) { return renderContext.get(key); }
	public void clear() { renderContext.clear(); }
	
	public String template(String templateId) throws IOException, TemplateException {
		return html.renderTemplate(templateId,renderContext);
	}
	
	public void row(String rowContent) {
		wrappedElement.append(rowContent);
	}

}
