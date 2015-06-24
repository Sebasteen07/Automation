package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormOtherProvidersPage extends PortalFormPage {


	public FormOtherProvidersPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id="idonot_referring_doctors")
	WebElement noOtherProviders;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
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
}
