package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;

public class AlertNotification extends SettingsTab {

	public AlertNotification(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return false;
	}

}
