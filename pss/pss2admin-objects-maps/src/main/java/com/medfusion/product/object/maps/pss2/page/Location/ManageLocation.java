// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Location;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;

public class ManageLocation extends PSS2MenuPage {

	@FindBy(how = How.ID, using = "search-location")
	private WebElement locationSearch;

	@FindBy(how = How.XPATH, using = "//*[@title=\"Add Practice Location\"]")
	private WebElement addLocation;

	@FindBy(how = How.XPATH, using = "//tfoot/tr/td/mfbootstrappaginator/mfpaginator/ul[2]/li[3]/a")
	private WebElement show50Location;


	@FindBy(how = How.XPATH, using = "//select[@name='profile']")
	private WebElement selectTimeZone;

	@FindBy(how = How.XPATH, using = "//table/tbody/tr/td[1]/span/a")
	private WebElement selectLocation;


	public ManageLocation(WebDriver driver) {
		super(driver);
	}

	public List<WebElement> getLocationNameList() {

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		List<WebElement> locationListOfNames = driver.findElements(By.xpath("//*[@class=\"mt\"]/table/tbody/tr/td/span/a"));
		return locationListOfNames;
	}

	public void showMoreLocation() {
		IHGUtil.waitForElement(driver, 120, locationSearch);
		javascriptClick(show50Location);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return false;
	}

	public void searchlocation(String locationname) {
		locationSearch.sendKeys(locationname);

	}

	public void selectlocation(String locationname) {
		searchlocation(locationname);
		IHGUtil.waitForElement(driver, 60, selectLocation);
		selectLocation.click();
		log("clicked on location  ");
	}

	public String getTimezone() {
		Select select = new Select(selectTimeZone);
		WebElement option = select.getFirstSelectedOption();
		String gettimezone = option.getText();
		String gettimezonetrim = gettimezone.trim();
		log("Current TimeZone on adminUI is " + gettimezonetrim);
		return gettimezonetrim;
	}
}
