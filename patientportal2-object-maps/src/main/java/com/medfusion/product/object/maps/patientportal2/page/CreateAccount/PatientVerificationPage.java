//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
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

		public PatientVerificationPage(WebDriver driver, String url) {
				super(driver, url);
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
				if (!areBasicPageElementsPresent()) {
						throw new UnsupportedOperationException("Page was not successfully loaded");
				}
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
				continueButton.click();
				return PageFactory.initElements(driver, AuthUserLinkAccountPage.class);
		}

		private void fillPatientIdentifyInfo(String zipCode, String month, String day, String year) throws InterruptedException {
				IHGUtil.PrintMethodName();

				log("Fill inputs with ZIP: " + zipCode + " and DOB: " + month + "/" + day + "/" + year);
				IHGUtil.waitForElement(driver, 10, zipCodeInput);
				zipCodeInput.sendKeys(zipCode);
				IHGUtil.waitForElement(driver, 10, dateOfBirthMonthSelect);
				Select dobMonth = new Select(dateOfBirthMonthSelect);
				if (month.startsWith("0")) {
						month = month.substring(1);
				}
				dobMonth.selectByValue(month);
				IHGUtil.waitForElement(driver, 10, dateOfBirthDayInput);
				dateOfBirthDayInput.sendKeys(day);
				IHGUtil.waitForElement(driver, 10, dateOfBirthYearInput);
				dateOfBirthYearInput.sendKeys(year);
		}
}
