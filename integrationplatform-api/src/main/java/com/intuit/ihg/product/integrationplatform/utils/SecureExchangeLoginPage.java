package com.intuit.ihg.product.integrationplatform.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SecureExchangeLoginPage {
	protected WebDriver driver;
	@FindBy(how = How.ID, using = "UserName")
	public WebElement inputUserName;
	
	@FindBy(how = How.ID, using = "Password")
	public WebElement inputPassword;
	
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Sign In')]")
	public WebElement buttonSignIn;
	
	public SecureExchangeLoginPage(WebDriver driver,String url) {
		this.driver = driver;
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		PageFactory.initElements(driver, this);
	}

	public SecureExchangeEmailPage SecureLogin(String username, String password) {
		inputUserName.sendKeys(username);
		inputPassword.sendKeys(password);
		buttonSignIn.click();
		return PageFactory.initElements(driver, SecureExchangeEmailPage.class);
	}
}