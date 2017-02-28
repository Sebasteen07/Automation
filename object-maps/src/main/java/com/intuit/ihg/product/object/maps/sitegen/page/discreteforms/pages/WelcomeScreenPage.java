package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WelcomeScreenPage extends ConfiguratorFormPage {

	public WelcomeScreenPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(name = "welcomeScreenText")
	private WebElement lnkWelcomeScreenText;

	/**
	 * Replace welcome message for the patient
	 * 
	 * @param message a message that will be set as welcome message
	 */
	public WelcomeScreenPage setWelcomeMessage(String message) {
		lnkWelcomeScreenText.clear();
		lnkWelcomeScreenText.sendKeys(message);
		return this;
	}
}
