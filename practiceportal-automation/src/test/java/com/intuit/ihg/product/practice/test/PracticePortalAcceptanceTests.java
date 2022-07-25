//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.practice.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.common.utils.YopMail;
import com.medfusion.pojos.Patient;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.common.utils.PatientFactory;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.familyManagement.PatientTrustedRepresentativePage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.medfusion.product.object.maps.practice.page.patientMessaging.PatientMessagingPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.object.maps.practice.page.treatmentplanpage.TreatmentPlansPage;
import com.medfusion.product.patientportal2.utils.PortalUtil2;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.api.utils.PracticeUtil;
import com.medfusion.product.practice.api.utils.ReadFilePath;
import com.medfusion.product.practice.tests.VirtualCardSwiperTest;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.AuthUserLinkAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage.JalapenoForgotPasswordPage4;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MedicationsPage.MedicationsHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;

import static org.testng.Assert.*;

import java.io.IOException;

public class PracticePortalAcceptanceTests extends BaseTestNGWebDriver {

	private ReadFilePath path = new ReadFilePath();
	private PropertyFileLoader testData;

	public PracticePortalAcceptanceTests() throws Exception {
		path.getFilepath(PracticeConstants.FILE_DIRECTORY);
	}

	@BeforeClass(alwaysRun = true)
	public void prepareTestData() throws IOException {
		log("Getting Test Data");
		testData = new PropertyFileLoader();
	}

	/**
	 * @Author:- rperkinsjr
	 * @Date:-3/27/2013
	 * @User Story ID in Rally : NA
	 * @StepsToReproduce: Navigate to login page Enter credentials and login
	 *                    Validate Home page loads Click 'Sign Out' Validate logout
	 *                    occurred (should redirect back to login page)
	 * @AreaImpacted:- Description
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginLogout() throws Exception {
		logStep("Navigate to Practice portal login page");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PerformanceReporter.getPageLoadDuration(driver, PracticeLoginPage.PAGE_NAME);

		logStep("Login to Practice Portal");
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		PerformanceReporter.getPageLoadDuration(driver, PracticeHomePage.PAGE_NAME);
		assertTrue(practiceHome.isHomePageLoaded(),
				"Expected to see 'Recent Activity' on home page, but it was not found");

		logStep("Click sign out");
		practiceLogin = practiceHome.logOut();
		assertTrue(practiceLogin.isLoginPageLoaded(), "Expected to see login page");
	}

	/**
	 * @Author: bbinisha User Story : US6575
	 * @Date: 07/26/2013
	 * @StepsToReproduce: Practice portal login Click on Manage TreatmentPlan Click
	 *                    on 'Submit' button Enter the treatment info Click on
	 *                    'Create TreatmentPlan' button.
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testCreateTreatmentPlan() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Navigate to Manage TreatmentPlan page.");
		TreatmentPlansPage treatmentPlan = practiceHome.clickOnManageTreatmentPlan();

		logStep("Verify the Treatment plan page is displayed properly.");
		treatmentPlan.checkTreatmentPlanPage();

		logStep("Click on submit button.");
		treatmentPlan.selectAppointmentRequest();
		treatmentPlan.clickOnSubmitButton();

		logStep("Add treatment plan info and create treatment plan.");
		treatmentPlan.createTreatmentPlanInfo(PracticeConstants.TREATMENT_PLAN_TITLE,
				PracticeConstants.TREATMENT_PLAN_SUBJECT, PracticeConstants.TREATMENT_PLAN_BODY);

		logStep("Verify whether the treatmentPlan is added Successfully.");
		assertEquals(
				treatmentPlan.checkTreatmentPlanSuccessMsg().contains(PracticeConstants.TREATMENT_PLAN_SUCCESS_MSG),
				true, "Treatment plan is not added properly.");
	}

	/**
	 * @Author: bbinisha refactored by Prokop Rehacek User Story : US6579
	 * @Date: 07/26/2013
	 * @StepsToReproduce: Practice portal login Click on 'Virtual Card Swiper' Enter
	 *                    the card info Click on 'Click Here To Charge The Card'
	 *                    button. Verify the Success Message.
	 * @AreaImpacted:
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testVirtualCardSwiper() throws Exception {
		VirtualCardSwiperTest virtualCardSwiperTest = new VirtualCardSwiperTest();

		virtualCardSwiperTest.virtualCardSwipeTest(driver, testData, PracticeConstants.CARD_TYPE_VISA);
	}

	/**
	 * @Author: Prokop Rehacek User Story : US6579
	 * @Date: 3/11/2014
	 * @StepsToReproduce: Practice portal login Click on 'Virtual Card Swiper' Enter
	 *                    the card info Click on 'Click Here To Charge The Card'
	 *                    button. Verify the Success Message.
	 * @AreaImpacted:
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testVirtualCardSwiperPayPal() throws Exception {
		VirtualCardSwiperTest virtualCardSwiperTest = new VirtualCardSwiperTest();

		virtualCardSwiperTest.setSwipeString(PracticeConstants.SWIPE_STRING_MASTERCARD);
		virtualCardSwiperTest.virtualCardSwipeTest(driver, testData, PracticeConstants.CARD_TYPE_MASTERCARD);
	}

	/**
	 * @Author: Kiran_GT
	 * @Date: 07/26/2013
	 * @StepsToReproduce: Practice Portal login Click on Patient Search Link Set
	 *                    Patient Search Fields,Verify Patient Search Result
	 * @AreaImpacted:
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientSearchLink() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(),
				testData.getDoctorPassword());

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		logStep("Search for patient in Patient Search");
		// pPatientSearchPage.setPatientSearchFields();
		pPatientSearchPage.searchForPatientInPatientSearch(PracticeConstants.PATIENT_FIRST_NAME,
				PracticeConstants.PATIENT_LAST_NAME);

		logStep("Verify the Search Result");
		IHGUtil.waitForElement(driver, 30, pPatientSearchPage.searchResult);
		assertEquals(true, pPatientSearchPage.searchResult.getText().contains(PracticeConstants.PATIENT_FIRST_NAME));
	}

	/**
	 * @Author: Prokop Rehacek
	 * @Date: 04/14/2014
	 * @StepsToReproduce: 1. Practice Portal login 2. Click on Patient Search Link
	 *                    3. Set Patient Search Fields, 4. Open patient dashboard 5.
	 *                    Click on Edit link next to Email 6. Change email address
	 *                    7. Verify that it was changed
	 * @AreaImpacted:
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testChangePatientEmail() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(),
				testData.getDoctorPassword());

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("change.email.first.name"),
				testData.getProperty("change.email.last.name"));

		logStep("Open Patient Dashboard");
		PatientDashboardPage pPatientDashboardPage = pPatientSearchPage.clickOnPatient(
				testData.getProperty("change.email.first.name"), testData.getProperty("change.email.last.name"));

		logStep("Click Edit email");
		pPatientSearchPage = pPatientDashboardPage.clickEditEmail();

		logStep("Update email");
		pPatientDashboardPage = pPatientSearchPage.changeEmail(testData.getProperty("change.email.new.email"));
		assertEquals(true, pPatientDashboardPage.getFeedback().contains("Patient Email Address / User Id Was Updated"));
	}

	/**
	 * @Author: Prokop Rehacek
	 * @Date: 07/26/2013
	 * @StepsToReproduce:
	 * @AreaImpacted:
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendUserIdEmail() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(),
				testData.getDoctorPassword());

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("forgot.username.first.name"),
				testData.getProperty("forgot.username.last.name"));
		PatientDashboardPage pPatientDashboardPage = pPatientSearchPage.clickOnPatient(
				testData.getProperty("forgot.username.first.name"), testData.getProperty("forgot.username.last.name"));

		logStep("Send Email reminder with User ID");
		pPatientSearchPage = pPatientDashboardPage.sendEmailUserID();

		logStep("Click Send Email");
		pPatientDashboardPage = pPatientSearchPage.sendUserNameEmail();
		assertTrue(pPatientDashboardPage.getFeedback().contains("Username email sent to patient"),
				"No success message on send!");

		logStep("Access Mailinator and check for received email");
		YopMail mail = new YopMail(driver);
		assertTrue(mail.getEmailContent(testData.getProperty("forgot.username.mail"),
				testData.getProperty("forgot.username.mail.subject"), testData.getProperty("forgot.username.login"),
				20), "Mail not received after max retries");
	}

	/**
	 * @Author: Gajendran
	 * @Date: 07/26/2013
	 * @StepsToReproduce: Patient login
	 * @AreaImpacted:
	 */

	@Test(enabled = true, groups = { "SmokeTest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testClickOnTabs() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		logStep("Click on Patient Activation Tab");
		practiceHome.clickPatientactivationTab();
		Thread.sleep(2000);

		logStep("Click on Virtual Card Swiper Tab");
		practiceHome.clickVirtualCardSwiperTab();
		Thread.sleep(2000);

		logStep("Click on Patient Messaging Tab");
		practiceHome.clickOnlineBillPayTab();
		Thread.sleep(2000);

		logStep("Click on Logout");
		practiceHome.logOut();
	}

	/**
	 * @Author: Kiran_GT
	 * @Date: 07/26/2013
	 * @StepsToReproduce: Practice Portal login Click on Patient Messaging
	 *                    Link-->Quick Send Set Quick Send Fields and verify for
	 *                    Message Sent text
	 * @AreaImpacted:
	 */
	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testQuickSendPatientPDF() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(),
				testData.getDoctorPassword());

		logStep("Click on Patient Search Link");
		PatientMessagingPage pPatientMessagingPage = pPracticeHomePage.clickPatientMessagingTab();

		logStep("Getting the document to upload from filepath");
		PracticeUtil pUtil = new PracticeUtil(driver);
		String value = pUtil.getFilepath(PracticeConstants.FILE_DIRECTORY).concat(PracticeConstants.PDF_NAME);

		logStep("Set Quick Send Fields");
		pPatientMessagingPage.setFieldsAndPublishMessage(value);

		logStep("Verify the Published Message Succesfully text");
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 20, pPatientMessagingPage.publishedSuccessfullyMessage);
		assertEquals(pPatientMessagingPage.publishedSuccessfullyMessage.getText(), "Message Published Successfully");
	}

	/**
	 * @Author: Kiran_GT
	 * @Date: 07/29/2013
	 * @StepsToReproduce: Practice Portal login Click on Online Bill Payment--->Make
	 *                    Payment For Patient Search for Patient,Make Payment for
	 *                    Patient and Verify for Payment Successfull Message
	 * @AreaImpacted:
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testMakePaymentForPatient() throws Exception {
		logStep("" + "Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getProperty("doctor.login.payment"),
				testData.getProperty("doctor.password.payment"));

		logStep("Click On Online BillPayment Tab in Practice Portal--->Make Payment For Patient");
		PayMyBillOnlinePage pPayMyBillOnlinePage = pPracticeHomePage.clickMakePaymentForPatient();

		logStep("Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(PracticeConstants.PATIENT_FIRST_NAME,
				PracticeConstants.PATIENT_LAST_NAME);

		logStep("Set Patient Transaction Fields");
		pPayMyBillOnlinePage.setPatientTransactionFields();

		logStep("Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 20, pPayMyBillOnlinePage.paymentConfirmationText);
		assertTrue(pPayMyBillOnlinePage.paymentConfirmationText.getText()
				.contains(PracticeConstants.PAYMENT_SUCCESSFULL_TEXT));
	}

	/**
	 * @Author: bbinisha User Story : US6488
	 * @Date: 08/01/2013
	 * @StepsToReproduce: Practice portal login Click on 'Online Billpay' Click on
	 *                    'Make Payment' Link Search for the patient Enter the
	 *                    Payment info Click on 'Submit Payment' button. Click on
	 *                    Search Patient Page Verify whether the transaction is
	 *                    displayed. Navigate to that transaction page Click on
	 *                    'Void Payment' button. Enter Void comment Close the Pop Up
	 *                    Click On 'Refund Payment' button. Check whethr the patient
	 *                    got email in gmail inbox notifying the void transaction
	 *                    done. *** Checking gmail part has to be completed***
	 * @AreaImpacted:
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testOnlineBillPayProcess() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("doctor.login.payment"),
				testData.getProperty("doctor.password.payment"));

		logStep("Click on Make Payment link.");
		PayMyBillOnlinePage pPayMyBillOnlinePage = practiceHome.clickMakePaymentForPatient();

		logStep("Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(testData.getProperty("online.bill.pay.first.name"),
				testData.getProperty("online.bill.pay.last.name"));

		String amount = IHGUtil.createRandomNumericStringInRange(5, 500);
		log("Random generated amount: " + amount);

		logStep("Set all the transaction details");
		pPayMyBillOnlinePage.setTransactionsForOnlineBillPayProcess(PracticeConstants.LOCATION,
				PracticeConstants.PROVIDER, PracticeConstants.CARD_NUMBER, amount,
				PracticeConstants.PROCESS_CARD_HOLDER_NAME, PracticeConstants.CARD_NUMBER,
				PracticeConstants.CARD_TYPE_VISA);

		logStep("Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 20, pPayMyBillOnlinePage.paymentConfirmationText);
		assertEquals(true, pPayMyBillOnlinePage.paymentConfirmationText.getText()
				.contains(PracticeConstants.PAYMENT_SUCCESSFULL_TEXT));

		logStep("Navigate to Patient Search Page.");
		OnlineBillPaySearchPage onlineBillPay = new OnlineBillPaySearchPage(driver);
		PatientSearchPage patientsearchPage = onlineBillPay.clickOnPatientSearchLink();

		logStep("Search the patient in Patient Search page.");
		patientsearchPage.searchPatient(PracticeConstants.PATIENT_FIRST_NAME, PracticeConstants.PATIENT_LAST_NAME);

		logStep("Verify whether the transaction is present.");
		assertTrue(patientsearchPage.isTransactionPresent(amount, PracticeConstants.PATIENT_FIRST_NAME,
				PracticeConstants.PATIENT_LAST_NAME));

		logStep("Select the particular Transaction from the Search Result.");
		patientsearchPage.selectTheTransaction(amount, PracticeConstants.PATIENT_FIRST_NAME,
				PracticeConstants.PATIENT_LAST_NAME);
		assertFalse(pPayMyBillOnlinePage.isVoidTransactionPresent());

		logStep("Click on Void Payment Link and void the transaction.");
		pPayMyBillOnlinePage.voidPayment(PracticeConstants.VOID_COMMENT);
		assertTrue(pPayMyBillOnlinePage.isVoidTransactionPresent());

		/*
		 * This needs to be checked if it can work after QB is working again
		 * log("Step 13 : Click on Refund Payment and give comments and amount to refund"
		 * ); pPayMyBillOnlinePage.refundPayment(amount,
		 * PracticeConstants.REFUND_COMMENT);
		 */

		// Checking email needs to be completed
	}

	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testBudgetPaymentProcess() throws Exception {
		String amount = "50";
		String prepayamount = "10";

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("doctor.login.payment"),
				testData.getProperty("doctor.password.payment"));
		logStep("Click on Make Payment link.");
		PayMyBillOnlinePage pPayMyBillOnlinePage = practiceHome.clickMakePaymentForPatient();

		logStep("Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(testData.getProperty("online.bill.pay.first.name"),
				testData.getProperty("online.bill.pay.last.name"));

		logStep("Set all the transaction details");
		pPayMyBillOnlinePage.setTransactionsForBudgetPaymentPlan(PracticeConstants.LOCATION, PracticeConstants.PROVIDER,
				PracticeConstants.CARD_NUMBER, amount, prepayamount, PracticeConstants.PROCESS_CARD_HOLDER_NAME,
				PracticeConstants.CARD_NUMBER, PracticeConstants.CARD_TYPE_VISA);

		logStep("Verify the your Budget payment plan start date text");
		assertTrue(pPayMyBillOnlinePage.getPaymentStartDateText().contains("Your payment plan start date is "
				+ pPayMyBillOnlinePage.getPlanStartDate() + " recurring every month."));

		logStep("Verify the creditcard last four digit");
		assertTrue(pPayMyBillOnlinePage.getCreditCardLastFourDigits().contains("1111"));

		String EnddatePlanText = pPayMyBillOnlinePage.getPlanEndDate();

		logStep("click to submit the Budget Payment Plan search");
		pPayMyBillOnlinePage.clickOnSubmitPayment();

		logStep("click on Budget Payment pLan to serach the Budget Payment done ");
		practiceHome.budgetPaymentPlanSearch();

		logStep("Set the Enrollment date ");
		pPayMyBillOnlinePage.setTheEnrollmentDate();

		logStep("Searching of Budget Payment plan with patient firstName and lastName ");
		pPayMyBillOnlinePage.budgetPaymentPlanSearchPatient(testData.getProperty("online.bill.pay.first.name"),
				testData.getProperty("online.bill.pay.last.name"));

		logStep("Verify the BudgetPaymentPlan End Date and card ending");
		assertTrue(pPayMyBillOnlinePage.getplanEndDateBudgetSearch().equals(EnddatePlanText));

		logStep("Verify the creditcard last four digit in Budget Payment Plan Search");
		assertTrue(pPayMyBillOnlinePage.getActiveBudgetPaymentCardDigit().contains("1111"));

		logStep("Stop the Budget Payment Plan ");
		pPayMyBillOnlinePage.clickOnStopBudgetPayment();

	}

	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testOnlineBillPayRefundProcess() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("doctor.login.payment"),
				testData.getProperty("doctor.password.payment"));
		logStep("Click on Make Payment link.");
		PayMyBillOnlinePage pPayMyBillOnlinePage = practiceHome.clickMakePaymentForPatient();

		logStep("Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(testData.getProperty("online.bill.pay.first.name"),
				testData.getProperty("online.bill.pay.last.name"));

		String amount = IHGUtil.createRandomNumericStringInRange(100, 500);
		log("Random generated amount: " + amount);

		int adjustedAmountRefund = Integer.parseInt(amount) - 10;
		log("The Adjusted Amount After Refund: " + adjustedAmountRefund);

		logStep("Set all the transaction details");
		pPayMyBillOnlinePage.setTransactionsForOnlineBillPayProcess(PracticeConstants.LOCATION,
				PracticeConstants.PROVIDER, PracticeConstants.CARD_NUMBER, amount,
				PracticeConstants.PROCESS_CARD_HOLDER_NAME, PracticeConstants.CARD_NUMBER,
				PracticeConstants.CARD_TYPE_VISA);

		logStep("Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 20, pPayMyBillOnlinePage.paymentConfirmationText);
		assertEquals(true, pPayMyBillOnlinePage.paymentConfirmationText.getText()
				.contains(PracticeConstants.PAYMENT_SUCCESSFULL_TEXT));

		logStep("Navigate to Patient Search Page.");
		OnlineBillPaySearchPage onlineBillPay = new OnlineBillPaySearchPage(driver);
		PatientSearchPage patientsearchPage = onlineBillPay.clickOnPatientSearchLink();

		logStep("Search the patient in Patient Search page.");
		patientsearchPage.searchPatient(PracticeConstants.PATIENT_FIRST_NAME, PracticeConstants.PATIENT_LAST_NAME);

		logStep("Verify whether the transaction is present.");
		assertTrue(patientsearchPage.isTransactionPresent(amount, PracticeConstants.PATIENT_FIRST_NAME,
				PracticeConstants.PATIENT_LAST_NAME));

		logStep("Select the particular Transaction from the Search Result.");
		patientsearchPage.selectTheTransaction(amount, PracticeConstants.PATIENT_FIRST_NAME,
				PracticeConstants.PATIENT_LAST_NAME);
		assertFalse(pPayMyBillOnlinePage.isRefundTransactionPresent());

		logStep("Click on Refund Payment Link and Refund the transaction.");
		pPayMyBillOnlinePage.refundPayment("10", PracticeConstants.REFUND_COMMENT);
		assertTrue(pPayMyBillOnlinePage.isRefundTransactionPresent());

		logStep("Validate the Adjusted Amount after the Refund Amount");
		assertTrue(pPayMyBillOnlinePage.getAdjustedAmount().equals("$" + adjustedAmountRefund + ".00"));

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendPasswordResetEmail() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("forgot.password.first.name"),
				testData.getProperty("forgot.password.last.name"));
		PatientDashboardPage pPatientDashboardPage = pPatientSearchPage.clickOnPatient(
				testData.getProperty("forgot.password.first.name"), testData.getProperty("forgot.password.last.name"));

		logStep("Send Password Reset Email to Patient");
		pPatientSearchPage = pPatientDashboardPage.sendResetPasswordLink();

		logStep("Click Send Email");
		pPatientDashboardPage = pPatientSearchPage.sendPasswordResetEmail();
		assertTrue(pPatientDashboardPage.getFeedback().contains("Password reset email sent to Patient"),
				"No success message on send!");

		logStep("Access Yopmail and check for Reset Password Link");
		String[] mailAddress = testData.getProperty("forgot.password.mail").split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";

		YopMail mail = new YopMail(driver);
		String resetPasswordLink = mail.getLinkFromEmail(mailAddress[0], emailSubject, inEmail, 20);
		log("Link from mail is" + resetPasswordLink);

		String url = getRedirectUrl(resetPasswordLink);
		System.out.println("Redirected url is" + url);
		assertNotNull(url, "Error: Reset Password link not found.");

		JalapenoForgotPasswordPage4 forgotPasswordPage = new JalapenoForgotPasswordPage4(driver);
		forgotPasswordPage.fillInPassword(testData.getProperty("new.password"));
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPasswordResetEmailForTrustedRepresentative() throws Exception {
		// Instant passwordResetStart = Instant.now();
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(
				testData.getProperty("trusted.rep.forgot.password.first.name"),
				testData.getProperty("trusted.rep.forgot.password.last.name"));
		PatientDashboardPage pPatientDashboardPage = pPatientSearchPage.clickOnPatient(
				testData.getProperty("trusted.rep.forgot.password.first.name"),
				testData.getProperty("trusted.rep.forgot.password.last.name"));

		logStep("Send Password Reset Email to Patient");
		pPatientSearchPage = pPatientDashboardPage.trustedRepSendResetPasswordLink();

		logStep("Click Send Email");
		pPatientDashboardPage = pPatientSearchPage.sendPasswordResetEmail();
		assertTrue(
				pPatientDashboardPage.getFeedback()
						.contains("Password reset email sent to Guardian or Trusted Representative"),
				"No success message on send!");

		logStep("Access Yopmail and check for Reset Password Link");
		String[] mailAddress = testData.getProperty("forgot.password.mail").split("@");
		String emailSubject = "Help with your user name or password";
		String inEmail = "Reset Password Now";
		
		YopMail email = new YopMail(driver);
		String receivedEmail = email.getLinkFromEmail(mailAddress[0], emailSubject, inEmail, 20);
		log("Link from mail is" + receivedEmail);

		System.out.println("Link from mail is" + receivedEmail);
		String url = getRedirectUrl(receivedEmail);
		System.out.println("Redirected url is" + url);
		assertNotNull(url, "Error: Reset Password link not found.");

		JalapenoForgotPasswordPage4 forgotPasswordPage = new JalapenoForgotPasswordPage4(driver);
		forgotPasswordPage.fillInPassword(testData.getProperty("new.password"));
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDuplicatePatientCreation() throws Exception {

		String patientLastName = "Duplicate";
		String patientEmail = "Duplicate@yopmail.com";
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(),
				testData.getDoctorPassword());

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = pPatientSearchPage.clickOnAddNewPatient();

		pPatientSearchPage.clickOnAddNewPatient();
		patientActivationPage.setInitialDetailsFields("Guardian", patientLastName, "F", PracticeConstants.PATIENT_ID,
				testData.getProperty("phone.number"), patientEmail, testData.getProperty("dob.month"),
				testData.getProperty("dob.day"), testData.getProperty("dob.year"), "address1", "address2", "city",
				"Alabama", testData.getProperty("zip.code"));
		assertTrue(pPatientSearchPage.isDuplicatePatientIDErrorDisplayed());
		assertTrue(pPatientSearchPage.isPatientCreationErrorDisplayed());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSearchValidAndInvalidPatientID() throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(),
				testData.getDoctorPassword());

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		logStep("Search for patient in Patient Search");
		pPatientSearchPage.searchForPatientWithPatientID(testData.getProperty("search.valid.patientID"));

		logStep("Verify the Search Result");
		IHGUtil.waitForElement(driver, 30, pPatientSearchPage.searchResult);
		assertEquals(true, pPatientSearchPage.searchResult.getText().contains(PracticeConstants.PATIENT_FIRST_NAME));
		pPracticeHomePage.clickPatientSearchLink();

		logStep("Search for patient in Patient Search");
		pPatientSearchPage.searchForPatientWithPatientID(testData.getProperty("search.invalid.patientID"));
		assertTrue(pPatientSearchPage.isNoRecordsFoundMsgDisplayed());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInviteGuardianWithFullAcess() throws Exception {
		String patientLogin = PortalUtil2.generateUniqueUsername("login", testData);
		String patientLastName = patientLogin.replace("login", "last");
		String patientEmail = patientLogin.replace("login", "mail") + "@yopmail.com";
		Patient localpatient = PatientFactory.createJalapenoPatient(patientLogin, testData);

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		logStep("Click on Add new Patient");
		PatientActivationPage patientActivationPage = pPatientSearchPage.clickOnAddNewPatient();

		logStep("Register Dependent - Enter all the details and click on Register");
		patientActivationPage.setInitialDetailsAllFields("Dependent", patientLastName, "M", patientLastName + "D",
				testData.getPhoneNumber(), patientEmail, testData.getDOBMonth(), testData.getDOBDay(),
				testData.getDOBYearUnderage(), "address1", "address2", "city", "Alabama", testData.getZipCode());

		logStep("Click on Patient Search Link");
		pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();
		logStep("Search for patient in Patient Search");
		pPatientSearchPage.searchForPatientInPatientSearch(patientEmail);
		pPatientSearchPage.clickOnPatient("Dependent", patientLastName);

		logStep("Invite Guardian");
		PatientTrustedRepresentativePage patientInviteTrustedRepresentative = pPatientSearchPage
				.clickInviteTrustedRepresentative();
		patientInviteTrustedRepresentative.inviteGuardian(localpatient);

		logStep("Waiting for invitation email");
		YopMail mail = new YopMail(driver);
		String patientUrl = mail.getLinkFromEmail(localpatient.getEmail(),
				"You are invited to create a Patient Portal guardian account at", "Sign Up!", 20);

		assertNotNull(patientUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientUrl);

		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				testData.getZipCode(), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill guardian name");
		linkAccountPage.checkDependentInfo("Dependent", patientLastName, localpatient.getEmail());
		SecurityDetailsPage accountDetailsPage = linkAccountPage.continueToCreateGuardianOnly("Guardian",
				patientLastName, "Parent");

		logStep("Continue registration - create dependents credentials and continue to Home page");
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patientLogin, "Medfusion123",
				testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber());
		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Verify Appointment Solutions");
		JalapenoAppointmentRequestPage appReqPage = homePage.clickOnAppointment(driver);

		logStep("Verify Request An Appointment Button is present");
		assertTrue(appReqPage.isAppointmentRequestBtnDisplayed());

		logStep("Log out and Login to patient portal");
		homePage.clickOnLogout();
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver);
		homePage = loginPage.login(patientLogin, testData.getProperty("patient.guardianpassword"));

		logStep("Verify MedicationsHome Solutions");
		MedicationsHomePage medReqPage = homePage.clickOnMedications(driver);

		logStep("Verify Rx Request Button is present in Medications module");
		assertTrue(medReqPage.isRxRequestBtnDisplayed());

		logStep("Log out and Login to patient portal");
		homePage.clickOnLogout();
		homePage = loginPage.login(patientLogin, testData.getProperty("patient.guardianpassword"));

		logStep("Click on forms solution");
		assertTrue(homePage.isFormsSolutionDisplayed());
		homePage.clickOnHealthForms();
	}

	private String getRedirectUrl(String originUrl) {
		log("Navigating to input URL and checking redirection for 10 seconds");
		driver.get(originUrl);
		for (int i = 0; i < 10; i++) {
			if (!driver.getCurrentUrl().equals(originUrl)) {
				log("Found redirected URL: " + driver.getCurrentUrl());
				return driver.getCurrentUrl();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return originUrl;
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testChangePatientDetails() throws Exception {
		// *A patient has been created with first name "Test" and last name
		// "PatientDetails".
		// *This script will update the first name and last name to "Update" and "PD"
		// respectively and verify the same in patient portal.
		// *However from the second execution onwards, first name and last name will be
		// updated.
		// *In order to handle this scenario, I have put a condition to check for both
		// "Test" and "Update" before searching the patient and making changes to its
		// name.

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		PatientDashboardPage pPatientDashboardPage = pPatientSearchPage.modifiedPatientSearch(
				testData.getProperty("change.email.first.name1"), testData.getProperty("change.email.last.name1"),
				testData.getProperty("change.email.first.name2"), testData.getProperty("change.email.last.name2"));

		logStep("Click Edit email");
		pPatientSearchPage = pPatientDashboardPage.clickEditEmail();

		logStep("Update email");
		pPatientDashboardPage = pPatientSearchPage.changeEmail(testData.getProperty("change.email.new.email"));
		assertEquals(true, pPatientDashboardPage.getFeedback().contains("Patient Email Address / User Id Was Updated"));

		logStep("Click Edit name");
		pPatientSearchPage = pPatientDashboardPage.clickEditName();
		String patName = pPatientSearchPage.changeName(testData.getProperty("change.email.first.name1"),
				testData.getProperty("change.email.last.name1"), testData.getProperty("change.email.first.name2"),
				testData.getProperty("change.email.last.name2"));

		logStep("Click Edit gender");
		pPatientSearchPage = pPatientDashboardPage.clickEditGender();
		String g = pPatientSearchPage.changeGender();

		logStep("Click Edit zip");
		pPatientSearchPage = pPatientDashboardPage.clickEditZip();
		String zip = pPatientSearchPage.changeZip();

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.url"));

		logStep("Fill in credentials and log in");
		JalapenoHomePage jalapenoHomePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Go to account page");
		jalapenoHomePage.clickOnAccount();

		logStep("Go to MyAccount page");
		JalapenoMyAccountProfilePage myAccountPage = jalapenoHomePage.goToAccountPage();

		logStep("Verify Patient Details");
		assertTrue(myAccountPage.checkZipCode(zip));
		assertTrue(myAccountPage.checkPatientName(patName, testData.getProperty("change.email.first.name1"),
				testData.getProperty("change.email.last.name1"), testData.getProperty("change.email.first.name2"),
				testData.getProperty("change.email.last.name2")));
		Thread.sleep(2000);
		if (g.equals("Male")) {
			assertTrue(myAccountPage.checkGender(Patient.GenderExtended.MALE));
		} else {
			assertTrue(myAccountPage.checkGender(Patient.GenderExtended.FEMALE));
		}

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLAGuardianAcessForMessagesFromPracticePortal() throws Exception {

		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		JalapenoMessagesPage messagesPage;
		PatientSearchPage pPatientSearchPage;
		PatientTrustedRepresentativePage patientInviteTrustedRepresentative;

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		pPatientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));

		logStep("EditAccess");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.selectCustomAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Messages", "noAccess");
		patientInviteTrustedRepresentative.clickOnInviteBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		homePage = loginPage.login(testData.getProperty("patient.guardianlogin"),
				testData.getProperty("patient.guardianpassword"));

		logStep("Go to Messages and ASKA Question Not displayed when No Access is granted");
		assertFalse(homePage.isMessagesDisplayed(), "Messages Not Accessible");
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));

		logStep("Set Patient Search Fields");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Messages", "viewOnly");
		patientInviteTrustedRepresentative.clickOnInviteBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		loginPage.login(testData.getProperty("patient.guardianlogin"),
				testData.getProperty("patient.guardianpassword"));

		logStep("Go to messages");
		messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.returnSubjectMessage().length() > 0);

		logStep("Verify Aska question button should not display for view only access");
		assertFalse(messagesPage.isAskaQuestionButtonDisplayed());
		homePage.clickOnLogout();

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLAGuardianAcessForFormsFromPracticePortal() throws Exception {

		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		PatientSearchPage pPatientSearchPage;
		PatientTrustedRepresentativePage patientInviteTrustedRepresentative;

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		pPatientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));

		logStep("EditAccess");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.selectCustomAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Forms", "noAccess");
		patientInviteTrustedRepresentative.clickOnInviteBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		homePage = loginPage.login(testData.getProperty("patient.guardianlogin"),
				testData.getProperty("patient.guardianpassword"));

		logStep("Verify Forms solution Not display for Gurdian Role");
		assertFalse(homePage.isFormsSolutionDisplayed(), "Forms Not Accessible");
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));

		logStep("Set Patient Search Fields");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Forms", "viewOnly");
		patientInviteTrustedRepresentative.clickOnInviteBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		loginPage.login(testData.getProperty("patient.guardianlogin"),
				testData.getProperty("patient.guardianpassword"));

		logStep("Verify the Forms Solution and Click on the Health Forms");
		assertTrue(homePage.isFormsSolutionDisplayed(), "Forms should be Accessible");
		homePage.clickOnHealthForms();
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLAGuardianAcessForAppointmentsRxRFromPractice() throws Exception {

		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		PatientSearchPage pPatientSearchPage;
		PatientTrustedRepresentativePage patientInviteTrustedRepresentative;

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		pPatientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));

		logStep("EditAccess");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.selectCustomAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Appointments", "viewOnly");
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Medications", "viewOnly");
		patientInviteTrustedRepresentative.clickOnInviteBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		homePage = loginPage.login(testData.getProperty("patient.guardianlogin"),
				testData.getProperty("patient.guardianpassword"));

		logStep("Verify Appointment Solutions");
		JalapenoAppointmentRequestPage appReqPage = homePage.clickOnAppointment(driver);
		logStep("Verify Request An Appointment Button is not present");
		assertFalse(appReqPage.isAppointmentRequestBtnDisplayed());
		homePage.clickOnLogout();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		homePage = loginPage.login(testData.getProperty("patient.guardianlogin"),
				testData.getProperty("patient.guardianpassword"));

		logStep("Verify Rx Request Button is not present in Medications module");
		MedicationsHomePage medReqPage = homePage.clickOnMedications(driver);
		assertFalse(medReqPage.isRxRequestBtnDisplayed());
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));

		logStep("Set Patient Search Fields");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Appointments", "noAccess");
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Medications", "noAccess");
		patientInviteTrustedRepresentative.clickOnInviteBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		loginPage.login(testData.getProperty("patient.guardianlogin"),
				testData.getProperty("patient.guardianpassword"));

		logStep("Verify Request An Appointment solution is not displayed");
		assertFalse(appReqPage.isAppointmentRequestBtnDisplayed());

		logStep("Verify Rx Request solution is not displayed");
		assertFalse(medReqPage.isRxRequestBtnDisplayed());
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLAGuardianAcessForHealthRecordFromPracticePortal() throws Exception {

		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		PatientSearchPage pPatientSearchPage;
		PatientTrustedRepresentativePage patientInviteTrustedRepresentative;

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		pPatientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));

		logStep("EditAccess");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.selectCustomAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Health Record", "noAccessHealthRecord");
		patientInviteTrustedRepresentative.clickOnUpdateBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		homePage = loginPage.login(testData.getProperty("patient.guardianlogin"),
				testData.getProperty("patient.guardianpassword"));

		logStep("Verify that system should not display the Health Record");
		assertFalse(homePage.isHealthRecordSolutionisplayed());
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("Guardian.first.name"),
				testData.getProperty("Guardian.last.name"));

		logStep("Set Patient Search Fields");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Health Record", "fullAccessHealthRecord");
		patientInviteTrustedRepresentative.clickOnUpdateBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		loginPage.login(testData.getProperty("patient.guardianlogin"),
				testData.getProperty("patient.guardianpassword"));

		logStep("Verify that system should display the Health Record");
		assertTrue(homePage.isHealthRecordSolutionisplayed());
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLATrustedRepAcessForHealthRecordFromPracticePortal() throws Exception {

		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;
		JalapenoLoginPage loginPage;
		JalapenoHomePage homePage;
		PatientSearchPage pPatientSearchPage;
		PatientTrustedRepresentativePage patientInviteTrustedRepresentative;

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		pPatientSearchPage = practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(
				testData.getProperty("trusted.rep.care.management.first.name"),
				testData.getProperty("trusted.rep.care.management.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("trusted.rep.care.management.first.name"),
				testData.getProperty("trusted.rep.care.management.last.name"));

		logStep("EditAccess");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.selectCustomAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Health Record", "noAccessHealthRecord");
		patientInviteTrustedRepresentative.clickOnUpdateBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		homePage = loginPage.login(testData.getProperty("trusted.rep.login"),
				testData.getProperty("trusted.rep.password"));

		logStep("Verify that system should not display the Health Record");
		assertFalse(homePage.isHealthRecordSolutionisplayed());
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Search Link");
		practiceHome.clickPatientSearchLink();

		logStep("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(
				testData.getProperty("trusted.rep.care.management.first.name"),
				testData.getProperty("trusted.rep.care.management.last.name"));
		pPatientSearchPage.clickOnPatient(testData.getProperty("trusted.rep.care.management.first.name"),
				testData.getProperty("trusted.rep.care.management.last.name"));

		logStep("Set Patient Search Fields");
		patientInviteTrustedRepresentative = pPatientSearchPage.editTrustedRepresentativeAccess();
		patientInviteTrustedRepresentative.updateWithModuleNameAndAccess("Health Record", "fullAccessHealthRecord");
		patientInviteTrustedRepresentative.clickOnUpdateBtn();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getProperty("Patient.guardian.url"));
		homePage = loginPage.login(testData.getProperty("trusted.rep.login"),
				testData.getProperty("trusted.rep.password"));

		logStep("Verify that system should display the Health Record");
		assertTrue(homePage.isHealthRecordSolutionisplayed());
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResendPatientInvitationFailures() throws Exception {
		PracticeLoginPage practiceLogin;
		PracticeHomePage practiceHome;

		logStep("Login to Practice Portal");
		practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
		practiceHome = practiceLogin.login(testData.getProperty("doctor2.login"),
				testData.getProperty("doctor2.password"));

		logStep("Click on Patient Activation tab");
		PatientActivationPage activationPage = practiceHome.clickPatientactivationTab();

		logStep("Click on Failures link");
		activationPage.clickonFailuresLink();

		logStep("Select date range and resend patient invite failures");
		assertTrue(activationPage.resendFailures());
	}

}
