//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.pharmacy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class ManageYourPharmacies extends BasePageObject {
	
	@FindBy(xpath = "//input[@value='Add New Pharmacy']")
	private WebElement btnAddNewPharmacy;
	
	@FindBy(xpath="//div[@id='availablePharmaciesParams']//tbody/tr[1]/td[2]")
	private WebElement lnkEditPharmacy;
	
	@FindBy(xpath="//div[@id='availablePharmaciesParams']//tbody/tr[1]/td[1]")
	private WebElement pharmacyInList;
	
	@FindBy(xpath="//input[@name='exportPharmaciesBtn']")
	private WebElement btnExportPharmacy;
	
	@FindBy(xpath="//input[@name='importPharmaciesBtn']")
	private WebElement btnImportPharmacy;

	public ManageYourPharmacies(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public AddPharmacyPage clickOnAddPharmacyButton() {
		IHGUtil.PrintMethodName();
		btnAddNewPharmacy.click();
		return PageFactory.initElements(driver, AddPharmacyPage.class);
	}	
	
	public String clickonEditPharmacyLink() throws InterruptedException {
		String pharmacyName= pharmacyInList.getText();
		lnkEditPharmacy.click();
		return pharmacyName ;
	}
	
	public boolean confirmPharmacyInTable(String pharmacyName) {
		if((pharmacyInList.isDisplayed()))
		{
		  return true;
		}
			
		return false;	
	
	}
}
