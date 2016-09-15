package com.medfusion.plus.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.medfusion.plus.angular.NgWebDriver;

public class CreateAccountPage {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;

	public CreateAccountPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		driver.findElement(By.linkText("Create a new account")).click();
		ngWebDriver.waitForAngularRequestsToFinish();
	}

	public void createAccount(String userName, String securepassword) {

		WebElement username = driver.findElement(By.id("username"));
		username.clear();
		username.sendKeys(userName);

		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(securepassword);

		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

}
