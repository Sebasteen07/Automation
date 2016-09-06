package com.medfusion.mdvip.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPSettingsPage {
	
	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	
	public MDVIPSettingsPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
	}
	
	public void goToPayments() {
		WebElement element = driver.findElement(By.xpath("//li[@href='#/new-tabs-layout/settings/payment-methods']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public void goToAccount() {
		WebElement element = driver.findElement(By.xpath("//li[@href='#/new-tabs-layout/settings/account']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public void goToNotifications() {
		WebElement element = driver.findElement(By.xpath("//li[@href='#/new-tabs-layout/settings/notifications']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public void goToAutopay() {
		WebElement element = driver.findElement(By.xpath("//li[@href='#/new-tabs-layout/settings/autopay']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
}
