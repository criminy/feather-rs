package feather.rs;

/**
 * One argument function
 * @author sheenobu
 *
 * @param <T>
 * @param <A>
 */
public interface Fn1<T,A> {
	public T get(A a);
}
