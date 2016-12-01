package com.intuit.ihg.product.integrationplatform.test;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCPayload;
import com.intuit.ihg.product.integrationplatform.utils.CCDPayload;
import com.intuit.ihg.product.integrationplatform.utils.EHDC;
import com.intuit.ihg.product.integrationplatform.utils.LoadPreTestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;

/**
 * @author rkhambe
 * @Date 22/Nov/2016
 * @Description :-Regression Test for CCD
 * @Note : Optimizing scripts to incorporate different patient, practice and staff. 
 */

public class IntegrationPlatformRegressionTests extends BaseTestNGWebDriver {

	@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testEHDCSendCCD() throws Exception {
		log("Test Case: send a CCD and check in patient Portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		EHDC testData = new EHDC();		
		LoadPreTestDataObj.loadEHDCDataFromProperty(testData);
		log("Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
		String ccd = CCDPayload.getCCDPayload(testData);
		Thread.sleep(6000);
		log("Wait to generate CCD Payload");
		log("Step 2: Do Message Post Request");
		log("ResponsePath: "+testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, ccd, testData.ResponsePath);

		log("Processing URL: " + processingUrl);
		log("Step 3: Get processing status until it is completed");
		Thread.sleep(60000);

		log("Step 4: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));

		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements(), "Inbox failed to load properly.");

		log("Step 5: Validate message subject and send date");
		Thread.sleep(1000);
		log("Message Date" + IHGUtil.getEstTiming());
		assertTrue(messagesPage.isMessageDisplayed(driver, "You have new health data"));
		log("CCD sent date & time is : " + messagesPage.returnMessageSentDate());

		JalapenoCcdViewerPage jalapenoCcdPage = new JalapenoCcdViewerPage(driver);

		log("Step 6: Click on link View health data");
		jalapenoCcdPage.clickBtnViewHealthData();

		log("Step 7: Verify if CCD Viewer is loaded and click Close Viewer");
		jalapenoCcdPage.verifyCCDViewerAndClose();

		log("Logging out");
		homePage.clickOnLogout();
	}
	
	
	 @Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCSecureMessages() throws Exception {
		log("Test Case: AMDC Secure Message with Read Communication");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from property file");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		AMDC testData = new AMDC();

		LoadPreTestDataObj.loadAMDCDataFromProperty(testData);
		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);

		log("Step 3: Fill Message data");
		long timestamp = System.currentTimeMillis();
		String message = AMDCPayload.getAMDCPayload(testData); 

		String messageID = AMDCPayload.messageID;
		log("Partner Message ID:" + messageID);

		log("Step 4: Do Message Post Request");
		log("responsePath: "+testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, message, testData.ResponsePath);

		log("Step 5: Get processing status until it is completed");
		String orignal = "dev3vip-events-core-svc\\.qhg\\.local";
		String modified = "dev3aapp11.qhg.local:8080";
		processingUrl = processingUrl.replaceAll(orignal, modified);
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");

		log("Step 6: Check secure message in patient email inbox");
		String link = null;
		String emailType = testData.GmailUserName.substring(testData.GmailUserName.indexOf("@") + 1);
		emailType = emailType.substring(0, emailType.indexOf('.'));
		if (emailType.contains("gmail")) {
			link = RestUtils.verifyEmailNotification(testData.GmailUserName, testData.GmailPassword, testData.Sender3, 3, "Portal 2.0");
		}
		if (emailType.contains("mailinator")) {
			Mailinator mail = new Mailinator();
			String subject = "New message from " + testData.Sender3;
			String messageLink = "Sign in to view this message";
			link = mail.getLinkFromEmail(testData.GmailUserName, subject, messageLink, 5);
			
		}
		//Wait so that Link can be retrieved from the Email.
		Thread.sleep(5000);
		assertTrue(link!=null, "AMDC Secure Message link not found in mail.");
		link = link.replace("login?redirectoptout=true", "login");
		log("Step 7: Login to Patient Portal");
		log("Link is " + link);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, link);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements(), "Inbox failed to load properly.");
		log("Step 8: Find message in Inbox");
		String messageIdentifier = AMDCPayload.messageIdentifier;
		log("message subject " + messageIdentifier);
		log("Step 9: Log the message read time ");
		long epoch = System.currentTimeMillis() / 1000;

		String readdatetimestamp = RestUtils.readTime(epoch);
		log("Message Read Time:" + readdatetimestamp);

		log("Step 10: Validate message loads and is the right message");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageIdentifier));

		log("Step 11: Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		log("Step 12: Wait 1 min, so the message can be processed");
		Thread.sleep(60000);

		log("Getting messages since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.ReadCommuniationURL + "?since=" + since + ",0", testData.ResponsePath);

		log("Step 13: Validate the message id and read time in response");
		RestUtils.isReadCommunicationMessage(testData.ResponsePath, messageID, readdatetimestamp);

		log("Step 14: Reply to the message");
		messagesPage.replyToMessage(driver);

		log("Logging out");
		homePage.clickOnLogout();

		log("Step 15: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		log("Step 16: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(testData.RestUrl + "?since=" + since + ",0", testData.ResponsePath);

		log("Step 17: Validate message reply");
		RestUtils.isReplyPresent(testData.ResponsePath, messageIdentifier);
	}	 
}