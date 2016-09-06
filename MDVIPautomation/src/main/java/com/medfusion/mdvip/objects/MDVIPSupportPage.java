package com.medfusion.mdvip.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPSupportPage {
	
	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	private String winHandleBefore;
	
	public MDVIPSupportPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
	}
		
	public void goToFAQ() {		
		WebElement element = driver.findElement(By.xpath("//div[@href='https://connect.mdvip.com.qa.mindgruve.com/app/frequently-asked-questions']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public void goToSupportRequest() {		
		WebElement element = driver.findElement(By.xpath("//div[@href='#/support-request']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public void goToAboutMDVIPConnect() {		
		WebElement element = driver.findElement(By.xpath("//div[@href='#/support/about']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public void goToPrivacyStatement() {		
		WebElement element = driver.findElement(By.xpath("//div[@href='https://connect.mdvip.com.qa.mindgruve.com/app/privacy-policy']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public void goToTermsOfService() {		
		WebElement element = driver.findElement(By.xpath("//div[@href='https://connect.mdvip.com.qa.mindgruve.com/app/terms-of-use']"));
		
		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}
	
	public String getWindowBeforePopUp() {
		winHandleBefore = driver.getWindowHandle();
		return winHandleBefore;
	}
	
	public void switchToNewWindow() {		
		for(String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}	
	}
	
	public void goBackToPreviousWindow(String windowBefore) {
		driver.switchTo().window(windowBefore);
	}
	
	public void clickBackButton() throws InterruptedException {
		Thread.sleep(2000);
		driver.navigate().back();
	}
}
