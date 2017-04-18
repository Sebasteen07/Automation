package com.medfusion.plus.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.plus.angular.NgWebDriver;

public class HomePage {
	private WebDriver driver;
	private NgWebDriver ngWebDriver;

	public HomePage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@icon-on='mf-icon mf-color__inverse mf-icon__profile']")));
	}

	public void selectPersonTab() throws InterruptedException {
		Thread.sleep(2000);
		WebElement element = driver.findElement(By.xpath("//a[@icon-on='mf-icon mf-color__inverse mf-icon__profile']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}

	public String verifyProfileName() {
		WebElement element = driver.findElement(By.xpath("//span[@class='nav-bar-title ng-binding']"));
		String name = element.getText();
		return name;
	}
}
