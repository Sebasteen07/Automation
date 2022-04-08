// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Provider;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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
	@FindAll({
			@FindBy(xpath = "//*[@class='col-sm-6 col-xs-12 provider-width-btn'or @class='btn providerimage-btn handle-text-Overflow outer-div'or @class='btn providerimage-btn handle-text-Overflow outer-div ']") })
	private List<WebElement> providerList;

	@FindAll({ @FindBy(xpath = "//div[@id='providerwizard']/div/a/div/span") })
	private List<WebElement> providerList1;

	@FindAll({ @FindBy(xpath = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[4]/div[2]/div[2]/a[1]/div[1]/div[2]") })
	private WebElement providerNextavaliable;

	@FindBy(how = How.ID, using = "providerserach")
	private WebElement searchForProvider;
	
	@FindBy(how = How.ID, using = "//h5[@class='announcementmessages']/div")
	private WebElement careTeamAnn;

	@FindAll({ @FindBy(css = ".providerImage-width") })
	private List<WebElement> providerImages;

	@FindAll({ @FindBy(xpath = "//div[@class='inner-div']/span") })
	private List<WebElement> providernameLink;

	public Provider(WebDriver driver) {
		super(driver);
	}

	CommonMethods CommonMethods = new CommonMethods(driver);

	public Location selectLocation(String providerName) {
		log("in selectLocation providerList" + providerName);
		for (int i = 0; i < providerList.size(); i++) {
			log(providerList.get(i).getText() + " match "
					+ providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().trim().equalsIgnoreCase(providerName.trim())) {
				providerList.get(i).click();
				return PageFactory.initElements(driver, Location.class);
			}
		}
		return PageFactory.initElements(driver, Location.class);
	}
	
	public Location selectLocation1(String providerName) {
		log("in selectLocation providerList" + providerName);
		for (int i = 0; i < providerList1.size(); i++) {
			log(providerList1.get(i).getText() + " match "
					+ providerList1.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList1.get(i).getText().trim().equalsIgnoreCase(providerName.trim())) {
				providerList1.get(i).click();
				return PageFactory.initElements(driver, Location.class);
			}
		}
		return PageFactory.initElements(driver, Location.class);
	}

	public AppointmentPage selectAppointment(String providerName) {
		log("size= " + providerList.size());
		log("Text= " + providerList.get(0).getText());
		for (int i = 0; i < providerList.size(); i++) {
			log(providerList.get(i).getText() + " match " + providerName + "= "
					+ providerList.get(i).getText().trim().equalsIgnoreCase(providerName.trim()));
			if (providerList.get(i).getText().trim().equalsIgnoreCase(providerName.trim())) {
				providerList.get(i).click();
				log("Clicked on the Provider");
				return PageFactory.initElements(driver, AppointmentPage.class);
			}
		}
		return PageFactory.initElements(driver, AppointmentPage.class);
	}

	public AppointmentDateTime selectDateTime(String providerName) throws InterruptedException {
		isViewallmessagesButtonPresent(driver);
		IHGUtil.waitForElement(driver, 150, providerList.get(0));
		log("size= " + providerList.size());
		log("Text= " + providerList.get(0).getText());
		for (int i = 0; i < providerList.size(); i++) {
			log(providerList.get(i).getText() + " match "
					+ providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().contains(providerName)) {
				providerList.get(i).click();
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public boolean isViewallmessagesButtonPresent(WebDriver driver) throws InterruptedException {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(60))
				.pollingEvery(Duration.ofSeconds(3)).ignoring(NoSuchElementException.class)
				.ignoring(NoSuchFrameException.class).ignoring(WebDriverException.class);
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

	public String getProviderNamesfromProviderList(String providerName) {
		searchForProvider.clear();
		searchForProvider.sendKeys(providerName.trim());
		log("providerList = " + providerList.size());
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(providerList.get(0));
		String provider = providerList.get(0).getText();
		log("First provider is" + provider);
		return provider;
	}

	public void getProvider(String providerName) {
		log("in select Provider from providerList" + providerName);
		for (int i = 0; i < providerList.size(); i++) {
			log(providerList.get(i).getText() + " match "
					+ providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().trim().equalsIgnoreCase(providerName.trim())) {
				providerList.get(i).click();
			}
		}
	}

	public String getProviderText(String providerName) {
		log("in select Provider from providerList" + providerName);
		String book = null;
		for (int i = 0; i < providerList.size(); i++) {
			log(providerList.get(i).getText() + " match "
					+ providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().contains(providerName)) {
				book = providerList.get(i).getText();
				break;
			}
		}
		return book;
	}

	public AppointmentDateTime searchForProviderFromList(String providerName) throws InterruptedException {
		searchForProvider.sendKeys(providerName.trim());
		log("providerList = " + providerList.size());
		Thread.sleep(8000);
		CommonMethods.highlightElement(providerList.get(0));
		Thread.sleep(3000);
		providerList.get(0).click();
		log("Clicked on the Provider ");
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public AppointmentDateTime getProviderandClick(String providerName) throws InterruptedException {
		log("in select Provider from providerList" + providerName);
		log("Size is " + providerList.size());
		Thread.sleep(1000);
		for (int i = 0; i < providerList.size(); i++) {
			log(providerList.get(i).getText() + " match " + providerList.get(i).getText().contains(providerName));
			if (providerList.get(i).getText().trim().contains(providerName.trim())) {
				CommonMethods.highlightElement(providerList.get(i));
				IHGUtil.waitForElement(driver, 13, providerList.get(i));
				Thread.sleep(1000);
				log("Search Provider");
				log("Provider of user found at " + providerList.get(i).getText());
				providerList.get(i).click();
				log("Clicked on Provider");
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}
	
	public AppointmentDateTime getProviderAndClick1(String providerName) throws InterruptedException {
		log("in select Provider from providerList" + providerName);
		log("Size is " + providerList1.size());
		Thread.sleep(1000);
		for (int i = 0; i < providerList1.size(); i++) {
			log(providerList1.get(i).getText() + " match " + providerList1.get(i).getText().contains(providerName));
			if (providerList1.get(i).getText().trim().contains(providerName.trim())) {
				CommonMethods.highlightElement(providerList1.get(i));
				IHGUtil.waitForElement(driver, 13, providerList1.get(i));
				Thread.sleep(1000);
				log("Search Provider");
				log("Provider of user found at " + providerList1.get(i).getText());
				providerList1.get(i).click();
				log("Clicked on Provider");
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public int searchForProviderFromListt(String providerName) throws InterruptedException {
		searchForProvider.sendKeys(providerName);
		log("providerList = " + providerList.size());
		Thread.sleep(1000);
		return providerList.size();
	}
	
	public ArrayList<String> getBookList() throws InterruptedException {

		ArrayList<String> providerName = new ArrayList<String>();

		for (int i = 0; i < providerList1.size(); i++) {
			providerName.add(providerList1.get(i).getText());
		}
		log("providerList = " + providerName);
		return providerName;
	}
	
	public String getCareTeamAnn() {
		String message=careTeamAnn.getText();
		log("Care Team Announcement Message- "+message);
		return message;
	}
	
	public int getNumberOfBook() throws InterruptedException {
		log("providerList = " + providerList.size());
		return providerList.size();
	}

	public AppointmentDateTime searchForProviderFromList1(String providerName) throws InterruptedException {
		searchForProvider.sendKeys(providerName.trim());
		log("providerList = " + providerList.size());
		Thread.sleep(20000);
		getNextavaliableDate();
		CommonMethods.highlightElement(providerList.get(0));
		providerList.get(0).click();
		log("Clicked on the Provider ");
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public String getNextavaliableDate() {

		log("Next Available date is  " + providerNextavaliable.getText());
		String nextav = providerNextavaliable.getText();
		String nextDate = nextav.substring(16, 28);
		log("Only date is  " + nextDate);
		return nextDate;
	}

	public String getNextavaliableText() {
		return providerNextavaliable.getText();
	}
}
