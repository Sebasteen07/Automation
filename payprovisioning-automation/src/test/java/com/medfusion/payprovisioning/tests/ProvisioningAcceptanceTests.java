package com.medfusion.payprovisioning.tests;

import java.util.Random;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.product.object.maps.provisioning.page.ProvisioningAddMerchantPage;
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
		log("Test Case : Login & Data Smoke");
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
		log("Test Case : Static Search");
		log("Execution Environment :" + IHGUtil.getEnvironmentType());
		log("Execution Browser" + TestConfig.getBrowserType());
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();		
	
		log("Step 1: Log in");
		ProvisioningLoginPage loginPage = new ProvisioningLoginPage(driver, testData.getProvisioningUrl());
		ProvisioningDashboardPage pDashboardPage =  loginPage.login(testData.getUserId(), testData.getPassword());
				
		log("Step 2: Click search merchants");
		ProvisioningSearchMerchantPage pSearchPage = pDashboardPage.clickSearchMerchant();
		
		log("Step 3: Search for a static merchant, verify search table");
		assertTrue(pSearchPage.searchVerifyDetails(testData.getRegex(), testData.getStaticMerchantMID(), testData.getStaticExternalId(), testData.getStaticMerchantName()));
		
		log("Step 4: Navigate to merchant details and reverify");
		ProvisioningMerchantDetailPage pMerchantDetailPage = pSearchPage.clickFirstResultMerchantDetail();
		pMerchantDetailPage.waitTillLoaded();
		assertTrue(pMerchantDetailPage.verifyBasicInfo(testData.getStaticMerchantMID(), testData.getStaticExternalId(), testData.getStaticMerchantName()));
		log("Step 5: Logout");
		pDashboardPage.logout();		
		
	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateMerchantVerifySearchAndDetails() throws Exception {
		log("Test Case : Create and Verify");
		log("Execution Environment:" + IHGUtil.getEnvironmentType());
		log("Execution Browser" + TestConfig.getBrowserType());
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();	
		String randNum = IHGUtil.createRandomNumericString(8);
		String newName = "[Automation]TestPatient"+ randNum;
		Random rBool = new Random();
		boolean amex = rBool.nextBoolean();
		boolean visa = rBool.nextBoolean();
		boolean discover = rBool.nextBoolean();
		boolean carecred = true;
		//TODO
		String newExternalId = "0"; 
		String newVantiv = IHGUtil.createRandomNumericString(3);
		String newElement = IHGUtil.createRandomNumericString(3);
		String newZip = IHGUtil.createRandomNumericString(5);
		
		
		String newRemitName = "{REMIT}" + randNum;
		log("Creating merchant : " + newName);
		log("Step 1: Log in");
		ProvisioningLoginPage loginPage = new ProvisioningLoginPage(driver, testData.getProvisioningUrl());
		ProvisioningDashboardPage pDashboardPage =  loginPage.login(testData.getUserId(), testData.getPassword());
				
		log("Step 2: Click add a merchant");
		ProvisioningAddMerchantPage pAddMerchantPage = pDashboardPage.clickAddMerchant();
		ProvisioningMerchantDetailPage pMerchantDetailPage = pAddMerchantPage.fillAndSubmit(newName, newExternalId, newVantiv, newElement, "1 Randomstreet", "", "Randotown", newZip, "United States", "Alabama",
				newRemitName, "1 Remitstreet", "", "Remitown", "54321", "United States", "Alaska", amex, visa, discover, carecred);
		
		
		log("Step 3: Verify merchant details");
		pMerchantDetailPage.waitTillLoaded();
		assertTrue(pMerchantDetailPage.verifyInfoWithoutMid(newExternalId, newName, newVantiv, newElement, "1 Randomstreet", "", newZip, "United States", "Alabama", newRemitName, "1 Remitstreet", "", "Remitown", "54321", "United States", "Alaska"));
		assertTrue(pMerchantDetailPage.checkCards(amex, visa, discover, carecred));
		
		log("Step 4: Search for created merchant, verify search table");		
		pMerchantDetailPage.searchMerchantButton.click();
		ProvisioningSearchMerchantPage pSearchPage = new ProvisioningSearchMerchantPage(driver);
		pSearchPage.searchForMerchant(newName);
		assertTrue(pSearchPage.getFirstResultMerchantName().equals(newName)&&pSearchPage.getFirstResultExternalID().equals(newExternalId));				
	}
}
 