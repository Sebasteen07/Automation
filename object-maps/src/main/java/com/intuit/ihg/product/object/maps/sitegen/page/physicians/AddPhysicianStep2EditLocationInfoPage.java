//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.physicians;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class AddPhysicianStep2EditLocationInfoPage extends BasePageObject {

	@FindBy(xpath = "//input[@value='Edit Location Information']")
	private WebElement btnEditLocationInformation;

	@FindBy(xpath = "//input[@value='Go back to Manage Physicians']")
	private WebElement btnGoBackToManagePhysicians;

	@FindBy(name = "btnDelete")
	private WebElement btnConfirmDeletePhysican;

	public AddPhysicianStep2EditLocationInfoPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, btnGoBackToManagePhysicians);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public ManageYourPhysiciansPage clickBtnGoBackToManagePhysicians() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		btnGoBackToManagePhysicians.click();
		return PageFactory.initElements(driver, ManageYourPhysiciansPage.class);
	}

	public ManageYourPhysiciansPage deletePhysican() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		btnConfirmDeletePhysican.click();
		return PageFactory.initElements(driver, ManageYourPhysiciansPage.class);
	}
}
