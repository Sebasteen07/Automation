package com.medfusion.product.object.maps.patientportal1.page.questionnaires.supplemental_pages;

import com.medfusion.product.object.maps.patientportal1.page.questionnaires.PortalFormPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CurrentSymptomsSupplementalPage extends PortalFormPage {

	@FindBy(id = "custom_mappingid_currentsymptoms_0")
	private WebElement firstQuestion;
	
	@FindBy(id = "custom_mappingid_currentsymptoms_1")
	private WebElement secondQuestion;
	
	@FindBy(id = "custom_mappingid_currentsymptoms_2")
	private WebElement thirdQuestion;

	private WebElement[] customQuestions;
	
	public CurrentSymptomsSupplementalPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		customQuestions = new WebElement[3];
		customQuestions[0] = firstQuestion;
		customQuestions[1] = secondQuestion;
		customQuestions[2] = thirdQuestion;
	}	

	/**
	 * Send string answer to selected question
	 * @param questionNumber index of the question to answer 
	 * @param answer 
	 */
	public void answerCustomQuestion(int questionNumber, String answer) {
		customQuestions[questionNumber].sendKeys(answer);
	}
	
	/**
	 * Fills out the questions on the page for form PDF test
	 * Fills out weight in lbs and height in foots and inches or whatever the correct imperial units are
	 */
	public void fillLogicalAnswersForPdfTest() {
		answerCustomQuestion(0, "Random comment");
		answerCustomQuestion(1, "6'4\"");
		answerCustomQuestion(2, "170 lbs");
	}
}
