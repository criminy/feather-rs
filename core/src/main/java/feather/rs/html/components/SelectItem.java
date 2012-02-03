package feather.rs.html.components;

public class SelectItem {

	private String label;
	private String value;
	private boolean selected;
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public interface Processor<T> {
		public void process(T t,SelectItem item);
	}
	
	
}
