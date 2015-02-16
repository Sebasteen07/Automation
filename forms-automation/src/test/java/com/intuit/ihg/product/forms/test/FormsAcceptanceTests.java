package com.intuit.ihg.product.forms.test;

import com.intuit.ihg.product.object.maps.practice.page.customform.SearchPartiallyFilledPage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.healthform.HealthFormPage;
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
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.DiscreteFormsPage;
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

public class FormsAcceptanceTests extends BaseTestNGWebDriver {

	private void logTestEnvironmentInfo(String testName) {
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
		FormBasicInfoPage basicInfoPage = welcomePage.clickSaveAndContinueButton(FormBasicInfoPage.class);		
		FormCurrentSymptomsPage currentSymptomsPage = basicInfoPage.clickSaveAndContinueButton(FormCurrentSymptomsPage.class);
		currentSymptomsPage.setBasicSymptoms();
		currentSymptomsPage.enterComment(diacriticString);
		CurrentSymptomsSupplementalPage symptomsSupplemental = currentSymptomsPage.clickSaveAndContinueButton(CurrentSymptomsSupplementalPage.class);
		symptomsSupplemental.fillLogicalAnswersForPdfTest();
		FormMedicationsPage medicationsPage = symptomsSupplemental.clickSaveAndContinueButton(FormMedicationsPage.class);
		medicationsPage.setNoMedications();
		IllnessesSupplementalPage illnessesPage = medicationsPage.clickSaveAndContinueButton(IllnessesSupplementalPage.class);
		illnessesPage.fillOut();
		FormFamilyHistoryPage familyPage = illnessesPage.clickSaveAndContinueButton(FormFamilyHistoryPage.class); 
		familyPage.setNoFamilyHistory();
		FormSocialHistoryPage socialHistoryPage = familyPage.clickSaveAndContinueButton(FormSocialHistoryPage.class);
		socialHistoryPage.fillOutDefaultExerciseLength();
		socialHistoryPage.clickSaveAndContinueButton();
		socialHistoryPage.submitForm();
	}

    /**
     * Logs in to Patient Portal with default test patient and opens up a form selected by the identifier
     * @param formIdentifier
     * @return
     * @throws Exception
     */
    private MyPatientPage openFormOnPatientPortal(String formIdentifier) throws Exception {
        log("Get Portal Data from Excel ##########");
        Portal portal = new Portal();
        TestcasesData portalTestcasesData = new TestcasesData(portal);
        log("Patient Portal URL: " + portalTestcasesData.getFormsAltUrl());

        log("Log in to Patient Portal");
        PortalLoginPage loginpage = new PortalLoginPage(driver, portalTestcasesData.getFormsAltUrl());
        MyPatientPage pMyPatientPage = loginpage.login(portalTestcasesData.getUsername(), portalTestcasesData.getPassword());

        log("Go to forms page and open the \"" + formIdentifier + "\" form");
        HealthFormPage formPage = pMyPatientPage.clickFillOutFormsLink();
        formPage.openDiscreteForm(formIdentifier);
        return pMyPatientPage;
    }

    private SearchPatientFormsPage getPracticePortalSearchFormsPage() throws Exception {
        Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

        PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
        PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getFormUser(),
                practiceTestData.getFormPassword());

        log("Click CustomFormTab");
        SearchPatientFormsPage pSearchPatientFormsPage = practiceHome.clickCustomFormTab();
        verifyTrue(pSearchPatientFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + " failed to load.");
        return pSearchPatientFormsPage;
    }

    /**
     * Verifies that record of completed or partially completed form is from the current day and that
     * the pdf is downloadable
     */
    private void verifyFormsDateAndPDF(ViewPatientFormPage pViewPatientFormPage)
            throws Exception {
        String currentDate = IHGUtil.getFormattedCurrentDate("yyyy-MM-dd");

        log("Verify date and download code");
        // take the year, month and day (yyyy-MM-dd - 10 chars) of form submission
        String submittedDate = pViewPatientFormPage.getLastUpdatedDateFormatted();
        assertEquals(submittedDate, currentDate, "Form submitted today not found");

        log("Download URL: " + pViewPatientFormPage.getDownloadURL());
        URLStatusChecker status = new URLStatusChecker(driver);
        assertEquals(status.getDownloadStatusCode(pViewPatientFormPage.getDownloadURL(), RequestMethod.GET), 200);
    }

    @Test(groups = {"smokeTest"})
    public void formsConfigSmokeTest() throws Exception {
        SitegenTestData testData = new SitegenTestData(new Sitegen());
        SiteGenSteps sgSteps = new SiteGenSteps();

        logTestEnvironmentInfo("formsConfigSmokeTest");
        DiscreteFormsPage formsPage = sgSteps
                .logInUserToSG(driver, testData.getFormUser(), testData.getFormPassword())
                .clickLnkDiscreteForms();
        assertTrue(formsPage.isPageLoaded());
    }

    @Test(enabled = true, groups = {"PatientForms"})
	public void testQuotationMarksInForm() throws Exception {
		logTestEnvironmentInfo("testQuotationMarksInForm");

        openFormOnPatientPortal("specialChars");

		log("Step 5: Fill the form out with values containing quotes");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		SpecialCharFormFirstPage customPage1 = welcomePage.initializeFormToFirstPage(SpecialCharFormFirstPage.class);
		customPage1.selectQuotatedAnswers();
		SpecialCharFormSecondPage customPage2 = customPage1.clickSaveAndContinueButton(SpecialCharFormSecondPage.class);
		customPage2.selectAnswerQuoteMark();
		customPage2.signConsent();
		customPage2.clickSaveAndContinueButton();
		customPage2.submitForm();
	}
    
    /**
     * Tect Case in TestLink: MF-1265
     * @author phajek
     * @Date: 13/02/2015
	 * StepsToReproduce:
     *      Log in to SG as SU
     *      Go to Forms Config
     *      Unpublish all forms
     *      Delete all forms
     *      Search and add a new Calculated form
     *      Test if it is displayed in Calculated Forms directory
     *      Delete the Form
     *      Test if it is displayed in Calculated Forms directory
     * === Prerequisite for the test case to run=========
	 * Practice configured
	 * Practices configured on: DEV3, DEMO, PROD
	 * ============================================================
	 * @throws Exception
	 */
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, groups = {"PatientForms"})
	public void testCalculatedFormAddRemove() throws Exception {

		logTestEnvironmentInfo("testDiscreteFormDeleteCreatePublish");
        Sitegen sitegen = new Sitegen();
        SitegenTestData testcasesData = new SitegenTestData(sitegen);
        SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps()
                .logInUserToSG(driver, testcasesData.getadminUser(), testcasesData.getadminPassword(), testcasesData.getAutomationPracticeName());
		String parentHandle = driver.getWindowHandle(); // Get the current window handle before opening new window

		log("step 1: Click on Patient Forms");
		DiscreteFormsPage pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 2: Unpublish and delete all forms and add calculated form");
        driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		assertTrue(
				pManageDiscreteForms.addCalculatedForm(SitegenConstants.CALCULATEDFORMNAME));
		

		log("step 3: Check if the added form is no longer in the Calculated Form Directory ");
		assertFalse(
				pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATEDFORMNAME));
		
		log("step 4: Delete all Forms and check if the Calculated Form is back in Directory");
		pManageDiscreteForms.initializePracticeForNewForm();
		assertTrue(
				pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATEDFORMNAME));

		log("step 5: Close the window and logout from SiteGenerator");
		// Switching back to original window using previously saved handle descriptor
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();

		
	}

	/**
	 * @Author: Adam Warzel
	 * @Date: April-01-2014
	 * @UserStory: US7083
	 * 
	 * Tests if filling out a form generates a PDF, if link for downloading
	 * the PDF appears in Patient Portal and if the link is working and also
	 * whether corresponding CCD was generated.
	 * 
	 * Creates new patient
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
		TestcasesData portalTestcasesData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalTestcasesData.getFormsAltUrl());
	
		log("step 1: Click on Sign Up Fill detials in Create Account Page");
		String email = PortalUtil.createRandomEmailAddress(portalTestcasesData.getEmail());
		log("email:-" + email);
		CreatePatientTest createPatient = new CreatePatientTest();
		createPatient.setUrl(portalTestcasesData.getFormsAltUrl());
		MyPatientPage pMyPatientPage = createPatient.createPatient(driver, portalTestcasesData);
		
		log("step 2: Click on forms and open the form");
		HealthFormPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		formsPage.openDiscreteForm("pdfForm");
		
		log("Step 3: Fill out the form");
		fillOutputForm(diacriticString);
		
		log("Step 4: Test if PDF is downloadable");
		PortalUtil.setPortalFrame(driver);
		URLStatusChecker status = new URLStatusChecker(driver);
		assertTrue(formsPage.isPDFLinkPresent(), "PDF link not found, PDF not generated");
		assertEquals(status.getDownloadStatusCode(formsPage.getPDFDownloadLink(), RequestMethod.GET), 200);
		
		log("Step 5: Test if CCD is produced");
		log("Calling rest");
		xml = CCDTest.getFormCCD(timestamp, portalTestcasesData.getRestUrl());
		assertTrue(xml.contains(easyBruisingString), "Symptom not found in the CCD, printing the CCD:\n" + xml);
	}

	@Test(enabled = true, groups = {"PatientForms"})
	public void testFormPracticePortal() throws Exception {
		String discreteFormName = "Form for Practice view test";

		logTestEnvironmentInfo("testFormPracticePortal");

        log("Step 1: Open the form");
        MyPatientPage pMyPatientPage = openFormOnPatientPortal("practiceForm");

		log("Step 2: Fill out the form");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		FormBasicInfoPage demographPage = welcomePage.initializeFormToFirstPage(FormBasicInfoPage.class);
		FormMedicationsPage medsPage = demographPage.clickSaveAndContinueButton(FormMedicationsPage.class);
		medsPage.setNoMedications();
		FormIllnessConditionsPage illsPage = medsPage.clickSaveAndContinueButton(FormIllnessConditionsPage.class);
		illsPage.checkMononucleosis();
		illsPage.clickSaveAndContinueButton(null);
		illsPage.submitForm();

		log("Step 3: Logout of patient portal");
		pMyPatientPage.logout(driver);

        SearchPatientFormsPage pSearchPatientFormsPage = getPracticePortalSearchFormsPage();

		log("step 6: Search for PatientForms With Status Open");
		SearchPatientFormsResultPage pSearchPatientFormsResultPage =
                pSearchPatientFormsPage.SearchDiscreteFormsWithOpenStatus(discreteFormName);

		log("step 7: View the Result");
		ViewPatientFormPage pViewPatientFormPage = pSearchPatientFormsResultPage.clickViewLink();
        verifyFormsDateAndPDF(pViewPatientFormPage);
	}

    @Test(enabled = true, groups = {"PatientForms"})
    public void testPartiallyCompletedForm() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        logTestEnvironmentInfo("testFormPracticePortal");

        log("Step 1: Open the form");
        MyPatientPage myPatientPage = openFormOnPatientPortal("practiceForm");

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
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, groups = {"PatientForms"})
	public void testDiscreteFormDeleteCreatePublish() throws Exception {
        String newFormName = SitegenConstants.DISCRETEFORMNAME + IHGUtil.createRandomNumericString().substring(0, 4);

		logTestEnvironmentInfo("testDiscreteFormDeleteCreatePublish");
        Sitegen sitegen = new Sitegen();
        SitegenTestData testcasesData = new SitegenTestData(sitegen);
        SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps()
                .logInUserToSG(driver, testcasesData.getFormUser(), testcasesData.getFormPassword());
		String parentHandle = driver.getWindowHandle(); // Get the current window handle before opening new window

		log("step 1: Click on Patient Forms");
		DiscreteFormsPage pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 2: Unpublish and delete all forms and create a new one");
        driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		pManageDiscreteForms.createNewForm();

		log("step 3: Initialize the new form");
		pManageDiscreteForms.prepareFormForTest(newFormName);

		log("step 4: Publish the saved Discrete Form");
		pManageDiscreteForms.publishTheSavedForm(newFormName);

		log("step 5: Close the window and logout from SiteGenerator");
		// Switching back to original window using previously saved handle descriptor
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();

		log("step 6: Go to Patient Portal using the original window");
		Portal portal = new Portal();
		TestcasesData portalTestcasesData = new TestcasesData(portal);
		log("URL: " + portalTestcasesData.getFormsUrl());

		log("step 7: Log in to Patient Portal");
		PortalLoginPage loginpage = new PortalLoginPage(driver, portalTestcasesData.getFormsUrl());
		MyPatientPage pMyPatientPage = loginpage.login(portalTestcasesData.getUsername(),
                portalTestcasesData.getPassword());

		log("step 8: Click On Start Registration Button and verify welcome page of the previously created form");
		FormWelcomePage pFormWelcomePage = pMyPatientPage.clickStartRegistrationButton(driver);
		assertTrue( pFormWelcomePage.welcomeMessageContent( pManageDiscreteForms.getWelcomeMessage() ));
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
	public void testCustomFormPublished() throws Exception {

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
			HealthFormPage page = checkOldCustomFormTest.checkOldCustomForm(driver, portalTestcasesData, customFormTitle);


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
