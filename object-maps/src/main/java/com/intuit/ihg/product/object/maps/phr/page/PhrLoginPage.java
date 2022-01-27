//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.phr.page;

import static org.testng.Assert.assertFalse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class PhrLoginPage extends BasePageObject {

	public static final String PAGE_NAME = "PHR Portal Login Page";

	@FindBy(xpath = "//font[@id='error']")
	private WebElement loginError;

	@FindBy(xpath = "//input[@name='loginName']")
	private WebElement txtloginName;

	@FindBy(xpath = "//input[@name='password']")
	private WebElement txtpassword;

	@FindBy(xpath = "(//a)[1]")
	private WebElement btnLogin;

	public PhrLoginPage(WebDriver driver) {
		super(driver);
	}

	public void setLoginName(String sLogin) {
		IHGUtil.PrintMethodName();
		log("LOGIN: [" + sLogin + "]");
		txtloginName.sendKeys(sLogin);
	}

	public void setPassword(String sPassword) {
		IHGUtil.PrintMethodName();
		log("PASSWORD: [" + sPassword + "]");
		txtpassword.sendKeys(sPassword);
	}

	public PhrHomePage login(String sUser, String sPassword) throws InterruptedException {
		IHGUtil.PrintMethodName();
		log("Waiting for the Login Name element, max wait time is 60 seconds");
		IHGUtil.waitForElement(driver, 60, txtloginName);
		this.setLoginName(sUser);
		this.setPassword(sPassword);
		btnLogin.click();
		return PageFactory.initElements(driver, PhrHomePage.class);
	}

	public PhrLoginPage(WebDriver driver, String baseURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		String sanitizedUrl = baseURL.trim();
		log("baseURL: " + baseURL);
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		maxWindow();
		// See if SSL setup properly
		assertFalse(driver.getTitle().contains("Untrusted Connection"),
				"### PHR SSL CERT ISSUE (Untrusted Connection) ?");
		PageFactory.initElements(driver, this);
	}
	
	public boolean waitforTXTPassword(WebDriver driver, int n) throws InterruptedException {
		IHGUtil.PrintMethodName();
		return IHGUtil.waitForElement(driver, n, txtpassword);
	}

	public boolean isSearchPageLoaded() {
		IHGUtil.PrintMethodName();
		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 160, txtpassword);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
}
