package com.intuit.ihg.common.utils.dataprovider;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.intuit.ihg.common.utils.IHGUtil;

public class PropertyFileLoader {

	private Properties property = new Properties();

	public String getUrl() {
		return property.getProperty("url");
	}

	public String getUserId() {

		return property.getProperty("userid");
	}

	public String getPassword() {

		return property.getProperty("password");
	}

	public PropertyFileLoader() throws IOException {

		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";

		URL url = ClassLoader.getSystemResource("data-driven/"
				+ propertyFileNameString);
		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);

	}

}
