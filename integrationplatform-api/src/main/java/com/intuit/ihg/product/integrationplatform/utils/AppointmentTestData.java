//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.medfusion.common.utils.EncryptionUtils;
import com.medfusion.common.utils.IHGUtil;

public class AppointmentTestData {

	private Properties property = new Properties();
	
	public AppointmentTestData() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";
		
		URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);
		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);
	}

	public String getUrl() {
		return property.getProperty("patient.portal.url.appointments");
	}

	public String getUserName() {
		return property.getProperty("patient.portal.username.appointments");
	}

	public String getPassword() {
		return EncryptionUtils.decrypt(property.getProperty("patient.portal.password.appointments"));
	}

	public String getRestUrl() {
		return property.getProperty("rest.url.appointments");
	}

	public String getResponsePath() {
		return property.getProperty("response.path");
	}

	public String getOAuthProperty() {
		return property.getProperty("oauth.property");
	}

	public String getOAuthKeyStore() {
		return property.getProperty("oauth.keystore");
	}

	public String getOAuthAppToken() {
		return property.getProperty("oauth.app.token");
	}

	public String getOAuthUsername() {
		return property.getProperty("oauth.username");
	}

	public String getOAuthPassword() {
		return EncryptionUtils.decrypt(property.getProperty("oauth.password"));
	}

	public String getFrom() {
		return property.getProperty("from.appointments");
	}

	public String getAppointmentPath() {
		return property.getProperty("appointment.path");
	}

	public String getPracticeURL() {
		return property.getProperty("practice.url");
	}

	public String getPracticeUserName() {
		return property.getProperty("practice.username");
	}

	public String getPracticePassword() {
		return EncryptionUtils.decrypt(property.getProperty("practice.password"));
	}

	public String getPracticeName() {
		return property.getProperty("practice.name.appointments");
	}

	public String getRestV3Url() {
		return property.getProperty("restv3.url.appointments");
	}

	public String getAppointmentPathV3() {
		return property.getProperty("appointmentv3.path");
	}

	public String getRestUrlV3Headers() {
		return property.getProperty("restv3.url.headers.appointments");
	}
}
