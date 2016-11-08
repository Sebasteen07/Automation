package com.medfusion.plus.objects;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.plus.angular.NgWebDriver;

public class ProfilePage {
	private WebDriver driver;
	private NgWebDriver ngWebDriver;
	private static WebElement zip;

	public ProfilePage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//button[@class='button mf-button mf-button-danger mf-button-outline mf-button-circle']")));
		zip = driver.findElement(By.id("zip"));
	}

	public int addNewZipCode() throws InterruptedException {
		int zipCode = gen();
		zip.clear();
		zip.sendKeys(String.valueOf(zipCode));
		zip.sendKeys(Keys.TAB);
		zip.sendKeys(Keys.ENTER);

		return zipCode;
	}

	public int gen() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}

	public String verifyZipCode() {
		String zipCode = zip.getAttribute("value");
		return zipCode;
	}

	public void clickDeleteThisProfile() throws InterruptedException {
		driver.findElement(By.xpath("//button[@class='button mf-button mf-button-danger mf-button-outline mf-button-circle']")).click();
		clickDeleteOnPopupWindow();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@ng-click='afterdeleteModal.cancel()']")).click();
	}

	public void clickDeleteOnPopupWindow() {
		String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
		String subWindowHandler = null;

		Set<String> handles = driver.getWindowHandles(); // get all window handles
		Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler);
		driver.findElement(By.xpath("//button[@class='button ng-binding mf-button mf-button-danger']")).click();
		driver.switchTo().window(parentWindowHandler);
	}
}
