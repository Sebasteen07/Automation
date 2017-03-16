package com.intuit.ihg.product.integrationplatform.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SecureExchangeLoginPage {
	protected WebDriver driver;
	@FindBy(how = How.ID, using = "UserName")
	public WebElement inputUserName;
	
	@FindBy(how = How.ID, using = "Password")
	public WebElement inputPassword;
	
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Sign In')]")
	public WebElement buttonSignIn;
	
	public SecureExchangeLoginPage(WebDriver driver,String url) throws InterruptedException {
		this.driver = driver;
		driver.get(url);
		driver.manage().window().maximize();
		Thread.sleep(5000);
		PageFactory.initElements(driver, this);
	}

	public SecureExchangeEmailPage SecureLogin(String username, String password) {
		inputUserName.sendKeys(username);
		inputPassword.sendKeys(password);
		buttonSignIn.click();
		return PageFactory.initElements(driver, SecureExchangeEmailPage.class);
	}
}