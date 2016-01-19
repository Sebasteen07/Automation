package com.medfusion.product.object.maps.jalapeno.page;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage.JalapenoForgotPasswordPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JalapenoLoginPage extends BasePageObject {

	@FindBy(how = How.ID, using = "userid")
	public WebElement inputUserId;

	@FindBy(how = How.ID, using = "password")
	public WebElement inputPassword;

	@FindBy(how = How.ID, using = "signin_btn")
	public WebElement signInButton;

	@FindBy(how = How.ID, using = "create_btn")
	public WebElement joinButton;

	@FindBy(how = How.ID, using = "remember")
	public WebElement rememberUserNameCheckbox;

	@FindBy(how = How.ID, using = "paynow_button")
	public WebElement payNowButton;
	
	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "I forgot my user name and/or password.")
	public WebElement forgotUserOrPasswordButton;
	
	@FindBy(how = How.ID, using = "paymentPreference_Electronic")
	private WebElement electronicPaymentPreference;
	
	@FindBy(how = How.ID, using = "updateMissingInfoButton")
	private WebElement okButton;

	public JalapenoLoginPage(WebDriver driver, String url) {

		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading login page");
		String sanitizedUrl = url.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
	}
	
	public JalapenoLoginPage(WebDriver driver) {
		
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean assessLoginPageElements() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputUserId);
		webElementsList.add(inputPassword);
		webElementsList.add(forgotUserOrPasswordButton);
		webElementsList.add(signInButton);
		webElementsList.add(joinButton);
		webElementsList.add(rememberUserNameCheckbox);
		webElementsList.add(payNowButton);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public JalapenoHomePage login(String username, String password) {

		IHGUtil.PrintMethodName();
		log("Login Credentials: [" + username + "] [" + password + "]");
		// catching webdriver exception which started to show up after selenium 2.45 and firefox 36 updates
		// try removing the try catch once newrelic is deprecated and fully removed
		try {
			inputUserId.clear();
			inputUserId.sendKeys(username);
		}
		catch (org.openqa.selenium.WebDriverException e) {
			inputUserId.clear();
			inputUserId.sendKeys(username);
		}
		inputPassword.sendKeys(password);
		signInButton.click();
		
		selectStatementIfRequired();
		
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
	
	public JalapenoCreateAccountPage clickSignInButton() {
		
		IHGUtil.PrintMethodName();
		log("Clicking on Join In button");
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(joinButton));
		joinButton.click();
		return PageFactory.initElements(driver, JalapenoCreateAccountPage.class);
	}
	
	public JalapenoForgotPasswordPage clickForgotPasswordButton() {
		IHGUtil.PrintMethodName();
		log("Clicking on Forgot Password button");
		forgotUserOrPasswordButton.click();
		return PageFactory.initElements(driver, JalapenoForgotPasswordPage.class);
	}
	
	public boolean isTextVisible(String text) {
		return driver.findElement(By.xpath("// * [contains(text(),'" + text + "')]")).isDisplayed();
	}
	
	private void selectStatementIfRequired() {
		if ( new IHGUtil(driver).exists(electronicPaymentPreference) ) {
			electronicPaymentPreference.click();
			okButton.click();
		}
	}

}
