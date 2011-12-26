package feather.rs.html;

import feather.rs.Fn;
import feather.rs.Fn1;

/**
 * Interface for specifying an iteration over a set of items, used with
 * CSS element selection.
 * 
 * @author sheenobu
 *
 * @param <T>
 */
public interface ForEach<T> {

	public void render(Item<T> item);

	/**
	 * Pre-created ForEach for setting the content of an element.
	 */
	public static Fn<ForEach> SetContent = new Fn<ForEach>() {
		@Override
		public ForEach get() {
			return new ForEach() {
				@Override
				public void render(Item item) {
					item.setContent(item.getObject().toString());
				}
			};
		}
	};
	
	/**
	 * ForEach object which, when built, sets the given attribute of every item. 
	 */
	public static Fn1<ForEach,String> SetAttribute = new Fn1<ForEach,String>() {
		@Override
		public ForEach get(final String key) {
			return new ForEach() {
				@Override
				public void render(Item item) {
					item.e.attr(key, item.getObject().toString());					
				}
			};
		}

	};

}
