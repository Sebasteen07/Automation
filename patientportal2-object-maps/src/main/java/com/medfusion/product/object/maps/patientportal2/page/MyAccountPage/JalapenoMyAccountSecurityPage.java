//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoMyAccountSecurityPage extends JalapenoMyAccountPage {

		@FindBy(how = How.XPATH, using = "//button[contains(@class, 'listItemBtn')]")
		private WebElement changeEmailBtn;

		@FindBy(how = How.XPATH, using = "(//button[contains(@class, 'listItemBtn')])[2]")
		private WebElement changePasswordBtn;

		@FindBy(how = How.XPATH, using = "(//button[contains(@class, 'listItemBtn')])[3]")
		private WebElement changeUserNameBtn;

		@FindBy(how = How.XPATH, using = "(//button[contains(@class, 'listItemBtn')])[4]")
		private WebElement changeSecurityQuestionBtn;

		@FindBy(how = How.ID, using = "currentPassword")
		private WebElement currentPasswordInput;

		@FindBy(how = How.ID, using = "newPassword")
		private WebElement newPasswordInput;

		@FindBy(how = How.ID, using = "confirmPassword")
		private WebElement confirmPasswordInput;

		@FindBy(how = How.ID, using = "newEmail")
		private WebElement newEmailInput;

		@FindBy(how = How.ID, using = "confirmEmail")
		private WebElement confirmEmailInput;

		@FindBy(how = How.ID, using = "newUsername")
		private WebElement newUserNameInput;

		@FindBy(how = How.ID, using = "confirmUsername")
		private WebElement confirmUserNameInput;

		@FindBy(how = How.ID, using = "secretQuestion")
		private WebElement secretQuestionSelect;

		@FindBy(how = How.ID, using = "secretAnswer")
		private WebElement secretQuestionAnswerInput;

		@FindBy(how = How.ID, using = "prevStep")
		private WebElement previousStep;

		@FindBy(how = How.ID, using = "saveAccountChanges")
		private WebElement saveAccountChanges;

		@FindBy(how = How.XPATH, using = "//p[text()='You have successfully updated your email address.']")
		private WebElement emailSuccessfulUpdateMessage;

		@FindBy(how = How.XPATH, using = "//p[text()='You have successfully updated your password.']")
		private WebElement passwordSuccessfulUpdateMessage;

		@FindBy(how = How.XPATH, using = "//p[text()='You have successfully updated your user name.']")
		private WebElement userNameSuccessfulUpdateMessage;

		@FindBy(how = How.XPATH, using = "//p[text()='You have successfully updated your security question and answer.']")
		private WebElement securityQuestionSuccessfulUpdateMessage;

		@FindBy(how = How.XPATH, using = "//ul[contains(@class, 'myAccountList')]")
		private WebElement myAccountList;
		
		@FindBy(how = How.XPATH, using = "//span[contains(text(),'This password does not match our records.')]")
		private WebElement txtCurrentPasswordErrorMsg;
		
		private static final String VERIFICATION_XPATH = ".//div/text()[normalize-space(.)='%s']/parent::*";

		public JalapenoMyAccountSecurityPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
				driver.manage().window().maximize();
				PageFactory.initElements(driver, this);
		}

		public void goToChangeEmailAndAssessElements() {
				changeEmailBtn.click();
				assertTrue(areEmailChangePageElementsPresent());
		}

		public void goToChangePasswordAndAssessElements() {
				changePasswordBtn.click();
				assertTrue(arePasswordChangePageElementsPresent());
		}

		public void goToChangeUserNameAndAssessElements() {
				changeUserNameBtn.click();
				assertTrue(areUserNameChangePageElementsPresent());
		}

		public void goToChangeSecurityQuestionAndAssessElements() {
				changeSecurityQuestionBtn.click();
				assertTrue(areSecurityQuestionChangePageElementsPresent());
		}

		public void changeEmailAndVerify(String currentPassword, String newEmail) {
			currentPasswordInput.sendKeys(IHGUtil.createRandomNumericString(8));
			newEmailInput.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(txtCurrentPasswordErrorMsg));
			currentPasswordInput.clear();
			currentPasswordInput.sendKeys(currentPassword);
			newEmailInput.click();
			newEmailInput.sendKeys(newEmail);
			confirmEmailInput.sendKeys(newEmail);
			saveAccountChanges.click();
			new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(emailSuccessfulUpdateMessage));
			findDivInMyAccountListWithTextValue(newEmail);
		}

		public void changeUserNameAndVerify(String currentPassword, String newUserName) throws InterruptedException {
			currentPasswordInput.sendKeys(IHGUtil.createRandomNumericString(8));
			newUserNameInput.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(txtCurrentPasswordErrorMsg));
			currentPasswordInput.clear();
			currentPasswordInput.sendKeys(currentPassword);
			newUserNameInput.click();
			newUserNameInput.sendKeys(newUserName);
			confirmUserNameInput.sendKeys(newUserName);
			scrollAndWait(0,300,5000);
			saveAccountChanges.click();
			new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(userNameSuccessfulUpdateMessage));
			findDivInMyAccountListWithTextValue(newUserName);
		}

		public void changeSecurityQuestionAndVerify(String currentPassword, String newSecurityQuestion, String newSecurityQuestionAnswer) {
			currentPasswordInput.sendKeys(IHGUtil.createRandomNumericString(8));
			secretQuestionAnswerInput.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(txtCurrentPasswordErrorMsg));
			currentPasswordInput.clear();
			currentPasswordInput.sendKeys(currentPassword);
			secretQuestionAnswerInput.click();
			new Select(secretQuestionSelect).selectByValue(newSecurityQuestion);
			secretQuestionAnswerInput.sendKeys(newSecurityQuestionAnswer);
			saveAccountChanges.click();
			new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(securityQuestionSuccessfulUpdateMessage));
			findDivInMyAccountListWithTextValue(newSecurityQuestion);
		}

		public void changePassword(String currentPassword, String newPassword) {
			currentPasswordInput.sendKeys(IHGUtil.createRandomNumericString(8));
			newPasswordInput.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(txtCurrentPasswordErrorMsg));
			currentPasswordInput.clear();
			currentPasswordInput.sendKeys(currentPassword);
			newPasswordInput.click();
			newPasswordInput.sendKeys(newPassword);
			confirmPasswordInput.sendKeys(newPassword);
			saveAccountChanges.click();
			new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(passwordSuccessfulUpdateMessage));
		}

		public JalapenoMyAccountPreferencesPage goToPrefererencesTab() {

				log("Click on Preferences");
				preferencesTab.click();

				return PageFactory.initElements(driver, JalapenoMyAccountPreferencesPage.class);
		}

		private boolean areEmailChangePageElementsPresent() {
				ArrayList<WebElement> webElementList = new ArrayList<WebElement>();
				webElementList.add(currentPasswordInput);
				webElementList.add(newEmailInput);
				webElementList.add(confirmEmailInput);
				webElementList.add(saveAccountChanges);
				return assessPageElements(webElementList);
		}

		private boolean arePasswordChangePageElementsPresent() {
				ArrayList<WebElement> webElementList = new ArrayList<WebElement>();
				webElementList.add(currentPasswordInput);
				webElementList.add(newPasswordInput);
				webElementList.add(confirmPasswordInput);
				webElementList.add(saveAccountChanges);
				return assessPageElements(webElementList);
		}

		private boolean areUserNameChangePageElementsPresent() {
				ArrayList<WebElement> webElementList = new ArrayList<WebElement>();
				webElementList.add(currentPasswordInput);
				webElementList.add(newUserNameInput);
				webElementList.add(confirmUserNameInput);
				webElementList.add(saveAccountChanges);
				return assessPageElements(webElementList);
		}

		private boolean areSecurityQuestionChangePageElementsPresent() {
				ArrayList<WebElement> webElementList = new ArrayList<WebElement>();
				webElementList.add(currentPasswordInput);
				webElementList.add(secretQuestionSelect);
				webElementList.add(secretQuestionAnswerInput);
				webElementList.add(saveAccountChanges);
				return assessPageElements(webElementList);
		}

		private void findDivInMyAccountListWithTextValue(String value) {
				myAccountList.findElement(By.xpath(String.format(VERIFICATION_XPATH, value)));
		}

}
