package com.medfusion.mdvip.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPAddAPersonPage {
	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	private WebElement name;
	private WebElement zipCode;
	
	public MDVIPAddAPersonPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
		name = driver.findElement(By.id("name"));
		zipCode = driver.findElement(By.id("zip"));
	}
	
	public void addNewPerson(String personName, String zip) throws InterruptedException {
		name.clear();
		name.sendKeys(personName);		
		zipCode.clear();
		zipCode.sendKeys(zip);			
		zipCode.sendKeys(Keys.TAB);
		zipCode.sendKeys(Keys.ENTER);
		Thread.sleep(1000);
	}
}
