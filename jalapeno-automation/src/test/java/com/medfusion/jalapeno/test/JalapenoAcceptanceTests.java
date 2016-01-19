package com.medfusion.jalapeno.test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.IHGUtil.Gender;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.mail.Mailinator;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep1Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep2Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep3Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep4Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.intuit.ihg.product.object.maps.practice.page.patientMessaging.PatientMessagingPage;
import com.intuit.ihg.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.intuit.ihg.product.object.maps.practice.page.rxrenewal.RxRenewalSearchPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.medfusion.product.jalapeno.JalapenoPatient;
import com.medfusion.product.object.maps.jalapeno.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.jalapeno.page.AskAStaff.JalapenoAskAStaffPage;
import com.medfusion.product.object.maps.jalapeno.page.CcdViewer.JalapenoCcdPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoPatientActivationPage;
import com.medfusion.product.object.maps.jalapeno.page.FamillyAccountPage.JalapenoCreateGuardianPage;
import com.medfusion.product.object.maps.jalapeno.page.FamillyAccountPage.JalapenoCreateGuardianPage2;
import com.medfusion.product.object.maps.jalapeno.page.FamillyAccountPage.JalapenoIdentifyDependantPage;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage2;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage3;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage4;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.jalapeno.page.MyAccountPage.JalapenoMyAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.NewPayBillsPage.JalapenoNewPayBillsPage;
import com.medfusion.product.object.maps.jalapeno.page.PrescriptionsPage.JalapenoPrescriptionsPage;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * @Author:Jakub Calabek
 * @Date:24.7.2013
 */

@Test
public class JalapenoAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAssessLoginPageElements() throws Exception {

		logTestEnvironment();

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(jalapenoLoginPage.assessLoginPageElements());

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginValidCredentials() throws Exception {

		logTestEnvironment();

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.assessLoginPageElements());
		
		JalapenoHomePage jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		loginPage = jalapenoHomePage.logout(driver);
		assertTrue(loginPage.assessLoginPageElements());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginInvalidCredentials() throws Exception {

		logTestEnvironment();

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		
		//JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), "InvalidPassword");
		//assertFalse(jalapenoHomePage.assessHomePageElements());
		jalapenoLoginPage.login(testData.getUserId(), "InvalidPassword");
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatient() throws Exception {
		logTestEnvironment();

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Creating a new patient");
		JalapenoPatient patient = new JalapenoPatient();
		JalapenoHomePage homePage = patient.createAndLogInPatient(driver, testData);
		assertTrue(homePage.assessHomePageElements());
		
		log("Checking if the information");
		JalapenoMyAccountPage myAccountPage = homePage.clickOnMyAccount(driver);
		assertTrue(myAccountPage.checkForAddress(driver, testData.getZipCode()));
		assertEquals(myAccountPage.getGender(), patient.getGender());

		JalapenoLoginPage loginPage = homePage.logout(driver);
		assertTrue(loginPage.assessLoginPageElements());
		
		homePage = loginPage.login(patient.getEmail(), patient.getPassword());
		assertTrue(homePage.assessHomePageElements());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientActivation() throws Exception {
		logTestEnvironment();
		
		log("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
		String patientsEmail = IHGUtil.createRandomEmailAddress(testDataFromProp.getEmail(), '.');

		log("Patient Activation on Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		patientActivationSearchTest
				.getPatientActivationLink(driver, practiceTestData, patientsEmail, testDataFromProp);

		log("Logging into Mailinator and getting Patient Activation url");
		String emailSubject = "You're invited to create a Patient Portal account at "
				+ testDataFromProp.getPracticeName();
		String inEmail = "Sign Up!";
		String unlockLink = new Mailinator().getLinkFromEmail(patientsEmail, emailSubject, inEmail, 10);
		assertNotNull(unlockLink, "Error: Activation link not found.");
		log("Retrieved activation link is " + unlockLink);

		log("Finishing of patient activation: step 1 - verifying identity");
		JalapenoPatientActivationPage patientActivationPage =
				new JalapenoPatientActivationPage(driver, unlockLink);
		assertTrue(patientActivationPage.assessPatientActivationVerifyPageElements());
		patientActivationPage.verifyPatientIdentity(PracticeConstants.Zipcode, PortalConstants.DateOfBirthMonth,
				PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);

		log("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = patientActivationPage.fillInPatientActivation(
				patientActivationSearchTest.getPatientIdString(), testDataFromProp.getPassword(),
				testDataFromProp);

		log("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.assessHomePageElements());

		log("Checking if address in My Account is filled");
		JalapenoMyAccountPage jalapenoMyAccountPage = jalapenoHomePage.clickOnMyAccount(driver);
		assertTrue(jalapenoMyAccountPage.checkForAddress(driver, "5501 Dillard Dr", "Cary", PracticeConstants.Zipcode));

		log("Logging out");
		JalapenoLoginPage jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());

		log("Logging again: " + patientActivationSearchTest.getPatientIdString() + " \\ "
				+ testDataFromProp.getPassword());
		jalapenoHomePage = jalapenoLoginPage.login(patientActivationSearchTest.getPatientIdString(),testDataFromProp.getPassword());

		assertTrue(jalapenoHomePage.assessHomePageElements());
		log("Logging out");
		
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}

	private void logTestEnvironment() {
		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testForgotPassword() throws Exception {

		logTestEnvironment();
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Initiate patient data");
		JalapenoPatient createPatient = new JalapenoPatient();
		JalapenoHomePage homePage = createPatient.createAndLogInPatient(driver, testData);

		log("Logout");
		JalapenoLoginPage loginPage = homePage.logout(driver);
		assertTrue(loginPage.assessLoginPageElements());
		
		log("Clicking on forgot username or password");
		JalapenoForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordButton();
		
		assertTrue(forgotPasswordPage.assessForgotPasswordPageElements());
		
		JalapenoForgotPasswordPage2 forgotPasswordPage2 = forgotPasswordPage.fillInDataPage(createPatient.getEmail());
		log("Message was sent, closing");
		forgotPasswordPage2.clickCloseButton();
		
		log("Logging into Mailinator and getting ResetPassword url");
		Mailinator mailinator = new Mailinator();
		String[] mailAddress = createPatient.getEmail().split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";
		String url = mailinator.getLinkFromEmail(mailAddress[0], emailSubject, inEmail, 10);
		
		assertTrue(url != null);
		url = url.substring(0, url.length()-4); //because Jsoup.parse at Mailinator changes "&lang" to "?" (problem is in progress)
		
		JalapenoForgotPasswordPage3 forgotPasswordPage3 = new JalapenoForgotPasswordPage3(driver, url);
		log("Redirecting to patient portal, filling secret answer as: " + testData
				.getSecretAnswer());
		JalapenoForgotPasswordPage4 forgotPasswordPage4 = forgotPasswordPage3.fillInSecretAnswer(testData.getSecretAnswer());
		
		log("Filling new password");
		homePage = forgotPasswordPage4.fillInNewPassword(testData.getPassword());
		assertTrue(homePage.assessHomePageElements());
		
		log("Logging out");
		loginPage = homePage.logout(driver);
		assertTrue(loginPage.assessLoginPageElements());
	}	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMessaging() throws Exception {

		logTestEnvironment();
		
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Initiate patient data");
		JalapenoPatient createPatient = new JalapenoPatient();
		JalapenoHomePage homePage = createPatient.createAndLogInPatient(driver, testData);

		JalapenoLoginPage loginPage = homePage.logout(driver);
		assertTrue(loginPage.assessLoginPageElements());
				
		//login to practice portal
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(),
				testData.getDoctorPassword());

		PatientMessagingPage patientMessagingPage = practiceHome.clickPatientMessagingTab();
		patientMessagingPage.setQuickSendFields(createPatient.getFirstName(), createPatient.getLastName(),"TestingMessage");
		
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.assessLoginPageElements());
		
		homePage = loginPage.login(createPatient.getEmail(), createPatient.getPassword());
		assertTrue(homePage.assessHomePageElements());
		
		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements());
		
		log("Waiting for message from practice portal");	
		assertTrue(messagesPage.isMessageDisplayed(driver, "Quick Send"));
		
		log("Response to the message");
		messagesPage.replyToMessage(driver);
		//TODO: "system is unable to send a reply" message, even if the message is sent
		
		log("Back to the practice portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		patientMessagingPage = practiceHome.clickPatientMessagingTab();
		String patientName = createPatient.getFirstName() + " " + createPatient.getLastName();
		assertTrue(patientMessagingPage.findMyMessage(patientName));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testViewCCD() throws Exception {

		logTestEnvironment();
		
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(), testData.getPassword());
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		JalapenoCcdPage jalapenoCcdPage = jalapenoMessagesPage.findCcdMessage(driver);
		
		assertTrue(jalapenoCcdPage.assessCcdElements());
		assertTrue(jalapenoCcdPage.checkPdfToDownload(driver));
		assertTrue(jalapenoCcdPage.checkRawToDownload(driver));
		assertTrue(jalapenoCcdPage.sendInformation("ihg_qa@direct.healthvault.com"));
		
		jalapenoMessagesPage = jalapenoCcdPage.closeCcd(driver);
		assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		jalapenoHomePage = jalapenoMessagesPage.backToHomePage(driver);
		
		log("Logging out");
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6SamePractice() throws Exception {

		logTestEnvironment();

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Initiate patient data");
		JalapenoPatient patient = new JalapenoPatient();
		JalapenoHomePage homePage = patient.createAndLogInPatient(driver, testData);
		assertTrue(homePage.assessHomePageElements());

		log("Checking if zipCode in My Account is filled");
		JalapenoMyAccountPage jalapenoMyAccountPage = homePage.clickOnMyAccount(driver);
		assertTrue(jalapenoMyAccountPage.checkForAddress(driver, testData.getZipCode()));

		JalapenoLoginPage loginPage = homePage.logout(driver);
		assertTrue(loginPage.assessLoginPageElements());

		log("Try to create the same patient");
		JalapenoCreateAccountPage createAccountPage = loginPage.clickSignInButton();
		assertTrue(createAccountPage.assessCreateAccountPageElements());

		createAccountPage.fillInDataPage(patient.getFirstName(), patient.getLastName(),
				patient.getEmail(), testData);

		assertTrue(loginPage.assessLoginPageElements());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6DifferentPractice() throws Exception {

		logTestEnvironment();
	
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Initiate patient data");
		JalapenoPatient patient = new JalapenoPatient();
		JalapenoHomePage homePage = patient.createAndLogInPatient(driver, testData);
		
		assertTrue(homePage.assessHomePageElements());
		
		JalapenoLoginPage loginPage = homePage.logout(driver);
		assertTrue(loginPage.assessLoginPageElements());
		
		log("Try to create the same patient in different practice");
		loginPage = new JalapenoLoginPage(driver, testData.getPractice2Url());
		assertTrue(loginPage.assessLoginPageElements());
		JalapenoCreateAccountPage createAccountPage = loginPage.clickSignInButton();
		
		assertTrue(createAccountPage.assessCreateAccountPageElements());
		
		JalapenoPatientActivationPage patientActivationPage = createAccountPage.fillInDataPage(
				patient.getFirstName(), patient.getLastName(), patient.getEmail(),
				testData);
		
		assertTrue(patientActivationPage.assessPatientActivationPageElements());
		log("Patient with same demographics was allowed");

		patientActivationPage.fillInPatientActivation(patient.getEmail(), patient.getPassword(),
				testData);

		assertTrue(patientActivationPage.assessPatientActivationPageElements());
		log("Username match was found");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6Inactive() throws Exception {

		logTestEnvironment();

		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		log("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		
		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
		String patientsEmail = IHGUtil.createRandomEmailAddress(testDataFromProp.getEmail(), '.');

		log("Patient Activation on Practice Portal");
		patientActivationSearchTest.getPatientActivationLink(driver, practiceTestData,
				patientsEmail, testDataFromProp);
		
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testDataFromProp.getUrl());
		assertTrue(loginPage.assessLoginPageElements());
		
		JalapenoCreateAccountPage createAccountPage = loginPage.clickSignInButton();
		assertTrue(createAccountPage.assessCreateAccountPageElements());
		
		createAccountPage.fillInDataPage(patientActivationSearchTest.getFirstNameString(),
				patientActivationSearchTest.getLastNameString(),
				patientActivationSearchTest.getEmailAddressString(), "1",
				PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear, Gender.MALE,
				patientActivationSearchTest.getZipCodeString());
		
		assertTrue(createAccountPage.assessCreateAccountPageElements());
	}
		
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequest() throws Exception {

		logTestEnvironment();

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.assessLoginPageElements());
		
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(),testData.getPassword());
		assertTrue(homePage.assessHomePageElements());

		JalapenoAppointmentRequestPage appointmentRequestPage = homePage.clickOnAppointment(
				driver);
		appointmentRequestPage.clickOnContinueButton(driver);
		assertTrue(appointmentRequestPage.fillAndSendTheAppointmentRequest(driver));
		
		homePage = appointmentRequestPage.returnToHomePage(driver);
		homePage.logout(driver);
		
		log("Login to Practice Portal");
		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(),
				testData.getDoctorPassword());

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestSearchPage.PAGE_NAME);

		log("Search for appt requests");
		apptSearch.searchForApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails("Illness");
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestDetailStep1Page.PAGE_NAME);

		log("Choose process option and respond to patient");
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmit();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestDetailStep2Page.PAGE_NAME);

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(),
				"Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();
				
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
				
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements());
		
		log("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved"));
	} 	

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrescriptionRenewal() throws Exception {
		logTestEnvironment();

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Creating a new patient");
		JalapenoPatient patient = new JalapenoPatient();
		JalapenoHomePage homePage = patient.createAndLogInPatient(driver, testData);
		assertTrue(homePage.assessHomePageElements());
				
		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		prescriptionsPage.clickContinueButton(driver);
		homePage = prescriptionsPage.fillThePrescription(driver, "XANAX", "21", 10);
		assertTrue(homePage.assessHomePageElements());
		
		homePage.logout(driver);
		
		log("Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		log("Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome.clickonRxRenewal();

		log("Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday();

		log("Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		log("Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.setRxRenewalFields();

		log("Click On Process RxRenewal Button in Practice Portal");
		rxRenewalSearchPage.clickProcessRxRenewal();

		String subject = rxRenewalSearchPage.getSubject();
		log("Verify Prescription Confirmation in Practice Portal");
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject);

		log("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		log("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		log("Logout of Practice Portal");
		practiceHome.logOut();
		
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patient.getEmail(), patient.getPassword());
				
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements());
		
		log("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPayBills() throws Exception {
		logTestEnvironment();

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Initiate payment data");	
		String accountNumber = IHGUtil.createRandomNumericString(8);
		String amount = IHGUtil.createRandomNumericString(3);

		log("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(),testData.getPassword());
		
		JalapenoNewPayBillsPage payBillsPage = homePage.clickOnNewPayBills(driver);
		assertTrue(payBillsPage.assessNewPayBillsElements());
		
		assertNotNull(payBillsPage.fillPaymentInfo(amount, accountNumber));
		homePage = payBillsPage.submitPayment();
		assertTrue(homePage.isNotificationDisplayed("Your payment was successful."));
		homePage.logout(driver);
		
		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();

		log("Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		log("Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage.searchForBillPayment(accountNumber);

		log("Get Bill Details");
		onlineBillPaySearchPage.getBillPayDetails();

		log("Set Payment Communication Details");
		onlineBillPaySearchPage.setPaymentCommunicationDetails();

		log("Logout of Practice Portal");
		practiceHome.logOut();
		
		String uniquePracticeResponse = Long.toString(onlineBillPaySearchPage.getCreatedTs())+PracticeConstants.BillPaymentSubject;
		
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		
		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		
		log("Waiting for message from practice portal");	
		assertTrue(messagesPage.isMessageDisplayed(driver, uniquePracticeResponse));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAStaff() throws Exception {
		logTestEnvironment();

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		
		log("Click Ask A Staff tab");
		JalapenoAskAStaffPage askPage = homePage.clickOnAskAStaff(driver);
		
		log("Fill and submit question");
		assertTrue(askPage.fillAndSubmitAskAStaff(driver));
		
		log("Check history");
		assertTrue(askPage.checkHistory(driver));
		
		log("Logout patient");
		askPage.backToHomePage(driver);
		assertTrue(homePage.assessHomePageElements());
		homePage.logout(driver);
		
		log("Login to practice portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		log("Click Ask A Staff tab");
		AskAStaffSearchPage searchQ = practiceHome.clickAskAStaffTab();

		log("Search for questions");
		searchQ.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = searchQ.getQuestionDetails("Ola! "+(Long.toString(askPage.getCreatedTimeStamp())));
		assertNotNull(detailStep1, "The submitted patient question was not found in the practice");
		
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep1Page.PAGE_NAME);
		log("Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1.chooseProvideAdviceOnly();

		log("Respond to patient question");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2.processAndCommunicate("Automated Test",
				"This message was generated by an automated test");
		
		log("Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3.confirmProcessedQuestion();

		log("Validate submit of confirmation");
		detailStep4.isQuestionDetailPageLoaded();

		log("Logout of Practice Portal");
		practiceHome.logOut();
		
		log("Login patient");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		
		log("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		
		log("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver,"Ola! "+(Long.toString(askPage.getCreatedTimeStamp()))));
	}
	
	// Create under-age patient, complete registration with new guardian, checks login credentials and then checks guardian email
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFACreateGuardianOnly() throws Exception {
		logTestEnvironment();
		
		log("Step 1: Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		long generatedTS = System.currentTimeMillis();
		String patientLastName = "last" + generatedTS; //guardian's and dependant's last name and dependant's id
		String patientLogin = "login" + generatedTS; //guardian's login
		String patientEmail = "mail" + generatedTS + "@mailinator.com"; //guardian's and dependant's email
		
		log("Step 2: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("Step 3: Create under-age patient");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		log("Step 4: Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		log("Step 5: Enter all the details and click on Register");
		String guardianUrl = patientActivationPage.setInitialDetailsAllFields("Dependant", patientLastName, "F", patientLastName, 
				testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage(), 
				"address1", "address2", "city", "Alabama", testData.getZipCode());
		
		log("Step 6: Continue to Portal Inspired");
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));
		JalapenoIdentifyDependantPage identifyDependantPage = new JalapenoIdentifyDependantPage(driver, guardianUrl);
		assertTrue(identifyDependantPage.assessElements());

		log("Step 7: Identify patient");
		identifyDependantPage.fillPatientIdentifyInfo(testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());
		
		log("Step 8: Continue registration - check dependant info and fill guardian name");
		JalapenoCreateGuardianPage createGuardianStep1 = identifyDependantPage.continueToCreateGuardianPage(driver);
		assertTrue(createGuardianStep1.checkDependantInfoRegisterPage("Dependant", patientLastName, patientEmail));
		createGuardianStep1.createGuardianOnlyFirstPage("Guardian", patientLastName, "Parent");
		
		log("Step 9: Continue registration - create dependants credentials");
		JalapenoCreateGuardianPage2 createGuardianStep2 = createGuardianStep1.continueToSecondPage(driver);
		assertTrue(createGuardianStep2.assessElements());
		createGuardianStep2.fillGuardianSecurityDetails(patientLogin, testData.getDoctorPassword(), testData.getSecretQuestion(), 
				testData.getSecretAnswer(), testData.getPhoneNumber());
		
		log("Step 10: Continue to Home Page");
		JalapenoHomePage homePage = createGuardianStep2.clickEnterPortal(driver);
		assertTrue(homePage.assessHomePageElements());
		assertTrue(homePage.assessFamilyAccountElements(false));
		
		log("Step 11: Log out and log in");
		JalapenoLoginPage loginPage = homePage.logout(driver);
		homePage = loginPage.login(patientLogin, testData.getPassword());
		assertTrue(homePage.assessHomePageElements());
		assertTrue(homePage.assessFamilyAccountElements(false));
		
		log("Step 12: Logging into Mailinator and getting Guardian Activation url");
		String emailSubject = "You are invited to create a Patient Portal guardian account at "
				+ testData.getPracticeName();
		String inEmail = "Sign Up!";
		String guardianUrlEmail = new Mailinator().getLinkFromEmail(patientEmail, emailSubject, inEmail, 10);
		assertNotNull(guardianUrlEmail, "Error: Activation link not found.");
		guardianUrlEmail = guardianUrlEmail.substring(0, guardianUrlEmail.length()-4); //because Jsoup.parse at Mailinator changes "&lang" to "?" (problem is in progress)
		log("Retrieved activation link is " + guardianUrlEmail);
		log("Comparing with link from PrP " + guardianUrl);
		assertTrue(guardianUrl.contains(guardianUrlEmail));
	}	
}
