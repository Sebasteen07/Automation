package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoMyAccountProfilePage extends JalapenoMyAccountPage {

	@FindBy(how = How.XPATH, using = "//input[@id='address1']")
	private WebElement address1Textbox;

	@FindBy(how = How.XPATH, using = "//input[@id='city']")
	private WebElement cityTextbox;

	@FindBy(how = How.XPATH, using = "//input[@id='postalCode']")
	private WebElement zipCodeTextbox;

	@FindBy(how = How.XPATH, using = "//input[@id='gender_male']")
	private WebElement maleRadioButton;

	@FindBy(how = How.XPATH, using = "//input[@id='gender_female']")
	private WebElement femaleRadioButton;

	@FindBy(how = How.ID, using = "birthDate_year")
	private WebElement DOByear;

	@FindBy(how = How.ID, using = "birthDate_month")
	private WebElement DOBmonth;

	@FindBy(how = How.ID, using = "birthDate_day")
	private WebElement DOBday;

	@FindBy(how = How.ID, using = "state")
	private WebElement stateSelect;

	public JalapenoMyAccountProfilePage(WebDriver driver) throws InterruptedException {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public int getDOBday() {
		return Integer.parseInt(DOBday.getAttribute("value"));
	}

	public int getDOByear() {
		return Integer.parseInt(DOByear.getAttribute("value"));
	}

	public int getDOBmonth() {
		return Integer.parseInt(DOBmonth.getAttribute("value"));
	}

	public String getDOB() {
		return getDOBmonth() + "/" + getDOBday() + "/" + getDOByear();
	}

	public boolean checkForAddress(WebDriver driver, String addressLine1, String city, String zipCode) {

		log("Checking address in My Account");

		String savedAddressLine1 = address1Textbox.getAttribute("value");
		String savedCity = cityTextbox.getAttribute("value");
		String savedZipCode = zipCodeTextbox.getAttribute("value");

		if (savedAddressLine1.isEmpty() || savedCity.isEmpty() || savedZipCode.isEmpty()) {
			log("One of the address information if missing");
			return false;
		}

		if (!addressLine1.equals(savedAddressLine1)) {
			log("Line 1 is incorrect: " + savedAddressLine1);
			return false;
		}
		if (!city.equals(savedCity)) {
			log("City is incorrect: " + savedCity);
			return false;
		}
		if (!zipCode.equals(savedZipCode)) {
			log("ZipCode is incorrect: " + savedZipCode);
			return false;
		}

		log("Everything is saved, values are as follows");

		log("Address line 1 value: " + savedAddressLine1);
		log("City value: " + savedCity);
		log("ZipCode value: " + savedZipCode);

		return true;
	}

	public boolean checkZipCode(String zipCode) {

		log("Checking ZipCode textbox");

		String savedZipCode = zipCodeTextbox.getAttribute("value");

		if (!StringUtils.equals(zipCode, savedZipCode)) {
			log("ZipCode does not match, expected '" + zipCode + "' but there is '" + savedZipCode + "'");
			return false;
		}

		log("ZipCode value: " + savedZipCode);
		return true;
	}

	public boolean checkGender(Gender genderExpected) {
		log("Checking gender");
		Gender genderOnPage = maleRadioButton.isSelected() ? Gender.MALE : Gender.MALE;
		return genderExpected == genderOnPage;
	}

	public JalapenoHomePage returnToHomePage(WebDriver driver) {
		log("Return to dashboard");
		driver.findElement(By.id("home")).click();

		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public JalapenoMyAccountSecurityPage goToSecurityTab(WebDriver driver) {
		log("Click on Security");
		securityTab.click();

		return PageFactory.initElements(driver, JalapenoMyAccountSecurityPage.class);
	}

	public JalapenoMyAccountPreferencesPage goToPreferencesTab(WebDriver driver) {
		log("Click on Preferences");
		preferencesTab.click();

		return PageFactory.initElements(driver, JalapenoMyAccountPreferencesPage.class);
	}

	@Override
	public boolean areBasicPageElementsPresent() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		webElementsList.add(profileTab);
		webElementsList.add(securityTab);
		webElementsList.add(preferencesTab);
		webElementsList.add(address1Textbox);
		webElementsList.add(cityTextbox);
		webElementsList.add(zipCodeTextbox);
		webElementsList.add(maleRadioButton);

		return super.assessPageElements(true) && new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public boolean modifyAndValidatePageContent() {
		Map<WebElement, String> itemsToChange = new HashMap<WebElement, String>();
		itemsToChange.put(address1Textbox, "address");
		itemsToChange.put(cityTextbox, "city");
		itemsToChange.put(stateSelect, "Alaska");
		itemsToChange.put(zipCodeTextbox, "54321");

		return updateAndValidateWebElements(itemsToChange, saveAccountChanges);
	}

	/**
	 * 
	 * @return proper gender or null if not specified
	 */
	public Gender getGender() {
		if (maleRadioButton.isSelected())
			return Gender.MALE;
		if (femaleRadioButton.isSelected())
			return Gender.FEMALE;
		return null;
	}
}
