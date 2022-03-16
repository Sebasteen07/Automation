// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.medfusion.common.utils.EncryptionUtils;
import com.medfusion.common.utils.IHGUtil;

public class EHDCTestData {

	private Properties property = new Properties();

	public EHDCTestData() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";

		URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);
		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);
	}

	public String getUrl() {
		return property.getProperty("patient.portal.url.ehdc");
	}

	public String getUserName() {
		return property.getProperty("patient.portal.username.ehdc");
	}

	public String getPassword() {
		return EncryptionUtils.decrypt(property.getProperty("patient.portal.password.ehdc"));
	}

	public String getRestUrl() {
		return property.getProperty("rest.url.ehdc");
	}

	public String getCCDPath() {
		return property.getProperty("ccd.path.ehdc");
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
}
