package com.medfusion.patientportal2.test;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal1.page.AChecker;
import com.medfusion.product.object.maps.patientportal1.page.AChecker.LevelOfWCAG;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;
import com.medfusion.product.patientportal2.utils.PortalConstants;
import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.PracticeTestData;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.tests.PatientActivationSearchTest;

@Test
public class PatientPortal2MU3AcceptanceTests extends BaseTestNGWebDriver {

	PropertyFileLoader testData;
	private LevelOfWCAG level = LevelOfWCAG.AAA;

	// TODO move stuff around stepCounter to BaseTestNGWebDriver
	private int stepCounter;

	@Override
	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		super.setUp();

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		testData = new PropertyFileLoader();

		log("Resetting step counter");
		stepCounter = 0;
	}

	private void logStep(String logText) {
		log("STEP " + ++stepCounter + ": " + logText);
	}

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}


	@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginPage() throws Exception {
		logStep("Load login page");
		new JalapenoLoginPage(driver, testData.getUrl());

		copySourceNavigateToACheckerAndValidate();
	}

	@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMessagingPages() throws Exception {
		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getCCDPatientUsername(), testData.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.assessMessagesElements());

		copySourceNavigateToACheckerAndValidate();
	}

	@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateAccountPages() throws Exception {
		JalapenoPatient patient = new JalapenoPatient(testData);

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());

		logStep("Click on create new account");
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();

		logStep("Copy source of Patient Demographic Page and continue");
		Thread.sleep(15000);
		StringSelection sourceDemographicPage = new StringSelection(driver.getPageSource());
		patientDemographicPage.fillInPatientData(patient);
		patientDemographicPage.continueToSecurityPage();

		logStep("Copy source of Patient Security Page");
		StringSelection sourceSecurityPage = new StringSelection(driver.getPageSource());

		logStep("Navigate to AChecker");
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		logStep("Check Patient Demographic Page");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sourceDemographicPage, sourceDemographicPage);
		achecker.validate();

		logStep("Check Patient Security Page");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sourceSecurityPage, sourceSecurityPage);
		achecker.validate();
	}

	@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientActivationPages() throws Exception {
		logStep("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
		String patientsEmail = IHGUtil.createRandomEmailAddress(testDataFromProp.getEmail(), '.');

		logStep("Patient Activation on Practice Portal");
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		String unlockLinkPortal = patientActivationSearchTest.getPatientActivationLink(driver, practiceTestData, patientsEmail, testDataFromProp);

		logStep("Copy source of Patient Verification Page and continue");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
		StringSelection sourceVerificationPage = new StringSelection(driver.getPageSource());
		patientVerificationPage.fillPatientInfoAndContinue(PracticeConstants.Zipcode, PortalConstants.DateOfBirthMonthNumber, PortalConstants.DateOfBirthDay,
				PortalConstants.DateOfBirthYear);

		logStep("Copy source of Account Details Page and continue");
		StringSelection sourceDetailsPage = new StringSelection(driver.getPageSource());

		logStep("Navigate to AChecker");
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		logStep("Check Patient Verification Page");
		pastAndValidateSource(achecker, sourceVerificationPage);

		logStep("Check Account Details Page");
		pastAndValidateSource(achecker, sourceDetailsPage);
	}

	private void copySourceNavigateToACheckerAndValidate() {
		logStep("Copy source");
		StringSelection source = new StringSelection(driver.getPageSource());

		logStep("Navigate to AChecker");
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		logStep("Validate");
		pastAndValidateSource(achecker, source);
	}

	private void pastAndValidateSource(AChecker achecker, StringSelection source) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(source, source);
		achecker.validate();
	}
}
