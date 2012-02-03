package feather.rs.conversion;

public interface ConverterFactory {

	public Object convert(String input, Class<?> output);
	
}
