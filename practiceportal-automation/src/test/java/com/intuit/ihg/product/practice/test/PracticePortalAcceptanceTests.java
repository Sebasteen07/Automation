package com.intuit.ihg.product.practice.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.PropertyFileLoader;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.medfusion.product.object.maps.practice.page.patientMessaging.PatientMessagingPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.treatmentplanpage.TreatmentPlansPage;
import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.PracticeTestData;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.api.utils.PracticeUtil;
import com.medfusion.product.practice.api.utils.ReadFilePath;
import com.medfusion.product.practice.tests.VirtualCardSwiperTest;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PracticePortalAcceptanceTests extends BaseTestNGWebDriver {

		private ReadFilePath path = new ReadFilePath();
		private PropertyFileLoader testData;

		// TODO move stuff around stepCounter to BaseTestNGWebDriver
		private int stepCounter;

		public PracticePortalAcceptanceTests() throws Exception {
				path.getFilepath("documents");
		}

		@BeforeClass(alwaysRun = true)
		public void prepareTestData() throws IOException {
				log("Getting Test Data");
				testData = new PropertyFileLoader();
		}

		@BeforeMethod(alwaysRun = true)
		public void setUpPracticePortalTest() {
				log(this.getClass().getName());
				log("Execution Environment: " + IHGUtil.getEnvironmentType());
				log("Execution Browser: " + TestConfig.getBrowserType());

				log("Resetting step counter");
				stepCounter = 0;
		}

		private void logStep(String logText) {
				log("STEP " + ++stepCounter + ": " + logText);
		}

		/**
		 * @throws Exception
		 * @Author:- rperkinsjr
		 * @Date:-3/27/2013
		 * @User Story ID in Rally : NA
		 * @StepsToReproduce: Navigate to login page Enter credentials and login Validate Home page loads Click 'Sign Out' Validate logout occurred (should redirect
		 * back to login page) =============================================================
		 * @AreaImpacted :- Description
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testLoginLogout() throws Exception {
				logStep("Navigate to Practice portal login page");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
				PerformanceReporter.getPageLoadDuration(driver, PracticeLoginPage.PAGE_NAME);

				logStep("Login to Practice Portal");
				PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());
				PerformanceReporter.getPageLoadDuration(driver, PracticeHomePage.PAGE_NAME);
				assertTrue(practiceHome.isHomePageLoaded(), "Expected to see 'Recent Activity' on home page, but it was not found");

				logStep("Click sign out");
				practiceLogin = practiceHome.logOut();
				assertTrue(practiceLogin.isLoginPageLoaded(), "Expected to see login page");
		}

		/**
		 * @throws Exception
		 * @Author: bbinisha User Story : US6575
		 * @Date: 07/26/2013
		 * @StepsToReproduce: Practice portal login Click on Manage TreatmentPlan Click on 'Submit' button Enter the treatment info Click on 'Create TreatmentPlan'
		 * button. =============================================================
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"})
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
				treatmentPlan
						.createTreatmentPlanInfo(PracticeConstants.treatmentPlanTitle, PracticeConstants.treatmentPlanSubject, PracticeConstants.treatmentPlanBody);

				logStep("Verify whether the treatmentPlan is added Successfully.");
				assertEquals(treatmentPlan.checkTreatmentPlanSuccessMsg().contains(PracticeConstants.treatmentPlanSuccessMsg), true,
						"Treatment plan is not added properly.");
		}

		/**
		 * @throws Exception
		 * @Author: bbinisha refactored by Prokop Rehacek User Story : US6579
		 * @Date: 07/26/2013
		 * @StepsToReproduce: Practice portal login Click on 'Virtual Card Swiper' Enter the card info Click on 'Click Here To Charge The Card' button. Verify the
		 * Success Message. =============================================================
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"})
		public void testVirtualCardSwiper() throws Exception {
				// Instancing virtualCardSwiperTest
				VirtualCardSwiperTest virtualCardSwiperTest = new VirtualCardSwiperTest();

				// Setting data provider
				Practice practice = new Practice();
				PracticeTestData practiceTestData = new PracticeTestData(practice);

				// Executing Test
				virtualCardSwiperTest.virtualCardSwipeTest(driver, practiceTestData, "Visa");
		}

		/**
		 * @throws Exception
		 * @Author: Prokop Rehacek User Story : US6579
		 * @Date: 3/11/2014
		 * @StepsToReproduce: Practice portal login Click on 'Virtual Card Swiper' Enter the card info Click on 'Click Here To Charge The Card' button. Verify the
		 * Success Message. =============================================================
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"})
		public void testVirtualCardSwiperPayPal() throws Exception {

				// Instancing virtualCardSwiperTest
				VirtualCardSwiperTest virtualCardSwiperTest = new VirtualCardSwiperTest();

				// Setting data provider
				Practice practice = new Practice();
				PracticeTestData practiceTestData = new PracticeTestData(practice);

				// Executing Test
				virtualCardSwiperTest.setSwipeString(PracticeConstants.swipeStringMaster);
				virtualCardSwiperTest.virtualCardSwipeTest(driver, practiceTestData, "MasterCard");
		}

		/**
		 * @throws Exception
		 * @Author: Kiran_GT
		 * @Date: 07/26/2013
		 * @StepsToReproduce: Practice Portal login Click on Patient Search Link Set Patient Search Fields,Verify Patient Search Result
		 * ==================================== =========================
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testPatientSearchLink() throws Exception {
				logStep("Login to Practice Portal");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
				PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

				logStep("Click on Patient Search Link");
				PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

				logStep("Search for patient in Patient Search");
				// pPatientSearchPage.setPatientSearchFields();
				pPatientSearchPage.searchForPatientInPatientSearch(PracticeConstants.PatientFirstName, PracticeConstants.PatientLastName);

				logStep("Verify the Search Result");
				IHGUtil.waitForElement(driver, 30, pPatientSearchPage.searchResult);
				assertEquals(true, pPatientSearchPage.searchResult.getText().contains(PracticeConstants.PatientFirstName));

		}

		/**
		 * @throws Exception
		 * @Author: Prokop Rehacek
		 * @Date: 04/14/2014
		 * @StepsToReproduce: 1. Practice Portal login 2. Click on Patient Search Link 3. Set Patient Search Fields, 4. Open patient dashboard 5. Click on Edit link
		 * next to Email 6. Change email address 7. Verify that it was changed ==================================== =========================
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testChangePatientEmail() throws Exception {
				logStep("Login to Practice Portal");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
				PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

				logStep("Click on Patient Search Link");
				PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

				logStep("Set Patient Search Fields");
				pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("changeEmailFirstName"), testData.getProperty("changeEmailLastName"));

				logStep("Open Patient Dashboard");
				PatientDashboardPage pPatientDashboardPage =
						pPatientSearchPage.clickOnPatient(testData.getProperty("changeEmailFirstName"), testData.getProperty("changeEmailLastName"));

				logStep("Click Edit email");
				pPatientSearchPage = pPatientDashboardPage.clickEditEmail();

				log("Update email");
				pPatientDashboardPage = pPatientSearchPage.changeEmail(testData.getProperty("changeEmailNewEmail"));
				assertEquals(true, pPatientDashboardPage.getFeedback().contains("Patient Email Address / User Id Was Updated"));
		}

		/**
		 * @throws Exception
		 * @Author: Prokop Rehacek
		 * @Date: 07/26/2013
		 * @StepsToReproduce: ==================================== =========================
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testSendUserIdEmail() throws Exception {
				logStep("Login to Practice Portal");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
				PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

				logStep("Click on Patient Search Link");
				PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

				logStep("Set Patient Search Fields");
				pPatientSearchPage.searchForPatientInPatientSearch(testData.getProperty("forgotUsernameFirstName"), testData.getProperty("forgotUsernameLastName"));
				PatientDashboardPage pPatientDashboardPage =
						pPatientSearchPage.clickOnPatient(testData.getProperty("forgotUsernameFirstName"), testData.getProperty("forgotUsernameLastName"));

				logStep("Send Email reminder with User ID");
				pPatientSearchPage = pPatientDashboardPage.sendEmailUserID();

				logStep("Click Send Email");
				pPatientDashboardPage = pPatientSearchPage.sendUserNameEmail();
				assertTrue(pPatientDashboardPage.getFeedback().contains("Username email sent to patient"), "No success message on send!");
				logStep("Access Mailinator and check for received email");
				Mailinator mailinator = new Mailinator();
				assertTrue(mailinator.catchNewMessageCheckContent(testData.getProperty("forgotUsernameMail"), testData.getProperty("forgotUsernameMailSubject"),
						testData.getProperty("forgotUsernameLogin"), 10), "Mail not received after max retries");
		}

		/**
		 * @throws Exception
		 * @Author: Gajendran
		 * @Date: 07/26/2013
		 * @StepsToReproduce: Patient login
		 * <p>
		 * <p>
		 * =============================================================
		 * @AreaImpacted :
		 */

		@Test(enabled = true, groups = {"SmokeTest"}, retryAnalyzer = RetryAnalyzer.class)
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
		 * @throws Exception
		 * @Author: Kiran_GT
		 * @Date: 07/26/2013
		 * @StepsToReproduce: Practice Portal login Click on Patient Messaging Link-->Quick Send Set Quick Send Fields and verify for Message Sent text
		 * ==================================== =========================
		 * @AreaImpacted :
		 */
		@Test(enabled = false, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testQuickSendPatientPDF() throws Exception {
				logStep("Login to Practice Portal");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
				PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

				logStep("Click on Patient Search Link");
				PatientMessagingPage pPatientMessagingPage = pPracticeHomePage.clickPatientMessagingTab();

				logStep("Getting the document to upload from filepath");
				PracticeUtil pUtil = new PracticeUtil(driver);
				String value = pUtil.getFilepath(PracticeConstants.fileDirectory).concat(PracticeConstants.pdfname);

				logStep("Set Quick Send Fields");
				pPatientMessagingPage.setFieldsAndPublishMessage(value);

				logStep("Verify the Published Message Succesfully text");
				IHGUtil.setFrame(driver, PracticeConstants.frameName);
				IHGUtil.waitForElement(driver, 20, pPatientMessagingPage.publishedSuccessfullyMessage);
				assertEquals(pPatientMessagingPage.publishedSuccessfullyMessage.getText(), "Message Published Successfully");
		}


		/**
		 * @throws Exception
		 * @Author: Kiran_GT
		 * @Date: 07/29/2013
		 * @StepsToReproduce: Practice Portal login Click on Online Bill Payment--->Make Payment For Patient Search for Patient,Make Payment for Patient and Verify
		 * for Payment Successfull Message ==================================== =========================
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"})
		public void testMakePaymentForPatient() throws Exception {
				logStep("" + "Login to Practice Portal");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
				PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

				logStep("Click On Online BillPayment Tab in Practice Portal--->Make Payment For Patient");
				PayMyBillOnlinePage pPayMyBillOnlinePage = pPracticeHomePage.clickMakePaymentForPatient();

				logStep("Search For Patient");
				pPayMyBillOnlinePage.searchForPatient();

				logStep("Set Patient Transaction Fields");
				pPayMyBillOnlinePage.setPatientTransactionFields();

				logStep("Verify the Payment Confirmation text");
				IHGUtil.setFrame(driver, PracticeConstants.frameName);
				IHGUtil.waitForElement(driver, 20, pPayMyBillOnlinePage.paymentConfirmationText);
				assertTrue(pPayMyBillOnlinePage.paymentConfirmationText.getText().contains(PracticeConstants.PaymentSuccessfullText));
		}

		/**
		 * @throws Exception
		 * @Author: bbinisha User Story : US6488
		 * @Date: 08/01/2013
		 * @StepsToReproduce: Practice portal login Click on 'Online Billpay' Click on 'Make Payment' Link Search for the patient Enter the Payment info Click on
		 * 'Submit Payment' button. Click on Search Patient Page Verify whether the transaction is displayed. Navigate to that transaction page
		 * Click on 'Void Payment' button. Enter Void comment Close the Pop Up Click On 'Refund Payment' button. Check whethr the patient got email
		 * in gmail inbox notifying the void transaction done. ============================================================= *** Checking gmail
		 * part has to be completed****** =============================================================
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"})
		public void testOnlineBillPayProcess() throws Exception {
				logStep("Login to Practice Portal");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());
				PracticeHomePage practiceHome = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

				logStep("Click on Make Payment link.");
				PayMyBillOnlinePage pPayMyBillOnlinePage = practiceHome.clickMakePaymentForPatient();

				logStep("Search For Patient");
				pPayMyBillOnlinePage.searchForPatient(testData.getProperty("onlineBillPayFirstName"), testData.getProperty("onlineBillPayLastName"));

				String amount = IHGUtil.createRandomNumericStringInRange(5, 500);
				log("Random generated amount: " + amount);

				logStep("Set all the transaction details");
				pPayMyBillOnlinePage
						.setTransactionsForOnlineBillPayProcess(PracticeConstants.Location, PracticeConstants.Provider, PracticeConstants.processCardNum, amount,
								PracticeConstants.ProcessCardHolderName, PracticeConstants.processCardNum, PracticeConstants.processCardType);

				logStep("Verify the Payment Confirmation text");
				IHGUtil.setFrame(driver, PracticeConstants.frameName);
				IHGUtil.waitForElement(driver, 20, pPayMyBillOnlinePage.paymentConfirmationText);
				assertEquals(true, pPayMyBillOnlinePage.paymentConfirmationText.getText().contains(PracticeConstants.PaymentSuccessfullText));

				logStep("Navigate to Patient Search Page.");
				OnlineBillPaySearchPage onlineBillPay = new OnlineBillPaySearchPage(driver);
				PatientSearchPage patientsearchPage = onlineBillPay.clickOnPatientSearchLink();

				logStep("Search the patient in Patient Search page.");
				patientsearchPage.searchPatient(PracticeConstants.PatientFirstName, PracticeConstants.PatientLastName);

				logStep("Verify whether the transaction is present.");
				assertTrue(patientsearchPage.isTransactionPresent(amount, PracticeConstants.PatientFirstName, PracticeConstants.PatientLastName));

				logStep("Select the particular Transaction from the Search Result.");
				patientsearchPage.selectTheTransaction(amount, PracticeConstants.PatientFirstName, PracticeConstants.PatientLastName);
				assertFalse(pPayMyBillOnlinePage.isVoidTransactionPresent());

				logStep("Click on Void Payment Link and void the transaction.");
				pPayMyBillOnlinePage.voidPayment(PracticeConstants.voidComment);
				assertTrue(pPayMyBillOnlinePage.isVoidTransactionPresent());

				/*
				 * This needs to be checked if it can work after QB is working again log("Step 13 : Click on Refund Payment and give comments and amount to refund");
				 * pPayMyBillOnlinePage.refundPayment(amount, PracticeConstants.refundComment);
				 */

				// Checking email needs to be completed
		}
}
