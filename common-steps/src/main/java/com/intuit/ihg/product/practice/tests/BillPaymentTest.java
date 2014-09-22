package com.intuit.ihg.product.practice.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;

public class BillPaymentTest extends BaseTestNGWebDriver{

	public String billPaymentTest(WebDriver driver, PracticeTestData practiceTestData, String accountNumber) throws Exception {
		
		log("Test Case: TestLoginLogout");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Get Data from Excel");	

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();

		log("Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		log("Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage.searchForBillPayment(accountNumber);

		log("Get Bill Details");
		onlineBillPaySearchPage.getBillPayDetails();

		log("Set Payment Communication Details");
		onlineBillPaySearchPage.setPaymentCommunicationDetails();

		log("Logout of Practice Portal");
		practiceHome.logOut();
		
		String uniquePracticeResponse = Long.toString(onlineBillPaySearchPage.getCreatedTs())+PracticeConstants.BillPaymentSubject;
		return uniquePracticeResponse;
		
		
	}
	
 
}
