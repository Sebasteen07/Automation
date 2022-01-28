//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.location;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class ManageYourLocationsPage extends BasePageObject {

	@FindBy(name = "btn_add")
	private WebElement btnAddNewLocation;

	@FindBy(linkText = "Add Location")
	private WebElement lnkAddLocation;

	@FindBy(linkText = "Edit Location")
	private WebElement lnkEditLocation;

	public ManageYourLocationsPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, btnAddNewLocation);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public AddLocationPage clicklnkAddLocation() {
		SitegenlUtil.setDefaultFrame(driver);
		lnkAddLocation.click();
		return PageFactory.initElements(driver, AddLocationPage.class);
	}

	public boolean checkTheData(String practiceName, String State) throws Exception {
		boolean flag = false;
		if ((SitegenlUtil.verifyTextPresent(driver, practiceName, 1000) == true)
				&& (SitegenlUtil.verifyTextPresent(driver, State, 00) == true)) {
			flag = true;
			return flag;
		}
		return flag;
	}

	public void cleaningTestdata(String practiceName, String State) throws Exception {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		if (checkTheData(practiceName, State) == true) {
			lnkEditLocation.click();
			EditSiteGenAutomationPage pEditSiteGenAutomationPage = PageFactory.initElements(driver,
					EditSiteGenAutomationPage.class);
			assertTrue(pEditSiteGenAutomationPage.isSearchPageLoaded(),
					"Expected the Edit your location Page  to be loaded, but it was not.");
			pEditSiteGenAutomationPage.deleteLocation();
			assertTrue(this.isSearchPageLoaded(),
					"Expected the Manage your location Page  to be loaded, but it was not.");
			assertFalse(SitegenlUtil.verifyTextPresent(driver, practiceName, 1000),
					"Clean process is unable to clean data ,kindly check it manually");
			assertFalse(SitegenlUtil.verifyTextPresent(driver, State, 1000),
					"Clean process is unable to clean data ,kindly check it manually");
		} else {
			log("The data is already clean");
		}
	}
}
