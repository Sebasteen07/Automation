//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class FormPastMedicalHistoryPage extends PortalFormPage {

	public FormPastMedicalHistoryPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_conditions")
	WebElement noConditions;

	@FindBy(id = "mononucleosis_condition_other")
	WebElement monoCheckbox;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(xpath = "//h3[contains(./text(),'Female-Specific')]")
	private WebElement femaleQuestionsHeader;

	@FindBy(id = "howmanypregnancies_condition_femalespecific")
	private WebElement countOfPregnancies;

	public void setNoConditions() throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		noConditions.click();
	}

	public void checkMononucleosis() throws Exception {
		if (monoCheckbox.isSelected() == false)
			monoCheckbox.click();
	}

	public void checkMononucleosis_20() throws Exception {
		if (monoCheckbox.isSelected() == false) {
			monoCheckbox.click();
		}
		saveAndContinuebtn.click();
	}

	public FormFamilyHistoryPage setIllnessConditionFormFields() throws Exception {
		setNoConditions();
		return clickSaveContinue(FormFamilyHistoryPage.class);
	}

	public boolean areFemaleQuestionsDisplayed() {
		return IHGUtil.waitForElement(driver, 3, femaleQuestionsHeader);
	}

	public void setCountOfPregnancies(int count) {
		if (count < 0)
			throw new IllegalArgumentException("can not set less then 0 pregnancies");
		Select countOfPregnanciesSel = new Select(countOfPregnancies);
		countOfPregnanciesSel.selectByIndex(count + 1);
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Past Medical History")))
				.isDisplayed();
	}
}
