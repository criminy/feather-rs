package feather.rs.example.growl.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.scheduling.annotation.Scheduled;

import feather.rs.growl.service.GrowlService;

/**
 * Simple job class, which pushes messages onto the
 * {@link GrowlService}.
 * @author sheenobu
 *
 */
@Named
public class Job {

	@Inject GrowlService growlService;
	
	@Scheduled(fixedDelay=3000)
	public void event()
	{
		System.out.println("pushing a message to everyone");
		growlService.pushToAll("Job invocation!");
	}
	
}
