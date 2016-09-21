package com.medfusion.mdvip.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPAddPaymentMethod {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;

	public MDVIPAddPaymentMethod(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
	}

	public void clickAddACard() {
		WebElement element = driver.findElement(By.xpath("//li[@href='#/new-tabs-layout/settings/payment-methods/add-or-edit-card/']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

	public void clickAddABankAccount() {
		WebElement element = driver.findElement(By.xpath("//li[@href='#/new-tabs-layout/settings/payment-methods/add-or-edit-bank-account/']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
}
