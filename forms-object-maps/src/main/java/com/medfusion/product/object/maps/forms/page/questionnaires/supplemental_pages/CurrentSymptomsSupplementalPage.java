//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.supplemental_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

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
	
	public void answerCustomQuestion(int questionNumber, String answer) {
		customQuestions[questionNumber].sendKeys(answer);
	}

	public void fillLogicalAnswersForPdfTest() {
		answerCustomQuestion(0, "Random comment");
		answerCustomQuestion(1, "6'4\"");
		answerCustomQuestion(2, "170 lbs");
	}

	@Override
	public boolean isPageLoaded() {
		throw new UnsupportedOperationException();
	}
}
