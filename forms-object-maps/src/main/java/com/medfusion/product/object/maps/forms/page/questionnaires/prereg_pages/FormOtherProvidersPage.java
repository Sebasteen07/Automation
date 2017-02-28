package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormOtherProvidersPage extends PortalFormPage {

	public FormOtherProvidersPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_referring_doctors")
	WebElement noOtherProviders;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Providers
	 * @throws Exception
	 */
	public void setNoProviders() throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noOtherProviders.click();

	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Other Doctors You've Seen"))).isDisplayed();
	}

}
