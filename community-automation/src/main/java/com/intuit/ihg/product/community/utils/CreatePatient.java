package com.intuit.ihg.product.community.utils;

import java.util.Date;
import java.util.Random;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.CreateAnAccount.CreateAnAccountHandleIframe;
import com.intuit.ihg.product.community.page.CreateAnAccount.CreateAnAccountPasswordPage;
import com.intuit.ihg.product.community.page.CreateAnAccount.CreateAnAccountPatientPage;
import com.intuit.ihg.product.community.page.CreateAnAccount.CreateAnAccountWelcomePage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessageDetailPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessagePage;

public class CreatePatient extends BaseTestNGWebDriver {

	public String email;
	public String password = "intuit123";

	public void CreatePatientTest(WebDriver driver, TestObject test, PracticeUrl practiceUrl, GmailMessage gmailMessage, Patient patient)
			throws Exception {

		CommunityLoginPage communityLoginPage = new CommunityLoginPage(driver);
		Random randomGenerator = new Random();

		// Setting up date for Gmail search
		Date startEmailSearchDate = new Date();
		GmailCommunity gmail = new GmailCommunity(patient.getGmailUName(), patient.getGmailPassword());

		// Creating random number which is used for identification of the User
		int randomTestID = 100000000 + randomGenerator.nextInt(900000);
		String testIDString = Integer.toString(randomTestID);

		CommunityUtils communityUtils = new CommunityUtils();

		// Moving to Community Login Page
		log("Moving to page: " + communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
				"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		String errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		assertTrue(communityLoginPage.validatePageElements(),
				"Cannot locate the User ID and Password Inputs and Sign In button. Community sign in page is not loaded or Community is down");

		// Moving to Sign Up Now Page
		log("Clicking on Sign Up now");
		communityLoginPage.btn_Sign_Up_Now.click();

		CreateAnAccountHandleIframe createAnAccountHandleIframe = new CreateAnAccountHandleIframe(driver);
		createAnAccountHandleIframe.handleIframeAccount();

		CreateAnAccountPatientPage createAnAccountPatientPage = new CreateAnAccountPatientPage(driver);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
				"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Setting up User properties
		log("Specifiyng User properties");

		log("First Name: Community" + testIDString);
		createAnAccountPatientPage.firstname.sendKeys("Community" + testIDString);

		log("Last name: Automation:" + testIDString);
		createAnAccountPatientPage.lastname.sendKeys("Automation:" + testIDString);

		email = createAnAccountPatientPage.generateEmail(test.getTestEnv(), testIDString);
		log("Email address: " + email);
		createAnAccountPatientPage.email.sendKeys(email);

		log("Email address confirm: " + createAnAccountPatientPage.generateEmail(test.getTestEnv(), testIDString));
		createAnAccountPatientPage.confirmEmail.sendKeys(createAnAccountPatientPage.generateEmail(test.getTestEnv(), testIDString));

		log("Phone number: 5555555555");
		createAnAccountPatientPage.phonenumber.sendKeys("5555555555");

		log("Phone number type: Mobile");
		createAnAccountPatientPage.phonetype.sendKeys("Mobile");

		Thread.sleep(1000);

		log("Birth mont: January");
		createAnAccountPatientPage.input_dob_month.sendKeys("January");

		log("Birth Day: 01");
		createAnAccountPatientPage.input_dob_day.sendKeys("01");

		log("Birth Year: 1970");
		createAnAccountPatientPage.input_dob_year.sendKeys("1970");

		log("ZIP code: 94720");
		createAnAccountPatientPage.zipcode.sendKeys("94720");

		log("Social Security Number: " + testIDString);
		createAnAccountPatientPage.ssn.sendKeys(testIDString);

		// Moving to Password Page
		log("Moving to security setup page");
		CreateAnAccountPasswordPage createAnAccountPasswordPage = createAnAccountPatientPage.clickSubmit();
		
		log("Waiting for page to load");
		assertTrue(createAnAccountPasswordPage.findUserIdInput(driver), "User ID input was not found, page was not loaded properly");

		log("Setting User ID: " + email);
		createAnAccountPasswordPage.userid.sendKeys(email);

		log("Setting User Password: intuit123");
		createAnAccountPasswordPage.password.sendKeys(password);

		log("Setting User Password Confirm: intuit123");
		createAnAccountPasswordPage.confirmPassword.sendKeys(password);

		log("Selecting Secret Question: What is your favourite sports team?");
		createAnAccountPasswordPage.chooseSecurityQuestion.sendKeys("What is your favourite sports team?");

		log("Secret Question answer: a");
		createAnAccountPasswordPage.securityAnswer.sendKeys("a");

		CreateAnAccountWelcomePage createAnAccountWelcomePage = createAnAccountPasswordPage.clickSubmit();

		log("Assert for First user container");
		assertTrue(createAnAccountWelcomePage.findFirstUserContainer(driver), "First use container was not found");

		log("Assert for Congratulation message");
		assertTrue(createAnAccountWelcomePage.findWelcomeMessage(driver), "Congratulation message was not found");

		log("Assert for User welcome message");
		assertTrue(createAnAccountWelcomePage.findCongratulationsMessage("Community" + testIDString), "Welcome message was not found");

		Thread.sleep(5000);

		assertTrue(createAnAccountWelcomePage.findFeaturesContainer(), "Freatures Container not found");
		assertTrue(createAnAccountWelcomePage.findIconMessages(), "Messages icon not found");
		assertTrue(createAnAccountWelcomePage.findIconAppointments(), "Appointments icon not found");
		assertTrue(createAnAccountWelcomePage.findIconBillPayment(), "Bill Payment icon not found");

		createAnAccountWelcomePage.clickMoreFeatures();
		Thread.sleep(5000);

		assertTrue(createAnAccountWelcomePage.findIconForms(), "Forms icon not found");
		assertTrue(createAnAccountWelcomePage.findIconPrescriptions(), "Prescripton icon not found");
		assertTrue(createAnAccountWelcomePage.findIconAskAQuestion(), "AskAQuestion icon not found");

		createAnAccountWelcomePage.clickStartusingPortal();

		CommunityHomePage communityHomePage = new CommunityHomePage(driver);
		communityHomePage.btn_Sign_Out.click();

		/*
		 * // Checking whether was email reaches the mail box boolean foundEmail
		 * = CheckMailCommunity.validateForgotPasswordTrash(gmail,
		 * startEmailSearchDate, patient.getGmailUName(),
		 * gmailMessage.getMessage(), ""); assertTrue(foundEmail,
		 * gmailMessage.getMessage());
		 * 
		 * GmailBot gBot = new GmailBot(); // Setting up Gmail Credentials
		 * gBot.setCredentials(patient.getGmailUName(),
		 * patient.getGmailPassword());
		 * 
		 * // Searching for the link for password reset in the Gmail Trash
		 * folder String sURL = gBot.findTrashEmailLink("",
		 * gmailMessage.getMessage(), practiceUrl.getPractice(), 3, false,
		 * false);
		 * 
		 * // Moving to Signing page log("Movin to Sign In Page: " + sURL);
		 */
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
				"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		assertTrue(communityLoginPage.validatePageElements(),
				"Cannot locate the User ID and Password Inputs and Sign In button. Community sign in page is not loaded or Community is down");

		// Login to Community
		log("Login to Community");
		communityLoginPage.LoginToCommunity(email, password);

		log("Clicking on Messages");
		communityHomePage.icon_Messages.click();

		MessagePage messagePage = new MessagePage(driver);

		// Looking for Welcome message
		log("Looking for the Welcome message");
		messagePage.clickMessage("Welcome to our Patient Portal!");

		log("Looking for subject of the Welcome message");
		MessageDetailPage messageDetailPage = new MessageDetailPage(driver);
		assertTrue(messageDetailPage.isSubjectLocated("Community" + testIDString), "Welcome message was not find");

		communityHomePage.btn_Sign_Out.click();

		log("Summarize of Created Patient");
		log("------------------------------------");
		log("First Name: Community" + testIDString);
		log("Last name: Automation:" + testIDString);
		log("Email address: " + email);
		log("Email address confirm: " + createAnAccountPatientPage.generateEmail(test.getTestEnv(), testIDString));
		log("Phone number: 5555555555");
		log("Phone number type: Mobile");
		log("Birth mont: January");
		log("Birth Day: 01");
		log("Birth Year: 1970");
		log("ZIP code: 94720");
		log("Social Security Number: " + testIDString);
		log("Moving to security setup page");
		log("Setting User ID: " + email);
		log("Setting User Password: intuit123");
		log("Setting User Password Confirm: intuit123");
		log("Selecting Secret Question: What is your favourite sports team?");
		log("Secret Question answer: a");
		log("------------------------------------");
	}

}
