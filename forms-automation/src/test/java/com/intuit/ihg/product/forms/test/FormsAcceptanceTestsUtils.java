//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.forms.test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.ccd.CCDTest;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.WelcomeScreenPage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.pojos.Patient;
import com.medfusion.product.forms.pojo.Question;
import com.medfusion.product.forms.pojo.SelectQuestion;
import com.medfusion.product.forms.pojo.SelectableAnswer;
import com.medfusion.product.forms.pojo.TextQuestion;
import com.medfusion.product.forms.services.QuestionsService;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.NewCustomFormPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.custom_pages.SpecialCharFormFirstPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.custom_pages.SpecialCharFormSecondPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.*;
import com.medfusion.product.object.maps.forms.page.questionnaires.supplemental_pages.CurrentSymptomsSupplementalPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.supplemental_pages.IllnessesSupplementalPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPartiallyFilledPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPatientFormsPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPatientFormsResultPage;
import com.medfusion.product.object.maps.practice.page.customform.ViewPatientFormPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class FormsAcceptanceTestsUtils extends BaseTestNGWebDriver {
		void testQuotationMarksInForm(HealthFormListPage formsPage) throws Exception {
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

		void testFormPdfCcd(HealthFormListPage formsPage, String ccdUrl) throws Exception {
				long timestamp = System.currentTimeMillis() / 1000L;
				String xml;
				// easy bruising is mapped to following term in Forms Configurator in SiteGen
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
				xml = CCDTest.getFormCCD(timestamp, ccdUrl);
				assertTrue(xml.contains(easyBruisingString), "Symptom not found in the CCD, printing the CCD:\n" + xml);

				log("Step 5: Test if the submission date is correct");
				formsPage.clickOnHealthForms();
				Utils.verifyFormsDatePatientPortal(formsPage, SitegenConstants.PDF_CCD_FORM, driver);
		}

		void testFormPracticePortal(HealthFormListPage formsPage, String practicePortalUrl, String practicePortalUsername, String practicePortalPassword)
				throws Exception {
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
				SearchPatientFormsPage pSearchPatientFormsPage = getPracticePortalSearchFormsPage(practicePortalUrl, practicePortalUsername, practicePortalPassword);

				log("step 3: Search for PatientForms With Status Open");
				SearchPatientFormsResultPage pSearchPatientFormsResultPage = pSearchPatientFormsPage.SearchDiscreteFormsWithOpenStatus(SitegenConstants.PRACTICE_FORM);

				log("step 4: View the Result");
				ViewPatientFormPage pViewPatientFormPage = pSearchPatientFormsResultPage.clickViewLink();
				verifyFormsDateAndPDF(pViewPatientFormPage);
		}

		void testPartiallyCompletedForm(HealthFormListPage formsPage, String practicePortalUrl, String practicePortalUsername, String practicePortalPassword)
				throws Exception {
				log("step 1: Open form: " + SitegenConstants.PRACTICE_FORM);
				FormBasicInfoPage firstPage = formsPage.openDiscreteForm(SitegenConstants.PRACTICE_FORM).initToFirstPage(FormBasicInfoPage.class);

				log("Step 2: Fill out the form");
				firstPage.clickSaveAndFinishAnotherTime();
				formsPage.logout();

				log("Step 3: Go to Practice Portal forms tab");
				SearchPatientFormsPage pSearchPatientFormsPage = getPracticePortalSearchFormsPage(practicePortalUrl, practicePortalUsername, practicePortalPassword);
				SearchPartiallyFilledPage searchPage = pSearchPatientFormsPage.getPartiallyFilledSearch();
				searchPage.clickSearch();
				ViewPatientFormPage resultPage = searchPage.selectPatientsFirstForm();
				verifyFormsDateAndPDF(resultPage);
		}

		void testFormPatientDashboard(HealthFormListPage formsPage, Patient patient, String practicePortalUrl, String practicePortalUsername,
				String practicePortalPassword) throws Exception {
				testFormPatientDashboard(formsPage, patient.getEmail(), patient.getFirstName(), patient.getLastName(), practicePortalUrl, practicePortalUsername,
						practicePortalPassword);
		}

		void testFormPatientDashboard(HealthFormListPage formsPage, String email, String firstname, String lastname, String practicePortallUrl,
				String practicePortalUsername, String practicePortalPassword) throws Exception {

				log("step 1: Open form :" + SitegenConstants.PDF_CCD_FORM);
				formsPage.openDiscreteForm(SitegenConstants.PDF_CCD_FORM).initToFirstPage(FormBasicInfoPage.class);

				log("Step 2: Fill out the form");
				fillOutputForm("Written by formPatientDashboardTest");

				log("Step 3: Logout");
				formsPage.logout();

				log("Step 4: Log in to Practice Portal");
				PracticeHomePage pHomePage = loginToPracticePortal(practicePortallUrl, practicePortalUsername, practicePortalPassword);

				log("Step 5: Search for previously created patient");
				PatientSearchPage pSearchPage = pHomePage.clickPatientSearchLink();
				pSearchPage.searchForPatientInPatientSearch(email);

				log("Step 6: Get into patient dashboard");
				PatientDashboardPage pDashboardPage = pSearchPage.clickOnPatient(firstname, lastname);

				log("Step 7: Verify if there's submitted form on patient dashboard");
				assertTrue(pDashboardPage.verifySubmittedForm(SitegenConstants.PDF_CCD_FORM), "Submitted form was not found on Patient Dashboard");

				log("Step 8: Open forms detail and check donwload link");
				pDashboardPage.openFormDetails(SitegenConstants.PDF_CCD_FORM);
				Utils.checkIfPDFCanBeDownloaded("View as PDF", driver);
		}

		void testCustomFormWithFUPs(HealthFormListPage healthFormPage) throws Exception {
				log("step 1: Open PI-testBeforeSubmit form");
				NewCustomFormPage customFormPage = healthFormPage.openDiscreteForm("PI-testBeforeSubmit").initToFirstPage(NewCustomFormPage.class);
				log("step 2: Clear all answers of 1st section");
				customFormPage.clearAllInputs();
				log("step 3: Check that FUPs are hidden after clear");
				List<WebElement> initiallyVisibleQuestionsFirstSection = customFormPage.getVisibleQuestions();
				assertEquals(initiallyVisibleQuestionsFirstSection.size(), 3);
				log("step 4: Test interactivity");
				log("step 4.1: Test top lvl required flag");
				customFormPage = customFormPage.clickSaveContinue(NewCustomFormPage.class);
				assertTrue(customFormPage.isInputErrorMessageDisplayed());
				customFormPage.fillMultiLineAnswer(initiallyVisibleQuestionsFirstSection.get(0), "1st multiline answer");
				log("step 4.2: Test showing of FUPs");
				customFormPage.fillSingleLineAnswer(initiallyVisibleQuestionsFirstSection.get(1), "1st singleline answer");
				customFormPage.selectAnswers(initiallyVisibleQuestionsFirstSection.get(2), Arrays.asList(1, 2));
				List<WebElement> visibleQuestionsFirstSection = customFormPage.getVisibleQuestions();
				assertEquals(visibleQuestionsFirstSection.size(), 7);
				log("step 4.3: Test FUP required flag");
				customFormPage = customFormPage.clickSaveContinue(NewCustomFormPage.class);
				assertTrue(customFormPage.isInputErrorMessageDisplayed());
				log("step 4.4: Test answering FUPs");
				customFormPage.selectAnswer(visibleQuestionsFirstSection.get(3), 1);
				customFormPage.selectAnswer(visibleQuestionsFirstSection.get(4), 1);
				customFormPage.selectAnswers(visibleQuestionsFirstSection.get(5), Arrays.asList(1, 2));
				customFormPage = customFormPage.clickSaveContinue(NewCustomFormPage.class);
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
				customFormPage = customFormPage.clickSaveContinue(NewCustomFormPage.class);
				assertTrue(customFormPage.isInputErrorMessageDisplayed());
				customFormPage.consentAllVisibleStatements("InMyName");
				customFormPage = customFormPage.clickSaveContinue(NewCustomFormPage.class);
				log("step 4.8: Test section update button");
				customFormPage.selectSectionToUpdate(2, NewCustomFormPage.class);
				customFormPage.selectAnswer(visibleQuestionsSecondSection.get(1), 1);
				log("step 5: Save the form");
				customFormPage.goBack(NewCustomFormPage.class);
				customFormPage.clickSaveAndFinishAnotherTime();
				log("step 6: Open the form and checks saved values");
				IHGUtil.setFrame(driver, "iframe");
				customFormPage = healthFormPage.openDiscreteForm("PI-testBeforeSubmit").initToFirstPage(NewCustomFormPage.class);
				assertEquals(QuestionsService.getSetOfVisibleQuestions(driver), getExpectedQuestionsForFirstSection());
				customFormPage = customFormPage.clickSaveContinue(NewCustomFormPage.class);
				assertEquals(QuestionsService.getSetOfVisibleQuestions(driver), getExpectedQuestionsForSecondSection());
		}

		void testEGQEnabled(HealthFormListPage formsList, String ccdUrl) throws Exception {
				long timestamp = System.currentTimeMillis() / 1000L;
				log("verify question labels");
				FormBasicInfoPage basicInfo = formsList.openDiscreteForm(SitegenConstants.FORM_EGQ_NAME).initToFirstPage(FormBasicInfoPage.class);
				assertEquals(basicInfo.getGenderQuestionLabel(), "What sex were you assigned at birth on your original birth certificate?*");
				log("verify that answer is prefilled by information from my account");
				assertEquals(basicInfo.getSelectedGender(), "Male");
				log("verify that answer is required");
				basicInfo.setSex("Choose...");
				basicInfo.clickSaveContinue();
				assertTrue(basicInfo.isInputErrorMessageDisplayed());
				basicInfo.setSex("Male");
				basicInfo.setSexualOrientation("Don't know");
				basicInfo.setGenderIdentity("Male");
				FormCurrentSymptomsPage symptoms = basicInfo.clickSaveContinue(FormCurrentSymptomsPage.class);
				log("verify that only the male category is displayed, female category hidden");
				assertTrue(symptoms.areMaleQuestionsDisplayed());
				assertFalse(symptoms.areFemaleQuestionsDisplayed());
				symptoms.setNoMaleSymptoms();
				FormPastMedicalHistoryPage medHistory = symptoms.clickSaveContinue(FormPastMedicalHistoryPage.class);
				log("verify that save&continue was successful");
				assertFalse(symptoms.areMaleQuestionsDisplayed());
				symptoms = medHistory.goBack(FormCurrentSymptomsPage.class);
				basicInfo = medHistory.goBack(FormBasicInfoPage.class);
				log("choose declined to answer");
				basicInfo.setSex("Declined to answer");
				basicInfo.setGenderIdentity("Additional gender category or other, please specify");
				basicInfo.setGenderIdentityDetails("GI custom text");
				symptoms = basicInfo.clickSaveContinue(FormCurrentSymptomsPage.class);
				log("verify that all questions are displayed to user with gender not matching original birth certificate gender");
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
				String ccd = CCDTest.getFormCCD(timestamp, ccdUrl);
				log("verify that answered questions are in CCD");
				assertTrue(ccd.contains("Penile lesions"), "not found in ccd: " + ccd);
				assertTrue(ccd.contains("Vaginal dryness"), "not found in ccd: " + ccd);
				assertTrue(ccd.contains("None, I have never been pregnant"), "not found in ccd: " + ccd);
				log("verify that there is nullFlavor UNK for gender in CCD");
				assertTrue(Pattern.compile("<administrativeGenderCode[^>]+nullFlavor=\"UNK\"").matcher(ccd).find(), "not found in ccd: " + ccd);
				formsList = PageFactory.initElements(driver, HealthFormListPage.class);
				formsList.goToHomePage();
		}

		String createFormSG(String usernameSG, String passwordSG, String newFormName) throws Exception {
				SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps().logInUserToSG(driver, usernameSG, passwordSG);
				// Get the current window handle before opening new window
				String parentHandle = driver.getWindowHandle();

				log("step 1: Click on Patient Forms");
				DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
				assertTrue(pManageDiscreteForms.isPageLoaded());

				log("step 2: Unpublish and delete all forms and create a new one");
				driver.manage().window().maximize();
				pManageDiscreteForms.initializePracticeForNewForm(SitegenConstants.DISCRETEFORMNAME);
				WelcomeScreenPage welcomePage = pManageDiscreteForms.createAndOpenRegistrationForm(newFormName);

				log("step 3: Initialize the new form");
				pManageDiscreteForms.prepareFormForTest(welcomePage);

				log("step 4: Publish the saved Discrete Form");
				pManageDiscreteForms.publishForm(newFormName);

				log("step 5: Close the window and logout from SiteGenerator");
				// Switching back to original window using previously saved handle
				// descriptor
				driver.switchTo().window(parentHandle);
				pSiteGenPracticeHomePage.clicklogout();

				return pManageDiscreteForms.getWelcomeMessage();
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


		private PracticeHomePage loginToPracticePortal(String url, String username, String password) throws Exception {
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, url);
				return practiceLogin.login(username, password);
		}

		private SearchPatientFormsPage getPracticePortalSearchFormsPage(String url, String username, String password) throws Exception {
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, url);
				PracticeHomePage practiceHome = practiceLogin.login(username, password);

				log("Click CustomFormTab");
				SearchPatientFormsPage searchFormsPage = practiceHome.clickCustomFormTab();

				// check if the page is loaded, sometimes the test ends up on login page
				// at this point
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				if (!searchFormsPage.isPageLoaded() && practiceLogin.isLoginPageLoaded()) {
						searchFormsPage = practiceLogin.login(username, password).clickCustomFormTab();
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
				Thread.sleep(8000);
				String submittedDate = IHGUtil.extractDateFromText(viewFormPage.getLastUpdatedDateText());
				// get current date in the same format as the date at the page
				String currentDate = IHGUtil.getFormattedCurrentDate(submittedDate);
				assertEquals(submittedDate, currentDate, "Form submitted today not found");

				log("Download URL: " + viewFormPage.getDownloadURL());
				URLStatusChecker status = new URLStatusChecker(driver);
				assertEquals(status.getDownloadStatusCode(viewFormPage.getDownloadURL(), RequestMethod.GET), 200);
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
}
