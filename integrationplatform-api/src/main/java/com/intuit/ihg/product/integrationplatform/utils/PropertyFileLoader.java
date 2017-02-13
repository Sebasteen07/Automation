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
		return property.getProperty("restUrlPIDC");
	}
	public String getPATIENT_PRACTICEID() {
		return property.getProperty("practiceIdPIDC");
	}
	public String getPATIENT_EXTERNAL_ID() {
		return property.getProperty("externalSystemIDPIDC");
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
	public String getCCDPATH4() {
		return property.getProperty("ccdXMLPath4");
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
	
	
	//PIDC
	public String getUrl() {
		return property.getProperty("url");
	}

	public String getUserName() {
		return property.getProperty("username");
	}

	public String getPassword() {
		return property.getProperty("password");
	}

	public String getRestUrl() {
		return property.getProperty("restUrl");
	}

	public String getRestUrl1() {
		return property.getProperty("restUrl1");
	}

	public String getRestUrl2() {
		return property.getProperty("restUrl2");
	}

	public String getPatientPath() {
		return property.getProperty("patientPath");
	}

	public String getOAuthAppToken() {
		return property.getProperty("oAuthAppToken");
	}

	public String getOAuthUsername() {
		return property.getProperty("oAuthUsername");
	}

	public String getOAuthPassword() {
		return property.getProperty("oAuthPassword");
	}

	public String getBirthday() {
		return property.getProperty("birthDay");
	}

	public String getZipCode() {
		return property.getProperty("zipCode");
	}

	public String getSSN() {
		return property.getProperty("SSN");
	}

	public String getEmail() {
		return property.getProperty("email");
	}

	public String getRace() {
		return property.getProperty("race");
	}

	public String getRelation() {
		return property.getProperty("relation");
	}

	public String getPreferredLanguage() {
		return property.getProperty("preferredLanguage");
	}

	public String getEthnicity() {
		return property.getProperty("ethnicity");
	}

	public String getMaritalStatus() {
		return property.getProperty("maritalStatus");
	}

	public String getChooseCommunication() {
		return property.getProperty("chooseCommunication");
	}

	public String getInsurance_Type() {
		return property.getProperty("insurance_Type");
	}

	public String getBatchSize() {
		return property.getProperty("batchSize");
	}

	public String getPracticeURL() {
		return property.getProperty("practiceURL");
	}
	public String getPortalVersion() {
		return property.getProperty("portalVersion");
	}
	
	public String getCSVFilePath() {
		return property.getProperty("csvFilePath");
	}
	
	public String getPracticeId_PIDC() {
		return property.getProperty("practiceId_PIDC");
	}
	
	
	public String getRestUrl1_20() {
		return property.getProperty("restUrl1_20");
	}
	public String getRestUrl2_20() {
		return property.getProperty("restUrl2_20");
	}
	public String getPracticeId_PIDC_20() {
		return property.getProperty("practiceId_PIDC_20");
	}
	public String getOAuthAppToken_20() {
		return property.getProperty("oAuthAppToken_20");
	}
	public String getOAuthUsername_20() {
		return property.getProperty("oAuthUsername_20");
	}
	public String getOAuthPassword_20() {
		return property.getProperty("oAuthPassword_20");
	}
	
	public String getStandard_Email() {
		return property.getProperty("standard_Email");
	}
	
	//PIDC END
	
	//Appointment Data Request from partner
	public String getAppointmentRequestURL() {
		return property.getProperty("appointmentRequestURL");
	}
	public String getOAuthAppToken_AD() {
		return property.getProperty("oAuthAppToken_AD");
	}
	public String getOAuthUsername_AD() {
		return property.getProperty("oAuthUsername_AD");
	}
	public String getoAuthPassword_AD() {
		return property.getProperty("oAuthPassword_AD");
	}
	public String getIntegrationPracticeID_AD() {
		return property.getProperty("integrationPracticeID_AD");
	}
	public String getMedfusionPracticeId_AD() {
		return property.getProperty("medfusionPracticeId_AD");
	}
	public String getUserName_AD() {
		return property.getProperty("userName_AD");
	}
	public String getPassword_AD() {
		return property.getProperty("password_AD");
	}
	public String getPatientPracticeID_AD() {
		return property.getProperty("patientPracticeID_AD");
	}
	public String getMedfusionPatientId_AD() {
		return property.getProperty("medfusionPatientId_AD");
	}
	public String getEmail_AD() {
		return property.getProperty("email_AD");
	}
	public String getProviderIdentifier_AD() {
		return property.getProperty("providerIdentifier_AD");
	}
	public String getProviderName_AD() {
		return property.getProperty("providerName_AD");
	}
	public String getAppointmentTime_AD() {
		return property.getProperty("appointmentTime_AD");
	}
	public String getAppointmentLocation_AD() {
		return property.getProperty("appointmentLocation_AD");
	}
	public String getAppointmentStatus_AD() {
		return property.getProperty("appointmentStatus_AD");
	}
	public String getAppointmentDescription_AD() {
		return property.getProperty("appointmentDescription_AD");
	}
	public String getAppointmentReason_AD() {
		return property.getProperty("appointmentReason_AD");
	}
	public String getAppointmentType_AD() {
		return property.getProperty("appointmentType_AD");
	}
	public String getPracticeURL_AD() {
		return property.getProperty("practiceURL_AD");
	}
	public String getPreviousAppointmentId() {
		return property.getProperty("previousAppointmentId");
	}
	public String getFirstName_AD() {
		return property.getProperty("firstName_AD");
	}
	public String getLastName_AD() {
		return property.getProperty("lastName_AD");
	}
	public String getBatchSize_AD() {
		return property.getProperty("batchSize");
	}
	public String getCSVFILEPATH_AD() {
		return property.getProperty("csvFilePath_AD");
	}
	public String getPracticePortalURL_AD() {
		return property.getProperty("portalUrl");
	}
	public String getProtalUserName_AD() {
		return property.getProperty("portalUserName");
	}
	public String getPortalPassword_AD() {
		return property.getProperty("portalPassword");
	}
	
}