//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.forms.test;

import static com.intuit.ihg.product.forms.test.Utils.loginPI;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.medfusion.common.utils.EncryptionUtils;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CustomFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CustomFormPageSection;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.pojos.Patient;
import com.medfusion.product.object.maps.forms.page.FiltersFormPages;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class Portal2FormsAcceptanceTests extends FormsAcceptanceTestsUtils {
	private PropertyFileLoader testData;
	private Patient patient = null;

	// TODO common PI patient
	// @BeforeMethod(alwaysRun = true, onlyForGroups = "commonPIpatient")
	private void createCommonPatient() throws Exception {
		if (patient == null) {
			String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);
			String url = testData.getUrl();
			patient = Utils.createPatientPI(driver, username, url, testData);
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void setUpFormsTest() throws Exception {
		logStep("Getting Test Data");
		testData = new PropertyFileLoader();
	}

	@Test(groups = { "Forms" })
	public void testQuotationMarksInFormPI() throws Exception {
		testQuotationMarksInForm(Utils.loginPIAndOpenFormsList(driver, PracticeType.SECONDARY, testData));
	}

	/**
	 * @Author: Adam Warzel
	 * @Date: April-01-2014
	 * @UserStory: US7083 Tests if filling out a form generates a PDF, if link for
	 *             downloading the PDF appears in Patient Portal and if the link is
	 *             working and also whether corresponding CCD was generated. Then it
	 *             checks if the submitted date is accurate and if patient's DOB has
	 *             not been changed.
	 */
	@Test(groups = { "Forms" })
	public void testFormPdfCcdPI() throws Exception {
		createCommonPatient();

		logStep("Login");
		JalapenoHomePage homePage = loginPI(driver, PracticeType.SECONDARY, patient, testData);

		testFormPdfCcd(homePage.clickOnHealthForms(), testData.getProperty("get.ccd.url2"));
		driver.switchTo().defaultContent();
		homePage.clickOnLogout();
		logStep("Test if the DOB has not been changed");
		homePage = loginPI(driver, PracticeType.SECONDARY, patient.getUsername(), EncryptionUtils.decrypt(patient.getPassword()), testData);
		assertTrue(homePage.areMenuElementsPresent());
		JalapenoMyAccountProfilePage pMyAccountPage = homePage.clickOnAccount().clickOnEditMyAccount();
		assertEquals(pMyAccountPage.getDOByear(), patient.getDOBYear());
		assertEquals(pMyAccountPage.getDOBmonth(), patient.getDOBMonth());
		assertEquals(pMyAccountPage.getDOBday(), patient.getDOBDay());
	}

	@Test(groups = { "Forms" })
	public void testFormPracticePortalPI() throws Exception {
		HealthFormListPage healthFormListPage = Utils.loginPIAndOpenFormsList(driver, PracticeType.SECONDARY, testData);
		testFormPracticePortal(healthFormListPage, testData.getProperty("practice.url"),
				testData.getProperty("practice.username2"),
				EncryptionUtils.decrypt(testData.getProperty("practice.password2")));
	}

	@Test(groups = { "Forms" })
	public void testPartiallyCompletedFormPI() throws Exception {
		HealthFormListPage healthFormListPage = Utils.loginPIAndOpenFormsList(driver, PracticeType.SECONDARY, testData);
		testPartiallyCompletedForm(healthFormListPage, testData.getProperty("practice.url"),
				testData.getProperty("practice.username2"),
				EncryptionUtils.decrypt(testData.getProperty("practice.password2")));
	}

	/**
	 * User Story ID in Rally: US544 - TA30648 StepsToReproduce: logStep in to SG Go
	 * to Forms Config Unpublish all forms Delete all forms Create a new form and
	 * configure it Create a custom section and test saving it without name and
	 * questions Save the form Publish it Test viewing the form on Patient Portal
	 * Prerequisite for the test case to run Practice configured Practices
	 * configured on: DEV3, DEMO, PROD
	 */
	@Test(groups = { "Forms" })
	public void testDiscreteFormDeleteCreatePublishPI() throws Exception {
		String newFormName = SitegenConstants.DISCRETEFORMNAME;
		String welcomeMessage = createFormSG(testData.getProperty("sitegen.username1"),
				EncryptionUtils.decrypt(testData.getProperty("sitegen.password1")), newFormName);

		logStep("create patient and logStep in to PI");
		JalapenoHomePage home = Utils.createAndLoginPatientPI(driver, testData, PracticeType.PRIMARY);

		logStep("Click On Start Registration Button and verify welcome page of the previously created form");
		FiltersFormPages filtersFormPages = home.clickStartRegistrationButton();
		HealthFormListPage healthFormListPage = filtersFormPages.selectfilterforms();
		assertEquals(healthFormListPage.openDiscreteForm(newFormName).getMessageText(), welcomeMessage);
	}

	@Test(groups = { "Forms" })
	public void testFormPatientDashboardPI() throws Exception {
		createCommonPatient();

		logStep("Login");
		JalapenoHomePage homePage = loginPI(driver, PracticeType.SECONDARY, patient, testData);

		testFormPatientDashboard(homePage.clickOnHealthForms(), patient, testData.getProperty("practice.url"),
				testData.getProperty("practice.username2"),
				EncryptionUtils.decrypt(testData.getProperty("practice.password2")));
	}

	/**
	 * Fills and saves (does not submit) custom form. Tests displaying and
	 * interactivity of elements including FUPs.
	 */
	@Test(groups = { "Forms" })
	public void testCustomFormWithFUPsPI() throws Exception {
		// if the tests is failing, it might be because the test failed in the middle in
		// previous runs and the form is in different state than this tests expects
		// quick trouble shooting: run this test with new patient
		// if it is frequent issue, run this test with new patient every run
		testCustomFormWithFUPs(Utils.loginPIAndOpenFormsList(driver, PracticeType.SECONDARY, testData));
	}

	@Test(groups = { "Forms" })
	public void testEGQEnabledPI() throws Exception {
		logStep("create patient and login to PI");
		createCommonPatient();
		JalapenoHomePage home = loginPI(driver, PracticeType.SECONDARY, patient, testData);
		testEGQEnabled(home.clickOnHealthForms(), testData.getProperty("get.ccd.url2"));
		logStep("verify that value in my account has been changed based on form answer");
		driver.switchTo().defaultContent();
		home = PageFactory.initElements(driver, JalapenoHomePage.class);
		JalapenoMyAccountProfilePage pMyAccountPage = home.clickOnAccount().clickOnEditMyAccount();
		assertTrue(pMyAccountPage.getGender() == Patient.GenderExtended.DECLINED);
	}

	/**
	 * @UserStory: FORMS-346 Logins into sitegen. Creates a new custom form. Adds
	 *             and removes FUPs. Saves form. Reopens the form and checks that it
	 *             contains correct items.
	 */
	@Test(groups = { "Forms" })
	public void testSitegenFUPInteraction() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps().logInUserToSG(driver,
				testData.getProperty("sitegen.username2"),
				EncryptionUtils.decrypt(testData.getProperty("sitegen.password2")));

		logStep("Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		logStep("Unpublish and delete all forms and create a new one");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm(SitegenConstants.FORM_FUP_SG);

		logStep("Open created custom form");
		CustomFormPage customFormPage = pManageDiscreteForms.createAndOpenCustomForm(SitegenConstants.FORM_FUP_SG);
		customFormPage.clickOnSection(1);
		CustomFormPageSection section1 = customFormPage.getFirstSection();

		logStep("Create question and add 3 answers");
		section1.addQuestionItem(SitegenConstants.QUESTION_TYPE4, "base question", false, false);
		section1.addAnswers(1, Arrays.asList("answer1", "answer2", "answer3"));

		logStep("Add 2 FUPs to 1st answer of 1st question");
		section1.addQuestionFUP(1, 1, SitegenConstants.QUESTION_TYPE1, "followUpText", false, false);
		jse.executeScript("scroll(0, 0);");
		Thread.sleep(500);
		section1.addQuestionFUP(1, 1, SitegenConstants.QUESTION_TYPE3, "sndFollowUp", false, false);
		section1.addAnswersFUP(1, 2, Arrays.asList("sub-answer1", "sub-answer2"));

		logStep("Add FUP to 2nd answer of 1st question");
		section1.addQuestionFUP(1, 2, SitegenConstants.QUESTION_TYPE3, "thirdFollowUp", false, false);
		section1.addAnswersFUP(1, 3, Arrays.asList("sub-answer1", "sub-answer2", "sub-answer3"));

		logStep("Add FUP to 3rd answer of 1st question and delete it immediately");
		section1.addHeadingFUP(1, 3, "deleted heading");
		section1.removeFUP(1, 4);

		logStep("save and reopen form");
		customFormPage.saveForm();
		customFormPage.leaveFormPage();
		customFormPage = pManageDiscreteForms.openCustomForm(SitegenConstants.FORM_FUP_SG);
		customFormPage.clickOnSection(1);
		section1 = customFormPage.getFirstSection();

		logStep("test if form contains correct count of FUPs");
		assertEquals(section1.getCountOfFUPsOfAnswer(1, 1), 2);
		assertEquals(section1.getCountOfFUPsOfAnswer(1, 3), 0);

		logStep("test FUPs minimization");
		assertFalse(section1.areFUPsMinimized(1, 1));
		section1.toogleFUPs(1, 1);
		assertTrue(section1.areFUPsMinimized(1, 1));
		jse.executeScript("scroll(0, 250);");
		section1.toogleFUPs(1, 1);
		assertFalse(section1.areFUPsMinimized(1, 1));

		logStep("test consistency of FUPs");
		assertEquals("sndFollowUp", section1.getTitleOfFUPQuestion(1, 2));
		List<String> expectedAnswers = new ArrayList<String>();
		expectedAnswers.add("sub-answer1");
		expectedAnswers.add("sub-answer2");
		assertTrue(section1.getAnswersOfFUPQuestion(1, 2).containsAll(expectedAnswers));
	}
}
