//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.physicians;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class ManageYourPhysiciansPage extends BasePageObject {

	@FindBy(xpath = "//input[@value='Add New Physician']")
	private WebElement btnAddNewPhysician;

	@FindBy(linkText = "Add Physician")
	private WebElement lnkAddPhysician;

	@FindBy(linkText = "List All Physicians")
	private WebElement lnkListAllPhysicians;

	@FindBy(linkText = "Edit Physician")
	private WebElement lnkEditPhysician;

	@FindBy(linkText = "Import Personnel and Physicians")
	private WebElement lnkImportPersonnelAndPhysicians;

	@FindBy(linkText = "Export Personnel")
	private WebElement lnkExportPersonnel;

	@FindBy(linkText = "List by Location")
	private WebElement lnkListByLocation;

	public ManageYourPhysiciansPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, btnAddNewPhysician);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public AddPhysicianPage clicklnkAddPhysician() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		lnkAddPhysician.click();
		return PageFactory.initElements(driver, AddPhysicianPage.class);
	}

	public AddPhysicianPage clicklnkEditPhysician() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		lnkEditPhysician.click();
		return PageFactory.initElements(driver, AddPhysicianPage.class);
	}

	public String getProviderName(String lastname, String firstName, String title) {
		String str = lastname + ", " + firstName + ": " + title;

		return str;
	}

	public void cleanTestPhysiciansData() throws Exception {
		while (islnkEditPhysicianDisplayed()) {
			AddPhysicianPage pAddPhysicianPage = clicklnkEditPhysician();
			AddPhysicianStep2EditLocationInfoPage pAddPhysicianStep2EditLocationInfoPage = pAddPhysicianPage
					.deletePhysician();
			pAddPhysicianStep2EditLocationInfoPage.deletePhysican();
			assertTrue(SitegenlUtil.verifyTextPresent(driver, "Information Updated", 2));
		}
	}

	public boolean islnkEditPhysicianDisplayed() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 5, lnkEditPhysician);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public AddPhysicianStep2EditLocationInfoPage clicklnkImportPersonnelAndPhysicians() {
		IHGUtil.PrintMethodName();
		lnkImportPersonnelAndPhysicians.click();
		return PageFactory.initElements(driver, AddPhysicianStep2EditLocationInfoPage.class);
	}

	public AddPhysicianStep2EditLocationInfoPage clicklnkExportPersonnel() {
		IHGUtil.PrintMethodName();
		lnkExportPersonnel.click();
		return PageFactory.initElements(driver, AddPhysicianStep2EditLocationInfoPage.class);
	}

	public ManageYourPhysiciansPage clicklnkListAllPhysicians() {
		IHGUtil.PrintMethodName();
		lnkListAllPhysicians.click();
		return PageFactory.initElements(driver, ManageYourPhysiciansPage.class);
	}
}
