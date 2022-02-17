//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.patientportal2.test;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import com.intuit.ihg.common.utils.PatientFactory;
import com.medfusion.pojos.Patient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.object.maps.patientportal2.page.AcheckerPage.AChecker;
import com.medfusion.product.object.maps.patientportal2.page.AcheckerPage.AChecker.LevelOfWCAG;
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
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.tests.PatientActivationSearchTest;

@Test
public class PatientPortal2MU3AcceptanceTests extends BaseTestNGWebDriver {

		private PropertyFileLoader testData;
		private LevelOfWCAG level = LevelOfWCAG.AAA;

		@BeforeMethod(alwaysRun = true)
		public void setUpPortal2Test() throws Exception {
				log("Getting Test Data");
				testData = new PropertyFileLoader();
		}

		@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
		public void testLoginAndDashboardPages() throws InterruptedException {
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
				String patientsEmail = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');

				logStep("Patient Activation on Practice Portal");
				PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
				String unlockLinkPortal = patientActivationSearchTest.getPatientActivationLink(driver, testData, patientsEmail);

				logStep("Copy source of Patient Verification Page and continue");
				PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, unlockLinkPortal);
				StringSelection sourceVerificationPage = patientVerificationPage.getHtmlSource();
				SecurityDetailsPage securityDetailPage = patientVerificationPage
						.fillPatientInfoAndContinue(PracticeConstants.ZIP_CODE, JalapenoConstants.DATE_OF_BIRTH_MONTH_NO, JalapenoConstants.DATE_OF_BIRTH_DAY,
								JalapenoConstants.DATE_OF_BIRTH_YEAR);

				logStep("Copy source of Account Details Page and validate");
				AChecker achecker = copySourceNavigateToACheckerAndValidate(securityDetailPage);

				logStep("Validate Patient Verification Page");
				pastAndValidateSource(achecker, sourceVerificationPage);
		}

		@Test(enabled = true, groups = {"acceptance-MU3"}, retryAnalyzer = RetryAnalyzer.class)
		public void testPatientAccountPages() throws InterruptedException {
				logStep("Login patient");
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
				JalapenoHomePage homePage = loginPage.login(testData.getProperty("user.mu3.account.page"), testData.getPassword());

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
		public void testHealthRecordPage() throws InterruptedException {
				logStep("Login patient");
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
				JalapenoHomePage homePage = loginPage.login(testData.getCCDPatientUsername(), testData.getPassword());

				logStep("Click on Health record menu");
				MedicalRecordSummariesPage healthRecordPage = homePage.clickOnMedicalRecordSummaries(driver);

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

		private AChecker copySourceNavigateToACheckerAndValidate(MedfusionPage page) throws InterruptedException {
				StringSelection source = page.getHtmlSource();
				logStep("Navigate to AChecker");
				AChecker achecker = openAchecker();
				pastAndValidateSource(achecker, source);
				return achecker;
		}

		private void pastAndValidateSource(AChecker achecker, StringSelection source) throws InterruptedException {
			    Thread.sleep(3000);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(source, source);
				achecker.validate();
				
		}

	}
