// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.medfusion.common.utils.EncryptionUtils;
import com.medfusion.common.utils.IHGUtil;

public class PIDCTestData {
	
	public final String GENDERIDENTITY = "Male,Female,Decline to answer";
	
	private static Properties property = new Properties();

	public PIDCTestData() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";
		InputStream url = ClassLoader.getSystemResourceAsStream("data-driven/" + propertyFileNameString);
		property.load(url);
	}

	public String getUrl() {
		return property.getProperty("patient.portal.url.pidc");
	}

	public String getUserName() {
		return property.getProperty("patient.portal.username.pidc");
	}

	public String getPassword() {
		return EncryptionUtils.decrypt(property.getProperty("patient.portal.password.pidc"));
	}

	public String getRestUrl() {
		return property.getProperty("rest.url");
	}

	public String getPatientPath() {
		return property.getProperty("patient.path");
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

	public String getBirthDay() {
		return property.getProperty("patient.birthday");
	}

	public String getZipCode() {
		return property.getProperty("patient.zip.code");
	}

	public String getEmail() {
		return property.getProperty("patient.email");
	}

	public String getPatientPassword() {
		return EncryptionUtils.decrypt(property.getProperty("patient.password"));
	}

	public String getSecretQuestion() {
		return property.getProperty("secret.question");
	}

	public String getSecretAnswer() {
		return property.getProperty("secret.answer");
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

	public String getLastName() {
		return property.getProperty("last.name");
	}

	public String getCity() {
		return property.getProperty("city");
	}

	public String getState() {
		return property.getProperty("state");
	}

	public String getAddress1() {
		return property.getProperty("address1");
	}

	public String getAddress2() {
		return property.getProperty("address2");
	}

	public String getHomePhoneNo() {
		return property.getProperty("phone.number");
	}

	public String getRace() {
		return property.getProperty("race");
	}

	public String getEthnicity() {
		return property.getProperty("ethnicity");
	}

	public String getChooseCommunication() {
		return property.getProperty("choose.communication");
	}

	public String getPortalURL() {
		return property.getProperty("patient.portal.url.pidc");
	}

	public String getPortalRestUrl() {
		return property.getProperty("portal.rest.url.pidc");
	}

	public String getHealthKeyPatientUserName() {
		return property.getProperty("health.key.patient.username");
	}

	public String getHealthKeyPatientPath() {
		return property.getProperty("health.key.patient.path");
	}
	
	public String getCancelInviteRestUrl() {
		return property.getProperty("cancel.invite.rest.url.pidc");
	}
	
	public String getNewPatientPassword() {
		return EncryptionUtils.decrypt(property.getProperty("new.password"));
	}

	public String getRestv1Url() {
		return property.getProperty("restv1.url");
	}

	public String getRestv2Url() {
		return property.getProperty("restv2.url");
	}

	public String getRestv3Url() {
		return property.getProperty("restv3.url");
	}
	
}
