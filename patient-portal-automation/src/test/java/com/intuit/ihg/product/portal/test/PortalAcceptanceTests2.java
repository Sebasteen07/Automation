package com.intuit.ihg.product.portal.test;

import static org.testng.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessagePage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessageCenterInboxPage;
import com.intuit.ihg.product.object.maps.portal.page.makePaymentpage.MakePaymentPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.insurance.InsurancePage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.wallet.WalletPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffHistoryPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep1Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep2Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep3Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitConfirmationPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitHistoryDetailPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitHistoryPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitPharmacyPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitProviderPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitQuestionnairePage;
import com.intuit.ihg.product.object.maps.portal.page.symptomAssessment.NewSymptomAssessmentPage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep1Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep2Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep3Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep4Page;
import com.intuit.ihg.product.object.maps.practice.page.askstaff.AskAStaffSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.messages.PracticeMessagePage;
import com.intuit.ihg.product.object.maps.practice.page.messages.PracticeMessagesSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.intuit.ihg.product.object.maps.practice.page.symptomassessment.SymptomAssessmentDetailsPage;
import com.intuit.ihg.product.object.maps.practice.page.symptomassessment.SymptomAssessmentFilterPage;
import com.intuit.ihg.product.object.maps.practice.page.virtualofficevisit.VirtualOfficeVisitOnlineVisitAndMedicationPage;
import com.intuit.ihg.product.object.maps.practice.page.virtualofficevisit.VirtualOfficeVisitPracticeConfirmationPage;
import com.intuit.ihg.product.object.maps.practice.page.virtualofficevisit.VirtualOfficeVisitSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.virtualofficevisit.VirtualOfficeVisitTakeActionPage;
import com.intuit.ihg.product.portal.tests.FamilyAccountTest;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;

public class PortalAcceptanceTests2 extends BaseTestNGWebDriver{
	
	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}
	
	/**
	 * @Author: Kiran_GT
	 * @Date: 07/10/2013
	 * @StepsToReproduce: Patient login Click on Make Payment Give Payment
	 *                    Details Submit Payment Details Logout of Patient
	 *                    Portal Login to Practice Portal and Click on Online
	 *                    Bill Payment Set Details in
	 *                    PaymentCommunicationDetails and Sent Message to
	 *                    Patient Logout from the Practice Portal Login to
	 *                    Patient Portal Go to Inbox and Find message
	 *                    Validate message
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBillPaymentEnd2End() throws Exception {

		log("Test Case: testAppointmentRequest");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Verify for My Patient Page ");
		PortalUtil.setPortalFrame(driver);
		verifyEquals(myPatientPage.gettxtMyPatientPage().getText(), PortalConstants.MyPatientPage);

		log("step 4: Click on Make Payment Link ");
		MakePaymentPage makePaymentPage = myPatientPage.clickMakePaymentLnk();

		log("step 5: Set Make Payments Fields");
		makePaymentPage.setMakePaymentFields();

		log("step 6: Logout of Patient Portal");
		myPatientPage.logout(driver);

		// Load up practice test data
		log("step 7: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 8:Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();

		log("step 9:Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		log("Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage.searchForBillPayment(PortalConstants.PatientAccountNumber);

		log("Get Bill Details");
		onlineBillPaySearchPage.getBillPayDetails();

		log("Set Payment Communication Details");
		onlineBillPaySearchPage.setPaymentCommunicationDetails();

		log("step 10: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 11: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 12: Go to Inbox");
		MessageCenterInboxPage inboxPage = myPatientPage.clickViewAllMessagesInMessageCenter();
		PerformanceReporter.getPageLoadDuration(driver, MessageCenterInboxPage.PAGE_NAME);

		log("step 13: Find message in Inbox");
		MessagePage message = inboxPage.openMessageInInbox(PortalConstants.RxRenewalSubject);

		log("step 14: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(PracticeConstants.BillPaymentSubject), "Expected subject containting ["
						+ PortalConstants.RxRenewalSubject + "but actual subject was [" + actualSubject + "]");

	}
	
	/**
	 * @Author: bkrishnankutty
	 * @Date: 05/6/2013
	 * @StepsToReproduce: Patient login Click on SymptomAssessment and Select
	 *                    your doctor Give Your Symptom and submit Answer all
	 *                    the Question in coming up in the further pages??
	 *                    Assert Text after completing Symptom Assessment
	 *                    Logout of Patient Portal Login to Practice Portal
	 *                    and Click on SymptomAssessmentTab Verify the
	 *                    information on SymptomAssessmentDetailsPage and
	 *                    Sent Message to Patient Logout from the Practice
	 *                    Portal Login to Patient Portal Go to Inbox and Find
	 *                    message Validate message
	 *                    ============================
	 *                    =================================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSymptomAssessment() throws Exception {

		log("Test Case: testSymptomAssessment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Retrieving test case data");
		Portal portal = new Portal();
		TestcasesData patientData = new TestcasesData(portal);

		log("URL: " + patientData.geturl());
		log("USER NAME: " + patientData.getUsername());
		log("Password: " + patientData.getPassword());

		log("step 1: Patient login");
		PortalLoginPage loginPage = new PortalLoginPage(driver, patientData.geturl());
		MyPatientPage pMyPatientPage = loginPage.login(patientData.getUsername(), patientData.getPassword());

		log("step 2: Click on SymptomAssessment");
		NewSymptomAssessmentPage pNewSymptomAssessmentPage = pMyPatientPage.clickNewSymptomAssessmentLink();
		assertTrue(pNewSymptomAssessmentPage.isPageLoaded(), NewSymptomAssessmentPage.PAGE_NAME + " failed to load.");

		log("step 3: Select your doctor");
		pNewSymptomAssessmentPage.selectProvider(patientData.getPreferredDoctor());

		log("step 4: type Your Symptom and submit");
		pNewSymptomAssessmentPage.typeYourSymptom(PortalConstants.Symptom);

		log("step 5: DoYouHaveSymptom Now ?? Answer :-NO ");
		pNewSymptomAssessmentPage.answerDoYouHaveSymptom();

		log("step 6: Assert Text after  completing  Symptom Assessment ");
		assertTrue(verifyTextPresent(driver, "Thank you for "));
		/*
		 * completing our Symptom Assessment Patient Care Form."));
		 * assertTrue(verifyTextPresent(driver,
		 * "Your information has been sent directly to your healthcare provider."
		 * )); assertTrue(verifyTextPresent(driver,
		 * "Please feel free to contact us if you have any further questions about your Symptom Assessment."
		 * ));
		 */

		log("step 7: Logout of Patient Portal");
		pMyPatientPage.logout(driver);

		log("step 8: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 9: On Practice Portal Home page Click SymptomAssessmentTab");
		SymptomAssessmentFilterPage pSymptomAssessmentFilter = practiceHome.clicksymptomAssessmentTab();

		log("step 10: On Practice Portal Home page Click SymptomAssessmentTab");
		SymptomAssessmentDetailsPage pSymptomAssessmentDetailsPage = pSymptomAssessmentFilter.searchSymptomAssessment();

		log("step 11: Verification on SymptomAssessmentDetailsPage");
		assertTrue(verifyTextPresent(driver, "Date of Birth : 01/11/1987"));
		/*
		 * assertTrue(verifyTextPresent(driver,
		 * "Home Phone : (958) 963-1234"));
		 */
		assertTrue(verifyTextPresent(driver, "His reason for visit is \"Cough\"."));
		log("step 12: Sent Message to Patient");
		String practiceResponse = pSymptomAssessmentDetailsPage.sentMessage();

		log("step 13: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 14: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, patientData.geturl());
		pMyPatientPage = loginPage.login(patientData.getUsername(), patientData.getPassword());

		log("step 15: Go to Inbox");
		MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, MessageCenterInboxPage.PAGE_NAME);

		log("step 16: Find message in Inbox");
		MessagePage message = inboxPage.openMessageInInbox(practiceResponse);

		log("step 17: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(practiceResponse), "Expected subject containting [" + practiceResponse
				+ "but actual subject was [" + actualSubject + "]");
		

	}
	
	/**
	 * @Author:- rperkinsjr
	 * @Date:-30/April/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce: Login to Patient Portal Initiate new Virtual Office
	 *                    Vist Complete virtual office visit View Virtual
	 *                    Office Visit history Ensure item is listed in
	 *                    history Navigate to Practice Portal and login Go to
	 *                    Virtual Office Visit search page Initiate search
	 *                    Click on specific item process the item Logout of
	 *                    practice portal login to patient portal go to inbox
	 *                    find secure message open secure message
	 *                    ============
	 *                    =================================================
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testVirtualOfficeVisitEnd2End() throws Exception {
		log("Test Case: testVirtualOfficeVisitEnd2End");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Retrieving test case data");
		Portal portal = new Portal();
		TestcasesData patientData = new TestcasesData(portal);

		log("URL: " + patientData.geturl());
		log("USER NAME: " + patientData.getUsername());
		log("Password: " + patientData.getPassword());

		log("step 1: Patient login");
		PortalLoginPage login = new PortalLoginPage(driver, patientData.geturl());
		MyPatientPage home = login.login(patientData.getUsername(), patientData.getPassword());

		log("step 2: Start Virtual Office Visit");
		VirtualOfficeVisitProviderPage vovProvider = home.clickVirtualOfficeVisitLink();
		assertTrue(vovProvider.isPageLoaded(), VirtualOfficeVisitProviderPage.PAGE_NAME + " failed to load");
		PerformanceReporter.getPageLoadDuration(driver, VirtualOfficeVisitProviderPage.PAGE_NAME);

		log("step 3: Choose Vov Provider");
		// VirtualOfficeVisitPharmacyPage vovPharmacy =
		// vovProvider.chooseProviderAndContinue(patientData.getPreferredDoctor());
		VirtualOfficeVisitPharmacyPage vovPharmacy = vovProvider.chooseProviderAndContinue("");
		assertTrue(vovPharmacy.isPageLoaded(), VirtualOfficeVisitPharmacyPage.PAGE_NAME + " failed to load");
		PerformanceReporter.getPageLoadDuration(driver, VirtualOfficeVisitPharmacyPage.PAGE_NAME);

		log("step 4: Choose Vov Pharmacy");
		VirtualOfficeVisitQuestionnairePage vovQuestionnaire = vovPharmacy.choosePharmacyAndContinue("");

		log("step 5: Complete Vov Questionnaire and verify it submitted correctly");
		// Setup the date for searching later
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -1); // need to subtract one minute to
							// make
		// sure the search works.
		Date sentDate = cal.getTime();

		// Now go and complete the form
		VirtualOfficeVisitConfirmationPage vovConfirmation = vovQuestionnaire.completeQuestionnaire();
		assertTrue(vovConfirmation.isPageLoaded(), VirtualOfficeVisitConfirmationPage.PAGE_NAME + " failed to load.");
		assertTrue(verifyTextPresent(driver, "Thank you for submitting your Virtual Office Visit."));

		log("step 6: Check Vov History page for new item");
		VirtualOfficeVisitHistoryPage vovHistory = vovConfirmation.visitVirtualOfficeVistHistory();

		log("step 7: Open Vov History item");
		VirtualOfficeVisitHistoryDetailPage vovHistoryDetail = vovHistory.viewVirtualOfficeVisitHistoryDetails(sentDate);

		log("step 8: Validate Vov History item opened correctly");
		assertTrue(vovHistoryDetail.didHistoryDetailsLoad(), VirtualOfficeVisitHistoryDetailPage.PAGE_NAME + " failed to load");

		log("step 9: Logout of Patient Portal");
		home.logout(driver);

		log("step 10: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 11: Click Vov tab");
		VirtualOfficeVisitSearchPage vovSearch = practiceHome.clickVirtualOfficeVisitTab();
		PerformanceReporter.getPageLoadDuration(driver, VirtualOfficeVisitSearchPage.PAGE_NAME);
		assertTrue(vovSearch.isPageLoaded(), VirtualOfficeVisitSearchPage.PAGE_NAME + " failed to load");

		log("step 12: initiate search");
		vovSearch.doBasicSearch();

		log("step 13: select item from results");
		VirtualOfficeVisitTakeActionPage vovAction = vovSearch.getDetails(sentDate);
		assertTrue(vovAction.isPageLoaded(), VirtualOfficeVisitTakeActionPage.PAGE_NAME + " failed to load");

		log("step 14: choose Vov processing action");
		VirtualOfficeVisitOnlineVisitAndMedicationPage vovPrescribe = vovAction.takeActionOfOnlineVisitAndPrescribeMedication(null);
		assertTrue(vovPrescribe.isPageLoaded(), VirtualOfficeVisitOnlineVisitAndMedicationPage.PAGE_NAME + " failed to load");

		log("step 15: enter prescription and secure message details");
		VirtualOfficeVisitPracticeConfirmationPage vovConfirm = vovPrescribe.completePrescriptionAndCommunication("IHGQA Auto");
		assertTrue(vovConfirm.isPageLoaded(), VirtualOfficeVisitPracticeConfirmationPage.PAGE_NAME + " failed to load");

		log("step 16: confirm and submit");
		vovConfirm.confirmAndSubmit();
		assertTrue(verifyTextPresent(driver, "Your prescription and communication have been posted and the visit has been closed."));

		log("step 17: logout of practice portal");
		practiceHome.logOut();

		log("step 18: login to patient portal (again)");
		login = new PortalLoginPage(driver, patientData.geturl());
		home = login.login(patientData.getUsername(), patientData.getPassword());

		log("step 19: Go to Inbox");
		MessageCenterInboxPage inboxPage = home.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, MessageCenterInboxPage.PAGE_NAME);

		log("step 20: Find message in Inbox");
		String uniquePracticeResponse = vovPrescribe.getCreatedTs();
		MessagePage message = inboxPage.openMessageInInbox(uniquePracticeResponse);

		log("step 21: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
						+ uniquePracticeResponse + "but actual subject was [" + actualSubject + "]");

		log("step 22: Reply back to practice");
		inboxPage = message.replyToMessage(null,null);
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		Thread.sleep(10000);
		log("step 23: Logout of Patient Portal");
		home.logout(driver);

		log("step 24: Login to Practice Portal");
		Thread.sleep(10000);
		// Load up practice test data
		practice = new Practice();
		practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 25: Click Vov tab");
		vovSearch = practiceHome.clickVirtualOfficeVisitTab();
		assertTrue(vovSearch.isPageLoaded(), VirtualOfficeVisitSearchPage.PAGE_NAME + " failed to load");

		log("step 26: Click My Messages");
		PracticeMessagesSearchPage messageSearch = vovSearch.goToMyMessages();
		assertTrue(messageSearch.isPageLoaded(), PracticeMessagesSearchPage.PAGE_NAME + " failed to load");

		log("step 27: Search for messages");
		messageSearch.searchForVirtualOfficeVisitMessages();

		log("step 28: Find message in search results");
		PracticeMessagePage theMessage = messageSearch.retrieveMessage(uniquePracticeResponse);
		assertNotNull(theMessage, "The Patients reply to the Virtual Office Visit message was not received by the practice.");
		assertTrue(theMessage.isPageLoaded(), PracticeMessagePage.PAGE_NAME + " failed to load");

	}
	
	/**
	 * @Author: rperkinsjr
	 * @Date: 4/16/2013
	 * @StepsToReproduce: Login to Patient Portal Click on Ask A Staff/Doctor
	 *                    Fill out Ask A Staff details Submit Ask A Staff Go
	 *                    to Ask A Staff History page Validate submitted
	 *                    question is listed on History page Logout of
	 *                    Patient Portal Login to Practice Portal Search Ask
	 *                    A Questions Access details for submitted question
	 *                    Respond to question Logout of Practice Portal Login
	 *                    to Patient Portal Access Patient Inbox Validate Ask
	 *                    A Staff response is listed
	 *                    ==========================
	 *                    ===================================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAStaffEnd2End() throws Exception {
		log("Test Case: testAskAStaff");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		assertTrue(loginPage.isLoginPageLoaded(), "There was an error loading the login page");
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Click Ask A Staff");
		AskAStaffStep1Page askStaff1 = myPatientPage.clickAskAStaffLink();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffStep1Page.PAGE_NAME);

		log("step 4: Complete step 1 of Ask A Staff");
		AskAStaffStep2Page askStaff2 = askStaff1.askQuestion("Test", "This is generated from the testAskAStaff automation test case.");
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffStep2Page.PAGE_NAME);

		log("step 5: Complete step 2 of Ask A Staff");
		AskAStaffStep3Page askStaff3 = askStaff2.submitQuestion();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffStep3Page.PAGE_NAME);

		log("step 6: Validate entry is on Ask A Staff History page");
		AskAStaffHistoryPage aasHistory = askStaff3.clickAskAStaffHistory();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffHistoryPage.PAGE_NAME);
		assertTrue(aasHistory.isAskAStaffOnHistoryPage(Long.toString(askStaff1.getCreatedTimeStamp())), "Expected to see a subject containing "
						+ askStaff1.getCreatedTimeStamp() + " on the Ask A Staff History page. None were found.");

		log("step 7: Logout of Patient Portal");
		myPatientPage.logout(driver);

		log("step 8: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 9: Click Ask A Staff tab");
		AskAStaffSearchPage aasSearch = practiceHome.clickAskAStaffTab();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffSearchPage.PAGE_NAME);

		log("step 10: Search for questions");
		aasSearch.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = aasSearch.getQuestionDetails(Long.toString(askStaff1.getCreatedTimeStamp()));
		assertNotNull(detailStep1, "The submitted patient question was not found in the practice");
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep1Page.PAGE_NAME);

		log("step 11: Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1.chooseProvideAdviceOnly();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep2Page.PAGE_NAME);

		log("step 12: Respond to patient question");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2.processAndCommunicate("Automated Test",
						"This message was generated by an automated test");
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep3Page.PAGE_NAME);

		log("step 13: Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3.confirmProcessedQuestion();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep4Page.PAGE_NAME);

		log("step 14: Validate submit of confirmation");
		detailStep4.isQuestionDetailPageLoaded();

		log("step 15: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 16: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 17: Go to Inbox");
		MessageCenterInboxPage inboxPage = myPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, MessageCenterInboxPage.PAGE_NAME);

		log("step 18: Find message in Inbox");
		String uniquePracticeResponse = Long.toString(detailStep2.getCreatedTimeStamp());
		MessagePage message = inboxPage.openMessageInInbox(uniquePracticeResponse);

		log("step 19: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
				+ uniquePracticeResponse + "but actual subject was [" + actualSubject + "]");
	}
	
	/**
	 * @Author:- bkrishnankutty
	 * @Date:-3/29/2013
	 * @User Story ID in Rally :
	 * @StepsToReproduce: Login to Patient Portal Click on My Account Click
	 *                    FamilyLink Click on Add a Family MemberLink on
	 *                    CreatefamilymemberPage createFamilyMember Delete
	 *                    the account created
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFamilyAccount() throws Exception {
		
		//Instancing FamilyAccountTest
		FamilyAccountTest familyAccountTest  = new FamilyAccountTest();
		
		//Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);
		
		//Executing test
		familyAccountTest.FamilyAccount(driver, testcasesData);
		
	}
	
	/**
	 * @Author:- shanthala
	 * @Date:-4/April/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce: Login to Patient Portal Click on myaccountLink on
	 *                    MyPatientPage Click on insuranceLink on
	 *                    MyAccountPage Add Insurance Assert it Remove
	 *                    Insurance
	 *                    ==========================================
	 *                    ===================
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddInsurance() throws Exception {
		log("Test Case: testAddInsurance");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 3:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 4:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

		log("step 5:Click on insuranceLink  on MyAccountPage");
		InsurancePage pinsuranceDetailsPage = pMyAccountPage.addInsuranceLink();

		log("step 6:check for existing insurances and delete");
		pinsuranceDetailsPage.deleteAllInsurances();

		log("step 7:Start to add Insurance details");
		pinsuranceDetailsPage.addInsuranceDetails();

		log("step 8:asserting for Insurance Name and Insurance Type");
		Thread.sleep(20000);
		assertTrue(verifyTextPresent(driver, PortalConstants.InsuranceName));
		assertTrue(verifyTextPresent(driver, PortalConstants.InsuranceType));

		log("step 9:Click on delete button to delete Insurance added");
		pinsuranceDetailsPage.deleteInsurance();

		log("step 10:asserting for Insurance Name and Insurance Type not present after Insurance deleted");
		Thread.sleep(20000);
		assertFalse(verifyTextNotPresent(driver, PortalConstants.InsuranceName));
		assertFalse(verifyTextNotPresent(driver, PortalConstants.InsuranceType));
	}
	
	/**
	 * @Author:- bkrishnankutty
	 * @Date:-2/6/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce: Login to Patient Portal Click on My Account Click
	 *                    Wallet Link Add credit Card Remove added Credit
	 *                    Card
	 *                    ================================================
	 *                    =============
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddandChangeCreditCard() throws Exception {

		log("Test Case: testAddandChangeCreditCard");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 3:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 4:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

		log("step 5:Click on Wallet Link  on MyAccountPage");
		WalletPage pWalletPage = pMyAccountPage.clickWalletLink();

		log("step 6:Add credit card ");
		pWalletPage.addCreditCardDetails(PortalConstants.CardholderName, PortalConstants.CreditCardType, PortalConstants.CreditCardNumber,
						testcasesData.getDob_Month(), PortalConstants.Year, testcasesData.getZip());

		log("step 7:Verify correct CC details get added");
		pWalletPage.verifyCreditCardDetails(PortalConstants.CardholderName, PortalConstants.CreditCardType, PortalConstants.Month,
						PortalConstants.Year);

		log("Delete the new added Credit card");
		pWalletPage.removeCreditCard(PortalConstants.CreditCardType);

		log("step 13:Click on Add a Family MemberLink  on CreatefamilymemberPage ");
		pWalletPage.logout(driver);
	}

	

	
	

	
	
	
	

	
	


}
