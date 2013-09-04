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
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.Practice;
import com.intuit.ihg.common.entities.RxRenewal;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.ExcelSheetUtil;
import com.intuit.ihg.common.utils.dataprovider.Filter;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.RxRenewal.RxRenewalChooseDoctor;
import com.intuit.ihg.product.community.page.RxRenewal.RxRenewalChoosePharmacy;
import com.intuit.ihg.product.community.page.RxRenewal.RxRenewalChoosePrescription;
import com.intuit.ihg.product.community.page.RxRenewal.RxRenewalSearchDoctor;
import com.intuit.ihg.product.community.page.solutions.Messages.MessageDetailPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessagePage;
import com.intuit.ihg.product.community.utils.CheckMailCommunity;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.GmailCommunity;
import com.intuit.ihg.product.community.utils.GmailMessage;
import com.intuit.ihg.product.community.utils.PracticeUrl;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalConfirmCommunication;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalDetailPage;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalDetailPageConfirmation;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalSearchPage;

public class RxRenewalTest extends BaseTestNGWebDriver {

	@DataProvider(name = "rxrenewall")
	public static Iterator<Object[]> fileDataProvider3(ITestContext testContext) throws Exception {

		// Define hashmap
		LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
		classMap.put("TestObject", TestObject.class);
		classMap.put("Patient", Patient.class);
		classMap.put("RxRenewal", RxRenewal.class);
		classMap.put("PracticeUrl", PracticeUrl.class);
		classMap.put("Practice", Practice.class);
		classMap.put("GmailMessage", GmailMessage.class);

		Filter filterTestEnvironment = Filter.equalsIgnoreCase(TestObject.TEST_ENV, IHGUtil.getEnvironmentType().toString());

		Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(RxRenewalTest.class, classMap, "RxRenewalTestData.csv", 0, null,
						filterTestEnvironment);

		return it;
	}

	@Test(enabled = false, groups = { "AcceptanceTests" }, dataProvider = "rxrenewall")
	public void rxRenewalTest(TestObject test, Patient patient, RxRenewal rxRenewal, PracticeUrl practiceUrl, Practice practice,
					GmailMessage gmailMessage) throws Exception {

		CommunityLoginPage communityLoginPage = new CommunityLoginPage(driver);
		Random randomGenerator = new Random();

		// Creating random number which is used for identification of the
		// RxRenewal
		int randomTestID = 100000 + randomGenerator.nextInt(900000);
		String randomRxNumber = String.valueOf(100000 + randomGenerator.nextInt(900000));

		// Setting up date for Gmail search
		Date startEmailSearchDate1 = new Date();

		GmailCommunity gmail1 = new GmailCommunity(patient.getGmailUName(), patient.getGmailPassword());
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

		// Loging to Community
		log("Loging to Community with username: " + patient.getUserName() + " and password: " + patient.getPassword());
		communityLoginPage.LoginToCommunity(patient.getUserName(), patient.getPassword());
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		CommunityHomePage communityHomePage = new CommunityHomePage(driver);
		communityHomePage.link_Renewal_Request.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		RxRenewalChoosePrescription renewalChoosePrescription = new RxRenewalChoosePrescription(driver);

		if (renewalChoosePrescription.locateEnterNewPrescription() == true) {
			renewalChoosePrescription.SelectNewPrescription();
		}
		renewalChoosePrescription.fillPrescription(rxRenewal.getRxName(), randomRxNumber, rxRenewal.getDosage());
		renewalChoosePrescription.btnContinue.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);
		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		RxRenewalChooseDoctor renewalChooseDoctor = new RxRenewalChooseDoctor(driver);
		renewalChooseDoctor.searchForDifferentDoctor.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		RxRenewalSearchDoctor renewalSearchDoctor = new RxRenewalSearchDoctor(driver);
		renewalSearchDoctor.selectDoctor(rxRenewal.getDoctor());
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		RxRenewalChoosePharmacy rxRenewalChoosePharmacy = new RxRenewalChoosePharmacy(driver);
		rxRenewalChoosePharmacy.selectPharmacy(rxRenewal.getZipOrStateToSearch());
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);
		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		assertTrue(communityHomePage.checkSuccesNotification(driver), "Success notification is not displaed, BillPay procedure probably failed");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Signing of the Community
		communityHomePage.btn_Sign_Out.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

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

		// Selecting RxRenewal
		log("Clicking on RxRenewal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome.clickonRxRenewal();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Searching for new Renewals which were created today
		log("Sarching for Renewals");
		rxRenewalSearchPage.searchForRxRenewalToday();

		// Filling up the RxRenewal
		RxRenewalDetailPage rxRenewalDetailPage = rxRenewalSearchPage.getRxRenewalDetails();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		rxRenewalDetailPage.setFrame();
		log("Specifiyng RxRenwal");
		rxRenewalDetailPage.prescribe("1", "12");
		rxRenewalDetailPage.setMessageFrom(practice.getDocUName());
		rxRenewalDetailPage.setSubject("Text RX message " + randomTestID);
		rxRenewalDetailPage.setMessageBody("Text RX message " + randomTestID);

		// Clicking on continue
		log("Clicking on continue");
		RxRenewalDetailPageConfirmation rxRenewalDetailPageConfirmation = rxRenewalDetailPage.clickCommunicateAndProcessRxRenewal();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);
		rxRenewalDetailPageConfirmation.clickCallInTheRx();

		// Finishing RxRenewal
		log("Finishing RxRenewal");
		RxRenewalConfirmCommunication renewalConfirmCommunication = rxRenewalDetailPageConfirmation.clickContinue();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Signing of the Patient Portal
		log("Signing of the Patient Portal");
		practiceHome.logOut();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Checking Gmail
		boolean foundEmail1 = CheckMailCommunity.validateForgotPasswordTrash(gmail1, startEmailSearchDate1, patient.getGmailUName(),
						gmailMessage.getMessage(), "");
		verifyTrue(foundEmail1, gmailMessage.getMessage());

		// Moving to Community Login Page
		log("Moving to page: " + communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
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

		// Loging to Community
		log("Loging to Community with username: " + patient.getUserName() + " and password: " + patient.getPassword());
		communityLoginPage.LoginToCommunity(patient.getUserName(), patient.getPassword());
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		communityHomePage.link_View_All_Messages.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		MessagePage messagePage = new MessagePage(driver);

		// Searching for the email with correct subject
		log("Searching for email with subject: Text RX message " + randomTestID);
		messagePage.clickMessage("Text RX message " + randomTestID);
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Validating whether the message is the correct one
		MessageDetailPage messageDetails = new MessageDetailPage(driver);
		log("Veryfing match of the Message subject: Text RX message " + randomTestID);
		assertTrue(messageDetails.isSubjectLocated("Text RX message " + randomTestID), "Message with correct Subject was not found");
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		communityHomePage.btn_Sign_Out.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);
	}
}
