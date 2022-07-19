// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.custom_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalCustomFormPage;

public class SpecialCharFormSecondPage extends PortalCustomFormPage {

	@FindBy(id = "custom_mappingid_customsecond_section_0_5")
	private WebElement severeButt;

	@FindBy(id = "custom_mappingid_customsecond_section_consentanswer_custom_1")
	private WebElement consent;

	@FindBy(xpath = "//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy(xpath = ".//iframe[@title ='Forms']")
	private WebElement iframe;

	public SpecialCharFormSecondPage(WebDriver driver) {
		super(driver);
	}

	public void selectAnswerQuoteMark() {
		severeButt.click();
	}

	public void signConsent() {
		consent.clear();
		consent.sendKeys("Frankie \"Tester\" Testguy");
	}
}
