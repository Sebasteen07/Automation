package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormWelcomePage extends BasePageObject
{


	public FormWelcomePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="continueWelcomePageButton")
	private WebElement btnContinue;


	/**
	 * @Description:Click on Continue Button
	 * @return
	 * @throws Exception
	 */
	public FormBasicInfoPage clickContinueButton() throws Exception{
		IHGUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(btnContinue));
		btnContinue.click();
		return PageFactory.initElements(driver, FormBasicInfoPage.class);

	}

	/**
	 * Checks if the Welcome page of the form is loaded
	 * @return True if Continue button (which is the main functional part of the page) is loaded, otherwise false
	 */
	public boolean isWelcomePageLoaded() {
		boolean result = false;
		try {
			result = btnContinue.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Welcome page of forms not loaded");
		}
		return result;
	}
	
}
