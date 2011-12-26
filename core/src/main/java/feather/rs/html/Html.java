package feather.rs.html;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
