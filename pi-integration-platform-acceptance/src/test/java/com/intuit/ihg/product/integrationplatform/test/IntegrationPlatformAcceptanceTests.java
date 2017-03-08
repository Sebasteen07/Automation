package com.intuit.ihg.product.integrationplatform.test;

import static org.testng.Assert.assertNotNull;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.Appointment;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentTestData;
import com.intuit.ihg.product.integrationplatform.utils.EHDC;
import com.intuit.ihg.product.integrationplatform.utils.EHDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PIDC;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.Payment;
import com.intuit.ihg.product.integrationplatform.utils.PaymentTestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.integrationplatform.utils.StatementPreference;
import com.intuit.ihg.product.integrationplatform.utils.StatementPreferenceTestData;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step1;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step2;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPreferencesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.medfusion.product.object.maps.practice.page.apptrequest.ApptRequestSearchPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.patientportal1.utils.PortalConstants;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;
import com.medfusion.product.patientportal2.pojo.StatementPreferenceType;

/**
 * @author dsalaskar
 * @Date 28/Aug/2015
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformAcceptanceTests extends BaseTestNGWebDriver {

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		String zip = testData.getZipCode();
		String date = testData.getBirthDay();

		String dt = date.substring(0, 2);
		String month = date.substring(3, 5);
		String year = date.substring(6);

		log("Created Patient details");
		log("Practice Patient ID: " + practicePatientId);
		log("Firstname: " + firstName);
		log("Lastname: " + lastName);
		log("Email address: " + email);
		log("Birthdate: " + date);
		log("Zipcode: " + zip);

		String patient = RestUtils.preparePatient(testData.getPatientPath(), practicePatientId, firstName, lastName, dt, month, year, email, zip, null);
		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		log("Step 3: Do a POST call and get processing status URL");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), patient, testData.getResponsePath());

		log("Step 4: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			// wait 60 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");
		// comment code for optimization
		GmailBot gBot = new GmailBot();
		log("Step 5: Checking for the activation link inside the patient Gmail inbox");

		// Searching for the link for patient activation in the Gmail Inbox
		String activationUrl = gBot.findInboxEmailLink(testData.getGmailUsername(), testData.getGmailPassword(), PortalConstants.NewPatientActivationMessage,
				"portal/#/user/activate", 3, false, true);

		log("Step 6: Moving to the link obtained from the email message");
		assertNotNull(activationUrl, "Error: Activation link not found.");
		log("Retrieved activation link is " + activationUrl);

		log("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientActivationPage = new PatientVerificationPage(driver, activationUrl);
		SecurityDetailsPage accountDetailsPage = patientActivationPage.fillPatientInfoAndContinue(zip, month, dt, year);
		/*
		 * JalapenoPatient jalapenoPatient = new JalapenoPatient(); jalapenoPatient.setZipCode(zip); jalapenoPatient.setDOBMonthText(monthstring);
		 * jalapenoPatient.setDOBDay(dt); jalapenoPatient.setDOBYear(year); JalapenoPatientCreateSecurityDetailsPage jalapenoPatientCreateSecurityDetailsPage =
		 * patientActivationPage .fillInPatientDataAndSubmitForm(jalapenoPatient);
		 */
		log("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(email, testData.getPatientPassword(), testData.getSecretQuestion(),
				testData.getSecretAnswer(), testData.getHomePhoneNo());

		log("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));

		log("Logging out");
		jalapenoHomePage.clickOnLogout();

		log("Step 10: Do a GET on PIDC Url to get registered patient");
		// get only patients from last day in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;

		log("Getting patients since timestamp: " + since);

		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 11: Find the patient and check if he is registered");
		RestUtils.isPatientRegistered(testData.getResponsePath(), practicePatientId, firstName, lastName, null);
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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		log("Step 3: Fill Message data");
		long timestamp = System.currentTimeMillis();
		String message = RestUtils.prepareSecureMessage(testData.getSecureMessagePath(), testData.getFrom(), testData.getUserName(), "Test " + timestamp, null);

		String messageID = RestUtils.newMessageID();
		log("Partner Message ID:" + messageID);

		log("Step 4: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), message, testData.getResponsePath());

		log("Step 5: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 6: Check secure message in patient gmail inbox");
		String link = RestUtils.verifyEmailNotification(testData.getGmailUserName(), testData.getGmailPassword(), testData.getSender3(), 3, "Portal 2.0");

		log("Step 7: Login to Patient Portal");
		log("Link is " + link);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, link);
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));

		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

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
		RestUtils.setupHttpGetRequest(testData.getReadCommunicationURL() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 13: Validate the message id and read time in response");
		RestUtils.isReadCommunicationMessage(testData.getResponsePath(), messageID, readdatetimestamp);

		log("Step 14: Reply to the message");
		messagesPage.replyToMessage(driver);
		// TODO: "system is unable to send a reply" message, even if the message
		// is sent

		log("Logging out");
		homePage.clickOnLogout();

		log("Step 15: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		log("Step 16: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 17: Validate message reply");
		RestUtils.isReplyPresent(testData.getResponsePath(), messageIdentifier);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		String ccd = RestUtils.prepareCCD(testData.getCCDPath());

		log("Step 2: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), ccd, testData.getResponsePath());

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
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		log("Step 5: Validate message subject and send date");
		Thread.sleep(1000);
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(messagesPage.isMessageDisplayed(driver, "You have a new health data summary"));
		log("CCD sent date & time is : " + messagesPage.returnMessageSentDate());

		JalapenoCcdViewerPage jalapenoCcdPage = new JalapenoCcdViewerPage(driver);

		log("Step 6: Click on link View health data");
		jalapenoCcdPage.clickBtnViewHealthData();

		log("Step 7: Verify if CCD Viewer is loaded and click Close Viewer");
		jalapenoCcdPage.verifyCCDViewerAndClose();

		log("Logging out");
		homePage.clickOnLogout();
		/*
		 * log("Step 10: Go to patient page"); pMyPatientPage = pMessage.clickMyPatientPage();
		 * 
		 * log("Step 11: Click PHR"); pMyPatientPage.clickPHRWithoutInit(driver); PhrHomePage phrPage = PageFactory.initElements(driver, PhrHomePage.class);
		 * 
		 * log("Step 12: Go to PHR Inbox"); PhrMessagesPage phrMessagesPage = phrPage.clickOnMyMessages(); // assertTrue(phrMessagesPage.isInboxLoaded(), //
		 * "Inbox failed to load properly.");
		 * 
		 * log("Step 13: Click first message"); PhrInboxMessage phrInboxMessage = phrMessagesPage.clickOnFirstMessage();
		 * 
		 * log("Step 14: Validate message subject and send date"); Thread.sleep(1000); assertEquals(phrInboxMessage.getPhrMessageSubject(),
		 * IntegrationConstants.CCD_MESSAGE_SUBJECT, "### Assertion failed for Message subject"); log("######  Message Date :: " + IHGUtil.getEstTiming());
		 * assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));
		 * 
		 * log("Step 15: Click on link ReviewHealthInformation"); PhrDocumentsPage phrDocuments = phrInboxMessage .clickBtnReviewHealthInformationPhr();
		 * 
		 * log("step 16:Click on View health data"); phrDocuments.clickViewHealthInformation();
		 * 
		 * log("step 17:click Close Viewer"); phrDocuments.closeViewer();
		 * 
		 * log("step 18:Click Logout"); phrDocuments.clickLogout();
		 */
		// driver.switchTo().defaultContent();

	}

	/*
	 * @Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class) public void testE2EAppointmentRequest10() throws Exception {
	 * log("Test Case: End to end testing Appointment Request 1.0 for PI"); log("Execution Environment: " + IHGUtil.getEnvironmentType());
	 * log("Execution Browser: " + TestConfig.getBrowserType()); Long timestamp = System.currentTimeMillis(); log("Step 1: Get Data from Excel"); Appointment
	 * aptData = new Appointment(); AppointmentTestData testData = new AppointmentTestData(aptData); log("Url: " + testData.getUrl()); log("User Name: " +
	 * testData.getUserName()); log("Password: " + testData.getPassword()); log("Rest Url: " + testData.getRestUrl()); log("Response Path: " +
	 * testData.getResponsePath()); log("From: " + testData.getFrom()); log("AppointmentPath: " + testData.getAppointmentPath()); log("OAuthProperty: " +
	 * testData.getOAuthProperty()); log("OAuthKeyStore: " + testData.getOAuthKeyStore()); log("OAuthAppToken: " + testData.getOAuthAppToken());
	 * log("OAuthUsername: " + testData.getOAuthUsername()); log("OAuthPassword: " + testData.getOAuthPassword()); log("PracticePassword: "+
	 * testData.getPracticePassword()); log("Practice User Name: "+ testData.getPracticeUserName()); log("Step 2: LogIn"); JalapenoLoginPage loginPage = new
	 * JalapenoLoginPage(driver, testData.getUrl()); JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());
	 * log("Detecting if Home Page is opened"); assertTrue(homePage.isHomeButtonPresent(driver)); log("Step 3: Click on Appointment Button on Home Page");
	 * JalapenoAppointmentRequestPage apptPage = homePage.clickOnAppointment(driver); log("Step 4: Complete Appointment Request Page");
	 * apptPage.clickOnContinueButton(driver); assertTrue(apptPage.fillAndSendTheAppointmentRequest(driver), "Request not submitted");
	 * log("Step 5: Return to Home Page"); homePage = apptPage.returnToHomePage(driver); log("Step 6: Logout of Patient Portal"); homePage.logout(driver);
	 * log("Step 7: Setup Oauth client"); RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
	 * testData.getOAuthUsername(), testData.getOAuthPassword()); log("Step 8: Get Appointment Rest call"); // get only messages from last hour in epoch time to
	 * avoid transferring // lot of data Long since = timestamp / 1000L - 60 * 24; log("Getting messages since timestamp: " + since); // do the call and save xml,
	 * ",0" is there because of the since // attribute format RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0",
	 * testData.getResponsePath()); log("Step 9: Checking reason in the response xml"); String reason = "Illness";
	 * RestUtils.isReasonResponseXMLValid(testData.getResponsePath(), reason); String arSMSubject = "Reply to Appointment Request"; String arSMBody =
	 * "This is reply to AR for "+reason; String postXML = RestUtils.findValueOfChildNode( testData.getResponsePath(), "AppointmentRequest", reason, arSMSubject,
	 * arSMBody, testData.getAppointmentPath()); // httpPostRequest method log("Step 10: Do Message Post Request"); String processingUrl =
	 * RestUtils.setupHttpPostRequest( testData.getRestUrl(), postXML, testData.getResponsePath()); log("Step 11: Get processing status until it is completed");
	 * boolean completed = false; for (int i = 0; i < 3; i++) { // wait 10 seconds so the message can be processed Thread.sleep(120000);
	 * RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath()); if (RestUtils.isMessageProcessingCompleted(testData .getResponsePath())) {
	 * completed = true; break; } } verifyTrue(completed, "Message processing was not completed in time");
	 * log("Step 12: Check secure message in patient gmail inbox"); String emailMessageLink = RestUtils.verifyEmailNotification( testData.getGmailUserName(),
	 * testData.getGmailPassword(), testData.getPracticeName(), 3, "Portal 2.0"); log ("Email link is: "+emailMessageLink); // patient Portal validation
	 * log("Step 13: Login to Patient Portal"); JalapenoLoginPage loginPage2 = new JalapenoLoginPage(driver, emailMessageLink); JalapenoHomePage homePage2 =
	 * loginPage2.login(testData.getUserName(), testData.getPassword()); log("Step 14:Click on messages solution"); JalapenoMessagesPage messagesPage =
	 * homePage2.showMessages(driver); assertTrue(messagesPage.assessMessagesElements(), "Inbox failed to load properly.");
	 * log("Step 15: Find & validate message in Inbox"); assertTrue(messagesPage.isMessageDisplayed(driver, arSMSubject));
	 * log("Step 16: Logout of Patient Portal"); homePage2.logout(driver); // Practice portal validation log("Step 17: Login to Practice Portal");
	 * PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL()); PracticeHomePage practiceHome = practiceLogin.login(
	 * testData.getPracticeUserName(), testData.getPracticePassword()); log("Step 18: Click Appt Request tab"); ApptRequestSearchPage apptSearch =
	 * practiceHome.clickApptRequestTab(); //PerformanceReporter.getPageLoadDuration(driver, //ApptRequestSearchPage.PAGE_NAME);
	 * log("Step 19: Search for appt requests"); apptSearch.searchForApptRequests(2, null, null); Thread.sleep(60000); ApptRequestDetailStep1Page detailStep1 =
	 * apptSearch .getRequestDetails(reason); assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
	 * //PerformanceReporter.getPageLoadDuration(driver, //ApptRequestDetailStep1Page.PAGE_NAME); String actualSMSubject =
	 * detailStep1.getPracticeMessageSubject(); assertTrue(detailStep1.getPracticeMessageSubject() .contains(arSMSubject),
	 * "Expected Secure Message Subject containing [" + arSMSubject + "but actual message subject was [" + actualSMSubject + "]"); String actualSMBody =
	 * detailStep1.getPracticeMessageBody(); assertTrue(detailStep1.getPracticeMessageBody().contains(arSMBody), "Expected Secure Message Body containing [" +
	 * arSMBody + "but actual message body was [" + actualSMBody + "]"); log("Step 20: Logout of Practice Portal"); practiceHome.logOut(); }
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		log("PracticePassword: " + testData.getPracticePassword());
		log("Practice User Name: " + testData.getPracticeUserName());

		String reason = "Reason" + timestamp;

		log("Step 2: LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click on Appointment Button on Home Page");
		JalapenoAppointmentRequestV2Step1 apptPage1 = homePage.clickOnAppointmentV2(driver);
		assertTrue(apptPage1.assessElements());

		log("Step 4: Complete Appointment Request Page");
		JalapenoAppointmentRequestV2Step2 apptPage2 = apptPage1.continueToStep2(driver);

		apptPage2.fillAppointmentRequestForm(reason);
		homePage = apptPage2.submitAppointment(driver);

		log("Step 5: Check if thank you frame is displayd");
		assertTrue(homePage.isTextDisplayed("Thank you"));

		log("Step 6: Logout of Patient Portal");
		homePage.clickOnLogout();

		log("Step 7: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		log("Step 8: Get Appointment Rest call");

		// get only messages from last hour in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;

		log("Getting messages since timestamp: " + since);

		// do the call and save xml, ",0" is there because of the since
		// attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 9: Checking reason in the response xml");
		RestUtils.isReasonResponseXMLValid(testData.getResponsePath(), reason);

		String arSMSubject = "Reply to Appointment Request";

		String arSMBody = "This is reply to AR for " + reason;

		String postXML =
				RestUtils.findValueOfChildNode(testData.getResponsePath(), "AppointmentRequest", reason, arSMSubject, arSMBody, testData.getAppointmentPath());

		// httpPostRequest method
		log("Step 10: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), postXML, testData.getResponsePath());

		log("Step 11: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(120000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 12: Check secure message in patient gmail inbox");
		String emailMessageLink =
				RestUtils.verifyEmailNotification(testData.getGmailUserName(), testData.getGmailPassword(), testData.getPracticeName(), 3, "Portal 2.0");

		log("Email link is: " + emailMessageLink);

		// patient Portal validation
		log("Step 13: Login to Patient Portal");
		JalapenoLoginPage loginPage2 = new JalapenoLoginPage(driver, emailMessageLink);
		JalapenoHomePage homePage2 = loginPage2.login(testData.getUserName(), testData.getPassword());

		log("Step 14:Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage2.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		log("Step 15: Find & validate message in Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, arSMSubject));

		log("Step 16: Logout of Patient Portal");
		homePage2.clickOnLogout();

		// Practice portal validation
		log("Step 17: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(), testData.getPracticePassword());

		log("Step 18: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		// PerformanceReporter.getPageLoadDuration(driver,
		// ApptRequestSearchPage.PAGE_NAME);

		log("Step 19: Search for appt requests");
		apptSearch.searchForApptRequests(2, null, null);
		Thread.sleep(120000);
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(reason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		// PerformanceReporter.getPageLoadDuration(driver,
		// ApptRequestDetailStep1Page.PAGE_NAME);

		String actualSMSubject = detailStep1.getPracticeMessageSubject();
		assertTrue(actualSMSubject.contains(arSMSubject),
				"Expected Secure Message Subject containing [" + arSMSubject + "but actual message subject was [" + actualSMSubject + "]");

		String actualSMBody = detailStep1.getPracticeMessageBody();
		assertTrue(actualSMBody.contains(arSMBody), "Expected Secure Message Body containing [" + arSMBody + "but actual message body was [" + actualSMBody + "]");

		log("Step 20: Logout of Practice Portal");
		practiceHome.logOut();

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2E_OLBP20() throws Exception {

		log("Test Case: End to end testing Online Bill Pay 2.0 for PI");

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		Payment paymentData = new Payment();
		PaymentTestData OLBPData = new PaymentTestData(paymentData);

		log("URL: " + OLBPData.getUrl());
		log("User Name: " + OLBPData.getUserName());
		log("Password: " + OLBPData.getPassword());

		log("Step 2: Generate required payment related test data");
		Long timestamp = System.currentTimeMillis();
		String accountNumber = IHGUtil.createRandomNumericString();
		String amount = IHGUtil.createRandomNumericString(3);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);
		String CClastdig = creditCard.getLastFourDigits();
		String CCtype = "MasterCard";
		String amt = amount.substring(0, 1) + "." + amount.substring(1);

		log("Step 3: LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, OLBPData.getUrl());
		JalapenoHomePage homePage = loginPage.login(OLBPData.getUserName(), OLBPData.getPassword());

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));

		log("Step 4: Click on Make Payment Link ");
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		assertTrue(payBillsPage.assessPayBillsMakePaymentPageElements());

		log("Step 5: Set Make Payments Fields ");
		payBillsPage.removeAllCards();
		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, accountNumber, creditCard);

		log("Step 6: fetch confirmation number ");
		assertTrue(confirmationPage.assessPayBillsConfirmationPageElements());
		homePage = confirmationPage.commentAndSubmitPayment("Testing payment from number: " + accountNumber);

		log("Step 7: Logout of Patient Portal");
		assertTrue(homePage.wasPayBillsSuccessfull());
		String confirmationnumber = homePage.getConfirmationNumberFromPayment();
		homePage.clickOnLogout();

		log("Step 8: Setup Oauth client 2.O");
		RestUtils.oauthSetup(OLBPData.getOAuthKeyStore(), OLBPData.getOAuthProperty(), OLBPData.getOAuthAppToken(), OLBPData.getOAuthUsername(),
				OLBPData.getOAuthPassword());

		log("Step 9: Getting messages since timestamp: " + timestamp);
		RestUtils.setupHttpGetRequest(OLBPData.getRestUrl() + "?since=" + timestamp, OLBPData.getResponsePath());

		log("Step 10: Verify payment details");
		RestUtils.isPaymentAppeared(OLBPData.getResponsePath(), accountNumber, amt, CClastdig, CCtype, IntegrationConstants.SUBMITTED, confirmationnumber);

		log("Step 10: Generate unique Message Subject and set messageThreadID as PaymentId");
		String messageThreadID = RestUtils.paymentID;
		log("Payment ID :" + messageThreadID);
		String reply_Subject = "Test " + IHGUtil.createRandomNumericString();

		String message = RestUtils.prepareSecureMessage(OLBPData.getcommunicationXML(), OLBPData.getFrom(), OLBPData.getUserName(), reply_Subject, messageThreadID);

		log("POST AMDC: " + message);

		log("Step 11: Do Message Post AMDC Request");
		String processingUrl = RestUtils.setupHttpPostRequest(OLBPData.getCommRestUrl(), message, OLBPData.getResponsePath());

		log("Step 12: Get processing status until it is completed");
		boolean completed = false;
		// wait 90 seconds so the message can be processed
		Thread.sleep(90000);
		RestUtils.setupHttpGetRequest(processingUrl, OLBPData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(OLBPData.getResponsePath())) {
			completed = true;
		}

		verifyTrue(completed, "Message processing was not completed in time");

		// wait 90 seconds so the email-notification is delivered
		Thread.sleep(90000);

		log("Step 13: Check secure message in patient gmail inbox");
		RestUtils.verifyEmailNotification(OLBPData.getGmailUserName(), OLBPData.getGmailPassword(), OLBPData.getPracticeName(), 3, "Portal 2.0");

		// patient Portal validation
		log("Step 14: Login to Patient Portal");
		JalapenoLoginPage ploginPage = new JalapenoLoginPage(driver, OLBPData.getUrl());
		JalapenoHomePage inboxPage = ploginPage.login(OLBPData.getUserName(), OLBPData.getPassword());

		log("Step 15: Detecting if Home Page is opened");
		assertTrue(inboxPage.isHomeButtonPresent(driver));

		log("Step 16: Click on messages solution");
		JalapenoMessagesPage messagesPage = inboxPage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		log("Step 17: Validate message loads and is the right message");
		assertTrue(messagesPage.isMessageDisplayed(driver, reply_Subject));

		// wait 60 seconds so the message can be processed
		Thread.sleep(60000);

		log("Step 18: Reply to the message");
		messagesPage.replyToMessage(driver);

		log("Step 19: Logging out");
		inboxPage.clickOnLogout();

		// Wait 60 seconds, so the message can be processed
		Thread.sleep(60000);

		log("Step 20: Do a GET AMDC and verify patient reply in Get AMDC response");
		Long since = timestamp / 1000L - 60 * 24;
		RestUtils.setupHttpGetRequest(OLBPData.getCommRestUrl() + "?since=" + since + ",0", OLBPData.getResponsePath());

		log("Step 21: Validate message reply");
		RestUtils.isReplyPresent(OLBPData.getResponsePath(), reply_Subject);

		String postPayload = RestUtils.preparePayment(OLBPData.getPaymentPath(), messageThreadID, null, IntegrationConstants.BILLPAYMENT);

		log("Step 22: Do a Post and get the message");
		processingUrl = RestUtils.setupHttpPostRequest(OLBPData.getRestUrl(), postPayload, OLBPData.getResponsePath());

		// wait 60 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, OLBPData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(OLBPData.getResponsePath())) {
			completed = true;
		}
		verifyTrue(completed, "Message processing was not completed in time");

		log("Verify Payment status in Practice Portal");
		log("Step 23: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, OLBPData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(OLBPData.getPracticeUserName(), OLBPData.getPracticePassword());

		log("Step 24: Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();

		log("Step 25: Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		log("Step 26: Search For Payment By Status ");
		onlineBillPaySearchPage.searchForBillStatus(2);

		log("Step 27: Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage.searchForBillPayment(accountNumber);

		String Status = onlineBillPaySearchPage.getBillDetails();
		assertNotNull(Status, "The submitted Online Bill request was not found in the practice");

		log("Step 28: Logout of Practice Portal");
		practiceHome.logOut();

	}

	/*
	 * Test to be updated once PayHere PI related automation is in place public void testPayNow() throws Exception {
	 * 
	 * log("Test Case: testPayNow - No login payment"); log("Execution Environment: " + IHGUtil.getEnvironmentType()); log("Execution Browser: " +
	 * TestConfig.getBrowserType());
	 * 
	 * log("Step 1: Get Data from Excel"); PayNow payNowData = new PayNow(); PayNowTestData PayHereData = new PayNowTestData(payNowData);
	 * 
	 * log("URL: " + PayHereData.getUrl());
	 * 
	 * log("Step 2: Generate required payment related test data"); Long timestamp = System.currentTimeMillis();
	 * 
	 * log("Step 3: Verify payment OK"); assertTrue(driver.getPageSource() .contains("Thank You for your payment"));
	 * 
	 * log("Step 4: Verify account set to N/A"); verifyTrue(driver.getPageSource().contains("Account N/A."));
	 * 
	 * //log("Step 5: Verify the prize format."); //verifyTrue(driver.getPageSource().contains( // "$" + pNoLoginPaymentPage.GetAmountPrize() + ".00"));
	 * Thread.sleep(60000);
	 * 
	 * log("Step 6: fetch confirmation number "); //String confirmationNumber = pNoLoginPaymentPage // .readConfirmationNumber();
	 * 
	 * log("Step 7: Setup Oauth client 2.O"); RestUtils.oauthSetup(PayHereData.getOAuthKeyStore(), PayHereData.getOAuthProperty(), PayHereData.getOAuthAppToken(),
	 * PayHereData.getOAuthUsername(), PayHereData.getOAuthPassword());
	 * 
	 * log("Step 8: Getting messages since timestamp: " + timestamp); String lastTimestamp = RestUtils.setupHttpGetRequest( PayHereData.getRestUrl() +
	 * "=payNowpayment" + "&since=" + timestamp, PayHereData.getResponsePath());
	 * 
	 * log("Step 9: Verify payment details"); //RestUtils.verifyPayment(PayHereData.getResponsePath(), // 23.00 + ".00", // IntegrationConstants.SUBMITTED, //
	 * IntegrationConstants.PAYNOWPAYMENT, confirmationNumber);
	 * 
	 * String paymentID = RestUtils.paymentID; log("Payment ID :" + paymentID);
	 * 
	 * String postPayload = RestUtils.preparePayment( PayHereData.getPaymentPath(), paymentID, 24.00 + ".00", IntegrationConstants.PAYNOWPAYMENT);
	 * 
	 * log("Step 10: Do a Post and get the message"); String processingUrl = RestUtils.setupHttpPostRequest( PayHereData.getRestUrl() + "=payNowpayment",
	 * postPayload, PayHereData.getResponsePath());
	 * 
	 * log("Step 11: Get processing status until it is completed"); boolean completed = false; // wait 10 seconds so the message can be processed
	 * Thread.sleep(60000); RestUtils.setupHttpGetRequest(processingUrl, PayHereData.getResponsePath()); if (RestUtils.isMessageProcessingCompleted(PayHereData
	 * .getResponsePath())) { completed = true; }
	 * 
	 * verifyTrue(completed, "Message processing was not completed in time");
	 * 
	 * log("Verify Payment status in Practice Portal"); log("Step 12: Login to Practice Portal"); PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
	 * PayHereData.getPracticeURL()); PracticeHomePage practiceHome = practiceLogin.login( PayHereData.getPracticeUserName(), PayHereData.getPracticePassword());
	 * 
	 * log("Step 13: Click on Virtual Card Swiper Tab "); VirtualCardSwiperPage vcsPage = practiceHome .clickVirtualCardSwiperTab();
	 * 
	 * log("Step 14: Click on Charge History Link "); VirtualCardSwiperPageChargeHistory vcsPageChargeHistory = vcsPage .lnkChargeHistoryclick(driver);
	 * 
	 * log("Step 15: Search for payment "); vcsPageChargeHistory.SearchPayment(1);
	 * 
	 * //String Status = vcsPageChargeHistory.getBillDetails("$" // + pNoLoginPaymentPage.GetAmountPrize() + ".00"); //assertNotNull(Status, //
	 * "The submitted pay now request was not found in the practice ");
	 * 
	 * log("Step 16: Logout of Practice Portal "); practiceHome.logOut();
	 * 
	 * log("Step 17: Verify Payment status in Get Response using the Timestamp received in response of Step 7");
	 * RestUtils.setupHttpGetRequest(PayHereData.getRestUrl() + "=payNowpayment" + "&since=" + lastTimestamp, PayHereData.getResponsePath()); }
	 */


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testStatementPreference() throws Exception {
		log("Test Case: Statement Preference in Portal 2.0");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		StatementPreference statementPreferenceData = new StatementPreference();
		StatementPreferenceTestData testData = new StatementPreferenceTestData(statementPreferenceData);

		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Patient's First Name: " + testData.getFirstName());
		log("Patient's Last Name: " + testData.getLastName());
		log("Rest Url: " + testData.getRestUrl());
		log("Statement Path: " + testData.getStatementPath());
		log("Response Path: " + testData.getResponsePath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());

		Long timeStamp = System.currentTimeMillis();

		log("Step 2: LogIn to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click on myaccountLink on MyPatientPage");
		JalapenoMyAccountProfilePage myAccountProfilePage = homePage.goToAccountPage();

		log("Step 4: Click on Preferences Tab");
		JalapenoMyAccountPreferencesPage myPreferencePage = myAccountProfilePage.goToPreferencesTab(driver);

		log("Step 5: Set Statement Delievery Preference as Paper Statement");
		myPreferencePage.checkAndSetStatementPreference(driver, StatementPreferenceType.PAPER);

		log("Step 6: Logout from Patient portal");
		homePage.clickOnLogout();

		log("Step 7: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(), testData.getPracticePassword());

		log("Step 8: Search for above patient with first name & last name");
		PatientSearchPage patientSearch = practiceHome.clickPatientSearchLink();
		patientSearch.searchForPatientInPatientSearch(testData.getFirstName(), testData.getLastName());

		log("Step 9: Verify search results");
		IHGUtil.waitForElement(driver, 60, patientSearch.searchResult);
		assertTrue(patientSearch.searchResult.getText().contains(testData.getFirstName()));

		log("Step 10: Get Medfusion Member Id & External Id of the patient");
		PatientDashboardPage patientDashboard = patientSearch.clickOnPatient(testData.getFirstName(), testData.getLastName());
		patientDashboard.editPatientLink();

		String memberId = patientDashboard.medfusionID();
		log("MemberId is " + memberId);
		String externalPatientId = patientDashboard.readExternalPatientID();
		log("External Id is " + externalPatientId);

		practiceHome.logOut();

		log("Step 11: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		log("Step 12: Wait for 60 seconds");
		Thread.sleep(60000);

		log("Step 13: Getting statement preference updates since timestamp: " + timeStamp);
		String nextTimeStamp = RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + timeStamp, testData.getResponsePath());

		log("Step 14: Validate the response");
		RestUtils.isStatementPreferenceCorrect(testData.getResponsePath(), memberId, "PAPER");

		String statementPreference[] = {"E_STATEMENT", "BOTH"};

		for (int i = 0; i < statementPreference.length; i++) {
			log("-----Statement Preference : " + statementPreference[i] + "-----");
			log("Step 15: Prepare payload to set Statement Preference as " + statementPreference[i]);
			if (StringUtils.isBlank(nextTimeStamp))
				timeStamp = System.currentTimeMillis();
			else
				timeStamp = Long.valueOf(nextTimeStamp);

			String payload = RestUtils.preparePostStatementPreference(testData.getStatementPath(), memberId, externalPatientId, statementPreference[i]);

			log("Step 16: Do POST Statement Preference API & set preference to " + statementPreference[i]);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), payload, testData.getResponsePath());

			log("Step 17: Get processing status until it is completed");
			boolean completed = false;
			for (int j = 0; j < 3; j++) {
				Thread.sleep(60000);
				RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
				if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
					completed = true;
					break;
				}
			}
			assertTrue(completed);

			log("Step 18: Login to Patient Portal");
			JalapenoLoginPage loginPage1 = new JalapenoLoginPage(driver, testData.getUrl());
			JalapenoHomePage homePage1 = loginPage1.login(testData.getUserName(), testData.getPassword());

			log("Step 19: Check for update in Statement Preference");
			JalapenoMyAccountProfilePage myAccountProfilePage1 = homePage1.goToAccountPage();
			JalapenoMyAccountPreferencesPage myPreferencePage1 = myAccountProfilePage1.goToPreferencesTab(driver);

			assertTrue(myPreferencePage1.checkStatementPreferenceUpdated(StatementPreferenceType.valueOf(statementPreference[i])));

			log("Step 20: Logout of Portal");
			homePage1.clickOnLogout();

			log("Step 21: GET Statement Preference API");
			log("Getting statement preference updates since timestamp: " + timeStamp);
			nextTimeStamp = RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + timeStamp, testData.getResponsePath());

			log("Step 22: Validate the response");
			RestUtils.isStatementPreferenceCorrect(testData.getResponsePath(), memberId, statementPreference[i]);
		}
	}
}
