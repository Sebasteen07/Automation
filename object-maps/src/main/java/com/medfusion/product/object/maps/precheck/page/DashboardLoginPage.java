package com.medfusion.product.object.maps.precheck.page;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.precheck.page.HomePage.HomePage;

public class DashboardLoginPage extends BasePageObject {

	@FindBy(how = How.XPATH, using = ".//*[@id='loginFormInnerWrapper']/form/div[1]/input")
	public WebElement inputUserId;

	@FindBy(how = How.XPATH, using = ".//*[@id='loginFormInnerWrapper']/form/div[2]/input")
	public WebElement inputPassword;

	@FindBy(how = How.ID, using = "loginButton")
	public WebElement buttonLogin;

	public DashboardLoginPage(WebDriver driver, String url) {

		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading Dashboard login page");
		String sanitizedUrl = url.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
	}

	public DashboardLoginPage(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean assessLoginPageElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputUserId);
		webElementsList.add(inputPassword);
		webElementsList.add(buttonLogin);

		for (WebElement w : webElementsList) {

			try {
				IHGUtil.waitForElement(driver, 60, w);
				log("Checking WebElement" + w.toString());
				if (w.isDisplayed()) {
					log("WebElement " + w.toString() + "is displayed");
					allElementsDisplayed = true;
				} else {
					log("WebElement " + w.toString() + "is NOT displayed");
					return false;
				}
			}

			catch (Throwable e) {
				log(e.getStackTrace().toString());
			}

		}
		return allElementsDisplayed;
	}

	public HomePage login(String username, String password) {

		IHGUtil.PrintMethodName();
		log("Login Credentials: [" + username + "] [" + password + "]");
		try {
			inputUserId.sendKeys(username);
		} catch (org.openqa.selenium.WebDriverException e) {
			inputUserId.sendKeys(username);
		}
		inputPassword.sendKeys(password);
		buttonLogin.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

}
