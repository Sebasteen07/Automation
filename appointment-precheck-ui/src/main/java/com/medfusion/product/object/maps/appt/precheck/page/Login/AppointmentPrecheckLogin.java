package com.medfusion.product.object.maps.appt.precheck.page.Login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.appt.precheck.Main.ApptPrecheckMainPage;
import com.medfusion.product.object.maps.appt.precheck.util.ApptPrecheckConfiguration;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;

public class AppointmentPrecheckLogin extends ApptPrecheckMainPage {

	@FindBy(how = How.ID, using = "username")
	private WebElement inputUserName;

	@FindBy(how = How.ID, using = "password")
	private WebElement inputPassword;

	@FindBy(how = How.LINK_TEXT, using = "Login")
	private WebElement buttonLogin;

	@FindBy(how = How.CSS, using = "body > div.medfusion-logo")
	private WebElement medfusionLogo;

	@FindBy(how = How.CSS, using = "div.ping-header")
	private WebElement PleaseLogIn;

	public AppointmentPrecheckLogin(WebDriver driver) {
		super(driver);
	}

	public AppointmentPrecheckLogin(WebDriver driver, String url) {
		super(driver, url);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public ApptPrecheckConfiguration login(String username, String pass) throws InterruptedException {
		commonMethods.highlightElement(inputUserName);
		inputUserName.sendKeys(username);
		commonMethods.highlightElement(inputPassword);
		inputPassword.sendKeys(pass);
		buttonLogin.click();
		return PageFactory.initElements(driver, ApptPrecheckConfiguration.class);
	}

	public boolean visibilityOfMedfusionLogo() {
		IHGUtil.waitForElement(driver, 10, medfusionLogo);
		return medfusionLogo.isDisplayed();
	}

	public String logInText() {
		return PleaseLogIn.getText();
	}
}
