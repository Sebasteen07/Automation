// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoForgotPasswordPage4 extends MedfusionPage {

		@FindBy(how = How.ID, using = "newPassword")
		public WebElement newPassword;

		@FindBy(how = How.ID, using = "confirmPassword")
		public WebElement confirmPassword;

		@FindBy(how = How.ID, using = "resetButtonContinueButton")
		public WebElement resetPasswordButton;

		@FindBy(how = How.ID, using = "paymentPreference_Electronic")
		private WebElement electronicPaymentPreference;

		@FindBy(how = How.ID, using = "updateMissingInfoButton")
		private WebElement okButton;
		
		@FindBy(how = How.ID, using = "updateMissingInformationForm")
		private WebElement statementDailougeBox;
		
		@FindBy(how = How.ID, using = "secretAnswer_forgot")
		public WebElement secretAnswer;
		
		@FindBy(how = How.XPATH, using = "//input[@name='securityAnswer']")
		public WebElement answerSecret;
		
		@FindBy(how = How.XPATH, using = "//select[@id='secretQuestion']")
		public WebElement securityQuestion;
		
			
		public JalapenoForgotPasswordPage4(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
				log("Loading ForgotPasswordPage4");
		}

		public JalapenoHomePage fillInNewPassword(String password) throws InterruptedException {
				newPassword.sendKeys(password);
				confirmPassword.sendKeys(password);
				resetPasswordButton.click();
				selectStatementIfRequired();
				return PageFactory.initElements(driver, JalapenoHomePage.class);
		}

		private void selectStatementIfRequired() throws InterruptedException {
			if (new IHGUtil(driver).exists(statementDailougeBox))  {
				new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(electronicPaymentPreference));
						electronicPaymentPreference.click();
						okButton.click();
				}
		}

		public JalapenoHomePage fillInPassword(String password) throws Exception {
			PropertyFileLoader testData = new PropertyFileLoader();
			newPassword.sendKeys(password);
			confirmPassword.sendKeys(password);
			resetPasswordButton.click();
			fillInSecretQuestionAndAnswer(testData.getProperty("reset.password.security.question"),testData.getProperty("reset.password.security.answer"));
			
			return PageFactory.initElements(driver, JalapenoHomePage.class);
		}
		
		public void fillInSecretQuestionAndAnswer(String secretQuestion, String secretAns) throws InterruptedException {
				Select DDsecretQuestion = new Select(securityQuestion);
				DDsecretQuestion.selectByVisibleText(secretQuestion);
				answerSecret.sendKeys(secretAns);
				okButton.click();
			}			
		}
		
