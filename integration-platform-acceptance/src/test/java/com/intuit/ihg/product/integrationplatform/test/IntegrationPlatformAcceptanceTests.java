// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.test;

import static org.testng.Assert.assertNotNull;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.Appointment;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentTestData;
import com.intuit.ihg.product.integrationplatform.utils.EHDC;
import com.intuit.ihg.product.integrationplatform.utils.EHDCTestData;
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
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.portal.utils.PortalConstants;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step1;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step2;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPreferencesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.PayNow.JalapenoPayNowPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;
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
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;
import com.medfusion.product.patientportal2.pojo.StatementPreferenceType;

/**
 * @author bkrishnankutty
 * @Date 5/Aug/2013
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformAcceptanceTests extends BaseTestNGWebDriver {

	/*
	 * ////@Test (enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer =
	 * RetryAnalyzer.class) public void testGetAppointmentRequest() throws Exception
	 * {
	 * 
	 * log("Test Case: Appointment Request");
	 * 
	 * log("Execution Environment: " + IHGUtil.getEnvironmentType());
	 * log("Execution Browser: " + TestConfig.getBrowserType());
	 * 
	 * log("Step 1: Get Data from Excel"); Appointment aptData = new Appointment();
	 * AppointmentTestData testData = new AppointmentTestData(aptData); Long
	 * timestamp = System.currentTimeMillis();
	 * 
	 * log("Url: " + testData.getUrl()); log("User Name: " +
	 * testData.getUserName()); log("Password: " + testData.getPassword());
	 * log("Rest Url: " + testData.getRestUrl()); log("Response Path: " +
	 * testData.getResponsePath()); log("From: " + testData.getFrom());
	 * log("SecureMessagePath: " + testData.getSecureMessagePath());
	 * log("OAuthProperty: " + testData.getOAuthProperty()); log("OAuthKeyStore: " +
	 * testData.getOAuthKeyStore()); log("OAuthAppToken: " +
	 * testData.getOAuthAppToken()); log("OAuthUsername: " +
	 * testData.getOAuthUsername()); log("OAuthPassword: " +
	 * testData.getOAuthPassword());
	 * 
	 * log("Step 2: LogIn"); PortalLoginPage loginPage = new PortalLoginPage(driver,
	 * testData.getUrl()); assertTrue(loginPage.isLoginPageLoaded(),
	 * "There was an error loading the login page"); MyPatientPage myPatientPage =
	 * loginPage.login(testData.getUserName(), testData.getPassword());
	 * 
	 * log("Step 3: Click on Appointment Button on My Patient Page");
	 * AppointmentRequestStep1Page apptRequestStep1 =
	 * myPatientPage.clickAppointmentRequestTab();
	 * 
	 * log("Step 4: Complete Appointment Request Step1 Page  ");
	 * AppointmentRequestStep2Page apptRequestStep2 =
	 * apptRequestStep1.requestAppointment
	 * (null,null,testData.getPreferredDoctor(),null);
	 * 
	 * log("Step 5: Complete Appointment Request Step2 Page  ");
	 * AppointmentRequestStep3Page apptRequestStep3 =
	 * apptRequestStep2.fillInForm(PortalConstants.PreferredTimeFrame,
	 * PortalConstants.PreferredDay, PortalConstants.ChoosePreferredTime,
	 * PortalConstants.ApptReason, PortalConstants.WhichIsMoreImportant,
	 * testData.getPhoneNumber());
	 * 
	 * log("Getting Appointment reason "); long
	 * time=apptRequestStep2.getCreatedTs(); String
	 * reason=PortalConstants.ApptReason.toString()+" "+String.valueOf(time);
	 * 
	 * log("Step 6: Complete Appointment Request Step3 Page  ");
	 * AppointmentRequestStep4Page apptRequestStep4 =
	 * apptRequestStep3.clickSubmit();
	 * 
	 * log("Step 7: Complete Appointment Request Step4 Page  "); myPatientPage =
	 * apptRequestStep4.clickBackToMyPatientPage();
	 * 
	 * log("Step 8: Setup Oauth client");
	 * 
	 * //OAuthPropertyManager.init(testData.getOAuthProperty());
	 * 
	 * log("Step 9: Get Appointment Rest call");
	 * 
	 * //get only messages from last day in epoch time to avoid transferring lot of
	 * data Long since = timestamp / 1000L - 60 * 60 * 24;
	 * 
	 * log("Getting messages since timestamp: " + since);
	 * 
	 * //do the call and save xml, ",0" is there because of the since attribute
	 * format RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" +
	 * since + ",0", testData.getResponsePath());
	 * 
	 * log("Step 10: Checking validity of the response xml");
	 * RestUtils.isReasonResponseXMLValid(testData.getResponsePath(), reason); }
	 */

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCAskQuestionPaid(String version) throws Exception {
		if (version.contains("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: AMDC Ask Question to your Staff");

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
		log("RestV3Url: " + testData.getRestV3Url());

		log("Step 2: LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click Ask A Staff");
		JalapenoAskAStaffPage askStaff1 = homePage.clickOnAskAStaff(driver);

		Thread.sleep(8000);
		log("Step 4: fill and complete the of Ask A Staff");
		boolean askStaff2 = askStaff1.fillAndSubmitAskAStaff(driver);

		log("Step 6: Validate entry is on Ask A Staff History page");
		homePage.clickOnAskAStaff(driver);
		boolean aasHistory = askStaff1.checkHistory(driver);
		Thread.sleep(7000);
		verifyTrue(aasHistory);

		log("Step 7: Logout of Patient Portal");
		homePage.clickOnLogout();

		log("Step 8: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		// OAuthPropertyManager.init(testData.getOAuthProperty());

		log("Step 9: Get AMDC Rest call");
		// get only messages from last day in epoch time to avoid transferring
		// lot of data
		Long since = askStaff1.getCreatedTimeStamp() / 1000L;

		log("Getting messages since timestamp: " + since);

		// do the call and save xml, ",0" is there because of the since
		// attribute format

		if (version.contains("v1")) {
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		} else if (version.contains("v3")) {
			RestUtils.setupHttpGetRequest(testData.getRestV3Url() + "?since=" + since + ",0",
					testData.getResponsePath());
		}

		log("Step 10: Checking validity of the response xml");
		RestUtils.isQuestionResponseXMLValid(testData.getResponsePath(), askStaff1.getCreatedTimeStamp());
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientUpdate(String version) throws Exception {
		log("Test Case: PIDC Patient Update");
		PIDCTestData testData = loadDataFromExcel(version);

		Long timestamp = System.currentTimeMillis();
		log("Step 2: LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click on myaccountLink on MyPatientPage");
		JalapenoMyAccountProfilePage myAccountPage = homePage.goToAccountPage();

		log("Step 4: Create random  addresses to update");
		Random random = new Random();
		String firstLine = "Street " + random.nextInt(1000);
		String secondLine = "Street " + random.nextInt(1000);

		log("Address 1: " + firstLine + "\nAddress 2: " + secondLine);
		Thread.sleep(9000);
		log("Step 5: Modify address line 1 and 2 on MyAccountPage");
		myAccountPage.modifyAndSubmitAddressLines(firstLine, secondLine);

		log("Step 6: Logout from Patient portal");
		myAccountPage.clickOnLogout();

		log("Step 7: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		log("Step 8: Wait 60 seconds, so that patient-outbound can be processed");
		Thread.sleep(60000);

		log("Step 9: Do a GET on PIDC");
		// this Step assumes that the updated patient is the patient from first
		// ten registered patients, so we can save traffic
		// if max argument is ommited patient should be in first 100 patients
		Long since = timestamp / 1000L - 60 * 24;
		if (version.contains("v1")) {
			log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestv1Url() + "?since=" + since + ",0",
					testData.getResponsePath());

			log("Step 10: Check changes of address lines");
			RestUtils.isPatientUpdated(testData.getResponsePath(), testData.getUserName(), firstLine, secondLine);
		}
		if (version.contains("v2")) {
			log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestv2Url() + "?since=" + since + ",0",
					testData.getResponsePath());

			log("Step 10: Check changes of address lines");
			RestUtils.isPatientUpdated(testData.getResponsePath(), testData.getUserName(), firstLine, secondLine);
		}
		if (version.contains("v3")) {
			log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestv3Url() + "?since=" + since + ",0",
					testData.getResponsePath());

			log("Step 10: Check changes of address lines");
			RestUtils.isPatientUpdated(testData.getResponsePath(), testData.getUserName(), firstLine, secondLine);
		}
	}

	private PIDCTestData loadDataFromExcel(String version) throws Exception {
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

		if (version.contains("v1")) {
			log("URL: " + testData.getRestv1Url());
		} else if (version.contains("v2")) {
			log("URL: " + testData.getRestv2Url());
		} else {
			log("URL: " + testData.getRestv3Url());
		}

		return testData;
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ERxPrescription(String version) throws Exception {
		if (version.contains("v2"))
			throw new SkipException("Test skipped as version is:" + version);

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
		log("RestV3Url: " + testData.getRestV3Url());
		log("PrescriptionPathV3: " + testData.getPrescriptionPathV3());

		log("Step 2: LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());
		Thread.sleep(9000);

		log("Step 3: Click on PrescriptionRenewal Link ");
		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(15000);
		prescriptionsPage.clickContinueButton(driver);
		Thread.sleep(15000);

		log("Getting Provider Details");
		String practiceLocation = prescriptionsPage.getPracticeLocation(driver);
		log("Practce Location: " + practiceLocation);
		String practiceProvider = prescriptionsPage.getPracticeProvider(driver);
		log("Practice Provider Name :" + practiceProvider);

		log("Getting Medication Name ");
		long time = prescriptionsPage.getCreatedTs();
		String medicationName = PortalConstants.MedicationName.toString() + String.valueOf(time);
		log("Medication Name :" + medicationName);

		String rxSMSubject = PortalConstants.RxRenewal_Subject_Tag.toString() + String.valueOf(time);
		log("Perscription Subject :" + rxSMSubject);

		String rxSMBody = IntegrationConstants.QUESTION_MESSAGE.toString() + "" + String.valueOf(time);
		log("Perscription Reply :" + rxSMBody);

		prescriptionsPage.fillThePrescriptionforExisitngUser();

		log("Step 6 : Verify RxRenewal Confirmation Message");
		IHGUtil.waitForElement(driver, 5, prescriptionsPage.renewalConfirmationmessage);
		assertEquals(prescriptionsPage.renewalConfirmationmessage.getText(), PortalConstants.RenewalConfirmation);
		prescriptionsPage.homeButton.click();
		driver.switchTo().defaultContent();
		Thread.sleep(5000);

		log("Step 7: Logout of Patient Portal");
		homePage.clickOnLogout();

		log("Step 8: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());
		String prescriptionId = "";
		String sigCodes = "";
		if (version.equals("v1")) {
			log("For V1 endpoint");
			log("Step 9: Get Prescription Rest call");
			// get only messages from last hour in epoch time to avoid transferring
			// lot of data
			Long since = timestamp / 1000L - 60 * 24;

			log("Getting messages since timestamp :" + since);

			// do the call and save xml, ",0" is there because of the since
			// attribute format
			Thread.sleep(4000);
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			log("Step 10: Checking validity of the response xml");

			RestUtils.isMedicationDetailsResponseXMLValid(testData.getResponsePath(), medicationName);

			String postXML = RestUtils.findValueOfMedicationNode(testData.getResponsePath(), "Medication",
					medicationName, rxSMSubject, rxSMBody, testData.getPrescriptionPath());

			String SigCodeAbbreviation = RestUtils.SigCodeAbbreviation;
			String SigCodeMeaning = RestUtils.SigCodeMeaning;

			sigCodes = SigCodeAbbreviation + " - " + SigCodeMeaning;

			log("SigCodeAbbreviation :" + SigCodeAbbreviation);
			log("SigCodeMeaning :" + SigCodeMeaning);

			log("Step 11: Do Message Post Request" + postXML);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), postXML,
					testData.getResponsePath());

			log("Step 12: Get processing status until it is completed");
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
		} else {
			log("For V3 endpoint");
			log("Step 9: Get Prescription Rest call");
			// get only messages from last hour in epoch time to avoid transferring
			// lot of data
			Long since = timestamp / 1000L - 60 * 24;

			log("Getting messages since timestamp :" + since);

			// do the call and save xml, ",0" is there because of the since
			// attribute format
			Thread.sleep(4000);
			RestUtils.setupHttpGetRequest(testData.getRestV3Url() + "?since=" + since + ",0",
					testData.getResponsePath());
			log("Step 10: Checking validity of the response xml");

			RestUtils.isMedicationDetailsResponseXMLValid(testData.getResponsePath(), medicationName);

			String postXML = RestUtils.findValueOfMedicationNode(testData.getResponsePath(), "Medication",
					medicationName, rxSMSubject, rxSMBody, testData.getPrescriptionPathV3());

			String SigCodeAbbreviation = RestUtils.SigCodeAbbreviation;
			String SigCodeMeaning = RestUtils.SigCodeMeaning;

			sigCodes = SigCodeAbbreviation + " - " + SigCodeMeaning;

			log("SigCodeAbbreviation :" + SigCodeAbbreviation);
			log("SigCodeMeaning :" + SigCodeMeaning);

			log("Getting Prescription ID");
			prescriptionId = RestUtils.getPrescriptionID(testData.getResponsePath());
			log("Prescription ID: " + prescriptionId);

			log("Step 11: Do Message Post Request" + postXML);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestV3Url(), postXML,
					testData.getResponsePath());

			log("Step 12: Get processing status until it is completed");
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
		}
        // Patient portal validation
		log("Step 13: Check secure message in patient mailinator inbox");
		Mailinator mail = new Mailinator();
		String subject = "New message from PI Automation rsdk Integrated";
		String messageLink = "Sign in to view this message";
		String emailMessageLink = mail.getLinkFromEmail(testData.getUserName(), subject, messageLink, 20);
		log("Email message link " + emailMessageLink);

		log("Step 14: Login to Patient Portal");
		JalapenoLoginPage ploginPage = new JalapenoLoginPage(driver, emailMessageLink);
		JalapenoHomePage phomePage = ploginPage.login(testData.getUserName(), testData.getPassword());
		Thread.sleep(9000);

		log("Click on msessage box");
		JalapenoMessagesPage inboxPage = phomePage.clickOnMenuMessages();
		Thread.sleep(9000);

		log("Step 15: Find message in Inbox");
		boolean msg = inboxPage.isMessageDisplayed(driver, rxSMSubject);

		log("Step 16: Verify Provider details on patient portal");
		inboxPage.checkProviderDetails(practiceProvider, practiceLocation);

		log("Step 17: Logout of Patient Portal");
		homePage.clickOnLogout();

		log("Step 18: Login to Practice Portal");
		Thread.sleep(6000);
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(),
				testData.getPracticePassword());

		log("Step 19: Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome.clickonRxRenewal();
		Thread.sleep(10000);

		log("Step 20: Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday(2);
		Thread.sleep(10000);

		log("Step 21: Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		log("Step 22: Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.checkMedicationDetails(medicationName, sigCodes);

		log("Step 23: Logout of Practice Portal");
		practiceHome.logOut();

		if (version.equals("v3")) {
			log("Step 24: Get PrescriptionHeader call");
			// get only messages from last hour in epoch time to avoid transferring
			// lot of data
			Long since = timestamp / 1000L - 60 * 24;
			log("Getting messages since timestamp :" + since);
			log("For V3 endpoint");
			String getStatusV3Url = testData.getRestV3Url().replaceAll("prescriptions", "prescriptionsHeaders");
			log("getStatusUrl  :" + getStatusV3Url);
			Thread.sleep(4000);
			RestUtils.setupHttpGetRequest(getStatusV3Url + "?since=" + since + ",0", testData.getResponsePath());
			log("Step 25: Verify PrescriptionHeader ID");
			String PrescriptionHeaderID = RestUtils.getPrescriptionHeaderID(testData.getResponsePath());
			log("Prescription ID: " + prescriptionId + "Prescription Header ID: " + PrescriptionHeaderID);
			assertTrue(prescriptionId.contains(PrescriptionHeaderID), "Expected Prescription ID Body is[" + prescriptionId+"]" 
			          + "but actual Prescription ID was [" + PrescriptionHeaderID + "]");
		}
	}

	@Test(enabled = true,dataProvider = "channelVersion", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testOnlineBillPayment(String version) throws Exception {
		if (version.contains("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: testOnlineBillPayment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		Payment paymentData = new Payment();
		PaymentTestData testcasesData = new PaymentTestData(paymentData);
		Long timestamp = System.currentTimeMillis();
		String accountNumber = IHGUtil.createRandomNumericString();
		String amount = "100.00";
		String CCType = "Visa";
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Visa, name);
		String CCLastDig = creditCard.getLastFourDigits();
		String reply_Subject = "Test Message " + IHGUtil.createRandomNumericString();
		String messageThreadID;
		String lastTimestamp;
		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("Step 2: LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testcasesData.getUrl());

		JalapenoHomePage homePage = loginPage.login(testcasesData.getUserName(), testcasesData.getPassword());
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		logStep("Remove all cards because Selenium can't see AddNewCard button");
		payBillsPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(payBillsPage.isAnyCardPresent());
		assertTrue(payBillsPage.areBasicPageElementsPresent());

		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, accountNumber,
				creditCard);
		assertTrue(confirmationPage.areBasicPageElementsPresent());
		logStep("Verifying credit card ending");
		assertTrue(confirmationPage.getCreditCardEnding().equals(creditCard.getLastFourDigits()));

		homePage = confirmationPage.commentAndSubmitPayment("Testing payment from number: " + accountNumber);
		log("Step 6: fetch confirmation number ");
		String confirmationNumber = payBillsPage.readConfirmationNumber();
		log("Confirmation Number is: " + confirmationNumber);
		assertTrue(homePage.wasPayBillsSuccessfull());
		homePage.clickOnLogout();

		log("Step 8: Setup Oauth client 2.O");
		RestUtils.oauthSetup(testcasesData.getOAuthKeyStore(), testcasesData.getOAuthProperty(), testcasesData.getOAuthAppToken(), testcasesData.getOAuthUsername(),
				testcasesData.getOAuthPassword());
		if(version.equals("v1")) {
		log("Step 9: Getting messages since timestamp: " + timestamp);
		lastTimestamp = RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "?since=" + timestamp, testcasesData.getResponsePath());

		log("Step 10: Verify payment details");
		RestUtils.isPaymentAppeared(testcasesData.getResponsePath(), accountNumber, amount, CCLastDig, CCType,
				IntegrationConstants.SUBMITTED, confirmationNumber);

		messageThreadID = RestUtils.paymentID;
		log("Payment ID :" + messageThreadID);

		String message = RestUtils.prepareSecureMessage(testcasesData.getcommunicationXML(), testcasesData.getFrom(), testcasesData.getUserName(), reply_Subject, 
				messageThreadID);
		log("Payload to beposted for AM: " + message);
		log("Step 11: Do Message Post AMDC Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getCommRestUrl(), message,
				testcasesData.getResponsePath());

		log("Step 12: Get processing status until it is completed");
		boolean completed = false;
		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}
		verifyTrue(completed, "Message processing was not completed in time");
		}
		else {
			log("Step 9: Getting messages since timestamp: " + timestamp);
			lastTimestamp = RestUtils.setupHttpGetRequest(testcasesData.getRestV3Url() + "?since=" + timestamp, testcasesData.getResponsePath());

			log("Step 10: Verify payment details");
			RestUtils.isPaymentAppeared(testcasesData.getResponsePath(), accountNumber, amount, CCLastDig, CCType, IntegrationConstants.SUBMITTED, confirmationNumber);

			messageThreadID = RestUtils.paymentID;
			log("Payment ID :" + messageThreadID);

			String message = RestUtils.prepareSecureMessage(testcasesData.getcommunicationXML(), testcasesData.getFrom(), testcasesData.getUserName(), reply_Subject, 
					messageThreadID);
			log("Payload to beposted for AM: " + message);
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
		}
		log("Step 13: Check secure message in patient mailinator inbox");
		Mailinator mail = new Mailinator();
		String subject = "New message from PI Automation rsdk Integrated";
		String messageLink = "Sign in to view this message";
		String emailMessageLink = mail.getLinkFromEmail(testcasesData.getUserName(), subject, messageLink, 20);

		// patient Portal validation
		log("Step 14: Login to Patient Portal");

		log("Step 7: Login to Patient Portal");
		log("Link is " + emailMessageLink);
		JalapenoLoginPage ploginPage = new JalapenoLoginPage(driver, emailMessageLink);
		Thread.sleep(9000);
		JalapenoHomePage phomePage = loginPage.login(testcasesData.getUserName(), testcasesData.getPassword());
		assertTrue(phomePage.isHomeButtonPresent(driver));

		Thread.sleep(9000);
		JalapenoMessagesPage messagesPage = phomePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		Long since = timestamp / 1000L - 60 * 24;

		log("Step 15: Validate message loads and is the right message");
		boolean msg = messagesPage.isMessageDisplayed(driver, reply_Subject);

		Thread.sleep(60000);
		log("Step 16: Reply to the message");
		messagesPage.replyToMessage(driver);

		log("Step 17: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		log("Step 18: Do a GET AMDC and verify patient reply in Get AMDC response");
		RestUtils.setupHttpGetRequest(testcasesData.getCommRestUrl() + "?since=" + since + ",0",
				testcasesData.getResponsePath());

		log("Step 19: Validate message reply");
		RestUtils.isReplyPresent(testcasesData.getResponsePath(), reply_Subject);

		log("Logout from Patient Portal");
		homePage.clickOnLogout();

		if(version.equals("v1")) {
		String postPayload = RestUtils.preparePayment(testcasesData.getPaymentPath(), messageThreadID, null, IntegrationConstants.BILLPAYMENT);

		log("Post Payload is:  " + postPayload);
		log("Step 20: Do a Post and get the message");
		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestUrl(), postPayload, testcasesData.getResponsePath());
		boolean completed = false;

		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}
		verifyTrue(completed, "Message processing was not completed in time");
		} 
		else {
			String postPayload = RestUtils.preparePayment(testcasesData.getPaymentPathV3(), messageThreadID, null, IntegrationConstants.BILLPAYMENT);

			log("Post Payload is:  " + postPayload);
			log("Step 20: Do a Post and get the message");
			String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestV3Url(), postPayload, testcasesData.getResponsePath());
			boolean completed = false;
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
				completed = true;
			}
			verifyTrue(completed, "Message processing was not completed in time");
		}
		Thread.sleep(5000);
		log("Verify Payment status in Practice Portal");
		log("Step 21: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testcasesData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testcasesData.getPracticeUserName(),
				testcasesData.getPracticePassword());
		Thread.sleep(6000);
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

		if(version.equals("v1")) {
		RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "?since=" + lastTimestamp, testcasesData.getResponsePath());
		} 
		else {
		RestUtils.setupHttpGetRequest(testcasesData.getRestV3Url() + "?since=" + lastTimestamp, testcasesData.getResponsePath());
		}
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPayNow(String version) throws Exception {
		if (version.contains("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: testPayNow - No login payment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		String lastTimestamp;
		PayNow payNowData = new PayNow();
		PayNowTestData testcasesData = new PayNowTestData(payNowData);
		Long timestamp = System.currentTimeMillis();

		logStep("Step 1: Open login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testcasesData.getUrl());

		logStep("Step 2: Click on Pay a bill (without logging in");
		JalapenoPayNowPage pNoLoginPaymentPage = loginPage.clickPayNowButton();
		log("Step 3: Verify payment OK");
		assertTrue(pNoLoginPaymentPage.validateNoLoginPaymentPage(testcasesData.getFirstName(),
				testcasesData.getLastName(), testcasesData.getZip(), testcasesData.getEmail()));
		Thread.sleep(90000);

		log("Step 4: Verify account set to N/A");
		verifyTrue(driver.getPageSource().contains("Account N/A."));

		log("Step 5: Verify the prize format.");
		verifyTrue(driver.getPageSource().contains("$" + pNoLoginPaymentPage.GetAmountPrize() + ".00"));
		Thread.sleep(60000);

		log("Step 6: fetch confirmation number ");
		String confirmationNumber = pNoLoginPaymentPage.readConfirmationNumber();

		log("Step 7: Setup Oauth client 2.O");
		RestUtils.oauthSetup(testcasesData.getOAuthKeyStore(), testcasesData.getOAuthProperty(),
				testcasesData.getOAuthAppToken(), testcasesData.getOAuthUsername(), testcasesData.getOAuthPassword());

		log("Step 8: Getting messages since timestamp: " + timestamp);

		if(version.equals("v1")) {
		lastTimestamp = 
				RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "=payNowpayment" + "&since=" + timestamp, testcasesData.getResponsePath());
		} 
		else {
		lastTimestamp = 
		        RestUtils.setupHttpGetRequest(testcasesData.getRestV3Url() + "=payNowpayment" + "&since=" + timestamp, testcasesData.getResponsePath());
		}

		log("Step 9: Verify payment details");
		RestUtils.verifyPayment(testcasesData.getResponsePath(), pNoLoginPaymentPage.GetAmountPrize() + ".00",
				IntegrationConstants.SUBMITTED, IntegrationConstants.PAYNOWPAYMENT, confirmationNumber);

		String paymentID = RestUtils.paymentID;
		log("Payment ID :" + paymentID);

		String postPayload;
		log("Step 10: Do a Post and get the message");
		if(version.equals("v1")) {
		postPayload =
				    RestUtils.preparePayment(testcasesData.getPaymentPath(), paymentID, pNoLoginPaymentPage.GetAmountPrize() + ".00", IntegrationConstants.PAYNOWPAYMENT);
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
		} 
		else {
			postPayload = 
				    RestUtils.preparePayment(testcasesData.getPaymentPathV3(), paymentID, pNoLoginPaymentPage.GetAmountPrize() + ".00", IntegrationConstants.PAYNOWPAYMENT);
		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestV3Url() + "=payNowpayment", postPayload, testcasesData.getResponsePath());
		log("Step 11: Get processing status until it is completed");
		boolean completed = false;
		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}
		verifyTrue(completed, "Message processing was not completed in time");
		}
		Thread.sleep(5000);
		log("Verify Payment status in Practice Portal");
		log("Step 12: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testcasesData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testcasesData.getPracticeUserName(),
				testcasesData.getPracticePassword());

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

		if(version.equals("v1")) {
		    RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "=payNowpayment" + "&since=" + lastTimestamp, testcasesData.getResponsePath());
		} 
		else {
			RestUtils.setupHttpGetRequest(testcasesData.getRestV3Url() + "=payNowpayment" + "&since=" + lastTimestamp, testcasesData.getResponsePath());
		}

	}

	@Test(enabled = true,dataProvider = "channelVersion", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testVirtualCardSwiper(String version) throws Exception {
		if (version.contains("v2"))
			throw new SkipException("Test skipped as version is:" + version);

		log("Test Case: Virtual Card Swiper");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		PayNow payNowData = new PayNow();
		PayNowTestData testcasesData = new PayNowTestData(payNowData);
		Long timestamp = System.currentTimeMillis();
		log("timestamp : "+timestamp);
		String lastTimestamp;
		log("Step 1: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testcasesData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testcasesData.getPracticeUserName(),
				testcasesData.getPracticePassword());
		Thread.sleep(9000);
		log("Step 2: Click on Virtual Card Swiper Tab ");
		VirtualCardSwiperPage vcsPage = practiceHome.clickVirtualCardSwiperTab();
		String Amount = IHGUtil.createRandomNumericString().substring(1, 4);
		log("Step 3: Click on Charge Card ");
		vcsPage.addCreditCardInfo("Test", "5105105105105100", "Visa", "12", "2022", Amount, "110", "12345", "Test0001",
				"Test Patient", "comment");

		log("Step 4: Verify whether the payment is completed successfully.");
		verifyEquals(Boolean.valueOf(vcsPage.getPaymentCompletedSuccessMsg().contains("Payment completed")),
				Boolean.valueOf(true), "The payment is completed properly.");

		log("Step 5: Logout of Practice Portal ");
		practiceHome.logOut();

		log("Step 6: Setup Oauth client 2.O");
		RestUtils.oauthSetup(testcasesData.getOAuthKeyStore(), testcasesData.getOAuthProperty(),
				testcasesData.getOAuthAppToken(), testcasesData.getOAuthUsername(), testcasesData.getOAuthPassword());

		// wait 30 seconds so the message can be processed
		Thread.sleep(180000);
		if(version.equals("v1")) {
		log("Step 7: Getting messages since timestamp: " + timestamp);

		lastTimestamp = RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "=vcsPayment" + "&since=" + timestamp, testcasesData.getResponsePath());

		log("Step 8: Verify payment details");
		RestUtils.verifyPayment(testcasesData.getResponsePath(), Amount + ".00", IntegrationConstants.SUBMITTED,
				IntegrationConstants.VCSPAYMENT, null);

		String paymentID = RestUtils.paymentID;
		log("Payment ID :" + paymentID);

		String postPayload = RestUtils.preparePayment(testcasesData.getPaymentPath(), paymentID, Amount + ".00",
				IntegrationConstants.VCSPAYMENT);

		log("Step 9: Do a Post and get the message");
		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestUrl() + "=vcsPayment", postPayload,
				testcasesData.getResponsePath());

		log("Step 10: Get processing status until it is completed");
		boolean completed = false;
		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}
		verifyTrue(completed, "Message processing was not completed in time");
		} 
		else  {
		log("Step 7: Getting messages since timestamp: " + timestamp);
		lastTimestamp = RestUtils.setupHttpGetRequest(testcasesData.getRestV3Url() + "=vcsPayment" + "&since=" + timestamp, testcasesData.getResponsePath());

		log("Step 8: Verify payment details");
		RestUtils.verifyPayment(testcasesData.getResponsePath(), Amount + ".00", IntegrationConstants.SUBMITTED, IntegrationConstants.VCSPAYMENT, null);

		String paymentID = RestUtils.paymentID;
		log("Payment ID :" + paymentID);

		String postPayload = RestUtils.preparePayment(testcasesData.getPaymentPathV3(), paymentID, Amount + ".00", IntegrationConstants.VCSPAYMENT);

		log("Step 9: Do a Post and get the message");

		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestV3Url() + "=vcsPayment", postPayload, testcasesData.getResponsePath());

		log("Step 10: Get processing status until it is completed");
		boolean completed = false;
		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
		completed = true;
		}
		verifyTrue(completed, "Message processing was not completed in time");
		}
		Thread.sleep(5000);
		log("Step 11: Login to Practice Portal to search record");
		practiceLogin = new PracticeLoginPage(driver, testcasesData.getPracticeURL());
		practiceHome = practiceLogin.login(testcasesData.getPracticeUserName(), testcasesData.getPracticePassword());
		Thread.sleep(9000);
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

		if(version.equals("v1")) {
			RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "=vcsPayment" + "&since=" + lastTimestamp, testcasesData.getResponsePath());
		}
		else {
			RestUtils.setupHttpGetRequest(testcasesData.getRestV3Url() + "=vcsPayment" + "&since=" + lastTimestamp, testcasesData.getResponsePath());
		}

	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCAskQuestionUnpaid(String version) throws Exception {
		if (version.contains("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: AMDC Ask Question to your Doc");

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
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click Ask Ur Doc");
		JalapenoAskAStaffPage askStaff1 = homePage.clickOnAskADoc(driver);

		Thread.sleep(8000);
		log("Step 4: fill and complete the of Ask A Staff");
		boolean askStaff2 = askStaff1.fillAndSubmitAskyourDocUnpaid(driver);

		log("Step 6: Validate entry is on Ask A Staff History page");
		homePage.clickOnAskADoc(driver);
		boolean aasHistory = askStaff1.checkHistory(driver);
		Thread.sleep(7000);
		// verifyTrue(aasHistory);

		log("Step 7: Logout of Patient Portal");
		homePage.clickOnLogout();

		log("Step 8: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		// OAuthPropertyManager.init(testData.getOAuthProperty());

		log("Step 9: Get AMDC Rest call");
		// get only messages from last day in epoch time to avoid transferring
		// lot of data
		Long since = askStaff1.getCreatedTimeStamp() / 1000L;

		log("Getting messages since timestamp: " + since);

		// do the call and save xml, ",0" is there because of the since

		if (version.contains("v1")) {
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		} else if (version.contains("v3")) {
			RestUtils.setupHttpGetRequest(testData.getRestV3Url() + "?since=" + since + ",0",
					testData.getResponsePath());
		}

		log("Step 10: Checking validity of the response xml");
		RestUtils.isQuestionResponseXMLValid(testData.getResponsePath(), askStaff1.getCreatedTimeStamp());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		log("Step 3: Fill Message data");
		long timestamp = System.currentTimeMillis();
		String Subject = "Test " + timestamp;
		String message = RestUtils.prepareSecureMessage(testData.getSecureMessagePath(),
				testData.getIntegrationPracticeID(), testData.getUserName(), "Test " + timestamp, null);

		String messageID = RestUtils.newMessageID();
		log("Partner Message ID:" + messageID);

		log("Payload posted is ___________" + message);
		log("Step 4: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), message,
				testData.getResponsePath());

		log("Step 5: Get processing status until it is completed");
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

		log("Step 6: Check secure message in patient gmail inbox");
		Mailinator mail = new Mailinator();
		String subject = "New message from IHGQA Automation Integrated Oauth 2.0";
		String messageLink = "Sign in to view this message";
		String emailMessageLink = mail.getLinkFromEmail(testData.getUserName(), subject, messageLink, 20);

		log("Step 7: Login to Patient Portal");
		log("Link is " + emailMessageLink);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, emailMessageLink);
		Thread.sleep(9000);
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());
		assertTrue(homePage.isHomeButtonPresent(driver));

		Thread.sleep(9000);
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		Long since = timestamp / 1000L - 60 * 24;

		log("Step 8: Validate message loads and is the right message");
		assertTrue(messagesPage.isMessageDisplayed(driver, Subject), "Message received with timestamp");

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
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		String ccd = RestUtils.prepareCCD(testData.getCCDPath());

		log("ccd    : "+ccd);
		log("Step 2: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), ccd, testData.getResponsePath());

		log("Processing URL: " + processingUrl);
		log("Step 3: Get processing status until it is completed");
		Thread.sleep(60000);

		log("Step 4:LogIn to Patient Portal ");
		JalapenoLoginPage portalloginpage = new JalapenoLoginPage(driver, testData.getURL());
		JalapenoHomePage homePage = portalloginpage.login(testData.getUserName(), testData.getPassword());

		log("Step 5: Go to Inbox");
		JalapenoMessagesPage inboxPage = homePage.clickOnMenuMessages();
		assertTrue(inboxPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		log("Step 5: Validate message subject and send date");
		assertTrue(inboxPage.isMessageDisplayed(driver, "You have a new health data summary"));
		log("CCD sent date & time is : " + inboxPage.returnMessageSentDate());

		log("Step 6: Click on link View health data");
		JalapenoCcdViewerPage jalapenoCcdPage = inboxPage.findCcdMessage(driver);

		log("Step 7: Verify if CCD Viewer is loaded and click Close Viewer");
		assertTrue(jalapenoCcdPage.areBasicPageElementsPresent());
		inboxPage = jalapenoCcdPage.closeCcd(driver);

		log("Step 8: Logging out");
		assertTrue(inboxPage.areBasicPageElementsPresent());
		homePage = inboxPage.backToHomePage(driver);
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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

		String reason = "Reason" + timestamp;

		log("Step 2: LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click on Appointment Button on Home Page");
		JalapenoAppointmentRequestPage apptPage = homePage.clickOnAppointment(driver);
		JalapenoAppointmentRequestV2Step1 apptPage1 = apptPage.requestForAppointmentStep1(driver);

		apptPage1.chooseFirstProvider();

		log("Step 4: Complete Appointment Request Page");
		JalapenoAppointmentRequestV2Step2 apptPage2 = apptPage1.continueToStep2(driver);

		apptPage2.fillAppointmentRequestForm(reason);
		homePage = apptPage2.submitAppointment(driver);

		log("Step 5: Check if thank you frame is displayd");
		assertTrue(homePage.isTextDisplayed("Thank you"));

		log("Step 6: Logout of Patient Portal");
		homePage.clickOnLogout();

		Thread.sleep(5000);

		log("Step 7: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

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

		// String arSMSubject = "Reply to Appointment Request";
		// String arSMBody = "This is reply to AR for " + reason;

		log("Getting Appointment reason ");
		String arSMSubject = IntegrationConstants.AR_SM_SUBJECT.toString();
		String arSMBody = IntegrationConstants.AR_SM_BODY.toString();
		log("************Appointment Reason: " + reason);
		log("************Appointment Secure Message Subject: " + arSMSubject);
		log("************Appointment Secure Message Body: " + arSMBody);

		String postXML =
				RestUtils.findValueOfChildNode(testData.getResponsePath(), "AppointmentRequest", reason, arSMSubject, arSMBody, testData.getAppointmentPath());

		// httpPostRequest method
		log("Step 10: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), postXML,
				testData.getResponsePath());

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
		Mailinator mail = new Mailinator();
		String subject = "New message from "+testData.getPracticeName();
		String messageLink = "Sign in to view this message";
		String emailMessageLink = mail.getLinkFromEmail(testData.getUserName(), subject, messageLink, 5);

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
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(),
				testData.getPracticePassword());

		log("Step 18: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestSearchPage.PAGE_NAME);

		log("Step 19: Search for appt requests");
		apptSearch.searchForApptRequests(2, null, null);
		Thread.sleep(120000);
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(reason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestDetailStep1Page.PAGE_NAME);

		String actualSMSubject = detailStep1.getPracticeMessageSubject();
		assertTrue(detailStep1.getPracticeMessageSubject().contains(arSMSubject),
				"Expected Secure Message Subject containing [" + arSMSubject + "but actual message subject was ["
						+ actualSMSubject + "]");

		String actualSMBody = detailStep1.getPracticeMessageBody();
		assertTrue(detailStep1.getPracticeMessageBody().contains(arSMBody), "Expected Secure Message Body containing ["
				+ arSMBody + "but actual message body was [" + actualSMBody + "]");

		log("Step 20: Logout of Practice Portal");
		practiceHome.logOut();

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testStatementPreference() throws Exception {
		log("Test Case: Statement Preference in Patient Portal");
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
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(),
				testData.getPracticePassword());

		log("Step 8: Search for above patient with first name & last name");
		PatientSearchPage patientSearch = practiceHome.clickPatientSearchLink();
		patientSearch.searchForPatientInPatientSearch(testData.getFirstName(), testData.getLastName());

		log("Step 9: Verify search results");
		IHGUtil.waitForElement(driver, 60, patientSearch.searchResult);
		assertTrue(patientSearch.searchResult.getText().contains(testData.getFirstName()));

		log("Step 10: Get Medfusion Member Id & External Id of the patient");
		PatientDashboardPage patientDashboard = patientSearch.clickOnPatient(testData.getFirstName(),
				testData.getLastName());
		patientDashboard.editPatientLink();

		String memberId = patientDashboard.medfusionID();
		log("MemberId is " + memberId);
		String externalPatientId = patientDashboard.readExternalPatientID();
		log("External Id is " + externalPatientId);

		practiceHome.logOut();

		log("Step 11: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		log("Step 12: Wait 60 seconds");
		Thread.sleep(60000);

		log("Step 13: Getting statement preference updates since timestamp: " + timeStamp);
		String nextTimeStamp = RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + timeStamp,
				testData.getResponsePath());

		log("Step 14: Validate the response");
		RestUtils.isStatementPreferenceCorrect(testData.getResponsePath(), memberId, "PAPER");

		String statementPreference[] = { "E_STATEMENT", "BOTH" };

		for (int i = 0; i < statementPreference.length; i++) {
			log("-----Statement Preference : " + statementPreference[i] + "-----");
			log("Step 15: Prepare payload to set Statement Preference as " + statementPreference[i]);
			if (StringUtils.isBlank(nextTimeStamp))
				timeStamp = System.currentTimeMillis();
			else
				timeStamp = Long.valueOf(nextTimeStamp);

			String payload = RestUtils.preparePostStatementPreference(testData.getStatementPath(), memberId,
					externalPatientId, statementPreference[i]);

			log("Step 16: Do POST Statement Preference API & set preference to " + statementPreference[i]);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), payload,
					testData.getResponsePath());

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

			assertTrue(myPreferencePage1
					.checkStatementPreferenceUpdated(StatementPreferenceType.valueOf(statementPreference[i])));

			log("Step 20: Logout of Portal");
			homePage1.clickOnLogout();

			log("Step 21: GET Statement Preference API");
			log("Getting statement preference updates since timestamp: " + timeStamp);
			nextTimeStamp = RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + timeStamp,
					testData.getResponsePath());

			log("Step 22: Validate the response");
			RestUtils.isStatementPreferenceCorrect(testData.getResponsePath(), memberId, statementPreference[i]);
		}
	}

	@DataProvider(name = "channelVersion")
	public Object[][] channelVersion() {
		Object[][] obj = new Object[][] { { "v1" }, { "v2" }, { "v3" } };
		return obj;
	}

}
