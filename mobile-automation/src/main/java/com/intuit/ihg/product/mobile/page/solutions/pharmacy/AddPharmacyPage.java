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
public class AddPharmacyPage extends MobileBasePage {
	
	@FindBy( id = "selectedRxLocation")
	private WebElement selectRxLocation;
	
	@FindBy( id = "findPharmaLink")
	private WebElement findPharmaLink;

	public AddPharmacyPage (WebDriver driver) {
		super(driver);
	}
	
	public ChooseLocationPage selectLocation() throws InterruptedException
	{
		IHGUtil.waitForElement(driver, 10, selectRxLocation);
		Thread.sleep(2000);
		selectRxLocation.click();
		return PageFactory.initElements(driver, ChooseLocationPage.class);
	}
	
	public PharmaciesListPage searchPharmacies() throws InterruptedException
	{
		IHGUtil.waitForElement(driver, 6, findPharmaLink);
		Thread.sleep(2000);
		findPharmaLink.click();
		return PageFactory.initElements(driver, PharmaciesListPage.class);
	}
	
	
	
}
