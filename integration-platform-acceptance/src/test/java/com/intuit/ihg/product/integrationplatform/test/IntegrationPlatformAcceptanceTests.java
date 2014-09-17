package com.intuit.ihg.product.integrationplatform.test;


import java.util.Random;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
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
import com.intuit.ihg.product.integrationplatform.utils.FormExportTestData;
import com.intuit.ihg.product.integrationplatform.utils.GE;
import com.intuit.ihg.product.integrationplatform.utils.GETestData;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PIDC;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.FormExport;
import com.intuit.ihg.product.integrationplatform.utils.Prescription;
import com.intuit.ihg.product.integrationplatform.utils.PrescriptionTestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.object.maps.integrationplatform.page.TestPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrHomePage;
import com.intuit.ihg.product.object.maps.phr.page.messages.PhrInboxMessage;
import com.intuit.ihg.product.object.maps.phr.page.messages.PhrMessagesPage;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.createAccount.CreateAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessageCenterInboxPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessagePage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.newRxRenewalpage.NewRxRenewalPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages.FormBasicInfoPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages.FormCurrentSymptomsPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages.FormEmergencyContactPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages.FormMedicationsPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormWelcomePage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.AppointmentRequestStep1Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.AppointmentRequestStep2Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.AppointmentRequestStep3Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.AppointmentRequestStep4Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffHistoryPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep1Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep2Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep3Page;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.intuit.ihg.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.rxrenewal.RxRenewalSearchPage;
import com.intuit.ihg.product.object.maps.smintegration.page.BetaCreateNewPatientPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;


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

	
	////////////////@Test       (enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testSiteGenLoginLogout() throws Exception {

	 log("+++++++++++++ Test run+++++++++++");
	 
	 log("Test Case: testAddandRemovePreferences");
	 log("Execution Environment: " + IHGUtil.getEnvironmentType());
	 log("Execution Browser: " + TestConfig.getBrowserType());
    	log("URL: " +"http://wwww.google.com");
		

		log("step 2:LogIn");
		new TestPage(driver, "http://www.google.com");
		Thread.sleep(100000);
		//MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());

	}

	/*
	//////////@Test       (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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
	*/
	
	@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAppointmentRequest() throws Exception {
		
		log("Test Case: Appointment Request");
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		Long timestamp = System.currentTimeMillis();
		log("step 1: Get Data from Excel");
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
		String arSMSubject=IntegrationConstants.AR_SM_SUBJECT.toString()+""+String.valueOf(time);
		String arSMBody=IntegrationConstants.AR_SM_BODY.toString()+""+String.valueOf(time);
		log("************Appointment Reason: "+reason);
		log("************Appointment Secure Message Subject: "+arSMSubject);
		log("************Appointment Secure Message Body: "+arSMBody);
	    
		log("step 6: Complete Appointment Request Step3 Page  ");
		AppointmentRequestStep4Page apptRequestStep4 = apptRequestStep3.clickSubmit();

		log("step 7: Complete Appointment Request Step4 Page  ");
		myPatientPage = apptRequestStep4.clickBackToMyPatientPage();

		log("step 8: Logout of Patient Portal");
		myPatientPage.logout(driver);
		
		log("step 9: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		log("step 10: Get Appointment Rest call");
		
		//get only messages from last hour in epoch time to avoid transferring lot of data
		Long since = timestamp / 1000L - 60 * 24;
		
		log("Getting messages since timestamp: " + since);
		
		//do the call and save xml, ",0" is there because of the since attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		
		log("step 11: Checking reason in the response xml");
		RestUtils.isReasonResponseXMLValid(testData.getResponsePath(), reason);
		
		String postXML = RestUtils.findValueOfChildNode(testData.getResponsePath(),"AppointmentRequest",reason,arSMSubject,arSMBody,testData.getAppointmentPath());
		
		// httpPostRequest method
		log("step 12: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), postXML, testData.getResponsePath());

		log("step 13: Get processing status until it is completed");
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
		
		//patient Portal validation 
		log("step 18: Login to Patient Portal");
		PortalLoginPage ploginPage = new PortalLoginPage(driver, testData.getUrl());
		MyPatientPage pPatientPage = ploginPage.login(testData.getUserName(), testData.getPassword());

		log("step 19: Go to Inbox");
		MessageCenterInboxPage inboxPage = pPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 20: Find message in Inbox");
		MessagePage msg = inboxPage.openMessageInInbox(arSMSubject);

		log("step 21: Validate message loads and is the right message");
		assertTrue(msg.isSubjectLocated(arSMSubject));
				
		log("step 22: Logout of Patient Portal");
		pPatientPage.logout(driver);		
			
		//Practice portal validation  
		log("step 14: Login to Practice Portal");

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(), testData.getPracticePassword());

		log("step 15: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestSearchPage.PAGE_NAME);
		
		log("step 16: Search for appt requests");
		apptSearch.searchForApptRequests(2,null,null);
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(reason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestDetailStep1Page.PAGE_NAME);
		
		String actualSMSubject=detailStep1.getPracticeMessageSubject();
		assertTrue(detailStep1.getPracticeMessageSubject().contains(arSMSubject), "Expected Secure Message Subject containing [" +arSMSubject
				+ "but actual message subject was [" + actualSMSubject + "]");
		
		
		String actualSMBody=detailStep1.getPracticeMessageBody();
		assertTrue(detailStep1.getPracticeMessageBody().contains(arSMBody), "Expected Secure Message Body containing [" +arSMBody
				+ "but actual message body was [" + actualSMBody + "]");
				
		log("step 17: Logout of Practice Portal");
		practiceHome.logOut();	
		
		
			
		
	}
	
	@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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
	
	@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCSecureMessageWithReadCommnunication() throws Exception{
		log("Test Case: AMDC Secure Message with Read Communication");
		
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
		
		String messageID=RestUtils.newMessageID();
		log("Partner Message ID:"+messageID);
		
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
		MessageCenterInboxPage inboxPage = myPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 8: Find message in Inbox");
		String messageIdentifier = Long.toString(timestamp);
		
		log("step 9: Log the message read time ");
		long epoch = System.currentTimeMillis()/1000;
		
		String readdatetimestamp=RestUtils.readTime(epoch);
		log("Message Read Time:"+readdatetimestamp);
		
		MessagePage msg = inboxPage.openMessageInInbox(messageIdentifier);

		log("step 10: Validate message loads and is the right message");
		assertTrue(msg.isSubjectLocated(messageIdentifier));
		
		log("step 11: Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;
		
		log("step 12: Wait 120 seconds, so the message can be processed");
		Thread.sleep(120000);
		
		log("Getting messages since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getReadCommunicationURL() + "?since=" + since + ",0", testData.getResponsePath());
		
		log("step 13: Validate the message id and read time in response");
		RestUtils.isReadCommunicationMessage(testData.getResponsePath(),messageID,readdatetimestamp);
				
		log("step 14: Reply to the message");
		msg.replyToMessage(IntegrationConstants.MESSAGE_REPLY);
		
		log("step 15: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);
		
		log("step 16: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		
		log("step 17: Validate message reply");
		RestUtils.isReplyPresent(testData.getResponsePath(), messageIdentifier);
		
	}
	
	
	@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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
	
	@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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
		log("Practice Patient ID: " +practicePatientId);
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
		Long since = timestamp / 1000L - 60 * 24;

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
	
	@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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
		
		log("step 2: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), ccd, testData.getResponsePath());

		log("step 3: Get processing status until it is completed");
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
		log("step 4:LogIn to Patient Portal ");
		PortalLoginPage portalloginpage = new PortalLoginPage(driver,
				testData.getURL());
		MyPatientPage pMyPatientPage = portalloginpage.login(
				testData.getUserName(),
				testData.getPassword());
		
		log("step 5: Go to Inbox");
		MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 6: Find message in Inbox");
		MessagePage pMessage = inboxPage
		.clickFirstMessageRow();

		log("step 7: Validate message subject and send date");
		Thread.sleep(1000);
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(pMessage.isSubjectLocated("New Health Information Import"));
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("step 8 Click on link ReviewHealthInformation");
		pMessage.clickBtnReviewHealthInformation();

		
		log("step 9: Verify if CCD Viewer is loaded and click Close Viewer");
		pMessage.verifyCCDViewerAndClose();
		
		driver.switchTo().defaultContent();
		
		log("step 10: Go to patient page");
		pMyPatientPage = pMessage.clickMyPatientPage();
		
		log("step 11:Click PHR");
		pMyPatientPage.clickPHRWithoutInit(driver);
		PhrHomePage phrPage = PageFactory.initElements(driver, PhrHomePage.class);
		
		log("step 12:Go to PHR Inbox");
		PhrMessagesPage phrMessagesPage = phrPage.clickOnMyMessages();
		assertTrue(phrMessagesPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 13: Click first message");
		PhrInboxMessage phrInboxMessage = phrMessagesPage.clickOnFirstMessage();

		log("step 14: Validate message subject and send date");
		Thread.sleep(1000);
		assertEquals(phrInboxMessage.getPhrMessageSubject(),
				IntegrationConstants.CCD_MESSAGE_SUBJECT,
		"### Assertion failed for Message subject");
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("step 15: Click on link ReviewHealthInformation");
		phrInboxMessage.clickBtnReviewHealthInformationPhr();

		
		log("step 16:Verify if CCD Viewer is loaded and click Close Viewer");
		phrInboxMessage.verifyCCDViewerAndClosePhr();
		
		//driver.switchTo().defaultContent();
		
	}
	
	@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ERxPrescription() throws Exception {
		log("Test Case: Rx Prescription Request");
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("step 1: Get Data from Excel");
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

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
		MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("step 3: Verify for My Patient Page ");
		PortalUtil.setPortalFrame(driver);
		verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

		log("step 4: Click on PrescriptionRenewal Link ");
		NewRxRenewalPage newRxRenewalPage = myPatientPage.clickPrescriptionRenewal();
		
		log("step 5:set Medication Fields in RxRenewal Page");
		newRxRenewalPage.setMedicationDetails();

		log("step 6:set Pharmacy Fields in RxRenewal Page");
		newRxRenewalPage.setPharmacyFields();
		
		log("Getting Medication Name ");
		long time=newRxRenewalPage.getCreatedTs();
		String medicationName=PortalConstants.MedicationName.toString()+String.valueOf(time);
		log("Medication Name :"+medicationName);
		
		String rxSMSubject=PortalConstants.RxRenewal_Subject_Tag.toString()+String.valueOf(time);
		log("Perscription Subject :"+rxSMSubject);
		
		String rxSMBody=IntegrationConstants.QUESTION_MESSAGE.toString()+""+String.valueOf(time);
		log("Perscription Reply :"+rxSMBody);
		
		log("step 7:Verify RxRenewal Confirmation Message");
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 5, newRxRenewalPage.renewalConfirmationmessage);
		verifyEquals(newRxRenewalPage.renewalConfirmationmessage.getText(), PortalConstants.RenewalConfirmation);

		log("step 8: Logout of Patient Portal");
		myPatientPage.logout(driver);
		

		log("step 9: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		//OAuthPropertyManager.init(testData.getOAuthProperty());
		
		log("step 10: Get Prescription Rest call");
		//get only messages from last hour in epoch time to avoid transferring lot of data
		Long since = timestamp / 1000L - 60 * 24;
		
		log("Getting messages since timestamp: " + since);
				
		//do the call and save xml, ",0" is there because of the since attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("step 11: Checking validity of the response xml");
		RestUtils.isMedicationDetailsResponseXMLValid(testData.getResponsePath(), medicationName);	
		
		String postXML = RestUtils.findValueOfMedicationNode(testData.getResponsePath(),"Medication",medicationName,rxSMSubject,rxSMBody,testData.getPrescriptionPath());
		
		log("step 12: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), postXML, testData.getResponsePath());

		log("step 13: Get processing status until it is completed");
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
		
		//Patient portal validation
		log("step 14: Login to Patient Portal");
		PortalLoginPage ploginPage = new PortalLoginPage(driver, testData.getUrl());
		MyPatientPage pPatientPage = ploginPage.login(testData.getUserName(), testData.getPassword());

		log("step 15: Go to Inbox");
		MessageCenterInboxPage inboxPage = pPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 16: Find message in Inbox");
		MessagePage msg = inboxPage.openMessageInInbox(rxSMSubject);

		log("step 17: Validate message loads and is the right message");
		assertTrue(msg.isSubjectLocated(rxSMSubject));
		
		
		log("step 18: Logout of Patient Portal");
		pPatientPage.logout(driver);
		
		//Practice portal validation  
		log("step 19: Login to Practice Portal");

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(), testData.getPracticePassword());

		log("step 20:Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome.clickonRxRenewal();

		log("step 21:Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday(2);

		log("Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		/*log("Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.setRxRenewalFields();*/

		log("step 22: Logout of Practice Portal");
		practiceHome.logOut();
		
	}
		
		@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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
			
			log("step 1: Setup Oauth client"); 
			RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
			
			String ccd = RestUtils.prepareCCD(testData.getCCDPath());
			
			log("step 2: Do Message Post Request");
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), ccd, testData.getResponsePath());	

			log("step 3: Get processing status until it is completed");
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
			
			log("step 4:LogIn to Patient Portal ");
			PortalLoginPage portalloginpage = new PortalLoginPage(driver,
					testData.getURL());
			MyPatientPage pMyPatientPage = portalloginpage.login(
					testData.getUserName(),
					testData.getPassword());
			
			log("step 5: Go to Inbox");
			MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
			assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

			log("step 6: Find message in Inbox");
			MessagePage pMessage = inboxPage
			.clickFirstMessageRow();

			log("step 7: Validate message subject and send date");
			Thread.sleep(1000);
			log("######  Message Date :: " + IHGUtil.getEstTiming());
			assertTrue(pMessage.isSubjectLocated("New Health Information Import"));
			assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

			log("step 8: Click on link ReviewHealthInformation");
			pMessage.clickBtnReviewHealthInformation();

			
			log("step 9: Verify if CCD Viewer is loaded and click Close Viewer");
			pMessage.verifyCCDViewerAndClose();
			
			driver.switchTo().defaultContent();
			
			log("step 10: Go to patient page");
			pMyPatientPage = pMessage.clickMyPatientPage();
			
			log("step 11:Click PHR");
			pMyPatientPage.clickPHRWithoutInit(driver);
			PhrHomePage phrPage = PageFactory.initElements(driver, PhrHomePage.class);
			
			log("step 12:Go to PHR Inbox");
			PhrMessagesPage phrMessagesPage = phrPage.clickOnMyMessages();
			assertTrue(phrMessagesPage.isInboxLoaded(), "Inbox failed to load properly.");

			log("step 13: Click first message");
			PhrInboxMessage phrInboxMessage = phrMessagesPage.clickOnFirstMessage();

			log("step 14: Validate message subject and send date");
			Thread.sleep(1000);
			assertEquals(phrInboxMessage.getPhrMessageSubject(),
					IntegrationConstants.CCD_MESSAGE_SUBJECT,
			"### Assertion failed for Message subject");
			log("######  Message Date :: " + IHGUtil.getEstTiming());
			assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

			log("step 15: Click on link ReviewHealthInformation");
			phrInboxMessage.clickBtnReviewHealthInformationPhr();
			
			log("step 16:Verify if CCD Viewer is loaded and click Close Viewer");
			phrInboxMessage.verifyCCDViewerAndClosePhr();
			
		}
		
		@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
		public void testAllScriptImportCCD() throws Exception {
			
			log("Test Case:  Import CCD via All script Adapter and check in patient Portal");
			AllScript allScriptData = new AllScript();
			AllScriptTestData testData = new AllScriptTestData(allScriptData);
			
			log("UserName: " + testData.getUserName());
			log("Password:" + testData.getPassword());
			log("Rest Url: " + testData.getRestUrl());
			log("CCD Path: " + testData.getCCDPath());
			log("Response Path: " + testData.getResponsePath());
			
			log("step 1: AllScript CCD");
			String ccd = RestUtils.readXMLfile(testData.getCCDPath());
			
			log("step 2: Do Message Post Request");
			RestUtils.setupHttpPostRequestExceptOauth(testData.getRestUrl(), ccd, testData.getResponsePath());	
			
			log("step 3: verify transport status in response xml");
			boolean completed = false;
			for (int i = 0; i < 3; i++) {
				// wait 10 seconds so the message can be processed
				Thread.sleep(10000);
				if (RestUtils.isCCDProcessingCompleted(testData.getResponsePath())) {
					completed = true;
					break;
				}
			}
			verifyTrue(completed, "Message processing was not completed in time");
			
			log("step 4:LogIn to Patient Portal ");
			PortalLoginPage portalloginpage = new PortalLoginPage(driver,
					testData.getURL());
			MyPatientPage pMyPatientPage = portalloginpage.login(
					testData.getUserName(),
					testData.getPassword());
			
			log("step 5: Go to Inbox");
			MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
			assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

			log("step 6: Find message in Inbox");
			MessagePage pMessage = inboxPage
			.clickFirstMessageRow();

			log("step 7: Validate message subject and send date");
			Thread.sleep(1000);
			/*assertEquals(pMessage.getPracticeReplyMessageTitle(),
					IntegrationConstants.CCD_MESSAGE_SUBJECT,
			"### Assertion failed for Message subject");*/
			log("######  Message Date :: " + IHGUtil.getEstTiming());
			assertTrue(pMessage.isSubjectLocated("New Health Information Import"));
			assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

			log("step 8: Click on link ReviewHealthInformation");
			pMessage.clickBtnReviewHealthInformation();

			
			log("step 9: Verify if CCD Viewer is loaded and click Close Viewer");
			pMessage.verifyCCDViewerAndClose();
			
			driver.switchTo().defaultContent();
			
			log("step 10: Go to patient page");
			pMyPatientPage = pMessage.clickMyPatientPage();
			
			log("step 11:Click PHR");
			pMyPatientPage.clickPHRWithoutInit(driver);
			PhrHomePage phrPage = PageFactory.initElements(driver, PhrHomePage.class);
			
			log("step 12:Go to PHR Inbox");
			PhrMessagesPage phrMessagesPage = phrPage.clickOnMyMessages();
			assertTrue(phrMessagesPage.isInboxLoaded(), "Inbox failed to load properly.");

			log("step 13: Click first message");
			PhrInboxMessage phrInboxMessage = phrMessagesPage.clickOnFirstMessage();

			log("step 14: Validate message subject and send date");
			Thread.sleep(1000);
			assertEquals(phrInboxMessage.getPhrMessageSubject(),
					IntegrationConstants.CCD_MESSAGE_SUBJECT,
			"### Assertion failed for Message subject");
			log("######  Message Date :: " + IHGUtil.getEstTiming());
			assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

			log("step 15: Click on link ReviewHealthInformation");
			phrInboxMessage.clickBtnReviewHealthInformationPhr();
			
			log("step 16:Verify if CCD Viewer is loaded and click Close Viewer");
			phrInboxMessage.verifyCCDViewerAndClosePhr();
			
		}
		@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
			public void testE2EFormsExport() throws Exception {
			log("Test Case: testE2EFormsExport");
			
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());
			
			log("step 1: Get Data from Excel");
			FormExport formExport = new FormExport();
			FormExportTestData testData = new FormExportTestData(formExport);
			Long timestamp = System.currentTimeMillis();
			
			log("Url: " + testData.getUrl());
			log("Rest Url: " + testData.getRestUrl());
			log("Response Path: " + testData.getResponsePath());
			log("OAuthProperty: " + testData.getOAuthProperty());
			log("OAuthKeyStore: " + testData.getOAuthKeyStore());
			log("OAuthAppToken: " + testData.getOAuthAppToken());
			log("OAuthUsername: " + testData.getOAuthUsername());
			log("OAuthPassword: " + testData.getOAuthPassword());
				
			
			log("step 2: Click Sign-UP");
			PortalLoginPage loginpage = new PortalLoginPage(driver,
					testData.getUrl());
			loginpage
					.signUp();

			BetaCreateNewPatientPage createNewPatientPage = new BetaCreateNewPatientPage(
					driver);
			log("step 3: Fill details in Create Account Page");
			// Setting the variables for user in other tests
			String email = PortalUtil.createRandomEmailAddress(testData
					.getEmail());
			log("Email as well as UserName:-" + email);
			MyPatientPage pMyPatientPage = createNewPatientPage.BetaSiteCreateAccountPage(testData.getFirstName(),
							testData.getLastName(), email,
							testData.getPhoneNumber(),
							testData.getDob_Month(), testData.getDob_Day(),
							testData.getDob_Year(), testData.getZip(),
							testData.getSSN(), testData.getAddress(),
							testData.getPassword(),
							testData.getSecretQuestion(),
							testData.getAnswer(),
							testData.getAddressState(),
							testData.getAddressCity());
			
			String firstName=createNewPatientPage.FName;
			String lastName=createNewPatientPage.LName;
			
			
			log("step 4: Assert Webelements in MyPatientPage");
			assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

			log("step 5: Logout");
			pMyPatientPage.clickLogout(driver);
						
			log("step 6: Login to Practice Portal");
			PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
			PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getPracticeUsername(),testData.getPracticePassword());

			log("step 7: Click on Patient Search Link");
			PatientSearchPage pPatientSearchPage= pPracticeHomePage.clickPatientSearchLink();

			log("step 8: Set Patient Search Fields");
			pPatientSearchPage.searchForPatientInPatientSearch(firstName,lastName);
		
			log("step 9: Verify the Search Result");
			IHGUtil.waitForElement(driver,30,pPatientSearchPage.searchResult);
			verifyEquals(true,pPatientSearchPage.searchResult.getText().contains(firstName));
			
			log("step 10: Click on Patient");
			PatientDashboardPage patientPage=pPatientSearchPage.clickOnPatient(firstName, lastName);
			
			log("step 11: Set External Patient ID");
			String externalPatientID=patientPage.setExternalPatientID();
			
			log("External Patient Id:-"+externalPatientID);
			
			String patientID=patientPage.patientID;
			log("Medfusion Patient ID:-"+patientID);
			
			log("step 12: Logout of Practice Portal");
			pPracticeHomePage.logOut();
					
			log("step 13: Login to Patient Portal for submitting 'Patient Registration' Discrete Form");
			PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
			MyPatientPage myPatientPage = loginPage.login(email, testData.getPassword());
			
			log("step 14: Verify for My Patient Page ");
			PortalUtil.setPortalFrame(driver);
			verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);
			
			log("step 15: Click on Registration button ");
			FormWelcomePage pFormWelcomePage = pMyPatientPage.clickStartRegistrationButton(driver);
			
			log("step 16: Click on Continue button ");
			FormBasicInfoPage pFormBasicInfoPage=pFormWelcomePage.clickSaveAndContinueButton(FormBasicInfoPage.class);
			
			log("step 17: Set Basic Information Form Fields");
			FormEmergencyContactPage pFormEmergencyContactPage = pFormBasicInfoPage.setBasicInfoFromField();
			
			log("step 18: Set Emergency Contact Form Fields");
			FormCurrentSymptomsPage pFormCurrentSymptomsPage=pFormEmergencyContactPage.fillEmergencyContactFormFields();
			
			log("step 19: Set Current Symptoms Form Fields");
			pFormCurrentSymptomsPage.setNoSymptoms();
			FormMedicationsPage pFormMedicationsPage = pFormCurrentSymptomsPage.clickSaveAndContinueButton(FormMedicationsPage.class);
			
			log("step 20: Set Medication Form Fields");
			myPatientPage=pFormMedicationsPage.fillMedicationFormFields();	

			log("step 21: Setup Oauth client"); 
			RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
			
			Long since = timestamp / 1000L - 60 * 24;
			
			log("step 22: Getting messages since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			
			
			log("step 23: Validate PatientDemographics and CCD details in the response");
			RestUtils.isPatientAppeared(testData.getResponsePath(), externalPatientID,patientID,firstName);
				
			log("step 24: Logout");
			pMyPatientPage.clickLogout(driver);
			
		}
}