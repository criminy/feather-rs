package feather.rs.modules.persistence;

public interface PersistenceProvider<T,K> {

	public T get(K k);
	public void put(K k,T t);
	public void remove(K k);
	
}
