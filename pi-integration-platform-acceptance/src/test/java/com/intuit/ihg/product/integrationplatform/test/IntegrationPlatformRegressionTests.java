package com.intuit.ihg.product.integrationplatform.test;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.flows.iEHDCSendCCD;
import com.intuit.ihg.product.integrationplatform.flows.iPIDCSendPatientInvite;
import com.intuit.ihg.product.integrationplatform.implementedExternals.SendCCD;
import com.intuit.ihg.product.integrationplatform.implementedExternals.SendPatientInvite;
import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCPayload;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentData;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentDataUtils;
import com.intuit.ihg.product.integrationplatform.utils.BulkAdmin;
import com.intuit.ihg.product.integrationplatform.utils.BulkMessagePayload;
import com.intuit.ihg.product.integrationplatform.utils.CCDPayload;
import com.intuit.ihg.product.integrationplatform.utils.DirectorySearchUtils;
import com.intuit.ihg.product.integrationplatform.utils.EHDC;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.LoadPreTestData;
import com.intuit.ihg.product.integrationplatform.utils.MU2GetEventData;
import com.intuit.ihg.product.integrationplatform.utils.MU2Utils;
import com.intuit.ihg.product.integrationplatform.utils.P2PUnseenMessageList;
import com.intuit.ihg.product.integrationplatform.utils.PatientRegistrationUtils;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.integrationplatform.utils.SendDirectMessage;
import com.intuit.ihg.product.integrationplatform.utils.SendDirectMessageUtils;
import com.intuit.ihg.product.integrationplatform.utils.StatementEventData;
import com.intuit.ihg.product.integrationplatform.utils.StatementEventUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPreferencesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;
import com.medfusion.product.patientportal2.utils.PortalConstants;
import com.medfusion.product.practice.api.pojo.Practice;

/**
 * @author rkhambe
 * @Date 22/Nov/2016
 * @Description :-Regression Test for pi-integration
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
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		log("Step 5: Validate message subject and send date");
		Thread.sleep(1000);
		log("Message Date" + IHGUtil.getEstTiming());
		assertTrue(messagesPage.isMessageDisplayed(driver, "You have a new health data summary"));
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
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");
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
	 
	 @Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testMU2GetEventForExistingPatient() throws Exception {
		log("Test Case (testMU2GetEventForExistingPatient): Consolidated CCD related events verification in Pull Events");

		log("Test case Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);
		
		
		MU2Utils MU2UtilsObj = new MU2Utils();
		MU2UtilsObj.mu2GetEvent(testData,driver);
	}
	 
	 @Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testMU2GetEventForNewPatient() throws Exception {	
		log("Test Case (testMU2GetEventForNewPatient): Consolidated CCD related events verification for newly created patients");
		log("Step 1:  Create Patient");
		long timestamp = System.currentTimeMillis();
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);
		
		EHDC EHDCObj = new EHDC();
		
		LoadPreTestDataObj.loadEHDCDataFromProperty(EHDCObj);
		iPIDCSendPatientInvite sendPatientInviteObj = new SendPatientInvite();
		ArrayList<String> patientDetail = sendPatientInviteObj.sendPatientInviteToPractice(testData.PATIENT_INVITE_RESTURL, testData.PATIENT_PRACTICEID,testData.PATIENT_EXTERNAL_ID);
		
		log("Follwing are patient details");
		for (String values : patientDetail) {
			log(" " + values);
		}
		log("checking email for activation UrL link");
		Thread.sleep(5000);
		Mailinator mail = new Mailinator();
		String activationUrl = mail.getLinkFromEmail(patientDetail.get(4), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 20);
		assertTrue(activationUrl!=null, "Error: Activation link not found.");
		
		PatientRegistrationUtils.registerPatient(activationUrl, patientDetail.get(4), testData.PatientPassword, testData.SecretQuestion, testData.SecretAnswer, testData.HomePhoneNo, driver, patientDetail.get(2), patientDetail.get(3));
		
		Thread.sleep(12000);
		log("Step 2:  Send CCD to Patient");
		
		iEHDCSendCCD sendCCDObj = new SendCCD();
		
		log("Send 1st CCD to Patient");
		ArrayList<String> ccdDetail1 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From, testData.PATIENT_PRACTICEID, patientDetail.get(0), EHDCObj.ccdXMLPath,testData.PATIENT_EXTERNAL_ID);
		log(ccdDetail1.get(0));
		Thread.sleep(8000);
		
		log("Send 2nd CCD to Patient");
		ArrayList<String> ccdDetail2 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From, testData.PATIENT_PRACTICEID, patientDetail.get(0),  testData.CCDPATH1,testData.PATIENT_EXTERNAL_ID);
		log(ccdDetail2.get(0));
		Thread.sleep(8000);
		
		log("Send 3rd CCD to Patient");
		ArrayList<String> ccdDetail3 =sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From, testData.PATIENT_PRACTICEID, patientDetail.get(0),  testData.CCDPATH2,testData.PATIENT_EXTERNAL_ID);
		log(ccdDetail3.get(0));
		Thread.sleep(8000);
		
		log("Send 4th CCD to Patient");
		ArrayList<String> ccdDetail4 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From, testData.PATIENT_PRACTICEID, patientDetail.get(0),  testData.CCDPATH3,testData.PATIENT_EXTERNAL_ID);
		log(ccdDetail4.get(0));
		
		log("Set username and password for MU2 : UserName "+patientDetail.get(4)+" password: "+testData.PatientPassword);
		
		RestUtils.oauthSetup(testData.OAUTH_KEYSTORE, testData.OAUTH_PROPERTY, testData.OAUTH_APPTOKEN, testData.OAUTH_USERNAME,testData.OAUTH_PASSWORD);
		
		Long since = timestamp / 1000L - 60 * 24;
		log("Getting patients since timestamp: " + since);
		log("PUSH_RESPONSEPATH: " + testData.PUSH_RESPONSEPATH);
		RestUtils.setupHttpGetRequest(testData.PATIENT_INVITE_RESTURL + "?since=" + since + ",0", testData.PUSH_RESPONSEPATH);
		
		MU2Utils MU2UtilsObj = new MU2Utils();
	    String patientID = MU2UtilsObj.getMedfusionID(testData.PUSH_RESPONSEPATH,patientDetail.get(0));
		
		log("patientID : "+patientID);
		
		log("waiting for CCD to reflect on portal 2.0 ");
		testData.PORTAL_USERNAME=patientDetail.get(4);
		testData.PORTAL_PASSWORD=testData.PatientPassword;
		testData.INTUIT_PATIENT_ID =patientID;
		testData.CCDMessageID1 =ccdDetail4.get(0);
		testData.CCDMessageID2 =ccdDetail3.get(0);
		testData.PatientExternalId_MU2=patientDetail.get(0);
		testData.PatientFirstName_MU2  = patientDetail.get(0);
		testData.PatientLastName_MU2 = patientDetail.get(1);
		
    	Thread.sleep(70000);
		
		log("Step 4:  Login Portal 2.0");
		
		MU2UtilsObj.mu2GetEvent(testData,driver);
	 }
	 
	 @DataProvider(name = "portalVersion")
	 public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] {{"1.0"},{"2.0"},};
			return obj;
	 }
	 
	 @Test(enabled = true,dataProvider = "portalVersion", groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testPIDCPatientRegistrationV1(String portalVersion) throws Exception {
	 	log("Test Case: PIDC Patient Registration v1 channel for portal-"+portalVersion);
	 	log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
	 	PatientRegistrationUtils.pidcPatientRegistration("v1",driver,portalVersion); 	
	 }
	 
	 @Test(enabled = true,dataProvider = "portalVersion", groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testPIDCPatientRegistrationV2(String portalVersion) throws Exception {
	 	log("Test Case: PIDC Patient Registration v2 channel for portal-"+portalVersion);
	 	log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
	 	PatientRegistrationUtils.pidcPatientRegistration("v2",driver,portalVersion);
     }
	 
	 @Test(enabled = true,groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testAppointmentRequestForExistingPatient() throws Exception {
		log("Test Case: Appointment Request for Existing Patient From Partner");
	 	log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		AppointmentData testData = new AppointmentData();		
		LoadPreTestDataObj.loadAppointmentDataFromProperty(testData);
		AppointmentDataUtils aDUtils = new AppointmentDataUtils();
		
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.csvFilePath;
		aDUtils.csvFileReader(testData,workingDir);
		
		testData.Status = "NEW";
		testData.FirstName=testData.FirstName;
		testData.LastName=testData.LastName;
		testData.EmailUserName=testData.EmailUserName;
		testData.BatchSize = "2";
		
		testData.Time = testData.appointmentDetailList.get(1).getTime();
		testData.appointmentType = "FUTURE";
		testData.Location = "NEW";
		
		testData.Type=testData.appointmentDetailList.get(1).getType();
		testData.Reason=testData.appointmentDetailList.get(1).getReason();
		testData.Description=testData.appointmentDetailList.get(1).getDescription();
		
		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
		
		aDUtils.checkAppointment(testData, driver);
		Thread.sleep(6000);
		//homePage.clickOnLogout();
		Thread.sleep(3000);
		
		testData.Status = "UPDATE";
		testData.Time =  testData.appointmentDetailList.get(3).getTime(); 
		testData.Location = "Update";
		testData.appointmentType = "FUTURE";
		
		testData.Type=testData.appointmentDetailList.get(3).getType();
		testData.Reason=testData.appointmentDetailList.get(3).getReason();
		testData.Description=testData.appointmentDetailList.get(3).getDescription();
		testData.BatchSize = "1";
		
		aDUtils.checkAppointment(testData, driver);
		Thread.sleep(3000);
		
		testData.Status = "CANCEL";
		testData.Time = testData.appointmentDetailList.get(4).getTime();
		testData.Location = "Cancel";
		testData.appointmentType = "FUTURE";
		
		testData.Type=testData.appointmentDetailList.get(4).getType();
		testData.Reason=testData.appointmentDetailList.get(4).getReason();
		testData.Description=testData.appointmentDetailList.get(4).getDescription();
		testData.BatchSize = "1";
		
		aDUtils.checkAppointment(testData, driver);
		
	 }

	 @Test(enabled = true,groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testAppointmentRequestForNewSelfPatient() throws Exception {
	 	log("Test Case: Appointment Request for New Patient From Partner");
	 	log("Execution Environment: " + IHGUtil.getEnvironmentType());
	 	log("Execution Browser: " + TestConfig.getBrowserType());
	 	AppointmentDataUtils aDUtils = new AppointmentDataUtils();
	 	LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
	 	AppointmentData testData = new AppointmentData();		
	 	LoadPreTestDataObj.loadAppointmentDataFromProperty(testData);
		log("Step 1: Create patient");
		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(), patient.getPassword(), testDataPFL);
		
		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		
		homePage.clickOnLogout();
	
		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
		
		RestUtils.setupHttpGetRequest(testData.PATIENT_INVITE_RESTURL + "?since=" + since + ",0", testData.ResponsePath);
		Thread.sleep(2000);
		//log("responsePath- "+RestUtils.prepareCCD(testData.ResponsePath));
		
		String responseXML = RestUtils.prepareCCD(testData.ResponsePath);
		String medfusionID = aDUtils.getMedfusionID(patient.getEmail(), responseXML);
		log("medfusionID "+medfusionID);
		
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.csvFilePath;
		aDUtils.csvFileReader(testData,workingDir);
		
		log("Step 3: Post New AppointMentData with MFPatientID");
		
		testData.FirstName=patient.getFirstName();
		testData.LastName=patient.getLastName();
		testData.EmailUserName=patient.getEmail();
		testData.PatientPracticeId = patient.getFirstName();
		testData.MFPatientId=medfusionID;
		testData.BatchSize = "1";
		testData.Status = testData.appointmentDetailList.get(1).getStatus(); //"NEW";
		testData.Time = testData.appointmentDetailList.get(1).getTime(); //"2017-02-13T21:30:00.000Z";
		testData.Location =testData.appointmentDetailList.get(1).getLocation();
		testData.appointmentType = "FUTURE";
		testData.UserName = patient.getEmail();
		testData.Password = patient.getPassword();
		
		testData.Type=testData.appointmentDetailList.get(1).getType();
		testData.Reason=testData.appointmentDetailList.get(1).getReason();
		testData.Description=testData.appointmentDetailList.get(1).getDescription();
		
		
		aDUtils.checkAppointment(testData, driver);
		Thread.sleep(6000);
		
		log("Step 4: Post UPDATE AppointMentData ");
		testData.Status = "UPDATE";
		testData.Time = testData.appointmentDetailList.get(3).getTime(); //"2017-03-13T16:30:59.999Z";
		testData.Location = testData.appointmentDetailList.get(3).getLocation();
		testData.appointmentType = "FUTURE";
		testData.PatientPracticeId = patient.getFirstName();
		testData.BatchSize = "1";
		testData.Type=testData.appointmentDetailList.get(3).getType();
		testData.Reason=testData.appointmentDetailList.get(3).getReason();
		testData.Description=testData.appointmentDetailList.get(3).getDescription();
		
		
		aDUtils.checkAppointment(testData, driver);
		
		log("Step 5: Post CANCEL AppointMentData ");
		Thread.sleep(3000);
		testData.Status = "CANCEL";
		testData.Time = testData.appointmentDetailList.get(4).getTime(); //"2017-03-13T16:30:59.999Z";
		testData.Location = testData.appointmentDetailList.get(4).getLocation();
		testData.appointmentType = "FUTURE";
		testData.PatientPracticeId = patient.getFirstName();
		testData.BatchSize = "1";
		
		testData.Type=testData.appointmentDetailList.get(4).getType();
		testData.Reason=testData.appointmentDetailList.get(4).getReason();
		testData.Description=testData.appointmentDetailList.get(4).getDescription();
		
		aDUtils.checkAppointment(testData, driver);
		
		log("step 6: Login to Practice Portal");
		
		Practice practice = new Practice();
		practice.url = testData.portalURL; 
		practice.username = testData.practiceUserName; 
		practice.password = testData.practicePassword;
		
		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practice.url);
		PracticeHomePage pPracticeHomePage = practiceLogin.login(practice.username, practice.password);

		log("step 7: Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		log("step 8:Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(patient.getFirstName(), patient.getLastName());

		log("step 9:Verify the Search Result");
		IHGUtil.waitForElement(driver, 60, pPatientSearchPage.searchResult);
		Assert.assertTrue(pPatientSearchPage.searchResult.getText().contains(patient.getFirstName()));

		String searchResult = "//*[@id=\"table-1\"]/tbody/tr/td[1]/a";
		driver.findElement(By.xpath(searchResult)).click();
		
		String editPatientID = "//*[@id=\"dashboard\"]/fieldset[1]/table/tbody/tr[7]/td[2]/a";
		driver.findElement(By.xpath(editPatientID)).click();
		Thread.sleep(3000);
		String onDemandID = "//*[@id=\"content\"]/form/table/tbody/tr[7]/td[2]/input";
		String patientExternalID = driver.findElement(By.xpath(onDemandID)).getAttribute("value");
		
		log("Actual patient ID "+patientExternalID);
		log("Expected patient ID "+patient.getFirstName());
		
		Assert.assertEquals(patient.getFirstName(), patientExternalID, "Patient External ID Matched !");
	 }

	 
	 @Test(enabled = true,groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testStatementEventForExistingPatient() throws Exception {
		log("Test Case: Post Statment and verify its Event for Existing Patient From Partner");
	 	log("Execution Environment: " + IHGUtil.getEnvironmentType());
	 	log("Execution Browser: " + TestConfig.getBrowserType());
	 	StatementEventData testData = new StatementEventData();
	 	log("Step 1: load from external property file");
	 	LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadStatementEventDataFromProperty(testData);
		log("url is "+testData.Url);
		log("Step 2: Call Statement Post");
		StatementEventUtils sEventObj = new StatementEventUtils();
		sEventObj.generateViewEvent(driver,testData);
	 	
	 }
	 
	 @Test(enabled = true,groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testStatementEventForNewSelfPatient() throws Exception {
		log("Test Case: POST Statement and Get Statement Event for New Patient From Partner");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Create patient");
		StatementEventData testData = new StatementEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadStatementEventDataFromProperty(testData);
		log("Statement Event for Practice: "+testData.PracticeName);
		
		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		patient.setUrl(testData.Url);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(), patient.getPassword(), testDataPFL);
		
		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		
		StatementEventUtils sEventObj = new StatementEventUtils();
		
		homePage.clickOnLogout();
		
		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
		
		RestUtils.setupHttpGetRequest(testData.RestURLPIDC + "?since=" + since + ",0", testData.ResponsePath);
		Thread.sleep(2000);
		
		String responseXML = RestUtils.prepareCCD(testData.ResponsePath);
		String medfusionID = sEventObj.getMedfusionID(patient.getEmail(), responseXML);
		log("medfusionID "+medfusionID);
		log("Step 3: set patient details ");
		testData.UserName = patient.getUsername();
		testData.Password = patient.getPassword();
		testData.FirstName = patient.getFirstName();
		testData.LastName = patient.getLastName();
		testData.Email = patient.getEmail();
		testData.PatientID = "";
		testData.MFPatientID = medfusionID;
		testData.UserName = patient.getEmail();
		testData.StatementType="New";
		testData.since = since;
		log("Step 4: Call Statement Post");
		sEventObj.generateViewEvent(driver,testData);
	 }
	 
	@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBulkSecureMessage() throws Exception {
		log("Test Case: Bulk Secure Message");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Get Data from property file");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		BulkAdmin testData = new BulkAdmin();
		LoadPreTestDataObj.loadDataFromPropertyBulk(testData);
		Thread.sleep(3000);
		
		log("Step 2: Setup Oauth client");
		if (BulkMessagePayload.checkWithPrevioudBulkMessageID) {
			testData.PatientsUserNameArray[0] = testData.oUserName;
			testData.PatientsPasswordArray[0] = testData.oPassword;
			testData.PatientsIDArray[0] = testData.oPatientID;
			testData.PatientEmailArray[0] = testData.oEmailID;
			testData.AddAttachment = "no";
			testData.MaxPatients = "1";
			testData.NumberOfAttachments = "1";
		}
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
		
		log("Step 3: Fill Message data");
		String message = BulkMessagePayload.getBulkMessagePayload(testData);
		Thread.sleep(6000);
		
		//log("message xml : " + message);
		String messageID = BulkMessagePayload.messageId;
		log("Partner Message ID:" + messageID);
		
		log("Step 4: Do Message Post Request");
		log("ResponsePath:- " + testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, message, testData.ResponsePath);
		
		log("Step 5: Get processing status until it is completed");
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
		assertTrue(completed==true, "Message processing was not completed in time");
		log("testData.MaxPatients : "+testData.MaxPatients);
		
		for (int i = 1; i <= Integer.parseInt(testData.MaxPatients); i++) {
			// Loop through different patients email and login to view the message.
			log("Patient is - " + testData.PatientsUserNameArray[i - 1]);
			String subject = "New message from PI Automation rsdk Integrated";
			log("Step 6: Check secure message in patient Email inbox");

			String link = "";
			Mailinator mail = new Mailinator();
			String email = testData.PatientEmailArray[i-1]; 
			String messageLink = "Sign in to view this message";
			link = mail.getLinkFromEmail(email, subject, messageLink, 20);

			link = link.replace("login?redirectoptout=true", "login");
			log("Step 7: Login to Patient Portal");
			log("Link is " + link);
			JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, link);
			JalapenoHomePage homePage = loginPage.login(testData.PatientsUserNameArray[i - 1], testData.PatientsPasswordArray[i - 1]);
			
			Thread.sleep(5000);
			log("Detecting if Home Page is opened");
			assertTrue(homePage.isHomeButtonPresent(driver));
			log("Click on messages solution");
			JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
			assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");
			log("Step 8: Find message in Inbox");
			String messageIdentifier = BulkMessagePayload.subject;
			log("message subject " + messageIdentifier);
			log("Step 9: Log the message read time ");
			long epoch = System.currentTimeMillis() / 1000;
			log("Step 10: Validate message loads and is the right message");
			assertTrue(messagesPage.isMessageDisplayed(driver, messageIdentifier));
			log("Step 11: Check if attachment is present or not");
			String readdatetimestamp = RestUtils.readTime(epoch);
			log("Message Read Time:" + readdatetimestamp);
			if(testData.AddAttachment.equalsIgnoreCase("yes")) {
				String attachmentFileName = driver.findElement(By.xpath("// a [contains(text(),'bulk1.pdf')]")).getText();
				log("attachmentFileName "+attachmentFileName);
				assertFalse(attachmentFileName.equalsIgnoreCase("1.pdf"));
			}
			homePage.clickOnLogout();
		}
		if (testData.resendPreviousMessage.contains("yes") && BulkMessagePayload.messageIdCounter == 0) {

			BulkMessagePayload.checkWithPrevioudBulkMessageID = true;
			log("Step 12: Start Bulk mass admin for patient with  No attachment but previous Message ID");
			testBulkSecureMessage();
		}
	}
		 
	 @Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testDirectorySearch() throws Exception {
		DirectorySearchUtils DirectorySearchUtilsObj = new DirectorySearchUtils();
		DirectorySearchUtilsObj.directorySearchParam("all");
	 }
	 
	 @Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testDirectorySearchSingleValue() throws Exception {
		DirectorySearchUtils DirectorySearchUtilsObj = new DirectorySearchUtils();
		DirectorySearchUtilsObj.directorySearchParam("acceptance");
	 }
	 
	 @DataProvider(name = "attachmentType")
	 public Object[][] sendDirectAttachmentTypeUsed() {
		Object[][] obj = new Object[][] {{"xml"},{"pdf"},{"png"},{"none"},};
			return obj;
	 }
	 
	 @Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testSendDirectMessageXML() throws Exception {
		log("Test Case: Send Secure Direct Message with XML as attachment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Set Test Data from Property file");
		SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();
		SendDirectMessageUtilsObj.sendSecureDirectMessage(driver, "xml");
 	 }
	 
	 @Test(enabled = true,dataProvider = "attachmentType", groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testSendDirectMessageAll(String typeOfAttachmentUsed) throws Exception {
		log("Test Case: Send Secure Direct Message");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Set Test Data from Property file ");
		SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();
		SendDirectMessageUtilsObj.sendSecureDirectMessage(driver, typeOfAttachmentUsed);
	 }
	 
	 
	 @DataProvider(name = "channelVersion")
	 public Object[][] channelVersionPIDC() {
		Object[][] obj = new Object[][] {{"v1"},{"v2"}};
			return obj;
	 }
	 
	 @Test(enabled = true,dataProvider = "channelVersion", groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testOnDemandProvisionPIDC(String version) throws Exception {
     	log("Test Case: Test OnDemand Provision with PIDC");
	    log("Execution Environment: " + IHGUtil.getEnvironmentType());
   	    log("Execution Browser: " + TestConfig.getBrowserType());
	    log("Step 1: Set Test Data from Property file ");
	    Long timestamp = System.currentTimeMillis();
	 	AppointmentDataUtils aDUtils = new AppointmentDataUtils();
	 	LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
	 	PIDCInfo testData = new PIDCInfo();		
	 	LoadPreTestDataObj.loadDataFromProperty(testData,version,"2.0");
	 	
		log("Step 2: Create patient");
		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(), patient.getPassword(), testDataPFL);
		Long since = timestamp / 1000L - 60 * 24;
		Thread.sleep(5000);
		homePage.clickOnLogout();
	
		log("Step 3: Setup Oauth client");
		RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(), testData.getoAuthUsername(), testData.getoAuthPassword());
		
		log("Step 4: Get request to fetch Medfusion ID");
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		Thread.sleep(2000);
		String responseXML = RestUtils.prepareCCD(testData.getResponsePath());
		String medfusionID = aDUtils.getMedfusionID(patient.getEmail(), responseXML);
		log("medfusionID "+medfusionID);
		String practicePatientId = IHGUtil.createRandomNumericString();
		log("patientExternalID on Demand  "+practicePatientId);
		String patientPayload = RestUtils.preparePatient(testData.getPatientPath(), practicePatientId, patient.getFirstName(), patient.getLastName(), patient.getDOBDay(), patient.getDOBMonth(), patient.getDOBYear(), patient.getEmail(), patient.getZipCode(), medfusionID);
		if(version.equalsIgnoreCase("v2"))
		patientPayload = patientPayload.replaceAll("v1", "v2");
		
		Thread.sleep(600);		
		
		log("Step 5: Post Patient");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), patientPayload, testData.getResponsePath());
		
		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			// wait 60 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequestExceptOauth(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				
			}
		}
		Assert.assertTrue(completed, "Message processing was not completed in time");
		
		log("Step 6: Do a GET on PIDC Url to get registered patient for version "+version);
		log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 7: Find the patient and verify PracticePatientId/Medfusion Patient Id and Patient's demographics details");
		RestUtils.isPatientRegistered(testData.getResponsePath(), practicePatientId, patient.getFirstName(), patient.getLastName(), medfusionID);
	 }
	 
	 @Test(enabled = true,dataProvider = "channelVersion", groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	 public void testPIDCPatientDemographicsUpdate(String version) throws Exception {
		log("Test Case: PIDC Patient Update for Race, Ethnicity, Gender and Language all the values for Version "+version);
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		PIDCInfo testData = new PIDCInfo();
		Long timestamp = System.currentTimeMillis();
		LoadPreTestDataObj.loadDataFromProperty(testData,version,"2.0");		 
		 
		log("Step 2: LogIn to "+testData.getPortalURL());
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getUsername(), testData.getPassword());
		 
		log("Step 3: Click on myaccountLink on MyPatientPage");
		JalapenoAccountPage accountPageObject = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage accountProfilePageObject = accountPageObject.clickOnEditMyAccount();
		
		String dropValues[] = {"Race", "Ethnicity"};
		for (int k = 0; k < dropValues.length; k++) {
			log("Updating Values of '" + dropValues[k] + "' field");
			int count = accountProfilePageObject.countDropDownValue(dropValues[k].charAt(0));
			log("Total number of values in '" + dropValues[k] + "' field drop-down:" + count);
			for (int i = 0; i < count; i++) {
				String updatedValue = accountProfilePageObject.updateDropDownValue(i, dropValues[k].charAt(0));
				if (updatedValue.equalsIgnoreCase("Declined to Answer") && dropValues[k].equalsIgnoreCase(IntegrationConstants.RACE)) {
					updatedValue = "Unreported or refused to report";
				}
				if (updatedValue.equalsIgnoreCase("Declined to Answer") && dropValues[k].equalsIgnoreCase(IntegrationConstants.ETHINICITY)) {
					updatedValue = "Unreported";
				}
				
				log("Updated Value :" + updatedValue);
				Thread.sleep(40000);
				Long since = timestamp / 1000L - 60 * 24;
				if (!updatedValue.equalsIgnoreCase("Choose One")) {
					log("Do a GET on PIDC Url to fetch updated patient for "+version);
					RestUtils.setupHttpGetRequestExceptOauth(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
					Thread.sleep(800);
					RestUtils.validateNode(testData.getResponsePath(), updatedValue, dropValues[k].charAt(0), testData.getPracticeId_PIDC_20());
				}
			}
		}
		String[] gender = null;
		gender= new String[]{"Male","Decline to answer","Female"};
		Thread.sleep(300);
		for(int m=0;m<gender.length;m++) {
			log("gender to update  : "+gender[m]);
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,350)", "");
			Thread.sleep(5000);
			String updatedValue = accountProfilePageObject.updateGenderValue(m, gender[m].charAt(0));
			log("Updated Value :" + updatedValue);
			Thread.sleep(40000);
			Long since = timestamp / 1000L - 60 * 24;
			log("Do a GET on PIDC Url to fetch updated patient for "+version);
			RestUtils.setupHttpGetRequestExceptOauth(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			Thread.sleep(800);
			if(!gender[m].equalsIgnoreCase("Decline to answer") && version.equalsIgnoreCase("v2")) {
				 RestUtils.validateNode(testData.getResponsePath(), updatedValue, 'G', testData.getPracticeId_PIDC_20());
			}
		}
		Thread.sleep(8000);
		
		log("Step 4: Click on Preferences Tab");
		JalapenoMyAccountPreferencesPage myPreferencePage = accountProfilePageObject.goToPreferencesTab(driver);
		String[] languageType = testData.getPreferredLanguageType().split(",");
		Long since1 = timestamp / 1000L - 60 * 24;
		for(int v=0;v<languageType.length;v++) {
			myPreferencePage.setStatementLanguage(driver,languageType[v]);	
			if (languageType[v].equalsIgnoreCase("Declined to Answer") ) {
				languageType[v] = "Other";
			}
			Thread.sleep(40000);
			log("Do a GET on PIDC Url to fetch updated patient for "+version);
			RestUtils.setupHttpGetRequestExceptOauth(testData.getRestUrl() + "?since=" + since1 + ",0", testData.getResponsePath());
			Thread.sleep(800);
			RestUtils.validateNode(testData.getResponsePath(), languageType[v], 'L', testData.getPracticeId_PIDC_20());
		}
	 }
	 
	@Test(enabled = true,dataProvider = "channelVersion", groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientDemographicsUpdateWithSpecialCharacter(String version) throws Exception {
		log("Step 1: Test Case: Patient Update with special character data");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		PIDCInfo testData = new PIDCInfo(); 
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		Long timestamp = System.currentTimeMillis();
		LoadPreTestDataObj.loadDataFromProperty(testData,version,"2.0");
	
		log("Step 2: LogIn to ");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getUsername(), testData.getPassword());
		Thread.sleep(800);
		
		log("Step 3: Click on myaccountLink on MyPatientPage");
		JalapenoAccountPage accountPageObject = homePage.clickOnAccount();
	  	JalapenoMyAccountProfilePage accountProfilePageObject = accountPageObject.clickOnEditMyAccount();
	  	Thread.sleep(3000);
		List<String> patientData = new ArrayList<String>();
		String randomString = IHGUtil.createRandomNumericString();
		patientData.add("Fname" + "'" + randomString); 
		patientData.add("TestPatient" + "'" + randomString);  
		patientData.add("Line1" + "&" + randomString);  
		patientData.add('"' + randomString + '"'); 
		patientData.add("1" + IHGUtil.createRandomNumericString()); 
		patientData.add("1/1/1987"); 
		patientData.add("2");
		patientData.add("White"); 
		patientData.add("Hispanic or Latino"); 
		patientData.add(null); 
		patientData.add(null); 
		patientData.add("12345");
		
		log("Step 4: Update patient demographics datails with special charcters data");
		accountProfilePageObject.updateDemographics(patientData);
		Thread.sleep(40000);
		Long since = timestamp / 1000L - 60 * 24;
		
		log("Step 5: Invoke Get PIDC and verify patient details for version "+version);
		RestUtils.setupHttpGetRequestExceptoAuth(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		Thread.sleep(10000);
		RestUtils.checkPatientRegistered(testData.getResponsePath(), patientData);
	}
	
	@Test(enabled = true,dataProvider = "channelVersion", groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientRegistrationfromPractice(String version) throws Exception {
		log("Test Case: Patient Registration from Practice Portal" + version);
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		PIDCInfo testData = new PIDCInfo();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		Long timestamp = System.currentTimeMillis();
		
		log("Step 1: Load Data from Property file");
		LoadPreTestDataObj.loadDataFromProperty(testData,version,"2.0");
		
		log("Step 2: Login to Portal 2.0");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(), testData.getPracticePassword());

		log("Step 3: Click on Patient Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		log("Step 4: Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		log("Step 5: Enter all the details and click on Register");
		patientActivationPage.setFullDetails(testData.getEmail(),testData.getLastName(),testData.getHomePhoneNo(),testData.getAddress1(),testData.getAddress2(),testData.getCity(),testData.getState(),testData.getZipCode());
		String firstNameString = patientActivationPage.getFirstNameString();
		String patientIdString = patientActivationPage.getPatientIdString();
		String emailAddressString = patientActivationPage.getEmailAddressString();
		String firstName = "mf.patient"+IHGUtil.createRandomNumericString();;
		String unlocklink = patientActivationPage.getUnlockLink();
		
		log("Step 6: Logout of Practice Portal");
		practiceHome.logOut();
		String[] Date = testData.getBirthDay().split("/");
		
		log("Step 7: Moving to linkUrl to finish Create Patient procedure");
		PatientVerificationPage patientAccountActivationPage = new PatientVerificationPage(driver, unlocklink);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientAccountActivationPage.fillPatientInfoAndContinue(testData.getZipCode(), Date[1], Date[0], Date[2]);
		
		log("Step 8: Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage =
				accountDetailsPage.fillAccountDetailsAndContinue(firstName, testData.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(),testData.getHomePhoneNo());
	
		log("Step 9: Detecting if Home Page is opened");
		Thread.sleep(2000);
		Assert.assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));
		
		log("Step 10: Do a GET on PIDC Url to get registered patient for version "+version);
		Long since = timestamp / 1000L - 60 * 24;

		log("Step 11: Setup Oauth client");
		RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(), testData.getoAuthUsername(),
				testData.getoAuthPassword());
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		firstNameString = firstNameString.replaceAll("&amp;","&");
		List<String> patientData = new ArrayList<String>();
		patientData.add(firstNameString);
		patientData.add(testData.getLastName());
		patientData.add(testData.getHomePhoneNo());
		patientData.add(testData.getAddress1());
		patientData.add(testData.getAddress2());
		patientData.add(testData.getCity());
		patientData.add(testData.getZipCode());
		patientData.add(testData.getSSN());
		patientData.add("MALE");
		patientData.add(emailAddressString);

		log("Step 12: Find the patient and check if he is registered");
		RestUtils.isPatientRegistered(testData.getResponsePath(), patientIdString, patientData.get(0), patientData.get(1), null);

		log("Step 13: Verify patient Demographics Details");
		RestUtils.verifyPatientDetails(testData.getResponsePath(), patientIdString, patientData, null);
	}
	
	@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testUnseenMessageList() throws Exception {
		log("Test Case: Get Unseen Messages" );
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		long epoch = System.currentTimeMillis();
		String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new java.util.Date (epoch));
		log("currentDate "+currentDate);
		
		log("Step 1 : Set Test Data for UnseenMessageList");
		SendDirectMessage testData = new SendDirectMessage();
	 	LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
	 	LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);
	 	
	 	log("Step 2 : Set up Oauth Token");
	 	RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
	 	
	 	log("Step 3 : Mark all UnseenMesaages as READ");
	 	RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
	 	RestUtils.readUnseenMessages(testData.ResponsePath, testData.messageStatusUpdate);
	 	
	 	log("Step 4 : Check for 200 response when no UnseenMessages");
	 	RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
	 	int NoOfUnreadMessage = RestUtils.readUnseenMessages(testData.ResponsePath, testData.messageStatusUpdate);
	 	log("NoOfUnreadMessage : "+NoOfUnreadMessage);
	 	Assert.assertEquals(NoOfUnreadMessage, 0);
	 	
	 	log("Step 5 : Post New Secure Message ");
		SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();
		SendDirectMessageUtilsObj.postSecureMessage(driver,testData, "xml");
		
		P2PUnseenMessageList P2PUnseenMessageListObject = new P2PUnseenMessageList();
		
		P2PUnseenMessageListObject.verifyUnseenMessage(testData);
	}
	
	
	 @DataProvider(name = "p2pattachmentType")
	 public Object[][] sendDirectAttachment() {
		Object[][] obj = new Object[][] {{"none"},{"pdf"},{"xml"},};
			return obj;
	 }
	 
	@Test(enabled = true,dataProvider = "p2pattachmentType", groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testUnseenMessageListAll(String attachment) throws Exception {
		log("Test Case: Get Unseen Messages with attachment type " +attachment);
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("Step 1 : Set Test Data for UnseenMessageList");
		SendDirectMessage testData = new SendDirectMessage();
	 	LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
	 	LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);
	 	
	 	log("Step 2 : Set up Oauth Token");
	 	RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
	 	
	 	log("Step 3 : Mark all UnseenMesaages as READ");
	 	RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
	 	RestUtils.readUnseenMessages(testData.ResponsePath, testData.messageStatusUpdate);
	 	
	 	log("Step 4 : Check for 200 response when no UnseenMessages");
	 	RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
	 	int NoOfUnreadMessage = RestUtils.readUnseenMessages(testData.ResponsePath, testData.messageStatusUpdate);
	 	log("NoOfUnreadMessage : "+NoOfUnreadMessage);
	 	Assert.assertEquals(NoOfUnreadMessage, 0);
	 	P2PUnseenMessageList P2PUnseenMessageListObject = new P2PUnseenMessageList();
	 	log("Step 5 : Post New Secure Message ");
		
	 	SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();
	 	
		SendDirectMessageUtilsObj.postSecureMessage(driver,testData, attachment);
		P2PUnseenMessageListObject.verifyUnseenMessage(testData);

	}
	
	@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testUnseenMessageInvalidList() throws Exception {
		log("Step 1 : Set Test Data for UnseenMessageList");
		SendDirectMessage testData = new SendDirectMessage();
	 	LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
	 	LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);
	 	P2PUnseenMessageList P2PUnseenMessageListObject = new P2PUnseenMessageList();
	 	String invalidPracticeId = testData.messageHeaderURL+testData.invalidPracticeMessageHeaderURL+"/directmessageheaders/"+testData.ToEmalID;
	 	
	 	log("Step 2 : Set up Oauth Token");
	 	RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
	 	
	 	log("Step 3 : Get Unseen Message Header and Verify For Invalid PracticeID");
	 	int responseCode = RestUtils.setupHttpGetRequestInvalid(invalidPracticeId, testData.ResponsePath);
	 	Assert.assertEquals(responseCode, 400);
	 	P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath,"<h1>(.+?)</h1>",testData.invalidPracticeMessageHeaderURL);
	 	
	 	log("Step 4 : Get Unseen Message Header and Verify For Invalid Email ID");
	 	String invalidEmailID = testData.messageHeaderURL+testData.validPracticeID+"/directmessageheaders/"+testData.invalidEmailMessageHeaderURL;
	 	int responseCodeE = RestUtils.setupHttpGetRequestInvalid(invalidEmailID, testData.ResponsePath);
	 	Assert.assertEquals(responseCodeE, 400);
	 	P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath,"<ErrorResponse>(.+?)</ErrorResponse>",testData.invalidEmailMessageHeaderURL);

	 	log("Step 5 : Get Unseen Message Body and Verify For Invalid Message Uid");
	 	String getMessageBody = testData.messageHeaderURL+testData.validPracticeID+"/directmessage/"+testData.ToEmalID+"/message/"+testData.invalidUID;
	 	log("getMessageBody :"+getMessageBody);
	 	int responseCodeUid = RestUtils.setupHttpGetRequestInvalid(getMessageBody, testData.ResponsePath);
	 	Assert.assertEquals(responseCodeUid, 400);
	 	P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath,"<ErrorResponse>(.+?)</ErrorResponse>",testData.invalidUID);
	 	
	 	log("Step 6 : Get Unseen Message Body and Verify For Invalid Message Uid");
	 	String getMessageBodyIE = testData.messageHeaderURL+testData.validPracticeID+"/directmessage/"+testData.invalidEmailMessageHeaderURL+"/message/1";
	 	log("getMessageBodyInvalidEmail :"+getMessageBodyIE);
	 	int responseCodeIEmail = RestUtils.setupHttpGetRequestInvalid(getMessageBodyIE, testData.ResponsePath);
	 	Assert.assertEquals(responseCodeIEmail, 400);
	 	P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath,"<ErrorResponse>(.+?)</ErrorResponse>",testData.invalidEmailMessageHeaderURL);
	 	
	 	log("Step 7 : Get Unseen Message Status Update and Verify For Invalid Message Status");
	 	String invalidMessageUpdateStatusURL = testData.messageHeaderURL+testData.validPracticeID+"/directmessage/"+testData.ToEmalID+"/message/1/status/UNREAD";
	 		
	 	int invalidResponseStatus = RestUtils.setupHttpPostInvalidRequest(invalidMessageUpdateStatusURL,"", testData.ResponsePath);
	 	Assert.assertEquals(invalidResponseStatus, 400);
	 	P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath,"<ErrorResponse>(.+?)</ErrorResponse>","UNREAD");
	 	
	 	log("Step 8 : Get Unseen Message Status Update and Verify For Invalid Message Uid");
	 	String invalidMessageUIDURL = testData.messageHeaderURL+testData.validPracticeID+"/directmessage/"+testData.ToEmalID+"/message/"+testData.invalidUID+"/status/NEW";
	 	int invalidResponseUID = RestUtils.setupHttpPostInvalidRequest(invalidMessageUIDURL, "", testData.ResponsePath);
	 	Assert.assertEquals(invalidResponseUID, 400);
	 	P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath,"<ErrorResponse>(.+?)</ErrorResponse>",testData.invalidUID);

	}
}