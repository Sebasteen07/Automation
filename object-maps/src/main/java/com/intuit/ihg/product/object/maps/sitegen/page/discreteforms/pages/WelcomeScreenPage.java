package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.ConfiguratorFormPage;

public class WelcomeScreenPage extends ConfiguratorFormPage {

	public WelcomeScreenPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(name = "welcomeScreenText")
	private WebElement lnkWelcomeScreenText;

	@FindBy(id = "custom_form_name")
	private WebElement formNameField;

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

	/**
	 * Set the name of the form
	 * 
	 * @param newFormName - the name for the form
	 */
	public WelcomeScreenPage setFormName(String newFormName) {
		formNameField.clear();
		formNameField.sendKeys(newFormName);
		return this;
	}
}
