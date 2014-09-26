package com.medfusion.jalapeno.test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.jalapeno.utils.Jalapeno;
import com.medfusion.product.jalapeno.JalapenoCreatePatientTest;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

/**
 * @Author:Jakub Calabek
 * @Date:24.7.2013
 */

@Test
public class JalapenoAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAssessLoginPageElements() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		assertTrue(jalapenoLoginPage.assessLoginPageElements());

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginValidCredentials() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		jalapenoLoginPage.login(testData.getUserId(), testData.getPassword());
		// TODO Implement Verification on Home Page once developed
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginInvalidCredentials() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Load login page");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		jalapenoLoginPage.login(testData.getUserId(), "WrongPassword");
		// TODO Implement Verification on Home Page once developed
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatient() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		JalapenoCreatePatientTest jalapenoCreatePatientTest = new JalapenoCreatePatientTest(driver);
		jalapenoCreatePatientTest.createPatient(driver, testData);
		
	}
}
