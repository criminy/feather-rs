package feather.rs.growl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * Growl service is a managed service class which
 * provides a publishing endpoint for Growl-style messages.
 *  
 * Usage:
 * 	GrowlService growlService = ...;
 *  Thread1: 
 *  	growlService.popAll(id);
 *  
 *  Thread2:
 *  	growlService.register(Id);
 *  	growlService.push(id,"message");
 *  
 * @author sheenobu
 *
 */
@Named
public class GrowlService {

	/**
	 * The message persistence.
	 */
	@Inject
	@Named("persistenceGrowls") 
	protected Map<String,Stack<String>> growls;
	
	/**
	 * Tracks the connected clients.
	 */
	@Inject
	@Named("persistenceGrowlsClients")
	protected Set<String> clients;			

	/**
	 * Tracks the amount of seconds a client has to re-connect before
	 * being dropped, where '0' is the moment of drop.
	 */
	@Inject 
	@Named("persistenceGrowlsClientTimeouts")
	protected Map<String,Integer> clientTimeouts;
	
	private TimeoutRunnable runnable;	
	private Thread th;

	/**
	 * Creates the timeout thread, which is responsible for removing clients
	 * which have not connected to the growlservice in a given number of seconds.
	 */
	@PostConstruct
	public void registerTimeoutThread() {
		//TODO: totally not cluster safe
		runnable = new TimeoutRunnable();
		runnable.growlService = this;
		th = new Thread(runnable);		
		th.start();
	}
	
	/**
	 * Destroys the timeout thread.
	 */
	@PreDestroy
	public void destroyTimeoutThread() {
		th.interrupt();
	}
	
	/**
	 * Registers a client, given its HttpSession, and 
	 * provides a 25 second timeout on the client.
	 * @param s The HttpSession
	 */
	public void register(HttpSession s) 
	{
		clients.add(s.getId());
		clientTimeouts.put(s.getId(),25);//25 seconds
	}
	
	/**
	 * Registers a client, given its ID, and 
	 * provides a 25 second timeout on the client.
	 * @param id the ID of the client.
	 */
	public void register(String id) 
	{
		clients.add(id);
		clientTimeouts.put(id,25);//25 seconds
	}
	
	
	/**
	 * Pushes a message to the given client ID
	 * @param id
	 * @param message
	 */
	public void push(String id, String message)
	{		
		if(growls.get(id) == null)			
		{
			growls.put(id,new Stack<String>());
		}

		growls.get(id).add(message);		
	}
	
	/**
	 * Pushes to every connected client.
	 * @param message
	 */
	public void pushToAll(String message)
	{		
		for(String client : clients)
		{
			push(client,message);
		}
	}
	
	/**
	 * Returns and removes the messages for the given ID.
	 * @param id The ID of the client.
	 * @return The List of messages.
	 */
	public List<String> popAll(String id) {		
		if(growls.get(id) == null)
		{
			return new ArrayList<String>();
		}
		else
		{
			List<String> ret = new ArrayList<String>();
			while(!growls.get(id).isEmpty())
			{
				ret.add(growls.get(id).pop());	
			}
			return ret;			
		}
	}
}

/**
 * A runnable object which, when run, attempts
 * to timeout the growl clients every second.
 * @author sheenobu
 *
 */
class TimeoutRunnable implements Runnable{
	GrowlService growlService;
		
	@Override
	public void run() {
		Stack<String> toTimeout = new Stack<String>();
		while(true) {
			
			for(String s : growlService.clients)
			{
				int timeout = growlService.clientTimeouts.get(s);
				timeout--;
				if(timeout <= 0)
				{					
					toTimeout.push(s);
				}else{
					growlService.clientTimeouts.put(s,timeout);
				}
			}
			
			while(!toTimeout.isEmpty())
			{
				// we erase everything a client may have in order
				// 	to time them out.
				String id = toTimeout.pop();
				growlService.growls.remove(id);
				growlService.clientTimeouts.remove(id);
				growlService.clients.remove(id);
			}
			
			
			try {				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("GrowlService: " + e.getMessage());
				
			}
		}
	}
}
