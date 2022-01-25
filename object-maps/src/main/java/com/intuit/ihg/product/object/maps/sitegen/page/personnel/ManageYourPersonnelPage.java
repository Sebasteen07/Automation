//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.personnel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class ManageYourPersonnelPage extends BasePageObject {

	@FindBy(xpath = "//input[@value='Add New Personnel']")
	private WebElement btnAddNewPersonnel;

	@FindBy(linkText = "Personnel (Non-Physicians)")
	private WebElement lnkPersonnel;

	@FindBy(linkText = "Import Personnel and Physicians")
	private WebElement lnkImportPersonnelAndPhysicians;

	@FindBy(linkText = "Export Personnel")
	private WebElement lnkExportPersonnel;

	@FindBy(linkText = "List by Location")
	private WebElement lnkListByLocation;

	@FindBy(linkText = "List All Personnel")
	private WebElement lnkListAllPersonnel;

	@FindBy(xpath = "//input[@value='Import Personnel and Physicians']")
	private WebElement btnImportPersonnelAndPhysicians;

	public ManageYourPersonnelPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, btnAddNewPersonnel);
		} catch (Exception e) {
			// Catch any element not found errors
		}
		return result;
	}

	public ImportPersonnelAndPhysiciansPage clicklnkImportPersonnelAndPhysicians() {
		IHGUtil.PrintMethodName();
		lnkImportPersonnelAndPhysicians.click();
		return PageFactory.initElements(driver, ImportPersonnelAndPhysiciansPage.class);
	}

	public ImportPersonnelAndPhysiciansPage clickBtnImportPersonnelAndPhysicians() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		btnImportPersonnelAndPhysicians.click();
		return PageFactory.initElements(driver, ImportPersonnelAndPhysiciansPage.class);
	}

	public ExportPersonnelPage clicklnkExportPersonnel() {

		IHGUtil.PrintMethodName();
		lnkExportPersonnel.click();
		return PageFactory.initElements(driver, ExportPersonnelPage.class);
	}

	public ManageYourPersonnelPage clicklnkListAllPersonnel() {

		IHGUtil.PrintMethodName();
		lnkListAllPersonnel.click();
		return PageFactory.initElements(driver, ManageYourPersonnelPage.class);
	}
}
