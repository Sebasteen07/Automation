package com.medfusion.mdvip.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPConnectionsPage {
	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	
	public MDVIPConnectionsPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
	}
	
	public void clickEditMyProfile() {
		WebElement element = driver.findElement(By.xpath("//li[@ui-sref='^.profile({ profileId: selectedProfile.id })']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
}
