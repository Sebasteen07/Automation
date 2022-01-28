//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.common.utils.IHGUtil;

public class CurrentSymptomsPage extends ConfiguratorFormPage {

	@FindBy(xpath = "//h5[contains(text(), 'General Health')]/span/input")
	private WebElement chckGeneralHealth;

	@FindBy(id = "fever_symptom_general")
	private WebElement feverSymptomGeneral;

	@FindBy(id = "weakness_symptom_general")
	private WebElement weaknessSymptomGeneral;

	@FindBy(xpath = "//h5[contains(text(), 'Blood')]/span/input")
	private WebElement chckBlood;

	@FindBy(id = "bleeding_symptom_blood")
	private WebElement bleedingSymptomBlood;

	@FindBy(id = "bruising_symptom_blood")
	private WebElement bruisingSymptomBlood;

	@FindBy(xpath = "//h5[contains(text(), 'Ears, Nose & Throat')]/span/input")
	private WebElement chckEyesEarsNoseThroat;

	@FindBy(id = "bleedinggums_symptom_ent")
	private WebElement bleedinggumsSymptomEnt;

	@FindBy(id = "difficultyswallowing_symptom_ent")
	private WebElement difficultyswallowingSymptomEnt;

	@FindBy(id = "save_config_form")
	private WebElement btnSave;

	@FindBy(xpath = "//div[contains(@class,'currentsymptoms')]//*[contains(text(),'Female-Specific')]//input")
	private WebElement femaleSymptomsGroupInput;

	@FindBy(xpath = "//div[contains(@class,'currentsymptoms')]//*[contains(text(),'Male-Specific')]//input")
	private WebElement maleSymptomsGroupInput;

	public CurrentSymptomsPage(WebDriver driver) {
		super(driver);
		jse = (JavascriptExecutor) driver;
	}

	public void selectBasicSymptoms() throws InterruptedException {
		log("Check General Health");
		IHGUtil.waitForElement(driver, 30, chckGeneralHealth);
		scrollAndWait(0, 0, 500);

		chckGeneralHealth.click();
		log("Check Blood");
		chckBlood.click();

		log("Check Eyes, Ears,Nose and Throat");
		javascriptClick(chckEyesEarsNoseThroat);
	}

	/**
	 * Click on save button and close the form editor
	 */

	public void clickSave() {
		btnSave.click();
	}

	public void addMaleSymptoms() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, maleSymptomsGroupInput);
		scrollAndWait(0, 0, 1000);
		if (!maleSymptomsGroupInput.isSelected())
			maleSymptomsGroupInput.click();
	}

	public void addFemaleSymptoms() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, femaleSymptomsGroupInput);
		scrollAndWait(0, 0, 1000);
		if (!femaleSymptomsGroupInput.isSelected())
			femaleSymptomsGroupInput.click();
	}

}
