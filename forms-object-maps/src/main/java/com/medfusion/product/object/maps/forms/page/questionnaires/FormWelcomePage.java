package com.medfusion.product.object.maps.forms.page.questionnaires;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGConstants;
import com.medfusion.portal.utils.PortalUtil;

public class FormWelcomePage extends PortalFormPage {

	@FindBy(id = "continueWelcomePageButton")
	private WebElement btnContinue;

	@FindBy(xpath = "//section[@class='content indented']/p[1]")
	private WebElement welcomeMessage;


	public FormWelcomePage(WebDriver driver) {
		super(driver);
	}

	public String getMessageText() {
		try {
			PortalUtil.setquestionnarieFrame(driver);
			return welcomeMessage.getText().trim();
		} catch (WebDriverException e) {
			log("exception caught, retrying");
			try {
				PortalUtil.setPortalFrame(driver);				
			} catch (Exception e1) {				
				driver.switchTo().defaultContent();
			}
			try {
				PortalUtil.setquestionnarieFrame(driver);
			} catch (Exception e2) {
				//nothing to do at this point				
			}
			return welcomeMessage.getText().trim();
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks if the Welcome page of the form is loaded
	 * 
	 * @return True if Continue button (which is the main functional part of the page) is loaded, otherwise false
	 */
	@Override
	public boolean isPageLoaded() {
		boolean result = false;

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.switchTo().activeElement();
		try {
			result = btnContinue.isEnabled();
		} catch (NoSuchElementException e) {
			log("Welcome page of forms is not loaded");
		}
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
		return result;
	}

	@Override
	public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass) throws Exception {
		return super.clickSaveContinue(nextPageClass, this.btnContinue);
	}

	/**
	 * @param nextPageClass - class of the page that follows immediately after the welcome page
	 * @return Initiated object for the next page
	 * @throws Exception
	 */
	public <T extends PortalFormPage> T initToFirstPage(Class<T> nextPageClass) throws Exception {
		if (isPageLoaded()) {
			return clickSaveContinue(nextPageClass);
		} else {
			goToFirstPage();
			return PageFactory.initElements(driver, nextPageClass);
		}
	}
}
