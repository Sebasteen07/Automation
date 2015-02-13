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
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.medfusion.product.jalapeno.JalapenoCreatePatientTest;
import com.medfusion.product.jalapeno.JalapenoHealthKey6Of6DifferentPractice;
import com.medfusion.product.jalapeno.JalapenoHealthKey6Of6Inactive;
import com.medfusion.product.jalapeno.JalapenoHealthKey6Of6SamePractice;
import com.medfusion.product.jalapeno.PreferenceDeliverySelection;
import com.medfusion.product.jalapeno.PreferenceDeliverySelection.Method;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.PatientActivationPage.JalapenoPatientActivationPage;

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
		
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), testData.getPassword());
		assertTrue(jalapenoHomePage.assessHomePageElements());
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
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
		
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), "InvalidPassword");
		assertFalse(jalapenoHomePage.assessHomePageElements());
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatient() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		JalapenoCreatePatientTest jalapenoCreatePatientTest = new JalapenoCreatePatientTest();
		JalapenoHomePage jalapenoHomePage = jalapenoCreatePatientTest.createPatient(driver, testData);		
		
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		JalapenoLoginPage jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		jalapenoHomePage = jalapenoLoginPage.login(jalapenoCreatePatientTest.getEmail(), jalapenoCreatePatientTest.getPassword());	
		PreferenceDeliverySelection preferenceDeliverySelection = new PreferenceDeliverySelection();
		jalapenoHomePage = preferenceDeliverySelection.SelectDeliveryMethod(driver, Method.ELECTRONIC);
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientActivation() throws Exception {

		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		log("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);
		
		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
		
		log("Patient Activation on Practice Portal");
		String unlockLink = patientActivationSearchTest.PatientActivation(driver, practiceTestData, testcasesData.getEmail(), 
				testDataFromProp.getDoctorLogin(), testDataFromProp.getDoctorPassword(), testDataFromProp.getPortalUrl());	
		
		log("Finishing of patient activation");
		JalapenoPatientActivationPage jalapenoPatientActivationPage = new JalapenoPatientActivationPage(driver, unlockLink);
	
		JalapenoHomePage jalapenoHomePage = jalapenoPatientActivationPage.fillInPatientActivation("",
			testDataFromProp.getPassword(), testDataFromProp.getSecretQuestion(), 
			testDataFromProp.getSecretAnswer(), testDataFromProp.getphoneNumer());
		
		log("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		log("Logging out");
		JalapenoLoginPage jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
		
		log("Logging again: " + patientActivationSearchTest.getPatientIdString() + " \\ " + testDataFromProp.getPassword());
		jalapenoHomePage = jalapenoLoginPage.login(patientActivationSearchTest.getPatientIdString(),testDataFromProp.getPassword());	
		
		log("Select PAPER delivery preference");
		PreferenceDeliverySelection preferenceDeliverySelection = new PreferenceDeliverySelection();
		jalapenoHomePage = preferenceDeliverySelection.SelectDeliveryMethod(driver, Method.PAPER);
		assertTrue(jalapenoHomePage.assessHomePageElements());
		
		log("Logging out");
		
		jalapenoLoginPage = jalapenoHomePage.logout(driver);
		assertTrue(jalapenoLoginPage.assessLoginPageElements());
	}
	
	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6SamePractice() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		
		JalapenoHealthKey6Of6SamePractice jalapenoHealthKey6Of6SamePractice = new JalapenoHealthKey6Of6SamePractice();
		jalapenoHealthKey6Of6SamePractice.healthKey6Of6SamePractice(driver, testData);
			
	}
	
	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6DifferentPractice() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		JalapenoHealthKey6Of6DifferentPractice jalapenoHealthKey6Of6DifferentPractice = new JalapenoHealthKey6Of6DifferentPractice();
		jalapenoHealthKey6Of6DifferentPractice.healthKey6Of6DifferentPractice(driver, testData);
		
	}
		
	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientHealthKey6outOf6Inactive() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		JalapenoHealthKey6Of6Inactive jalapenoHealthKey6Of6Inactive = new JalapenoHealthKey6Of6Inactive();
		jalapenoHealthKey6Of6Inactive.healthKey6Of6Inactive(driver, testData);
	
	}
	
}
