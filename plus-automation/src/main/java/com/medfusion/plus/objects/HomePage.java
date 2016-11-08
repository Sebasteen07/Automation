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
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@icon='ion-person']")));
	}

	public void selectPersonTab() {
		WebElement element = driver.findElement(By.cssSelector(".tab-item[icon='ion-person']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}

	public String verifyProfileName() {
		WebElement element = driver.findElement(By.xpath("//span[@class='nav-bar-title ng-binding']"));
		String name = element.getText();
		return name;
	}
}
