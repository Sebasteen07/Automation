package com.intuit.ihg.product.forms.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.google.common.base.Charsets;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.WebDriverFactory;
import com.intuit.ihg.common.utils.ccd.CCDTest;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.BasicInformationAboutYouPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CurrentSymptomsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CustomFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CustomFormPageSection;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.forms.pojo.Question;
import com.medfusion.product.forms.pojo.SelectQuestion;
import com.medfusion.product.forms.pojo.SelectableAnswer;
import com.medfusion.product.forms.pojo.TextQuestion;
import com.medfusion.product.forms.services.QuestionsService;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.NewCustomFormPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.FormWelcomePage;
import com.medfusion.product.object.maps.forms.page.questionnaires.custom_pages.SpecialCharFormFirstPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.custom_pages.SpecialCharFormSecondPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormBasicInfoPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormCurrentSymptomsPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormFamilyHistoryPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormMedicationsPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormPastMedicalHistoryPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormSocialHistoryPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.supplemental_pages.CurrentSymptomsSupplementalPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.supplemental_pages.IllnessesSupplementalPage;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.MyAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPartiallyFilledPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPatientFormsPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPatientFormsResultPage;
import com.medfusion.product.object.maps.practice.page.customform.ViewPatientFormPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.patientportal1.flows.CreatePatientTest;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.TestcasesData;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;
import com.medfusion.product.patientportal2.tests.CommonSteps;
import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.PracticeTestData;

public class FormsAcceptanceTests extends BaseTestNGWebDriver {

	@Test(groups = {"smokeTest"})
	public void formsConfigSmokeTest() throws Exception {
		SitegenTestData testData = new SitegenTestData(new Sitegen());
		SiteGenSteps sgSteps = new SiteGenSteps();

		Utils.logTestEnvironmentInfo("formsConfigSmokeTest");
		DiscreteFormsList formsPage = sgSteps.logInUserToSG(driver, testData.getFormUser(), testData.getFormPassword()).clickLnkDiscreteForms();
		assertTrue(formsPage.isPageLoaded());
	}

	@Test(groups = {"Forms"})
	public void testQuotationMarksInFormPI() throws Exception {
		testQuotationMarksInForm(Utils.loginPIAndOpenFormsList(driver, true));
	}

	@Test(groups = "OldPortalForms")
	public void testQuotationMarksInFormPortal1() throws Exception {
		testQuotationMarksInForm(Utils.loginPortal1AndOpenFormsList(driver, true));
	}

	private void testQuotationMarksInForm(HealthFormListPage formsPage) throws Exception {
		log("step 1: Open form: " + SitegenConstants.SPECIAL_CHARS_FORM);
		SpecialCharFormFirstPage customPage1 = formsPage.openDiscreteForm(SitegenConstants.SPECIAL_CHARS_FORM).initToFirstPage(SpecialCharFormFirstPage.class);

		log("Step 2: Fill the form out with values containing quotes");
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
	 * @UserStory: US7083 Tests if filling out a form generates a PDF, if link for downloading the PDF appears in Patient Portal and if the link is working and
	 *             also whether corresponding CCD was generated. Then it checks if the submitted date is accurate and if patient's DOB has not been changed.
	 */
	@Test(groups = {"Forms"})
	public void testFormPdfCcdPI() throws Exception {
		TestcasesData portalData = new TestcasesData(new Portal());
		PropertyFileLoader testData = new PropertyFileLoader();
		JalapenoPatient patientData = new JalapenoPatient(testData);
		patientData.setUrl(new TestcasesData(new Portal()).getPIFormsAltUrl());
		patientData.setDOBYear("1900");
		HealthFormListPage hflp = CommonSteps.createAndLogInPatient(patientData, testData, driver).clickOnHealthForms();
		testFormPdfCcd(hflp, portalData);
		log("Step 6: Test if the DOB has not been changed");
		JalapenoHomePage jhp = Utils.loginPI(driver, true, patientData.getEmail(), patientData.getPassword());
		assertTrue(jhp.areMenuElementsPresent());
		JalapenoMyAccountProfilePage pMyAccountPage = jhp.clickOnAccount().clickOnEditMyAccount();
		assertEquals(IHGUtil.convertDate(pMyAccountPage.getDOB(), "MM/dd/yyyy", "MMMMM/dd/yyyy"),
				patientData.getDOBMonthText() + "/" + patientData.getDOBDay() + "/" + patientData.getDOBYear());
	}

	@Test(groups = "OldPortalForms")
	public void testFormPdfCcdPortal1() throws Exception {
		TestcasesData portalData = new TestcasesData(new Portal());
		CreatePatientTest patientCreation = new CreatePatientTest(null, null, new TestcasesData(new Portal()).getFormsAltUrl());
		HealthFormListPage hflp = patientCreation.createPatient(driver, new TestcasesData(new Portal())).clickOnHealthForms();
		testFormPdfCcd(hflp, portalData);
		log("Step 6: Test if the DOB has not been changed");
		MyAccountPage pMyAccountPage = Utils.loginPortal1(driver, true, patientCreation.getEmail(), patientCreation.getPassword()).clickMyAccountLink();
		assertEquals(IHGUtil.convertDate(pMyAccountPage.getDOB(), "MM/dd/yyyy", "MMMMM/dd/yyyy"), portalData.getDOB());
	}

	private void testFormPdfCcd(HealthFormListPage formsPage, TestcasesData portalData) throws Exception {
		long timestamp = System.currentTimeMillis() / 1000L;
		String xml;
		// easy bruising is mapped to following term in Forms Configurator in
		// SiteGen
		String easyBruisingString = "ABO donor$$$easy";
		String diacriticString = "¿¡eñÑeŘ\"";

		log("step 1: Open form: " + SitegenConstants.PDF_CCD_FORM);
		formsPage.openDiscreteForm(SitegenConstants.PDF_CCD_FORM).initToFirstPage(FormBasicInfoPage.class);

		log("Step 2: Fill out the form");
		fillOutputForm(diacriticString);

		log("Step 3: Test if PDF is downloadable");
		Utils.checkIfPDFCanBeDownloaded(SitegenConstants.PDF_CCD_FORM, driver);

		log("Step 4: Test if CCD is produced");
		log("Calling rest");
		xml = CCDTest.getFormCCD(timestamp, portalData.getCCDRestAltUrl());
		assertTrue(xml.contains(easyBruisingString), "Symptom not found in the CCD, printing the CCD:\n" + xml);

		log("Step 5: Test if the submission date is correct");
		Utils.verifyFormsDatePatientPortal(formsPage, SitegenConstants.PDF_CCD_FORM, driver);
		formsPage.logout();
	}

	@Test(groups = {"Forms"})
	public void testFormPracticePortalPI() throws Exception {
		testFormPracticePortal(Utils.loginPIAndOpenFormsList(driver, true));
	}

	@Test(groups = "OldPortalForms")
	public void testFormPracticePortalPortal1() throws Exception {
		testFormPracticePortal(Utils.loginPortal1AndOpenFormsList(driver, true));
	}

	private void testFormPracticePortal(HealthFormListPage formsPage) throws Exception {
		log("step 1: Open form: " + SitegenConstants.PRACTICE_FORM);
		FormBasicInfoPage demographPage = formsPage.openDiscreteForm(SitegenConstants.PRACTICE_FORM).initToFirstPage(FormBasicInfoPage.class);

		log("Step 2: Fill out and submit the form");
		FormMedicationsPage medsPage = demographPage.clickSaveContinue(FormMedicationsPage.class);
		medsPage.setNoMedications();

		FormPastMedicalHistoryPage illsPage = medsPage.clickSaveContinue(FormPastMedicalHistoryPage.class);
		illsPage.checkMononucleosis();
		illsPage.clickSaveContinueSamePage(3);

		log("Submitting form");
		illsPage.submitForm();

		log("Step 3: Logout from PI and login to Practice Portal");
		formsPage.logout();
		SearchPatientFormsPage pSearchPatientFormsPage = getPracticePortalSearchFormsPage();

		log("step 3: Search for PatientForms With Status Open");
		SearchPatientFormsResultPage pSearchPatientFormsResultPage = pSearchPatientFormsPage.SearchDiscreteFormsWithOpenStatus(SitegenConstants.PRACTICE_FORM);

		log("step 4: View the Result");
		ViewPatientFormPage pViewPatientFormPage = pSearchPatientFormsResultPage.clickViewLink();
		verifyFormsDateAndPDF(pViewPatientFormPage);
	}

	@Test(groups = {"Forms"})
	public void testPartiallyCompletedFormPI() throws Exception {
		testPartiallyCompletedForm(Utils.loginPIAndOpenFormsList(driver, true));
	}

	@Test(groups = "OldPortalForms")
	public void testPartiallyCompletedFormPortal1() throws Exception {
		testPartiallyCompletedForm(Utils.loginPortal1AndOpenFormsList(driver, true));
	}

	private void testPartiallyCompletedForm(HealthFormListPage formsPage) throws Exception {
		log("step 1: Open form: " + SitegenConstants.PRACTICE_FORM);
		FormBasicInfoPage firstPage = formsPage.openDiscreteForm(SitegenConstants.PRACTICE_FORM).initToFirstPage(FormBasicInfoPage.class);

		log("Step 2: Fill out the form");
		firstPage.clickSaveAndFinishAnotherTime();
		formsPage.logout();

		log("Step 3: Go to Practice Portal forms tab");
		SearchPatientFormsPage pSearchPatientFormsPage = getPracticePortalSearchFormsPage();
		SearchPartiallyFilledPage searchPage = pSearchPatientFormsPage.getPartiallyFilledSearch();
		searchPage.clickSearch();
		ViewPatientFormPage resultPage = searchPage.selectPatientsFirstForm();
		verifyFormsDateAndPDF(resultPage);
	}

	/**
	 * User Story ID in Rally: US544 - TA30648 StepsToReproduce: Log in to SG Go to Forms Config Unpublish all forms Delete all forms Create a new form and
	 * configure it Create a custom section and test saving it without name and questions Save the form Publish it Test viewing the form on Patient Portal ===
	 * Prerequisite for the test case to run========= Practice configured Practices configured on: DEV3, DEMO, PROD
	 * ============================================================
	 * 
	 * @throws Exception
	 */
	@Test(groups = {"Forms"})
	public void testDiscreteFormDeleteCreatePublish() throws Exception {

		String welcomeMessage = createFormSG();

		log("step 7: Log in to Patient Portal 1.0");
		MyPatientPage pMyPatientPage = Utils.loginPortal1(driver, false);

		log("step 8: Click On Start Registration Button and verify welcome page of the previously created form");
		FormWelcomePage pFormWelcomePage = pMyPatientPage.clickStartRegistrationButton(driver);
		assertEquals(pFormWelcomePage.getMessageText(), welcomeMessage);
		FormBasicInfoPage firstRegPage = pFormWelcomePage.clickSaveContinue(FormBasicInfoPage.class);
		firstRegPage.clickSaveAndFinishAnotherTime();
		pMyPatientPage.logout(driver);

		log("step 9: Log in to Patient Portal Inspired");
		JalapenoHomePage pHomePage = Utils.loginPI(driver, false);

		log("step 10: Click On Start Registration Button and verify welcome page of the previously created form");
		pHomePage.clickContinueRegistrationButton(driver);
		firstRegPage = new FormBasicInfoPage(driver);
		assertEquals(firstRegPage.isPageLoaded(), true);
	}

	@Test(groups = {"Forms"})
	public void testFormPatientDashboardPI() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		JalapenoPatient jp = new JalapenoPatient(testData);
		jp.setUrl(new TestcasesData(new Portal()).getPIFormsAltUrl());
		jp.setDOBYear("1900");
		HealthFormListPage hflp = CommonSteps.createAndLogInPatient(jp, testData, driver).clickOnHealthForms();
		testFormPatientDashboard(hflp, jp.getEmail(), jp.getFirstName(), jp.getLastName());
	}

	@Test(groups = "OldPortalForms")
	public void testFormPatientDashboardPortal1() throws Exception {
		CreatePatientTest cpt = new CreatePatientTest(null, null, new TestcasesData(new Portal()).getFormsAltUrl());
		HealthFormListPage hflp = cpt.createPatient(driver, new TestcasesData(new Portal())).clickOnHealthForms();
		testFormPatientDashboard(hflp, cpt.getEmail(), cpt.getFirstName(), cpt.getLastName());
	}

	private void testFormPatientDashboard(HealthFormListPage formsPage, String email, String firstName, String lastName) throws Exception {
		log("step 1: Open form :" + SitegenConstants.PDF_CCD_FORM);
		formsPage.openDiscreteForm(SitegenConstants.PDF_CCD_FORM).initToFirstPage(FormBasicInfoPage.class);

		log("Step 2: Fill out the form");
		fillOutputForm("Written by formPatientDashboardTest");

		log("Step 3: Logout");
		formsPage.logout();

		log("Step 4: Log in to Practice Portal");
		PracticeHomePage pHomePage = loginToPracticePortal();

		log("Step 5: Search for previously created patient");
		PatientSearchPage pSearchPage = pHomePage.clickPatientSearchLink();
		pSearchPage.searchForPatientInPatientSearch(email);

		log("Step 6: Get into patient dashboard");
		PatientDashboardPage pDashboardPage = pSearchPage.clickOnPatient(firstName, lastName);

		log("Step 7: Verify if there's submitted form on patient dashboard");
		assertTrue(pDashboardPage.verifySubmittedForm(SitegenConstants.PDF_CCD_FORM), "Submitted form was not found on Patient Dashboard");

		log("Step 8: Open forms detail and check donwload link");
		pDashboardPage.openFormDetails(SitegenConstants.PDF_CCD_FORM);
		Utils.checkIfPDFCanBeDownloaded("View as PDF", driver);
	}

	/**
	 * @UserStory: FORMS-346 Logins into sitegen. Creates a new custom form. Adds and removes FUPs. Saves form. Reopens the form and checks that it contains
	 *             correct items.
	 */
	@Test(groups = {"Forms"})
	public void testSitegenFUPInteraction() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Utils.logTestEnvironmentInfo("testCreateFormWithFUPs");
		Sitegen sitegen = new Sitegen();
		SitegenTestData SGData = new SitegenTestData(sitegen);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps().logInUserToSG(driver, SGData.getFormUser(), SGData.getFormPassword());

		log("step 1: Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 2: Unpublish and delete all forms and create a new one");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		pManageDiscreteForms.createCustomForm();

		log("step 3: Open created custom form");
		CustomFormPage customFormPage = pManageDiscreteForms.clickOnLastCreatedForm();
		customFormPage.clickOnSection(1);
		CustomFormPageSection section1 = customFormPage.getFirstSection();

		log("step 4: Create question and add 3 answers");
		section1.addQuestionItem(SitegenConstants.QUESTION_TYPE4, "base question", false, false);
		section1.addAnswers(1, Arrays.asList("answer1", "answer2", "answer3"));

		log("step 5: Add 2 FUPs to 1st answer of 1st question");
		section1.addQuestionFUP(1, 1, SitegenConstants.QUESTION_TYPE1, "followUpText", false, false);
		jse.executeScript("scroll(0, 0);");
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

	/**
	 * Fills and saves (does not submit) custom form. Tests displaying and interactivity of elements including FUPs.
	 */
	@Test(groups = {"Forms"})
	public void testCustomFormWithFUPsPI() throws Exception {
		testCustomFormWithFUPs(Utils.loginPIAndOpenFormsList(driver, true));
	}

	@Test(groups = "OldPortalForms")
	public void testCustomFormWithFUPsPortal1() throws Exception {
		testCustomFormWithFUPs(Utils.loginPortal1AndOpenFormsList(driver, true));
	}

	private void testCustomFormWithFUPs(HealthFormListPage healthFormPage) throws Exception {
		log("step 1: Open PI-testBeforeSubmit form");
		NewCustomFormPage customFormPage = healthFormPage.openNewCustomForm("PI-testBeforeSubmit");
		log("step 2: Clear all answers of 1st section");
		customFormPage.goToFirstSection();
		customFormPage.clearAllInputs();
		log("step 3: Check that FUPs are hidden after clear");
		List<WebElement> initiallyVisibleQuestionsFirstSection = customFormPage.getVisibleQuestions();
		assertEquals(initiallyVisibleQuestionsFirstSection.size(), 3);
		log("step 4: Test interactivity");
		log("step 4.1: Test top lvl required flag");
		assertFalse(customFormPage.clickOnSaveAndContinueButton());
		customFormPage.fillMultiLineAnswer(initiallyVisibleQuestionsFirstSection.get(0), "1st multiline answer");
		assertTrue(customFormPage.clickOnSaveAndContinueButton());
		customFormPage.clickOnGoToPrevious();
		log("step 4.2: Test showing of FUPs");
		customFormPage.fillSingleLineAnswer(initiallyVisibleQuestionsFirstSection.get(1), "1st singleline answer");
		customFormPage.selectAnswers(initiallyVisibleQuestionsFirstSection.get(2), Arrays.asList(1, 2));
		List<WebElement> visibleQuestionsFirstSection = customFormPage.getVisibleQuestions();
		assertEquals(visibleQuestionsFirstSection.size(), 7);
		log("step 4.3: Test FUP required flag");
		assertFalse(customFormPage.clickOnSaveAndContinueButton());
		log("step 4.4: Test answering FUPs");
		customFormPage.selectAnswer(visibleQuestionsFirstSection.get(3), 1);
		customFormPage.selectAnswer(visibleQuestionsFirstSection.get(4), 1);
		customFormPage.selectAnswers(visibleQuestionsFirstSection.get(5), Arrays.asList(1, 2));
		assertTrue(customFormPage.clickOnSaveAndContinueButton());
		log("step 4.5: Test second section behaving");
		customFormPage.clearAllInputs();
		List<WebElement> initiallyVisibleQuestionsSecondSection = customFormPage.getVisibleQuestions();
		assertEquals(initiallyVisibleQuestionsSecondSection.size(), 3);
		customFormPage.selectAnswer(initiallyVisibleQuestionsSecondSection.get(0), 2);
		List<WebElement> visibleQuestionsSecondSection = customFormPage.getVisibleQuestions();
		customFormPage.fillSingleLineAnswer(visibleQuestionsSecondSection.get(1), "should not see me and the end");
		customFormPage.selectAnswer(visibleQuestionsSecondSection.get(2), 1);
		log("step 4.6: Test headings and texts visibility");
		assertTrue(customFormPage.isHeadingDisplayed("HeadingS2"));
		assertTrue(customFormPage.isTextDisplayed("TextS2"));
		assertFalse(customFormPage.isHeadingDisplayed("Heading22"));
		assertFalse(customFormPage.isTextDisplayed("Text22"));
		customFormPage.selectAnswer(initiallyVisibleQuestionsSecondSection.get(0), 1);
		visibleQuestionsSecondSection = customFormPage.getVisibleQuestions();
		assertEquals(visibleQuestionsSecondSection.size(), 4);
		assertTrue(customFormPage.isHeadingDisplayed("Heading22"));
		assertTrue(customFormPage.isTextDisplayed("Text22"));
		log("step 4.7: Test consents's required flag");
		assertFalse(customFormPage.clickOnSaveAndContinueButton());
		customFormPage.consentAllVisibleStatements("InMyName");
		assertTrue(customFormPage.clickOnSaveAndContinueButton());
		log("step 4.8: Test section update button");
		customFormPage.selectSectionToUpdate(2);
		assertTrue(customFormPage.isSectionDisplayed(2));
		customFormPage.selectAnswer(visibleQuestionsSecondSection.get(1), 1);
		log("step 5: Save the form");
		customFormPage.clickOnGoToPrevious();
		customFormPage.clickOnSaveAndFinishButton();
		log("step 6: Open the form and checks saved values");
		IHGUtil.setFrame(driver, "iframe");
		customFormPage = healthFormPage.openNewCustomForm("PI-testBeforeSubmit");
		assertEquals(QuestionsService.getSetOfVisibleQuestions(1, driver), getExpectedQuestionsForFirstSection());
		assertTrue(customFormPage.clickOnSaveAndContinueButton());
		assertEquals(QuestionsService.getSetOfVisibleQuestions(2, driver), getExpectedQuestionsForSecondSection());
	}

	@Test
	public void testFormExportImport() throws Exception {
		driver.close();
		driver = Utils.getFirefoxDriverForDownloading();

		Utils.logTestEnvironmentInfo("Test exporting and importing of patient form");
		log("step 1: login to SG as superuser");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testcasesData = new SitegenTestData(sitegen);
		String automationPracticeID = String.valueOf(Utils.getAutomationPracticeID(true));
		SiteGenHomePage sHomePage = new SiteGenLoginPage(driver, testcasesData.getSiteGenUrl()).clickOnLoginAsInternalEmployee();
		// now you have to LOG IN MANUALLY AS SUPERUSER, the test will continue after that
		log("step 2: navigate to SiteGen PracticeHomePage, practice with ID: " + automationPracticeID);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = sHomePage.searchPracticeFromSGAdmin(automationPracticeID);
		String parentHandle = driver.getWindowHandle();
		log("step 3: Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());
		log("step 4: Cleanup unpublished forms");
		pManageDiscreteForms.deleteUnpublishedFormsNamedLike(SitegenConstants.FORM_EXPORT_IMPORT);
		log("step 5: Export form");
		pManageDiscreteForms.exportForm(SitegenConstants.FORM_EXPORT_IMPORT);

		log("step 6: Compare exported form file with representative file");
		Path exportedFilePath = Paths.get(System.getProperty("user.dir") + "\\" + SitegenConstants.FORM_EXPORT_IMPORT + ".txt");
		String exportedFileString = stripVariablesFromExportedForm(Files.readAllLines(exportedFilePath, Charsets.UTF_8).get(0));
		String representativeFileString = stripVariablesFromExportedForm(
				Files.readAllLines(Paths.get(ClassLoader.getSystemResource(SitegenConstants.FORM_EXPORT_IMPORT + ".txt").toURI()), Charsets.UTF_8).get(0));
		assertEquals(exportedFileString, representativeFileString);

		log("step 7: Import form");
		pManageDiscreteForms.importForm(SitegenConstants.FORM_EXPORT_IMPORT);

		log("step 8: Check imported form preview");
		assertFalse(pManageDiscreteForms.openUnpublishedFormPreview(SitegenConstants.FORM_EXPORT_IMPORT).getMessageText().isEmpty());

		log("step 9: Close the window and logout from SiteGenerator");
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();
	}

	@Test
	public void testEGQ() throws Exception {
		Utils.logTestEnvironmentInfo("Test exporting and importing of patient form");
		log("step 1: login to SG as superuser");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testcasesData = new SitegenTestData(sitegen);
		String automationPracticeID = String.valueOf(Utils.getAutomationPracticeID(false));
		SiteGenHomePage sHomePage = new SiteGenLoginPage(driver, testcasesData.getSiteGenUrl()).clickOnLoginAsInternalEmployee();
		// now you have to LOG IN MANUALLY AS SUPERUSER, the test will continue after that
		log("step 2: navigate to SiteGen PracticeHomePage - practice with id: " + automationPracticeID);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = sHomePage.searchPracticeFromSGAdmin(automationPracticeID);
		log("step 3: enable EGQ");
		pSiteGenPracticeHomePage.openPracticeInfo().enableGenderQuestions().returnToPracticeHomePage();
		String homePageSGWindow = driver.getWindowHandle();
		log("step 4: open forms and clean current forms");
		DiscreteFormsList formsList = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		formsList.initializePracticeForNewForm();
		log("step 5: create form for test");
		formsList.createRegistrationForm();
		String formsSGListWindow = driver.getWindowHandle();
		log("step 6: test behaving when EGQ are enabled - SG");
		testEGQSitegenEnabledFlow(formsList);
		log("step 7: test behaving when EGQ are enabled - PI");
		WebDriver portalDriver = WebDriverFactory.getWebDriver();
		portalDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		HealthFormListPage formsListPI = testEGQEnabledPIFlow(portalDriver);
		driver.switchTo().window(homePageSGWindow);
		log("step 9: disable EGQ");
		pSiteGenPracticeHomePage.openPracticeInfo().disableGenderQuestions().returnToPracticeHomePage();
		driver.switchTo().window(formsSGListWindow);
		log("step 9: test behaving when EGQ are disabled - SG");
		testEGQSitegenDisabledFlow(formsList);
		log("step 10: test behaving when EGQ are disabled - PI");
		testEGQDisabledPIFlow(formsListPI);
	}

	private void testEGQSitegenEnabledFlow(DiscreteFormsList formsList) throws Exception {
		IHGUtil.PrintMethodName();
		BasicInformationAboutYouPage basicInfoPage =
				formsList.openDiscreteForm(SitegenConstants.FORMS_REGISTRATION_FORM_INITIAL_NAME).clicklnkBasicInfoAboutYourPage();
		String sexQuestionLabel = "What sex were you assigned at birth on your original birth certificate?";
		log("check sex question label");
		assertEquals(basicInfoPage.getSexQuetionLabel(), sexQuestionLabel);
		log("check sex question allways required");
		assertTrue(basicInfoPage.isQuestionRequired(sexQuestionLabel));
		basicInfoPage.clickQuestionRequiredAsterisk(sexQuestionLabel);
		assertTrue(basicInfoPage.isQuestionRequired(sexQuestionLabel));
		log("check adding question");
		assertFalse(basicInfoPage.isQuestionInForm(sexQuestionLabel));
		basicInfoPage.addQuestionToForm(sexQuestionLabel);
		assertTrue(basicInfoPage.isQuestionInForm(sexQuestionLabel));
		log("configure form");
		CurrentSymptomsPage symptoms = basicInfoPage.clicklnkCurrentSymptoms();
		symptoms.addMaleSymptoms();
		symptoms.addFemaleSymptoms();
		symptoms.clicklnkPastMedicalHistory().addFemaleQuestions();
		int[] sectionsToHide = {2, 3, 4, 5, 7, 8, 9, 10, 11, 13, 14};
		symptoms.hideSections(sectionsToHide);
		log("save and leave");
		basicInfoPage.saveOpenedForm();
		formsList = basicInfoPage.clickBackToTheList();
		formsList.publishForm(SitegenConstants.FORMS_REGISTRATION_FORM_INITIAL_NAME);
	}

	private HealthFormListPage testEGQEnabledPIFlow(WebDriver driver) throws Exception {
		long timestamp = System.currentTimeMillis() / 1000L;
		log("create patient and login to PI");
		PropertyFileLoader testData = new PropertyFileLoader();
		JalapenoPatient patientData = new JalapenoPatient(testData);
		patientData.setUrl(new TestcasesData(new Portal()).getPIFormsUrl());
		patientData.setDOBYear("1900");
		JalapenoHomePage home = CommonSteps.createAndLogInPatient(patientData, testData, driver);
		HealthFormListPage formsList = home.clickOnHealthForms();
		log("check question label");
		FormBasicInfoPage basicInfo = formsList.openDiscreteForm(SitegenConstants.FORMS_REGISTRATION_FORM_INITIAL_NAME).initToFirstPage(FormBasicInfoPage.class);
		assertEquals(basicInfo.getGenderQuestionLabel(), "What sex were you assigned at birth on your original birth certificate?*");
		log("check that answer is prefilled by information from my account");
		assertEquals(basicInfo.getSelectedGender(), "Male");
		log("check that answer is required");
		basicInfo.setSex("Choose...");
		basicInfo.clickSaveContinue();
		assertTrue(basicInfo.isInputErrorMessageDisplayed());
		basicInfo.setSex("Male");
		FormCurrentSymptomsPage symptoms = basicInfo.clickSaveContinue(FormCurrentSymptomsPage.class);
		log("check that only male-specific questions are displayed to male");
		assertTrue(symptoms.areMaleQuestionsDisplayed());
		assertFalse(symptoms.areFemaleQuestionsDisplayed());
		symptoms.setNoMaleSymptoms();
		FormPastMedicalHistoryPage medHistory = symptoms.clickSaveContinue(FormPastMedicalHistoryPage.class);
		assertFalse(symptoms.areFemaleQuestionsDisplayed());
		symptoms = medHistory.goBack(FormCurrentSymptomsPage.class);
		basicInfo = medHistory.goBack(FormBasicInfoPage.class);
		log("choose declined to answer");
		basicInfo.setSex("Declined to answer");
		symptoms = basicInfo.clickSaveContinue(FormCurrentSymptomsPage.class);
		log("check that all questions are displayed to user who didn't answerd gender");
		assertTrue(symptoms.areFemaleQuestionsDisplayed());
		assertTrue(symptoms.areMaleQuestionsDisplayed());
		symptoms.checkAnswer("Penile lesions");
		symptoms.checkAnswer("Vaginal dryness");
		medHistory = symptoms.clickSaveContinue(FormPastMedicalHistoryPage.class);
		medHistory.setCountOfPregnancies(0);
		medHistory.clickSaveContinue();
		log("submit form");
		medHistory.submitForm();
		log("calling CCD rest");
		String ccd = CCDTest.getFormCCD(timestamp, new TestcasesData(new Portal()).getCCDRestUrl());
		log("check that answered questions are in CCD");
		assertTrue(ccd.contains("Penile lesions"), "not found in ccd: " + ccd);
		assertTrue(ccd.contains("Vaginal dryness"), "not found in ccd: " + ccd);
		assertTrue(ccd.contains("None, I have never been pregnant"), "not found in ccd: " + ccd);
		log("check that there is nullFlavor UNK for gender in CCD");
		assertTrue(Pattern.compile("<administrativeGenderCode[^>]+nullFlavor=\"UNK\"").matcher(ccd).find(), "not found in ccd: " + ccd);
		formsList.goToHomePage();
		log("check that value in my account has been changed based on form answer");
		JalapenoMyAccountProfilePage pMyAccountPage = home.clickOnMyAccount();
		assertTrue(pMyAccountPage.getGender() == null);
		return pMyAccountPage.clickOnMenuHealthForms();
	}

	private void testEGQSitegenDisabledFlow(DiscreteFormsList formsList) throws Exception {
		IHGUtil.PrintMethodName();
		BasicInformationAboutYouPage basicInfoPage =
				formsList.openDiscreteForm(SitegenConstants.FORMS_REGISTRATION_FORM_INITIAL_NAME).clicklnkBasicInfoAboutYourPage();
		String sexQuestionLabel = "Sex";
		log("check sex question label");
		assertEquals(basicInfoPage.getSexQuetionLabel(), sexQuestionLabel);
		log("check sex question required/optional");
		boolean atFirst = basicInfoPage.isQuestionRequired(sexQuestionLabel);
		basicInfoPage.clickQuestionRequiredAsterisk(sexQuestionLabel);
		boolean afterClick = basicInfoPage.isQuestionRequired(sexQuestionLabel);
		assertEquals(atFirst, !afterClick);
		log("save and close");
		basicInfoPage.addQuestionToForm(sexQuestionLabel);
		basicInfoPage.makeQuestionOptional(sexQuestionLabel);
		basicInfoPage.saveOpenedForm();
		basicInfoPage.clickBackToTheList();
	}

	private void testEGQDisabledPIFlow(HealthFormListPage formsList) throws Exception {
		long timestamp = System.currentTimeMillis() / 1000L;
		String ccd;
		log("check question label");
		FormBasicInfoPage basicInfo = formsList.openDiscreteForm(SitegenConstants.FORMS_REGISTRATION_FORM_INITIAL_NAME).initToFirstPage(FormBasicInfoPage.class);
		assertEquals(basicInfo.getGenderQuestionLabel(), "Sex");
		log("check that answer is prefilled by information from my account (declined -> choose ...)");
		assertEquals(basicInfo.getSelectedGender(), "Choose...");
		log("check that answer is not required");
		basicInfo.setSex("Choose...");
		FormCurrentSymptomsPage symptoms = basicInfo.clickSaveContinue(FormCurrentSymptomsPage.class);
		log("check that all questions are displayed to user who didn't answered gender");
		assertTrue(symptoms.areFemaleQuestionsDisplayed());
		assertTrue(symptoms.areMaleQuestionsDisplayed());
		symptoms.checkAnswer("Discharge from penis");
		symptoms.checkAnswer("Vaginal itching/burning");
		FormPastMedicalHistoryPage medHistory = symptoms.clickSaveContinue(FormPastMedicalHistoryPage.class);
		assertTrue(medHistory.areFemaleQuestionsDisplayed());
		medHistory.setCountOfPregnancies(5);
		medHistory.clickSaveContinue();
		log("submit form");
		medHistory.submitForm();
		log("calling CCD rest");
		ccd = CCDTest.getFormCCD(timestamp, new TestcasesData(new Portal()).getCCDRestUrl());
		log("check that prevously answered questions are not in CCD");
		assertFalse(ccd.contains("Penile lesions"), "not found in ccd: " + ccd);
		assertFalse(ccd.contains("Vaginal dryness"), "not found in ccd: " + ccd);
		assertFalse(ccd.contains("None, I have never been pregnant"), "not found in ccd: " + ccd);
		log("check that answered questions are in CCD");
		assertTrue(ccd.contains("Discharge from penis"), "not found in ccd: " + ccd);
		assertTrue(ccd.contains("Vaginal itching/burning"), "not found in ccd: " + ccd);
		formsList.goToHomePage();
	}

	/**
	 * Fills out Output form for CCD test. Needs the form to be opened and on the first (welcome) page
	 * 
	 * @param diacriticString - String to fill out in Symptoms comments, used for testing special diacritic
	 */
	private void fillOutputForm(String diacriticString) throws Exception {
		FormBasicInfoPage basicInfoPage = PageFactory.initElements(driver, FormBasicInfoPage.class);
		FormCurrentSymptomsPage currentSymptomsPage = basicInfoPage.clickSaveContinue(FormCurrentSymptomsPage.class);
		currentSymptomsPage.setBasicSymptoms();
		currentSymptomsPage.enterComment(diacriticString);
		CurrentSymptomsSupplementalPage symptomsSupplemental = currentSymptomsPage.clickSaveContinue(CurrentSymptomsSupplementalPage.class);
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
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getFormUser(), practiceTestData.getFormPassword());

		log("Click CustomFormTab");
		SearchPatientFormsPage searchFormsPage = practiceHome.clickCustomFormTab();

		// check if the page is loaded, sometimes the test ends up on login page
		// at this point
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!searchFormsPage.isPageLoaded() && practiceLogin.isLoginPageLoaded()) {
			searchFormsPage = practiceLogin.login(practiceTestData.getFormUser(), practiceTestData.getFormPassword()).clickCustomFormTab();
		}
		driver.manage().timeouts().implicitlyWait(SitegenConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);

		assertTrue(searchFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + " failed to load.");
		return searchFormsPage;
	}

	/**
	 * Verifies that record of completed or partially completed form is from the current day and that the pdf is downloadable
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

	protected String createFormSG() throws Exception {
		String newFormName = SitegenConstants.DISCRETEFORMNAME + IHGUtil.createRandomNumericString().substring(0, 4);

		Utils.logTestEnvironmentInfo("testDiscreteFormDeleteCreatePublish");
		Sitegen sitegen = new Sitegen();
		SitegenTestData SGData = new SitegenTestData(sitegen);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps().logInUserToSG(driver, SGData.getFormUser(), SGData.getFormPassword());
		// Get the current window handle before opening new window
		String parentHandle = driver.getWindowHandle();

		log("step 1: Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 2: Unpublish and delete all forms and create a new one");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		pManageDiscreteForms.createRegistrationForm();

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

	private Set<Question> getExpectedQuestionsForFirstSection() {
		Set<Question> expectedQuestions = new HashSet<Question>();
		expectedQuestions.add(new TextQuestion("top lvl mlt-txt req*", "1st multiline answer"));
		expectedQuestions.add(new TextQuestion("top lvl slt-txt", "1st singleline answer"));
		Set<SelectableAnswer> h1 = new HashSet<SelectableAnswer>();
		h1.add(new SelectableAnswer("radio fups", true));
		h1.add(new SelectableAnswer("chckbox fups", true));
		expectedQuestions.add(new SelectQuestion("top lvl chckbox", h1));
		Set<SelectableAnswer> h2 = new HashSet<SelectableAnswer>();
		h2.add(new SelectableAnswer("r1", true));
		h2.add(new SelectableAnswer("r2", false));
		expectedQuestions.add(new SelectQuestion("rf1 req*", h2));
		Set<SelectableAnswer> h3 = new HashSet<SelectableAnswer>();
		h3.add(new SelectableAnswer("r1", true));
		h3.add(new SelectableAnswer("r2", false));
		expectedQuestions.add(new SelectQuestion("rf2 req*", h3));
		Set<SelectableAnswer> h4 = new HashSet<SelectableAnswer>();
		h4.add(new SelectableAnswer("c1", true));
		h4.add(new SelectableAnswer("c2", true));
		expectedQuestions.add(new SelectQuestion("cf1 req*", h4));
		Set<SelectableAnswer> h5 = new HashSet<SelectableAnswer>();
		h5.add(new SelectableAnswer("c1", false));
		h5.add(new SelectableAnswer("c2", false));
		expectedQuestions.add(new SelectQuestion("cf2", h5));
		return expectedQuestions;
	}

	private Set<Question> getExpectedQuestionsForSecondSection() {
		Set<Question> expectedQuestions = new HashSet<Question>();
		Set<SelectableAnswer> h1 = new HashSet<SelectableAnswer>();
		h1.add(new SelectableAnswer("with FUPs", true));
		h1.add(new SelectableAnswer("withtwo FUPs", false));
		expectedQuestions.add(new SelectQuestion("QuestionS2 req*", h1));
		Set<SelectableAnswer> h2 = new HashSet<SelectableAnswer>();
		h2.add(new SelectableAnswer("answer1", true));
		h2.add(new SelectableAnswer("answer2", false));
		expectedQuestions.add(new SelectQuestion("QuestionS2FUP1", h2));
		expectedQuestions.add(new TextQuestion("QuestionS2FUP2", ""));
		expectedQuestions.add(new TextQuestion("QuestionS2FUP2", ""));
		return expectedQuestions;
	}

	private String stripVariablesFromExportedForm(String s) {
		return s.replaceAll("\"\\w+Date\":\\d+", "").replaceAll("\"formId\":\"[^\"]+\"", "").replaceAll("\"copiedFrom\":\"[^\"]+\"", "")
				.replaceAll("\"\\w+racticeId\":\\d+", "").replaceAll("\"environment\":\"\\w+\"", "");
	}
}
