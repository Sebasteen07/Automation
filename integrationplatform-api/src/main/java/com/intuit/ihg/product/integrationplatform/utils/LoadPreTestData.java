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
}