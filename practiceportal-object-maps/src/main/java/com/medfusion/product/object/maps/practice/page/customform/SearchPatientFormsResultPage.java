//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.customform;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class SearchPatientFormsResultPage extends BasePageObject {

	@FindBy(css = "td.searchResultsDetails > a")
	private WebElement lnkViewDetails;


	public SearchPatientFormsResultPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Click on View Link and go to Patient CustomForm Detail Page
	 * 
	 * @return ViewPatientFormPage
	 */
	public ViewPatientFormPage clickViewLink() throws Exception {
		IHGUtil.PrintMethodName();

		try {
			lnkViewDetails.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " + "the staff member is not permissioned for 'CustomForm' solution,"
					+ " or there was an error on login");
		}
		lnkViewDetails.click();
		return PageFactory.initElements(driver, ViewPatientFormPage.class);
	}

	public ViewPatientFormPage clickOnAutomationCustomForm() {
		IHGUtil.PrintMethodName();
		log("Finding the custom form in the search page");
		String xpath = ".//form[@id='searchForm']//table[@id='table-1']/tbody/tr/td[contains(text() ,'automation,')]";
		try {
			driver.findElement(By.xpath(xpath)).click();
		} catch (Exception e) {
			driver.findElement(By.xpath(xpath)).click();
			log("Custom form details not found");
		}

		return PageFactory.initElements(driver, ViewPatientFormPage.class);
	}

	public ViewPatientFormPage clickOnSitegenCustomForm(String formName) {
		IHGUtil.PrintMethodName();
		log("Finding the custom form in the search page");
		String xpath = ".//form[@id='searchForm']//table[@id='table-1']/tbody/tr/td[text()='" + formName + "']";
		try {
			driver.findElement(By.xpath(xpath)).click();
		} catch (Exception e) {
			driver.findElement(By.xpath(xpath)).click();
			log("Custom form details not found");
		}
		return PageFactory.initElements(driver, ViewPatientFormPage.class);
	}

}
