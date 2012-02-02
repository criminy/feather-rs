package feather.rs.html;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import feather.rs.View;
import feather.rs.forms.Form;
import feather.rs.forms.FormBuilder;
import feather.rs.html.components.HtmlList;
import feather.rs.log.Log;
import feather.rs.log.LogFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Primary class for end-developers to interact with the 
 * html templating system of powderj.
 * 
 * @author sheenobu
 *
 */
public class Html {

	Document document;
	Map<String,String> templates = new HashMap<String, String>();
	Log log = LogFactory.getLog(Html.class);
	FormBuilder formBuilder;
	Configuration cfg;
	
	/**
	 * Set the form builder class.
	 * @param formBuilder
	 */
	public void setFormBuilder(FormBuilder formBuilder) {
		this.formBuilder = formBuilder;
	}
	
	/**
	 * Get the underlying JSoup {@link Document} instance backing this {@link Html} object.
	 * @return The {@link Document} instance.
	 */
	public Document getDocument() {
		return document;
	}
	
	/**
	 * Converts the extended protocol name (HTTP/1.X, HTTPS/1.2) to its shortened
	 * form.
	 * @param proto The protocol string
	 * @return The {@link String} http or https.
	 */
	protected String convertProtocol(String proto){
		if(proto.toLowerCase().contains("https"))
		{
			return "https";		
		}else{
			return "http";
		}
	}
	
	/**
	 * Render a {@link Form} object on the elements specified by the given CSS selector.
	 * @param cssSelector The CSS selector expression.
	 * @param form THe {@link Form} object.
	 */
	public  <T>  void form(String cssSelector,Form<T> form)
	{
		formBuilder.renderFormUsingP(this,cssSelector,form);
	}
	
	/**
	 * Process the HTML content and update invalid absolute links to
	 * absolute links containing protocol, hostname, port, etc.
	 * 
	 * Conversion scheme:
	 * 
	 *  /a/someURl => http(s)://hostname(:optional-port)/context-path/a/someUrl
	 * 
	 * @param req The servlet request object.
	 */
	public void updateLinks(HttpServletRequest req){
		Elements ex = document.select("a");
		Elements ex2 = document.select("link");
		Elements ex3 = document.select("form");
		Elements ex4 = document.select("img");
		for(Element e : ex)
		{
			updateLink(req, e);
		}
		for(Element e : ex2)
		{
			updateLink(req, e);
		}
		for(Element e : ex3)
		{
			updateLink(req, e);
		}
		for(Element e : ex4)
		{
			updateLink(req, e);
		}
	}
	
	/**
	 * Process the given {@link Element} object, updating its invalid absolute
	 * links to complete links.
	 * @param req The {@link HttpServletRequest} request object.
	 * @param e The {@link Element} to process.
	 */
	protected void updateLink(HttpServletRequest req, Element e)
	{		
		if((e.tagName().equalsIgnoreCase("a") || e.tagName().equalsIgnoreCase("link")) && 
			e.attr("href").startsWith("/"))
		{
			if( (req.getServerPort() == 80 && req.getProtocol().matches("HTTP/.*")) || 
				(req.getServerPort() == 443 && req.getProtocol().matches("HTTPS/.*")))
			{					
				e.attr("href", 
						String.format("%s://%s%s%s",
								convertProtocol(req.getProtocol()),
								req.getServerName(),
								req.getContextPath(),
								e.attr("href")));
			}else{
				e.attr("href", 
						String.format("%s://%s:%s%s%s",
								convertProtocol(req.getProtocol()),
								req.getServerName(),
								Integer.toString(req.getServerPort()),
								req.getContextPath(),
								e.attr("href")));
			}
		}else if(e.tagName().equalsIgnoreCase("form") && e.attr("action").startsWith("/"))  {
			if( (req.getServerPort() == 80 && req.getProtocol().matches("HTTP/.*")) || 
					(req.getServerPort() == 443 && req.getProtocol().matches("HTTPS/.*")))
				{					
					e.attr("action", 
							String.format("%s://%s%s%s",
									convertProtocol(req.getProtocol()),
									req.getServerName(),
									req.getContextPath(),
									e.attr("action")));
				}else{
					e.attr("action", 
							String.format("%s://%s:%s%s%s",
									convertProtocol(req.getProtocol()),
									req.getServerName(),
									Integer.toString(req.getServerPort()),
									req.getContextPath(),
									e.attr("action")));
				}
			
		}else if(e.tagName().equalsIgnoreCase("img") && e.attr("src").startsWith("/"))  {
			if( (req.getServerPort() == 80 && req.getProtocol().matches("HTTP/.*")) || 
					(req.getServerPort() == 443 && req.getProtocol().matches("HTTPS/.*")))
				{					
					e.attr("src", 
							String.format("%s://%s%s%s",
									convertProtocol(req.getProtocol()),
									req.getServerName(),
									req.getContextPath(),
									e.attr("src")));
				}else{
					e.attr("src", 
							String.format("%s://%s:%s%s%s",
									convertProtocol(req.getProtocol()),
									req.getServerName(),
									Integer.toString(req.getServerPort()),
									req.getContextPath(),
									e.attr("src")));
				}
			
		}
	}
	
	/**
	 * Render the given {@link View} to the CSS selector expression.
	 * @param cssSelector The CSS selector expression.
	 * @param view THe {@link View} object.
	 * @throws Exception On any errors rendering the sub {@link View}.
	 */
	public void renderTo(String cssSelector, View view) throws Exception
	{
		Html newHtml = new Html();
		newHtml.setFormBuilder(formBuilder);
		view.render(newHtml);
		Elements ex = document.select(cssSelector);
	
		for (Element e : ex) {
			e.empty();
			e.append(newHtml.document.html());
		}
	}
	
	/**
	 * Load a {@link InputStream} into the document object, as HTML.
	 * 
	 * @param is The {@link InputStream} object.
	 * @throws IOException
	 */
	public void load(InputStream is) throws IOException {		
		log.warn("Use baseUri for parsing");
		document = Jsoup.parse(is, "UTF-8","/");		
		findTemplates();		
	}
	
	/**
	 * Load the {@link String} into the {@link Document} object, as HTML.
	 * @param content
	 * @throws IOException
	 */
	public void loadFromString(String content) throws IOException {	
		log.warn("Use baseUri for parsing");
		load(IOUtils.toInputStream(content));
	}

	/**
	 * For every matched element in the CSS Selector, set the content string
	 * to the given string, escaping the HTML.
	 * 
	 * @param cssSelector THe CSS selector expression.
	 * @param content The content {@link String}.
	 */
	public void bindValue(String cssSelector, String content) {		
		Elements ex = document.select(cssSelector);
		for (Element e : ex) {
			e.text(content);
		}
	}
	
	/**
	 * Find and process any template within the loaded DOM.
	 */
	protected void findTemplates() {
		for(Element e : document.select("[template]"))
		{
			Element cl = e.clone();			
			templates.put(e.id(),cl.toString());
			e.remove();
		}
	}
	

	/**
	 * Render a template, returning the populated {@link String}.
	 * @param templateId The template ID.
	 * @param context The Variable context for the template.
	 * @return The populated template {@link String}
	 * @throws IOException If the template can't be loaded.
	 * @throws TemplateException If the template has a syntax error.
	 * @see Template
	 */
	public String renderTemplate(String templateId,
			Map<String,Object> context) throws IOException, TemplateException {		
		String templateStr = templates.get(templateId);	
		Template t = new Template(templateId,new StringReader(templateStr),cfg);
		StringWriter wr = new StringWriter();
		t.process(context,wr);
		return wr.toString();
	}
	
	/**
	 * Use the given css selector expression to build
	 * an HtmlList object, capable of operating on Ul,Li, and other HTML list tags.
	 * 
	 * @param cssSelector The CSS selector expression.
	 * @return The {@link HtmlList} object, or NULL if the css selector did not find a matching element.
	 */
	public HtmlList list(String cssSelector) {		
		//TODO:verify we find one, return null if we don't.
		Element e = this.document.select(cssSelector).get(0);		
		HtmlList l = new HtmlList(this,e);
		return l;
	}
	

}
