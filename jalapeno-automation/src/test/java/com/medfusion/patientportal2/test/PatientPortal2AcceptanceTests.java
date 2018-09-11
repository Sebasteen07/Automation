package com.medfusion.patientportal2.test;

import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.pojo.ExpectedEmail;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2HistoryPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step1;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step2;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.DocumentsPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.AuthUserLinkAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage.JalapenoForgotPasswordPage;
import com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage.JalapenoForgotPasswordPage2;
import com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage.JalapenoForgotPasswordPage3;
import com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage.JalapenoForgotPasswordPage4;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountSecurityPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;
import com.medfusion.product.object.maps.patientportal2.page.SymptomAssessment.JalapenoSymptomAssessmentPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep1Page;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep2Page;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep3Page;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep4Page;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffSearchPage;
import com.medfusion.product.object.maps.practice.page.familyManagement.AgeOutReportPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.object.maps.practice.page.patientMessaging.PatientMessagingPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.object.maps.practice.page.rxrenewal.RxRenewalSearchPage;
import com.medfusion.product.object.maps.practice.page.symptomassessment.SymptomAssessmentDetailsPage;
import com.medfusion.product.object.maps.practice.page.symptomassessment.SymptomAssessmentFilterPage;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;
import com.medfusion.product.patientportal2.tests.CommonSteps;
import com.medfusion.product.patientportal2.utils.PortalConstants;
import com.medfusion.product.patientportal2.utils.PortalUtil;
import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.PracticeTestData;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.api.utils.PracticeUtil;
import com.medfusion.product.practice.tests.AppoitmentRequest;
import com.medfusion.product.practice.tests.PatientActivationSearchTest;
import com.medfusion.qa.mailinator.Email;
import com.medfusion.qa.mailinator.Mailer;

@Test
public class PatientPortal2AcceptanceTests extends BaseTestNGWebDriver {

	private static final String NEW_EMAIL_TEMPLATE = "jalapeno2.%s@mailinator.com";
	private static final String NEW_USERNAME_TEMPLATE = "automation%s";
	private static final String NAME_OF_FIRST_PET_SECURITY_QUESTION = "What was the name of your first pet?";
	private static final String NAME_OF_FIRST_PET_SECURITY_QUESTION_ANSWER = "Jerry";
	private static final String NEW_PASSWORD = "Idkfa12.";
	private static final String ACCOUNT_CHANGE_NOTIFICATION_EMAIL_SUBJECT_TEMPLATE = "Verify your account with %s";
	private static final String EMAIL_ADDRESS_CHANGE_NOTIFICATION_EMAIL_CONTENT = "email address associated with your Patient Portal account has been changed";
	private static final String PASSWORD_CHANGE_NOTIFICATION_EMAIL_CONTENT = "You recently changed your Patient Portal password.";
	private static final String USERNAME_CHANGE_NOTIFICATION_EMAIL_CONTENT = "User ID associated with your Patient Portal account has been changed";
	private static final String DIRECT_EMAIL_ADDRESS = "medfusionqa@service2.directaddress.net";
	private static final String INVITE_EMAIL_SUBJECT_PATIENT = "You're invited to create a Patient Portal account at ";
	private static final String INVITE_EMAIL_SUBJECT_REPRESENTATIVE = "You're invited to create a Portal account to be a trusted representative of a patient at ";
	private static final String INVITE_EMAIL_BUTTON_TEXT = "Sign Up!";
	private static final String INVITE_LINK_FRAGMENT = "invite";
	
	PropertyFileLoader testData;

	// TODO move stuff around stepCounter to BaseTestNGWebDriver
	int stepCounter;
	Instant testStart;

	@Override
	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		super.setUp();

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		testData = new PropertyFileLoader();

		log("Resetting step counter");
		stepCounter = 0;
		testStart = Instant.now();
	}

	private void logStep(String logText) {
		log("STEP " + ++stepCounter + ": " + logText);
	}
	private long testSecondsTaken() {
		return testStart.until(Instant.now(), ChronoUnit.SECONDS);
	}
	
	private String getRedirectUrl(String originUrl) {
		log("Navigating to input URL and checking redirection for 10 seconds");
		driver.get(originUrl);
		for(int i = 0; i < 10; i++){
			if (driver.getCurrentUrl() != originUrl) {
				log("Found redirected URL: " + driver.getCurrentUrl());
				return driver.getCurrentUrl();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
		
	}
	/**
	 * verifies if the invite url contains a specific keyword(INVITE_LINK_FRAGMENT), if not, it is a redirect url (e.g.from sendinblue)
	 * @return
	 */
	private boolean isInviteLinkFinal(String url){
		boolean ret = url.contains(INVITE_LINK_FRAGMENT);
		if (ret) log("The retrieved link is a redirect URL : " + url);
		return ret;
	}

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}
	
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testAssessLoginPageElements() throws Exception {
		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginValidCredentialsAndValidateMenuElements() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Fill in credentials and log in");
		JalapenoHomePage jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Validate menu elements");
		assertTrue(jalapenoHomePage.areMenuElementsPresent());

		logStep("Log out");
		loginPage = jalapenoHomePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());
	}
	/**
  TODO: Uncomment when someone merges development into master at qa-main repository
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSendAnEmailAndVerifyIfDelivered() throws Exception {
		logStep("Send an email");
		EmailRest email = new EmailRest();
		EmailBodyPojo emailBody = new EmailBodyPojo();
		String postUrl = "http://" + testData.getPracticeSvcsServer() + "/practice-svcs/services/message/practice/" + testData.getPracticeId();
		String emailSubject = "Test Email " + System.currentTimeMillis();
		String urlInEmail = "https://medfusion.com/";

		emailBody.setSenderValue(testData.getPracticeStaffId());
		emailBody.setRecipientValue(testData.getPracticeMemberId());
		emailBody.setSubject(emailSubject);
		emailBody.setHtmlBody("<a href='" + urlInEmail + "'>Click here!</a>");
		emailBody.setTextBody("");
		HttpResponse response = email.sendAnEmail(postUrl, emailBody);

		logStep("Check status code of request");
		log(response.toString());
		assertEquals(response.getStatusLine().getStatusCode(), 200, "Error: Response status code is not 200!");

		logStep("Check if email was delivered");
		String elementContainingString = new Mailinator().getLinkFromEmail(testData.getUserEmail(), emailSubject, "Click here!", 120);
		assertNotNull(elementContainingString, "Error: Email or email body was not found.");
	}
**/
	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginInvalidCredentials() throws Exception {

		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		jalapenoLoginPage.loginUnsuccessfuly(testData.getUserId(), "InvalidPassword");
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatient() throws Exception {

		JalapenoPatient jalapenoPatient = new JalapenoPatient(testData);

		logStep("Creating a new patient");
		JalapenoPatient patient = new JalapenoPatient(testData);
		JalapenoHomePage homePage = CommonSteps.createAndLogInPatient(jalapenoPatient, testData, driver,
				patient.getUrl());

		logStep("Checking if the information are correct");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage myAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(myAccountPage.checkZipCode(testData.getZipCode()));
		assertTrue(myAccountPage.checkGender(jalapenoPatient.getGender()));

		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());

		homePage = loginPage.login(jalapenoPatient.getEmail(), jalapenoPatient.getPassword());
		assertTrue(homePage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientActivation() throws Exception {

		logStep("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
		String patientsEmail = IHGUtil.createRandomEmailAddress(testDataFromProp.getEmail(), '.');

		logStep("Patient Activation on Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationLink(driver, practiceTestData, patientsEmail, testDataFromProp);

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(PracticeConstants.Zipcode,
				PortalConstants.DateOfBirthMonthNumber, PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage =
				accountDetailsPage.fillAccountDetailsAndContinue(patientActivationSearchTest.getPatientIdString(), testDataFromProp.getPassword(), testDataFromProp);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Checking if address in My Account is filled");
		JalapenoAccountPage accountPage = jalapenoHomePage.clickOnAccount();
		JalapenoMyAccountProfilePage jalapenoMyAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(jalapenoMyAccountPage.checkForAddress(driver, "5501 Dillard Dr", "Cary", PracticeConstants.Zipcode));

		logStep("Logging out");
		JalapenoLoginPage jalapenoLoginPage = jalapenoHomePage.clickOnLogout();
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());

		logStep("Logging again: " + patientActivationSearchTest.getPatientIdString() + " \\ " + testDataFromProp.getPassword());
		jalapenoHomePage = jalapenoLoginPage.login(patientActivationSearchTest.getPatientIdString(), testDataFromProp.getPassword());

		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());
		logStep("Logging out");

		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());

		logStep("Logging into Mailinator and getting Patient Activation url");				
		String unlockLinkEmail = new Mailinator().getLinkFromEmail(patientsEmail, INVITE_EMAIL_SUBJECT_PATIENT + testDataFromProp.getPracticeName(), INVITE_EMAIL_BUTTON_TEXT, 10);
		assertNotNull(unlockLinkEmail, "Error: Activation link not found.");
		logStep("Retrieved activation link is " + unlockLinkEmail);
		logStep("Comparing with portal unlock link " + unlockLinkPortal);
		assertEquals(unlockLinkEmail, unlockLinkPortal, "!patient unlock links are not equal!");
	}

	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testForgotPassword() throws Exception {

		logStep("Initiate patient data");
		JalapenoPatient patient = new JalapenoPatient(testData);
		JalapenoHomePage homePage = CommonSteps.createAndLogInPatient(patient, testData, driver, patient.getUrl());

		logStep("Logout");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Clicking on forgot username or password");
		JalapenoForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordButton();

		assertTrue(forgotPasswordPage.assessForgotPasswordPageElements());

		JalapenoForgotPasswordPage2 forgotPasswordPage2 = forgotPasswordPage.fillInDataPage(patient.getEmail());
		logStep("Message was sent, closing");
		forgotPasswordPage2.clickCloseButton();

		logStep("Logging into Mailinator and getting ResetPassword url");
		Mailinator mailinator = new Mailinator();
		String[] mailAddress = patient.getEmail().split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";
		String url = mailinator.getLinkFromEmail(mailAddress[0], emailSubject, inEmail, 15);

		assertTrue(url != null);

		JalapenoForgotPasswordPage3 forgotPasswordPage3 = new JalapenoForgotPasswordPage3(driver, url);
		logStep("Redirecting to patient portal, filling secret answer as: " + testData.getSecretAnswer());
		JalapenoForgotPasswordPage4 forgotPasswordPage4 = forgotPasswordPage3.fillInSecretAnswer(testData.getSecretAnswer());

		logStep("Filling new password");
		homePage = forgotPasswordPage4.fillInNewPassword(testData.getPassword());
		assertTrue(homePage.areBasicPageElementsPresent());

		logStep("Logging out");
		loginPage = homePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMessaging() throws Exception {

		String messageSubject = "Namaste " + System.currentTimeMillis();

		logStep("Login physician");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Send a new secure message to static patient");
		PatientMessagingPage patientMessagingPage = practiceHome.clickPatientMessagingTab();
		patientMessagingPage.setFieldsAndPublishMessage(testData, "TestingMessage", messageSubject);
		
		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent());

		logStep("Waiting for message from practice portal");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageSubject));

		logStep("Response to the message");
		assertTrue(messagesPage.replyToMessage(driver));

		logStep("Back to the practice portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		patientMessagingPage = practiceHome.clickPatientMessagingTab();
		assertTrue(patientMessagingPage.findMyMessage(messageSubject));
		// TODO: Edit navigation on portal and search by date to search exact
		// day
	}

	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testViewCCD() throws Exception {

		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(), testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		JalapenoCcdViewerPage jalapenoCcdPage = jalapenoMessagesPage.findCcdMessage(driver);

		assertTrue(jalapenoCcdPage.areBasicPageElementsPresent());
		if ((IHGUtil.getEnvironmentType().toString().equals("DEV3")) || (IHGUtil.getEnvironmentType().toString().equals("QA1"))) {
			log("Skipping method checkPdfToDownload and checkRawToDownload because of known issue on DEV3&&QA1 javax.net.ssl.SSLHandshakeException");
		} else {
			assertTrue(jalapenoCcdPage.checkPdfToDownload(driver));
			assertTrue(jalapenoCcdPage.checkRawToDownload(driver));
		}
		assertTrue(jalapenoCcdPage.sendInformationToDirectEmail(DIRECT_EMAIL_ADDRESS));

		jalapenoMessagesPage = jalapenoCcdPage.closeCcd(driver);
		assertTrue(jalapenoMessagesPage.areBasicPageElementsPresent());

		jalapenoHomePage = jalapenoMessagesPage.backToHomePage(driver);

		logStep("Logging out");
		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());
	}
	
	@Test(enabled = false, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSendCCDToNonSecureEmail() throws Exception {
		String email = System.currentTimeMillis() + "unsecure@mailinator.com";
		
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(), testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		JalapenoCcdViewerPage jalapenoCcdPage = jalapenoMessagesPage.findCcdMessage(driver);

		assertTrue(jalapenoCcdPage.areBasicPageElementsPresent());
		
		assertTrue(jalapenoCcdPage.sendInformationToUnsecureEmail(email));
		
		//TODO: Check if email arrived once is in use
	}
	
	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthRecords() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(), testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Navigate to Medical Record Summaries Page");
		MedicalRecordSummariesPage recordSummaries = jalapenoHomePage.clickOnMedicalRecordSummaries(driver);
		
		logStep("Send CCD if there is CCD older than 7 days");
		recordSummaries.sendCCDIfNewestIsOlderThan(7);
		
		logStep("Set filter to third visible CCD");
		recordSummaries.setFilterToThirdCCDDate();
		
		logStep("Select and send CCD to Direct email address");
		recordSummaries.selectFirstVisibleCCD();
		recordSummaries.sendFirstVisibleCCDUsingDirectProtocol(DIRECT_EMAIL_ADDRESS);
		
		logStep("Set filter to default position and check page elements");
		recordSummaries.setFilterToDefaultPositionAndCheckElements();
		
		logStep("Go to Documents tab");
		DocumentsPage documentsPage = recordSummaries.gotoOtherDocumentTab();
		//assertTrue(documentsPage.areBasicPageElementsPresent());
		
	}

	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthRecordsDocuments() throws Exception {
	
		String messageSubject = "Document " + System.currentTimeMillis();

		logStep("Login physician");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("Getting the document to upload from filepath");
		PracticeUtil pUtil = new PracticeUtil(driver);
		String value = pUtil.getFilepath(testData.getProperty("fileDirectory")).concat(testData.getProperty("healthRecordsSendPdfFileName"));
		
		log("Change name of the file");
		File originalDocument = new File(value);
		
		File tmpDocument = File.createTempFile("Document", ".pdf");
		tmpDocument.deleteOnExit();
		FileUtils.copyFile(originalDocument, tmpDocument);				
		
		logStep("Send a new secure message with attachment to static patient");
		PatientMessagingPage patientMessagingPage = practiceHome.clickPatientMessagingTab();
		patientMessagingPage.setFieldsAndPublishMessageWithFile(testData, "TestingMessage", messageSubject, tmpDocument.toString());
		
		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("documentsPatientUserId"), testData.getPassword());
	
		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		
		logStep("Waiting for message from practice portal");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageSubject));
		
		logStep("Go to Documents tab");
		DocumentsPage documentsPage = messagesPage.goToDocumentsPage();
		 
		
		logStep("Check if doccument from received message is displayed on Documents page");
		assertTrue(documentsPage.checkLastImportedFileName(tmpDocument.getName()));
		
	}
		
	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6SamePractice() throws Exception {

		logStep("Initiate patient data");
		JalapenoPatient patient = new JalapenoPatient(testData);
		JalapenoHomePage homePage = CommonSteps.createAndLogInPatient(patient, testData, driver, patient.getUrl());

		logStep("Checking if zipCode in My Account is filled");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage myAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(myAccountPage.checkZipCode(testData.getZipCode()));

		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Try to create the same patient");
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		assertTrue(patientDemographicPage.areBasicPageElementsPresent());

		patientDemographicPage.fillInPatientData(patient);

		patientDemographicPage.tryToContinueToSecurityPage();
		assertTrue(loginPage.areBasicPageElementsPresent());
		logStep("Username match was found");
	}

	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6DifferentPractice() throws Exception {

		logStep("Create patient");
		JalapenoPatient patient = new JalapenoPatient(testData);
		JalapenoHomePage homePage = CommonSteps.createAndLogInPatient(patient, testData, driver, patient.getUrl());

		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Try to create the same patient in different practice");
		loginPage = new JalapenoLoginPage(driver, testData.getPractice2Url());
		assertTrue(loginPage.areBasicPageElementsPresent());
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();

		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		logStep("Patient with same demographics was allowed");

		accountDetailsPage.fillAccountDetailsAndContinueWithError(patient.getEmail(), patient.getPassword(), testData);
		assertTrue(accountDetailsPage.areBasicPageElementsPresent());
		logStep("Username match was found");
	}

	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6Inactive() throws Exception {

		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		logStep("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
		String patientsEmail = IHGUtil.createRandomEmailAddress(testDataFromProp.getEmail(), '.');

		logStep("Patient Activation on Practice Portal");
		patientActivationSearchTest.getPatientActivationLink(driver, practiceTestData, patientsEmail, testDataFromProp);

		logStep("Going to PI login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testDataFromProp.getUrl());
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Going to create account page");
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		assertTrue(patientDemographicPage.areBasicPageElementsPresent());

		logStep("Creating patient with the same data as in practice portal");
		patientDemographicPage.fillInPatientData(patientActivationSearchTest.getFirstNameString(), patientActivationSearchTest.getLastNameString(),
				patientActivationSearchTest.getEmailAddressString(), PortalConstants.DateOfBirthMonth, PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear,
				Gender.MALE, patientActivationSearchTest.getZipCodeString());

		logStep("Checking that I am still on create account page due to healthKey check won't let me create patient with the same data");
		assertTrue(patientDemographicPage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequest() throws Exception {

		String appointmentReason = "Illness" + System.currentTimeMillis();
		
		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Create an appointment request");
		JalapenoAppointmentRequestPage appointmentRequestPage = homePage.clickOnAppointment(driver);
		appointmentRequestPage.clickOnContinueButton(driver);
		assertTrue(appointmentRequestPage.fillAndSendTheAppointmentRequest(driver, appointmentReason));

		logStep("Logout from Patient Portal");
		homePage = appointmentRequestPage.returnToHomePage(driver);
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal =
				practicePortal.ProceedAppoitmentRequest(driver, false, appointmentReason, testData.getPortalUrl(), testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Continue in Portal Inspired");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved " + tsPracticePortal));
	}

	// TODO: after Appointment Request v1 is not used - delete test above and
	// set up this test to
	// Jalapeno Automation
	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestV2() throws Exception {

		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());

		logStep("Logging in");
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click appointment request");
		JalapenoAppointmentRequestV2Step1 appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);

		logStep("Assess Elements and choose provider");
		assertTrue(appointmentRequestStep1.assessElements());
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2.: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.assessElements());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.assessElements());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.returnToHomePage(driver);
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequest(driver, true, appointmentReason, testData.getPortalUrl(), testData.getDoctorLogin2(),
				testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved " + tsPracticePortal));
	}

	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPrescriptionRenewal() throws Exception {

		logStep("Creating a new patient");
		JalapenoPatient patient = new JalapenoPatient(testData);
		JalapenoHomePage homePage = CommonSteps.createAndLogInPatient(patient, testData, driver, patient.getUrl());

		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(15000);
		prescriptionsPage.clickContinueButton(driver);
		Thread.sleep(15000);
		homePage = prescriptionsPage.fillThePrescription(driver, "XANAX", "21", 10);

		homePage.clickOnLogout();

		logStep("Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome.clickonRxRenewal();

		logStep("Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday();

		logStep("Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		logStep("Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.setRxRenewalFields();

		logStep("Click On Process RxRenewal Button in Practice Portal");
		rxRenewalSearchPage.clickProcessRxRenewal();

		String subject = rxRenewalSearchPage.getSubject();
		logStep("Verify Prescription Confirmation in Practice Portal");
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject);

		logStep("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patient.getEmail(), patient.getPassword());

		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));
	}

	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPayBills() throws Exception {

		logStep("Initiate payment data");
		String accountNumber = IHGUtil.createRandomNumericString(8);
		String amount = IHGUtil.createRandomNumericString(3);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		// remove all cards because Selenium can't see AddNewCard button
		payBillsPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(payBillsPage.isAnyCardPresent());
		assertTrue(payBillsPage.assessPayBillsMakePaymentPageElements());

		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, accountNumber, creditCard);
		assertTrue(confirmationPage.assessPayBillsConfirmationPageElements());
		logStep("Verifying credit card ending");
		assertTrue(confirmationPage.getCreditCardEnding().equals(creditCard.getLastFourDigits()));

		homePage = confirmationPage.commentAndSubmitPayment("Testing payment from number: " + accountNumber);
		assertTrue(homePage.wasPayBillsSuccessfull());
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();

		logStep("Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		logStep("Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage.searchForBillPayment(accountNumber);

		logStep("Get Bill Details");
		onlineBillPaySearchPage.getBillPayDetails();

		logStep("Set Payment Communication Details");
		onlineBillPaySearchPage.setPaymentCommunicationDetails();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		String uniquePracticeResponse = Long.toString(onlineBillPaySearchPage.getCreatedTs()) + PracticeConstants.BillPaymentSubject;

		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Waiting for message from practice portal");
		assertTrue(messagesPage.isMessageDisplayed(driver, uniquePracticeResponse));
	}

	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAStaff() throws Exception {

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffPage askPage = homePage.clickOnAskAStaff(driver);

		logStep("Fill and submit question");
		assertTrue(askPage.fillAndSubmitAskAStaff(driver));

		logStep("Check history");
		assertTrue(askPage.checkHistory(driver));

		logStep("Logout patient");
		askPage.backToHomePage(driver);
		assertTrue(homePage.areBasicPageElementsPresent());
		homePage.clickOnLogout();

		logStep("Login to practice portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click Ask A Staff tab");
		AskAStaffSearchPage searchQ = practiceHome.clickAskAStaffTab();

		logStep("Search for questions");
		searchQ.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = searchQ.getQuestionDetails("Ola! " + (Long.toString(askPage.getCreatedTimeStamp())));
		assertNotNull(detailStep1, "The submitted patient question was not found in the practice");

		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep1Page.PAGE_NAME);
		logStep("Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1.chooseProvideAdviceOnly();

		logStep("Respond to patient question");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2.processAndCommunicate("Automated Test", "This message was generated by an automated test");

		logStep("Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3.confirmProcessedQuestion();

		logStep("Validate submit of confirmation");
		assertTrue(detailStep4.isQuestionDetailPageLoaded());

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Login patient");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Automated Test " + (Long.toString(detailStep2.getCreatedTimeStamp()))));
	}

	// Create under-age patient, complete registration with new guardian, checks
	// login credentials and
	// then checks
	// guardian email
	@Test(enabled = true, groups = {"acceptance-linkedaccounts"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLACreateGuardianOnly() throws Exception {

		long generatedTS = System.currentTimeMillis();
		String patientLogin = "login" + generatedTS; // guardian's login
		
		int i = 0;
		while(!PortalUtil.checkUsernameEmailIsUnique(patientLogin, testData.getProperty("credentialsMatcherUrl"))){
			assertTrue(i++ < 10,"Username was not unique after 10 attempts");
			generatedTS = System.currentTimeMillis();
			patientLogin = "login" + generatedTS;
		}
		String patientLastName = "last" + generatedTS; //lastname for both		
		String patientEmail = "mail" + generatedTS + "@mailinator.com"; //email for both

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Create under-age patient");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Enter all the details and click on Register");
		String guardianUrl =
				patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "F", patientLastName, testData.getPhoneNumber(), patientEmail,
						testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama", testData.getZipCode());

		logStep("Continue to Portal Inspired");
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, guardianUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(testData.getZipCode(), testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill guardian name");
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, patientEmail);
		SecurityDetailsPage accountDetailsPage = linkAccountPage.continueToCreateGuardianOnly("Guardian", patientLastName, "Parent");

		logStep("Continue registration - create dependents credentials and continue to Home page");
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin, testData.getPassword(), testData.getSecretQuestion(),
				testData.getSecretAnswer(), testData.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		homePage = loginPage.login(patientLogin, testData.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));

		homePage.clickOnLogout();

		logStep("Using mailinator Mailer to retrieve the latest emails for patient and guardian");		
		
		String emailSubjectGuardian = "You are invited to create a Patient Portal guardian account at " + testData.getPracticeName();			
		Email emailGuardian = new Mailer(patientEmail).pollForNewEmailWithSubject(emailSubjectGuardian, 30, testSecondsTaken());
		assertNotNull(emailGuardian, "Error: No email found for guardian recent enough and with specified subject: " + emailSubjectGuardian);		
		String guardianUrlEmail = Mailer.getLinkByText(emailGuardian, INVITE_EMAIL_BUTTON_TEXT);
		assertNotNull(guardianUrlEmail, "Error: No matching link found in guardian invite email!");	
		//SendInBlue workaround, go through the redirect and save the actual URL if the invite link does not contain a specific string
		if (!isInviteLinkFinal(guardianUrlEmail)){
			guardianUrlEmail = getRedirectUrl(guardianUrlEmail);
		}
		logStep("Retrieved dependents activation link is " + guardianUrlEmail);
		logStep("Comparing with dependents link from PrP " + guardianUrl);
		assertEquals(guardianUrl, guardianUrlEmail, "Practice portal and email unlock links for guardian are not equal!");
	}

	@Test(enabled = true, groups = {"acceptance-linkedaccounts"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLACreateDependentAndGuardian() throws Exception {

		long generatedTS = System.currentTimeMillis();
		String patientLogin = "login" + generatedTS; // guardian login
		
		int i = 0;
		while(!PortalUtil.checkUsernameEmailIsUnique(patientLogin, testData.getProperty("credentialsMatcherUrl"))){
			assertTrue(i++ < 10,"Username was not unique after 10 attempts");
			generatedTS = System.currentTimeMillis();
			patientLogin = "login" + generatedTS;
		}
		
		String patientLastName = "last" + generatedTS;		
		String patientEmail = "mail" + generatedTS + "@mailinator.com";

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Register Guardian - Enter all the details and click on Register");
		String patientUrl = patientActivationPage.setInitialDetailsAllFields("Guardian", patientLastName, "F", patientLastName + "G", testData.getPhoneNumber(),
				patientEmail, testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear(), "address1", "address2", "city", "Alabama", testData.getZipCode());

		logStep("Register Dependent - Enter all the details and click on Register");
		String guardianUrl =
				patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "M", patientLastName + "D", testData.getPhoneNumber(), patientEmail,
						testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama", testData.getZipCode());
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);
		SecurityDetailsPage accountDetailsPage =
				patientVerificationPage.fillPatientInfoAndContinue(testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin, testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(guardianUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(testData.getZipCode(), testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill login credentials");
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, patientEmail);
		jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(patientLogin, testData.getPassword(), "Parent");

		logStep("Continue to the portal and check elements");
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

		logStep("Logout, login and change patient");
		JalapenoLoginPage loginPage = jalapenoHomePage.clickOnLogout();
		jalapenoHomePage = loginPage.login(patientLogin, testData.getPassword());
		jalapenoHomePage.faChangePatient();
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

		logStep("Using mailinator Mailer to retrieve the latest emails for patient and guardian");		
		
		String emailSubjectGuardian = "You are invited to create a Patient Portal guardian account at " + testData.getPracticeName();			
		Email emailGuardian = new Mailer(patientEmail).pollForNewEmailWithSubject(emailSubjectGuardian, 30, testSecondsTaken());
		assertNotNull(emailGuardian, "Error: No email found for guardian recent enough and with specified subject: " + emailSubjectGuardian);		
		String guardianUrlEmail = Mailer.getLinkByText(emailGuardian, INVITE_EMAIL_BUTTON_TEXT);
		assertNotNull(guardianUrlEmail, "Error: No matching link found in guardian invite email!");	
		//SendInBlue workaround, go through the redirect and save the actual URL if the invite link does not contain a specific string
		if (!isInviteLinkFinal(guardianUrlEmail)){
			guardianUrlEmail = getRedirectUrl(guardianUrlEmail);
		}
		logStep("Retrieved dependents activation link is " + guardianUrlEmail);
		logStep("Comparing with dependents link from PrP " + guardianUrl);
		assertEquals(guardianUrl, guardianUrlEmail, "Practice portal and email unlock links for guardian are not equal!");
		
		String emailSubjectPatient = INVITE_EMAIL_SUBJECT_PATIENT + testData.getPracticeName();
		Email emailPatient = new Mailer(patientEmail).pollForNewEmailWithSubject(emailSubjectPatient, 30, testSecondsTaken());
		assertNotNull(emailPatient, "Error: No email found for patient recent enough and with specified subject: " + emailSubjectPatient);		
		String patientUrlEmail = Mailer.getLinkByText(emailPatient, INVITE_EMAIL_BUTTON_TEXT);
		assertNotNull(patientUrlEmail, "Error: No matching link found in dependent invite email!");	
		//SendInBlue workaround, go through the redirect and save the actual URL if the invite link does not contain a specific string
		if (!isInviteLinkFinal(patientUrlEmail)){
			patientUrlEmail = getRedirectUrl(patientUrlEmail);
		}
		logStep("Retrieved patients activation link is " + patientUrl);
		logStep("Comparing with patients link from PrP " + patientUrlEmail);
		assertEquals(patientUrl, patientUrlEmail, "Practice portal and email unlock links for dependent are not equal!");


	}

	// This test uses under-age patients created at tests
	// FACreateDependentAndGuardian and
	// FACreateGuardianOnly
	@Test(enabled = true, groups = {"acceptance-linkedaccounts"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLAPostAgeOutFlow() throws Exception {

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Family Management");
		AgeOutReportPage AOPage = practiceHome.clickFamilyManagementTab();

		logStep("Age-out first patient");
		AOPage.searchInAgeOutReport(true, false, testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());
		String name = AOPage.ageOutFirstPatient();
		logStep("Patients name: " + name);

		logStep("Proceed to his dashboard");
		AOPage.searchInAgeOutReport(false, true, testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());
		PatientDashboardPage patientDashboard = AOPage.findPatientInList(name);
		String id = name.replaceAll("[^0-9]", "");
		String email = "mail" + id + "@mailinator.com";

		logStep("Change patients email to " + email);
		PatientSearchPage patientSearch = patientDashboard.clickEditEmail();
		patientDashboard = patientSearch.changeEmailWithoutModify(email);

		logStep("Send post age-out invitation");
		patientDashboard.sendPostAgeOutInvitation();

		logStep("Wait for email");
		String emailSubject = "Invitation to join our patient portal at " + testData.getPracticeName();
		String patientUrlEmail = new Mailinator().getLinkFromEmail(email, emailSubject, INVITE_EMAIL_BUTTON_TEXT, 15);
		assertNotNull(patientUrlEmail, "Error: Activation patients link not found.");
		logStep("Retrieved patients activation link is " + patientUrlEmail);

		logStep("Identify patient");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrlEmail);
		SecurityDetailsPage accountDetailsPage =
				patientVerificationPage.fillPatientInfoAndContinue(testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Create patient");
		String login = "selflogin" + id;
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(login, testData.getPassword(), testData);
		homePage.areBasicPageElementsPresent();
		homePage.isTextDisplayed("You have successfully created your account and can access all of your previous health information.");

		logStep("Logout and login patient");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		homePage = loginPage.login(login, testData.getPassword());
		homePage.areBasicPageElementsPresent();
	}
	
	@Test(enabled = true, groups = {"acceptance-linkedaccounts"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLADocumentsAccess() throws Exception {

		long generatedTS = System.currentTimeMillis();
		String patientLogin = "login" + generatedTS; // guardian's login
		
		int i = 0;
		while(!PortalUtil.checkUsernameEmailIsUnique(patientLogin, testData.getProperty("credentialsMatcherUrl"))){
			assertTrue(i++ < 10,"Username was not unique after 10 attempts");
			generatedTS = System.currentTimeMillis();
			patientLogin = "login" + generatedTS;
		}
		String patientLastName = "last" + generatedTS; //lastname for both		
		String patientEmail = "mail" + generatedTS + "@mailinator.com"; //email for both

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Create under-age patient");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Enter all the details and click on Register");
		String guardianUrl =
				patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "F", patientLastName, testData.getPhoneNumber(), patientEmail,
						testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama", testData.getZipCode());

		logStep("Continue to Portal Inspired");
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, guardianUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(testData.getZipCode(), testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill guardian name");
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, patientEmail);
		SecurityDetailsPage accountDetailsPage = linkAccountPage.continueToCreateGuardianOnly("Guardian", patientLastName, "Parent");

		logStep("Continue registration - create dependents credentials and continue to Home page");
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin, testData.getPassword(), testData.getSecretQuestion(),
				testData.getSecretAnswer(), testData.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and back to Practice portal");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		
		String messageSubject = "Document " + System.currentTimeMillis();

		logStep("Login physician");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("Getting the document to upload from filepath");
		PracticeUtil pUtil = new PracticeUtil(driver);
		String value = pUtil.getFilepath(testData.getProperty("fileDirectory")).concat(testData.getProperty("healthRecordsSendPdfFileName"));
		
		log("Randomize fileName");
		File originalDocument = new File(value);		
		File tmpDocument = File.createTempFile("Document", ".pdf");
		tmpDocument.deleteOnExit();
		FileUtils.copyFile(originalDocument, tmpDocument);				
		
		logStep("Send a secure message with attachment to dependent");
		PatientMessagingPage patientMessagingPage = practiceHome.clickPatientMessagingTab();
		patientMessagingPage.setFieldsAndPublishMessageWithFile("Dependent", patientLastName, "TestingMessage", messageSubject, tmpDocument.toString());
		
		logStep("Back to patient portal, log in as guardian");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patientLogin, testData.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		
		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent());

		logStep("Waiting for message from practice portal");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageSubject));	
		
		logStep("Go to health record, expect uploaded document");
		messagesPage.menuHealthRecordClickOnly();
		
		WebElement documentName = new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOf(
						driver.findElement(By.xpath("//table[@id='documentsTable']/tbody/tr/td[contains(text(),"+ tmpDocument.getName() + ")]"))
						)
					);
		assertTrue(null != documentName,"The uploaded document was not found on the dependents documents page when logged in as guardian");
		homePage.clickOnLogout();		
	}

	/**
	 * Creates patient, modifies My Account setting and validates result
	 */
	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMyAccount() throws Exception {

		logStep("Create and login patient");
		JalapenoPatient patient = new JalapenoPatient(testData);
		JalapenoHomePage homePage = CommonSteps.createAndLogInPatient(new JalapenoPatient(testData), testData, driver,
				patient.getUrl());
		logStep("Going to MyAccount page");
		JalapenoMyAccountProfilePage myAccountPage = homePage.goToAccountPage();
		logStep("Modify some elements and check their values on My Account page");
		assertTrue(myAccountPage.modifyAndValidatePageContent());
	}

	@Test(enabled = true, groups = {"acceptance-basics"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMyAccountSecurityTab() throws Exception {
		JalapenoPatient patient = new JalapenoPatient(testData);
		List<ExpectedEmail> mails = new ArrayList<ExpectedEmail>();

		long generatedTS = System.currentTimeMillis();
		String newEmail = String.format(NEW_EMAIL_TEMPLATE, generatedTS);
		String newUserName = String.format(NEW_USERNAME_TEMPLATE, generatedTS);
		String accountChangeNotificationSubject = String.format(ACCOUNT_CHANGE_NOTIFICATION_EMAIL_SUBJECT_TEMPLATE, testData.getPracticeName());

		logStep("Create and log in patient");
		JalapenoHomePage homePage = CommonSteps.createAndLogInPatient(patient, testData, driver, patient.getUrl());

		logStep("Go to security tab on my account page");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage myAccountPage = accountPage.clickOnEditMyAccount();
		JalapenoMyAccountSecurityPage myAccountSecurityPage = myAccountPage.goToSecurityTab(driver);
		assertTrue(myAccountSecurityPage.areBasicPageElementsPresent());

		logStep("Change email");
		myAccountSecurityPage.goToChangeEmailAndAssessElements();
		myAccountSecurityPage.changeEmailAndVerify(patient.getPassword(), newEmail);
		mails.add(new ExpectedEmail(newEmail, accountChangeNotificationSubject, EMAIL_ADDRESS_CHANGE_NOTIFICATION_EMAIL_CONTENT));
		mails.add(new ExpectedEmail(patient.getEmail(), accountChangeNotificationSubject, EMAIL_ADDRESS_CHANGE_NOTIFICATION_EMAIL_CONTENT));

		logStep("Change username");
		myAccountSecurityPage.goToChangeUserNameAndAssessElements();
		myAccountSecurityPage.changeUserNameAndVerify(patient.getPassword(), newUserName);
		mails.add(new ExpectedEmail(newEmail, accountChangeNotificationSubject, USERNAME_CHANGE_NOTIFICATION_EMAIL_CONTENT));

		logStep("Change security question");
		myAccountSecurityPage.goToChangeSecurityQuestionAndAssessElements();
		myAccountSecurityPage.changeSecurityQuestionAndVerify(patient.getPassword(), NAME_OF_FIRST_PET_SECURITY_QUESTION,
				NAME_OF_FIRST_PET_SECURITY_QUESTION_ANSWER);

		logStep("Change password");
		myAccountSecurityPage.goToChangePasswordAndAssessElements();
		myAccountSecurityPage.changePassword(patient.getPassword(), NEW_PASSWORD);
		logStep("Try new password");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		loginPage.login(newUserName, NEW_PASSWORD);
		mails.add(new ExpectedEmail(newEmail, accountChangeNotificationSubject, PASSWORD_CHANGE_NOTIFICATION_EMAIL_CONTENT));

		logStep("Check notification emails");
		assertTrue(new Mailinator().areAllMessagesInInbox(mails, 15));
	}

	@Test(enabled = true, groups = {"acceptance-linkedaccounts"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLACreateTrustedRepresentativeOnly() throws Exception {

		logStep("Creating a new patient");
		JalapenoPatient patient = new JalapenoPatient(testData);
		JalapenoPatient trustedPatient = new JalapenoPatient(testData);
		JalapenoHomePage homePage = CommonSteps.createAndLogInPatient(patient, testData, driver, patient.getUrl());

		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient);

		logStep("Waiting for invitation email");				
		String patientUrl = new Mailinator().getLinkFromEmail(trustedPatient.getEmail(), INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 15);
		assertNotNull(patientUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage =
				patientVerificationPage.fillDependentInfoAndContinue(patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());

		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(patient.getFirstName(), patient.getLastName(), trustedPatient.getEmail());
		SecurityDetailsPage accountDetailsPage = linkAccountPage.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");

		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getUsername(), trustedPatient.getPassword(), testData.getSecretQuestion(),
				testData.getSecretAnswer(), testData.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));

		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExtendedGenderQuestion() throws Exception {
		logStep("Creating new patient and log in");
		JalapenoPatient patient = new JalapenoPatient(testData);
		JalapenoHomePage homePage = CommonSteps.createAndLogInPatient(patient, testData, driver,
				testData.getProperty("url2"));
		logStep("Going to MyAccount page");
		JalapenoMyAccountProfilePage myAccountPage = homePage.clickOnMyAccount();
		assertTrue(myAccountPage.checkExtendedGenderQuestion());
		logStep("Log Out");
		homePage.clickOnLogout();
	}
	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSymptomAssessmentSafe() throws Exception {

		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getProperty("saUsername"), testData.getPassword());
		
		JalapenoSymptomAssessmentPage saPage = jalapenoHomePage.clickOnSymptomAssessment(driver);
		
		log("Select your doctor");		
		saPage.selectProvider(testData.getProperty("saProviderName"));
		
		log("type Your Symptom and submit");
		saPage.typeYourSymptom(PortalConstants.Symptom);

		log("DoYouHaveSymptom Now ?? Answer :-NO ");
		saPage.answerDoYouHaveSymptom();
		
		
		log("Logout of Patient Portal");
		saPage.clickHome().clickOnLogout();

		log("Login to Practice Portal");
		// Load up practice test data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getProperty("portalUrl"));
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("saProviderUsername"), testData.getProperty("saProviderPassword"));

		log("On Practice Portal Home page Click SymptomAssessmentTab");
		SymptomAssessmentFilterPage pSymptomAssessmentFilter = practiceHome.clicksymptomAssessmentTab();

		log("On Practice Portal Home page Click SymptomAssessmentTab");
		SymptomAssessmentDetailsPage pSymptomAssessmentDetailsPage = pSymptomAssessmentFilter.searchSymptomAssessment();

		log("Verification on SymptomAssessmentDetailsPage");
		assertTrue(verifyTextPresent(driver, "Date of Birth : 01/01/1987"));
		/*
		 * assertTrue(verifyTextPresent(driver, "Home Phone : (958) 963-1234"));
		 */
		assertTrue(verifyTextPresent(driver, "Reason for visit: \"cough\"."));
		log("Sent Message to Patient");
		String practiceResponse = pSymptomAssessmentDetailsPage.sentMessage();

		log("Logout of Practice Portal");
		practiceHome.logOut();
		
		logStep("back to PI");
		jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		jalapenoHomePage = jalapenoLoginPage.login(testData.getProperty("saUsername"), testData.getPassword());
		
		logStep("find SA message subject");
		JalapenoMessagesPage messagePage = jalapenoHomePage.showMessages(driver);
		
		assertTrue(messagePage.isMessageDisplayed(driver, practiceResponse));
		
		
		
		
		logStep("Logging out");
		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());
	}
	
	@Test(enabled = true, groups = {"acceptance-solutions"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBlinkBannerHiding() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Fill in credentials and log in");
		JalapenoHomePage jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Validate Blink banner present");
		assertTrue(jalapenoHomePage.areMenuElementsPresent());
		assertTrue(jalapenoHomePage.isBlinkBannerDisplayed());
		
		logStep("Hide Blink banner, verify that it hides");
		jalapenoHomePage.clickBlinkBannerHide();		
		Thread.sleep(3000);
		assertFalse(jalapenoHomePage.isBlinkBannerDisplayed());
		
		logStep("Refresh, verify Blink banner is still hidden");
		driver.navigate().refresh();
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());
		assertFalse(jalapenoHomePage.isBlinkBannerDisplayed());		
		
		logStep("Log out");
		loginPage = jalapenoHomePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());
		
		logStep("Log in again");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());
		
		logStep("Verify Blink banner is still hidden");
		assertFalse(jalapenoHomePage.isBlinkBannerDisplayed());
	}

}
