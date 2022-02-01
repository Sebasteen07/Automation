//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires;

import java.time.Duration;
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
import com.medfusion.product.patientportal2.utils.PortalUtil2;

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
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(3))
				.ignoring(NoSuchFrameException.class)
				.ignoring(NoSuchElementException.class);


		WebElement welcome = wdw.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					PortalUtil2.setPortalFrame(driver);
				} catch (TimeoutException e) {					
					log("portal frame not found and exception caught");
				}
				try {					
					PortalUtil2.setquestionnarieFrame(driver);
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

	public <T extends PortalFormPage> T initToFirstPage(Class<T> nextPageClass) throws Exception {
		if (isPageLoaded()) {
			return clickSaveContinue(nextPageClass);
		} else {
			goToFirstPage();
			return PageFactory.initElements(driver, nextPageClass);
		}
	}
}
