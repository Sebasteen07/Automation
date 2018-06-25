package com.medfusion.product.object.maps.forms.page.questionnaires;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

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

		FluentWait<WebDriver> wdw = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(3, TimeUnit.SECONDS)
				.ignoring(NoSuchFrameException.class)
				.ignoring(NoSuchElementException.class);


		WebElement welcome = wdw.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					PortalUtil.setPortalFrame(driver);
				} catch (TimeoutException e) {					
					log("portal frame not found and exception caught");
				}
				try {					
					PortalUtil.setquestionnarieFrame(driver);
				} catch (NoSuchElementException e) {
					driver.switchTo().defaultContent();
					log("questionnaire frame not found and exception caught");
				}			    	 
				return driver.findElement(By.xpath("//section[@class='content indented']/p[1]"));
			}
		}
				);	
		return welcome.getText().trim();
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
