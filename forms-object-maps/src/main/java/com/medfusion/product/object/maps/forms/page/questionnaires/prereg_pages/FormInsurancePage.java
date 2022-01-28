//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class FormInsurancePage extends PortalFormPage {

	public FormInsurancePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_primary_insurance")
	private WebElement selfPay;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(xpath = "//label[@for='primary_insurance_company']/../input")
	private WebElement nameOfPrimaryInsurance;

	@FindBy(xpath = "//label[@for='primary_policy_holder_firstname']/../input")
	private WebElement policyHolderFirstName;

	public void setSelfPay() throws Exception {
		PortalUtil2.PrintMethodName();
		selfPay.click();
	}

	public void setNameofPolicyHolderFirstname(String PrimaryInsurance, String PatientName)
			throws InterruptedException {
		Thread.sleep(4000);
		nameOfPrimaryInsurance.clear();
		nameOfPrimaryInsurance.sendKeys(PrimaryInsurance);
		policyHolderFirstName.clear();
		policyHolderFirstName.sendKeys(PatientName);
		saveAndContinuebtn.click();
	}

	public FormSecondaryInsurancePage fillfirstInsurance(String PrimaryInsurance, String PatientName)
			throws InterruptedException {
		setNameofPolicyHolderFirstname(PrimaryInsurance, PatientName);

		return PageFactory.initElements(driver, FormSecondaryInsurancePage.class);
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Health Insurance Information")))
				.isDisplayed();
	}
}
