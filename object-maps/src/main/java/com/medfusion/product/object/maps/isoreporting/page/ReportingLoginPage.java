package com.medfusion.product.object.maps.isoreporting.page;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;


public class ReportingLoginPage extends BasePageObject {

	@FindBy(how = How.ID, using = "username")
	public WebElement inputUserId;

	@FindBy(how = How.ID, using = "password")
	public WebElement inputPassword;

	@FindBy(how = How.ID, using = "loginButton")
	public WebElement loginButton;

	public ReportingLoginPage(WebDriver driver, String url) {

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
	
	public ReportingLoginPage(WebDriver driver) {
		
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean assessLoginPageElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputUserId);
		webElementsList.add(inputPassword);
		webElementsList.add(loginButton);

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

	public ReportingDailyReportPage login(String username, String password) {

		IHGUtil.PrintMethodName();
		log("Login Credentials: [" + username + "] [" + password + "]");
		try{
			inputUserId.clear();
			inputUserId.sendKeys(username);
		}
		catch (org.openqa.selenium.WebDriverException e) {
			inputUserId.sendKeys(username);
		}
		inputPassword.sendKeys(password);
		loginButton.click();
		return PageFactory.initElements(driver, ReportingDailyReportPage.class);
	}
	
	
	public boolean isTextVisible(String text) {
		return driver.findElement(By.xpath("// * [contains(text(),'" + text + "')]")).isDisplayed();
	}

}
