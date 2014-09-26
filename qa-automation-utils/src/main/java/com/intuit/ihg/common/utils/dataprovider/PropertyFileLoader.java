package com.intuit.ihg.common.utils.dataprovider;

import java.io.FileReader;
import java.io.IOException;
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
	
	public String getFirstName() {
		return property.getProperty("FirstName");
	}

	public String getLastName() {

		return property.getProperty("LastName");
	}

	public String getEmail() {

		return property.getProperty("email");
	}
	
	public String getDOBDay() {
		return property.getProperty("DOBDay");
	}

	public String getDOBMonth() {

		return property.getProperty("DOBMonth");
	}

	public String getDOBYear() {

		return property.getProperty("DOBYear");
	}
	
	public String getZipCode() {
		return property.getProperty("ZipCode");
	}
	
	public String getSecretQuestion() {

		return property.getProperty("SecretQuestion");
	}
	
	public String getSecretAnswer() {
		return property.getProperty("SecretAnswer");
	}
	
	public String getphoneNumer() {
		return property.getProperty("phoneNumer");
	}

	public PropertyFileLoader() throws IOException {

		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";

		URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);
		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);

	}

}
