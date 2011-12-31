package feather.rs.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogFactory {

	public static Log getLog(final Class<?> clazz)
	{
		return new Log() {
			
			Logger logger = LoggerFactory.getLogger(clazz);
			
			@Override
			public void warn(String msg) {
				logger.warn(msg);
			}
			
			@Override
			public void info(String msg) {
				logger.info(msg);
			}
			
			@Override
			public void error(String msg) {
				logger.error(msg);
			}
		};
	}
}
