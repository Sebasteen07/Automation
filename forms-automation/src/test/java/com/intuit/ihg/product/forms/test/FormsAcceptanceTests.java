package com.intuit.ihg.product.forms.test;

import com.intuit.ihg.product.object.maps.practice.page.customform.SearchPartiallyFilledPage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.healthform.HealthFormsPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.*;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages.*;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.supplemental_pages.*;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.custom_pages.*;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.customform.SearchPatientFormsPage;
import com.intuit.ihg.product.object.maps.practice.page.customform.SearchPatientFormsResultPage;
import com.intuit.ihg.product.object.maps.practice.page.customform.ViewPatientFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.AddQuestionsToCategoryPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomForms;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormAddCategoriesPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormLayoutPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormPreviewPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.ManageYourFormsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.portal.tests.CheckOldCustomFormTest;
import com.intuit.ihg.product.portal.tests.CreatePatientTest;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.ccd.CCDTest;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;

import java.util.concurrent.TimeUnit;

public class FormsAcceptanceTests extends BaseTestNGWebDriver {

	protected void logTestEnvironmentInfo(String testName) {
		log(testName);
		log("Environment on which Testcase is Running: " + IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: " + TestConfig.getBrowserType());
	}

	/**
	 * Fills out Output form for CCD test. Needs the form to be opened and on the first (welcome) page
	 * @param diacriticString - String to fill out in Symptoms comments, used for testing special diacritic
	 */
	private void fillOutputForm(String diacriticString) throws Exception {
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		FormBasicInfoPage basicInfoPage = welcomePage.clickSaveContinue(FormBasicInfoPage.class);

        FormCurrentSymptomsPage currentSymptomsPage =
                basicInfoPage.clickSaveContinue(FormCurrentSymptomsPage.class);
		currentSymptomsPage.setBasicSymptoms();
		currentSymptomsPage.enterComment(diacriticString);
		CurrentSymptomsSupplementalPage symptomsSupplemental =
                currentSymptomsPage.clickSaveContinue(CurrentSymptomsSupplementalPage.class);
		symptomsSupplemental.fillLogicalAnswersForPdfTest();

        FormMedicationsPage medicationsPage =
                symptomsSupplemental.clickSaveContinue(FormMedicationsPage.class);
		medicationsPage.setNoMedications();

        IllnessesSupplementalPage illnessesPage =
                medicationsPage.clickSaveContinue(IllnessesSupplementalPage.class);
		illnessesPage.fillOut();

        FormFamilyHistoryPage familyPage =
                illnessesPage.clickSaveContinue(FormFamilyHistoryPage.class);
		familyPage.setNoFamilyHistory();

        FormSocialHistoryPage socialHistoryPage =
                familyPage.clickSaveContinue(FormSocialHistoryPage.class);
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
     * Logs in to Patient Portal with default test patient and opens up a form selected by the identifier
     * @param formIdentifier
     * @return
     * @throws Exception
     */
    protected MyPatientPage openFormOnPatientPortal(String formIdentifier) throws Exception {
		log("Step 1: Get Portal Data from Excel ##########");
        Portal portal = new Portal();
        TestcasesData portalData = new TestcasesData(portal);
        log("Patient Portal URL: " + portalData.getFormsAltUrl());

		log("Step 2: Log in to Patient Portal");
        PortalLoginPage loginPage = new PortalLoginPage(driver, portalData.getFormsAltUrl());
        MyPatientPage pMyPatientPage =
                loginPage.login(portalData.getUsername(), portalData.getPassword());

		log("Step 3: Go to the forms page and open the form: \"" + formIdentifier + "\"");
        HealthFormsPage formPage = pMyPatientPage.clickFillOutFormsLink();
        formPage.openDiscreteForm(formIdentifier);
        return pMyPatientPage;
    }

    protected SearchPatientFormsPage getPracticePortalSearchFormsPage() throws Exception {
        Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

        PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
        PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getFormUser(),
                practiceTestData.getFormPassword());

        log("Click CustomFormTab");
        SearchPatientFormsPage searchFormsPage = practiceHome.clickCustomFormTab();

		// check if the page is loaded, sometimes the test ends up on login page at this point
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!searchFormsPage.isPageLoaded() && practiceLogin.isLoginPageLoaded()) {
				searchFormsPage = practiceLogin
						.login(practiceTestData.getFormUser(), practiceTestData.getFormPassword())
						.clickCustomFormTab();
		}
		driver.manage().timeouts().implicitlyWait(SitegenConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);

				verifyTrue(searchFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + " failed to load.");
        return searchFormsPage;
    }

    /**
     * Verifies that record of completed or partially completed form is from the current day and that
     * the pdf is downloadable
     */
    private void verifyFormsDateAndPDF(ViewPatientFormPage viewFormPage)
            throws Exception {
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

	protected void verifyFormsDatePatientPortal(HealthFormsPage formsPage, String formName)
			throws Exception {
		PortalUtil.setPortalFrame(driver);
		String submittedDate = formsPage.getSubmittedDate(formName);
		String currentDate = IHGUtil.getFormattedCurrentDate(submittedDate);
		assertEquals(submittedDate, currentDate, "Form submitted today not found");
	}

	protected void checkPDF(HealthFormsPage formsPage, String formName) throws Exception {
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

    @Test(groups = {"smokeTest"})
    public void formsConfigSmokeTest() throws Exception {
        SitegenTestData testData = new SitegenTestData(new Sitegen());
        SiteGenSteps sgSteps = new SiteGenSteps();

        logTestEnvironmentInfo("formsConfigSmokeTest");
        DiscreteFormsList formsPage = sgSteps
                .logInUserToSG(driver, testData.getFormUser(), testData.getFormPassword())
                .clickLnkDiscreteForms();
        assertTrue(formsPage.isPageLoaded());
    }

    @Test(enabled = true, groups = {"PatientForms"})
	public void testQuotationMarksInForm() throws Exception {
		logTestEnvironmentInfo("testQuotationMarksInForm");

		openFormOnPatientPortal(SitegenConstants.SPECIAL_CHARS_FORM);

		log("Step 4: Fill the form out with values containing quotes");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		SpecialCharFormFirstPage customPage1 =
                welcomePage.initializeFormToFirstPage(SpecialCharFormFirstPage.class);
		customPage1.selectQuotatedAnswers();

        SpecialCharFormSecondPage customPage2 =
                customPage1.clickSaveContinue(SpecialCharFormSecondPage.class);
		customPage2.selectAnswerQuoteMark();
		customPage2.signConsent();
		customPage2.clickSaveContinue();
		customPage2.submitForm();
	}

	@Test(enabled = true)
	public void testFormValidation() throws Exception {
		logTestEnvironmentInfo("TestFormValidation");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalData.getFormsAltUrl());

		log("step 1: Click on Sign Up Fill details in Create Account Page");
		MyPatientPage pMyPatientPage = createPatient(portalData);

		log("step 2: Click on forms and open the form");
		HealthFormsPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		FormWelcomePage welcomePage = formsPage.openDiscreteForm("Validation test form");
		welcomePage.clickSaveContinue(FormWelcomePage.class);

		log("step 2: Test validation for various pages");
		PortalFormPage formPages[] = {
				new FormBasicInfoPage(driver), new FormInsurancePage(driver), new FormCurrentSymptomsPage(driver),
				new FormAllergiesPage(driver)
		};

		for (int i = 0; i < formPages.length; i++) {
			formPages[i] = PageFactory.initElements(driver, formPages[i].getClass());
			formPages[i].testValidation();
			formPages[i].clickSaveContinue();
		}

		formPages[formPages.length - 1].submitForm();
	}

	/**
	 * @Author: Petr Hajek
	 * @Date: April-28-2015
	 */
	@Test(enabled = true)
	public void testCustomFormValidation() throws Exception {
		logTestEnvironmentInfo("TestCustomFormValidation");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalData.getFormsAltUrl());

		log("step 1: Click on Sign Up Fill details in Create Account Page");
		MyPatientPage pMyPatientPage = createPatient(portalData);

		log("step 2: Click on forms and open the form");
		HealthFormsPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		ValidationFormPage firstPage = formsPage.openDiscreteForm("Validation test Custom form")
				.clickSaveContinue(ValidationFormPage.class);

		log("Step 3: Validate");
		firstPage.testValidation();
		PortalFormPage lastPage = firstPage.clickSaveContinue(PortalFormPage.class);
		lastPage.submitForm();
	}

    /**
	 * @Author: Adam Warzel
	 * @Date: April-01-2014
	 * @UserStory: US7083
	 *             Creates a new user.
	 *             Tests if filling out a form generates a PDF, if link for downloading
	 *             the PDF appears in Patient Portal and if the link is working and also
	 *             whether corresponding CCD was generated.
	 *             Then it checks if the submitted date is accurate and if patient's DOB has not been changed
	 */
	@Test(enabled = true, groups = {"PatientForms"})
	public void testFormPdfCcd() throws Exception {
		long timestamp = System.currentTimeMillis() / 1000L;
		String xml;
		// easy bruising is mapped to following term in Forms Configurator in SiteGen
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
		HealthFormsPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		formsPage.openDiscreteForm(SitegenConstants.PDF_CCD_FORM);

		log("Step 3: Fill out the form");
		fillOutputForm(diacriticString);

		log("Step 4: Test if PDF is downloadable");
		checkPDF(formsPage, SitegenConstants.PDF_CCD_FORM);

		log("Step 5: Test if CCD is produced");
		log("Calling rest");
		xml = CCDTest.getFormCCD(timestamp, portalData.getRestUrl());
		assertTrue(xml.contains(easyBruisingString),
                "Symptom not found in the CCD, printing the CCD:\n" + xml);

		log("Step 6: Test if the submission date is correct");
		verifyFormsDatePatientPortal(formsPage, SitegenConstants.PDF_CCD_FORM);

		log("Step 7: Log out and in again");
		driver.switchTo().defaultContent();
		PortalLoginPage loginpage = pMyPatientPage.clickLogout(driver);
		loginpage.navigateTo(driver, url);
		pMyPatientPage = loginpage.login(createPatient.getEmail(), createPatient.getPassword());

		log("Step 8: Test if the DOB has not been changed");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();
		String accountDOB = IHGUtil.convertDate(pMyAccountPage.getDOB(), "MM/dd/yyyy",
				"MMMMM/dd/yyyy");
		assertEquals(portalData.getDOB(), accountDOB, "Date of birth is not accurate!");

	}

	@Test(enabled = true, groups = {"PatientForms"})
	public void testFormPracticePortal() throws Exception {

		logTestEnvironmentInfo("testFormPracticePortal");

        log("Step 1: Open the form");
		MyPatientPage pMyPatientPage = openFormOnPatientPortal(SitegenConstants.PRACTICE_FORM);

		log("Step 2: Fill out the form");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);

        FormBasicInfoPage demographPage =
                welcomePage.initializeFormToFirstPage(FormBasicInfoPage.class);

        FormMedicationsPage medsPage =
                demographPage.clickSaveContinue(FormMedicationsPage.class);
		medsPage.setNoMedications();

        FormIllnessConditionsPage illsPage =
                medsPage.clickSaveContinue(FormIllnessConditionsPage.class);
		illsPage.checkMononucleosis();
		illsPage.clickSaveContinue(null);
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

    @Test(enabled = true, groups = {"PatientForms"})
    public void testPartiallyCompletedForm() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        logTestEnvironmentInfo("testPartiallyCompletedForm");

        log("Step 1: Open the form");
		MyPatientPage myPatientPage = openFormOnPatientPortal(SitegenConstants.PRACTICE_FORM);

        log("Step 2: Fill out the form");
        FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
        welcomePage.initializeFormToFirstPage(FormBasicInfoPage.class);
        welcomePage.clickSaveAndFinishAnotherTime();
        driver.switchTo().defaultContent();
        wait.until( ExpectedConditions.elementToBeClickable(myPatientPage.getLogoutLink()) );

        log("Step 3: Go to Practice Portal forms tab");
        SearchPatientFormsPage pSearchPatientFormsPage = getPracticePortalSearchFormsPage();
        SearchPartiallyFilledPage searchPage = pSearchPatientFormsPage.getPartiallyFilledSearch();
        searchPage.clickSearch();
        ViewPatientFormPage resultPage = searchPage.selectPatientsFirstForm();
        verifyFormsDateAndPDF(resultPage);
    }

    /**
     * User Story ID in Rally: US544 - TA30648
	 * StepsToReproduce:
     *      Log in to SG
     *      Go to Forms Config
     *      Unpublish all forms
     *      Delete all forms
     *      Create a new form and configure it
     *      Create a custom section and test saving it without name and questions
     *      Save the form
     *      Publish it
     *      Test viewing the form on Patient Portal
	 * === Prerequisite for the test case to run=========
	 * Practice configured
	 * Practices configured on: DEV3, DEMO, PROD
	 * ============================================================
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"PatientForms"})
	public void testDiscreteFormDeleteCreatePublish() throws Exception {

		String welcomeMessage = createFormSG();
		log("step 6: Go to Patient Portal using the original window");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("URL: " + portalData.getFormsUrl());

		log("step 7: Log in to Patient Portal");
		PortalLoginPage loginPage = new PortalLoginPage(driver, portalData.getFormsUrl());
		MyPatientPage pMyPatientPage = loginPage.login(portalData.getUsername(),
				portalData.getPassword());

		log("step 8: Click On Start Registration Button and verify welcome page of the previously created form");
		FormWelcomePage pFormWelcomePage = pMyPatientPage.clickStartRegistrationButton(driver);
		assertEquals(pFormWelcomePage.getMessageText(), welcomeMessage);
	}

	protected String createFormSG() throws Exception {
        String newFormName = SitegenConstants.DISCRETEFORMNAME + IHGUtil.createRandomNumericString().substring(0, 4);

		logTestEnvironmentInfo("testDiscreteFormDeleteCreatePublish");
        Sitegen sitegen = new Sitegen();
        SitegenTestData SGData = new SitegenTestData(sitegen);
        SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps()
                .logInUserToSG(driver, SGData.getFormUser(), SGData.getFormPassword());
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
		// Switching back to original window using previously saved handle descriptor
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();

		return pManageDiscreteForms.getWelcomeMessage();

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
	@Test(enabled = true, groups = {"CustomForms"}, retryAnalyzer = RetryAnalyzer.class)
	public void testOldCustomForms() throws Exception {

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
		HealthFormsPage pHealthForm = pMyPatientPage.clickFillOutFormsLink();

		log("step 3: Fill CustomForm");
		pHealthForm.fillInsuranceHealthForm();

		assertFalse(driver.getPageSource().contains("Female question"));

		pHealthForm.submitInsuranceHealthForm();

		verifyEquals(pHealthForm.InsuranceHelthform.getText(), "Thank you for completing our Insurance Health Form ( Testing).");
		// assertTrue(verifyTextPresent(driver,"Thank you for completing our Insurance Health Form ( Testing)."));

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
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 8: On Practice Portal Home page Click CustomFormTab");
		SearchPatientFormsPage pSearchPatientFormsPage = practiceHome.clickCustomFormTab();
		assertTrue(pSearchPatientFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + " failed to load.");

		log("step 9: Search for PatientForms With Status Open");
		SearchPatientFormsResultPage pSearchPatientFormsResultPage = pSearchPatientFormsPage.SearchPatientFormsWithOpenStatus(
				patientData.getFirstName(), patientData.getLastName(), patientData.getDob_Month(), patientData.getDob_Day(),
				patientData.getDob_Year());

		log("step 10: View the Result");
		pSearchPatientFormsResultPage.clickViewLink();

		log("step 11: Verify the Result");
		String actualPatientName = pHealthForm.Patientname.getText().trim();

		log("Displayed patient name is :"+actualPatientName);
		verifyEquals(pHealthForm.Patientname.getText().trim().contains("Patient Name : Ihgqa"), true);
		/*
		 * assertTrue(verifyTextPresent(driver,
		 * "Patient Name : ihgqa  automation "));
		 * assertTrue(verifyTextPresent(driver, "Patient DOB : 01/11/1987"));
		 */
		// assertTrue(verifyTextPresent(driver,
		// "Patient SSN : 987-65-4322"));
	}

	/**
	 * @Author:-Shanthala  : Modified :bbinisha : Modified-Modified: Prokop Rehacek
	 * @Date:- 07-03-2013
	 * @User Story ID in Rally :  US6152 and US6151 and US7626
	 * @StepsToReproduce:
	 *Go to siteGen
	 *Enter the credentials
	 *Search for the practice
	 *Click on Custom Form
	 *Click on Create Custom Form
	 *Publish Custom Form and check for preview
	 *Unpublish Custom Form, check for Preview and delete unpublished custom form
	 *
	 *=== Prerequisite for the test case to run=========
	 * Nurse Named :-
	 *
	 *====Valid Custom Form details required. Test data would be updated after getting proper test data
	 * =============================================================
	 * @AreaImpacted :-
	 * Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"CustomForms"})
	public void testOldCustomFormPublished() throws Exception {

		logTestEnvironmentInfo("testCustomFormPublished");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		SiteGenSteps.logSGLoginInfo(testcasesData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage=loginpage.login(testcasesData.getAutomationUser(), testcasesData.getAutomationUserPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage  to be loaded, but it was not.");

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 4: Click on Custom Forms");
		String winHandleSiteGen = driver.getWindowHandle();
		CreateCustomForms pManageCustomForms=pSiteGenPracticeHomePage.clickCustomForms();
		String winHandleCustomBuilder = driver.getWindowHandle();

		log("step 5:Click on Manage Custom Form and delete custom form 'Automation Custom Form' if present");
		ManageYourFormsPage plinkOnManageCustomForm = pManageCustomForms.clicklnkManageCustomForm();
		verifyEquals(plinkOnManageCustomForm.isSearchPageLoaded(), true, "Expected SiteGen Manage custom form page to be loaded to unpublish the published form, but itwas not.");

		if(plinkOnManageCustomForm.isUnPublished(SitegenConstants.FORMTITLE)) {
			log("There is a form with the name 'Automation Custom Form' and is unpublished");

			verifyTrue(plinkOnManageCustomForm.isSearchPageLoadedForUnpublishedTable(), "Expected the SiteGen Manage custom form page to be loaded to delete unpublishedform , but it was not.");
			plinkOnManageCustomForm.deleteUnpublishedForm(SitegenConstants.FORMTITLE);
			log("Existing custom form named 'Automation Custom Form deleted");

		} else {
			log("step 6: Click on Create Custom Form");
			CreateCustomFormPage plinkOnCustomForm = pManageCustomForms.clicklnkCreateCustomForm();

			String customFormTitle = SitegenConstants.FORMTITLE+IHGUtil.createRandomNumber();

			log("step 7: Enter Custom Form details");
			assertTrue(plinkOnCustomForm.isSearchPageLoaded(), "Expected the SiteGen Create a Custom Form page to be loaded to create a new custom form with details, but it was not.");
			CustomFormAddCategoriesPage pCustomFormAddCategories = plinkOnCustomForm.enterCustomFormDetails(SitegenConstants.FORMTYPE,customFormTitle,SitegenConstants.FORMINSTRUCTIONS,SitegenConstants.FORMMESSAGE);

			log("step 8: Build a Custom Form");
			assertTrue(pCustomFormAddCategories.isSearchPageLoaded(), "Expected the SiteGen Build a Custom Form page to be loaded to add categories into the custom form, but it was not.");
			AddQuestionsToCategoryPage pAddCAtegories = pCustomFormAddCategories.addCategoriesDetails(SitegenConstants.FORMCATEGORY);

			log("step 9A: Add Question1 to category 1");
			verifyTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add questions to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Questions are not getting added to expected Category");
			verifyTrue(pAddCAtegories.addQuestion1ToCategory(SitegenConstants.FORMQUESTION1), "Custom Form question1 and answerset1 did not updated successfully.");
			pAddCAtegories.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET1);
			pAddCAtegories.saveCategoryQuestions();

			CustomFormAddCategoriesPage pCustomFormAddCategories2 = pAddCAtegories.clickCustomFormAddCategoriesPage();
			AddQuestionsToCategoryPage pAddCAtegories2 = pCustomFormAddCategories2.addCategoriesDetails(SitegenConstants.FORMCATEGORY2);
            System.out.print(SitegenConstants.FORMCATEGORY);

			log("step 9B: Add Question2 to category 2");
			verifyTrue(pAddCAtegories2.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2),"Questions are not getting added to expected Category");
			verifyTrue(pAddCAtegories2.addQuestion1ToCategory(SitegenConstants.FORMQUESTION2), "Custom Form question2 and answerset2 did not updated successfully.");
			pAddCAtegories2.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET2);
			pAddCAtegories2.saveCategoryQuestions();

			CustomFormAddCategoriesPage pCustomFormAddCategories3 = pAddCAtegories2.clickCustomFormAddCategoriesPage();
			AddQuestionsToCategoryPage pAddCAtegories3 = pCustomFormAddCategories3.addCategoriesDetails(SitegenConstants.FORMCATEGORY3);

			log("step 9C: Add Question3 to category 3");
			verifyTrue(pAddCAtegories3.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3),"Questions are not getting added to expected Category");
			verifyTrue(pAddCAtegories3.addQuestion1ToCategory(SitegenConstants.FORMQUESTION3), "Custom Form question3 and answerset3 did not updated successfully.");
			pAddCAtegories3.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET3);

			log("step 9D: Save added questions to category");
			verifyTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3),"Questions are not getting added to expected Category");
			pAddCAtegories.saveCategoryQuestions();

			CustomFormLayoutPage pAddQuestionsToCategory = pAddCAtegories.clickCustomFormLayoutPage();

			log("step 10: Set Custom Form Layout");
			verifyTrue(pAddQuestionsToCategory.isSearchPageLoaded(), "Expected the SiteGen form Layout page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Form Layout is not set for Expected Category");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2),"Form Layout is not set for Expected Category");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3),"Form Layout is not set for Expected Category");
			pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE, SitegenConstants.FORMCATEGORY);
			pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE2, SitegenConstants.FORMCATEGORY2);
			pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE3, SitegenConstants.FORMCATEGORY3);


			CustomFormPreviewPage pCustomFormPreview = pAddQuestionsToCategory.saveFormLayout();

			pCustomFormPreview.waitForPublishLink();

			log("step 11: Custom Form Preview Page to click on publish");
			verifyTrue(pCustomFormPreview.isSearchPageLoaded(), "Expected the SiteGen create custom form page preview with publish link to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2),"Form Layout is not set for Expected Category");
			verifyTrue(verifyTextPresent(driver,customFormTitle),"Vewing custom form is not expected custom form");

			//This assert statements can be changed after getting standard valid custom form from Richard/Don B
			//verifyEquals(verifyTextPresent(driver,"Insurance Type"),true,"Insurance Type is not present in form preview");
			verifyEquals(verifyTextPresent(driver,"First Name"),true, "Demographic information is not present in form preview");
			//verifyEquals(verifyTextPresent(driver,"Vital"),true, "Vital information is not present in form preview");
			ManageYourFormsPage pManageForm = pCustomFormPreview.clickOnPublishLink();

			pCustomFormPreview.waitForUnpublishLink();

			log("step 12: Manage your forms -Check custom Form published successfully");
			verifyEquals(pManageForm.checkForPublishedPage(customFormTitle), true, "Custom Form did not published successfully and not present in published forms table");

			driver.switchTo().window(winHandleSiteGen);

			// Instancing CreatePatientTest
			CheckOldCustomFormTest checkOldCustomFormTest =  new CheckOldCustomFormTest();

			// Setting data provider
			Portal portal = new Portal();
			TestcasesData portalTestcasesData = new TestcasesData(portal);

			// Executing Test
			checkOldCustomFormTest.setUrl(pSiteGenPracticeHomePage.getPatientPortalUrl());
			String winHandlePatientPortal = driver.getWindowHandle();
			HealthFormsPage page = checkOldCustomFormTest.checkOldCustomForm(driver, portalTestcasesData, customFormTitle);


			driver.switchTo().window(winHandleCustomBuilder);


			log("step 13: Manage your forms -Check published Form Preview by clicking on Preview link");
			verifyEquals(pManageForm.isSearchPageLoaded(),true, "Expected the SiteGen Manage your Forms -> published form preview page to be loaded, but it was not.");
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
			verifyEquals(pManageForm.isSearchPageLoaded(), true, "Expected the SiteGen Manage your Forms -> published form preview page to be loaded, but it was not.");
			pManageForm.clickOnUnpublishedFormPreviewLink(customFormTitle);

			log("step 15: Manage your forms -Click on publish link");
			verifyEquals(pCustomFormPreview.isSearchPageLoaded(),true, "Expected the SiteGen create custom form page preview with publish link to be loaded, but it was not.");
			pCustomFormPreview.clickOnPublishLink();

			log("step 16: Manage your forms -Check custom Form published was able to unpublish successfully");
			verifyEquals(pManageForm.isSearchPageLoadedForUnpublishedTable(),true, "Expected the SiteGen Manage your Forms -> unpublished form page to be loaded to unpublish the published form, but it was not.");
			pManageForm.unPublishThepublishedForm(customFormTitle);

			log("step 17: Manage your forms -Check custom Form unpublished was able to delete successfully");
			pManageForm.deleteUnpublishedForm(customFormTitle);
		}
	}
}
