// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Menu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class PSSPatientHeader extends PSS2MainPage {

	@FindBy(how = How.ID, using = "headerlogo")
	private WebElement companyLogo;

	@FindBy(how = How.XPATH, using = "//*[@class=\"country-flag\"]/img")
	private WebElement flagImage;

	@FindBy(how = How.CLASS_NAME, using = "country-label")
	private WebElement languageText;

	@FindBy(how = How.ID, using = "logoutbutton")
	private WebElement nameSettings;
	
	@FindBy(how = How.XPATH, using = "//span[@class='circle']")
	private WebElement logoutCircle;

	@FindBy(how = How.XPATH, using = "//*[@class='dropdown-menu']//a")
	private WebElement logout;

	public PSSPatientHeader(WebDriver driver) {
		super(driver);
	}

	public void logout() throws InterruptedException {
		Thread.sleep(1000);
		jse.executeScript("window.scrollBy(1000,0)");
		Thread.sleep(1000);
		nameSettings.click();
		Thread.sleep(1000);
		IHGUtil.waitForElement(driver, 60, logout);
		logout.click();
	}
	
	public void backToHomePage() {
		companyLogo.click();
	}
}
