package com.intuit.ihg.product.fundamentalArchitects.test;

import static org.testng.Assert.assertNotNull;

import org.bouncycastle.asn1.ess.SigningCertificate;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.product.mobile.page.MobileHomePage;
import com.intuit.ihg.product.mobile.page.MobileSignInPage;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageInboxPage;
import com.intuit.ihg.product.mobile.utils.Mobile;
import com.intuit.ihg.product.mobile.utils.MobileTestCaseData;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxMessage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxPage;
import com.intuit.ihg.product.portal.page.inbox.OfficeMessagesPage;
import com.intuit.ihg.product.portal.page.inbox.ThreeTabbedSolutionPage;
import com.intuit.ihg.product.portal.page.makePaymentpage.MakePaymentPage;
import com.intuit.ihg.product.portal.page.newRxRenewalpage.NewRxRenewalPage;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep1Page;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep2Page;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep3Page;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep4Page;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.ApptRequestHistoryPage;
import com.intuit.ihg.product.portal.page.solutions.askstaff.AskAStaffHistoryPage;
import com.intuit.ihg.product.portal.page.solutions.askstaff.AskAStaffStep1Page;
import com.intuit.ihg.product.portal.page.solutions.askstaff.AskAStaffStep2Page;
import com.intuit.ihg.product.portal.page.solutions.askstaff.AskAStaffStep3Page;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestSearchPage;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffQuestionDetailStep1Page;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffQuestionDetailStep2Page;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffQuestionDetailStep3Page;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffQuestionDetailStep4Page;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffSearchPage;
import com.intuit.ihg.product.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalSearchPage;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;

public class MessageSync extends BaseTestNGWebDriver {

	/**
	 * @Author: jcalabek
	 * @Date: 8/29/2013
	 * @StepsToReproduce: Login to Patient Portal Click on Appointment Button
	 *                    on My Patient Page Complete Appointment Request
	 *                    Step1 Page Complete Appointment Request Step2 Page
	 *                    Complete Appointment Request Step3 Page Complete
	 *                    Appointment Request Step4 Page Navigate to Appt
	 *                    Request History Page Logout from Patient Portal
	 *                    Login to Practice Portal Click Appt Request tab
	 *                    Search for appt requests Choose process option and
	 *                    respond to patient Confirm response details to
	 *                    patient Logout of Practice Portal Login to Patient
	 *                    Portal Go to Inbox Find message checking whether
	 *                    message status is unread. Moving to Old messaging
	 *                    via Three tabbed solution and checking message
	 *                    state(unread) Then moving to Mobile and checking
	 *                    message status(unread) ==============
	 *                    ===============================================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void appointmentRequest() throws Exception {

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

		log("step 3: Click on Appointment Button on My Patient Page");
		AppointmentRequestStep1Page apptRequestStep1 = myPatientPage.clickAppointmentRequestTab();

		log("step 4: Complete Appointment Request Step1 Page  ");
		AppointmentRequestStep2Page apptRequestStep2 = apptRequestStep1.requestAppointment(testcasesData.getTitle(),
						testcasesData.getPreferredLocation(), testcasesData.getPreferredDoctor(), null);

		log("step 5: Complete Appointment Request Step2 Page  ");
		AppointmentRequestStep3Page apptRequestStep3 = apptRequestStep2.fillInForm(PortalConstants.PreferredTimeFrame,
						PortalConstants.PreferredDay, PortalConstants.ChoosePreferredTime, PortalConstants.ApptReason,
						PortalConstants.WhichIsMoreImportant, testcasesData.getPhoneNumber());

		log("step 6: Complete Appointment Request Step3 Page  ");
		AppointmentRequestStep4Page apptRequestStep4 = apptRequestStep3.clickSubmit();

		log("step 7: Complete Appointment Request Step4 Page  ");
		myPatientPage = apptRequestStep4.clickBackToMyPatientPage();

		log("step 8: Navigate to Appt Request History Page");
		ApptRequestHistoryPage apptRequestHistory = myPatientPage.clickApptRequestHistoryLink();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestHistoryPage.PAGE_NAME);

		log("step 9 :Logout from Patient Portal ");
		myPatientPage.logout(driver);

		log("step 10: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 11: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestSearchPage.PAGE_NAME);

		log("step 12: Search for appt requests");
		apptSearch.searchForApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(Long.toString(apptRequestStep2.getCreatedTs()));
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestDetailStep1Page.PAGE_NAME);

		log("step 13: Choose process option and respond to patient");
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmit();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestDetailStep2Page.PAGE_NAME);

		log("step 15: Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("step 16: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 17: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 18: Go to Inbox");
		ConsolidatedInboxPage inboxPage = myPatientPage.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		String uniquePracticeResponse = Long.toString(detailStep1.getCreatedTs());

		log("Unique Practice Response is: " + uniquePracticeResponse);

		log("step 19: Find message in Inbox");
		verifyTrue(inboxPage.findUnreadMessage(uniquePracticeResponse), "Message was not found.");

		inboxPage.openMessageInInbox(uniquePracticeResponse);
		myPatientPage.clickMymessages();

		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);
		verifyFalse(inboxPage.findUnreadMessage(uniquePracticeResponse), "Message was not found.");
		ThreeTabbedSolutionPage threeTabbedSolution = inboxPage.clickThreeTabbedSolution();

		log("step 19: Movin to Three tabed solution and location the message");
		OfficeMessagesPage officeMessagesPage = threeTabbedSolution.clickOfficeMessages();

		verifyFalse(officeMessagesPage.findUnreadMessage(uniquePracticeResponse));

		log("step 20 :Logout from Patient Portal ");
		myPatientPage.logout(driver);

		// Checking on the Mobile

		log("step 21 :Signing to the Mobile");
		Mobile mobile = new Mobile();

		MobileTestCaseData mobileTestCaseData = new MobileTestCaseData(mobile);

		MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, mobileTestCaseData.getUrl());

		MobileHomePage mobileHomePage = mobileSignInPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 22 :Moving to my messages");
		MessageInboxPage messageInboxPage = mobileHomePage.clickMyMessages();

		log("step 23 :Checking message");
		verifyTrue(messageInboxPage.findMessage(uniquePracticeResponse), "Message found");

		log("step 24 :Checking message");
		verifyFalse(messageInboxPage.findUnreadMessage(uniquePracticeResponse), "Message found");

	}

	/**
	 * @Author: jcalabek
	 * @Date: 8/30/2013
	 * @StepsToReproduce: Login to Patient Portal Click on BillPay Button
	 *                    on My Patient Page Complete BillPay Request
	 *                    Step1 Page Complete BillPay Request Step2 Page
	 *                    Complete BillPay Request Step3 Page Complete
	 *                    BillPay Request Step4 Page. Logout from Patient Portal
	 *                    Login to Practice Portal Click BillPay Request tab
	 *                    Search for BillPay requests Choose process option and
	 *                    respond to patient Confirm response details to
	 *                    patient Logout of Practice Portal Login to Patient
	 *                    Portal Go to Inbox Find message checking whether
	 *                    message status is unread. Moving to Old messaging
	 *                    via Three tabbed solution and checking message
	 *                    state(unread) Then moving to Mobile and checking
	 *                    message status(unread) ==============
	 *                    ===============================================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBillPayment() throws Exception {

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
		verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

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
		ConsolidatedInboxPage inboxPage = myPatientPage.clickViewAllMessages();
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		String uniquePracticeResponse = Long.toString(onlineBillPaySearchPage.getCreatedTs()) + PracticeConstants.BillPaymentSubject;

		log("step 13: Find message in Inbox");
		verifyTrue(inboxPage.findUnreadMessage(uniquePracticeResponse), "Message was not found.");
		ConsolidatedInboxMessage message = inboxPage.clickMessageLinkOpenMessageInInbox(uniquePracticeResponse);

		log("step 14: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
						+ uniquePracticeResponse + "but actual subject was [" + actualSubject + "]");
		myPatientPage.clickMymessages();

		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);
		verifyFalse(inboxPage.findUnreadMessage(uniquePracticeResponse), "Message was not found.");
		ThreeTabbedSolutionPage threeTabbedSolution = inboxPage.clickThreeTabbedSolution();

		log("step 15: Movin to Three tabed solution and location the message");
		OfficeMessagesPage officeMessagesPage = threeTabbedSolution.clickOfficeMessages();

		verifyFalse(officeMessagesPage.findUnreadMessage(uniquePracticeResponse));

		log("step 16 :Logout from Patient Portal ");
		myPatientPage.logout(driver);

		// Checking on the Mobile

		log("step 17 :Signing to the Mobile");
		Mobile mobile = new Mobile();

		MobileTestCaseData mobileTestCaseData = new MobileTestCaseData(mobile);

		MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, mobileTestCaseData.getUrl());

		MobileHomePage mobileHomePage = mobileSignInPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 18 :Moving to my messages");
		MessageInboxPage messageInboxPage = mobileHomePage.clickMyMessages();

		log("step 19 :Checking message");
		verifyTrue(messageInboxPage.findMessage(uniquePracticeResponse), "Message found");

		log("step 20 :Checking message");
		verifyFalse(messageInboxPage.findUnreadMessage(uniquePracticeResponse), "Message found");

	}
	
	/**
	 * @Author: jcalabek
	 * @Date: 8/30/2013
	 * @StepsToReproduce: Login to Patient Portal Click on RxRenewal Button
	 *                    on My Patient Page Complete RxRenewal Request
	 *                    Step1 Page Complete RxRenewal Request Step2 Page
	 *                    Complete RxRenewal Request Step3 Page Complete
	 *                    RxRenewal Request Step4 Page. Logout from Patient Portal
	 *                    Login to Practice Portal Click RxRenewal Request tab
	 *                    Search for RxRenewal requests Choose process option and
	 *                    respond to patient Confirm response details to
	 *                    patient Logout of Practice Portal Login to Patient
	 *                    Portal Go to Inbox Find message checking whether
	 *                    message status is unread. Moving to Old messaging
	 *                    via Three tabbed solution and checking message
	 *                    state(unread) Then moving to Mobile and checking
	 *                    message status(unread) ==============
	 *                    ===============================================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRxRenewal() throws Exception {

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
		verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

		log("step 4: Click on PrescriptionRenewal Link ");
		NewRxRenewalPage newRxRenewalPage = myPatientPage.clickPrescriptionRenewal();

		log("step 5: Enter Details");
		newRxRenewalPage.chooseProvider(testcasesData.getpreferredprovider());

		log("set Medication Fields in NewRxRenewal Page");
		newRxRenewalPage.setMedicationFields();

		log("set Pharmacy Fields in NewRxRenewal Page");
		newRxRenewalPage.setPharmacyFields();

		log("Verify RxRenewal Confirmation Message");
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 5, newRxRenewalPage.renewalConfirmationmessage);
		verifyEquals(newRxRenewalPage.renewalConfirmationmessage.getText(), PortalConstants.RenewalConfirmation);

		log("step 6: Logout of Patient Portal");
		myPatientPage.logout(driver);

		// Load up practice test data
		log("step 7: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 8:Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome.clickonRxRenewal();

		log("step 9:Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday();

		log("Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		log("Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.setRxRenewalFields();

		log("Click On Process RxRenewal Button in Practice Portal");
		rxRenewalSearchPage.clickProcessRxRenewal();

		log("Verify Prescription Confirmation in Practice Portal");
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection();

		log("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		log("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		log("step 10: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 11: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 12: Go to Inbox");
		ConsolidatedInboxPage inboxPage = myPatientPage.clickViewAllMessages();
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		String uniquePracticeResponse = Long.toString(rxRenewalSearchPage.getCreatedTs())+PortalConstants.RxRenewalSubject;
		
		log("step 13: Find message in Inbox");
		verifyTrue(inboxPage.findUnreadMessage(uniquePracticeResponse), "Message was not found.");
		
		log("step 14: Find message in Inbox And Validate Message Subject");
		ConsolidatedInboxMessage message = inboxPage.clickMessageLinkOpenMessageInInbox(uniquePracticeResponse);

		log("step 15: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
				+ uniquePracticeResponse + "but actual subject was [" + actualSubject + "]");
		
		myPatientPage.clickMymessages();

		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);
		verifyFalse(inboxPage.findUnreadMessage(uniquePracticeResponse), "Message was not found.");
		ThreeTabbedSolutionPage threeTabbedSolution = inboxPage.clickThreeTabbedSolution();

		log("step 16: Movin to Three tabed solution and location the message");
		OfficeMessagesPage officeMessagesPage = threeTabbedSolution.clickOfficeMessages();

		verifyFalse(officeMessagesPage.findUnreadMessage(uniquePracticeResponse));

		log("step 17 :Logout from Patient Portal ");
		myPatientPage.logout(driver);

		// Checking on the Mobile

		log("step 18 :Signing to the Mobile");
		Mobile mobile = new Mobile();

		MobileTestCaseData mobileTestCaseData = new MobileTestCaseData(mobile);

		MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, mobileTestCaseData.getUrl());

		MobileHomePage mobileHomePage = mobileSignInPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 19 :Moving to my messages");
		MessageInboxPage messageInboxPage = mobileHomePage.clickMyMessages();

		log("step 20 :Checking message");
		verifyTrue(messageInboxPage.findMessage(uniquePracticeResponse), "Message found");

		log("step 21 :Checking message");
		verifyFalse(messageInboxPage.findUnreadMessage(uniquePracticeResponse), "Message found");

	}
	
	/**
	 * @Author: jcalabek
	 * @Date: 8/30/2013
	 * @StepsToReproduce: Login to Patient Portal Click on AskAQuestion Button
	 *                    on My Patient Page Complete AskAQuestion Request
	 *                    Step1 Page Complete AskAQuestion Request Step2 Page
	 *                    Complete AskAQuestion Request Step3 Page Complete
	 *                    AskAQuestion Request Step4 Page. Logout from Patient Portal
	 *                    Login to Practice Portal Click AskAQuestion Request tab
	 *                    Search for AskAQuestion requests Choose process option and
	 *                    respond to patient Confirm response details to
	 *                    patient Logout of Practice Portal Login to Patient
	 *                    Portal Go to Inbox Find message checking whether
	 *                    message status is unread. Moving to Old messaging
	 *                    via Three tabbed solution and checking message
	 *                    state(unread) Then moving to Mobile and checking
	 *                    message status(unread) ==============
	 *                    ===============================================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAStaff() throws Exception {
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
		assertTrue(aasHistory.isAskAStaffOnHistoryPage(Long.toString(askStaff1.getCreatedTimeStamp())),
				"Expected to see a subject containing " + askStaff1.getCreatedTimeStamp()
						+ " on the Ask A Staff History page. None were found.");

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
		ConsolidatedInboxPage inboxPage = myPatientPage.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		log("step 18: Find message in Inbox");
		String uniquePracticeResponse = Long.toString(detailStep2.getCreatedTimeStamp());
		verifyTrue(inboxPage.findUnreadMessage(uniquePracticeResponse), "Message was not found.");
		ConsolidatedInboxMessage message = inboxPage.openMessageInInbox(uniquePracticeResponse);

		log("step 19: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
				+ uniquePracticeResponse + "but actual subject was [" + actualSubject + "]");
		
		myPatientPage.clickMymessages();

		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);
		verifyFalse(inboxPage.findUnreadMessage(uniquePracticeResponse), "Message was not found.");
		ThreeTabbedSolutionPage threeTabbedSolution = inboxPage.clickThreeTabbedSolution();

		log("step 20: Movin to Three tabed solution and location the message");
		OfficeMessagesPage officeMessagesPage = threeTabbedSolution.clickOfficeMessages();

		verifyFalse(officeMessagesPage.findUnreadMessage(uniquePracticeResponse));

		log("step 21 :Logout from Patient Portal ");
		myPatientPage.logout(driver);

		// Checking on the Mobile

		log("step 22 :Signing to the Mobile");
		Mobile mobile = new Mobile();

		MobileTestCaseData mobileTestCaseData = new MobileTestCaseData(mobile);

		MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, mobileTestCaseData.getUrl());

		MobileHomePage mobileHomePage = mobileSignInPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 23 :Moving to my messages");
		MessageInboxPage messageInboxPage = mobileHomePage.clickMyMessages();

		log("step 24 :Checking message");
		verifyTrue(messageInboxPage.findMessage(uniquePracticeResponse), "Message found");

		log("step 25 :Checking message");
		verifyFalse(messageInboxPage.findUnreadMessage(uniquePracticeResponse), "Message found");
	}
	
}
