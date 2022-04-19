//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalCustomFormPage extends PortalFormPage {

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement btnContinue;

	public PortalCustomFormPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass) throws Exception {
		return super.clickSaveContinue(nextPageClass, this.btnContinue);
	}

	@Override
	public <T extends PortalFormPage> T clickSaveContinue() throws Exception {
		return clickSaveContinue(null, this.btnContinue);
	}

	@Override
	public boolean isPageLoaded() {
		throw new UnsupportedOperationException();
	}
}
