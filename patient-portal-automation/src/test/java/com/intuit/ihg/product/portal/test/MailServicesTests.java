package com.intuit.ihg.product.portal.test;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.Mailinator;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class MailServicesTests extends BaseTestNGWebDriver {
	
	
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
		PortalUtil util = new PortalUtil(driver);
		util.cleanInbox(testcasesData.getUsername(),testcasesData.getPassword());
	}

	@Test
	public void testMailinator() throws Exception {
		Mailinator mailinator = new Mailinator();
		String username = "test1854medfusion";
		String emailSubject = "test";
		String findInEmail = "testing";
		String targetUrl = "medfusion.com";
		int retries = 3;
		assertTrue(mailinator.isMessageInInbox(username, emailSubject, findInEmail, retries));
		assertFalse(mailinator.isMessageInInbox(username, emailSubject, "something not there", retries));
		assertTrue(mailinator.catchNewMessageCheckLinkUrl(username, emailSubject, findInEmail,
				targetUrl, retries));
		assertFalse(mailinator.catchNewMessageCheckLinkUrl(username, emailSubject, findInEmail,
				targetUrl + "nononono", retries));
	}
}
