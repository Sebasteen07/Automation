package com.intuit.ihg.product.community.test;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.entities.BillPay;
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.Practice;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.ExcelSheetUtil;
import com.intuit.ihg.common.utils.dataprovider.Filter;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.BillPay.BillPayConfirmationPage;
import com.intuit.ihg.product.community.page.BillPay.BillPayPaymentDetailsPage;
import com.intuit.ihg.product.community.page.BillPay.BillPayChooseYourPractice;
import com.intuit.ihg.product.community.page.solutions.Messages.MessageDetailPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessagePage;
import com.intuit.ihg.product.community.utils.CheckMailCommunity;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.GmailCommunity;
import com.intuit.ihg.product.community.utils.GmailMessage;
import com.intuit.ihg.product.community.utils.PracticeUrl;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.onlinebillpay.OnlineBillPayDetailPage;
import com.intuit.ihg.product.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.intuit.ihg.product.practice.page.onlinebillpay.OnlineBillPayVerifyPage;

public class BillPayTest extends BaseTestNGWebDriver {

	@DataProvider(name = "appointmentRequest")
	public static Iterator<Object[]> fileDataProvider3(ITestContext testContext) throws Exception {

		// Define hashmap
		LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
		classMap.put("TestObject", TestObject.class);
		classMap.put("Patient", Patient.class);
		classMap.put("BillPay", BillPay.class);
		classMap.put("PracticeUrl", PracticeUrl.class);
		classMap.put("Practice", Practice.class);
		classMap.put("GmailMessage", GmailMessage.class);

		// Filter
		Filter filterTestEnvironment = Filter.equalsIgnoreCase(TestObject.TEST_ENV, TestConfig.getTestEnv());

		// Fetch data from CommunityAcceptanceTestData.csv
		Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(BillPayTest.class, classMap, "BillPayTestData.csv", 0, null,
						filterTestEnvironment);

		return it;
	}

	@Test(enabled = false, groups = { "AcceptanceTests" }, dataProvider = "appointmentRequest")
	public void billPayTest(TestObject test, Patient patient, BillPay billPay, PracticeUrl practiceUrl, Practice practice,
					GmailMessage gmailMessage) throws Exception {

		Random randomGenerator = new Random();

		int randomAmount = randomGenerator.nextInt(10000);
		int randomTestID = 100000 + randomGenerator.nextInt(900000);
		String randomAccountNumber = String.valueOf(100000 + randomGenerator.nextInt(900000));

		// Setting up date for Gmail search
		Date startEmailSearchDate1 = new Date();

		GmailCommunity gmail1 = new GmailCommunity(patient.getGmailUName(), patient.getGmailPassword());

		CommunityUtils communityUtils = new CommunityUtils();
		CommunityLoginPage communityLoginpage = new CommunityLoginPage(driver);

		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		String errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		assertTrue(communityLoginpage.validatePageElements(),
						"Cannot locate the User ID and Password Inputs and Sign In button. Community sign in page is not loaded or Community is down");

		communityLoginpage.LoginToCommunity(patient.getUserName(), patient.getPassword());
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		CommunityHomePage homePage = new CommunityHomePage(driver);
		homePage.link_Make_A_Payment.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		BillPayChooseYourPractice billPayChooseYourPractice = new BillPayChooseYourPractice(driver);
		// Selecting Practice Name
		log("Selecting Practice with name: " + practice.getPracName());
		billPayChooseYourPractice.SelectPractice(practice.getPracName());
		billPayChooseYourPractice.btnContinue.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		BillPayPaymentDetailsPage billPayPaymentDetailsPage = new BillPayPaymentDetailsPage(driver);

		// Selecting Location name
		log("Selecting Location with name: " + practice.getPracName());
		billPayPaymentDetailsPage.SelectLocation(practice.getPracName());
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Setting up payment details
		log("Setting up payment details");

		log("Account number: " + randomAccountNumber);
		billPayPaymentDetailsPage.accountNumber.sendKeys(randomAccountNumber);

		log("Phone number: " + billPay.getPhoneNumber());
		billPayPaymentDetailsPage.phoneNumber.sendKeys(billPay.getPhoneNumber());

		log("Payment ammount: " + String.valueOf(randomAmount));
		billPayPaymentDetailsPage.amount.sendKeys(String.valueOf(randomAmount));

		log("Description and comments: " + billPay.getDescription());
		billPayPaymentDetailsPage.setComment("TestName " + randomTestID);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Choosing new credit card
		log("Choosing new credit card");
		billPayPaymentDetailsPage.creditCard.click();

		// Setting up credit card details
		log("Setting up credit card details");

		log("Card name: " + "TestName " + randomTestID);
		billPayPaymentDetailsPage.cardName.sendKeys("TestName " + randomTestID);

		log("Card number: " + billPay.getCardNumber());
		billPayPaymentDetailsPage.cardNumber.sendKeys(billPay.getCardNumber());

		log("Card CCV: " + billPay.getCardCcv());
		billPayPaymentDetailsPage.cardCVV.sendKeys(billPay.getCardCcv());

		log("Card Expiration month: " + billPay.getCardExpirationMonth());
		billPayPaymentDetailsPage.cardExpirationMonth.sendKeys(billPay.getCardExpirationMonth());

		log("Card Expiration year: " + billPay.getCardExpirationYear());
		billPayPaymentDetailsPage.cardExpirationYear.sendKeys(billPay.getCardExpirationYear());

		log("Card address: " + billPay.getCardAddress1());
		billPayPaymentDetailsPage.cardAddress1.sendKeys(billPay.getCardAddress1());

		log("Card Zip: " + billPay.getCardZip());
		billPayPaymentDetailsPage.cardZip.sendKeys(billPay.getCardZip());

		// Clicking continue
		log("Clicking continue");
		billPayPaymentDetailsPage.btnContinue.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Make a payment Confirmation page
		log("Payment Confirm");
		BillPayConfirmationPage paymentConfirmation = new BillPayConfirmationPage(driver);
		paymentConfirmation.btnMakeAPayment.click();
		
		waitForPageTitle(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, 60);
		
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		assertTrue(homePage.checkSuccesNotification(driver), "BillPay notification not found");
		homePage.btn_Sign_Out.click();

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		boolean foundEmail1 = CheckMailCommunity.validateForgotPasswordTrash(gmail1, startEmailSearchDate1, patient.getGmailUName(),
						gmailMessage.getMessage(), "");
		verifyTrue(foundEmail1, gmailMessage.getMessage());

		// Login to Practice Portal
		log("Login to Practice Portal with username: " + practice.getDocUName() + " and password: " + practice.getDocPassword());
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, IHGUtil.getEnvironmentType().toString(), null);
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		PracticeHomePage practiceHome = practiceLogin.login(practice.getDocUName(), practice.getDocPassword());
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Moving to Online Bill Pay
		log("Click Online Bill Pay tab");
		OnlineBillPaySearchPage onlineBillpaysearch = practiceHome.clickOnlineBillPayTab();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Searching for BillPay with specific account number and which
		// was payed today
		log("Setting search for current day");
		onlineBillpaysearch.searchForBillPayToday();
		log("Searching for BillPay with account number: " + randomAccountNumber);
		onlineBillpaysearch.searchForBillPayment(randomAccountNumber);
		log("Clicking on the result of the search");

		OnlineBillPayDetailPage onlineBillPayDetailPage = onlineBillpaysearch.getBillPayDetails();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		onlineBillPayDetailPage.setFrame();
		onlineBillPayDetailPage.CommunicateBillPay("Test Bill Pay subject " + randomTestID, "Test Bill Pay message " + randomTestID);
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		OnlineBillPayVerifyPage onlineBillPayVerifyPage = new OnlineBillPayVerifyPage(driver);
		assertTrue(onlineBillPayVerifyPage.MessageSentNotification(), "Success notification is not displayed");
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Log of the Practice Portal
		log("Logout of Practice Portal");
		practiceHome.logOut();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Moving back to Community
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		assertTrue(communityLoginpage.validatePageElements(),
						"Cannot locate the User ID and Password Inputs and Sign In button. Community sign in page is not loaded or Community is down");

		communityLoginpage.LoginToCommunity(patient.getUserName(), patient.getPassword());
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Moving to the Messages
		homePage.icon_Messages.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		MessagePage messagePage = new MessagePage(driver);

		// Searching for the email with correct subject
		log("Searching for email with subject: " + "Test Bill Pay subject " + randomTestID);
		messagePage.clickMessage("Test Bill Pay subject " + randomTestID);
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Validating whether the message is the correct one
		MessageDetailPage messageDetails = new MessageDetailPage(driver);
		log("Veryfing match of the Message subject: " + "Test Bill Pay message " + randomTestID);
		assertTrue(messageDetails.isSubjectLocated("Test Bill Pay message " + randomTestID), "Message with correct Subject was not found");
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		homePage.btn_Sign_Out.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");
	}

}
