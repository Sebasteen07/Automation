package com.intuit.ihg.product.practice.test;


import java.util.Date;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.CheckEmail;
import com.intuit.ihg.common.utils.mail.Gmail;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
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


public class PracticePortalAcceptanceTests extends BaseTestNGWebDriver {

	ReadFilePath path = new ReadFilePath();

	public PracticePortalAcceptanceTests() throws Exception {
		path.getFilepath("documents");
	}

	/**
	 * @Author:- rperkinsjr
	 * @Date:-3/27/2013
	 * @User Story ID in Rally : NA
	 * @StepsToReproduce:
	 * Navigate to login page
	 * Enter credentials and login
	 * Validate Home page loads
	 * Click 'Sign Out'
	 * Validate logout occurred (should redirect back to login page)
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testLoginLogout() throws Exception {

		log("Test Case: TestLoginLogout");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");	

		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData =new PracticeTestData(practice);

		log("step 2: Navigate to Login page"); 
		PracticeLoginPage practiceLogin =new PracticeLoginPage(driver, practiceTestData.getUrl());
		PerformanceReporter.getPageLoadDuration(driver, PracticeLoginPage.PAGE_NAME);

		log("step 3: Enter credentials and login");
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());
		PerformanceReporter.getPageLoadDuration(driver, PracticeHomePage.PAGE_NAME);
		assertTrue(practiceHome.isHomePageLoaded(), "Expected to see 'Recent Activity' on home page, but it was not found");

		log("step 4: Click sign out");
		practiceLogin = practiceHome.logOut();
		assertTrue(practiceLogin.isLoginPageLoaded(), "Expected to see login page");

	}


	/**
	 * @Author: bbinisha
	 * User Story : US6575
	 * @Date: 07/26/2013
	 * @StepsToReproduce:
	 * Practice portal login
	 * Click on Manage TreatmentPlan 
	 * Click on 'Submit' button
	 * Enter the treatment info
	 * Click on 'Create TreatmentPlan' button.
	 * =============================================================
	 * @AreaImpacted : 
	 * @throws Exception
	 */
	@Test (enabled = true, groups = {"AcceptanceTests"})
	public void testCreateTreatmentPlan() throws Exception {

		log("Test Case: TestLoginLogout");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");	

		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData =new PracticeTestData(practice);

		log("step 2: Navigate to Login page"); 
		PracticeLoginPage practiceLogin =new PracticeLoginPage(driver, practiceTestData.getUrl());

		log("step 3: Enter credentials and login");
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("Step 4 : Navigate to Manage TreatmentPlan page.");
		TreatmentPlansPage treatmentPlan = practiceHome.clickOnManageTreatmentPlan();

		log("Verify the Treatment plan page is displayed properly.");
		treatmentPlan.checkTreatmentPlanPage();

		log(" Step 5 : Click on submit button.");
		treatmentPlan.selectAppointmentRequest();
		treatmentPlan.clickOnSubmitButton();

		log(" Step 6 : Add treatment plan info and create treatment plan.");
		treatmentPlan.createTreatmentPlanInfo(PracticeConstants.treatmentPlanTitle, PracticeConstants.treatmentPlanSubject, PracticeConstants.treatmentPlanBody);

		log("Verify whether the treatmentPlan is added Successfully.");
		verifyEquals(treatmentPlan.checkTreatmentPlanSuccessMsg().contains(PracticeConstants.treatmentPlanSuccessMsg), true, "Treatment plan is not added properly.");

	}

	/**
	 * @Author: bbinisha refactored by Prokop Rehacek
	 * User Story : US6579
	 * @Date: 07/26/2013
	 * @StepsToReproduce:
	 * Practice portal login
	 * Click on 'Virtual Card Swiper' 
	 * Enter the card info
	 * Click on 'Click Here To Charge The Card' button.
	 * Verify the Success Message.
	 * =============================================================
	 * @AreaImpacted : 
	 * @throws Exception
	 */
	@Test (enabled = true, groups = {"AcceptanceTests"})
	public void testVirtualCardSwiper() throws Exception {
		
		
		// Instancing virtualCardSwiperTest
		VirtualCardSwiperTest virtualCardSwiperTest = new VirtualCardSwiperTest();
		
		// Setting data provider
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		
		// Executing Test
		virtualCardSwiperTest.virtualCardSwipeTest(driver, practiceTestData,"Visa");
		
	}
	
	/**
	 * @Author: Prokop Rehacek
	 * User Story : US6579
	 * @Date: 3/11/2014
	 * @StepsToReproduce:
	 * Practice portal login
	 * Click on 'Virtual Card Swiper' 
	 * Enter the card info
	 * Click on 'Click Here To Charge The Card' button.
	 * Verify the Success Message.
	 * =============================================================
	 * @AreaImpacted : 
	 * @throws Exception
	 */
	@Test (enabled = true, groups = {"AcceptanceTests"})
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
	 * @Author: Kiran_GT
	 * @Date: 07/26/2013
	 * @StepsToReproduce: Practice Portal login Click on Patient Search Link
	 *                    Set Patient Search Fields,Verify Patient Search Result
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test (enabled = true, groups = {"AcceptanceTests"},retryAnalyzer=RetryAnalyzer.class)
	public void testPatientSearchLink() throws Exception {

		log("Test Case: testPatientSearchLink");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData =new PracticeTestData(practice);

		//Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 2: Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage= pPracticeHomePage.clickPatientSearchLink();

		log("step 3:Set Patient Search Fields");
//		pPatientSearchPage.setPatientSearchFields();
		pPatientSearchPage.searchForPatientInPatientSearch(PracticeConstants.PatientFirstName, PracticeConstants.PatientLastName);
	
		log("step 4:Verify the Search Result");
		IHGUtil.waitForElement(driver,30,pPatientSearchPage.searchResult);
		verifyEquals(true,pPatientSearchPage.searchResult.getText().contains(PracticeConstants.PatientFirstName));

	}
	
	/**
	 * @Author: Prokop Rehacek
	 * @Date: 04/14/2014
	 * @StepsToReproduce: 	1. Practice Portal login 
	 * 						2. Click on Patient Search Link
	 *                   	3. Set Patient Search Fields,
	 *                   	4. Open patient dashboard
	 *                   	5. Click on Edit link next to Email
	 *                   	6. Change email address
	 *                   	7. Verify that it was changed
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test (enabled = true, groups = {"AcceptanceTests"},retryAnalyzer=RetryAnalyzer.class)
	public void testChangePatientEmail() throws Exception {

		log("Test Case: testPatientSearchLink");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 2: Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage= pPracticeHomePage.clickPatientSearchLink();

		log("step 3: Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(PracticeConstants.chngMailFName, PracticeConstants.chngMailLName);
		
		log("step 4: Open Patient Dashboard");
		PatientDashboardPage pPatientDashboardPage =  pPatientSearchPage.clickOnPatient(PracticeConstants.chngMailFName, PracticeConstants.chngMailLName);
		
		log("step 5: Click Edit email");
		pPatientSearchPage = pPatientDashboardPage.clickEditEmail();
		
		log("step 6: Update email");
		pPatientDashboardPage = pPatientSearchPage.changeEmail(practiceTestData.getPatientEmail());
		verifyEquals(true,pPatientDashboardPage.getFeedback().contains("Patient Email Address / User Id Was Updated"));

	}
	
	/**
	 * @Author: Prokop Rehacek
	 * @Date: 07/26/2013
	 * @StepsToReproduce: 
	 *                   
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test (enabled = true, groups = {"AcceptanceTests"},retryAnalyzer=RetryAnalyzer.class)
	public void testSendUserIdEmail() throws Exception {

		log("Test Case: testPatientSearchLink");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		Date startEmailSearchDate = new Date();
		
		
		log("step 1: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData =new PracticeTestData(practice);
		
		Gmail gmail = new Gmail(practiceTestData.getPatientEmail(), practiceTestData.getPatientPassword());

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 2: Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage= pPracticeHomePage.clickPatientSearchLink();

		log("step 3: Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(PracticeConstants.frgtFName, PracticeConstants.frgtLName);
		PatientDashboardPage pPatientDashboardPage =  pPatientSearchPage.clickOnPatient(PracticeConstants.frgtFName, PracticeConstants.frgtLName);
	
		log("step 4: Send Email reminder with User ID");
		pPatientSearchPage = pPatientDashboardPage.sendEmailUserID();
		
		log("step 5: click Send Email");
		pPatientDashboardPage = pPatientSearchPage.sendUserNameEmail();
		verifyEquals(true,pPatientDashboardPage.getFeedback().contains("Username email sent to patient"));
		
		log("step 6: Access Gmail and check for received email");
		log("patient userID: " + practiceTestData.getPatientUser());
		log("email: " + practiceTestData.getPatientEmail());
		log("pass: " + practiceTestData.getPatientPassword());
		
		int count = 1;
		boolean flag = false;
		do {
			boolean foundEmail = CheckEmail.validateForgotUserID(gmail, startEmailSearchDate, practiceTestData.getPatientEmail(), "Your User ID for",
					practiceTestData.getPatientUser());
			if (foundEmail) {
				assertTrue(foundEmail, "The Forgot User ID email wasn't received.");
				System.out.println("The User ID email receiced In between :" + count * 60 + "seconds");
				flag = true;
				break;
			} else {
				Thread.sleep(60000);
				count++;
			}

		} while (count < 21);
		if (!flag) {
			log("The User ID email wasn't received even after Five minutes of wait");
		}
		
	}
	
	
	/**
	 * @Author: Gajendran
	 * @Date: 07/26/2013
	 * @StepsToReproduce:
	 * Patient login
	 * 

	 * =============================================================
	 * @AreaImpacted : 
	 * @throws Exception
	 */

	@Test (enabled = true, groups = {"SmokeTest"} ,retryAnalyzer=RetryAnalyzer.class)
	public void testClickOnTabs() throws Exception {

		log("Test Case: testClickOnTabs:- SmokeTest");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData =new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 2: Click on Patientactivationtab");
		practiceHome.clickPatientactivationTab();
		Thread.sleep(2000);

		log("step 3: Click on VirtualCardSwiperTab");
		practiceHome.clickVirtualCardSwiperTab();
		Thread.sleep(2000);

		log("step 4: Click on Patient Messaging Tab");
		practiceHome.clickOnlineBillPayTab();
		Thread.sleep(2000);

		log("step 5: Click on Logout");
		practiceHome.logOut();



	}	



	/**
	 * @Author: Kiran_GT
	 * @Date: 07/26/2013
	 * @StepsToReproduce: Practice Portal login Click on Patient Messaging Link-->Quick Send
	 *                    Set Quick Send Fields and verify for Message Sent text
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */	
	@Test (enabled = true, groups = {"AcceptanceTests"},retryAnalyzer=RetryAnalyzer.class)
	public void testQuickSendPatientPDF() throws Exception {

		log("Test Case: testQuickSendPatientPDF");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData =new PracticeTestData(practice);

		//Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 2: Click on Patient Search Link");
		PatientMessagingPage pPatientMessagingPage= pPracticeHomePage.clickPatientMessagingTab();

		log("Step 3: Getting the document to upload from filepath");
		PracticeUtil pUtil = new PracticeUtil(driver);
		String value =pUtil.getFilepath(PracticeConstants.fileDirectory).concat(PracticeConstants.pdfname);

		log("Step 4:Set Quick Send Fields");
		pPatientMessagingPage.setFieldsAndPublishMessage(value);

		log("step 5:Verify the Published Message Succesfully text");
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,20,pPatientMessagingPage.publishedSuccessfullyMessage);
		verifyEquals(pPatientMessagingPage.publishedSuccessfullyMessage.getText(),"Message Published Successfully");


	}



	/**
	 * @Author: Kiran_GT
	 * @Date: 07/29/2013
	 * @StepsToReproduce: Practice Portal login Click on Online Bill Payment--->Make Payment For Patient
	 *                    Search for Patient,Make Payment for Patient and Verify for Payment Successfull Message
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */	
	@Test (enabled = true, groups = {"AcceptanceTests"})
	public void testMakePaymentForPatient() throws Exception {

		log("Test Case: testMakePaymentForPatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData =new PracticeTestData(practice);

		//Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 2:Click On Online BillPayment Tab in Practice Portal--->Make Payment For Patient");
		PayMyBillOnlinePage pPayMyBillOnlinePage = pPracticeHomePage.clickMakePaymentForPatient();

		log("step 3:Search For Patient");
		pPayMyBillOnlinePage.searchForPatient();

		log("step 4:Set Patient Transaction Fields");
		pPayMyBillOnlinePage.setPatientTransactionFields();

		log("step 5:Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,20,pPayMyBillOnlinePage.paymentConfirmationText);
		assertTrue(pPayMyBillOnlinePage.paymentConfirmationText.getText().contains(PracticeConstants.PaymentSuccessfullText));


	}


	/**
	 * @Author: bbinisha
	 * User Story : US6488
	 * @Date: 08/01/2013
	 * @StepsToReproduce:
	 * Practice portal login
	 * Click on 'Online Billpay' 
	 * Click on 'Make Payment' Link
	 * Search for the patient
	 * Enter the Payment info
	 * Click on 'Submit Payment' button.
	 * Click on Search Patient Page
	 * Verify whether the transaction is displayed.
	 * Navigate to that transaction page
	 * Click on 'Void Payment' button.
	 * Enter Void comment 
	 * Close the Pop Up
	 * Click On 'Refund Payment' button.
	 * Check whethr the patient got email in gmail inbox notifying the void transaction done.
	 * =============================================================
	 * *** Checking gmail part has to be completed******
	 * =============================================================
	 * @AreaImpacted : 
	 * @throws Exception
	 */
	@Test (enabled = true, groups = {"AcceptanceTests"})
	public void testOnlineBillPayProcess() throws Exception {

		log("Test Case: TestLoginLogout");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");	

		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData =new PracticeTestData(practice);

		log("step 2: Navigate to Login page"); 
		PracticeLoginPage practiceLogin =new PracticeLoginPage(driver, practiceTestData.getUrl());

		log("step 3: Enter credentials and login");
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log(" Step 4 : Click on Make Payment link.");
		PayMyBillOnlinePage pPayMyBillOnlinePage = practiceHome.clickMakePaymentForPatient();

		log("Step 5 : Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(PracticeConstants.PatientFirstName, PracticeConstants.PatientLastName);

		String amount = IHGUtil.createRandomNumericStringInRange(5,500);
		log("Amount: " + amount);

		log("Step 6 : Set all the transaction details");
		pPayMyBillOnlinePage.setTransactionsForOnlineBillPayProcess ( PracticeConstants.Location, PracticeConstants.Provider, PracticeConstants.processCardNum, amount, PracticeConstants.ProcessCardHolderName, PracticeConstants.processCardNum , PracticeConstants.processCardType);

		log("Step 7: Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,20,pPayMyBillOnlinePage.paymentConfirmationText);
		assertEquals(true,pPayMyBillOnlinePage.paymentConfirmationText.getText().contains(PracticeConstants.PaymentSuccessfullText));

		log("Step 7 : Navigate to Patient Search Page.");
		OnlineBillPaySearchPage onlineBillPay = new OnlineBillPaySearchPage(driver);
		PatientSearchPage patientsearchPage = onlineBillPay.clickOnPatientSearchLink();

		log(" Step 8 : Search the patient in Patient Search page.");
		patientsearchPage.searchPatient(PracticeConstants.PatientFirstName, PracticeConstants.PatientLastName);

		log(" Step 9 :Verify whether the transaction is present.");
		assertTrue(patientsearchPage.isTransactionPresent(amount, PracticeConstants.PatientFirstName, PracticeConstants.PatientLastName));

		log("Step 10 : Select the particular Transaction from the Search Result.");
		patientsearchPage.selectTheTransaction(amount, PracticeConstants.PatientFirstName, PracticeConstants.PatientLastName);
		assertFalse(pPayMyBillOnlinePage.isVoidTransactionPresent());

		log("Step 11 : Click on Void Payment Link and void the transaction.");
		pPayMyBillOnlinePage.voidPayment(PracticeConstants.voidComment);
		assertTrue(pPayMyBillOnlinePage.isVoidTransactionPresent());

/*
		This needs to be checked if it can work after QB is working again
		log("Step 13 : Click on Refund Payment and give comments and amount to refund");
		pPayMyBillOnlinePage.refundPayment(amount, PracticeConstants.refundComment);
*/

		//Checking email needs to be completed
	}


}
