//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.Integrations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class CreateIntegrationStep2Page extends BasePageObject {

	@FindBy(name = "integrationName")
	private WebElement txtIntegrationName;

	@FindBy(xpath = "//form[@id='editIntegrationForm']/fieldset/table/tbody/tr[7]/td[2]/span")
	private WebElement textIntegrationId;

	@FindBy(name = "buttons:submit")
	private WebElement btnSaveAndContinue;

	public CreateIntegrationStep2Page(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = btnSaveAndContinue.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public ViewIntegrationsPage clickbtnSaveAndContinue() {
		IHGUtil.PrintMethodName();
		javascriptClick(btnSaveAndContinue);
		return PageFactory.initElements(driver, ViewIntegrationsPage.class);
	}
}
