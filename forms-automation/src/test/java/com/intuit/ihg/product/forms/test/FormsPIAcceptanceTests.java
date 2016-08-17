package com.intuit.ihg.product.forms.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.questionnaires.FormWelcomePage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HealthForms.JalapenoHealthFormsListPage;
import com.medfusion.product.object.maps.patientportal2.page.HealthForms.JalapenoNewCustomHealthFormPage;
import com.medfusion.product.object.maps.patientportal2.page.HealthForms.Question;
import com.medfusion.product.object.maps.patientportal2.page.HealthForms.QuestionUtils;
import com.medfusion.product.object.maps.patientportal2.page.HealthForms.SelectQuestion;
import com.medfusion.product.object.maps.patientportal2.page.HealthForms.SelectableAnswer;
import com.medfusion.product.object.maps.patientportal2.page.HealthForms.TextQuestion;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.TestcasesData;

public class FormsPIAcceptanceTests extends FormsAcceptanceTests {

	@Override
	@Test(enabled = false, groups = { "PIForms" })
	public void testDiscreteFormDeleteCreatePublish() throws Exception {
		String welcomeMessage = createFormSG();

		log("step 6: Go to Patient Portal using the original window");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("URL: " + portalData.getPIFormsUrl());

		log("step 7: Log in to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, portalData.getPIFormsUrl());
		JalapenoHomePage homePage = loginPage.login(portalData.getUsername(), portalData.getPassword());
		assertTrue(homePage.isMessagesButtonDisplayed(driver));

		log("step 8: Click On Start Registration Button and verify welcome page of the previously created form");
		homePage.clickStartRegistrationButton(driver);
		FormWelcomePage pFormWelcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		assertTrue(pFormWelcomePage.isWelcomePageLoaded());
		assertEquals(pFormWelcomePage.getMessageText(), welcomeMessage);
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

	/**
	 * Fills and saves (does not submit) custom form. Tests displaying and
	 * interactivity of elements including FUPs.
	 */
	@Test(enabled = true, groups = { "PIForms" })
	public void testCustomFormWithFUPs() throws Exception {
		log("step 1: Go to Patient Portal");
		TestcasesData portalData = new TestcasesData(new Portal());
		log("step 2: Log in to Patient Portal");
		JalapenoHomePage homePage = new JalapenoLoginPage(driver, portalData.getPIFormsAltUrl())
				.login(portalData.getUsername(), portalData.getPassword());
		log("step 3: Open test form");
		JalapenoHealthFormsListPage healthFormPage = homePage.clickOnHealthForms(driver);
		IHGUtil.setFrame(driver, "iframe");
		JalapenoNewCustomHealthFormPage customFormPage = healthFormPage.openForm("PI-testBeforeSubmit");
		log("step 4: Clear all answers of 1st section");
		customFormPage.goToFirstSection();
		customFormPage.clearAllInputs();
		log("step 5: Check that FUPs are hidden after clear");
		List<WebElement> initiallyVisibleQuestionsFirstSection = customFormPage.getVisibleQuestions();
		assertEquals(initiallyVisibleQuestionsFirstSection.size(), 3);
		log("step 6: Test interactivity");
		log("step 6.1: Test top lvl required flag");
		assertFalse(customFormPage.clickOnSaveAndContinueButton());
		customFormPage.fillMultiLineAnswer(initiallyVisibleQuestionsFirstSection.get(0), "1st multiline answer");
		assertTrue(customFormPage.clickOnSaveAndContinueButton());
		customFormPage.clickOnGoToPrevious();
		log("step 6.2: Test showing of FUPs");
		customFormPage.fillSingleLineAnswer(initiallyVisibleQuestionsFirstSection.get(1), "1st singleline answer");
		customFormPage.selectAnswers(initiallyVisibleQuestionsFirstSection.get(2), Arrays.asList(1, 2));
		List<WebElement> visibleQuestionsFirstSection = customFormPage.getVisibleQuestions();
		assertEquals(visibleQuestionsFirstSection.size(), 7);
		log("step 6.3: Test FUP required flag");
		assertFalse(customFormPage.clickOnSaveAndContinueButton());
		log("step 6.4: Test answering FUPs");
		customFormPage.selectAnswer(visibleQuestionsFirstSection.get(3), 1);
		customFormPage.selectAnswer(visibleQuestionsFirstSection.get(4), 1);
		customFormPage.selectAnswers(visibleQuestionsFirstSection.get(5), Arrays.asList(1, 2));
		assertTrue(customFormPage.clickOnSaveAndContinueButton());
		log("step 6.5: Test second section behaving");
		customFormPage.clearAllInputs();
		List<WebElement> initiallyVisibleQuestionsSecondSection = customFormPage.getVisibleQuestions();
		assertEquals(initiallyVisibleQuestionsSecondSection.size(), 3);
		customFormPage.selectAnswer(initiallyVisibleQuestionsSecondSection.get(0), 2);
		List<WebElement> visibleQuestionsSecondSection = customFormPage.getVisibleQuestions();
		customFormPage.fillSingleLineAnswer(visibleQuestionsSecondSection.get(1), "should not see me and the end");
		customFormPage.selectAnswer(visibleQuestionsSecondSection.get(2), 1);
		log("step 6.6: Test headings and texts visibility");
		assertTrue(customFormPage.isHeadingDisplayed("HeadingS2"));
		assertTrue(customFormPage.isTextDisplayed("TextS2"));
		assertFalse(customFormPage.isHeadingDisplayed("Heading22"));
		assertFalse(customFormPage.isTextDisplayed("Text22"));
		customFormPage.selectAnswer(initiallyVisibleQuestionsSecondSection.get(0), 1);
		visibleQuestionsSecondSection = customFormPage.getVisibleQuestions();
		assertEquals(visibleQuestionsSecondSection.size(), 4);
		assertTrue(customFormPage.isHeadingDisplayed("Heading22"));
		assertTrue(customFormPage.isTextDisplayed("Text22"));
		log("step 6.7: Test consents's required flag");
		assertFalse(customFormPage.clickOnSaveAndContinueButton());
		customFormPage.consentAllVisibleStatements("InMyName");
		assertTrue(customFormPage.clickOnSaveAndContinueButton());
		log("step 6.8: Test section update button");
		customFormPage.selectSectionToUpdate(2);
		assertTrue(customFormPage.isSectionDisplayed(2));
		customFormPage.selectAnswer(visibleQuestionsSecondSection.get(1), 1);
		log("step 7: Save the form");
		customFormPage.clickOnGoToPrevious();
		customFormPage.clickOnSaveAndFinishButton();
		log("step 8: Open the form and checks saved values");
		IHGUtil.setFrame(driver, "iframe");
		customFormPage = healthFormPage.openForm("PI-testBeforeSubmit");
		assertEquals(QuestionUtils.getSetOfVisibleQuestions(1, driver), getExpectedQuestionsForFirstSection());
		assertTrue(customFormPage.clickOnSaveAndContinueButton());
		assertEquals(QuestionUtils.getSetOfVisibleQuestions(2, driver), getExpectedQuestionsForSecondSection());
	}

}
