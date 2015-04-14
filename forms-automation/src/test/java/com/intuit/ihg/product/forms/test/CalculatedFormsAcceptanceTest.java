package com.intuit.ihg.product.forms.test;

import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;

import org.testng.annotations.Test;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.healthform.HealthFormPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.CalculatedFormPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormWelcomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.WelcomeScreenPage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;

public class CalculatedFormsAcceptanceTest extends FormsAcceptanceTests {

	/**
	 * Tect Case in TestLink: MF-1265
	 * 
	 * @author phajek
	 * @Date: 13/02/2015
	 *        StepsToReproduce:
	 *        Log in to SG as SU
	 *        Go to Forms Config
	 *        Unpublish all forms
	 *        Delete all forms
	 *        Search and add a new Calculated form
	 *        Test if it is displayed in Calculated Forms directory
	 *        Delete the Form
	 *        Test if it is displayed in Calculated Forms directory
	 *        === Prerequisite for the test case to run=========
	 *        Practices configured on: DEV3
	 *        ============================================================
	 * @throws Exception
	 */


	@Test
	public void testCalculatedFormAddRemove() throws Exception {

		logTestEnvironmentInfo("Test Adding and removing of Calculated Form");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testData = new SitegenTestData(sitegen);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps().logInUserToSG(driver,
				testData.getAdminUser(), testData.getAdminPassword(), testData.getAutomationPracticeName());
		// Get the current window handle before opening new window
		String parentHandle = driver.getWindowHandle();

		log("step 1: Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 2: Unpublish and delete all forms and add calculated form");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		assertTrue(pManageDiscreteForms.addCalculatedForm(SitegenConstants.CALCULATEDFORMNAME));

		log("step 3: Check if the added form is no longer in the Calculated Form Directory ");
		assertFalse(pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATEDFORMNAME));

		log("step 4: Delete all Forms and check if the Calculated Form is back in Directory");
		pManageDiscreteForms.initializePracticeForNewForm();
		assertTrue(pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATEDFORMNAME));

		log("step 5: Close the window and logout from SiteGenerator");
		// Switching back to original window using previously saved handle descriptor
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();

	}

	/**
	 * @author: Adam W
	 * @Steps: Login to Site Generator, click on Patient Forms, open calculated form
	 *         change welcome screen, save the form, exit,
	 */

	@Test(groups = "calculatedForms")
	public void testCalculatedFormSGEdit() throws Exception {
		logTestEnvironmentInfo("testCalculatedFormSGEdit");
		String newWelcomeMessage = "Welcome " + IHGUtil.createRandomNumber();

		Sitegen sitegen = new Sitegen();
		SitegenTestData testData = new SitegenTestData(sitegen);
		SiteGenPracticeHomePage SGPracticePage = new SiteGenSteps().logInUserToSG(driver, testData.getFormUser(),
				testData.getFormPassword());

		DiscreteFormsList formsConfigPage = SGPracticePage.clickLnkDiscreteForms();
		driver.manage().window().maximize();
		WelcomeScreenPage welcomePage = formsConfigPage.openDiscreteForm(SitegenConstants.CALCULATEDFORMNAME);

		welcomePage.setWelcomeMessage(newWelcomeMessage);
		welcomePage.saveOpenedForm();
		welcomePage.clickBackToTheList();

		FormWelcomePage previewWelcomePage = formsConfigPage.openCalculatedFormPreview();
		PortalUtil.setquestionnarieFrame(driver);
		assertEquals(newWelcomeMessage, previewWelcomePage.getMessageText());
	}

	/**
	 * @author: Petr H
	 * @Steps: Login to Patient Portal, click on Patient Forms, open calculated form,
	 *         fill in the form, submit the form, check if PDF was generated
	 *         Practices configured on: DEV3
	 */

	@Test(groups = "calculatedForms")
	public void testAllCalculatedFormsPatientPortal() throws Exception {
		String[] names = { "PHQ-2", "PHQ-9", "ADHD" };
		testCalculatedFormPatientPortal(names);
	}

	@Test
	public void testOneCalculatedFormPatientPortal() throws Exception {
		String[] name = { "PHQ-2" };
		testCalculatedFormPatientPortal(name);
	}

	@Test
	private void testCalculatedFormPatientPortal(String[] formNames)
			throws Exception {
		logTestEnvironmentInfo("Test filling Calculated form in Patient Portal");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalData.getFormsAltUrl());

		log("step 1: Click on Sign Up Fill details in Create Account Page");
		MyPatientPage pMyPatientPage = createPatient(portalData);

		log("step 2: Click on forms and open the form");
		HealthFormPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		for (String formName : formNames) {
			log("Testing Form named " + formName);
			formsPage.openDiscreteForm(formName);

			log("Step 3: Fill in the form with all the required answers and submit.");
			FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
			CalculatedFormPage calculatedFormPage = welcomePage
					.initializeFormToFirstPage(CalculatedFormPage.class);
			assertTrue(calculatedFormPage.isPageLoaded());
			calculatedFormPage.fillFormRightmostAnswer();
			calculatedFormPage.clickSaveContinue();
			calculatedFormPage.submitForm();

			log("Step 4: Check if the PDF is downloadable.");
			checkPDF(formsPage, formName);

			log("Step 5: Check if the date is correct");
			verifyFormsDatePatientPortal(formsPage, formName);

		}
	}

	/**
	 * @author: Petr H
	 * @Steps: Login to Patient Portal, click on Patient Forms, open calculated form,
	 *         try to save without any answer, try to save it with one answer missing
	 *         and finally saves it with all the correct answers
	 *         Practices configured on: DEV3
	 */
	@Test(groups = "calculatedForms")
	public void testCalculatedFormValidation() throws Exception {
		logTestEnvironmentInfo("Test filling Calculated form in Patient Portal");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalData.getFormsAltUrl());
	
		log("step 1: Click on Sign Up Fill details in Create Account Page");
		MyPatientPage pMyPatientPage = createPatient(portalData);
	
		log("step 2: Click on forms and open the form");
		HealthFormPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		formsPage.openDiscreteForm("PHQ-9");
	
		log("Step 3: Try to Save and continue without any answer.");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		CalculatedFormPage calculatedFormPage = welcomePage.initializeFormToFirstPage(CalculatedFormPage.class);
		assertTrue(calculatedFormPage.isPageLoaded());
		assertFalse(calculatedFormPage.isValidationErrorDisplayed());
		calculatedFormPage.clickSaveContinue();
		assertTrue(calculatedFormPage.isValidationErrorDisplayed());
	
		log("Step 4: Try to Save and continue with one answer missing.");
		assertTrue(calculatedFormPage.isPageLoaded());
		calculatedFormPage.fillFormExcludingLastQuestion();
		assertTrue(calculatedFormPage.isValidationErrorDisplayed());
		assertTrue(calculatedFormPage.isPageLoaded());
	
		log("Step 5: Fill all the answers and click Save and continue.");
		calculatedFormPage.fillFormLeftmostAnswer();
		calculatedFormPage.clickSaveContinue();
		calculatedFormPage.submitForm();
	}

}
