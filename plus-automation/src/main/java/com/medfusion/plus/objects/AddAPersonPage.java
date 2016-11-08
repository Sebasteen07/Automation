package com.medfusion.plus.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.plus.angular.NgWebDriver;

public class AddAPersonPage {
	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	private WebElement name;
	private WebElement zipCode;

	public AddAPersonPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@id='zip']")));
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
	}
}
