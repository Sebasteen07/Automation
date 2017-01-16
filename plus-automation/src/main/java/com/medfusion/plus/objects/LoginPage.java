package com.medfusion.plus.objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.plus.angular.NgWebDriver;

public class LoginPage {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	private WebElement username;
	private WebElement password;

	private static final Logger log = LogManager.getLogger(LoginPage.class);

	public LoginPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		driver.findElement(By.xpath("//button[@class='mf-cta__primary buffer-vertical--small']")).click();
		ngWebDriver.waitForAngularRequestsToFinish();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@class='mf-cta__secondary']")));
		username = driver.findElement(By.id("username"));
		password = driver.findElement(By.id("password"));
	}

	public boolean verifyLoginPageElements() {

		if (username == null) {
			log.info("Username is not present");
			return false;
		}
		if (password == null) {
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

	public void goToSupportPage() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@href='#/support']")).click();
	}

}
