package com.intuit.ihg.product.community.test;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.ExcelSheetUtil;
import com.intuit.ihg.common.utils.dataprovider.Filter;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountEmailAddressPage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountMenuPage;
import com.intuit.ihg.product.community.utils.CheckMailCommunity;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.CreatePatient;
import com.intuit.ihg.product.community.utils.GmailCommunity;
import com.intuit.ihg.product.community.utils.GmailMessage;
import com.intuit.ihg.product.community.utils.PracticeUrl;

public class MyAccountEmailAddressTest extends BaseTestNGWebDriver {

	@DataProvider(name = "emailAddress")
	public static Iterator<Object[]> fileDataProvider3(ITestContext testContext) throws Exception {

		// Define hashmap
		LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
		classMap.put("TestObject", TestObject.class);
		classMap.put("PracticeUrl", PracticeUrl.class);
		classMap.put("GmailMessage", GmailMessage.class);
		classMap.put("Patient", Patient.class);

		// Filter according to the Environment
		Filter filterTestEnvironment = Filter.equalsIgnoreCase(TestObject.TEST_ENV, IHGUtil.getEnvironmentType().toString());

		// Fetch data from CreatePatientTestData
		Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(CreatePatientTest.class, classMap, "MyAccountEmailAddressTestData.csv", 0,
						null, filterTestEnvironment);

		return it;
	}

	@Test(enabled = false, groups = { "MyAccount" }, dataProvider = "emailAddress")
	public void testMyAccountEmailAddress(TestObject test, PracticeUrl practiceUrl, GmailMessage gmailMessage, Patient patient) throws Exception {

		GmailCommunity gmail1 = new GmailCommunity(patient.getGmailUName(), patient.getGmailPassword());
		Date startEmailSearchDate1 = new Date();

		CreatePatient createPatient = new CreatePatient();

		createPatient.CreatePatientTest(driver, test, practiceUrl, gmailMessage, patient);

		String email = createPatient.email;
		String password = createPatient.password;
		// Creating email for change of the email created in the
		// createPatientMethod
		String newEmailString = email;
		// Adding letter C at the end of ENV type in the email)

		log("Email: " + email);
		newEmailString = newEmailString.replace(test.getTestEnv(), test.getTestEnv() + "C");
		log("New Email: " + newEmailString);
		CommunityUtils communityUtils = new CommunityUtils();

		CommunityLoginPage communityLoginPage = new CommunityLoginPage(driver);

		// Navigate to login page
		log("Moving to page: " + communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));

		// Look for Error Messages
		log("Looking for error messages");
		String errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Loging to Community with username: " + email + " and password: " + password);
		communityLoginPage.LoginToCommunity(email, password);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Clicking on button My Account");
		CommunityHomePage communityHomePage = new CommunityHomePage(driver);
		communityHomePage.btn_My_Account.click();

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Clicking on Email Tab");
		MyAccountMenuPage myAccountMenu = new MyAccountMenuPage(driver);
		myAccountMenu.email.click();

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		MyAccountEmailAddressPage myAccountEmailAddressPage = new MyAccountEmailAddressPage(driver);
		myAccountEmailAddressPage.setEmail(driver, newEmailString, password);

		log("Clicking on Save button");
		// Clicking on Save button
		myAccountEmailAddressPage.btnSaveChanges.click();

		assertTrue(myAccountEmailAddressPage.sucessNotification(driver), "Success notification is not displayed");

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Signing off the Community
		log("Signing of the Community");
		communityHomePage.btn_Sign_Out.click();

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Logging to Community with new Email
		log("Logging to Community with new Email");
		log("Email: " + newEmailString + " Password: " + password);
		communityLoginPage.LoginToCommunity(newEmailString, password);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);
		log("Logging successs");
		log("Signing Out of the Community");
		communityHomePage.btn_Sign_Out.click();

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

	/*	boolean foundEmail1 = CheckMailCommunity.validateForgotPasswordTrash(gmail1, startEmailSearchDate1, patient.getGmailUName(),
						gmailMessage.getMessage2(), "");

		assertTrue(foundEmail1, gmailMessage.getMessage2()); */

	}
}
