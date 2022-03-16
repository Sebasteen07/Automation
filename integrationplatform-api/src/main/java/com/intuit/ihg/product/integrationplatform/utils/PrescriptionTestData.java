// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.medfusion.common.utils.EncryptionUtils;
import com.medfusion.common.utils.IHGUtil;

public class PrescriptionTestData {

	private Properties property = new Properties();

	public PrescriptionTestData() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";

		URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);
		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);
	}

	public String getUrl() {
		return property.getProperty("patient.portal.url.prescription");
	}

	public String getUserName() {
		return property.getProperty("patient.portal.username.prescription");
	}

	public String getPassword() {
		return EncryptionUtils.decrypt(property.getProperty("patient.portal.password.prescription"));
	}

	public String getRestUrl() {
		return property.getProperty("rest.url.prescription");
	}

	public String getResponsePath() {
		return property.getProperty("response.path");
	}

	public String getFrom() {
		return property.getProperty("from");
	}

	public String getPrescriptionPath() {
		return property.getProperty("prescription.path");
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

	public String getPracticeURL() {
		return property.getProperty("practice.url");
	}

	public String getPracticeUserName() {
		return property.getProperty("practice.username");
	}

	public String getPracticePassword() {
		return EncryptionUtils.decrypt(property.getProperty("practice.password"));
	}

	public String getRestV3Url() {
		return property.getProperty("restv3.url.prescription");
	}
	
	public String getPrescriptionPathV3() {
		return property.getProperty("prescriptionv3.path");
	}
}
