package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NewPatientIDP extends PSS2MainPage {

	@FindBy(how = How.ID, using = "loginUsername")
	private WebElement inputLoginUsername;

	@FindBy(how = How.ID, using = "loginPassword")
	private WebElement inputLoginPassword;

	@FindBy(how = How.XPATH, using = "/html/body/div[3]/div[1]/div[1]/form/a/span")
	private WebElement buttonSignIn;

	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "Create a new account")
	private WebElement createNewAccount;

	@FindBy(how = How.LINK_TEXT, using = "Forgot username")
	private WebElement forgotUserName;

	@FindBy(how = How.LINK_TEXT, using = "Forgot password")
	private WebElement forgotPassword;

	public NewPatientIDP(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public void createNewAccount() {
		createNewAccount.click();
	}
}
