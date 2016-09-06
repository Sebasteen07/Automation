package com.medfusion.mdvip.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPSelectAProfilePage {
	
	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	
	public MDVIPSelectAProfilePage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
	}
	
	public void clickProfile() {
		WebElement element = driver.findElement(By.xpath("//div[@href='#/new-tabs-layout/profile-tabs/12/home/dashboard']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public void clickAddAPerson() {
		WebElement element = driver.findElement(By.xpath("//div[@href='#/new-tabs-layout/create']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public boolean verifyProfileNotPresent(String name) {
		if (driver.getPageSource().contains(name)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void clickMoreOptions() throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("//button[@class='button button-icon ion-more']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
		Thread.sleep(1000);
	}
	
	public void goToSettings() {
		WebElement element = driver.findElement(By.xpath("//ion-item[@href='#/new-tabs-layout/settings']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
		
	}
}
