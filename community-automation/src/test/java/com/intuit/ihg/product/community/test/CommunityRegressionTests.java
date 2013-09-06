package com.intuit.ihg.product.community.test;

import java.util.Random;

import junit.framework.Assert;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountMenuPage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountProfilePage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountSecurityQuestionPage;
import com.intuit.ihg.product.community.page.RxRenewal.RxRenewalChoosePharmacy;
import com.intuit.ihg.product.community.page.RxRenewal.RxRenewalChoosePrescription;
import com.intuit.ihg.product.community.page.RxRenewal.RxRenewalSearchDoctor;
import com.intuit.ihg.product.community.page.solutions.Messages.MessageDetailPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessagePage;
import com.intuit.ihg.product.community.utils.Community;
import com.intuit.ihg.product.community.utils.CommunityConstants;
import com.intuit.ihg.product.community.utils.CommunityTestData;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalConfirmCommunication;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalDetailPage;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalDetailPageConfirmation;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalSearchPage;

public class CommunityRegressionTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	/**
	 * @Author:yvaddavalli
	 * @Date:8/16/2013
	 * @StepsToReproduce: Launch Community and Login.Clik on My Account link on
	 *                    Home Page and click on profile. Update Street address
	 *                    and save profile. Log out and re login and check if
	 *                    the savings are there.
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityMyAccountProfile() throws Exception {

		log("Test Case: testCommunityMyAccountProfile");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Community community = new Community();
		CommunityTestData testcasesData = new CommunityTestData(community);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Community URL and Check the Page Title");

		CommunityLoginPage loginPage = new CommunityLoginPage(driver,
				testcasesData.getUrl());

		Assert.assertEquals(
				"### It seems Community may be down at this moment .... Community Title what we ",
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
						.trim());
		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());

		assertTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 4: Clicking on My Account link on Home Page");
		MyAccountMenuPage myAccountMenu = homePage.clickMyAccount();

		log("step 5: Clicking on Profile Tab in My Account Page");
		MyAccountProfilePage myAccountProfile = myAccountMenu
				.clickMyAccountProfile();

		String randomstreet = ("Street# " + CommunityUtils.createRandomNumber());

		log("step 6: Updating Profile");
		myAccountProfile.updateProfile(randomstreet);

		log("step 7: Checking for Successful Notification");
		assertTrue(myAccountProfile.sucessNotification(driver),
				"Success notification is not displayed");

		log("step 8:Logout of Community");
		homePage.logOutCommunity();
		// Re login and check if the savings are there

		log("step 9: Re login and check if the savings are there");

		homePage = loginPage.LoginToCommunity(testcasesData.getUserName(),
				testcasesData.getPassword());

		assertTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 10: Clicking on My Account link on Home Page");
		myAccountMenu = homePage.clickMyAccount();

		log("step 11: Clicking on Profile Tab in My Account Page");
		myAccountProfile = myAccountMenu.clickMyAccountProfile();

		assertTrue(myAccountProfile.checkProfileSave(randomstreet),
				"Street address did not saved");
		log("step 12: profile saved correctly");

		log("step 13:Logout of Community");
		homePage.logOutCommunity();

		log("Test case passed  End of Test");

	}

	/**
	 * @Author:yvaddavalli
	 * @Date:8/16/2013
	 * @StepsToReproduce: Launch Community and Login.Clik on My Account link on
	 *                    Home Page and click on Security Question. Update
	 *                    Question and answer and save. Assert successful
	 *                    message. Log out of Community
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityMyAccountSecurityQuestion() throws Exception {

		log("Test Case: testCommunityMyAccountSecurityQuestion");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Community community = new Community();
		CommunityTestData testcasesData = new CommunityTestData(community);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Community URL and Check the Page Title");

		CommunityLoginPage loginPage = new CommunityLoginPage(driver,
				testcasesData.getUrl());

		Assert.assertEquals(
				"### It seems Community may be down at this moment .... Community Title what we ",
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
						.trim());
		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());

		assertTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 4: Clicking on My Account link on Home Page");
		MyAccountMenuPage myAccountMenu = homePage.clickMyAccount();

		log("step 5: Clicking on Security Question Tab in My Account Page");
		MyAccountSecurityQuestionPage myAccountSecurity = myAccountMenu
				.clickMyAccountSecurityQuestion();

		String randomAnswer = ("My Hero is:" + CommunityUtils
				.createRandomNumber());

		log("step 6: Updating Security Question and Answer");
		myAccountSecurity.areElementsDisplayed();
		myAccountSecurity.setSecuityQuestion(
				CommunityConstants.MyAccountSecurityQuestion, randomAnswer);

		log("step 7: Checking for Successful Notification");
		assertTrue(myAccountSecurity.sucessNotification(driver),
				"Success notification is not displayed");

		log("step 8:Logout of Community");
		homePage.logOutCommunity();
		log("Test case passed  End of Test");
	}

}