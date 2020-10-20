package com.medfusion.product.object.maps.patientportal2.page;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage.JalapenoForgotPasswordPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.PayNow.JalapenoPayNowPage;

public class JalapenoLoginPage extends MedfusionPage {

	@FindBy(how = How.ID, using = "userid")
	private WebElement inputUserName;
	
	@FindBy(how = How.ID, using = "userid_error")
	private WebElement userNameError;

	@FindBy(how = How.ID, using = "password")
	private WebElement inputPassword;
	
	@FindBy(how = How.ID, using = "password_error")
	private WebElement passwordError;

	@FindBy(how = How.ID, using = "signin_btn")
	private WebElement buttonSignIn;

	@FindBy(how = How.ID, using = "create_btn")
	private WebElement buttonCreateANewAccount;

	@FindBy(how = How.ID, using = "remember")
	private WebElement rememberUserNameCheckbox;

	@FindBy(how = How.ID, using = "paynow_button")
	private WebElement buttonPayNow;

	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "I forgot my user name and/or password.")
	private WebElement forgotUserOrPasswordButton;

	@FindBy(how = How.ID, using = "paymentPreference_Electronic")
	private WebElement electronicPaymentPreference;

	@FindBy(how = How.ID, using = "updateMissingInfoButton")
	private WebElement okButton;

	@FindBy(how = How.XPATH, using = "//span[@data-ng-show = 'notice.existingaccount_same']")
    private WebElement healthKeyMatchError;

	public JalapenoLoginPage(WebDriver driver, String url) {
		super(driver, url);
	}

	public JalapenoLoginPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputUserName);
		webElementsList.add(inputPassword);
		webElementsList.add(forgotUserOrPasswordButton);
		webElementsList.add(buttonSignIn);
		webElementsList.add(buttonCreateANewAccount);
		webElementsList.add(rememberUserNameCheckbox);
		return assessPageElements(webElementsList);
	}

	public JalapenoHomePage login(String username, String password) {
		makeLogin(username, password);
		log("User is logged in");
		handleWeNeedToConfirmSomethingModal();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
	
	public  void loginEmptyCredentials() {
		makeLogin("", "");
		log("User clicked Signin in with empty credentials");		
			}

	public JalapenoLoginPage loginUnsuccessfuly(String username, String password) {
		makeLogin(username, password);
		log("User was not successfuly logged in");
		return PageFactory.initElements(driver, JalapenoLoginPage.class);
	}

	private void makeLogin(String username, String password) {
		log("Trying to login with Credentials: [" + username + "] [" + password + "]");
		updateWebElement(inputUserName, username);
		updateWebElement(inputPassword, password);
		clickOnElement(buttonSignIn);
	}

	public String getUserErrorText() {
		return userNameError.getText();
		
	}
	
	public String getPasswordErrorText() {
		return passwordError.getText();
		
	}
	
	public PatientDemographicPage clickCreateANewAccountButton() {

		IHGUtil.PrintMethodName();
		log("Clicking on Create a new account button");
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(buttonCreateANewAccount));
		buttonCreateANewAccount.click();
		return PageFactory.initElements(driver, PatientDemographicPage.class);
	}

	public JalapenoForgotPasswordPage clickForgotPasswordButton() {
		IHGUtil.PrintMethodName();
		log("Clicking on Forgot Password button");
		forgotUserOrPasswordButton.click();
		return PageFactory.initElements(driver, JalapenoForgotPasswordPage.class);
	}

	public JalapenoPayNowPage clickPayNowButton() {
		IHGUtil.PrintMethodName();
		log("Clicking on Pay Now button");
		buttonPayNow.click();
		return PageFactory.initElements(driver, JalapenoPayNowPage.class);
	}

	private void selectStatementIfRequired() {
		if (new IHGUtil(driver).exists(electronicPaymentPreference, 10)) {
			electronicPaymentPreference.click();
			okButton.click();
		}
	}

	public boolean isExistingAccountErrorDisplayed() {
	    try {
	        log("Looking for account already exists error on loginPage");
	        return healthKeyMatchError.isDisplayed();
	    }
	    catch (Exception e) {	        
	    }
	    return false;
	}
	
public JalapenoHomePage RememberUserName(String username, String password) {
		
		rememberUserNameCheckbox.click();
		
		log("unchecking the Remember username checkbox");
		makeLogin(username, password);
		log("User is logged in");
		handleWeNeedToConfirmSomethingModal();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

public String getUserNameFieldText()
{
	return inputUserName.getText();
	
}
}


