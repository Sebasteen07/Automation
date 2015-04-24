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
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.patientMessaging.PatientMessagingPage;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.medfusion.product.jalapeno.JalapenoCreatePatientTest;
import com.medfusion.product.jalapeno.JalapenoHealthKey6Of6DifferentPractice;
import com.medfusion.product.jalapeno.JalapenoHealthKey6Of6Inactive;
import com.medfusion.product.jalapeno.JalapenoHealthKey6Of6SamePractice;
//import com.medfusion.product.jalapeno.PreferenceDeliverySelection;
//import com.medfusion.product.jalapeno.PreferenceDeliverySelection.Method;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.CcdViewer.JalapenoCcdPage;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage2;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage3;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage4;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.jalapeno.page.MyAccountPage.JalapenoMyAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.PatientActivationPage.JalapenoPatientActivationPage;
import com.medfusion.product.object.maps.jalapeno.page.PayBillsStatementPage.JalapenoPayBillsStatementPage;
import com.medfusion.rcm.utils.RCMUtil;

/**
 * @Author:Jakub Calabek
 * @Date:24.7.2013
 */

@Test
public class RcmAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendStatementAndVerifyUI() throws Exception {

		log(this.getClass().getName());
		RCMUtil util = new RCMUtil(driver);
		Harakirimail mail = new Harakirimail(driver);
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();	
		/*
		log("Post eStatement");
		util.postStatementToPatient(testData.getRcmStatementRest(), IHGUtil.getEnvironmentType().toString());
		
		log("Check email notification");
		String box = testData.getEmail().split("@")[0];
		assertTrue(mail.isMessageInInbox(box, "Your patient eStatement is now available","Visit our website", 20));		
		*/
		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), testData.getPassword());		
		
		log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		/*
		assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		log("Expect an estatement message");
		assertTrue(jalapenoMessagesPage.isMessageFromEstatementsDisplayed(driver));
		
		jalapenoMessagesPage.archiveOpenMessage();
		*/			
		JalapenoPayBillsStatementPage statementPage = jalapenoMessagesPage.goToPayBillsPage(driver);
		log("Balance due :" + statementPage.getBalanceDue(driver));
		assertTrue(testData.getStatementBalanceDue().equals(statementPage.getBalanceDue(driver)));
		log("devstop");
		Thread.sleep(10000);
		
	}
	
}
