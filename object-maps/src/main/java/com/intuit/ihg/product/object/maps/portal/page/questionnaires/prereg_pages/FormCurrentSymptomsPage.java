package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import org.testng.annotations.Test;


public class FormCurrentSymptomsPage extends PortalFormPage 
{


	public FormCurrentSymptomsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_symptoms_general_group")
	private WebElement noGeneralSymptoms;

	@FindBy(id = "idonot_symptoms_eyes")
	private WebElement noEyeSymptoms;
	
	@FindBy(id = "chills_symptom_general")
	private WebElement checkChills;
	
	@FindBy(id = "insomnia_symptom_general")
	private WebElement checkInsomnia;
	
	@FindBy(id = "bruising_symptom_blood")
	private WebElement checkBruising;
	
	@FindBy(id = "earache_sumptom_ent")
	private WebElement checkEarache;

	@FindBy(id = "symptoms_anythingelse")
	private WebElement commentsField;
	
	@FindBy(linkText = "Save and finish another time")
	private WebElement saveAndFinishLater;
	
	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	
	
	public WebElement getCheckEarache() {
		return checkEarache;
	}
	
	public String getCommentTextContent() {
		WebDriverWait wait = new WebDriverWait(driver, 6, 2000);
		wait.until(ExpectedConditions.visibilityOf(commentsField));
		return commentsField.getText();
	}

	/**
	 * Set No Symptoms
	 * @throws Exception
	 */
	public void setNoGeneralSymptoms() throws Exception {
		PortalUtil.PrintMethodName();
		noGeneralSymptoms.click();
	}

	/**
	 * Set some basic symptom for later PDF test
	 * @throws Exception
	 */
	public void setBasicSymptoms() throws Exception {
		checkChills.click();
		checkInsomnia.click();
		checkBruising.click();
		checkEarache.click();
	}

	public void enterComment(String comment) {
		commentsField.sendKeys(comment);
	}

	@Override
	public void testValidation() throws InterruptedException {
		assertErrorMessageAfterContinuing();

		noGeneralSymptoms.click();
		noEyeSymptoms.click();

		assertErrorMessageAfterContinuing();

		checkBruising.click();
		log("Validation test passed");
	}

}
