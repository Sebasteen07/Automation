package com.intuit.ihg.product.community.test;

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
import com.intuit.ihg.product.community.page.MyAccount.MyAccountMenuPage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountProfilePage;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.CreatePatient;
import com.intuit.ihg.product.community.utils.GmailMessage;
import com.intuit.ihg.product.community.utils.PracticeUrl;

public class MyAccountProfileTest extends BaseTestNGWebDriver {

	@DataProvider(name = "createPatient")
	public static Iterator<Object[]> fileDataProvider3(ITestContext testContext) throws Exception {

		// Define hashmap
		LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
		classMap.put("TestObject", TestObject.class);
		classMap.put("PracticeUrl", PracticeUrl.class);
		classMap.put("GmailMessage", GmailMessage.class);
		classMap.put("Patient", Patient.class);

		// Filter according to the Environment
		Filter filterTestEnvironment = Filter.equalsIgnoreCase(TestObject.TEST_ENV, IHGUtil.getEnvironmentType().toString());

		// Fetch data from CreatePatientTestData.csv
		Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(CreatePatientTest.class, classMap, "CreatePatientTestData.csv", 0,
						null, filterTestEnvironment);

		return it;
	}

	@Test(enabled = true, groups = { "MyAccount" }, dataProvider = "createPatient")
	public void testMyAccountProfile(TestObject test, PracticeUrl practiceUrl, GmailMessage gmailMessage, Patient patient) throws Exception {

		CreatePatient createPatient = new CreatePatient();

		createPatient.CreatePatientTest(driver, test, practiceUrl, gmailMessage, patient);

		String email = createPatient.email;
		String password = createPatient.password;

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

		log("Clicking on Profile Tab");
		MyAccountMenuPage myAccountMenu = new MyAccountMenuPage(driver);
		myAccountMenu.profile.click();

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		MyAccountProfilePage myAccountProfilePage = new MyAccountProfilePage(driver);

		log("Setting profile data");
		// Setting Profile Data
		myAccountProfilePage.setProfile(driver, test, practiceUrl, gmailMessage, patient);

		assertTrue(myAccountProfilePage.sucessNotification(driver), "Success notification is not displayed");

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		communityHomePage.btn_Sign_Out.click();

		// Navigate to login page
		log("Moving to page: " + communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Loging to Community with username: " + email + " and password: " + password);
		communityLoginPage.LoginToCommunity(email, password);

		log("Clicking on button My Account");
		communityHomePage.btn_My_Account.click();

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Clicking on Profile Tab");
		myAccountMenu.profile.click();

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Asserting Data");
		// Asserting User Saved User Data, values are same for all
		// environments
		assertEquals(myAccountProfilePage.streetAddress.getAttribute("value"), "Somewhere1");
		assertEquals(myAccountProfilePage.city.getAttribute("value"), "Boston");
		assertEquals(myAccountProfilePage.state.getAttribute("value"), "NE");
		assertEquals(myAccountProfilePage.zip.getAttribute("value"), "74601");
		assertEquals(myAccountProfilePage.phoneNumber.getAttribute("value"), "(456) 123-3442");
		assertEquals(myAccountProfilePage.phoneType.getAttribute("value"), "HOME");
		assertEquals(myAccountProfilePage.sex.getAttribute("value"), "M");
		assertEquals(myAccountProfilePage.maritalStatus.getAttribute("value"), "SINGLE");
		assertEquals(myAccountProfilePage.preferredCommunication.getAttribute("value"), "HOME_PHONE");
		assertEquals(myAccountProfilePage.preferredLanguage.getAttribute("value"), "en");
		assertEquals(myAccountProfilePage.race.getAttribute("value"), "W");
		assertEquals(myAccountProfilePage.ethnicity.getAttribute("value"), "DTA");

		log("Signing out of the Community");
		communityHomePage.btn_Sign_Out.click();

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

	}

}
