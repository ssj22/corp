package net.corp.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SpringPropertyLoadConfigurer extends PropertyPlaceholderConfigurer {
	private static Map<String, String> propertiesMap;
	// Default as in PropertyPlaceholderConfigurer
	private int springSystemPropertiesMode = SYSTEM_PROPERTIES_MODE_FALLBACK;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 * #processProperties
	 * (org.springframework.beans.factory.config.ConfigurableListableBeanFactory
	 * , java.util.Properties)
	 */
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);

		propertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String valueStr = resolvePlaceholder(keyStr, props,
					springSystemPropertiesMode);
			propertiesMap.put(keyStr, valueStr);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 * #setSystemPropertiesMode(int)
	 */
	@Override
	public void setSystemPropertiesMode(int systemPropertiesMode) {
		super.setSystemPropertiesMode(systemPropertiesMode);
		springSystemPropertiesMode = systemPropertiesMode;
	}

	/**
	 * Get the property value
	 * 
	 * @param name
	 * @return String
	 */
	public static String getProperty(String name) {
		return propertiesMap.get(name).toString();
	}
}
