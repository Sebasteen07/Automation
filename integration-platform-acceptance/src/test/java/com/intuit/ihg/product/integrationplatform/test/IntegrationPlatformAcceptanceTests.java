// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.test;

import static org.testng.Assert.*;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.product.integrationplatform.utils.AMDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentTestData;
import com.intuit.ihg.product.integrationplatform.utils.EHDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.PayNow;
import com.intuit.ihg.product.integrationplatform.utils.PayNowTestData;
import com.intuit.ihg.product.integrationplatform.utils.PaymentTestData;
import com.intuit.ihg.product.integrationplatform.utils.PrescriptionTestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.integrationplatform.utils.StatementPreferenceTestData;
import com.intuit.ihg.product.integrationplatform.utils.YopMailUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
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


public class IntegrationPlatformAcceptanceTests extends BaseTestNGWebDriver {	
	@DataProvider(name = "channelVersion")
	public Object[][] channelVersion() {
		Object[][] obj = new Object[][] { { "v1" }, { "v3" } };
		return obj;
	}

	@DataProvider(name = "channelVersionPIDC")
	public Object[][] channelVersionPIDC() {
		Object[][] obj = new Object[][] { { "v1" }, { "v2" }, { "v3" } };
		return obj;
	}
	
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
	 * log("Step 1: Get Test Data"); 
	 * AppointmentTestData testData = new AppointmentTestData(); Long
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
		logStep("Get Test Data");
		AMDCTestData testData = new AMDCTestData();
		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("Response Path: " + testData.getResponsePath());
		log("From: " + testData.getFrom1());
		log("SecureMessagePath: " + testData.getSecureMessagePath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());
		log("RestV3Url: " + testData.getRestV3Url());

		logStep("Log in");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		logStep("Click Ask A Staff");
		JalapenoAskAStaffPage askStaff1 = homePage.clickOnAskAStaff(driver);
		Thread.sleep(8000);
		
		logStep("Fill and complete the of Ask A Staff");
		askStaff1.fillAndSubmitAskAStaff(driver);

		logStep("Validate entry is on Ask A Staff History page");
		homePage.clickOnAskAStaff(driver);
		boolean aasHistory = askStaff1.checkHistory(driver);
		Thread.sleep(7000);
		assertTrue(aasHistory);

		logStep("Logout of Patient Portal");
		homePage.clickOnLogout();

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		logStep("Get AMDC Rest call");
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

		logStep("Checking validity of the response xml");
		RestUtils.isQuestionResponseXMLValid(testData.getResponsePath(), askStaff1.getCreatedTimeStamp());
	}

	@Test(enabled = true, dataProvider = "channelVersionPIDC", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientUpdate(String version) throws Exception {
		logStep("Get Test Data");
		PIDCTestData testData = new PIDCTestData();
		Long timestamp = System.currentTimeMillis();
		
		logStep("Log in");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		logStep("Click on myaccountLink on MyPatientPage");
		JalapenoMyAccountProfilePage myAccountPage = homePage.goToAccountPage();

		logStep("Create random  addresses to update");
		Random random = new Random();
		String firstLine = "Street " + random.nextInt(1000);
		String secondLine = "Street " + random.nextInt(1000);
		log("Address 1: " + firstLine + "\nAddress 2: " + secondLine);
		Thread.sleep(9000);
		
		logStep("Modify address line 1 and 2 on MyAccountPage");
		myAccountPage.modifyAndSubmitAddressLines(firstLine, secondLine);

		logStep("Logout from Patient portal");
		myAccountPage.clickOnLogout();

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());
		log("Wait 60 seconds, so that patient-outbound can be processed");
		Thread.sleep(60000);

		logStep("Do a GET on PIDC");
		// this Step assumes that the updated patient is the patient from first
		// ten registered patients, so we can save traffic
		// if max argument is ommited patient should be in first 100 patients
		Long since = timestamp / 1000L - 60 * 24;
		if (version.contains("v1")) {
			log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestv1Url() + "?since=" + since + ",0",
					testData.getResponsePath());

			logStep("Check changes of address lines");
			RestUtils.isPatientUpdated(testData.getResponsePath(), testData.getUserName(), firstLine, secondLine);
		}
		if (version.contains("v2")) {
			log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestv2Url() + "?since=" + since + ",0",
					testData.getResponsePath());

			logStep("Check changes of address lines");
			RestUtils.isPatientUpdated(testData.getResponsePath(), testData.getUserName(), firstLine, secondLine);
		}
		if (version.contains("v3")) {
			log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestv3Url() + "?since=" + since + ",0",
					testData.getResponsePath());

			logStep("Check changes of address lines");
			RestUtils.isPatientUpdated(testData.getResponsePath(), testData.getUserName(), firstLine, secondLine);
		}
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ERxPrescription(String version) throws Exception {
		// No longer in use as prescription renewal is handled in RxMedication20 case in Pi-integration-regression
		if (version.contains("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		logStep("Get Test Data");
		PrescriptionTestData testData = new PrescriptionTestData();
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

		logStep("Log in");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());
		Thread.sleep(9000);

		logStep("Click on PrescriptionRenewal Link ");
		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(15000);
		prescriptionsPage.clickContinueButton(driver);
		Thread.sleep(15000);

		logStep("Getting Provider Details");
		String practiceLocation = prescriptionsPage.getPracticeLocation(driver);
		log("Practce Location: " + practiceLocation);
		String practiceProvider = prescriptionsPage.getPracticeProvider(driver);
		log("Practice Provider Name :" + practiceProvider);

		logStep("Getting Medication Name, Prescription Subject, and Prescription Reply");
		long time = prescriptionsPage.getCreatedTs();
		String medicationName = IntegrationConstants.MEDICATIONNAME.toString() + String.valueOf(time);
		log("Medication Name :" + medicationName);
		String rxSMSubject = IntegrationConstants.RXRENEWAL_SUBJECT_TAG.toString() + String.valueOf(time);
		log("Prescription Subject :" + rxSMSubject);
		String rxSMBody = IntegrationConstants.QUESTION_MESSAGE.toString() + "" + String.valueOf(time);
		log("Prescription Reply :" + rxSMBody);
		prescriptionsPage.fillThePrescriptionforExisitngUser();

		logStep("Verify RxRenewal Confirmation Message");
		IHGUtil.waitForElement(driver, 5, prescriptionsPage.renewalConfirmationmessage);
		assertEquals(prescriptionsPage.renewalConfirmationmessage.getText(), IntegrationConstants.RENEWAL_CONFIRMATION);
		prescriptionsPage.homeButton.click();
		driver.switchTo().defaultContent();
		Thread.sleep(5000);

		logStep("Logout of Patient Portal");
		homePage.clickOnLogout();

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());
		String prescriptionId = null;
		String sigCodes = "";
		if (version.equals("v1")) {
			log("For V1 endpoint");
			logStep("Get Prescription Rest call");
			// get only messages from last hour in epoch time to avoid transferring
			// lot of data
			Long since = timestamp / 1000L - 60 * 24;

			log("Getting messages since timestamp :" + since);

			// do the call and save xml, ",0" is there because of the since
			// attribute format
			Thread.sleep(4000);
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			logStep("Checking validity of the response xml");

			RestUtils.isMedicationDetailsResponseXMLValid(testData.getResponsePath(), medicationName);

			String postXML = RestUtils.findValueOfMedicationNode(testData.getResponsePath(), "Medication",
					medicationName, rxSMSubject, rxSMBody, testData.getPrescriptionPath());

			String SigCodeAbbreviation = RestUtils.SigCodeAbbreviation;
			String SigCodeMeaning = RestUtils.SigCodeMeaning;

			sigCodes = SigCodeAbbreviation + " - " + SigCodeMeaning;

			log("SigCodeAbbreviation :" + SigCodeAbbreviation);
			log("SigCodeMeaning :" + SigCodeMeaning);

			logStep("Do Message Post Request" + postXML);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), postXML,
					testData.getResponsePath());

			logStep("Get processing status until it is completed");
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
			assertTrue(completed, "Message processing was not completed in time");
		} else {
			log("For V3 endpoint");
			logStep("Get Prescription Rest call");
			// get only messages from last hour in epoch time to avoid transferring
			// lot of data
			Long since = timestamp / 1000L - 60 * 24;

			log("Getting messages since timestamp :" + since);

			// do the call and save xml, ",0" is there because of the since
			// attribute format
			Thread.sleep(4000);
			RestUtils.setupHttpGetRequest(testData.getRestV3Url() + "?since=" + since + ",0",
					testData.getResponsePath());
			logStep("Checking validity of the response xml");
			RestUtils.isMedicationDetailsResponseXMLValid(testData.getResponsePath(), medicationName);
			String postXML = RestUtils.findValueOfMedicationNode(testData.getResponsePath(), "Medication",
					medicationName, rxSMSubject, rxSMBody, testData.getPrescriptionPathV3());
			String SigCodeAbbreviation = RestUtils.SigCodeAbbreviation;
			String SigCodeMeaning = RestUtils.SigCodeMeaning;
			sigCodes = SigCodeAbbreviation + " - " + SigCodeMeaning;
			log("SigCodeAbbreviation :" + SigCodeAbbreviation);
			log("SigCodeMeaning :" + SigCodeMeaning);

			logStep("Getting Prescription ID");
			prescriptionId = RestUtils.getPrescriptionID(testData.getResponsePath());
			log("Prescription ID: " + prescriptionId);

			logStep("Do Message Post Request" + postXML);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestV3Url(), postXML,
					testData.getResponsePath());

			logStep("Get processing status until it is completed");
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
			assertTrue(completed, "Message processing was not completed in time");
		}
		
        // Patient portal validation
		logStep("Check secure message in patient mailinator inbox");
		YopMailUtils mail = new YopMailUtils(driver);
		String subject = "New message from ";
		String messageLink = "Sign in to view this message";
		String emailMessageLink = mail.getLinkFromEmail(testData.getUserName(), subject, messageLink, 20);
		log("Email message link " + emailMessageLink);

		logStep("Login to Patient Portal");
		JalapenoLoginPage ploginPage = new JalapenoLoginPage(driver, emailMessageLink);
		JalapenoHomePage phomePage = ploginPage.login(testData.getUserName(), testData.getPassword());
		Thread.sleep(9000);

		logStep("Click on msessage box");
		JalapenoMessagesPage inboxPage = phomePage.clickOnMenuMessages();
		Thread.sleep(9000);

		logStep("Find message in Inbox");
		inboxPage.isMessageDisplayed(driver, rxSMSubject);

		logStep("Verify Provider details on patient portal");
		inboxPage.checkProviderDetails(practiceProvider, practiceLocation);

		logStep("Logout of Patient Portal");
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		Thread.sleep(6000);
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(),
				testData.getPracticePassword());

		logStep("Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome.clickonRxRenewal();
		Thread.sleep(10000);

		logStep("Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday(2);
		Thread.sleep(10000);

		logStep("Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		logStep("Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.checkMedicationDetails(medicationName, sigCodes);

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		if (version.equals("v3")) {
			logStep("Get PrescriptionHeader call");
			// get only messages from last hour in epoch time to avoid transferring
			// lot of data
			Long since = timestamp / 1000L - 60 * 24;
			log("Getting messages since timestamp :" + since);
			log("For V3 endpoint");
			String getStatusV3Url = testData.getRestV3Url().replaceAll("prescriptions", "prescriptionsHeaders");
			log("getStatusUrl  :" + getStatusV3Url);
			Thread.sleep(4000);
			RestUtils.setupHttpGetRequest(getStatusV3Url + "?since=" + since + ",0", testData.getResponsePath());
			
			logStep("Verify PrescriptionHeader ID");
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
		log("Step 1: Get Test Data");
		PaymentTestData testcasesData = new PaymentTestData();
		Long timestamp = System.currentTimeMillis();
		String accountNumber = IHGUtil.createRandomNumericString();
		String amount = "100.00";
		String name = "TestPatient CreditCard";
		CreditCard creditCard;
		String CCType;
		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
			CCType = "Visa";
			creditCard = new CreditCard(CardType.Visa, name);
		} else {
			CCType = "MasterCard";
			creditCard = new CreditCard(CardType.Mastercard, name);
		}

		String CCLastDig = creditCard.getLastFourDigits();
		String reply_Subject = "Test Message " + IHGUtil.createRandomNumericString();
		String messageThreadID;
		String lastTimestamp;
		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		logStep("LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testcasesData.getUrl());

		JalapenoHomePage homePage = loginPage.login(testcasesData.getUserName(), testcasesData.getPassword());
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		logStep("Remove all cards because Selenium can't see AddNewCard button");
		payBillsPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(payBillsPage.isAnyCardPresent());

		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, accountNumber,
				creditCard);
		logStep("Verifying credit card ending , expected: "+creditCard.getLastFourDigits() );
		assertTrue(confirmationPage.getCreditCardEnding().equals(creditCard.getLastFourDigits()));

		homePage = confirmationPage.commentAndSubmitPayment("Testing payment from number: " + accountNumber);
		logStep("fetch confirmation number ");
		String confirmationNumber = payBillsPage.readConfirmationNumber();
		log("Confirmation Number is: " + confirmationNumber);
		assertTrue(homePage.wasPayBillsSuccessfull());
		homePage.clickOnLogout();

		logStep(" Setup Oauth client 2.O");
		RestUtils.oauthSetup(testcasesData.getOAuthKeyStore(), testcasesData.getOAuthProperty(), testcasesData.getOAuthAppToken(), testcasesData.getOAuthUsername(),
				testcasesData.getOAuthPassword());
		if(version.equals("v1")) {
			logStep(" Getting messages since timestamp: " + timestamp);
		lastTimestamp = RestUtils.setupHttpGetRequest(testcasesData.getRestUrl() + "?since=" + timestamp, testcasesData.getResponsePath());

			logStep(" Verify payment details");
		RestUtils.isPaymentAppeared(testcasesData.getResponsePath(), accountNumber, amount, CCLastDig, CCType,
				IntegrationConstants.SUBMITTED, confirmationNumber);

		messageThreadID = RestUtils.paymentID;
		log("Payment ID :" + messageThreadID);

		String message = RestUtils.prepareSecureMessage(testcasesData.getcommunicationXML(), testcasesData.getFrom(), testcasesData.getUserName(), reply_Subject,
				messageThreadID);
		log("Payload to beposted for AM: " + message);
		logStep("Do Message Post AMDC Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getCommRestUrl(), message,
				testcasesData.getResponsePath());

		logStep("Get processing status until it is completed");
		boolean completed = false;
		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}
		assertTrue(completed, "Message processing was not completed in time");
		}
		else {
			logStep("Getting messages since timestamp: " + timestamp);
			lastTimestamp = RestUtils.setupHttpGetRequest(testcasesData.getRestV3Url() + "?since=" + timestamp, testcasesData.getResponsePath());

			logStep("Verify payment details");
			RestUtils.isPaymentAppeared(testcasesData.getResponsePath(), accountNumber, amount, CCLastDig, CCType, IntegrationConstants.SUBMITTED, confirmationNumber);

			messageThreadID = RestUtils.paymentID;
			log("Payment ID :" + messageThreadID);

			String message = RestUtils.prepareSecureMessage(testcasesData.getcommunicationXML(), testcasesData.getFrom(), testcasesData.getUserName(), reply_Subject,
					messageThreadID);
			log("Payload to beposted for AM: " + message);
			logStep("Do Message Post AMDC Request");
			String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getCommRestUrl(), message, testcasesData.getResponsePath());

			logStep("Get processing status until it is completed");
			boolean completed = false;
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
				completed = true;
			}
			assertTrue(completed, "Message processing was not completed in time"); 
		}
		logStep("Check secure message in patient yopmail inbox");
		YopMailUtils mail = new YopMailUtils(driver);
		String subject = "New message from "+testcasesData.getPracticeName();
		String messageLink = "Sign in to view this message";
		String emailMessageLink = mail.getLinkFromEmail(testcasesData.getUserName(), subject, messageLink, 20);

		// patient Portal validation
		logStep("Login to Patient Portal");

		logStep("Login to Patient Portal");
		log("Link is " + emailMessageLink);
		new JalapenoLoginPage(driver, emailMessageLink);
		Thread.sleep(9000);
		JalapenoHomePage phomePage = loginPage.login(testcasesData.getUserName(), testcasesData.getPassword());
		assertTrue(phomePage.isHomeButtonPresent(driver));

		Thread.sleep(9000);
		JalapenoMessagesPage messagesPage = phomePage.showMessages(driver);
		Long since = timestamp / 1000L - 60 * 24;

		logStep("Validate message loads and is the right message");
		messagesPage.isMessageDisplayed(driver, reply_Subject);

		Thread.sleep(60000);
		logStep("Reply to the message");
		messagesPage.replyToMessage(driver);

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET AMDC and verify patient reply in Get AMDC response");
		RestUtils.setupHttpGetRequest(testcasesData.getCommRestUrl() + "?since=" + since + ",0",
				testcasesData.getResponsePath());

		logStep("Validate message reply");
		RestUtils.isReplyPresent(testcasesData.getResponsePath(), reply_Subject);

		logStep("Logout from Patient Portal");
		homePage.clickOnLogout();

		if(version.equals("v1")) {
		String postPayload = RestUtils.preparePayment(testcasesData.getPaymentPath(), messageThreadID, null, IntegrationConstants.BILLPAYMENT);

		log("Post Payload is:  " + postPayload);
		logStep("Do a Post and get the message");
		String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestUrl(), postPayload, testcasesData.getResponsePath());
		boolean completed = false;

		// wait 10 seconds so the message can be processed
		Thread.sleep(60000);
		RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
			completed = true;
		}
		assertTrue(completed, "Message processing was not completed in time");
		}
		else {
			String postPayload = RestUtils.preparePayment(testcasesData.getPaymentPathV3(), messageThreadID, null, IntegrationConstants.BILLPAYMENT);

			log("Post Payload is:  " + postPayload);
			logStep("Do a Post and get the message");
			String processingUrl = RestUtils.setupHttpPostRequest(testcasesData.getRestV3Url(), postPayload, testcasesData.getResponsePath());
			boolean completed = false;
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testcasesData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testcasesData.getResponsePath())) {
				completed = true;
			}
			assertTrue(completed, "Message processing was not completed in time");
		    }
		Thread.sleep(5000);
		log("Verify Payment status in Practice Portal");
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testcasesData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testcasesData.getPracticeUserName(),
				testcasesData.getPracticePassword());
		Thread.sleep(6000);
		logStep("Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();

		logStep("Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		logStep("Search For Payment By Status ");
		onlineBillPaySearchPage.searchForBillStatus(2);

		logStep("Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage.searchForBillPayment(accountNumber);

		String Status = onlineBillPaySearchPage.getBillDetails();
		assertNotNull(Status, "The submitted Online Bill request was not found in the practice");

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Verify Payment status in Get Response using the Timestamp received in response of Step 8");

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
		assertTrue(driver.getPageSource().contains("Account N/A."));

		log("Step 5: Verify the prize format.");
		assertTrue(driver.getPageSource().contains("$" + pNoLoginPaymentPage.GetAmountPrize() + ".00"));
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
		assertTrue(completed, "Message processing was not completed in time");
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
		assertTrue(completed, "Message processing was not completed in time");
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
		assertEquals(Boolean.valueOf(vcsPage.getPaymentCompletedSuccessMsg().contains("Payment completed")),
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
		assertTrue(completed, "Message processing was not completed in time");
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
		assertTrue(completed, "Message processing was not completed in time");
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
		Thread.sleep(10000);
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

		logStep("Get Data from Excel");
		AMDCTestData testData = new AMDCTestData();

		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("Response Path: " + testData.getResponsePath());
		log("From: " + testData.getFrom1());
		log("SecureMessagePath: " + testData.getSecureMessagePath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());

		logStep("LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		logStep("Click Ask Ur Doc");
		JalapenoAskAStaffPage askStaff1 = homePage.clickOnAskADoc(driver);
		Thread.sleep(8000);
		logStep("Fill and complete the of Ask A Staff");
		String attachmentName= askStaff1.fillAndSubmitAskyourDocUnpaid(driver);
		log("FileName : "+attachmentName);

		logStep("Validate entry is on Ask A Staff History page");
		homePage.clickOnAskADoc(driver);
		askStaff1.checkHistory(driver);
		Thread.sleep(7000);

		logStep("Logout of Patient Portal");
		homePage.clickOnLogout();

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		logStep("Get AMDC Rest call");
		// get only messages from last day in epoch time to avoid transferring
		// lot of data
		Long since = askStaff1.getCreatedTimeStamp() / 1000L;

		log("Getting messages since timestamp: " + since);

		// do the call and save xml, ",0" is there because of the since

		if (version.contains("v1")) {
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		} else if (version.contains("v3")) {
			RestUtils.setupHttpGetRequest(testData.getRestV3Url() + "?since=" + since + ",0", testData.getResponsePath());
		}

		logStep("Checking validity of the response xml");
		RestUtils.isQuestionResponseXMLValid(testData.getResponsePath(), askStaff1.getCreatedTimeStamp());
		
		if (version.contains("v3")) {
			String attachementURL = RestUtils.isResponseContainsValidAttachmentURL(testData.getResponsePath());
			logStep("Make GET call with attachement URL");
			RestUtils.setupHttpGetRequest(attachementURL, testData.getResponsePath());
		}
		
		logStep("Validate the FileName in the attachement URL response");
		RestUtils.validateAttachementName(testData.getResponsePath(),attachmentName);
		log("Attached FileName is correctly Populdated in the GET response of attachment URL ");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCSecureMessageFromPractice() throws Exception {
		log("Test Case: AMDC Secure Message from Practice. ");

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		logStep("Get Data from Excel");
		AMDCTestData testData = new AMDCTestData();

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

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		logStep("Fill Message data");
		long timestamp = System.currentTimeMillis();
		String Subject = "Test " + timestamp;
		String message = RestUtils.prepareSecureMessage(testData.getSecureMessagePath(),
				testData.getIntegrationPracticeID(), testData.getUserName(), "Test " + timestamp, null);

		String messageID = RestUtils.newMessageID();
		log("Partner Message ID:" + messageID);

		log("Payload posted is ___________" + message);
		logStep("Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), message,
				testData.getResponsePath());

		logStep("Get processing status until it is completed");
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
		assertTrue(completed, "Message processing was not completed in time");

		logStep("Check secure message in patient mail inbox");
		YopMailUtils mail = new YopMailUtils(driver);
		String subject = "New message from IHGQA Automation Integrated Oauth 2.0";
		String messageLink = "Sign in to view this message";
		String emailMessageLink = mail.getLinkFromEmail(testData.getUserName(), subject, messageLink, 20);

		logStep("Login to Patient Portal");
		log("Link is " + emailMessageLink);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, emailMessageLink);
		Thread.sleep(9000);
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());
		assertTrue(homePage.isHomeButtonPresent(driver));

		Thread.sleep(9000);
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Validate message loads and is the right message");
		assertTrue(messagesPage.isMessageDisplayed(driver, Subject), "Message received with timestamp");

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEHDCsendCCD() throws Exception {

		logStep("Get Test Data");
		EHDCTestData testData = new EHDCTestData();

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

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());
		String ccd = RestUtils.prepareCCD(testData.getCCDPath());
		log("ccd    : "+ccd);
		
		logStep("Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), ccd, testData.getResponsePath());
		log("Processing URL: " + processingUrl);
		log("Get processing status until it is completed");
		Thread.sleep(60000);

		logStep("Log in to Patient Portal ");
		JalapenoLoginPage portalloginpage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = portalloginpage.login(testData.getUserName(), testData.getPassword());
		JalapenoMenu menuPage = new JalapenoHomePage(driver);

		logStep("Go to Inbox");
		JalapenoMessagesPage inboxPage = homePage.clickOnMenuMessages();

		logStep("Validate message subject and send date");
		assertTrue(inboxPage.isMessageDisplayed(driver, "You have a new health data summary"));
		log("CCD sent date & time is : " + inboxPage.returnMessageSentDate());

		logStep("Click on link View health data");
		JalapenoCcdViewerPage jalapenoCcdPage = inboxPage.findCcdMessage(driver);

		logStep("Verify if CCD Viewer is loaded and click Close Viewer");
		inboxPage = jalapenoCcdPage.closeCcd(driver);

		logStep("Logging out");
		menuPage.clickOnMenuHome();
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAppointmentRequest() throws Exception {
		Long timestamp = System.currentTimeMillis();
		logStep("Get Test Data");
		AppointmentTestData testData = new AppointmentTestData();

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

		logStep("LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		logStep("Click on Appointment Button on Home Page");
		JalapenoAppointmentRequestPage apptPage = homePage.clickOnAppointment(driver);
		JalapenoAppointmentRequestV2Step1 apptPage1 = apptPage.requestForAppointmentStep1(driver);
		apptPage1.chooseFirstProvider();

		logStep("Complete Appointment Request Page");
		JalapenoAppointmentRequestV2Step2 apptPage2 = apptPage1.continueToStep2(driver);
		apptPage2.fillAppointmentRequestForm(reason);
		homePage = apptPage2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Logout of Patient Portal");
		homePage.clickOnLogout();
		Thread.sleep(5000);

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		logStep("Get Appointment Rest call");
		// get only messages from last hour in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;
		log("Getting messages since timestamp: " + since);
		// do the call and save xml, ",0" is there because of the since
		// attribute format
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		logStep("Checking reason in the response xml");
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
		logStep("Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), postXML,
				testData.getResponsePath());

		logStep("Get processing status until it is completed");
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
		assertTrue(completed, "Message processing was not completed in time");

		logStep("Check secure message in patient gmail inbox");
		YopMailUtils mail = new YopMailUtils(driver);
		String subject = "New message from "+testData.getPracticeName();
		String messageLink = "Sign in to view this message";
		String emailMessageLink = mail.getLinkFromEmail(testData.getUserName(), subject, messageLink, 5);

		// patient Portal validation
		logStep("Login to Patient Portal");
		JalapenoLoginPage loginPage2 = new JalapenoLoginPage(driver, emailMessageLink);
		JalapenoHomePage homePage2 = loginPage2.login(testData.getUserName(), testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage2.showMessages(driver);

		logStep("Find & validate message in Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, arSMSubject));

		logStep("Logout of Patient Portal");
		homePage2.clickOnLogout();

		// Practice portal validation
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(),
				testData.getPracticePassword());

		logStep("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestSearchPage.PAGE_NAME);

		logStep("Search for appt requests");
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

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testStatementPreference() throws Exception {
		logStep("Get Test Data");
		StatementPreferenceTestData testData = new StatementPreferenceTestData();

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

		logStep("LogIn to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		logStep("Click on myaccountLink on MyPatientPage");
		JalapenoMyAccountProfilePage myAccountProfilePage = homePage.goToAccountPage();

		logStep("Click on Preferences Tab");
		JalapenoMyAccountPreferencesPage myPreferencePage = myAccountProfilePage.goToPreferencesTab(driver);

		logStep("Set Statement Delievery Preference as Paper Statement");
		myPreferencePage.checkAndSetStatementPreference(driver, StatementPreferenceType.PAPER);

		logStep("Logout from Patient portal");
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(),
				testData.getPracticePassword());

		logStep("Search for above patient with first name & last name");
		PatientSearchPage patientSearch = practiceHome.clickPatientSearchLink();
		patientSearch.searchForPatientInPatientSearch(testData.getFirstName(), testData.getLastName());

		logStep("Verify search results");
		IHGUtil.waitForElement(driver, 60, patientSearch.searchResult);
		assertTrue(patientSearch.searchResult.getText().contains(testData.getFirstName()));

		logStep("Get Medfusion Member Id & External Id of the patient");
		PatientDashboardPage patientDashboard = patientSearch.clickOnPatient(testData.getFirstName(),
				testData.getLastName());
		patientDashboard.editPatientLink();
		String memberId = patientDashboard.medfusionID();
		log("MemberId is " + memberId);
		String externalPatientId = patientDashboard.readExternalPatientID();
		log("External Id is " + externalPatientId);

		logStep("Logout");
		practiceHome.logOut();

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());
		log("Wait 60 seconds");
		Thread.sleep(60000);

		logStep("Getting statement preference updates since timestamp: " + timeStamp);
		String nextTimeStamp = RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + timeStamp,
				testData.getResponsePath());

		logStep("Validate the response");
		RestUtils.isStatementPreferenceCorrect(testData.getResponsePath(), memberId, "PAPER");
		String statementPreference[] = { "E_STATEMENT", "BOTH" };
		for (int i = 0; i < statementPreference.length; i++) {
			log("-----Statement Preference : " + statementPreference[i] + "-----");
			logStep("Prepare payload to set Statement Preference as " + statementPreference[i]);
			if (StringUtils.isBlank(nextTimeStamp))
				timeStamp = System.currentTimeMillis();
			else
				timeStamp = Long.valueOf(nextTimeStamp);

			String payload = RestUtils.preparePostStatementPreference(testData.getStatementPath(), memberId,
					externalPatientId, statementPreference[i]);

			logStep("Do POST Statement Preference API & set preference to " + statementPreference[i]);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), payload,
					testData.getResponsePath());

			logStep("Get processing status until it is completed");
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

			logStep("Login to Patient Portal");
			JalapenoLoginPage loginPage1 = new JalapenoLoginPage(driver, testData.getUrl());
			JalapenoHomePage homePage1 = loginPage1.login(testData.getUserName(), testData.getPassword());

			logStep("Check for update in Statement Preference");
			JalapenoMyAccountProfilePage myAccountProfilePage1 = homePage1.goToAccountPage();
			JalapenoMyAccountPreferencesPage myPreferencePage1 = myAccountProfilePage1.goToPreferencesTab(driver);

			assertTrue(myPreferencePage1
					.checkStatementPreferenceUpdated(StatementPreferenceType.valueOf(statementPreference[i])));

			logStep("Logout of Portal");
			homePage1.clickOnLogout();

			logStep("GET Statement Preference API");
			log("Getting statement preference updates since timestamp: " + timeStamp);
			nextTimeStamp = RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + timeStamp,
					testData.getResponsePath());

			logStep("Validate the response");
			RestUtils.isStatementPreferenceCorrect(testData.getResponsePath(), memberId, statementPreference[i]);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentID() throws Exception {
		Long timestamp = System.currentTimeMillis();
		logStep("Get Test Data");
		AppointmentTestData testData = new AppointmentTestData();

		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Response Path: " + testData.getResponsePath());
		log("From: " + testData.getFrom());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());
		log("RestV3 Url: " + testData.getRestV3Url());
		log("ApointmentPathV3: " + testData.getAppointmentPathV3());
		log("RestV3Headers Url: " + testData.getRestUrlV3Headers());

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		logStep("Get Appointment Rest call");
		Long since = timestamp / 1000L - 60 * 24;
		log("Getting messages since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestV3Url() + "?since=" + since + ",0", testData.getResponsePath());

		logStep("Checking Appointment Id in the response xml");
		String GetApptId = RestUtils.GetAppointmentId(testData.getResponsePath());
		log("AppointmentId:" + GetApptId);
		String AppointmentId = GetApptId;

		logStep("Get Appointment Rest call");
		log("Getting messages since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrlV3Headers() + "?since=" + since + ",0",
				testData.getResponsePath());

		logStep("Verifying Appointment Id in the response xml");
		String VerifyApptId = RestUtils.VerifyAppointmentId(testData.getResponsePath(), AppointmentId);
		log("AppointmentIdHeader:" + VerifyApptId);

	}

}


