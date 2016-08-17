package com.intuit.ihg.product.forms.test;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.healthform.HealthFormPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.CalculatedFormPage;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.FormWelcomePage;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.PortalUtil;
import com.medfusion.product.patientportal1.utils.TestcasesData;

public class CalculatedFormsAcceptanceTest extends FormsAcceptanceTests {

	/**
	 * Tect Case in TestLink: MF-1265
	 * 
	 * @author phajek
	 * @Date: 13/02/2015 StepsToReproduce: Log in to SG as SU Go to Forms Config
	 *        Unpublish all forms Delete all forms Search and add a new
	 *        Calculated form Test if it is displayed in Calculated Forms
	 *        directory Delete the Form Test if it is displayed in Calculated
	 *        Forms directory === Prerequisite for the test case to run=========
	 *        Practices configured on: DEV3, MANUAL login to the SG as superuser
	 *        ============================================================
	 * @throws Exception
	 */
	@Test
	public void testCalculatedFormAddRemove() throws Exception {
		logTestEnvironmentInfo("Test Adding and removing of Calculated Form");
		log("step 1: navigate to SiteGen PracticeHomePage");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testcasesData = new SitegenTestData(sitegen);
		new SiteGenLoginPage(driver, testcasesData.getSiteGenUrl());
		log("step 2: LOG IN MANUALLY AS SUPERUSER, the test will continue after that, waiting 30s");
		SiteGenHomePage sHomePage = PageFactory.initElements(driver, SiteGenHomePage.class);
		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage;
		pSiteGenPracticeHomePage = sHomePage.searchPracticeFromSGAdmin(testcasesData.getAutomationPracticeName());
		String parentHandle = driver.getWindowHandle();
		log("step 1: Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 2: Unpublish and delete all forms and add calculated form");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		assertTrue(pManageDiscreteForms.addCalculatedForm(SitegenConstants.CALCULATED_PHQ9_FORM));

		log("step 3: Check if the added form is no longer in the Calculated Form Directory ");
		assertFalse(pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATED_PHQ9_FORM));

		log("step 4: Delete all Forms and check if the Calculated Form is back in Directory");
		pManageDiscreteForms.initializePracticeForNewForm();
		assertTrue(pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATED_PHQ9_FORM));

		log("step 5: Close the window and logout from SiteGenerator");
		// Switching back to original window using previously saved handle
		// descriptor
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();

	}

	/**
	 * @author Adam W Steps: Login to Site Generator, click on Patient Forms,
	 *         open calculated form change welcome screen, save the form, exit,
	 *         publish it, test changes in preview do the same for a second form
	 */
	@Test(groups = "CalculatedForms")
	public void testCalculatedFormSGEdit() throws Exception {
		logTestEnvironmentInfo("testCalculatedFormSGEdit");
		String newWelcomeMessage = "Welcome " + IHGUtil.createRandomNumber();

		Sitegen sitegen = new Sitegen();
		SitegenTestData testData = new SitegenTestData(sitegen);
		SiteGenPracticeHomePage SGPracticePage = new SiteGenSteps().logInUserToSG(driver, testData.getFormUser(),
				testData.getFormPassword());

		DiscreteFormsList formsConfigPage = SGPracticePage.clickLnkDiscreteForms();
		driver.manage().window().maximize();
		formsConfigPage.unpublishAllForms()
				.editFormsWelcomePage(SitegenConstants.CALCULATED_PHQ9_FORM, newWelcomeMessage)
				.editFormsWelcomePage(SitegenConstants.CALCULATED_ADHD_FORM, newWelcomeMessage)
				.publishForm(SitegenConstants.CALCULATED_PHQ9_FORM).publishForm(SitegenConstants.CALCULATED_ADHD_FORM);

		FormWelcomePage previewWelcomePage = formsConfigPage.openCalculatedFormPreview();
		PortalUtil.setquestionnarieFrame(driver);
		assertEquals(newWelcomeMessage, previewWelcomePage.getMessageText());
	}

	/**
	 * @author: Petr H
	 * @Steps: Login to Patient Portal, click on Patient Forms, open calculated
	 *         form, fill in the form, submit the form, check if PDF was
	 *         generated Practices configured on: DEV3
	 */

	@Test(groups = "CalculatedForms")
	public void testAllCalculatedFormsPatientPortal() throws Exception {
		String[] names = { SitegenConstants.CALCULATED_PHQ2_FORM, SitegenConstants.CALCULATED_PHQ9_FORM,
				SitegenConstants.CALCULATED_ADHD_FORM };
		testCalculatedFormPatientPortal(names);
	}

	/**
	 * @author: Petr H
	 * @Steps: Login to Patient Portal, click on Patient Forms, open calculated
	 *         form, try to save without any answer, try to save it with one
	 *         answer missing and finally saves it with all the correct answers
	 *         Practices configured on: DEV3
	 */
	@Test(groups = "CalculatedForms")
	public void testCalculatedFormValidation() throws Exception {
		logTestEnvironmentInfo("Test filling Calculated form in Patient Portal");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalData.getFormsAltUrl());

		log("step 1: Click on Sign Up Fill details in Create Account Page");
		MyPatientPage pMyPatientPage = createPatient(portalData);

		log("step 2: Click on forms and open the form");
		HealthFormPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		formsPage.openDiscreteForm(SitegenConstants.CALCULATED_PHQ9_FORM);

		log("Step 3: Try to Save and continue without any answer.");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		CalculatedFormPage calculatedFormPage = welcomePage.initToFirstPage(CalculatedFormPage.class);
		assertTrue(calculatedFormPage.isPageLoaded());
		assertFalse(calculatedFormPage.isValidationErrorDisplayed());
		calculatedFormPage.clickSaveContinue();
		assertTrue(calculatedFormPage.isValidationErrorDisplayed());

		log("Step 4: Try to Save and continue with one answer missing.");
		calculatedFormPage.fillFormExcludingLastQuestion();
		assertTrue(calculatedFormPage.isValidationErrorDisplayed());

		log("Step 5: Fill all the answers and click Save and continue.");
		calculatedFormPage.fillFormLeftmostAnswer();
		calculatedFormPage.clickSaveContinueSamePage(3);
		calculatedFormPage.submitForm();
	}

	private void testCalculatedFormPatientPortal(String[] formNames) throws Exception {
		logTestEnvironmentInfo("Test filling Calculated form in Patient Portal");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalData.getFormsAltUrl());

		log("step 1: Click on Sign Up Fill details in Create Account Page");
		MyPatientPage pMyPatientPage = createPatient(portalData);

		log("step 2: Click on forms");
		HealthFormPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		for (String formName : formNames) {
			log("Opening Form named " + formName);
			try {
				formsPage.openDiscreteForm(formName);
			} catch (StaleElementReferenceException e) {
				formsPage.openDiscreteForm(formName);
			}

			log("Step 3: Fill in the form with all the required answers and submit.");
			FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
			CalculatedFormPage calculatedFormPage = welcomePage.initToFirstPage(CalculatedFormPage.class);
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

}
