package com.medfusion.jalapeno.test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.jalapeno.utils.JalapenoTestCaseData;
import com.medfusion.jalapeno.utils.Jalapeno;
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
	
			log("Get Data from Excel");
			Jalapeno jalapenoTestProperties = new Jalapeno();
			JalapenoTestCaseData jalapenoTestCaseData = new JalapenoTestCaseData(
					jalapenoTestProperties);
	
			log("Load login page");
			JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver,"https://dev3.dev.medfusion.net/jalapenoconfiguratortest/portal/#/user/login");
			assertTrue(jalapenoLoginPage.assessLoginPageElements());
	
		}
		
		@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
		public void testLoginValidCredentials() throws Exception {
			
			log(this.getClass().getName());
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());
	
			log("Get Data from Excel");
			Jalapeno jalapenoTestProperties = new Jalapeno();
			JalapenoTestCaseData jalapenoTestCaseData = new JalapenoTestCaseData(jalapenoTestProperties);
	
			log("Load login page");
			JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver,"https://dev3.dev.medfusion.net/jalapenoconfiguratortest/portal/#/user/login");
			JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login("jc2014119325", "medfusion123");
		}
		
		@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
		public void testLoginInvalidCredentials() throws Exception {
			
			log(this.getClass().getName());
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());
	
			log("Get Data from Excel");
			Jalapeno jalapenoTestProperties = new Jalapeno();
			JalapenoTestCaseData jalapenoTestCaseData = new JalapenoTestCaseData(jalapenoTestProperties);
	
			log("Load login page");
			JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver,"https://dev3.dev.medfusion.net/jalapenoconfiguratortest/portal/#/user/login");
			JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login("jc2014119325", "Medfusion123");
			
		}
}
