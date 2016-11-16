package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormInsurancePage extends PortalFormPage {

	public FormInsurancePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_primary_insurance")
	private WebElement selfPay;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set Self Pay
	 * @throws Exception
	 */

	public void setSelfPay() throws Exception {
		PortalUtil.PrintMethodName();
		selfPay.click();
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Health Insurance Information"))).isDisplayed();
	}
}
