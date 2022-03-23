//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.forms.test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.google.common.base.Charsets;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.BasicInformationAboutYouPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.WelcomeScreenPage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormBasicInfoPage;

public class FormsAcceptanceTests extends BaseTestNGWebDriver {

		private PropertyFileLoader testData;

		@BeforeMethod(alwaysRun = true)
		public void setUpFormsTest() throws Exception {
				log("Getting Test Data");
				testData = new PropertyFileLoader();
		}

		@Test(groups = {"smokeTest"})
		public void formsConfigSmokeTest() throws Exception {
				SiteGenSteps sgSteps = new SiteGenSteps();
				DiscreteFormsList formsPage =
						sgSteps.logInUserToSG(driver, testData.getProperty("sitegen.username1"), testData.getProperty("sitegen.password1")).clickLnkDiscreteForms();
				assertTrue(formsPage.isPageLoaded());
		}

		@Test
		public void testFormExportImport() throws Exception {
				log("step 1: login to SG as superuser");
				String automationPracticeID = String.valueOf(Utils.getPracticeIDFromPIUrl(testData.getProperty("portal2.url1")));
				SiteGenHomePage sHomePage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url")).clickOnLoginAsInternalEmployee();
				// now you have to LOG IN MANUALLY AS SUPERUSER, the test will continue after that
				log("step 2: navigate to SiteGen PracticeHomePage, practice with ID: " + automationPracticeID);
				SiteGenPracticeHomePage pSiteGenPracticeHomePage = sHomePage.searchPracticeFromSGAdmin(automationPracticeID);
				String parentHandle = driver.getWindowHandle();
				log("step 3: Click on Patient Forms");
				DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
				assertTrue(pManageDiscreteForms.isPageLoaded());
				log("step 4: Cleanup unpublished forms");
				pManageDiscreteForms.deleteUnpublishedForms(SitegenConstants.FORM_EXPORT_IMPORT);
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
		public void testEGQEnablingSG() throws Exception {
				log("step 1: login to SG as superuser");
				String automationPracticeID = String.valueOf(Utils.getAutomationPracticeID(PracticeType.SECONDARY));
				SiteGenHomePage sHomePage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url")).clickOnLoginAsInternalEmployee();
				// now you have to LOG IN MANUALLY AS SUPERUSER, the test will continue after that
				log("step 2: navigate to SiteGen PracticeHomePage - practice with id: " + automationPracticeID);
				SiteGenPracticeHomePage pSiteGenPracticeHomePage = sHomePage.searchPracticeFromSGAdmin(automationPracticeID);
				log("step 3: enable EGQ");
				pSiteGenPracticeHomePage.openPracticeInfo().enableGenderQuestions().returnToPracticeHomePage();
				String homePageSGWindow = driver.getWindowHandle();
				log("step 4: open EGQ form");
				DiscreteFormsList formsList = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
				String formsSGListWindow = driver.getWindowHandle();
				WelcomeScreenPage welcomePage = formsList.openDiscreteForm(SitegenConstants.FORM_EGQ_NAME);
				log("step 5: test behaving when EGQ are enabled - SG");
				testEGQSitegenEnabledFlow(formsList, welcomePage);
				log("step 6: test behaving when EGQ are enabled - PI");
				driver.switchTo().window(homePageSGWindow);
				pSiteGenPracticeHomePage.clickLnkDiscreteForms();
				HealthFormListPage formsListPI = testEGQEnabledPIFlow(driver);
				String PIwindow = driver.getWindowHandle();
				log("step 7: disable EGQ");
				driver.switchTo().window(homePageSGWindow);
				pSiteGenPracticeHomePage.openPracticeInfo().disableGenderQuestions().returnToPracticeHomePage();
				log("step 8: test behaving when EGQ are disabled - SG");
				driver.switchTo().window(formsSGListWindow);
				testEGQSitegenDisabledFlow(formsList);
				log("step 9: test behaving when EGQ are disabled - PI");
				driver.switchTo().window(PIwindow);
				IHGUtil.setFrame(driver, "iframe");
				testEGQDisabledPIFlow(formsListPI);
		}

		private void testEGQSitegenEnabledFlow(DiscreteFormsList formsList, WelcomeScreenPage welcomePage) throws Exception {
				IHGUtil.PrintMethodName();
				BasicInformationAboutYouPage basicInfoPage = welcomePage.clicklnkBasicInfoAboutYourPage();
				log("verify egq questions are displayed");
				assertTrue(basicInfoPage.areEGQQuestionsDisplayed());
				log("verify sex question always required");
				assertTrue(basicInfoPage.isQuestionRequired(BasicInformationAboutYouPage.EGQ_SEX_QUESTION_LABEL));
				basicInfoPage.clickQuestionRequiredAsterisk(BasicInformationAboutYouPage.EGQ_SEX_QUESTION_LABEL);
				assertTrue(basicInfoPage.isQuestionRequired(BasicInformationAboutYouPage.EGQ_SEX_QUESTION_LABEL));
				log("add egq questions");
				basicInfoPage.addQuestionToForm("What is your sexual orientation?");
				basicInfoPage.addQuestionToForm("What is your current gender identity?");
				basicInfoPage.saveOpenedForm();
				basicInfoPage.clickBackToTheList();
		}

		private HealthFormListPage testEGQEnabledPIFlow(WebDriver driver) throws Exception {
				log("create patient, login to PI and open form");
				HealthFormListPage formsList = Utils.createAndLoginPatientPI(driver, testData, PracticeType.SECONDARY).clickOnHealthForms();
				FormBasicInfoPage basicInfo = formsList.openDiscreteForm(SitegenConstants.FORM_EGQ_NAME).clickSaveContinue(FormBasicInfoPage.class);
				log("check 'decline to answer' and submit form");
				basicInfo.setSex("Declined to answer");
				basicInfo.clickSaveContinue();
				basicInfo.submitForm();
				return formsList;
		}

		private void testEGQSitegenDisabledFlow(DiscreteFormsList formsList) throws Exception {
				IHGUtil.PrintMethodName();
				BasicInformationAboutYouPage basicInfoPage = formsList.openDiscreteForm(SitegenConstants.FORM_EGQ_NAME).clicklnkBasicInfoAboutYourPage();
				log("verify that EGQ questions are not displayed");
				assertFalse(basicInfoPage.areEGQQuestionsDisplayed());
				log("verify sex question required/optional");
				boolean atFirst = basicInfoPage.isQuestionRequired("Sex");
				basicInfoPage.clickQuestionRequiredAsterisk("Sex");
				boolean afterClick = basicInfoPage.isQuestionRequired("Sex");
				assertEquals(atFirst, !afterClick);
				log("close form");
				basicInfoPage.saveOpenedForm();
				basicInfoPage.clickBackToTheList();
		}

		private void testEGQDisabledPIFlow(HealthFormListPage formsList) throws Exception {
				log("verify question label");
				FormBasicInfoPage basicInfo = formsList.openDiscreteForm(SitegenConstants.FORM_EGQ_NAME).initToFirstPage(FormBasicInfoPage.class);
				assertEquals(basicInfo.getGenderQuestionLabel(), "Sex");
				log("verify that answer is prefilled by information from my account (declined -> choose ...)");
				assertEquals(basicInfo.getSelectedGender(), "Choose...");
				log("verify that answer is not required");
				basicInfo.setSex("Choose...");
				basicInfo.clickSaveContinue();
				basicInfo.submitForm();
		}

		private String stripVariablesFromExportedForm(String s) {
				return s.replaceAll("\"\\w+Date\":\\d+", "").replaceAll("\"formId\":\"[^\"]+\"", "").replaceAll("\"copiedFrom\":\"[^\"]+\"", "")
						.replaceAll("\"\\w+racticeId\":\\d+", "").replaceAll("\"environment\":\"\\w+\"", "");
		}
}
