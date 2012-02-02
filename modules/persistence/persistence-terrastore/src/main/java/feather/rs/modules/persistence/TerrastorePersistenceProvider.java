package feather.rs.modules.persistence;

import javax.inject.Inject;

import terrastore.client.TerrastoreClient;

public abstract class TerrastorePersistenceProvider<T> 
	implements PersistenceProvider<T,String>
{

	@Inject
	private TerrastoreClient client;
	
	public String bucketName;
	
	public abstract Class<T> clazz();
	
	public T get(String k) {
		return (T) client.bucket(bucketName).key(k).get(clazz());
	}
	public void put(String k, T t) {
		client.bucket(bucketName).key(k).put(t);
	}
	public void remove(String key) {
		client.bucket(bucketName).key(key).remove();
	}
	
}
