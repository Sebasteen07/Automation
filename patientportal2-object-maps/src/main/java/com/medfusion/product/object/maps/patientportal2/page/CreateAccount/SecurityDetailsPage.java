//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import com.medfusion.pojos.Patient;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class SecurityDetailsPage extends MedfusionPage {

		@FindBy(how = How.ID, using ="userid")
		private WebElement inputUserId;

		@FindBy(how = How.ID, using = "password")
		private WebElement inputPassword;

		@FindBy(how = How.ID, using = "secretQuestion")
		private WebElement selectSecretQuestion;

		@FindBy(how = How.ID, using = "secretAnswer")
		private WebElement inputSecretAnswer;
		
		@FindBy(how = How.XPATH, using = "//span[contains(text(),'Just one quick thing')]")
		private WebElement confirmationPopup;
		
		@FindBy(how = How.XPATH, using = "//button[@id='updateMissingInfoButton']")
		private WebElement updateMissingInfoButton;

		@FindBy(how = How.ID, using = "phone1")
		private WebElement inputPhone1;

		@FindBy(how = How.ID, using = "phone2")
		private WebElement inputPhone2;

		@FindBy(how = How.ID, using = "phone3")
		private WebElement inputPhone3;

		@FindBy(how = How.ID, using = "phone_type")
		private WebElement selectPhoneType;

		@FindBy(how = How.TAG_NAME, using = "ng-select")
		private WebElement primaryLocationElement;
		
		@FindBy(how = How.XPATH, using = "(//*[@class='ng-option'])[1]")
		private WebElement setLocation;

		@FindBy(how = How.ID, using = "prevStep")
		private WebElement buttonPreviousStep;

		@FindBy(how = How.ID, using = "finishStep")
		private WebElement buttonFinishStep;
		
		@FindBy(how = How.ID, using = "updateMissingInformationForm")
		private WebElement statementDailougeBox;
		
		@FindBy(how = How.ID, using = "paymentPreference_Electronic")
		private WebElement electronicPaymentPreference;

		@FindBy(how = How.ID, using = "paymentPreference_Paper")
		private WebElement paperPaymentPreference;

		@FindBy(how = How.ID, using = "paymentPreference_Both")
		private WebElement bothPaymentPreference;

		@FindBy(how = How.ID, using = "updateMissingInfoButton")
		private WebElement okButton;

		@FindBy(how = How.XPATH, using = "//span[@id = 'userid_error_invalid'][contains(text(),'The user name you entered is already taken. Enter another user name.')]")
		private WebElement usernameTakenError;
		
	    public SecurityDetailsPage(WebDriver driver) {
				super(driver);
		}

		public JalapenoHomePage fillAccountDetailsAndContinue(Patient patient) throws InterruptedException {
				return fillAccountDetailsAndContinue(patient.getUsername(), patient.getPassword(), patient.getSecurityQuestion(), patient.getSecurityQuestionAnswer(),
						patient.getPhoneMobile(), 2);
		}
		
		public JalapenoHomePage fillAccountDetailsAndContinueWithStatement(Patient patient, int statementValue)throws InterruptedException {
			return fillAccountDetailsAndContinue(patient.getUsername(), patient.getPassword(), patient.getSecurityQuestion(), patient.getSecurityQuestionAnswer(), 
					patient.getPhoneMobile(), statementValue);	
	}

		public JalapenoHomePage fillAccountDetailsAndContinue(String userId, String password, PropertyFileLoader testData) throws InterruptedException {
			return fillAccountDetailsAndContinue(userId, password, testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber(), 2);
		}

		public JalapenoHomePage fillAccountDetailsAndContinue(String userId, String password, String secretQuestion, String secretAnswer, String phoneNumber) throws InterruptedException {
				return fillAccountDetailsAndContinue(userId, password, secretQuestion, secretAnswer, phoneNumber, 2);
		}

		public JalapenoHomePage fillAccountDetailsAndContinue(String userId, String password, String secretQuestion, String secretAnswer, String phoneNumber,
				int statementPreference) throws InterruptedException {
				IHGUtil.PrintMethodName();
				fillAccountDetails(userId, password, secretQuestion, secretAnswer, phoneNumber, statementPreference);
				IHGUtil.waitForElement(driver, 60, buttonFinishStep);
				scrollAndWait(0,300,3000);
				log("Clicking finish btn");
				buttonFinishStep.click();
				selectStatementIfRequired(statementPreference); //TODO move to handleWeNeedToConfirmSomethingModal
				handleWeNeedToConfirmSomethingModal();
				return PageFactory.initElements(driver, JalapenoHomePage.class);
		}

		public void fillAccountDetailsAndContinueWithError(String userId, String password, PropertyFileLoader testData) throws InterruptedException {
				fillAccountDetails(userId, password, testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber(), 3);
				javascriptClick(buttonFinishStep);
		}

		private void fillAccountDetails(String userId, String password, String secretQuestion, String secretAnswer, String phoneNumber, int statementPreference) throws InterruptedException {
				log("Setting User Name and Password as " + userId + "/" + password);
				Thread.sleep(1000);
				inputUserId.sendKeys(userId);
				inputPassword.sendKeys(password);
				IHGUtil.waitForElement(driver, 3, selectSecretQuestion);
				selectSecretQuestion.sendKeys(secretQuestion);
				inputSecretAnswer.sendKeys(secretAnswer);
				scrollAndWait(0,300,2000);
				IHGUtil.waitForElement(driver, 3, inputPhone1);
				inputPhone1.sendKeys(phoneNumber.substring(0, 3));
				inputPhone2.sendKeys(phoneNumber.substring(3, 6));
				inputPhone3.sendKeys(phoneNumber.substring(6, 10));
				scrollAndWait(0,300,3000);

				if (new IHGUtil(driver).isRendered(primaryLocationElement)) {
						log("Set primary location");
						primaryLocationElement.click();
						IHGUtil.waitForElement(driver, 60, setLocation);
						javascriptClick(setLocation);

				}
		}

		public void selectStatementIfRequired(int deliveryPref) throws InterruptedException {

				if (new IHGUtil(driver).exists(statementDailougeBox)) {
						log("Statement delivery preference lightbox is displayed");
						if (deliveryPref == 1) {
							new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(paperPaymentPreference));
								paperPaymentPreference.click();
						} else if (deliveryPref == 2) {
							if (new IHGUtil(driver).exists(electronicPaymentPreference)) {
								new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(electronicPaymentPreference));
								electronicPaymentPreference.click();
							}
							
						} else if (deliveryPref == 3) {
							new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(bothPaymentPreference));
								bothPaymentPreference.click();
						}
						
						if (new IHGUtil(driver).exists(okButton)) {
							new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(okButton));

							okButton.click();
						}
						

						/*
						 * try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
						 */
				}

		}

		public boolean isUsernameTakenErrorDisplayed() {
				try {
						log("Looking for username taken error on SecurityDetailsPage");
						return usernameTakenError.isDisplayed();
				} catch (Exception e) {
				}
				return false;
		}
		}
