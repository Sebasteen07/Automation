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
import com.intuit.ihg.product.object.maps.practice.page.patientMessaging.PatientMessagingPage;
import com.intuit.ihg.product.object.maps.practice.page.rxrenewal.RxRenewalSearchPage;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.medfusion.product.jalapeno.JalapenoCreatePatientTest;
import com.medfusion.product.jalapeno.JalapenoPatient;
import com.medfusion.product.object.maps.jalapeno.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.jalapeno.page.CcdViewer.JalapenoCcdPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage2;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoPatientActivationPage;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage2;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage3;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage4;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.jalapeno.page.MyAccountPage.JalapenoMyAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.PrescriptionsPage.JalapenoPrescriptionsPage;

import org.openqa.selenium.support.PageFactory;
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

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(jalapenoLoginPage.assessLoginPageElements());

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginValidCredentials() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), testData.getPassword());
		assertTrue(jalapenoHomePage.assessHomePageElements());
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginInvalidCredentials() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

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

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

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
		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		log("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);
		
		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
		
		log("Patient Activation on Practice Portal");
		String unlockLink = patientActivationSearchTest.getPatientActivationLink(driver,
				practiceTestData, testcasesData.getEmail(), testDataFromProp);

		log("Finishing of patient activation: step 1 - verifying identity");
		JalapenoPatientActivationPage patientActivationPage =
				new JalapenoPatientActivationPage(driver, unlockLink);

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
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testForgotPassword() throws Exception {
		
		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
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
		String url = mailinator.email(mailAddress[0], emailSubject, inEmail);
		
		assertTrue(url != null);
		
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
		
		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
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
		
		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
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
		//assertTrue(jalapenoCcdPage.sendInformation("ihg_qa@direct.healthvault.com"));
		
		jalapenoMessagesPage = jalapenoCcdPage.closeCcd(driver);
		assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		jalapenoHomePage = jalapenoMessagesPage.backToHomePage(driver);
		
		log("Logging out");
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6SamePractice() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

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
		createAccountPage.goToNextPage();

		assertTrue(loginPage.assessLoginPageElements());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6DifferentPractice() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
	
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

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		log("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);
		
		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
		
		log("Patient Activation on Practice Portal");
		patientActivationSearchTest.getPatientActivationLink(driver, practiceTestData,
				testcasesData.getEmail(), testDataFromProp.getDoctorLogin(),
				testDataFromProp.getDoctorPassword(), testDataFromProp.getPortalUrl());
		
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
		
		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Initiate patient data");
		JalapenoPatient createPatient = new JalapenoPatient();

		JalapenoHomePage homePage = createPatient.createAndLogInPatient(driver, testData);

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
				
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(createPatient.getEmail(), createPatient.getPassword());
				
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements());
		
		log("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved"));
	} 	

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrescriptionRenewal() throws Exception {
		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Initiate patient data");
		JalapenoCreatePatientTest createPatient = new JalapenoCreatePatientTest();
		createPatient.initPatientData(driver, testData);
		
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();
		assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());

		JalapenoCreateAccountPage2 jalapenoCreateAccountPage2 = jalapenoCreateAccountPage.fillInDataPage1(createPatient.getFirstName(), 
				createPatient.getLastName(), createPatient.getEmail(), testData.getDOBMonth(), testData.getDOBDay(), 
				testData.getDOBYear(), true, testData.getZipCode());
		assertTrue(jalapenoCreateAccountPage2.assessCreateAccountPage2Elements());
		
		JalapenoHomePage jalapenoHomePage = jalapenoCreateAccountPage2.fillInDataPage2(createPatient.getEmail(), createPatient.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getphoneNumer());
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		JalapenoPrescriptionsPage jalapenoPrescriptionsPage = jalapenoHomePage.clickOnPrescriptions(driver);
		jalapenoPrescriptionsPage.clickContinueButton(driver);
		jalapenoHomePage = jalapenoPrescriptionsPage.fillThePrescription(driver, "XANAX", "pills", 10);
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		jalapenoHomePage.logout(driver);
		
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
		
		jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		jalapenoHomePage = jalapenoLoginPage.login(createPatient.getEmail(), createPatient.getPassword());
				
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		log("Looking for appointment approval from doctor");
		assertTrue(jalapenoMessagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));	
	}
}
