// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;

public class CreateNewAccountIDP extends PSS2MainPage {

	@FindBy(how = How.ID, using = "createEmail")
	public WebElement inputEmail;

	@FindBy(how = How.ID, using = "createUsername")
	public WebElement inputUsername;

	@FindBy(how = How.ID, using = "createPassword")
	public WebElement inputPassword;

	@FindBy(how = How.ID, using = "copyEmailCheckbox")
	public WebElement emailAsUserNameCheck;

	@FindBy(how = How.ID, using = "termsAgreed")
	public WebElement acceptTOC;

	@FindBy(how = How.XPATH, using = "/html/body/div[3]/div[1]/div[2]/form/a")
	public WebElement buttonCreateAccount;

	@FindBy(how = How.LINK_TEXT, using = "Return to login")
	public WebElement returnToLoginPage;

	@FindBy(how = How.XPATH, using = "//*[@id=\"createProfileError\"]")
	public WebElement errorMessage;

	public CreateNewAccountIDP(WebDriver driver) {
		super(driver);
	}

	public void createNewAccount(String email, String password) {
		inputEmail.sendKeys(email);
		emailAsUserNameCheck.click();
		inputPassword.sendKeys(password);
		acceptTOC.click();
		buttonCreateAccount.click();
	}

	public void createNewAccount(String email, String username, String password) {
		inputEmail.sendKeys(email);
		inputUsername.sendKeys(username);
		inputPassword.sendKeys(password);
		acceptTOC.click();
		buttonCreateAccount.click();
	}

	public String errorMessageText() {
		IHGUtil.waitForElement(driver, 80, errorMessage);
		return errorMessage.getText();
	}
}
