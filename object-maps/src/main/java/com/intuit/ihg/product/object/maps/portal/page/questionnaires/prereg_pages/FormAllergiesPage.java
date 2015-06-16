package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;
import org.testng.annotations.Test;

public class FormAllergiesPage extends PortalFormPage
{


	public FormAllergiesPage(WebDriver driver)  {
		super(driver);
	}

	@FindBy(id = "idonot_drug_allergies")
	private WebElement noDrugAllergies;

	@FindBy(id = "idonot_food_allergies")
	private WebElement noFoodAllergies;

	@FindBy(id = "idonot_environmental_allergies")
	private WebElement noEnvironmentalAllergies;

	@FindBy(id = "allergies_anythingelse")
	private WebElement comments;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(id = "something_else_env_allergies")
	private WebElement otherEnviromentalAllergies;

	@FindBy(id = "something_else_drug_allergies")
	private WebElement otherDrugAllergies;

	@FindBy(id = "peanuts_allergy_food")
	private WebElement peanutsCheckbox;

	/**
	 * Set No Drug Allergies
	 * @throws Exception
	 */
	public void setNoDrugAllergies() throws Exception {
		noDrugAllergies.click();
	}

	/**
	 * Set No Food Allergies
	 * @throws Exception
	 */
	public void setNoFoodAllergies() throws Exception {
		noFoodAllergies.click();
	}

	/**
	 * Set No Environment Allergies
	 * @throws Exception
	 */
	public void setNoEnvironmentalAllergies() throws Exception {
		noEnvironmentalAllergies.click();
	}

	public void enterComment(String comment) {
		comments.clear();
		comments.sendKeys(comment);
	}

	@Override
	public void testValidation() throws Exception {
		setNoDrugAllergies();

		assertErrorMessageAfterContinuing();

		otherDrugAllergies.click();
		enterComment("Some other allergy");

		assertErrorMessageAfterContinuing();

		peanutsCheckbox.click();

		assertErrorMessageAfterContinuing();

		setNoEnvironmentalAllergies();
	}
}
