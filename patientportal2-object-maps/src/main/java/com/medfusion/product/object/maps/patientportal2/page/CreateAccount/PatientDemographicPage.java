package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;

import com.medfusion.pojos.Patient;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class PatientDemographicPage extends MedfusionPage {

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

		//TODO declined gender

		@FindBy(how = How.ID, using = "address1")
		private WebElement inputAddress1;

		@FindBy(how = How.ID, using = "address2")
		private WebElement inputAddress2;

		@FindBy(how = How.ID, using = "city")
		private WebElement inputCity;

		@FindBy(how = How.XPATH, using = "//*[@placeholder='State'][1]")
		private WebElement inputState;

		@FindBy(how = How.XPATH, using = "//li[@class='ui-select-choices-group']/div[3]/span/div")
		private WebElement setState;

		@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='postalCode']")
		private WebElement inputZipCode;

		@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='cancelStep']")
		private WebElement buttonCancel;

		@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='nextStep']")
		private WebElement buttonContinue;

		@FindBy(how = How.XPATH, using = "//p[@data-ng-show = 'createAccountStep1_form.$error.inactiveAccount'][contains(text(),'Looks like we have previously invited you to join our portal. We just sent you another email invitation. Please check your email and click on the button to sign up.')]")
		private WebElement inactiveAccountExistsError;

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

		public void fillInPatientData(JalapenoPatient patient) {
				//TODO remove
				fillInPatientData(patient.getFirstName(), patient.getLastName(), patient.getEmail(), patient.getDOBMonthText(), patient.getDOBDay(),
						patient.getDOBYear(), patient.getGender(), patient.getZipCode(), patient.getAddress1(), patient.getAddress2(), patient.getCity(),
						patient.getState());
		}

		public void fillInPatientData(Patient patient) {
				fillInPatientData(patient.getFirstName(), patient.getLastName(), patient.getEmail(), convertDOBMonthToText(patient.getDOBMonth()), patient.getDOBDay(),
						patient.getDOBYear(), patient.getGender(), patient.getZipCode(), patient.getAddress1(), patient.getAddress2(), patient.getCity(),
						patient.getState());
		}

		public void fillInPatientData(String firstName, String lastName, String emailAddress, String dobMonth, String dobDay, String dobYear,
				Patient.GenderExtended gender, String zipCode) throws NullPointerException, Exception {
				PropertyFileLoader testData = new PropertyFileLoader();
				fillInPatientData(firstName, lastName, emailAddress, dobMonth, dobDay, dobYear, gender, zipCode, testData.getProperty("Address1"),
						testData.getProperty("Address2"), testData.getProperty("City"), testData.getProperty("State"));
		}

		public void fillInPatientData(String firstName, String lastName, String email, String dobMonthText, String dobDay, String dobYear,
				Patient.GenderExtended gender, String zipCode, String address1, String address2, String city, String state) {
				setName(firstName, lastName);
				setEmail(email);
				setDateOfBirth(dobMonthText, dobDay, dobYear);
				setGender(gender);
				setAddress(address1, address2, city, state, zipCode);
		}

		public SecurityDetailsPage continueToSecurityPage() {
				javascriptClick(buttonContinue);
				return PageFactory.initElements(driver, SecurityDetailsPage.class);
		}

		public void tryToContinueToSecurityPage() {
				buttonContinue.click();
		}

		private void setDateOfBirth(String month, String day, String year) {
				updateWebElement(inputDateOfBirthMonth, month);
				updateWebElement(inputDateOfBirthDay, day);
				updateWebElement(inputDateOfBirthYear, year);
		}

		private void setGender(Patient.GenderExtended gender) {
				if (gender == Patient.GenderExtended.MALE) {
						maleGender.click();
				} else if (gender == Patient.GenderExtended.FEMALE) {
						femaleGender.click();
				} else {
						//TODO declined/error
				}
		}

		private void setName(String firstName, String lastName) {
				updateWebElement(inputPatientFirstName, firstName);
				updateWebElement(inputPatientLastName, lastName);
		}

		private void setEmail(String email) {
				updateWebElement(inputEmailAddresss, email);
		}

		private void setAddress(String address1, String address2, String city, String state, String zipCode) {
				updateWebElement(inputAddress1, address1);
				updateWebElement(inputAddress2, address2);
				updateWebElement(inputCity, city);
				updateWebElement(inputZipCode, zipCode);
				inputState.click();
				setState.click();
		}

		public boolean isInactiveAccountExistsErrorDisplayed() {
				try {
						log("Looking for inactive account already exists error on PatientDemographicsPage");
						return inactiveAccountExistsError.isDisplayed();
				} catch (Exception e) {
				}
				return false;
		}
}
