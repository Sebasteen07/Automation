//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.patientportal2.test;

import static org.testng.Assert.*;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.PatientFactory;
import com.medfusion.pojos.Patient;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountDevicesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPreferencesPage;
import com.medfusion.product.object.maps.patientportal2.page.PayNow.JalapenoPayNowPage;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.medfusion.product.patientportal2.utils.PortalUtil2;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.pojo.ExpectedEmail;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.patientportalbroadcast.PatientPortalBroadcastPage;
import com.intuit.ihg.product.object.maps.sitegen.page.pharmacy.AddPharmacyPage;
import static com.intuit.ihg.product.object.maps.sitegen.page.pharmacy.AddPharmacyPage.pharmacyName;
import com.intuit.ihg.product.object.maps.sitegen.page.pharmacy.ManageYourPharmacies;
import com.intuti.ihg.product.object.maps.sitegen.page.onlineBillPay.EstatementPage;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.common.utils.YopMail;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginEnrollment;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2HistoryPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step1;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step2;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2HistoryDetailPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2HistoryListPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page1;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page2;
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
import com.medfusion.product.object.maps.patientportal2.page.MedicationsPage.LocationAndProviderPage;
import com.medfusion.product.object.maps.patientportal2.page.MedicationsPage.MedicationsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.MedicationsPage.MedicationsHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MedicationsPage.SelectMedicationsPage;
import com.medfusion.product.object.maps.patientportal2.page.MedicationsPage.PrescriptionFeePage;
import com.medfusion.product.object.maps.patientportal2.page.MedicationsPage.SelectPharmacyPage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountSecurityPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsStatementPdfPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;
import com.medfusion.product.object.maps.patientportal2.page.ScheduleAppoinment.JalapenoAppoinmentSchedulingPage;
import com.medfusion.product.object.maps.patientportal2.page.ThirdPartySso.ThirdPartySsoPage;
import com.medfusion.product.object.maps.patientportal2.page.util.CreatePatient;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep1Page;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep2Page;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep3Page;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep4Page;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffSearchPage;
import com.medfusion.product.object.maps.practice.page.familyManagement.AgeOutReportPage;
import com.medfusion.product.object.maps.practice.page.familyManagement.PatientTrustedRepresentativePage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.object.maps.practice.page.patientMessaging.PatientMessagingPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.object.maps.practice.page.rxrenewal.RxRenewalSearchPage;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.api.utils.PracticeUtil;
import com.medfusion.product.practice.tests.AppoitmentRequest;
import com.medfusion.product.practice.tests.PatientActivationSearchTest;
import com.medfusion.qa.mailinator.Email;
import com.medfusion.qa.mailinator.Mailer;

public class PatientPortal2AcceptanceTests extends BaseTestNGWebDriver {

	private static final String NEW_EMAIL_TEMPLATE = "jalapeno2.%s@yopmail.com";
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
	private static final String questionText = "What is the question";
	private static final String DRUG_DOSAGE = "35 mg";
	private static final String DEPENDENT_DRUG_DOSAGE = "150 mg";
	private static final String ErrorfilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\File_Attachment\\Error_Files_Testing.pdf";
	private static final String CorrectfilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\File_Attachment\\sw-test-academy.txt";
	private static final String MessagefilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\documents\\QuickSend.pdf";
	private static final String MessageErrorfilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\documents\\SecureMessageFile.pdf";
	private static final String InvalidfilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\File_Attachment\\Error_Files_Testing1.json";
	private static final String WEBSITE_LINK = "Visit our website";

	private PropertyFileLoader testData;
	private Patient patient = null;

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
			String username = PortalUtil2.generateUniqueUsername(testData.getProperty("tr.user.id"), testData);
			patient = PatientFactory.createJalapenoPatient(username, testData);
			patient = new CreatePatient().selfRegisterPatient(driver, patient, testData.getUrl());
		}
	}

	public void createCommonPatientWithStatement() throws Exception {
		if (patient == null) {
			String username = PortalUtil2.generateUniqueUsername(testData.getProperty("tr.user.id"), testData);
			patient = PatientFactory.createJalapenoPatient(username, testData);
			patient = new CreatePatient().selfRegisterPatientWithPreference(driver, patient, testData.getUrl(), 2);
		}
	}

	// TODO Consolidate these create patients and make sure we are not using the
	// method to logout at the end. Logging out every time is time consuming when we
	// use this method several times through this automation.
	public void createPatient() throws Exception {
		if (patient == null) {
			String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
			patient = PatientFactory.createJalapenoPatient(username, testData);
			patient = new CreatePatient().registerPatient(driver, patient, testData.getUrl());
		}
	}

	public void createUnderAgePatient() throws Exception {
		if (Objects.isNull(patient)) {
			String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
			patient = PatientFactory.createJalapenoPatient(username, testData);
			patient = new CreatePatient().selfRegisterUnderAgePatient(driver, patient, testData.getUrl());
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

		log("Assessing login page elements");
		jalapenoLoginPage.assessPageElements();
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginValidCredentialsAndValidateMenuElements() {
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Fill in credentials and log in");
		JalapenoHomePage jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Validate menu elements");
		assertTrue(jalapenoHomePage.areMenuElementsPresent());

		logStep("Log out");
		loginPage = jalapenoHomePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginRememberUsername() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Fill in credentials, Remember username unchecked and log in");
		loginPage.selectRememberUsernameCheckbox("uncheck");
		JalapenoHomePage jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Log out");
		loginPage = jalapenoHomePage.clickOnLogout();

		logStep("Verify username textfield is empty");
		assertTrue(loginPage.getUserNameFieldText().equals(""));

		logStep("Fill in credentials, Remember username checked and log in");
		loginPage.selectRememberUsernameCheckbox("check");
		loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Log out");
		loginPage = jalapenoHomePage.clickOnLogout();

		logStep("Verify username textfield has previously signed in username");
		assertTrue(loginPage.getUserNameFieldText().contains(testData.getUserId()));
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginEmptyUserName() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Fill in empty credentials and log in");
		loginPage.loginEmptyCredentials();

		logStep("Verify username error displayed");
		assertTrue(loginPage.getUserErrorText().contentEquals("Please enter a user name."));

		logStep("Verify password error displayed");
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

	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatient() throws Exception {
		createCommonPatient();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Checking if the information are correct");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage myAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(myAccountPage.checkZipCode(patient.getZipCode()));
		assertTrue(myAccountPage.checkGender(patient.getGender()));

		logStep("Logging out");
		loginPage = homePage.clickOnLogout();

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
				PracticeConstants.ZIP_CODE, JalapenoConstants.DATE_OF_BIRTH_MONTH_NO,
				JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				patientActivationSearchTest.getPatientIdString(), testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");

		logStep("Checking if address in My Account is filled");
		JalapenoAccountPage accountPage = jalapenoHomePage.clickOnAccount();
		JalapenoMyAccountProfilePage jalapenoMyAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(
				jalapenoMyAccountPage.checkForAddress(driver, "5501 Dillard Dr", "Cary", PracticeConstants.ZIP_CODE));

		logStep("Logging out");
		JalapenoLoginPage jalapenoLoginPage = jalapenoHomePage.clickOnLogout();

		logStep("Logging again: " + patientActivationSearchTest.getPatientIdString() + " \\ " + testData.getPassword());
		jalapenoHomePage = jalapenoLoginPage.login(patientActivationSearchTest.getPatientIdString(),
				testData.getPassword());

		logStep("Logging out");

		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();

		logStep("Logging into yopmail and getting Patient Activation url");
		YopMail mail = new YopMail(driver);
		String unlockLinkEmail = mail.getLinkFromEmail(patientsEmail,
				INVITE_EMAIL_SUBJECT_PATIENT + testData.getPracticeName().replace(" ", ""), INVITE_EMAIL_BUTTON_TEXT,
				10);
		assertNotNull(unlockLinkEmail, "Error: Activation link not found.");
		logStep("Retrieved activation link is " + unlockLinkEmail);
		if (!isInviteLinkFinal(unlockLinkEmail)) {
			unlockLinkEmail = getRedirectUrl(unlockLinkEmail);
			log("Retrieved link was redirect link. Final link is " + unlockLinkEmail);
		}
		logStep("Comparing with portal unlock link " + unlockLinkPortal);
		assertEquals(unlockLinkEmail, unlockLinkPortal, "!patient unlock links are not equal!");
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testForgotPassword() throws Exception {
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().selfRegisterPatient(driver, patient, testData.getUrl());
		resetForgottenPasswordOrUsername(patient.getEmail());
		logStep("Login with Old Password");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		loginPage.loginUnsuccessfuly(patient.getUsername(), patient.getPassword());
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientForgotUserIdCaseInsensitiveEmail() throws Exception {
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().selfRegisterPatient(driver, patient, testData.getUrl());
		String email = IHGUtil.mixCase(patient.getEmail());
		resetForgottenPasswordOrUsername(email);
	}

	private void resetForgottenPasswordOrUsername(String email) throws InterruptedException {
		Instant passwordResetStart = Instant.now();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Clicking on forgot username or password");
		JalapenoForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordButton();

		JalapenoForgotPasswordPage2 forgotPasswordPage2 = forgotPasswordPage.fillInDataPage(email);
		logStep("Message was sent, closing");
		forgotPasswordPage2.clickCloseButton();

		logStep("Logging into yopmail and getting ResetPassword url");
		String[] mailAddress = email.split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";

		YopMail mail = new YopMail(driver);
		String url = mail.getLinkFromEmail(mailAddress[0], emailSubject, inEmail, 10);

		if (!isInviteLinkFinal(url)) {
			url = getRedirectUrl(url);
		}
		assertNotNull(url, "Url is null.");

		JalapenoForgotPasswordPage3 forgotPasswordPage3 = new JalapenoForgotPasswordPage3(driver, url);
		logStep("Redirecting to patient portal, filling secret answer as: " + patient.getSecurityQuestionAnswer());
		JalapenoForgotPasswordPage4 forgotPasswordPage4 = forgotPasswordPage3
				.fillInSecretAnswer(patient.getSecurityQuestionAnswer());

		logStep("Filling new password");
		JalapenoHomePage homePage = forgotPasswordPage4.fillInNewPassword(testData.getProperty("med.wf.password"));

		logStep("Logging out");
		loginPage = homePage.clickOnLogout();

	}

	private String resetForgotPasswordLink(String email) throws InterruptedException {
		Instant passwordResetStart = Instant.now();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Clicking on forgot username or password");
		JalapenoForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordButton();

		JalapenoForgotPasswordPage2 forgotPasswordPage2 = forgotPasswordPage.fillInDataPage(email);
		logStep("Message was sent, closing");
		forgotPasswordPage2.clickCloseButton();

		logStep("Logging into yopmail and getting ResetPassword url");
		String[] mailAddress = email.split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";

		YopMail mail = new YopMail(driver);
		String resetUrl = mail.getLinkFromEmail(mailAddress[0], emailSubject, inEmail, 20);

		if (!isInviteLinkFinal(resetUrl)) {
			resetUrl = getRedirectUrl(resetUrl);
		}
		assertNotNull(resetUrl, "Url is null.");

		JalapenoForgotPasswordPage3 forgotPasswordPage3 = new JalapenoForgotPasswordPage3(driver, resetUrl);
		logStep("Redirecting to patient portal, filling secret answer as: " + patient.getSecurityQuestionAnswer());
		JalapenoForgotPasswordPage4 forgotPasswordPage4 = forgotPasswordPage3
				.fillInSecretAnswer(patient.getSecurityQuestionAnswer());

		logStep("Filling new password");
		JalapenoHomePage homePage = forgotPasswordPage4.fillInNewPassword(testData.getProperty("med.wf.password"));

		logStep("Logging out");
		loginPage = homePage.clickOnLogout();

		return resetUrl;

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMessaging() throws Exception {
		String messageSubject = "Namaste " + System.currentTimeMillis();

		logStep("Login physician");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		// Instant messageBuildingStart = Instant.now();
		logStep("Send a new secure message to static patient");
		PatientMessagingPage patientMessagingPage = practiceHome.clickPatientMessagingTab();
		ArrayList<String> practicePortalMessage = patientMessagingPage.setFieldsAndPublishMessage(testData,
				"TestingMessage", messageSubject);

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

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
		String[] mailAddress = testData.getProperty("eamil.build.message").split("@");
		String emailBody = "Sign in to view this message";
		YopMail mail = new YopMail(driver);
		String email = mail.getLinkFromEmail(mailAddress[0], notificationEmailSubject, emailBody, 15);

		// Email email = new
		// Mailer(mailAddress[0]).pollForNewEmailWithSubject(notificationEmailSubject,
		// 90,
		// testSecondsTaken(messageBuildingStart));
		assertNotNull(email, "Error: No new message notification recent enough found");
		// String emailBody = email.getBody();
		// assertTrue(emailBody.contains("Sign in to view this message"));
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMessageArchiving() throws Exception {
		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');

		logStep("Patient Activation on Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationLink(driver, testData, patientsEmail);

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				PracticeConstants.ZIP_CODE, JalapenoConstants.DATE_OF_BIRTH_MONTH_NO,
				JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				patientActivationSearchTest.getPatientIdString(), testData.getPassword(), testData);

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = jalapenoHomePage.showMessages(driver);

		assertTrue(messagesPage.returnSubjectMessage().length() > 0);

		logStep("Click on the archive button from inbox tab");
		messagesPage.archiveMessage();

		logStep("Go to archived tab");
		messagesPage.goToArchivedMessages();

		int messageCount = messagesPage.MessageCount();

		logStep("Click on the unarchive button");
		messagesPage.clickOnUnArchive();

		logStep("Go to Inbox messages tab");
		messagesPage.goToInboxMessage();
		int y = messagesPage.MessageCount();

		logStep("Message archicved Successfuly");
		assertTrue(y == messageCount);

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testViewCCD() throws Exception {

		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(),
				testData.getPassword());

		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		JalapenoCcdViewerPage jalapenoCcdPage = jalapenoMessagesPage.findCcdMessage(driver);

		if ((IHGUtil.getEnvironmentType().toString().equals("DEV3"))
				|| (IHGUtil.getEnvironmentType().toString().equals("QA1"))) {
			log("Skipping method checkPdfToDownload and checkRawToDownload because of known issue on DEV3&&QA1 javax.net.ssl.SSLHandshakeException");
		} else {
			assertTrue(jalapenoCcdPage.checkPdfToDownload(driver));
			assertTrue(jalapenoCcdPage.checkRawToDownload(driver));
		}
		assertTrue(jalapenoCcdPage.sendInformationToDirectEmail(DIRECT_EMAIL_ADDRESS));

		jalapenoMessagesPage = jalapenoCcdPage.closeCcd(driver);

		logStep("Logging out");
		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();

	}

	@Test(enabled = false, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendCCDToNonSecureEmail() throws Exception {
		String email = System.currentTimeMillis() + "unsecure@yopmail.com";

		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(),
				testData.getPassword());

		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		JalapenoCcdViewerPage jalapenoCcdPage = jalapenoMessagesPage.findCcdMessage(driver);

		assertTrue(jalapenoCcdPage.sendInformationToUnsecureEmail(email));

		// TODO: Check if email arrived once is in use
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthRecords() {
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getCCDPatientUsername(),
				testData.getPassword());

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
		recordSummaries.gotoOtherDocumentTab();
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientEducationNoIssue() throws InterruptedException {
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage
				.login(testData.getProperty("patient.education.no.issue.username"), testData.getPassword());

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
		String value = pUtil.getFilepath(testData.getProperty("file.directory"))
				.concat(testData.getProperty("health.records.send.pdf.file.name"));

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
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("documents.patient.user.id"),
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

		logStep("Try to create the same patient");
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();

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

		logStep("Try to create the same patient in different practice");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));

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

		logStep("Going to create account page");
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();

		logStep("Creating patient with the same data as in practice portal");
		patientDemographicPage.fillInPatientData(patientActivationSearchTest.getFirstNameString(),
				patientActivationSearchTest.getLastNameString(), patientActivationSearchTest.getEmailAddressString(),
				JalapenoConstants.DATE_OF_BIRTH_MONTH, JalapenoConstants.DATE_OF_BIRTH_DAY,
				JalapenoConstants.DATE_OF_BIRTH_YEAR, Patient.GenderExtended.MALE, PracticeConstants.ZIP_CODE); // TODO
																												// use
																												// only
																												// one
																												// constant
																												// file
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
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
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

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		// Thread.sleep(1000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");

		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequest(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved " + tsPracticePortal));
	}

	
	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPayBills() throws Exception {
		logStep("Initiate payment data");
		String accountNumber = IHGUtil.createRandomNumericString(8);
		String amount = IHGUtil.createRandomNumericString(3);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);

		createCommonPatientWithStatement();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		logStep("Remove all cards because Selenium can't see AddNewCard button");
		payBillsPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(payBillsPage.isAnyCardPresent());

		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, accountNumber,
				creditCard);

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
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

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
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaV2(testData.getProperty("aska.v2.name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.fillAndContinue(askaSubject, questionText);

		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPage1 = homePage.openSpecificAskaV2(testData.getProperty("aska.v2.name"));
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
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("sa.provider.username"),
				testData.getProperty("sa.provider.password"));

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
		homePage = loginPage.login(testData.getProperty("aska.v2.user"), testData.getProperty("aska.v2.password"));

		logStep("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver,
				"Automated Test " + (Long.toString(detailStep2.getCreatedTimeStamp()))));

		JalapenoLoginPage loginPageNew = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePageNew = loginPageNew.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		logStep("Click Ask A Staff tab");
		askPage1 = homePageNew.openSpecificAskaV2(testData.getProperty("aska.v2.name"));
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
		String patientLogin = PortalUtil2.generateUniqueUsername("login", testData); // guardian's login
		String patientLastName = patientLogin.replace("login", "last"); // lastname for both
		String patientEmail = patientLogin.replace("login", "mail") + "@yopmail.com"; /// email for both

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

		logStep("Using Yopmail Mailer to retrieve the latest emails for patient and guardian");

		String emailSubjectGuardian = "You are invited to create a Patient Portal guardian account atJalapeno Automation";
		testData.getPracticeName();

		YopMail mail = new YopMail(driver);
		String guardianUrlEmail = mail.getLinkFromEmail(patientEmail, emailSubjectGuardian, INVITE_EMAIL_BUTTON_TEXT,
				15);

		// Email emailGuardian = new
		// Mailer(patientEmail).pollForNewEmailWithSubject(emailSubjectGuardian, 30,
		// testSecondsTaken(testStart));
		// assertNotNull(emailGuardian,
		// "Error: No email found for guardian recent enough and with specified subject:
		// " + emailSubjectGuardian);
		// String guardianUrlEmail = Mailer.getLinkByText(emailGuardian,
		// INVITE_EMAIL_BUTTON_TEXT);

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
		// Instant testStart = Instant.now();
		String patientLogin = PortalUtil2.generateUniqueUsername("login", testData); // guardian login
		String patientLastName = patientLogin.replace("login", "last");
		String patientEmail = patientLogin.replace("login", "mail") + "@yopmail.com";

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

		logStep("Using yopmail to retrieve the latest emails for patient and guardian");

		String emailSubjectGuardian = "You are invited to create a Patient Portal guardian account at "
				+ testData.getPracticeName();
		System.out.println("This is the emailSubjectGuardian::" + emailSubjectGuardian);
		YopMail mail = new YopMail(driver);
		String guardianUrlEmail = mail.getLinkFromEmail(patientEmail, emailSubjectGuardian, INVITE_EMAIL_BUTTON_TEXT,
				10);
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
		String patientUrlEmail = mail.getLinkFromEmail(patientEmail, emailSubjectPatient, INVITE_EMAIL_BUTTON_TEXT, 10);
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
		String email = "mail" + id + "@yopmail.com";

		logStep("Change patients email to " + email);
		PatientSearchPage patientSearch = patientDashboard.clickEditEmail();
		patientDashboard = patientSearch.changeEmailWithoutModify(email);

		logStep("Send post age-out invitation");
		patientDashboard.sendPostAgeOutInvitation();

		logStep("Wait for email");
		String emailSubject = "Invitation to join our patient portal at " + testData.getPracticeName();

		YopMail mail = new YopMail(driver);
		String patientUrlEmail = mail.getLinkFromEmail(email, emailSubject, INVITE_EMAIL_BUTTON_TEXT, 15);

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

		homePage.isTextDisplayed(
				"You have successfully created your account and can access all of your previous health information.");

		logStep("Logout and login patient");
		JalapenoLoginPage loginPage = homePage.clickOnLogout();
		homePage = loginPage.login(login, testData.getPassword());

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLADocumentsAccess() throws Exception {
		String patientLogin = PortalUtil2.generateUniqueUsername("login", testData); // guardian's login
		String patientLastName = patientLogin.replace("login", "last"); // lastname for both
		String patientEmail = patientLogin.replace("login", "mail") + "@yopmail.com"; // email for both

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
		String value = pUtil.getFilepath(testData.getProperty("file.directory"))
				.concat(testData.getProperty("health.records.send.pdf.file.name"));

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
	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMyAccount() throws Exception {
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().selfRegisterPatient(driver, patient, testData.getUrl());
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
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
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
				PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData), testData);

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient);

		logStep("Waiting for invitation email");

		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(trustedPatient.getEmail(), INVITE_EMAIL_SUBJECT_REPRESENTATIVE,
				INVITE_EMAIL_BUTTON_TEXT, 15);

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

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInviteTrustedRepresentativeWithAccount() throws Exception {
		createPatient();
		String email = testData.getProperty("tr.user.id") + IHGUtil.createRandomNumber() + "@yopmail.com";

		logStep("Go to account page");
		JalapenoHomePage homePage = new JalapenoHomePage(driver);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(testData.getTrustedRepFirstName(), testData.getTrustedRepLastName(),
				email);

		logStep("Waiting for invitation email");
		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(email, INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT,
				15);
		assertNotNull(patientUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());

		logStep("Continue registration - sign in as trusted representative username and password");
		homePage = linkAccountPage.linkPatientToCreateTrustedRep(testData.getTrustedRepUsername(),
				testData.getTrustedRepPassword(), "Spouse");

		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Unlink account from test patient");
		homePage.clickOnAccount();
		accountPage.clickOnUnlinkTrustedRepresentative();
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExtendedGenderQuestion() throws Exception {
		logStep("Creating new patient");
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
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

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBlinkBannerHiding() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Fill in credentials and log in");
		JalapenoHomePage jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Validate Blink banner present");
		assertTrue(jalapenoHomePage.areMenuElementsPresent());
		assertTrue(jalapenoHomePage.isBlinkBannerDisplayed());

		logStep("Hide Blink banner, verify that it hides");
		jalapenoHomePage.clickBlinkBannerHide();
		Thread.sleep(3000);
		assertFalse(jalapenoHomePage.isBlinkBannerDisplayed());

		logStep("Refresh, verify Blink banner is still hidden");
		driver.navigate().refresh();

		assertFalse(jalapenoHomePage.isBlinkBannerDisplayed());

		logStep("Log out");
		loginPage = jalapenoHomePage.clickOnLogout();

		logStep("Log in again");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Verify Blink banner is still hidden");
		assertFalse(jalapenoHomePage.isBlinkBannerDisplayed());
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddandRemovePreferences() throws Exception {
		logStep("Creating new patient");
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
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
		preferencesPage.addPreferredProviderAndSave(testData.getProperty("preferred.provider"));

		logStep("Get preferred providers");
		List<String> updatedPreferredProviders = preferencesPage.getPreferredProviders();

		logStep("Verify if new preferred provider added");
		assertTrue(originalPreferredProviders.size() + 1 == updatedPreferredProviders.size());
		assertTrue(updatedPreferredProviders.contains(testData.getProperty("preferred.provider")),
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
		String askPaidAmount = "$ 2";
		String accountNumber = IHGUtil.createRandomNumericString(8);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);
		JalapenoAskAStaffV2Page2 askPage2 = new JalapenoAskAStaffV2Page2(driver);

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));
		Thread.sleep(3000);
		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaPaidV2(testData.getProperty("aska.v2.name"));

		Thread.sleep(3000);
		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());
		Thread.sleep(3000);
		logStep("Fill question and continue");

		askPage2 = askPage1.fillAndContinue(askaSubject, questionText);

		logStep("Remove all cards because Selenium can't see AddNewCard button");
		JalapenoAskAStaffV2Page1 askPaidPage = new JalapenoAskAStaffV2Page1(driver);

		assertTrue(askPaidAmount.equals(askPaidPage.getAskaPaymentText()),
				"Expected: " + askPaidAmount + ", found: " + askPaidPage.getAskaPaymentText());
		askPaidPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(askPaidPage.isAnyCardPresent());

		askPaidPage.fillPaymentInfo(accountNumber, creditCard);
		Thread.sleep(8000);

		askPage2 = new JalapenoAskAStaffV2Page2(driver);
		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPage1 = homePage.openSpecificAskaPaidV2(testData.getProperty("aska.v2.name"));
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
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("aska.v2.doctor.login"),
				testData.getProperty("aska.v2.doctor.password"));

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
		homePage = loginPage.login(testData.getProperty("aska.v2.user"), testData.getProperty("aska.v2.password"));

		logStep("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver,
				"Automated Test " + (Long.toString(detailStep2.getCreatedTimeStamp()))));

		JalapenoLoginPage loginPageNew = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePageNew = loginPageNew.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		askPage1 = homePageNew.openSpecificAskaPaidV2(testData.getProperty("aska.v2.name"));
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
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaV2(testData.getProperty("aska.v2.name"));

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
		askPage1 = homePage.openSpecificAskaV2(testData.getProperty("aska.v2.name"));
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
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("aska.v2.doctor.login"),
				testData.getProperty("aska.v2.doctor.password"));

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
		homePage = loginPage.login(testData.getProperty("aska.v2.user"), testData.getProperty("aska.v2.password"));

		logStep("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver,
				"Automated Test " + (Long.toString(detailStep2.getCreatedTimeStamp()))));

		logStep("Go back to the aska again and check submission status changed");
		homePage = messagesPage.clickOnMenuHome();
		askPage1 = homePage.openSpecificAskaV2(testData.getProperty("aska.v2.name"));
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
		String patientLogin = PortalUtil2.generateUniqueUsername("statelogin", testData);
		String patientLastName = patientLogin.replace("login", "statelast");
		String patientEmail = patientLogin.replace("statelogin", "mail") + "@yopmail.com";

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

		logStep("Using yopmail Mailer to retrieve the latest emails for patient and guardian");

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
	public void testPatientStateSpecificCreation() throws Exception {
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		new CreatePatient().selfRegisterPatientStateSpecific(driver, patient, testData.getUrl());
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientActivationEnrollment() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		List<ExpectedEmail> mails = new ArrayList<ExpectedEmail>();

		logStep("Generating Activation Link of patient in First Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String firstunlockLinkPortal = patientActivationSearchTest.getPatientActivationPracticeLink(driver, testData,
				patientsEmail);
		logStep("Patient first practice unlockLinkPortal" + firstunlockLinkPortal);

		logStep("Logging into yopmail and getting Patient Activation url for first Practice");
		YopMail mail = new YopMail(driver);
		String firstunlockLinkEmail = mail.getLinkFromEmail(patientsEmail,
				INVITE_EMAIL_SUBJECT_PATIENT + testData.getProperty("practice.name1").replace(" ", ""),
				INVITE_EMAIL_BUTTON_TEXT, 15);

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
				patientsEmail);

		logStep("Logging into YOPmail and getting Patient Activation url for Seond Practice");

		String secondunlockLinkEmail = mail.getLinkFromEmail(patientsEmail,
				INVITE_EMAIL_SUBJECT_PATIENT + testData.getProperty("practice.name2").replace(" ", ""),
				INVITE_EMAIL_BUTTON_TEXT, 15);

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
				PracticeConstants.ZIP_CODE, JalapenoConstants.DATE_OF_BIRTH_MONTH_NO,
				JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				patientActivationSearchTest1.getPatientIdString(), testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");

		logStep("Checking if address in My Account is filled");
		JalapenoAccountPage accountPage = jalapenoHomePage.clickOnAccount();
		JalapenoMyAccountProfilePage jalapenoMyAccountPage = accountPage.clickOnEditMyAccount();
		assertTrue(
				jalapenoMyAccountPage.checkForAddress(driver, "5501 Dillard Dr", "Cary", PracticeConstants.ZIP_CODE));

		mails.add(new ExpectedEmail(patientsEmail, WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BODY_PATTERN_PRACTICE));
		mails.add(new ExpectedEmail(patientsEmail, WELCOME_EMAIL_SUBJECT_PATIENT,
				WELCOME_EMAIL_BODY_PATTERN_SECOND_PRACTICE));
		assertTrue(new Mailinator().areAllMessagesInInbox(mails, 60));

		logStep("Load login page for the auto enrolled practice");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, testData.getProperty("practice.url1"));
		loginPage.login(patientActivationSearchTest1.getPatientIdString(), testData.getPassword());

	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAutoEnrollmentSamePatientid() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		String firstPatientEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		String patientId = "SamePatientID" + IHGUtil.createRandomNumericString();

		logStep("Patient Activation at First Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationLinkWithPatientId(1, driver,
				testData.getProperty("doctor.login1"), testData.getProperty("doctor.password1"),
				testData.getProperty("portal.url"), firstPatientEmail, patientId);
		logStep("Activation Link of First Practice is " + unlockLinkPortal);

		logStep("Logging into yopmail and getting Patient Activation url for first Practice");
		YopMail mail = new YopMail(driver);
		String unlockLinkEmail = mail.getLinkFromEmail(firstPatientEmail,
				INVITE_EMAIL_SUBJECT_PATIENT + testData.getProperty("practice.name1").replace(" ", ""),
				INVITE_EMAIL_BUTTON_TEXT, 10);

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
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getProperty("dob.year"));

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				patientActivationSearchTest.getPatientIdString(), testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");

		logStep("Patient Activation on Second Practice Portal- Patient Activation link will not be present");
		PatientActivationSearchTest patientActivationSearchTest12 = new PatientActivationSearchTest();
		patientActivationSearchTest12.getPatientActivationLinkWithPatientId(0, driver,
				testData.getProperty("doctor.login.practice2"), testData.getProperty("doctor.password.practice2"),
				testData.getPortalUrl(), firstPatientEmail, patientId);

		log("Waiting for welcome mail at patient inbox from second practice");

		// Instant testStart = Instant.now();
		String portalUrlLink = mail.getLinkFromEmail(firstPatientEmail,
				WELCOME_EMAIL_SUBJECT_PATIENT + testData.getProperty("practice.name1").replace(" ", ""),
				WELCOME_EMAIL_BUTTON_TEXT, 10);

		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		if (!isWelcomeLinkFinal(portalUrlLink)) {
			portalUrlLink = getRedirectUrl(portalUrlLink);
		}

		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		loginPage.login(patientActivationSearchTest.getPatientIdString(), testData.getPassword());

		logStep("Detecting if Home Page is opened");

		logStep("Switching to Second Practice to verify auto enrollment");
		jalapenoHomePage.switchPractice(testData.getProperty("practice.name1"));

		logStep("Auto Enrollment to Second Practice is completed");
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMinorAutoEnrollment() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		String guardianPatientLogin = PortalUtil2.generateUniqueUsername("login", testData);
		String guardianPatientLastName = guardianPatientLogin.replace("login", "last");
		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		String dependentPatientFirstName = "Dependent" + IHGUtil.createRandomNumericString();

		logStep("Patient Activation at First Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationPortalLink(1, driver, patientsEmail,
				testData.getProperty("doctor.login1"), testData.getProperty("doctor.password1"),
				testData.getProperty("portal.url"), dependentPatientFirstName);
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

		logStep("Guardian not a patient so family account element not present");
		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Patient Activation on Second Practice Portal- Patient Activation link will not be present");
		PatientActivationSearchTest patientActivationSearchTest12 = new PatientActivationSearchTest();
		patientActivationSearchTest12.getPatientActivationPortalLink(0, driver, patientsEmail,
				testData.getProperty("doctor.login.practice2"), testData.getProperty("doctor.password.practice2"),
				testData.getPortalUrl(), dependentPatientFirstName);

		log("Waiting for welcome mail at patient inbox from second practice");
		Instant testStart = Instant.now();
		Email visitPortal = new Mailer(patientsEmail).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 60,
				testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);

		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);

		System.out.println("patient portal url is " + portalUrlLink);

		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		if (!isWelcomeLinkFinal(portalUrlLink)) {
			portalUrlLink = getRedirectUrl(portalUrlLink);
		}

		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage jalapenoHomePage = loginPage.login(guardianPatientLogin, testData.getPassword());

		logStep("Detecting if Home Page is opened");

		logStep("Switching to Second Practice to verify auto enrollment");
		jalapenoHomePage.switchPractice(testData.getProperty("practice.name1"));

		logStep("Auto Enrollment to Second Practice is completed");
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGuardianAutoEnrollment() throws Exception {
		String guardianpatientEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		String guardianFirstName = "BetaGuardian" + IHGUtil.createRandomNumericString();
		String guardianLogin = PortalUtil2.generateUniqueUsername("login", testData);

		logStep("Guardian Patient Activation at Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationPortalLink(1, driver,
				guardianpatientEmail, testData.getProperty("doctor.login1"), testData.getProperty("doctor.password1"),
				testData.getPortalUrl(), guardianFirstName);

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(guardianLogin,
				testData.getPassword(), testData);

		logStep("Detecting if Home Page is opened");

		logStep("Registering Dependent patient at Practice1");
		String patientFirstName = "BetaDependent" + IHGUtil.createRandomNumericString();

		String guardianLinkPortal01 = patientActivationSearchTest.getPatientActivationPortalLink(1, driver,
				guardianpatientEmail, testData.getProperty("doctor.login1"), testData.getProperty("doctor.password1"),
				testData.getPortalUrl(), patientFirstName);

		logStep("Logging into yopmail and getting Patient Activation url");
		YopMail mail = new YopMail(driver);
		String unlockLinkEmail01 = mail.getLinkFromEmail(guardianpatientEmail, GUARDIAN_INVITE_SUBJECT,
				INVITE_EMAIL_BUTTON_TEXT, 10);
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
				testData.getProperty("doctor.login.practice2"), testData.getProperty("doctor.password.practice2"),
				testData.getPortalUrl(), patientFirstName);

		logStep("Validate Welcome mail recieved by guardianpatient at Practice Portal2");

		log("Waiting for welcome mail at patient inbox from second practice");
		String portalUrlLink = mail.getLinkFromEmail(guardianpatientEmail, WELCOME_EMAIL_SUBJECT_PATIENT,
				WELCOME_EMAIL_BUTTON_TEXT, 10);

		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		if (!isWelcomeLinkFinal(portalUrlLink)) {
			portalUrlLink = getRedirectUrl(portalUrlLink);
		}

		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		loginPage.login(guardianLogin, testData.getPassword());

		logStep("Detecting if Home Page is opened");

		jalapenoHomePage.faChangePatient();
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

		logStep("Switching to Second Practice to verify auto enrollment");
		jalapenoHomePage.switchPractice(testData.getProperty("practice.name1"));

		logStep("Auto Enrollment of Guardian and Dependent to Second Practice is completed");
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
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
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

		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal to Approved the request with adding Attachment");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequesAttachmentt(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword(), MessageErrorfilePath,
				MessagefilePath);

		logStep("Login back to patient portal to check the Approved status");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
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

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequest(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
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

	/**
	 * Updating appointment request while processing them with the following
	 * appointment statuses -Process Externaly.
	 *
	 */

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentProcessExternallyStatus() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
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

		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
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
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
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
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		driver.navigate().refresh();

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
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
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
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for appointment Set to Pending from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Set to Pending " + tsPracticePortal));
		homePage.clickOnLogout();

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientEducationKnownIssue() throws InterruptedException {
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage
				.login(testData.getProperty("patient.education.with.issue.username"), testData.getPassword());

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
		String messageSubject = "Pay My Bill";

		createCommonPatientWithStatement();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		logStep("Remove all cards because Selenium can't see AddNewCard button");
		payBillsPage.removeAllCards();

		logStep("Check that no card is present");
		assertFalse(payBillsPage.isAnyCardPresent());
		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, accountNumber,
				creditCard);

		logStep("Verifying credit card ending");
		assertTrue(confirmationPage.getCreditCardEnding().equals(creditCard.getLastFourDigits()));
		homePage = confirmationPage.commentAndSubmitPayment("Testing payment from number: " + accountNumber);
		assertTrue(homePage.wasPayBillsSuccessfull());

		logStep("Click on Pay Bills");
		payBillsPage = homePage.clickOnNewPayBills(driver);

		logStep("Click on Payment History and select the recent transaction");
		payBillsPage.clickPaymentHistory();

		logStep("Verifying credit card ending in payment receipt");
		assertTrue(payBillsPage.getReceiptCreditCardDigit().equals(creditCard.getLastFourDigits()));
		homePage.clickOnLogout();

		logStep("Load login page");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Click on messages solution and navigate to Inbox");
		JalapenoMessagesPage messagesPage = homePage.showMessagesSent(driver);

		logStep("Waiting for message in SecureMessage Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageSubject));

		logStep("Verifying the Payment Notification Mail is received by the patient or not");
		YopMail mail = new YopMail(driver);
		String notificationEmailSubject = "Payment Receipt";
		String mailAddress = patient.getEmail();
//		assertTrue(mail.getEmailContentText(mailAddress, notificationEmailSubject, "************", 10));


	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBuildMessaging() throws Exception {
		String messageSubject = "Namaste " + System.currentTimeMillis();

		logStep("Login physician");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Send a new secure message to static patient");
		PatientMessagingPage patientMessagingPage = practiceHome.clickPatienBuildtMessagingTab();
		ArrayList<String> practicePortalMessage = patientMessagingPage.setFieldsAndPublishMessageforBuild(testData,
				"TestingMessage", messageSubject);

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Waiting for message from practice portal");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageSubject));

		logStep("Veriying the length of the subject at Patient Portal");
		int subjectLength = messagesPage.checkSubjectLength();
		assertEquals(messageSubject.length(), subjectLength);

		logStep("Verifying the content of the Message at Patient Portal");
		assertEquals(practicePortalMessage.get(1), messagesPage.getMessageBody());

		logStep("Verifying that URL is present in Patient inbox as sent from Practice Portal");
		assertEquals(practicePortalMessage.get(0), messagesPage.getMessageURL());

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUnlinkDependent() throws Exception {
		Instant testStart = Instant.now();
		String patientLogin = PortalUtil2.generateUniqueUsername("login", testData); // guardian login
		String patientLastName = patientLogin.replace("login", "last");
		String patientEmail = patientLogin.replace("login", "mail") + "@yopmail.com";

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

		logStep("Going to MyAccount page and unlink dependent");
		jalapenoHomePage.unlinkDependentAccount();
		assertTrue(jalapenoHomePage.wasUnlinkSuccessful());

		logStep("Using YOP Mailer to retrieve the latest emails for dependent");
		String emailSubjectDependent = "Unlink notification of your account at " + testData.getPracticeName();
		YopMail mail = new YopMail(driver);
		String emailDependent = mail.getLinkFromEmail(patientEmail, emailSubjectDependent, WEBSITE_LINK, 15);

		assertNotNull(emailDependent, "Error: No email found for dependent recent enough and with specified subject: "
				+ emailSubjectDependent);
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMedications() throws Exception {

		String name = "Medication Patient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("med.username"),
				testData.getProperty("med.password"));
		driver.navigate().refresh();

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProvider();

		logStep("Enter Credit Card Details");
		PrescriptionFeePage feePage = new PrescriptionFeePage(driver);
		feePage.fillRenewalFee(driver, creditCard);

		logStep("Select a pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.selectPharmacy();

		logStep("Select Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectMedications();

		logStep("Confirm Medication Request from Patient Portal");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);

		String successMsg = confirmPage.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		homePage.clickOnLogout();

		logStep("Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("med.doc.username"),
				testData.getProperty("med.doc.password"));

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
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject, DRUG_DOSAGE);

		logStep("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		homePage = loginPage.login(testData.getProperty("med.username"), testData.getProperty("med.password"));

		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for Medication approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMedicationsAddNewPharmacy() throws Exception {

		String name = "Medication Patient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("med.username"),
				testData.getProperty("med.password"));

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProvider();

		logStep("Enter Credit Card Details");
		PrescriptionFeePage feePage = new PrescriptionFeePage(driver);
		feePage.fillRenewalFee(driver, creditCard);

		logStep("Select a pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.addNewPharmacy(driver);

		logStep("Select Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectMedications();

		logStep("Confirm Medication Request from Patient Portal");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);
		String successMsg = confirmPage.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		homePage.clickOnLogout();

		logStep("Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("med.doc.username"),
				testData.getProperty("med.doc.password"));

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
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject, DRUG_DOSAGE);

		logStep("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		homePage = loginPage.login(testData.getProperty("med.username"), testData.getProperty("med.password"));

		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for Medication approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));

	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientActivationInvalidZipCode() throws Exception {
		String guardianpatientEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		String guardianFirstName = "Guardian" + IHGUtil.createRandomNumericString();

		logStep("Patient Activation at Practice Portal1");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		logStep("Finishing of patient activation: step 1 - Filling the patient details");
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationPortalLink(1, driver,
				guardianpatientEmail, testData.getProperty("doctor.login1"), testData.getProperty("doctor.password1"),
				testData.getPortalUrl(), guardianFirstName);

		logStep("Finishing of patient activation: step 2 - verifying identity with invalid zipcode and valid date of birth");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		patientVerificationPage.fillPatientInfoAndContinue(PracticeConstants.INVALID_ZIP_CODE,
				JalapenoConstants.DATE_OF_BIRTH_MONTH_NO, JalapenoConstants.DATE_OF_BIRTH_DAY,
				JalapenoConstants.DATE_OF_BIRTH_YEAR);

		logStep("Looking for the Error Message: step 3 - verifying the error message");
		assertTrue(patientVerificationPage.isZipCodeDobErrorDisplayed());

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMedicationsWithoutRenewalFee() throws Exception {

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("med.wf.user.id"),
				testData.getProperty("med.wf.password"));

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProviderwithoutFee();

		logStep("Select a pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.selectPharmacy();

		logStep("Select Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectMedications();

		logStep("Validating Prescription Renewal Fee Text is not present");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);
		confirmPage.prescriptionRenewalFee();

		logStep("Confirm Medication Request from Patient Portal");
		MedicationsConfirmationPage confirmPage1 = new MedicationsConfirmationPage(driver);
		String successMsg = confirmPage1.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("med.wf.doc.user.id"),
				testData.getProperty("med.wf.doc.password"));

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
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject, DRUG_DOSAGE);

		logStep("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Login to Patient Portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		homePage = loginPage.login(testData.getProperty("med.wf.user.id"), testData.getProperty("med.wf.password"));

		logStep("Navigate to Message Inbox");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for Medication approval from doctor in Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));

	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAuthUserLinkAccountForgotPassword() throws Exception {
		Instant passwordResetStart = Instant.now();
		String patientLogin = PortalUtil2.generateUniqueUsername("login", testData); // guardian login
		String patientLastName = patientLogin.replace("login", "last");
		String patientEmail = patientLogin.replace("login", "mail") + "@yopmail.com";

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
		accountDetailsPage.fillAccountDetailsAndContinue(patientLogin, testData.getPassword(), testData);

		log("Login username of Guardian is " + patientLogin);

		logStep("Detecting if Home Page is opened");

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(guardianUrl);
		patientVerificationPage.fillDependentInfoAndContinue(testData.getZipCode(), testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Clicking on forgot username or password");
		patientVerificationPage.securityDetailsPageclickForgotPasswordButton();
		JalapenoForgotPasswordPage forgotPasswordPage = new JalapenoForgotPasswordPage(driver);
		JalapenoForgotPasswordPage2 forgotPasswordPage2 = forgotPasswordPage.fillInDataPage(patientEmail);
		logStep("Message was sent, closing");
		forgotPasswordPage2.clickOnCloseButton();

		logStep("Logging into yopmail and getting ResetPassword url");
		String[] mailAddress = patientEmail.split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";
		YopMail mail = new YopMail(driver);
		String url = mail.getLinkFromEmail(mailAddress[0], emailSubject, inEmail, 15);

		if (!isInviteLinkFinal(url)) {
			url = getRedirectUrl(url);
		}
		assertNotNull(url, "Url is null.");

		JalapenoForgotPasswordPage3 forgotPasswordPage3 = new JalapenoForgotPasswordPage3(driver, url);
		logStep("Redirecting to patient portal, filling secret answer as: " + testData.getSecretAnswer());
		JalapenoForgotPasswordPage4 forgotPasswordPage4 = forgotPasswordPage3
				.fillInSecretAnswer(testData.getSecretAnswer());

		logStep("Filling new password");
		JalapenoHomePage homePage = forgotPasswordPage4.fillInNewPassword(testData.getPassword());

		logStep("Logging out");
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts",
			"commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUnlinkTrustedRepresentative() throws Exception {
		createCommonPatient();
		Patient trustedPatient = PatientFactory.createJalapenoPatient(
				PortalUtil2.generateUniqueUsername(testData.getProperty("tr.user.id"), testData), testData);
		String email = testData.getTrustedRepEmail() + IHGUtil.createRandomNumber() + "@yopmail.com";

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(testData.getTrustedRepFirstName(), testData.getTrustedRepLastName(),
				email);

		logStep("Waiting for invitation email");
		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(email, INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT,
				15);
		assertNotNull(patientUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());

		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(patient.getFirstName(), patient.getLastName(), email);
		SecurityDetailsPage accountDetailsPage = linkAccountPage
				.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");

		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getUsername(), trustedPatient.getPassword(),
				testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out from patient portal");
		loginPage = homePage.clickOnLogout();

		logStep("Log in and log out as Trusted Representative");
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		homePage.clickOnLogout();

		homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		accountPage = homePage.clickOnAccount();
		accountPage.unlinkTrustedRepresentativeAccount();
		assertTrue(homePage.wasUnlinkSuccessful());
		loginPage = homePage.clickOnLogout();

		logStep("Using YOPMail Mailer to retrieve the latest emails for Trusted Representative");
		String emailSubjectTrustedRepresentative = "Unlink notification of your account at "
				+ testData.getPracticeName();
		String emailTrustedRepresentative = mail.getLinkFromEmail(email, emailSubjectTrustedRepresentative,
				WEBSITE_LINK, 15);
		assertNotNull(emailTrustedRepresentative,
				"Error: No email found for Trusted Representative recent enough and with specified subject: "
						+ emailSubjectTrustedRepresentative);

		logStep("Log in as Trusted Representative");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		loginPage.loginUnsuccessfuly(trustedPatient.getUsername(), trustedPatient.getPassword());

		logStep("Looking for the Error Message and verifying the error message");
		assertTrue(loginPage.isTrustedRepresentativeAccountErrorDisplayed());
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMedicationsProviderPharmacyList() throws Exception {
		logStep("Login to sitegen as Admin user");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("sitegen.admin.user"),
				testData.getProperty("sitegen.password.user"));
		logStep("Navigate to SiteGen PracticeHomePage");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		logStep("Check if SiteGen Practice Homepage elements are present ");
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");
		pSiteGenPracticeHomePage.clickOnPharmacy();

		ManageYourPharmacies managepharmacy = new ManageYourPharmacies(driver);
		managepharmacy.clickOnAddPharmacyButton();

		AddPharmacyPage addPharmacyPage = new AddPharmacyPage(driver);
		String externalid = IHGUtil.createRandomNumericString(12);
		addPharmacyPage.fillPharmacyDetails(externalid, true);

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("med.wf.user.id"),
				testData.getProperty("med.wf.password"));

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProviderwithoutFee();

		logStep("Select a pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.addProviderSuggestedPharmacy(driver, pharmacyName);

		logStep("Select Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectMedications();

		logStep("Validating Prescription Renewal Fee Text is not present");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);
		confirmPage.prescriptionRenewalFee();

		logStep("Confirm Medication Request from Patient Portal");
		String successMsg = confirmPage.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletePatient() throws Exception {

		createCommonPatient();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Logging out");
		loginPage = homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Delete registered patient");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		patientSearchPage.searchForPatientInPatientSearch(patient.getEmail());
		patientSearchPage.clickOnPatient(patient.getFirstName(), patient.getLastName());
		patientSearchPage.deletePatient();

		logStep("Load login page");
		JalapenoLoginPage loginWithDeletedPatient = new JalapenoLoginPage(driver, testData.getUrl());
		logStep("login with deleted patient details");
		loginWithDeletedPatient.loginUnsuccessfuly(patient.getUsername(), patient.getPassword());

		logStep("Looking for the Error Message and verifying the error message");
		assertTrue(loginWithDeletedPatient.isInactivePatientErrorDisplayed());
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testThirdPartySso() throws Exception {
		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("user.id"), testData.getProperty("password"));

		logStep("Click on the Third Party SSO tab");
		ThirdPartySsoPage thirdpartyssopage = homePage.clickOnThirdPartySso(driver);

		logStep("Verify the Third Party SSO Pop up Screen");
		assertTrue(thirdpartyssopage.isLeavingMedfusionBannerDisplay());

		logStep("Verify the Destination URL on SSO Pop up Screen");
		assertTrue(thirdpartyssopage.isDestinationUrlDisplay());

		logStep("Verify the exist portal message on SSO Pop up Screen");
		assertTrue(thirdpartyssopage.isExistPortalMessageDisplay());

		logStep("Click on the continue button");
		thirdpartyssopage.clickOnContinueButton();

		logStep("Verify the New Tab Open");
		assertTrue(thirdpartyssopage.isNewTabOpenDestinationUrl(driver));
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAChangeAmount() throws Exception {

		logStep("Initiate payment data");
		String askPaidAmount = "$ 2";
		String askPaidChangeAmount = "$ 5";
		String accountNumber = IHGUtil.createRandomNumericString(8);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);
		JalapenoAskAStaffV2Page2 askPage2 = new JalapenoAskAStaffV2Page2(driver);

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaPaidV2(testData.getProperty("aska.v2.name"));
		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		askPage2 = askPage1.fillAndContinue(askaSubject, questionText);

		logStep("Remove all cards because Selenium can't see AddNewCard button");
		JalapenoAskAStaffV2Page1 askPaidPage = new JalapenoAskAStaffV2Page1(driver);
		assertTrue(askPaidAmount.equals(askPaidPage.getAskaPaymentText()),
				"Expected: " + askPaidAmount + ", found: " + askPaidPage.getAskaPaymentText());
		askPaidPage.removeAllCards();

		logStep("Check that no card is present");
		assertFalse(askPaidPage.isAnyCardPresent());
		askPaidPage.fillPaymentInfo(accountNumber, creditCard);
		askPage2 = new JalapenoAskAStaffV2Page2(driver);
		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPage1 = homePage.openSpecificAskaPaidV2(testData.getProperty("aska.v2.name"));
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
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("aska.v2.doctor.login"),
				testData.getProperty("aska.v2.doctor.password"));

		logStep("Click Ask A Staff tab");
		AskAStaffSearchPage searchQ = practiceHome.clickAskAStaffTab();

		logStep("Search for questions");
		searchQ.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = searchQ.getQuestionDetails(askaSubject);
		assertNotNull(detailStep1, "The submitted patient question was not found in the practice");
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep1Page.PAGE_NAME);

		logStep("Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1.chooseProvideAdviceOnly();

		logStep("Respond to patient question and change the practice amount ");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2.chargeAmountAndCommunicate("Automated Test",
				"This message was generated by an automated test", askPaidChangeAmount.replace("$ ", ""));

		logStep("Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3.confirmProcessedQuestion();

		logStep("Validate submit of confirmation");
		assertTrue(detailStep4.isQuestionDetailPageLoaded());

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Login patient");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		homePage = loginPage.login(testData.getProperty("aska.v2.user"), testData.getProperty("aska.v2.password"));

		logStep("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver,
				"Automated Test " + (Long.toString(detailStep2.getCreatedTimeStamp()))));

		JalapenoLoginPage loginPageNew = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePageNew = loginPageNew.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		askPage1 = homePageNew.openSpecificAskaPaidV2(testData.getProperty("aska.v2.name"));
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
				askPaidChangeAmount.replace("$ ", "")
						.equals(askHistoryDetail.getRequestDetailPayment()
								.replace("This online encounter was charged $", "").replace(".", "")),
				"Expected: " + askPaidChangeAmount.replace("$ ", "") + ", found: " + askHistoryDetail
						.getRequestDetailPayment().replace("This online encounter was charged $", "").replace(".", ""));

		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientAutoEnrollment() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		String patientFirstName = "Betapatient" + IHGUtil.createRandomNumericString();

		logStep("Patient Activation at First Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationPortalLink(1, driver, patientsEmail,
				testData.getProperty("doctor.login1"), testData.getProperty("doctor.password1"),
				testData.getProperty("portal.url"), patientFirstName);
		logStep("Activation Link of First Practice is " + unlockLinkPortal);

		logStep("Logging into yopmail and getting Patient Activation url for first Practice");
		YopMail mail = new YopMail(driver);
		String unlockLinkEmail = mail.getLinkFromEmail(patientsEmail, INVITE_EMAIL_SUBJECT_PATIENT,
				INVITE_EMAIL_BUTTON_TEXT, 15);

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
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getProperty("dob.year"));

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				patientActivationSearchTest.getPatientIdString(), testData.getPassword(), testData);

		logStep("Patient Activation on Second Practice Portal- Patient Activation link will not be present");
		PatientActivationSearchTest patientActivationSearchTest12 = new PatientActivationSearchTest();
		patientActivationSearchTest12.getPatientActivationPortalLink(0, driver, patientsEmail,
				testData.getProperty("doctor.login.practice2"), testData.getProperty("doctor.password.practice2"),
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
		loginPage.login(patientActivationSearchTest.getPatientIdString(), testData.getPassword());

		logStep("Switching to Second Practice to verify auto enrollment");
		jalapenoHomePage.switchPractice(testData.getProperty("practice.name1"));

		logStep("Auto Enrollment to Second Practice is completed");
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCcdDemographicValidation() throws Exception {

		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getProperty("ccd.patient.username"),
				testData.getPassword());

		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		JalapenoCcdViewerPage jalapenoCcdPage = jalapenoMessagesPage.findCcdMessage(driver);

		assertTrue(testData.getProperty("pat.header.name").equals(jalapenoCcdPage.getHeaderName()),
				"Expected:" + testData.getProperty("pat.header.name") + ", found: " + jalapenoCcdPage.getHeaderName());

		assertTrue(testData.getProperty("pat.name").equals(jalapenoCcdPage.getPatientName()),
				"Expected:" + testData.getProperty("pat.name") + ", found: " + jalapenoCcdPage.getPatientName());

		assertTrue(testData.getProperty("pat.email").equals(jalapenoCcdPage.getPatientEmail()),
				"Expected:" + testData.getProperty("pat.email") + ", found: " + jalapenoCcdPage.getPatientEmail());

		assertTrue(testData.getProperty("pat.dob").equals(jalapenoCcdPage.getPatientDOB()),
				"Expected:" + testData.getProperty("pat.dob") + ", found: " + jalapenoCcdPage.getPatientDOB());

		assertTrue(testData.getProperty("pat.ethnicity").equals(jalapenoCcdPage.getPatientEthnicity()), "Expected:"
				+ testData.getProperty("pat.ethnicity") + ", found: " + jalapenoCcdPage.getPatientEthnicity());

		assertTrue(testData.getProperty("pat.marital.status").equals(jalapenoCcdPage.getPatientMaritialStatus()),
				"Expected:" + testData.getProperty("pat.marital.status") + ", found: "
						+ jalapenoCcdPage.getPatientMaritialStatus());

		assertTrue(testData.getProperty("pat.care.team.member").equals(jalapenoCcdPage.getPatientCareTeamMember()),
				"Expected:" + testData.getProperty("pat.care.team.member") + ", found: "
						+ jalapenoCcdPage.getPatientCareTeamMember());

		assertTrue(testData.getProperty("pat.phone").equals(jalapenoCcdPage.getPatientPhoneNumber()), "Expected:"
				+ testData.getProperty("pat.phone") + ", found: " + jalapenoCcdPage.getPatientPhoneNumber());

		jalapenoMessagesPage = jalapenoCcdPage.closeCcd(driver);

		logStep("Logging out");
		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSelfTrustedRepresentative() throws Exception {
		patient = null;
		createPatient();
		String email = testData.getTrustedRepEmail() + IHGUtil.createRandomNumber() + "@yopmail.com";

		logStep("Go to account page");
		JalapenoHomePage homePage = new JalapenoHomePage(driver);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(testData.getTrustedRepFirstName(), testData.getTrustedRepLastName(),
				email);

		logStep("Waiting for invitation email");
		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(email, INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT,
				15);
		assertNotNull(patientUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());

		logStep("Continue registration - linking same patient as trusted representative");
		linkAccountPage.linkSamePatientAsSelfTrustedRep(patient.getUsername(), patient.getPassword(), "Spouse");

		assertTrue(linkAccountPage.isSelfTrustedRepresentativeErrorDisplayed());
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppoinmentScheduling() throws Exception {
		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("med.username"),
				testData.getProperty("med.password"));

		logStep("Click on the Appoinment Scheduling tab");
		JalapenoAppoinmentSchedulingPage appoinmentschedulingpage = homePage.clickOnAppoinmentScheduled(driver);

		logStep("Verify the Scheduled an appoinment Pop up Screen");
		assertTrue(appoinmentschedulingpage.isScheduledAnAppoinmentPopUpDisplay());

		logStep("Verify the message on a Pop up Screen");
		assertTrue(appoinmentschedulingpage.isPopUpMessageDisplay());

		logStep("Verify the exist portal button on pop up Screen");
		assertTrue(appoinmentschedulingpage.isClosePopUpMessageDisplay());

		logStep("Click on the continue button");
		appoinmentschedulingpage.clickOnContinueButton();

		logStep("Verify the New Tab Open");
		assertTrue(appoinmentschedulingpage.isNewTabOpenDestinationUrl(driver));
	}

	/*
	 * SCENARIO1- where a patient having statement Paper+Electronic will get update
	 * to Electronic after updating the job with estatement configuration
	 */

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSatementPreferenceUpdatingToElectronic() throws Exception {
		SiteGenLoginPage loginpage;
		SiteGenHomePage pSiteGenHomePage;
		SiteGenPracticeHomePage pSiteGenPracticeHomePage;
		EstatementPage estatement;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		JalapenoAccountPage accountPage;
		JalapenoMyAccountPreferencesPage myAccountSecurityPage;
		JalapenoMyAccountProfilePage myAccountPage;

		logStep("Login to sitegen as Admin user");
		loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		pSiteGenHomePage = loginpage.login(testData.getProperty("jalapeno.sitgen.admin"),
				testData.getProperty("jalapeno.sitgen.password"));

		logStep("Navigate to SiteGen PracticeHomePage");
		pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		logStep("Check if SiteGen Practice Homepage elements are present ");
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		logStep("Click on online bill pay and Navigate to Estatement");
		pSiteGenPracticeHomePage.clickOnOnlineBillPay();

		logStep("Doing the configuration setting for estatement and setting up the default delivery option");
		estatement = new EstatementPage(driver);
		estatement.enableStatementDelivery("check");
		estatement.bothPaperAndElectronic("check");
		estatement.disablePaperOnly("uncheck");
		Thread.sleep(5000);// Waiting for the update of default delivery options
		estatement.defaultDeliveryOption("Paper + Electronic");
		estatement.submitButton();

		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().selfRegisterPatientWithPreference(driver, patient, testData.getUrl(), 3);

		logStep("Load login page");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Go to Account tab on my account page");
		accountPage = homePage.clickOnAccount();
		myAccountPage = accountPage.clickOnEditMyAccount();

		logStep("Navigate to Preference page and validate the preferencer has been updated to Paper + Electronic");
		myAccountSecurityPage = myAccountPage.goToPreferencesTab(driver);
		assertEquals("Paper + Electronic", myAccountSecurityPage.getSelectedStatementPreference());
		myAccountPage.clickOnLogout();

		logStep("Again login back to Sitegen for estamenet Setting ");
		loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		pSiteGenHomePage = loginpage.login(testData.getProperty("jalapeno.sitgen.admin"),
				testData.getProperty("jalapeno.sitgen.password"));

		logStep("Navigate to SiteGen PracticeHomePage");
		pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		logStep("Check if SiteGen Practice Homepage elements are present ");
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		logStep("Clicking on online Bill Pay and Navigate to Estatement");
		pSiteGenPracticeHomePage.clickOnOnlineBillPay();

		logStep("Setting up the estatement configuration and setting the default delivery option to estatement");
		estatement = new EstatementPage(driver);
		estatement.enableStatementDelivery("check");
		estatement.bothPaperAndElectronic("uncheck");
		estatement.disablePaperOnly("check");
		Thread.sleep(5000);// Waiting for the update of default delivery options
		estatement.defaultDeliveryOption("eStatement");
		estatement.submitButton();

		logStep("Load login page");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Go to security tab on my account page");
		accountPage = homePage.clickOnAccount();
		myAccountPage = accountPage.clickOnEditMyAccount();

		logStep("Navigate to Preference page and validate the preferencer has been updated to Electronically");
		myAccountSecurityPage = myAccountPage.goToPreferencesTab(driver);
		assertEquals("Electronically", myAccountSecurityPage.getStatementPreferenceafterUpdate());

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLACreateDependentWithInvalidGuardianCredentials() throws Exception {
		String patientLogin = PortalUtil2.generateUniqueUsername("login", testData);
		String patientLastName = patientLogin.replace("login", "last");
		String patientEmail = patientLogin.replace("login", "mail") + "@yopmail.com";

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

		logStep("Continue registration - check dependent info and fill login credentials");
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, patientEmail);
		linkAccountPage.linkPatientToCreateGuardian(testData.getProperty("guardian.username"),
				testData.getProperty("guardian.invalid.password"), "Parent");

		assertTrue(linkAccountPage.isIncorrectUsernamePasswordErrorDisplayed());

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPayBillsDuplicateAmount() throws Exception {
		logStep("Initiate payment data");
		String accountNumber = IHGUtil.createRandomNumericString(8);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);

		createCommonPatient();
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		logStep("Remove all cards because Selenium can't see AddNewCard button");
		payBillsPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(payBillsPage.isAnyCardPresent());
		logStep("Fill all the details on a payment page for first payment");
		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage
				.fillPaymentInfoForDuplicate(PracticeConstants.amount, accountNumber, creditCard);
		logStep("Click on the submit button and verify the payment status");
		homePage = confirmationPage.commentAndSubmitPayment("Testing payment from number: " + accountNumber);
		assertTrue(homePage.wasPayBillsSuccessfull());
		logStep("Click on the Pay bill for duplicate payment");
		JalapenoPayBillsMakePaymentPage payBillswithDuplicateAmount = homePage
				.clickOnNewPayBillsForDuplicatePayment(driver);
		logStep("Fill all the details on a payment page for Duplicate payment");
		JalapenoPayBillsConfirmationPage confirmationPageForduplicatePayment = payBillswithDuplicateAmount
				.fillPaymentInfoWithExistingCards(PracticeConstants.amount, accountNumber, PracticeConstants.CVV);
		logStep("Click on the submit button and verify the error message");
		confirmationPageForduplicatePayment.commentAndSubmitPayment("System should display the error message");
		assertTrue(confirmationPageForduplicatePayment.isDuplicatePaymentErrorMessageDisplayed());

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAStaffWithProviderAndLocation() throws Exception {
		String questionText = "wat";

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaV2(testData.getProperty("aska.v2.name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());
		Thread.sleep(5000);
		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.fillAndContinueWithProviderAndLocation(askaSubject, questionText);

		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPage1 = homePage.openSpecificAskaV2(testData.getProperty("aska.v2.name"));
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
	}

	/*
	 * SCENARIO3- where a patient having statement Preference as Electronic+Paper
	 * will get update to Paper after updating the job with Paper configuration
	 */

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSatementPreferenceUpdatingToPaperFromElectronicPaper() throws Exception {
		SiteGenLoginPage loginpage;
		SiteGenHomePage pSiteGenHomePage;
		SiteGenPracticeHomePage pSiteGenPracticeHomePage;
		EstatementPage estatement;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		JalapenoAccountPage accountPage;
		JalapenoMyAccountPreferencesPage myAccountSecurityPage;
		JalapenoMyAccountProfilePage myAccountPage;

		logStep("Login to sitegen as Admin user");
		loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		pSiteGenHomePage = loginpage.login(testData.getProperty("jalapeno.sitgen.admin"),
				testData.getProperty("jalapeno.sitgen.password"));

		logStep("Navigate to SiteGen PracticeHomePage");
		pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		logStep("Check if SiteGen Practice Homepage elements are present ");
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		logStep("Click on online bill pay and Navigate to Estatement");
		pSiteGenPracticeHomePage.clickOnOnlineBillPay();

		logStep("Doing the configuration setting for estatement and setting up the default delivery option");
		estatement = new EstatementPage(driver);
		estatement.enableStatementDelivery("check");
		estatement.bothPaperAndElectronic("check");
		estatement.disablePaperOnly("uncheck");
		Thread.sleep(5000);// Waiting for the update of default delivery options
		estatement.defaultDeliveryOption("Paper + Electronic");
		estatement.submitButton();

		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().selfRegisterPatientWithPreference(driver, patient, testData.getUrl(), 3);

		logStep("Load login page");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Go to Account tab on my account page");
		accountPage = homePage.clickOnAccount();
		myAccountPage = accountPage.clickOnEditMyAccount();

		logStep("Navigate to Preference page and validate the preferencer has been updated to Paper + Electronic");
		myAccountSecurityPage = myAccountPage.goToPreferencesTab(driver);
		assertEquals("Paper + Electronic", myAccountSecurityPage.getSelectedStatementPreference());
		myAccountPage.clickOnLogout();

		logStep("Again login back to Sitegen for estamenet Setting ");
		loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		pSiteGenHomePage = loginpage.login(testData.getProperty("jalapeno.sitgen.admin"),
				testData.getProperty("jalapeno.sitgen.password"));

		logStep("Navigate to SiteGen PracticeHomePage");
		pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		logStep("Check if SiteGen Practice Homepage elements are present ");
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		logStep("Clicking on online Bill Pay and Navigate to Estatement");
		pSiteGenPracticeHomePage.clickOnOnlineBillPay();

		logStep("Setting up the estatement configuration and setting the default delivery option to Paper");
		estatement = new EstatementPage(driver);
		estatement.enableStatementDelivery("check");
		estatement.bothPaperAndElectronic("uncheck");
		estatement.disablePaperOnly("uncheck");
		Thread.sleep(5000);// Waiting for the update of default delivery options
		estatement.defaultDeliveryOption("Paper statement");
		estatement.submitButton();

		logStep("Load login page");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Go to security tab on my account page");
		accountPage = homePage.clickOnAccount();
		myAccountPage = accountPage.clickOnEditMyAccount();

		logStep("Navigate to Preference page and validate the preferencer has been updated to Paper Statement");
		myAccountSecurityPage = myAccountPage.goToPreferencesTab(driver);
		assertEquals("In the mail (paper statements)", myAccountSecurityPage.getSelectedStatementPreference());

	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeactivePatient() throws Exception {
		patient = null;
		createPatient();

		logStep("Go to account page");
		JalapenoHomePage homePage = new JalapenoHomePage(driver);
		homePage.clickOnLogout();

		logStep("Login to practice portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getProperty("doctor.login"),
				testData.getProperty("doctor.password"));

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();
		pPatientSearchPage.searchForPatientInPatientSearch(patient.getEmail());

		logStep("Verify the Search Result");
		IHGUtil.waitForElement(driver, 30, pPatientSearchPage.searchResult);
		pPatientSearchPage.clickOnPatient(patient.getFirstName(), patient.getLastName());
		pPatientSearchPage.deactivatePatient();

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		logStep("login with deactive patient details");
		loginPage.loginUnsuccessfuly(patient.getUsername(), patient.getPassword());

		logStep("Looking for the Error Message and verifying the error message");
		assertTrue(loginPage.isInactivePatientErrorDisplayed());

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDependentMedicationsRenewalWithoutFee() throws Exception {

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("med.wf.user.id"),
				testData.getProperty("med.wf.password"));

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProviderwithoutFee();

		logStep("Select a pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.selectPharmacy();

		logStep("Select Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectDependentMedications();

		logStep("Validating Prescription Renewal Fee Text is not present");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);
		confirmPage.prescriptionRenewalFee();

		logStep("Confirm Medication Request from Patient Portal");
		MedicationsConfirmationPage confirmPage1 = new MedicationsConfirmationPage(driver);
		String successMsg = confirmPage1.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("med.wf.doc.user.id"),
				testData.getProperty("med.wf.doc.password"));

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
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject, DEPENDENT_DRUG_DOSAGE);

		logStep("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Login to Patient Portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		homePage = loginPage.login(testData.getProperty("med.wf.user.id"), testData.getProperty("med.wf.password"));

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

		logStep("Navigate to Message Inbox");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for Medication approval from doctor in Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMedicationWithFeeForTrustedRep() throws Exception {
		JalapenoLoginPage loginPage;
		String name = "Medication Patient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Visa, name);
		Patient trustedPatient = PatientFactory.createJalapenoPatient(
				PortalUtil2.generateUniqueUsername(testData.getProperty("tr.user.id"), testData), testData);
		String email = testData.getTrustedRepEmail() + IHGUtil.createRandomNumber() + "@yopmail.com";

		logStep("Create a new patient on a practice where madication is configured");
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().registerPatient(driver, patient, testData.getProperty("med.portal.url"));

		logStep("Go to account page");
		JalapenoHomePage homePage = new JalapenoHomePage(driver);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(testData.getTrustedRepFirstName(), testData.getTrustedRepLastName(),
				email);

		logStep("Waiting for invitation email");
		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(email, INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT,
				15);

		assertNotNull(patientUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());

		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(patient.getFirstName(), patient.getLastName(), email);
		SecurityDetailsPage accountDetailsPage = linkAccountPage
				.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");

		logStep("Continue registration - create trusted representative credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getUsername(), trustedPatient.getPassword(),
				testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber());

		logStep("Log out from patient portal");
		loginPage = homePage.clickOnLogout();
		driver.navigate().refresh();

		logStep("Log in as Trusted Representative");
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProvider();

		logStep("Enter Credit Card Details");
		PrescriptionFeePage feePage = new PrescriptionFeePage(driver);
		feePage.fillRenewalFee(driver, creditCard);

		logStep("Select a pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.addNewPharmacy(driver);

		logStep("Select Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectMedications();

		logStep("Confirm Medication Request from Patient Portal");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);

		String successMsg = confirmPage.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		homePage.clickOnLogout();

		logStep("Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("med.doc.username"),
				testData.getProperty("med.doc.password"));

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
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject, DRUG_DOSAGE);

		logStep("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());

		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for Medication approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMedicationWithOutFeeForTrustedRep() throws Exception {
		JalapenoLoginPage loginPage;
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		Patient trustedPatient = PatientFactory.createJalapenoPatient(
				PortalUtil2.generateUniqueUsername(testData.getProperty("tr.user.id"), testData), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().registerPatient(driver, patient, testData.getProperty("med.wf.portal.url"));

		String email = testData.getTrustedRepEmail() + IHGUtil.createRandomNumber() + "@yopmail.com";

		logStep("Go to account page");
		JalapenoHomePage homePage = new JalapenoHomePage(driver);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(testData.getProperty("trusted.rep.first.name.medication"),
				testData.getProperty("trusted.rep.last.name.medication"), email);

		logStep("Waiting for invitation email");

		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(email, INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT,
				15);

		assertNotNull(patientUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());

		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(patient.getFirstName(), patient.getLastName(), email);
		SecurityDetailsPage accountDetailsPage = linkAccountPage
				.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");

		logStep("Continue registration - create trusted representative credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getUsername(), trustedPatient.getPassword(),
				testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber());

		logStep("Log out from patient portal");
		loginPage = homePage.clickOnLogout();
		driver.navigate().refresh();

		logStep("Log in as Trusted Representative");
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProviderwithoutFee();

		logStep("Select a pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.addNewPharmacy(driver);

		logStep("Select Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectMedications();

		logStep("Validating Prescription Renewal Fee Text is not present");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);
		confirmPage.prescriptionRenewalFee();

		logStep("Confirm Medication Request from Patient Portal");
		MedicationsConfirmationPage confirmPage1 = new MedicationsConfirmationPage(driver);
		String successMsg = confirmPage1.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("med.wf.doc.user.id"),
				testData.getProperty("med.wf.doc.password"));

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
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject, DRUG_DOSAGE);

		logStep("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Login to Patient Portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());

		logStep("Navigate to Message Inbox");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for Medication approval from doctor in Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMedicationsDeleteDependantPharmacy() throws Exception {

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("med.wf.user.id"),
				testData.getProperty("med.wf.password"));

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProviderwithoutFee();

		logStep("Add and delete dependant pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.addPharmacyWithNameAndPhoneNumber(driver);
		pharmaPage.deletePharmacy();
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMedicationDependent() throws Exception {

		String patientLogin = PortalUtil2.generateUniqueUsername("login", testData);
		String patientLastName = patientLogin.replace("login", "last");
		String patientEmail = patientLogin.replace("login", "mail") + "@yopmail.com";
		String name = "Medication Patient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getProperty("portal.url"));
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("med.doc.username"),
				testData.getProperty("med.doc.password"));

		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		logStep("Register Guardian - Enter all the details and click on Register");
		String patientUrl = patientActivationPage.setInitialDetailsAllFields("Guardian", patientLastName, "F",
				patientLastName + "G", testData.getProperty("phone.number"), patientEmail,
				testData.getProperty("dob.month"), testData.getProperty("dob.day"), testData.getProperty("dob.year"),
				"address1", "address2", "city", "Alabama", testData.getProperty("zip.code"));

		logStep("Register Dependent - Enter all the details and click on Register");
		String guardianUrl = patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "M",
				patientLastName + "D", testData.getProperty("phone.number"), patientEmail,
				testData.getProperty("dob.month"), testData.getProperty("dob.day"),
				testData.getProperty("dob.year.underage"), "address1", "address2", "city", "Alabama",
				testData.getProperty("zip.code"));
		assertTrue(patientActivationPage.checkGuardianUrl(guardianUrl));

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());

		logStep("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin,
				testData.getProperty("password"), testData);

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(guardianUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getProperty("zip.code"), testData.getProperty("dob.month"), testData.getProperty("dob.day"),
				testData.getProperty("dob.year.underage"));

		logStep("Continue registration - check dependent info and fill login credentials");
		log("Login username of Guardian is " + patientLogin);
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, patientEmail);
		jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(patientLogin, testData.getPassword(), "Parent");

		logStep("Guardian requesting Medication Renewal for his dependent");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		JalapenoHomePage homePage = jalapenoHomePage = loginPage.login(patientLogin, testData.getPassword());
		driver.navigate().refresh();

		logStep("Switching to dependent account");
		homePage.faChangePatient();

		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProvider();

		logStep("Enter Credit Card Details");
		PrescriptionFeePage feePage = new PrescriptionFeePage(driver);
		feePage.fillRenewalFee(driver, creditCard);

		logStep("Select a pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.addNewPharmacy(driver);

		logStep("Select Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectMedications();

		logStep("Confirm Medication Request from Patient Portal");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);

		String successMsg = confirmPage.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		homePage.clickOnLogout();

		logStep("Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin1 = new PracticeLoginPage(driver, testData.getProperty("portal.url"));
		PracticeHomePage practiceHome1 = practiceLogin1.login(testData.getProperty("med.doc.username"),
				testData.getProperty("med.doc.password"));

		logStep("Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome1.clickonRxRenewal();

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
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject, DRUG_DOSAGE);

		logStep("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		homePage = loginPage.login(patientLogin, testData.getPassword());

		logStep("Switching to dependent account");
		homePage.faChangePatient();

		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for Medication approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLADependentAppointmentRequest() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

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
		logStep("Choose a provider");
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2.: click on continue button");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayed");
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");

		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequest(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

		logStep("Looking for dependent appointment approval from doctor");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved " + tsPracticePortal));
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLADependentAppointmentRequestUpdate() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();
		logStep("Click appointment request");
		homePage.clickOnAppointmentV2(driver);

		try {
			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");
		}
		JalapenoAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver,
				JalapenoAppointmentRequestV2Step1.class);
		logStep("Choose a provider");
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2: click on continue");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayed");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequest(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for dependent appointment approval from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Approved " + tsPracticePortal));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal for second time ");
		AppoitmentRequest practicePortalApproved = new AppoitmentRequest();
		long tsPracticePortalUpdate = practicePortalApproved.ProceedAppoitmentRequestUpdate(driver, true,
				appointmentReason, testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal for update Appointment ");
		loginPage = new JalapenoLoginPage(driver, testData.getPracticeUrl2());
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();
		JalapenoMessagesPage messagesPageUpdate = homePage.showMessages(driver);

		logStep("Looking for dependent appointment approval from doctor");
		assertTrue(messagesPageUpdate.isMessageDisplayed(driver, "Update Appointment " + tsPracticePortalUpdate));
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestCancelAttachmentForDependent() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";
		String attachmentFile = "QuickSend.pdf";

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

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
		logStep("Choose a provider");
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2.: click continue");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayed");
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");

		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal to Approve the request with adding Attachment");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentRequesAttachmentt(driver, true, appointmentReason,
				testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword(), MessageErrorfilePath,
				MessagefilePath);

		logStep("Login back to patient portal to check the Approved status");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

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

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

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

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

		JalapenoMessagesPage messagesPageCommunicate = homePage.showMessages(driver);

		logStep("Looking for appointment Communicate only status from doctor");
		assertTrue(
				messagesPageCommunicate.isMessageDisplayed(driver, "Communicate only " + tsPracticePortalcommunicate));
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLAAppointmentProcessExternallyStatus2() throws Exception {
		String appointmentReason = System.currentTimeMillis() + " is my favorite number!";
		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

		logStep("Click appointment request");
		homePage.clickOnAppointmentV2(driver);

		try {
			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");
		}

		JalapenoAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver,
				JalapenoAppointmentRequestV2Step1.class);
		logStep("Choose a provider");
		appointmentRequestStep1.chooseFirstProvider();

		logStep("Continue to step 2: click on continue button");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayed");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		appointmentRequestStep1 = homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");

		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage.clickOnLogout();

		logStep("Proceed in Practice Portal");
		AppoitmentRequest practicePortal = new AppoitmentRequest();
		long tsPracticePortal = practicePortal.ProceedAppoitmentProcessExternallyRequest(driver, true,
				appointmentReason, testData.getPortalUrl(), testData.getDoctorLogin2(), testData.getDoctorPassword());

		logStep("Login back to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("practice.url2"));
		homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for Dependent appointment Process Externally from doctor");
		assertTrue(messagesPage.isMessageDisplayed(driver, "Process Externally " + tsPracticePortal));
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInactiveMedications() throws Exception {

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("med.wf.user.id"),
				testData.getProperty("med.wf.password"));

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProviderwithoutFee();

		logStep("Select a pharmacy");
		SelectPharmacyPage pharmaPage = new SelectPharmacyPage(driver);
		pharmaPage.selectPharmacy();

		logStep("Select Inactive Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectInactiveMedication();

		logStep("Validating Prescription Renewal Fee Text is not present");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);
		confirmPage.prescriptionRenewalFee();

		logStep("Confirm Medication Request from Patient Portal");
		MedicationsConfirmationPage confirmPage1 = new MedicationsConfirmationPage(driver);
		String successMsg = confirmPage1.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("med.wf.doc.user.id"),
				testData.getProperty("med.wf.doc.password"));

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

		logStep("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		logStep("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		logStep("Login to Patient Portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		homePage = loginPage.login(testData.getProperty("med.wf.user.id"), testData.getProperty("med.wf.password"));

		logStep("Navigate to Message Inbox");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Looking for Medication approval from doctor in Inbox");
		assertTrue(messagesPage.isMessageDisplayed(driver, "RxRenewalSubject"));

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAFreeforDependents() throws Exception {
		String questionText = "wat";

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPageFreequs = homePage.openSpecificAskaFree(testData.getProperty("aska.v2.name"));

		String askaSubjectForDependent = Long.toString(askPageFreequs.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPageFreequs.fillAndContinue(askaSubjectForDependent, questionText);

		assertTrue(askaSubjectForDependent.equals(askPage2.getSubject()),
				"Expected: " + askaSubjectForDependent + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPageFreequs = homePage.openSpecificAskaFree(testData.getProperty("aska.v2.name"));
		JalapenoAskAStaffV2HistoryListPage askHistoryList = askPageFreequs.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		JalapenoAskAStaffV2HistoryDetailPage askHistoryDetail = askHistoryList
				.goToDetailByReason(askaSubjectForDependent);

		logStep("Verify the subject and question in history detail match submission");
		assertTrue(askaSubjectForDependent.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubjectForDependent + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Open".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Open" + ", found: " + askHistoryDetail.getRequestDetailStatus());
		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();

		logStep("Login to practice portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("aska.v2.doctor.login"),
				testData.getProperty("aska.v2.doctor.password"));

		logStep("Click Ask A Staff tab");
		AskAStaffSearchPage searchQ = practiceHome.clickAskAStaffTab();

		logStep("Search for questions");
		searchQ.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = searchQ.getQuestionDetails(askaSubjectForDependent);
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
		homePage = loginPage.login(testData.getProperty("aska.v2.user"), testData.getProperty("aska.v2.password"));

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

		logStep("Go to messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Check if message was delivered");
		assertTrue(messagesPage.isMessageDisplayed(driver,
				"Automated Test " + (Long.toString(detailStep2.getCreatedTimeStamp()))));

		JalapenoLoginPage loginPageNew = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePageNew = loginPageNew.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		logStep("Switching to Dependent Account");
		homePage.faChangePatient();

		askPageFreequs = homePageNew.openSpecificAskaFree(testData.getProperty("aska.v2.name"));
		askHistoryList = askPageFreequs.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		askHistoryDetail = askHistoryList.goToDetailByReason(askaSubjectForDependent);

		logStep("Reverify subject and question in history detail, verify status is now completed");
		assertTrue(askaSubjectForDependent.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubjectForDependent + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Completed".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Completed" + ", found: " + askHistoryDetail.getRequestDetailStatus());
		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMyDeviceTab() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Fill in credentials and log in");
		JalapenoHomePage jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Click on Account");
		JalapenoMyAccountProfilePage myAccountPage = jalapenoHomePage.goToAccountPage();

		logStep("Click on Devices Tab");
		JalapenoMyAccountDevicesPage myDevicesTab = myAccountPage.navigateToDevicesTab(driver);
		assertTrue(myDevicesTab.isRemoveDeviceDisplayed());

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLATrustedRepresentativeAcessForMessagesFromPracticePortal() throws Exception {

		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		JalapenoMessagesPage messagesPage;
		PatientSearchPage pPatientSearchPage;
		PatientDashboardPage pPatientDashboardPage;
		PatientTrustedRepresentativePage patientInviteTrustedRepresentative;

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Patient Search Link");
		pPatientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(
				testData.getProperty("trusted.rep.care.management.first.name"),
				testData.getProperty("trusted.rep.care.management.last.name"));
		pPatientDashboardPage = pPatientSearchPage.clickOnPatient(
				testData.getProperty("trusted.rep.care.management.first.name"),
				testData.getProperty("trusted.rep.care.management.last.name"));

		logStep("Set Patient Search Fields");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.selectCustomAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Messages", "noAccess");
		patientInviteTrustedRepresentative.clickOnInviteBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getProperty("patient.login"), testData.getProperty("patient.password"));

		logStep("Go to Messages and ASKA Question Not displayed when No Access is granted");
		assertFalse(homePage.isMessagesDisplayed(), "Messages Not Accessible");
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Patient Search Link");
		practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(
				testData.getProperty("trusted.rep.care.management.first.name"),
				testData.getProperty("trusted.rep.care.management.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("trusted.rep.care.management.first.name"),
				testData.getProperty("trusted.rep.care.management.last.name"));

		logStep("Set Patient Search Fields");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Messages", "viewOnly");
		patientInviteTrustedRepresentative.clickOnInviteBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		loginPage.login(testData.getProperty("patient.login"), testData.getProperty("patient.password"));

		logStep("Go to messages");
		messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.returnSubjectMessage().length() > 0);

		logStep("Verify Aska question button should not display for view only access");
		assertFalse(messagesPage.isAskaQuestionButtonDisplayed());
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLATrustedRepresentativeAcessForFormsFromPractice() throws Exception {
		patient = null;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;
		PatientSearchPage patientSearchPage;
		PatientTrustedRepresentativePage patientInviteTrustedRepresentative;
		createPatient();
		Patient trustedPatient = PatientFactory.createJalapenoPatient(
				PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData), testData);
		logStep("Load login page");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Search");
		patientSearchPage = practiceHome.clickPatientSearchLink();
		patientSearchPage.searchForPatientInPatientSearch(patient.getEmail());
		patientSearchPage.clickOnPatient(patient.getFirstName(), patient.getLastName());
		logStep("Invite Trusted Representative With View Only Access");
		patientInviteTrustedRepresentative = patientSearchPage.clickInviteTrustedRepresentative();
		patientInviteTrustedRepresentative.inviteTrustedRepresentative(trustedPatient, "Forms", "viewOnly");
		patientInviteTrustedRepresentative.clickOnInviteBtn();
		assertTrue(patientSearchPage.wasInviteTrustedRepresentativeSuccessful());

		logStep("Waiting for invitation email");

		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(trustedPatient.getEmail(), INVITE_EMAIL_SUBJECT_REPRESENTATIVE,
				INVITE_EMAIL_BUTTON_TEXT, 15);

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

		logStep("Verify Forms Solutions");
		assertTrue(homePage.isFormsSolutionDisplayed());
		homePage.clickOnHealthForms();

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Search");
		patientSearchPage = practiceHome.clickPatientSearchLink();
		patientSearchPage.searchForPatientInPatientSearch(patient.getEmail());
		patientSearchPage.clickOnPatient(patient.getFirstName(), patient.getLastName());
		logStep("Forms: Update Trusted Representative Access with No Access");
		patientInviteTrustedRepresentative = patientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Forms", "noAccess");
		patientInviteTrustedRepresentative.clickOnUpdateBtn();

		logStep("Login as Trusted Representative and verify Forms Solution");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());
		assertFalse(homePage.isFormsSolutionDisplayed());
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLATrustedRepresentativeAcessForMessagesFromPatient() throws Exception {
		logStep("Createing a Gurdian Patient");
		createCommonPatient();
		Patient trustedPatient = PatientFactory.createJalapenoPatient(
				PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData), testData);

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative with no access to message solution");
		accountPage.clickInviteButton();
		accountPage.givingPermissionWithModuleName("Messages", "noAccess");
		accountPage.inviteTrustedRepresentativeWithPermission(trustedPatient);

		logStep("Waiting for invitation email");

		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(trustedPatient.getEmail(), INVITE_EMAIL_SUBJECT_REPRESENTATIVE,
				INVITE_EMAIL_BUTTON_TEXT, 15);

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

		logStep("Log out from patient portal");
		loginPage = homePage.clickOnLogout();

		logStep("Log in and log out as Trusted Representative");
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));

		log("Verify Message solution Not display for Trusted Rep");
		assertTrue(homePage.isMessageSolutionDisplayed());
		homePage.clickOnLogout();

		logStep("Log in to a Gurdian user role and change the permission");
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		accountPage = homePage.clickOnAccount();
		homePage.editTrustedRepAccount();
		accountPage.givingPermissionWithModuleName("Messages", "viewOnly");
		accountPage.clickOnSaveMyChangesButton();

		logStep("Verify the success message after changeing the user permission to view only");
		homePage.clickOnLogout();

		logStep("Log in and log out as Trusted Representative and verify the view only access permission");
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.returnSubjectMessage().length() > 0);

		logStep("Verify Aska question button should not display for view only access");
		assertFalse(messagesPage.isAskaQuestionButtonDisplayed());
		homePage.clickOnLogout();

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLATrustedRepresentativeAcessForFormsFromPatient() throws Exception {
		logStep("Creating a Gurdian Patient");
		createCommonPatient();
		Patient trustedPatient = PatientFactory.createJalapenoPatient(
				PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData), testData);

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative with no access to message solution");
		accountPage.clickInviteButton();
		accountPage.givingPermissionWithModuleName("Forms", "noAccess");
		accountPage.inviteTrustedRepresentativeWithPermission(trustedPatient);

		logStep("Waiting for invitation email");

		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(trustedPatient.getEmail(), INVITE_EMAIL_SUBJECT_REPRESENTATIVE,
				INVITE_EMAIL_BUTTON_TEXT, 15);

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

		logStep("Log out from patient portal");
		loginPage = homePage.clickOnLogout();

		logStep("Log in and log out as Trusted Representative");
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));

		log("Verify Forms solution Not display for Trusted Rep");
		assertFalse(homePage.isFormsSolutionDisplayed());
		homePage.clickOnLogout();

		logStep("Log in to a Gurdian user role and change the permission");
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		accountPage = homePage.clickOnAccount();
		homePage.editTrustedRepAccount();
		accountPage.givingPermissionWithModuleName("Forms", "viewOnly");
		accountPage.clickOnSaveMyChangesButton();

		logStep("Verify the success message after changeing the user permission to view only");
		homePage.clickOnLogout();
		driver.navigate().refresh();

		logStep("Login as Trusted Representative and verify the view only access permission");
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());

		logStep("Click on forms solution");
		assertTrue(homePage.isFormsSolutionDisplayed());
		homePage.clickOnHealthForms();

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts",
			"commonpatient" }, retryAnalyzer = RetryAnalyzer.class)

	public void testCareManagerTrustedRepForAppointmentMedication() throws Exception {

		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().selfRegisterPatient(driver, patient, testData.getProperty("med.wf.portal.url"));
		Patient trustedPatient = PatientFactory.createJalapenoPatient(
				PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData), testData);

		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		log("Click on Account and Invite buutton for Trusted Rep");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		accountPage.clickInviteButton();

		log("Giving view only permission to Appointment and Medication");
		accountPage.givingPermissionWithModuleName("Appointments", "viewOnly");
		accountPage.givingPermissionWithModuleName("Medications", "viewOnly");

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentativeWithPermission(trustedPatient);

		logStep("Waiting for invitation email");

		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(trustedPatient.getEmail(), INVITE_EMAIL_SUBJECT_REPRESENTATIVE,
				INVITE_EMAIL_BUTTON_TEXT, 10);

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

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal verify rxrerequest Button not displayed");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		assertFalse(medPage.isRxRequestBtnDisplayed());
		homePage.clickOnLogout();

		log("Login and verify Appointment Request Button Not displayed in Appointment");
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());
		homePage.clickOnAppointmentV2(driver);
		JalapenoAppointmentRequestPage apptRequestPage = new JalapenoAppointmentRequestPage(driver);
		assertFalse(apptRequestPage.isAppointmentRequestBtnDisplayed());
		homePage.clickOnLogout();

		logStep("Log in to a Account Owner");
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		log("clicked on Account and Edit Tristed Rep Button");
		accountPage = homePage.clickOnAccount();
		accountPage.clickOnEditTrustedRepAccount();

		log("Changing the permission to NoAccess and logout");
		accountPage.givingPermissionWithModuleName("Appointments", "noAccess");
		accountPage.givingPermissionWithModuleName("Medications", "noAccess");
		accountPage.clickOnSaveMyChangesButton();
		homePage.clickOnLogout();

		log("Loging with trusted rep");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());

		log("Validate Appointment and Medication icon not present in Homepage");
		assertFalse(homePage.isMedicationSolutionisplayed());
		assertFalse(homePage.isAppointmentSolutionisplayed());

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLATrustedRepresentativeAccessForAppointmentsRxRFromPractice() throws Exception {
		patient = null;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;
		PatientSearchPage patientSearchPage;
		PatientTrustedRepresentativePage patientInviteTrustedRepresentative;
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().selfRegisterPatient(driver, patient, testData.getProperty("med.wf.portal.url"));
		Patient trustedPatient = PatientFactory.createJalapenoPatient(
				PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData), testData);
		logStep("Load login page");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		homePage = loginPage.login(patient.getUsername(), patient.getPassword());

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getProperty("med.wf.doc.user.id"),
				testData.getProperty("med.wf.doc.password"));

		logStep("Click on Search");
		patientSearchPage = practiceHome.clickPatientSearchLink();
		patientSearchPage.searchForPatientInPatientSearch(patient.getEmail());
		patientSearchPage.clickOnPatient(patient.getFirstName(), patient.getLastName());
		logStep("Invite Trusted Representative With View Only Access for Appointments");
		PatientTrustedRepresentativePage patientInviteTR = patientSearchPage.clickInviteTrustedRepresentative();
		patientInviteTR.inviteTrustedRepresentative(trustedPatient, "Appointments", "viewOnly");
		patientInviteTR.clearAllFields();
		patientInviteTR.inviteTrustedRepresentative(trustedPatient, "Medications", "viewOnly");
		patientInviteTR.clickOnInviteBtn();
		assertTrue(patientSearchPage.wasInviteTrustedRepresentativeSuccessful());

		logStep("Waiting for invitation email");

		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(trustedPatient.getEmail(), INVITE_EMAIL_SUBJECT_REPRESENTATIVE,
				INVITE_EMAIL_BUTTON_TEXT, 15);

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

		logStep("Verify Appointment Solutions");
		JalapenoAppointmentRequestPage appReqPage = homePage.clickOnAppointment(driver);

		logStep("Verify Request An Appointment Button is not present");
		assertFalse(appReqPage.isAppointmentRequestBtnDisplayed());

		logStep("Load login page");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());

		MedicationsHomePage medReqPage = homePage.clickOnMedications(driver);
		logStep("Verify Rx Request Button is not present in Medications module");
		assertFalse(medReqPage.isRxRequestBtnDisplayed());

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getProperty("med.wf.doc.user.id"),
				testData.getProperty("med.wf.doc.password"));

		logStep("Click on Search");
		patientSearchPage = practiceHome.clickPatientSearchLink();
		patientSearchPage.searchForPatientInPatientSearch(patient.getEmail());
		patientSearchPage.clickOnPatient(patient.getFirstName(), patient.getLastName());
		logStep("Update Trusted Representative Access with No Access for Appointments and Medications");

		patientInviteTrustedRepresentative = patientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Appointments", "noAccess");
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Medications", "noAccess");
		patientInviteTrustedRepresentative.clickOnUpdateBtn();

		logStep("Login as Trusted Representative and verify Appointments module is not present");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
		homePage = loginPage.login(trustedPatient.getUsername(), trustedPatient.getPassword());
		assertFalse(homePage.isAppointmentSolutionisplayed());
		logStep("Verify Medications module is not present");
		assertFalse(homePage.isMedicationSolutionisplayed());
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGreenLight() throws Exception {
		logStep("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());

		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage
				.login(testData.getProperty("patient.education.with.issue.username"), testData.getPassword());

		logStep("Navigate to Medical Record Summaries Page");
		MedicalRecordSummariesPage recordSummaries = jalapenoHomePage.clickOnMedicalRecordSummaries(driver);

		logStep("Click on Green Light Button ");
		recordSummaries.clickGreenLight();

		logStep("Validating the Green Light Health Page");
		assertTrue(recordSummaries.isGreenLightLogoDisplayed());
		assertEquals(recordSummaries.getCreateYourNewAccount(), "Create your new account");

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLATrustedRepresentativeAcessForHealthRecordFromPatient() throws Exception {

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("user.id"), testData.getProperty("password"));

		logStep("Click on the acount button");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Click on the edit trusted representatives button");
		homePage.editTrustedRepAccount();

		logStep("Select manage access per category radio button");
		accountPage.clickOnRdoManageAccessPerCategory();

		logStep("Unchecked the full access to health record and click on save my change button");
		accountPage.givingPermissionWithModuleName("Health Record", "fullAccessHealthRecord");
		accountPage.clickOnSaveMyChangesButton();

		logStep("Load trusted representatives user role");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getProperty("caremanager.trustedrep.healthrecord.username"),
				testData.getProperty("password"));

		logStep("Verify that system should not display the Health Record");
		assertFalse(homePage.isHealthRecordSolutionisplayed());

		logStep("Load login page with the parent role");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getProperty("user.id"), testData.getProperty("password"));

		logStep("Click on the acount button");
		accountPage = homePage.clickOnAccount();

		logStep("Click on the edit trusted representatives button");
		homePage.editTrustedRepAccount();

		logStep("Select manage access per category radio button");
		accountPage.clickOnRdoManageAccessPerCategory();

		logStep("Unchecked the full access to health record and click on save my change button");
		accountPage.givingPermissionWithModuleName("Health Record", "fullAccessHealthRecord");
		accountPage.clickOnSaveMyChangesButton();

		logStep("Load trusted representatives user role");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		homePage = loginPage.login(testData.getProperty("caremanager.trustedrep.healthrecord.username"),
				testData.getProperty("password"));

		logStep("Verify that system should not display the Health Record");
		assertTrue(homePage.isHealthRecordSolutionisplayed());

		logStep("Verify that system should allow user to view the Health Record");
		MedicalRecordSummariesPage healthrecord = homePage.clickOnMedicalRecordSummaries(driver);
		assertTrue(healthrecord.isViewButtonDisplayed());
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAttachmentInQuestionHistory() throws Exception {
		String questionText = "wat";
		String expectedCorrectFileText = "sw-test-academy.txt";

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPageFreequs = homePage.openSpecificAskaFree(testData.getProperty("aska.v2.name"));

		String askaSubjectForDependent = Long.toString(askPageFreequs.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPageFreequs.uploadAttachment(askaSubjectForDependent, questionText,
				CorrectfilePath);

		assertTrue(askaSubjectForDependent.equals(askPage2.getSubject()),
				"Expected: " + askaSubjectForDependent + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPageFreequs = homePage.openSpecificAskaFree(testData.getProperty("aska.v2.name"));
		JalapenoAskAStaffV2HistoryListPage askHistoryList = askPageFreequs.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		JalapenoAskAStaffV2HistoryDetailPage askHistoryDetail = askHistoryList
				.goToDetailByReason(askaSubjectForDependent);

		logStep("Verify the subject and question in history detail match submission");
		assertTrue(askaSubjectForDependent.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubjectForDependent + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Open".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Open" + ", found: " + askHistoryDetail.getRequestDetailStatus());
		assertTrue(expectedCorrectFileText.equals(askHistoryDetail.getRequestAttachedFile()),
				"Expected: " + expectedCorrectFileText + ", found: " + askHistoryDetail.getRequestAttachedFile());

		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testAskInvalidAttachment() throws Exception {
		String expectedCorrectFileText = "sw-test-academy.txt";

		logStep("Login as patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("aska.v2.user"),
				testData.getProperty("aska.v2.password"));

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaV2(testData.getProperty("aska.v2.name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.fillAndContinueAttachment(askaSubject, questionText);

		logStep("Add Attachment and remove Attachment ");
		askPage1.uploadInvalidAndValidFile(InvalidfilePath, CorrectfilePath);

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
		askPage1 = homePage.openSpecificAskaV2(testData.getProperty("aska.v2.name"));
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
	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testConsolidatedHealthRecord() throws Exception {
		logStep("Login as a patient user role");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(
				testData.getProperty("caremanager.trustedrep.healthrecord.username"), testData.getProperty("password"));

		logStep("Verify that system should display the Health Record");
		assertTrue(homePage.isHealthRecordSolutionisplayed());

		logStep("Verify that system should allow user to view the Health Record");
		MedicalRecordSummariesPage healthrecord = homePage.clickOnMedicalRecordSummaries(driver);
		assertTrue(healthrecord.isViewButtonDisplayed());

		logStep("Click on the Consolidated Health Record");
		healthrecord.clickOnConsolidatedHealthRecordBtn();

		logStep("Select the Consolidated Health Record check box");
		healthrecord.selectCheckBox();

		logStep("Select the Request Record Button");
		healthrecord.clickOnRequestRecordButton();

		logStep("Verify the Request Recived message");
		assertTrue(healthrecord.isRequestRecivedMessageDisplayed());

	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateZipcodeDobPhoneInMyAccount() throws Exception {
		String phoneNumberLastDigit = IHGUtil.createRandomNumericString(4);
		String zipCode = IHGUtil.createRandomZip();
		String yearDob = IHGUtil.createRandomNumericStringInRange(1980, 2021);

		logStep("Login as a patient user role");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("user.id"), testData.getProperty("password"));

		logStep("Go to security tab on my account page");
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Click on the edit my account button");
		JalapenoMyAccountProfilePage myAccountPage = accountPage.clickOnEditMyAccount();

		logStep("Update phone number, zipcode and year");
		myAccountPage.updateDobZipPhoneFields(phoneNumberLastDigit, zipCode, yearDob);

		logStep("Click on the save button");
		myAccountPage.clickOnSaveAccountButton();

		logStep("Verify the updated message");
		assertTrue(myAccountPage.isProfileInformationUpdateMessageDisplayed());

		logStep("Verify the update dob,phone and zipcode");
		assertEquals(myAccountPage.getDOByear(), yearDob);
		assertEquals(myAccountPage.getPhone3(), phoneNumberLastDigit);
		assertEquals(myAccountPage.getZipCodeTextbox(), zipCode);
	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testValidateDependentPharmacy() throws Exception {

		String name = "Medication Patient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);

		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("guardian.username"),
				testData.getProperty("guardian.password"));

		logStep("Switch to the Dependent and place a Rx Request foor dependent");

		JalapenoHomePage jalapenoHomePage = new JalapenoHomePage(driver);
		jalapenoHomePage.faChangePatient();

		logStep("Click on Medications");
		homePage.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPage = new MedicationsHomePage(driver);
		medPage.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage select = new LocationAndProviderPage(driver);
		select.chooseLocationAndProvider();

		logStep("Enter Credit Card Details");
		PrescriptionFeePage feePage = new PrescriptionFeePage(driver);
		feePage.fillRenewalFee(driver, creditCard);

		logStep("Select a pharmacy");
		SelectPharmacyPage dependentPharmaPage = new SelectPharmacyPage(driver);
		String selectedPharmacy = dependentPharmaPage.addNewPharmacy(driver,
				testData.getProperty("dependent.pharmacy"));

		logStep("Select Medications");
		SelectMedicationsPage selectMedPage = new SelectMedicationsPage(driver);
		selectMedPage.selectMedications();

		logStep("Confirm Medication Request from Patient Portal");
		MedicationsConfirmationPage confirmPage = new MedicationsConfirmationPage(driver);

		String successMsg = confirmPage.confirmMedication(driver);
		assertEquals(successMsg, "Your prescription request has been submitted.");

		logStep("Login as Guardian and verify Dependent pharmacy is not present");
		JalapenoLoginPage loginPageNew = new JalapenoLoginPage(driver, testData.getProperty("med.portal.url"));
		JalapenoHomePage homePageNew = loginPageNew.login(testData.getProperty("guardian.username"),
				testData.getProperty("guardian.password"));

		logStep("Click on Medications");
		homePageNew.clickOnMedications(driver);

		log("Initiating Medications 2.0 Request from Patient Portal");
		MedicationsHomePage medPageNew = new MedicationsHomePage(driver);
		medPageNew.clickOnRxRequest();

		logStep("Select Location and Provider");
		LocationAndProviderPage selectNew = new LocationAndProviderPage(driver);
		selectNew.chooseLocationAndProvider();

		logStep("Enter Credit Card Details");
		PrescriptionFeePage feePageNew = new PrescriptionFeePage(driver);
		feePageNew.fillRenewalFee(driver, creditCard);

		logStep("Validate that Pharmacy added by dependent is not present in list of pharmacies");
		SelectPharmacyPage guardianPharmaPage = new SelectPharmacyPage(driver);
		assertTrue(guardianPharmaPage.validateIfDependentPharmacyPresent(selectedPharmacy));

		homePage.clickOnLogout();

	}

	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResetPasswordWithSameEmailAndDOB() throws Exception {
		logStep("Logging into yopmail and delete older mails");
		YopMail mail = new YopMail(driver);
		mail.deleteAllEmails(testData.getProperty("forgot.password.email"));

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

		logStep("Clicking on forgot username or password");
		JalapenoForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordButton();
		JalapenoForgotPasswordPage2 forgotPasswordPage2 = forgotPasswordPage.fillInDataPageWithSameEmailAndDOB(
				testData.getProperty("forgot.password.email"), testData.getDOBMonth(), testData.getDOBDay(),
				testData.getDOBYear(), testData.getProperty("forgot.password.first.name"));

		logStep("Message was sent, closing");
		forgotPasswordPage2.clickCloseButton();
		logStep("Logging into yopmail and getting ResetPassword url");
		String[] mailAddress = testData.getProperty("forgot.password.email").split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";
		String url = mail.getLinkFromEmail(mailAddress[0], emailSubject, inEmail, 20);
		if (!isInviteLinkFinal(url)) {
			url = getRedirectUrl(url);
		}
		assertNotNull(url, "Url is null.");
		JalapenoForgotPasswordPage3 forgotPasswordPage3 = new JalapenoForgotPasswordPage3(driver, url);

		logStep("Redirecting to patient portal, filling secret answer as: " + testData.getProperty("secret.answer"));
		JalapenoForgotPasswordPage4 forgotPasswordPage4 = forgotPasswordPage3
				.fillInSecretAnswer(testData.getProperty("secret.answer"));

		logStep("Filling new password");
		JalapenoHomePage homePage = forgotPasswordPage4.fillInNewPassword(testData.getPassword());

		logStep("Logging out");
		loginPage = homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "acceptance-basics", "commonpatient" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateSamePatientWithVaildPhonenumber() throws Exception {
		createCommonPatient();
		logStep("Load login page and login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		logStep("Try to create the same patient");
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();

		patientDemographicPage.fillInPatientData(patient.getFirstName(), patient.getLastName(),
				testData.getProperty("email"), testData.getProperty("dob.month.text"), patient.getDOBDay(),
				patient.getDOBYear(), patient.getGender(), patient.getZipCode(), patient.getAddress2(),
				patient.getAddress1(), patient.getCity(), patient.getState());

		patientDemographicPage.tryToContinueToSecurityPage();
		patientDemographicPage.tryToVerifyPhonenumber(testData.getProperty("phone.number"));
		logStep("Verify password error displayed");
		assertTrue(loginPage.getAlreadyHaveAnAccountErrorText().contentEquals(
				"Thanks for verifying your account. We've determined that you already have an account at this practice. Please sign in or click the 'I forgot…' link to recover your user name or password."));

	}

	public void testSuppressPayments() throws Exception {

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("statements.portal.url"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("statements.pat.username"),
				testData.getProperty("statments.pat.password"));

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Verify Statements Message is Displayed in Inbox");
		assertTrue(messagesPage.isMessageFromEstatementsDisplayed(driver));

		logStep("Verify Statements PDF is displayed");
		JalapenoPayBillsStatementPdfPage statementPage = messagesPage.openPDFStatement();
		assertTrue(statementPage.isStatementPDFdisplayed(driver));

		logStep("Click on Return to Statements to view Statements history and details");
		statementPage.clickOnStatementsHistory();
		statementPage.showStatementDetails();

	}

	@Test(enabled = true, groups = { "acceptance-linkedaccounts" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientTrustedRep() throws Exception {
		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;
		PatientSearchPage patientSearchPage;
		JalapenoHomePage jalapenoHomePage;
		JalapenoLoginPage jalapenoLoginPage;
		String email = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
		String firstname = testData.getProperty("first.name") + IHGUtil.createRandomNumber();
		String lastname = testData.getProperty("last.name");
		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');

		logStep("Patient Activation on Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationLink(driver, testData, patientsEmail);

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				PracticeConstants.ZIP_CODE, JalapenoConstants.DATE_OF_BIRTH_MONTH_NO,
				JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);
		String firstNameAccount = patientActivationSearchTest.getFirstNameString();

		logStep("Finishing of patient activation: step 2 - filling patient data");
		jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				patientActivationSearchTest.getPatientIdString(), testData.getPassword(), testData);

		logStep("Logging out");
		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();

		logStep("Logging again: " + patientActivationSearchTest.getPatientIdString() + " \\ " + testData.getPassword());
		jalapenoHomePage = jalapenoLoginPage.login(patientActivationSearchTest.getPatientIdString(),
				testData.getPassword());

		logStep("Nvaigating to account and fetch the first name");
		JalapenoAccountPage accountPage = jalapenoHomePage.clickOnAccount();
		JalapenoMyAccountProfilePage myAccountPage = new JalapenoMyAccountProfilePage(driver);
		myAccountPage = accountPage.clickOnEditMyAccount();

		logStep("Logging out");
		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();

		YopMail mail = new YopMail(driver);
		logStep("Logging into Mailinator and getting Patient Activation url");
		String unlockLinkEmail = mail.getLinkFromEmail(patientsEmail,
				INVITE_EMAIL_SUBJECT_PATIENT + testData.getPracticeName(), INVITE_EMAIL_BUTTON_TEXT, 30);
		assertNotNull(unlockLinkEmail, "Error: Activation link not found.");

		logStep("Retrieved activation link is " + unlockLinkEmail);
		if (!isInviteLinkFinal(unlockLinkEmail)) {
			unlockLinkEmail = getRedirectUrl(unlockLinkEmail);
			log("Retrieved link was redirect link. Final link is " + unlockLinkEmail);
		}
		logStep("Comparing with portal unlock link " + unlockLinkPortal);
		assertEquals(unlockLinkEmail, unlockLinkPortal, "!patient unlock links are not equal!");

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor.login"),
				testData.getProperty("doctor.password"));

		logStep("Click on Search");
		patientSearchPage = practiceHome.clickPatientSearchLink();
		patientSearchPage.searchForPatientInPatientSearch(patientsEmail);
		patientSearchPage.clickOnPatient(firstNameAccount, "Tester");
		patientSearchPage.clickInviteTrustedRepresentative();
		PatientTrustedRepresentativePage trustedrep = new PatientTrustedRepresentativePage(driver);
		trustedrep.inviteTrustedRepresentativenew(firstname, lastname, email);
		String patientUrl = mail.getLinkFromEmail(email, INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT,
				15);

		logStep("Redirecting to verification page");
		patientVerificationPage = new PatientVerificationPage(driver, patientUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				PracticeConstants.ZIP_CODE, JalapenoConstants.DATE_OF_BIRTH_MONTH_NO,
				JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);

		logStep("Continue registration - sign in as trusted representative username and password");

		linkAccountPage.continueWithAuthUserTurstedRep();

		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(firstname, testData.getPassword(),
				testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber());

		assertTrue(jalapenoHomePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		jalapenoLoginPage = jalapenoHomePage.clickOnLogout();
		jalapenoHomePage = jalapenoLoginPage.login(firstname, testData.getPassword());
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(false));

		jalapenoHomePage.clickOnLogout();

	}

	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testValidateResetPasswordLink() throws Exception {

		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
		patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().selfRegisterPatient(driver, patient, testData.getUrl());
		String resetUrl = resetForgotPasswordLink(patient.getEmail());

		logStep("Validate Reset Url is invalid");

		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver);
		assertTrue(loginPage.checkResetPasswordError(resetUrl));
	}
	
	@Test(enabled = true, groups = { "acceptance-basics" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTermsOfServicePopUp() throws Exception {

		String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');

		logStep("Patient Activation on Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationLink(driver, testData, patientsEmail);

		logStep("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				PracticeConstants.ZIP_CODE, JalapenoConstants.DATE_OF_BIRTH_MONTH_NO,
				JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);

		logStep("Validate presence of Terms of Service popup in Security Details Page");
		assertTrue(accountDetailsPage.isTermsOfServicePopupDisplayed());
		
	}
	
	@Test(enabled = true, groups = { "acceptance-solutions" }, retryAnalyzer = RetryAnalyzer.class)
    public void testBanner() throws Exception {
        String message=IHGUtil.createRandomNumericString(8);
        
        logStep("Login to sitegen as Admin user");
        SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
        SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("sitegen.admin.user"),
                testData.getProperty("sitegen.password.user"));
        logStep("Navigate to SiteGen PracticeHomePage");
        SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
        logStep("Check if SiteGen Practice Homepage elements are present ");
        assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
                "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");
        
        pSiteGenPracticeHomePage.clickOnPatientBroadcast();

         PatientPortalBroadcastPage PatientPortalBroadcast = new PatientPortalBroadcastPage(driver);
         PatientPortalBroadcast.deleteBroadCast();
        PatientPortalBroadcast.addBroadCast(testData.getProperty("broadcast.title"),message);

        logStep("Load login page and login");
        JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("med.wf.portal.url"));
        String actual=loginPage.readBroadcastMessage();
        assertEquals(actual, message, "broadcast Message is matching");
        JalapenoHomePage homePage = loginPage.login(testData.getProperty("med.wf.user.id"),
                testData.getProperty("med.wf.password"));
        
        String actualBroadcastmessage=homePage.readBroadcast();
        assertEquals(actualBroadcastmessage, message);

        homePage.clickOnLogout();
    }
	
}
