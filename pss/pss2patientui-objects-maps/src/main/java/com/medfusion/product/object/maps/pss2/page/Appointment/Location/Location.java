// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Location;

import java.time.Duration;
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
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;

public class Location extends PSS2MainPage {

	@FindAll({ @FindBy(xpath = "//a[@class='locationlink locationlinkclick']") })
	private List<WebElement> locationList;

	@FindAll({ @FindBy(xpath = "//a[contains(text(),'River Oaks Main')]") })
	private List<WebElement> locationRever;

	@FindBy(how = How.XPATH, using = "//select[@id='sel1']")
	private WebElement selectRadius;

	@FindBy(how = How.XPATH, using = "//div[@class='col-sm-4 locationclass']//input[@placeholder='Zipcode']")
	private WebElement nearByZipCodeInput;
	
	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Next Available']")
	private WebElement locationNextAvailable;

	@FindAll({@FindBy(xpath = "//*[@id='locationwizardlist']//a[@class='locationlink locationlinkclick']/following-sibling::div[2]")})
	private List<WebElement> adddress2;
	
	@FindAll({@FindBy(xpath = "//*[@id='locationwizardlist']/div[3]/div/div/button/span")})
	private List<WebElement> directionLink;

	@FindAll({@FindBy(xpath = "//a[@class='locationlink locationlinkclick']")})
	private List<WebElement> locationListByZipCode;
	
	@FindAll({ @FindBy(xpath = "//div[@class='list-group-item-location listingOfappointments col-sm-12 col-xs-12']") })
    private List<WebElement> locationListNew;

    @FindAll({ @FindBy(xpath = "//div[@class='list-group-item-location listingOfappointments col-sm-12 col-xs-12']/b") })
    private List<WebElement> locationNameDetails;

    @FindAll({ @FindBy(xpath = "//div[@class='list-group-item-location listingOfappointments col-sm-12 col-xs-12']/div[2]/div/a") })
    private List<WebElement> locationSelect;

	public Location(WebDriver driver) {
		super(driver);
	}

	private String addressValue = null;

//	public AppointmentPage selectAppointment(String locationName) throws InterruptedException {
//		isViewallmessagesButtonPresent(driver);
//		log("location " + locationName);
//		for (int i = 0; i < locationList.size(); i++) {
//			if (locationList.get(i).getText().contains(locationName)) {
//				log("Search Location");
//				log("Location of user found at " + locationList.get(i).getText());
//				javascriptClick(locationList.get(i));
//				log("clicke on location  " + locationName);
//				return PageFactory.initElements(driver, AppointmentPage.class);
//			}
//		}
//		return null;
//	}
	
	 public AppointmentPage selectAppointment(String locationName) throws InterruptedException {
	        log("In Location Search Method");
	        for (int i = 0; i < locationNameDetails.size(); i++) {
	            log("Size of Location List - "+locationNameDetails.size());
	            if (locationNameDetails.get(i).getText().contains(locationName)) {
	                log("Location is ---> " + locationNameDetails.get(i).getText());
	                log("Search Provider");
	                log("Provider of user found at " + locationNameDetails.get(i).getText());
	                IHGUtil.waitForElement(driver, 5, locationNameDetails.get(i));
	                locationSelect.get(i).click();
	                return PageFactory.initElements(driver, AppointmentPage.class);
	            }
	        }
	        return PageFactory.initElements(driver, AppointmentPage.class);
	    }

	public Provider searchProvider(String locationName) throws InterruptedException {
		log("In SearchProvider Method");
		for (int i = 0; i < locationList.size(); i++) {
			if (locationList.get(i).getText().contains(locationName)) {
				log("Location is ---> " + locationList.get(i).getText());
				log("Search Provider");
				log("Provider of user found at " + locationList.get(i).getText());
				IHGUtil.waitForElement(driver, 5, locationList.get(i));
				locationList.get(i).click();
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		return PageFactory.initElements(driver, Provider.class);
	}
	
	public AppointmentDateTime searchLocation(String locationName) throws InterruptedException {
		log("In SearchProvider Method");
		for (int i = 0; i < locationList.size(); i++) {
			if (locationList.get(i).getText().contains(locationName)) {
				log("Location is ---> " + locationList.get(i).getText());
				locationList.get(i).click();
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public AppointmentDateTime selectDatTime(String dateTime) throws Exception {
		log("location is  ");
		log("location " + dateTime);
		for (int i = 0; i < locationList.size(); i++) {
			if (locationList.get(i).getText().contains(dateTime)) {
				log("Location of user found at " + locationList.get(i).getText());
				javascriptClick(locationList.get(i));
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return null;
	}

	public boolean isViewallmessagesButtonPresent(WebDriver driver) throws InterruptedException {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(60))
				.pollingEvery(Duration.ofSeconds(3)).ignoring(NoSuchElementException.class)
				.ignoring(NoSuchFrameException.class).ignoring(WebDriverException.class);
		boolean result = wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return driver.findElement(By.className("locationlinkclick")).isDisplayed();
			}
		});
		return result;
	}

	public String getAddressValue() {
		return addressValue;
	}

	public void setAddressValue(String addressValue) {
		this.addressValue = addressValue;
	}

	public int getLocationListSize() {
		return locationList.size();
	}

	public List<WebElement> getLocationNames() {
		return locationList;
	}

	public Boolean isSearchLocationEnabled() {
		if (selectRadius.isDisplayed() && nearByZipCodeInput.isDisplayed()) {
			log("selectRadius.isDisplayed  --->" + selectRadius.isDisplayed());
			return true;
		} else {
			return false;
		}
	}
	
	public String getNextavaliableText() {
		IHGUtil.waitForElement(driver, 10, locationNextAvailable);
		return locationNextAvailable.getText();
	}

	public String address2(String addressLine2Name) {
		String name = "";
		for (int i = 0; i < adddress2.size(); i++) {
			if (adddress2.get(i).getText().equalsIgnoreCase(addressLine2Name)) {
				log("adddress2 is ---> " + adddress2.get(i).getText());
				name = adddress2.get(i).getText();
			}
		}
		return name;
	}
	
	public  void selectGetDirectionLink() throws Exception {
		WebElement button=directionLink.get(0);
		button.click();
	}
	
	public  String getDirectionLinktext() throws Exception {
		String linkgetText=directionLink.get(0).getText();
		return linkgetText;
	}

	public void searchLocationByZipCode(String zipCode) throws InterruptedException {
		nearByZipCodeInput.clear();
		nearByZipCodeInput.sendKeys(zipCode);
		WebElement searchRadius = driver.findElement(By.xpath("//*[@id='sel1']"));
		Select selectOptions = new Select(searchRadius);
		selectOptions.selectByIndex(0);
		Thread.sleep(2000);
		log("Nearby zip code and search radius are edited successfully");
	}
	
	public String chooseLocationText() {
		for( WebElement locationName : locationListByZipCode){
			String chooseLocationAddressText= locationName.getText();
			return chooseLocationAddressText;
		}
		return null;
	}
	
}
