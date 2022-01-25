//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WelcomeScreenPage extends ConfiguratorFormPage {

	public WelcomeScreenPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(name = "welcomeScreenText")
	private WebElement lnkWelcomeScreenText;

	public WelcomeScreenPage setWelcomeMessage(String message) {
		lnkWelcomeScreenText.clear();
		lnkWelcomeScreenText.sendKeys(message);
		return this;
	}
}
