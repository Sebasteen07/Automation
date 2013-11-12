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
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.ForgotPassword.ResetPasswordEnterNewPasswordPage;
import com.intuit.ihg.product.community.page.ForgotPassword.ResetPasswordEnterUserIDPage;
import com.intuit.ihg.product.community.page.ForgotPassword.ResetPasswordSignInNewPassword;
import com.intuit.ihg.product.community.page.ForgotPassword.ResetPasswordSummaryPage;
import com.intuit.ihg.product.community.page.ForgotUserId.ForgotUserIdAnswerQuestionPage;
import com.intuit.ihg.product.community.page.ForgotUserId.ForgotUserIdEnterEmailAddressPage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountMenuPage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountProfilePage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountSecurityQuestionPage;
import com.intuit.ihg.product.community.utils.Community;
import com.intuit.ihg.product.community.utils.CommunityConstants;
import com.intuit.ihg.product.community.utils.CommunityTestData;
import com.intuit.ihg.product.community.utils.CommunityUtils;

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
		Thread.sleep(5000);
		Assert.assertEquals(
				"### It seems Community may be down at this moment .... Community Title what we ",
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
						.trim());
		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(5000);
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
		Thread.sleep(5000);
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
		Thread.sleep(5000);
		Assert.assertEquals(
				"### It seems Community may be down at this moment .... Community Title what we ",
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
						.trim());
		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(5000);
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

	/**
	 * @Author:yvaddavalli
	 * @Date:9/6/2013
	 * @StepsToReproduce: Launch Community. Click on Forgot password link and
	 *                    enter user ID. Create new random password and enter.
	 *                    Complete flow and check Gmail.Get the link from Gmail
	 *                    and enter User Id and password and login to Community
	 *                    using new password.
	 * 
	 */
	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityForgotPassword() throws Exception {

		log("Test Case: testCommunityForgotPassword");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Community community = new Community();
		CommunityTestData testcasesData = new CommunityTestData(community);

		log("step 2: Clean the Gmail Inbox");
		GmailBot gbot = new GmailBot();
		String sSubject = String
				.format(CommunityConstants.EMAIL_ForgotPassword_SUBJECT.trim());
		gbot.deleteMessagesFromInbox(testcasesData.getGmailUName(),
				testcasesData.getGmailPassword(), sSubject);

		log("step 3: Launching Community. URL: " + testcasesData.getUrl());
		CommunityLoginPage loginPage = new CommunityLoginPage(driver,
				testcasesData.getUrl());
		Thread.sleep(5000);
		Assert.assertEquals(
				"### It seems Community may be down at this moment .... Community Title what we ",
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
						.trim());

		log("step 4: Click Forgot Password link on Login Page ");
		ResetPasswordEnterUserIDPage step1 = loginPage.clickForgotPassword();

		log("step 5: Enter User ID :" + testcasesData.getForgotUserName());
		ResetPasswordEnterNewPasswordPage step2 = step1
				.enterUserID(testcasesData.getForgotUserName());

		log("step 6: Enter answer for security question and Create new random password and enter");
		String answer = "a";
		String randompassword = (CommunityConstants.ForgotPassword + CommunityUtils
				.createRandomNumber());

		log("step 7: Entered New Random Passoword :" + randompassword);
		ResetPasswordSummaryPage step3 = step2.resetPassword(answer,
				randompassword);

		log("step 8: Click on Submit and complete flow");
		step3.confirmPasswordReset();

		log("step 9: Checking Gmail");

		log("step 10: subject of mail is " + sSubject);
		String sURL = gbot.findInboxEmailLink(testcasesData.getGmailUName(),
				testcasesData.getGmailPassword(), sSubject,

				CommunityConstants.TextInForgotPasswordEmailLink, 20, false,
				false);

		if (sURL.isEmpty()) {

			log("###Couldn't get URL from email");
			Assert.fail("NO email found in the Gmail Inbox");

		} else {
			log("step 11: Got the email and clicking on the link in the email: "
					+ sURL);
			driver.navigate().to(sURL);
		}

		ResetPasswordSignInNewPassword step4 = new ResetPasswordSignInNewPassword(
				driver);

		log("step 12: Entering the UserID and New Random Password and logging in");
		CommunityHomePage homepage = step4.resetPassword(
				testcasesData.getForgotUserName(), randompassword);
		Thread.sleep(5000);
		log("step 13: Checking Messages Icon is there on the Home");
		homepage.isViewallmessagesLinkPresent(driver);

		log("End of Test. Testcase Passed");
	}

	/**
	 * @Author:yvaddavalli
	 * @Date:9/6/2013
	 * @StepsToReproduce: Launch Community. Click on Forgot User name link and
	 *                    enter email address . Enter answer for security
	 *                    Question. Complete flow and check Gmail.Get the link
	 *                    from Gmail and enter User Id and password and login to
	 *                    Community .
	 * 
	 */
	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityForgotUserID() throws Exception {

		log("Test Case: testCommunityForgotUserID");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Community community = new Community();
		CommunityTestData testcasesData = new CommunityTestData(community);

		log("step 2: Clean the Gmail Inbox");
		GmailBot gbot = new GmailBot();
		String sSubject = String
				.format(CommunityConstants.EMAIL_ForgotUserName_SUBJECT.trim());
		gbot.deleteMessagesFromInbox(testcasesData.getGmailUName(),
				testcasesData.getGmailPassword(), sSubject);

		log("step 3: Launching Community. URL: " + testcasesData.getUrl());
		CommunityLoginPage loginPage = new CommunityLoginPage(driver,
				testcasesData.getUrl());
		Thread.sleep(5000);
		Assert.assertEquals(
				"### It seems Community may be down at this moment .... Community Title what we ",
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
						.trim());

		log("step 4: Click Forgot User Name  link on Login Page ");
		ForgotUserIdEnterEmailAddressPage step1 = loginPage.clickForgotUserID();

		log("step 5: Enter Email ID :" + testcasesData.getUserName());
		ForgotUserIdAnswerQuestionPage step2 = step1.enterEmail(testcasesData
				.getForgotUserName());

		log("step 6: Enter answer for security question and complete flow ");
		String answer = "a";
		step2.completeForgotUserFlow(answer);

		log("step 7: Checking Gmail ");

		log("step 8: subject of mail is " + sSubject);

		// Verifying Gmail

		String link = "https://";
		String sURL = gbot.findInboxEmailLink(testcasesData.getGmailUName(),
				testcasesData.getGmailPassword(), sSubject,

				link, 20, false, false);

		if (sURL.isEmpty()) {

			log("###Couldn't get URL from email");
			Assert.fail("NO email found in the Gmail Inbox");

		} else {
			log("step 9: Got the email and clicking on the link in the email: "
					+ sURL);
			driver.navigate().to(sURL);
		}

		log("step 10 : Login to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(5000);
		assertTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");
		log("End of the Test: Test Passed");
	}

}