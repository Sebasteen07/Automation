//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.precheck.page.patient;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class DemographicsPage extends BasePageObject {

	@FindBy(how = How.ID, using = "demographicsConfirmButton")
	private WebElement demographicsConfirmButton;

	@FindBy(how = How.ID, using = "firstName")
	private WebElement firstNameInput;

	@FindBy(how = How.ID, using = "middleName")
	private WebElement middleNameInput;

	@FindBy(how = How.ID, using = "lastName")
	private WebElement lastNameInput;

	@FindBy(how = How.ID, using = "dob")
	private WebElement dobInput;

	@FindBy(how = How.ID, using = "street")
	private WebElement streetInput;

	@FindBy(how = How.ID, using = "street2")
	private WebElement street2Input;

	@FindBy(how = How.ID, using = "city")
	private WebElement cityInput;

	@FindBy(how = How.ID, using = "state")
	private WebElement stateInput;

	@FindBy(how = How.ID, using = "zip")
	private WebElement zipInput;

	@FindBy(how = How.ID, using = "phone")
	private WebElement phoneInput;

	@FindBy(how = How.ID, using = "email")
	private WebElement emailInput;

	@FindBy(how = How.ID, using = "preferredPharmacyName")
	private WebElement preferredPharmacyNameInput;

	@FindBy(how = How.ID, using = "preferredPharmacyPhoneNumber")
	private WebElement preferredPharmacyPhoneNumber;


	public DemographicsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void fillInDemogprahicsData(String firstName, String middleName, String lastName, String dob, String address1, String address2, String city,
			String state, String zip, String phone, String email, String pharmacy, String pharmacyPhoneNumber) {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(firstNameInput);
		webElementsList.add(middleNameInput);
		webElementsList.add(lastNameInput);
		webElementsList.add(dobInput);
		webElementsList.add(streetInput);
		webElementsList.add(street2Input);
		webElementsList.add(cityInput);
		webElementsList.add(stateInput);
		webElementsList.add(zipInput);
		webElementsList.add(phoneInput);
		webElementsList.add(emailInput);
		webElementsList.add(preferredPharmacyNameInput);
		webElementsList.add(preferredPharmacyPhoneNumber);

		for (WebElement w : webElementsList) {
			w.clear();
		}

		firstNameInput.sendKeys(firstName);
		middleNameInput.sendKeys(middleName);
		lastNameInput.sendKeys(lastName);
		dobInput.sendKeys(dob);
		streetInput.sendKeys(address1);
		street2Input.sendKeys(address2);
		cityInput.sendKeys(city);
		stateInput.sendKeys(state);
		zipInput.sendKeys(zip);
		phoneInput.sendKeys(phone);
		emailInput.sendKeys(email);
		preferredPharmacyNameInput.sendKeys(pharmacy);
		preferredPharmacyPhoneNumber.sendKeys(pharmacyPhoneNumber);
	}

	public PatientHomePage clickConfirmDemographics() {
		demographicsConfirmButton.click();
		return PageFactory.initElements(driver, PatientHomePage.class);
	}

}
