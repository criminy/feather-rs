package feather.rs.growl.resources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Growls {
	private List<String> growls = new ArrayList<String>();
	private int time;
	
	{
		time = (int) (System.currentTimeMillis() / 1000L);
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
			
	public List<String> getGrowls() {
		return growls;
	}

	public void setGrowls(List<String> growls) {
		this.growls = growls;
	}

}