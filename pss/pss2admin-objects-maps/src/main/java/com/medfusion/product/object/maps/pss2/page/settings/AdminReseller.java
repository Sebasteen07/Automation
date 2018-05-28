package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;

public class AdminReseller extends SettingsTab {

	public AdminReseller(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return false;
	}

}
