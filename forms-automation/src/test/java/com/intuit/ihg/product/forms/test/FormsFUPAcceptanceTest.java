package com.intuit.ihg.product.forms.test;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CustomFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CustomFormPageSection;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.SiteGenSteps;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;

public class FormsFUPAcceptanceTest extends FormsAcceptanceTests {
	/**
	 * @Author: Jan Tomasek
	 * @Date: July-15-2016
	 * @UserStory: FORMS-346 Logins into sitegen. Creates a new custom form.
	 *             Adds and removes FUPs. Saves form. Reopens the form and
	 *             checks that it contains correct items.
	 */
	@Test
	public void testSitegenFUPInteraction() throws Exception {
		logTestEnvironmentInfo("testCreateFormWithFUPs");
		Sitegen sitegen = new Sitegen();
		SitegenTestData SGData = new SitegenTestData(sitegen);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps().logInUserToSG(driver,
				SGData.getFormUser(), SGData.getFormPassword());

		log("step 1: Click on Patient Forms");
		DiscreteFormsList pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		assertTrue(pManageDiscreteForms.isPageLoaded());

		log("step 2: Unpublish and delete all forms and create a new one");
		driver.manage().window().maximize();
		pManageDiscreteForms.initializePracticeForNewForm();
		pManageDiscreteForms.createANewCustomForm();

		log("step 3: Open created custom form");
		CustomFormPage customFormPage = pManageDiscreteForms.clickOnLastCreatedForm();
		customFormPage.clickOnSection(1);
		CustomFormPageSection section1 = customFormPage.getFirstSection();

		log("step 4: Create question and add 3 answers");
		section1.addEmptyItem();
		section1.setItemAsQuestion(1, SitegenConstants.QUESTION_TYPE4, "base question", false, false);
		section1.addAnswer(1, "answer1");
		section1.addAnswer(1, "answer2");
		section1.addAnswer(1, "answer3");

		log("step 5: Add 2 FUPs to 1st answer of 1st question");
		section1.addFUP(1, 1);
		section1.addFUP(1, 1);
		section1.setItemAsQuestionFUP(1, 1, SitegenConstants.QUESTION_TYPE1, "followUpText", false, false);
		section1.setItemAsQuestionFUP(1, 2, SitegenConstants.QUESTION_TYPE3, "sndFollowUp", false, false);
		section1.addAnswerFUP(1, 2, "sub-answer1");
		section1.addAnswerFUP(1, 2, "sub-answer2");

		log("step 6: Add FUP to 2nd answer of 1st question");
		section1.addFUP(1, 2);
		section1.setItemAsQuestionFUP(1, 3, SitegenConstants.QUESTION_TYPE3, SitegenConstants.QUESTION_TYPE3, false,
				false);
		section1.addAnswerFUP(1, 3, "sub-answer1");
		section1.addAnswerFUP(1, 3, "sub-answer2");
		section1.addAnswerFUP(1, 3, "sub-answer3");

		log("step 7: Add FUP to 3rd answer of 1st question and delete it immediately");
		section1.addFUP(1, 3);
		section1.setItemAsHeadingFUP(1, 4, "deleted heading");
		section1.removeFUP(1, 4);

		log("step 8: save and reopen form");
		customFormPage.saveForm();
		customFormPage.leaveFormPage();
		customFormPage = pManageDiscreteForms.clickOnLastCreatedForm();
		customFormPage.clickOnSection(1);
		section1 = customFormPage.getFirstSection();

		log("step 9: test if form contains correct count of FUPs");
		// first answer has 2 FUPs
		assertEquals(section1.getCountOfFUPsOfAnswer(1, 1), 2);
		// 3rd answer can't contain FUP because that one was deleted before save
		assertEquals(section1.getCountOfFUPsOfAnswer(1, 3), 0);

		log("step 10: test FUPs minimization");
		assertFalse(section1.areFUPsMinimized(1, 1));
		section1.toogleFUPs(1, 1);
		assertTrue(section1.areFUPsMinimized(1, 1));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, -250);");
		Thread.sleep(1000);
		section1.toogleFUPs(1, 1);
		assertFalse(section1.areFUPsMinimized(1, 1));

		log("step 11: test consistency of FUPs");
		assertEquals("sndFollowUp", section1.getTitleOfFUPQuestion(1, 2));
		List<String> expectedAnswers = new ArrayList<String>();
		expectedAnswers.add("sub-answer1");
		expectedAnswers.add("sub-answer2");
		assertTrue(section1.getAnswersOfFUPQuestion(1, 2).containsAll(expectedAnswers));
	}
}