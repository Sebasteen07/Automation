//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.pharmacy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class ManageYourPharmacies extends BasePageObject {
	
	@FindBy(xpath = "//input[@value='Add New Pharmacy']")
	private WebElement btnAddNewPharmacy;
	
	@FindBy(linkText = "Edit Pharmacy")
	private WebElement lnkEditPharmacy;
	
	@FindBy(xpath="//input[@name='exportPharmaciesBtn']")
	private WebElement btnExportPharmacy;
	
	@FindBy(xpath="//input[@name='importPharmaciesBtn']")
	private WebElement btnImportPharmacy;

	public ManageYourPharmacies(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public AddPharmacyPage clickOnAddPharmacyButton() {
		IHGUtil.PrintMethodName();
		btnAddNewPharmacy.click();
		return PageFactory.initElements(driver, AddPharmacyPage.class);
	}	
}

