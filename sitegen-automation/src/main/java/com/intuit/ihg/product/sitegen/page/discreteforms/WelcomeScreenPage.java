package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class WelcomeScreenPage extends BasePageObject {
	
	public WelcomeScreenPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="//li[@data-section='welcome']/a")
	private WebElement lnkWelcome;
	
	@FindBy(name="welcomeScreenText")
	private WebElement lnkWelcomeScreenText;

	@FindBy(xpath="//li[@data-section='demographics']/a")
	private WebElement lnkBasicInformationAboutYou;
	
	
	
	
	/**
	 * Replace welcome message for the patient
	 * @param message
	 */
	public void setWelcomeMessage(String message)
	{
		lnkWelcomeScreenText.clear();
		lnkWelcomeScreenText.sendKeys(message);
		
	}
	
	/**
	 * Clicks on the next page 1. Basic Information About You
	 * @return PageFactory initialization of BasicInformationAboutYouPage Class
	 */
	public BasicInformationAboutYouPage clickLnkBasicInfoAboutYou() 
	{
		lnkBasicInformationAboutYou.click();
		return PageFactory.initElements(driver, BasicInformationAboutYouPage.class);
	}
	
	/**
	 * Click on welcome message in menu	
	 */
	public void clickWelcomePageMessage()
	{
		lnkWelcome.click();
	}
}
