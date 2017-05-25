package com.intuit.ihg.product.integrationplatform.utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class SendDirectMessageUtils {
	public void sendSecureDirectMessage(WebDriver driver,String typeOfAttachmentUsed) throws Exception {
		SendDirectMessage testData = new SendDirectMessage();
	 	LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
	 	LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);
	 	
		postSecureMessage(driver, testData, typeOfAttachmentUsed);
		
		Log4jUtil.log("Step 7: Login to Secure Exchange Services");
		SecureExchangeLoginPage SecureLoginPageObject = new SecureExchangeLoginPage(driver,testData.SecureDirectMessageURL);
		SecureExchangeEmailPage SecureEmailPageObject = SecureLoginPageObject.SecureLogin(testData.SecureDirectMessageUsername, testData.SecureDirectMessagePassword);
		Thread.sleep(8000);
		
		Log4jUtil.log("Step 8: Check if Secure Email is recieved");
		SecureEmailPageObject.verifySecureEmail(testData.Subject,testData.AttachmentType,testData.FileName,testData.ToEmalID,testData.FromEmalID,testData.TOCName);	
		
		Log4jUtil.log("Step 9: Logout");	
		SecureEmailPageObject.SignOut();
	 
	}
	
	
	public void postSecureMessage(WebDriver driver, SendDirectMessage testData,String typeOfAttachmentUsed) throws Exception {
	 	
	 	
	 	String testSubject=testData.Subject;
		
	 	testData.AttachmentType = typeOfAttachmentUsed;
	 	testData.Subject = testSubject;
	 	String[] fileNameUpdate = testData.FileName.split("\\.");
	 	testData.FileName = fileNameUpdate[0]+"."+testData.AttachmentType;
	 	Log4jUtil.log("FileName  "+testData.FileName);
	 	
	 	Log4jUtil.log("Step 2: Generate Payload");
	 	SendDirectMessagePayload directMessagePayload = new SendDirectMessagePayload();
	 	String payload = directMessagePayload.getSendDirectMessagePayload(testData);
	 	Thread.sleep(4000);
	 	Log4jUtil.log("Step 3: Setup Oauth client");
	 	Log4jUtil.log("Message Subject  "+testData.Subject);
	 	RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
	 	Thread.sleep(3000);
	 	Log4jUtil.log("Step 4: Do Send Direct Message Post Request");
		
		RestUtils.setupHttpPostRequest(testData.RestUrl, payload, testData.ResponsePath);

		Log4jUtil.log("Step 5: Verify MFMessageId, PartnerMessageId and StatusCode");
		String mfMsgID =RestUtils.verifyDirectMessageResponse(testData.ResponsePath,directMessagePayload.partnerMessageId);
		
		Log4jUtil.log("Step 6: Verify messageStatus by invoking Get Status on MFMessageId");
		String getStatusUrl=testData.RestUrl.replaceAll("directmessages", "directmessage");
		String processingUrl = getStatusUrl+"/"+mfMsgID+"/status";
		
		boolean completed = false;
		for (int j = 0; j < 3; j++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isSendDirectMessageProcessed(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		Assert.assertTrue(completed==true, "Message processing was not completed in time");
		
		Thread.sleep(4000);
		RestUtils.verifyDirectMessageGetStatus(testData.ResponsePath,mfMsgID,testData.FromEmalID,testData.ToEmalID);
		
		
	}
}
