package com.intuit.ihg.product.mobile.test;



import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.MobileHomePage;
import com.intuit.ihg.product.mobile.page.MobileSignInPage;
import com.intuit.ihg.product.mobile.utils.Mobile;
import com.intuit.ihg.product.mobile.utils.MobileTestCaseData;


public class MobileSmokeTests extends BaseTestNGWebDriver {
	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	/**
	 * @Author:yvaddavalli
	 * @Date:10/08/2013
	 * @StepsToReproduce: Launch Mobile URL and Login. Validate Home Page and
	 *                    Log Out from Mobile
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "SmokeTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileLoginLogout() throws Exception {

		log("Test Case: TestMobileLoginLogout");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");

		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3:Assert Welcome text");
		assertTrue(verifyTextPresent(driver, "Welcome"));

		log("step 4:LogOut");
		pMyPatientPage.clickLogout();

	}

	/**
	 * @Author:yvaddavalli
	 * @Date:10/08/2013
	 * @StepsToReproduce: Launch Mobile URL and Login. Click on Appointment Tab
	 *                    Log Out from Mobile
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "SmokeTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTabAppointmentRequest() throws Exception {

		log("Test Case: testTabAppointmentRequest");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");

		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3: Click ApptReqTab");
		MobileBasePage mobileBasePage = pMyPatientPage.clickARLink();

		log("step 4: Click Home");
		mobileBasePage.clickHome();

		log("step 5:LogOut");
		pMyPatientPage.clickLogout();

	}

	@Test(enabled = true, groups = { "SmokeTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTabRxRenewal() throws Exception {

		log("Test Case: testTabRxRenewal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3: Click RxRenewalTab");
		MobileBasePage mobileBasePage = pMyPatientPage.clickRXLink();

		log("step 4: Click Home");
		mobileBasePage.clickHome();

		log("step 5:LogOut");
		pMyPatientPage.clickLogout();

	}

	@Test(enabled = true, groups = { "SmokeTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTabAsk() throws Exception {
		log("Test Case: testTabAsk");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3: Click Ask a Question Tab");
		MobileBasePage mobileBasePage = pMyPatientPage.clickAAQLink();

		log("step 4: Click Home");
		mobileBasePage.clickHome();

		log("step 5:LogOut");
		pMyPatientPage.clickLogout();

	}

	@Test(enabled = true, groups = { "SmokeTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTabMakeAPayment() throws Exception {

		log("Test Case: testTabMakeAPayment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3: Click Bill Pay Tab");
		MobileBasePage mobileBasePage = pMyPatientPage.clickBillPayLink();

		log("step 4: Click Home");
		mobileBasePage.clickHome();

		log("step 5:LogOut");
		pMyPatientPage.clickLogout();

	}

}
