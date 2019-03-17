package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import java.util.ArrayList;

import com.medfusion.pojos.Patient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class PatientVerificationPage extends MedfusionPage {

		public static final String ACTIVE_TAB_XPATH_SELECTOR = "//div[contains(@class,'tab-pane') and contains(@class,'active')]";

		@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='birthDate_month']") private WebElement dateOfBirthMonthSelect;

		@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='birthDate_day']") private WebElement dateOfBirthDayInput;

		@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='birthDate_year']") private WebElement dateOfBirthYearInput;

		@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='postalCode']") private WebElement zipCodeInput;

		@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='cancelStep']") private WebElement cancelButton;

		@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='nextStep']") private WebElement continueButton;

		public PatientVerificationPage(WebDriver driver, String url) {
				super(driver, url);
				log("URL: " + url);
				driver.manage().window().maximize();
				PageFactory.initElements(driver, this);
		}

		@Override public boolean areBasicPageElementsPresent() {

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

		public SecurityDetailsPage fillPatientInfoAndContinue(Patient patient) {
				return fillPatientInfoAndContinue(patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());
		}

		public SecurityDetailsPage fillPatientInfoAndContinue(String zipCode, String dobMonth, String dobDay, String dobYear) {
				fillPatientIdentifyInfo(zipCode, dobMonth, dobDay, dobYear);
				continueButton.click();
				return PageFactory.initElements(driver, SecurityDetailsPage.class);
		}

		public AuthUserLinkAccountPage fillDependentInfoAndContinue(Patient patient) {
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
