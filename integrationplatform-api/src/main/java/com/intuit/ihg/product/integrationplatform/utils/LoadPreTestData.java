package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;

public class LoadPreTestData {

	public EHDC loadEHDCDataFromProperty(EHDC testData) throws IOException {

		PropertyFileLoader propertyData = new PropertyFileLoader();

		testData.URL = propertyData.getUrlCCD();
		testData.PHR_URL = propertyData.getPHR_URL();
		testData.UserName = propertyData.getUserNameCCD();
		testData.Password = propertyData.getPasswordCCD();
		testData.RestUrl = propertyData.getRestUrlCCD();
		testData.RestUrlV3 = propertyData.getRestUrlV3CCD();
		testData.EPracticeName = propertyData.getEPracticeNameCCD();
		testData.ccdXMLPath = propertyData.getCCDPATH3();
		testData.CCDPath = propertyData.getCCDPath();
		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();
		testData.OAuthAppToken = propertyData.getOAuthAppTokenCCD();
		testData.OAuthUsername = propertyData.getOAuthUsernameCCD();
		testData.OAuthPassword = propertyData.getOAuthPasswordCCD();
		testData.ResponsePath = propertyData.getResponsePath();

		testData.From = propertyData.getFromCCD();
		testData.IntegrationPracticeID = propertyData.getIntegrationPracticeIDCCD();
		testData.Zip = propertyData.getCCDZIP();

		testData.deviceName = propertyData.getdeviceNameCCD();
		testData.deviceVersion = propertyData.getdeviceVersionCCD();
		testData.vendorName = propertyData.getvendorNameCCD();
		testData.MessageId = propertyData.getMessageIdCCD();

		testData.PracticePatientId = propertyData.getPracticePatientIdCCD();
		testData.Prefix = propertyData.getPrefixCCD();
		testData.FirstName = propertyData.getFirstNameCCD();
		testData.MiddleName = propertyData.getMiddleNameCCD();
		testData.LastName = propertyData.getLastNameCCD();
		testData.Line1 = propertyData.getLine1CCD();
		testData.Line2 = propertyData.getLine2CCD();
		testData.City = propertyData.getCityCCD();
		testData.State = propertyData.getStateCCD();
		testData.Country = propertyData.getCountryCCD();
		testData.ccdXMLPathLargeSize = propertyData.getCCDPATHLargeSize();

		return testData;
	}

	public AMDC loadAMDCDataFromProperty(AMDC testData) throws IOException {

		PropertyFileLoader propertyData = new PropertyFileLoader();

		testData.Url = propertyData.geturlAMDC();
		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();
		testData.OAuthAppToken = propertyData.getOAuthAppTokenAMDC();
		testData.OAuthUsername = propertyData.getOAuthUsernameAMDC();
		testData.OAuthPassword = propertyData.getOAuthPasswordAMDC();
		testData.UserName = propertyData.getUserNameAMDC();
		testData.Password = propertyData.getPasswordAMDC();
		testData.RestUrl = propertyData.getRestUrlAMDC();
		testData.From = propertyData.getFromAMDC();
		testData.ReadCommuniationURL = propertyData.getReadCommunicationUrlAMDC();
		testData.Sender3 = propertyData.getSender3AMDC();
		testData.GmailUserName = propertyData.getGmailUserNameAMDC();
		testData.GmailPassword = propertyData.getGmailPasswordAMDC();
		testData.AllowReply = propertyData.getAllowReply();
		testData.Message = propertyData.getMessageAMDC();
		testData.PatientExternalId = propertyData.getPatientExternalIdAMDC();
		testData.ResponsePath = propertyData.getResponsePath();
		testData.IntegrationPracticeID = propertyData.getIntegrationPracticeID();
		testData.allowAttachment = propertyData.getAllowAttachment();
		testData.categoryType = propertyData.getCategoryType();
		testData.fileName = propertyData.getFileNameAMDC();
		testData.mimeType = propertyData.getMimeType();
		testData.attachmentBody = propertyData.getAttachmentBody();
		testData.downloadLocation = propertyData.getDownloadFileLocation();
		testData.portalCategoryType = propertyData.getPortalCategoryType();
		testData.RestV3Url = propertyData.getRestUrlV3AMDC();
		testData.ReadCommuniationURLV3 = propertyData.getReadCommunicationUrlV3AMDC();

		return testData;
	}

	public MU2GetEventData loadAPITESTDATAFromProperty(MU2GetEventData testData) throws IOException {

		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.PULLAPI_URL = propertyData.getPULLAPI_URL_MU2();
		testData.PULLAPI_URLV3 = propertyData.getPULLAPI_URLV3_MU2();
		testData.OAUTH_PROPERTY = propertyData.getOAuthProperty();
		testData.OAUTH_KEYSTORE = propertyData.getOAuthKeyStore();
		testData.OAUTH_APPTOKEN = propertyData.getOAuthAppToken_MU2();
		testData.OAUTH_USERNAME = propertyData.getOAuthUsername_MU2();
		testData.OAUTH_PASSWORD = propertyData.getOAuthPassword_MU2();
		testData.PUSHAPI_URL = propertyData.getPUSHAPI_URL_MU2();
		testData.PUSH_RESPONSEPATH = propertyData.getResponsePath();
		testData.PORTAL_URL = propertyData.getPORTAL_URL_MU2();
		testData.PORTAL_USERNAME = propertyData.getPORTAL_USERNAME_MU2();
		testData.PORTAL_PASSWORD = propertyData.getPORTAL_PASSWORD_MU2();
		testData.INTUIT_PATIENT_ID = propertyData.getINTUIT_PATIENT_ID_MU2();
		testData.TRANSMIT_EMAIL = propertyData.getTRANSMIT_EMAIL_MU2();
		testData.IMAGE_PATH = propertyData.getIMAGE_PATH_MU2();
		testData.PATIENT_ID = propertyData.getPatient_ID_MU2();
		testData.PATIENT_INVITE_RESTURL = propertyData.getPATIENT_INVITE_RESTURL();
		testData.PATIENT_PRACTICEID = propertyData.getPATIENT_PRACTICEID();
		testData.PATIENT_EXTERNAL_ID = propertyData.getPATIENT_EXTERNAL_ID();
		testData.CCDPATH1 = propertyData.getCCDPATH1();
		testData.CCDPATH2 = propertyData.getCCDPATH2();
		testData.CCDPATH3 = propertyData.getCCDPATH3();
		testData.CCDPATH4 = propertyData.getCCDPATH4();

		testData.HomePhoneNo = propertyData.getHomePhoneNo();
		testData.SecretQuestion = propertyData.getSecretQuestion();
		testData.SecretAnswer = propertyData.getSecretAnswer();
		testData.BirthDay = propertyData.getBirthDay();
		testData.PatientPassword = propertyData.getPatientPassword();

		testData.CCDMessageID1 = propertyData.getCCDMessageID1();
		testData.CCDMessageID2 = propertyData.getCCDMessageID2();
		testData.PatientExternalId_MU2 = propertyData.getPatientExternalId_MU2();
		testData.PatientFirstName_MU2 = propertyData.getPatientFirstName_MU2();
		testData.PatientLastName_MU2 = propertyData.getPatientLastName_MU2();
		testData.Standard_Email = propertyData.getStandard_Email();

		testData.Standard_Email = propertyData.getStandard_Email();

		testData.patientUA_MU2 = propertyData.getPatientUA_MU2();
		testData.guardian_UserName_MU2 = propertyData.getGuardian_UserName_MU2();
		testData.guardian_Password_MU2 = propertyData.getGuardian_Password_MU2();
		testData.patientUA_ExternalPatientID_MU2 = propertyData.getPatient_ExternalPatientID_MU2();
		testData.intuit_PATIENT_ID_MU2_Guardian = propertyData.getPatientIntuItID_Guardian();

		testData.patientUA_MU2_Existing = propertyData.getPatientUA_MU2_Existing();
		testData.guardian_UserName_MU2_Existing = propertyData.getGuardian_UserName_MU2_Existing();
		testData.guardian_Password_MU2_Existing = propertyData.getGuardian_Password_MU2_Existing();
		testData.patientUA_ExternalPatientID_MU2_Existing = propertyData.getPatient_ExternalPatientID_MU2_Existing();
		testData.intuit_PATIENT_ID_MU2_Guardian_Existing = propertyData.getPatientIntuItID_GuardianExisting();
		testData.secureEmailTransmitActivity = propertyData.getSecureEmailTransmitActivity();
		testData.standardEmailTransmitActivity = propertyData.getStandardEmailTransmitActivity();
		testData.patientUA_MU2_LastName = propertyData.getPatientUA_MU2_LastName();
		testData.patientUA_MU2_LastName_Existing = propertyData.getPatientUA_MU2_LastName_Existing();
		testData.pdfFilePath = propertyData.getPdfFilePath();
		testData.token = propertyData.getToken_MU();

		return testData;
	}

	public PIDCInfo loadDataFromProperty(PIDCInfo testData, String channelVersion, String portalVersion)
			throws Exception {

		PropertyFileLoader propertyData = new PropertyFileLoader();

		testData.setBatchSize(propertyData.getBatchSize());

		testData.setoAuthProperty(propertyData.getOAuthProperty());
		testData.setoAuthKeyStore(propertyData.getOAuthKeyStore());

		testData.setPatientPath(propertyData.getPatientPath());
		testData.setResponsePath(propertyData.getResponsePath());
		testData.setUsername(propertyData.getUserName());
		testData.setPassword(propertyData.getPassword());

		testData.setBirthDay(propertyData.getBirthday());
		testData.setZipCode(propertyData.getZipCode());
		testData.setSSN(propertyData.getSSN());
		testData.setEmail(propertyData.getEmail());
		testData.setPatientPassword(propertyData.getPatientPassword());
		testData.setSecretQuestion(propertyData.getSecretQuestion());
		testData.setSecretAnswer(propertyData.getSecretAnswer());
		testData.setPracticeURL(propertyData.getPracticeURL());
		testData.setHomePhoneNo(propertyData.getHomePhoneNo());
		testData.setCity(propertyData.getCity());
		testData.setState(propertyData.getState());
		testData.setLastName(propertyData.getLastName());
		testData.setRelation(propertyData.getRelation());
		testData.setMaritalStatus(propertyData.getMaritalStatus());
		testData.setChooseCommunication(propertyData.getChooseCommunication());
		testData.setInsurance_Type(propertyData.getInsurance_Type());
		testData.setCsvFilePath(propertyData.getCSVFilePath());

		if (channelVersion.contains("v1")) {
			testData.setRestUrl_20(propertyData.getRestUrl1_20());
		}
		if (channelVersion.contains("v2")) {
			testData.setRestUrl_20(propertyData.getRestUrl2_20());
		}
		if (channelVersion.contains("v3")) {
			testData.setRestUrl_20(propertyData.getRestUrl3_20());
		}
		if (channelVersion.contains("v4")) {
			testData.setRestUrl_20(propertyData.getRestUrl4_20());
		}

		if (portalVersion.contains("2.0")) {
			if (channelVersion.contains("v1")) {
				testData.setRestUrl(propertyData.getRestUrl1_20());
			}
			if (channelVersion.contains("v2")) {
				testData.setRestUrl(propertyData.getRestUrl2_20());
			}
			if (channelVersion.contains("v3")) {
				testData.setRestUrl(propertyData.getRestUrl3_20());
			}
			
			testData.setPracticeId(propertyData.getPracticeId_PIDC_20());
			testData.setoAuthAppToken(propertyData.getOAuthAppToken_20());
			testData.setoAuthUsername(propertyData.getOAuthUsername_20());
			testData.setoAuthPassword(propertyData.getOAuthPassword_20());

			testData.setFnameSC(propertyData.getFirstName_AD());
			testData.setLnameSC(propertyData.getLastName_AD());
			testData.setSecretQuestion(propertyData.getSecretQuestion());
			testData.setSecretAnswer(propertyData.getSecretAnswer());

			testData.setPortalURL(propertyData.getUrl());
			testData.setPracticeUserName(propertyData.getPracticeUserName());
			testData.setPracticePassword(propertyData.getPracticePassword());
			testData.setPracticeId_PIDC_20(propertyData.getPatientID());
			testData.setPreferredLanguageType(propertyData.getLanguageType());
			testData.setToken(propertyData.getToken_MU());
			testData.setTestPatientIDUserName(propertyData.getTestPatientIDUserName());
			testData.setPrecheckSubscriberPatientRestURL(propertyData.getPrecheckSubscriberPatientRestURL());
			testData.setSubscriberPracticeID(propertyData.getSubscriberPracticeID());
		}

		return testData;
	}

	public AppointmentData loadAppointmentDataFromProperty(AppointmentData testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.AppointmentPath = propertyData.getAppointmentRequestURL();
		testData.EmailUserName = propertyData.getEmail_AD();
		testData.MFPatientId = propertyData.getMedfusionPatientId_AD();
		testData.MFPracticeId = propertyData.getMedfusionPracticeId_AD();
		testData.OAuthAppToken = propertyData.getOAuthAppToken_AD();
		testData.OAuthUsername = propertyData.getOAuthUsername_AD();
		testData.OAuthPassword = propertyData.getoAuthPassword_AD();

		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();
		testData.ResponsePath = propertyData.getResponsePath();
		testData.PracticeName = propertyData.getIntegrationPracticeID_AD();
		testData.URL = propertyData.getPracticeURL_AD();
		testData.UserName = propertyData.getUserName_AD();
		testData.Password = propertyData.getPassword_AD();
		testData.PatientPracticeId = propertyData.getPatientPracticeID_AD();
		testData.PreviousAppointmentId = propertyData.getPreviousAppointmentId();
		testData.PATIENT_EXTERNAL_ID = propertyData.getPATIENT_EXTERNAL_ID();
		testData.PATIENT_INVITE_RESTURL = propertyData.getPATIENT_INVITE_RESTURL();
		testData.From = propertyData.getProviderIdentifier_AD();

		testData.FirstName = propertyData.getFirstName_AD();
		testData.LastName = propertyData.getLastName_AD();
		testData.SecretQuestion = propertyData.getSecretQuestion();
		testData.SecretAnswer = propertyData.getSecretAnswer();
		testData.HomePhoneNo = propertyData.getHomePhoneNo();
		testData.BatchSize = propertyData.getBatchSize_AD();
		testData.csvFilePath = propertyData.getCSVFILEPATH_AD();
		testData.portalURL = propertyData.getPracticePortalURL_AD();
		testData.practiceUserName = propertyData.getProtalUserName_AD();
		testData.practicePassword = propertyData.getPortalPassword_AD();
		testData.AppointmentRequestV3URL = propertyData.getAppointmentRequestV3URL();
		testData.AppointmentRequestV4URL = propertyData.getAppointmentRequestV4URL();
		testData.PATIENT_INVITE_RESTV3URL = propertyData.getPATIENT_INVITE_RESTV3URL();
		testData.PATIENT_INVITE_RESTV4URL = propertyData.getPATIENT_INVITE_RESTV4URL();
		return testData;
	}

	public StatementEventData loadStatementEventDataFromProperty(StatementEventData testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.Url = propertyData.getUrl_SE();
		testData.UserName = propertyData.getUserName_SE();
		testData.Password = propertyData.getPassword_SE();
		testData.Email = propertyData.getEmail_SE();
		testData.RestUrl = propertyData.getRestUrl_SE();
		testData.RestV3Url = propertyData.getRestV3Url_SE();
		testData.ResponsePath = propertyData.getResponsePath();
		testData.OAuthAppToken = propertyData.getOAuthAppToken_SE();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();
		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthUsername = propertyData.getOAuthUsername_SE();
		testData.OAuthPassword = propertyData.getOAuthPassword_SE();
		testData.FirstName = propertyData.getFirstName_SE();
		testData.LastName = propertyData.getLastName_SE();
		testData.PatientID = propertyData.getPatientID_SE();
		testData.MFPatientID = propertyData.getMFPatientID_SE();
		testData.StatementEventURL = propertyData.getStatementEventURL();
		testData.StatementEventV3URL = propertyData.getStatementEventV3URL();
		testData.StatementFormat = propertyData.getStatementFormat_SE();
		testData.Address1 = propertyData.getAddress1_SE();
		testData.Address2 = propertyData.getAddress2_SE();
		testData.City = propertyData.getCity_SE();
		testData.State = propertyData.getState_SE();
		testData.ZipCode = propertyData.getZipCode_SE();
		testData.NewCharges = propertyData.getNewCharges_SE();
		testData.TotalCharges = propertyData.getTotalCharges_SE();
		testData.AmountDue = propertyData.getAmountDue_SE();
		testData.BalanceForwardType = propertyData.getBalanceForwardType_SE();
		testData.BalanceForwardAmount = propertyData.getBalanceForwardAmount_SE();
		testData.OutstandingBalance = propertyData.getOutstandingBalance_SE();
		testData.StatementComment = propertyData.getStatementComment_SE();
		testData.DunningMessage = propertyData.getDunningMessage_SE();
		testData.PracticeProviderName = propertyData.getPracticeProviderName_SE();
		testData.PaymentDueDate = propertyData.getPaymentDueDate();
		testData.RestURLPIDC = propertyData.getRestUrlPIDC_SE();
		testData.PracticeName = propertyData.getPracticeName_SE();
		testData.portalURL = propertyData.getPracticePortalURL_AD();
		testData.practiceUserName = propertyData.getPortalUserName_SE();
		testData.practicePassword = propertyData.getPortalPassword_SE();
		testData.emailSubject = propertyData.getStatementEmailSubject();
		testData.balanceUrl = propertyData.getBalanceUrl();
		testData.balanceIntegrationPracticeId = propertyData.getBalanceIntegrationPracticeId();
		testData.newPassword = propertyData.getNewPassword();
		testData.emailSubjectBalance = propertyData.getEmailSubjectBalance();
		testData.emailLinkText = propertyData.getEmailLinkText();
		testData.StatementPdf_Detail = propertyData.getStatementPdfDetail();
		testData.restUrlV3_Statement = propertyData.getStatementUrlV3();
		testData.url_PatientStatement= propertyData.getPatientStatementUrl();
		testData.oAuthAppToken_PatientSt= propertyData.getOAuthAppToken_Statement();
		testData.oAuthAppUsername_PatientSt= propertyData.getOAuthAppUsername_Statement();
		testData.oAuthAppPw_PatientSt= propertyData.getOAuthAppPassword_Statement();
		return testData;
	}

	public BulkAdmin loadDataFromPropertyBulk(BulkAdmin testData) throws IOException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();
		testData.OAuthAppToken = propertyData.getOAuthAppTokenAMDC();
		testData.OAuthUsername = propertyData.getOAuthUsernameAMDC();
		testData.OAuthPassword = propertyData.getOAuthPasswordAMDC();

		testData.UserName = propertyData.getUserNameAMDC();
		testData.Password = propertyData.getPasswordAMDC();
		testData.RestUrl = propertyData.getRestUrlBulk();
		testData.From = propertyData.getFromAMDC();

		testData.ResponsePath = propertyData.getResponsePath();
		testData.ReadCommuniationURL = propertyData.getReadCommunicationUrlAMDC();
		testData.SecureMessage_AskaStaffXML = propertyData.getSecureMsgAskAStaff();
		testData.Batch_SecureMessage = propertyData.getBatch_SecureMessage();
		testData.UserName1 = propertyData.getUserName1AMDC();
		testData.From1 = propertyData.getFrom1AMDC();
		testData.UserName2 = propertyData.getUserName2AMDC();
		testData.IntegrationPracticeID = propertyData.getIntegrationPracticeID();
		testData.PatientName1 = propertyData.getPatientName1AMDC();
		testData.PatientName2 = propertyData.getPatientName2AMDC();
		testData.PatientName3 = propertyData.getPatientName3AMDC();

		testData.GmailUserName = propertyData.getGmailUserNameAMDC();
		testData.GmailPassword = propertyData.getGmailPasswordAMDC();
		testData.Sender1 = propertyData.getSender1AMDC();
		testData.Sender2 = propertyData.getSender2AMDC();
		testData.Sender3 = propertyData.getSender2AMDC();

		testData.AttachmentLocation = propertyData.getAttachmentLocation();
		testData.NumberOfAttachments = propertyData.getNumberOfAttachments();
		testData.MaxPatients = propertyData.getMaxPatients();
		testData.previousBulkMessageId = propertyData.getpreviousBulkMessageId();
		testData.resendPreviousMessage = propertyData.getresendPrevoiusMessage();
		testData.ParamValue = propertyData.getParamValue1();
		testData.ParamName = propertyData.getParamName1();
		testData.categoryType = propertyData.getCategoryType();

		testData.NumberOfParams = propertyData.getNumberOfParams();
		testData.ParamValue1 = propertyData.getParamValue1();
		testData.ParamName1 = propertyData.getParamName1();
		testData.ParamValue2 = propertyData.getParamValue2();
		testData.ParamName2 = propertyData.getParamName2();
		testData.ParamValue3 = propertyData.getParamValue3();
		testData.ParamName3 = propertyData.getParamName3();
		testData.RestV3Url = propertyData.getRestUrlV3Bulk();

		for (int j = 1; j <= Integer.parseInt(testData.NumberOfParams); j++) {
			testData.PatientsUserNameArray[j - 1] = (String) propertyData.getClass().getMethod("getUserNameBulk" + j)
					.invoke(propertyData);
			testData.PatientsPasswordArray[j - 1] = (String) propertyData.getClass().getMethod("getPasswordBulk" + j)
					.invoke(propertyData);
			testData.PatientsIDArray[j - 1] = (String) propertyData.getClass().getMethod("getUserPatiendIDBulk" + j)
					.invoke(propertyData);
			testData.PatientEmailArray[j - 1] = (String) propertyData.getClass().getMethod("getUserEmailBulk" + j)
					.invoke(propertyData);
		}

		for (int i = 1; i <= Integer.parseInt(testData.NumberOfParams); i++) {
			testData.ParamNameArray[i - 1] = (String) propertyData.getClass().getMethod("getParamName" + i)
					.invoke(propertyData);
			testData.ParamValueArray[i - 1] = (String) propertyData.getClass().getMethod("getParamValue" + i)
					.invoke(propertyData);
		}

		testData.oUserName = propertyData.getresendMessageToPatientUserName();
		testData.oPassword = propertyData.getresendMessageToPatientPassword();
		testData.oPatientID = propertyData.getresendMessageToPatientID();
		testData.oEmailID = propertyData.getresendMessageToPatientEmail();
		testData.MessageBulk = propertyData.getMessageBulk();

		testData.Subject = propertyData.getSubject();
		testData.FileName = propertyData.getFileName();
		testData.BulkEmailType = propertyData.getBulkEmailType();
		testData.AddAttachment = propertyData.getAddAttachment();
		testData.PatientExternalId = propertyData.getPatientExternalId();

		testData.categoryType = propertyData.getCategoryType();
		testData.fileName = propertyData.getFileNameAMDC();
		testData.mimeType = propertyData.getMimeType();
		testData.attachmentBody = propertyData.getAttachmentBody();
		testData.downloadLocation = propertyData.getDownloadFileLocation();
		testData.portalCategoryType = propertyData.getPortalCategoryType();

		return testData;
	}

	public SendDirectMessage loadSendDirectMessageDataFromProperty(SendDirectMessage testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.RestUrl = propertyData.getRestURL_SDM();
		testData.ResponsePath = propertyData.getResponsePath();
		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();
		testData.OAuthAppToken = propertyData.getOAuthAppToken_SDM();
		testData.OAuthUsername = propertyData.getOAuthUsername_SDM();
		testData.OAuthPassword = propertyData.getOAuthPassword_SDM();
		testData.Subject = propertyData.getSubject_SDM();
		testData.SecureDirectMessageURL = propertyData.getSecureEmailURL_SDM();
		testData.SecureDirectMessageUsername = propertyData.getSecureEmailUsername_SDM();
		testData.SecureDirectMessagePassword = propertyData.getSecureEmailPassword_SDM();
		testData.ApplicationName = propertyData.getApplicationName_SDM();
		testData.MessageBody = propertyData.getMessageBody_SDM();
		testData.FileName = propertyData.getFileName_SDM();
		testData.PatientID = propertyData.getPatientId_SDM();
		testData.XMLAttachmentFileLocation = propertyData.getAttachmentLocationXML_SDM();
		testData.PDFAttachmentFileLocation = propertyData.getAttachmentLocationPDF_SDM();
		testData.PNGAttachmentFileLocation = propertyData.getAttachmentLocationPNG_SDM();
		testData.AttachmentType = propertyData.getAttachmentType_SDM();
		testData.FromEmalID = propertyData.getFromSecureEmailID_SDM();
		testData.ToEmalID = propertyData.getToSecureEmailID_SDM();
		testData.TOCName = propertyData.getTOCName_SDM();
		testData.MessageStatus = propertyData.getMessageStatus_SDM();
		testData.unseenMessageHeader = propertyData.getUnseenMessageHeaderURL_SDM();
		testData.unseenMessageBody = propertyData.getUnseenMessageBodyURL_SDM();
		testData.messageStatusUpdate = propertyData.getMessageStatusUpdate_SDM();
		testData.messageStatusToUpdate = propertyData.getMessageStatusToUpdate_SDM();

		testData.messageHeaderURL = propertyData.getMessageHeaderURL_SDM();
		testData.invalidPracticeMessageHeaderURL = propertyData.getInvalidPracticeMessageHeaderURL_SDM();
		testData.invalidEmailMessageHeaderURL = propertyData.getInvalidEmailMessageHeaderURL_SDM();
		testData.invalidUID = propertyData.getInvalidUID_SDM();
		testData.validPracticeID = propertyData.getValidPracticeID_SDM();

		testData.token = propertyData.getDeleteToken();
		return testData;
	}

	public DirectorySearch loadDirectorySearchDataFromProperty(DirectorySearch testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.RestUrl = propertyData.getRestURL_SD();

		testData.RestUrl = propertyData.getRestURL_SD();
		testData.DirectAddressOrganization = propertyData.getDirectAddress_organization_DS();
		testData.DirectAddressProvider = propertyData.getDirectAddress_provider_DS();
		testData.OAuthAppToken = propertyData.getOAuthAppToken_DS();
		testData.OAuthUsername = propertyData.getOAuthUsername_DS();
		testData.OAuthPassword = propertyData.getOAuthPassword_DS();
		testData.SearchLength = propertyData.getSearchLength();
		testData.CSVFilePath = propertyData.getCSVFilePath_DS();
		testData.ResponsePath = propertyData.getResponsePath();
		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();

		return testData;
	}

	public PatientFormsExportInfo loadFormsExportInfofromProperty(PatientFormsExportInfo testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.url_FE = propertyData.geturl_FE();
		testData.patientfilepath_FE = propertyData.getpatientfilepath_FE();
		testData.practiceNew_URL_FE = propertyData.getpracticeNew_URL_FE();
		testData.patient_url_FE = propertyData.getpatient_url_FE();
		testData.patientFirstName_FE = propertyData.getpatientFirstName_FE();
		testData.patientLastName_FE = propertyData.getpatientLastName_FE();
		testData.patientEmailAddress1_FE = propertyData.getpatientEmailAddress1_FE();
		testData.patientEmailCity_FE = propertyData.getpatientEmailCity_FE();
		testData.patientphoneNumber_FE = propertyData.getpatientphoneNumber_FE();
		testData.patientDOBDay1_FE = propertyData.getpatientDOBDay1_FE();
		testData.patientDOBMonth_FE = propertyData.getpatientDOBMonth_FE();
		testData.patientDOBMonthtext_FE = propertyData.getpatientDOBMonthText_FE();
		testData.patientDOBYear_FE = propertyData.getpatientDOBYear_FE();
		testData.patientAddress1_FE = propertyData.getpatientAddress1_FE();
		testData.patientAddress2_FE = propertyData.getpatientAddress2_FE();
		testData.patientCity_FE = propertyData.getpatientCity_FE();
		testData.patientState_FE = propertyData.getpatientState_FE();
		testData.patientZipCode1_FE = propertyData.getpatientZipCode1_FE();
		testData.patientuserid_FE = propertyData.getpatientuserid_FE();
		testData.patientPassword1_FE = propertyData.getpatientPassword1_FE();
		testData.patientSecretQuestion_FE = propertyData.getpatientSecretQuestion_FE();
		testData.patientSecretAnswer_FE = propertyData.getpatientSecretAnswer_FE();
		testData.patientHomePhoneNo_FE = propertyData.getpatientHomePhoneNo_FE();
		testData.patientethnicity_FE = propertyData.getpatientethnicity_FE();
		testData.patientMaritalStatus_FE = propertyData.getpatientMaritalStatus_FE();
		testData.patientWhoIs_FE = propertyData.getpatientWhoIs_FE();
		testData.NameofPrimaryInsurance = propertyData.getNameofPrimaryInsurance();
		testData.NameofsecondaryInsurance = propertyData.getNameofsecondaryInsurance();
		testData.relFirstName = propertyData.getrelFirstNAme();
		testData.relLastName = propertyData.getrelLastNAme();
		testData.relation1 = propertyData.getrelation1();
		testData.phonenumber1 = propertyData.getphonenumber1();
		testData.phonetype1 = propertyData.getphonetype1();
		testData.tetanus1 = propertyData.gettetanus1();
		testData.HPV1 = propertyData.getHPV1();
		testData.Influenza1 = propertyData.getInfluenza1();
		testData.Pneumonia1 = propertyData.getPneumonia1();
		testData.SurgeryName = propertyData.getSurgeryName();
		testData.SurgeryTimeFrame = propertyData.getSurgeryTimeFrame();
		testData.HospitalizationReason = propertyData.getHospitalizationReason();
		testData.HospitalizationTimeFrame = propertyData.getHospitalizationTimeFrame();
		testData.TestTimeFrame = propertyData.getTestTimeFrame();
		testData.NameofDoctorSpeciality = propertyData.getNameofDoctorSpeciality();
		testData.NameDosage = propertyData.getNameDosage();
		testData.times = propertyData.gettimes();
		testData.exercise = propertyData.getexercise();
		testData.day = propertyData.getday();
		testData.ccd_url1_FE = propertyData.getccd_url1_FE();
		testData.oAuthAppTokenCCD1_FE = propertyData.getoAuthAppTokenCCD1_FE();
		testData.oAuthUsernameCCD1_FE = propertyData.getoAuthUsernameCCD1_FE();
		testData.oAuthPasswordCCD1_FE = propertyData.getoAuthPasswordCCD1_FE();
		testData.oAuthKeyStore1_FE = propertyData.getoAuthKeyStore1_FE();
		testData.oAuthProperty1_FE = propertyData.getoAuthProperty1_FE();
		testData.responsePath_CCD1_FE = propertyData.getresponsePath_CCD1_FE();
		testData.practiceURL_FE = propertyData.getpracticeURL_FE();
		testData.practiceUserName_FE = propertyData.getpracticeUserName_FE();
		testData.practicePassword_FE = propertyData.getpracticePassword_FE();
		testData.patientLocation_FE = propertyData.getpatientLocation_FE();
		testData.patientProvider_FE = propertyData.getpatientProvider_FE();
		testData.responsePDF_FE = propertyData.getresponsePDF_FE();
		testData.ccd_PDfUrl_FE = propertyData.getccd_PDfUrl_FE();
		testData.responsePDFBatch_FE = propertyData.getresponsePDFBatch_FE();
		testData.uiPDFFile_FE = propertyData.getUIPDFFile_FE();
		testData.downloadFileLocation = propertyData.getDownloadFileLocation();
		testData.preCheckURL = propertyData.getPreCheckURL();
		testData.preCheckPatientExternalID = propertyData.getPreCheckPatientExternalID();
		testData.appointmentRestUrl = propertyData.getPreCheckAppointmentRestUrl();
		testData.preCheckZip = propertyData.getPreCheckZipCode();
		testData.preCheckDOB = propertyData.getPreCheckDOB();
		testData.preCheckPatientEmailID = propertyData.getPreCheckPatientEmailID_FE();
		testData.preCheckPatientFirstName = propertyData.getPreCheckPatientFirstName_FE();
		testData.preCheckGetPIDC = propertyData.getPreCheckGetPIDC();
		testData.preCheckEmailSubject = propertyData.getPreCheckEmailSubject();
		testData.preCheckEmailLink = propertyData.getPreCheckEmailLink();
		testData.preCheckInsuranceImageType = propertyData.getPreCheckInsuranceImageType();
		return testData;
	}

	public AppointmentData loadAppointmentTypeFromProperty(AppointmentData testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();
		testData.OAuthAppToken = propertyData.getOAuthAppToken_AD();
		testData.OAuthUsername = propertyData.getOAuthUsername_AD();
		testData.OAuthPassword = propertyData.getoAuthPassword_AD();
		testData.ResponsePath = propertyData.getResponsePath();
		testData.AppointmentTypeName = propertyData.getAppointmentTypeName();
		testData.AppointmentTypeID = propertyData.getAppointmentTypeID();
		testData.AppointmentCategoryName = propertyData.getAppointmentCategoryName();
		testData.AppointmentCategoryID = propertyData.getAppointmentCategoryID();
		testData.ActiveFlag = propertyData.getActiveFlag();
		testData.Comment = propertyData.getComment();
		testData.AppointmentTypeUrl = propertyData.getAppointmentTypeUrl();
		return testData;
	}

	public Pharmacies loadPharmaciesFromProperty(Pharmacies testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();
		testData.ResponsePath = propertyData.getResponsePath();
		testData.OAuthAppToken = propertyData.getOAuthAppToken_PH();
		testData.OAuthUsername = propertyData.getOAuthUsername_PH();
		testData.OAuthPassword = propertyData.getoAuthPassword_PH();
		testData.UserName = propertyData.getPatientUsername_PH();
		testData.Password = propertyData.getPatientPassword_PH();
		testData.PharmacyRenewalUrl = propertyData.getPharmacyRenewalUrl();
		testData.URL = propertyData.getpatientUrl();
		testData.ExternalPharmacyId = propertyData.getExternalPharmacyId();
		testData.Line1 = propertyData.getLine1();
		testData.Line2 = propertyData.getLine2();
		testData.City = propertyData.getCity_PH();
		testData.State = propertyData.getState_PH();
		testData.Country = propertyData.getCountry();
		testData.ZipCode = propertyData.getZipCode_PH();
		testData.PharmacyPhone = propertyData.getPharmacyPhone();
		testData.PharmacyFaxNumber = propertyData.getPharmacyFaxNumber();
		return testData;
	}

	public Patient_Login loadLogindata(Patient_Login testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.Url = propertyData.getPatienturl_Event();
		testData.UserName = propertyData.getPatientUsername_Event();
		testData.Password = propertyData.getPatientpassword_Event();
		testData.restUrlLogin_V3 = propertyData.getPatientrestUrlLogin_V3_Event();
		testData.OAuthProperty = propertyData.getPatientoAuthProperty_Event();
		testData.OAuthKeyStore = propertyData.getPatientoAuthKeystore_Event();
		testData.OAuthAppToken = propertyData.getPatientoAuthAppToken_Event();
		testData.OAuthUsername = propertyData.getPatientoAuthUsername_Event();
		testData.OAuthPassword = propertyData.getPatientoAuthPassword_Event();
		testData.ResponsePath = propertyData.getPatientResponsePath_Event();

		return testData;
	}
	
	
	public Attachment loadAttachmentDataFromProperty(Attachment testData) throws IOException {

		PropertyFileLoader propertyData = new PropertyFileLoader();

		testData.restUrl = propertyData.getRestUrlAttachment();
		testData.patientExternalId = propertyData.getPatientExternalIdAttachment();


		return testData;
	}

}
