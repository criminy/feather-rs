package feather.rs.html;

import org.jsoup.nodes.Element;

/**
 * An iteration item in a {@link ForEach} object.
 * @author sheenobu
 *
 * @param <T>
 */
public class Item<T> {

	T object;
	Element e;
	
	public T getObject(){
		return object;
	}
	
	public void setHtml(String html){
		e.text("");		
		e.append(html);
	}
	
	public void setContent(String value){
		e.text(value);
	}
	
	public Item(T object,Element e) {
		this.object = object;
		this.e = e;
	}
	
	public void setColumnContent(int id, String val)
	{
		e.child(id).text(val);
	}
	
}
