package com.medfusion.product.object.maps.pss2.page.Appointment.Loginless;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.util.DateMatcher;

public class LoginlessPatientInformation extends PSS2MainPage {

	@FindBy(how = How.NAME, using = "firstname")
	private WebElement inputFirstName;

	@FindBy(how = How.ID, using = "lastname")
	private WebElement inputLastName;

	@FindBy(how = How.XPATH, using = "//*[@class=\"input-group date\"]/span/span")
	private WebElement datePicker;

	@FindBy(how = How.ID, using = "email")
	private WebElement inputEmail;

	@FindBy(how = How.ID, using = "gender")
	private WebElement selectGender;

	@FindBy(how = How.ID, using = "zip")
	private WebElement inputZipCode;

	@FindBy(how = How.ID, using = "phone")
	private WebElement inputPrimaryPhoneNumber;

	@FindBy(how = How.CLASS_NAME, using = "custombuttonexistnext")
	private WebElement buttonNext;

	@FindBy(how = How.CLASS_NAME, using = "custombuttonexist")
	private WebElement buttonCancel;

	@FindBy(how = How.ID, using = "city")
	private WebElement inputCity;

	@FindBy(how = How.ID, using = "state")
	private WebElement selectState;

	@FindBy(how = How.ID, using = "zip")
	private WebElement inputZip;

	@FindBy(how = How.ID, using = "street")
	private WebElement inputStreet;

	public LoginlessPatientInformation(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputPrimaryPhoneNumber);
		webElementsList.add(inputZipCode);
		webElementsList.add(selectGender);
		webElementsList.add(inputEmail);
		webElementsList.add(datePicker);
		webElementsList.add(inputLastName);
		webElementsList.add(inputFirstName);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public NewPatientInsuranceInfo fillPatientForm(String firstName, String lastName, String dob, String email, String gender, String zipCodeValue,
			String phoneNumber,
 String city, String street) {
		log("phoneNumber= " + phoneNumber);
		inputFirstName.sendKeys(firstName);
		inputLastName.sendKeys(lastName);
		datePicker.click();

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		DateMatcher dateMatcher = new DateMatcher();
		dateMatcher.selectDate(dob, driver);

		selectGender.click();
		Select selectGenderType = new Select(selectGender);
		selectGenderType.selectByValue(gender);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		inputPrimaryPhoneNumber.sendKeys(phoneNumber);

		inputEmail.sendKeys(email);
		inputStreet.sendKeys(street);
		inputCity.sendKeys(city);
		Select selectStateValue = new Select(selectState);
		selectStateValue.selectByValue("AZ");
		inputZip.sendKeys(zipCodeValue);
		log("formfilled ...");
		buttonNext.click();
		return PageFactory.initElements(driver, NewPatientInsuranceInfo.class);
	}


	public HomePage fillNewPatientForm(String firstName, String lastName, String dob, String email, String gender, String zipCodeValue, String phoneNumber,
			String city, String street) {
		log("phoneNumber= " + phoneNumber);
		inputFirstName.sendKeys(firstName);
		inputLastName.sendKeys(lastName);
		datePicker.click();

		DateMatcher dateMatcher = new DateMatcher();
		dateMatcher.selectDate(dob, driver);

		selectGender.click();
		Select selectGenderType = new Select(selectGender);
		selectGenderType.selectByValue(gender);
		inputPrimaryPhoneNumber.sendKeys(phoneNumber);

		inputEmail.sendKeys(email);
		inputStreet.sendKeys(street);
		inputCity.sendKeys(city);
		Select selectStateValue = new Select(selectState);
		selectStateValue.selectByValue("AZ");
		inputZip.sendKeys(zipCodeValue);
		log("formfilled ...");
		buttonNext.click();
		return PageFactory.initElements(driver, HomePage.class);
	}


	public HomePage fillNewPatientFormToActivate(String firstName, String lastName, String dob, String email, String gender, String zipCodeValue,
			String phoneNumber,
			String city, String street) {
		log("phoneNumber= " + phoneNumber);
		inputFirstName.sendKeys(firstName);
		inputLastName.sendKeys(lastName);
		datePicker.click();

		DateMatcher dateMatcher = new DateMatcher();
		dateMatcher.selectDate(dob, driver);

		selectGender.click();
		Select selectGenderType = new Select(selectGender);
		selectGenderType.selectByValue(gender);
		inputPrimaryPhoneNumber.sendKeys(phoneNumber);

		inputEmail.sendKeys(email);
		inputStreet.sendKeys(street);
		inputCity.sendKeys(city);
		Select selectStateValue = new Select(selectState);
		selectStateValue.selectByValue("AZ");
		inputZip.sendKeys(zipCodeValue);
		log("formfilled ...");
		buttonNext.click();
		return PageFactory.initElements(driver, HomePage.class);
	}


	public void isPageLoaded() {
		IHGUtil.waitForElement(driver, 80, this.buttonNext);
	}
}