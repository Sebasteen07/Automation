package com.medfusion.mdvip.objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPAutoPayPage {
	
	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	
	public MDVIPAutoPayPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
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
		}
		else {
			return false;
		}
	}
	
	public void clickBackButton() throws InterruptedException {
		Thread.sleep(2000);
		driver.navigate().back();
	}
}
