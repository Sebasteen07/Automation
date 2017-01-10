package com.medfusion.mdvip.objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPPaymentMethods {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;

	public MDVIPPaymentMethods(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();
	}

	public void addNewPayment() {
		WebElement element = driver.findElement(By.xpath("//li[@href='#/new-tabs-layout/settings/payment-methods/add-payment-method']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

	public boolean verifyPaymentMethodListed(String name) {
		List<WebElement> allElements = driver.findElements(By.xpath("//ul[@class='mf-list']/li"));
		boolean result = false;
		for (WebElement element : allElements) {
			System.out.println(element.getText());
			if (element.getText().contains(name)) {
				result = true;
				break;
			}
		}
		return result;
	}
}
