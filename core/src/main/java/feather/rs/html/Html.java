package feather.rs.html;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feather.rs.View;

/**
 * Primary class for end-developers to interact with the 
 * html templating system of powderj.
 * 
 * @author sheenobu
 *
 */
public class Html {

	Document document;

	Logger log = LoggerFactory.getLogger(Html.class);

	public Document getDocument() {
		return document;
	}
	
	protected String convertProtocol(String proto){
		if(proto.toLowerCase().contains("https"))
		{
			return "https";		
		}else{
			return "http";
		}
	}
	
	public void updateLinks(HttpServletRequest req){
		Elements ex = document.select("a");
		Elements ex2 = document.select("link");
		for(Element e : ex)
		{
			updateLink(req, e);
		}
		for(Element e : ex2)
		{
			updateLink(req, e);
		}
	}
	
	protected void updateLink(HttpServletRequest req, Element e)
	{
		if(e.attr("href").startsWith("/"))
		{
			//is a relative URL
			
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
		}	
	}
	
	public void renderTo(String cssSelector, View view) throws Exception
	{
		Html newHtml = new Html();
		view.render(newHtml);
		Elements ex = document.select(cssSelector);
	
		for (Element e : ex) {
			e.empty();
			e.append(newHtml.document.html());
		}
	}
	
	/**
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public void load(InputStream is) throws IOException {		
		log.warn("Use baseUri for parsing");
		document = Jsoup.parse(is, "UTF-8","/");
	}
	
	public void loadFromString(String content) throws IOException {	
		log.warn("Use baseUri for parsing");
		document = Jsoup.parse(content);
	}

	public void bindValue(String cssSelector, String content) {
		Elements ex = document.select(cssSelector);
		for (Element e : ex) {
			e.text(content);
		}
	}

	public <T> void forEachValue(String cssSelector, Iterable<T> iter,
			ForEach<T> foreach) {
		Elements ex = document.select(cssSelector);
		for (Element e : ex) {
			Element repeatedChild = e.children().first();	
			
			for(Element ch : e.children()) ch.remove();		
					
			for (T t : iter) 
			{			
				Element newChild = new Element(repeatedChild.tag(),repeatedChild.baseUri());
				//newChild.text(repeatedChild.text());
				newChild.attributes().addAll(repeatedChild.attributes());
				
				for(Node child : repeatedChild.childNodes())
				{
					newChild.appendChild(child.clone());	
				}
				
				
				
				Item<T> i = new Item<T>(t,newChild);				
				foreach.render(i);				
				e.appendChild(newChild);
			}			
		}
	}

}
