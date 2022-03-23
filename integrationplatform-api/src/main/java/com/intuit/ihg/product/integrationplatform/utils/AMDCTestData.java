// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.medfusion.common.utils.EncryptionUtils;
import com.medfusion.common.utils.IHGUtil;

public class AMDCTestData {

	private Properties property = new Properties();

	public AMDCTestData() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";

		URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);
		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);
	}

	public String getUrl() {
		return property.getProperty("patient.portal.url.amdc");
	}

	public String getUserName() {
		return property.getProperty("patient.portal.username.amdc");
	}

	public String getPassword() {
		return EncryptionUtils.decrypt(property.getProperty("patient.portal.password.amdc"));
	}

	public String getRestUrl() {
		return property.getProperty("rest.url.amdc");
	}

	public String getResponsePath() {
		return property.getProperty("response.path");
	}

	public String getFrom1() {
		return property.getProperty("from1.amdc");
	}

	public String getSecureMessagePath() {
		return property.getProperty("secure.message.path.amdc");
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

	public String getReadCommunicationURL() {
		return property.getProperty("read.communication.url.amdc");
	}

	public String getSecureMessage_AskaStaffXML() {
		return property.getProperty("secure.message.ask.a.staff.xml.amdc");
	}

	public String getBatch_SecureMessage() {
		return property.getProperty("batch.secure.message.amdc");
	}

	public String getUserName1() {
		return property.getProperty("username1.amdc");
	}

	public String getFrom2() {
		return property.getProperty("from2.amdc");
	}

	public String getUserName2() {
		return property.getProperty("username2.amdc");
	}

	public String getSender1() {
		return property.getProperty("amdc.sender1");
	}

	public String getSender2() {
		return property.getProperty("amdc.sender2");
	}

	public String getSender3() {
		return property.getProperty("amdc.sender3");
	}

	public String getIntegrationPracticeID() {
		return property.getProperty("integration.practice.id");
	}

	public String getPatientName1() {
		return property.getProperty("patient.name1.amdc");
	}

	public String getPatientName2() {
		return property.getProperty("patient.name2.amdc");
	}

	public String getPatientName3() {
		return property.getProperty("patient.name3.amdc");
	}

	public String getRestV3Url() {
		return property.getProperty("rest.urlv3.amdc");
	}
}
