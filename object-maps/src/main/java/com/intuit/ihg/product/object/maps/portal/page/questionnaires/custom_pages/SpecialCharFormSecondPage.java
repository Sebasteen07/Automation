package com.intuit.ihg.product.object.maps.portal.page.questionnaires.custom_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalCustomFormPage;

public class SpecialCharFormSecondPage extends PortalCustomFormPage {

	@FindBy(id = "custom_mappingid_customsecond_section_0_5")
	private WebElement severeButt;
	
	@FindBy(id = "custom_mappingid_customsecond_section_consentanswer_custom_1")
	private WebElement consent;
	
	@FindBy(xpath="//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy( xpath = ".//iframe[@title ='Forms']")
	private WebElement iframe;

	public SpecialCharFormSecondPage(WebDriver driver) {
		super(driver);
	}

	public void selectAnswerQuoteMark() {
		severeButt.click();
	}
	
	/**
	 * @brief Signs consent with a name containing quote marks
	 */
	public void signConsent() {
		if (consent.getText().length() == 0)
			consent.sendKeys("Frankie \"Tester\" Testguy");
	}
}
