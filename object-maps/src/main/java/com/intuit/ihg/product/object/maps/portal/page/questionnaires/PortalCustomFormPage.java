package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalCustomFormPage extends PortalFormPage {

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement btnContinue;
	
	public PortalCustomFormPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * @brief Click on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	@Override
	public <T extends PortalFormPage> T clickSaveAndContinueButton(Class<T> nextPageClass) throws Exception {
		return super.clickSaveAndContinueButton(nextPageClass, this.btnContinue);
	}
	
	/**
	 * @brief Click on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	@Override
	public <T extends PortalFormPage> T clickSaveAndContinueButton() throws Exception {
		return clickSaveAndContinueButton(null, this.btnContinue);
	}
}
