package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;

public class NewPatientLoginPage extends PSS2MainPage {

	@FindBy(how = How.ID, using = "loginUsername")
	private WebElement inputUserName;

	@FindBy(how = How.ID, using = "loginPassword")
	private WebElement inputPassword;

	@FindBy(how = How.CLASS_NAME, using = "mf-sign-in-text")
	private WebElement buttonSubmit;

	@FindBy(how = How.LINK_TEXT, using = "Create a new account")
	private WebElement linkCreateNewAccount;

	@FindBy(how = How.LINK_TEXT, using = "Forgot username")
	private WebElement linkForgotUserName;

	@FindBy(how = How.LINK_TEXT, using = "Forgot password")
	private WebElement linkForgotPassword;
	// Create a new account

	public NewPatientLoginPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(linkForgotPassword);
		webElementsList.add(linkForgotUserName);
		webElementsList.add(linkCreateNewAccount);
		webElementsList.add(buttonSubmit);
		webElementsList.add(inputPassword);
		webElementsList.add(inputUserName);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public NewPatient createNewAccount() {
		linkCreateNewAccount.click();
		return PageFactory.initElements(driver, NewPatient.class);
	}

	public LoginlessPatientInformation loginLessNewPatient() {
		linkCreateNewAccount.click();
		return PageFactory.initElements(driver, LoginlessPatientInformation.class);
	}

}
