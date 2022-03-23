// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.medfusion.common.utils.EncryptionUtils;
import com.medfusion.common.utils.IHGUtil;

public class MedicationTestData {

	private Properties property = new Properties();
	
	public MedicationTestData() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";

		URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);
		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);
	}

	public String getUrl() {
		return property.getProperty("patient.url");
	}

	public String getUserName() {
		return property.getProperty("med.patient.username");
	}

	public String getPassword() {
		return EncryptionUtils.decrypt(property.getProperty("med.patient.password"));
	}

	public String getRestUrl() {
		return property.getProperty("med.rest.url");
	}

	public String getResponsePath() {
		return property.getProperty("response.path");
	}

	public String getFrom() {
		return property.getProperty("med.from");
	}

	public String getMedicationPath() {
		return property.getProperty("med.medication.path");
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

	public String getPreferredDoctor() {
		return property.getProperty("med.preferred.doctor");
	}

	public String getPhoneNumber() {
		return property.getProperty("med.phone.number");
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

	public String getGmailUserName() {
		return property.getProperty("gmail.username");
	}

	public String getGmailPassword() {
		return EncryptionUtils.decrypt(property.getProperty("gmail.password"));
	}

	public String getPracticeName() {
		return property.getProperty("practice.name");
	}

	public String getFirstName() {
		return property.getProperty("med.first.name");
	}

	public String getLastName() {
		return property.getProperty("med.last.name");
	}

	public String getMFPatientID() {
		return property.getProperty("med.mf.patient.id");
	}

	public String getRemoveMedicationRestUrl() {
		return property.getProperty("med.remove.medications.rest.url");
	}

}
