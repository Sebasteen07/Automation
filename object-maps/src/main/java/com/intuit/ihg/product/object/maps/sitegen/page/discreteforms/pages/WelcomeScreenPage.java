package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.ConfiguratorFormPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WelcomeScreenPage extends ConfiguratorFormPage {
	
	public WelcomeScreenPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//li[@data-section='currentsymptoms']/a")
	private WebElement lnkCurrentSymptoms;
	
	@FindBy(xpath = "//li[@data-section='welcome']/a")
	private WebElement lnkWelcome;
	
	@FindBy(name = "welcomeScreenText")
	private WebElement lnkWelcomeScreenText;

	@FindBy(xpath = "//li[@data-section='demographics']/a")
	private WebElement lnkBasicInformationAboutYou;
	
	@FindBy(id = "custom_form_name")
	private WebElement formNameField;
	
	/**
	 * Replace welcome message for the patient
	 * @param message a message that will be set as welcome message
	 */
	public WelcomeScreenPage setWelcomeMessage(String message) {
		lnkWelcomeScreenText.clear();
		lnkWelcomeScreenText.sendKeys(message);
		return this;
	}

	/**
	 * Set the name of the form
	 * @param newFormName - the name for the form
	 */
	public WelcomeScreenPage setFormName(String newFormName) {
		formNameField.clear();
		formNameField.sendKeys(newFormName);
		return this;
	}
	
	/**
	 * Clicks on the next page 1. Basic Information About You
	 * @return PageFactory initialization of BasicInformationAboutYouPage Class
	 */
	public BasicInformationAboutYouPage clickLnkBasicInfoAboutYou() {
		lnkBasicInformationAboutYou.click();
		return PageFactory.initElements(driver, BasicInformationAboutYouPage.class);
	}
	
	/**
	 * Click on welcome message page in menu	
	 */
	public void clickWelcomeMessagePage() {
		lnkWelcome.click();
	}
}
