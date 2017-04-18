package com.medfusion.plus.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.plus.angular.NgWebDriver;

public class SupportPage {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	private String winHandleBefore;

	public SupportPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@src='images/medfusion-logo.svg']")));
	}

	public void goToFAQ() throws InterruptedException {	
		Thread.sleep(200);
		WebElement element = driver.findElement(By.xpath("//li[@href='https://medfusionplus.com/faq/']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
	
	public void goToSendFeedback() throws InterruptedException {
		Thread.sleep(1000);
		WebElement element = driver.findElement(By.xpath("//li[@href='#/support/send-feedback']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

	public void goToSupportRequest() {
		WebElement element = driver.findElement(By.xpath("//li[@href='#/support/support-request']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

	public void goToAboutMedfusionPlus() throws InterruptedException {
		Thread.sleep(1000);
		WebElement element = driver.findElement(By.xpath("//li[@href='#/support/about-the-app']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
	
	public void goToRequestProvider() {
		WebElement element = driver.findElement(By.xpath("//div[@href='#/new-tabs-layout/support/provider-request']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}

	public void goToPrivacyStatement() throws InterruptedException {
		Thread.sleep(500);
		WebElement element = driver.findElement(By.xpath("//li[@href='https://medfusionplus.com/privacy/index.html']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

	public void goToTermsOfService() {
		WebElement element = driver.findElement(By.xpath("//li[@href='https://medfusionplus.com/tos/index.html']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

	public String getWindowBeforePopUp() {
		winHandleBefore = driver.getWindowHandle();
		return winHandleBefore;
	}

	public void switchToNewWindow() {
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public void goBackToPreviousWindow(String windowBefore) {
		driver.switchTo().window(windowBefore);
	}

	public void clickBackButton() {
		driver.navigate().back();
	}
}
