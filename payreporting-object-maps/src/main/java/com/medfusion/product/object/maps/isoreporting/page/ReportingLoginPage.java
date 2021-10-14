//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;


public class ReportingLoginPage extends BasePageObject {

	@FindBy(how = How.ID, using = "username")
	public WebElement inputUserId;

	@FindBy(how = How.ID, using = "password")
	public WebElement inputPassword;

	@FindBy(how = How.XPATH, using = "//a[@title='Login']")
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
		ArrayList<WebElement> loginPageElements = new ArrayList<WebElement>();
		loginPageElements.add(inputUserId);
		loginPageElements.add(inputPassword);
		loginPageElements.add(loginButton);
		IHGUtil util = new IHGUtil(driver);
		return util.assessAllPageElements(loginPageElements, ReportingLoginPage.class);
	}

	public ReportingDailyReportPage login(String username, String password) throws InterruptedException {

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
		Thread.sleep(5000);
		return PageFactory.initElements(driver, ReportingDailyReportPage.class);
	}
	
	
	public boolean isTextVisible(String text) {
		return driver.findElement(By.xpath("// * [contains(text(),'" + text + "')]")).isDisplayed();
	}

}
