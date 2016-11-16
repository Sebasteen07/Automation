package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormSecondaryInsurancePage extends PortalFormPage {

	public FormSecondaryInsurancePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_secondary_insurance")
	private WebElement idonotSecondaryInsurance;

	/**
	 * @Description:Set no secondary insurance
	 * @throws Exception
	 */
	public void setNoSecondaryInsurance() throws Exception {
		idonotSecondaryInsurance.click();
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Secondary Health Insurance Information"))).isDisplayed();
	}
}
