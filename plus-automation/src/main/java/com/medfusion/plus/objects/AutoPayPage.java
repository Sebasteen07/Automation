package com.medfusion.plus.objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.plus.angular.NgWebDriver;

public class AutoPayPage {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;

	public AutoPayPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//label[@class='toggle mf-toggle disable-user-behavior']")));
	}

	public void enableAutopay() {
		WebElement element = driver.findElement(By.xpath("//label[@class='toggle mf-toggle disable-user-behavior']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}

	public void disableAutopay() {
		WebElement element = driver.findElement(By.xpath("//label[@class='toggle mf-toggle disable-user-behavior']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}

	public boolean verifyAutopayIsOn() {
		List<WebElement> element = driver.findElements(By.xpath("//input[@class='ng-pristine ng-untouched ng-valid ng-not-empty']"));
		if (element.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void clickBackButton() {
		driver.navigate().back();
	}
}
