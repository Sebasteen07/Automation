//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormSecondaryInsurancePage extends PortalFormPage {

	public FormSecondaryInsurancePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_secondary_insurance")
	private WebElement idonotSecondaryInsurance;

	@FindBy(id = "secondary_insurance_company")
	private WebElement secondaryCompany;

	@FindBy(id = "secondary_policy_holder_firstname")
	private WebElement secondaryFirstName;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinueButton;

	public void setNoSecondaryInsurance() throws Exception {
		idonotSecondaryInsurance.click();
	}

	public void setSecondaryInsuranceName(String nameOfSecondInsurance, String patientName)
			throws InterruptedException {
		IHGUtil.waitForElement(driver, 20, secondaryCompany);
		secondaryCompany.clear();
		secondaryCompany.sendKeys(nameOfSecondInsurance);
		secondaryFirstName.clear();
		secondaryFirstName.sendKeys(patientName);
		saveAndContinueButton.click();
	}

	public FormMedicationsPage fillSecondInsurance(String nameofSecondInsurance, String Patientname)
			throws InterruptedException {
		setSecondaryInsuranceName(nameofSecondInsurance, Patientname);

		return PageFactory.initElements(driver, FormMedicationsPage.class);

	}

	@Override
	public boolean isPageLoaded() {
		return driver
				.findElement(
						By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Secondary Health Insurance Information")))
				.isDisplayed();
	}
}