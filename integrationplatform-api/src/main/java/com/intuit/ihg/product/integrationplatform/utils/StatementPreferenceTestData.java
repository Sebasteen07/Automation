// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.medfusion.common.utils.EncryptionUtils;
import com.medfusion.common.utils.IHGUtil;

public class StatementPreferenceTestData {

	private Properties property = new Properties();

	public StatementPreferenceTestData() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";

		URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);
		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);
	}

	public String getUrl() {
		return property.getProperty("patient.portal.url.statements");
	}

	public String getUserName() {
		return property.getProperty("patient.portal.username.statements");
	}

	public String getPassword() {
		return EncryptionUtils.decrypt(property.getProperty("patient.portal.password.statements"));
	}

	public String getRestUrl() {
		return property.getProperty("rest.url.statements");
	}

	public String getStatementPath() {
		return property.getProperty("statement.path");
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

	public String getPracticeURL() {
		return property.getProperty("practice.url");
	}

	public String getPracticeUserName() {
		return property.getProperty("practice.username");
	}

	public String getPracticePassword() {
		return EncryptionUtils.decrypt(property.getProperty("practice.password"));
	}

	public String getFirstName() {
		return property.getProperty("first.name.statements");
	}

	public String getLastName() {
		return property.getProperty("last.name.statements");
	}
	
	public String getRestUrlV3() {
		return property.getProperty("restv3.url.statements");
	}

	public String getStatementPathV3() {
		return property.getProperty("statementv3.path");
	}
}
