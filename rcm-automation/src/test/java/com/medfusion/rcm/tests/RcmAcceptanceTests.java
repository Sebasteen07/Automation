package com.medfusion.rcm.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.mail.Harakirimail;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.jalapeno.page.PayBillsStatementPage.JalapenoPayBillsStatementPage;
import com.medfusion.rcm.utils.RCMUtil;

/**
 * @Author:Jakub Odvarka
 * @Date:24.4.2015
 */

@Test
public class RcmAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendStmtVerifyNotificationsMessagesBalance() throws Exception {

		log(this.getClass().getName());
		RCMUtil util = new RCMUtil(driver);
		Harakirimail mail = new Harakirimail(driver);
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();	
		
		log("Post eStatement");
		util.postStatementToPatient(testData.getRcmStatementRest(), IHGUtil.getEnvironmentType().toString());
		
		log("Check email notification and URL");
		String box = testData.getEmail().split("@")[0];		
		assertTrue(mail.catchNewMessageCheckLinkUrl(box, "Your patient eStatement is now available","Visit our website",testData.getUrl(), 50));
		
		log("Log in");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver,testData.getUrl());
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), testData.getPassword());		
		
		log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		
		assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		log("Expect an estatement message");
		assertTrue(jalapenoMessagesPage.isMessageFromEstatementsDisplayed(driver));
		
		log("Archive the message");
		jalapenoMessagesPage.archiveOpenMessage();
					
		JalapenoPayBillsStatementPage statementPage = jalapenoMessagesPage.goToPayBillsPage(driver);
		log("Check expected balance");
		assertTrue(testData.getStatementBalanceDue().equals(statementPage.getBalanceDue(driver)));
		log("Balance checks out!");
		
	}
	
}
