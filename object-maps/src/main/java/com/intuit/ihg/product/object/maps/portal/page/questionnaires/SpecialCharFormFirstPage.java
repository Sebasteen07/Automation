package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class SpecialCharFormFirstPage extends BasePageObject {
	
	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(id = "custom_mappingid_customfirst_section_2_0")
	private WebElement feverCheck;

	@FindBy(id = "custom_mappingid_customfirst_section_2_2")
	private WebElement coughCheck;
	
	public SpecialCharFormFirstPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * @brief Click on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends BasePageObject> T clickSaveAndContinueButton(Class<T> nextPageClass) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, nextPageClass);
	}

	/**
	 * @brief Selects checkboxes in the form that contain quotations marks - "
	 */
	public void selectQuotatedAnswers() {
		if (feverCheck.isSelected() == false) 
			feverCheck.click();
		
		if (coughCheck.isSelected())
			coughCheck.click();
	}
	
}
