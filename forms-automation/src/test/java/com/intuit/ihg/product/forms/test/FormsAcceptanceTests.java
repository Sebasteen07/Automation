package com.intuit.ihg.product.forms.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.ccd.CCDTest;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.AddQuestionsToCategoryPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomForms;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormAddCategoriesPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormLayoutPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormPreviewPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.ManageYourFormsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CustomFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CustomFormPageSection;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.healthform.HealthFormPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.MyAccountPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.FormWelcomePage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.custom_pages.SpecialCharFormFirstPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.custom_pages.SpecialCharFormSecondPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.prereg_pages.FormBasicInfoPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.prereg_pages.FormCurrentSymptomsPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.prereg_pages.FormFamilyHistoryPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.prereg_pages.FormIllnessConditionsPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.prereg_pages.FormMedicationsPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.prereg_pages.FormSocialHistoryPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.supplemental_pages.CurrentSymptomsSupplementalPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.supplemental_pages.IllnessesSupplementalPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPartiallyFilledPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPatientFormsPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPatientFormsResultPage;
import com.medfusion.product.object.maps.practice.page.customform.ViewPatientFormPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.patientportal1.flows.CheckOldCustomFormTest;
import com.medfusion.product.patientportal1.flows.CreatePatientTest;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.PortalUtil;
import com.medfusion.product.patientportal1.utils.TestcasesData;
import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.PracticeTestData;

public class FormsAcceptanceTests extends BaseTestNGWebDriver {

	protected void logTestEnvironmentInfo(String testName) {
		log(testName);
		log("Environment on which Testcase is Running: " + IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: " + TestConfig.getBrowserType());
	}

	/**
	 * Fills out Output form for CCD test. Needs the form to be opened and on
	 * the first (welcome) page
	 * 
	 * @param diacriticString
	 *            - String to fill out in Symptoms comments, used for testing
	 *            special diacritic
	 */
	private void fillOutputForm(String diacriticString) throws Exception {
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		FormBasicInfoPage basicInfoPage = welcomePage.clickSaveContinue(FormBasicInfoPage.class);

		FormCurrentSymptomsPage currentSymptomsPage = basicInfoPage.clickSaveContinue(FormCurrentSymptomsPage.class);
		currentSymptomsPage.setBasicSymptoms();
		currentSymptomsPage.enterComment(diacriticString);
		CurrentSymptomsSupplementalPage symptomsSupplemental = currentSymptomsPage
				.clickSaveContinue(CurrentSymptomsSupplementalPage.class);
		symptomsSupplemental.fillLogicalAnswersForPdfTest();

		FormMedicationsPage medicationsPage = symptomsSupplemental.clickSaveContinue(FormMedicationsPage.class);
		medicationsPage.setNoMedications();

		IllnessesSupplementalPage illnessesPage = medicationsPage.clickSaveContinue(IllnessesSupplementalPage.class);
		illnessesPage.fillOut();

		FormFamilyHistoryPage familyPage = illnessesPage.clickSaveContinue(FormFamilyHistoryPage.class);
		familyPage.setNoFamilyHistory();

		FormSocialHistoryPage socialHistoryPage = familyPage.clickSaveContinue(FormSocialHistoryPage.class);
		socialHistoryPage.fillOutDefaultExerciseLength();
		socialHistoryPage.clickSaveContinue();
		socialHistoryPage.submitForm();
	}

	protected MyPatientPage createPatient(TestcasesData portalData) throws Exception {
		CreatePatientTest createPatient = new CreatePatientTest();
		createPatient.setUrl(portalData.getFormsAltUrl());
		return createPatient.createPatient(driver, portalData);
	}

	/**
	 * Logs in to Patient Portal with default test patient and opens up a form
	 * selected by the identifier
	 * 
	 * @param formIdentifier
	 * @return
	 * @throws Exception
	 */
	protected MyPatientPage openFormOnPatientPortal(String formIdentifier) throws Exception {
		log("Get Portal Data from Excel ##########");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalData.getFormsAltUrl());

		log("Log in to Patient Portal");
		PortalLoginPage loginPage = new PortalLoginPage(driver, portalData.getFormsAltUrl());
		MyPatientPage pMyPatientPage = loginPage.login(portalData.getUsername(), portalData.getPassword());

		// just a workaround because there is npp still displaying for the same
		// patient
		if (pMyPatientPage.isTermsOfUseDisplayed()) {
			log("Terms of Use page is displayed");
			pMyPatientPage.acknowledgeTermsOfUse();
		}

		log("Go to forms page and open the \"" + formIdentifier + "\" form");
		HealthFormPage formPage = pMyPatientPage.clickFillOutFormsLink();
		formPage.openDiscreteForm(formIdentifier);
		return pMyPatientPage;
	}

	protected PracticeHomePage loginToPracticePortal() throws Exception {
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		return practiceLogin.login(practiceTestData.getFormUser(), practiceTestData.getFormPassword());
	}

	protected SearchPatientFormsPage getPracticePortalSearchFormsPage() throws Exception {
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getFormUser(),
				practiceTestData.getFormPassword());

		log("Click CustomFormTab");
		SearchPatientFormsPage searchFormsPage = practiceHome.clickCustomFormTab();

		// check if the page is loaded, sometimes the test ends up on login page
		// at this point
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!searchFormsPage.isPageLoaded() && practiceLogin.isLoginPageLoaded()) {
			searchFormsPage = practiceLogin.login(practiceTestData.getFormUser(), practiceTestData.getFormPassword())
					.clickCustomFormTab();
		}
		driver.manage().timeouts().implicitlyWait(SitegenConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);

		assertTrue(searchFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + " failed to load.");
		return searchFormsPage;
	}

	/**
	 * Verifies that record of completed or partially completed form is from the
	 * current day and that the pdf is downloadable
	 */
	private void verifyFormsDateAndPDF(ViewPatientFormPage viewFormPage) throws Exception {
		log("Verify date and download code");
		// take the date of form submission
		String submittedDate = IHGUtil.extractDateFromText(viewFormPage.getLastUpdatedDateText());
		// get current date in the same format as the date at the page
		String currentDate = IHGUtil.getFormattedCurrentDate(submittedDate);
		assertEquals(submittedDate, currentDate, "Form submitted today not found");

		log("Download URL: " + viewFormPage.getDownloadURL());
		URLStatusChecker status = new URLStatusChecker(driver);
		assertEquals(status.getDownloadStatusCode(viewFormPage.getDownloadURL(), RequestMethod.GET), 200);
	}

	protected void verifyFormsDatePatientPortal(HealthFormPage formsPage, String formName) throws Exception {
		PortalUtil.setPortalFrame(driver);
		String submittedDate = formsPage.getSubmittedDate(formName);
		String currentDate = IHGUtil.getFormattedCurrentDate(submittedDate);
		assertEquals(submittedDate, currentDate, "Form submitted today not found");
	}

	protected void checkPDF(HealthFormPage formsPage, String formName) throws Exception {
		PortalUtil.setPortalFrame(driver);
		URLStatusChecker status = new URLStatusChecker(driver);
		String pdfLink;
		try {
			pdfLink = formsPage.getPDFDownloadLink(formName);
		} catch (StaleElementReferenceException e) {
			pdfLink = formsPage.getPDFDownloadLink(formName);
		} catch (NoSuchElementException f) {
			log("PDF not found!");
			throw f;
		}
		assertEquals(status.getDownloadStatusCode(pdfLink, RequestMethod.GET), 200);
	}

	protected String createFormSG() throws Exception {
		String newFormName = SitegenConstants.DISCRETEFORMNAME + IHGUtil.createRandomNumericString().substring(0, 4);

		logTestEnvironmentInfo("testDiscreteFormDeleteCreatePublish");
		Sitegen sitegen = new Sitegen();
		SitegenTestData SGData = new SitegenTestData(sitegen);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps().logInUserToSG(driver,
				SGData.getFormUser(), SGData.getFormPassword());
		// Get the current window handle before opening new window
		String parentHandle = driver.getWindowHandle();

		log("step 1: Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 2: Unpublish and delete all forms and create a new one");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		pManageDiscreteForms.createNewForm();

		log("step 3: Initialize the new form");
		pManageDiscreteForms.prepareFormForTest(newFormName);

		log("step 4: Publish the saved Discrete Form");
		pManageDiscreteForms.publishForm(newFormName);

		log("step 5: Close the window and logout from SiteGenerator");
		// Switching back to original window using previously saved handle
		// descriptor
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();

		return pManageDiscreteForms.getWelcomeMessage();
	}

	@Test(groups = { "smokeTest" })
	public void formsConfigSmokeTest() throws Exception {
		SitegenTestData testData = new SitegenTestData(new Sitegen());
		SiteGenSteps sgSteps = new SiteGenSteps();

		logTestEnvironmentInfo("formsConfigSmokeTest");
		DiscreteFormsList formsPage = sgSteps.logInUserToSG(driver, testData.getFormUser(), testData.getFormPassword())
				.clickLnkDiscreteForms();
		assertTrue(formsPage.isPageLoaded());
	}

	@Test(enabled = true, groups = { "PatientForms" })
	public void testQuotationMarksInForm() throws Exception {
		logTestEnvironmentInfo("testQuotationMarksInForm");

		openFormOnPatientPortal(SitegenConstants.SPECIAL_CHARS_FORM);

		log("Step 5: Fill the form out with values containing quotes");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		SpecialCharFormFirstPage customPage1 = welcomePage.initToFirstPage(SpecialCharFormFirstPage.class);
		customPage1.selectQuotatedAnswers();

		SpecialCharFormSecondPage customPage2 = customPage1.clickSaveContinue(SpecialCharFormSecondPage.class);
		customPage2.selectAnswerQuoteMark();
		customPage2.signConsent();
		customPage2.clickSaveContinue();
		customPage2.submitForm();
	}

	/**
	 * @Author: Adam Warzel
	 * @Date: April-01-2014
	 * @UserStory: US7083 Creates a new user. Tests if filling out a form
	 *             generates a PDF, if link for downloading the PDF appears in
	 *             Patient Portal and if the link is working and also whether
	 *             corresponding CCD was generated. Then it checks if the
	 *             submitted date is accurate and if patient's DOB has not been
	 *             changed
	 */
	@Test(enabled = true, groups = { "PatientForms" })
	public void testFormPdfCcd() throws Exception {
		long timestamp = System.currentTimeMillis() / 1000L;
		String xml;
		// easy bruising is mapped to following term in Forms Configurator in
		// SiteGen
		String easyBruisingString = "ABO donor$$$easy";
		String diacriticString = "¿¡eñÑeŘ\"";

		logTestEnvironmentInfo("testDiscreteFormPDF");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		String url = portalData.getFormsAltUrl();
		log("Patient Portal URL: " + url);

		log("step 1: Click on Sign Up Fill details in Create Account Page");
		CreatePatientTest createPatient = new CreatePatientTest();
		createPatient.setUrl(url);
		MyPatientPage pMyPatientPage = createPatient.createPatient(driver, portalData);

		log("step 2: Click on forms and open the form");
		HealthFormPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		formsPage.openDiscreteForm(SitegenConstants.PDF_CCD_FORM);

		log("Step 3: Fill out the form");
		fillOutputForm(diacriticString);

		log("Step 4: Test if PDF is downloadable");
		checkPDF(formsPage, SitegenConstants.PDF_CCD_FORM);

		log("Step 5: Test if CCD is produced");
		log("Calling rest");
		xml = CCDTest.getFormCCD(timestamp, portalData.getRestUrl());
		assertTrue(xml.contains(easyBruisingString), "Symptom not found in the CCD, printing the CCD:\n" + xml);

		log("Step 6: Test if the submission date is correct");
		verifyFormsDatePatientPortal(formsPage, SitegenConstants.PDF_CCD_FORM);

		log("Step 7: Log out and in again");
		driver.switchTo().defaultContent();
		PortalLoginPage loginpage = pMyPatientPage.clickLogout(driver);
		loginpage.navigateTo(driver, url);
		pMyPatientPage = loginpage.login(createPatient.getEmail(), createPatient.getPassword());

		log("Step 8: Test if the DOB has not been changed");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();
		String accountDOB = IHGUtil.convertDate(pMyAccountPage.getDOB(), "MM/dd/yyyy", "MMMMM/dd/yyyy");
		assertEquals(portalData.getDOB(), accountDOB, "Date of birth is not accurate!");

	}

	@Test(enabled = true, groups = { "PatientForms" })
	public void testFormPracticePortal() throws Exception {

		logTestEnvironmentInfo("testFormPracticePortal");

		log("Step 1: Open the form");
		MyPatientPage pMyPatientPage = openFormOnPatientPortal(SitegenConstants.PRACTICE_FORM);

		log("Step 2: Fill out the form");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);

		FormBasicInfoPage demographPage = welcomePage.initToFirstPage(FormBasicInfoPage.class);

		FormMedicationsPage medsPage = demographPage.clickSaveContinue(FormMedicationsPage.class);
		medsPage.setNoMedications();

		FormIllnessConditionsPage illsPage = medsPage.clickSaveContinue(FormIllnessConditionsPage.class);
		illsPage.checkMononucleosis();
		illsPage.clickSaveContinueSamePage(3);

		log("Submitting form");
		illsPage.submitForm();

		log("Step 3: Logout of patient portal");
		pMyPatientPage.logout(driver);

		SearchPatientFormsPage pSearchPatientFormsPage = getPracticePortalSearchFormsPage();

		log("step 6: Search for PatientForms With Status Open");
		SearchPatientFormsResultPage pSearchPatientFormsResultPage = pSearchPatientFormsPage
				.SearchDiscreteFormsWithOpenStatus(SitegenConstants.PRACTICE_FORM);

		log("step 7: View the Result");
		ViewPatientFormPage pViewPatientFormPage = pSearchPatientFormsResultPage.clickViewLink();
		verifyFormsDateAndPDF(pViewPatientFormPage);
	}

	@Test(enabled = true, groups = { "PatientForms" })
	public void testPartiallyCompletedForm() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 5);

		logTestEnvironmentInfo("testPartiallyCompletedForm");

		log("Step 1: Open the form");
		MyPatientPage myPatientPage = openFormOnPatientPortal(SitegenConstants.PRACTICE_FORM);

		log("Step 2: Fill out the form");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		welcomePage.initToFirstPage(FormBasicInfoPage.class);
		welcomePage.clickSaveAndFinishAnotherTime();
		driver.switchTo().defaultContent();

		wait.until(ExpectedConditions.elementToBeClickable(myPatientPage.getLogoutLink()));
		log("Logging out");
		myPatientPage.getLogoutLink().click();

		log("Step 3: Go to Practice Portal forms tab");
		SearchPatientFormsPage pSearchPatientFormsPage = getPracticePortalSearchFormsPage();
		SearchPartiallyFilledPage searchPage = pSearchPatientFormsPage.getPartiallyFilledSearch();
		searchPage.clickSearch();
		ViewPatientFormPage resultPage = searchPage.selectPatientsFirstForm();
		verifyFormsDateAndPDF(resultPage);
	}

	/**
	 * User Story ID in Rally: US544 - TA30648 StepsToReproduce: Log in to SG Go
	 * to Forms Config Unpublish all forms Delete all forms Create a new form
	 * and configure it Create a custom section and test saving it without name
	 * and questions Save the form Publish it Test viewing the form on Patient
	 * Portal === Prerequisite for the test case to run========= Practice
	 * configured Practices configured on: DEV3, DEMO, PROD
	 * ============================================================
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "PatientForms" })
	public void testDiscreteFormDeleteCreatePublish() throws Exception {

		String welcomeMessage = createFormSG();
		log("step 6: Go to Patient Portal using the original window");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("URL: " + portalData.getFormsUrl());

		log("step 7: Log in to Patient Portal");
		PortalLoginPage loginPage = new PortalLoginPage(driver, portalData.getFormsUrl());
		MyPatientPage pMyPatientPage = loginPage.login(portalData.getUsername(), portalData.getPassword());

		log("step 8: Click On Start Registration Button and verify welcome page of the previously created form");
		FormWelcomePage pFormWelcomePage = pMyPatientPage.clickStartRegistrationButton(driver);
		assertEquals(pFormWelcomePage.getMessageText(), welcomeMessage);
	}

	/**
	 * @Author: bkrishnankutty
	 * @Date: 05/4/2013
	 * @StepsToReproduce: Login to Patient Portal Click on CustomForm Fill
	 *                    CustomForm and submit Download InsuranceHealthForm
	 *                    pdf-- validate HTTP Status Code Logout from Patient
	 *                    Portal Login to Practice Portal On Practice Portal
	 *                    Home page Click on CustomFormTab Search for
	 *                    PatientForms With Status Open View and Validate the
	 *                    Result ==============================================
	 *                    ===============
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "CustomForms" })
	public void testCustomForms() throws Exception {

		log("Test Case: testCustomForms");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Retrieving test case data");
		Portal portal = new Portal();
		TestcasesData patientData = new TestcasesData(portal);

		log("URL: " + patientData.geturl());
		log("USER NAME: " + patientData.getUsername());
		log("Password: " + patientData.getPassword());

		log("step 1: Create Patient");
		CreatePatientTest createPatient = new CreatePatientTest();
		MyPatientPage pMyPatientPage = createPatient.createPatient(driver, patientData);

		log("step 2: Click on CustomForm");
		HealthFormPage pHealthForm = pMyPatientPage.clickFillOutFormsLink();

		log("step 3: Fill CustomForm");
		pHealthForm.fillInsuranceHealthForm();

		assertFalse(driver.getPageSource().contains("Female question"));

		pHealthForm.submitInsuranceHealthForm();
		assertEquals(pHealthForm.InsuranceHelthform.getText(),
				"Thank you for completing our Ivan Insurance Health Form ( Testing).");

		log("step 4: Download InsuranceHealthForm -- validate HTTP Status Code");
		assertEquals(pHealthForm.clickInsuranceHealthFormDownloadText(), 200,
				"Download of InsuranceHealth Form PDF returned unexpected HTTP status code");

		log("step 7: Logout of Patient Portal");
		pMyPatientPage.logout(driver);

		log("step 8: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(),
				practiceTestData.getPassword());

		log("step 8: On Practice Portal Home page Click CustomFormTab");
		SearchPatientFormsPage pSearchPatientFormsPage = practiceHome.clickCustomFormTab();
		assertTrue(pSearchPatientFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + " failed to load.");

		log("step 9: Search for PatientForms With Status Open");
		SearchPatientFormsResultPage pSearchPatientFormsResultPage = pSearchPatientFormsPage
				.SearchPatientFormsWithOpenStatus(patientData.getFirstName(), patientData.getLastName(),
						patientData.getDob_Month(), patientData.getDob_Day(), patientData.getDob_Year());

		log("step 10: View the Result");
		pSearchPatientFormsResultPage.clickViewLink();

		log("step 11: Verify the Result");
		String actualPatientName = pHealthForm.Patientname.getText().trim();

		log("Displayed patient name is :" + actualPatientName);
		assertEquals(pHealthForm.Patientname.getText().trim().contains("Patient Name : Ihgqa"), true);
		assertTrue(verifyTextPresent(driver, "Patient DOB : 01/11/1987"));
	}

	/**
	 * @Author:-Shanthala : Modified :bbinisha : Modified-Modified: Prokop
	 *                    Rehacek
	 * @Date:- 07-03-2013
	 * @User Story ID in Rally : US6152 and US6151 and US7626
	 * @StepsToReproduce: Go to siteGen Enter the credentials Search for the
	 *                    practice Click on Custom Form Click on Create Custom
	 *                    Form Publish Custom Form and check for preview
	 *                    Unpublish Custom Form, check for Preview and delete
	 *                    unpublished custom form
	 *
	 *                    === Prerequisite for the test case to run=========
	 *                    Nurse Named :-
	 *
	 *                    ====Valid Custom Form details required. Test data
	 *                    would be updated after getting proper test data
	 *                    =============================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "CustomForms" })
	public void testCustomFormPublished() throws Exception {

		logTestEnvironmentInfo("testCustomFormPublished");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testcasesData = new SitegenTestData(sitegen);

		SiteGenSteps.logSGLoginInfo(testcasesData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testcasesData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testcasesData.getAutomationUser(),
				testcasesData.getAutomationUserPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(),
				"Expected the SiteGen HomePage  to be loaded, but it was not.");

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 4: Click on Custom Forms");
		String winHandleSiteGen = driver.getWindowHandle();
		CreateCustomForms pManageCustomForms = pSiteGenPracticeHomePage.clickCustomForms();
		String winHandleCustomBuilder = driver.getWindowHandle();

		log("step 5: Clear forms - unpublish and delete forms created by this method in previous runs");
		ManageYourFormsPage plinkOnManageCustomForm = pManageCustomForms.clicklnkManageCustomForm();
		plinkOnManageCustomForm.unpublishFormsNamedLike(SitegenConstants.FORMTITLE);
		plinkOnManageCustomForm.deleteFormsNamedLike(SitegenConstants.FORMTITLE);
		log("step 6: Click on Create Custom Form");
		CreateCustomFormPage plinkOnCustomForm = pManageCustomForms.clicklnkCreateCustomForm();

		String customFormTitle = SitegenConstants.FORMTITLE + IHGUtil.createRandomNumber();

		log("step 7: Enter Custom Form details");
		assertTrue(plinkOnCustomForm.isSearchPageLoaded(),
				"Expected the SiteGen Create a Custom Form page to be loaded to create a new custom form with details, but it was not.");
		CustomFormAddCategoriesPage pCustomFormAddCategories = plinkOnCustomForm.enterCustomFormDetails(
				SitegenConstants.FORMTYPE, customFormTitle, SitegenConstants.FORMINSTRUCTIONS,
				SitegenConstants.FORMMESSAGE);

		log("step 8: Build a Custom Form");
		assertTrue(pCustomFormAddCategories.isSearchPageLoaded(),
				"Expected the SiteGen Build a Custom Form page to be loaded to add categories into the custom form, but it was not.");
		AddQuestionsToCategoryPage pAddCAtegories = pCustomFormAddCategories
				.addCategoriesDetails(SitegenConstants.FORMCATEGORY);

		log("step 9A: Add Question1 to category 1");
		assertTrue(pAddCAtegories.isSearchPageLoaded(),
				"Expected the SiteGen Add questions to the category page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),
				"Questions are not getting added to expected Category");
		assertTrue(pAddCAtegories.addQuestion1ToCategory(SitegenConstants.FORMQUESTION1),
				"Custom Form question1 and answerset1 did not updated successfully.");
		pAddCAtegories.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET1);
		pAddCAtegories.saveCategoryQuestions();

		CustomFormAddCategoriesPage pCustomFormAddCategories2 = pAddCAtegories.clickCustomFormAddCategoriesPage();
		AddQuestionsToCategoryPage pAddCAtegories2 = pCustomFormAddCategories2
				.addCategoriesDetails(SitegenConstants.FORMCATEGORY2);
		System.out.print(SitegenConstants.FORMCATEGORY);

		log("step 9B: Add Question2 to category 2");
		assertTrue(pAddCAtegories2.isSearchPageLoaded(),
				"Expected the SiteGen Add question to the category page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2),
				"Questions are not getting added to expected Category");
		assertTrue(pAddCAtegories2.addQuestion1ToCategory(SitegenConstants.FORMQUESTION2),
				"Custom Form question2 and answerset2 did not updated successfully.");
		pAddCAtegories2.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET2);
		pAddCAtegories2.saveCategoryQuestions();

		CustomFormAddCategoriesPage pCustomFormAddCategories3 = pAddCAtegories2.clickCustomFormAddCategoriesPage();
		AddQuestionsToCategoryPage pAddCAtegories3 = pCustomFormAddCategories3
				.addCategoriesDetails(SitegenConstants.FORMCATEGORY3);

		log("step 9C: Add Question3 to category 3");
		assertTrue(pAddCAtegories3.isSearchPageLoaded(),
				"Expected the SiteGen Add question to the category page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3),
				"Questions are not getting added to expected Category");
		assertTrue(pAddCAtegories3.addQuestion1ToCategory(SitegenConstants.FORMQUESTION3),
				"Custom Form question3 and answerset3 did not updated successfully.");
		pAddCAtegories3.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET3);

		log("step 9D: Save added questions to category");
		assertTrue(pAddCAtegories.isSearchPageLoaded(),
				"Expected the SiteGen Add question to the category page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3),
				"Questions are not getting added to expected Category");
		pAddCAtegories.saveCategoryQuestions();

		CustomFormLayoutPage pAddQuestionsToCategory = pAddCAtegories.clickCustomFormLayoutPage();

		log("step 10: Set Custom Form Layout");
		assertTrue(pAddQuestionsToCategory.isSearchPageLoaded(),
				"Expected the SiteGen form Layout page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),
				"Form Layout is not set for Expected Category");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2),
				"Form Layout is not set for Expected Category");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3),
				"Form Layout is not set for Expected Category");
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE, SitegenConstants.FORMCATEGORY);
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE2, SitegenConstants.FORMCATEGORY2);
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE3, SitegenConstants.FORMCATEGORY3);

		CustomFormPreviewPage pCustomFormPreview = pAddQuestionsToCategory.saveFormLayout();

		pCustomFormPreview.waitForPublishLink();

		log("step 11: Custom Form Preview Page to click on publish");
		assertTrue(pCustomFormPreview.isSearchPageLoaded(),
				"Expected the SiteGen create custom form page preview with publish link to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2),
				"Form Layout is not set for Expected Category");
		assertTrue(verifyTextPresent(driver, customFormTitle), "Vewing custom form is not expected custom form");

		assertEquals(verifyTextPresent(driver, "First Name"), true,
				"Demographic information is not present in form preview");
		pCustomFormPreview.clickOnPage(2);
		assertEquals(verifyTextPresent(driver, "Vital"), true, "Vital information is not present in form preview");
		pCustomFormPreview.clickOnPage(3);
		assertEquals(verifyTextPresent(driver, "Insurance Type"), true,
				"Insurance Type is not present in form preview");
		ManageYourFormsPage pManageForm = pCustomFormPreview.clickOnPublishLink();

		pCustomFormPreview.waitForUnpublishLink();

		log("step 12: Manage your forms -Check custom Form published successfully");
		assertEquals(pManageForm.checkForPublishedPage(customFormTitle), true,
				"Custom Form did not published successfully and not present in published forms table");

		driver.switchTo().window(winHandleSiteGen);

		// Instancing CreatePatientTest
		CheckOldCustomFormTest checkOldCustomFormTest = new CheckOldCustomFormTest();

		// Setting data provider
		Portal portal = new Portal();
		TestcasesData portalTestcasesData = new TestcasesData(portal);

		// Executing Test
		checkOldCustomFormTest.setUrl(pSiteGenPracticeHomePage.getPatientPortalUrl());
		String winHandlePatientPortal = driver.getWindowHandle();
		HealthFormPage page = checkOldCustomFormTest.checkOldCustomForm(driver, portalTestcasesData, customFormTitle);

		driver.switchTo().window(winHandleCustomBuilder);

		log("step 13a: Delete 2 pages");
		pManageForm.clickOnPublishedFormPreviewLink(customFormTitle);
		pAddCAtegories.clickCustomFormLayoutPage();
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE0, SitegenConstants.FORMCATEGORY);
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE0, SitegenConstants.FORMCATEGORY3);
		pAddQuestionsToCategory.categorySequence();

		pAddQuestionsToCategory.saveFormLayout();
		driver.switchTo().window(winHandlePatientPortal);

		checkOldCustomFormTest.checkDeletedPages(driver, page, customFormTitle);

		driver.switchTo().window(winHandleCustomBuilder);
		pCustomFormPreview.clickOnUnPublishLink();

		log("step 14: Manage your forms -Check unpublished Form Preview");
		pManageForm.clickOnUnpublishedFormPreviewLink(customFormTitle);

		log("step 15: Manage your forms -Click on publish link");
		pCustomFormPreview.clickOnPublishLink();

		log("step 16: Manage your forms -Check custom Form published was able to unpublish successfully");
		pManageForm.unPublishThepublishedForm(customFormTitle);

		log("step 17: Manage your forms -Check custom Form unpublished was able to delete successfully");
		pManageForm.deleteUnpublishedForm(customFormTitle);
	}

	@Test(enabled = true, groups = { "PatientForms" })
	public void testFormPatientDashboard() throws Exception {
		String comment = "Written by formPatientDashboardTest";

		logTestEnvironmentInfo("testPatientDashboard");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		String url = portalData.getFormsAltUrl();
		log("Patient Portal URL: " + url);

		log("step 1: Click on Sign Up Fill details in Create Account Page");
		CreatePatientTest createPatient = new CreatePatientTest();
		createPatient.setUrl(url);
		MyPatientPage pMyPatientPage = createPatient.createPatient(driver, portalData);

		log("step 2: Click on forms and open the form");
		HealthFormPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		formsPage.openDiscreteForm(SitegenConstants.PDF_CCD_FORM);

		log("Step 3: Fill out the form");
		fillOutputForm(comment);

		log("Step 4: Log out");
		driver.switchTo().defaultContent();
		pMyPatientPage.clickLogout(driver);

		log("Step 5: Log in to Practice Portal");
		PracticeHomePage pHomePage = loginToPracticePortal();

		log("Step 6: Search for previously created patient");
		PatientSearchPage pSearchPage = pHomePage.clickPatientSearchLink();
		pSearchPage.searchForPatientInPatientSearch(createPatient.getFirstName(), createPatient.getLastName());

		log("Step 7: Get into patient dashboard");
		PatientDashboardPage pDashboardPage = pSearchPage.clickOnPatient(createPatient.getFirstName(),
				createPatient.getLastName());

		log("Step 8: Verify if there's submitted form on patient dashboard");
		assertTrue(pDashboardPage.verifySubmittedForm(SitegenConstants.PDF_CCD_FORM),
				"Submitted form was not found on Patient Dashboard");
	}

	/**
	 * @UserStory: FORMS-346 Logins into sitegen. Creates a new custom form.
	 *             Adds and removes FUPs. Saves form. Reopens the form and
	 *             checks that it contains correct items.
	 */
	@Test(enabled = true, groups = { "PatientForms" })
	public void testSitegenFUPInteraction() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		logTestEnvironmentInfo("testCreateFormWithFUPs");
		Sitegen sitegen = new Sitegen();
		SitegenTestData SGData = new SitegenTestData(sitegen);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps().logInUserToSG(driver,
				SGData.getFormUser(), SGData.getFormPassword());

		log("step 1: Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 2: Unpublish and delete all forms and create a new one");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		pManageDiscreteForms.createANewCustomForm();

		log("step 3: Open created custom form");
		CustomFormPage customFormPage = pManageDiscreteForms.clickOnLastCreatedForm();
		customFormPage.clickOnSection(1);
		CustomFormPageSection section1 = customFormPage.getFirstSection();

		log("step 4: Create question and add 3 answers");
		section1.addQuestionItem(SitegenConstants.QUESTION_TYPE4, "base question", false, false);
		section1.addAnswers(1, Arrays.asList("answer1", "answer2", "answer3"));

		log("step 5: Add 2 FUPs to 1st answer of 1st question");
		section1.addQuestionFUP(1, 1, SitegenConstants.QUESTION_TYPE1, "followUpText", false, false);
		jse.executeScript("scroll(0, -250);");
		Thread.sleep(500);
		section1.addQuestionFUP(1, 1, SitegenConstants.QUESTION_TYPE3, "sndFollowUp", false, false);
		section1.addAnswersFUP(1, 2, Arrays.asList("sub-answer1", "sub-answer2"));

		log("step 6: Add FUP to 2nd answer of 1st question");
		section1.addQuestionFUP(1, 2, SitegenConstants.QUESTION_TYPE3, "thirdFollowUp", false, false);
		section1.addAnswersFUP(1, 3, Arrays.asList("sub-answer1", "sub-answer2", "sub-answer3"));

		log("step 7: Add FUP to 3rd answer of 1st question and delete it immediately");
		section1.addHeadingFUP(1, 3, "deleted heading");
		section1.removeFUP(1, 4);

		log("step 8: save and reopen form");
		customFormPage.saveForm();
		jse.executeScript("scroll(0, -250);");
		customFormPage.leaveFormPage();
		customFormPage = pManageDiscreteForms.clickOnLastCreatedForm();
		customFormPage.clickOnSection(1);
		section1 = customFormPage.getFirstSection();

		log("step 9: test if form contains correct count of FUPs");
		assertEquals(section1.getCountOfFUPsOfAnswer(1, 1), 2);
		assertEquals(section1.getCountOfFUPsOfAnswer(1, 3), 0);

		log("step 10: test FUPs minimization");
		assertFalse(section1.areFUPsMinimized(1, 1));
		section1.toogleFUPs(1, 1);
		assertTrue(section1.areFUPsMinimized(1, 1));
		jse.executeScript("scroll(0, 250);");
		section1.toogleFUPs(1, 1);
		assertFalse(section1.areFUPsMinimized(1, 1));

		log("step 11: test consistency of FUPs");
		assertEquals("sndFollowUp", section1.getTitleOfFUPQuestion(1, 2));
		List<String> expectedAnswers = new ArrayList<String>();
		expectedAnswers.add("sub-answer1");
		expectedAnswers.add("sub-answer2");
		assertTrue(section1.getAnswersOfFUPQuestion(1, 2).containsAll(expectedAnswers));
	}
}
