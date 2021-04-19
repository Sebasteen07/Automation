//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
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
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * The method asserts patient data in the PHR site After modification in the Portal site
	 * 
	 * @param city
	 * @param zip
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
	 * Click on log out button and return PHR LogIn page
	 * 
	 * @return
	 */
	public PhrLoginPage clickLogout() {
		IHGUtil.PrintMethodName();
		btnLogout.click();
		return PageFactory.initElements(driver, PhrLoginPage.class);
	}
}
