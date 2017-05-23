package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

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

	private static final String VERIFICATION_XPATH = ".//div/text()[normalize-space(.)='%s']/parent::*";

	public JalapenoMyAccountSecurityPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public void goToChangeEmailAndAssessElements() {
		changeEmailBtn.click();
		assessEmailChangePageElements();
	}

	public void goToChangePasswordAndAssessElements() {
		changePasswordBtn.click();
		assessPasswordChangePageElements();
	}

	public void goToChangeUserNameAndAssessElements() {
		changeUserNameBtn.click();
		assessUserNameChangePageElements();
	}

	public void goToChangeSecurityQuestionAndAssessElements() {
		changeSecurityQuestionBtn.click();
		assessSecurityQuestionChangePageElements();
	}

	public void changeEmailAndVerify(String currentPassword, String newEmail) {
		currentPasswordInput.sendKeys(currentPassword);
		newEmailInput.sendKeys(newEmail);
		confirmEmailInput.sendKeys(newEmail);
		saveAccountChanges.click();
		new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(emailSuccessfulUpdateMessage));
		findDivInMyAccountListWithTextValue(newEmail);
	}

	public void changeUserNameAndVerify(String currentPassword, String newUserName) {
		currentPasswordInput.sendKeys(currentPassword);
		newUserNameInput.sendKeys(newUserName);
		confirmUserNameInput.sendKeys(newUserName);
		saveAccountChanges.click();
		new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(userNameSuccessfulUpdateMessage));
		findDivInMyAccountListWithTextValue(newUserName);
	}

	public void changeSecurityQuestionAndVerify(String currentPassword, String newSecurityQuestion, String newSecurityQuestionAnswer) {
		currentPasswordInput.sendKeys(currentPassword);
		new Select(secretQuestionSelect).selectByValue(newSecurityQuestion);
		secretQuestionAnswerInput.sendKeys(newSecurityQuestionAnswer);
		saveAccountChanges.click();
		new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(securityQuestionSuccessfulUpdateMessage));
		findDivInMyAccountListWithTextValue(newSecurityQuestion);
	}

	public void changePassword(String currentPassword, String newPassword) {
		currentPasswordInput.sendKeys(currentPassword);
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

	public boolean areBasicPageElementsPresent() {
		return super.assessPageElements(false) && assessElements(changeEmailBtn, changePasswordBtn, changeUserNameBtn, changeSecurityQuestionBtn);
	}

	private boolean assessEmailChangePageElements() {
		return super.assessPageElements(true) && assessElements(currentPasswordInput, newEmailInput, confirmEmailInput);
	}

	private boolean assessPasswordChangePageElements() {
		return super.assessPageElements(true) && assessElements(currentPasswordInput, newPasswordInput, confirmPasswordInput);
	}

	private boolean assessUserNameChangePageElements() {
		return super.assessPageElements(true) && assessElements(currentPasswordInput, newUserNameInput, confirmUserNameInput);
	}

	private boolean assessSecurityQuestionChangePageElements() {
		return super.assessPageElements(true) && assessElements(currentPasswordInput, secretQuestionSelect, secretQuestionAnswerInput);
	}

	private boolean assessElements(WebElement... elements) {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		for (WebElement e : elements) {
			webElementsList.add(e);
		}
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	private void findDivInMyAccountListWithTextValue(String value) {
		myAccountList.findElement(By.xpath(String.format(VERIFICATION_XPATH, value)));
	}

}
