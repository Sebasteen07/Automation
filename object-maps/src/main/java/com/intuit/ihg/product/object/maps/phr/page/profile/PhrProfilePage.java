//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.phr.page.profile;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.phr.page.PhrLoginPage;

public class PhrProfilePage extends BasePageObject {

	public static final String PAGE_NAME = "PHR PROFILE PAGE";

	@FindBy(xpath = "//em[text()='Log out']")
	private WebElement btnLogout;

	@FindBy(id = "city")
	private WebElement txtCity;

	@FindBy(id = "phone3")
	private WebElement txtMobile;

	@FindBy(id = "phone2")
	private WebElement txtHomePhone;

	@FindBy(id = "zipCode")
	private WebElement txtzipCode;

	@FindBy(css = "img[alt='SAVE CHANGES']")
	private WebElement btnSaveChanges;

	public PhrProfilePage(WebDriver driver) {
		super(driver);
	}

	/**
	 * The method asserts patient data in the PHR site After modification in the Portal site
	 */
	public void assertDataCityAndZip(String city, String zip) {
		IHGUtil.PrintMethodName();
		log("Value of City: " + txtCity.getAttribute("value"));
		log("Value of Zip: " + txtzipCode.getAttribute("value"));

		assertEquals(txtCity.getAttribute("value"), city, "The City is not updated in PHR ##### ");
		assertEquals(txtzipCode.getAttribute("value"), zip, "The Zip code is not updated in PHR  ##### ");
	}

	public void assertMobilePhoneNumber(String phoneNumber) {
		IHGUtil.PrintMethodName();
		log("Value of Phone number: " + txtMobile.getAttribute("value"));

		assertEquals(txtMobile.getAttribute("value").replaceAll("-", ""), phoneNumber, "Mobile Phone number is not updated in PHR ##### ");
	}

	public void assertHomePhoneNumber(String phoneNumber) {
		IHGUtil.PrintMethodName();
		log("Value of Phone number: " + txtHomePhone.getAttribute("value"));

		assertEquals(txtHomePhone.getAttribute("value").replaceAll("-", ""), phoneNumber, "Home Phone number is not updated in PHR ##### ");
	}


	/**
	 * The method will modify data in the PHR site with Primary User Data ie from Portal sheet
	 */
	public void setCityZip(String portalCity, String portalZip) throws Exception {
		IHGUtil.PrintMethodName();
		log("Setting the City to " + portalCity);
		log("Setting the ZIP to " + portalZip);
		txtCity.clear();
		txtCity.sendKeys(portalCity);
		txtzipCode.clear();
		txtzipCode.sendKeys(portalZip);
		saveChanges();
	}

	public void setMobilePhoneNumber(String phoneNumber) throws Exception {
		IHGUtil.PrintMethodName();
		log("Setting the Mobile Phone number to " + phoneNumber);
		txtMobile.clear();
		txtMobile.sendKeys(phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6));
		saveChanges();
	}

	public void setHomePhoneNumber(String phoneNumber) throws Exception {
		IHGUtil.PrintMethodName();
		log("Setting the Home Phone number to " + phoneNumber);
		txtHomePhone.clear();
		txtHomePhone.sendKeys(phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6));
		saveChanges();
	}

	public PhrLoginPage clickLogout() {

		IHGUtil.PrintMethodName();
		btnLogout.click();
		return PageFactory.initElements(driver, PhrLoginPage.class);
	}

	public void saveChanges() {
		btnSaveChanges.click();
	}

}
