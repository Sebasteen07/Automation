package com.medfusion.product.object.maps.forms.page.questionnaires.custom_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalCustomFormPage;

public class SpecialCharFormFirstPage extends PortalCustomFormPage {

	@FindBy(id = "custom_mappingid_customfirst_section_2_0")
	private WebElement feverCheck;

	@FindBy(id = "custom_mappingid_customfirst_section_2_2")
	private WebElement coughCheck;

	public SpecialCharFormFirstPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * @throws InterruptedException
	 * @brief Selects checkboxes in the form that contain quotations marks - "
	 */
	public void selectQuotatedAnswers() throws InterruptedException {
		scrollAndWait(0, 0, 500);
		if (feverCheck.isSelected() == false)
			feverCheck.click();

		if (coughCheck.isSelected() == false)
			coughCheck.click();
	}

}