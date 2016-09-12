package com.medfusion.mdvip.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.mdvip.angular.NgWebDriver;

public class MDVIPAccountPage {

	private WebDriver driver;
	private NgWebDriver ngWebDriver;

	public MDVIPAccountPage(FirefoxDriver driver) {
		this.driver = driver;
		ngWebDriver = new NgWebDriver(driver);
		this.driver.manage().window().maximize();
		ngWebDriver.waitForAngularRequestsToFinish();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[@href='#/new-tabs-layout/create']")));
	}

	public void updateMemberProfile(String salutation, String street, String city, String state, String zip, String email, String phone)
			throws InterruptedException {

		Select inputSalutation = new Select(driver.findElement(By.id("salutationInput")));
		inputSalutation.selectByVisibleText(salutation);
		driver.findElement(By.id("mailingStreetInput")).clear();
		WebElement inputStreet = driver.findElement(By.id("mailingStreetInput"));
		inputStreet.sendKeys(street);
		driver.findElement(By.id("mailingCityInput")).clear();
		WebElement inputCity = driver.findElement(By.id("mailingCityInput"));
		inputCity.sendKeys(city);
		Select inputState = new Select(driver.findElement(By.id("mailingStateInput")));
		inputState.selectByVisibleText(state);
		driver.findElement(By.id("mailingPostalCodeInput")).clear();
		WebElement inputZip = driver.findElement(By.id("mailingPostalCodeInput"));
		inputZip.sendKeys(zip);
		driver.findElement(By.id("emailInput")).clear();
		WebElement inputEmail = driver.findElement(By.id("emailInput"));
		inputEmail.sendKeys(email);
		WebElement inputPhone = driver.findElement(By.id("phoneInput"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", inputPhone);
		inputPhone.clear();
		inputPhone.sendKeys(phone);

		inputPhone.sendKeys(Keys.TAB, Keys.TAB, Keys.TAB, Keys.ENTER);
	}

	public void resetMemberProfile(String salutation, String street, String city, String state, String zip, String email, String phone)
			throws InterruptedException {

		Select inputSalutation = new Select(driver.findElement(By.id("salutationInput")));
		inputSalutation.selectByVisibleText(salutation);
		driver.findElement(By.id("mailingStreetInput")).clear();
		WebElement inputStreet = driver.findElement(By.id("mailingStreetInput"));
		inputStreet.sendKeys(street);
		driver.findElement(By.id("mailingCityInput")).clear();
		WebElement inputCity = driver.findElement(By.id("mailingCityInput"));
		inputCity.sendKeys(city);
		Select inputState = new Select(driver.findElement(By.id("mailingStateInput")));
		inputState.selectByVisibleText(state);
		driver.findElement(By.id("mailingPostalCodeInput")).clear();
		WebElement inputZip = driver.findElement(By.id("mailingPostalCodeInput"));
		inputZip.sendKeys(zip);
		driver.findElement(By.id("emailInput")).clear();
		WebElement inputEmail = driver.findElement(By.id("emailInput"));
		inputEmail.sendKeys(email);
		WebElement inputPhone = driver.findElement(By.id("phoneInput"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", inputPhone);
		inputPhone.clear();
		inputPhone.sendKeys(phone);

		inputPhone.sendKeys(Keys.TAB, Keys.TAB, Keys.TAB, Keys.ENTER);
	}

	public String getSalutation() {
		String salutation = new Select(driver.findElement(By.id("salutationInput"))).getFirstSelectedOption().getText();
		return salutation;
	}

	public String getStreet() {
		String street = driver.findElement(By.id("mailingStreetInput")).getAttribute("value");
		return street;
	}

	public String getCity() {
		String city = driver.findElement(By.id("mailingCityInput")).getAttribute("value");
		return city;
	}

	public String getEmail() {
		String email = driver.findElement(By.id("emailInput")).getAttribute("value");
		return email;
	}

	public String getPhone() {
		String phone = driver.findElement(By.id("phoneInput")).getAttribute("value");
		return phone;
	}
}
