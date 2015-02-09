package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.common.utils.IHGConstants;

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
		
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		try {
			result = btnContinue.isEnabled();
		} catch (NoSuchElementException e) {
			log("Welcome page of forms is not loaded");
		}
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
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
	
	/**
	 * @param nextPageClass - class of the page that follows immediately after the welcome page
	 * @return Initiated object for the next page
	 * @throws Exception
	 */
	public <T extends PortalFormPage> T initializeFormToFirstPage(Class<T> nextPageClass) throws Exception {
		if (isWelcomePageLoaded()) {
			return clickSaveAndContinueButton(nextPageClass);
        }
		else {
            goToFirstPage();
            return PageFactory.initElements(driver, nextPageClass);
        }
	}
}
