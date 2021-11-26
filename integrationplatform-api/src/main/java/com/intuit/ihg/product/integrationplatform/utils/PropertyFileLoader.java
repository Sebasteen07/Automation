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
		return property.getProperty("response.path");
	}

	public String getOAuthProperty() {
		return property.getProperty("oauth.property");
	}

	public String getOAuthKeyStore() {
		return property.getProperty("oauth.keystore");
	}

	public String getPHR_URL() {
		return property.getProperty("phr.url");
	}

	public String getRestUrlCCD() {
		return property.getProperty("resturl.ccd");
	}

	public String getRestUrlV3CCD() {
		return property.getProperty("resturlv3.ccd");
	}

	public String getEPracticeNameCCD() {
		return property.getProperty("epracticename.ccd");
	}

	public String getCCDXMLPath() {
		return property.getProperty("ccdxml.path");
	}

	public String getCCDPATHLargeSize() {
		return property.getProperty("ccdxml.path.largesize");
	}

	public String getCCDPath() {
		return property.getProperty("ccd.path");
	}

	public String getCCDZIP() {
		return property.getProperty("zip.ccd");
	}

	public String getUserNameCCD() {
		return property.getProperty("username.ccd");
	}

	public String getPasswordCCD() {
		return property.getProperty("password.ccd");
	}

	public String getdeviceNameCCD() {
		return property.getProperty("devicename.ccd");
	}

	public String getdeviceVersionCCD() {
		return property.getProperty("deviceversion.ccd");
	}

	public String getvendorNameCCD() {
		return property.getProperty("vendorname.ccd");
	}

	public String getMessageIdCCD() {
		return property.getProperty("messageid.ccd");
	}

	public String getPrefixCCD() {
		return property.getProperty("prefix.ccd");
	}

	public String getFirstNameCCD() {
		return property.getProperty("firstname.ccd");
	}

	public String getMiddleNameCCD() {
		return property.getProperty("middlename.ccd");
	}

	public String getLastNameCCD() {
		return property.getProperty("lastname.ccd");
	}

	public String getLine1CCD() {
		return property.getProperty("line1.ccd");
	}

	public String getLine2CCD() {
		return property.getProperty("line2.ccd");
	}

	public String getCityCCD() {
		return property.getProperty("city.ccd");
	}

	public String getStateCCD() {
		return property.getProperty("state.ccd");
	}

	public String getCountryCCD() {
		return property.getProperty("country.ccd");
	}

	public String getPracticePatientIdCCD() {
		return property.getProperty("practice.patientid");
	}

	public String getOAuthAppTokenCCD() {
		return property.getProperty("oauth.apptoken.ccd");
	}

	public String getOAuthUsernameCCD() {
		return property.getProperty("oauth.username.ccd");
	}

	public String getOAuthPasswordCCD() {
		return property.getProperty("oauth.password.ccd");
	}

	public String getUrlCCD() {
		return property.getProperty("url.ccd");
	}

	public String getFromCCD() {
		return property.getProperty("from.ccd");
	}

	public String getIntegrationPracticeIDCCD() {
		return property.getProperty("integration.practiceid.ccd");
	}

	// AMDC
	public String geturlAMDC() {
		return property.getProperty("url.amdc");
	}

	public String getOAuthAppTokenAMDC() {
		return property.getProperty("oauth.apptoken.amdc");
	}

	public String getOAuthUsernameAMDC() {
		return property.getProperty("oauth.username.amdc");
	}

	public String getOAuthPasswordAMDC() {
		return property.getProperty("oauth.password.amdc");
	}

	public String getUserNameAMDC() {
		return property.getProperty("username.amdc");
	}

	public String getPasswordAMDC() {
		return property.getProperty("password.amdc");
	}

	public String getRestUrlAMDC() {
		return property.getProperty("resturl.amdc");
	}

	public String getResponsePathAMDC() {
		return property.getProperty("response.path.amdc");
	}

	public String getFromAMDC() {
		return property.getProperty("from.amdc");
	}

	public String getReadCommunicationUrlAMDC() {
		return property.getProperty("readcommunicationurl.amdc");
	}

	public String getSender3AMDC() {
		return property.getProperty("sender3.amdc");
	}

	public String getIntegrationPracticeID() {
		return property.getProperty("integrationpracticeid.amdc");
	}

	public String getGmailUserNameAMDC() {
		return property.getProperty("gmail.username.amdc");
	}

	public String getGmailPasswordAMDC() {
		return property.getProperty("gmail.password.amdc");
	}

	public String getAllowReply() {
		return property.getProperty("allow.reply");
	}

	public String getMessageAMDC() {
		return property.getProperty("message.amdc");
	}

	public String getPatientExternalIdAMDC() {
		return property.getProperty("patient.externalid.amdc");
	}

	public String getRestUrlV3AMDC() {
		return property.getProperty("resturlv3.amdc");
	}

	public String getReadCommunicationUrlV3AMDC() {
		return property.getProperty("readcommunicationurlv3.amdc");
	}

	// MU2 EVENTS
	public String getPULLAPI_URL_MU2() {
		return property.getProperty("pullapi.url.mu2");
	}

	public String getPULLAPI_URLV3_MU2() {
		return property.getProperty("pullapi.urlv3.mu2");
	}

	public String getOAuthAppToken_MU2() {
		return property.getProperty("oauth.apptoken.mu2");
	}

	public String getOAuthUsername_MU2() {
		return property.getProperty("oauth.username.mu2");
	}

	public String getOAuthPassword_MU2() {
		return property.getProperty("oauth.password.mu2");
	}

	public String getPUSHAPI_URL_MU2() {
		return property.getProperty("pushapi.url.mu2");
	}

	public String getPORTAL_URL_MU2() {
		return property.getProperty("portal.url.mu2");
	}

	public String getPORTAL_USERNAME_MU2() {
		return property.getProperty("portal.username.mu2");
	}

	public String getPORTAL_PASSWORD_MU2() {
		return property.getProperty("portal.password.mu2");
	}

	public String getINTUIT_PATIENT_ID_MU2() {
		return property.getProperty("intuit.patient.id.mu2");
	}

	public String getTRANSMIT_EMAIL_MU2() {
		return property.getProperty("transmit.email.mu2");
	}

	public String getIMAGE_PATH_MU2() {
		return property.getProperty("image.path.mu2");
	}

	public String getPatient_ID_MU2() {
		return property.getProperty("patient.id.mu2");
	}

	public String getPATIENT_INVITE_RESTURL() {
		return property.getProperty("resturl.pidc");
	}

	public String getPATIENT_PRACTICEID() {
		return property.getProperty("practice.id.pidc");
	}

	public String getPATIENT_EXTERNAL_ID() {
		return property.getProperty("externalsystemid.pidc");
	}

	public String getCCDPATH1() {
		return property.getProperty("ccdxml.path1");
	}

	public String getCCDPATH2() {
		return property.getProperty("ccdxml.path2");
	}

	public String getCCDPATH3() {
		return property.getProperty("ccdxml.path3");
	}

	public String getCCDPATH4() {
		return property.getProperty("ccdxml.path4");
	}

	public String getHomePhoneNo() {
		return property.getProperty("home.phoneno");
	}

	public String getSecretQuestion() {
		return property.getProperty("secret.question");
	}

	public String getSecretAnswer() {
		return property.getProperty("secret.answer");
	}

	public String getBirthDay() {
		return property.getProperty("birthday");
	}

	public String getPatientPassword() {
		return property.getProperty("patient.password");
	}

	public String getCCDMessageID1() {
		return property.getProperty("ccdmessageid1");
	}

	public String getCCDMessageID2() {
		return property.getProperty("ccdmessageid2");
	}

	public String getPatientExternalId_MU2() {
		return property.getProperty("patient.externalid.mu2");
	}

	public String getPatientFirstName_MU2() {
		return property.getProperty("patientfirstname.mu2");
	}

	public String getPatientLastName_MU2() {
		return property.getProperty("patientlastname.mu2");
	}

	// Guardian New
	public String getPatientUA_MU2() {
		return property.getProperty("patientua.mu2");
	}

	public String getGuardian_UserName_MU2() {
		return property.getProperty("guardian.username.mu2");
	}

	public String getGuardian_Password_MU2() {
		return property.getProperty("guardian.password.mu2");
	}

	public String getPatient_ExternalPatientID_MU2() {
		return property.getProperty("patientua.externalpatientid.mu2");
	}

	public String getPatientIntuItID_Guardian() {
		return property.getProperty("intuit.patient.id.mu2.guardian");
	}

	// Guardian Existing
	public String getPatientUA_MU2_Existing() {
		return property.getProperty("patientua.mu2.existing");
	}

	public String getGuardian_UserName_MU2_Existing() {
		return property.getProperty("guardian.username.mu2.existing");
	}

	public String getGuardian_Password_MU2_Existing() {
		return property.getProperty("guardian.password.mu2.existing");
	}

	public String getPatient_ExternalPatientID_MU2_Existing() {
		return property.getProperty("patientua.externalpatientid.mu2.existing");
	}

	public String getPatientIntuItID_GuardianExisting() {
		return property.getProperty("intuit.patient.id.mu2.guardian.existing");
	}

	public String getSecureEmailTransmitActivity() {
		return property.getProperty("secure.emailtransmit.activity");
	}

	public String getStandardEmailTransmitActivity() {
		return property.getProperty("standard.emailtransmit.activity");
	}

	public String getPatientUA_MU2_LastName() {
		return property.getProperty("patientua.lastname");
	}

	public String getPatientUA_MU2_LastName_Existing() {
		return property.getProperty("patientua.lastname.existing");
	}

	public String getPdfFilePath() {
		return property.getProperty("pdffile");
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
		return property.getProperty("resturl");
	}

	public String getRestUrl1() {
		return property.getProperty("resturl1");
	}

	public String getRestUrl2() {
		return property.getProperty("resturl2");
	}

	public String getPatientPath() {
		return property.getProperty("patient.path");
	}

	public String getOAuthAppToken() {
		return property.getProperty("oauth.apptoken");
	}

	public String getOAuthUsername() {
		return property.getProperty("oauth.username");
	}

	public String getOAuthPassword() {
		return property.getProperty("oauth.password");
	}

	public String getBirthday() {
		return property.getProperty("birthday");
	}

	public String getZipCode() {
		return property.getProperty("zipcode");
	}

	public String getSSN() {
		return property.getProperty("ssn");
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
		return property.getProperty("preferred.language");
	}

	public String getEthnicity() {
		return property.getProperty("ethnicity");
	}

	public String getMaritalStatus() {
		return property.getProperty("marital.status");
	}

	public String getChooseCommunication() {
		return property.getProperty("choose.communication");
	}

	public String getInsurance_Type() {
		return property.getProperty("insurance.type");
	}

	public String getBatchSize() {
		return property.getProperty("batch.size");
	}

	public String getPracticeURL() {
		return property.getProperty("practiceurl");
	}

	public String getPortalVersion() {
		return property.getProperty("portal.version");
	}

	public String getCSVFilePath() {
		return property.getProperty("csvfile.path");
	}

	public String getPracticeId_PIDC() {
		return property.getProperty("practiceid.pidc");
	}

	public String getPatientID() {
		return property.getProperty("patientid");
	}

	public String getRestUrl1_20() {
		return property.getProperty("resturl1.20");
	}

	public String getRestUrl2_20() {
		return property.getProperty("resturl2.20");
	}

	public String getRestUrl3_20() {
		return property.getProperty("resturl3.20");
	}
	
	public String getRestUrl4_20() {
		return property.getProperty("resturl4.20");
	}

	public String getPracticeId_PIDC_20() {
		return property.getProperty("practiceid.pidc.20");
	}

	public String getOAuthAppToken_20() {
		return property.getProperty("oauth.apptoken.20");
	}

	public String getOAuthUsername_20() {
		return property.getProperty("oauth.username.20");
	}

	public String getOAuthPassword_20() {
		return property.getProperty("oauth.password.20");
	}

	public String getStandard_Email() {
		return property.getProperty("standard.email");
	}

	public String getPracticeUserName() {
		return property.getProperty("practice.username");
	}

	public String getPracticePassword() {
		return property.getProperty("practice.password");
	}

	public String getCity() {
		return property.getProperty("city");
	}

	public String getState() {
		return property.getProperty("state");
	}

	public String getLastName() {
		return property.getProperty("lnamesc");
	}

	public String getLanguageType() {
		return property.getProperty("preferred.language");
	}
	
	public String getTestPatientIDUserName() {
			return property.getProperty("username");
	}
	
	public String getPrecheckSubscriberPatientRestURL() {
		return property.getProperty("rest.precheck.subscriber");
	}
	public String getSubscriberPracticeID() {
		return property.getProperty("subscriber.praticeid");
	}
	
	// PIDC END

	// Appointment Data Request from partner
	public String getAppointmentRequestURL() {
		return property.getProperty("appointment.request.url");
	}

	public String getOAuthAppToken_AD() {
		return property.getProperty("oauth.apptoken.ad");
	}

	public String getOAuthUsername_AD() {
		return property.getProperty("oauth.username.ad");
	}

	public String getoAuthPassword_AD() {
		return property.getProperty("oauth.password.ad");
	}

	public String getIntegrationPracticeID_AD() {
		return property.getProperty("integrationpracticeid.ad");
	}

	public String getMedfusionPracticeId_AD() {
		return property.getProperty("medfusionpracticeid.ad");
	}

	public String getUserName_AD() {
		return property.getProperty("username.ad");
	}

	public String getPassword_AD() {
		return property.getProperty("password.ad");
	}

	public String getPatientPracticeID_AD() {
		return property.getProperty("patientpracticeid.ad");
	}

	public String getMedfusionPatientId_AD() {
		return property.getProperty("medfusionpatientid.ad");
	}

	public String getEmail_AD() {
		return property.getProperty("email.ad");
	}

	public String getProviderIdentifier_AD() {
		return property.getProperty("provideridentifier.ad");
	}

	public String getProviderName_AD() {
		return property.getProperty("providername.ad");
	}

	public String getAppointmentTime_AD() {
		return property.getProperty("appointmenttime.ad");
	}

	public String getAppointmentLocation_AD() {
		return property.getProperty("appointmentlocation.ad");
	}

	public String getAppointmentStatus_AD() {
		return property.getProperty("appointmentstatus.ad");
	}

	public String getAppointmentDescription_AD() {
		return property.getProperty("appointmentdescription.ad");
	}

	public String getAppointmentReason_AD() {
		return property.getProperty("appointmentreason.ad");
	}

	public String getAppointmentType_AD() {
		return property.getProperty("appointmenttype.ad");
	}

	public String getPracticeURL_AD() {
		return property.getProperty("practiceurl.ad");
	}

	public String getPreviousAppointmentId() {
		return property.getProperty("previousappointmentid");
	}

	public String getFirstName_AD() {
		return property.getProperty("firstname.ad");
	}

	public String getLastName_AD() {
		return property.getProperty("lastname.ad");
	}

	public String getBatchSize_AD() {
		return property.getProperty("batch.size.ad");
	}

	public String getCSVFILEPATH_AD() {
		return property.getProperty("csvfile.path.ad");
	}

	public String getPracticePortalURL_AD() {
		return property.getProperty("portalurl");
	}

	public String getProtalUserName_AD() {
		return property.getProperty("portal.username");
	}

	public String getPortalPassword_AD() {
		return property.getProperty("portal.password");
	}

	// Start POST STATEMENT & Get EVENT
	// Set Payload Atttributes

	public String getStatementFormat_SE() {
		return property.getProperty("statementformat.se");
	}

	public String getAddress1_SE() {
		return property.getProperty("address1.se");
	}

	public String getAddress2_SE() {
		return property.getProperty("address2.se");
	}

	public String getCity_SE() {
		return property.getProperty("city.se");
	}

	public String getState_SE() {
		return property.getProperty("state.se");
	}

	public String getZipCode_SE() {
		return property.getProperty("zipcode.se");
	}

	public String getNewCharges_SE() {
		return property.getProperty("newcharges.se");
	}

	public String getTotalCharges_SE() {
		return property.getProperty("totalcharges.se");
	}

	public String getAmountDue_SE() {
		return property.getProperty("amountdue.se");
	}

	public String getBalanceForwardType_SE() {
		return property.getProperty("balanceforwardtype.se");
	}

	public String getBalanceForwardAmount_SE() {
		return property.getProperty("balanceforwardamount.se");
	}

	public String getOutstandingBalance_SE() {
		return property.getProperty("outstandingbalance.se");
	}

	public String getStatementComment_SE() {
		return property.getProperty("statementcomment.se");
	}

	public String getDunningMessage_SE() {
		return property.getProperty("dunningmessage.se");
	}

	public String getPracticeProviderName_SE() {
		return property.getProperty("practice.provider.name.se");
	}

	// set application attributes
	public String getUrl_SE() {
		return property.getProperty("url.se");
	}

	public String getUserName_SE() {
		return property.getProperty("username.se");
	}

	public String getPassword_SE() {
		return property.getProperty("password.se");
	}

	public String getEmail_SE() {
		return property.getProperty("email.se");
	}

	public String getRestUrl_SE() {
		return property.getProperty("resturl.se");
	}

	public String getRestV3Url_SE() {
		return property.getProperty("restv3url.se");
	}

	public String getOAuthAppToken_SE() {
		return property.getProperty("oauth.apptoken.se");
	}

	public String getOAuthUsername_SE() {
		return property.getProperty("oauth.username.se");
	}

	public String getOAuthPassword_SE() {
		return property.getProperty("oauth.password.se");
	}

	public String getFirstName_SE() {
		return property.getProperty("firstname.se");
	}

	public String getLastName_SE() {
		return property.getProperty("lastname.se");
	}

	public String getPatientID_SE() {
		return property.getProperty("patientid.se");
	}

	public String getMFPatientID_SE() {
		return property.getProperty("mfpatientid.se");
	}

	public String getStatementEventURL() {
		return property.getProperty("statement.event.url");
	}

	public String getStatementEventV3URL() {
		return property.getProperty("statement.event.v3.url");
	}

	public String getPaymentDueDate() {
		return property.getProperty("payment.due.date");
	}

	public String getRestUrlPIDC_SE() {
		return property.getProperty("resturl.pidc.se");
	}

	public String getPracticeName_SE() {
		return property.getProperty("practicename.se");
	}

	public String getStatementEmailSubject() {
		return property.getProperty("statement.email.subject");
	}

	public String getPortalUserName_SE() {
		return property.getProperty("portal.username.se");
	}

	public String getPortalPassword_SE() {
		return property.getProperty("portal.password.se");
	}
	
	// BULK
	public String getOAuthPropertyAMDC() {
		return property.getProperty("oauth.property.amdc");
	}

	public String getOAuthKeyStoreAMDC() {
		return property.getProperty("oauth.keystore.amdc");
	}

	public String getSecureMessagePathAMDC() {
		return property.getProperty("securemessagepath.amdc");
	}

	public String getSecureMsgAskAStaff() {
		return property.getProperty("securemsg.askastaff");
	}

	public String getBatch_SecureMessage() {
		return property.getProperty("batch.secureMessage");
	}

	public String getUserName1AMDC() {
		return property.getProperty("username1.amdc");
	}

	public String getFrom1AMDC() {
		return property.getProperty("from1.amdc");
	}

	public String getUserName2AMDC() {
		return property.getProperty("username2.amdc");
	}

	public String getUserName3AMDC() {
		return property.getProperty("userName3.amdc");
	}

	public String getSender1AMDC() {
		return property.getProperty("sender1.amdc");
	}

	public String getSender2AMDC() {
		return property.getProperty("sender2.amdc");
	}

	public String getPatientName1AMDC() {
		return property.getProperty("patientname1.amdc");
	}

	public String getPatientName2AMDC() {
		return property.getProperty("patientname2.amdc");
	}

	public String getPatientName3AMDC() {
		return property.getProperty("patientname3.amdc");
	}

	public String getRestUrlBulk() {
		return property.getProperty("resturl.bulk");
	}

	public String getAttachmentLocation() {
		return property.getProperty("attachment.location");
	}

	public String getNumberOfAttachments() {
		return property.getProperty("numberof.attachments");
	}

	public String getMaxPatients() {
		return property.getProperty("max.patients");
	}

	public String getFileName() {
		return property.getProperty("file.name");
	}

	public String getSubject() {
		return property.getProperty("subject.bulk");
	}

	public String getMessageBulk() {
		return property.getProperty("message.bulk");
	}

	public String getresendPrevoiusMessage() {
		return property.getProperty("resend.prevoius.message");
	}

	public String getpreviousBulkMessageId() {
		return property.getProperty("previous.bulk.messageid");
	}

	public String getNumberOfParams() {
		return property.getProperty("numberof.params");
	}

	public String getAddAttachment() {
		return property.getProperty("add.attachment");
	}

	public String getParamName1() {
		return property.getProperty("param.name1");
	}

	public String getParamValue1() {
		return property.getProperty("param.value1");
	}

	public String getParamValue2() {

		return property.getProperty("param.value2");
	}

	public String getParamName2() {
		return property.getProperty("param.name2");
	}

	public String getParamValue3() {

		return property.getProperty("param.value3");
	}

	public String getParamName3() {
		return property.getProperty("param.name3");
	}

	public String getBulkEmailType() {
		return property.getProperty("bulk.emailtype");
	}

	public String getPatientExternalId() {
		return property.getProperty("patient.externalid");
	}

	public String getUserNameBulk1() {
		return property.getProperty("username.bulk1");
	}

	public String getUserNameBulk2() {
		return property.getProperty("username.bulk2");
	}

	public String getUserNameBulk3() {
		return property.getProperty("username.bulk3");
	}

	public String getPasswordBulk1() {
		return property.getProperty("password.bulk1");
	}

	public String getPasswordBulk2() {
		return property.getProperty("password.bulk2");
	}

	public String getPasswordBulk3() {
		return property.getProperty("password.bulk3");
	}

	public String getUserPatiendIDBulk1() {
		return property.getProperty("patient.externalid1");
	}

	public String getUserPatiendIDBulk2() {
		return property.getProperty("patient.externalid2");
	}

	public String getUserPatiendIDBulk3() {
		return property.getProperty("patient.externalid3");
	}

	public String getUserEmailBulk1() {
		return property.getProperty("patient.emailid1");
	}

	public String getUserEmailBulk2() {
		return property.getProperty("patient.emailid2");
	}

	public String getUserEmailBulk3() {
		return property.getProperty("patient.emailid3");
	}

	public String getresendMessageToPatientUserName() {
		return property.getProperty("resend.message.to.patient.username");
	}

	public String getresendMessageToPatientPassword() {
		return property.getProperty("resend.message.to.patient.password");
	}

	public String getresendMessageToPatientID() {
		return property.getProperty("resend.message.to.patientid");
	}

	public String getresendMessageToPatientEmail() {
		return property.getProperty("resend.message.to.patient.email");
	}

	public String getRestUrlV3Bulk() {
		return property.getProperty("resturlv3.bulk");
	}

	// SendDirectMessage
	public String getRestURL_SDM() {
		return property.getProperty("resturl.sdm");
	}

	public String getSecureEmailURL_SDM() {
		return property.getProperty("secureemailurl.sdm");
	}

	public String getSecureEmailUsername_SDM() {
		return property.getProperty("secureemailusername.sdm");
	}

	public String getSecureEmailPassword_SDM() {
		return property.getProperty("secureemailpassword.sdm");
	}

	public String getFromSecureEmailID_SDM() {
		return property.getProperty("fromsecureemailid.sdm");
	}

	public String getToSecureEmailID_SDM() {
		return property.getProperty("tosecureemailid.sdm");
	}

	public String getApplicationName_SDM() {
		return property.getProperty("applicationname.sdm");
	}

	public String getSubject_SDM() {
		return property.getProperty("subject.sdm");
	}

	public String getMessageBody_SDM() {
		return property.getProperty("messagebody.sdm");
	}

	public String getPatientId_SDM() {
		return property.getProperty("patientid.sdm");
	}

	public String getAttachmentType_SDM() {
		return property.getProperty("attachmenttype.sdm");
	}

	public String getFileName_SDM() {
		return property.getProperty("file.name.sdm");
	}

	public String getAttachmentLocationXML_SDM() {
		return property.getProperty("attachmentlocationxml.sdm");
	}

	public String getAttachmentLocationPDF_SDM() {
		return property.getProperty("attachmentlocationpdf.sdm");
	}

	public String getAttachmentLocationPNG_SDM() {
		return property.getProperty("attachmentlocationpng.sdm");
	}

	public String getOAuthAppToken_SDM() {
		return property.getProperty("oauth.apptoken.sdm");
	}

	public String getOAuthUsername_SDM() {
		return property.getProperty("oauth.username.sdm");
	}

	public String getOAuthPassword_SDM() {
		return property.getProperty("oauth.password.sdm");
	}

	public String getTOCName_SDM() {
		return property.getProperty("tocname.sdm");
	}

	public String getMessageStatus_SDM() {
		return property.getProperty("messagestatus.sdm");
	}

	public String getUnseenMessageHeaderURL_SDM() {
		return property.getProperty("unseen.message.header.url");
	}

	public String getUnseenMessageBodyURL_SDM() {
		return property.getProperty("unseen.message.body.url");
	}

	public String getMessageStatusUpdate_SDM() {
		return property.getProperty("message.status.update.url");
	}

	public String getMessageStatusToUpdate_SDM() {
		return property.getProperty("message.status.to.update");
	}

	public String getMessageHeaderURL_SDM() {
		return property.getProperty("message.header.url");
	}

	public String getInvalidPracticeMessageHeaderURL_SDM() {
		return property.getProperty("invalidpracticemessage.header.url");
	}

	public String getInvalidEmailMessageHeaderURL_SDM() {
		return property.getProperty("invalidemailmessage.header.url");
	}

	public String getInvalidUID_SDM() {
		return property.getProperty("invalid.uid");
	}

	public String getValidPracticeID_SDM() {
		return property.getProperty("valid.practiceid");
	}

	// Directory Search
	public String getRestURL_SD() {
		return property.getProperty("resturl.ds");
	}

	public String getDirectAddress_organization_DS() {
		return property.getProperty("directaddress.organization.ds");
	}

	public String getDirectAddress_provider_DS() {
		return property.getProperty("directaddress.provider.ds");
	}

	public String getOAuthAppToken_DS() {
		return property.getProperty("oauth.apptoken.ds");
	}

	public String getOAuthUsername_DS() {
		return property.getProperty("oauth.username.ds");
	}

	public String getOAuthPassword_DS() {
		return property.getProperty("oauth.password.ds");
	}

	public String getSearchLength() {
		return property.getProperty("searchlength");
	}

	public String getCSVFilePath_DS() {
		return property.getProperty("csvfile.path.ds");
	}

	// PatientFormsExportInfo
	public String geturl_FE() {
		return property.getProperty("url.fe");
	}

	public String getpracticeNew_URL_FE() {
		return property.getProperty("practicenew.url.fe");
	}

	public String getpatient_url_FE() {
		return property.getProperty("patient.url.fe");
	}

	public String getpatientfilepath_FE() {
		return property.getProperty("patientfilepath.fe");
	}

	public String getpatientFirstName_FE() {
		return property.getProperty("patientfirstname.fe");
	}

	public String getpatientLastName_FE() {
		return property.getProperty("patientlastname.fe");
	}

	public String getpatientEmailAddress1_FE() {
		return property.getProperty("patientemailaddress1.fe");
	}

	public String getpatientEmailCity_FE() {
		return property.getProperty("patientemailcity.fe");
	}

	public String getpatientphoneNumber_FE() {
		return property.getProperty("patientphonenumber.fe");
	}

	public String getpatientDOBDay1_FE() {
		return property.getProperty("patientdobday1.fe");
	}

	public String getpatientDOBMonth_FE() {
		return property.getProperty("patientdobmonth.fe");
	}

	public String getpatientDOBYear_FE() {
		return property.getProperty("patientdobyear.fe");
	}

	public String getpatientDOBMonthText_FE() {
		return property.getProperty("patientdobmonthtext.fe");
	}

	public String getpatientZipCode1_FE() {
		return property.getProperty("getpatientzipcode1.fe");
	}

	public String getpatientSecretQuestion_FE() {
		return property.getProperty("patientsecret.question.fe");
	}

	public String getpatientuserid_FE() {
		return property.getProperty("patientuserid.fe");
	}

	public String getpatientPassword1_FE() {
		return property.getProperty("patient.password1.fe");
	}

	public String getpatientSecretAnswer_FE() {
		return property.getProperty("patientsecret.answer.fe");
	}

	public String getpatientAddress1_FE() {
		return property.getProperty("patientaddress1.fe");
	}

	public String getpatientAddress2_FE() {
		return property.getProperty("patientaddress2.fe");
	}

	public String getpatientCity_FE() {
		return property.getProperty("patientcity.fe");
	}

	public String getpatientState_FE() {
		return property.getProperty("patientstate.fe");
	}

	public String getpatientHomePhoneNo_FE() {
		return property.getProperty("patienthome.phoneno.fe");
	}

	public String getpatientethnicity_FE() {
		return property.getProperty("patientethnicity.fe");
	}

	public String getpatientMaritalStatus_FE() {
		return property.getProperty("patientmarital.status.fe");
	}

	public String getpatientWhoIs_FE() {
		return property.getProperty("patientwhois.fe");
	}

	public String getNameofPrimaryInsurance() {
		return property.getProperty("name.of.primary.insurance");
	}

	public String getNameofsecondaryInsurance() {
		return property.getProperty("name.of.secondary.insurance");
	}

	public String getrelFirstNAme() {
		return property.getProperty("rel.first.name");
	}

	public String getrelLastNAme() {
		return property.getProperty("rel.last.name");
	}

	public String getrelation1() {
		return property.getProperty("relation1");
	}

	public String getphonenumber1() {
		return property.getProperty("phone.number1");
	}

	public String getphonetype1() {
		return property.getProperty("phone.type1");
	}

	public String gettetanus1() {
		return property.getProperty("tetanus1");
	}

	public String getHPV1() {
		return property.getProperty("hpv1");
	}

	public String getInfluenza1() {
		return property.getProperty("influenza1");
	}

	public String getPneumonia1() {
		return property.getProperty("pneumonia1");
	}

	public String getSurgeryName() {
		return property.getProperty("surgery.name");
	}

	public String getSurgeryTimeFrame() {
		return property.getProperty("surgery.timeframe");
	}

	public String getHospitalizationReason() {
		return property.getProperty("hospitalization.reason");
	}

	public String getHospitalizationTimeFrame() {
		return property.getProperty("hospitalization.timeframe");
	}

	public String getTestTimeFrame() {
		return property.getProperty("test.timeframe");
	}

	public String getNameofDoctorSpeciality() {
		return property.getProperty("nameofdoctor.speciality");
	}

	public String getNameDosage() {
		return property.getProperty("name.dosage");
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
		return property.getProperty("ccd.url1.fe");
	}

	public String getoAuthAppTokenCCD1_FE() {
		return property.getProperty("oauth.apptoken.ccd1.fe");
	}

	public String getoAuthUsernameCCD1_FE() {
		return property.getProperty("oauth.username.ccd1.fe");
	}

	public String getoAuthPasswordCCD1_FE() {
		return property.getProperty("oauth.password.ccd1.fe");
	}

	public String getoAuthKeyStore1_FE() {
		return property.getProperty("oauth.keystore1.fe");
	}

	public String getoAuthProperty1_FE() {
		return property.getProperty("oauth.property1.fe");
	}

	public String getresponsePath_CCD1_FE() {
		return property.getProperty("response.path.ccd1.fe");
	}

	public String getpracticeURL_FE() {
		return property.getProperty("practiceurl.fe");
	}

	public String getpracticeUserName_FE() {
		return property.getProperty("practice.username.fe");

	}

	public String getpracticePassword_FE() {
		return property.getProperty("practice.password.fe");
	}

	public String getpatientLocation_FE() {
		return property.getProperty("patientlocation.fe");
	}

	public String getpatientProvider_FE() {
		return property.getProperty("patientprovider.fe");
	}

	public String getresponsePDF_FE() {
		return property.getProperty("responsepdf.fe");
	}

	public String getccd_PDfUrl_FE() {
		return property.getProperty("ccd.pdfurl.fe");
	}

	public String getresponsePDFBatch_FE() {
		return property.getProperty("responsepdfbatch.fe");
	}

	public String getUIPDFFile_FE() {
		return property.getProperty("uipdffile.fe");
	}

	public String getday() {
		return property.getProperty("day");
	}

	public String getDownloadFileLocation() {
		return property.getProperty("download.file.location");
	}

	public String getPreCheckURL() {
		return property.getProperty("precheckurl.fe");
	}

	public String getPreCheckPatientExternalID() {
		return property.getProperty("precheckpatient.externalid.fe");
	}

	public String getAllowAttachment() {
		return property.getProperty("allow.attachment");
	}

	public String getCategoryType() {
		return property.getProperty("category.type");
	}

	public String getFileNameAMDC() {
		return property.getProperty("file.name.amdc");
	}

	public String getMimeType() {
		return property.getProperty("mime.type");
	}

	public String getAttachmentBody() {
		return property.getProperty("attachment.body");
	}

	public String getPortalCategoryType() {
		return property.getProperty("portal.category.type");
	}

	public String getAppointmentPayload() {
		return property.getProperty("precheck.appointment.payload");
	}

	public String getPreCheckAppointmentRestUrl() {
		return property.getProperty("appointment.resturl");
	}

	public String getBasicAccesstokenPrecheck() {
		return property.getProperty("basic.acesstoken.precheck");
	}

	public String getPreCheckZipCode() {
		return property.getProperty("precheck.zipcode");
	}

	public String getPreCheckDOB() {
		return property.getProperty("precheck.dob");
	}

	public String getPreCheckPatientEmailID_FE() {
		return property.getProperty("precheckpatient.emailid.fe");
	}

	public String getPreCheckPatientFirstName_FE() {
		return property.getProperty("precheckpatientfirstname.fe");
	}

	public String getPreCheckGetPIDC() {
		return property.getProperty("precheck.getpidc");
	}

	public String getPreCheckEmailSubject() {
		return property.getProperty("precheck.email.subject");
	}

	public String getPreCheckEmailLink() {
		return property.getProperty("precheck.email.link");
	}

	public String getPreCheckInsuranceImageType() {
		return property.getProperty("precheck.image.insurancetype");
	}

	public String getBalanceUrl() {
		return property.getProperty("balance.url");
	}

	public String getBalanceIntegrationPracticeId() {
		return property.getProperty("balance.integration.practiceid");
	}

	public String getNewPassword() {
		return property.getProperty("new.password");
	}

	public String getEmailSubjectBalance() {
		return property.getProperty("email.subject.balance");
	}

	public String getEmailLinkText() {
		return property.getProperty("email.link.text");
	}

	public String getToken_MU() {
		return property.getProperty("token.mu");
	}

	public String getDeleteToken() {
		return property.getProperty("token.p2p");
	}

	// DB
	public String getPostGREdbHostName() {
		return property.getProperty("postgres.db.host.name");
	}

	public String getPostGREdbName() {
		return property.getProperty("postgres.db.name");
	}

	public String getPostGREdbUserName() {
		return property.getProperty("postgres.db.username");
	}

	public String getPostGREdbPassword() {
		return property.getProperty("postgres.db.password");
	}

	public String getCoreMSSQLdbHostName() {
		return property.getProperty("core.db.host.name");
	}

	public String getCoreMSSQLdbName() {
		return property.getProperty("core.db.name");
	}

	public String getCoreMSSQLdbUserName() {
		return property.getProperty("core.db.username");
	}

	public String getCoreMSSQLdbPassword() {
		return property.getProperty("core.db.password");
	}

	public String getMFMSSQLdbHostName() {
		return property.getProperty("mf.agent.db.host.name");
	}

	public String getMFMSSQLdbName() {
		return property.getProperty("mf.agent.db.name");
	}

	public String getMFMSSQLdbUserName() {
		return property.getProperty("mf.agent.db.username");
	}

	public String getMFMSSQLdbPassword() {
		return property.getProperty("mf.agent.db.password");
	}

	public String getMFOracleSQLdbHostName() {
		return property.getProperty("consumer.db.host.name");
	}

	public String getMFOracleSQLdbName() {
		return property.getProperty("consumer.db.name");
	}

	public String getMFOracleSQLdbUserName() {
		return property.getProperty("consumer.db.username");
	}

	public String getMFOracleSQLdbPassword() {
		return property.getProperty("consumer.db.password");
	}

	// MF API Url
	public String getProcessingURL() {
		return property.getProperty("processing.url");
	}

	public String getPIDCURL() {
		return property.getProperty("get.pidc.url");
	}

	public String getPhoneNumber() {
		return property.getProperty("phone.number");
	}

	// Practice Portal URL
	public String getDoctorLogin() {
		return property.getProperty("doctor.login");
	}

	public String getDoctorPassword() {
		return property.getProperty("doctor.password");
	}

	public String getPortalUrl() {
		return property.getProperty("portal.url");
	}

	public String getDOBDay() {
		return property.getProperty("dob.day");
	}

	public String getDOBMonth() {
		return property.getProperty("dob.month");
	}

	public String getDOBMonthText() {
		return property.getProperty("dob.month.text");
	}

	public String getDOBYear() {
		return property.getProperty("dob.year");
	}

	public String getDOBYearUnderage() {
		return property.getProperty("dob.year.underage");
	}

	// NG API
	public String getNGAPIexecutionMode() {
		return property.getProperty("ng.api.execution.mode");
	}

	public String getNGAPIQAMainPracticeID() {
		return property.getProperty("ng.api.qa.main.practice.id");
	}

	public String getNGAPIQAMainEnterpriseID() {
		return property.getProperty("ng.api.qa.main.enterprise.id");
	}

	public String getNGAPISITPracticeID() {
		return property.getProperty("ng.api.sit.practice.id");
	}

	public String getNGAPISITEnterpriseID() {
		return property.getProperty("ng.api.sit.enterprise.id");
	}

	public String getProperty(String prop) throws NullPointerException {
		if (property.getProperty(prop) == null)
			throw new NullPointerException("Property " + prop + " not found in the property file.");
		return property.getProperty(prop);
	}

	public String getNGEPMProviderName() {
		return property.getProperty("epm.provider.name");
	}

	public String getNGEPMLocationName() {
		return property.getProperty("epm.location.name");
	}

	public String getNGEPMRenderingProviderName() {
		return property.getProperty("epm.rendering.provider.name");
	}

	// NG Enterprise Enrollment
	public String getNGEnterpiseEnrollmentE1() {
		return property.getProperty("ng.enterprise.enrollment.enterprise1");
	}

	public String getNGEnterpiseEnrollmentE2() {
		return property.getProperty("ng.enterprise.enrollment.enterprise2");
	}

	public String getNGEnterpiseEnrollmentE1P1() {
		return property.getProperty("ng.enterprise1.practice1");
	}

	public String getNGEnterpiseEnrollmentE1P2() {
		return property.getProperty("ng.enterprise1.practice2");
	}

	public String getNGEnterpiseEnrollmentE1P3() {
		return property.getProperty("ng.enterprise1.practice3");
	}

	public String getNGEnterpiseEnrollmentE1P4() {
		return property.getProperty("ng.enterprise1.practice4");
	}

	public String getNGEnterpiseEnrollmentE1P5() {
		return property.getProperty("ng.enterprise1.practice5");
	}

	public String getNGEnterpiseEnrollmentE2P1() {
		return property.getProperty("ng.enterprise2.practice1");
	}

	public String getNGEnterpiseEnrollmentE2P2() {
		return property.getProperty("ng.enterprise2.practice2");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P1() {
		return property.getProperty("integration.practice.id.e1.p1");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P2() {
		return property.getProperty("integration.practice.id.e1.p2");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P3() {
		return property.getProperty("integration.practice.id.e1.p3");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P4() {
		return property.getProperty("integration.practice.id.e1.p4");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE1P5() {
		return property.getProperty("integration.practice.id.e1.p5");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE2P1() {
		return property.getProperty("integration.practice.id.e2.p1");
	}

	public String getNGEnterpiseEnrollmentintegrationPracticeIDE2P2() {
		return property.getProperty("integration.practice.id.e2.p2");
	}

	public String getNGE1P1Provider() {
		return property.getProperty("ng.e1.p1.provider");
	}

	public String getNGE1P2Provider() {
		return property.getProperty("ng.e1.p2.provider");
	}

	public String getNGE1P3Provider() {
		return property.getProperty("ng.e1.p3.provider");
	}

	public String getNGE1P4Provider() {
		return property.getProperty("ng.e1.p4.provider");
	}

	public String getNGE1P5Provider() {
		return property.getProperty("ng.e1.p5.provider");
	}

	public String getNGE2P1Provider() {
		return property.getProperty("ng.e2.p1.provider");
	}

	public String getNGE2P2Provider() {
		return property.getProperty("ng.e2.p2.provider");
	}

	public String getNGE1P1Location() {
		return property.getProperty("ng.e1.p1.location");
	}

	public String getNGE1P2Location() {
		return property.getProperty("ng.e1.p2.location");
	}

	public String getNGE1P3Location() {
		return property.getProperty("ng.e1.p3.location");
	}

	public String getNGE1P4Location() {
		return property.getProperty("ng.e1.p4.location");
	}

	public String getNGE1P5Location() {
		return property.getProperty("ng.e1.p5.location");
	}

	public String getNGE2P1Location() {
		return property.getProperty("ng.e2.p1.location");
	}

	public String getNGE2P2Location() {
		return property.getProperty("ng.e2.p2.location");
	}

	// AppoinmentType
	public String getAppointmentTypeName() {
		return property.getProperty("appointment.type.name");
	}

	public String getAppointmentTypeID() {
		return property.getProperty("appointment.type.id");
	}

	public String getAppointmentCategoryName() {
		return property.getProperty("appointment.category.name");
	}

	public String getAppointmentCategoryID() {
		return property.getProperty("appointment.category.id");
	}

	public String getActiveFlag() {
		return property.getProperty("active.flag");
	}

	public String getComment() {
		return property.getProperty("comment");
	}

	public String getAppointmentTypeUrl() {
		return property.getProperty("appointment.type.url");
	}

	// Pharmacies
	public String getOAuthAppToken_PH() {
		return property.getProperty("oauth.apptoken.ph");
	}

	public String getOAuthUsername_PH() {
		return property.getProperty("oauth.username.ph");
	}

	public String getoAuthPassword_PH() {
		return property.getProperty("oauth.password.ph");
	}

	public String getPatientUsername_PH() {
		return property.getProperty("username.ph");
	}

	public String getPatientPassword_PH() {
		return property.getProperty("password.ph");
	}

	public String getPharmacyRenewalUrl() {
		return property.getProperty("pharmacy.renewal.url");
	}

	public String getpatientUrl() {
		return property.getProperty("patient.url");
	}

	public String getPharmacyName() {
		return property.getProperty("pharmacy.name");
	}

	public String getExternalPharmacyId() {
		return property.getProperty("external.pharmacy.id");
	}

	public String getLine1() {
		return property.getProperty("line1");
	}

	public String getLine2() {
		return property.getProperty("line2");
	}

	public String getCity_PH() {
		return property.getProperty("city.ph");
	}

	public String getState_PH() {
		return property.getProperty("state.ph");
	}

	public String getCountry() {
		return property.getProperty("country");
	}

	public String getZipCode_PH() {
		return property.getProperty("zipcode.ph");
	}

	public String getPharmacyPhone() {
		return property.getProperty("pharmacy.phone");
	}

	public String getPharmacyFaxNumber() {
		return property.getProperty("pharmacy.fax.number");
	}

	public String getAppointmentRequestV3URL() {
		return property.getProperty("appointment.request.v3url");
	}
	
	public String getAppointmentRequestV4URL() {
		return property.getProperty("appointment.request.v4url");
	}

	public String getPATIENT_INVITE_RESTV3URL() {
		return property.getProperty("resturl.pidcv3");
	}
	public String getPATIENT_INVITE_RESTV4URL() {
		return property.getProperty("resturl.pidcv4");
	}
	
	public String getPatienturl_Event() {
		return property.getProperty("patient.url.loginevent");
	}

	public String getPatientUsername_Event() {
		return property.getProperty("username.loginevent");
	}

	public String getPatientpassword_Event() {
		return property.getProperty("password.loginevent");
	}

	public String getPatientoAuthAppToken_Event() {
		return property.getProperty("oauth.apptoken.loginevent");
	}

	public String getPatientoAuthUsername_Event() {
		return property.getProperty("oauth.username.loginevent");
	}

	public String getPatientoAuthPassword_Event() {
		return property.getProperty("oauth.password.loginevent");
	}

	public String getPatientoAuthProperty_Event() {
		return property.getProperty("oauth.property.loginevent");
	}

	public String getPatientoAuthKeystore_Event() {
		return property.getProperty("oauth.keystore.loginevent");
	}

	public String getPatientResponsePath_Event() {
		return property.getProperty("response.path.loginevent");
	}

	public String getPatientrestUrlLogin_V3_Event() {
		return property.getProperty("resturllogin.v3");
	}

	public String getStatementPdfDetail() {
		return property.getProperty("statement.pdf.detail");
	}

	public String getStatementUrlV3() {
		return property.getProperty("resturlv3.statement");
	}

	public String getPatientStatementUrl() {
		return property.getProperty("url.patientstatement");
	}

	public String getOAuthAppToken_Statement() {
		return property.getProperty("oauth.apptoken.patientstatement");
	}

	public String getOAuthAppUsername_Statement() {
		return property.getProperty("oauth.username.patientstatement");
	}

	public String getOAuthAppPassword_Statement() {
		return property.getProperty("oauth.password.patientstatement");
	}

	// Attachment

	public String getRestUrlAttachment() {
		return property.getProperty("rest.url.attachment");
	}

	public String getPatientExternalIdAttachment() {
		return property.getProperty("patient.externalid.attachment");
	}

}
