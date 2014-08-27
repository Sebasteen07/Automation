package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;

public class FormWelcomePage extends PortalFormPage {

	@FindBy(id = "continueWelcomePageButton")
	private WebElement btnContinue;

	@FindBy(xpath = "//section[@class='content indented']/p[1]")
	private WebElement welcomeMessage;
	
	
	public FormWelcomePage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Checks if the Welcome page of the form is loaded
	 * @return True if Continue button (which is the main functional part of the page) is loaded, otherwise false
	 */
	public boolean isWelcomePageLoaded() {
		boolean result = false;
		try {
			result = btnContinue.isEnabled();
		} catch (NoSuchElementException e) {
			log("Welcome page of forms is not loaded");
		}
		return result;
	}
	
	/**
	 * Compares string from parameter with Welcome Page message text
	 * @return True if Welcome page message is equal to the message entered as parameter
	 */
	public boolean welcomeMessageContent(String message) {		
		return message.equals(welcomeMessage.getText());
	}

	@Override
	public <T extends PortalFormPage> T clickSaveAndContinueButton(Class<T> nextPageClass) throws Exception {
		return super.clickSaveAndContinueButton(nextPageClass, this.btnContinue);
	}
}
