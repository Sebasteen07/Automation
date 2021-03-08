//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.pojos.Patient;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;

public class PatientDemographicPage extends MedfusionPage {

	private PropertyFileLoader testData;

	public static final String ACTIVE_TAB_XPATH_SELECTOR = "//div[contains(@class,'tab-pane') and contains(@class,'active')]";

	@FindBy(how = How.ID, using = "firstName")
	private WebElement inputPatientFirstName;

	@FindBy(how = How.ID, using = "lastName")
	private WebElement inputPatientLastName;

	@FindBy(how = How.ID, using = "email")
	private WebElement inputEmailAddresss;

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='birthDate_month']")
	private WebElement inputDateOfBirthMonth;

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='birthDate_day']")
	private WebElement inputDateOfBirthDay;

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='birthDate_year']")
	private WebElement inputDateOfBirthYear;

	@FindBy(how = How.ID, using = "gender_male")
	private WebElement maleGender;

	@FindBy(how = How.ID, using = "gender_female")
	private WebElement femaleGender;

	@FindBy(how = How.ID, using = "gender_decline")
	private WebElement declinedGender;

	@FindBy(how = How.ID, using = "address1")
	private WebElement inputAddress1;

	@FindBy(how = How.ID, using = "address2")
	private WebElement inputAddress2;

	@FindBy(how = How.ID, using = "city")
	private WebElement inputCity;

	@FindBy(how = How.XPATH, using = "//div[@class='ng-input']")
	private WebElement inputState;
	
	@FindBy(how = How.XPATH, using = "//div[@class='ng-option ng-option-marked']")
	private WebElement setState;

	@FindBy(how = How.XPATH, using = "//span[@ng-click='$select.toggle($event)']")
	private WebElement dropdownToggle;

	@FindBy(how = How.XPATH, using = "//li[@class='ui-select-choices-group']/div[4]/span/div")
	private WebElement setStateAlaska;

	@FindBy(how = How.XPATH, using = "//li[@class='ui-select-choices-group']/div[15]/span/div")
	private WebElement setStateHawaii;

	@FindBy(how = How.XPATH, using = "//input[@type='search']")
	private WebElement setStatesendkeys;

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='postalCode']")
	private WebElement inputZipCode;

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='cancelStep']")
	private WebElement buttonCancel;

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='nextStep']")
	private WebElement buttonContinue;

	
    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Looks like we have previously invited you to join our portal. We just sent you another email invitation. Please check your email and click on the button to sign up.')]")
	private WebElement inactiveAccountExistsError;
	

	@FindBy(how = How.XPATH, using = "//p[@id='dateofbirth-error']")
	private WebElement StateAgeOutError;

	@FindBy(how = How.XPATH, using = "//span[text()='Accounts for patients under 12 must be activated by the practice.']")
	private WebElement albamaErrorMessage;

	@FindBy(how = How.XPATH, using = "//span[text()='Accounts for patients under 18 must be activated by the practice.']")
	private WebElement alaskaErrorMessage;

	@FindBy(how = How.XPATH, using = "//span[text()='Accounts for patients under 20 must be activated by the practice.']")
	private WebElement HuwaiiErrorMessage;

	public PatientDemographicPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputPatientFirstName);
		webElementsList.add(inputPatientLastName);
		webElementsList.add(inputEmailAddresss);
		webElementsList.add(inputDateOfBirthMonth);
		webElementsList.add(inputDateOfBirthDay);
		webElementsList.add(inputDateOfBirthYear);
		webElementsList.add(maleGender);
		webElementsList.add(femaleGender);
		webElementsList.add(inputAddress1);
		webElementsList.add(inputAddress2);
		webElementsList.add(inputCity);
		webElementsList.add(inputState);
		webElementsList.add(inputZipCode);
		webElementsList.add(buttonCancel);
		webElementsList.add(buttonContinue);

		return assessPageElements(webElementsList);
	}

	private String convertDOBMonthToText(String monthNumber) {
		String monthText = Month.of(Integer.parseInt(monthNumber)).name();
		monthText = monthText.substring(0, 1).toUpperCase() + monthText.substring(1).toLowerCase();
		return monthText;
	}

	public void fillInPatientData(JalapenoPatient patient) throws Exception {
		fillInPatientData(patient.getFirstName(), patient.getLastName(), patient.getEmail(), patient.getDOBMonthText(),
				patient.getDOBDay(), patient.getDOBYear(), patient.getGender(), patient.getZipCode(),
				patient.getAddress1(), patient.getAddress2(), patient.getCity(), patient.getState());
	}

	public void fillInPatientData(Patient patient) throws Exception {
		fillInPatientData(patient.getFirstName(), patient.getLastName(), patient.getEmail(),
				convertDOBMonthToText(patient.getDOBMonth()), patient.getDOBDay(), patient.getDOBYear(),
				patient.getGender(), patient.getZipCode(), patient.getAddress1(), patient.getAddress2(),
				patient.getCity(), patient.getState());
	}

	public void fillInPatientDataStateSpecific(Patient patient) throws InterruptedException, IOException {
		fillInPatientDataStateSpecific(patient.getFirstName(), patient.getLastName(), patient.getEmail(),
				convertDOBMonthToText(patient.getDOBMonth()), patient.getDOBDay(), patient.getDOBYear(),
				patient.getGender(), patient.getZipCode(), patient.getAddress1(), patient.getAddress2(),
				patient.getCity(), patient.getState());
	}

	public void fillInPatientDataStateSpecific(String firstName, String lastName, String email, String dobMonthText,
			String dobDay, String dobYear, Patient.GenderExtended gender, String zipCode, String address1,
			String address2, String city, String state) throws InterruptedException, IOException {
		setName(firstName, lastName);
		setEmail(email);
		setDateOfBirthStateSpecific(dobMonthText, dobDay, dobYear);
		setGender(gender);
		setAddressStateSpecific(address1, address2, city, state, zipCode);
	}

	public void fillInPatientData(String firstName, String lastName, String emailAddress, String dobMonth,
			String dobDay, String dobYear, Patient.GenderExtended gender, String zipCode)
			throws NullPointerException, Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		fillInPatientData(firstName, lastName, emailAddress, dobMonth, dobDay, dobYear, gender, zipCode,
				testData.getProperty("Address1"), testData.getProperty("Address2"), testData.getProperty("City"),
				testData.getProperty("State"));
	}

	public void fillInPatientData(String firstName, String lastName, String email, String dobMonthText, String dobDay,
			String dobYear, Patient.GenderExtended gender, String zipCode, String address1, String address2,
			String city, String state) throws Exception {
		setName(firstName, lastName);
		setEmail(email);
		setDateOfBirth(dobMonthText, dobDay, dobYear);
		setGender(gender);
		setAddress(address1, address2, city, state, zipCode);
	}

	private void setDateOfBirth(String month, String day, String year) {
		updateWebElement(inputDateOfBirthMonth, month);
		updateWebElement(inputDateOfBirthDay, day);
		updateWebElement(inputDateOfBirthYear, year);
	}

	private void setName(String firstName, String lastName) {
		updateWebElement(inputPatientFirstName, firstName);
		updateWebElement(inputPatientLastName, lastName);
	}

	private void setAddress(String address1, String address2, String city, String state, String zipCode) {
		updateWebElement(inputAddress1, address1);
		updateWebElement(inputAddress2, address2);
		updateWebElement(inputCity, city);
		updateWebElement(inputZipCode, zipCode);
		inputState.click();
		setState.click();
	}

	private void setDateOfBirthStateSpecific(String month, String day, String year)
			throws InterruptedException, IOException {
		PropertyFileLoader testData = new PropertyFileLoader();
		updateWebElement(inputDateOfBirthMonth, month);
		updateWebElement(inputDateOfBirthDay, day);
		inputDateOfBirthYear.sendKeys(testData.getDOBYearUnderage());

	}

	private void setEmail(String email) {
		updateWebElement(inputEmailAddresss, email);
	}

	private void setGender(Patient.GenderExtended gender) {
		if (gender == Patient.GenderExtended.MALE) {
			maleGender.click();
		} else if (gender == Patient.GenderExtended.FEMALE) {
			femaleGender.click();
		} else if (gender == Patient.GenderExtended.DECLINED) {
			declinedGender.click();
		}
	}

	public boolean isInactiveAccountExistsErrorDisplayed() {
		try {
			log("Looking for inactive account already exists error on PatientDemographicsPage");
			return inactiveAccountExistsError.isDisplayed();
		} catch (Exception e) {
		}
		return false;
	}

	public SecurityDetailsPage continueToSecurityPage() {
		javascriptClick(buttonContinue);
		return PageFactory.initElements(driver, SecurityDetailsPage.class);
	}

	public void tryToContinueToSecurityPage() {
		buttonContinue.click();
	}

	public void fillInUnderAgePatientData(Patient patient) throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		String UnderageYear = testData.getDOBYearUnderage();
		fillInPatientData(patient.getFirstName(), patient.getLastName(), patient.getEmail(),
				convertDOBMonthToText(patient.getDOBMonth()), patient.getDOBDay(), UnderageYear, patient.getGender(),
				patient.getZipCode(), patient.getAddress1(), patient.getAddress2(), patient.getCity(),
				patient.getState());
		log("Verify if the error message is displayed while creating under-age patient at patient portal");
		assertTrue(StateAgeOutError.isDisplayed());
		log("The Error Message is: " + StateAgeOutError.getText());
		assertTrue(
				StateAgeOutError.getText().equals("Accounts for patients under 12 must be activated by the practice."));
		log("Verify that the Next Button is disabled");
		assertFalse(buttonContinue.isEnabled());

	}

	private void setAddressStateSpecific(String address1, String address2, String city, String state, String zipCode)
			throws InterruptedException, IOException {
		PropertyFileLoader testData = new PropertyFileLoader();
		String State2 = testData.getProperty("State2");
		String State1 = testData.getProperty("State1");
		updateWebElement(inputAddress1, address1);
		updateWebElement(inputAddress2, address2);
		updateWebElement(inputCity, city);
		updateWebElement(inputZipCode, zipCode);
		inputState.click();

		for (int i = 0; i <= 3; i++) {

			if (i == 0)

				setStatesendkeys.sendKeys(state);
			setStatesendkeys.sendKeys(Keys.ENTER);
			assertTrue(albamaErrorMessage.getText()
					.equals("Accounts for patients under 12 must be activated by the practice."));
			log("The Error Message is eqauls" + albamaErrorMessage.getText());
			Boolean value = buttonContinue.isEnabled();
			log("The continue button is not enabled=" + value);

			if (i == 1)
				IHGUtil.waitForElement(driver, 10, dropdownToggle);
			dropdownToggle.click();
			setStatesendkeys.sendKeys(State1);
			setStatesendkeys.sendKeys(Keys.ENTER);
			assertTrue(alaskaErrorMessage.getText()
					.equals("Accounts for patients under 18 must be activated by the practice."));
			log("The Error Message is eqauls" + alaskaErrorMessage.getText());

			if (i == 2)
				IHGUtil.waitForElement(driver, 10, dropdownToggle);
			dropdownToggle.click();
			setStatesendkeys.sendKeys(State2);
			setStatesendkeys.sendKeys(Keys.ENTER);
			assertTrue(HuwaiiErrorMessage.getText()
					.equals("Accounts for patients under 20 must be activated by the practice."));
			log("The Error Message is eqauls" + HuwaiiErrorMessage.getText());

			if (i == 3)
				IHGUtil.waitForElement(driver, 10, inputDateOfBirthYear);
			inputDateOfBirthYear.clear();
			inputDateOfBirthYear.sendKeys(testData.getDOBYear());
			IHGUtil.waitForElement(driver, 10, dropdownToggle);
			dropdownToggle.click();
			setStatesendkeys.sendKeys(state);
			setStatesendkeys.sendKeys(Keys.ENTER);
			break;
		}
	}

	public void fillInPatientDataPortal2(String firstName, String lastName, String email, String dobMonthText, String dobDay, String dobYear,
			String zip, String address1, String address2, String state, String city, String Gender) throws InterruptedException {
		log("Gender Passed is:    " + Gender);
		setName(firstName, lastName);
		setEmail(email);
		setDateOfBirth(dobMonthText, dobDay, dobYear);
		setAddress(address1, address2, city, state, zip);
		Thread.sleep(10000);
		driver.findElement(By.xpath("//label[contains(text(), '" + Gender + "')]/preceding-sibling::input")).click();
		IHGUtil.waitForElement(driver, 10, buttonContinue);
		buttonContinue.click();

	}

}
