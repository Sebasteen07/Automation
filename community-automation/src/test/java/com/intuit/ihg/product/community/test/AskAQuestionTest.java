package com.intuit.ihg.product.community.test;

import static org.testng.Assert.assertNotNull;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.entities.AskAQuestion;
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.Practice;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.ExcelSheetUtil;
import com.intuit.ihg.common.utils.dataprovider.Filter;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.AskAQuestion.AskAQuestionHandleLocation;
import com.intuit.ihg.product.community.page.AskAQuestion.AskAQuestionHistory;
import com.intuit.ihg.product.community.page.AskAQuestion.AskAQuestionMessage;
import com.intuit.ihg.product.community.page.AskAQuestion.AskAQuestionQuestionType;
import com.intuit.ihg.product.community.page.AskAQuestion.AskAQuestionSelectDoctor;
import com.intuit.ihg.product.community.page.AskAQuestion.AskAQuestionSelectLocation;
import com.intuit.ihg.product.community.page.solutions.Messages.MessageDetailPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessagePage;
import com.intuit.ihg.product.community.utils.CheckMailCommunity;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.GmailCommunity;
import com.intuit.ihg.product.community.utils.GmailMessage;
import com.intuit.ihg.product.community.utils.PracticeUrl;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffQuestionDetailStep1Page;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffQuestionDetailStep2Page;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffQuestionDetailStep3Page;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffQuestionDetailStep4Page;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffSearchPage;

public class AskAQuestionTest extends BaseTestNGWebDriver {

	/**
	 * @Author:Jakub Calabek
	 * @Date:11.6.2013
	 * @User Story ID in Rally : TA18033 Creating Question for the Doctor,
	 *       Answering to the question as a Doctor from the Practice Portal.
	 *       Check whether the answer reached the gmail account. Loging to
	 *       Community and check that answer reached the Inbox as well.
	 */

	@DataProvider(name = "askaquestion")
	public static Iterator<Object[]> fileDataProvider3(ITestContext testContext) throws Exception {

		// Define hashmap
		LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
		classMap.put("TestObject", TestObject.class);
		classMap.put("Patient", Patient.class);
		classMap.put("PracticeUrl", PracticeUrl.class);
		classMap.put("Practice", Practice.class);
		classMap.put("AskAQuestion", AskAQuestion.class);
		classMap.put("GmailMessage", GmailMessage.class);

		// Filter is set on DEMO environment
		Filter filterTestEnvironment = Filter.equalsIgnoreCase(TestObject.TEST_ENV, TestConfig.getTestEnv());

		// Fetch data from CommunityAcceptanceTestData.csv
		Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(AskAQuestionTest.class, classMap, "AskAQuestionTestData.csv", 0, null,
						filterTestEnvironment);

		return it;
	}

	@Test(enabled = false, groups = { "AcceptanceTests" }, dataProvider = "askaquestion")
	public void askAQuestion(TestObject test, Patient patient, PracticeUrl practiceUrl, Practice practice, AskAQuestion askAQuestion,
					GmailMessage gmailMessage) throws Exception {

		Random randomGenerator = new Random();
		CommunityUtils communityUtils = new CommunityUtils();
		GmailCommunity gmail1 = new GmailCommunity(patient.getGmailUName(), patient.getGmailPassword());

		CommunityLoginPage communityLoginPage = new CommunityLoginPage(driver);
		int randomTestID = 100000 + randomGenerator.nextInt(900000);

		Date startEmailSearchDate1 = new Date();

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

		// Sign In to Community
		log("Loging to Community with username: " + patient.getUserName() + " and password: " + patient.getPassword());
		communityLoginPage.LoginToCommunity(patient.getUserName(), patient.getPassword());

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Moving to Ask a Question
		log("Moving to Ask a Question");
		CommunityHomePage communityHomePage = new CommunityHomePage(driver);
		communityHomePage.link_Ask_New_Question.click();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Selecting Question type
		log("Selecting question type: " + askAQuestion.getStaff());
		AskAQuestionQuestionType askAQuestionQuestionType = new AskAQuestionQuestionType(driver);
		askAQuestionQuestionType.SelectQuestionType(askAQuestion.getStaff());
		askAQuestionQuestionType.btnContinue.click();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Selecting the doctor
		log("Selecting Doctor: " + askAQuestion.getDoctor());
		AskAQuestionSelectDoctor askAQuestionSelectDoctor = new AskAQuestionSelectDoctor(driver);
		askAQuestionSelectDoctor.SelectDoctor(askAQuestion.getDoctor());
		askAQuestionSelectDoctor.btnContinue.click();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		AskAQuestionHandleLocation askAQuestionHandleLocation = new AskAQuestionHandleLocation(driver);
		boolean checkLocationPresent = askAQuestionHandleLocation.checkPageTitle();

		if (checkLocationPresent == true) {

			// Selecting Location
			log("Selecting Location: " + askAQuestion.getLocation());
			AskAQuestionSelectLocation askAQuestionSelectLocation = new AskAQuestionSelectLocation(driver);
			askAQuestionSelectLocation.SelectLocation(askAQuestion.getLocation());
			askAQuestionSelectLocation.btnContinue.click();

			log("Checking Page Title");
			assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
							"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

			log("Looking for error messages");
			errorMessage = communityUtils.getTextError(driver);
			log("This is the Error message: " + errorMessage);
			assertEquals(errorMessage, "", "Error found");

		}

		// Fill the Question description
		AskAQuestionMessage askAQuestionMessage = new AskAQuestionMessage(driver);
		askAQuestionMessage.inputSubject.sendKeys(askAQuestion.getQuestion() + randomTestID);
		askAQuestionMessage.inputContentElement.sendKeys(askAQuestion.getQuestion() + " " + randomTestID);
		askAQuestionMessage.btnContinue.click();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Assert whether the Success notification appears on the Home
		// Page
		assertTrue(communityHomePage.checkSuccesNotification(driver), "Succes notification was not found");
		// Logging of the Community
		communityHomePage.btn_Sign_Out.click();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, IHGUtil.getEnvironmentType().toString(), null);

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		PracticeHomePage practiceHome = practiceLogin.login(practice.getDocUName(), practice.getDocPassword());

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		log("Click Ask A Staff tab");
		AskAStaffSearchPage aasSearch = practiceHome.clickAskAStaffTab();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		log("Search for questions");
		aasSearch.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = aasSearch.getQuestionDetails(askAQuestion.getQuestion() + randomTestID);
		assertNotNull(detailStep1, "The submitted patient question was not found in the practice");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Waiting for Inner Frame to be loaded, max wait time is 30
		// seconds
		WebElement innerFrame = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id("iframe")));

		log("Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1.chooseProvideAdviceOnly();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Getting the value of getCreatedTimeStamp
		long getCreatedTimeStamp = detailStep2.getCreatedTimeStamp();

		log("Respond to patient question");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2.processAndCommunicate("Automated Test",
						"This message was generated by an automated test");

		log("Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3.confirmProcessedQuestion();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		log("Validate submit of confirmation");
		detailStep4.isQuestionDetailPageLoaded();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		log("Logout of Practice Portal");
		practiceHome.logOut();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Verifying Gmail for new message from Doctor
		log("Verify Gmail");
		log("Checking Gmail for new messages " + patient.getGmailUName() + "pasword: " + patient.getPassword());

		log("Access Gmail and check for received email");

		log(patient.getGmailUName());

		boolean foundEmail1 = CheckMailCommunity.validateForgotPasswordTrash(gmail1, startEmailSearchDate1, patient.getGmailUName(),
						gmailMessage.getMessage(), "");
		verifyTrue(foundEmail1, gmailMessage.getMessage());

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

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);

		// Moving to the Messages
		communityHomePage.icon_Messages.click();
		MessagePage messagePage = new MessagePage(driver);

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);

		// Searching for the email with correct subject
		log("Searching for email with subject: " + "Automated Test " + getCreatedTimeStamp);
		messagePage.clickMessage("Automated Test " + getCreatedTimeStamp);

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);

		// Validating whether the message is the correct one
		MessageDetailPage messageDetails = new MessageDetailPage(driver);
		log("Veryfing match of the Message subject: " + askAQuestion.getQuestion() + " " + randomTestID);
		assertTrue(messageDetails.isSubjectLocated(askAQuestion.getQuestion() + " " + randomTestID),
						"Message with correct Subject was not found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);

		// Signing out of the Community
		communityHomePage.btn_Sign_Out.click();

		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));

		// Loging to Community
		log("Loging to Community with username: " + patient.getUserName() + " and password: " + patient.getPassword());
		communityLoginPage.LoginToCommunity(patient.getUserName(), patient.getPassword());

		log("Clicking on Question History");
		communityHomePage.link_Question_History.click();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);

		log("Looking for message");
		AskAQuestionHistory askAQuestionHistory = new AskAQuestionHistory(driver);
		askAQuestionHistory.findAskAQuestion(askAQuestion.getQuestion() + randomTestID);
		log("Message found");

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		communityHomePage.btn_Sign_Out.click();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

	}
}