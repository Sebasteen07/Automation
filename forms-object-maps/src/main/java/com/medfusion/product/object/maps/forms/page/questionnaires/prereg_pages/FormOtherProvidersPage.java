//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class FormOtherProvidersPage extends PortalFormPage {

	public FormOtherProvidersPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_referring_doctors")
	WebElement noOtherProviders;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinueButton;

	@FindBy(id = "doctors_seen")
	private WebElement doctorsName;

	public void setNoProviders() throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		noOtherProviders.click();

	}

	public FormMedicationsPage setProvidername(String input) throws Exception {
		doctorsName.clear();
		doctorsName.sendKeys(input);

		return PageFactory.initElements(driver, FormMedicationsPage.class);
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Other Doctors You've Seen")))
				.isDisplayed();
	}

}
