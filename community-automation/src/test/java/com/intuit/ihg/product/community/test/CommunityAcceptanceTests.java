package com.intuit.ihg.product.community.test;

import static org.testng.Assert.assertNotNull;

import java.util.Random;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.community.utils.Community;
import com.intuit.ihg.product.community.utils.CommunityConstants;
import com.intuit.ihg.product.community.utils.CommunityTestData;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.object.maps.community.page.CommunityHomePage;
import com.intuit.ihg.product.object.maps.community.page.CommunityLoginPage;
import com.intuit.ihg.product.object.maps.community.page.CreatePatientBeta;
import com.intuit.ihg.product.object.maps.community.page.AskAQuestion.AskAQuestionHistory;
import com.intuit.ihg.product.object.maps.community.page.AskAQuestion.AskAQuestionMessage;
import com.intuit.ihg.product.object.maps.community.page.AskAQuestion.AskAQuestionQuestionType;
import com.intuit.ihg.product.object.maps.community.page.AskAQuestion.AskAQuestionSelectDoctor;
import com.intuit.ihg.product.object.maps.community.page.AskAQuestion.AskAQuestionSelectLocation;
import com.intuit.ihg.product.object.maps.community.page.BillPay.BillPayChooseYourPractice;
import com.intuit.ihg.product.object.maps.community.page.BillPay.BillPayPaymentDetailsPage;
import com.intuit.ihg.product.object.maps.community.page.MakeAppointmentRequest.AppointmentRequestScheduleAppointmentPage;
import com.intuit.ihg.product.object.maps.community.page.MakeAppointmentRequest.AppointmentRequestSelectDoctorPage;
import com.intuit.ihg.product.object.maps.community.page.MakeAppointmentRequest.AppointmentRequestSelectLocationPage;
import com.intuit.ihg.product.object.maps.community.page.RxRenewal.RxRenewalChoosePharmacy;
import com.intuit.ihg.product.object.maps.community.page.RxRenewal.RxRenewalChoosePrescription;
import com.intuit.ihg.product.object.maps.community.page.RxRenewal.RxRenewalSearchDoctor;
import com.intuit.ihg.product.object.maps.community.page.solutions.Messages.MessageDetailPage;
import com.intuit.ihg.product.object.maps.community.page.solutions.Messages.MessageHealthInformationPage;
import com.intuit.ihg.product.object.maps.community.page.solutions.Messages.MessageIframeHandlePage;
import com.intuit.ihg.product.object.maps.community.page.solutions.Messages.MessagePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep1Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep2Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep3Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep4Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.onlinebillpay.OnlineBillPayDetailPage;
import com.intuit.ihg.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.intuit.ihg.product.object.maps.practice.page.onlinebillpay.OnlineBillPayVerifyPage;
import com.intuit.ihg.product.object.maps.practice.page.rxrenewal.RxRenewalDetailPage;
import com.intuit.ihg.product.object.maps.practice.page.rxrenewal.RxRenewalDetailPageConfirmation;
import com.intuit.ihg.product.object.maps.practice.page.rxrenewal.RxRenewalSearchPage;

@Test
public class CommunityAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	/**
	 * @Author:yvaddavalli
	 * @Date:8/16/2013
	 * @StepsToReproduce: Launch Community and Login. Validate Home Page and Log
	 *                    Out from Community
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityLoginLogout() throws Exception {

		log("Test Case: TestCommunityLoginLogout");
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
		verifyEquals(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
				.trim(),"### It seems Community may be down at this moment .... Community Home Page Title what we ");
		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(3000);
		
		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 4: Logout of Community");
		loginPage = homePage.logOutCommunity();

	}

	/**
	 * @Author: yvaddavalli
	 * @Date: 8/16/2013
	 * @StepsToReproduce: Login to Community. Click on New appointment request
	 *                    link on Home Page and start Appointment Request.
	 *                    Select Doctor, Location, Fill out form and complete
	 *                    appointemnt request. Check Success Notification
	 *                    Message in Home Page. Logout of Community. Login to
	 *                    Practice Portal Click Appt Request tab Search for appt
	 *                    requests Choose process option and respond to patient
	 *                    Confirm response details to patient Logout of Practice
	 *                    Portal. Login to Gmail and check for email. Login to
	 *                    Community Go to Inbox Find message in Inbox Validate
	 *                    message loads and is the right message. Log out of
	 *                    Community
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityAppointmentRequest() throws Exception {

		log("Test Case: testCommunityAppointmentRequest");
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
		
		verifyEquals(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
				.trim(),
				"### It seems Community may be down at this moment .... Community Title what we ");

		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(3000);
		
		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 4: Click on Appointment link on Home Page");
		AppointmentRequestSelectDoctorPage apptRequestStep1 = homePage
				.clickAppointmentRequest();

		log("step 5: Select a Doctor ");
		AppointmentRequestSelectLocationPage apptRequestStep2 = apptRequestStep1
				.SelectDoctor(testcasesData.getAppointmentDoctor());

		log("step 6: select a Location  ");
		AppointmentRequestScheduleAppointmentPage apptRequestStep3 = apptRequestStep2
				.SelectLocation(testcasesData.getAppointmentLocation());

		// Create random number to create a reason
		String apptreason = (CommunityConstants.ApptReason + CommunityUtils
				.createRandomNumber());

		log("step 7: Fill out appointment details and complete appointemnt request ");
		homePage = apptRequestStep3.filloutAppoitmentDetails(apptreason);

		log("step 8: Check for successfull appointment notification on Home page ");
		verifyTrue(
				homePage.checkSuccesNotification(driver),
				"There was an issue with Community Appointment. Expected to see 'Successfull Notification Message on Home Page '  but it was not found.");

		log("step 9: Logout of Community");
		homePage.logOutCommunity();

		// Practice Portal Appointment confirmation

		log("step 10:Reading the Excel Data for Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				testcasesData.getPracticeUrl());

		log("step 11:Login to Practice Portal");
		PracticeHomePage practiceHome = practiceLogin.login(
				testcasesData.getPracticePortalAppointmentDoctorUserName(),
				testcasesData.getPracticePortalAppointmentDoctorPassword());

		log("step 12:Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("step 13:Search for the Appt request submitted in Community");
		apptSearch.searchForApptRequestsForToday();

		log("step 13:Get the Details of appointment");
		ApptRequestDetailStep1Page detailStep1 = apptSearch
				.getRequestDetails(apptreason);
		assertNotNull(detailStep1,
				"The submitted patient request was not found in the practice");

		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id("iframe")));

		log("step 14:Choose process option and respond to patient");
		ApptRequestDetailStep2Page detailStep2 = detailStep1
				.chooseApproveAndSubmit();

		log("step 15:Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		verifyTrue(apptSearch.isSearchPageLoaded(),
				"Expected the Appt Search Page to be loaded, but it was not.");

		// Logging of the Practice Portal

		log("step 16:Logout of Practice Portal");

		practiceHome.logOut();

		// Verifying Gmail for new message from Doctor
		
		log (" Checking Gmail for new message from Doctor");
//		String sSubject = "New message from";
		String link = "https://";
		GmailBot gbot = new GmailBot();
		String sURL = gbot.findInboxEmailLink(testcasesData.getGmailUName(),
				testcasesData.getGmailPassword(), testcasesData.getGmailMessage(),

				link, 20, false, false);

		if (sURL.isEmpty()) {

			log("###Couldn't get URL from email");
			Assert.fail("NO email found in the Gmail Inbox");

		} else {
			log("step 17: Got the email and clicking on the link in the email: "
					+ sURL);
			driver.navigate().to(sURL);
		}

		log("Login to Community to check If the Patient got a message in the Inbox from Pratcie Portal");

		log("step 18: Load the Community URL and Check the Page Title");
		new CommunityLoginPage(driver,
				testcasesData.getUrl());
		Thread.sleep(5000);
		Assert.assertEquals(
				"### It seems Community may be down at this moment .... Community Title what we ",
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
						.trim());

		log("step 19 : LogIn to Community");
		loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());

		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		// Moving to the Messages

		log("step 20 : Click on Messages");
		homePage.clickMessages();
		MessagePage messagePage = new MessagePage(driver);

		log("step 21:Searching for the email with correct subject");

		log("step 22: Searching for email with subject: " + "Approved "
				+ detailStep1.getCreatedTs());
		messagePage.clickMessage("Approved " + detailStep1.getCreatedTs());

		log(" step 23: Validating whether the message is the correct one");
		MessageDetailPage messageDetails = new MessageDetailPage(driver);

		log("step 24: Veryfing match of the Message subject: " + apptreason);
		verifyTrue(messageDetails.isSubjectLocated(apptreason),
				"Message with correct Subject was not found");

		log("step 25: Message found");

		log("step  26: Logout of Community");
		loginPage = homePage.logOutCommunity();

		log("Test case passed  End of Test");

	}

	/**
	 * @Author: yvaddavalli
	 * @Date: 8/16/2013
	 * @StepsToReproduce: Login to Community. Click on New Question link on Home
	 *                    Page and start Ask a Question flow. Select a Question,
	 *                    Select Doctor, Location, Fill out form and complete
	 *                    Ask a Question. Check Success Notification Message in
	 *                    Home Page. Click on Ask a Question History link and
	 *                    check history. Logout of Community. Login to Practice
	 *                    Portal Search Ask A Questions Access details for
	 *                    submitted question Respond to question Logout of
	 *                    Practice Portal .Login to Community and Find message
	 *                    in Inbox Validate the message loads and is the right
	 *                    message. Log out of Community
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityAskAQuestion() throws Exception {

		log("Test Case:testCommunityAskAQuestion ");
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
		verifyEquals(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
				.trim(), 
				"### It seems Community may be down at this moment .... Community Title what we ");

		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(3000);
		
		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 4: Click on New Question link on Home Page");
		AskAQuestionQuestionType askAQuestionStep1 = homePage
				.clickAskAQuestion();
		log("step 5: Select a Question");
		AskAQuestionSelectDoctor askAQuestionStep2 = askAQuestionStep1
				.SelectQuestionType(testcasesData.getAskAQuestionType());

		log("step 6: Select a Doctor ");
		AskAQuestionSelectLocation askAQuestionStep3 = askAQuestionStep2
				.SelectDoctor(testcasesData.getAskAQuestionDoctor());

		log("step 7: select a Location  ");
		AskAQuestionMessage askAQuestionStep4 = askAQuestionStep3
				.SelectLocation(testcasesData.getAskAQuestionLocation());

		// Create random number to create a Question and Content
		String askaquestion = (CommunityConstants.AskAQuestion + CommunityUtils
				.createRandomNumber());

		log("step 8: Fill out Question details and complete request ");
		homePage = askAQuestionStep4.FilloutAskAQuestionDetails(askaquestion,
				askaquestion);

		log("step 9: Check for successfull notification Message on Home page ");
		verifyTrue(
				homePage.checkSuccesNotification(driver),
				"There was an issue with Community Ask A Question. Expected to see 'Successfull Notification Message on Home Page '  but it was not found.");

		log("step 10: Click on Ask a Question History link");
		homePage.clickAskAQuestionHistorylink();

		log("step 11 : Check  Ask a Question History got the above request");
		AskAQuestionHistory askAQuestionHistory = new AskAQuestionHistory(
				driver);
	
//		driver.navigate().refresh();
		verifyTrue(askAQuestionHistory.checkAskAQuestionHistory(askaquestion));

		log("Question found in History");

		log("step 12: Logout of Community");
		homePage.logOutCommunity();

		// Practice Portal Ask a Question reply flow

		log("step 13: Reading the Excel Data for Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				testcasesData.getPracticeUrl());

		log("step 14: Login to Practice Portal");
		PracticeHomePage practiceHome = practiceLogin.login(
				testcasesData.getPracticePortalAskAQuestionDoctorUserName(),
				testcasesData.getPracticePortalAskAQuestionDoctorPassword());

		log("step 15: Click Ask A Staff tab");
		AskAStaffSearchPage aasSearch = practiceHome.clickAskAStaffTab();

		log("step 16: Search for questions");
		aasSearch.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = aasSearch
				.getQuestionDetails(askaquestion);
		assertNotNull(detailStep1,
				"The submitted patient question was not found in the practice");

		// Waiting for Inner Frame to be loaded, max wait time is 30
		// seconds

		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id("iframe")));

		log("step 17: Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1
				.chooseProvideAdviceOnly();

		// Getting the value of getCreatedTimeStamp

		long getCreatedTimeStamp = detailStep2.getCreatedTimeStamp();

		log("step 18: Respond to patient question");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2
				.processAndCommunicate("Automated Test",
						"This message was generated by an automated test");

		log("step 19: Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3
				.confirmProcessedQuestion();

		log("step 20 : Validate submit of confirmation");
		detailStep4.isQuestionDetailPageLoaded();

		log("step 21: Logout of Practice Portal");
		practiceHome.logOut();

		// Community Message Validation

		log("Login to Community to check If the Patient got a message in the Inbox from Pratcie Portal");

		log("step 22: Load the Community URL and Check the Page Title");
		new CommunityLoginPage(driver,
				testcasesData.getUrl());
		Thread.sleep(5000);
		verifyEquals(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle().trim(),
				"### It seems Community may be down at this moment .... Community Title what we ");

		log("step 23: LogIn to Community");
		loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(3000);

		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		// Moving to the Messages

		log("step 24: Click on Messages");
		homePage.clickMessages();
		MessagePage messagePage = new MessagePage(driver);

		// Searching for the email with correct subject

		log("step 25: Searching for email with subject: " + "Automated Test "
				+ getCreatedTimeStamp);
		messagePage.clickMessage("Automated Test " + getCreatedTimeStamp);

		// Validating whether the message is the correct one
		MessageDetailPage messageDetails = new MessageDetailPage(driver);

		log("step 26: Veryfing match of the Message subject: " + askaquestion);
		verifyTrue(messageDetails.isSubjectLocated(askaquestion),
				"Message with correct Subject was not found");

		log(" Message found");

		log("step 27: Logout of Community");
		loginPage = homePage.logOutCommunity();

		log("Test case passed  End of Test");
		;

	}

	/**
	 * @Author: yvaddavalli
	 * @Date: 8/16/2013
	 * @StepsToReproduce: Login to Community. Click on Make a Payment link on
	 *                    Home Page and start Bill Pay flow. Select a Practice,
	 *                    Fill out form and complete Bill Pay. Check Success
	 *                    Notification Message in Home Page. Logout of
	 *                    Community. Login to Practice Portal and Click on
	 *                    Online Bill Payment Set Details in
	 *                    PaymentCommunicationDetails and Send Message to
	 *                    Patient.Login to Community and Find message in Inbox
	 *                    Validate the message loads and is the right message.
	 *                    Log out of Community
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityBillPay() throws Exception {

		log("Test Case: testCommunityBillPay");	
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

		verifyEquals(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle().trim(),
				"### It seems Community may be down at this moment .... Community Title what we ");
		

		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(3000);
		
		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 4: Click on Make a payment link on Home Page");
		BillPayChooseYourPractice billPayStep1 = homePage.clickBillPay();

		log("step 5: Select a Practice ");
		BillPayPaymentDetailsPage billPayStep2 = billPayStep1
				.SelectPractice(testcasesData.getBillPayPracticeName());

		// Create random numbers for amount and test id

		Random randomGenerator = new Random();
		int randomAmount = randomGenerator.nextInt(100);
		int randomTestID = 100000 + randomGenerator.nextInt(900);

		log("step 6: Fillout Payment details and Card info");

		homePage = billPayStep2.filloutPaymentDetails((String.format("%d",
				randomAmount)));

		log("step 9: Check for successfull Payment  notification on Home page ");
		verifyTrue(
				homePage.checkSuccesNotification(driver),
				"There was an issue with Community Bill Pay  Expected to see 'Successfull Notification Message on Home Page '  but it was not found.");

		log("step 9: Logout of Community");
		homePage.logOutCommunity();

		// Practice Portal Bill Pay Confirmation flow

		log("step 10: Reading the Excel Data for Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				testcasesData.getPracticeUrl());

		log("step 11: Login to Practice Portal");
		PracticeHomePage practiceHome = practiceLogin.login(
				testcasesData.getPracticePortalBillPayDoctorUserName(),
				testcasesData.getPracticePortalBillPayDoctorPassword());

		log("step 12: Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome
				.clickOnlineBillPayTab();

		log("step 13: Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		log("step 14: Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage
				.searchForBillPayment(CommunityConstants.PatientAccountNumber);

		log("step 15: Get Bill Pay Details");
		OnlineBillPayDetailPage onlineBillPayDetailPage = onlineBillPaySearchPage
				.getBillPayDetails();

		log("step 16: Set Payment Communication Details");
		onlineBillPayDetailPage.setFrame();
		onlineBillPayDetailPage.CommunicateBillPay("Test Bill Pay subject "
				+ randomTestID, "Test Bill Pay message " + randomTestID);

		OnlineBillPayVerifyPage onlineBillPayVerifyPage = new OnlineBillPayVerifyPage(
				driver);
		verifyTrue(onlineBillPayVerifyPage.MessageSentNotification(),
				"Success notification is not displayed after the payment email sent");

		log("step 17: Logout of Practice Portal");
		practiceHome.logOut();

		// Checking Gmail for Bill Pay receipt

		GmailBot gbot = new GmailBot();
//		String sSubject = "Your receipt from";
		String link = "http://";
		String sURL = gbot.findInboxEmailLink(testcasesData.getGmailUName(),
				testcasesData.getGmailPassword(), testcasesData.getGmailBillPayReceipt(),

				link, 20, false, false);

		if (sURL.isEmpty()) {

			Assert.fail("NO email found in the Gmail Inbox");

		} else {
			log("step 11: Got the email for bill pay receipt");
			
		}

		// Community Message Validation

		log("Login to Community to check If the Patient got a message in the Inbox from Pratcie Portal");

		log("step 19: Load the Community URL and Check the Page Title");
		new CommunityLoginPage(driver,
				testcasesData.getUrl());
		Thread.sleep(3000);
		
		verifyEquals(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
				.trim(),"### It seems Community may be down at this moment .... Community Title what we ");

		log("step 20 : LogIn to Community");
		loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());

		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		// Moving to the Messages

		log("step 21: Click on Messages");
		homePage.clickMessages();
		MessagePage messagePage = new MessagePage(driver);

		log("step 22: Searching for email with subject: "
				+ "Test Bill Pay subject " + randomTestID);
		messagePage.clickMessage("Test Bill Pay subject " + randomTestID);
		MessageDetailPage messageDetails = new MessageDetailPage(driver);

		log(" step 23: Validating whether the message is the correct one");
		verifyTrue(
				messageDetails.isSubjectLocated("Test Bill Pay message "
						+ randomTestID),
				"Message with correct Subject was not found");

		log("step 24: Message found");

		log("step  25: Logout of Community");
		loginPage = homePage.logOutCommunity();

		log("Test case passed  End of Test");

	}

	/**
	 * @Author: yvaddavalli
	 * @Date: 8/16/2013
	 * @StepsToReproduce: Login to Community. Click on Make a Payment link on
	 *                    Home Page and start Bill Pay flow. Select a Practice,
	 *                    Fill out form and complete Bill Pay. Check Success
	 *                    Notification Message in Home Page. Logout of
	 *                    Community. Login to Practice Portal and Click on
	 *                    Online Bill Payment Set Details in
	 *                    PaymentCommunicationDetails and Send Message to
	 *                    Patient.Login to Community and Find message in Inbox
	 *                    Validate the message loads and is the right message.
	 *                    Log out of Community
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityRxRenewal() throws Exception {

		log("Test Case: testCommunityRxRenewal");
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
		
		verifyEquals(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
				.trim(), "### It seems Community may be down at this moment .... Community Title what we ");

		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(3000);
		
		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 4: Click on Rx Renewal link on Home Page");
		RxRenewalChoosePrescription rxRenewalStep1 = homePage.clickRxRenewal();

		log("step 5: Click on Enter New prescription");
		rxRenewalStep1.SelectNewPrescription();

		// Creating random number which is used for identification of the
		// RxRenewal
		Random randomGenerator = new Random();
		int randomTestID = 100000 + randomGenerator.nextInt(900000);
		String randomRxNumber = String.valueOf(100000 + randomGenerator
				.nextInt(900000));

		log("step 6: Fill out prescription Details");
		rxRenewalStep1.fillPrescription(testcasesData.getMedicine(),
				CommunityConstants.Dosage, randomRxNumber);

		log("step 7: select Doctor");
		RxRenewalSearchDoctor renewalSearchDoctor = new RxRenewalSearchDoctor(
				driver);
		renewalSearchDoctor.selectDoctor(testcasesData.getRxDoctor());

		log("step 8: search for Pharmacy and complete Rx request");
		RxRenewalChoosePharmacy rxRenewalChoosePharmacy = new RxRenewalChoosePharmacy(
				driver);
		rxRenewalChoosePharmacy.selectPharmacy(testcasesData.getPharmacy());

		log("step 9:check for successful notification message on Home Page");
		verifyTrue(
				homePage.checkSuccesNotification(driver),
				"There was an issue with Community Rx Renewal  Expected to see 'Successfull Notification Message on Home Page'  but it was not found.");

		log("step 10:Succefull Notifcation displayed on Home page");

		log("step 11: Logout of Community");
		homePage.logOutCommunity();
		// Practice Portal Appointment confirmation

		log("step 12:Reading the Excel Data for Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				testcasesData.getPracticeUrl());

		log("step 13:Login to Practice Portal");
		PracticeHomePage practiceHome = practiceLogin.login(
				testcasesData.getPracticePortalRxDoctorUserName(),
				testcasesData.getPracticePortalRxDoctorPassword());

		// Selecting RxRenewal
		log("step 13: Clicking on RxRenewal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome
				.clickonRxRenewal();

		// Searching for new Renewals which were created today
		log("step 14: Searching for Renewals");
		rxRenewalSearchPage.searchForRxRenewalToday();

		// Filling up the RxRenewal
		RxRenewalDetailPage rxRenewalDetailPage = rxRenewalSearchPage
				.getRxRenewalDetails();
		rxRenewalDetailPage.setFrame();

		log("step 15: Specifiyng RxRenwal");
		rxRenewalDetailPage.prescribe("1", "12");
		rxRenewalDetailPage.setMessageFrom(testcasesData
				.getPracticePortalRxDoctorUserName());
		rxRenewalDetailPage.setSubject("Text RX message " + randomTestID);
		rxRenewalDetailPage.setMessageBody("Text RX message " + randomTestID);

		// Clicking on continue
		log("step 16: Clicking on continue");
		RxRenewalDetailPageConfirmation rxRenewalDetailPageConfirmation = rxRenewalDetailPage
				.clickCommunicateAndProcessRxRenewal();
		rxRenewalDetailPageConfirmation.clickCallInTheRx();

		// Finishing RxRenewal
		log("step 17: Finishing RxRenewal");
		rxRenewalDetailPageConfirmation
				.clickContinue();

		log("step 18: Signing of the Patient Portal");
		practiceHome.logOut();

		// Community Message Validation

		log("step 19:Login to Community to check If the Patient got a message in the Inbox from Pratcie Portal");

		log("step 20: Load the Community URL and Check the Page Title");
		new CommunityLoginPage(driver,
				testcasesData.getUrl());
		Thread.sleep(5000);
		
		verifyEquals(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
				.trim(),"### It seems Community may be down at this moment .... Community Title what we "
				);

		log("step 21 : LogIn to Community");
		loginPage.LoginToCommunity(
				testcasesData.getUserName(), testcasesData.getPassword());
		Thread.sleep(3000);
		
		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		// Moving to the Messages

		log("step 22: Click on Messages");
		homePage.clickMessages();
		MessagePage messagePage = new MessagePage(driver);

		// Searching for the email with correct subject
		log("Searching for email with subject: Text RX message " + randomTestID);
		messagePage.clickMessage("Text RX message " + randomTestID);

		// Validating whether the message is the correct one
		MessageDetailPage messageDetails = new MessageDetailPage(driver);
		log("Veryfing match of the Message subject: Text RX message "
				+ randomTestID);
		verifyTrue(messageDetails.isSubjectLocated("Text RX message "
				+ randomTestID));

		log("step 23: Message found");

		log("step  24: Logout of Community");
		loginPage = homePage.logOutCommunity();

		log("Test case passed  End of Test");

	}

	/**
	 * @Author: yvaddavalli
	 * @Date: 8/16/2013
	 * @StepsToReproduce: Click Sign up now button on Login Page and Fill out
	 *                    all the details and complete Account Creation.Click on
	 *                    Messages and check if there is Welcome Message in the
	 *                    Inbox. message. Log out of Community
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityCreateAccount() throws Exception {

		log("Test Case: testCommunityCreateAccount");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		Community community = new Community();
		CommunityTestData communityTestData = new CommunityTestData(community);

		CreatePatientBeta createPatient = new CreatePatientBeta();
		createPatient.CreatePatientTest(driver, communityTestData);

	}

	/**
	 * @Author: yvaddavalli
	 * @Date: 8/29/2013
	 * @StepsToReproduce: Login to Community as a user you got CCD Imported
	 *                    already. Click on Messages. Click on the message that
	 *                    is for CCD Import and click on Review your helath info
	 *                    and add open the CCD viewer and verify if the CCD
	 *                    Displays info correctly. Logout of Commmunity
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommunityCCDViewer() throws Exception {

		log("Test Case: testCommunityCCDViewer");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Community community = new Community();
		CommunityTestData testcasesData = new CommunityTestData(community);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getCCDUserName());
		log("Password: " + testcasesData.getCCDUserPassword());

		log("step 2: Load the Community URL and Check the Page Title");
		CommunityLoginPage loginPage = new CommunityLoginPage(driver,
				testcasesData.getUrl());
		Thread.sleep(5000);
		
		verifyEquals(CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
				.trim(),"### It seems Community may be down at this moment .... Community Title what we ");

		log("step 3: LogIn to Community");
		CommunityHomePage homePage = loginPage.LoginToCommunity(
				testcasesData.getCCDUserName(),
				testcasesData.getCCDUserPassword());
		Thread.sleep(3000);
		
		verifyTrue(
				homePage.isViewallmessagesLinkPresent(driver),
				"There was an issue with Community login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 3: Click on Messages");
		homePage.clickMessages();
		MessagePage messagePage = new MessagePage(driver);

		log("step 4: Searching for email with subject: New Health Information Import ");
		messagePage.clickMessage("New Health Information Import");

		log("step 5: Clicking on Review Health Information");
		MessageDetailPage messageDetail = new MessageDetailPage(driver);
		messageDetail.clickReviewHealthInformation();

		log("step 6: Switching to CCD Viewer  Waiting for Inner frame to load");
		MessageIframeHandlePage messageIframeHandlePage = new MessageIframeHandlePage(
				driver);
		messageIframeHandlePage.handleIframe();

		MessageHealthInformationPage healthInformationPage = new MessageHealthInformationPage(
				driver);

		log("step 7: Searching for elements on the CCDViewer site");
		verifyTrue(healthInformationPage.areElementsLocated(),
				"CCD message was not displayed properly");

		log("step 8: CCDViewer displayed elements correctly");

		driver.switchTo().defaultContent();

		log("step 9: : Logout of Community");
		loginPage = homePage.logOutCommunity();

		log("Test case passed  End of Test");
	}
}
