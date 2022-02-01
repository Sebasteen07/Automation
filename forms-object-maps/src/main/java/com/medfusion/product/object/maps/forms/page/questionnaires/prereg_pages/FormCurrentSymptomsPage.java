//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class FormCurrentSymptomsPage extends PortalFormPage {

	public FormCurrentSymptomsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_symptoms_general_group")
	WebElement noSymptoms;

	@FindBy(id = "idonot_symptoms_malespecific")
	WebElement noMaleSymptoms;

	@FindBy(id = "idonot_symptoms_femalespecific")
	WebElement noFemaleSymptoms;

	@FindBy(id = "chills_symptom_general")
	WebElement checkChills;

	@FindBy(id = "insomnia_symptom_general")
	WebElement checkInsomnia;

	@FindBy(id = "bruising_symptom_blood")
	WebElement checkBruising;

	@FindBy(id = "earache_sumptom_ent")
	WebElement checkEarache;

	@FindBy(id = "symptoms_anythingelse")
	WebElement commentsField;

	@FindBy(linkText = "Save and finish another time")
	WebElement saveAndFinishLater;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(xpath = "//h3[contains(./text(),'Male-Specific')]")
	private WebElement maleQuestionsHeader;

	@FindBy(xpath = "//h3[contains(./text(),'Female-Specific')]")
	private WebElement femaleQuestionsHeader;

	public WebElement getCheckEarache() {
		return checkEarache;
	}

	public String getCommentTextContent() {
		WebDriverWait wait = new WebDriverWait(driver, 6, 2000);
		wait.until(ExpectedConditions.visibilityOf(commentsField));
		return commentsField.getText();
	}

	public void setNoSymptoms() throws Exception {
		PortalUtil2.PrintMethodName();
		noSymptoms.click();
	}

	public void setNoMaleSymptoms() throws Exception {
		PortalUtil2.PrintMethodName();
		scrollAndWait(0, 0, 500);
		noMaleSymptoms.click();
	}

	public void setNoFemaleSymptoms() throws Exception {
		PortalUtil2.PrintMethodName();
		scrollAndWait(0, 0, 500);
		noFemaleSymptoms.click();
	}

	public void setBasicSymptoms() throws Exception {
		if (!checkChills.isSelected()) {
			checkChills.click();
		}
		if (!checkInsomnia.isSelected()) {
			checkInsomnia.click();
		}
		if (!checkBruising.isSelected()) {
			checkBruising.click();
		}
		scrollAndWait(0, 200, 500);
		if (!checkEarache.isSelected()) {
			checkEarache.click();
		}
	}

	public void enterComment(String comment) {
		commentsField.sendKeys(comment);
	}

	public void closeForm() {
		saveAndFinishLater.click();
	}

	public boolean areFemaleQuestionsDisplayed() {
		return IHGUtil.waitForElement(driver, 10, femaleQuestionsHeader);
	}

	public boolean areMaleQuestionsDisplayed() {
		return IHGUtil.waitForElement(driver, 10, maleQuestionsHeader);
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Current Symptoms")))
				.isDisplayed();
	}
}
