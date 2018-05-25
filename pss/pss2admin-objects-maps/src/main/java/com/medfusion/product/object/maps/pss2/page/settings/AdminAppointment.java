package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;

public class AdminAppointment extends SettingsTab {

	public AdminAppointment(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

}
