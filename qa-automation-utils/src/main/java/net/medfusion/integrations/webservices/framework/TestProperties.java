//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package net.medfusion.integrations.webservices.framework;

import org.apache.commons.lang.StringUtils;

import java.util.Properties;

public class TestProperties {
	private static final Properties properties;

	static {
		properties = new Properties();

		/* read test properties file */

		// TestPropertiesLoader loader = new TestPropertiesLoader("webservicesclient." + IHGUtil.getEnvironmentType() + ".properties", properties);
		TestPropertiesLoader loader = new TestPropertiesLoader("webservicesclient.PROD" + ".properties", properties);
		// TestPropertiesLoader loader = new TestPropertiesLoader("webservicesclient.DEV3" + ".properties", properties);

		loader.loadProperties();
	}

	public static boolean getBoolean(String name, boolean defaultValue) {
		String value = getProperty(name);

		if (value == null) {
			return defaultValue;
		} 

		return Boolean.parseBoolean(value);
	} 

	public static boolean getBoolean(String name) {
		return getBoolean(name, false);
	} 

	public static int getInteger(String name, int defaultValue) {
		String value = getProperty(name);

		if (value == null) {
			return defaultValue;
		} 

		return Integer.parseInt(value);
	} 
	
	public static int getInteger(String name) {
		return getInteger(name, 0);
	} 

	public static String getProperty(String name, String defaultValue) {
		String value = System.getProperty(name);

		value = StringUtils.trimToNull(value);

		if (value == null) {
			value = properties.getProperty(name);
			value = StringUtils.trimToNull(value);
		} 
		
		if (value == null) {
			value = defaultValue;
		} 

		return value;
	} 

	public static String getProperty(String name) {
		return getProperty(name, null);
	} 

	public static String getRequiredProperty(String key) {
		String value = getProperty(key);
		if (value == null) {
			throw new ConfigError("property " + key + " was not defined.");
		} 
		return value;
	} 
} 
