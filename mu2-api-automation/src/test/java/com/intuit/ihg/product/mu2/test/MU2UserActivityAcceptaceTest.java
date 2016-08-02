package com.intuit.ihg.product.mu2.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.BaseTestSoftAssert;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.mu2.utils.MU2Constants;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.MyAccountPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.AccountActivity.ViewAccountActivityPage;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.TestcasesData;


public class MU2UserActivityAcceptaceTest  extends BaseTestNGWebDriver {
	
	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	
	
	//@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUserActivityLog() throws Exception {
		log("Test Case: testUserActivityLog");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 3:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 4:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

		log("step 5: Click on Manage Health Information link");
		ViewAccountActivityPage viewAccountActivity = pMyAccountPage.addAccountActivityLink();
		viewAccountActivity.clickOnViewAccountActivity();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Activity Log']")));
				
		List<Object> viewList=IHGUtil.searchResultTable(driver,"//table[@id='activityTable']/tbody",new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_VIEWED)));
		if(!viewList.isEmpty())
		{
			BaseTestSoftAssert.assertTrue(((Boolean) viewList.get(1)).booleanValue());
		}
		else
		{
			BaseTestSoftAssert.assertTrue(false,"Health Information Viewed event not present");	
		}
		
				
		List<Object> downloadList=IHGUtil.searchResultTable(driver,"//table[@id='activityTable']/tbody",new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_DOWNLOADED)));
		if(!downloadList.isEmpty())
		{
			BaseTestSoftAssert.assertTrue(((Boolean) downloadList.get(1)).booleanValue());
		}
		else
		{
			BaseTestSoftAssert.assertTrue(false,"Health Information Downloaded event not present");	
		}
		
		List<Object> transmitList=IHGUtil.searchResultTable(driver,"//table[@id='activityTable']/tbody",new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_TRANSMITTED)));
		if(!transmitList.isEmpty())
		{
			BaseTestSoftAssert.assertTrue(((Boolean) transmitList.get(1)).booleanValue());
		}
		else
		{
			BaseTestSoftAssert.assertTrue(false,"Health Information Transmitted event not present");	
		}
	
			 WebElement closeViewer = driver.findElement(By.linkText("Close Viewer"));
			 closeViewer.click();
	}

}
