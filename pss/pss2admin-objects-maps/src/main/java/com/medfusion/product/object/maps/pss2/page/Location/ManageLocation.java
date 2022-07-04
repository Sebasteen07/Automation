// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
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
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

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
	
	@FindBy(how = How.XPATH, using = "//span[@class='ng-star-inserted']//*[name()='svg']")
	private WebElement addressCheckbox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='zipCode']")
	private WebElement fillZipCodeValue;
	
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	private WebElement saveBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='app']/nav[2]/ul[2]/li[4]/a")
	private WebElement settingBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='app']/nav[2]/ul[2]/li[4]/ul")
	private WebElement logoutBtn;
	
	CommonMethods commonMethods = new CommonMethods(driver);

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
	
	public void changeAddressZipCode(String zipCode) {
		addressCheckbox.click();
		fillZipCodeValue.clear();
		fillZipCodeValue.sendKeys(zipCode);
		IHGUtil.waitForElement(driver, 60, saveBtn);
		commonMethods.highlightElement(saveBtn);
		javascriptClick(saveBtn);
		log("ZipCode changed for selected location");
	}
	
	public void logout() {
		settingBtn.click();
		logoutBtn.click();
	}
}
