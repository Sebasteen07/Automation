//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal2.utils.PortalUtil2;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormAllergiesPage extends PortalFormPage {

	public FormAllergiesPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_drug_allergies")
	WebElement noDrugAllergies;

	@FindBy(id = "idonot_food_allergies")
	WebElement noFoodAllergies;

	@FindBy(id = "idonot_environmental_allergies")
	WebElement noEnvironmentalAllergies;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(id = "generalanesthetic_allergy_drug")
	WebElement generalAnesthetic;

	@FindBy(id = "peanuts_allergy_food")
	WebElement peanuts;

	public void setNoDrugAllergies() throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		noDrugAllergies.click();
	}

	public void setNoFoodAllergies() throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		noFoodAllergies.click();
	}

	public void setGeneralAnesthetic_20(Boolean isFormTypePreCheck) throws Exception {
		PortalUtil2.PrintMethodName();

		if (!isFormTypePreCheck) {
			WebElement W1 = driver.findElement(By.xpath("//iframe[@title='Forms']"));
			driver.switchTo().frame(W1);
		}
		if (!generalAnesthetic.isSelected()) {
			IHGUtil.waitForElement(driver, 60, generalAnesthetic);
			generalAnesthetic.click();
		}
	}

	public void setPeanuts_20() throws Exception {
		PortalUtil2.PrintMethodName();
		if (!peanuts.isSelected()) {
			peanuts.click();
		}
	}

	public FormVaccinePage setAllergies(Boolean isFormTypePreCheck) throws Exception {
		setGeneralAnesthetic_20(isFormTypePreCheck);
		setPeanuts_20();
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormVaccinePage.class);

	}

	public void setNoEnvironmentalAllergies() throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		noEnvironmentalAllergies.click();
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Allergies"))).isDisplayed();
	}
}
