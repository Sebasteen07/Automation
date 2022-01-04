//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoForgotPasswordPage extends MedfusionPage {

		@FindBy(how = How.ID, using = "emailOrUsername")
		public WebElement inputEmail;

		@FindBy(how = How.ID, using = "forgotFormContinueButton")
		public WebElement continueButton;
		
		@FindBy(how = How.XPATH, using = "//*[@id='birthDate_month']")
		private WebElement inputDateOfBirthMonth;
		
		@FindBy(how = How.XPATH, using = "//*[@id='birthDate_day']")
		private WebElement inputDateOfBirthDay;
		
		@FindBy(how = How.XPATH, using = "//*[@id='birthDate_year']")
		private WebElement inputDateOfBirthYear;

		@FindBy(how = How.ID, using = "firstName")
		public WebElement inputFirstName;

		public JalapenoForgotPasswordPage(WebDriver driver, String url) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		public JalapenoForgotPasswordPage(WebDriver driver) {
				super(driver);
		}

		public JalapenoForgotPasswordPage2 fillInDataPage(String email) {
				IHGUtil.PrintMethodName();

				log("Setting email address as " + email);
				inputEmail.sendKeys(email);

				log("Clicking on Continue button");

				continueButton.click();

				return PageFactory.initElements(driver, JalapenoForgotPasswordPage2.class);
		}
		
		public JalapenoForgotPasswordPage2 fillInDataPageWithSameEmailAndDOB(String email, String dateOfBirthMonth, String dateOfBirthDay, String dateOfBirthYear, String firstName){
			IHGUtil.PrintMethodName();
			log("Setting email address as " + email);
			inputEmail.sendKeys(email);
			log("Clicking on Continue button");
			continueButton.click();
			if (new IHGUtil(driver).isRendered(inputDateOfBirthMonth)) {
				log("Enter DOB");
				new Select(inputDateOfBirthMonth).selectByValue(dateOfBirthMonth);
				inputDateOfBirthDay.sendKeys(dateOfBirthDay);
				inputDateOfBirthYear.sendKeys(dateOfBirthYear);
				continueButton.click();
				if (new IHGUtil(driver).isRendered(inputFirstName)) {
					log("Enter the First Name");
					inputFirstName.sendKeys(firstName);
					continueButton.click();
				}
			}
			return PageFactory.initElements(driver, JalapenoForgotPasswordPage2.class);
	}

}
