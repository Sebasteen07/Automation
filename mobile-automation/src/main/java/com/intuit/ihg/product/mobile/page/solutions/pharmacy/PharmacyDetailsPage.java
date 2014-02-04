package com.intuit.ihg.product.mobile.page.solutions.pharmacy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;

/**
 * Created by Prokop Rehacek.
 * User: prehacek
 * Date: 2/3/14
 */
public class PharmacyDetailsPage extends MobileBasePage {
	
	@FindBy( id = "pharmaMap")
	private WebElement pharmaMap;
	
	@FindBy( id = "pharmaTel")
	private WebElement pharmaTel;
	
	@FindBy( id = "addFoundPharmacy")
	private WebElement addFoundPharmacy;
		
	public PharmacyDetailsPage (WebDriver driver) {
		super(driver);
	}
	
	public boolean verifyPharmacyDetails()
	{
		IHGUtil.waitForElement(driver, 6, pharmaMap);
		IHGUtil.waitForElement(driver, 6, pharmaTel);

        return (pharmaMap.isDisplayed() && pharmaTel.isDisplayed());
	}
	
}
