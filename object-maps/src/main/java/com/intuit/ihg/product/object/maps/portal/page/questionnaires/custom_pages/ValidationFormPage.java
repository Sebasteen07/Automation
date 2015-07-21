package com.intuit.ihg.product.object.maps.portal.page.questionnaires.custom_pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalCustomFormPage;

public class ValidationFormPage extends PortalCustomFormPage {

	@FindBy(id = "custom_mappingid_customfirst_section_0_1")
	private WebElement answerToQ1;

	@FindBy(id = "custom_mappingid_customfirst_section_1_2")
	private WebElement answerToQ2;

	@FindBy(id = "custom_mappingid_customfirst_section_2")
	private WebElement answerToQ3;

	@FindBy(id = "custom_mappingid_customfirst_section_3")
	private WebElement answerToQ4;

	@FindBy(id = "custom_mappingid_customfirst_section_consentanswer_custom_4")
	private WebElement answerToConsent1;

	@FindBy(id = "custom_mappingid_customfirst_section_7_5")
	private WebElement answerToQ5;

	@FindBy(id = "custom_mappingid_customfirst_section_8")
	private WebElement answerToQ6;

	@FindBy(id = "custom_mappingid_customfirst_section_consentanswer_custom_10")
	private WebElement answerToConsent2;

	private List<WebElement> answers;

	

	public ValidationFormPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

		answers = new ArrayList<WebElement>();
		answers.add(answerToQ1);
		answers.add(answerToQ2);
		answers.add(answerToQ3);
		answers.add(answerToQ4);
		answers.add(answerToConsent1);
		answers.add(answerToQ5);
		answers.add(answerToQ6);
		answers.add(answerToConsent2);

	}

	/**
	 * @brief Tests different possibilities of incorrect answering of the form
	 */
	public void testValidation() throws Exception {
		log("Validation Step 1: Continue with nothing answered.");
		assertErrorMessageAfterContinuing();

		log("Validation Step 2: try incrementally answering and continuing Q1 to Q7 including Consent1.");
		for (WebElement answer : answers.subList(0, answers.size() - 1)) {
			setAnswer(answer);
			assertErrorMessageAfterContinuing();
		}
		log("Validation Step 3: Go through all the answers with one answer missing starting with Q2 missing");
		// first set the last answer
		setAnswer(answers.get(answers.size() - 1));
		for (int i = 0; i < answers.size() - 2; i++) {
			setAnswer(answers.get(i));
			unsetAnswer(answers.get(i + 1));
			assertErrorMessageAfterContinuing();
		}
		setAnswer(answers.get(answers.size() - 2));
		
	}
	
	private void setAnswer(WebElement answer) {

		if ((answer.getAttribute("type").equals("radio"))
				|| (answer.getAttribute("type").equals("checkbox"))) {
			answer.click();
		} else {
			answer.sendKeys("Answer");
		}
	}

	private void unsetAnswer (WebElement answer) {
		if ((answer.getAttribute("type").equals("radio"))
				|| (answer.getAttribute("type").equals("checkbox"))) {
			answer.click();
		} else {
			answer.clear();
		}
	}

}
