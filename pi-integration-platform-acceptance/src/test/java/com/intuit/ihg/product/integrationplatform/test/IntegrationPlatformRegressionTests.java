package com.intuit.ihg.product.integrationplatform.test;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.product.integrationplatform.flows.iEHDCSendCCD;
import com.intuit.ihg.product.integrationplatform.flows.iPIDCSendPatientInvite;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCPayload;
import com.intuit.ihg.product.integrationplatform.utils.CCDPayload;
import com.intuit.ihg.product.integrationplatform.utils.EHDC;
import com.intuit.ihg.product.integrationplatform.utils.LoadPreTestData;
import com.intuit.ihg.product.integrationplatform.utils.MU2GetEventData;
import com.intuit.ihg.product.integrationplatform.utils.MU2Utils;
import com.intuit.ihg.product.integrationplatform.utils.PatientRegistrationUtils;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.patientportal2.utils.PortalConstants;
import com.intuit.ihg.product.integrationplatform.implementedExternals.SendCCD;
import com.intuit.ihg.product.integrationplatform.implementedExternals.SendPatientInvite;
//import com.intuit.ihg.product.support.utils.SupportConstants;

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
			
			//PatientRegistrationUtils patientRegistrationUtilsObject= new PatientRegistrationUtils();
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
			Object[][] obj = new Object[][] {{"1.0"},{"2.0"},
			};
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
}