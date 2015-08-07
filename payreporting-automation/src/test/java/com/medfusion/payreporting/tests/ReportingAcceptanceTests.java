package com.medfusion.payreporting.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.makePaymentpage.MakePaymentPage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.practice.tests.VirtualCardSwiperTest;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.medfusion.product.object.maps.isoreporting.page.ReportingDailyReportPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingLoginPage;
/**
 * @Author:Jakub Odvarka
 * @Date:28.7.2015
 */

@Test
public class ReportingAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	protected void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReportingLoginCheckTableLogout() throws Exception {		
		log("Test Case: Login & Data Smoke");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("step 1: Assess login elements");
		ReportingLoginPage loginPage = new ReportingLoginPage(driver, testData.getReportingUrl());
		loginPage.assessLoginPageElements();
		
		log("step 2: Log in");		
		ReportingDailyReportPage dailyPage = loginPage.login(testData.getDoctorLogin(), testData.getDoctorPassword());
				
		log("step 3: Verify table presence and integrity for default time interval");
		dailyPage.clickSearch();
		assertTrue(dailyPage.checkTableIntegrityOnly());
		
		
		log("step 2: Log out");
		dailyPage.logout(driver);
		
		log("step 3: Assess login elements after logout");
		loginPage.assessLoginPageElements();
	}
	
	//done on one date only!
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPayForPatientVerifyDailyReport() throws Exception {
		log("Test Case: Practice -> OBP for patient -> Verify in reporting");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		String amount = IHGUtil.createRandomNumericString().substring(0, 2);
		while (amount.charAt(0) == '0'){
			log("Leading zero, generating single digit payment");
			amount = IHGUtil.createRandomNumericString().substring(0, 1);
		}
		String formattedAmount = new StringBuffer(amount).insert(amount.length(), ".00").insert(0, "$").toString();
		log(formattedAmount);
		int retries = 30;
		
		log("step 1: Login to Practice Portal");	

		//Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		log("step 2: Click On Online BillPayment Tab in Practice Portal--->Make Payment For Patient");
		PayMyBillOnlinePage pPayMyBillOnlinePage = pPracticeHomePage.clickMakePaymentForPatient();

		log("step 3: Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(testData.getFirstName(), testData.getLastName());

		log("step 4: Set Patient Transaction Fields");
		pPayMyBillOnlinePage.setTransactionsForOnlineBillPayProcess(testData.getLocationName(),testData.getProviderName(), testData.getBillingAccountNumber(),
					amount, testData.getFirstName() + " " + testData.getLastName(), PortalConstants.CreditCardNumber, PortalConstants.CreditCardType);

		log("step 5: Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,20,pPayMyBillOnlinePage.paymentConfirmationText);
		verifyEquals(true,pPayMyBillOnlinePage.paymentConfirmationText.getText().contains(PracticeConstants.PaymentSuccessfullText));
		
		log("step 6: Log in to ISO Reporting");
		ReportingLoginPage loginPage = new ReportingLoginPage(driver, testData.getReportingUrl());
		ReportingDailyReportPage dailyPage = loginPage.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("step 7: Click search and validate table");
		log("SETTING DATE FROM = DATE TO");
		dailyPage.pushDateFrom();
		dailyPage.clickSearch();
		assertTrue(dailyPage.checkTableIntegrityOnly());
		
		for (int i = 0; i < retries; i++){
			log("Validating latest submission, attempt " + (i+1) + "/" + retries);
			if (formattedAmount.equals(dailyPage.getLastRowAmount())) {
				log("Paid amount found! Checking table totals refresh");
				assertTrue(dailyPage.checkTableIntegrityOnly());
				return;	
			}
			else dailyPage.clickSearch();						
			
		}		
		log("Latest found " +dailyPage.getLastRowAmount() + " expected " + formattedAmount +" => Failure");
		assertTrue(false);
		
	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHistoricalData() throws Exception {		
		log("Test Case: Historic data check");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		String from = testData.getHistoricDateFrom();
		String to = testData.getHistoricDateTo();
		log("Date from : " + from);
		log("Date to : " + to);
		log("step 1: Assess login elements");
		ReportingLoginPage loginPage = new ReportingLoginPage(driver, testData.getReportingUrl());
		loginPage.assessLoginPageElements();
		
		log("step 2: Log in");		
		ReportingDailyReportPage dailyPage = loginPage.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("step 2: Set dates and click search");
		dailyPage.fillDateFrom(from);
		dailyPage.fillDateTo(to);
		dailyPage.clickSearch();
		
		log("step 4: Verify table presence and integrity, compare with expected values");		
		assertTrue(dailyPage.checkTableContents(true,true,IHGUtil.formatNumber(Integer.parseInt(testData.getHistoricPaySum())),testData.getHistoricPayCount(),IHGUtil.formatNumber(Integer.parseInt(testData.getHistoricRefSum())),testData.getHistoricRefCount()));		
	}
	/*
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDownloadAndCompareHistoricalCsv() throws Exception {
		
	}
	*/
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPayAndVoid() throws Exception {
		log("Test Case: Practice -> OBP for patient -> Verify in reporting -> Void");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		String amount = IHGUtil.createRandomNumericString().substring(0, 2);
		while (amount.charAt(0) == '0'){
			log("Leading zero, generating single digit payment");
			amount = IHGUtil.createRandomNumericString().substring(0, 1);
		}
		String formattedAmount = new StringBuffer(amount).insert(amount.length(), ".00").insert(0, "$").toString();
		log(formattedAmount);
		int retries = 30;
		
		log("step 1: Login to Practice Portal");	

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		log("step 2: Click On Online BillPayment Tab in Practice Portal--->Make Payment For Patient");
		PayMyBillOnlinePage pPayMyBillOnlinePage = pPracticeHomePage.clickMakePaymentForPatient();

		log("step 3: Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(testData.getFirstName(), testData.getLastName());

		log("step 4: Set Patient Transaction Fields");
		pPayMyBillOnlinePage.setTransactionsForOnlineBillPayProcess(testData.getLocationName(),testData.getProviderName(), testData.getBillingAccountNumber(),
					amount, testData.getFirstName() + " " + testData.getLastName(), PortalConstants.CreditCardNumber, PortalConstants.CreditCardType);

		log("step 5: Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,20,pPayMyBillOnlinePage.paymentConfirmationText);
		verifyEquals(true,pPayMyBillOnlinePage.paymentConfirmationText.getText().contains(PracticeConstants.PaymentSuccessfullText));
		
		log("step 6: Log in to ISO Reporting");
		ReportingLoginPage loginPage = new ReportingLoginPage(driver, testData.getReportingUrl());
		ReportingDailyReportPage dailyPage = loginPage.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("step 7: Click search and validate table");
		dailyPage.clickSearch();
		assertTrue(dailyPage.checkTableIntegrityOnly());
		try{
			dailyPage.getLastRowAmount();
		}
		catch(Exception e){
			log("Exception when accessing last row payment amount! \n"
			  + "Waiting 30s for first payment in two days.");
			Thread.sleep(30000);
			
		}
		
		int i = 0;
		for (; i < retries; i++){
			log("Validating latest submission, attempt " + (i+1) + "/" + retries);
			if (formattedAmount.equals(dailyPage.getLastRowAmount())) {
				log("Paid amount found! Checking table totals refresh");
				assertTrue(dailyPage.checkTableIntegrityOnly());
				break;	
			}
			else dailyPage.clickSearch();						
			
		}		
		log("step 8: Void the payment");
		String refSum = dailyPage.getRefundSum();
		log("Current refund sum is :" + refSum);		
		refSum = IHGUtil.formatNumber(IHGUtil.deformatNumber(refSum) + IHGUtil.deformatNumber(formattedAmount));
		log("Expected after void : " + refSum);	
		dailyPage.clickDetailsLast();
		log("p1");
		Thread.sleep(500);
		dailyPage.clickRefundButton();
		log("p2");
		Thread.sleep(500);
		dailyPage.clickVoidSubmitButton();
		log("p3");
		Thread.sleep(500);
		dailyPage.clickSearch();
		for (int j = 0; j < retries; j++){
			log("Validating latest refund, attempt " + (j+1) + "/" + retries);
			if (formattedAmount.equals(dailyPage.getLastRowRefund())) {
				log("Refund amount found! Checking table totals refresh");
				assertTrue(dailyPage.checkTableContents(true,true,"","",refSum,""));
				break;	
			}
			else dailyPage.clickSearch();						
			
		}
		log("step 9: Check last status changed to void");
		assertTrue(("Void").equals(dailyPage.getLastStatus()));
		
	}
	
	public void testPayAndRefund() throws Exception {
		log("Test Case: Practice -> OBP for patient -> Verify in reporting -> Refund");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		String amount = IHGUtil.createRandomNumericString().substring(0, 2);
		while (amount.charAt(0) == '0'){
			log("Leading zero, generating single digit payment");
			amount = IHGUtil.createRandomNumericString().substring(0, 1);
		}
		amount = amount + ".31";
		String formattedAmount = new StringBuffer(amount).insert(0, "$").toString();
		log("Payment of " + formattedAmount);
		int retries = 30;
		
		log("step 1: Login to Practice Portal");	

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPortalUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

		log("step 2: Click On Online BillPayment Tab in Practice Portal--->Make Payment For Patient");
		PayMyBillOnlinePage pPayMyBillOnlinePage = pPracticeHomePage.clickMakePaymentForPatient();

		log("step 3: Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(testData.getFirstName(), testData.getLastName());

		log("step 4: Set Patient Transaction Fields");
		pPayMyBillOnlinePage.setTransactionsForOnlineBillPayProcess(testData.getLocationName(),testData.getProviderName(), testData.getBillingAccountNumber(),
					amount, testData.getFirstName() + " " + testData.getLastName(), PortalConstants.CreditCardNumber, PortalConstants.CreditCardType);

		log("step 5: Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,20,pPayMyBillOnlinePage.paymentConfirmationText);
		verifyEquals(true,pPayMyBillOnlinePage.paymentConfirmationText.getText().contains(PracticeConstants.PaymentSuccessfullText));
		
		log("step 6: Log in to ISO Reporting");
		ReportingLoginPage loginPage = new ReportingLoginPage(driver, testData.getReportingUrl());
		ReportingDailyReportPage dailyPage = loginPage.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("step 7: Click search and validate table");
		dailyPage.clickSearch();
		assertTrue(dailyPage.checkTableIntegrityOnly());
		try{
			dailyPage.getLastRowAmount();
		}
		catch(Exception e){
			log("Exception when accessing last row payment amount! \n"
			  + "Waiting 30s for first payment in two days.");
			Thread.sleep(30000);
			
		}
		
		int i = 0;
		for (; i < retries; i++){
			log("Validating latest submission, attempt " + (i+1) + "/" + retries);
			if (formattedAmount.equals(dailyPage.getLastRowAmount())) {
				log("Paid amount found! Checking table totals refresh");
				assertTrue(dailyPage.checkTableIntegrityOnly());
				break;	
			}
			else dailyPage.clickSearch();						
			
		}		
		log("step 8: Refund the payment");
		String refSum = dailyPage.getRefundSum();
		log("Current refund sum is :" + refSum);		
		refSum = IHGUtil.formatNumber(IHGUtil.deformatNumber(refSum) + 314);
		log("Expected after void : " + refSum);	
		dailyPage.clickDetailsLast();
		log("Details !");
		Thread.sleep(500);
		dailyPage.clickVoidButton();
		log("Refunding static $3.14!");
		dailyPage.fillRefundAmount(IHGUtil.formatNumber(314));		
		Thread.sleep(500);
		dailyPage.clickRefundSubmitButton();
		log("Click!");
		Thread.sleep(500);		
		dailyPage.clickSearch();
		dailyPage.clickCloseButton();
		for (int j = 0; j < retries; j++){
			log("Validating latest refund, attempt " + (j+1) + "/" + retries);
			if (IHGUtil.formatNumber(314).equals(dailyPage.getLastRowRefund())) {
				log("Refund amount found! Checking table totals refresh");
				assertTrue(dailyPage.checkTableContents(true,true,"","",refSum,""));
				break;	
			}
			else dailyPage.clickSearch();						
			
		}					
			
	}	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPayAsPatientVerifyDailyReport() throws Exception {
		log("Test Case: Practice -> Pay As Patient -> Verify in reporting");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		//Payment uses PortalConstants.Amount (100.00)
		String amount = "100";		
		String formattedAmount = new StringBuffer(amount).insert(amount.length(), ".00").insert(0, "$").toString();
		log(formattedAmount);
		int retries = 30;
		
		log("step 1: Login to Patient Portal");			
		log("USER NAME: " + testData.getUserId());
		log("Password: " + testData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage ploginPage = new PortalLoginPage(driver, testData.getUrl());
		MyPatientPage myPatientPage = ploginPage.login(testData.getUserId(), testData.getPassword());
		log("step 3: Verify for My Patient Page ");
		PortalUtil.setPortalFrame(driver);
		verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

		log("step 4: Click on Make Payment Link ");
		MakePaymentPage makePaymentPage = myPatientPage.clickMakePaymentLnk();

		log("step 5: Set Make Payments Fields");
		makePaymentPage.setMakePaymentFields("150");

		log("step 6: Logout of Patient Portal");
		myPatientPage.logout(driver);				
		
		log("step 6: Log in to ISO Reporting");
		ReportingLoginPage loginPage = new ReportingLoginPage(driver, testData.getReportingUrl());
		ReportingDailyReportPage dailyPage = loginPage.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("step 7: Click search and validate table");
		dailyPage.clickSearch();
		assertTrue(dailyPage.checkTableIntegrityOnly());
		
		for (int i = 0; i < retries; i++){
			log("Validating latest submission, attempt " + (i+1) + "/" + retries);
			if (formattedAmount.equals(dailyPage.getLastRowAmount())) {
				log("Paid amount found! Checking table totals refresh");
				assertTrue(dailyPage.checkTableIntegrityOnly());
				return;	
			}
			else dailyPage.clickSearch();						
			
		}		
		log("Latest found " +dailyPage.getLastRowAmount() + " expected " + formattedAmount +" => Failure");
		assertTrue(false);
		
	}
	
	//TODO
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPayVCSVerifyDailyReport() throws Exception {
		log("Test Case: Practice -> OBP for patient -> Verify in reporting");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
				
		int retries = 30;
		
		log("step A1: Login to Practice Portal");	

		//Utilizing older test, let's construct a practiceTestData object
		Practice prax = new Practice();
		prax.url = testData.getPortalUrl();
		prax.username = testData.getDoctorLogin();
		prax.password = testData.getDoctorPassword();
		PracticeTestData ptestData = new PracticeTestData();
		ptestData.setPractice(prax);
		
		log("step A2: Generic payment using portal constants");
		VirtualCardSwiperTest virtualCardSwiperTest = new VirtualCardSwiperTest();
		// Executing Test
		String formattedAmount = virtualCardSwiperTest.virtualCardSwipeTest(driver, ptestData);
		log("Payment done : " + formattedAmount);
		
		log("step A3: Log in to ISO Reporting");
		ReportingLoginPage loginPage = new ReportingLoginPage(driver, testData.getReportingUrl());
		ReportingDailyReportPage dailyPage = loginPage.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		
		log("step A4: Click search and validate table");
		dailyPage.clickSearch();
		assertTrue(dailyPage.checkTableIntegrityOnly());
		
		for (int i = 0; i < retries; i++){
			log("Validating latest submission, attempt " + (i+1) + "/" + retries);
			if (formattedAmount.equals(dailyPage.getLastRowAmount())) {
				log("Paid amount found! Checking table totals refresh");
				assertTrue(dailyPage.checkTableIntegrityOnly());
				return;	
			}
			else dailyPage.clickSearch();						
			
		}		
		log("Latest found " +dailyPage.getLastRowAmount() + " expected " + formattedAmount +" => Failure");
		assertTrue(false);
		
	}
	
		
}
