//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdminReseller extends SettingsTab {

	@FindBy(how = How.XPATH, using = "//a[@title='delete reseller logo']")
	private WebElement deleteButton;
	
	@FindBy(how = How.XPATH, using = "//textarea[@id='resellerdisclaimer']")
	private WebElement footerTextArea;
	
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	private WebElement saveButton;
	
	public AdminReseller(WebDriver driver) {
		super(driver);
	}
	
	public void setFooterTextWhenSpanishIsDisabled(String footerText) {
		footerTextArea.clear();
		footerTextArea.sendKeys(footerText);
		saveButton.click();
	}

}
