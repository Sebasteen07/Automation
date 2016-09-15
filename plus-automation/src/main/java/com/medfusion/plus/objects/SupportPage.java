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
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@href='https://medfusionplus.com/tos/index.html']")));
	}

	public void goToFAQ() {
		WebElement element = driver.findElement(By.xpath("//div[@href='https://medfusionplus.com/faq/']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}

	public void goToSupportRequest() {
		WebElement element = driver.findElement(By.xpath("//div[@href='#/support/support-request']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}

	public void goToAboutMedfusionPlus() {
		WebElement element = driver.findElement(By.xpath("//div[@href='#/support/about-the-app']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}

	public void goToPrivacyStatement() {
		WebElement element = driver.findElement(By.xpath("//div[@href='https://medfusionplus.com/privacy/index.html']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
	}

	public void goToTermsOfService() {
		WebElement element = driver.findElement(By.xpath("//div[@href='https://medfusionplus.com/tos/index.html']"));

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

	public void clickBackButton() throws InterruptedException {
		driver.navigate().back();
	}
}
