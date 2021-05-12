// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

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

	public LoginlessPatientInformation loginLessNewPatient() {
		linkCreateNewAccount.click();
		return PageFactory.initElements(driver, LoginlessPatientInformation.class);
	}

}
