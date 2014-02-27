package com.intuit.ihg.product.portal.test;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;

import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class CleanGmailInboxTest extends BaseTestNGWebDriver {
	
	
	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCleanGmailInbox() throws Exception {
		
		log("Test Case: testCleanGmailInbox");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());
		
		log("step 2: Clean the Gmail Inbox");
		String sSubject = String.format("Your password has been reset");
		PortalUtil util = new PortalUtil(driver);
		util.cleanInbox(testcasesData.getUsername(),testcasesData.getPassword());
	}
}
