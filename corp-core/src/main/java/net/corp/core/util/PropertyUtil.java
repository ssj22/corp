package net.corp.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyUtil {
	private static PropertyUtil instance;
	private Properties prop = null;
	private static final Logger log = Logger.getLogger(PropertyUtil.class); 
	
	private PropertyUtil() {
		populateMap();
	}
	
	public static PropertyUtil getInstance() {
		if (instance == null) {
			instance  = new PropertyUtil();
		}
		return instance;
	}
	
	public String getProperty(String name) {
		return prop.getProperty(name);
	}
	
	public void addProperty(String name, String value) {
		prop.put(name, value);
	}
	
	public boolean exists(String name) {
		return prop.containsKey(name);
	}
	
	private void populateMap() {
		prop = new Properties();
		InputStream input = null;
		 
		try {
			input = new FileInputStream(System.getProperty("catalina.base") + "/conf/config.properties");
			log.info(System.getProperty("catalina.base"));
		    //input = new FileInputStream("/Users/sushiljoshi/servers/apache-tomcat-7.0.57/conf/config.properties");
	 		// load a properties file
			prop.load(input);
	  
		} catch (IOException ex) {
			log.error("Error while loading property file: " + ex.getMessage(), ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error("Error while closing property file on disk: " + e.getMessage(), e);
				}
			}
		}
	}
	
	public void reload() {
		populateMap();
	}
}
