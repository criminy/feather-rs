package feather.rs.html;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.select.Elements;
import org.junit.Test;

import feather.rs.html.ForEach;
import feather.rs.html.Html;
import feather.rs.html.Item;

public class ForEachTest {

	@Test
	public void testSimpleUlList() throws IOException {
		Html html = new Html();
		html.loadFromString("<ul id='id1'><li class='c'>a</li></ul>");

		List<String> vals = Arrays.asList("a1", "a2", "b3");

		html.forEachValue("#id1", vals, new ForEach<String>() {
			@Override
			public void render(Item<String> item) {
				item.setContent(item.getObject());
			}
		});

		Elements ex = html.document.select("#id1");

		assertNotNull(ex);
		assertEquals(1, ex.size());
		assertEquals(3, ex.first().childNodes().size());

		assertEquals("c", ex.first().child(0).attr("class"));
		assertEquals("c", ex.first().child(1).attr("class"));
		assertEquals("c", ex.first().child(2).attr("class"));

		assertEquals(vals.get(0), ex.first().child(0).text());
		assertEquals(vals.get(1), ex.first().child(1).text());
		assertEquals(vals.get(2), ex.first().child(2).text());
	}
	
	@Test
	public void testSimpleUlTestBuiltinForEach() throws IOException {
		Html html = new Html();
		html.loadFromString("<ul id='id1'><li class='c'>a</li></ul>");

		List<String> vals = Arrays.asList("a1", "a2", "b3");

		html.forEachValue("#id1", vals, ForEach.SetContent.get());

		Elements ex = html.document.select("#id1");

		assertNotNull(ex);
		assertEquals(1, ex.size());
		assertEquals(3, ex.first().childNodes().size());

		assertEquals("c", ex.first().child(0).attr("class"));
		assertEquals("c", ex.first().child(1).attr("class"));
		assertEquals("c", ex.first().child(2).attr("class"));

		assertEquals(vals.get(0), ex.first().child(0).text());
		assertEquals(vals.get(1), ex.first().child(1).text());
		assertEquals(vals.get(2), ex.first().child(2).text());
	}

	@Test
	public void testSimpleUlTestBuiltinForEachSetAttribute() throws IOException {
		Html html = new Html();
		html.loadFromString("<ul id='id1'><li class='c'>____</li></ul>");

		List<String> vals = Arrays.asList("a1", "a2", "b3");

		html.forEachValue("#id1", vals, ForEach.SetAttribute.get("id"));

		Elements ex = html.document.select("#id1");

		assertNotNull(ex);
		assertEquals(1, ex.size());
		assertEquals(3, ex.first().childNodes().size());

		assertEquals("c", ex.first().child(0).attr("class"));
		assertEquals("c", ex.first().child(1).attr("class"));
		assertEquals("c", ex.first().child(2).attr("class"));

		assertEquals("____", ex.first().child(0).text());
		assertEquals("____", ex.first().child(1).text());
		assertEquals("____", ex.first().child(2).text());
		
		assertEquals(vals.get(0), ex.first().child(0).attr("id"));
		assertEquals(vals.get(1), ex.first().child(1).attr("id"));
		assertEquals(vals.get(2), ex.first().child(2).attr("id"));
	}


	@Test
	public void testDynamicContentUlList() throws Exception {
		Html html = new Html();
		html.loadFromString("<ul id='id1'><li class='c'>a</li></ul>");

		List<String> vals = Arrays.asList("a1", "a2", "b3");

		html.forEachValue("#id1", vals, new ForEach<String>() {
			@Override
			public void render(Item<String> item) {
				if(item.getObject().equals("a2")){
					item.setHtml("<a href='#'>" + item.getObject() + "</a>");
				}else
					item.setContent(item.getObject());
			
			}
		});

		Elements ex = html.document.select("#id1");

		assertNotNull(ex);
		assertEquals(1, ex.size());
		assertEquals(3, ex.first().childNodes().size());

		assertEquals("c", ex.first().child(0).attr("class"));
		assertEquals("c", ex.first().child(1).attr("class"));
		assertEquals("c", ex.first().child(2).attr("class"));

		assertEquals(0, ex.first().child(0).children().size());
		assertEquals(1, ex.first().child(1).children().size());
		assertEquals(0, ex.first().child(2).children().size());
	
		assertEquals("a", ex.first().child(1).child(0).tagName());
		
		assertEquals(vals.get(0), ex.first().child(0).text());
		assertEquals(vals.get(1), ex.first().child(1).text());
		assertEquals(vals.get(2), ex.first().child(2).text());

	}

	
	@Test
	public void testSimpleTable() throws IOException {
		Html html = new Html();
		html.loadFromString("<table id='t1'><thead></thead><tbody><tr><td></td><td></td><td></td></tr></tbody></table>");

		List<String> vals = Arrays.asList("One", "two", "three");

		html.forEachValue("#t1 tbody", vals, new ForEach<String>() {
			@Override
			public void render(Item<String> item) {
				item.setColumnContent(0,item.getObject());
				item.setColumnContent(1,Integer.toString(item.getObject().length()));
				item.setColumnContent(2,item.getObject().toUpperCase());				
			}
		});

		Elements ex = html.document.select("#t1 tbody");

		assertNotNull(ex);
		assertEquals(1, ex.size());
		assertEquals(3, ex.first().childNodes().size());

		assertEquals("One", ex.first().child(0).child(0).text());
		assertEquals("3", ex.first().child(0).child(1).text());
		assertEquals("ONE", ex.first().child(0).child(2).text());
		
		assertEquals("two", ex.first().child(1).child(0).text());
		assertEquals("3", ex.first().child(1).child(1).text());
		assertEquals("TWO", ex.first().child(1).child(2).text());
		
		assertEquals("three", ex.first().child(2).child(0).text());
		assertEquals("5", ex.first().child(2).child(1).text());
		assertEquals("THREE", ex.first().child(2).child(2).text());
		
	}

	
}
