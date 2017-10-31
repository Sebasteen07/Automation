package com.medfusion.dre.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class AdminUILoginPage extends BasePageObject {
	
	private WebDriver driver;
	private WebElement username;
	private WebElement password;

	public AdminUILoginPage(FirefoxDriver driver, String url) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading login page");
		String sanitizedUrl = url.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
		driver.findElement(By.linkText("Sign in")).click();
		
		username = driver.findElement(By.id("username"));
		password = driver.findElement(By.id("password"));
	}
	
	public void login(String userName, String securedPassword) throws InterruptedException {
		username.clear();
		username.sendKeys(userName);

		password.clear();
		password.sendKeys(securedPassword);

		driver.findElement(By.className("mf-login-button normal allow")).click();
	}
}
