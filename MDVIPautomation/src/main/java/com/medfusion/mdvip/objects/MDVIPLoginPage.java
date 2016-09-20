package com.medfusion.mdvip.objects;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPLoginPage {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	private WebElement username;
	private WebElement password;
	
	
	private static final Logger log = LogManager.getLogger(MDVIPLoginPage.class);

	public MDVIPLoginPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		driver.findElement(By.linkText("Sign in")).click();
		ngWebDriver.waitForAngularRequestsToFinish();
		username = driver.findElement(By.id("username"));
		password = driver.findElement(By.id("password"));
	}
	
	public boolean verifyLoginPageElements() {

        if(username==null)
        {
        	log.info("Username is not present");
        	return false;
        }
        if(password==null)
        {
        	log.info("password is not present");
        	return false;
        }
        log.info("Elements Found");
        return true;        
    }

	public void login(String userName, String securepassword) {
		
		username.clear();
		username.sendKeys(userName);
		
		password.clear();
		password.sendKeys(securepassword);
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

}
