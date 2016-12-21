package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;

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
		
		return testData;
	}
}