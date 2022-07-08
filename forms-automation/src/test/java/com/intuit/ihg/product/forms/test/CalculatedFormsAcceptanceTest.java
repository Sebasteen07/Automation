//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.forms.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.CalculatedFormPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.FormWelcomePage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

import static com.intuit.ihg.product.forms.test.Utils.*;

public class CalculatedFormsAcceptanceTest extends BaseTestNGWebDriver {

	private PropertyFileLoader testData;

	@BeforeMethod(alwaysRun = true)
	public void setUpCalculatedFormsTest() throws Exception {
		logStep("Getting Test Data");
		testData = new PropertyFileLoader();
	}

	/**
	 * Tect Case in TestLink: MF-1265
	 *
	 * @throws Exception when exception occurs
	 * @author phajek
	 * @Date: 13/02/2015 StepsToReproduce: logStep in to SG as SU Go to Forms Config
	 *        Unpublish all forms Delete all forms Search and add a new Calculated
	 *        form Test if it is displayed in Calculated Forms directory Delete the
	 *        Form Test if it is displayed in Calculated Forms directory
	 *        Prerequisite for the test case to run Practices configured on: DEV3,
	 *        MANUAL login to the SG as superuser
	 */
	@Test
	public void testCalculatedFormAddRemove() throws Exception {
		logStep("login to SG as superuser - THIS REQUIRES MANUAL INPUT");
		SiteGenHomePage sHomePage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"))
				.clickOnLoginAsInternalEmployee();
		logStep("navigate to SiteGen PracticeHomePage");
		String automationPracticeID = String
				.valueOf(Utils.getPracticeIDFromPIUrl(testData.getProperty("portal2.url2")));
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = sHomePage.searchPracticeFromSGAdmin(automationPracticeID);

		String parentHandle = driver.getWindowHandle();
		logStep("Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		logStep("Unpublish and delete all forms and add calculated form");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm(SitegenConstants.CALCULATED_PHQ2_FORM);
		assertTrue(pManageDiscreteForms.addCalculatedForm(SitegenConstants.CALCULATED_PHQ2_FORM));

		logStep("Check if the added form is no longer in the Calculated Form Directory ");
		assertFalse(pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATED_PHQ2_FORM));

		logStep("Delete all Forms and check if the Calculated Form is back in Directory");
		pManageDiscreteForms.initializePracticeForNewForm(SitegenConstants.CALCULATED_PHQ2_FORM);
		assertTrue(pManageDiscreteForms.searchCalculatedForm(SitegenConstants.CALCULATED_PHQ2_FORM));

		logStep("Close the window and logout from SiteGenerator");
		// Switching back to original window using previously saved handle descriptor
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();

	}

	/**
	 * @author Adam W Steps: Login to Site Generator, click on Patient Forms, open
	 *         calculated form change welcome screen, save the form, exit, publish
	 *         it, test changes in preview do the same for a second form
	 */
	@Test(groups = "CalculatedForms")
	public void testCalculatedFormSGEdit() throws Exception {
		String newWelcomeMessage = "Welcome " + IHGUtil.createRandomNumber();
		SiteGenPracticeHomePage SGPracticePage = new SiteGenSteps().logInUserToSG(driver,
				testData.getProperty("sitegen.username1"), testData.getProperty("sitegen.password1"));

		DiscreteFormsList formsConfigPage = SGPracticePage.clickLnkDiscreteForms();
		driver.manage().window().maximize();
		formsConfigPage.unpublishForms(SitegenConstants.CALCULATED_ADHD_FORM)
				.editFormsWelcomePage(SitegenConstants.CALCULATED_ADHD_FORM, newWelcomeMessage)
				.publishForm(SitegenConstants.CALCULATED_ADHD_FORM);
		FormWelcomePage previewWelcomePage = formsConfigPage.openCalculatedFormPreview();
		assertEquals(newWelcomeMessage, previewWelcomePage.getMessageText());
	}

	@Test(groups = "CalculatedForms")
	public void testCalculatedFormPI() throws Exception {
		logStep("Create patient and logStep to Portal 2");
		JalapenoHomePage homePage = createAndLoginPatientPI(driver, testData, PracticeType.SECONDARY);
		testCalculatedForm(homePage.clickOnHealthForms());
	}

	private void testCalculatedForm(HealthFormListPage formsPage) throws Exception {
		logStep("Open Form named " + SitegenConstants.CALCULATED_PHQ9_FORM);
		formsPage.openDiscreteForm(SitegenConstants.CALCULATED_PHQ9_FORM);

		logStep("Fill in the form with all the required answers and submit.");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		CalculatedFormPage calculatedFormPage = welcomePage.initToFirstPage(CalculatedFormPage.class);
		calculatedFormPage.fillFormRightmostAnswer();
		calculatedFormPage.clickSaveContinue();
		calculatedFormPage.submitForm();

		logStep("Check if the PDF is downloadable.");
		Utils.checkIfPDFCanBeDownloaded(SitegenConstants.CALCULATED_PHQ9_FORM, driver);

		logStep("Check if the date is correct");
		Utils.verifyFormsDatePatientPortal(formsPage, SitegenConstants.CALCULATED_PHQ9_FORM, driver);
	}

	@Test(groups = "CalculatedForms")
	public void testCalculatedFormValidationPI() throws Exception {
		logStep("Create patient and logStep to Portal 2");
		JalapenoHomePage homePage = createAndLoginPatientPI(driver, testData, PracticeType.SECONDARY);
		testCalculatedFormValidation(homePage.clickOnHealthForms());
	}

	private void testCalculatedFormValidation(HealthFormListPage healthFormsList) throws Exception {
		logStep("Open form: " + SitegenConstants.CALCULATED_PHQ9_FORM);
		healthFormsList.openDiscreteForm(SitegenConstants.CALCULATED_PHQ9_FORM);

		logStep("Try to Save and continue without any answer.");
		FormWelcomePage welcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		CalculatedFormPage calculatedFormPage = welcomePage.initToFirstPage(CalculatedFormPage.class);
		assertFalse(calculatedFormPage.isValidationErrorDisplayed());
		calculatedFormPage.clickSaveContinue();
		Thread.sleep(3000);
		assertTrue(calculatedFormPage.isValidationErrorDisplayed());

		logStep("Try to Save and continue with one answer missing.");
		calculatedFormPage.fillFormExcludingLastQuestion();
		assertTrue(calculatedFormPage.isValidationErrorDisplayed());

		logStep("Fill all the answers and click Save and continue.");
		calculatedFormPage.fillFormLeftmostAnswer();
		calculatedFormPage.clickSaveContinueSamePage(3);
		calculatedFormPage.submitForm();
	}
}
