//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.Integrations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class CreateIntegrationStep1Page extends BasePageObject {

	@FindBy(name = "externalSystemSelect")
	private WebElement dropDownExternalSystem;

	@FindBy(name = "channelSelect")
	private WebElement dropDownChannelSelect;

	@FindBy(name = "reviewTypeSelect")
	private WebElement dropDownReviewTypeSelect;

	@FindBy(name = "integrationName")
	private WebElement txtIntegrationName;

	@FindBy(name = "buttons:submit")
	private WebElement btnContinue;

	@FindBy(name = "buttons:cancel")
	private WebElement btnCancel;

	public CreateIntegrationStep1Page(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = txtIntegrationName.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public CreateIntegrationStep2Page addNewIntegrationEng(String externalSystem, String channel, String integrationName, String reviewType)
			throws InterruptedException {
		IHGUtil.PrintMethodName();

		Select selExternalSystem = new Select(dropDownExternalSystem);
		selExternalSystem.selectByVisibleText(externalSystem);

		txtIntegrationName.sendKeys(integrationName);

		Select selReviewTypeSelect = new Select(dropDownReviewTypeSelect);
		selReviewTypeSelect.selectByVisibleText(reviewType);

		Thread.sleep(2000);
		Select selChannelSelect = new Select(dropDownChannelSelect);
		log("#######channel" + channel);
		selChannelSelect.selectByVisibleText(channel);

		btnContinue.click();

		return PageFactory.initElements(driver, CreateIntegrationStep2Page.class);
	}
}
