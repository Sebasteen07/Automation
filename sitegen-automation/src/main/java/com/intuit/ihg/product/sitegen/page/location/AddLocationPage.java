package com.intuit.ihg.product.sitegen.page.location;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

/**
 * @author bkrishnankutty
 * @Date 6/12/2013
 * @Description :- Page Object for SiteGen Location Page
 * @Note :
 */
public class AddLocationPage extends BasePageObject {

	@FindBy(name = "Practice_Name")
	private WebElement txtPracticeName;

	@FindBy(name = "ST_Address1")
	private WebElement txtStreetAddress;

	@FindBy(name = "City")
	private WebElement txtCity;

	@FindBy(name = "state")
	private WebElement dropDownState;

	@FindBy(name = "Country")
	private WebElement dropDownCountry;

	@FindBy(name = "Zip_Code")
	private WebElement txtZipCode;

	@FindBy(name = "Office_Phone")
	private WebElement txtOfficePhone;

	@FindBy(name = "Loc_Contact")
	private WebElement txtContactPerson;

	@FindBy(name = "Loc_Contact_email")
	private WebElement txtEmail;

	@FindBy(name = "btn_Done")
	private WebElement btnAddLocation;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public AddLocationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:-Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		boolean result = false;

		try {
			result = IHGUtil.waitForElement(driver, 6, btnAddLocation);
			// result = btnAddLocation.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * 
	 * @author bkrishnankutty
	 * @Desc:- Add New Location
	 * @return ManageYourLocationsPage
	 * 
	 * @param practiceName
	 * @param streetAddress
	 * @param city
	 * @param state
	 * @param country
	 * @param zipCode
	 * @param officePhone
	 * @param contactPerson
	 * @param email
	 * @return
	 */
	public ManageYourLocationsPage addLocation(String practiceName,
			String streetAddress, String city, String state, String country,
			String zipCode, String officePhone, String contactPerson,
			String email) {

		SitegenlUtil.setSiteGenFrame(driver);

		txtPracticeName.sendKeys(practiceName);
		txtStreetAddress.sendKeys(streetAddress);
		txtCity.sendKeys(city);
		Select selectState = new Select(dropDownState);
		selectState.selectByVisibleText(state);
		Select selectCountry = new Select(dropDownCountry);
		selectCountry.selectByVisibleText(country);
		txtZipCode.sendKeys(zipCode);
		txtOfficePhone.sendKeys(officePhone);
		txtContactPerson.sendKeys(contactPerson);
		txtEmail.sendKeys(email);
		btnAddLocation.click();
		return PageFactory.initElements(driver, ManageYourLocationsPage.class);
	}
}
