package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OtherDoctorsYouSeen extends ConfiguratorFormPage {

	@FindBy(id = "hide_currentproviders_check")
	private WebElement hideCurrentprovidersCheck;

	@FindBy(id = "referring_physician_firstname")
	private WebElement referringPhysicianFirstname;

	@FindBy(id = "referring_physician_phone_number")
	private WebElement referringPhysicianPhoneNumber;

	@FindBy(id = "referring_physician_address")
	private WebElement referringPhysicianAddress;

	@FindBy(id = "referring_physician_city")
	private WebElement referringPhysicianCity;

	@FindBy(id = "referring_physician_state")
	private WebElement referringPhysicianState;

	@FindBy(id = "referring_physician_zipcode")
	private WebElement referringPhysicianZipcode;

	@FindBy(id = "referring_physician_speciality")
	private WebElement referringPhysicianSpeciality;

	@FindBy(id = "referring_physician_speciality_is_primary")
	private WebElement referringPhysicianSpecialityIsPrimary;

	@FindBy(id = "doctors_seen")
	private WebElement doctorsSeen;

	@FindBy(id = "pharmacy_name")
	private WebElement pharmacyName;

	@FindBy(id = "pharmacy_phone")
	private WebElement pharmacyPhone;


	public OtherDoctorsYouSeen(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
}

