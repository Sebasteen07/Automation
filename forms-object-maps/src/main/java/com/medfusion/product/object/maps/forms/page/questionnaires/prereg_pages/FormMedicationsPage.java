//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormMedicationsPage extends PortalFormPage {

	public FormMedicationsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "idonot_medications")
	WebElement noMedications;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(xpath = ".//*[@id='section5']/section/div[2]/a")
	private WebElement btnSubmit;

	@FindBy(id = "medications_other_field_autocomplete")
	private WebElement medications;

	@FindBy(id = "medications_other_field_frequency")
	private WebElement taken;

	@FindBy(xpath = "//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	public void setNoMedications() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 20, noMedications);
		if (noMedications.isSelected() == false)
			noMedications.click();
	}

	public FormAllergiesPage setMedicationFormFields_20(String medicine, String input) throws Exception {
		fillMedicationFormFields_new(medicine);
		setTakenInterval(input);
		IHGUtil.waitForElement(driver, 30, saveAndContinuebtn);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormAllergiesPage.class);
	}

	public FormAllergiesPage setMedicationFormFields() throws Exception {
		setNoMedications();

		return clickSaveContinue(FormAllergiesPage.class);
	}

	public void fillMedicationFormFields() throws Exception {
		setNoMedications();
		saveAndContinuebtn.click();

		IHGUtil.waitForElement(driver, 10, btnSubmit);
		btnSubmit.click();
	}

	public void fillMedicationFormFields_new(String medicine) throws Exception {
		IHGUtil.waitForElement(driver, 10, medications);
		medications.clear();
		medications.sendKeys(medicine);
		IHGUtil.waitForElement(driver, 40, autoComplete);
		autoComplete.click();
	}

	public void setTakenInterval(String input) throws InterruptedException {
		IHGUtil.waitForElement(driver, 20, taken);
		taken.clear();
		taken.sendKeys(input);
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Medications"))).isDisplayed();
	}
}
