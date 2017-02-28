package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;

import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;

public class LoadPreTestData {

	public EHDC loadEHDCDataFromProperty(EHDC testData) throws IOException {

		PropertyFileLoader propertyData = new PropertyFileLoader();
		
		testData.URL = propertyData.getUrlCCD();
		testData.PHR_URL = propertyData.getPHR_URL();
		testData.UserName = propertyData.getUserNameCCD();
		testData.Password = propertyData.getPasswordCCD();
		testData.RestUrl = propertyData.getRestUrlCCD();
		testData.EPracticeName = propertyData.getEPracticeNameCCD();
		testData.ccdXMLPath = propertyData.getCCDXMLPath();
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
		
		return testData;
	}
	
	public MU2GetEventData loadAPITESTDATAFromProperty(MU2GetEventData testData) throws IOException {

		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.PULLAPI_URL = propertyData.getPULLAPI_URL_MU2();
		testData.OAUTH_PROPERTY = propertyData.getOAuthProperty();
		testData.OAUTH_KEYSTORE = propertyData.getOAuthKeyStore();
		testData.OAUTH_APPTOKEN = propertyData.getOAuthAppToken_MU2();
		testData.OAUTH_USERNAME = propertyData.getOAuthUsername_MU2();
		testData.OAUTH_PASSWORD = propertyData.getOAuthPassword_MU2();
		testData.PUSHAPI_URL = propertyData.getPUSHAPI_URL_MU2();
		testData.PUSH_RESPONSEPATH = propertyData.getResponsePath();
		testData.PORTAL_URL= propertyData.getPORTAL_URL_MU2();
		testData.PORTAL_USERNAME= propertyData.getPORTAL_USERNAME_MU2();
		testData.PORTAL_PASSWORD= propertyData.getPORTAL_PASSWORD_MU2();
		testData.INTUIT_PATIENT_ID= propertyData.getINTUIT_PATIENT_ID_MU2();
		testData.TRANSMIT_EMAIL= propertyData.getTRANSMIT_EMAIL_MU2();
		testData.IMAGE_PATH= propertyData.getIMAGE_PATH_MU2();
		testData.PATIENT_ID= propertyData.getPatient_ID_MU2();
		testData.PATIENT_INVITE_RESTURL=propertyData.getPATIENT_INVITE_RESTURL();
		testData.PATIENT_PRACTICEID=propertyData.getPATIENT_PRACTICEID();
		testData.PATIENT_EXTERNAL_ID=propertyData.getPATIENT_EXTERNAL_ID();
		testData.CCDPATH1=propertyData.getCCDPATH1();
		testData.CCDPATH2=propertyData.getCCDPATH2();
		testData.CCDPATH3=propertyData.getCCDPATH3();
		testData.CCDPATH4=propertyData.getCCDPATH4();
		
		testData.HomePhoneNo=propertyData.getHomePhoneNo();
		testData.SecretQuestion=propertyData.getSecretQuestion();
		testData.SecretAnswer=propertyData.getSecretAnswer();
		testData.BirthDay=propertyData.getBirthDay();
		testData.PatientPassword=propertyData.getPatientPassword();
		
		testData.CCDMessageID1 = propertyData.getCCDMessageID1();
		testData.CCDMessageID2 = propertyData.getCCDMessageID2();
		testData.PatientExternalId_MU2 = propertyData.getPatientExternalId_MU2();
		testData.PatientFirstName_MU2 = propertyData.getPatientFirstName_MU2();
		testData.PatientLastName_MU2=propertyData.getPatientLastName_MU2();
		
		testData.Standard_Email = propertyData.getStandard_Email();
		return testData;
	}
	
	public PIDCInfo loadDataFromProperty(PIDCInfo testData, String channelVersion, String portalVersion) throws Exception {

		PropertyFileLoader propertyData = new PropertyFileLoader();
	
		testData.setBatchSize(propertyData.getBatchSize());

		testData.setoAuthProperty(propertyData.getOAuthProperty());
		testData.setoAuthKeyStore(propertyData.getOAuthKeyStore());
		
		testData.setPatientPath(propertyData.getPatientPath());
		testData.setResponsePath(propertyData.getResponsePath());
		testData.setUsername(propertyData.getUserName());
		testData.setPassword(propertyData.getPassword());
		//testData.setRestUrl(propertyData.getRestUrl());	
		
		testData.setBirthDay(propertyData.getBirthday());
		testData.setZipCode(propertyData.getZipCode());
		testData.setSSN(propertyData.getSSN());
		testData.setEmail(propertyData.getEmail());
		testData.setPatientPassword(propertyData.getPatientPassword());
		testData.setSecretQuestion(propertyData.getSecretQuestion());
		testData.setSecretAnswer(propertyData.getSecretAnswer());
		testData.setPracticeURL(propertyData.getPracticeURL());
		testData.setHomePhoneNo(propertyData.getHomePhoneNo());

		testData.setRelation(propertyData.getRelation());
		testData.setMaritalStatus(propertyData.getMaritalStatus());
		testData.setChooseCommunication(propertyData.getChooseCommunication());
		testData.setInsurance_Type(propertyData.getInsurance_Type());
		testData.setCsvFilePath(propertyData.getCSVFilePath());
		//testData.setPortalVersion(propertyData.getPortalVersion());
		
		
		if (channelVersion.contains("v1")) {
			testData.setRestUrl_20(propertyData.getRestUrl1_20());
		}
		if (channelVersion.contains("v2")) {
			testData.setRestUrl_20(propertyData.getRestUrl2_20());
		}
		
		if(portalVersion.contains("1.0")) {
			if (channelVersion.contains("v1")) {
				testData.setRestUrl(propertyData.getRestUrl1());
			}
			if (channelVersion.contains("v2")) {
				testData.setRestUrl(propertyData.getRestUrl2());
			}
			testData.setPracticeId(propertyData.getPracticeId_PIDC());
			testData.setoAuthAppToken(propertyData.getOAuthAppToken());
			testData.setoAuthUsername(propertyData.getOAuthUsername());
			testData.setoAuthPassword(propertyData.getOAuthPassword());
		}
		if(portalVersion.contains("2.0")) {
			if (channelVersion.contains("v1")) {
				testData.setRestUrl(propertyData.getRestUrl1_20());
			}
			if (channelVersion.contains("v2")) {
				testData.setRestUrl(propertyData.getRestUrl2_20());
			}
			testData.setPracticeId(propertyData.getPracticeId_PIDC_20());
			testData.setoAuthAppToken(propertyData.getOAuthAppToken_20());
			testData.setoAuthUsername(propertyData.getOAuthUsername_20());
			testData.setoAuthPassword(propertyData.getOAuthPassword_20());
		}
		
		return testData;
	}
	
	public AppointmentData loadAppointmentDataFromProperty(AppointmentData testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.AppointmentPath= propertyData.getAppointmentRequestURL();
		testData.EmailUserName= propertyData.getEmail_AD();
		testData.MFPatientId = propertyData.getMedfusionPatientId_AD();
		testData.MFPracticeId = propertyData.getMedfusionPracticeId_AD();
		testData.OAuthAppToken = propertyData.getOAuthAppToken_AD();
		testData.OAuthUsername = propertyData.getOAuthUsername_AD();
		testData.OAuthPassword = propertyData.getoAuthPassword_AD();
		
		testData.OAuthProperty = propertyData.getOAuthProperty();
		testData.OAuthKeyStore = propertyData.getOAuthKeyStore();
		testData.ResponsePath = propertyData.getResponsePath();
		testData.PracticeName = propertyData.getIntegrationPracticeID_AD();
		//testData.PracticeName = propertyData.getPatientPracticeID_AD();
		testData.URL = propertyData.getPracticeURL_AD();
		testData.UserName = propertyData.getUserName_AD();
		testData.Password = propertyData.getPassword_AD();
		testData.PatientPracticeId = propertyData.getPatientPracticeID_AD();
		testData.PreviousAppointmentId = propertyData.getPreviousAppointmentId();
		testData.PATIENT_EXTERNAL_ID = propertyData.getPATIENT_EXTERNAL_ID();
		testData.PATIENT_INVITE_RESTURL=propertyData.getPATIENT_INVITE_RESTURL();
		testData.From = propertyData.getProviderIdentifier_AD();
		
		testData.FirstName = propertyData.getFirstName_AD();
		testData.LastName = propertyData.getLastName_AD();
		testData.SecretQuestion=propertyData.getSecretQuestion();
		testData.SecretAnswer=propertyData.getSecretAnswer();
		testData.HomePhoneNo=propertyData.getHomePhoneNo();
		testData.BatchSize=propertyData.getBatchSize_AD();
		testData.csvFilePath = propertyData.getCSVFILEPATH_AD();
		testData.portalURL = propertyData.getPracticePortalURL_AD();
		testData.practiceUserName = propertyData.getProtalUserName_AD();
		testData.practicePassword = propertyData.getPortalPassword_AD();
		
		return testData;
	}
	
	public StatementEventData loadStatementEventDataFromProperty(StatementEventData testData) throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		testData.Url = propertyData.getUrl_SE();
		testData.UserName = propertyData.getUserName_SE();
		testData.Password = propertyData.getPassword_SE();
		testData.Email = propertyData.getEmail_SE();
		testData.RestUrl = propertyData.getRestUrl_SE();
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
		return testData;
	}
		
}