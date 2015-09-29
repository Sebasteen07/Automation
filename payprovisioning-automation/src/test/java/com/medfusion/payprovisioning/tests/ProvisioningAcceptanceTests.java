package com.medfusion.payprovisioning.tests;

import java.util.Random;

import org.openqa.selenium.support.PageFactory;
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
import com.medfusion.product.object.maps.provisioning.page.ProvisioningEditStatementOptionsPage;
import com.medfusion.product.object.maps.provisioning.page.ProvisioningLoginPage;
import com.medfusion.product.object.maps.provisioning.page.ProvisioningMerchantDetailPage;
import com.medfusion.product.object.maps.provisioning.page.ProvisioningSearchMerchantPage;
import com.medfusion.product.object.maps.provisioning.page.ProvisioningUsersRolesPage;

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
		//we need at least one true
		boolean carecred = true;
		boolean amex = rBool.nextBoolean();
		boolean visa = rBool.nextBoolean();
		boolean discover = rBool.nextBoolean();
		
		//Statement options, we have a merchant name to check for even if all were to be false
		//merchantName = newName
		//statementLogoName = ''
		String payByPhoneNum = "+1234567890";
		String payByPhoneHours = "10-11";
		String billQueryPhoneNum = "+9876543210";
		String billQueryHours = "11-11:01";
		boolean agingBoxes = rBool.nextBoolean();
		boolean insuranceBoxes = false; 
		if(agingBoxes) insuranceBoxes = rBool.nextBoolean();
		boolean displayDetails = rBool.nextBoolean();
		boolean payByCheck = rBool.nextBoolean();
		boolean payByMoneyOrder = rBool.nextBoolean();
		boolean displayDetach = false;
		if(payByMoneyOrder) displayDetach = rBool.nextBoolean();
		boolean displayMerchantName = rBool.nextBoolean();

		
		//Set static externalId of 0 for testing merchants
		String newExternalId = "0";
		String newZip = IHGUtil.createRandomNumericString(5);
		
		
		String newRemitName = "{REMIT}" + randNum;
		log("Creating merchant : " + newName);
		log("Step 1: Log in");
		ProvisioningLoginPage loginPage = new ProvisioningLoginPage(driver, testData.getProvisioningUrl());
		ProvisioningDashboardPage pDashboardPage =  loginPage.login(testData.getUserId(), testData.getPassword());
				
		log("Step 2: Click add a merchant");
		ProvisioningAddMerchantPage pAddMerchantPage = pDashboardPage.clickAddMerchant();
		ProvisioningMerchantDetailPage pMerchantDetailPage = pAddMerchantPage.fillAndSubmit(newName, newExternalId, "1000", "1 Randomstreet", "", "Randotown", newZip, "United States", "Alabama",
				newRemitName, "1 Remitstreet", "", "Remitown", "54321", "United States", "Alaska", amex, visa, discover, carecred);
		
		
		log("Step 3: Verify merchant details");
		pMerchantDetailPage.waitTillLoaded();
		assertTrue(pMerchantDetailPage.verifyInfoWithoutMid(newExternalId, newName, "1000", "1 Randomstreet", "", newZip, "United States", "Alabama", newRemitName, "1 Remitstreet", "", "Remitown", "54321", "United States", "Alaska"));
		assertTrue(pMerchantDetailPage.checkCards(amex, visa, discover, carecred));
		
		//TODO step4 accounts and Ids 
		
		log("Step 5: Add Statement Options");
		//DEMO+PROD difference, fix to button order once deployed		
		pMerchantDetailPage.statementOptionsButton.click();
		ProvisioningEditStatementOptionsPage statementPage = PageFactory.initElements(driver, ProvisioningEditStatementOptionsPage.class);
		statementPage.fillSettingsAndSubmit(false, newName, payByPhoneNum, payByPhoneHours, billQueryPhoneNum, billQueryHours, agingBoxes, insuranceBoxes, displayDetails, payByCheck, payByMoneyOrder, displayDetach, displayMerchantName);
		//post and load merchant detail
		Thread.sleep(2000);
		
		log("Step 6: Back to edit Statement Options and verify");
		//DEMO+PROD difference, fix to button order once deployed		
		pMerchantDetailPage.statementOptionsButton.click();
		statementPage = PageFactory.initElements(driver, ProvisioningEditStatementOptionsPage.class);
		assertTrue(statementPage.verifySettings(newName, payByPhoneNum, payByPhoneHours, billQueryPhoneNum, billQueryHours, agingBoxes, insuranceBoxes, displayDetails, payByCheck, payByMoneyOrder, displayDetach, displayMerchantName));
		pMerchantDetailPage = statementPage.clickCancel();
		
		
		
		log("Step L: Search for created merchant, verify search table");		
		pMerchantDetailPage.searchMerchantButton.click();
		ProvisioningSearchMerchantPage pSearchPage = new ProvisioningSearchMerchantPage(driver);
		pSearchPage.searchForMerchant(newName);
		assertTrue(pSearchPage.getFirstResultMerchantName().equals(newName)&&pSearchPage.getFirstResultExternalID().equals(newExternalId));				
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddUserDeleteUser() throws Exception {
		log("Test Case : Add User and delete user");
		log("Execution Environment:" + IHGUtil.getEnvironmentType());
		log("Execution Browser" + TestConfig.getBrowserType());
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();	
		String randNum = IHGUtil.createRandomNumericString(5);
		//Can't be read from UI
		String newName = "[Automation]User"+ randNum;
		
		//TODO
		log("Step 1: Log in");
		ProvisioningLoginPage loginPage = new ProvisioningLoginPage(driver, testData.getProvisioningUrl());
		ProvisioningDashboardPage pDashboardPage =  loginPage.login(testData.getUserId(), testData.getPassword());

		log("Step 2: Search for a static merchant, verify search table");
		ProvisioningSearchMerchantPage pSearchPage = pDashboardPage.clickSearchMerchant();
		assertTrue(pSearchPage.searchVerifyDetails(testData.getRegex(), testData.getStaticMerchantMID(), testData.getStaticExternalId(), testData.getStaticMerchantName()));
		
		log("Step 3: Navigate to merchant details");
		ProvisioningMerchantDetailPage pMerchantDetailPage = pSearchPage.clickFirstResultMerchantDetail();
		pMerchantDetailPage.waitTillLoaded();
		
		log("Step 4: Navigate to Add Users and Roles");
		ProvisioningUsersRolesPage 	pUsersRolesPage = pMerchantDetailPage.clickUsersRolesAddOrEdit();
		
		log("Step 5: Delete user/s if there is/are");
		pUsersRolesPage.deleteOldUsers();
		
		log("Step 6: Create new user and verify on Users and Roles page");
		assertTrue(pUsersRolesPage.addNewUserAndVerify(randNum, newName));
		
		log("Step 7: Verify on Merchant Page");
		pUsersRolesPage.clickCancle();
		pMerchantDetailPage.waitTillLoaded();
		assertTrue(pMerchantDetailPage.verifyExistenceOfUser(randNum));
		
		log("Step 8: Delete new user and verify on Users and Roles page");
		pMerchantDetailPage.clickUsersRolesAddOrEdit();
		assertTrue(pUsersRolesPage.deleteUserAndVerify());
		
		log("Step 9: Verify on Merchant Page");
		pUsersRolesPage.clickCancle();
		pMerchantDetailPage.waitTillLoaded();
		assertTrue(pMerchantDetailPage.verifyNonexistenceOfUser());
		
		log("Step 10: Logout");
		pDashboardPage.logout();
	}	
	
}
 