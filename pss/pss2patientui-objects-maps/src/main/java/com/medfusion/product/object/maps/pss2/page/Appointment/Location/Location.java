package com.medfusion.product.object.maps.pss2.page.Appointment.Location;

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
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;


public class Location extends PSS2MainPage {

	@FindAll({@FindBy(xpath = "//a[@class='locationlink locationlinkclick']")})
	private List<WebElement> locationList;

	@FindAll({@FindBy(xpath = "//a[contains(text(),'River Oaks Main')]")})
	private List<WebElement> locationRever;

	@FindBy(how = How.XPATH, using = "//select[@id='sel1']")
	private WebElement selectRadius;

	@FindBy(how = How.XPATH, using = "//div[@class='col-sm-4 locationclass']//input[@placeholder='Zipcode']")
	private WebElement nearByZipCodeInput;

	public Location(WebDriver driver) {
		super(driver);
		// PageFactory.initElements(driver, Location.class);
	}

	private String addressValue = null;

	public AppointmentPage selectAppointment(String locationName) throws InterruptedException {
		isViewallmessagesButtonPresent(driver);
		log("location " + locationName);
		for (int i = 0; i < locationList.size(); i++) {
			if (locationList.get(i).getText().contains(locationName)) {
				log("Location of user found at " + locationList.get(i).getText());
				// getLocAddress(i + 1);
				javascriptClick(locationList.get(i));
				return PageFactory.initElements(driver, AppointmentPage.class);
			}
		}
		return null;
	}


	public Provider searchProvider(String locationName) throws InterruptedException {

		log("In SearchProvider Method");
		// isViewallmessagesButtonPresent(driver);
		for (int i = 0; i < locationList.size(); i++) {
			if (locationList.get(i).getText().contains(locationName)) {
				log("Location is ---> " + locationList.get(i).getText());

				locationList.get(i).click();
				// javascriptClick(locationList.get(i));
				// getLocAddress(i);
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		return PageFactory.initElements(driver, Provider.class);

	}





	public AppointmentDateTime selectDatTime(String dateTime) throws Exception {
		isViewallmessagesButtonPresent(driver);
		log("location " + dateTime);
		for (int i = 0; i < locationList.size(); i++) {
			if (locationList.get(i).getText().contains(dateTime)) {
				log("Location of user found at " + locationList.get(i).getText());
				// getLocAddress(i);

				javascriptClick(locationList.get(i));
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return null;

	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		// webElementsList.add(locationList.get(0));
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());

	}

	public boolean isViewallmessagesButtonPresent(WebDriver driver) throws InterruptedException {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
.withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(3, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(NoSuchFrameException.class)
				.ignoring(WebDriverException.class);
		
		boolean result = wait.until(new Function<WebDriver, Boolean>() {
			     public Boolean apply(WebDriver driver) {			    	
				return driver.findElement(By.className("locationlinkclick")).isDisplayed();
			       }
			     }
				);
		return result;
	}


	// public void getLocAddress(int indexValue) {
	// try {
	// Thread.sleep(9000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// log(indexValue + " = " + locationAddress.size());
	//
	// for (int i = 0; i < locationAddress.size(); i++) {
	// if (i == indexValue) {
	// setAddressValue(locationAddress.get(i).getText());
	// }
	// }
	//
	// }


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
		// log("ZipSearch display =" + nearByZipCodeInput.isDisplayed() + " and Select Dropdown display=" + selectRadius.isDisplayed());
		if (selectRadius.isDisplayed() && nearByZipCodeInput.isDisplayed()) {
			log("selectRadius.isDisplayed  --->" + selectRadius.isDisplayed());
			return true;
		} else {
			return false;
		}
	}
}