package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;

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

	@FindBy(how = How.ID, using = "address1")
	private WebElement inputAddress1;

	@FindBy(how = How.ID, using = "address2")
	private WebElement inputAddress2;

	@FindBy(how = How.ID, using = "city")
	private WebElement inputCity;

	@FindBy(how = How.XPATH, using = "//*[@id=\'state_field\']/mf-us-states/div/div[1]/span/span[1]")
	private WebElement inputState;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"ui-select-choices-row-0-0\"]/span/div")
	private WebElement setState;
	
	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='postalCode']")
	private WebElement inputZipCode;

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='cancelStep']")
	private WebElement buttonCancel;

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='nextStep']")
	private WebElement buttonContinue;
	

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

	public void fillInPatientData(JalapenoPatient patient) {
		fillInPatientData(patient.getFirstName(), patient.getLastName(), patient.getEmail(), patient.getDOBMonthText(), patient.getDOBDay(), patient.getDOBYear(),
				patient.getGender(), patient.getZipCode(), patient.getAddress1(), patient.getAddress2(), patient.getCity(), patient.getState());
	}

	public void fillInPatientData(String firstName, String lastName, String emailAddress, String dobMonth, String dobDay, String dobYear, Gender gender,
			String zipCode) throws IOException {
		PropertyFileLoader fileLoader = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(fileLoader);
		fillInPatientData(firstName, lastName, emailAddress, dobMonth, dobDay, dobYear, gender, zipCode, patient.getAddress1(), patient.getAddress2(),
				patient.getCity(), patient.getState());
	}

	public void fillInPatientData(String firstName, String lastName, String email, String dobMonthText, String dobDay, String dobYear, Gender gender,
			String zipCode, String address1, String address2, String city, String state) {
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

	private void setGender(Gender gender) {
		if (gender == Gender.MALE) {
			maleGender.click();
		} else {
			femaleGender.click();
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
	
}
