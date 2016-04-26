package com.intuit.ihg.product.integrationplatform.test;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.EHDC;
import com.intuit.ihg.product.integrationplatform.utils.EHDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.Appointment;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentTestData;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.integrationplatform.utils.PIDC;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoPatientActivationPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.jalapeno.page.AppointmentRequestPage.*;
import com.medfusion.product.object.maps.jalapeno.page.CcdViewer.JalapenoCcdPage;
import com.intuit.ihg.product.object.maps.practice.page.*;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.*;

import static org.testng.Assert.assertNotNull;

/**
 * @author dsalaskar
 * @Date 28/Aug/2015
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformAcceptanceTests extends BaseTestNGWebDriver {

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientRegistration() throws Exception {
		log("Test Case: PIDC Patient Registration");
		PIDCTestData testData = loadDataFromExcel();
		// PatientActivationSearchTest patientActivationSearchTest = new
		// PatientActivationSearchTest();

		log("Step 1: Prepare patient to post");
		Long timestamp = System.currentTimeMillis();
		String practicePatientId = "Patient" + timestamp;
		String firstName = "Name" + timestamp;
		String lastName = "TestPatient1" + timestamp;
		String email = IHGUtil.createRandomEmailAddress(testData.getEmail());
		log("Created Email address: " + email);
		log("Practice Patient ID: " + practicePatientId);
		String patient = RestUtils.preparePatient(testData.getPatientPath(),
				practicePatientId, firstName, lastName, email, null);

		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),
				testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		log("Step 3: Do a POST call and get processing status URL");
		String processingUrl = RestUtils.setupHttpPostRequest(
				testData.getRestUrl(), patient, testData.getResponsePath());

		log("Step 4: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			// wait 60 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl,
					testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData
					.getResponsePath())) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");
		// comment code for optimization
		GmailBot gBot = new GmailBot();
		log("Step 5: Checking for the activation link inside the patient Gmail inbox");

		// Searching for the link for patient activation in the Gmail Inbox
		String activationUrl = gBot.findInboxEmailLink(
				testData.getGmailUsername(), testData.getGmailPassword(),
				PortalConstants.NewPatientActivationMessage,
				"portal/#/user/activate", 3, false, true);

		log("Step 6: Moving to the link obtained from the email message");
		assertNotNull(activationUrl, "Error: Activation link not found.");
		log("Retrieved activation link is " + activationUrl);

		log("Finishing of patient activation: step 1 - verifying identity");
		JalapenoPatientActivationPage patientActivationPage = new JalapenoPatientActivationPage(
				driver, activationUrl);

		patientActivationPage
				.verifyPatientIdentity(testData.getZipCode(),
						PortalConstants.DateOfBirthMonth,
						PortalConstants.DateOfBirthDay,
						PortalConstants.DateOfBirthYear);

		log("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = patientActivationPage
				.fillInPatientActivation(email, testData.getPatientPassword(),
						testData.getSecretQuestion(),
						testData.getSecretAnswer(), testData.getHomePhoneNo());

		log("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));

		log("Logging out");
		jalapenoHomePage.logout(driver);

		log("Step 10: Do a GET on PIDC Url to get registered patient");
		// get only patients from last day in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;

		log("Getting patients since timestamp: " + since);

		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since
				+ ",0", testData.getResponsePath());

		log("Step 11: Find the patient and check if he is registered");
		RestUtils.isPatientRegistered(testData.getResponsePath(),
				practicePatientId, firstName, lastName, null);
	}

	private PIDCTestData loadDataFromExcel() throws Exception {
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		PIDC PIDCData = new PIDC();
		PIDCTestData testData = new PIDCTestData(PIDCData);

		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("Patient XML Path: " + testData.getPatientPath());
		log("Response Path: " + testData.getResponsePath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());
		log("BirthDay: " + testData.getBirthDay());
		log("ZipCode: " + testData.getZipCode());
		log("SSN: " + testData.getSSN());
		log("Email: " + testData.getEmail());
		log("PatientPassword: " + testData.getPatientPassword());
		log("SecretQuestion: " + testData.getSecretQuestion());
		log("SecretAnswer: " + testData.getSecretAnswer());

		return testData;
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCSecureMessageWithReadCommnunication() throws Exception {
		log("Test Case: AMDC Secure Message with Read Communication");

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		AMDC AMDCData = new AMDC();
		AMDCTestData testData = new AMDCTestData(AMDCData);

		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("Response Path: " + testData.getResponsePath());
		log("From: " + testData.getFrom());
		log("SecureMessagePath: " + testData.getSecureMessagePath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());

		
		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),
				testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		log("Step 3: Fill Message data");
		long timestamp = System.currentTimeMillis();
		String message = RestUtils.prepareSecureMessage(
				testData.getSecureMessagePath(), testData.getFrom(),
				testData.getUserName(), "Test " + timestamp, null);

		String messageID = RestUtils.newMessageID();
		log("Partner Message ID:" + messageID);

		log("Step 4: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(
				testData.getRestUrl(), message, testData.getResponsePath());

		log("Step 5: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(120000);
			RestUtils.setupHttpGetRequest(processingUrl,
					testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData
					.getResponsePath())) {
				completed = true;
				break;
			}
		}
		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 6: Check secure message in patient gmail inbox");
		RestUtils.verifyEmailNotification(
				testData.getGmailUserName(), testData.getGmailPassword(),
				testData.getSender3(), 3, "Portal 2.0");

		log("Step 7: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());
		
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		
		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements(), "Inbox failed to load properly.");

		log("Step 8: Find message in Inbox");
		String messageIdentifier = "Test " + Long.toString(timestamp);
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
		RestUtils.setupHttpGetRequest(testData.getReadCommunicationURL()
				+ "?since=" + since + ",0", testData.getResponsePath());

		log("Step 13: Validate the message id and read time in response");
		RestUtils.isReadCommunicationMessage(testData.getResponsePath(),
				messageID, readdatetimestamp);

		log("Step 14: Reply to the message");
		messagesPage.replyToMessage(driver);
		//TODO: "system is unable to send a reply" message, even if the message is sent

		log("Logging out");
		homePage.logout(driver);
		
		log("Step 15: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		log("Step 16: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since
				+ ",0", testData.getResponsePath());

		log("Step 17: Validate message reply");
		RestUtils.isReplyPresent(testData.getResponsePath(), messageIdentifier);

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEHDCsendCCD() throws Exception {

		log("Test Case: send a CCD and check in patient Portal");
		EHDC EHDCData = new EHDC();
		EHDCTestData testData = new EHDCTestData(EHDCData);
		
		log("UserName: " + testData.getUserName());
		log("Password:" + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("CCD Path: " + testData.getCCDPath());
		log("Response Path: " + testData.getResponsePath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());

		log("Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),
				testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		String ccd = RestUtils.prepareCCD(testData.getCCDPath());

		log("Step 2: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(
				testData.getRestUrl(), ccd, testData.getResponsePath());

		log("Processing URL: " + processingUrl);
		log("Step 3: Get processing status until it is completed");
		Thread.sleep(60000);
				
		log("Step 4: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getURL());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());
		
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		
		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements(), "Inbox failed to load properly.");

		log("Step 5: Validate message subject and send date");
		Thread.sleep(1000);
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(messagesPage.isMessageDisplayed(driver, "You have new health data"));
		log("CCD sent date & time is : "+messagesPage.returnMessageSentDate());
		
		JalapenoCcdPage jalapenoCcdPage = new JalapenoCcdPage(driver);
					
		log("Step 6: Click on link View health data");
		jalapenoCcdPage.clickBtnViewHealthData();
		
		log("Step 7: Verify if CCD Viewer is loaded and click Close Viewer");
		jalapenoCcdPage.verifyCCDViewerAndClose();
		
		log("Logging out");
		homePage.logout(driver);
		/*
		log("Step 10: Go to patient page");
		pMyPatientPage = pMessage.clickMyPatientPage();

		log("Step 11: Click PHR");
		pMyPatientPage.clickPHRWithoutInit(driver);
		PhrHomePage phrPage = PageFactory.initElements(driver,
				PhrHomePage.class);

		log("Step 12: Go to PHR Inbox");
		PhrMessagesPage phrMessagesPage = phrPage.clickOnMyMessages();
		// assertTrue(phrMessagesPage.isInboxLoaded(),
		// "Inbox failed to load properly.");

		log("Step 13: Click first message");
		PhrInboxMessage phrInboxMessage = phrMessagesPage.clickOnFirstMessage();

		log("Step 14: Validate message subject and send date");
		Thread.sleep(1000);
		assertEquals(phrInboxMessage.getPhrMessageSubject(),
				IntegrationConstants.CCD_MESSAGE_SUBJECT,
				"### Assertion failed for Message subject");
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("Step 15: Click on link ReviewHealthInformation");
		PhrDocumentsPage phrDocuments = phrInboxMessage
				.clickBtnReviewHealthInformationPhr();

		log("step 16:Click on View health data");
		phrDocuments.clickViewHealthInformation();

		log("step 17:click Close Viewer");
		phrDocuments.closeViewer();

		log("step 18:Click Logout");
		phrDocuments.clickLogout();
*/
		// driver.switchTo().defaultContent();

	}

	/*
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAppointmentRequest10() throws Exception {

		log("Test Case: End to end testing Appointment Request 1.0 for PI");

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		Long timestamp = System.currentTimeMillis();
		log("Step 1: Get Data from Excel");
		Appointment aptData = new Appointment();
		AppointmentTestData testData = new AppointmentTestData(aptData);

		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("Response Path: " + testData.getResponsePath());
		log("From: " + testData.getFrom());
		log("AppointmentPath: " + testData.getAppointmentPath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());
		log("PracticePassword: "+ testData.getPracticePassword());
		log("Practice User Name: "+ testData.getPracticeUserName());

		log("Step 2: LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		
		log("Step 3: Click on Appointment Button on Home Page");
		JalapenoAppointmentRequestPage apptPage = homePage.clickOnAppointment(driver);
				
		log("Step 4: Complete Appointment Request Page");
		apptPage.clickOnContinueButton(driver);
		assertTrue(apptPage.fillAndSendTheAppointmentRequest(driver), "Request not submitted");
													
		log("Step 5: Return to Home Page");
		homePage = apptPage.returnToHomePage(driver);

		log("Step 6: Logout of Patient Portal");
		homePage.logout(driver);

		log("Step 7: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),
				testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		log("Step 8: Get Appointment Rest call");

		// get only messages from last hour in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;

		log("Getting messages since timestamp: " + since);

		// do the call and save xml, ",0" is there because of the since
		// attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since
				+ ",0", testData.getResponsePath());

		log("Step 9: Checking reason in the response xml");
		String reason = "Illness";
		RestUtils.isReasonResponseXMLValid(testData.getResponsePath(), reason);

		String arSMSubject = "Reply to Appointment Request";
				
		String arSMBody = "This is reply to AR for "+reason;
		
		String postXML = RestUtils.findValueOfChildNode(
				testData.getResponsePath(), "AppointmentRequest", reason,
				arSMSubject, arSMBody, testData.getAppointmentPath());

		// httpPostRequest method
		log("Step 10: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(
				testData.getRestUrl(), postXML, testData.getResponsePath());

		log("Step 11: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(120000);
			RestUtils.setupHttpGetRequest(processingUrl,
					testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData
					.getResponsePath())) {
				completed = true;
				break;
			}
		}
		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 12: Check secure message in patient gmail inbox");
		String emailMessageLink = RestUtils.verifyEmailNotification(
				testData.getGmailUserName(), testData.getGmailPassword(),
				testData.getPracticeName(), 3, "Portal 2.0");

		log ("Email link is: "+emailMessageLink);
		// patient Portal validation
		log("Step 13: Login to Patient Portal");
		JalapenoLoginPage loginPage2 = new JalapenoLoginPage(driver, emailMessageLink);
		JalapenoHomePage homePage2 = loginPage2.login(testData.getUserName(), testData.getPassword());
		
		log("Step 14:Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage2.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements(), "Inbox failed to load properly.");

		log("Step 15: Find & validate message in Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, arSMSubject));

		log("Step 16: Logout of Patient Portal");
		homePage2.logout(driver);
		
		// Practice portal validation
		log("Step 17: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(
				testData.getPracticeUserName(), testData.getPracticePassword());

		log("Step 18: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		//PerformanceReporter.getPageLoadDuration(driver,
				//ApptRequestSearchPage.PAGE_NAME);

		log("Step 19: Search for appt requests");
		apptSearch.searchForApptRequests(2, null, null);
		Thread.sleep(60000);
		ApptRequestDetailStep1Page detailStep1 = apptSearch
				.getRequestDetails(reason);
		assertNotNull(detailStep1,
				"The submitted patient request was not found in the practice");
		//PerformanceReporter.getPageLoadDuration(driver,
				//ApptRequestDetailStep1Page.PAGE_NAME);

		String actualSMSubject = detailStep1.getPracticeMessageSubject();
		assertTrue(detailStep1.getPracticeMessageSubject()
				.contains(arSMSubject),
				"Expected Secure Message Subject containing [" + arSMSubject
						+ "but actual message subject was [" + actualSMSubject
						+ "]");

		String actualSMBody = detailStep1.getPracticeMessageBody();
		assertTrue(detailStep1.getPracticeMessageBody().contains(arSMBody),
				"Expected Secure Message Body containing [" + arSMBody
						+ "but actual message body was [" + actualSMBody + "]");

		log("Step 20: Logout of Practice Portal");
		practiceHome.logOut();

	}
	*/
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAppointmentRequest20() throws Exception {

		log("Test Case: End to end testing Appointment Request 2.0 for PI");

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		Long timestamp = System.currentTimeMillis();
		log("Step 1: Get Data from Excel");
		Appointment aptData = new Appointment();
		AppointmentTestData testData = new AppointmentTestData(aptData);

		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("Response Path: " + testData.getResponsePath());
		log("From: " + testData.getFrom());
		log("AppointmentPath: " + testData.getAppointmentPath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());
		log("PracticePassword: "+ testData.getPracticePassword());
		log("Practice User Name: "+ testData.getPracticeUserName());
		
		String reason = IntegrationConstants.AR_REASON;

		log("Step 2: LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		
		log("Step 3: Click on Appointment Button on Home Page");
		JalapenoAppointmentRequestV2Step1 apptPage1 = homePage.clickOnAppointmentV2(driver);
				
		log("Step 4: Complete Appointment Request Page");
		apptPage1.chooseFirstProvider();
		JalapenoAppointmentRequestV2Step2 apptPage2 = apptPage1.continueToStep2(driver);
		
		apptPage2.fillAppointmentRequestForm(reason);
		homePage = apptPage2.submitAppointment(driver);
		
		log("Check if thank you frame is displayd");
		assertTrue(homePage.isTextDisplayed("Thank you"));
		
		log("Step 6: Logout of Patient Portal");
		homePage.logout(driver);

		log("Step 7: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),
				testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		log("Step 8: Get Appointment Rest call");

		// get only messages from last hour in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;

		log("Getting messages since timestamp: " + since);

		// do the call and save xml, ",0" is there because of the since
		// attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since
				+ ",0", testData.getResponsePath());

		log("Step 9: Checking reason in the response xml");		
		RestUtils.isReasonResponseXMLValid(testData.getResponsePath(), reason);

		String arSMSubject = "Reply to Appointment Request";
				
		String arSMBody = "This is reply to AR for "+reason;
		
		String postXML = RestUtils.findValueOfChildNode(
				testData.getResponsePath(), "AppointmentRequest", reason,
				arSMSubject, arSMBody, testData.getAppointmentPath());

		// httpPostRequest method
		log("Step 10: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(
				testData.getRestUrl(), postXML, testData.getResponsePath());

		log("Step 11: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(120000);
			RestUtils.setupHttpGetRequest(processingUrl,
					testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData
					.getResponsePath())) {
				completed = true;
				break;
			}
		}
		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 12: Check secure message in patient gmail inbox");
		String emailMessageLink = RestUtils.verifyEmailNotification(
				testData.getGmailUserName(), testData.getGmailPassword(),
				testData.getPracticeName(), 3, "Portal 2.0");

		log ("Email link is: "+emailMessageLink);
		// patient Portal validation
		log("Step 13: Login to Patient Portal");
		JalapenoLoginPage loginPage2 = new JalapenoLoginPage(driver, emailMessageLink);
		JalapenoHomePage homePage2 = loginPage2.login(testData.getUserName(), testData.getPassword());
		
		log("Step 14:Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage2.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements(), "Inbox failed to load properly.");

		log("Step 15: Find & validate message in Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, arSMSubject));

		log("Step 16: Logout of Patient Portal");
		homePage2.logout(driver);
		
		// Practice portal validation
		log("Step 17: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(
				testData.getPracticeUserName(), testData.getPracticePassword());

		log("Step 18: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		//PerformanceReporter.getPageLoadDuration(driver,
			//	ApptRequestSearchPage.PAGE_NAME);

		log("Step 19: Search for appt requests");
		apptSearch.searchForApptRequests(2, null, null);
		Thread.sleep(60000);
		ApptRequestDetailStep1Page detailStep1 = apptSearch
				.getRequestDetails(reason);
		assertNotNull(detailStep1,
				"The submitted patient request was not found in the practice");
		//PerformanceReporter.getPageLoadDuration(driver,
			//	ApptRequestDetailStep1Page.PAGE_NAME);

		String actualSMSubject = detailStep1.getPracticeMessageSubject();
		assertTrue(detailStep1.getPracticeMessageSubject()
				.contains(arSMSubject),
				"Expected Secure Message Subject containing [" + arSMSubject
						+ "but actual message subject was [" + actualSMSubject
						+ "]");

		String actualSMBody = detailStep1.getPracticeMessageBody();
		assertTrue(detailStep1.getPracticeMessageBody().contains(arSMBody),
				"Expected Secure Message Body containing [" + arSMBody
						+ "but actual message body was [" + actualSMBody + "]");

		log("Step 20: Logout of Practice Portal");
		practiceHome.logOut();

	}
}
