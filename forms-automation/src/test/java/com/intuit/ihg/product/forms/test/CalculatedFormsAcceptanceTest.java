package com.intuit.ihg.product.forms.test;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.CalculatedFormPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.FormWelcomePage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class CalculatedFormsAcceptanceTest extends BaseTestNGWebDriver {

	/**
	 * Tect Case in TestLink: MF-1265
	 * 
	 * @author phajek
	 * @Date: 13/02/2015 StepsToReproduce: Log in to SG as SU Go to Forms Config Unpublish all forms Delete all forms Search and add a new Calculated form Test if
	 *        it is displayed in Calculated Forms directory Delete the Form Test if it is displayed in Calculated Forms directory === Prerequisite for the test
	 *        case to run========= Practices configured on: DEV3, MANUAL login to the SG as superuser ============================================================
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void testCalculatedFormAddRemove() throws Exception {
		Utils.logTestEnvironmentInfo("Test Adding and removing of Calculated Form");
		log("step 1: navigate to SiteGen PracticeHomePage");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testcasesData = new SitegenTestData(sitegen);
		new SiteGenLoginPage(driver, testcasesData.getSiteGenUrl());
		SiteGenHomePage sHomePage = PageFactory.initElements(driver, SiteGenHomePage.class);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage;
		log("step 2: LOG IN MANUALLY AS SUPERUSER, the test will continue after that, waiting 30s");
		pSiteGenPracticeHomePage = sHomePage.searchPracticeFromSGAdmin(testcasesData.getAutomationPracticeName());
		String parentHandle = driver.getWindowHandle();
		log("step 3: Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 4: Unpublish and delete all forms and add calculated form");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		assertTrue(pManageDiscreteForms.addCalculatedForm(SitegenConstants.CALCULATED_PHQ9_FORM));

		log("step 5: Check if the added form is no longer in the Calculated Form Directory ");
		assertFalse(pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATED_PHQ9_FORM));

		log("step 6: Delete all Forms and check if the Calculated Form is back in Directory");
		pManageDiscreteForms.initializePracticeForNewForm();
		assertTrue(pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATED_PHQ9_FORM));

		log("step 7: Close the window and logout from SiteGenerator");
		// Switching back to original window using previously saved handle
		// descriptor
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();

	}

	/**
	 * @author Adam W Steps: Login to Site Generator, click on Patient Forms, open calculated form change welcome screen, save the form, exit, publish it, test
	 *         changes in preview do the same for a second form
	 */
	@Test(groups = "CalculatedForms")
	public void testCalculatedFormSGEdit() throws Exception {
		Utils.logTestEnvironmentInfo("testCalculatedFormSGEdit");
		String newWelcomeMessage = "Welcome " + IHGUtil.createRandomNumber();

		Sitegen sitegen = new Sitegen();
		SitegenTestData testData = new SitegenTestData(sitegen);
		SiteGenPracticeHomePage SGPracticePage = new SiteGenSteps().logInUserToSG(driver, testData.getFormUser(), testData.getFormPassword());

		DiscreteFormsList formsConfigPage = SGPracticePage.clickLnkDiscreteForms();
		driver.manage().window().maximize();
		formsConfigPage.unpublishAllForms().editFormsWelcomePage(SitegenConstants.CALCULATED_PHQ9_FORM, newWelcomeMessage)
				.editFormsWelcomePage(SitegenConstants.CALCULATED_ADHD_FORM, newWelcomeMessage).publishForm(SitegenConstants.CALCULATED_PHQ9_FORM)
				.publishForm(SitegenConstants.CALCULATED_ADHD_FORM);

		FormWelcomePage previewWelcomePage = formsConfigPage.openCalculatedFormPreview();
		PortalUtil.setquestionnarieFrame(driver);
		assertEquals(newWelcomeMessage, previewWelcomePage.getMessageText());
	}

	/**
	 * @author: Petr H
	 * @Steps: Login to Patient Portal, click on Patient Forms, open calculated form, fill in the form, submit the form, check if PDF was generated Practices
	 *         configured on: DEV3
	 */
	@Test(groups = "OldPortalForms")
	public void testAllCalculatedFormsPortal1() throws Exception {
		testAllCalculatedForms(Utils.loginPortal1AndOpenFormsList(driver, true));
	}

	@Test(groups = "CalculatedForms")
	public void testAllCalculatedFormsPI() throws Exception {
		testAllCalculatedForms(Utils.loginPIAndOpenFormsList(driver, true));
	}

	private void testAllCalculatedForms(HealthFormListPage formsPage) throws Exception {
		String[] formNames = {SitegenConstants.CALCULATED_PHQ2_FORM, SitegenConstants.CALCULATED_PHQ9_FORM, SitegenConstants.CALCULATED_ADHD_FORM};
		log("For each calculated form:");
		for (String formName : formNames) {
			log("Step 1: Open Form named " + formName);
			try {
				formsPage.openDiscreteForm(formName);
			} catch (StaleElementReferenceException e) {
				formsPage.openDiscreteForm(formName);
			}

			log("Step 2: Fill in the form with all the required answers and submit.");
			FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
			CalculatedFormPage calculatedFormPage = welcomePage.initToFirstPage(CalculatedFormPage.class);
			assertTrue(calculatedFormPage.isPageLoaded());
			calculatedFormPage.fillFormRightmostAnswer();
			calculatedFormPage.clickSaveContinue();
			calculatedFormPage.submitForm();

			log("Step 3: Check if the PDF is downloadable.");
			Utils.checkIfPDFCanBeDownloaded(formName, driver);

			log("Step 4: Check if the date is correct");
			Utils.verifyFormsDatePatientPortal(formsPage, formName, driver);

		}
	}

	/**
	 * @author: Petr H
	 * @Steps: Login to Patient Portal, click on Patient Forms, open calculated form, try to save without any answer, try to save it with one answer missing and
	 *         finally saves it with all the correct answers Practices configured on: DEV3
	 */
	@Test(groups = "OldPortalForms")
	public void testCalculatedFormValidationPortal1() throws Exception {
		testCalculatedFormValidation(Utils.loginPortal1AndOpenFormsList(driver, true));
	}

	@Test(groups = "CalculatedForms")
	public void testCalculatedFormValidationPI() throws Exception {
		testCalculatedFormValidation(Utils.loginPIAndOpenFormsList(driver, true));
	}

	private void testCalculatedFormValidation(HealthFormListPage healthFormsList) throws Exception {
		log("Step 1: Open form: " + SitegenConstants.CALCULATED_PHQ9_FORM);
		healthFormsList.openDiscreteForm(SitegenConstants.CALCULATED_PHQ9_FORM);

		log("Step 2: Try to Save and continue without any answer.");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		CalculatedFormPage calculatedFormPage = welcomePage.initToFirstPage(CalculatedFormPage.class);
		assertTrue(calculatedFormPage.isPageLoaded());
		assertFalse(calculatedFormPage.isValidationErrorDisplayed());
		calculatedFormPage.clickSaveContinue();
		assertTrue(calculatedFormPage.isValidationErrorDisplayed());

		log("Step 2: Try to Save and continue with one answer missing.");
		calculatedFormPage.fillFormExcludingLastQuestion();
		assertTrue(calculatedFormPage.isValidationErrorDisplayed());

		log("Step 4: Fill all the answers and click Save and continue.");
		calculatedFormPage.fillFormLeftmostAnswer();
		calculatedFormPage.clickSaveContinueSamePage(3);
		calculatedFormPage.submitForm();
	}
}
