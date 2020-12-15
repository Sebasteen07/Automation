//  Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.

package com.medfusion.patientportal2.test;

import static org.testng.Assert.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.PatientFactory;
import com.medfusion.pojos.Patient;
import com.medfusion.portal.utils.PortalConstants;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPreferencesPage;
import com.medfusion.product.object.maps.patientportal2.page.PayNow.JalapenoPayNowPage;
import com.medfusion.product.patientportal2.implementedExternals.CreatePatient;
import com.medfusion.product.patientportal2.utils.PortalUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.pojo.ExpectedEmail;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginEnrollment;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2HistoryPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step1;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step2;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2HistoryDetailPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2HistoryListPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page1;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page2;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskPayBillsConfirmationPage;
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
	private static final String WELCOME_EMAIL_BUTTON_TEXT = "Visit our patient portal now";
	private static final String WELCOME_EMAIL_SUBJECT_PATIENT = "New Member Confirmation";
	private static final String WELCOME_EMAIL_BODY_PATTERN_PRACTICE = "Thank you for creating an account with TestPracticeELE02";
	private static final String WELCOME_EMAIL_BODY_PATTERN_SECOND_PRACTICE = "Thank you for creating an account with TestPracticeELE01";
	private static final String INVITE_LINK_FRAGMENT = "invite";
	private static final String ACTIVATE_LINK_FRAGMENT = "activate";
	private static final String GUARDIAN_INVITE_SUBJECT = "You are invited to create a Patient Portal guardian account at ";
	private static final String questionText = "wat";
	private static final String ErrorfilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\File_Attachment\\Error_Files_Testing.pdf";
	private static final String CorrectfilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\File_Attachment\\sw-test-academy.txt";
	private static final String MessagefilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\documents\\QuickSend.pdf";
	private static final String MessageErrorfilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\documents\\SecureMessageFile.pdf";

	private PropertyFileLoader testData;
	private Patient patient = null;
	private JalapenoPatient Jalapenopatient = null;

	@BeforeClass(alwaysRun = true)
	public void prepareTestData() throws IOException {
		log("Getting Test Data");
		testData = new PropertyFileLoader();
	}

	// TODO uncomment and remove call from all associated tests after testng 7.0.0
	// is released
	// @BeforeMethod(alwaysRun = true, onlyForGroups = "commonpatient")
	public void createCommonPatient() throws Exception {
		if (patient == null) {
			String username = PortalUtil.generateUniqueUsername(testData.getProperty("userid"), testData);
			patient = PatientFactory.createJalapenoPatient(username, testData);
			patient = new CreatePatient().selfRegisterPatient(driver, patient, testData.getUrl());
		}
	}

	public void createUnderAgePatient() throws Exception {
		if (Objects.isNull(patient)) {
			String username = PortalUtil.generateUniqueUsername(testData.getProperty("userid"), testData);
			patient = PatientFactory.createJalapenoPatient(username, testData);
			patient = new CreatePatient().selfRegisterUnderAgePatient(driver, patient, testData.getUrl());
		}
	}

	public void createCommonPatientStateSpecific() throws Exception {
		if (Objects.isNull(patient)) {
			String username = PortalUtil.generateUniqueUsername(testData.getProperty("userid"), testData);
			patient = PatientFactory.createJalapenoPatient(username, testData);
			patient = new CreatePatient().selfRegisterPatientStateSpecific(driver, patient, testData.getUrl());
		}
	}

	// TODO move to Utils
	private String getRedirectUrl(String originUrl) {
		log("Navigating to input URL and checking redirection for 10 seconds");
		driver.get(originUrl);
		for (int i = 0; i < 10; i++) {
			if (!driver.getCurrentUrl().equals(originUrl)) {
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
		return originUrl;
	}

	/**
	 * verifies if the invite url contains a specific keyword(INVITE_LINK_FRAGMENT
	 * or ACTIVATE_LINK_FRAGMENT), if not, it is a redirect url (e.g.from
	 * sendinblue)
	 *
	 * @return
	 */
	private boolean isInviteLinkFinal(String url) {
		boolean isFinalUrl = url.contains(INVITE_LINK_FRAGMENT) || url.contains(ACTIVATE_LINK_FRAGMENT);
		if (!isFinalUrl)
			log("The retrieved link is a redirect URL : " + url);
		return isFinalUrl;
	}

	private boolean isWelcomeLinkFinal(String url) {
		boolean isFinalUrl = url.contains(WELCOME_EMAIL_BUTTON_TEXT);
		if (!isFinalUrl)
			log("The retrieved link is a redirect URL:" + url);
		return isFinalUrl;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testAssessLoginPageElements() {
		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginValidCredentialsAndValidateMenuElements() {
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

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginRememberUserName() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Fill in credentials, Remember username unchecked and log in");
		loginPage.unCheckRememberUserName();
		JalapenoHomePage jalapenoHomePage = loginPage.RememberUserName(testData.getUserId(), testData.getPassword());

		logStep("Log out");
		loginPage = jalapenoHomePage.clickOnLogout();

		logStep("Since remember username checkbox is not checked - user name field is empty");
		assertTrue(loginPage.getUserNameFieldText().equals(""));

		logStep("Fill in credentials, Remember username checked and log in");
		loginPage.checkRememberUserName();
		loginPage.RememberUserName(testData.getUserId(), testData.getPassword());

		logStep("Log out");
		loginPage = jalapenoHomePage.clickOnLogout();

		logStep("Since remember username checkbox is checked - user name field is prepopulated");
		String userNameText = loginPage.getUserNameFieldText();
		assertTrue(loginPage.getUserNameFieldText().contains(userNameText));

	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginEmptyUserName() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Fill in empty credentials and log in");
		loginPage.loginEmptyCredentials();

		logStep("empty username error displayed");
		assertTrue(loginPage.getUserErrorText().contentEquals("Please enter a user name."));

		logStep("empty password error displayed");
		assertTrue(loginPage.getPasswordErrorText().contentEquals("Please enter a password."));

	}

	/**
	 * TODO: Uncomment when someone merges development into master at qa-main
	 * repository
	 *
	 * @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class) public void
	 *               testSendAnEmailAndVerifyIfDelivered() throws Exception {
	 *               logStep("Send an email"); EmailRest email = new EmailRest();
	 *               EmailBodyPojo emailBody = new EmailBodyPojo(); String postUrl =
	 *               "http://" + testData.getPracticeSvcsServer() +
	 *               "/practice-svcs/services/message/practice/" +
	 *               testData.getPracticeId(); String emailSubject = "Test Email " +
	 *               System.currentTimeMillis(); String urlInEmail =
	 *               "https://medfusion.com/";
	 *               <p>
	 *               emailBody.setSenderValue(testData.getPracticeStaffId());
	 *               emailBody.setRecipientValue(testData.getPracticeMemberId());
	 *               emailBody.setSubject(emailSubject);
	 *               emailBody.setHtmlBody("<a href='" + urlInEmail + "'>Click
	 *               here!</a>"); emailBody.setTextBody(""); HttpResponse response =
	 *               email.sendAnEmail(postUrl, emailBody);
	 *               <p>
	 *               logStep("Check status code of request");
	 *               log(response.toString());
	 *               assertEquals(response.getStatusLine().getStatusCode(), 200,
	 *               "Error: Response status code is not 200!");
	 *               <p>
	 *               logStep("Check if email was delivered"); String
	 *               elementContainingString = new
	 *               Mailinator().getLinkFromEmail(testData.getUserEmail(),
	 *               emailSubject, "Click here!", 120);
	 *               assertNotNull(elementContainingString, "Error: Email or email
	 *               body was not found."); }
	 **/
	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginInvalidCredentials() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		jalapenoLoginPage.loginUnsuccessfuly(testData.getUserId(), "InvalidPassword");
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatient() throws Exception {
		createCommonPatient();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		assertTrue(homePage.areBasicPageElementsPresent());

		logStep("Checking if the information are correct");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage myAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(myAccountPage.checkZipCode(patient.getZipCode()));
		assertTrue(myAccountPage.checkGender(patient.getGender()));

		logStep("Logging out");
		loginPage = homePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientActivation() throws Exception {
		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');

		logStep("Patient Activation on Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationLink(driver, testData, patientsEmail);

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				PracticeConstants.ZIP_CODE, PortalConstants.DateOfBirthMonthNumber, PortalConstants.DateOfBirthDay,
				PortalConstants.DateOfBirthYear);

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				patientActivationSearchTest.getPatientIdString(), testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Checking if address in My Account is filled");
		JalapenoAccountPage accountPage = jalapenoHomePage.clickOnAccount();
		JalapenoMyAccountProfilePage jalapenoMyAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(
				jalapenoMyAccountPage.checkForAddress(driver, "5501 Dillard Dr", "Cary", PracticeConstants.ZIP_CODE));

		logStep("Logging out");
		JalapenoLoginPage jalapenoLoginPage = jalapenoHomePage.clickOnLogout();
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());

		logStep("Logging again: " + patientActivationSearchTest.getPatientIdString() + " \\ " + testData.getPassword());
		jalapenoHomePage = jalapenoLoginPage.login(patientActivationSearchTest.getPatientIdString(),
				testData.getPassword());

		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());
		logStep("Logging out");

		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());

		logStep("Logging into Mailinator and getting Patient Activation url");
		String unlockLinkEmail = new Mailinator().getLinkFromEmail(patientsEmail,
				INVITE_EMAIL_SUBJECT_PATIENT + testData.getPracticeName(), INVITE_EMAIL_BUTTON_TEXT, 10);
		assertNotNull(unlockLinkEmail, "Error: Activation link not found.");
		logStep("Retrieved activation link is " + unlockLinkEmail);
		if (!isInviteLinkFinal(unlockLinkEmail)) {
			unlockLinkEmail = getRedirectUrl(unlockLinkEmail);
			log("Retrieved link was redirect link. Final link is " + unlockLinkEmail);
		}
		logStep("Comparing with portal unlock link " + unlockLinkPortal);
		assertEquals(unlockLinkEmail, unlockLinkPortal, "!patient unlock links are not equal!");
	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testForgotPassword() throws Exception {
		createCommonPatient();
		resetForgottenPasswordOrUsername(patient.getEmail());
	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientForgotUserIdCaseInsensitiveEmail() throws Exception {
		createCommonPatient();
		String email = IHGUtil.mixCase(patient.getEmail());
		resetForgottenPasswordOrUsername(email);
	}

	private void resetForgottenPasswordOrUsername(String email) {
		Instant passwordResetStart = Instant.now();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Clicking on forgot username or password");
		JalapenoForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordButton();

		JalapenoForgotPasswordPage2 forgotPasswordPage2 = forgotPasswordPage.fillInDataPage(email);
		logStep("Message was sent, closing");
		forgotPasswordPage2.clickCloseButton();

		logStep("Logging into Mailinator and getting ResetPassword url");
		String[] mailAddress = email.split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";
		Email receivedEmail = new Mailer(mailAddress[0]).pollForNewEmailWithSubject(emailSubject, 60,
				testSecondsTaken(passwordResetStart));
		String url = Mailer.getLinkByText(receivedEmail, inEmail);
		if (!isInviteLinkFinal(url)) {
			url = getRedirectUrl(url);
		}
		assertNotNull(url, "Url is null.");

		JalapenoForgotPasswordPage3 forgotPasswordPage3 = new JalapenoForgotPasswordPage3(driver, url);
		logStep("Redirecting to patient portal, filling secret answer as: " + patient.getSecurityQuestionAnswer());
		JalapenoForgotPasswordPage4 forgotPasswordPage4 = forgotPasswordPage3
				.fillInSecretAnswer(patient.getSecurityQuestionAnswer());

		logStep("Filling new password");
		JalapenoHomePage homePage = forgotPasswordPage4.fillInNewPassword(patient.getPassword());
		assertTrue(homePage.areBasicPageElementsPresent());

		logStep("Logging out");
		loginPage = homePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMessaging() throws Exception {
		String messageSubject = "Namaste " + System.currentTimeMillis();

		logStep("Login physician");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		Instant messageBuildingStart = Instant.now();
		logStep("Send a new secure message to static patient");
		PatientMessagingPage patientMessagingPage = practiceHome.clickPatientMessagingTab();
		ArrayList<String> practicePortalMessage = patientMessagingPage.setFieldsAndPublishMessage(testData,
				"TestingMessage", messageSubject);

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent());

		logStep("Waiting for message from practice portal");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageSubject));

		logStep("Veriying the length of the subject at Patient Portal");
		int subjectLength = messagesPage.checkSubjectLength();
		assertEquals(messageSubject.length(), subjectLength);

		logStep("Verifying the content of the Message at Patient Portal");
		assertEquals(practicePortalMessage.get(1), messagesPage.getMessageBody());

		logStep("Verifying that URL is present in Patient inbox as sent from Practice Portal");
		assertEquals(practicePortalMessage.get(0), messagesPage.getMessageURL());

		logStep("Response to the message");
		assertTrue(messagesPage.replyToMessage(driver));

		logStep("Back to the practice portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		patientMessagingPage = practiceHome.clickPatientMessagingTab();
		assertTrue(patientMessagingPage.findMyMessage(messageSubject));

		logStep("Verifying Reply Content at Practice Portal");
		String replyContent = patientMessagingPage.checkReplyContent();
		assertEquals(replyContent, JalapenoMessagesPage.getPatientReply());

		// TODO: Edit navigation on portal and search by date to search exact day

		logStep("Check email for notification for new message");
		// longer timeout needed as emails from Practice Portal are not sent out
		// immediately but only once in a minute
		// also need to check that the email is fresh enough as there might be email
		// with the same content from different test run
		String notificationEmailSubject = "New message from " + testData.getPracticeName();
		String[] mailAddress = testData.getEmail().split("@");
		Email email = new Mailer(mailAddress[0]).pollForNewEmailWithSubject(notificationEmailSubject, 90,
				testSecondsTaken(messageBuildingStart));
		assertNotNull(email, "Error: No new message notification recent enough found");
		String emailBody = email.getBody();
		assertTrue(emailBody.contains("Sign in to view this message"));
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMessageArchiving() throws Exception {

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent());
		assertTrue(messagesPage.returnSubjectMessage().length() > 0);

		logStep("Go to archived messages tab");
		messagesPage.goToArchivedMessages();

		int messageCount = messagesPage.MessageCount();
		logStep("Go to Inbox messages tab");
		messagesPage.goToInboxMessage();

		logStep("Click on Archive Button on open email");
		messagesPage.archiveOpenMessage();

		logStep("Click on Archived folder");
		messagesPage.goToArchivedMessages();
		int y = messagesPage.MessageCount();

		logStep("Message archicved Successfuly");
		assertTrue(y > messageCount);

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testViewCCD() throws Exception {

		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(),
				testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		JalapenoCcdViewerPage jalapenoCcdPage = jalapenoMessagesPage.findCcdMessage(driver);

		assertTrue(jalapenoCcdPage.areBasicPageElementsPresent());
		if ((IHGUtil.getEnvironmentType().toString().equals("DEV3"))
				|| (IHGUtil.getEnvironmentType().toString().equals("QA1"))) {
			log("Skipping method checkPdfToDownload and checkRawToDownload because of known issue on DEV3&&QA1 javax.net.ssl.SSLHandshakeException");
		} else {
			assertTrue(jalapenoCcdPage.checkPdfToDownload(driver));
			assertTrue(jalapenoCcdPage.checkRawToDownload(driver));
		}
		assertTrue(jalapenoCcdPage.sendInformationToDirectEmail(DIRECT_EMAIL_ADDRESS));

		jalapenoMessagesPage = jalapenoCcdPage.closeCcd(driver);
		assertTrue(jalapenoMessagesPage.areBasicPageElementsPresent());

		jalapenoHomePage = jalapenoMessagesPage.clickOnMenuHome();

		logStep("Logging out");
		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();
		assertTrue(jalapenoLoginPage.areBasicPageElementsPresent());
	}

	@Test(enabled = false, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendCCDToNonSecureEmail() throws Exception {
		String email = System.currentTimeMillis() + "unsecure@mailinator.com";

		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(),
				testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		JalapenoCcdViewerPage jalapenoCcdPage = jalapenoMessagesPage.findCcdMessage(driver);

		assertTrue(jalapenoCcdPage.areBasicPageElementsPresent());

		assertTrue(jalapenoCcdPage.sendInformationToUnsecureEmail(email));

		// TODO: Check if email arrived once is in use
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthRecords() {
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(),
				testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Navigate to Medical Record Summaries Page");
		MedicalRecordSummariesPage recordSummaries = jalapenoHomePage.clickOnMedicalRecordSummaries(driver);

		logStep("Send CCD if there is CCD older than 7 days");
		recordSummaries.sendCCDIfNewestIsOlderThan(7);

		logStep("Select and send CCD to Direct email address");
		recordSummaries.selectFirstVisibleCCD();
		recordSummaries.sendFirstVisibleCCDUsingDirectProtocol(DIRECT_EMAIL_ADDRESS);

		logStep("Set filter to default position and check page elements");
		recordSummaries.setFilterToDefaultPositionAndCheckElements();

		logStep("Go to Documents tab");
		DocumentsPage documentsPage = recordSummaries.gotoOtherDocumentTab();
		assertTrue(documentsPage.areBasicPageElementsPresent());

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientEducationNoIssue() throws InterruptedException {
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage
				.login(testData.getProperty("patientEducationNoissueUserName"), testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Navigate to Medical Record Summaries Page");
		MedicalRecordSummariesPage recordSummaries = jalapenoHomePage.clickOnMedicalRecordSummaries(driver);

		logStep("Select first visible CCD");
		recordSummaries.selectFirstVisibleCCD();

		logStep("Click on patient Education Button ");
		recordSummaries.clickPatientEducation();

		logStep("Validating the issue's on Care Nexis Page");
		assertEquals(recordSummaries.getUnmatchedCondition(), "Unmatched Condition");

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthRecordsDocuments() throws Exception {

		String messageSubject = "Document " + System.currentTimeMillis();

		logStep("Login physician");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		log("Getting the document to upload from filepath");
		PracticeUtil pUtil = new PracticeUtil(driver);
		String value = pUtil.getFilepath(testData.getProperty("fileDirectory"))
				.concat(testData.getProperty("healthRecordsSendPdfFileName"));

		log("Change name of the file");
		File originalDocument = new File(value);

		File tmpDocument = File.createTempFile("Document", ".pdf");
		tmpDocument.deleteOnExit();
		FileUtils.copyFile(originalDocument, tmpDocument);

		logStep("Send a new secure message with attachment to static patient");
		PatientMessagingPage patientMessagingPage = practiceHome.clickPatientMessagingTab();
		patientMessagingPage.setFieldsAndPublishMessageWithFile(testData, "TestingMessage", messageSubject,
				tmpDocument.toString());

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("documentsPatientUserId"),
				testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Waiting for message from practice portal");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageSubject));

		logStep("Go to Documents tab");
		DocumentsPage documentsPage = messagesPage.goToDocumentsPageFromMenu();

		logStep("Check if document from received message is displayed on Documents page");
		assertTrue(documentsPage.checkLastImportedFileName(tmpDocument.getName()));

	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6SamePractice() throws Exception {
		createCommonPatient();
		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Checking if zipCode in My Account is filled");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage myAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(myAccountPage.checkZipCode(patient.getZipCode()));

		loginPage = homePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Try to create the same patient");
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		assertTrue(patientDemographicPage.areBasicPageElementsPresent());

		patientDemographicPage.fillInPatientData(patient);

		patientDemographicPage.tryToContinueToSecurityPage();
		assertTrue(PageFactory.initElements(driver, JalapenoLoginPage.class).isExistingAccountErrorDisplayed());
		logStep("Username match was found");
	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6DifferentPractice() throws Exception {
		createCommonPatient();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		loginPage = homePage.clickOnLogout();
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Try to create the same patient in different practice");
		loginPage = new JalapenoLoginPage(driver, testData.getPractice2Url());
		assertTrue(loginPage.areBasicPageElementsPresent());
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();

		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		logStep("Patient with same demographics was allowed");

		accountDetailsPage.fillAccountDetailsAndContinueWithError(patient.getUsername(), patient.getPassword(),
				testData);
		assertTrue(accountDetailsPage.isUsernameTakenErrorDisplayed());
		logStep("Username match was found");
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6Inactive() throws Exception {

		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');

		logStep("Patient Activation on Practice Portal");
		patientActivationSearchTest.getPatientActivationLink(driver, testData, patientsEmail);

		logStep("Going to PI login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(loginPage.areBasicPageElementsPresent());

		logStep("Going to create account page");
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		assertTrue(patientDemographicPage.areBasicPageElementsPresent());

		logStep("Creating patient with the same data as in practice portal");
		patientDemographicPage.fillInPatientData(patientActivationSearchTest.getFirstNameString(),
				patientActivationSearchTest.getLastNameString(), patientActivationSearchTest.getEmailAddressString(),
				PortalConstants.DateOfBirthMonth, PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear,
				Patient.GenderExtended.MALE, PracticeConstants.ZIP_CODE); // TODO use only one constant file
		patientDemographicPage.tryToContinueToSecurityPage();

		logStep("Checking that I am still on create account page due to healthKey check won't let me create patient with the same data");
		patientDemographicPage = PageFactory.initElements(driver, PatientDemographicPage.class);
		assertTrue(patientDemographicPage.isInactiveAccountExistsErrorDisplayed());
	}

	@Test(enabled = false, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
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
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequest(driver, false, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Continue in Portal Inspired");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved " + tsPracticePortal));
	}

	// TODO: after Appointment Request v1 is not used - delete test above and
	// set up this test for main Jalapeno Automation practice, remove multiple
	// solutions workaround
	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestV2() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click appointment request");
		homePage.clickOnAppointmentV2(driver);

		// workaround for extra appointments list when multiple appointments solutions
		// are on
		try {

			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");// go on assuming we didn't find the extra page and button
		}

		JalapenoAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver,
				JalapenoAppointmentRequestV2Step1.class);
		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2.: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.areBasicPageElementsPresent());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequest(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved " + tsPracticePortal));
	}

	@Test(enabled = true, groups = { "acceptance-solutions", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrescriptionRenewal() throws Exception {
		createCommonPatient();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(10000);

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

		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
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

		String uniquePracticeResponse = Long.toString(onlineBillPaySearchPage.getCreatedTs())
				+ PracticeConstants.BILL_PAYMENT_SUBJECT;

		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Waiting for message from practice portal");
		assertTrue(messagesPage.isMessageDisplayed(driver, uniquePracticeResponse));
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAStaffV2() throws Exception {
		String questionText = "wat";

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("askAV2User"),
				testData.getProperty("askAV2Password"));

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaV2(testData.getProperty("askAV2Name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.fillAndContinue(askaSubject, questionText);

		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPage1 = homePage.openSpecificAskaV2(testData.getProperty("askAV2Name"));
		JalapenoAskAStaffV2HistoryListPage askHistoryList = askPage1.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		JalapenoAskAStaffV2HistoryDetailPage askHistoryDetail = askHistoryList.goToDetailByReason(askaSubject);

		logStep("Verify the subject and question in history detail match submission");
		assertTrue(askaSubject.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubject + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Open".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Open" + ", found: " + askHistoryDetail.getRequestDetailStatus());
		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();

		logStep("Login to practice portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("askAV2DoctorLogin"),
				testData.getProperty("askAV2DoctorPassword"));

		logStep("Click Ask A Staff tab");
		AskAStaffSearchPage searchQ = practiceHome.clickAskAStaffTab();

		logStep("Search for questions");
		searchQ.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = searchQ.getQuestionDetails(askaSubject);
		assertNotNull(detailStep1, "The submitted patient question was not found in the practice");

		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep1Page.PAGE_NAME);
		logStep("Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1.chooseProvideAdviceOnly();

		logStep("Respond to patient question");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2.processAndCommunicate("Automated Test",
				"This message was generated by an automated test");

		logStep("Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3.confirmProcessedQuestion();

		logStep("Validate submit of confirmation");
		assertTrue(detailStep4.isQuestionDetailPageLoaded());

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Login patient");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		homePage = loginPage.login(testData.getProperty("askAV2User"), testData.getProperty("askAV2Password"));

		logStep("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver,
				"Automated Test " + (Long.toString(detailStep2.getCreatedTimeStamp()))));

		logStep("Go back to the aska again and check submission status changed");
		homePage = messagesPage.clickOnMenuHome();
		askPage1 = homePage.openSpecificAskaV2(testData.getProperty("askAV2Name"));
		askHistoryList = askPage1.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		askHistoryDetail = askHistoryList.goToDetailByReason(askaSubject);

		logStep("Reverify subject and question in history detail, verify status is now completed");
		assertTrue(askaSubject.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubject + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Completed".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Completed" + ", found: " + askHistoryDetail.getRequestDetailStatus());
		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();

	}

	// Create under-age patient, complete registration with new guardian, checks
	// login credentials and
	// then checks
	// guardian email
	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLACreateGuardianOnly() throws Exception {
		Instant testStart = Instant.now();
		String patientLogin = PortalUtil.generateUniqueUsername("login", testData); // guardian's login
		String patientLastName = patientLogin.replace("login", "last"); // lastname for both
		String patientEmail = patientLogin.replace("login", "mail") + "@mailinator.com"; /// email for both

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Create under-age patient");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Enter all the details and click on Register");
		String guardianUrl = patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "F",
				patientLastName, testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(), testData.getDOBDay(),
				testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama", testData.getZipCode());

		logStep("Continue to Portal Inspired");
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, guardianUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill guardian name");
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, patientEmail);
		SecurityDetailsPage accountDetailsPage = linkAccountPage.continueToCreateGuardianOnly("Guardian",
				patientLastName, "Parent");

		logStep("Continue registration - create dependents credentials and continue to Home page");
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin,
				testData.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(),
				testData.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		homePage = loginPage.login(patientLogin, testData.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));

		homePage.clickOnLogout();

		logStep("Using mailinator Mailer to retrieve the latest emails for patient and guardian");

		String emailSubjectGuardian = "You are invited to create a Patient Portal guardian account at "
				+ testData.getPracticeName();
		Email emailGuardian = new Mailer(patientEmail).pollForNewEmailWithSubject(emailSubjectGuardian, 30,
				testSecondsTaken(testStart));
		assertNotNull(emailGuardian,
				"Error: No email found for guardian recent enough and with specified subject: " + emailSubjectGuardian);
		String guardianUrlEmail = Mailer.getLinkByText(emailGuardian, INVITE_EMAIL_BUTTON_TEXT);
		assertTrue(guardianUrlEmail.length() > 0, "Error: No matching link found in guardian invite email!");
		// SendInBlue workaround, go through the redirect and save the actual URL if the
		// invite link does not contain a specific string
		if (!isInviteLinkFinal(guardianUrlEmail)) {
			guardianUrlEmail = getRedirectUrl(guardianUrlEmail);
		}
		logStep("Retrieved dependents activation link is " + guardianUrlEmail);
		logStep("Comparing with dependents link from PrP " + guardianUrl);
		assertEquals(guardianUrl, guardianUrlEmail,
				"Practice portal and email unlock links for guardian are not equal!");
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLACreateDependentAndGuardian() throws Exception {
		Instant testStart = Instant.now();
		String patientLogin = PortalUtil.generateUniqueUsername("login", testData); // guardian login
		String patientLastName = patientLogin.replace("login", "last");
		String patientEmail = patientLogin.replace("login", "mail") + "@mailinator.com";

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Register Guardian - Enter all the details and click on Register");
		String patientUrl = patientActivationPage.setInitialDetailsAllFields("Guardian", patientLastName, "F",
				patientLastName + "G", testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYear(), "address1", "address2", "city", "Alabama",
				testData.getZipCode());

		logStep("Register Dependent - Enter all the details and click on Register");
		String guardianUrl = patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "M",
				patientLastName + "D", testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama",
				testData.getZipCode());
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin,
				testData.getPassword(), testData);

		log("Login username of Guardian is " + patientLogin);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(guardianUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill login credentials");
		log("Login username of Guardian is " + patientLogin);
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

		String emailSubjectGuardian = "You are invited to create a Patient Portal guardian account at "
				+ testData.getPracticeName();
		Email emailGuardian = new Mailer(patientEmail).pollForNewEmailWithSubject(emailSubjectGuardian, 30,
				testSecondsTaken(testStart));
		assertNotNull(emailGuardian,
				"Error: No email found for guardian recent enough and with specified subject: " + emailSubjectGuardian);
		String guardianUrlEmail = Mailer.getLinkByText(emailGuardian, INVITE_EMAIL_BUTTON_TEXT);

		assertTrue(guardianUrlEmail.length() > 0, "Error: No matching link found in guardian invite email!");

		// SendInBlue workaround, go through the redirect and save the actual URL if the
		// invite link does not contain a specific string
		if (!isInviteLinkFinal(guardianUrlEmail)) {
			guardianUrlEmail = getRedirectUrl(guardianUrlEmail);
		}

		logStep("Retrieved dependents activation link is " + guardianUrlEmail);
		logStep("Comparing with dependents link from PrP " + guardianUrl);
		assertEquals(guardianUrl, guardianUrlEmail,
				"Practice portal and email unlock links for guardian are not equal!");

		String emailSubjectPatient = INVITE_EMAIL_SUBJECT_PATIENT + testData.getPracticeName();
		Email emailPatient = new Mailer(patientEmail).pollForNewEmailWithSubject(emailSubjectPatient, 30,
				testSecondsTaken(testStart));
		assertNotNull(emailPatient,
				"Error: No email found for patient recent enough and with specified subject: " + emailSubjectPatient);
		String patientUrlEmail = Mailer.getLinkByText(emailPatient, INVITE_EMAIL_BUTTON_TEXT);
		assertTrue(patientUrlEmail.length() > 0, "Error: No matching link found in dependent invite email!");

		// SendInBlue workaround, go through the redirect and save the actual URL if the
		// invite link does not contain a specific string
		if (!isInviteLinkFinal(patientUrlEmail)) {
			patientUrlEmail = getRedirectUrl(patientUrlEmail);
		}
		logStep("Retrieved patients activation link is " + patientUrl);
		logStep("Comparing with patients link from PrP " + patientUrlEmail);
		assertEquals(patientUrl, patientUrlEmail,
				"Practice portal and email unlock links for dependent are not equal!");
	}

	private long testSecondsTaken(Instant testStart) {
		return testStart.until(Instant.now(), ChronoUnit.SECONDS);
	}

	// This test uses under-age patients created at tests
	// FACreateDependentAndGuardian and
	// FACreateGuardianOnly
	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLAPostAgeOutFlow() throws Exception {

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Family Management");
		AgeOutReportPage AOPage = practiceHome.clickFamilyManagementTab();

		logStep("Age-out first patient");
		AOPage.searchInAgeOutReport(true, false, testData.getDOBMonth(), testData.getDOBDay(),
				testData.getDOBYearUnderage());
		String name = AOPage.ageOutFirstPatient();
		logStep("Patients name: " + name);

		logStep("Proceed to his dashboard");
		AOPage.searchInAgeOutReport(false, true, testData.getDOBMonth(), testData.getDOBDay(),
				testData.getDOBYearUnderage());
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
		assertTrue(patientUrlEmail.length() > 0, "Error: Activation patients link not found.");
		logStep("Retrieved patients activation link is " + patientUrlEmail);

		logStep("Identify patient");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrlEmail);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Create patient");
		String login = "selflogin" + id;
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(login, testData.getPassword(),
				testData);
		homePage.areBasicPageElementsPresent();
		homePage.isTextDisplayed(
				"You have successfully created your account and can access all of your previous health information.");

		logStep("Logout and login patient");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		homePage = loginPage.login(login, testData.getPassword());
		homePage.areBasicPageElementsPresent();
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLADocumentsAccess() throws Exception {
		String patientLogin = PortalUtil.generateUniqueUsername("login", testData); // guardian's login
		String patientLastName = patientLogin.replace("login", "last"); // lastname for both
		String patientEmail = patientLogin.replace("login", "mail") + "@mailinator.com"; // email for both

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Create under-age patient");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Enter all the details and click on Register");
		String guardianUrl = patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "F",
				patientLastName, testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(), testData.getDOBDay(),
				testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama", testData.getZipCode());

		logStep("Continue to Portal Inspired");
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, guardianUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill guardian name");
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, patientEmail);
		SecurityDetailsPage accountDetailsPage = linkAccountPage.continueToCreateGuardianOnly("Guardian",
				patientLastName, "Parent");

		logStep("Continue registration - create dependents credentials and continue to Home page");
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin,
				testData.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(),
				testData.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and back to Practice portal");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();

		String messageSubject = "Document " + System.currentTimeMillis();

		logStep("Login physician");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		log("Getting the document to upload from filepath");
		PracticeUtil pUtil = new PracticeUtil(driver);
		String value = pUtil.getFilepath(testData.getProperty("fileDirectory"))
				.concat(testData.getProperty("healthRecordsSendPdfFileName"));

		log("Randomize fileName");
		File originalDocument = new File(value);
		File tmpDocument = File.createTempFile("Document", ".pdf");
		tmpDocument.deleteOnExit();
		FileUtils.copyFile(originalDocument, tmpDocument);

		logStep("Send a secure message with attachment to dependent");
		PatientMessagingPage patientMessagingPage = practiceHome.clickPatientMessagingTab();
		patientMessagingPage.setFieldsAndPublishMessageWithFile("Dependent", patientLastName, "TestingMessage",
				messageSubject, tmpDocument.toString());

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
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(
						"//table[@id='documentsTable']/tbody/tr/td[contains(text()," + tmpDocument.getName() + ")]"))));
		assertTrue(null != documentName,
				"The uploaded document was not found on the dependents documents page when logged in as guardian");
		homePage.clickOnLogout();
	}

	/**
	 * Creates patient, modifies My Account setting and validates result
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMyAccount() throws Exception {
		createCommonPatient();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Going to MyAccount page");
		JalapenoMyAccountProfilePage myAccountPage = homePage.goToAccountPage();
		logStep("Modify some elements and check their values on My Account page");
		assertTrue(myAccountPage.modifyAndValidatePageContent());
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMyAccountSecurityTab() throws Exception {
		logStep("Creating new patient");
		String username = PortalUtil.generateUniqueUsername(testData.getProperty("userid"), testData);
		Patient localpatient = PatientFactory.createJalapenoPatient(username, testData);
		localpatient = new CreatePatient().selfRegisterPatient(driver, localpatient, testData.getUrl());

		List<ExpectedEmail> mails = new ArrayList<ExpectedEmail>();

		long generatedTS = System.currentTimeMillis();
		String newEmail = String.format(NEW_EMAIL_TEMPLATE, generatedTS);
		String newUserName = String.format(NEW_USERNAME_TEMPLATE, generatedTS);
		String accountChangeNotificationSubject = String.format(ACCOUNT_CHANGE_NOTIFICATION_EMAIL_SUBJECT_TEMPLATE,
				testData.getPracticeName());

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(localpatient.getUsername(), localpatient.getPassword());

		logStep("Go to security tab on my account page");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage myAccountPage = accountPage.clickOnEditMyAccount();
		JalapenoMyAccountSecurityPage myAccountSecurityPage = myAccountPage.goToSecurityTab(driver);
		assertTrue(myAccountSecurityPage.areBasicPageElementsPresent());

		logStep("Change email");
		myAccountSecurityPage.goToChangeEmailAndAssessElements();
		myAccountSecurityPage.changeEmailAndVerify(localpatient.getPassword(), newEmail);
		mails.add(new ExpectedEmail(newEmail, accountChangeNotificationSubject,
				EMAIL_ADDRESS_CHANGE_NOTIFICATION_EMAIL_CONTENT));
		mails.add(new ExpectedEmail(localpatient.getEmail(), accountChangeNotificationSubject,
				EMAIL_ADDRESS_CHANGE_NOTIFICATION_EMAIL_CONTENT));
		localpatient.setEmail(newEmail);

		logStep("Change username");
		myAccountSecurityPage.goToChangeUserNameAndAssessElements();
		myAccountSecurityPage.changeUserNameAndVerify(localpatient.getPassword(), newUserName);
		mails.add(new ExpectedEmail(newEmail, accountChangeNotificationSubject,
				USERNAME_CHANGE_NOTIFICATION_EMAIL_CONTENT));
		localpatient.setUsername(newUserName);

		logStep("Change security question");
		myAccountSecurityPage.goToChangeSecurityQuestionAndAssessElements();
		myAccountSecurityPage.changeSecurityQuestionAndVerify(localpatient.getPassword(),
				NAME_OF_FIRST_PET_SECURITY_QUESTION, NAME_OF_FIRST_PET_SECURITY_QUESTION_ANSWER);
		localpatient.setSecurityQuestion(NAME_OF_FIRST_PET_SECURITY_QUESTION);
		localpatient.setSecurityQuestionAnswer(NAME_OF_FIRST_PET_SECURITY_QUESTION_ANSWER);

		logStep("Change password");
		myAccountSecurityPage.goToChangePasswordAndAssessElements();
		myAccountSecurityPage.changePassword(localpatient.getPassword(), NEW_PASSWORD);
		localpatient.setPassword(NEW_PASSWORD);
		logStep("Try new password");
		loginPage = homePage.clickOnLogout();
		loginPage.login(localpatient.getUsername(), localpatient.getPassword());
		mails.add(new ExpectedEmail(newEmail, accountChangeNotificationSubject,
				PASSWORD_CHANGE_NOTIFICATION_EMAIL_CONTENT));

		logStep("Check notification emails");
		assertTrue(new Mailinator().areAllMessagesInInbox(mails, 15));
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts",
			"commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLACreateTrustedRepresentativeOnly() throws Exception {
		createCommonPatient();
		Patient trustedPatient = PatientFactory.createJalapenoPatient(
				PortalUtil.generateUniqueUsername(testData.getProperty("userid"), testData), testData);

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient);

		logStep("Waiting for invitation email");
		String patientUrl = new Mailinator().getLinkFromEmail(trustedPatient.getEmail(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 15);
		assertNotNull(patientUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());

		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(patient.getFirstName(), patient.getLastName(), trustedPatient.getEmail());
		SecurityDetailsPage accountDetailsPage = linkAccountPage
				.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");

		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getUsername(), trustedPatient.getPassword(),
				testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.clickOnLogout();
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));

		homePage.clickOnLogout();

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExtendedGenderQuestion() throws Exception {
		logStep("Creating new patient");
		String username = PortalUtil.generateUniqueUsername(testData.getProperty("userid"), testData);
		Patient localpatient = PatientFactory.createJalapenoPatient(username, testData);
		localpatient = new CreatePatient().selfRegisterPatient(driver, localpatient, testData.getProperty("url2"));

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url2"));
		JalapenoHomePage homePage = loginPage.login(localpatient.getUsername(), localpatient.getPassword());

		logStep("Going to MyAccount page");
		JalapenoMyAccountProfilePage myAccountPage = homePage.clickOnMyAccount();
		assertTrue(myAccountPage.checkExtendedGenderQuestion());
		logStep("Log Out");
		homePage.clickOnLogout();
	}

	@Test(enabled = false, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSymptomAssessmentSafe() throws Exception {

		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getProperty("saUsername"),
				testData.getPassword());

		JalapenoSymptomAssessmentPage saPage = jalapenoHomePage.clickOnSymptomAssessment(driver);

		log("Select your doctor");
		saPage.selectProvider(testData.getProperty("saProviderName"));
		// TODO page Symptom assessment was changed
		log("type Your Symptom and submit");
		saPage.typeYourSymptom(PortalConstants.Symptom);

		log("DoYouHaveSymptom Now ?? Answer :-NO ");
		saPage.answerDoYouHaveSymptom();

		log("Logout of Patient Portal");
		saPage.clickHome().clickOnLogout();

		log("Login to Practice Portal");
		// Load up practice test data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getProperty("portalUrl"));
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("saProviderUsername"),
				testData.getProperty("saProviderPassword"));

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

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
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

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddandRemovePreferences() throws Exception {
		logStep("Creating new patient");
		String username = PortalUtil.generateUniqueUsername(testData.getProperty("userid"), testData);
		Patient localpatient = PatientFactory.createJalapenoPatient(username, testData);
		localpatient = new CreatePatient().selfRegisterPatient(driver, localpatient, testData.getUrl());

		logStep("Login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(localpatient.getUsername(), localpatient.getPassword());

		logStep("Go to MyAccount page");
		JalapenoMyAccountProfilePage myAccountPage = homePage.goToAccountPage();

		logStep("Open preferences tab");
		JalapenoMyAccountPreferencesPage preferencesPage = myAccountPage.goToPreferencesTab(driver);

		logStep("Set English preferred language");
		preferencesPage.setEnglishAsPreferredLanguageAndSave();

		logStep("Verify if preferred language is set to English");
		assertEquals(preferencesPage.getPreferredLanguage(), "English");

		logStep("Get preferred providers");
		List<String> originalPreferredProviders = preferencesPage.getPreferredProviders();

		logStep("Add preferred provider");
		preferencesPage.addPreferredProviderAndSave(testData.getProperty("preferredProvider"));

		logStep("Get preferred providers");
		List<String> updatedPreferredProviders = preferencesPage.getPreferredProviders();

		logStep("Verify if new preferred provider added");
		assertTrue(originalPreferredProviders.size() + 1 == updatedPreferredProviders.size());
		assertTrue(updatedPreferredProviders.contains(testData.getProperty("preferredProvider")),
				"List does not contain newly added provider");

		logStep("Remove all preferred providers");
		preferencesPage.removeAllPreferredProvidersAndSave();

		logStep("Verify all preferred providers removed");
		List<String> preferredProvidersAfterRemoval = preferencesPage.getPreferredProviders();
		assertTrue(preferredProvidersAfterRemoval.size() == 0,
				"List of preferred providers should be empty but it is not");
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPayNow() throws RuntimeException, InterruptedException {
		logStep("Open login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Click on Pay a bill (without logging in");
		JalapenoPayNowPage payNowPage = loginPage.clickPayNowButton();
		logStep("Verify Pay now (Pay here) page");
		assertTrue(payNowPage.validateNoLoginPaymentPage(testData.getFirstName(), testData.getLastName(),
				testData.getZipCode(), testData.getEmail()));
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAStaffPaid() throws Exception {

		logStep("Initiate payment data");
		String errorSubject = "How can i able to complete the task today";
		String askPaidAmount = "$ 2";
		String accountNumber = IHGUtil.createRandomNumericString(8);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);
		JalapenoAskAStaffV2Page2 askPage2 = new JalapenoAskAStaffV2Page2(driver);

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("askAV2User"),
				testData.getProperty("askAV2Password"));
		Thread.sleep(3000);
		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaPaidV2(testData.getProperty("askAV2Name"));

		Thread.sleep(3000);
		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());
		Thread.sleep(3000);
		logStep("Fill question and continue");

		askPage2 = askPage1.fillAndContinue(errorSubject, questionText, askaSubject);

		logStep("Remove all cards because Selenium can't see AddNewCard button");
		JalapenoAskAStaffV2Page1 askPaidPage = new JalapenoAskAStaffV2Page1(driver);

		assertTrue(askPaidAmount.equals(askPaidPage.getAskaPaymentText()),
				"Expected: " + askPaidAmount + ", found: " + askPaidPage.getAskaPaymentText());
		askPaidPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(askPaidPage.isAnyCardPresent());
		assertTrue(askPaidPage.areBasicPageElementsPresent());

		JalapenoAskPayBillsConfirmationPage confirmationPage = askPaidPage.fillPaymentInfo(accountNumber, creditCard);
		Thread.sleep(8000);
		assertTrue(confirmationPage.areBasicPageElementsPresent());

		askPage2 = new JalapenoAskAStaffV2Page2(driver);
		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPage1 = homePage.openSpecificAskaPaidV2(testData.getProperty("askAV2Name"));
		Thread.sleep(8000);
		JalapenoAskAStaffV2HistoryListPage askHistoryList = askPage1.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		JalapenoAskAStaffV2HistoryDetailPage askHistoryDetail = askHistoryList.goToDetailByReason(askaSubject);

		logStep("Verify the subject and question in history detail match submission");
		assertTrue(askaSubject.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubject + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Open".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Open" + ", found: " + askHistoryDetail.getRequestDetailStatus());
		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();

		logStep("Login to practice portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("askAV2DoctorLogin"),
				testData.getProperty("askAV2DoctorPassword"));

		logStep("Click Ask A Staff tab");
		AskAStaffSearchPage searchQ = practiceHome.clickAskAStaffTab();

		logStep("Search for questions");
		searchQ.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = searchQ.getQuestionDetails(askaSubject);
		assertNotNull(detailStep1, "The submitted patient question was not found in the practice");

		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep1Page.PAGE_NAME);
		logStep("Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1.chooseProvideAdviceOnly();

		logStep("Respond to patient question");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2.processAndCommunicate("Automated Test",
				"This message was generated by an automated test");

		logStep("Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3.confirmProcessedQuestion();

		logStep("Validate submit of confirmation");
		assertTrue(detailStep4.isQuestionDetailPageLoaded());

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Login patient");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		homePage = loginPage.login(testData.getProperty("askAV2User"), testData.getProperty("askAV2Password"));

		logStep("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver,
				"Automated Test " + (Long.toString(detailStep2.getCreatedTimeStamp()))));

		logStep("Go back to the aska again and check submission status changed");
		homePage = messagesPage.clickOnMenuHome();
		askPage1 = homePage.openSpecificAskaPaidV2(testData.getProperty("askAV2Name"));
		askHistoryList = askPage1.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		askHistoryDetail = askHistoryList.goToDetailByReason(askaSubject);

		logStep("Reverify subject and question in history detail, verify status is now completed");
		assertTrue(askaSubject.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubject + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Completed".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Completed" + ", found: " + askHistoryDetail.getRequestDetailStatus());
		assertTrue(
				askPaidAmount.replace("$ ", "")
						.equals(askHistoryDetail.getRequestDetailPayment()
								.replace("This online encounter was charged $", "").replace(".", "")),
				"Expected: " + askPaidAmount.replace("$ ", "") + ", found: " + askHistoryDetail
						.getRequestDetailPayment().replace("This online encounter was charged $", "").replace(".", ""));

		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();
	}

	@Test
	public void testAskAttachment() throws Exception {
		String expectedCorrectFileText = "sw-test-academy.txt";

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("askAV2User"),
				testData.getProperty("askAV2Password"));

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaV2(testData.getProperty("askAV2Name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.fillAndContinueAttachment(askaSubject, questionText);

		logStep("Add Attachmnet and remove Attachment ");
		askPage1.uploadFileWithRobotRepeat(ErrorfilePath, CorrectfilePath);

		logStep("Remove All the Attachment Except one and click on continue button ");
		askPage1.removeAttachment();

		logStep("Verify Uploaded file name in submit page ");
		assertTrue(expectedCorrectFileText.equals(askPage1.getProperFileText()),
				"Expected: " + expectedCorrectFileText + ", found: " + askPage1.getProperFileText());

		logStep("Verify Subject in submit page ");
		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());

		logStep("Verify Quesion in Submit paget ");
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPage1 = homePage.openSpecificAskaV2(testData.getProperty("askAV2Name"));
		JalapenoAskAStaffV2HistoryListPage askHistoryList = askPage1.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		JalapenoAskAStaffV2HistoryDetailPage askHistoryDetail = askHistoryList.goToDetailByReason(askaSubject);

		logStep("Verify the subject and question in history detail match submission");
		assertTrue(askaSubject.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubject + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Open".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Open" + ", found: " + askHistoryDetail.getRequestDetailStatus());

		assertTrue(expectedCorrectFileText.equals(askHistoryDetail.getRequestAttachedFile()),
				"Expected: " + expectedCorrectFileText + ", found: " + askHistoryDetail.getRequestAttachedFile());

		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();

		logStep("Login to practice portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("askAV2DoctorLogin"),
				testData.getProperty("askAV2DoctorPassword"));

		logStep("Click Ask A Staff tab");
		AskAStaffSearchPage searchQ = practiceHome.clickAskAStaffTab();

		logStep("Search for questions");
		searchQ.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = searchQ.getQuestionDetails(askaSubject);
		assertNotNull(detailStep1, "The submitted patient question was not found in the practice");

		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep1Page.PAGE_NAME);
		logStep("Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1.chooseProvideAdviceOnly();

		logStep("Respond to patient question");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2.processAndCommunicate("Automated Test",
				"This message was generated by an automated test");

		logStep("Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3.confirmProcessedQuestion();

		logStep("Validate submit of confirmation");
		assertTrue(detailStep4.isQuestionDetailPageLoaded());

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Login patient");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		homePage = loginPage.login(testData.getProperty("askAV2User"), testData.getProperty("askAV2Password"));

		logStep("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver,
				"Automated Test " + (Long.toString(detailStep2.getCreatedTimeStamp()))));

		logStep("Go back to the aska again and check submission status changed");
		homePage = messagesPage.clickOnMenuHome();
		askPage1 = homePage.openSpecificAskaV2(testData.getProperty("askAV2Name"));
		askHistoryList = askPage1.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		askHistoryDetail = askHistoryList.goToDetailByReason(askaSubject);

		logStep("Reverify subject and question in history detail, verify status is now completed");
		assertTrue(askaSubject.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubject + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Completed".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Completed" + ", found: " + askHistoryDetail.getRequestDetailStatus());
		assertTrue(expectedCorrectFileText.equals(askHistoryDetail.getRequestAttachedFile()),
				"Expected: " + expectedCorrectFileText + ", found: " + askHistoryDetail.getRequestAttachedFile());

		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testStateAgeOut() throws Exception {
		Instant testStart = Instant.now();
		String patientLogin = PortalUtil.generateUniqueUsername("statelogin", testData);
		String patientLastName = patientLogin.replace("login", "statelast");
		String patientEmail = patientLogin.replace("statelogin", "mail") + "@mailinator.com";

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Register Guardian - Enter all the details and click on Register");
		String patientUrl = patientActivationPage.setInitialDetailsAllFields("stateGuardian", patientLastName, "F",
				patientLastName + "G", testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYear(), "address1", "address2", "city", "Alabama",
				testData.getZipCode());

		logStep("Register Dependent - Enter all the details and click on Register");
		String guardianUrl = patientActivationPage.setInitialDetailsAllFields("stateDependent", patientLastName, "M",
				patientLastName + "D", testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama",
				testData.getZipCode());

		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin,
				testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(guardianUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

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

		logStep("Logging out");
		jalapenoHomePage.clickOnLogout();

		logStep("Using mailinator Mailer to retrieve the latest emails for patient and guardian");

		String emailSubjectGuardian = "You are invited to create a Patient Portal guardian account at "
				+ testData.getPracticeName();
		Email emailGuardian = new Mailer(patientEmail).pollForNewEmailWithSubject(emailSubjectGuardian, 30,
				testSecondsTaken(testStart));

		assertNotNull(emailGuardian,
				"Error: No email found for guardian recent enough and with specified subject: " + emailSubjectGuardian);
		String guardianUrlEmail = Mailer.getLinkByText(emailGuardian, INVITE_EMAIL_BUTTON_TEXT);

		assertTrue(guardianUrlEmail.length() > 0, "Error: No matching link found in guardian invite email!");

		if (!isInviteLinkFinal(guardianUrlEmail)) {
			guardianUrlEmail = getRedirectUrl(guardianUrlEmail);
		}

		logStep("Retrieved dependents activation link is " + guardianUrlEmail);
		logStep("Comparing with dependents link from PrP " + guardianUrl);

		assertEquals(guardianUrl, guardianUrlEmail,
				"Practice portal and email unlock links for guardian are not equal!");

		String emailSubjectPatient = INVITE_EMAIL_SUBJECT_PATIENT + testData.getPracticeName();
		Email emailPatient = new Mailer(patientEmail).pollForNewEmailWithSubject(emailSubjectPatient, 30,
				testSecondsTaken(testStart));

		assertNotNull(emailPatient,
				"Error: No email found for patient recent enough and with specified subject: " + emailSubjectPatient);
		String patientUrlEmail = Mailer.getLinkByText(emailPatient, INVITE_EMAIL_BUTTON_TEXT);

		assertTrue(patientUrlEmail.length() > 0, "Error: No matching link found in dependent invite email!");

		// SendInBlue workaround, go through the redirect and save the actual URL if the
		// invite link does not contain a specific string
		if (!isInviteLinkFinal(patientUrlEmail)) {
			patientUrlEmail = getRedirectUrl(patientUrlEmail);
		}
		logStep("Retrieved patients activation link is " + patientUrl);
		logStep("Comparing with patients link from PrP " + patientUrlEmail);

		assertEquals(patientUrl, patientUrlEmail,
				"Practice portal and email unlock links for dependent are not equal!");

		logStep("Create a underage patient account at patient portal and validate State ageout error");
		createUnderAgePatient();
		logStep("Test case passed");

	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientStateSpecific() throws Exception {
		createCommonPatientStateSpecific();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		assertTrue(homePage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientActivationEnrolment() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		List<ExpectedEmail> mails = new ArrayList<ExpectedEmail>();

		logStep("Generating Activation Link of patient in First Practice Porta");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String firstunlockLinkPortal = patientActivationSearchTest.getPatientActivationPracticeLink(driver, testData,
				patientsEmail, "doctorLogin1", "doctorPassword1");
		logStep("Patient first practice unlockLinkPortal" + firstunlockLinkPortal);

		logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
		String firstunlockLinkEmail = new Mailinator().getLinkFromEmail(patientsEmail,
				INVITE_EMAIL_SUBJECT_PATIENT + testData.getProperty("practiceName1"), INVITE_EMAIL_BUTTON_TEXT, 10);
		assertNotNull(firstunlockLinkEmail, "Error: Activation link not found.");

		logStep("Retrieved activation link for first Practice is " + firstunlockLinkEmail);
		if (!isInviteLinkFinal(firstunlockLinkEmail)) {
			firstunlockLinkEmail = getRedirectUrl(firstunlockLinkEmail);
			log("Retrieved link was redirect link. Final link for first Practice is " + firstunlockLinkEmail);
		}
		logStep("Comparing with portal unlock link for first Practice " + firstunlockLinkPortal);
		assertEquals(firstunlockLinkEmail, firstunlockLinkPortal, "!patient unlock links are not equal!");

		logStep("Generating Unlock link and  Activating patient on Seocnd Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest1 = new PatientActivationSearchTest();
		String secondunlockLinkPortal = patientActivationSearchTest1.getPatientActivationPracticeLink(driver, testData,
				patientsEmail, "doctorLoginPractice2", "doctorPasswordPractice2");

		logStep("Logging into Mailinator and getting Patient Activation url for Seond Practice");
		String secondunlockLinkEmail = new Mailinator().getLinkFromEmail(patientsEmail,
				INVITE_EMAIL_SUBJECT_PATIENT + testData.getProperty("practiceName2"), INVITE_EMAIL_BUTTON_TEXT, 20);
		assertNotNull(secondunlockLinkEmail, "Error: Activation link not found for second practice.");
		logStep("Retrieved activation link for Seond Practice is " + secondunlockLinkEmail);

		if (!isInviteLinkFinal(secondunlockLinkEmail)) {
			secondunlockLinkEmail = getRedirectUrl(secondunlockLinkEmail);
			log("Retrieved link was redirect link. Final link for Seond Practice is " + secondunlockLinkEmail);
		}
		logStep("Comparing with portal unlock link for Second Practice " + secondunlockLinkEmail);
		assertEquals(secondunlockLinkEmail, secondunlockLinkPortal,
				"!patient unlock links are not equal for Seond Practice!");

		logStep("Finishing of patient activation for portal 2: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, secondunlockLinkPortal);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				PracticeConstants.ZIP_CODE, PortalConstants.DateOfBirthMonthNumber, PortalConstants.DateOfBirthDay,
				PortalConstants.DateOfBirthYear);

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				patientActivationSearchTest1.getPatientIdString(), testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Checking if address in My Account is filled");
		JalapenoAccountPage accountPage = jalapenoHomePage.clickOnAccount();
		JalapenoMyAccountProfilePage jalapenoMyAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(
				jalapenoMyAccountPage.checkForAddress(driver, "5501 Dillard Dr", "Cary", PracticeConstants.ZIP_CODE));

		mails.add(new ExpectedEmail(patientsEmail, WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BODY_PATTERN_PRACTICE));
		mails.add(new ExpectedEmail(patientsEmail, WELCOME_EMAIL_SUBJECT_PATIENT,
				WELCOME_EMAIL_BODY_PATTERN_SECOND_PRACTICE));
		assertTrue(new Mailinator().areAllMessagesInInbox(mails, 15));

		logStep("Load login page for the auto enrolled practice");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, testData.getProperty("practiceUrl3"));
		JalapenoHomePage homePage = loginPage.login(patientActivationSearchTest1.getPatientIdString(),
				testData.getPassword());

	}

	// Auto Enrollment Scenario 2, PP-2134
	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientAutoEnrolment() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		String patientFirstName = "Betapatient" + IHGUtil.createRandomNumericString();

		logStep("Patient Activation at First Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationPortalLink(1, driver, patientsEmail,
				testData.getProperty("doctorLogin1"), testData.getProperty("doctorPassword1"),
				testData.getProperty("portalUrl"), patientFirstName);
		logStep("Activation Link of First Practice is " + unlockLinkPortal);

		logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
		String unlockLinkEmail = new Mailinator().getLinkFromEmail(patientsEmail,
				INVITE_EMAIL_SUBJECT_PATIENT + testData.getProperty("practiceName1"), INVITE_EMAIL_BUTTON_TEXT, 10);
		assertNotNull(unlockLinkEmail, "Error: Activation link not found.");

		logStep("Retrieved activation link for first Practice is " + unlockLinkEmail);
		if (!isInviteLinkFinal(unlockLinkEmail)) {
			unlockLinkEmail = getRedirectUrl(unlockLinkEmail);
			log("Retrieved link was redirect link. Final link for second Practice is " + unlockLinkEmail);
		}
		logStep("Comparing with portal unlock link for first Practice " + unlockLinkPortal);
		assertEquals(unlockLinkEmail, unlockLinkPortal, "!patient unlock links are not equal!");

		logStep("Finishing of patient activation for Practice1: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getProperty("DOBYear"));

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				patientActivationSearchTest.getPatientIdString(), testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Patient Activation on Second Practice Portal- Patient Activation link will not be present");
		PatientActivationSearchTest patientActivationSearchTest12 = new PatientActivationSearchTest();
		patientActivationSearchTest12.getPatientActivationPortalLink(0, driver, patientsEmail,
				testData.getProperty("doctorLoginPractice2"), testData.getProperty("doctorPasswordPractice2"),
				testData.getPortalUrl(), patientFirstName);

		log("Waiting for welcome mail at patient inbox from second practice");
		Instant testStart = Instant.now();
		Email visitPortal = new Mailer(patientsEmail).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 60,
				testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		if (!isWelcomeLinkFinal(portalUrlLink)) {
			portalUrlLink = getRedirectUrl(portalUrlLink);
		}

		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(patientActivationSearchTest.getPatientIdString(),
				testData.getPassword());

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Switching to Second Practice to verify auto enrollment");
		jalapenoHomePage.switchPractice(testData.getProperty("practiceName1"));

		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());
		logStep("Auto Enrolment to Second Practice is completed");
	}
	
	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMinorAutoEnrollment() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		String guardianPatientLogin = PortalUtil.generateUniqueUsername("login", testData); 
		String guardianPatientLastName = guardianPatientLogin.replace("login", "last"); 
		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		String dependentPatientFirstName = "Dependent" + IHGUtil.createRandomNumericString();

		logStep("Patient Activation at First Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationPortalLink(1, driver, patientsEmail,
				testData.getProperty("doctorLogin1"), testData.getProperty("doctorPassword1"),
				testData.getProperty("portalUrl"), dependentPatientFirstName);
		logStep("Activation Link of First Practice is " + unlockLinkPortal);
		
		logStep("Sign up using invite link from Practice Portal1");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());
		
		logStep("Continue registration - check dependent info and fill guardian name");
		linkAccountPage.checkDependentInfo(dependentPatientFirstName, "Tester", patientsEmail);
		SecurityDetailsPage accountDetailsPage = linkAccountPage.continueToCreateGuardianOnly("Guardian",
				guardianPatientLastName, "Parent");

		logStep("Continue registration - create dependents credentials and continue to Home page");
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(guardianPatientLogin,
				testData.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(),
				testData.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(true));

		logStep("Patient Activation on Second Practice Portal- Patient Activation link will not be present");
		PatientActivationSearchTest patientActivationSearchTest12 = new PatientActivationSearchTest();
		patientActivationSearchTest12.getPatientActivationPortalLink(0, driver, patientsEmail,
				testData.getProperty("doctorLoginPractice2"), testData.getProperty("doctorPasswordPractice2"),
				testData.getPortalUrl(), dependentPatientFirstName);
		
		log("Waiting for welcome mail at patient inbox from second practice");
		Instant testStart = Instant.now();
		Email visitPortal = new Mailer(patientsEmail).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT,
				60, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);

		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);

		System.out.println("patient portal url is " + portalUrlLink);

		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		if (!isWelcomeLinkFinal(portalUrlLink)) {
			portalUrlLink = getRedirectUrl(portalUrlLink);
		}

		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage jalapenoHomePage = loginPage.login(guardianPatientLogin,
				testData.getPassword());

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Switching to Second Practice to verify auto enrollment");
		jalapenoHomePage.switchPractice(testData.getProperty("practiceName1"));
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());
		
		logStep("Auto Enrollment to Second Practice is completed");
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGuardianAutoEnrollment() throws Exception {
		String guardianpatientEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		String guardianFirstName = "BetaGuardian" + IHGUtil.createRandomNumericString();
		String guardianLogin = PortalUtil.generateUniqueUsername("login", testData);

		logStep("Guardian Patient Activation at Practice Portal1");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationPortalLink(1, driver,
				guardianpatientEmail, testData.getProperty("doctorLogin1"), testData.getProperty("doctorPassword1"),
				testData.getPortalUrl(), guardianFirstName);

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(guardianLogin,
				testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Registering Dependent patient at Practice1");
		String patientFirstName = "BetaDependent" + IHGUtil.createRandomNumericString();

		String guardianLinkPortal01 = patientActivationSearchTest.getPatientActivationPortalLink(1, driver,
				guardianpatientEmail, testData.getProperty("doctorLogin1"), testData.getProperty("doctorPassword1"),
				testData.getPortalUrl(), patientFirstName);

		logStep("Logging into Mailinator and getting Patient Activation url");
		String unlockLinkEmail01 = new Mailinator().getLinkFromEmail(guardianpatientEmail,
				GUARDIAN_INVITE_SUBJECT + testData.getProperty("practiceName1"), INVITE_EMAIL_BUTTON_TEXT, 10);
		log("Guardian invite subject from mail is " + unlockLinkEmail01);
		assertNotNull(unlockLinkEmail01, "Error: Activation link not found.");

		log("UNLOCK LINK EMAIL PORTAL1+=====" + unlockLinkEmail01);

		logStep("Sign up using invite link from Practice Portal1");

		patientVerificationPage.getToThisPage(guardianLinkPortal01);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill login credentials");
		linkAccountPage.checkDependentInfo(patientFirstName, "Tester", guardianpatientEmail);
		jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(guardianLogin, testData.getPassword(), "Parent");

		logStep("Continue to the portal and check elements");
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

		logStep("Retrieved activation link is " + unlockLinkEmail01);
		if (!isInviteLinkFinal(unlockLinkEmail01)) {
			unlockLinkEmail01 = getRedirectUrl(unlockLinkEmail01);
			log("Retrieved link was redirect link. Final link is " + unlockLinkEmail01);
		}
		logStep("Comparing with portal unlock link " + guardianLinkPortal01);
		assertEquals(unlockLinkEmail01, guardianLinkPortal01, "!patient unlock links are not equal!");

		logStep("Registering Dependent Patient at Practice2");
		patientActivationSearchTest.getPatientActivationPortalLink(0, driver, guardianpatientEmail,
				testData.getProperty("doctorLoginPractice2"), testData.getProperty("doctorPasswordPractice2"),
				testData.getPortalUrl(), patientFirstName);

		logStep("Validate Welcome mail recieved by guardianpatient at Practice Portal2");

		log("Waiting for welcome mail at patient inbox from second practice");
		Instant testStart = Instant.now();
		Email visitPortal = new Mailer(guardianpatientEmail).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT,
				60, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);

		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);

		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		if (!isWelcomeLinkFinal(portalUrlLink)) {
			portalUrlLink = getRedirectUrl(portalUrlLink);
		}

		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(guardianLogin, testData.getPassword());

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		jalapenoHomePage.faChangePatient();
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

		logStep("Switching to Second Practice to verify auto enrollment");
		jalapenoHomePage.switchPractice(testData.getProperty("practiceName1"));

		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());
		logStep("Auto Enrolment of Guardian and Dependent to Second Practice is completed");
	}

	/**
	 * Adding attachments to the Secure message appointment request while processing
	 * them with the following appointment statuses (Cancel, Communicate Only
	 *
	 */
	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestCancelAttachment() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";
		String attachmentFile = "QuickSend.pdf";

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click appointment request");
		homePage.clickOnAppointmentV2(driver);

		// workaround for extra appointments list when multiple appointments solutions
		// are on
		try {

			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");
		}

		JalapenoAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver,
				JalapenoAppointmentRequestV2Step1.class);
		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2.: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.areBasicPageElementsPresent());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal to Approved the request with adding Attachment");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequesAttachmentt(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword(), MessageErrorfilePath,
				MessagefilePath);

		logStep("Login back to patient portal to check the Approved status");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved " + tsPracticePortal));

		logStep("Verify the Attachmnet file Text");
		assertEquals(attachmentFile, messagesPage.getAttachmentPdfFile());
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal second time to cancel the Approved status ");
		AppoitmentRequest practicePortalApproved = new AppoitmentRequest();
		long tsPracticePortalCancel = practicePortalApproved.ProceedAppoitmentRequestcancel(driver, true,
				appointmentReason, testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal second time to verify the Cancel status");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPageCancel = homePage.showMessages(driver);

		logStep("Looking for appointment Cancel from doctor ");
		assertTrue(messagesPageCancel.isMessageDisplayed(driver, "Cancel " + tsPracticePortalCancel));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal third time to communicate the cancel status ");
		AppoitmentRequest practicePortalCommunicate = new AppoitmentRequest();
		long tsPracticePortalcommunicate = practicePortalCommunicate.ProceedAppoitmentRequestCommunicateOnly(driver,
				true, appointmentReason, testData.getPortalUrl(), testData.getDoctorLogin2(),
				testData.getDoctorPassword());

		logStep("Login back to patient portal third time to verify the communicate only status");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPageCommunicate = homePage.showMessages(driver);

		logStep("Looking for appointment Communicate only status from doctor");
		assertTrue(
				messagesPageCommunicate.isMessageDisplayed(driver, "Communicate only " + tsPracticePortalcommunicate));
		homePage.clickOnLogout();
	}

	/**
	 * Updating appointment request while processing them with the following
	 * appointment statuses Update Appointment.
	 *
	 */
	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestUpdate() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click appointment request");
		homePage.clickOnAppointmentV2(driver);

		try {

			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");
		}

		JalapenoAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver,
				JalapenoAppointmentRequestV2Step1.class);
		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2.: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.areBasicPageElementsPresent());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequest(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved " + tsPracticePortal));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal for second time ");
		AppoitmentRequest practicePortalApproved = new AppoitmentRequest();
		long tsPracticePortalUpdate = practicePortalApproved.ProceedAppoitmentRequestUpdate(driver, true,
				appointmentReason, testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal for update Appointment ");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPageUpdate = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPageUpdate.isMessageDisplayed(driver, "Update Appointment " + tsPracticePortalUpdate));
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrescriptionGuardian() throws Exception {

		String patientLogin = PortalUtil.generateUniqueUsername("login", testData);
		String patientLastName = patientLogin.replace("login", "last");
		String patientEmail = patientLogin.replace("login", "mail") + "@mailinator.com";

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("doctorLogin1"),
				testData.getProperty("doctorPassword1"));

		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Register Guardian - Enter all the details and click on Register");
		String patientUrl = patientActivationPage.setInitialDetailsAllFields("Guardian", patientLastName, "F",
				patientLastName + "G", testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYear(), "address1", "address2", "city", "Alabama",
				testData.getZipCode());

		logStep("Register Dependent - Enter all the details and click on Register");
		String guardianUrl = patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "M",
				patientLastName + "D", testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama",
				testData.getZipCode());
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin,
				testData.getPassword(), testData);

		log("Login username of Guardian is " + patientLogin);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(guardianUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill login credentials");
		log("Login username of Guardian is " + patientLogin);
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, patientEmail);
		jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(patientLogin, testData.getPassword(), "Parent");

		logStep("Logout, login and change patient");
		JalapenoLoginEnrollment loginPage = jalapenoHomePage.clickOnLogoutEnrollment();
		jalapenoHomePage = loginPage.login(patientLogin, testData.getPassword());

		logStep("Guardian requesting Prescription Renewal for self");
		JalapenoPrescriptionsPage prescriptionsPage = jalapenoHomePage.clickOnPrescriptions(driver);
		Thread.sleep(10000);
		prescriptionsPage.prescriptionPayment();
		jalapenoHomePage = prescriptionsPage.fillThePrescription(driver, "XANAX", "21", 10);

		jalapenoHomePage.clickOnLogoutEnrollment();

		logStep("Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin1 = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome1 = practiceLogin1.login(testData.getProperty("doctorLogin1"),
				testData.getDoctorPassword());

		logStep("Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome1.clickonRxRenewal();

		logStep("Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday();

		logStep("Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		logStep("Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.setRxRenewalFields("Guardian");

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
		practiceHome1.logOut();

		JalapenoLoginEnrollment loginPage2 = new JalapenoLoginEnrollment(driver, testData.getProperty("url3"));
		JalapenoHomePage homePage2 = loginPage2.login(patientLogin, testData.getPassword());

		JalapenoMessagesPage messagesPage = homePage2.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));
	}

	/**
	 * Updating appointment request while processing them with the following
	 * appointment statuses -Process Externaly.
	 *
	 */

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentProcessExternallyStatus() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click appointment request");
		homePage.clickOnAppointmentV2(driver);

		try {

			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");
		}

		JalapenoAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver,
				JalapenoAppointmentRequestV2Step1.class);
		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2.: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.areBasicPageElementsPresent());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentProcessExternallyRequest(driver, true,
				appointmentReason, testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal1 = new AppoitmentRequest();
		practicePortal1.ProceedAppoitmentRequestExternalProcess(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment Process Externally from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Process Externally " + tsPracticePortal));
		homePage.clickOnLogout();

	}

	/**
	 * Updating appointment request while processing them with the following
	 * appointment statuses -Pending .
	 *
	 */

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentPendingStatus() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click appointment request");
		homePage.clickOnAppointmentV2(driver);

		try {

			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");
		}

		JalapenoAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver,
				JalapenoAppointmentRequestV2Step1.class);
		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2.: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.areBasicPageElementsPresent());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentSetToPendingRequest(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Proceed in Practice Portal and verify the request is present on the request made");
		AppoitmentRequest practicePortal1 = new AppoitmentRequest();
		practicePortal1.ProceedAppoitmentRequestSetToPending(driver, true, appointmentReason, testData.getPortalUrl(),
				testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment Set to Pending from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Set to Pending " + tsPracticePortal));
		homePage.clickOnLogout();

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrescriptionDependent() throws Exception {

		String patientLogin = PortalUtil.generateUniqueUsername("login", testData);
		String patientLastName = patientLogin.replace("login", "last");
		String patientEmail = patientLogin.replace("login", "mail") + "@mailinator.com";

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("doctorLogin1"),
				testData.getProperty("doctorPassword1"));

		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Register Guardian - Enter all the details and click on Register");
		String patientUrl = patientActivationPage.setInitialDetailsAllFields("Guardian", patientLastName, "F",
				patientLastName + "G", testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYear(), "address1", "address2", "city", "Alabama",
				testData.getZipCode());

		logStep("Register Dependent - Enter all the details and click on Register");
		String guardianUrl = patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "M",
				patientLastName + "D", testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama",
				testData.getZipCode());
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin,
				testData.getPassword(), testData);

		log("Login username of Guardian is " + patientLogin);

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(guardianUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill login credentials");
		log("Login username of Guardian is " + patientLogin);
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, patientEmail);
		jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(patientLogin, testData.getPassword(), "Parent");

		logStep("Guardian requesting Prescription Renewal for his dependent");

		JalapenoPrescriptionsPage dPrescriptionsPage = jalapenoHomePage.clickOnPrescriptions(driver);
		Thread.sleep(10000);
		dPrescriptionsPage.prescriptionPayment();
		jalapenoHomePage = dPrescriptionsPage.fillThePrescription(driver, "XANAX", "21", 10);

		jalapenoHomePage.clickOnLogoutEnrollment();

		logStep("Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage dPracticeLogin1 = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage dPracticeHome11 = dPracticeLogin1.login(testData.getProperty("doctorLogin1"),
				testData.getDoctorPassword());

		logStep("Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage dRxRenewalSearchPage = dPracticeHome11.clickonRxRenewal();

		logStep("Search for Today's RxRenewal in Practice Portal");
		dRxRenewalSearchPage.searchForRxRenewalToday();

		logStep("Get the RxRenewal Details in Practice Portal");
		dRxRenewalSearchPage.getRxRenewalDetails();

		logStep("Set the RxRenewal Fields in Practice Portal");
		dRxRenewalSearchPage.setRxRenewalFields("Dependent");

		logStep("Click On Process RxRenewal Button in Practice Portal");
		dRxRenewalSearchPage.clickProcessRxRenewal();
		String dependentSubject = dRxRenewalSearchPage.getSubject();
		logStep("Verify Prescription Confirmation in Practice Portal");
		dRxRenewalSearchPage.verifyPrescriptionConfirmationSection(dependentSubject);

		logStep("Set Action Radio Button in Practice Portal");
		dRxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		dRxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		dPracticeHome11.logOut();

		JalapenoLoginEnrollment dLoginPage2 = new JalapenoLoginEnrollment(driver, testData.getProperty("url3"));
		JalapenoHomePage dHomePage2 = dLoginPage2.login(patientLogin, testData.getPassword());

		logStep("Switch to dependent");
		jalapenoHomePage.faChangePatient();

		JalapenoMessagesPage dMessagesPage = dHomePage2.showMessages(driver);

		logStep("Looking for Prescription approval from doctor");
		assertTrue(dMessagesPage.isMessageDisplayed(driver, "RxRenewalSubjectDependent"));

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientEducationKnownIssue() throws InterruptedException {
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage
				.login(testData.getProperty("patientEducationWithissueUserName"), testData.getPassword());
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Navigate to Medical Record Summaries Page");
		MedicalRecordSummariesPage recordSummaries = jalapenoHomePage.clickOnMedicalRecordSummaries(driver);

		logStep("Select first visible CCD");
		recordSummaries.selectFirstVisibleCCD();

		logStep("Click on patient Education Button ");
		recordSummaries.clickPatientEducation();

		logStep("Validating the issue's on Care Nexis Page");
		assertEquals(recordSummaries.gethypertensiveEmergency(), "Hypertensive Emergency");
		assertEquals(recordSummaries.getHypothyroidism(), "Hypothyroidism");
		assertEquals(recordSummaries.getOrganTransplantRejection(), "Organ Transplant Rejection");
		assertEquals(recordSummaries.getFever(), "Fever");

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPayBillsCardMasked() throws Exception {
		logStep("Initiate payment data");
		String accountNumber = IHGUtil.createRandomNumericString(8);
		String amount = IHGUtil.createRandomNumericString(3);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);
		Instant messageBuildingStart = Instant.now();
		String messageSubject = "Pay My Bill";

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());
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
		assertTrue(homePage.wasPayBillsSuccessfull());

		logStep("Click on Pay Bills");
		payBillsPage = homePage.clickOnNewPayBills(driver);

		logStep("Click on Payment History and select the recent transaction");
		payBillsPage.clickPaymentHistory();

		logStep("Verifying credit card ending in payment receipt");
		String creditCardEnding=payBillsPage.getReceiptCreditCardDigit();
		assertTrue(payBillsPage.getReceiptCreditCardDigit().equals(creditCard.getLastFourDigits()));
		homePage.clickOnLogout();
		
		logStep("Load login page");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click on messages solution and navigate to Inbox");
		JalapenoMessagesPage messagesPage = homePage.showMessagesSent(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent());

		logStep("Waiting for message in SecureMessage Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageSubject));
		
		logStep("Verifying the Payment Notification Mail is received by the patient or not");
		String notificationEmailSubject = "Payment Receipt";
		String[] mailAddress = "automationjalapeno2@mailinator.com".split("@");
		Email email = new Mailer(mailAddress[0]).pollForNewEmailWithSubject(notificationEmailSubject, 90,
				testSecondsTaken(messageBuildingStart));
		assertNotNull(email, "Error: No new message notification recent enough found");
		String emailBody = email.getBody();
		assertTrue(emailBody.contains("************"+creditCardEnding));
		
		homePage.clickOnLogout();

	}
}
