package com.medfusion.payprovisioning.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.product.object.maps.provisioning.page.ProvisioningDashboardPage;
import com.medfusion.product.object.maps.provisioning.page.ProvisioningLoginPage;
import com.medfusion.product.object.maps.provisioning.page.ProvisioningMerchantDetailPage;
import com.medfusion.product.object.maps.provisioning.page.ProvisioningSearchMerchantPage;

public class ProvisioningAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	protected void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProvisioningLoginCheckDashboardLogout() throws Exception {
		log("Test Caser: Login & Data Smoke");
		log("Execution Environment:" + IHGUtil.getEnvironmentType());
		log("Execution Browser" + TestConfig.getBrowserType());
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		log("Step 1: Assess login elements");
		ProvisioningLoginPage loginPage = new ProvisioningLoginPage(driver, testData.getProvisioningUrl());
		assertTrue(loginPage.assessLoginPageElements());
	
		log("Step 2: Log in");
		ProvisioningDashboardPage provisioningDashboardPage =  loginPage.login(testData.getUserId(), testData.getPassword());
				
		log("Step 3: Verify presence of dashboard page");
		assertTrue(provisioningDashboardPage.checkDashboardContent(testData.getUserId()));
		
		log("Step 4: Logout");
		loginPage = provisioningDashboardPage.logout();
		
		log("Step 5: Assess login elements");
		assertTrue(loginPage.assessLoginPageElements());
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProvisioningStaticSearch() throws Exception {
		log("Test Caser: Login & Data Smoke");
		log("Execution Environment:" + IHGUtil.getEnvironmentType());
		log("Execution Browser" + TestConfig.getBrowserType());
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();		
	
		log("Step 1: Log in");
		ProvisioningLoginPage loginPage = new ProvisioningLoginPage(driver, testData.getProvisioningUrl());
		ProvisioningDashboardPage pDashboardPage =  loginPage.login(testData.getUserId(), testData.getPassword());
				
		log("Step 2: Click search merchants");
		ProvisioningSearchMerchantPage pSearchPage = pDashboardPage.clickSearchMerchant();
		
		log("Step 3: Search for a static merchant, verify search table");
		assertTrue(pSearchPage.searchVerifyDetails("asdf", "2560785077", "3", "[ASDF]"));
		
		log("Step 4: Navigate to merchant details and reverify");
		ProvisioningMerchantDetailPage pMerchantDetailPage = pSearchPage.clickFirstResultMerchantDetail();
		Thread.sleep(1000);
		assertTrue(pMerchantDetailPage.verifyBasicInfo("2560785077", "3", "[ASDF]"));
		log("Step 5: Logout");
		pDashboardPage.logout();		
		
	}
}
