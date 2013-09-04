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
import com.intuit.ihg.common.entities.AppointmentRequest;
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.Practice;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.ExcelSheetUtil;
import com.intuit.ihg.common.utils.dataprovider.Filter;
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.MakeAppointmentRequest.AppointmentRequestHandleLocation;
import com.intuit.ihg.product.community.page.MakeAppointmentRequest.AppointmentRequestScheduleAppointmentPage;
import com.intuit.ihg.product.community.page.MakeAppointmentRequest.AppointmentRequestSelectDoctorPage;
import com.intuit.ihg.product.community.page.MakeAppointmentRequest.AppointmentRequestSelectLocationPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessageDetailPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessagePage;
import com.intuit.ihg.product.community.utils.CheckMailCommunity;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.GmailCommunity;
import com.intuit.ihg.product.community.utils.GmailMessage;
import com.intuit.ihg.product.community.utils.PracticeUrl;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestSearchPage;

public class AppointmentRequestTest extends BaseTestNGWebDriver {

	/**
	 * @Author:Jakub Calabek
	 * @Date:3.6.2013
	 * @User Story ID in Rally : TA18722 Creating Appointment request,
	 *       Approving the Request as a Doctor. Check whether approval
	 *       reached the gmail account. Loging to Community and check that
	 *       approval reached the Inbox as well.
	 */

	@DataProvider(name = "appointmentRequest")
	public static Iterator<Object[]> fileDataProvider3(ITestContext testContext) throws Exception {

		// Define hashmap
		LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
		classMap.put("TestObject", TestObject.class);
		classMap.put("Patient", Patient.class);
		classMap.put("AppointmentRequest", AppointmentRequest.class);
		classMap.put("PracticeUrl", PracticeUrl.class);
		classMap.put("Practice", Practice.class);
		classMap.put("GmailMessage", GmailMessage.class);

		// Filter is set on DEMO environment
		Filter filterTestEnvironment = Filter.equalsIgnoreCase(TestObject.TEST_ENV, IHGUtil.getEnvironmentType().toString());

		// Fetch data from CommunityAcceptanceTestData.csv
		Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(AppointmentRequestTest.class, classMap, "AppointmentTestData.csv", 0,
						null, filterTestEnvironment);

		return it;
	}

	@Test(enabled = false, groups = { "AcceptanceTests" }, dataProvider = "appointmentRequest")
	public void appointmentRequest(TestObject test, Patient patient, AppointmentRequest appointment, PracticeUrl practiceUrl, Practice practice,
					GmailMessage gmailMessage) throws Exception {

		log(test.getTestTitle());
		CommunityLoginPage communityLoginPage = new CommunityLoginPage(driver);
		Random randomGenerator = new Random();

		// Creating random number which is used for identification of the
		// Appointment
		int randomTestID = 100000 + randomGenerator.nextInt(900000);

		// Setting up date for Gmail search
		Date startEmailSearchDate1 = new Date();

		GmailCommunity gmail1 = new GmailCommunity(patient.getGmailUName(), patient.getGmailPassword());
		CommunityUtils communityUtils = new CommunityUtils();

		// Moving to Community Login Page
		log("Moving to page: " + communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));

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
		
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Clicking on the Appointments
		CommunityHomePage homePage = new CommunityHomePage(driver);
		homePage.icon_Appointments.click();
		
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Moving to the Select Doctor Page and Selecting Doctor
		AppointmentRequestSelectDoctorPage appointmentSelectDoctorPage = new AppointmentRequestSelectDoctorPage(driver);
		log("Selecting Doctor: " + appointment.getPreferredDoctor());
		appointmentSelectDoctorPage.SelectDoctor(appointment.getPreferredDoctor());
		appointmentSelectDoctorPage.btnContinue.click();
		
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");
		
		// Moving to the Select Location Page and selecting location
		AppointmentRequestHandleLocation appointmentRequestHandleLocation = new AppointmentRequestHandleLocation(driver);
		
		boolean checkLocationPage = appointmentRequestHandleLocation.checkPageTitle();
		if (checkLocationPage == true) {
		
		AppointmentRequestSelectLocationPage appointmentSelectLocationPage = new AppointmentRequestSelectLocationPage(driver);
		log("Selecting Location: " + appointment.getPreferredLocation());
		appointmentSelectLocationPage.SelectLocation(appointment.getPreferredLocation());
		appointmentSelectLocationPage.btnContinue.click();
		
		}
		
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Specifying Appointment reason and generated TestID
		log("Setting Appointment reason: " + appointment.getReason() + " " + randomTestID);
		AppointmentRequestScheduleAppointmentPage appointmentSceduleAppointmentPage = new AppointmentRequestScheduleAppointmentPage(driver);
		appointmentSceduleAppointmentPage.inputReasonForAppointment.sendKeys(appointment.getReason() + " " + randomTestID);
		appointmentSceduleAppointmentPage.btnContinue.click();
		
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		CommunityHomePage communityHomePage = new CommunityHomePage(driver);

		// Checking occurrence of Success notification on the Community
		// Home Page
		assertTrue(communityHomePage.checkSuccesNotification(driver), "You have requested an appointment notification not found");
		communityHomePage.btn_Sign_Out.click();
		
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		log("Login to Practice Portal with username: " + practice.getDocUName() + " and password: " + practice.getDocPassword());
		log("ENV IS :"+test.getTestEnv()+" OR "+IHGUtil.getEnvironmentType().toString());
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, IHGUtil.getEnvironmentType().toString(), null);
		
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);
		
		PracticeHomePage practiceHome = practiceLogin.login(practice.getDocUName(), practice.getDocPassword());
		
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);
		
		// Searching for the appointment which was created previously on
		// the Community site
		log("Search for appt requests");
		apptSearch.searchForApptRequestsForToday();
		log("Searching for appointment request: " + appointment.getReason() + " " + randomTestID);
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointment.getReason() + " " + randomTestID);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Waiting for Inner Frame to be loaded, max wait time is 30
		// seconds
		WebElement innerFrame = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id("iframe")));

		log("Choose process option and respond to patient");
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmit();
		
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");
		
		log("Checking Page Title");
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_PRACTICE_PORTAL);

		// Logging of the Practice Portal
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
		log(gmailMessage.getMessage());

		boolean foundEmail1 = CheckMailCommunity.validateForgotPasswordTrash(gmail1, startEmailSearchDate1, patient.getGmailUName(),
						gmailMessage.getMessage(), "");
		verifyTrue(foundEmail1, gmailMessage.getMessage());

		// Login to Community
		log("Login to Community with URL: " + communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");
		
		log("Login to Community with userid: " + patient.getUserName() + " and password: " + patient.getPassword());
		communityLoginPage.LoginToCommunity(patient.getUserName(), patient.getPassword());

		// Moving to the Messages
		homePage.icon_Messages.click();
		MessagePage messagePage = new MessagePage(driver);
		
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");

		// Searching for the email with correct subject
		log("Searching for email with subject: " + "Approved " + detailStep1.getCreatedTs());
		messagePage.clickMessage("Approved " + detailStep1.getCreatedTs());

		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");
		
		// Validating whether the message is the correct one
		MessageDetailPage messageDetails = new MessageDetailPage(driver);
		log("Veryfing match of the Message subject: " + appointment.getReason() + " " + randomTestID);
		assertTrue(messageDetails.isSubjectLocated(appointment.getReason() + " " + randomTestID), "Message with correct Subject was not found");
		

		homePage.btn_Sign_Out.click();
		
		assertTrue(CommunityUtils.validatePageTitle(driver, CommunityUtils.PAGE_TITLE_INTUIT_HEALTH),
						"Page Title does not match the expected one: " + CommunityUtils.PAGE_TITLE_INTUIT_HEALTH);

		// Look for Error Messages
		log("Looking for error messages");
		errorMessage = communityUtils.getTextError(driver);
		log("This is the Error message: " + errorMessage);
		assertEquals(errorMessage, "", "Error found");
	}
}
