package com.medfusion.product.object.maps.jalapeno.page;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

public class JalapenoLoginPage extends BasePageObject {

	@FindBy(how = How.ID, using = "userid")
	public WebElement inputUserId;

	@FindBy(how = How.ID, using = "password")
	public WebElement inputPassword;

	@FindBy(how = How.ID, using = "signinForgotUserid")
	public WebElement forgotUserIdLink;

	@FindBy(how = How.ID, using = "signinForgotUserPassword")
	public WebElement forgotUserPasswordLink;

	@FindBy(how = How.ID, using = "signin_btn")
	public WebElement signInButton;

	@FindBy(how = How.ID, using = "create_btn")
	public WebElement joinButton;

	@FindBy(how = How.ID, using = "remember_label")
	public WebElement rememberUserNameCheckbox;

	@FindBy(how = How.ID, using = "healthkeyInfo")
	public WebElement healthKeyInfoLinkElement;

	@FindBy(how = How.ID, using = "paynow_button")
	public WebElement payNowButton;

	@FindBy(how = How.ID, using = "switchLanguage")
	public WebElement switchLanguageLink;

	@FindBy(how = How.ID, using = "help")
	public WebElement helpLink;

	public JalapenoLoginPage(WebDriver driver, String url) {

		super(driver);
		IHGUtil.PrintMethodName();
		String sanitizedUrl = url.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean assessLoginPageElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputUserId);
		webElementsList.add(inputPassword);
		webElementsList.add(forgotUserIdLink);
		webElementsList.add(forgotUserPasswordLink);
		webElementsList.add(signInButton);
		webElementsList.add(joinButton);
		webElementsList.add(rememberUserNameCheckbox);
		webElementsList.add(healthKeyInfoLinkElement);
		webElementsList.add(payNowButton);
		webElementsList.add(switchLanguageLink);
		webElementsList.add(helpLink);

		for (WebElement w : webElementsList) {

			try {
				log("Checking WebElement" + w.toString());
				if (w.isDisplayed()) {
					log("WebElement " + w.toString() + "is displayed");
					allElementsDisplayed = true;
				} else {
					return false;
				}
			}

			catch (Throwable e) {
				log(e.getStackTrace().toString());
			}

		}
		return allElementsDisplayed;
	}

	public JalapenoHomePage login(String username, String password) {

		IHGUtil.PrintMethodName();
		log("Login Credentials: [" + username + "] [" + password + "]");
		inputUserId.sendKeys(username);
		inputPassword.sendKeys(password);
		signInButton.click();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

}
