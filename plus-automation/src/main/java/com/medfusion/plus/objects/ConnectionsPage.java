package com.medfusion.plus.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.plus.angular.NgWebDriver;

public class ConnectionsPage {
	private WebDriver driver;
	private NgWebDriver ngWebDriver;

	public ConnectionsPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("li[href*='connections/profile']")));
	}

	public void clickEditMyProfile() throws InterruptedException {
		Thread.sleep(2000);
		WebElement element = driver.findElement(By.cssSelector("li[href*='connections/profile']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
}
