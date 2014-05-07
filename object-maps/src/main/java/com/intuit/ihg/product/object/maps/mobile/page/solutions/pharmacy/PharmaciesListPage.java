package com.intuit.ihg.product.object.maps.mobile.page.solutions.pharmacy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.mobile.page.MobileBasePage;

/**
 * Created by Prokop Rehacek.
 * User: prehacek
 * Date: 2/3/14
 */
public class PharmaciesListPage extends MobileBasePage {
	

	@FindBy( xpath = "//div[@id='pharmaList']/div[@class='scrollPanel']/div/ul/li[1]/a")
	private WebElement firstPharmacy;
	
	public PharmaciesListPage (WebDriver driver) {
		super(driver);
	}
	
	public PharmacyDetailsPage selectFirstPharmacy()
	{
		
		IHGUtil.waitForElement(driver, 6, firstPharmacy);
		firstPharmacy.click();
		
		return PageFactory.initElements(driver, PharmacyDetailsPage.class);
	}
	
}
