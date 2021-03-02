// Copyright 2016-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.medfusion.common.utils.IHGUtil;

public class PropertyFileLoader {
	private static Properties property = new Properties();

	public PropertyFileLoader() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";
		InputStream url = ClassLoader.getSystemResourceAsStream("data-driven/" + propertyFileNameString);
		property.load(url);

	}

	// CCD
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

	public String getRestUrlV3CCD() {
		return property.getProperty("restUrlV3CCD");
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

	// AMDC
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

	public String getRestUrlV3AMDC() {
		return property.getProperty("restUrlV3AMDC");
	}

	public String getReadCommunicationUrlV3AMDC() {
		return property.getProperty("readCommunicationUrlV3AMDC");
	}

	// MU2 EVENTS
	public String getPULLAPI_URL_MU2() {
		return property.getProperty("pullAPI_URL_MU2");
	}

	public String getPULLAPI_URLV3_MU2() {
		return property.getProperty("pullAPI_URLV3_MU2");
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

	// Guardian New
	public String getPatientUA_MU2() {
		return property.getProperty("patientUA_MU2");
	}

	public String getGuardian_UserName_MU2() {
		return property.getProperty("guardian_UserName_MU2");
	}

	public String getGuardian_Password_MU2() {
		return property.getProperty("guardian_Password_MU2");
	}

	public String getPatient_ExternalPatientID_MU2() {
		return property.getProperty("patientUA_ExternalPatientID_MU2");
	}

	public String getPatientIntuItID_Guardian() {
		return property.getProperty("intuit_PATIENT_ID_MU2_Guardian");
	}

	// Guardian Existing
	public String getPatientUA_MU2_Existing() {
		return property.getProperty("patientUA_MU2_Existing");
	}

	public String getGuardian_UserName_MU2_Existing() {
		return property.getProperty("guardian_UserName_MU2_Existing");
	}

	public String getGuardian_Password_MU2_Existing() {
		return property.getProperty("guardian_Password_MU2_Existing");
	}

	public String getPatient_ExternalPatientID_MU2_Existing() {
		return property.getProperty("patientUA_ExternalPatientID_MU2_Existing");
	}

	public String getPatientIntuItID_GuardianExisting() {
		return property.getProperty("intuit_PATIENT_ID_MU2_Guardian_Existing");
	}

	public String getSecureEmailTransmitActivity() {
		return property.getProperty("secureEmailTransmitActivity");
	}

	public String getStandardEmailTransmitActivity() {
		return property.getProperty("standardEmailTransmitActivity");
	}

	public String getPatientUA_MU2_LastName() {
		return property.getProperty("patientUA_LastName");
	}

	public String getPatientUA_MU2_LastName_Existing() {
		return property.getProperty("patientUA_LastName_Existing");
	}

	public String getPdfFilePath() {
		return property.getProperty("pdfFile");
	}

	// PIDC
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

	public String getPatientID() {
		return property.getProperty("patientID");
	}

	public String getRestUrl1_20() {
		return property.getProperty("restUrl1_20");
	}

	public String getRestUrl2_20() {
		return property.getProperty("restUrl2_20");
	}

	public String getRestUrl3_20() {
		return property.getProperty("restUrl3_20");
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

	public String getPracticeUserName() {
		return property.getProperty("practiceUserName");
	}

	public String getPracticePassword() {
		return property.getProperty("practicePassword");
	}

	public String getCity() {
		return property.getProperty("city");
	}

	public String getState() {
		return property.getProperty("state");
	}

	public String getLastName() {
		return property.getProperty("lnameSC");
	}

	public String getLanguageType() {
		return property.getProperty("preferredLanguage");
	}
	// PIDC END

	// Appointment Data Request from partner
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

	// Start POST STATEMENT & Get EVENT
	// Set Payload Atttributes

	public String getStatementFormat_SE() {
		return property.getProperty("statementFormat_SE");
	}

	public String getAddress1_SE() {
		return property.getProperty("address1_SE");
	}

	public String getAddress2_SE() {
		return property.getProperty("address2_SE");
	}

	public String getCity_SE() {
		return property.getProperty("city_SE");
	}

	public String getState_SE() {
		return property.getProperty("state_SE");
	}

	public String getZipCode_SE() {
		return property.getProperty("zipCode_SE");
	}

	public String getNewCharges_SE() {
		return property.getProperty("newCharges_SE");
	}

	public String getTotalCharges_SE() {
		return property.getProperty("totalCharges_SE");
	}

	public String getAmountDue_SE() {
		return property.getProperty("amountDue_SE");
	}

	public String getBalanceForwardType_SE() {
		return property.getProperty("balanceForwardType_SE");
	}

	public String getBalanceForwardAmount_SE() {
		return property.getProperty("balanceForwardAmount_SE");
	}

	public String getOutstandingBalance_SE() {
		return property.getProperty("outstandingBalance_SE");
	}

	public String getStatementComment_SE() {
		return property.getProperty("statementComment_SE");
	}

	public String getDunningMessage_SE() {
		return property.getProperty("dunningMessage_SE");
	}

	public String getPracticeProviderName_SE() {
		return property.getProperty("practiceProviderName_SE");
	}

	// set application attributes
	public String getUrl_SE() {
		return property.getProperty("url_SE");
	}

	public String getUserName_SE() {
		return property.getProperty("userName_SE");
	}

	public String getPassword_SE() {
		return property.getProperty("password_SE");
	}

	public String getEmail_SE() {
		return property.getProperty("email_SE");
	}

	public String getRestUrl_SE() {
		return property.getProperty("restUrl_SE");
	}

	public String getRestV3Url_SE() {
		return property.getProperty("restv3Url_SE");
	}

	public String getOAuthAppToken_SE() {
		return property.getProperty("oAuthAppToken_SE");
	}

	public String getOAuthUsername_SE() {
		return property.getProperty("oAuthUsername_SE");
	}

	public String getOAuthPassword_SE() {
		return property.getProperty("oAuthPassword_SE");
	}

	public String getFirstName_SE() {
		return property.getProperty("firstName_SE");
	}

	public String getLastName_SE() {
		return property.getProperty("lastName_SE");
	}

	public String getPatientID_SE() {
		return property.getProperty("patientID_SE");
	}

	public String getMFPatientID_SE() {
		return property.getProperty("mfPatientID_SE");
	}

	public String getStatementEventURL() {
		return property.getProperty("statementEventURL");
	}

	public String getStatementEventV3URL() {
		return property.getProperty("statementEventV3URL");
	}

	public String getPaymentDueDate() {
		return property.getProperty("paymentDueDate");
	}

	public String getRestUrlPIDC_SE() {
		return property.getProperty("restUrlPIDC_SE");
	}

	public String getPracticeName_SE() {
		return property.getProperty("practiceName_SE");
	}

	public String getStatementEmailSubject() {
		return property.getProperty("statementEmailSubject");
	}

	public String getPortalUserName_SE() {
		return property.getProperty("portalUserName_SE");
	}

	public String getPortalPassword_SE() {
		return property.getProperty("portalPassword_SE");
	}

	// BULK

	public String getOAuthPropertyAMDC() {
		return property.getProperty("oAuthPropertyAMDC");
	}

	public String getOAuthKeyStoreAMDC() {
		return property.getProperty("oAuthKeyStoreAMDC");
	}

	public String getSecureMessagePathAMDC() {
		return property.getProperty("secureMessagePathAMDC");
	}

	public String getSecureMsgAskAStaff() {
		return property.getProperty("secureMsgAskAStaff");
	}

	public String getBatch_SecureMessage() {
		return property.getProperty("batch_secureMessage");
	}

	public String getUserName1AMDC() {
		return property.getProperty("userName1AMDC");
	}

	public String getFrom1AMDC() {
		return property.getProperty("from1AMDC");
	}

	public String getUserName2AMDC() {
		return property.getProperty("userName2AMDC");
	}

	public String getUserName3AMDC() {
		return property.getProperty("userName3AMDC");
	}

	public String getSender1AMDC() {
		return property.getProperty("sender1AMDC");
	}

	public String getSender2AMDC() {
		return property.getProperty("sender2AMDC");
	}

	public String getPatientName1AMDC() {
		return property.getProperty("patientName1AMDC");
	}

	public String getPatientName2AMDC() {
		return property.getProperty("patientName2AMDC");
	}

	public String getPatientName3AMDC() {
		return property.getProperty("patientName3AMDC");
	}

	public String getRestUrlBulk() {
		return property.getProperty("RestUrlBulk");
	}

	public String getAttachmentLocation() {
		return property.getProperty("AttachmentLocation");
	}

	public String getNumberOfAttachments() {
		return property.getProperty("NumberOfAttachments");
	}

	public String getMaxPatients() {
		return property.getProperty("MaxPatients");
	}

	public String getFileName() {
		return property.getProperty("FileName");
	}

	public String getSubject() {
		return property.getProperty("SubjectBulk");
	}

	public String getMessageBulk() {
		return property.getProperty("MessageBulk");
	}

	public String getresendPrevoiusMessage() {
		return property.getProperty("resendPrevoiusMessage");
	}

	public String getpreviousBulkMessageId() {
		return property.getProperty("previousBulkMessageId");
	}

	public String getNumberOfParams() {
		return property.getProperty("NumberOfParams");
	}

	public String getAddAttachment() {
		return property.getProperty("AddAttachment");
	}

	public String getParamName1() {
		return property.getProperty("ParamName1");
	}

	public String getParamValue1() {
		return property.getProperty("ParamValue1");
	}

	public String getParamValue2() {

		return property.getProperty("ParamValue2");
	}

	public String getParamName2() {
		return property.getProperty("ParamName2");
	}

	public String getParamValue3() {

		return property.getProperty("ParamValue3");
	}

	public String getParamName3() {
		return property.getProperty("ParamName3");
	}

	public String getBulkEmailType() {
		return property.getProperty("BulkEmailType");
	}

	public String getPatientExternalId() {
		return property.getProperty("PatientExternalId");
	}

	public String getUserNameBulk1() {
		return property.getProperty("UserNameBulk1");
	}

	public String getUserNameBulk2() {
		return property.getProperty("UserNameBulk2");
	}

	public String getUserNameBulk3() {
		return property.getProperty("UserNameBulk3");
	}

	public String getPasswordBulk1() {
		return property.getProperty("PasswordBulk1");
	}

	public String getPasswordBulk2() {
		return property.getProperty("PasswordBulk2");
	}

	public String getPasswordBulk3() {
		return property.getProperty("PasswordBulk3");
	}

	public String getUserPatiendIDBulk1() {
		return property.getProperty("PatientExternalId1");
	}

	public String getUserPatiendIDBulk2() {
		return property.getProperty("PatientExternalId2");
	}

	public String getUserPatiendIDBulk3() {
		return property.getProperty("PatientExternalId3");
	}

	public String getUserEmailBulk1() {
		return property.getProperty("PatientEmailId1");
	}

	public String getUserEmailBulk2() {
		return property.getProperty("PatientEmailId2");
	}

	public String getUserEmailBulk3() {
		return property.getProperty("PatientEmailId3");
	}

	public String getresendMessageToPatientUserName() {
		return property.getProperty("resendMessageToPatientUserName");
	}

	public String getresendMessageToPatientPassword() {
		return property.getProperty("resendMessageToPatientPassword");
	}

	public String getresendMessageToPatientID() {
		return property.getProperty("resendMessageToPatientID");
	}

	public String getresendMessageToPatientEmail() {
		return property.getProperty("resendMessageToPatientEmail");
	}

	public String getRestUrlV3Bulk() {
		return property.getProperty("RestUrlV3Bulk");
	}

	// SendDirectMessage

	public String getRestURL_SDM() {
		return property.getProperty("restURL_SDM");
	}

	public String getSecureEmailURL_SDM() {
		return property.getProperty("secureEmailURL_SDM");
	}

	public String getSecureEmailUsername_SDM() {
		return property.getProperty("secureEmailUsername_SDM");
	}

	public String getSecureEmailPassword_SDM() {
		return property.getProperty("secureEmailPassword_SDM");
	}

	public String getFromSecureEmailID_SDM() {
		return property.getProperty("fromSecureEmailID_SDM");
	}

	public String getToSecureEmailID_SDM() {
		return property.getProperty("toSecureEmailID_SDM");
	}

	public String getApplicationName_SDM() {
		return property.getProperty("applicationName_SDM");
	}

	public String getSubject_SDM() {
		return property.getProperty("subject_SDM");
	}

	public String getMessageBody_SDM() {
		return property.getProperty("messageBody_SDM");
	}

	public String getPatientId_SDM() {
		return property.getProperty("patientId_SDM");
	}

	public String getAttachmentType_SDM() {
		return property.getProperty("attachmentType_SDM");
	}

	public String getFileName_SDM() {
		return property.getProperty("fileName_SDM");
	}

	public String getAttachmentLocationXML_SDM() {
		return property.getProperty("attachmentLocationXML_SDM");
	}

	public String getAttachmentLocationPDF_SDM() {
		return property.getProperty("attachmentLocationPDF_SDM");
	}

	public String getAttachmentLocationPNG_SDM() {
		return property.getProperty("attachmentLocationPNG_SDM");
	}

	public String getOAuthAppToken_SDM() {
		return property.getProperty("oAuthAppToken_SDM");
	}

	public String getOAuthUsername_SDM() {
		return property.getProperty("oAuthUsername_SDM");
	}

	public String getOAuthPassword_SDM() {
		return property.getProperty("oAuthPassword_SDM");
	}

	public String getTOCName_SDM() {
		return property.getProperty("tocName_SDM");
	}

	public String getMessageStatus_SDM() {
		return property.getProperty("messageStatus_SDM");
	}

	public String getUnseenMessageHeaderURL_SDM() {
		return property.getProperty("unseenMessageHeaderURL");
	}

	public String getUnseenMessageBodyURL_SDM() {
		return property.getProperty("unseenMessageBodyURL");
	}

	public String getMessageStatusUpdate_SDM() {
		return property.getProperty("messageStatusUpdateURL");
	}

	public String getMessageStatusToUpdate_SDM() {
		return property.getProperty("messageStatusToUpdate");
	}

	public String getMessageHeaderURL_SDM() {
		return property.getProperty("messageHeaderURL");
	}

	public String getInvalidPracticeMessageHeaderURL_SDM() {
		return property.getProperty("invalidPracticeMessageHeaderURL");
	}

	public String getInvalidEmailMessageHeaderURL_SDM() {
		return property.getProperty("invalidEmailMessageHeaderURL");
	}

	public String getInvalidUID_SDM() {
		return property.getProperty("invalidUID");
	}

	public String getValidPracticeID_SDM() {
		return property.getProperty("validPracticeID");
	}

	// Directory Search
	public String getRestURL_SD() {
		return property.getProperty("restUrl_DS");
	}

	public String getDirectAddress_organization_DS() {
		return property.getProperty("directAddress_organization_DS");
	}

	public String getDirectAddress_provider_DS() {
		return property.getProperty("directAddress_provider_DS");
	}

	public String getOAuthAppToken_DS() {
		return property.getProperty("oAuthAppToken_DS");
	}

	public String getOAuthUsername_DS() {
		return property.getProperty("oAuthUsername_DS");
	}

	public String getOAuthPassword_DS() {
		return property.getProperty("oAuthPassword_DS");
	}

	public String getSearchLength() {
		return property.getProperty("searchLength");
	}

	public String getCSVFilePath_DS() {
		return property.getProperty("csvFilePath_DS");
	}

	// PatientFormsExportInfo
	public String geturl_FE() {
		return property.getProperty("url_FE");
	}

	public String getpracticeNew_URL_FE() {
		return property.getProperty("practiceNew_URL_FE");
	}

	public String getpatient_url_FE() {
		return property.getProperty("patient_url_FE");
	}

	public String getpatientfilepath_FE() {
		return property.getProperty("patientfilepath_FE");
	}

	public String getpatientFirstName_FE() {
		return property.getProperty("patientFirstName_FE");
	}

	public String getpatientLastName_FE() {
		return property.getProperty("patientLastName_FE");
	}

	public String getpatientEmailAddress1_FE() {
		return property.getProperty("patientEmailAddress1_FE");
	}

	public String getpatientEmailCity_FE() {
		return property.getProperty("patientEmailCity_FE");
	}

	public String getpatientphoneNumber_FE() {
		return property.getProperty("patientphoneNumber_FE");
	}

	public String getpatientDOBDay1_FE() {
		return property.getProperty("patientDOBDay1_FE");
	}

	public String getpatientDOBMonth_FE() {
		return property.getProperty("patientDOBMonth_FE");
	}

	public String getpatientDOBYear_FE() {
		return property.getProperty("patientDOBYear_FE");
	}

	public String getpatientDOBMonthText_FE() {
		return property.getProperty("patientDOBMonthText_FE");
	}

	public String getpatientZipCode1_FE() {
		return property.getProperty("getpatientZipCode1_FE");
	}

	public String getpatientSecretQuestion_FE() {
		return property.getProperty("patientSecretQuestion_FE");
	}

	public String getpatientuserid_FE() {
		return property.getProperty("patientuserid_FE");
	}

	public String getpatientPassword1_FE() {
		return property.getProperty("patientPassword1_FE");
	}

	public String getpatientSecretAnswer_FE() {
		return property.getProperty("patientSecretAnswer_FE");
	}

	public String getpatientAddress1_FE() {
		return property.getProperty("patientAddress1_FE");
	}

	public String getpatientAddress2_FE() {
		return property.getProperty("patientAddress2_FE");
	}

	public String getpatientCity_FE() {
		return property.getProperty("patientCity_FE");
	}

	public String getpatientState_FE() {
		return property.getProperty("patientState_FE");
	}

	public String getpatientHomePhoneNo_FE() {
		return property.getProperty("patientHomePhoneNo_FE");
	}

	public String getpatientethnicity_FE() {
		return property.getProperty("patientethnicity_FE");
	}

	public String getpatientMaritalStatus_FE() {
		return property.getProperty("patientMaritalStatus_FE");
	}

	public String getpatientWhoIs_FE() {
		return property.getProperty("patientWhoIs_FE");
	}

	public String getNameofPrimaryInsurance() {
		return property.getProperty("NameofPrimaryInsurance");
	}

	public String getNameofsecondaryInsurance() {

		return property.getProperty("NameofsecondaryInsurance");
	}

	public String getrelFirstNAme() {

		return property.getProperty("relFirstNAme");
	}

	public String getrelLastNAme() {

		return property.getProperty("relLastNAme");
	}

	public String getrelation1() {

		return property.getProperty("relation1");
	}

	public String getphonenumber1() {

		return property.getProperty("phonenumber1");
	}

	public String getphonetype1() {

		return property.getProperty("phonetype1");
	}

	public String gettetanus1() {

		return property.getProperty("tetanus1");
	}

	public String getHPV1() {

		return property.getProperty("HPV1");
	}

	public String getInfluenza1() {

		return property.getProperty("Influenza1");
	}

	public String getPneumonia1() {

		return property.getProperty("Pneumonia1");
	}

	public String getSurgeryName() {

		return property.getProperty("SurgeryName");
	}

	public String getSurgeryTimeFrame() {

		return property.getProperty("SurgeryTimeFrame");
	}

	public String getHospitalizationReason() {

		return property.getProperty("HospitalizationReason");
	}

	public String getHospitalizationTimeFrame() {

		return property.getProperty("HospitalizationTimeFrame");
	}

	public String getTest() {

		return property.getProperty("Test");
	}

	public String getTestTimeFrame() {

		return property.getProperty("TestTimeFrame");
	}

	public String getNameofDoctorSpeciality() {

		return property.getProperty("NameofDoctorSpeciality");
	}

	public String getNameDosage() {

		return property.getProperty("NameDosage");
	}

	public String getFamilyMember() {

		return property.getProperty("FamilyMember");
	}

	public String getOtherMedicalhistory() {

		return property.getProperty("OtherMedicalhistory");
	}

	public String getsex() {

		return property.getProperty("sex");
	}

	public String state1() {
		return property.getProperty("state1");
	}

	public String gettimes() {

		return property.getProperty("times");
	}

	public String getexercise() {

		return property.getProperty("exercise");
	}

	public String getccd_url1_FE() {
		return property.getProperty("ccd_url1_FE");
	}

	public String getoAuthAppTokenCCD1_FE() {
		return property.getProperty("oAuthAppTokenCCD1_FE");
	}

	public String getoAuthUsernameCCD1_FE() {
		return property.getProperty("oAuthUsernameCCD1_FE");
	}

	public String getoAuthPasswordCCD1_FE() {
		return property.getProperty("oAuthPasswordCCD1_FE");
	}

	public String getoAuthKeyStore1_FE() {
		return property.getProperty("oAuthAppTokenCCD1_FE");
	}

	public String getoAuthProperty1_FE() {
		return property.getProperty("oAuthProperty1_FE");
	}

	public String getresponsePath_CCD1_FE() {
		return property.getProperty("responsePath_CCD1_FE");
	}

	public String getpracticeURL_FE() {
		return property.getProperty("practiceURL_FE");
	}

	public String getpracticeUserName_FE() {
		return property.getProperty("practiceUserName_FE");

	}

	public String getpracticePassword_FE() {
		return property.getProperty("practicePassword_FE");
	}

	public String getpatientLocation_FE() {

		return property.getProperty("patientLocation_FE");
	}

	public String getpatientProvider_FE() {
		return property.getProperty("patientProvider_FE");

	}

	public String getresponsePDF_FE() {
		return property.getProperty("responsePDF_FE");
	}

	public String getccd_PDfUrl_FE() {
		return property.getProperty("ccd_PDfUrl_FE");
	}

	public String getresponsePDFBatch_FE() {
		return property.getProperty("responsePDFBatch_FE");
	}

	public String getUIPDFFile_FE() {
		return property.getProperty("uiPDFFile_FE");
	}

	public String getday() {

		return property.getProperty("day");
	}

	public String getDownloadFileLocation() {

		return property.getProperty("downloadFileLocation");
	}

	public String getPreCheckURL() {

		return property.getProperty("preCheckURL_FE");
	}

	public String getPreCheckPatientExternalID() {

		return property.getProperty("preCheckPatientExternalID_FE");
	}

	public String getAllowAttachment() {
		return property.getProperty("allowAttachment");
	}

	public String getCategoryType() {
		return property.getProperty("categoryType");
	}

	public String getFileNameAMDC() {
		return property.getProperty("fileNameAMDC");
	}

	public String getMimeType() {
		return property.getProperty("mineType");
	}

	public String getAttachmentBody() {
		return property.getProperty("attacmentBody");
	}

	public String getPortalCategoryType() {
		return property.getProperty("portalCategoryType");
	}

	public String getAppointmentPayload() {
		return property.getProperty("precheckAppointmentPayload");
	}

	public String getPreCheckAppointmentRestUrl() {
		return property.getProperty("appointmentRestURL");
	}

	public String getBasicAccesstokenPrecheck() {
		return property.getProperty("basicAcessTokenPrecheck");
	}

	public String getPreCheckZipCode() {
		return property.getProperty("preCheckZipCode");
	}

	public String getPreCheckDOB() {
		return property.getProperty("preCheckDOB");
	}

	public String getPreCheckPatientEmailID_FE() {
		return property.getProperty("preCheckPatientEmailID_FE");
	}

	public String getPreCheckPatientFirstName_FE() {
		return property.getProperty("preCheckPatientFirstName_FE");
	}

	public String getPreCheckGetPIDC() {
		return property.getProperty("preCheckGetPIDC");
	}

	public String getPreCheckEmailSubject() {
		return property.getProperty("preCheckEmailSubject");
	}

	public String getPreCheckEmailLink() {
		return property.getProperty("preCheckEmailLink");
	}

	public String getPreCheckInsuranceImageType() {
		return property.getProperty("preCheckImageInsuranceType");
	}

	public String getBalanceUrl() {
		return property.getProperty("balanceUrl");
	}

	public String getBalanceIntegrationPracticeId() {
		return property.getProperty("balanceIntegrationPracticeId");
	}

	public String getNewPassword() {
		return property.getProperty("newPassword");
	}

	public String getEmailSubjectBalance() {
		return property.getProperty("emailSubjectBalance");
	}

	public String getEmailLinkText() {
		return property.getProperty("emailLinkText");
	}

	public String getToken_MU() {
		return property.getProperty("token_MU");
	}

	public String getDeleteToken() {
		return property.getProperty("token_p2p");
	}

	// DB
	public String getPostGREdbHostName() {
		return property.getProperty("PostGREdbHostName");
	}

	public String getPostGREdbName() {
		return property.getProperty("PostGREdbName");
	}

	public String getPostGREdbUserName() {
		return property.getProperty("PostGREdbUserName");
	}

	public String getPostGREdbPassword() {
		return property.getProperty("PostGREdbPassword");
	}

	public String getCoreMSSQLdbHostName() {
		return property.getProperty("CoredbHostName");
	}

	public String getCoreMSSQLdbName() {
		return property.getProperty("CoredbName");
	}

	public String getCoreMSSQLdbUserName() {
		return property.getProperty("CoredbUserName");
	}

	public String getCoreMSSQLdbPassword() {
		return property.getProperty("CoredbPassword");
	}

	public String getMFMSSQLdbHostName() {
		return property.getProperty("MFAgentdbHostName");
	}

	public String getMFMSSQLdbName() {
		return property.getProperty("MFAgentdbName");
	}

	public String getMFMSSQLdbUserName() {
		return property.getProperty("MFAgentdbUserName");
	}

	public String getMFMSSQLdbPassword() {
		return property.getProperty("MFAgentdbPassword");
	}

	public String getMFOracleSQLdbHostName() {
		return property.getProperty("ConsumerdbHostName");
	}

	public String getMFOracleSQLdbName() {
		return property.getProperty("ConsumerdbName");
	}

	public String getMFOracleSQLdbUserName() {
		return property.getProperty("ConsumerdbUserName");
	}

	public String getMFOracleSQLdbPassword() {
		return property.getProperty("ConsumerdbPassword");
	}

	// MF API Url
	public String getProcessingURL() {
		return property.getProperty("processingURL");
	}

	public String getPIDCURL() {
		return property.getProperty("GetPIDCURL");
	}

	public String getPhoneNumber() {
		return property.getProperty("phoneNumber");
	}

	// Practice Portal URL
	public String getDoctorLogin() {
		return property.getProperty("doctorLogin");
	}

	public String getDoctorPassword() {
		return property.getProperty("doctorPassword");
	}

	public String getPortalUrl() {
		return property.getProperty("portalUrl");
	}

	public String getDOBDay() {
		return property.getProperty("DOBDay");
	}

	public String getDOBMonth() {
		return property.getProperty("DOBMonth");
	}

	public String getDOBMonthText() {
		return property.getProperty("DOBMonthText");
	}

	public String getDOBYear() {
		return property.getProperty("DOBYear");
	}

	public String getDOBYearUnderage() {
		return property.getProperty("DOBYearUnderage");
	}

	// NG API
	public String getNGAPIexecutionMode() {
		return property.getProperty("NGAPIexecutionMode");
	}

	public String getNGAPIQAMainPracticeID() {
		return property.getProperty("NGAPIQAMainPracticeID");
	}

	public String getNGAPIQAMainEnterpriseID() {
		return property.getProperty("NGAPIQAMainEnterpriseID");
	}

	public String getNGAPISITPracticeID() {
		return property.getProperty("NGAPISITPracticeID");
	}

	public String getNGAPISITEnterpriseID() {
		return property.getProperty("NGAPISITEnterpriseID");
	}

	public String getProperty(String prop) throws NullPointerException {
		if (property.getProperty(prop) == null)
			throw new NullPointerException("Property " + prop + " not found in the property file.");
		return property.getProperty(prop);
	}

	public String getNGEPMProviderName() {
		return property.getProperty("EPMProviderName");
	}

	public String getNGEPMLocationName() {
		return property.getProperty("EPMLocationName");
	}

	public String getNGEPMRenderingProviderName() {
		return property.getProperty("EPMRenderingProviderName");
	}

	// NG Enterprise Enrollment
	public String getNGEnterpiseEnrollmentE1() {
		return property.getProperty("NGEnterpiseEnrollmentEnterprise1");
	}

	public String getNGEnterpiseEnrollmentE2() {
		return property.getProperty("NGEnterpiseEnrollmentEnterprise2");
	}

	public String getNGEnterpiseEnrollmentE1P1() {
		return property.getProperty("NGEnterprise1Practice1");
	}

	public String getNGEnterpiseEnrollmentE1P2() {
		return property.getProperty("NGEnterprise1Practice2");
	}

	public String getNGEnterpiseEnrollmentE1P3() {
		return property.getProperty("NGEnterprise1Practice3");
	}

	public String getNGEnterpiseEnrollmentE1P4() {
		return property.getProperty("NGEnterprise1Practice4");
	}

	public String getNGEnterpiseEnrollmentE1P5() {
		return property.getProperty("NGEnterprise1Practice5");
	}

	public String getNGEnterpiseEnrollmentE2P1() {
		return property.getProperty("NGEnterprise2Practice1");
	}

	public String getNGEnterpiseEnrollmentE2P2() {
		return property.getProperty("NGEnterprise2Practice2");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P1() {
		return property.getProperty("integrationPracticeIDE1P1");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P2() {
		return property.getProperty("integrationPracticeIDE1P2");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P3() {
		return property.getProperty("integrationPracticeIDE1P3");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P4() {
		return property.getProperty("integrationPracticeIDE1P4");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P5() {
		return property.getProperty("integrationPracticeIDE1P5");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE2P1() {
		return property.getProperty("integrationPracticeIDE2P1");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE2P2() {
		return property.getProperty("integrationPracticeIDE2P2");
	}

	public String getNGE1P1Provider() {
		return property.getProperty("NGE1P1Provider");
	}

	public String getNGE1P2Provider() {
		return property.getProperty("NGE1P2Provider");
	}

	public String getNGE1P3Provider() {
		return property.getProperty("NGE1P3Provider");
	}

	public String getNGE1P4Provider() {
		return property.getProperty("NGE1P4Provider");
	}

	public String getNGE1P5Provider() {
		return property.getProperty("NGE1P5Provider");
	}

	public String getNGE2P1Provider() {
		return property.getProperty("NGE2P1Provider");
	}

	public String getNGE2P2Provider() {
		return property.getProperty("NGE2P2Provider");
	}

	public String getNGE1P1Location() {
		return property.getProperty("NGE1P1Location");
	}

	public String getNGE1P2Location() {
		return property.getProperty("NGE1P2Location");
	}

	public String getNGE1P3Location() {
		return property.getProperty("NGE1P3Location");
	}

	public String getNGE1P4Location() {
		return property.getProperty("NGE1P4Location");
	}

	public String getNGE1P5Location() {
		return property.getProperty("NGE1P5Location");
	}

	public String getNGE2P1Location() {
		return property.getProperty("NGE2P1Location");
	}

	public String getNGE2P2Location() {
		return property.getProperty("NGE2P2Location");
	}

	// AppoinmentType
	public String getAppointmentTypeName() {
		return property.getProperty("AppointmentTypeName");
	}

	public String getAppointmentTypeID() {
		return property.getProperty("AppointmentTypeID");
	}

	public String getAppointmentCategoryName() {
		return property.getProperty("AppointmentCategoryName");
	}

	public String getAppointmentCategoryID() {
		return property.getProperty("AppointmentCategoryID");
	}

	public String getActiveFlag() {
		return property.getProperty("ActiveFlag");
	}

	public String getComment() {
		return property.getProperty("Comment");
	}

	public String getAppointmentTypeUrl() {
		return property.getProperty("AppointmentTypeUrl");
	}

	// Pharmacies
	public String getOAuthAppToken_PH() {
		return property.getProperty("oAuthAppToken_PH");
	}

	public String getOAuthUsername_PH() {
		return property.getProperty("oAuthUsername_PH");
	}

	public String getoAuthPassword_PH() {
		return property.getProperty("oAuthPassword_PH");
	}

	public String getPatientUsername_PH() {
		return property.getProperty("usename_PH");
	}

	public String getPatientPassword_PH() {
		return property.getProperty("password_PH");
	}

	public String getPharmacyRenewalUrl() {
		return property.getProperty("PharmacyRenewalUrl");
	}

	public String getpatientUrl() {
		return property.getProperty("patientUrl");
	}

	public String getPharmacyName() {
		return property.getProperty("PharmacyName");
	}

	public String getExternalPharmacyId() {
		return property.getProperty("ExternalPharmacyId");
	}

	public String getLine1() {
		return property.getProperty("Line1");
	}

	public String getLine2() {
		return property.getProperty("Line2");
	}

	public String getCity_PH() {
		return property.getProperty("City_PH");
	}

	public String getState_PH() {
		return property.getProperty("State_PH");
	}

	public String getCountry() {
		return property.getProperty("Country");
	}

	public String getZipCode_PH() {
		return property.getProperty("ZipCode_PH");
	}

	public String getPharmacyPhone() {
		return property.getProperty("PharmacyPhone");
	}

	public String getPharmacyFaxNumber() {
		return property.getProperty("PharmacyFaxNumber");
	}

	public String getAppointmentRequestV3URL() {
		return property.getProperty("appointmentRequestV3URL");
	}

	public String getPATIENT_INVITE_RESTV3URL() {
		return property.getProperty("restUrlPIDCV3");
	}

	public String getCCDPATHLargeSize() {
		return property.getProperty("ccdXMLPathLargeSize");
	}
}
