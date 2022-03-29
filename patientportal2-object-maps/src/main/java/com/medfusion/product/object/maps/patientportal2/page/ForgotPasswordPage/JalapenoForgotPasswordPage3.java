// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoForgotPasswordPage3 extends MedfusionPage {

		@FindBy(how = How.LINK_TEXT, using = "Sign In Now.")
		public WebElement signInButton;

		@FindBy(how = How.ID, using = "secretAnswer_forgot")
		public WebElement secretAnswer;

		@FindBy(how = How.ID, using = "forgotEnterSecretAnswerFormContinueButton")
		public WebElement continueAndResetButton;
		
		@FindBy(how = How.ID, using = "updateMissingInfoButton")
		private WebElement okButton;
		
		@FindBy(how = How.XPATH, using = "//*[contains(text(),\"Your account has been locked because you have entered an incorrect answer too many times\")]")
		private WebElement msgAccountLocked;

		public JalapenoForgotPasswordPage3(WebDriver driver, String url) {
				super(driver);
				IHGUtil.PrintMethodName();;
		}

		public JalapenoForgotPasswordPage4 fillInSecretAnswer(String answer) {

				// catching webdriver exception which started to show up after selenium 2.45 and firefox 36 updates
				// try removing the try catch once newrelic is deprecated and fully removed
				try {
						secretAnswer.sendKeys(answer);
				} catch (org.openqa.selenium.WebDriverException e) {
						secretAnswer.sendKeys(answer);
				}
				continueAndResetButton.click();

				return PageFactory.initElements(driver, JalapenoForgotPasswordPage4.class);
		}
		
		public JalapenoForgotPasswordPage4 fillInWrongSecretAnswer(String answer) throws InterruptedException {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 60, secretAnswer);
			int i = 0;
			while (i < 6) {
				secretAnswer.sendKeys(answer);
				Thread.sleep(2000);
				continueAndResetButton.click();
				secretAnswer.clear();
				i++;
			}
			IHGUtil.waitForElement(driver, 10, msgAccountLocked);
			log("Looking for account locked message");
			msgAccountLocked.isDisplayed();
			return PageFactory.initElements(driver, JalapenoForgotPasswordPage4.class);
		}

}
