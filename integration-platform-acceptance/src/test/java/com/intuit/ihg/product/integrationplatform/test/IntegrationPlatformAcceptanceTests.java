package com.intuit.ihg.product.integrationplatform.test;

import static org.testng.Assert.assertNotNull;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.AllScript;
import com.intuit.ihg.product.integrationplatform.utils.AllScriptTestData;
import com.intuit.ihg.product.integrationplatform.utils.Appointment;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentTestData;
import com.intuit.ihg.product.integrationplatform.utils.EHDC;
import com.intuit.ihg.product.integrationplatform.utils.EHDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.FormExport;
import com.intuit.ihg.product.integrationplatform.utils.FormExportTestData;
import com.intuit.ihg.product.integrationplatform.utils.GE;
import com.intuit.ihg.product.integrationplatform.utils.GETestData;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PIDC;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.PayNow;
import com.intuit.ihg.product.integrationplatform.utils.PayNowTestData;
import com.intuit.ihg.product.integrationplatform.utils.Payment;
import com.intuit.ihg.product.integrationplatform.utils.PaymentTestData;
import com.intuit.ihg.product.integrationplatform.utils.Prescription;
import com.intuit.ihg.product.integrationplatform.utils.PrescriptionTestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.integrationplatform.utils.StatementPreference;
import com.intuit.ihg.product.integrationplatform.utils.StatementPreferenceTestData;
import com.intuit.ihg.product.object.maps.integrationplatform.page.TestPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrDocumentsPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrHomePage;
import com.intuit.ihg.product.object.maps.phr.page.messages.PhrInboxMessage;
import com.intuit.ihg.product.object.maps.phr.page.messages.PhrMessagesPage;
import com.intuit.ihg.product.object.maps.smintegration.page.BetaCreateNewPatientPage;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.FormWelcomePage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormBasicInfoPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormCurrentSymptomsPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormEmergencyContactPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormMedicationsPage;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.NoLoginPaymentPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.createAccount.CreateAccountPage;
import com.medfusion.product.object.maps.patientportal1.page.inbox.MessageCenterInboxPage;
import com.medfusion.product.object.maps.patientportal1.page.inbox.MessagePage;
import com.medfusion.product.object.maps.patientportal1.page.makePaymentpage.MakePaymentPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.MyAccountPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.preferences.PreferencesPage;
import com.medfusion.product.object.maps.patientportal1.page.newRxRenewalpage.NewRxRenewalPage;
import com.medfusion.product.object.maps.patientportal1.page.solutions.apptRequest.AppointmentRequestStep1Page;
import com.medfusion.product.object.maps.patientportal1.page.solutions.apptRequest.AppointmentRequestStep2Page;
import com.medfusion.product.object.maps.patientportal1.page.solutions.apptRequest.AppointmentRequestStep3Page;
import com.medfusion.product.object.maps.patientportal1.page.solutions.apptRequest.AppointmentRequestStep4Page;
import com.medfusion.product.object.maps.patientportal1.page.solutions.askstaff.AskAStaffHistoryPage;
import com.medfusion.product.object.maps.patientportal1.page.solutions.askstaff.AskAStaffStep1Page;
import com.medfusion.product.object.maps.patientportal1.page.solutions.askstaff.AskAStaffStep2Page;
import com.medfusion.product.object.maps.patientportal1.page.solutions.askstaff.AskAStaffStep3Page;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.medfusion.product.object.maps.practice.page.apptrequest.ApptRequestSearchPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.rxrenewal.RxRenewalSearchPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPageChargeHistory;
import com.medfusion.product.patientportal1.utils.PortalConstants;
import com.medfusion.product.patientportal1.utils.PortalUtil;

/**
 * @author bkrishnankutty
 * @Date 5/Aug/2013
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformAcceptanceTests extends BaseTestNGWebDriver {

	/**
	 * @Author:-bkrishnankutty
	 * @Date:-5/Aug/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: List Steps here ======================================== =====================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	// @Test (enabled = true, groups = {"AcceptanceTests"},
	// retryAnalyzer=RetryAnalyzer.class)
	public void testSiteGenLoginLogout() throws Exception {

		log("+++++++++++++ Test run+++++++++++");

		log("Test Case: testAddandRemovePreferences");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("URL: " + "http://wwww.google.com");

		log("Step 2:LogIn");
		new TestPage(driver, "http://www.google.com");
		Thread.sleep(100000);
		// MyPatientPage pMyPatientPage =
		// loginpage.login(testcasesData.getUsername(),
		// testcasesData.getPassword());

	}

	/*
	 * ////@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class) public void testGetAppointmentRequest() throws Exception {
	 * 
	 * log("Test Case: Appointment Request");
	 * 
	 * log("Execution Environment: " + IHGUtil.getEnvironmentType()); log("Execution Browser: " + TestConfig.getBrowserType());
	 * 
	 * log("Step 1: Get Data from Excel"); Appointment aptData = new Appointment(); AppointmentTestData testData = new AppointmentTestData(aptData); Long
	 * timestamp = System.currentTimeMillis();
	 * 
	 * log("Url: " + testData.getUrl()); log("User Name: " + testData.getUserName()); log("Password: " + testData.getPassword()); log("Rest Url: " +
	 * testData.getRestUrl()); log("Response Path: " + testData.getResponsePath()); log("From: " + testData.getFrom()); log("SecureMessagePath: " +
	 * testData.getSecureMessagePath()); log("OAuthProperty: " + testData.getOAuthProperty()); log("OAuthKeyStore: " + testData.getOAuthKeyStore());
	 * log("OAuthAppToken: " + testData.getOAuthAppToken()); log("OAuthUsername: " + testData.getOAuthUsername()); log("OAuthPassword: " +
	 * testData.getOAuthPassword());
	 * 
	 * log("Step 2: LogIn"); PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl()); assertTrue(loginPage.isLoginPageLoaded(),
	 * "There was an error loading the login page"); MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());
	 * 
	 * log("Step 3: Click on Appointment Button on My Patient Page"); AppointmentRequestStep1Page apptRequestStep1 = myPatientPage.clickAppointmentRequestTab();
	 * 
	 * log("Step 4: Complete Appointment Request Step1 Page  "); AppointmentRequestStep2Page apptRequestStep2 = apptRequestStep1.requestAppointment
	 * (null,null,testData.getPreferredDoctor(),null);
	 * 
	 * log("Step 5: Complete Appointment Request Step2 Page  "); AppointmentRequestStep3Page apptRequestStep3 =
	 * apptRequestStep2.fillInForm(PortalConstants.PreferredTimeFrame, PortalConstants.PreferredDay, PortalConstants.ChoosePreferredTime,
	 * PortalConstants.ApptReason, PortalConstants.WhichIsMoreImportant, testData.getPhoneNumber());
	 * 
	 * log("Getting Appointment reason "); long time=apptRequestStep2.getCreatedTs(); String
	 * reason=PortalConstants.ApptReason.toString()+" "+String.valueOf(time);
	 * 
	 * log("Step 6: Complete Appointment Request Step3 Page  "); AppointmentRequestStep4Page apptRequestStep4 = apptRequestStep3.clickSubmit();
	 * 
	 * log("Step 7: Complete Appointment Request Step4 Page  "); myPatientPage = apptRequestStep4.clickBackToMyPatientPage();
	 * 
	 * log("Step 8: Setup Oauth client");
	 * 
	 * //OAuthPropertyManager.init(testData.getOAuthProperty());
	 * 
	 * log("Step 9: Get Appointment Rest call");
	 * 
	 * //get only messages from last day in epoch time to avoid transferring lot of data Long since = timestamp / 1000L - 60 * 60 * 24;
	 * 
	 * log("Getting messages since timestamp: " + since);
	 * 
	 * //do the call and save xml, ",0" is there because of the since attribute format RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since +
	 * ",0", testData.getResponsePath());
	 * 
	 * log("Step 10: Checking validity of the response xml"); RestUtils.isReasonResponseXMLValid(testData.getResponsePath(), reason); }
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAppointmentRequest() throws Exception {

		log("Test Case: Appointment Request");

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

		log("Step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.isLoginPageLoaded(), "There was an error loading the login page");
		MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click on Appointment Button on My Patient Page");
		AppointmentRequestStep1Page apptRequestStep1 = myPatientPage.clickAppointmentRequestTab();

		log("Step 4: Complete Appointment Request Step1 Page  ");
		AppointmentRequestStep2Page apptRequestStep2 = apptRequestStep1.requestAppointment(null, null, testData.getPreferredDoctor(), null);

		log("Step 5: Complete Appointment Request Step2 Page  ");
		AppointmentRequestStep3Page apptRequestStep3 = apptRequestStep2.fillInForm(PortalConstants.PreferredTimeFrame, PortalConstants.PreferredDay,
				PortalConstants.ChoosePreferredTime, PortalConstants.ApptReason, PortalConstants.WhichIsMoreImportant, testData.getPhoneNumber());

		log("Getting Appointment reason ");
		long time = apptRequestStep2.getCreatedTs();
		String reason = PortalConstants.ApptReason.toString() + " " + String.valueOf(time);
		String arSMSubject = IntegrationConstants.AR_SM_SUBJECT.toString() + "" + String.valueOf(time);
		String arSMBody = IntegrationConstants.AR_SM_BODY.toString() + "" + String.valueOf(time);
		log("************Appointment Reason: " + reason);
		log("************Appointment Secure Message Subject: " + arSMSubject);
		log("************Appointment Secure Message Body: " + arSMBody);

		log("Step 6: Complete Appointment Request Step3 Page  ");
		AppointmentRequestStep4Page apptRequestStep4 = apptRequestStep3.clickSubmit();

		log("Step 7: Complete Appointment Request Step4 Page  ");
		myPatientPage = apptRequestStep4.clickBackToMyPatientPage();

		log("Step 8: Logout of Patient Portal");
		myPatientPage.logout(driver);

		log("Step 9: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		log("Step 10: Get Appointment Rest call");

		// get only messages from last hour in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;

		log("Getting messages since timestamp: " + since);

		// do the call and save xml, ",0" is there because of the since
		// attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 11: Checking reason in the response xml");
		RestUtils.isReasonResponseXMLValid(testData.getResponsePath(), reason);

		String postXML =
				RestUtils.findValueOfChildNode(testData.getResponsePath(), "AppointmentRequest", reason, arSMSubject, arSMBody, testData.getAppointmentPath());

		// httpPostRequest method
		log("Step 12: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), postXML, testData.getResponsePath());

		log("Step 13: Get processing status until it is completed");
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

		log("Step 14: Check secure message in patient gmail inbox");
		String emailMessageLink =
				RestUtils.verifyEmailNotification(testData.getGmailUserName(), testData.getGmailPassword(), testData.getPracticeName(), 3, "Portal 1.0");

		// patient Portal validation
		log("Step 15: Login to Patient Portal");
		PortalLoginPage ploginPage = new PortalLoginPage(driver, emailMessageLink);
		MessageCenterInboxPage inboxPage = ploginPage.loginNavigateToInboxPage(testData.getUserName(), testData.getPassword());

		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("Step 16: Find message in Inbox");
		MessagePage msg = inboxPage.openMessageInInbox(arSMSubject);

		log("Step 17: Validate message loads and is the right message");
		assertTrue(msg.isSubjectLocated(arSMSubject));

		log("Step 18: Logout of Patient Portal");
		myPatientPage.logout(driver);

		// Practice portal validation
		log("Step 19: Login to Practice Portal");

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(), testData.getPracticePassword());

		log("Step 20: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestSearchPage.PAGE_NAME);

		log("Step 21: Search for appt requests");
		apptSearch.searchForApptRequests(2, null, null);
		Thread.sleep(240000);
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(reason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestDetailStep1Page.PAGE_NAME);

		String actualSMSubject = detailStep1.getPracticeMessageSubject();
		assertTrue(detailStep1.getPracticeMessageSubject().contains(arSMSubject),
				"Expected Secure Message Subject containing [" + arSMSubject + "but actual message subject was [" + actualSMSubject + "]");

		String actualSMBody = detailStep1.getPracticeMessageBody();
		assertTrue(detailStep1.getPracticeMessageBody().contains(arSMBody),
				"Expected Secure Message Body containing [" + arSMBody + "but actual message body was [" + actualSMBody + "]");

		log("Step 22: Logout of Practice Portal");
		practiceHome.logOut();

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCAskQuestion() throws Exception {

		log("Test Case: AMDC Ask Question");

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

		log("Step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.isLoginPageLoaded(), "There was an error loading the login page");
		MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click Ask A Staff");
		AskAStaffStep1Page askStaff1 = myPatientPage.clickAskAStaffLink();

		log("Step 4: Complete Step 1 of Ask A Staff");
		AskAStaffStep2Page askStaff2 = askStaff1.askQuestion("Test", "This is generated from the AMDCAskQuestion automation test case.");

		log("Step 5: Complete Step 2 of Ask A Staff");
		AskAStaffStep3Page askStaff3 = askStaff2.submitUnpaidQuestion();

		log("Step 6: Validate entry is on Ask A Staff History page");
		AskAStaffHistoryPage aasHistory = askStaff3.clickAskAStaffHistory();
		verifyTrue(aasHistory.isAskAStaffOnHistoryPage(Long.toString(askStaff1.getCreatedTimeStamp())),
				"Expected to see a subject containing " + askStaff1.getCreatedTimeStamp() + " on the Ask A Staff History page. None were found.");

		log("Step 7: Logout of Patient Portal");
		myPatientPage.logout(driver);

		log("Step 8: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		// OAuthPropertyManager.init(testData.getOAuthProperty());

		log("Step 9: Get AMDC Rest call");
		// get only messages from last day in epoch time to avoid transferring
		// lot of data
		Long since = askStaff1.getCreatedTimeStamp() / 1000L - 60 * 60 * 24;

		log("Getting messages since timestamp: " + since);

		// do the call and save xml, ",0" is there because of the since
		// attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 10: Checking validity of the response xml");
		RestUtils.isQuestionResponseXMLValid(testData.getResponsePath(), askStaff1.getCreatedTimeStamp());
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
			Thread.sleep(120000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 6: Check secure message in patient gmail inbox");
		String emailMessageLink =
				RestUtils.verifyEmailNotification(testData.getGmailUserName(), testData.getGmailPassword(), testData.getSender3(), 3, "Portal 1.0");

		log("Step 7: Login to Patient Portal");
		PortalLoginPage loginPage = new PortalLoginPage(driver, emailMessageLink);
		MessageCenterInboxPage inboxPage = loginPage.loginNavigateToInboxPage(testData.getUserName(), testData.getPassword());

		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("Step 8: Find message in Inbox");
		String messageIdentifier = Long.toString(timestamp);

		log("Step 9: Log the message read time ");
		long epoch = System.currentTimeMillis() / 1000;

		String readdatetimestamp = RestUtils.readTime(epoch);
		log("Message Read Time:" + readdatetimestamp);

		MessagePage msg = inboxPage.openMessageInInbox(messageIdentifier);

		log("Step 10: Validate message loads and is the right message");
		assertTrue(msg.isSubjectLocated(messageIdentifier));

		log("Step 11: Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		log("Step 12: Wait 120 seconds, so the message can be processed");
		Thread.sleep(120000);

		log("Getting messages since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getReadCommunicationURL() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 13: Validate the message id and read time in response");
		RestUtils.isReadCommunicationMessage(testData.getResponsePath(), messageID, readdatetimestamp);

		log("Step 14: Reply to the message");
		msg.replyToMessage(IntegrationConstants.MESSAGE_REPLY, null);

		log("Step 15: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		log("Step 16: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 17: Validate message reply");
		RestUtils.isReplyPresent(testData.getResponsePath(), messageIdentifier);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientUpdate() throws Exception {
		log("Test Case: PIDC Patient Update");
		PIDCTestData testData = loadDataFromExcel();

		Long timestamp = System.currentTimeMillis();
		log("Step 2: LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testData.getUrl());
		MyPatientPage pMyPatientPage = loginpage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

		log("Step 4: Create random  addresses to update");
		Random random = new Random();
		String firstLine = "Street " + random.nextInt(1000);
		String secondLine = "Street " + random.nextInt(1000);

		log("Address 1: " + firstLine + "\nAddress 2: " + secondLine);

		log("Step 5: Modify address line 1 and 2 on MyAccountPage");
		pMyAccountPage.modifyAndSubmitAddressLines(firstLine, secondLine);

		log("Step 6: Logout from Patient portal");
		pMyAccountPage.logout(driver);

		log("Step 7: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		log("Step 8: Wait 60 seconds, so that patient-outbound can be processed");
		Thread.sleep(60000);

		log("Step 9: Do a GET on PIDC");
		// this Step assumes that the updated patient is the patient from first
		// ten registered patients, so we can save traffic
		// if max argument is ommited patient should be in first 100 patients
		Long since = timestamp / 1000L - 60 * 24;

		log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 10: Check changes of address lines");
		RestUtils.isPatientUpdated(testData.getResponsePath(), testData.getUserName(), firstLine, secondLine);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientRegistration() throws Exception {
		log("Test Case: PIDC Patient Registration");
		PIDCTestData testData = loadDataFromExcel();

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
				PortalConstants.NewPatientActivationMessageLink, 3, false, true);

		log("Step 6: Moving to the link obtained from the email message");
		// Moving to the Link from email
		driver.get(activationUrl);
		/*
		 * log("Get Activation Link from Practice Portal"); log("Step 5: Login to Practice Portal"); PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
		 * testData.getPracticeURL()); PracticeHomePage pPracticeHomePage = practiceLogin .login(testData.getPracticeUserName(),testData.getPracticePassword ());
		 * 
		 * log("Step 6: Click on Patient Search Link"); PatientSearchPage pPatientSearchPage= pPracticeHomePage.clickPatientSearchLink();
		 * 
		 * log("Step 7: Set Patient Search Fields"); pPatientSearchPage.searchAllPatientInPatientSearch (firstName,lastName,0);
		 * 
		 * log("Step 8: Verify the Search Result"); IHGUtil.waitForElement(driver,30,pPatientSearchPage.searchResult); verifyEquals
		 * (true,pPatientSearchPage.searchResult.getText().contains(firstName));
		 * 
		 * log("Step 9: Click on Patient"); PatientDashboardPage patientPage=pPatientSearchPage.clickOnPatient(firstName, lastName);
		 * 
		 * String activationUrl = patientPage.unlockLink(); log("Activation URL: "+activationUrl);
		 * 
		 * log("Step 10: Logout of Practice Portal"); pPracticeHomePage.logOut();
		 */
		CreateAccountPage pCreateAccountPage = new PortalLoginPage(driver).loadUnlockLink(activationUrl);

		log("Step 7: Filling in user credentials and finishing the registration");
		// Filing the User credentials
		MyPatientPage myPatientPage =
				pCreateAccountPage.fillPatientActivaion(zip, email, testData.getPatientPassword(), testData.getSecretQuestion(), testData.getSecretAnswer());

		log("Step 8: Assert Webelements in MyPatientPage");
		assertTrue(myPatientPage.isViewallmessagesButtonPresent(driver));

		log("Step 9: Signing out of the Patient Portal");
		myPatientPage.clickLogout(driver);

		log("Step 10: Do a GET on PIDC Url to get registered patient");
		// get only patients from last day in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;

		log("Wait 60 seconds so the message can be processed");
		Thread.sleep(60000);

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
		/*
		 * boolean completed = false; for (int i = 0; i < 3; i++) { // wait 10 seconds so the message can be processed Thread.sleep(180000);
		 * RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath()); if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
		 * completed = true; break; } } verifyTrue(completed, "Message processing was not completed in time");
		 */
		log("Step 4:LogIn to Patient Portal ");
		PortalLoginPage portalloginpage = new PortalLoginPage(driver, testData.getURL());
		MyPatientPage pMyPatientPage = portalloginpage.login(testData.getUserName(), testData.getPassword());

		log("Step 5: Go to Inbox");
		MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("Step 6: Find message in Inbox");
		MessagePage pMessage = inboxPage.clickFirstMessageRow();

		log("Step 7: Validate message subject and send date");
		Thread.sleep(10000);
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(pMessage.isSubjectLocated(IntegrationConstants.CCD_MESSAGE_SUBJECT));
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming(), 10000));
		log("CCD sent date & time is :" + pMessage.returnMessageSentDate());
		// assertTrue(RestUtils.verifyCCDMessageDate(pMessage.returnMessageSentDate(),timestamp));

		log("Step 8: Click on link View health data");
		pMessage.clickBtnReviewHealthInformation();

		log("Step 9: Verify if CCD Viewer is loaded and click Close Viewer");
		pMessage.verifyCCDViewerAndClose();

		driver.switchTo().defaultContent();

		log("Step 10: Go to patient page");
		Thread.sleep(2000);
		pMyPatientPage = pMessage.clickMyPatientPage();

		log("Step 11: Click PHR");
		pMyPatientPage.clickPHRWithoutInit(driver);
		PhrHomePage phrPage = PageFactory.initElements(driver, PhrHomePage.class);

		log("Step 12: Go to PHR Inbox");
		PhrMessagesPage phrMessagesPage = phrPage.clickOnMyMessages();
		// assertTrue(phrMessagesPage.isInboxLoaded(),
		// "Inbox failed to load properly.");

		log("Step 13: Click first message");
		PhrInboxMessage phrInboxMessage = phrMessagesPage.clickOnFirstMessage();

		log("Step 14: Validate message subject and send date");
		Thread.sleep(1000);
		assertEquals(phrInboxMessage.getPhrMessageSubject(), IntegrationConstants.CCD_MESSAGE_SUBJECT, "### Assertion failed for Message subject");
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("Step 15: Click on link ReviewHealthInformation");
		PhrDocumentsPage phrDocuments = phrInboxMessage.clickBtnReviewHealthInformationPhr();

		log("step 16:Click on View health data");
		phrDocuments.clickViewHealthInformation();

		log("step 17:click Close Viewer");
		phrDocuments.closeViewer();

		log("step 18:Click Logout");
		Thread.sleep(3000);
		phrDocuments.clickLogout();
		
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ERxPrescription() throws Exception {
		log("Test Case: Rx Prescription Request");

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		Prescription prescription = new Prescription();
		PrescriptionTestData testData = new PrescriptionTestData(prescription);
		Long timestamp = System.currentTimeMillis();

		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("Response Path: " + testData.getResponsePath());
		log("From: " + testData.getFrom());
		log("PrescriptionPath: " + testData.getPrescriptionPath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());

		log("Step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
		MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Verify for My Patient Page ");
		PortalUtil.setPortalFrame(driver);
		verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

		log("Step 4: Click on PrescriptionRenewal Link ");
		NewRxRenewalPage newRxRenewalPage = myPatientPage.clickPrescriptionRenewal();

		log("Step 5: Set Medication Fields in RxRenewal Page");
		newRxRenewalPage.setMedicationDetails();

		log("Step 6: Set Pharmacy Fields in RxRenewal Page");
		newRxRenewalPage.setPharmacyFields();

		log("Getting Medication Name ");
		long time = newRxRenewalPage.getCreatedTs();
		String medicationName = PortalConstants.MedicationName.toString() + String.valueOf(time);
		log("Medication Name :" + medicationName);

		String rxSMSubject = PortalConstants.RxRenewal_Subject_Tag.toString() + String.valueOf(time);
		log("Perscription Subject :" + rxSMSubject);

		String rxSMBody = IntegrationConstants.QUESTION_MESSAGE.toString() + "" + String.valueOf(time);
		log("Perscription Reply :" + rxSMBody);

		log("Step 7: Verify RxRenewal Confirmation Message");
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 5, newRxRenewalPage.renewalConfirmationmessage);
		verifyEquals(newRxRenewalPage.renewalConfirmationmessage.getText(), PortalConstants.RenewalConfirmation);

		log("Step 8: Logout of Patient Portal");
		myPatientPage.logout(driver);

		log("Step 9: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		// OAuthPropertyManager.init(testData.getOAuthProperty());

		log("Step 10: Get Prescription Rest call");
		// get only messages from last hour in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;

		log("Getting messages since timestamp :" + since);

		// do the call and save xml, ",0" is there because of the since
		// attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 11: Checking validity of the response xml");
		RestUtils.isMedicationDetailsResponseXMLValid(testData.getResponsePath(), medicationName);

		String postXML =
				RestUtils.findValueOfMedicationNode(testData.getResponsePath(), "Medication", medicationName, rxSMSubject, rxSMBody, testData.getPrescriptionPath());

		String SigCodeAbbreviation = RestUtils.SigCodeAbbreviation;
		String SigCodeMeaning = RestUtils.SigCodeMeaning;

		String sigCodes = SigCodeAbbreviation + " - " + SigCodeMeaning;

		log("SigCodeAbbreviation :" + SigCodeAbbreviation);
		log("SigCodeMeaning :" + SigCodeMeaning);

		log("Step 12: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), postXML, testData.getResponsePath());

		log("Step 13: Get processing status until it is completed");
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

		// Patient portal validation
		log("Step 13: Check secure message in patient gmail inbox");
		String emailMessageLink =
				RestUtils.verifyEmailNotification(testData.getGmailUserName(), testData.getGmailPassword(), testData.getPracticeName(), 3, "Portal 1.0");

		// patient Portal validation
		log("Step 14: Login to Patient Portal");
		PortalLoginPage ploginPage = new PortalLoginPage(driver, emailMessageLink);
		MessageCenterInboxPage inboxPage = ploginPage.loginNavigateToInboxPage(testData.getUserName(), testData.getPassword());
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		// String rxSMSubject="Prescription Renewal Approved1411738963818";
		log("Step 15: Find message in Inbox");
		MessagePage msg = inboxPage.openMessageInInbox(rxSMSubject);

		log("Step 16: Validate message loads and is the right message");
		assertTrue(msg.isSubjectLocated(rxSMSubject));

		log("Step 17: Checking Instructions(SigCodeMeaning) in inbox");
		String Instructions = msg.readSigCode(SigCodeMeaning);
		verifyEquals(SigCodeMeaning, Instructions);

		log("Step 18: Logout of Patient Portal");
		myPatientPage.logout(driver);

		// Practice portal validation
		log("Step 19: Login to Practice Portal");

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(), testData.getPracticePassword());

		log("Step 20: Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome.clickonRxRenewal();

		log("Step 21: Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday(2);

		log("Step 22: Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		log("Step 23: Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.checkMedicationDetails(medicationName, sigCodes);

		log("Step 24: Logout of Practice Portal");
		practiceHome.logOut();

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGEAdaptersendCCD() throws Exception {

		log("Test Case:  Import CCD via GE Adapter5 and check in patient Portal");
		GE EHDCData = new GE();
		GETestData testData = new GETestData(EHDCData);

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

		/*
		 * log("Step 3: Get processing status until it is completed"); boolean completed = false; for (int i = 0; i < 3; i++) { // wait 10 seconds so the message
		 * can be processed Thread.sleep(180000); RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath()); if
		 * (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) { completed = true; break; } } verifyTrue(completed,
		 * "Message processing was not completed in time");
		 */

		log("Step 5: LogIn to Patient Portal ");
		PortalLoginPage portalloginpage = new PortalLoginPage(driver, testData.getURL());
		MyPatientPage pMyPatientPage = portalloginpage.login(testData.getUserName(), testData.getPassword());

		log("Step 6: Go to Inbox");
		MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("Step 7: Find message in Inbox");
		MessagePage pMessage = inboxPage.clickFirstMessageRow();

		log("Step 8: Validate message subject and send date");
		Thread.sleep(1000);
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(pMessage.isSubjectLocated(IntegrationConstants.CCD_MESSAGE_SUBJECT));
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming(), 10000));
		log("CCD sent date & time is :" + pMessage.returnMessageSentDate());
		// assertTrue(RestUtils.verifyCCDMessageDate(pMessage.returnMessageSentDate(),timestamp));

		log("Step 9: Click on link View health data");
		pMessage.clickBtnReviewHealthInformation();

		log("Step 10: Verify if CCD Viewer is loaded and click Close Viewer");
		pMessage.verifyCCDViewerAndClose();

		driver.switchTo().defaultContent();

		/*
		 * log("Step 10: Go to patient page"); pMyPatientPage = pMessage.clickMyPatientPage();
		 * 
		 * log("Step 11:Click PHR"); pMyPatientPage.clickPHRWithoutInit(driver); PhrHomePage phrPage = PageFactory.initElements(driver, PhrHomePage.class);
		 * 
		 * log("Step 12:Go to PHR Inbox"); PhrMessagesPage phrMessagesPage = phrPage.clickOnMyMessages(); // assertTrue(phrMessagesPage.isInboxLoaded(), //
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
		 * log("step 16:Click on View Health Information"); phrDocuments.clickViewHealthInformation();
		 * 
		 * log("step 17:click Close Viewer"); phrDocuments.closeViewer();
		 * 
		 * log("step 18:Click Logout"); phrDocuments.clickLogout();
		 */

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAllScriptImportCCD() throws Exception {

		log("Test Case:  Import CCD via All script Adapter and check in patient Portal");
		AllScript allScriptData = new AllScript();
		AllScriptTestData testData = new AllScriptTestData(allScriptData);

		log("UserName: " + testData.getUserName());
		log("Password:" + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("CCD Path: " + testData.getCCDPath());
		log("Response Path: " + testData.getResponsePath());
		log("Response Path: " + testData.getEmail());

		log("Step 1: AllScript CCD");
		String ccd = RestUtils.fileToString(testData.getCCDPath());

		log("Step 2: Do Message Post Request");
		RestUtils.setupHttpPostRequestExceptOauth(testData.getRestUrl(), ccd, testData.getResponsePath(), null);

		log("Step 3: verify transport status in response xml");
		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(120000);
			if (RestUtils.isCCDProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 4: LogIn to Patient Portal ");
		PortalLoginPage portalloginpage = new PortalLoginPage(driver, testData.getURL());
		MyPatientPage pMyPatientPage = portalloginpage.login(testData.getUserName(), testData.getPassword());

		log("Step 5: Go to Inbox");
		MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("Step 6: Find message in Inbox");
		MessagePage pMessage = inboxPage.clickFirstMessageRow();

		log("Step 7: Validate message subject and send date");
		Thread.sleep(1000);
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(pMessage.isSubjectLocated(IntegrationConstants.CCD_MESSAGE_SUBJECT));
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming(), 10000));
		log("CCD sent date & time is :" + pMessage.returnMessageSentDate());
		// assertTrue(RestUtils.verifyCCDMessageDate(pMessage.returnMessageSentDate(),timestamp));

		log("Step 8: Click on link View health data");
		pMessage.clickBtnReviewHealthInformation();

		log("Step 9: Click on PDF download Link");
		pMessage.clickOnPDF();

		log("Step 10: Click on Send Information link");
		String Email = testData.getEmail();
		pMessage.generateTransmitEvent(Email);
		Thread.sleep(2000);
		log("Step 11: Verify if CCD Viewer is loaded and click Close Viewer");
		pMessage.verifyCCDViewerAndClose();

		driver.switchTo().defaultContent();

		log("Step 12: Go to patient page");
		pMyPatientPage = pMessage.clickMyPatientPage();

		log("Step 13: Click PHR");
		pMyPatientPage.clickPHRWithoutInit(driver);
		PhrHomePage phrPage = PageFactory.initElements(driver, PhrHomePage.class);

		log("Step 14: Go to PHR Inbox");
		PhrMessagesPage phrMessagesPage = phrPage.clickOnMyMessages();
		// assertTrue(phrMessagesPage.isInboxLoaded(),
		// "Inbox failed to load properly.");

		log("Step 15:Click first message");
		PhrInboxMessage phrInboxMessage = phrMessagesPage.clickOnFirstMessage();

		log("Step 16: Validate message subject and send date");
		Thread.sleep(1000);
		assertEquals(phrInboxMessage.getPhrMessageSubject(), IntegrationConstants.CCD_MESSAGE_SUBJECT, "### Assertion failed for Message subject");
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("Step 17: Click on link ReviewHealthInformation");
		PhrDocumentsPage phrDocuments = phrInboxMessage.clickBtnReviewHealthInformationPhr();

		log("step 18:Click on View health data");
		phrDocuments.clickViewHealthInformation();

		log("step 19:click Close Viewer");
		phrDocuments.closeViewer();

		log("step 20:Click Logout");
		Thread.sleep(3000);
		phrDocuments.clickLogout();

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EFormsExport() throws Exception {
		log("Test Case: testE2EFormsExport");

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		FormExport formExport = new FormExport();
		FormExportTestData testData = new FormExportTestData(formExport);
		Long timestamp = System.currentTimeMillis();

		log("Url: " + testData.getUrl());
		log("Response Path: " + testData.getResponsePath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());

		for (int i = 1; i < 3; i++) {
			log("Step 2: Click Sign-UP");
			PortalLoginPage loginpage = new PortalLoginPage(driver, testData.getUrl());
			loginpage.signUp();
			String getURL = testData.getRestUrl();

			BetaCreateNewPatientPage createNewPatientPage = new BetaCreateNewPatientPage(driver);
			log("Step 3: Fill details in Create Account Page");
			// Setting the variables for user in other tests
			String email = PortalUtil.createRandomEmailAddress(testData.getEmail());
			log("Email as well as UserName:-" + email);
			MyPatientPage pMyPatientPage =
					createNewPatientPage.BetaSiteCreateAccountPage(testData.getFirstName(), testData.getLastName(), email, testData.getPhoneNumber(),
							testData.getDob_Month(), testData.getDob_Day(), testData.getDob_Year(), testData.getZip(), testData.getSSN(), testData.getAddress(),
							testData.getPassword(), testData.getSecretQuestion(), testData.getAnswer(), testData.getAddressState(), testData.getAddressCity());

			String firstName = createNewPatientPage.FName;
			String lastName = createNewPatientPage.LName;

			log("Step 4: Assert Webelements in MyPatientPage");
			assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

			log("Step 5: Logout");
			pMyPatientPage.clickLogout(driver);

			log("Step 6: Login to Practice Portal");
			PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
			PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getPracticeUsername(), testData.getPracticePassword());

			log("Step 7: Click on Patient Search Link");
			PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

			log("Step 8: Set Patient Search Fields");
			pPatientSearchPage.searchForPatientInPatientSearch(firstName, lastName);

			log("Step 9: Verify the Search Result");
			Thread.sleep(12000);
			IHGUtil.waitForElement(driver, 60, pPatientSearchPage.searchResult);
			verifyEquals(true, pPatientSearchPage.searchResult.getText().contains(firstName));

			log("Step 10: Click on Patient");
			PatientDashboardPage patientPage = pPatientSearchPage.clickOnPatient(firstName, lastName);

			Thread.sleep(12000);
			log("Step 11: Set External Patient ID");
			String externalPatientID = patientPage.setExternalPatientID();

			log("External Patient Id:-" + externalPatientID);

			String patientID = patientPage.patientID;
			log("Medfusion Patient ID:-" + patientID);

			log("Step 12: Logout of Practice Portal");
			pPracticeHomePage.logOut();

			log("Step 13: Login to Patient Portal for submitting 'Patient Registration' Discrete Form");
			PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
			MyPatientPage myPatientPage = loginPage.login(email, testData.getPassword());

			log("Step 14: Verify for My Patient Page ");
			PortalUtil.setPortalFrame(driver);
			verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

			log("Step 15: Click on Registration button ");
			FormWelcomePage pFormWelcomePage = pMyPatientPage.clickStartRegistrationButton(driver);

			log("Step 16: Click on Continue button ");
			FormBasicInfoPage pFormBasicInfoPage = pFormWelcomePage.clickSaveContinue(FormBasicInfoPage.class);

			log("Step 17: Set Basic Information Form Fields");
			FormEmergencyContactPage pFormEmergencyContactPage = pFormBasicInfoPage.setBasicInfoFromField();

			log("Step 18: Set Emergency Contact Form Fields");
			FormCurrentSymptomsPage pFormCurrentSymptomsPage = pFormEmergencyContactPage.fillEmergencyContactFormFields();

			log("Step 19: Set Current Symptoms Form Fields");
			pFormCurrentSymptomsPage.setNoSymptoms();
			FormMedicationsPage pFormMedicationsPage = pFormCurrentSymptomsPage.clickSaveContinue(FormMedicationsPage.class);

			log("Step 20: Set Medication Form Fields");
			pFormMedicationsPage.fillMedicationFormFields();

			log("Step 21: Wait 120 seconds, so the message can be processed");
			Thread.sleep(60000);

			log("Step 22: Setup Oauth client 2.O");
			RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
					testData.getOAuthPassword());

			Long since = timestamp / 1000L - 60 * 24;

			if (i == 1) {
				getURL = testData.getRestUrl() + "/" + externalPatientID;
			} else {
				log("Verify both the patient details in ccdExchangeBatch response ");
				getURL = testData.getRestUrl() + "Batch";
			}

			log("Step 23:  Getting forms (CCDs) since timestamp: " + since + " using ccdExchange API");
			RestUtils.setupHttpGetRequest(getURL + "?since=" + since + ",0", testData.getResponsePath());

			List<String> patientList = RestUtils.patientDatails;
			patientList.add(externalPatientID);
			patientList.add(patientID);
			patientList.add(firstName);

			for (int j = 0; j < i; j++) {
				log("Step 24: Validate PatientDemographics and CCD details in the response");
				RestUtils.isPatientAppeared(testData.getResponsePath(), patientList.get(0), patientList.get(1), patientList.get(2));

				if (i == 2) {
					patientList.remove(0);
					patientList.remove(0);
					patientList.remove(0);
				}

			}
			if(i==2) {
				Thread.sleep(10000);
				log("Step 25: Verify 204 response when no new forms is present");
				long timeStamp204 = System.currentTimeMillis();
				Long sinceTime = timeStamp204 / 1000;
				RestUtils.setupHttpGetRequestExceptOauth(getURL + "?since=" + sinceTime + ",0", testData.getResponsePath());
				Thread.sleep(800);
				log("Step 26: Invoke ccdExchangeBatch api when there are no forms");
				String ccdExchangeBatchURL = RestUtils.headerUrl;
				RestUtils.setupHttpGetRequestExceptOauth(ccdExchangeBatchURL, testData.getResponsePath());
			}
			log("Step 27: Logout");
			pMyPatientPage.clickLogout(driver);

		}
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testOnlineBillPayment() throws Exception {

		log("Test Case: testOnlineBillPayment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		Payment paymentData = new Payment();
		PaymentTestData testcasesData = new PaymentTestData(paymentData);
		Long timestamp = System.currentTimeMillis();
		String accountNumber = IHGUtil.createRandomNumericString();
		String amount = "100.00";
		String CCLastDig = "1111";
		String CCType = "Visa";

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("Step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.getUrl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUserName(), testcasesData.getPassword());

		log("Step 3: Verify for My Patient Page ");
		PortalUtil.setPortalFrame(driver);
		verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

		log("Step 4: Click on Make Payment Link ");
		MakePaymentPage makePaymentPage = myPatientPage.clickMakePaymentLnk();

		log("Step 5: Set Make Payments Fields ");
		makePaymentPage.setMakePaymentFields(accountNumber);

		log("Step 6: fetch confirmation number ");
		String confirmationNumber = makePaymentPage.readConfirmationNumber();

		log("Step 7: Logout of Patient Portal");
		myPatientPage.logout(driver);

		log("Step 8: Setup Oauth client 2.O");
		RestUtils.oauthSetup(testcasesData.getOAuthKeyStore(), testcasesData.getOAuthProperty(), testcasesData.getOAuthAppToken(), testcasesData.getOAuthUsername(),
				testcasesData.getOAuthPassword());

		log("Step 9: Getting messages since timestamp: " + timestamp);
		String lastTimestamp = RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "?since=" + timestamp, testcasesData.getResponsePath());

		log("Step 10: Verify payment details");
		RestUtils.isPaymentAppeared(testcasesData.getResponsePath(), accountNumber, amount, CCLastDig, CCType, IntegrationConstants.SUBMITTED, confirmationNumber);

		String messageThreadID = RestUtils.paymentID;
		log("Payment ID :" + messageThreadID);

		String reply_Subject = "Test" + IHGUtil.createRandomNumericString();
		String message = RestUtils.prepareSecureMessage(testcasesData.getcommunicationXML(), testcasesData.getFrom(), testcasesData.getUserName(), reply_Subject,
				messageThreadID);

		log("Step 11: Do Message Post AMDC Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getCommRestUrl(), message, testcasesData.getResponsePath());

		log("Step 12: Get processing status until it is completed");
		boolean completed = false;
		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}

		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 13: Check secure message in patient gmail inbox");
		String emailMessageLink =
				RestUtils.verifyEmailNotification(testcasesData.getGmailUserName(), testcasesData.getGmailPassword(), testcasesData.getPracticeName(), 3, "Portal 1.0");

		// patient Portal validation
		log("Step 14: Login to Patient Portal");
		PortalLoginPage ploginPage = new PortalLoginPage(driver, emailMessageLink);
		MessageCenterInboxPage inboxPage = ploginPage.loginNavigateToInboxPage(testcasesData.getUserName(), testcasesData.getPassword());
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		MessagePage msg = inboxPage.openMessageInInbox(reply_Subject);

		log("Step 15: Validate message loads and is the right message");
		assertTrue(msg.isSubjectLocated(reply_Subject));

		Thread.sleep(60000);
		log("Step 16: Reply to the message");
		msg.replyToMessage(IntegrationConstants.MESSAGE_REPLY, null);

		log("Step 17: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Long since = timestamp / 1000L - 60 * 24;

		log("Step 18: Do a GET AMDC and verify patient reply in Get AMDC response");
		RestUtils.setupHttpGetRequest(testcasesData.getCommRestUrl() + "?since=" + since + ",0", testcasesData.getResponsePath());

		log("Step 19: Validate message reply");
		RestUtils.isReplyPresent(testcasesData.getResponsePath(), reply_Subject);

		myPatientPage.logout(driver);

		String postPayload = RestUtils.preparePayment(testcasesData.getPaymentPath(), messageThreadID, null, IntegrationConstants.BILLPAYMENT);

		log("Step 20: Do a Post and get the message");
		processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestUrl(), postPayload, testcasesData.getResponsePath());

		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}
		verifyTrue(completed, "Message processing was not completed in time");

		log("Verify Payment status in Practice Portal");
		log("Step 21: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testcasesData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testcasesData.getPracticeUserName(), testcasesData.getPracticePassword());

		log("Step 22: Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();

		log("Step 23: Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		log("Search For Payment By Status ");
		onlineBillPaySearchPage.searchForBillStatus(2);

		log("Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage.searchForBillPayment(accountNumber);

		String Status = onlineBillPaySearchPage.getBillDetails();
		assertNotNull(Status, "The submitted Online Bill request was not found in the practice");

		log("Step 24: Logout of Practice Portal");
		practiceHome.logOut();

		log("Step 25: Verify Payment status in Get Response using the Timestamp received in response of Step 8");
		RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "?since=" + lastTimestamp, testcasesData.getResponsePath());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPayNow() throws Exception {

		log("Test Case: testPayNow - No login payment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		PayNow payNowData = new PayNow();
		PayNowTestData testcasesData = new PayNowTestData(payNowData);
		Long timestamp = System.currentTimeMillis();

		log("Step 1: Open no login payment page");
		NoLoginPaymentPage pNoLoginPaymentPage = new NoLoginPaymentPage(driver, testcasesData.getUrl());

		log("Step 2: Fill in payment info and submit");
		pNoLoginPaymentPage.FillNoLoginPaymentPage(testcasesData.getFirstName(), testcasesData.getLastName(), testcasesData.getZip(), testcasesData.getEmail());

		log("Step 3: Verify payment OK");
		assertTrue(driver.getPageSource().contains("Thank You for your payment"));

		log("Step 4: Verify account set to N/A");
		verifyTrue(driver.getPageSource().contains("Account N/A."));

		log("Step 5: Verify the prize format.");
		verifyTrue(driver.getPageSource().contains("$" + pNoLoginPaymentPage.GetAmountPrize() + ".00"));
		Thread.sleep(60000);

		log("Step 6: fetch confirmation number ");
		String confirmationNumber = pNoLoginPaymentPage.readConfirmationNumber();

		log("Step 7: Setup Oauth client 2.O");
		RestUtils.oauthSetup(testcasesData.getOAuthKeyStore(), testcasesData.getOAuthProperty(), testcasesData.getOAuthAppToken(), testcasesData.getOAuthUsername(),
				testcasesData.getOAuthPassword());

		log("Step 8: Getting messages since timestamp: " + timestamp);
		String lastTimestamp =
				RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "=payNowpayment" + "&since=" + timestamp, testcasesData.getResponsePath());

		log("Step 9: Verify payment details");
		RestUtils.verifyPayment(testcasesData.getResponsePath(), pNoLoginPaymentPage.GetAmountPrize() + ".00", IntegrationConstants.SUBMITTED,
				IntegrationConstants.PAYNOWPAYMENT, confirmationNumber);

		String paymentID = RestUtils.paymentID;
		log("Payment ID :" + paymentID);

		String postPayload =
				RestUtils.preparePayment(testcasesData.getPaymentPath(), paymentID, pNoLoginPaymentPage.GetAmountPrize() + ".00", IntegrationConstants.PAYNOWPAYMENT);

		log("Step 10: Do a Post and get the message");
		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestUrl() + "=payNowpayment", postPayload, testcasesData.getResponsePath());

		log("Step 11: Get processing status until it is completed");
		boolean completed = false;
		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}

		verifyTrue(completed, "Message processing was not completed in time");

		log("Verify Payment status in Practice Portal");
		log("Step 12: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testcasesData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testcasesData.getPracticeUserName(), testcasesData.getPracticePassword());

		log("Step 13: Click on Virtual Card Swiper Tab ");
		VirtualCardSwiperPage vcsPage = practiceHome.clickVirtualCardSwiperTab();

		log("Step 14: Click on Charge History Link ");
		VirtualCardSwiperPageChargeHistory vcsPageChargeHistory = vcsPage.lnkChargeHistoryclick(driver);

		log("Step 15: Search for payment ");
		vcsPageChargeHistory.SearchPayment(1);

		String Status = vcsPageChargeHistory.getBillDetails("$" + pNoLoginPaymentPage.GetAmountPrize() + ".00");
		assertNotNull(Status, "The submitted pay now request was not found in the practice ");

		log("Step 16: Logout of Practice Portal ");
		practiceHome.logOut();

		log("Step 17: Verify Payment status in Get Response using the Timestamp received in response of Step 7");
		RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "=payNowpayment" + "&since=" + lastTimestamp, testcasesData.getResponsePath());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testVirtualCardSwiper() throws Exception {

		log("Test Case: Virtual Card Swiper");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		PayNow payNowData = new PayNow();
		PayNowTestData testcasesData = new PayNowTestData(payNowData);
		Long timestamp = System.currentTimeMillis();

		log("Step 1: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testcasesData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testcasesData.getPracticeUserName(), testcasesData.getPracticePassword());

		log("Step 2: Click on Virtual Card Swiper Tab ");
		VirtualCardSwiperPage vcsPage = practiceHome.clickVirtualCardSwiperTab();

		String Amount = IHGUtil.createRandomNumericString().substring(1, 4);
		log("Step 3: Click on Charge Card ");
		vcsPage.addCreditCardInfo("Test", "5105105105105100", "Visa", "12", "2022", Amount, "110", "12345", "Test0001", "Test Patient", "comment");

		log("Step 4: Verify whether the payment is completed successfully.");
		verifyEquals(Boolean.valueOf(vcsPage.getPaymentCompletedSuccessMsg().contains("Payment completed")), Boolean.valueOf(true),
				"The payment is completed properly.");

		log("Step 5: Logout of Practice Portal ");
		practiceHome.logOut();

		log("Step 6: Setup Oauth client 2.O");
		RestUtils.oauthSetup(testcasesData.getOAuthKeyStore(), testcasesData.getOAuthProperty(), testcasesData.getOAuthAppToken(), testcasesData.getOAuthUsername(),
				testcasesData.getOAuthPassword());

		// wait 30 seconds so the message can be processed
		Thread.sleep(180000);

		log("Step 7: Getting messages since timestamp: " + timestamp);
		String lastTimestamp = RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "=vcsPayment" + "&since=" + timestamp, testcasesData.getResponsePath());

		log("Step 8: Verify payment details");
		RestUtils.verifyPayment(testcasesData.getResponsePath(), Amount + ".00", IntegrationConstants.SUBMITTED, IntegrationConstants.VCSPAYMENT, null);

		String paymentID = RestUtils.paymentID;
		log("Payment ID :" + paymentID);

		String postPayload = RestUtils.preparePayment(testcasesData.getPaymentPath(), paymentID, Amount + ".00", IntegrationConstants.VCSPAYMENT);

		log("Step 9: Do a Post and get the message");
		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestUrl() + "=vcsPayment", postPayload, testcasesData.getResponsePath());

		log("Step 10: Get processing status until it is completed");
		boolean completed = false;
		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}

		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 11: Login to Practice Portal to search record");
		practiceLogin = new PracticeLoginPage(driver, testcasesData.getPracticeURL());
		practiceHome = practiceLogin.login(testcasesData.getPracticeUserName(), testcasesData.getPracticePassword());

		log("Step 12: Click on Virtual Card Swiper Tab ");
		vcsPage = practiceHome.clickVirtualCardSwiperTab();

		log("Step 13: Click on Charge History Link ");
		VirtualCardSwiperPageChargeHistory vcsPageChargeHistory = vcsPage.lnkChargeHistoryclick(driver);

		log("Step 14: Search for payment");
		vcsPageChargeHistory.SearchPayment(2);

		String Status = vcsPageChargeHistory.getBillDetails("$" + Amount + ".00");
		assertNotNull(Status, "The submitted csv request was not found in the practice ");

		log("Step 15: Logout of Practice Portal ");
		practiceHome.logOut();

		log("Step 16: Verify Payment status in Get Response using the Timestamp received in response of Step 7");
		RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "=vcsPayment" + "&since=" + lastTimestamp, testcasesData.getResponsePath());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCSecureMessageFromPractice() throws Exception {
		log("Test Case: AMDC Secure Message from Practice. ");

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
		log("From: " + testData.getIntegrationPracticeID());
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
		String message =
				RestUtils.prepareSecureMessage(testData.getSecureMessagePath(), testData.getIntegrationPracticeID(), testData.getUserName(), "Test " + timestamp, null);

		String messageID = RestUtils.newMessageID();
		log("Partner Message ID:" + messageID);

		log("Step 4: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), message, testData.getResponsePath());

		log("Step 5: Get processing status until it is completed");
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

		log("Step 6: Check secure message in patient gmail inbox");
		String emailMessageLink =
				RestUtils.verifyEmailNotification(testData.getGmailUserName(), testData.getGmailPassword(), testData.getSender3(), 3, "Portal 1.0");

		log("Step 7: Login to Patient Portal");
		PortalLoginPage loginPage = new PortalLoginPage(driver, emailMessageLink);
		MessageCenterInboxPage inboxPage = loginPage.loginNavigateToInboxPage(testData.getUserName(), testData.getPassword());

		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("Step 8: Find message in Inbox");
		String messageIdentifier = Long.toString(timestamp);

		MessagePage msg = inboxPage.openMessageInInbox(messageIdentifier);

		log("Step 9: Validate message loads and is the right message");
		assertTrue(msg.isSubjectLocated(messageIdentifier));

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testStatementPreference() throws Exception {
		log("Test Case: Statement Preference in Portal 1.0");
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
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.isLoginPageLoaded());
		MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click on myaccountLink on MyPatientPage");
		MyAccountPage myAccountPage = myPatientPage.clickMyAccountLink();

		log("Step 4: Click on Preferences Tab");
		PreferencesPage myPreferencePage = myAccountPage.clickpreferencesLink();

		log("Step 5: Set Statement Delievery Preference as Paper Statement");
		String setStatementPrefernce = "PAPER";
		myPreferencePage.setStatementPreference(setStatementPrefernce);
		myPreferencePage.clickupdateYourPreferences();

		log("Step 6: Logout from Patient portal");
		myAccountPage.logout(driver);

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
		log("External Patient Id is " + externalPatientId);

		practiceHome.logOut();

		log("Step 11: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		log("Step 12: Wait 60 seconds");
		Thread.sleep(60000);

		log("Step 13: Getting statement preference updates since timestamp: " + timeStamp);
		String nextTimeStamp = RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + timeStamp, testData.getResponsePath());

		log("Step 14: Validate the response");
		RestUtils.isStatementPreferenceCorrect(testData.getResponsePath(), memberId, setStatementPrefernce);

		log("Step 15: Prepare payload to set Statement Preference as Electronic Statement");
		setStatementPrefernce = "E_STATEMENT";
		if (StringUtils.isBlank(nextTimeStamp))
			timeStamp = System.currentTimeMillis();
		else
			timeStamp = Long.valueOf(nextTimeStamp);

		String payload = RestUtils.preparePostStatementPreference(testData.getStatementPath(), memberId, externalPatientId, setStatementPrefernce);

		log("Step 16: Do POST Statement Preference API & set preference to Electronic Statement");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), payload, testData.getResponsePath());

		log("Step 17: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		assertTrue(completed);

		log("Step 18: GET Statement Preference API");
		log("Getting statement preference updates since timestamp: " + timeStamp);
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + timeStamp, testData.getResponsePath());

		log("Step 19: Validate the response");
		RestUtils.isStatementPreferenceCorrect(testData.getResponsePath(), memberId, setStatementPrefernce);

		log("Step 20: Login to Patient Portal");
		PortalLoginPage loginPage1 = new PortalLoginPage(driver, testData.getUrl());
		assertTrue(loginPage1.isLoginPageLoaded());
		MyPatientPage myPatientPage1 = loginPage1.login(testData.getUserName(), testData.getPassword());

		log("Step 21: Check for update in Statement Preference");
		MyAccountPage myAccountPage1 = myPatientPage1.clickMyAccountLink();
		PreferencesPage myPreferencePage1 = myAccountPage1.clickpreferencesLink();
		myPreferencePage1.checkStatementPreference(setStatementPrefernce);

		log("Step 22: Logout of Portal");
		myAccountPage1.logout(driver);

	}

}
