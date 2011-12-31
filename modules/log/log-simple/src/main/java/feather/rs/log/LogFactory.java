package feather.rs.log;

public class LogFactory {

	public static Log getLog(final Class<?> clazz)
	{
		return new Log() {
			

			@Override
			public void warn(String msg) {
				System.out.println(String.format("[ WARN %s ] %s",clazz.getSimpleName(),msg));
			}
			
			@Override
			public void info(String msg) {
				System.out.println(String.format("[ INFO %s ] %s",clazz.getSimpleName(),msg));
			}
			
			@Override
			public void error(String msg) {
				System.out.println(String.format("[ ERRO %s ] %s",clazz.getSimpleName(),msg));
			}
		};
	}
}
