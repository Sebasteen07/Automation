
package net.medfusion.integrations.webservices.framework;

import org.apache.commons.lang.StringUtils;

import com.medfusion.common.utils.IHGUtil;

import java.util.Properties;



public class TestProperties {
	// ~ Static fields/initializers -------------------------------------------------------------------------------------

	// The Constant properties.
	private static final Properties properties;

	static {
		properties = new Properties();

		/* read test properties file */

		// TestPropertiesLoader loader = new TestPropertiesLoader("webservicesclient." + IHGUtil.getEnvironmentType() + ".properties", properties);
		TestPropertiesLoader loader = new TestPropertiesLoader("webservicesclient.PROD" + ".properties", properties);
		// TestPropertiesLoader loader = new TestPropertiesLoader("webservicesclient.DEV3" + ".properties", properties);

		loader.loadProperties();
		/*
		 * String propertiesFile = System.getProperty("webservicesclient.properties");
		 * 
		 * if (propertiesFile != null) { TestPropertiesLoader loader = new TestPropertiesLoader(propertiesFile, properties); loader.loadProperties(); } else {
		 * System.err.println("WARNING::::: No test.propertiesfile was specified"); } // end if-else
		 */
	}

	// ~ Methods --------------------------------------------------------------------------------------------------------

	/**
	 * Gets the boolean value corresponding to the given property.
	 *
	 * @param name the name of the property
	 * @param defaultValue the default value to use if not set
	 *
	 * @return the boolean value of the property
	 */
	public static boolean getBoolean(String name, boolean defaultValue) {
		String value = getProperty(name);

		if (value == null) {
			return defaultValue;
		} // end if

		return Boolean.parseBoolean(value);
	} // end method getBoolean

	/**
	 * Gets the boolean value corresponding to the given property.
	 *
	 * @param name the name of the property
	 *
	 * @return the boolean value of the property
	 */
	public static boolean getBoolean(String name) {
		return getBoolean(name, false);
	} // end method getBoolean

	/**
	 * Gets the integer value corresponding to the given property.
	 *
	 * @param name the name of the property
	 * @param defaultValue the default value to use if not set
	 *
	 * @return the integer value of the property
	 */
	public static int getInteger(String name, int defaultValue) {
		String value = getProperty(name);

		if (value == null) {
			return defaultValue;
		} // end if

		return Integer.parseInt(value);
	} // end method getInteger

	/**
	 * Gets the integer value corresponding to the given property. It returns 0 if not set.
	 *
	 * @param name the name of the property
	 *
	 * @return the integer value of the property
	 */
	public static int getInteger(String name) {
		return getInteger(name, 0);
	} // end method getInteger

	/**
	 * Returns a test property value. Uses the first non-null and non-empty value from the following locations:
	 *
	 * <ol>
	 * <li>System Properties</li>
	 * <li>Properties loaded from the test properties file</li>
	 * <li>Default value specified as an input parameter</li>
	 * </ol>
	 *
	 * @param name the name of the property
	 * @param defaultValue the default value to use if not set
	 *
	 * @return the property value
	 */
	public static String getProperty(String name, String defaultValue) {
		String value = System.getProperty(name);

		value = StringUtils.trimToNull(value);

		if (value == null) {
			value = properties.getProperty(name);
			value = StringUtils.trimToNull(value);
		} // end if

		if (value == null) {
			value = defaultValue;
		} // end if-else

		return value;
	} // end method getProperty

	/**
	 * Returns a test property value. Uses the first non-null value from the following locations:
	 *
	 * <ol>
	 * <li>System Properties</li>
	 * <li>Properties loaded from the test properties file</li>
	 * </ol>
	 *
	 * <p>
	 * If both values are null then returns null.
	 * </p>
	 *
	 * @param name the name of the property
	 *
	 * @return the property value
	 */
	public static String getProperty(String name) {
		return getProperty(name, null);
	} // end method getProperty

	/**
	 * Fetch a required property. No defaults obviously, this will throw {@link ConfigError} exception when the property cannot be loaded from system properties
	 * or the internal test.properties collection.
	 *
	 * @param key the property key to get
	 *
	 * @return the value
	 *
	 * @throws ConfigError if it fails to find the property with a suitable message
	 */
	public static String getRequiredProperty(String key) {
		String value = getProperty(key);

		if (value == null) {
			throw new ConfigError("property " + key + " was not defined.");
		} // end if

		return value;
	} // end method getRequiredProperty
} // end class TestProperties
