// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.customform;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;


public class SearchPatientFormsPage extends BasePageObject {

	public static final String PAGE_NAME = "Search Patient Forms Page";

	@FindBy(name = "firstName")
	private WebElement txtFirstName;

	@FindBy(name = "lastName")
	private WebElement txtLastName;

	@FindBy(name = "dob_m")
	private WebElement dropDownDobMonth;

	@FindBy(name = "dob_d")
	private WebElement dropDownDobDay;

	@FindBy(name = "dob_y")
	private WebElement dropDownDobYear;

	@FindBy(name = "start_m")
	private WebElement dropDownStartMonth;

	@FindBy(name = "start_d")
	private WebElement dropDownStartDay;

	@FindBy(name = "start_y")
	private WebElement dropDownStartYear;

	@FindBy(name = "end_m")
	private WebElement dropDownEndMonth;

	@FindBy(name = "end_d")
	private WebElement dropDownEndDay;

	@FindBy(name = "end_y")
	private WebElement dropDownEndYear;

	@FindBy(id = "formSearchSubmit")
	private WebElement btnSearch;

	@FindBy(css = "td.searchResultsDetails > a")
	private WebElement lnkViewDetails;

	@FindBy(name = "qnaireType")
	private WebElement dropDownForm;

	@FindBy(linkText = "Search Partially Completed Forms")
	private WebElement linkPartiallyFilledForm;

	public SearchPatientFormsPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Gives an indication if the page loaded as expected
	 * 
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		boolean result = false;
		try {
			result = txtFirstName.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found exceptions
		}

		return result;
	}

	public SearchPartiallyFilledPage getPartiallyFilledSearch() {
		linkPartiallyFilledForm.click();
		return PageFactory.initElements(driver, SearchPartiallyFilledPage.class);
	}

	/**
	 * Desc :-SearchPatientFormsWithOpenStatus method will filter the latest custorm form
	 * 
	 * @param firstName
	 * @param lastName
	 * @param dob_month
	 * @param dob_day
	 * @param dob_year
	 */
	public SearchPatientFormsResultPage SearchPatientFormsWithOpenStatus(String firstName, String lastName, String dob_month, String dob_day, String dob_year) {

		txtFirstName.sendKeys(firstName);
		txtLastName.sendKeys(lastName);

		Select start_m = new Select(dropDownStartMonth);
		start_m.selectByVisibleText(IHGUtil.getDate_Month());

		Select start_d = new Select(dropDownStartDay);
		start_d.selectByVisibleText(IHGUtil.getDate_d());

		Select start_y = new Select(dropDownStartYear);
		start_y.selectByVisibleText(IHGUtil.getDate_y());

		Select end_m = new Select(dropDownEndMonth);
		end_m.selectByVisibleText(IHGUtil.getDate_Month());
		log("DATE" + IHGUtil.getDate_d());

		// Select end_d = new Select(dropDownEndDay);
		// end_d.selectByVisibleText(IHGUtil.getDate_d());

		Select end_y = new Select(dropDownEndYear);
		end_y.selectByVisibleText(IHGUtil.getDate_y());

		btnSearch.click();

		return PageFactory.initElements(driver, SearchPatientFormsResultPage.class);
	}



	/**
	 * Search for discrete form
	 * 
	 * @param discreteFormName
	 */

	public SearchPatientFormsResultPage SearchDiscreteFormsWithOpenStatus(String discreteFormName) {

		Select start_m = new Select(dropDownForm);
		start_m.selectByVisibleText(discreteFormName);
		btnSearch.click();

		return PageFactory.initElements(driver, SearchPatientFormsResultPage.class);
	}



}
