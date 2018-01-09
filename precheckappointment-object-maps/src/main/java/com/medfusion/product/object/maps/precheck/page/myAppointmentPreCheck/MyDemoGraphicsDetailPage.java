package com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class MyDemoGraphicsDetailPage extends BasePageObject  {
	
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
	//state
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
	private WebElement preferredPharmacyPhoneNumberInput;
	
	@FindBy(how = How.ID, using = "demographicsConfirmButton")
	private WebElement demographicsConfirmButton;
	
	public MyDemoGraphicsDetailPage(WebDriver driver) {
		super(driver);
	}
	
	public void setFirstName(String fName) {
		firstNameInput.clear();
		firstNameInput.sendKeys(fName);
	}
	
	public void setMiddleName(String mName) {
		middleNameInput.clear();
		middleNameInput.sendKeys(mName);
	}
	
	public void setLastName(String lName) {
		lastNameInput.clear();
		lastNameInput.sendKeys(lName);
	}
	
	public void setDateOfBirth(String dob) {
		String[] date = dob.split(" ");
		String[] date0=date[1].split("/");
		dobInput.clear();
		dobInput.sendKeys(date[0]);
		dobInput.sendKeys(date0[0]);
		dobInput.sendKeys(date0[1]);
	}
	
	public void setStreet1Address(String street1Address) {
		streetInput.clear();
		streetInput.sendKeys(street1Address);
	}
	
	public void setStreet2Address(String street2Address) {
		street2Input.clear();
		street2Input.sendKeys(street2Address);
	}
	
	public void setCity(String cityValue) {
		cityInput.clear();
		cityInput.sendKeys(cityValue);
	}
	
	public void setState(String stateValue) {
		stateInput.clear();
		stateInput.sendKeys(stateValue);
	}
	
	public void setZip(String zipValue) {
		zipInput.clear();
		zipInput.sendKeys(zipValue);
	}
	
	public void setPhone(String phoneValue) {
		phoneInput.clear();
		phoneInput.sendKeys(phoneValue);
	}
	
	public void setEmail(String emailValue) {
		emailInput.clear();
		emailInput.sendKeys(emailValue);
	}
	
	public void setPreferredPharmacyNameInput(String pharmacyNameValue) {
		preferredPharmacyNameInput.clear();
		preferredPharmacyNameInput.sendKeys(pharmacyNameValue);
	}
	
	public void setPreferredPharmacyContactInput(String pharmacyContactValue) {
		preferredPharmacyPhoneNumberInput.clear();
		preferredPharmacyPhoneNumberInput.sendKeys(pharmacyContactValue);
	}
	
	public MyAppointmentPage clickDemographicsSaveButton() {
		demographicsConfirmButton.click();
		return PageFactory.initElements(driver, MyAppointmentPage.class);
	}
}
