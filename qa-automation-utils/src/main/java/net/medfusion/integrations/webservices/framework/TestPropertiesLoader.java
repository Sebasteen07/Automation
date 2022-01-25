//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package net.medfusion.integrations.webservices.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPropertiesLoader {

	private static final Pattern PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");

	private final String propertiesFile;
	private final Properties properties;

	/**
	 * Creates a new TestPropertiesLoader object.
	 *
	 * @param propertiesFile the file to use when loading properties
	 * @param properties Properties collection to populate with properties loaded
	 */
	public TestPropertiesLoader(String propertiesFile, Properties properties) {
		this.propertiesFile = propertiesFile;
		this.properties = properties;
	}

	/**
	 * Load all properties in the file provided resolving placeholders and imported file references.
	 */
	public void loadProperties() {
		File testProps = new File(propertiesFile);

		if (!testProps.exists()) {
			throw new ConfigError(testProps.getAbsolutePath() + " does not exist!");
		} // end if

		Collection<String> processedFiles = new HashSet<String>();
		recursiveLoadProperties(testProps, properties, processedFiles);
		resolvePlaceholders();
	}

	private void recursiveLoadProperties(File testPropsFile, Properties targetProps, Collection<String> processedFiles) throws ConfigError {
		try {
			processedFiles.add(testPropsFile.getCanonicalPath());

			Properties props = loadProperties(testPropsFile);

			String testImportFileName = (String) props.remove("test.properties.import");

			if (testImportFileName != null) {
				File testImportFile = new File(testImportFileName);

				if (!testImportFile.exists()) {
					File parentDir = testPropsFile.getParentFile();
					testImportFile = new File(parentDir, testImportFileName);

					if (!testImportFile.exists()) {
						throw new ConfigError("Import from " + testPropsFile.getAbsolutePath() + " file test.properties.import=" + testImportFileName + " does not exist!");
					} 
				} 
				
				if (processedFiles.contains(testImportFile.getCanonicalPath())) {
					System.err.println("WARNING: Recursive import from " + testPropsFile.getAbsolutePath() + " ignored.");
					System.err.println("         test.properties.import=" + testImportFile.getCanonicalPath());
				} else {
					recursiveLoadProperties(testImportFile, targetProps, processedFiles);
				} 
			} 

			targetProps.putAll(props);
		} catch (IOException e) {
			throw new ConfigError("Unable to read properties file " + testPropsFile.getAbsolutePath(), e);
		} 
	} 

	private Properties loadProperties(File testProps) throws FileNotFoundException, IOException {
		InputStream tcin = new FileInputStream(testProps);
		Properties props = new Properties();

		try {
			props.load(tcin);
		} finally {
			tcin.close();
		} 

		return props;
	} 

	private void resolvePlaceholders() {
		Collection<String> keys = new HashSet<String>();

		for (Object key : properties.keySet()) {
			keys.add((String) key);
		} 
		
		Map<String, String> workingKeys = new LinkedHashMap<String, String>();

		for (String key : keys) {
			workingKeys.clear();

			String value = properties.getProperty(key);
			workingKeys.put(key, value);

			String newValue = resolve(workingKeys, value);
			properties.put(key, newValue);
		} 
	} 

	private String resolve(Map<String, String> workingKeys, String value) {
		Matcher matcher = PATTERN.matcher(value);
		StringBuffer buf = new StringBuffer(value.length() * 2);

		while (matcher.find()) {
			String property = matcher.group(1);

			if (workingKeys.containsKey(property)) {
				System.err.println("WARNING: Recursive property evaluation!");

				for (Entry<String, String> entry : workingKeys.entrySet()) {
					System.err.println("    " + entry.getKey() + "=" + entry.getValue());
				} 
				
				matcher.appendReplacement(buf, Matcher.quoteReplacement(matcher.group()));

				continue;
			} 

			String replacement = System.getProperty(property);
			boolean update;

			if (replacement == null) {
				replacement = properties.getProperty(property);
				update = true;
			} else {
				update = false;
			} 

			if (replacement == null) {
				replacement = matcher.group();
				System.err.println("WARNING: Placeholder not resolved! (" + replacement + ")");
			} else {
				workingKeys.put(property, replacement);
				replacement = resolve(workingKeys, replacement);
				workingKeys.remove(property);

				if (update) {
					properties.put(property, replacement);
				} 
			} 

			replacement = Matcher.quoteReplacement(replacement);
			matcher.appendReplacement(buf, replacement);
		} 

		matcher.appendTail(buf);

		return buf.toString();
	} 
} 
