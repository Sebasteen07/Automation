package com.intuit.ihg.product.integrationplatform.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.medfusion.common.utils.IHGUtil;

public class PropertyFileLoader {
	private static Properties property = new Properties();

	public PropertyFileLoader() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";
		URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);

		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);

	}
	//CCD
	public String getResponsePath() {
		return property.getProperty("responsePath");
	}
	public String getOAuthProperty() {
		return property.getProperty("oAuthProperty");
	}
	public String getOAuthKeyStore() {
		return property.getProperty("oAuthKeyStore");
	}
	
	
	public String getPHR_URL() {
		return property.getProperty("phr_URL");
	}
	public String getRestUrlCCD() {
		return property.getProperty("restUrlCCD");
	}
	public String getEPracticeNameCCD() {
		return property.getProperty("ePracticeNameCCD");
	}
	public String getCCDXMLPath() {
		return property.getProperty("ccdXMLPath");
	}
	public String getCCDPath() {
		return property.getProperty("ccdPath");
	}
	public String getCCDZIP() {
		return property.getProperty("zipCCD");
	}
	public String getUserNameCCD() {
		return property.getProperty("userNameCCD");
	}
	public String getPasswordCCD() {
		return property.getProperty("passwordCCD");
	}
	public String getdeviceNameCCD() {
		return property.getProperty("deviceNameCCD");
	}
	public String getdeviceVersionCCD() {
		return property.getProperty("deviceVersionCCD");
	}
	public String getvendorNameCCD() {
		return property.getProperty("vendorNameCCD");
	}
	public String getMessageIdCCD() {
		return property.getProperty("messageIdCCD");
	}
	public String getPrefixCCD() {
		return property.getProperty("prefixCCD");
	}
	public String getFirstNameCCD() {
		return property.getProperty("firstNameCCD");
	}
	public String getMiddleNameCCD() {
		return property.getProperty("middleNameCCD");
	}
	public String getLastNameCCD() {
		return property.getProperty("lastNameCCD");
	}
	public String getLine1CCD() {
		return property.getProperty("line1CCD");
	}
	public String getLine2CCD() {
		return property.getProperty("line2CCD");
	}
	public String getCityCCD() {
		return property.getProperty("cityCCD");
	}
	public String getStateCCD() {
		return property.getProperty("stateCCD");
	}
	public String getCountryCCD() {
		return property.getProperty("countryCCD");
	}
	public String getPracticePatientIdCCD() {
		return property.getProperty("practicePatientId");
	}
	public String getOAuthAppTokenCCD() {
		return property.getProperty("oAuthAppTokenCCD");
	}
	public String getOAuthUsernameCCD() {
		return property.getProperty("oAuthUsernameCCD");
	}
	public String getOAuthPasswordCCD() {
		return property.getProperty("oAuthPasswordCCD");
	}
	public String getUrlCCD() {
		return property.getProperty("urlCCD");
	}
	public String getFromCCD() {
		return property.getProperty("fromCCD");
	}
	public String getIntegrationPracticeIDCCD() {
		return property.getProperty("integrationPracticeIDCCD");
	}
	
	//AMDC
	public String geturlAMDC() {
		return property.getProperty("urlAMDC");
	}
	public String getOAuthAppTokenAMDC() {
		return property.getProperty("oAuthAppTokenAMDC");
	}
	public String getOAuthUsernameAMDC() {
		return property.getProperty("oAuthUsernameAMDC");
	}
	public String getOAuthPasswordAMDC() {
		return property.getProperty("oAuthPasswordAMDC");
	}
	public String getUserNameAMDC() {
		return property.getProperty("userNameAMDC");
	}
	public String getPasswordAMDC() {
		return property.getProperty("passwordAMDC");
	}
	public String getRestUrlAMDC() {
		return property.getProperty("restUrlAMDC");
	}
	public String getResponsePathAMDC() {
		return property.getProperty("responsePathAMDC");
	}
	public String getFromAMDC() {
		return property.getProperty("fromAMDC");
	}
	public String getReadCommunicationUrlAMDC() {
		return property.getProperty("readCommunicationUrlAMDC");
	}
	public String getSender3AMDC() {
		return property.getProperty("sender3AMDC");
	}
	public String getIntegrationPracticeID() {
		return property.getProperty("integrationPracticeIDAMDC");
	}
	public String getGmailUserNameAMDC() {
		return property.getProperty("gmailUserNameAMDC");
	}
	public String getGmailPasswordAMDC() {
		return property.getProperty("gmailPasswordAMDC");
	}
	public String getAllowReply() {
		return property.getProperty("allowReply");
	}
	public String getMessageAMDC() {
		return property.getProperty("messageAMDC");
	}
	public String getPatientExternalIdAMDC() {
		return property.getProperty("patientExternalIdAMDC");
	}
	
	//MU2 EVENTS
	public String getPULLAPI_URL_MU2() {
		return property.getProperty("pullAPI_URL_MU2");
	}
	public String getOAuthAppToken_MU2() {
		return property.getProperty("oAuthAppToken_MU2");
	}
	public String getOAuthUsername_MU2() {
		return property.getProperty("oAuthUsername_MU2");
	}
	public String getOAuthPassword_MU2() {
		return property.getProperty("oAuthPassword_MU2");
	}
	public String getPUSHAPI_URL_MU2() {
		return property.getProperty("pushAPI_URL_MU2");
	}
	public String getPORTAL_URL_MU2() {
		return property.getProperty("portal_URL_MU2");
	}
	public String getPORTAL_USERNAME_MU2() {
		return property.getProperty("portal_USERNAME_MU2");
	}
	public String getPORTAL_PASSWORD_MU2() {
		return property.getProperty("portal_PASSWORD_MU2");
	}
	public String getINTUIT_PATIENT_ID_MU2() {
		return property.getProperty("intuit_PATIENT_ID_MU2");
	}
	public String getTRANSMIT_EMAIL_MU2() {
		return property.getProperty("transmit_EMAIL_MU2");
	}
	public String getIMAGE_PATH_MU2() {
		return property.getProperty("image_PATH_MU2");
	}
	public String getPatient_ID_MU2() {
		return property.getProperty("patient_ID_MU2");
	}
	public String getPATIENT_INVITE_RESTURL() {
		return property.getProperty("RestUrlPIDC");
	}
	public String getPATIENT_PRACTICEID() {
		return property.getProperty("PracticeIdPIDC");
	}
	public String getPATIENT_EXTERNAL_ID() {
		return property.getProperty("ExternalSystemIDPIDC");
	}
	public String getCCDPATH1() {
		return property.getProperty("ccdXMLPath1");
	}
	public String getCCDPATH2() {
		return property.getProperty("ccdXMLPath2");
	}
	public String getCCDPATH3() {
		return property.getProperty("ccdXMLPath3");
	}
	public String getHomePhoneNo() {
		return property.getProperty("homePhoneNo");
	}
	public String getSecretQuestion() {
		return property.getProperty("secretQuestion");
	}
	public String getSecretAnswer() {
		return property.getProperty("secretAnswer");
	}
	public String getBirthDay() {
		return property.getProperty("birthDay");
	}
	public String getPatientPassword() {
		return property.getProperty("patientPassword");
	}
	
	
	public String getCCDMessageID1() {
		return property.getProperty("ccdMessageID1");
	}
	public String getCCDMessageID2() {
		return property.getProperty("ccdMessageID2");
	}

	public String getPatientExternalId_MU2() {
		return property.getProperty("patientExternalId_MU2");
	}
	public String getPatientFirstName_MU2() {
		return property.getProperty("patientFirstName_MU2");
	}
	public String getPatientLastName_MU2() {
		return property.getProperty("patientLastName_MU2");
	}
	
}