// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class Provider extends PSS2MainPage {

	@FindAll({@FindBy(xpath = "//div[@class='col-sm-6 col-xs-12 provider-width-btn']")})
	private List<WebElement> providerList;

	@FindBy(how = How.ID, using = "providerserach")
	private WebElement searchForProvider;

	@FindAll({@FindBy(css = ".providerImage-width")})
	private List<WebElement> providerImages;

	public Provider(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		// webElementsList.add(providerList.get(0));
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	CommonMethods CommonMethods = new CommonMethods(driver);

	public Location selectLocation(String providerName) {
		log("in selectLocation providerList" + providerName);
		for (int i = 0; i < providerList.size(); i++) {
			log(providerList.get(i).getText() + " match " + providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().contains(providerName)) {
				providerList.get(i).click();
				return PageFactory.initElements(driver, Location.class);
			}
		}
		return null;
	}

	public AppointmentPage selectAppointment(String providerName) {
		log("size= " + providerList.size());
		log("Text= " + providerList.get(0).getText());
		for (int i = 0; i < providerList.size(); i++) {
			log(providerList.get(i).getText() + " match " + providerName + " = " + providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().contains(providerName)) {
				providerList.get(i).click();
				return PageFactory.initElements(driver, AppointmentPage.class);
			}
		}
		return null;
	}

	public AppointmentDateTime selectDateTime(String providerName) throws InterruptedException {
		isViewallmessagesButtonPresent(driver);
		IHGUtil.waitForElement(driver, 150, providerList.get(0));
		log("size= " + providerList.size());
		log("Text= " + providerList.get(0).getText());
		for (int i = 0; i < providerList.size(); i++) {
			log(providerList.get(i).getText() + " match " + providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().contains(providerName)) {
				providerList.get(i).click();
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public boolean isViewallmessagesButtonPresent(WebDriver driver) throws InterruptedException {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(3, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).ignoring(NoSuchFrameException.class).ignoring(WebDriverException.class);

		boolean result = wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return driver.findElement(By.cssSelector(".btn")).isDisplayed();
			}
		});
		return result;
	}

	public int providerListSize() {
		return providerList.size();
	}

	public int providerImageSize() {
		return providerImages.size();
	}

	public List<WebElement> getProviderNames() {
		return providerList;
	}

	public AppointmentDateTime searchForProviderFromList(String providerName) throws InterruptedException {
		searchForProvider.sendKeys(providerName);
		log("providerList = " + providerList.size());
		Thread.sleep(6000);

		CommonMethods.highlightElement(providerList.get(0));
		providerList.get(0).click();
		log("Clicked on the Provider ");
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public int searchForProviderFromListt(String providerName) throws InterruptedException {
		searchForProvider.sendKeys(providerName);
		log("providerList = " + providerList.size());
		Thread.sleep(6000);
		return providerList.size();
	}
}
