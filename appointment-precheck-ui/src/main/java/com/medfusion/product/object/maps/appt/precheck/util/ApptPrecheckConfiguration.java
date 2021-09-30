package com.medfusion.product.object.maps.appt.precheck.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.product.object.maps.appt.precheck.Main.ApptPrecheckMainPage;

public class ApptPrecheckConfiguration extends ApptPrecheckMainPage {

	@FindBy(how = How.CSS, using = "body>div.medfusion-logo")
	private WebElement medfusionLogo;

	@FindBy(how = How.CSS, using = "div.ping-header")
	private WebElement pleaseLoginText;

	public ApptPrecheckConfiguration(WebDriver driver) {
		super(driver);
	}

	public void presenseOfMedfusionLogo() {
		medfusionLogo.isDisplayed();
	}

	public String loginText() {
		return pleaseLoginText.getText();
	}

}
