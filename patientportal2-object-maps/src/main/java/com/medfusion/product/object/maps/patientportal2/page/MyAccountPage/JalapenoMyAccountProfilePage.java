// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.pojos.Patient;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class JalapenoMyAccountProfilePage extends JalapenoMyAccountPage {
	public static final List<String> GENDER_IDENTITY_LIST = Collections.unmodifiableList(Arrays.asList("Male", "Female",
			"Transgender female/Trans woman/Male-to-female (MTF)", "Transgender male/Trans man/Female-to-male (FTM)",
			"Genderqueer, neither exclusively male nor female", "Additional gender category/(or other)"));

	@FindBy(how = How.XPATH, using = "//input[@id='address1']")
	private WebElement address1Textbox;

	@FindBy(how = How.XPATH, using = "//input[@id='address2']")
	private WebElement address2Textbox;

	@FindBy(how = How.XPATH, using = "//input[@id='city']")
	private WebElement cityTextbox;

	@FindBy(how = How.XPATH, using = "//input[@id='postalCode']")
	private WebElement zipCodeTextbox;
	
	@FindBy(how = How.XPATH, using = "//input[@name='firstName']")
	private WebElement firstNameTextbox;
	
	@FindBy(how = How.XPATH, using = "//input[@name='lastName']")
	private WebElement lastNameTextbox;

	@FindBy(how = How.XPATH, using = "//input[@id='gender_male']")
	private WebElement maleRadioButton;

	@FindBy(how = How.XPATH, using = "//input[@id='gender_female']")
	private WebElement femaleRadioButton;

	@FindBy(how = How.XPATH, using = "//input[@id='gender_unknown']")
	private WebElement declineToAnswerRadioButton;

	@FindBy(how = How.XPATH, using = "//input[@id='gender_undifferentiated']")
	private WebElement undifferentiatedGenderRadioButton;

	@FindBy(how = How.XPATH, using = "//input[@name='birthDate_year']")
	private WebElement DOByear;

	@FindBy(how = How.XPATH, using = "//select[@name='birthDate_month']")
	private WebElement DOBmonth;

	@FindBy(how = How.XPATH, using = "//input[@name='birthDate_day']")
	private WebElement DOBday;

	@FindBy(how = How.ID, using = "state")
	private WebElement stateSelect;

	@FindBy(how = How.XPATH, using = "//select[@id='genderIdentitySelect']")
	private WebElement genderQuestion;

	@FindBy(how = How.XPATH, using = "//button[@id='saveAccountChanges']")
	private WebElement saveMyChanges;

	@FindBy(how = How.XPATH, using = "//select[@id='race']")
	private WebElement race;

	@FindBy(how = How.XPATH, using = "//select[@id='ethnicity']")
	private WebElement ethnicity;

	@FindBy(how = How.XPATH, using = "//select[@id='sexualOrientationSelect']")
	private WebElement sexualOrientation;

	@FindBy(how = How.ID, using = "firstName")
	private WebElement firstName;

	@FindBy(how = How.ID, using = "lastName")
	private WebElement lastName;
	
	@FindBy(how = How.ID, using = "phone2")
	private WebElement phone2;
	
	@FindBy(how = How.XPATH, using = "//input[@id='phone3']")
	private WebElement phone3;

	@FindBy(how = How.XPATH, using = "//input[@id='phone4']")
	private WebElement phone4;

	@FindBy(how = How.XPATH, using = "//input[@id='phone5']")
	private WebElement phone5;

	@FindBy(how = How.XPATH, using = "//input[@id='phone6']")
	private WebElement phone6;
	
	@FindBy(how = How.XPATH, using = "//p[text()=' Your profile information has been updated. ']")
	private WebElement notificationMessage;
	
    
	public JalapenoMyAccountProfilePage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}

	public String getDOBday() {
		return DOBday.getAttribute("value");
	}

	public String getDOByear() {
		return DOByear.getAttribute("value");
	}
	
	public String getPhone3() {
		return phone3.getAttribute("value");
	}

	public String getZipCodeTextbox() {
		return zipCodeTextbox.getAttribute("value");
	}

	public String getDOBmonth() {
		Select sc = new Select(DOBmonth);
		String webelementDOBMonth = sc.getFirstSelectedOption().getAttribute("value").replaceFirst("0", "");
		return webelementDOBMonth;
	}

	public String getDOB() {
		return getDOBmonth() + "/" + getDOBday() + "/" + getDOByear();
	}

	public boolean checkExtendedGenderQuestion() throws NoSuchElementException, InterruptedException {
		// String genderValue = "";
		new Select(ethnicity).selectByIndex(1);
		new Select(race).selectByIndex(1);

		if (!declineToAnswerRadioButton.isDisplayed()) {
			log("Checking Decline to answer radio button");
			return false;
		}

		for (String genderValue : GENDER_IDENTITY_LIST) {
			Select genderQuestionSelect = new Select(genderQuestion);
			try {
				log("Checking gender value: " + genderValue);
				Thread.sleep(2000);
				genderQuestionSelect.selectByVisibleText(genderValue);
				Thread.sleep(2000);
				log("Value " + genderValue + " is verified.");
				javascriptClick(saveMyChanges);
				IHGUtil.waitForElement(driver, 30, genderQuestion);
			} catch (NoSuchElementException e) {
				log("Gender value: " + genderValue + "is missing.");
				return false;
			}
			try {
				if (genderQuestionSelect.getFirstSelectedOption().getText().equals(genderValue)) {
					log("Gender is saved properly.");
				} else {
					log("Gender has not been saved.");
					return false;
				}
			} catch (NoSuchElementException e) {
				log("Gender has not been saved.");
				return false;
			}
		}
		return true;
	}

	public boolean checkForAddress(WebDriver driver, String addressLine1, String city, String zipCode) {

		log("Checking address in My Account");
		new WebDriverWait(driver, 25).until(ExpectedConditions.textToBePresentInElementValue(address1Textbox, addressLine1));
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
		new WebDriverWait(driver, 25).until(ExpectedConditions.textToBePresentInElementValue(zipCodeTextbox, zipCode));
		String savedZipCode = zipCodeTextbox.getAttribute("value");

		if (!StringUtils.equals(zipCode, savedZipCode)) {
			log("ZipCode does not match, expected '" + zipCode + "' but there is '" + savedZipCode + "'");
			return false;
		}

		log("ZipCode value: " + savedZipCode);
		return true;
	}
	
	public boolean checkPatientName(String firstName, String fName, String lName, String uFName, String uLName) {
		String lastName = "";
		log("Checking Patient first name and last name");
		if(firstName.equals(fName)) {
			lastName = lName;
		}
		else if(firstName.equals(uFName)) {
			lastName = uLName;
		}
		new WebDriverWait(driver, 25).until(ExpectedConditions.textToBePresentInElementValue(firstNameTextbox, firstName));
		String frstName = firstNameTextbox.getAttribute("value");
		String lstName = lastNameTextbox.getAttribute("value");

		if (!StringUtils.equals(firstName, frstName)) {
			log("FirstName does not match, expected '" + firstName + "' but there is '" + frstName + "'");
			return false;
		}
		
		if (!StringUtils.equals(lastName, lstName)) {
			log("LastName does not match, expected '" + lastName + "' but there is '" + lstName + "'");
			return false;
		}

		log("First Name value: " + frstName);
		log("Last Name value: " + lstName);
		return true;
	}

	public boolean checkGender(Patient.GenderExtended genderExpected) {
		log("Checking gender");
		Patient.GenderExtended genderOnPage;
		if (maleRadioButton.isSelected())
			genderOnPage = Patient.GenderExtended.MALE;
		else if (femaleRadioButton.isSelected())
			genderOnPage = Patient.GenderExtended.FEMALE;
		else if (declineToAnswerRadioButton.isSelected()) // displayed only if extended gender question enabled
			genderOnPage = Patient.GenderExtended.DECLINED;
		else
			genderOnPage = null;
		return genderExpected == genderOnPage;
	}

	public JalapenoMyAccountSecurityPage goToSecurityTab(WebDriver driver) {
		log("Click on Security");
		securityTab.click();

		return PageFactory.initElements(driver, JalapenoMyAccountSecurityPage.class);
	}

	public JalapenoMyAccountPreferencesPage goToPreferencesTab(WebDriver driver) {
		log("Click on Preferences");
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(preferencesTab));
		preferencesTab.click();

		return PageFactory.initElements(driver, JalapenoMyAccountPreferencesPage.class);
	}

	public JalapenoMyAccountActivityPage goToActivityTab(WebDriver driver) {
		log("Click on Activity");
		activityTab.click();
		return PageFactory.initElements(driver, JalapenoMyAccountActivityPage.class);

	}

	public boolean modifyAndValidatePageContent() throws InterruptedException {
		Map<WebElement, String> itemsToChange = new HashMap<WebElement, String>();
		itemsToChange.put(address1Textbox, "address");
		itemsToChange.put(cityTextbox, "city");
		itemsToChange.put(zipCodeTextbox, "54321");
		itemsToChange.put(race, "White");
		itemsToChange.put(ethnicity, "Hispanic or Latino");

		return updateAndValidateWebElements(itemsToChange, saveAccountChanges);
	}

	/**
	 * @return proper gender or null if not specified
	 */
	public Patient.GenderExtended getGender() {
		if (maleRadioButton.isSelected())
			return Patient.GenderExtended.MALE;
		if (femaleRadioButton.isSelected())
			return Patient.GenderExtended.FEMALE;
		if (declineToAnswerRadioButton.isSelected())
			return Patient.GenderExtended.DECLINED;
		return null;
	}

	public int countDropDownValue(char key) {
		IHGUtil.PrintMethodName();
		Select select = null;
		int size = 0;
		switch (key) {
		case 'R':
			select = new Select(race);
			break;
		case 'E':
			select = new Select(ethnicity);
			break;
		case 'G':
			select = new Select(genderQuestion);
			break;
		case 'S':
			select = new Select(sexualOrientation);
			break;
		default:
			break;
		}

		List<WebElement> element = select.getOptions();
		size = element.size();

		return size;
	}

	public String updateDropDownValue(int i, char key) {
		IHGUtil.PrintMethodName();
		Select select = null;
		String changeValue = null;
		switch (key) {
		case 'R':
			select = new Select(race);
			break;
		case 'E':
			select = new Select(ethnicity);
			break;
		case 'G':
			select = new Select(genderQuestion);
			break;
		case 'S':
			select = new Select(sexualOrientation);
			break;
		default:

			break;
		}
		select.selectByIndex(i);
		WebElement option = select.getFirstSelectedOption();
		changeValue = option.getText();
		javascriptClick(saveMyChanges);
		return changeValue;
	}

	public String updateGenderValue(int i, char key) {
		IHGUtil.PrintMethodName();
		String changeValue = null;
		switch (key) {
		case 'M':
			changeValue = "Male";
			new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(maleRadioButton));
			if (!maleRadioButton.isSelected())
				maleRadioButton.click();

			break;
		case 'F':
			changeValue = "Female";
			new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(femaleRadioButton));
			femaleRadioButton.click();
			break;
		case 'D':
			new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(declineToAnswerRadioButton));
			declineToAnswerRadioButton.click();
			changeValue = "Decline to answer";
			break;
		case 'U':
			new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(undifferentiatedGenderRadioButton));
			undifferentiatedGenderRadioButton.click();
			changeValue = "UNDIFFERENTIATED";
			break;


		default:

			break;
		}
		javascriptClick(saveMyChanges);
		return changeValue;
	}

	public void updateDemographics(List<String> updateData) {
		firstName.clear();
		firstName.sendKeys(updateData.get(0));
		lastName.clear();
		lastName.sendKeys(updateData.get(1));
		String[] DOB = updateData.get(5).split("/");
		DOBday.sendKeys(Keys.BACK_SPACE);
		DOBday.sendKeys(DOB[1]);
		new Select(DOBmonth).selectByIndex(Integer.parseInt(DOB[0]));
		DOByear.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), DOB[2]);
		address1Textbox.clear();
		address1Textbox.sendKeys(updateData.get(2));
		address2Textbox.clear();
		address2Textbox.sendKeys(updateData.get(3));
		phone4.clear();
		phone4.sendKeys(updateData.get(4).substring(0, 3));
		phone5.clear();
		phone5.sendKeys(updateData.get(4).substring(3, 6));
		phone6.clear();
		phone6.sendKeys(updateData.get(4).substring(6, 10));
		new Select(race).selectByVisibleText(updateData.get(7));
		new Select(ethnicity).selectByVisibleText(updateData.get(8));
		zipCodeTextbox.clear();
		zipCodeTextbox.sendKeys(updateData.get(11));
		IHGUtil.waitForElement(driver, 5, saveMyChanges);
		javascriptClick(saveMyChanges);

	}

	public void modifyAndSubmitAddressLines(String firstLine, String secondLine) {
		IHGUtil.PrintMethodName();
		address1Textbox.clear();
		address1Textbox.sendKeys(firstLine);
		address2Textbox.clear();
		address2Textbox.sendKeys(secondLine);
		saveMyChanges.click();
	}

	public void addInsuranceLink() {
		IHGUtil.PrintMethodName();
		PortalUtil2.setPortalFrame(driver);
	}
	
	public JalapenoMyAccountDevicesPage navigateToDevicesTab(WebDriver driver) {
		log("Click on My Devices");
		devicesTab.click();
		return PageFactory.initElements(driver, JalapenoMyAccountDevicesPage.class);
	}
	public void updateDobZipPhoneFields(String phoneNumber,String zipcode,String yeardob) throws InterruptedException {
		Thread.sleep(1500);// Waiting page to load.
		phone3.clear();
		IHGUtil.exists(driver, 200, phone3);
		phone3.sendKeys(phoneNumber);
		zipCodeTextbox.clear();
		IHGUtil.exists(driver, 200, zipCodeTextbox);
		zipCodeTextbox.sendKeys(zipcode);
		DOByear.clear();
		IHGUtil.exists(driver, 200, DOByear);
     	DOByear.sendKeys(yeardob);
		}
	public void clickOnSaveAccountButton() {
		IHGUtil.waitForElement(driver, 50, saveMyChanges);
		saveMyChanges.click();
	}
	public boolean isProfileInformationUpdateMessageDisplayed() {
		try {
			log("Looking for Profile Information Update Message");
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(notificationMessage));
			return notificationMessage.isDisplayed();
		} catch (Exception e) {
		}
		return false;
	}
	
}
