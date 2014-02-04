package com.intuit.ihg.product.mobile.page.solutions.pharmacy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;

/**
 * Created by Prokop Rehacek.
 * User: prehacek
 * Date: 2/3/14
 */
public class ChooseLocationPage extends MobileBasePage {
	
	@FindBy( id = "txtLocation")
	private WebElement inputLocation;

	@FindBy( xpath = "//span[text()='Lookup Location']/../..")
	private WebElement btnLookupLocation;
	
	public ChooseLocationPage (WebDriver driver) {
		super(driver);
	}
	
	public AddPharmacyPage selectLocation(String locationAddress) throws InterruptedException
	{
		IHGUtil.waitForElement(driver, 6, inputLocation);
		inputLocation.sendKeys(locationAddress);
		
		IHGUtil.waitForElement(driver, 6, btnLookupLocation);
		Thread.sleep(2000);
		btnLookupLocation.click();
		
		return PageFactory.initElements(driver, AddPharmacyPage.class);
	}
	
}
