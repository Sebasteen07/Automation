package com.medfusion.patientportal2.test;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import com.intuit.ihg.common.utils.PatientFactory;
import com.medfusion.pojos.Patient;
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
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountActivityPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountDevicesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPreferencesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountSecurityPage;
import com.medfusion.product.patientportal2.utils.PortalConstants;
import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.PracticeTestData;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.tests.PatientActivationSearchTest;

@Test public class PatientPortal2MU3AcceptanceTests extends BaseTestNGWebDriver {

		private PropertyFileLoader testData;
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
		public void testLoginAndDashboardPages() throws Exception {
				logStep("Load login page and copy source");
				JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
				StringSelection sourceLoginPage = jalapenoLoginPage.getHtmlSource();

				logStep("Load patient dashboard page and validate it");
				JalapenoHomePage jalapenoHomepage = jalapenoLoginPage.login(testData.getCCDPatientUsername(), testData.getPassword());
				AChecker achecker = copySourceNavigateToACheckerAndValidate(jalapenoHomepage);

				logStep("Validate login page");
				pastAndValidateSource(achecker, sourceLoginPage);
		}

		@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
		public void testMessagingPages() throws Exception {
				logStep("Login patient");
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
				JalapenoHomePage homePage = loginPage.login(testData.getCCDPatientUsername(), testData.getPassword());

				logStep("Click on messages solution");
				JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
				assertTrue(messagesPage.areBasicPageElementsPresent());

				copySourceNavigateToACheckerAndValidate(messagesPage);
		}

		@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
		public void testCreateAccountPages() throws Exception {
				Patient patient = PatientFactory.createJalapenoPatient(testData.getUserId(), testData); //patient is not created in Portal, only to get to next page

				logStep("Load login page");
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());

				logStep("Click on create new account");
				PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();

				logStep("Copy source of Patient Demographic Page and continue");
				Thread.sleep(15000);
				StringSelection sourceDemographicPage = patientDemographicPage.getHtmlSource();

				logStep("Fill in patient demographic data");
				patientDemographicPage.fillInPatientData(patient);
				SecurityDetailsPage securityDetailPage = patientDemographicPage.continueToSecurityPage();

				logStep("Copy source of Patient Security Page and validate");
				AChecker achecker = copySourceNavigateToACheckerAndValidate(securityDetailPage);

				logStep("Validate Patient Demographic Page");
				pastAndValidateSource(achecker, sourceDemographicPage);
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
				StringSelection sourceVerificationPage = patientVerificationPage.getHtmlSource();
				SecurityDetailsPage securityDetailPage = patientVerificationPage
						.fillPatientInfoAndContinue(PracticeConstants.Zipcode, PortalConstants.DateOfBirthMonthNumber, PortalConstants.DateOfBirthDay,
								PortalConstants.DateOfBirthYear);

				logStep("Copy source of Account Details Page and validate");
				AChecker achecker = copySourceNavigateToACheckerAndValidate(securityDetailPage);

				logStep("Validate Patient Verification Page");
				pastAndValidateSource(achecker, sourceVerificationPage);
		}

		@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
		public void testPatientAccountPages() throws Exception {

				logStep("Login patient");
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
				JalapenoHomePage homePage = loginPage.login(testData.getProperty("userMu3AcountPage"), testData.getPassword());

				logStep("Click on Account");
				JalapenoMyAccountProfilePage myAccountPage = homePage.goToAccountPage();

				logStep("Copy source of Account Profile tab Page and go to Security tab");
				StringSelection accountProfileTab = myAccountPage.getHtmlSource();
				JalapenoMyAccountSecurityPage myAccountSecurityPage = myAccountPage.goToSecurityTab(driver);

				logStep("Copy source of Account Security tab Page and go to Preferences tab");
				StringSelection accountSecurityTab = myAccountSecurityPage.getHtmlSource();
				JalapenoMyAccountPreferencesPage myAccountPreferencesPage = myAccountSecurityPage.goToPrefererencesTab();

				logStep("Copy source of Account Preferences tab Page and go to Activity page");
				StringSelection accountPreferencesTab = myAccountPreferencesPage.getHtmlSource();
				JalapenoMyAccountActivityPage myAccountActivityPage = myAccountPreferencesPage.goToActivityTab(driver);

				logStep("Copy source of Account Activity tab Page and go to My Devices page");
				StringSelection accountActivityTab = myAccountActivityPage.getHtmlSource();
				JalapenoMyAccountDevicesPage myAccountDevicesPage = myAccountActivityPage.goToDevicesTab(driver);


				AChecker achecker = copySourceNavigateToACheckerAndValidate(myAccountDevicesPage);

				logStep("Validate Account activity tab page, Account Preferences tab page, Security tab page and Account Profile tab page");
				pastAndValidateSource(achecker, accountActivityTab);
				pastAndValidateSource(achecker, accountPreferencesTab);
				pastAndValidateSource(achecker, accountSecurityTab);
				pastAndValidateSource(achecker, accountProfileTab);
		}

		@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
		public void testHealthRecordPage() throws Exception {

				logStep("Login patient");
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
				JalapenoHomePage homePage = loginPage.login(testData.getCCDPatientUsername(), testData.getPassword());

				logStep("Click on Health record menu");
				MedicalRecordSummariesPage healthRecordPage = homePage.goToHealthRecordsPage();
				assertTrue(healthRecordPage.areBasicPageElementsPresent());

				logStep("Copy source of Health Record Page and validate");
				copySourceNavigateToACheckerAndValidate(healthRecordPage);
		}


		@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
		public void testCcdViewerPage() throws Exception {
				logStep("Login patient");
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
				JalapenoHomePage homePage = loginPage.login(testData.getCCDPatientUsername(), testData.getPassword());

				logStep("Click on messages solution");
				JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
				assertTrue(messagesPage.areBasicPageElementsPresent());

				logStep("Click on View health data");
				JalapenoCcdViewerPage ccdViewerPage = messagesPage.findCcdMessage(driver);

				StringSelection source = ccdViewerPage.getIframeSource();

				AChecker achecker = openAchecker();
				pastAndValidateSource(achecker, source);
		}

		private AChecker openAchecker() {
				logStep("Navigate to AChecker and set level");
				AChecker achecker = new AChecker(driver);
				achecker.setupLevel(level);

				return achecker;
		}

		private AChecker copySourceNavigateToACheckerAndValidate(MedfusionPage page) {
				logStep("Copy source");
				StringSelection source = page.getHtmlSource();
				logStep("Navigate to AChecker");
				AChecker achecker = openAchecker();

				logStep("Validate");
				pastAndValidateSource(achecker, source);

				return achecker;
		}

		private void pastAndValidateSource(AChecker achecker, StringSelection source) {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(source, source);
				achecker.validate();
		}
}
