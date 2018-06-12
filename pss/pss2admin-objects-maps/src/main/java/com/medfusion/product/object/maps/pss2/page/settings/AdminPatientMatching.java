package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdminPatientMatching extends SettingsTab {

	@FindBy(how = How.NAME, using = "zone")
	private WebElement clientTimeZone;

	public AdminPatientMatching(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return false;
	}
	
	public void getClientTimeZone() {
		clientTimeZone.getText();
	}

}
