package com.intuit.ihg.product.forms.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.AddQuestionsToCategoryPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomForms;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormAddCategoriesPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormLayoutPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormPreviewPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.ManageYourFormsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.OldCustomFormPages;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPatientFormsPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPatientFormsResultPage;

public class OldCustomFormsAcceptanceTests extends BaseTestNGWebDriver {
    
    PropertyFileLoader testData;
    
    @Override
    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        super.setUp();

        log(this.getClass().getName());
        log("Execution Environment: " + IHGUtil.getEnvironmentType());
        log("Execution Browser: " + TestConfig.getBrowserType());

        log("Getting Test Data");
        testData = new PropertyFileLoader();        
    }

	private final int TRANSITION_WAIT_TIME_MS = 5000;
	/**
	 * @Author:-Shanthala : Modified :bbinisha : Modified-Modified: Prokop Rehacek
	 * @Date:- 07-03-2013
	 * @User Story ID in Rally : US6152 and US6151 and US7626
	 * @StepsToReproduce: Go to siteGen Enter the credentials Search for the practice Click on Custom Form Click on Create Custom Form Publish Custom Form and
	 *                    check for preview Unpublish Custom Form, check for Preview and delete unpublished custom form
	 *
	 *                    === Prerequisite for the test case to run========= Nurse Named :-
	 *
	 *                    ====Valid Custom Form details required. Test data would be updated after getting proper test data
	 *                    =============================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"OldCustomForms"})
	public void testCustomFormPublished() throws Exception {
		Utils.logTestEnvironmentInfo("testCustomFormPublished");
		log("step 1: Get Data from Excel ##########");		
		SiteGenSteps.logSGLoginInfo(testData);
		String customFormTitle = SitegenConstants.FORMTITLE + IHGUtil.createRandomNumber();
		log("new form name: " + customFormTitle);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegenUrl"));
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("sitegenUsername1"), testData.getProperty("sitegenPassword1"));
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage to be loaded, but it was not.");

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage to be loaded, but it was not.");

		log("step 4: Click on Custom Forms");
		String winHandleSiteGen = driver.getWindowHandle();
		CreateCustomForms pManageCustomForms = pSiteGenPracticeHomePage.clickCustomForms();
		String winHandleCustomBuilder = driver.getWindowHandle();

		log("step 5: Clear forms - unpublish and delete forms created by this method during previous runs");
		ManageYourFormsPage plinkOnManageCustomForm = pManageCustomForms.clicklnkManageCustomForm();
		plinkOnManageCustomForm.unpublishFormsNamedLike(SitegenConstants.FORMTITLE);
		plinkOnManageCustomForm.deleteFormsNamedLike(SitegenConstants.FORMTITLE);
		log("step 6: Click on Create Custom Form");
		CreateCustomFormPage plinkOnCustomForm = pManageCustomForms.clicklnkCreateCustomForm();

		log("step 7: Enter Custom Form details");
		assertTrue(plinkOnCustomForm.isSearchPageLoaded(),
				"Expected the SiteGen Create a Custom Form page to be loaded to create a new custom form with details, but it was not.");
		CustomFormAddCategoriesPage pCustomFormAddCategories =
				plinkOnCustomForm.enterCustomFormDetails(SitegenConstants.FORMTYPE, customFormTitle, SitegenConstants.FORMINSTRUCTIONS, SitegenConstants.FORMMESSAGE);

		log("step 8: Build a Custom Form");
		assertTrue(pCustomFormAddCategories.isSearchPageLoaded(),
				"Expected the SiteGen Build a Custom Form page to be loaded to add categories into the custom form, but it was not.");
		AddQuestionsToCategoryPage pAddCAtegories = pCustomFormAddCategories.addCategoriesDetails(SitegenConstants.FORMCATEGORY);

		log("step 9A: Add Question1 to category 1");
		assertTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add questions to the category page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY, TRANSITION_WAIT_TIME_MS), "Questions are not getting added to expected Category");
		assertTrue(pAddCAtegories.addQuestion1ToCategory(SitegenConstants.FORMQUESTION1), "Custom Form question1 and answerset1 did not updated successfully.");
		pAddCAtegories.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET1);
		pAddCAtegories.saveCategoryQuestions();

		CustomFormAddCategoriesPage pCustomFormAddCategories2 = pAddCAtegories.clickCustomFormAddCategoriesPage();
		AddQuestionsToCategoryPage pAddCAtegories2 = pCustomFormAddCategories2.addCategoriesDetails(SitegenConstants.FORMCATEGORY2);

		log("step 9B: Add Question2 to category 2");
		assertTrue(pAddCAtegories2.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2, TRANSITION_WAIT_TIME_MS), "Questions are not getting added to expected Category");
		assertTrue(pAddCAtegories2.addQuestion1ToCategory(SitegenConstants.FORMQUESTION2), "Custom Form question2 and answerset2 did not updated successfully.");
		pAddCAtegories2.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET2);
		pAddCAtegories2.saveCategoryQuestions();

		CustomFormAddCategoriesPage pCustomFormAddCategories3 = pAddCAtegories2.clickCustomFormAddCategoriesPage();
		AddQuestionsToCategoryPage pAddCAtegories3 = pCustomFormAddCategories3.addCategoriesDetails(SitegenConstants.FORMCATEGORY3);

		log("step 9C: Add Question3 to category 3");
		assertTrue(pAddCAtegories3.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3, TRANSITION_WAIT_TIME_MS), "Questions are not getting added to expected Category");
		assertTrue(pAddCAtegories3.addQuestion1ToCategory(SitegenConstants.FORMQUESTION3), "Custom Form question3 and answerset3 did not updated successfully.");
		pAddCAtegories3.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET3);

		log("step 9D: Save added questions to category");
		assertTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3, TRANSITION_WAIT_TIME_MS), "Questions are not getting added to expected Category");
		pAddCAtegories.saveCategoryQuestions();

		CustomFormLayoutPage pAddQuestionsToCategory = pAddCAtegories.clickCustomFormLayoutPage();

		log("step 10: Set Custom Form Layout");
		assertTrue(pAddQuestionsToCategory.isSearchPageLoaded(), "Expected the SiteGen form Layout page to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY, TRANSITION_WAIT_TIME_MS), "Form Layout is not set for Expected Category");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2, TRANSITION_WAIT_TIME_MS), "Form Layout is not set for Expected Category");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3), "Form Layout is not set for Expected Category");
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE, SitegenConstants.FORMCATEGORY);
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE2, SitegenConstants.FORMCATEGORY2);
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE3, SitegenConstants.FORMCATEGORY3);

		CustomFormPreviewPage pCustomFormPreview = pAddQuestionsToCategory.saveFormLayout();

		pCustomFormPreview.waitForPublishLink();

		log("step 11: Custom Form Preview Page to click on publish");
		assertTrue(pCustomFormPreview.isSearchPageLoaded(), "Expected the SiteGen create custom form page preview with publish link to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2), "Form Layout is not set for Expected Category");
		assertTrue(verifyTextPresent(driver, customFormTitle), "Vewing custom form is not expected custom form");

		assertEquals(verifyTextPresent(driver, "First Name"), true, "Demographic information is not present in form preview");
		pCustomFormPreview.clickOnPage(2);
		assertEquals(verifyTextPresent(driver, "Vital"), true, "Vital information is not present in form preview");
		pCustomFormPreview.clickOnPage(3);
		assertEquals(verifyTextPresent(driver, "Insurance Type"), true, "Insurance Type is not present in form preview");
		ManageYourFormsPage pManageForm = pCustomFormPreview.clickOnPublishLink();

		pCustomFormPreview.waitForUnpublishLink();

		log("step 12: Manage your forms -Check custom Form published successfully");
		assertEquals(pManageForm.checkForPublishedPage(customFormTitle), true,
				"Custom Form did not published successfully and not present in published forms table");
		driver.switchTo().window(winHandleSiteGen); // Setting data

		String winHandlePatientPortal = driver.getWindowHandle();
		log("step 12.1:Login to patient portal");
		log("URL: " + pSiteGenPracticeHomePage.getPatientPortalUrl());
		HealthFormListPage pHealthForm = Utils.loginPIAndOpenFormsList(driver, PracticeType.PRIMARY, testData);

		log("step 12.3: Open Form");
		OldCustomFormPages oldCustomForm = pHealthForm.openOldCustomForm(customFormTitle);
		log("step 12.4: Fill form");
		assertEquals(verifyTextPresent(driver, "First Name", TRANSITION_WAIT_TIME_MS), true, "Demographic information is not present in form on Portal");
		oldCustomForm.clickNext();
		assertEquals(verifyTextPresent(driver, "Vital", TRANSITION_WAIT_TIME_MS), true, "Vital information is not present in form on Portal");
		log("Step 12.5: Fill Vitals");
		oldCustomForm.fillVitals();
		oldCustomForm.clickNext();
		assertEquals(verifyTextPresent(driver, "Insurance Type", TRANSITION_WAIT_TIME_MS), true, "Insurance Type is not present in form on Portal");
		log("Step 12.6: exit non completed form");
		IHGUtil.setDefaultFrame(driver);
		pHealthForm.clickOnHealthForms();
		IHGUtil.setFrame(driver, "iframe");
		assertEquals(pHealthForm.getInfoAboutFormCompletion(customFormTitle), "2/3", "Partialy completed form not saved correctly");
		driver.switchTo().window(winHandleCustomBuilder);

		log("step 13a: Delete 2 pages");
		pManageForm.clickOnPublishedFormPreviewLink(customFormTitle);
		pAddCAtegories.clickCustomFormLayoutPage();
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE0, SitegenConstants.FORMCATEGORY);
		pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE0, SitegenConstants.FORMCATEGORY3);
		pAddQuestionsToCategory.categorySequence();

		pAddQuestionsToCategory.saveFormLayout();
		driver.switchTo().window(winHandlePatientPortal);

		pHealthForm.clickOnHealthForms();
		assertEquals(pHealthForm.getInfoAboutFormCompletion(customFormTitle), "0/1", "Partialy completed form after deleted pages not displayed correctly");
		pHealthForm.openOldCustomForm(customFormTitle);
		assertEquals(verifyTextPresent(driver, "First Name", TRANSITION_WAIT_TIME_MS), true, "Demographic information is not present in form on Portal");

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

	/**
	 * @Author: bkrishnankutty
	 * @Date: 05/4/2013
	 * @StepsToReproduce: Login to Patient Portal Click on CustomForm Fill CustomForm and submit Download InsuranceHealthForm pdf-- validate HTTP Status Code
	 *                    Logout from Patient Portal Login to Practice Portal On Practice Portal Home page Click on CustomFormTab Search for PatientForms With
	 *                    Status Open View and Validate the Result ============================================== ===============
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(groups = {"OldCustomForms"})
	public void testCustomFormsPI() throws Exception {
		testCustomForms(Utils.loginPIAndOpenFormsList(driver, PracticeType.PRIMARY, testData));
	}
	// PROD uses Portal 1 template on which Selenium doesn't see Health Form link after submitting custom form
	// @Test(groups = "OldPortalForms")
	// public void testCustomFormsPortal1() throws Exception {
	// testCustomForms(Utils.loginPortal1AndOpenFormsList(driver, true));
	// }

	private void testCustomForms(HealthFormListPage pHealthForm) throws Exception {
		log("step 1: Open and fill CustomForm");
		OldCustomFormPages oldCustomForm = pHealthForm.openOldCustomForm("Ivan Insurance Health Form ( Testing)");
		oldCustomForm.fillInsuranceHealthForm();
		assertFalse(driver.getPageSource().contains("Female question"));
		oldCustomForm.submitInsuranceHealthForm();
		Utils.checkIfPDFCanBeDownloaded("Ivan Insurance Health Form ( Testing)", driver);
		log("step 2: Go back to forms list (refresh page)");
		driver.navigate().refresh();
		Utils.verifyFormsDatePatientPortal(pHealthForm, "Ivan Insurance Health Form ( Testing)", driver);
		log("step 3: Login to Practice Portal");
		// Load up practice test data
		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getProperty("practiceUrl"));
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("practiceUsername1"), testData.getProperty("practicePassword1"));

		log("step 4: On Practice Portal Home page Click CustomFormTab");
		SearchPatientFormsPage pSearchPatientFormsPage = practiceHome.clickCustomFormTab();
		assertTrue(pSearchPatientFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + "failed to load.");

		log("step 5: Search for PatientForms With Status Open");		
		SearchPatientFormsResultPage pSearchPatientFormsResultPage = pSearchPatientFormsPage.SearchPatientFormsWithOpenStatus(testData.getProperty("patientFirstname"),
		        testData.getProperty("patientLastname"), testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());

		log("step 6: View the Result");
		pSearchPatientFormsResultPage.clickViewLink();

		log("step 7: Verify the Result");
		String actualPatientName = oldCustomForm.Patientname.getText().trim();

		log("Displayed patient name is :" + actualPatientName);
		assertEquals(oldCustomForm.Patientname.getText().trim().contains("Patient Name : Ihgqa"), true);
		assertTrue(verifyTextPresent(driver, "Patient DOB : 01/11/1987"));
	}

}
