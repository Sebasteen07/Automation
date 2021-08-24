//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

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

		@FindBy(how = How.ID, using = "birthDate_month")
		private WebElement dateOfBirthMonthSelect;

		@FindBy(how = How.ID, using = "birthDate_day")
		private WebElement dateOfBirthDayInput;

		@FindBy(how = How.ID, using = "birthDate_year")
		private WebElement dateOfBirthYearInput;

		@FindBy(how = How.ID, using = "postalCode")
		private WebElement zipCodeInput;

		@FindBy(how = How.ID, using = "cancelStep")
		private WebElement cancelButton;

		@FindBy(how = How.ID, using = "nextStep")
		private WebElement continueButton;
		
		@FindBy(how = How.XPATH, using = "//a[contains(text(),'forgot my user name')]")
		private WebElement clickForgotPasswordButton;

		@FindBy(how = How.XPATH, using = "//p[text()='The zip code and date of birth you have entered do not match our records. Please try again or contact our practice for help.']")
		private WebElement zipcodedobnotmatchederror;

		public PatientVerificationPage(WebDriver driver, String url) {
				super(driver, url);
		}

		public void getToThisPage(String url) {
				driver.get(url);
		}

		public SecurityDetailsPage fillPatientInfoAndContinue(Patient patient) throws InterruptedException {
				return fillPatientInfoAndContinue(patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());
		}

		public SecurityDetailsPage fillPatientInfoAndContinue(String zipCode, String dobMonth, String dobDay, String dobYear) throws InterruptedException {
				fillPatientIdentifyInfo(zipCode, dobMonth, dobDay, dobYear);
				continueButton.click();
				return PageFactory.initElements(driver, SecurityDetailsPage.class);
		}

		public AuthUserLinkAccountPage fillDependentInfoAndContinue(Patient patient) throws InterruptedException {
				return fillDependentInfoAndContinue(patient.getZipCode(), patient.getDOBMonth(), patient.getDOBDay(), patient.getDOBYear());
		}

		public AuthUserLinkAccountPage fillDependentInfoAndContinue(String zipCode, String dobMonth, String dobDay, String dobYear) throws InterruptedException {
				fillPatientIdentifyInfo(zipCode, dobMonth, dobDay, dobYear);
				Thread.sleep(3000);
				continueButton.click();
				return PageFactory.initElements(driver, AuthUserLinkAccountPage.class);
		}

		private void fillPatientIdentifyInfo(String zipCode, String month, String day, String year) throws InterruptedException {
				IHGUtil.PrintMethodName();

				log("Fill inputs with ZIP: " + zipCode + " and DOB: " + month + "/" + day + "/" + year);
				IHGUtil.waitForElement(driver, 60, zipCodeInput);
				zipCodeInput.sendKeys(zipCode);
				Thread.sleep(1000);
		
				Select dobMonth = new Select(dateOfBirthMonthSelect);
				if (month.startsWith("0")) {
						month = month.substring(1);
				}
				dobMonth.selectByValue(month);
				dateOfBirthDayInput.sendKeys(day);
				dateOfBirthYearInput.sendKeys(year);
		}
		
		public void securityDetailsPageclickForgotPasswordButton() {
			IHGUtil.PrintMethodName();
			log("Clicking on Forgot Password button");
			IHGUtil.waitForElement(driver, 60, clickForgotPasswordButton);
			clickForgotPasswordButton.click();
		}

		public void fillPatientZipCodeDobInfoAndContinue(String zipCode, String dobMonth, String dobDay, String dobYear) throws InterruptedException {
			fillPatientIdentifyInfo(zipCode, dobMonth, dobDay, dobYear);
			continueButton.click();
}
		public boolean isZipCodeDobErrorDisplayed() {
			try {
				log("Looking for inactive account already exists error on PatientDemographicsPage");
				return zipcodedobnotmatchederror.isDisplayed();
			} catch (Exception e) {
			}
			return false;
		}}
