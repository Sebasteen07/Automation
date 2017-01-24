package com.medfusion.dre.util;


import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import com.medfusion.dre.dreautomation.DreAcceptanceTests;

public class Data {

	private static TreeProperties retrieverProperties = new TreeProperties();

	static {
		try {			
            Properties testConfig = new Properties();
            testConfig.load(new FileInputStream("testConfig.properties"));
            String env = testConfig.getProperty("test.environment");
			
			FileInputStream inputStream = new FileInputStream("src/test/resources/test-data-" + env + "/" + DreAcceptanceTests.retrieverName + ".properties");
			retrieverProperties.load(inputStream);
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return retrieverProperties.getProperty(key);
	}

	public static Map<String, String> getMapFor(String key) {
		return retrieverProperties.getPropertySubtree(key);
	}

}