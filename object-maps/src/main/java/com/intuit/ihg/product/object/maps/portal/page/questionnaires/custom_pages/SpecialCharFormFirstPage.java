package com.intuit.ihg.product.object.maps.portal.page.questionnaires.custom_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalCustomFormPage;

public class SpecialCharFormFirstPage extends PortalCustomFormPage {

	@FindBy(id = "custom_mappingid_customfirst_section_2_0")
	private WebElement feverCheck;

	@FindBy(id = "custom_mappingid_customfirst_section_2_2")
	private WebElement coughCheck;
	
	public SpecialCharFormFirstPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * @brief Selects checkboxes in the form that contain quotations marks - "
	 */
	public void selectQuotatedAnswers() {
		if (feverCheck.isSelected() == false) 
			feverCheck.click();
		
		if (coughCheck.isSelected() == false)
			coughCheck.click();
	}
	
}
