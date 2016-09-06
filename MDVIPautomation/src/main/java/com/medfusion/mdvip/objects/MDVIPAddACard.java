package com.medfusion.mdvip.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPAddACard {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;

	public MDVIPAddACard(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
	}

	public void addRequiredCardInfo(String cardName, String cardNumber, String expMonth, String expYear, String cvv,
			String billAddress, String billCity, String billState, String billZip) {

		WebElement name = driver.findElement(By.id("cardName"));
		name.sendKeys(cardName);
		WebElement number = driver.findElement(By.id("cardNumber"));
		number.sendKeys(cardNumber);
		WebElement expMon = driver.findElement(By.id("expMonth"));
		expMon.sendKeys(expMonth);
		WebElement expYr = driver.findElement(By.id("expYear"));
		expYr.sendKeys(expYear);
		WebElement verification = driver.findElement(By.id("cvv"));
		verification.sendKeys(cvv);
		WebElement address = driver.findElement(By.id("billAddress1"));
		address.sendKeys(billAddress);
		WebElement city = driver.findElement(By.id("billCity"));
		city.sendKeys(billCity);
		WebElement zip = driver.findElement(By.id("billZip"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", zip);
		zip.sendKeys(billZip);
		Select state = new Select(driver.findElement(By.id("billState")));
		state.selectByVisibleText(billState);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}
}
