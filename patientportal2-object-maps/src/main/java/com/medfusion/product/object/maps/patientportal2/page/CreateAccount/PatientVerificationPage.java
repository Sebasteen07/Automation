package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;

public class PatientVerificationPage extends MedfusionPage {

	@FindBy(how = How.XPATH, using = "//*[@class='tab-pane active']//*[@id='birthDate_month']")
	private WebElement dateOfBirthMonthSelect;

	@FindBy(how = How.XPATH, using = "//*[@class='tab-pane active']//*[@id='birthDate_day']")
	private WebElement dateOfBirthDayInput;

	@FindBy(how = How.XPATH, using = "//*[@class='tab-pane active']//*[@id='birthDate_year']")
	private WebElement dateOfBirthYearInput;

	@FindBy(how = How.XPATH, using = "//*[@class='tab-pane active']//*[@id='postalCode']")
	private WebElement zipCodeInput;

	@FindBy(how = How.XPATH, using = "//*[@class='tab-pane active']//*[@id='cancelStep']")
	private WebElement cancelButton;

	@FindBy(how = How.XPATH, using = "//*[@class='tab-pane active']//*[@id='nextStep']")
	private WebElement continueButton;

	public PatientVerificationPage(WebDriver driver, String url) {
		super(driver, url);
		log("URL: " + url);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	@Override
	public boolean areBasicPageElementsPresent() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(zipCodeInput);
		webElementsList.add(dateOfBirthMonthSelect);
		webElementsList.add(dateOfBirthDayInput);
		webElementsList.add(dateOfBirthYearInput);
		webElementsList.add(cancelButton);
		webElementsList.add(continueButton);

		return assessPageElements(webElementsList);
	}

	public void getToThisPage(String url) {
		driver.get(url);
		areBasicPageElementsPresent();
	}

	public SecurityDetailsPage fillPatientInfoAndContinue(JalapenoPatient patient) {
		return fillPatientInfoAndContinue(patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());
	}

	public SecurityDetailsPage fillPatientInfoAndContinue(String zipCode, String dobMonth, String dobDay, String dobYear) {
		fillPatientIdentifyInfo(zipCode, dobMonth, dobDay, dobYear);
		continueButton.click();
		return PageFactory.initElements(driver, SecurityDetailsPage.class);
	}

	public AuthUserLinkAccountPage fillDependentInfoAndContinue(JalapenoPatient patient) {
		return fillDependentInfoAndContinue(patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());
	}

	public AuthUserLinkAccountPage fillDependentInfoAndContinue(String zipCode, String dobMonth, String dobDay, String dobYear) {
		fillPatientIdentifyInfo(zipCode, dobMonth, dobDay, dobYear);
		continueButton.click();
		return PageFactory.initElements(driver, AuthUserLinkAccountPage.class);
	}

	private void fillPatientIdentifyInfo(String zipCode, String month, String day, String year) {
		IHGUtil.PrintMethodName();

		log("Fill inputs with ZIP: " + zipCode + " and DOB: " + month + "/" + day + "/" + year);
		zipCodeInput.sendKeys(zipCode);
		Select dobMonth = new Select(dateOfBirthMonthSelect);
		if (month.startsWith("0")) {
			month = month.substring(1);
		}
		dobMonth.selectByValue(month);
		dateOfBirthDayInput.sendKeys(day);
		dateOfBirthYearInput.sendKeys(year);
	}
}
