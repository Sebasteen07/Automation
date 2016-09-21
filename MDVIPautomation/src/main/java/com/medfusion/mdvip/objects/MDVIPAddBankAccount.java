package com.medfusion.mdvip.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPAddBankAccount {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;

	public MDVIPAddBankAccount(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
	}

	public void addRequiredBankInfo(String bankName, String routingNumber, String accountNumber, String address, String city, String state, String zip) {

		WebElement name = driver.findElement(By.id("bankName"));
		name.sendKeys(bankName);
		WebElement rNumber = driver.findElement(By.id("routingNumber"));
		rNumber.sendKeys(routingNumber);
		WebElement aNumber = driver.findElement(By.id("accountNumber"));
		aNumber.sendKeys(accountNumber);
		WebElement bankAddress = driver.findElement(By.id("address"));
		bankAddress.sendKeys(address);
		WebElement bankCity = driver.findElement(By.id("city"));
		bankCity.sendKeys(city);
		WebElement bankZip = driver.findElement(By.id("zip"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bankZip);
		bankZip.sendKeys(zip);
		Select bankState = new Select(driver.findElement(By.id("state")));
		bankState.selectByVisibleText(state);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}
}
