package com.intuit.ihg.product.community.test;

import java.util.Iterator;
import java.util.LinkedHashMap;

import junit.framework.Assert;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.common.utils.dataprovider.ExcelSheetUtil;
import com.intuit.ihg.common.utils.dataprovider.Filter;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.PracticeUrl;

public class LoginTest extends BaseTestNGWebDriver {

	/**
	 * @Author:Jakub Calabek
	 * @Date:3.5.2013
	 * @User Story ID in Rally : TA18004 Signing in the Community Home page and
	 *       then Signing of the Home page
	 */

	@DataProvider(name = "LoginToCommunityData")
	public static Iterator<Object[]> fileDataProviderLogin(
			ITestContext testContext) throws Exception {

		// Define hashmap
		LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
		classMap.put("TestObject", TestObject.class);
		classMap.put("Patient", Patient.class);
		classMap.put("PracticeUrl", PracticeUrl.class);

		// Filter is set on DEMO environment
		Filter filterTestEnvironment = Filter.equalsIgnoreCase(
				TestObject.TEST_ENV, TestConfig.getTestEnv());

		// Fetch data from CommunityAcceptanceTestData.csv
		Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(
				LoginTest.class, classMap, "LoginTestData.csv", 0, null,
				filterTestEnvironment);

		return it;
	}

	@Test(enabled = false, groups = { "LoginTest" }, dataProvider = "LoginToCommunityData")
	public void testLoginToCommunity(TestObject test, Patient patient,
			PracticeUrl practiceUrl) throws Exception {
		CommunityLoginPage communityloginpage = new CommunityLoginPage(driver);

		// Printing Test Method and Test Title
		log(test.getTestMethod());
		log(test.getTestTitle());

		// Navigate to login page
		CommunityUtils communityUtils = new CommunityUtils();
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(),
				test.getTestEnv()));
		log("Checking Page Title");
		Assert.assertEquals(
				"### It seems Community may be down at this moment .... Community Title what we ",
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
						.trim());

		// Waiting up to 30 seconds for page to load
		waitForPageTitle("Intuit Health", 30);

		// Logging into the portal with specified credentials
		log("Logging on with User ID: " + patient.getUserName()
				+ " and Password: " + patient.getPassword());

		assertTrue(
				communityloginpage.validatePageElements(),
				"Cannot locate the User ID and Password Inputs and Sign In button. Community sign in page is not loaded or Community is down");

		// Filling up the credentials and clicking on Login
		communityloginpage.LoginToCommunity(patient.getUserName(),
				patient.getPassword());

		// Look for Error Messages --Example: If the user ID or Password is
		// invalid there will be error

		String errorMessage = communityUtils.getTextError(driver);
		Assert.assertEquals("### Error While Login: ", "", errorMessage);

		log("Checking Page Title After Login ");
		assertTrue(CommunityUtils.validatePageTitle(driver,
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
				"Page Title does not match the expected one: "
						+ CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		CommunityHomePage communityhomepage = new CommunityHomePage(driver);

		// Signing of the portal
		communityhomepage.btn_Sign_Out.click();
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver,
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
				"Page Title does not match the expected one: "
						+ CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");
		assertTrue(communityloginpage.btn_Sign_In.isDisplayed(),
				"Sign In button is not displayed, Login page was not reached");
	}
}