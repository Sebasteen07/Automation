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
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.ForgotPassword.ResetPasswordEnterUserIDPage;
import com.intuit.ihg.product.community.page.ForgotPassword.ResetPasswordEnterNewPasswordPage;
import com.intuit.ihg.product.community.page.ForgotPassword.ResetPasswordSignInNewPassword;
import com.intuit.ihg.product.community.utils.CheckMailCommunity;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.GmailCommunity;
import com.intuit.ihg.product.community.utils.PracticeUrl;

/**
 * @Author:Jakub Calabek
 * @Date:3.5.2013
 * @User Story ID in Rally : TA18007
 * @StepsToReproduce: Forgot User Password procedure from the Community Login
 *                    page. Specifying User ID, Secure question and setting up
 *                    new user password.
 */

public class ForgotUserPasswordTest extends BaseTestNGWebDriver {

	@DataProvider(name = "ForgotPassword")
	public static Iterator<Object[]> fileDataProvider2(ITestContext testContext) throws Exception {

		// Define hashmap
		LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
		classMap.put("TestObject", TestObject.class);
		classMap.put("Patient", Patient.class);
		classMap.put("PracticeUrl", PracticeUrl.class);

		Filter filterTestEnvironment = Filter.equalsIgnoreCase(TestObject.TEST_ENV, IHGUtil.getEnvironmentType().toString());

		// Fetch data from CommunityAcceptanceTestData.csv
		Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(ForgotUserPasswordTest.class, classMap,
						"ForgotUserPasswordTestData.csv", 0, null, filterTestEnvironment);

		return it;
	}

	@Test(enabled = false, groups = { "AcceptanceTests" }, dataProvider = "ForgotPassword")
	public void testResetPassword(TestObject test, Patient patient, PracticeUrl practiceUrl) throws Exception {

		// Printing Test Method and Test Title
		log(test.getTestMethod());
		log(test.getTestTitle());

		// Start email search date
		log("Logging to Gmail with credentials: " + patient.getGmailUName() + " " + patient.getGmailPassword());
		Date startEmailSearchDate = new Date();
		GmailCommunity gmail = new GmailCommunity(patient.getGmailUName(), patient.getGmailPassword());

		// Community Login Page and Click on Cant access your account
		CommunityLoginPage communityloginpage = new CommunityLoginPage(driver);
		CommunityUtils communityUtils = new CommunityUtils();

		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		waitForPageTitle("Intuit Health", 30);
		log("Clicking on Cant Acces Your Account on the Login Page");
		communityloginpage.link_Cant_Acces_Your_Account.click();

		ResetPasswordEnterUserIDPage newpasswordstep1 = new ResetPasswordEnterUserIDPage(driver);
		waitForPageTitle("Intuit Health", 30);

		// Set User ID and click on continue

		log("Setting the User ID: " + patient.getUserName());
		newpasswordstep1.User_ID.sendKeys(patient.getUserName());
		log("Clicking on Continue");
		newpasswordstep1.btn_Continue.click();

		// Insert Answer, New Password, Re-Enter New Password and click on
		// Email-me

		waitForPageTitle("Intuit Health", 30);
		ResetPasswordEnterNewPasswordPage newpasswordstep2 = new ResetPasswordEnterNewPasswordPage(driver);

		log("Filling the Security Question: " + patient.getSecAnswer());
		newpasswordstep2.Answer.sendKeys(patient.getSecAnswer());

		log("Setting new User Password: " + patient.getPassword());
		newpasswordstep2.New_Password_1.sendKeys(patient.getPassword());
		newpasswordstep2.New_Password_2.sendKeys(patient.getPassword());

		newpasswordstep2.Email_Me.click();

		// Checking whether was email reaches the mail box

		boolean foundEmail = CheckMailCommunity.validateForgotPasswordTrash(gmail, startEmailSearchDate, patient.getGmailUName(),
						"Your password has been reset", "");
		verifyTrue(foundEmail, "The Forgot User Password email wasn't received.");

		GmailBot gBot = new GmailBot();
		// Setting up Gmail Credentials
		gBot.setCredentials(patient.getGmailUName(), patient.getGmailPassword());

		// Searching for the link for password reset in the Gmail Trash
		// folder
		String sURL = "";
		sURL = gBot.findTrashEmailLink(test.getTestEnv(), "Your password has been reset", "forgotpasswordsignin", 2, false, true);

		// Moving to Signing page
		if (sURL.isEmpty()) {
			log("Movin to Sign In Page: " + communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
			driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		} else {
			log("Movin to Sign In Page: " + sURL);
			driver.get(sURL);
		}
		ResetPasswordSignInNewPassword resetPasswordSignInNewPassword = new ResetPasswordSignInNewPassword(driver);

		// Signing in with UserId and Password
		log("Signing In with UserId: " + patient.getUserName() + " and Password: " + patient.getPassword());
		resetPasswordSignInNewPassword.inputUsername.sendKeys(patient.getUserName());
		resetPasswordSignInNewPassword.inputPassword.sendKeys(patient.getPassword());
		resetPasswordSignInNewPassword.btn_Sign_In.click();

		// Check whether the Community Home page was reached
		log("Checking presence of element on Community Home Page");
		CommunityHomePage communityHomePage = new CommunityHomePage(driver);
		assertTrue(communityHomePage.btn_My_Account.isDisplayed(), "Community My Account button is not displayed, Login was not reached");
		communityHomePage.btn_Sign_Out.click();

	}
}
