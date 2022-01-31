//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.location;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class EditSiteGenAutomationPage extends BasePageObject {

	@FindBy(name = "btn_Delete")
	private WebElement btnDelete;

	public EditSiteGenAutomationPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, btnDelete);
		} catch (Exception e) {
			// Catch any element not found errors
		}
		return result;
	}

	public ManageYourLocationsPage deleteLocation() {
		SitegenlUtil.setSiteGenFrame(driver);
		javascriptClick(btnDelete);
		return PageFactory.initElements(driver, ManageYourLocationsPage.class);
	}

}
