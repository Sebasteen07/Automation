package com.medfusion.product.practice.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.practice.api.pojo.PracticeTestData;
import com.medfusion.product.practice.api.utils.PracticeConstants;

public class BillPaymentTest extends BaseTestNGWebDriver {

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

		String uniquePracticeResponse = Long.toString(onlineBillPaySearchPage.getCreatedTs()) + PracticeConstants.BILL_PAYMENT_SUBJECT;
		return uniquePracticeResponse;


	}


}
