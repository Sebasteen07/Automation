//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.permissionsAndPersonnelTypes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class ManageYourGroupPersonnelTypesPage extends BasePageObject {

	@FindBy(name = "btn_add")
	private WebElement btnAddPersonnelType;

	@FindBy(xpath = "(//a[contains(text(),'Manage Permissions')])[4]")
	private WebElement lnkManagePermissions;

	public ManageYourGroupPersonnelTypesPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, btnAddPersonnelType);
		} catch (Exception e) {
			// Catch any element not found errors
		}
		return result;
	}

	public ManageUserPermissionsPage clicklnkManagePermissions4Nurses() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkManagePermissions);
		lnkManagePermissions.click();
		return PageFactory.initElements(driver, ManageUserPermissionsPage.class);
	}
}
