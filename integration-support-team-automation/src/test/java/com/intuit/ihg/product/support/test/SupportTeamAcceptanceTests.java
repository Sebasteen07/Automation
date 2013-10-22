package com.intuit.ihg.product.support.test;


import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxMessage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxPage;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.support.utils.Support;
import com.intuit.ihg.product.support.utils.SupportTestcasesData;
import com.intuit.ihg.product.support.utils.SupportUtil;

public class SupportTeamAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	


	/**
	 * @Author:- bkrishnankutty
	 * @Date:-5/30/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce:
	 * LogIn to PHR portal using ccduser from excel
	 * Post CONSOLIDATED_CCD request
	 * Click on Document link
	 * Get first date from table list and assert it
	 * Click on it and then Click on View Health Information link
	 * click link on ShareWithADoctor
	 * Add Addresses and Validate
	 * Click On Close AfterSharingTheHealthInformation
	 * Click on Close Viewer
	 * Log into Patient Portal
	 * Go to inbox
	 * open the first mail and validate the subject and date
	 * click ReviewHealthInformation
	 * click link on ShareWithADoctor
	 * Add Addresses and Validate
	 * Click On Close AfterSharingTheHealthInformation
	 * Click on Close Viewer
	 * =============================================================
	 * @throws Exception
	 */

	//Note testcase is not working on prod because step 13 :- css :- Identifer for View All message has a small diiference ['>a' not there]

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportThroughEHDC() throws Exception {

		}


	/**
	 * @Author:- bkrishnankutty
	 * @Date:-5/30/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce:
	 * LogIn to PHR portal using ccduser from excel
	 * Post CONSOLIDATED_CCD request
	 * Click on Document link
	 * Get first date from table list and assert it
	 * Click on it and then Click on View Health Information link
	 * Click on Close Viewer
	 * Log into Patient Portal
	 * Go to inbox
	 * open the first mail and validate the subject and date
	 * click ReviewHealthInformation
	 * click link on ShareWithADoctor
	 * Add Addresses and Validate
	 * Click On Close AfterSharingTheHealthInformation
	 * Click on Close Viewer
	 * =============================================================
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNonCCDImportThroughEHDC() throws Exception {

		log("Test Case: testNonCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

			}




}



