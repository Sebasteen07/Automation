//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class SendDirectMessageUtils {
	public void sendSecureDirectMessage(WebDriver driver,String typeOfAttachmentUsed) throws Exception {
		SendDirectMessage testData = new SendDirectMessage();
	 	LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
	 	LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);
	 	
		postSecureMessage(driver, testData, typeOfAttachmentUsed);
		
		Log4jUtil.log("Login to Secure Exchange Services");
		SecureExchangeLoginPage SecureLoginPageObject = new SecureExchangeLoginPage(driver,testData.SecureDirectMessageURL);
		SecureExchangeEmailPage SecureEmailPageObject = SecureLoginPageObject.SecureLogin(testData.SecureDirectMessageUsername, testData.SecureDirectMessagePassword);
		
		Log4jUtil.log("Check if Secure Email is recieved");
		SecureEmailPageObject.verifySecureEmail(testData.Subject,testData.AttachmentType,testData.FileName,testData.ToEmalID,testData.FromEmalID,testData.TOCName);	
		
		Log4jUtil.log("Logout");
		SecureEmailPageObject.SignOut();
	 
	}
	
	
	public void postSecureMessage(WebDriver driver, SendDirectMessage testData,String typeOfAttachmentUsed) throws Exception {
	 	
	 	
	 	String testSubject=testData.Subject;
	 	Log4jUtil.log("postSecureMessage Step 1: Set Data ");
	 	testData.AttachmentType = typeOfAttachmentUsed;
	 	testData.Subject = testSubject;
	 	String[] fileNameUpdate = testData.FileName.split("\\.");
	 	testData.FileName = fileNameUpdate[0]+"."+testData.AttachmentType;
	 	Log4jUtil.log("FileName  "+testData.FileName);
	 	
	 	Log4jUtil.log("postSecureMessage Step 2: Generate Payload");
	 	SendDirectMessagePayload directMessagePayload = new SendDirectMessagePayload();
	 	String payload = directMessagePayload.getSendDirectMessagePayload(testData);
	 	Thread.sleep(10);
	 	Log4jUtil.log("payload"+payload);
	 	Log4jUtil.log("postSecureMessage Step 3: Setup Oauth client");
	 	Log4jUtil.log("Message Subject  "+testData.Subject);
	 	RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
	 	Thread.sleep(3000);
	 	Log4jUtil.log("postSecureMessage Step 4: Do Send Direct Message Post Request");
		
		RestUtils.setupHttpPostRequest(testData.RestUrl, payload, testData.ResponsePath);

		Log4jUtil.log("postSecureMessage Step 5: Verify MFMessageId, PartnerMessageId and StatusCode");
		String mfMsgID =RestUtils.verifyDirectMessageResponse(testData.ResponsePath,directMessagePayload.partnerMessageId);
		
		Log4jUtil.log("postSecureMessage Step 6: Verify messageStatus by invoking Get Status on MFMessageId");
		String getStatusUrl=testData.RestUrl.replaceAll("directmessages", "directmessage");
		String processingUrl = getStatusUrl+"/"+mfMsgID+"/status";
		
		boolean completed = false;
		for (int j = 0; j < 3; j++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(6000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isSendDirectMessageProcessed(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		assertTrue(completed==true, "Message processing was not completed in time");
		
		Thread.sleep(800);
		RestUtils.verifyDirectMessageGetStatus(testData.ResponsePath,mfMsgID,testData.FromEmalID,testData.ToEmalID);
		
		
	}
	
	public void deleteMessage(WebDriver driver,String subject, String username,String password,String url) throws Exception {		
		Log4jUtil.log("deleteMessage Step 1: Login to Secure Exchange Services");
		SecureExchangeLoginPage SecureLoginPageObject = new SecureExchangeLoginPage(driver,url);
		SecureExchangeEmailPage SecureEmailPageObject = SecureLoginPageObject.SecureLogin(username, password);

		Log4jUtil.log("deleteMessage Step 2: Search for deleted message if present");
		SecureEmailPageObject.serchForDeleteMessage(subject);
		
		Log4jUtil.log("deleteMessage Step 3: Logout");	
		SecureEmailPageObject.SignOut();
	}
}
