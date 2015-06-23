package com.medfusion.jalapeno.test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.mail.Mailinator;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.patientMessaging.PatientMessagingPage;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.medfusion.product.jalapeno.JalapenoCreatePatientTest;
//import com.medfusion.product.jalapeno.JalapenoHealthKey6Of6DifferentPractice;
//import com.medfusion.product.jalapeno.JalapenoHealthKey6Of6Inactive;
//import com.medfusion.product.jalapeno.JalapenoHealthKey6Of6SamePractice;
//import com.medfusion.product.jalapeno.PreferenceDeliverySelection;
//import com.medfusion.product.jalapeno.PreferenceDeliverySelection.Method;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.CcdViewer.JalapenoCcdPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage2;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage2;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage3;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage4;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.jalapeno.page.MyAccountPage.JalapenoMyAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.PatientActivationPage.JalapenoPatientActivationPage;

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
		
		log("Checking if zipCode in My Account is filled");
		JalapenoMyAccountPage jalapenoMyAccountPage = jalapenoHomePage.clickOnMyAccount(driver);
		assertTrue(jalapenoMyAccountPage.checkForAddress(driver,testData.getZipCode()));
		
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		jalapenoHomePage = jalapenoLoginPage.login(createPatient.getEmail(), createPatient.getPassword());	
		//PreferenceDeliverySelection preferenceDeliverySelection = new PreferenceDeliverySelection();
		//jalapenoHomePage = preferenceDeliverySelection.SelectDeliveryMethod(driver, Method.ELECTRONIC);
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
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
		String unlockLink = patientActivationSearchTest.PatientActivation(driver, practiceTestData, testcasesData.getEmail(), 
				testDataFromProp.getDoctorLogin(), testDataFromProp.getDoctorPassword(), testDataFromProp.getPortalUrl());	
		
		log("Finishing of patient activation: step 1 - verifying identity");
		JalapenoPatientActivationPage jalapenoPatientActivationPage = new JalapenoPatientActivationPage(driver, unlockLink);
		jalapenoPatientActivationPage.verifyPatientIdentity(PracticeConstants.Zipcode, PortalConstants.DateOfBirthMonth,
				PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);
	
		log("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = jalapenoPatientActivationPage.fillInPatientActivation(patientActivationSearchTest.getPatientIdString(),
			testDataFromProp.getPassword(), testDataFromProp.getSecretQuestion(), 
			testDataFromProp.getSecretAnswer(), testDataFromProp.getphoneNumer());
		
		log("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		log("Checking if address in My Account is filled");
		JalapenoMyAccountPage jalapenoMyAccountPage = jalapenoHomePage.clickOnMyAccount(driver);
		assertTrue(jalapenoMyAccountPage.checkForAddress(driver, "5501 Dillard Dr", "Cary", PracticeConstants.Zipcode));
		
		log("Logging out");
		JalapenoLoginPage jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		log("Logging again: " + patientActivationSearchTest.getPatientIdString() + " \\ " + testDataFromProp.getPassword());
		jalapenoHomePage = jalapenoLoginPage.login(patientActivationSearchTest.getPatientIdString(),testDataFromProp.getPassword());	
	/*	
		log("Select PAPER delivery preference");
		PreferenceDeliverySelection preferenceDeliverySelection = new PreferenceDeliverySelection();
		jalapenoHomePage = preferenceDeliverySelection.SelectDeliveryMethod(driver, Method.PAPER);
	*/	
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
		JalapenoCreatePatientTest createPatient = new JalapenoCreatePatientTest();
		createPatient.initPatientData(driver, testData);
		
		log("Load Login Page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		
		JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();
		assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());

		JalapenoCreateAccountPage2 jalapenoCreateAccountPage2 = jalapenoCreateAccountPage.fillInDataPage1(createPatient.getFirstName(), 
				createPatient.getLastName(), createPatient.getEmail(), testData.getDOBMonth(), testData.getDOBDay(), 
				testData.getDOBYear(), true, testData.getZipCode());
		
		assertTrue(jalapenoCreateAccountPage2.assessCreateAccountPage2Elements());
		JalapenoHomePage jalapenoHomePage = jalapenoCreateAccountPage2.fillInDataPage2(createPatient.getEmail(), createPatient.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getphoneNumer());
		
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		log("Logout");
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		log("Login test of patient which has been created");
		jalapenoHomePage = jalapenoLoginPage.login(createPatient.getEmail(), createPatient.getPassword());	
		
		/*
		log("Choosing Preference Delivery");
		PreferenceDeliverySelection preferenceDeliverySelection = new PreferenceDeliverySelection();
		jalapenoHomePage = preferenceDeliverySelection.SelectDeliveryMethod(driver, Method.ELECTRONIC);
		*/
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		log("Logout");
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		log("Clicking on forgot username or password");
		JalapenoForgotPasswordPage jalapenoForgotPasswordPage = jalapenoLoginPage.clickForgotPasswordButton();
		
		assertTrue(jalapenoForgotPasswordPage.assessForgotPasswordPageElements());
		
		JalapenoForgotPasswordPage2 jalapenoForgotPasswordPage2 = jalapenoForgotPasswordPage.fillInDataPage(createPatient.getEmail());		
		log("Message was sent, closing");
		jalapenoLoginPage = jalapenoForgotPasswordPage2.clickCloseButton();
		
		log("Logging into Mailinator and getting ResetPassword url");
		Mailinator mailinator = new Mailinator();
		String[] mailAddress = createPatient.getEmail().split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";
		String url = mailinator.email(mailAddress[0], emailSubject, inEmail);
		
		assertTrue(url != null);
		
		JalapenoForgotPasswordPage3 jalapenoForgotPasswordPage3 = new JalapenoForgotPasswordPage3(driver, url);
		log("Redirecting to patient portal, filling secret answer as: "+testData.getSecretAnswer());
		JalapenoForgotPasswordPage4 jalapenoForgotPasswordPage4 = jalapenoForgotPasswordPage3.fillInSecretAnswer(testData.getSecretAnswer());
		
		log("Filling new password");
		jalapenoHomePage = jalapenoForgotPasswordPage4.fillInNewPassword(testData.getPassword());
		assertTrue(jalapenoHomePage.assessHomePageElements());	
		
		log("Logging out");
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMessaging() throws Exception {
		
		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Initiate patient data");
		JalapenoCreatePatientTest createPatient = new JalapenoCreatePatientTest();
		createPatient.initPatientData(driver, testData);
		
		log("Load Login Page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		
		JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();
		assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());

		JalapenoCreateAccountPage2 jalapenoCreateAccountPage2 = jalapenoCreateAccountPage.fillInDataPage1(createPatient.getFirstName(), 
				createPatient.getLastName(), createPatient.getEmail(), testData.getDOBMonth(), testData.getDOBDay(), 
				testData.getDOBYear(), true, testData.getZipCode());
		
		assertTrue(jalapenoCreateAccountPage2.assessCreateAccountPage2Elements());
		JalapenoHomePage jalapenoHomePage = jalapenoCreateAccountPage2.fillInDataPage2(createPatient.getEmail(), createPatient.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getphoneNumer());
		
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
				
		//login to practice portal
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		PatientMessagingPage patientMessagingPage = practiceHome.clickPatientMessagingTab();
		patientMessagingPage.setQuickSendFields(createPatient.getFirstName(), createPatient.getLastName(),"TestingMessage");
		
		jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		jalapenoHomePage = jalapenoLoginPage.login(createPatient.getEmail(), createPatient.getPassword());
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		log("Waiting for message from practice portal");
		assertTrue(jalapenoMessagesPage.isMessageFromDoctorDisplayed(driver));
		
		log("Response to the message");
		jalapenoMessagesPage.replyToMessage(driver);
		//TODO: system is unable to send a reply message, but message is sent
		
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
	JalapenoCreatePatientTest createPatient = new JalapenoCreatePatientTest();
	createPatient.initPatientData(driver, testData);
	
	log("Load Login Page");
	JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
	
	JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();
	assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());

	JalapenoCreateAccountPage2 jalapenoCreateAccountPage2 = jalapenoCreateAccountPage.fillInDataPage1(createPatient.getFirstName(), 
			createPatient.getLastName(), createPatient.getEmail(), testData.getDOBMonth(), testData.getDOBDay(), 
			testData.getDOBYear(), true, testData.getZipCode());
	
	assertTrue(jalapenoCreateAccountPage2.assessCreateAccountPage2Elements());
	JalapenoHomePage jalapenoHomePage = jalapenoCreateAccountPage2.fillInDataPage2(createPatient.getEmail(), createPatient.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getphoneNumer());
	
	assertTrue(jalapenoHomePage.assessHomePageElements());
	
	log("Checking if zipCode in My Account is filled");
	JalapenoMyAccountPage jalapenoMyAccountPage = jalapenoHomePage.clickOnMyAccount(driver);
	assertTrue(jalapenoMyAccountPage.checkForAddress(driver, testData.getZipCode()));
	
	jalapenoLoginPage = jalapenoHomePage.logout(driver);
	assertTrue(jalapenoLoginPage.assessLoginPageElements());
	
	log("Try to create the same patient");
	jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();
	assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());
	
	jalapenoLoginPage = jalapenoCreateAccountPage.fillInTheSameData(createPatient.getFirstName(), 
			createPatient.getLastName(), createPatient.getEmail(), testData.getDOBMonth(), testData.getDOBDay(), 
			testData.getDOBYear(), true, testData.getZipCode());
	
	assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6DifferentPractice() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
	
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Initiate patient data");
		JalapenoCreatePatientTest createPatient = new JalapenoCreatePatientTest();
		createPatient.initPatientData(driver, testData);
		
		log("Load Login Page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		
		JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();
		assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());
	
		JalapenoCreateAccountPage2 jalapenoCreateAccountPage2 = jalapenoCreateAccountPage.fillInDataPage1(createPatient.getFirstName(), 
				createPatient.getLastName(), createPatient.getEmail(), testData.getDOBMonth(), testData.getDOBDay(), 
				testData.getDOBYear(), true, testData.getZipCode());
		
		assertTrue(jalapenoCreateAccountPage2.assessCreateAccountPage2Elements());
		JalapenoHomePage jalapenoHomePage = jalapenoCreateAccountPage2.fillInDataPage2(createPatient.getEmail(), createPatient.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getphoneNumer());
		
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		log("Try to create the same patient in different practice");
		jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getPractice2Url());
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();
		
		assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());
		
		jalapenoCreateAccountPage2 = jalapenoCreateAccountPage.fillInDataPage1(createPatient.getFirstName(), 
				createPatient.getLastName(), createPatient.getEmail(), testData.getDOBMonth(), testData.getDOBDay(), 
				testData.getDOBYear(), true, testData.getZipCode());
		
		assertTrue(jalapenoCreateAccountPage2.assessCreateAccountPage2Elements());
		log("Patient with same demographics was allowed");
		
		jalapenoHomePage = jalapenoCreateAccountPage2.fillInDataPage2(createPatient.getEmail(), createPatient.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getphoneNumer());

		assertTrue(jalapenoCreateAccountPage2.assessCreateAccountPage2Elements());
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
		patientActivationSearchTest.PatientActivation(driver, practiceTestData, testcasesData.getEmail(), 
				testDataFromProp.getDoctorLogin(), testDataFromProp.getDoctorPassword(), testDataFromProp.getPortalUrl());	
		
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testDataFromProp.getUrl());
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();
		assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());
		
		jalapenoCreateAccountPage.fillInDataPage1(patientActivationSearchTest.getFirstNameString(),
				patientActivationSearchTest.getLastNameString(), patientActivationSearchTest.getEmailAddressString(), "1",
				PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear, true, patientActivationSearchTest.getZipCodeString());
		
		assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());
	}
				
}
