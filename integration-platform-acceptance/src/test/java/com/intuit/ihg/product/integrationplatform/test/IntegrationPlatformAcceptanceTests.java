package com.intuit.ihg.product.integrationplatform.test;


import java.util.Random;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.product.integrationplatform.page.TestPage;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.Appointment;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentTestData;
import com.intuit.ihg.product.integrationplatform.utils.EHDC;
import com.intuit.ihg.product.integrationplatform.utils.EHDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PIDC;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.phr.page.PhrHomePage;
import com.intuit.ihg.product.phr.page.messages.PhrInboxMessage;
import com.intuit.ihg.product.phr.page.messages.PhrMessagesPage;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.createAccount.CreateAccountPage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxMessage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxPage;
import com.intuit.ihg.product.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep1Page;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep2Page;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep3Page;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep4Page;
import com.intuit.ihg.product.portal.page.solutions.askstaff.AskAStaffHistoryPage;
import com.intuit.ihg.product.portal.page.solutions.askstaff.AskAStaffStep1Page;
import com.intuit.ihg.product.portal.page.solutions.askstaff.AskAStaffStep2Page;
import com.intuit.ihg.product.portal.page.solutions.askstaff.AskAStaffStep3Page;
import com.intuit.ihg.product.portal.utils.PortalConstants;



/**
 * @author bkrishnankutty
 * @Date 5/Aug/2013
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformAcceptanceTests extends BaseTestNGWebDriver{

	/**
	 * @Author:-bkrishnankutty
	 * @Date:-5/Aug/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * List
	 * steps
	 * here
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	
	//@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testSiteGenLoginLogout() throws Exception {

	 log("+++++++++++++ Test run+++++++++++");
	 
	 log("Test Case: testAddandRemovePreferences");
	 log("Execution Environment: " + IHGUtil.getEnvironmentType());
	 log("Execution Browser: " + TestConfig.getBrowserType());
    	log("URL: " +"http://wwww.google.com");
		

		log("step 2:LogIn");
		TestPage loginpage = new TestPage(driver, "http://www.google.com");
		Thread.sleep(100000);
		//MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppointmentRequest() throws Exception {
		
		log("Test Case: Appointment Request");
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("step 1: Get Data from Excel");
		Appointment aptData = new Appointment();
		AppointmentTestData testData = new AppointmentTestData(aptData);
		Long timestamp = System.currentTimeMillis();
		
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

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.isLoginPageLoaded(), "There was an error loading the login page");
		MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("step 3: Click on Appointment Button on My Patient Page");
		AppointmentRequestStep1Page apptRequestStep1 = myPatientPage.clickAppointmentRequestTab();

		log("step 4: Complete Appointment Request Step1 Page  ");
		AppointmentRequestStep2Page apptRequestStep2 = apptRequestStep1.requestAppointment(null,null,testData.getPreferredDoctor(),null);

		log("step 5: Complete Appointment Request Step2 Page  ");
		AppointmentRequestStep3Page apptRequestStep3 = apptRequestStep2.fillInForm(PortalConstants.PreferredTimeFrame,
				PortalConstants.PreferredDay, PortalConstants.ChoosePreferredTime, PortalConstants.ApptReason,
				PortalConstants.WhichIsMoreImportant, testData.getPhoneNumber());
		
		log("Getting Appointment reason ");
		long time=apptRequestStep2.getCreatedTs();
		String reason=PortalConstants.ApptReason.toString()+" "+String.valueOf(time);
	    
		log("step 6: Complete Appointment Request Step3 Page  ");
		AppointmentRequestStep4Page apptRequestStep4 = apptRequestStep3.clickSubmit();

		log("step 7: Complete Appointment Request Step4 Page  ");
		myPatientPage = apptRequestStep4.clickBackToMyPatientPage();
			
		log("step 8: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		//OAuthPropertyManager.init(testData.getOAuthProperty());
		
		log("step 9: Get Appointment Rest call");
		
		//get only messages from last day in epoch time to avoid transferring lot of data
		Long since = timestamp / 1000L - 60 * 60 * 24;
		
		log("Getting messages since timestamp: " + since);
		
		//do the call and save xml, ",0" is there because of the since attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		
		log("step 10: Checking validity of the response xml");
		RestUtils.isReasonResponseXMLValid(testData.getResponsePath(), reason);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCAskQuestion() throws Exception {
		
		log("Test Case: AMDC Ask Question");
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("step 1: Get Data from Excel");
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


		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.isLoginPageLoaded(), "There was an error loading the login page");
		MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("step 3: Click Ask A Staff");
		AskAStaffStep1Page askStaff1 = myPatientPage.clickAskAStaffLink();

		log("step 4: Complete step 1 of Ask A Staff");
		AskAStaffStep2Page askStaff2 = askStaff1.askQuestion("Test", "This is generated from the AMDCAskQuestion automation test case.");

		log("step 5: Complete step 2 of Ask A Staff");
		AskAStaffStep3Page askStaff3 = askStaff2.submitUnpaidQuestion();

		log("step 6: Validate entry is on Ask A Staff History page");
		AskAStaffHistoryPage aasHistory = askStaff3.clickAskAStaffHistory();
		verifyTrue(aasHistory.isAskAStaffOnHistoryPage(Long.toString(askStaff1.getCreatedTimeStamp())), "Expected to see a subject containing "
						+ askStaff1.getCreatedTimeStamp() + " on the Ask A Staff History page. None were found.");

		log("step 7: Logout of Patient Portal");
		myPatientPage.logout(driver);
		
		
		log("step 8: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		//OAuthPropertyManager.init(testData.getOAuthProperty());
		
		log("step 9: Get AMDC Rest call");
		//get only messages from last day in epoch time to avoid transferring lot of data
		Long since = askStaff1.getCreatedTimeStamp() /1000L - 60*60*24;
		
		log("Getting messages since timestamp: " + since);
		
		//do the call and save xml, ",0" is there because of the since attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		
		log("step 10: Checking validity of the response xml");
		RestUtils.isQuestionResponseXMLValid(testData.getResponsePath(), askStaff1.getCreatedTimeStamp());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCSecureMessage() throws Exception{
		log("Test Case: AMDC Secure Message");
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("step 1: Get Data from Excel");
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
		
		log("step 2: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		log("step 3: Fill Message data");
		long timestamp = System.currentTimeMillis();
		String message = RestUtils.prepareSecureMessage(testData.getSecureMessagePath(), testData.getFrom(), testData.getUserName(), "Test " + timestamp);
		
		log("step 4: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), message, testData.getResponsePath());

		log("step 5: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(10000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		verifyTrue(completed, "Message processing was not completed in time");
		
		log("step 6: Login to Patient Portal");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
		MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("step 7: Go to Inbox");
		ConsolidatedInboxPage inboxPage = myPatientPage.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 8: Find message in Inbox");
		String messageIdentifier = Long.toString(timestamp);
		ConsolidatedInboxMessage msg = inboxPage.openMessageInInbox(messageIdentifier);

		log("step 9: Validate message loads and is the right message");
		String actualSubject = msg.getPracticeReplyMessageTitle();
		assertTrue(msg.getPracticeReplyMessageTitle().contains(messageIdentifier), "Expected subject containting ["
						+ messageIdentifier + "but actual subject was [" + actualSubject + "]");
		
		log("step 10: Reply to the message");
		msg.replyToMessage(IntegrationConstants.MESSAGE_REPLY);
		
		log("step 11: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);
		
		log("step 12: Do a GET and get the message");
		// get only messages from last day in epoch time to avoid transferring lot of data
		Long since = timestamp / 1000L - 60 * 60 * 24;

		log("Getting messages since timestamp: " + since);

		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		
		log("step 13: Validate message reply");
		RestUtils.isReplyPresent(testData.getResponsePath(), messageIdentifier);
		
	}
	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientUpdate() throws Exception{
		log("Test Case: PIDC Patient Update");
		PIDCTestData testData = loadDataFromExcel();
		 
		Long timestamp = System.currentTimeMillis();
		log("step 2:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testData.getUrl());
		MyPatientPage pMyPatientPage = loginpage.login(testData.getUserName(), testData.getPassword());

		log("step 3:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();
		
		log("step 4:Create random  addresses to update");
		Random random = new Random();
		String firstLine = "Street " + random.nextInt(1000);
		String secondLine = "Street " + random.nextInt(1000);
		
		log("Address 1: " + firstLine + "\nAddress 2: " + secondLine);
		
		log("step 5:Modify address line 1 and 2 on MyAccountPage");
		pMyAccountPage.modifyAndSubmitAddressLines(firstLine, secondLine);
		
		log("step 6:Logout from Patient portal");
		pMyAccountPage.logout(driver);
		
		log("step 7: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		log("step 8:Do a GET on PIDC");
		//this step assumes that the updated patient is the patient from first ten registered patients, so we can save traffic
		//if max argument is ommited patient should be in first 100 patients
		Long since = timestamp / 1000L - 60 * 60 * 24;

		log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		
		log("step 9:Check changes of address lines");
		RestUtils.isPatientUpdated(testData.getResponsePath(), testData.getUserName() , firstLine, secondLine);
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientRegistration() throws Exception{
		log("Test Case: PIDC Patient Registration");
		PIDCTestData testData = loadDataFromExcel();
		
		log("step 2: Prepare patient to post");
		Long timestamp = System.currentTimeMillis();
		String practicePatientId = "Patient" +timestamp;
		String firstName = "Name" + timestamp;
		String lastName = "Surname" + timestamp;
		String email = IHGUtil.createRandomEmailAddress(testData.getEmail());
		log("Created Email address: " + email);
		String patient = RestUtils.preparePatient(testData.getPatientPath(), practicePatientId, firstName, lastName, email);
		
		log("step 3: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		log("step 3: Do a POST call and get processing status URL");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), patient, testData.getResponsePath());
		
		log("step 4: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 7; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(30000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");
		
		GmailBot gBot = new GmailBot();
		log("step 5: Checking for the activation link inside the patient Gmail inbox");

		// Searching for the link for patient activation in the Gmail Inbox
		 String activationUrl = gBot.findInboxEmailLink(testData.getGmailUsername(), testData.getGmailPassword(),
				PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLink, 2, false, true);

		log("step 6: Moving to the link obtained from the email message");
		// Moving to the Link from email
		//driver.get(activationUrl);
		CreateAccountPage pCreateAccountPage = new PortalLoginPage(driver).loadUnlockLink(activationUrl);

		log("Step 7: Filling in user credentials and finishing the registration");
		// Filing the User credentials
		MyPatientPage myPatientPage = pCreateAccountPage.fillEmailActivaion("",
				testData.getBirthDay(), testData.getZipCode(), testData.getSSN(),
				email, testData.getPatientPassword(), testData.getSecretQuestion(),
				testData.getSecretAnswer());

		log("Step 8: Signing out of the Patient Portal");
		myPatientPage.clickLogout(driver);
		
		log("Step 9: Do a GET on PIDC Url to get registered patient");
		// get only patients from last day in epoch time to avoid transferring lot of data
		Long since = timestamp / 1000L - 60 * 60 * 24;

		log("Getting patients since timestamp: " + since);
		
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		
		log("Step 10: Find the patient and check if he is registered");
		RestUtils.isPatientRegistered(testData.getResponsePath(), practicePatientId);
	}
	
	private PIDCTestData loadDataFromExcel() throws Exception{
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
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
		
		log("step 1: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		String ccd = RestUtils.prepareCCD(testData.getCCDPath());
		
		RestUtils.setupHttpPostRequest(testData.getRestUrl(), ccd, testData.getResponsePath());
		
		log("step 2:LogIn to Patient Portal ");
		PortalLoginPage portalloginpage = new PortalLoginPage(driver,
				testData.getURL());
		MyPatientPage pMyPatientPage = portalloginpage.login(
				testData.getUserName(),
				testData.getPassword());
		
		log("step 3: Go to Inbox");
		ConsolidatedInboxPage inboxPage = pMyPatientPage.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 4: Find message in Inbox");
		ConsolidatedInboxMessage pConsolidatedInboxMessage = inboxPage
		.clickFirstMessageRow();

		log("step 5: Validate message subject and send date");
		Thread.sleep(1000);
		assertEquals(pConsolidatedInboxMessage.getMessageSubject(),
				IntegrationConstants.CCD_MESSAGE_SUBJECT,
		"### Assertion failed for Message subject");
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("step 6: Click on link ReviewHealthInformation");
		pConsolidatedInboxMessage.clickBtnReviewHealthInformation();

		
		log("step 7: Verify if CCD Viewer is loaded and click Close Viewer");
		pConsolidatedInboxMessage.verifyCCDViewerAndClose();
		
		driver.switchTo().defaultContent();
		
		log("step 8: Go to patient page");
		pMyPatientPage = pConsolidatedInboxMessage.clickMyPatientPage();
		
		log("step 9:Click PHR");
		pMyPatientPage.clickPHRWithoutInit(driver);
		PhrHomePage phrPage = PageFactory.initElements(driver, PhrHomePage.class);
		
		log("step 10:Go to PHR Inbox");
		PhrMessagesPage phrMessagesPage = phrPage.clickOnMyMessages();
		assertTrue(phrMessagesPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 11: Click first message");
		PhrInboxMessage phrInboxMessage = phrMessagesPage.clickOnFirstMessage();

		log("step 12: Validate message subject and send date");
		Thread.sleep(1000);
		assertEquals(phrInboxMessage.getPhrMessageSubject(),
				IntegrationConstants.CCD_MESSAGE_SUBJECT,
		"### Assertion failed for Message subject");
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("step 13: Click on link ReviewHealthInformation");
		phrInboxMessage.clickBtnReviewHealthInformationPhr();

		
		log("step 14:Verify if CCD Viewer is loaded and click Close Viewer");
		phrInboxMessage.verifyCCDViewerAndClosePhr();
		
		//driver.switchTo().defaultContent();
		
	}
}